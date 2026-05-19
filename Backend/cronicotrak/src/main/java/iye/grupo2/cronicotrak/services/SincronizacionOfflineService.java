package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.entities.SincronizacionOffline;
import iye.grupo2.cronicotrak.repositories.SincronizacionOfflineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SincronizacionOfflineService {
    private final SincronizacionOfflineRepository repository;

    public List<SincronizacionOffline> findAll() {
        return repository.findAll();
    }

    public Optional<SincronizacionOffline> findById(Long id) {
        return repository.findById(id);
    }

    public SincronizacionOffline save(SincronizacionOffline entity) {
        return repository.save(entity);
    }

    public SincronizacionOffline update(Long id, SincronizacionOffline entity) {
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
