package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.entities.Indicador;
import iye.grupo2.cronicotrak.repositories.IndicadorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IndicadorService {
    private final IndicadorRepository repository;

    public List<Indicador> findAll() {
        return repository.findAll();
    }

    public Optional<Indicador> findById(Long id) {
        return repository.findById(id);
    }

    public Indicador save(Indicador entity) {
        return repository.save(entity);
    }

    public Indicador update(Long id, Indicador entity) {
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
