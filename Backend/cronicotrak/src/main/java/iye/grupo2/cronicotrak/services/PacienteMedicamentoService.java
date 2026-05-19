package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.entities.PacienteMedicamento;
import iye.grupo2.cronicotrak.repositories.PacienteMedicamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PacienteMedicamentoService {
    private final PacienteMedicamentoRepository repository;

    public List<PacienteMedicamento> findAll() {
        return repository.findAll();
    }

    public Optional<PacienteMedicamento> findById(Long id) {
        return repository.findById(id);
    }

    public PacienteMedicamento save(PacienteMedicamento entity) {
        return repository.save(entity);
    }

    public PacienteMedicamento update(Long id, PacienteMedicamento entity) {
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
