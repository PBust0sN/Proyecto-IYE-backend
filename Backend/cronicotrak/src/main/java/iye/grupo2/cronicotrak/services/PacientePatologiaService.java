package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.entities.PacientePatologia;
import iye.grupo2.cronicotrak.repositories.PacientePatologiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PacientePatologiaService {
    private final PacientePatologiaRepository repository;

    public List<PacientePatologia> findAll() {
        return repository.findAll();
    }

    public Optional<PacientePatologia> findById(Long id) {
        return repository.findById(id);
    }

    public PacientePatologia save(PacientePatologia entity) {
        return repository.save(entity);
    }

    public PacientePatologia update(Long id, PacientePatologia entity) {
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
