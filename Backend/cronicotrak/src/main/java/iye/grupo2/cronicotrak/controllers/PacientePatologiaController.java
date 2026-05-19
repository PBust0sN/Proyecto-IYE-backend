package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.PacientePatologia;
import iye.grupo2.cronicotrak.services.PacientePatologiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/paciente-patologia")
@RequiredArgsConstructor
public class PacientePatologiaController {
    private final PacientePatologiaService service;

    @GetMapping
    public List<PacientePatologia> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacientePatologia> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PacientePatologia save(@RequestBody PacientePatologia entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacientePatologia> update(@PathVariable Long id, @RequestBody PacientePatologia entity) {
        PacientePatologia updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
