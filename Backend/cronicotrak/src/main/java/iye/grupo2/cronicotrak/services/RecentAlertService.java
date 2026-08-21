package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.dtos.RecentAlertDto;
import iye.grupo2.cronicotrak.entities.Alerta;
import iye.grupo2.cronicotrak.entities.PacientePatologia;
import iye.grupo2.cronicotrak.repositories.AlertaRepository;
import iye.grupo2.cronicotrak.repositories.PacientePatologiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecentAlertService {
    private final AlertaRepository alertaRepository;
    private final PacientePatologiaRepository pacientePatologiaRepository;

    public List<RecentAlertDto> getTodayAlerts() {
        LocalDateTime startOfDay = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endOfDay = startOfDay.plusDays(1);
        List<Alerta> alertas = alertaRepository.findTodayAlerts(startOfDay, endOfDay);
        
        return alertas.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private RecentAlertDto convertToDto(Alerta alerta) {
        String patientName = alerta.getPaciente().getNombre();
        
        // Obtener la primera patología del paciente
        List<PacientePatologia> patologias = pacientePatologiaRepository.findByPacienteId(alerta.getPaciente().getId());
        String condition = patologias.isEmpty() ? "Sin diagnóstico" : patologias.get(0).getPatologia().getNombre();
        
        // Determinar prioridad basada en el tipo de alerta
        String priority = determinePriority(alerta.getTipo());
        
        // Calcular tiempo transcurrido
        String timeAgo = calculateTimeAgo(alerta.getFecha());
        
        return RecentAlertDto.builder()
                .id(alerta.getId())
                .patientName(patientName)
                .type(alerta.getTipo())
                .description(alerta.getDescripcion())
                .time(timeAgo)
                .status(priority)
                .build();
    }

    private String determinePriority(String alertType) {
        if (alertType == null) {
            return "medium";
        }
        
        switch (alertType.toLowerCase()) {
            case "critica":
            case "critical":
            case "emergencia":
            case "emergency":
                return "high";
            case "deterioro":
            case "abandono":
            case "farmacia":
            case "estacional":
            case "advertencia":
            case "warning":
                return "medium";
            case "info":
            case "informacion":
                return "low";
            default:
                return "medium";
        }
    }

    private String calculateTimeAgo(LocalDateTime fecha) {
        if (fecha == null) {
            return "Desconocido";
        }
        
        LocalDateTime now = LocalDateTime.now();
        long minutes = ChronoUnit.MINUTES.between(fecha, now);
        long hours = ChronoUnit.HOURS.between(fecha, now);
        long days = ChronoUnit.DAYS.between(fecha, now);
        
        if (minutes < 1) {
            return "Hace unos segundos";
        } else if (minutes < 60) {
            return "Hace " + minutes + " min";
        } else if (hours < 24) {
            return "Hace " + hours + " h";
        } else {
            return "Hace " + days + " días";
        }
    }
}
