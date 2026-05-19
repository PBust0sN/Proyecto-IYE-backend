package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.Patologia;
import iye.grupo2.cronicotrak.services.PatologiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patologia")
@RequiredArgsConstructor
public class PatologiaController {
    private final PatologiaService service;

    @GetMapping
    public List<Patologia> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patologia> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Patologia save(@RequestBody Patologia entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patologia> update(@PathVariable Long id, @RequestBody Patologia entity) {
        Patologia updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
