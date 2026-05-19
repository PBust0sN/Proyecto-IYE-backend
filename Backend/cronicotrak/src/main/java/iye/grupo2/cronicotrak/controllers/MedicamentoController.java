package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.Medicamento;
import iye.grupo2.cronicotrak.services.MedicamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/medicamento")
@RequiredArgsConstructor
public class MedicamentoController {
    private final MedicamentoService service;

    @GetMapping
    public List<Medicamento> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Medicamento save(@RequestBody Medicamento entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicamento> update(@PathVariable Long id, @RequestBody Medicamento entity) {
        Medicamento updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
