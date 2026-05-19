package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.Establecimiento;
import iye.grupo2.cronicotrak.services.EstablecimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/establecimiento")
@RequiredArgsConstructor
public class EstablecimientoController {
    private final EstablecimientoService service;

    @GetMapping
    public List<Establecimiento> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Establecimiento> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Establecimiento save(@RequestBody Establecimiento entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Establecimiento> update(@PathVariable Long id, @RequestBody Establecimiento entity) {
        Establecimiento updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
