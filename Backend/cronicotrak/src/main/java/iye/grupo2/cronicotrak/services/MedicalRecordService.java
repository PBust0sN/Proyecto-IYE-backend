package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.dto.IndicatorDTO;
import iye.grupo2.cronicotrak.dto.MedicalRecordDTO;
import iye.grupo2.cronicotrak.dto.PathologyRecordDTO;
import iye.grupo2.cronicotrak.entities.*;
import iye.grupo2.cronicotrak.repositories.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicalRecordService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MedicalRecordService.class);

    private final PacienteRepository pacienteRepository;
    private final PacientePatologiaRepository pacientePatologiaRepository;
    private final IndicadorRepository indicadorRepository;
    private final MedicionRepository medicionRepository;
    private final PacienteMedicamentoRepository pacienteMedicamentoRepository;
    private final ControlRepository controlRepository;

    private static final DateTimeFormatter DATE_FMT = DateTimeFormatter.ofPattern("d/M/yyyy");

    // -------------------------------------------------------
    // GET: construye el DTO completo de ficha médica
    // -------------------------------------------------------
    @Transactional(readOnly = true)
    public Optional<MedicalRecordDTO> getMedicalRecord(Long patientId) {
        return pacienteRepository.findById(patientId)
                .filter(p -> !Boolean.FALSE.equals(p.getActivo()))
                .map(paciente -> {

                    // Edad calculada desde fecha de nacimiento
                    Integer age = paciente.getFechaNacimiento() != null
                            ? Period.between(paciente.getFechaNacimiento(), LocalDate.now()).getYears()
                            : null;

                    // Patologías del paciente → PathologyRecordDTO
                    List<PacientePatologia> relaciones = pacientePatologiaRepository.findByPacienteId(patientId);
                    List<PathologyRecordDTO> condition = relaciones.stream()
                            .map(rel -> buildPathologyRecord(patientId, rel))
                            .collect(Collectors.toList());

                    // Medicamentos activos → "Nombre dosis (frecuencia)"
                    List<String> actualMeds = pacienteMedicamentoRepository.findByPacienteId(patientId)
                            .stream()
                            .map(pm -> formatMed(pm))
                            .collect(Collectors.toList());

                    // Controles
                    String lastVisit = controlRepository.findLastControlByPacienteId(patientId)
                            .map(c -> formatDate(c.getFechaReal()))
                            .orElse(null);

                    String nextVisit = controlRepository.findNextControlByPacienteId(patientId, LocalDate.now())
                            .map(c -> formatDate(c.getFechaProgramada()))
                            .orElse(null);

                    return MedicalRecordDTO.builder()
                            .idPatient(paciente.getId())
                            .name(paciente.getNombre())
                            .age(age)
                            .condition(condition)
                            .status(paciente.getEstado())
                            .lastVisit(lastVisit)
                            .nextVisit(nextVisit)
                            .room(paciente.getHabitacion())
                            .phone(paciente.getPhone())
                            .mail(paciente.getEmail())
                            .address(paciente.getDireccion())
                            .bloodType(paciente.getTipoSangre())
                            .emergencyName(paciente.getNombreEmergencia())
                            .emergencyPhone(paciente.getTelefonoEmergencia())
                            .alergies(paciente.getAlergias())
                            .actualMeds(actualMeds)
                            .build();
                });
    }

    // -------------------------------------------------------
    // PUT: actualiza la ficha médica del paciente
    // -------------------------------------------------------
    @Transactional
    public Optional<MedicalRecordDTO> updateMedicalRecord(Long patientId, MedicalRecordDTO dto) {
        return pacienteRepository.findById(patientId)
                .filter(p -> !Boolean.FALSE.equals(p.getActivo()))
                .map(paciente -> {
                    // Actualiza datos básicos del paciente
                    paciente.setNombre(dto.getName());
                    paciente.setPhone(dto.getPhone());
                    paciente.setEmail(dto.getMail());
                    paciente.setDireccion(dto.getAddress());
                    paciente.setTipoSangre(dto.getBloodType());
                    paciente.setNombreEmergencia(dto.getEmergencyName());
                    paciente.setTelefonoEmergencia(dto.getEmergencyPhone());
                    paciente.setAlergias(dto.getAlergies());
                    paciente.setEstado(dto.getStatus());
                    paciente.setHabitacion(dto.getRoom());
                    pacienteRepository.save(paciente);

                    // Actualiza notas y fecha_ultimo_control de cada patología
                    if (dto.getCondition() != null) {
                        dto.getCondition().forEach(pathDto -> {
                            pacientePatologiaRepository.findByPacienteId(patientId).stream()
                                    .filter(rel -> rel.getPatologia() != null && rel.getPatologia().getId().equals(pathDto.getId()))
                                    .findFirst()
                                    .ifPresent(rel -> {
                                        rel.setNotas(pathDto.getNotes());
                                        if (pathDto.getLastUpdate() != null) {
                                            try {
                                                rel.setFechaUltimoControl(LocalDate.parse(pathDto.getLastUpdate(), DATE_FMT));
                                            } catch (DateTimeParseException e) {
                                                LOGGER.warn("Invalid lastUpdate date '{}' for patient {}", pathDto.getLastUpdate(), patientId);
                                            }
                                        }
                                        pacientePatologiaRepository.save(rel);

                                        // Actualiza la última medición de cada indicador
                                        if (pathDto.getIndicators() != null) {
                                            pathDto.getIndicators().forEach(indDto -> {
                                                medicionRepository.findLatestByPacienteIdAndIndicadorId(patientId, indDto.getId())
                                                        .ifPresent(m -> {
                                                            m.setValor(indDto.getQuantity());
                                                            m.setFecha(LocalDate.now());
                                                            medicionRepository.save(m);
                                                        });
                                            });
                                        }
                                    });
                        });
                    }

                    return getMedicalRecord(patientId).orElse(null);
                });
    }

    // -------------------------------------------------------
    // DELETE (soft): oculta la ficha del paciente
    // -------------------------------------------------------
    @Transactional
    public boolean hideMedicalRecord(Long patientId) {
        return pacienteRepository.findById(patientId).map(paciente -> {
            paciente.setActivo(false);
            pacienteRepository.save(paciente);
            return true;
        }).orElse(false);
    }

    // -------------------------------------------------------
    // Helpers
    // -------------------------------------------------------
    private PathologyRecordDTO buildPathologyRecord(Long patientId, PacientePatologia rel) {
        Patologia patologia = rel.getPatologia();

        List<IndicatorDTO> indicators = indicadorRepository.findByPatologiaId(patologia.getId())
                .stream()
                .map(ind -> buildIndicatorDTO(patientId, ind))
                .collect(Collectors.toList());

        return PathologyRecordDTO.builder()
                .id(patologia.getId())
                .name(patologia.getNombre())
                .description(patologia.getDescripcion())
                .indicators(indicators)
                .lastUpdate(formatDate(rel.getFechaUltimoControl()))
                .notes(rel.getNotas())
                .build();
    }

    private IndicatorDTO buildIndicatorDTO(Long patientId, Indicador indicador) {
        Optional<Medicion> latestMedicion = medicionRepository
                .findLatestByPacienteIdAndIndicadorId(patientId, indicador.getId());

        BigDecimal quantity = latestMedicion.map(Medicion::getValor).orElse(null);
        String state = calculateState(quantity, indicador.getLower(), indicador.getUpper());

        return IndicatorDTO.builder()
                .id(indicador.getId())
                .name(indicador.getNombre())
                .quantity(quantity)
                .unit(indicador.getUnidad())
                .lower(indicador.getLower())
                .upper(indicador.getUpper())
                .state(state)
                .build();
    }

    /**
     * Calcula el estado del indicador según los rangos:
     *  - Controlado: dentro del rango [lower, upper]
     *  - En Observación: ligeramente fuera del rango (≤10%)
     *  - Crítico: muy fuera del rango (>10%)
     */
    private String calculateState(BigDecimal quantity, BigDecimal lower, BigDecimal upper) {
        if (quantity == null) return null;

        boolean belowLower = lower != null && quantity.compareTo(lower) < 0;
        boolean aboveUpper = upper != null && quantity.compareTo(upper) > 0;

        if (!belowLower && !aboveUpper) return "Controlado";

        // Calcula desviación porcentual para diferenciar En Observación vs Crítico
        if (belowLower && lower != null && lower.compareTo(BigDecimal.ZERO) != 0) {
            double pct = lower.subtract(quantity).abs().doubleValue() / lower.abs().doubleValue();
            return pct > 0.10 ? "Crítico" : "En Observación";
        }
        if (aboveUpper && upper != null && upper.compareTo(BigDecimal.ZERO) != 0) {
            double pct = quantity.subtract(upper).abs().doubleValue() / upper.abs().doubleValue();
            return pct > 0.10 ? "Crítico" : "En Observación";
        }

        return "En Observación";
    }

    private String formatDate(LocalDate date) {
        return date != null ? date.format(DATE_FMT) : null;
    }

    private String formatMed(PacienteMedicamento pm) {
        String nombre = pm.getMedicamento() != null ? pm.getMedicamento().getNombre() : "";
        String dosis = pm.getDosis() != null ? " " + pm.getDosis() : "";
        String frecuencia = pm.getFrecuencia() != null ? " (" + pm.getFrecuencia() + ")" : "";
        return nombre + dosis + frecuencia;
    }
}
