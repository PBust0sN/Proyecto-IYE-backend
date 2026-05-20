package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.entities.Paciente;
import iye.grupo2.cronicotrak.repositories.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PacienteService {
    private final PacienteRepository repository;

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

    public long countAll() {
        return repository.count();
    }
}
