package iye.grupo2.cronicotrak.services;

import iye.grupo2.cronicotrak.entities.Control;
import iye.grupo2.cronicotrak.repositories.ControlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ControlService {
    private final ControlRepository repository;

    public List<Control> findAll() {
        return repository.findAll();
    }

    public Optional<Control> findById(Long id) {
        return repository.findById(id);
    }

    public Control save(Control entity) {
        return repository.save(entity);
    }

    public Control update(Long id, Control entity) {
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
