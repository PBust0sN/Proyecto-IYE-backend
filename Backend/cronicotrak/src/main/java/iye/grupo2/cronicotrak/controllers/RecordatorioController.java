package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.Recordatorio;
import iye.grupo2.cronicotrak.services.RecordatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recordatorio")
@RequiredArgsConstructor
public class RecordatorioController {
    private final RecordatorioService service;

    @GetMapping
    public List<Recordatorio> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recordatorio> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Recordatorio save(@RequestBody Recordatorio entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recordatorio> update(@PathVariable Long id, @RequestBody Recordatorio entity) {
        Recordatorio updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
