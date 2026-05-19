package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.SincronizacionOffline;
import iye.grupo2.cronicotrak.services.SincronizacionOfflineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/sincronizacion-offline")
@RequiredArgsConstructor
public class SincronizacionOfflineController {
    private final SincronizacionOfflineService service;

    @GetMapping
    public List<SincronizacionOffline> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<SincronizacionOffline> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public SincronizacionOffline save(@RequestBody SincronizacionOffline entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SincronizacionOffline> update(@PathVariable Long id, @RequestBody SincronizacionOffline entity) {
        SincronizacionOffline updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
