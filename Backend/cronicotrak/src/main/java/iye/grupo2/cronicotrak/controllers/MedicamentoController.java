package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.Medicamento;
import iye.grupo2.cronicotrak.services.MedicamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing medications.
 */
@RestController
@RequestMapping("/api/v1/medicamento")
@RequiredArgsConstructor
public class MedicamentoController {
    private final MedicamentoService service;

    /**
     * Retrieves all medications.
     *
     * @return a list of all medications
     */
    @GetMapping
    public List<Medicamento> findAll() {
        return service.findAll();
    }

    /**
     * Retrieves a medication by its ID.
     *
     * @param id the ID of the medication to retrieve
     * @return the medication with the given ID, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Saves a new medication.
     *
     * @param entity the medication to save
     * @return the saved medication
     */
    @PostMapping
    public Medicamento save(@RequestBody Medicamento entity) {
        return service.save(entity);
    }

    /**
     * Updates an existing medication.
     *
     * @param id the ID of the medication to update
     * @param entity the updated medication data
     * @return the updated medication, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Medicamento> update(@PathVariable Long id, @RequestBody Medicamento entity) {
        Medicamento updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /**
     * Deletes a medication by its ID.
     *
     * @param id the ID of the medication to delete
     * @return a response indicating the result of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
