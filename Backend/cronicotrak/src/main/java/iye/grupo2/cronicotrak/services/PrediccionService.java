package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.entities.Prediccion;
import iye.grupo2.cronicotrak.repositories.PrediccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PrediccionService {
    private final PrediccionRepository repository;

    public List<Prediccion> findAll() {
        return repository.findAll();
    }

    public Optional<Prediccion> findById(Long id) {
        return repository.findById(id);
    }

    public Prediccion save(Prediccion entity) {
        return repository.save(entity);
    }

    public Prediccion update(Long id, Prediccion entity) {
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
