package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.DTO.GETPatient;
import iye.grupo2.cronicotrak.DTO.PatientDetailDTO;
import iye.grupo2.cronicotrak.DTO.PatientQuantityDTO;
import iye.grupo2.cronicotrak.entities.Paciente;
import iye.grupo2.cronicotrak.repositories.ControlRepository;
import iye.grupo2.cronicotrak.repositories.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PacienteService {
    private final PacienteRepository repository;
    private final ControlRepository controlRepository;
    private final PacientePatologiaService pacientePatologiaService;
    private final AlertaService alertaService;
    private final PrediccionService prediccionService;
    private final MedicionService medicionService;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<PatientQuantityDTO> getPatientsQuantities() {
        return repository.countPatientsByStatus();
    }

    public PatientDetailDTO findPatientDetailById(Long id) {
        return repository.findById(id)
                .map(paciente -> {
                    Integer age = paciente.getFechaNacimiento() != null
                            ? Period.between(paciente.getFechaNacimiento(), LocalDate.now()).getYears()
                            : null;
                    return PatientDetailDTO.builder()
                            .id(paciente.getId())
                            .name(paciente.getNombre())
                            .age(age)
                            .condition(pacientePatologiaService.findPatologiasByPacienteId(id))
                            .alertLevel(alertaService.findAlertLevelsByPacienteId(id))
                            .pattern(prediccionService.findPatternsByPacienteId(id))
                            .lastMeasurement(medicionService.findLatestMeasurementByPacienteId(id))
                            .room(paciente.getHabitacion())
                            .phone(paciente.getPhone())
                            .build();
                })
                .orElse(null);
    }

    public List<GETPatient> findAllPatientsDTO() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private GETPatient mapToDTO(Paciente paciente) {
        List<String> conditions = pacientePatologiaService.findPatologiasByPacienteId(paciente.getId());

        Integer age = paciente.getFechaNacimiento() != null
                ? Period.between(paciente.getFechaNacimiento(), LocalDate.now()).getYears()
                : null;

        String lastVisit = controlRepository.findLastControlByPacienteId(paciente.getId())
                .filter(c -> c.getFechaReal() != null)
                .map(c -> c.getFechaReal().format(DATE_FORMATTER))
                .orElse(null);

        String nextVisit = controlRepository.findNextControlByPacienteId(paciente.getId(), LocalDate.now())
                .filter(c -> c.getFechaProgramada() != null)
                .map(c -> c.getFechaProgramada().format(DATE_FORMATTER))
                .orElse(null);

        return GETPatient.builder()
                .id(paciente.getId())
                .name(paciente.getNombre())
                .age(age)
                .condition(conditions)
                .status(paciente.getEstado())
                .lastVisit(lastVisit)
                .nextVisit(nextVisit)
                .room(paciente.getHabitacion())
                .phone(paciente.getPhone())
                .build();
    }

    public List<Paciente> findAll() {
        return repository.findAll();
    }

    public Optional<Paciente> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Paciente save(Paciente entity) {
        return repository.save(entity);
    }

    @Transactional
    public Paciente update(Long id, Paciente entity) {
        if (repository.existsById(id)) {
            entity.setId(id);
            return repository.save(entity);
        }
        return null;
    }

    @Transactional
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public long countAll() {
        return repository.count();
    }
}
