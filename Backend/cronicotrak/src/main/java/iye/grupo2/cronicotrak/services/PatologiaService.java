package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.entities.Patologia;
import iye.grupo2.cronicotrak.repositories.PatologiaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PatologiaService {
    private final PatologiaRepository repository;

    public List<Patologia> findAll() {
        return repository.findAll();
    }

    public Optional<Patologia> findById(Long id) {
        return repository.findById(id);
    }

    public Patologia save(Patologia entity) {
        return repository.save(entity);
    }

    public Patologia update(Long id, Patologia entity) {
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
