package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.PacienteMedicamento;
import iye.grupo2.cronicotrak.services.PacienteMedicamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing patient-medication relationships.
 */
@RestController
@RequestMapping("/api/v1/paciente-medicamento")
@RequiredArgsConstructor
public class PacienteMedicamentoController {
    private final PacienteMedicamentoService service;

    /**
     * Retrieves all patient-medication relationships.
     *
     * @return a list of all patient-medication relationships
     */
    @GetMapping
    public List<PacienteMedicamento> findAll() {
        return service.findAll();
    }

    /**
     * Retrieves a patient-medication relationship by its ID.
     *
     * @param id the ID of the patient-medication relationship to retrieve
     * @return the relationship with the given ID, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<PacienteMedicamento> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Saves a new patient-medication relationship.
     *
     * @param entity the relationship to save
     * @return the saved relationship
     */
    @PostMapping
    public PacienteMedicamento save(@RequestBody PacienteMedicamento entity) {
        return service.save(entity);
    }

    /**
     * Updates an existing patient-medication relationship.
     *
     * @param id the ID of the relationship to update
     * @param entity the updated relationship data
     * @return the updated relationship, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<PacienteMedicamento> update(@PathVariable Long id, @RequestBody PacienteMedicamento entity) {
        PacienteMedicamento updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /**
     * Deletes a patient-medication relationship by its ID.
     *
     * @param id the ID of the relationship to delete
     * @return a response indicating the result of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
