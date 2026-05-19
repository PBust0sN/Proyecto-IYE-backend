package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.IntegracionLog;
import iye.grupo2.cronicotrak.services.IntegracionLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/integracion-log")
@RequiredArgsConstructor
public class IntegracionLogController {
    private final IntegracionLogService service;

    @GetMapping
    public List<IntegracionLog> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<IntegracionLog> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public IntegracionLog save(@RequestBody IntegracionLog entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<IntegracionLog> update(@PathVariable Long id, @RequestBody IntegracionLog entity) {
        IntegracionLog updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
