package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.entities.PacientePatologia;
import iye.grupo2.cronicotrak.repositories.PacientePatologiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PacientePatologiaService {
    private final PacientePatologiaRepository repository;

    public List<PacientePatologia> findAll() {
        return repository.findAll();
    }

    public Optional<PacientePatologia> findById(Long id) {
        return repository.findById(id);
    }

    @Transactional
    public PacientePatologia save(PacientePatologia entity) {
        return repository.save(entity);
    }

    @Transactional
    public PacientePatologia update(Long id, PacientePatologia entity) {
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

    public List<String> findPatologiasByPacienteId(Long pacienteId) {
        return repository.findByPacienteId(pacienteId).stream()
                .map(pp -> pp.getPatologia().getNombre())
                .collect(java.util.stream.Collectors.toList());
    }
}
