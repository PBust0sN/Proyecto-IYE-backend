package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.entities.Recordatorio;
import iye.grupo2.cronicotrak.repositories.RecordatorioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecordatorioService {
    private final RecordatorioRepository repository;

    public List<Recordatorio> findAll() {
        return repository.findAll();
    }

    public Optional<Recordatorio> findById(Long id) {
        return repository.findById(id);
    }

    public Recordatorio save(Recordatorio entity) {
        return repository.save(entity);
    }

    public Recordatorio update(Long id, Recordatorio entity) {
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
