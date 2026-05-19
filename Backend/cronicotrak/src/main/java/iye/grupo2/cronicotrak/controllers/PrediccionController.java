package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.Prediccion;
import iye.grupo2.cronicotrak.services.PrediccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/prediccion")
@RequiredArgsConstructor
public class PrediccionController {
    private final PrediccionService service;

    @GetMapping
    public List<Prediccion> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Prediccion> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Prediccion save(@RequestBody Prediccion entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Prediccion> update(@PathVariable Long id, @RequestBody Prediccion entity) {
        Prediccion updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
