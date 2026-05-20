package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.DTO.GETPatient;
import iye.grupo2.cronicotrak.entities.Paciente;
import iye.grupo2.cronicotrak.repositories.PacientePatologiaRepository;
import iye.grupo2.cronicotrak.repositories.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PacienteService {
    private final PacienteRepository repository;
    private final PacientePatologiaRepository pacientePatologiaRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public List<GETPatient> findAllPatientsDTO() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private GETPatient mapToDTO(Paciente paciente) {
        List<String> conditions = pacientePatologiaRepository.findByPacienteId(paciente.getId()).stream()
                .map(pp -> pp.getPatologia().getNombre())
                .collect(Collectors.toList());

        return GETPatient.builder()
                .id(paciente.getId())
                .name(paciente.getNombre())
                .age(paciente.getAge())
                .condition(conditions)
                .status(paciente.getStatus())
                .lastVisit(paciente.getLastVisit() != null ? paciente.getLastVisit().format(DATE_FORMATTER) : null)
                .nextVisit(paciente.getNextVisit() != null ? paciente.getNextVisit().format(DATE_FORMATTER) : null)
                .room(paciente.getRoom())
                .phone(paciente.getPhone())
                .build();
    }

    public List<Paciente> findAll() {
        return repository.findAll();
    }

    public Optional<Paciente> findById(Long id) {
        return repository.findById(id);
    }

    public Paciente save(Paciente entity) {
        return repository.save(entity);
    }

    public Paciente update(Long id, Paciente entity) {
        if (repository.existsById(id)) {
            entity.setId(id);
            return repository.save(entity);
        }
        return null;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
