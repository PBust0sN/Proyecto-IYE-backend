package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.entities.IntegracionLog;
import iye.grupo2.cronicotrak.repositories.IntegracionLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IntegracionLogService {
    private final IntegracionLogRepository repository;

    public List<IntegracionLog> findAll() {
        return repository.findAll();
    }

    public Optional<IntegracionLog> findById(Long id) {
        return repository.findById(id);
    }

    public IntegracionLog save(IntegracionLog entity) {
        return repository.save(entity);
    }

    public IntegracionLog update(Long id, IntegracionLog entity) {
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
