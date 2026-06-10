package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.DTO.FutureAppointmentDTO;
import iye.grupo2.cronicotrak.entities.Control;
import iye.grupo2.cronicotrak.repositories.ControlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ControlService {
    private final ControlRepository repository;

    public List<FutureAppointmentDTO> findFutureAppointmentsDTO() {
        return repository.findByFechaProgramadaGreaterThanEqualOrderByFechaProgramadaAsc(LocalDate.now()).stream()
                .map(this::mapToFutureAppointmentDTO)
                .collect(Collectors.toList());
    }

    private FutureAppointmentDTO mapToFutureAppointmentDTO(Control control) {
        return FutureAppointmentDTO.builder()
                .id(control.getId().intValue())
                .patient(control.getPaciente() != null ? control.getPaciente().getNombre() : "Unknown")
                .date(control.getFechaProgramada() != null ? control.getFechaProgramada().toString() : "N/A")
                .time("09:00") // Valor por defecto ya que no hay campo de hora en la entidad
                .type(control.getTipo() != null ? control.getTipo() : "General")
                .doctor(control.getDoctor() != null ? control.getDoctor() : "TBD")
                .room(control.getPaciente() != null ? control.getPaciente().getHabitacion() : "N/A")
                .priority(control.getPrioridad() != null ? control.getPrioridad() : "medium")
                .build();
    }

    public List<Control> findAll() {
        return repository.findAll();
    }

    public Optional<Control> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public Control save(Control entity) {
        return repository.save(entity);
    }

    @Transactional
    public Control update(Long id, Control entity) {
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

    public long countTodayFollowups() {
        return repository.countByFechaReal(LocalDate.now());
    }

    public double getControlRate() {
        long totalControls = repository.count();
        if (totalControls == 0) {
            return 0.0;
        }
        long controlsCompleted = repository.countByAsistioTrue();
        return (double) controlsCompleted / totalControls;
    }
}
