package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.entities.Medicamento;
import iye.grupo2.cronicotrak.repositories.MedicamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicamentoService {
    private final MedicamentoRepository repository;

    public List<Medicamento> findAll() {
        return repository.findAll();
    }

    public Optional<Medicamento> findById(Long id) {
        return repository.findById(id);
    }

    public Medicamento save(Medicamento entity) {
        return repository.save(entity);
    }

    public Medicamento update(Long id, Medicamento entity) {
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
