package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.PacienteMedicamento;
import iye.grupo2.cronicotrak.services.PacienteMedicamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/paciente-medicamento")
@RequiredArgsConstructor
public class PacienteMedicamentoController {
    private final PacienteMedicamentoService service;

    @GetMapping
    public List<PacienteMedicamento> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteMedicamento> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public PacienteMedicamento save(@RequestBody PacienteMedicamento entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PacienteMedicamento> update(@PathVariable Long id, @RequestBody PacienteMedicamento entity) {
        PacienteMedicamento updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
