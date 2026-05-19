package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.entities.Establecimiento;
import iye.grupo2.cronicotrak.repositories.EstablecimientoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EstablecimientoService {
    private final EstablecimientoRepository repository;

    public List<Establecimiento> findAll() {
        return repository.findAll();
    }

    public Optional<Establecimiento> findById(Long id) {
        return repository.findById(id);
    }

    public Establecimiento save(Establecimiento entity) {
        return repository.save(entity);
    }

    public Establecimiento update(Long id, Establecimiento entity) {
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
