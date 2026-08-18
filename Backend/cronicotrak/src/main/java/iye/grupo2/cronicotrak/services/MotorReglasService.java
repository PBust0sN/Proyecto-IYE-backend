package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.entities.Alerta;
import iye.grupo2.cronicotrak.entities.Medicion;
import iye.grupo2.cronicotrak.entities.Paciente;
import iye.grupo2.cronicotrak.repositories.AlertaRepository;
import iye.grupo2.cronicotrak.repositories.ControlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class MotorReglasService {

    private final ControlRepository controlRepository;
    private final AlertaRepository alertaRepository;
    private final WhatsAppService whatsAppService;

    public void evaluarReglasPorMedicion(Medicion medicion) {
        if (medicion.getIndicador() == null || medicion.getPaciente() == null) {
            return;
        }

        Paciente paciente = medicion.getPaciente();
        BigDecimal valor = medicion.getValor();
        BigDecimal lower = medicion.getIndicador().getLower();
        BigDecimal upper = medicion.getIndicador().getUpper();

        boolean isAlterado = false;
        if (lower != null && valor.compareTo(lower) < 0) {
            isAlterado = true;
        }
        if (upper != null && valor.compareTo(upper) > 0) {
            isAlterado = true;
        }

        if (isAlterado) {
            boolean tieneInasistencia = controlRepository.existsByPacienteIdAndAsistioFalse(paciente.getId());

            if (tieneInasistencia) {
                // Generar alerta predictiva
                String descripcion = String.format(
                        "Alerta Preventiva Generada: Paciente %s presenta inasistencia a controles y lectura alterada de %s (%.2f %s).",
                        paciente.getNombre(),
                        medicion.getIndicador().getNombre(),
                        valor,
                        medicion.getIndicador().getUnidad()
                );

                Alerta alerta = Alerta.builder()
                        .paciente(paciente)
                        .tipo("Preventiva")
                        .descripcion(descripcion)
                        .fecha(LocalDateTime.now())
                        .resuelta(false)
                        .build();

                alertaRepository.save(alerta);

                // Enviar WhatsApp al paciente
                if (paciente.getPhone() != null && !paciente.getPhone().isEmpty()) {
                    String mensajeWhatsApp = String.format(
                            "Hola %s. Hemos detectado un registro alterado de %s y notamos que no asististe a tu último control. Por favor, comunícate con tu posta rural a la brevedad.",
                            paciente.getNombre(), medicion.getIndicador().getNombre());
                    whatsAppService.enviarMensaje(paciente.getPhone(), mensajeWhatsApp);
                }
            }
        }
    }
}
