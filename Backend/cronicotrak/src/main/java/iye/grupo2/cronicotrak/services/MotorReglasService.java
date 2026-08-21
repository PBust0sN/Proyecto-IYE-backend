package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.entities.Alerta;
import iye.grupo2.cronicotrak.entities.Medicion;
import iye.grupo2.cronicotrak.entities.Paciente;
import iye.grupo2.cronicotrak.entities.PacientePatologia;
import iye.grupo2.cronicotrak.repositories.AlertaRepository;
import iye.grupo2.cronicotrak.repositories.ControlRepository;
import iye.grupo2.cronicotrak.repositories.MedicionRepository;
import iye.grupo2.cronicotrak.repositories.PacientePatologiaRepository;
import iye.grupo2.cronicotrak.repositories.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MotorReglasService {

    private final ControlRepository controlRepository;
    private final AlertaRepository alertaRepository;
    private final WhatsAppService whatsAppService;
    private final MedicionRepository medicionRepository;
    private final PacienteRepository pacienteRepository;
    private final PacientePatologiaRepository pacientePatologiaRepository;

    public void evaluarReglasPorMedicion(Medicion medicion) {
        if (medicion.getIndicador() == null || medicion.getPaciente() == null || medicion.getValor() == null) {
            return;
        }

        Paciente paciente = medicion.getPaciente();
        BigDecimal valor = medicion.getValor();
        BigDecimal lower = medicion.getIndicador().getLower();
        BigDecimal upper = medicion.getIndicador().getUpper();

        boolean isCritica = false;
        if (lower != null && valor.compareTo(lower) < 0) {
            isCritica = true;
        }
        if (upper != null && valor.compareTo(upper) > 0) {
            isCritica = true;
        }

        if (isCritica) {
            generarAlerta(paciente, "Critica", String.format("Lectura crítica de %s: %.2f %s", medicion.getIndicador().getNombre(), valor, medicion.getIndicador().getUnidad()));
        } else {
            evaluarDeterioroProgresivo(paciente, medicion);
        }
    }

    private void evaluarDeterioroProgresivo(Paciente paciente, Medicion medicionActual) {
        List<Medicion> mediciones = medicionRepository.findByPacienteIdAndIndicadorId(paciente.getId(), medicionActual.getIndicador().getId());
        mediciones.sort((m1, m2) -> m2.getFecha().compareTo(m1.getFecha())); // Descendente por fecha

        if (mediciones.size() >= 3) {
            BigDecimal v1 = mediciones.get(2).getValor(); // Más antigua de las 3
            BigDecimal v2 = mediciones.get(1).getValor();
            BigDecimal v3 = mediciones.get(0).getValor(); // Más reciente (la actual)

            boolean tendenciaAlza = v1.compareTo(v2) < 0 && v2.compareTo(v3) < 0;
            boolean tendenciaBaja = v1.compareTo(v2) > 0 && v2.compareTo(v3) > 0;

            if (tendenciaAlza || tendenciaBaja) {
                if (!alertaRepository.existsByPacienteIdAndTipoAndResueltaFalse(paciente.getId(), "Deterioro")) {
                    generarAlerta(paciente, "Deterioro", String.format("Tendencia preocupante en %s detectada en las últimas 3 mediciones.", medicionActual.getIndicador().getNombre()));
                }
            }
        }
    }

    @Scheduled(cron = "0 0 8 * * ?") // Todos los días a las 8 AM
    @Transactional
    public void verificarAbandono() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        LocalDateTime threshold = LocalDateTime.now().minusDays(30);

        for (Paciente p : pacientes) {
            if (p.getLastVisit() != null && p.getLastVisit().isBefore(threshold)) {
                if (p.getNextVisit() == null || p.getNextVisit().isBefore(LocalDateTime.now())) {
                    if (!alertaRepository.existsByPacienteIdAndTipoAndResueltaFalse(p.getId(), "Abandono")) {
                        generarAlerta(p, "Abandono", "Paciente sin controles recientes ni agendados (>30 días).");
                        whatsAppService.enviarMensaje(p.getPhone(), "Hola " + p.getNombre() + ", notamos que hace más de 30 días no registras controles. Por favor, acércate a tu CESFAM para agendar una cita.");
                    }
                }
            }
        }
    }

    @Scheduled(cron = "0 15 8 * * ?") // Todos los días a las 8:15 AM
    @Transactional
    public void verificarAdherenciaFarmacos() {
        List<Paciente> pacientes = pacienteRepository.findAll();
        LocalDate hoy = LocalDate.now();

        for (Paciente p : pacientes) {
            if (p.getFechaProximoRetiro() != null) {
                long diasRetraso = ChronoUnit.DAYS.between(p.getFechaProximoRetiro(), hoy);
                if (diasRetraso > 5) {
                    if (!alertaRepository.existsByPacienteIdAndTipoAndResueltaFalse(p.getId(), "Farmacia")) {
                        generarAlerta(p, "Farmacia", "Retraso de más de 5 días en el retiro de medicamentos.");
                        whatsAppService.enviarMensaje(p.getPhone(), "Hola " + p.getNombre() + ", tienes un retraso en el retiro de tus medicamentos mensuales. Por favor, acércate a la farmacia del CESFAM lo antes posible.");
                    }
                }
            }
        }
    }

    @Scheduled(cron = "0 30 8 * * ?") // Todos los días a las 8:30 AM
    @Transactional
    public void verificarAlertaEstacional() {
        Month mesActual = LocalDate.now().getMonth();
        boolean esEpocaFria = mesActual == Month.MAY || mesActual == Month.JUNE || mesActual == Month.JULY || mesActual == Month.AUGUST;

        if (!esEpocaFria) return;

        List<Paciente> pacientes = pacienteRepository.findAll();
        LocalDate threshold = LocalDate.now().minusDays(15);

        for (Paciente p : pacientes) {
            List<PacientePatologia> patologias = pacientePatologiaRepository.findByPacienteId(p.getId());
            boolean tieneRespiratoria = patologias.stream().anyMatch(pp -> {
                String nombre = pp.getPatologia().getNombre().toLowerCase();
                return nombre.contains("epoc") || nombre.contains("asma");
            });

            if (tieneRespiratoria) {
                boolean sinControlReciente = p.getLastVisit() == null || p.getLastVisit().toLocalDate().isBefore(threshold);
                if (sinControlReciente) {
                    if (!alertaRepository.existsByPacienteIdAndTipoAndResueltaFalse(p.getId(), "Estacional")) {
                        generarAlerta(p, "Estacional", "Paciente con riesgo respiratorio sin controles preventivos en los últimos 15 días (Campaña de Invierno).");
                        whatsAppService.enviarMensaje(p.getPhone(), "Hola " + p.getNombre() + ", estamos en campaña de invierno. Dado tu diagnóstico respiratorio, te invitamos a registrar tus niveles o visitar tu CESFAM preventivamente.");
                    }
                }
            }
        }
    }

    @Transactional
    public String ejecutarDemo(String phone) {
        StringBuilder reporte = new StringBuilder();

        try {
            // 1. Demo Abandono (Demo X)
            Paciente demoX = crearDemoPaciente("Demo X", phone);
            demoX.setLastVisit(LocalDateTime.now().minusDays(35));
            demoX.setNextVisit(null);
            pacienteRepository.save(demoX);
            generarAlerta(demoX, "Abandono", "Paciente sin controles recientes ni agendados (>30 días).");
            whatsAppService.enviarMensaje(phone, "Hola Demo X, notamos que hace más de 30 días no registras controles. Por favor, acércate a tu CESFAM.");
            reporte.append("Simulada alerta de Abandono (Demo X).\n");
            Thread.sleep(1000);

            // 2. Demo Farmacia (Demo Y)
            Paciente demoY = crearDemoPaciente("Demo Y", phone);
            demoY.setFechaProximoRetiro(LocalDate.now().minusDays(6));
            pacienteRepository.save(demoY);
            generarAlerta(demoY, "Farmacia", "Retraso de más de 5 días en el retiro de medicamentos.");
            whatsAppService.enviarMensaje(phone, "Hola Demo Y, tienes un retraso en el retiro de tus medicamentos mensuales.");
            reporte.append("Simulada alerta de Farmacia (Demo Y).\n");
            Thread.sleep(1000);

            // 3. Demo Estacional (Demo W)
            Paciente demoW = crearDemoPaciente("Demo W", phone);
            demoW.setLastVisit(LocalDateTime.now().minusDays(20));
            pacienteRepository.save(demoW);
            generarAlerta(demoW, "Estacional", "Paciente con riesgo respiratorio sin controles preventivos (Campaña Invierno).");
            whatsAppService.enviarMensaje(phone, "Hola Demo W, estamos en campaña de invierno. Dado tu diagnóstico (EPOC/Asma), te invitamos a registrar tus niveles.");
            reporte.append("Simulada alerta Estacional (Demo W).\n");
            Thread.sleep(1000);

            // 4. Demo Deterioro (Demo V)
            Paciente demoV = crearDemoPaciente("Demo V", phone);
            pacienteRepository.save(demoV);
            generarAlerta(demoV, "Deterioro", "Tendencia preocupante detectada en las últimas 3 mediciones.");
            whatsAppService.enviarMensaje(phone, "Hola Demo V, hemos detectado una tendencia preocupante en tus últimos controles. Te sugerimos agendar una evaluación.");
            reporte.append("Simulada alerta de Deterioro (Demo V).\n");
            Thread.sleep(1000);

            // 5. Demo Crítica (Demo U)
            Paciente demoU = crearDemoPaciente("Demo U", phone);
            pacienteRepository.save(demoU);
            generarAlerta(demoU, "Critica", "Lectura crítica de medición registrada.");
            whatsAppService.enviarMensaje(phone, "Hola Demo U, hemos recibido una medición con valor crítico. Un profesional de salud te contactará a la brevedad.");
            reporte.append("Simulada alerta Crítica (Demo U).\n");

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        return reporte.toString();
    }

    private Paciente crearDemoPaciente(String nombre, String phone) {
        Paciente p = new Paciente();
        p.setNombre(nombre);
        p.setPhone(phone);
        p.setActivo(true);
        p.setRut(nombre.replace(" ", "") + "-" + (int)(Math.random()*9));
        p.setAge(50);
        return p;
    }

    private void generarAlerta(Paciente paciente, String tipo, String descripcion) {
        Alerta alerta = Alerta.builder()
                .paciente(paciente)
                .tipo(tipo)
                .descripcion(descripcion)
                .fecha(LocalDateTime.now())
                .resuelta(false)
                .build();
        alertaRepository.save(alerta);
    }
}
