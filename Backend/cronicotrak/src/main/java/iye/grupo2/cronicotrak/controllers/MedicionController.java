package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.Medicion;
import iye.grupo2.cronicotrak.services.MedicionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medicion")
@RequiredArgsConstructor
public class MedicionController {
    private final MedicionService service;

    @GetMapping
    public List<Medicion> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicion> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Medicion save(@RequestBody Medicion entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicion> update(@PathVariable Long id, @RequestBody Medicion entity) {
        Medicion updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
