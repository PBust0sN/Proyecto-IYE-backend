package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.entities.Alerta;
import iye.grupo2.cronicotrak.repositories.AlertaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlertaService {
    private final AlertaRepository repository;

    public List<Alerta> findAll() {
        return repository.findAll();
    }

    public Optional<Alerta> findById(Long id) {
        return repository.findById(id);
    }

    public Alerta save(Alerta entity) {
        return repository.save(entity);
    }

    public Alerta update(Long id, Alerta entity) {
        if (repository.existsById(id)) {
            entity.setId(id);
            return repository.save(entity);
        }
        return null;
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public long countActiveAlerts() {
        return repository.countByResueltaFalse();
    }
}
