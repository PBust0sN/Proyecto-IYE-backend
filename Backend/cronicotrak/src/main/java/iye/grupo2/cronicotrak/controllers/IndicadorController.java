package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.Indicador;
import iye.grupo2.cronicotrak.services.IndicadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/indicador")
@RequiredArgsConstructor
public class IndicadorController {
    private final IndicadorService service;

    @GetMapping
    public List<Indicador> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Indicador> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Indicador save(@RequestBody Indicador entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Indicador> update(@PathVariable Long id, @RequestBody Indicador entity) {
        Indicador updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
