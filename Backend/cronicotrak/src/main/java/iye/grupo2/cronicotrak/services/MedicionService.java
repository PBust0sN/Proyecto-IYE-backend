package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.entities.Medicion;
import iye.grupo2.cronicotrak.repositories.MedicionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicionService {
    private final MedicionRepository repository;

    public List<Medicion> findAll() {
        return repository.findAll();
    }

    public Optional<Medicion> findById(Long id) {
        return repository.findById(id);
    }

    public Medicion save(Medicion entity) {
        return repository.save(entity);
    }

    public Medicion update(Long id, Medicion entity) {
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
