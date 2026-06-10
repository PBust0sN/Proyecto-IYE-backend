package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.PacientePatologia;
import iye.grupo2.cronicotrak.services.PacientePatologiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing patient-pathology relationships.
 */
@RestController
@RequestMapping("/api/v1/paciente-patologia")
@RequiredArgsConstructor
public class PacientePatologiaController {
    private final PacientePatologiaService service;

    /**
     * Retrieves all patient-pathology relationships.
     *
     * @return a list of all patient-pathology relationships
     */
    @GetMapping
    public List<PacientePatologia> findAll() {
        return service.findAll();
    }

    /**
     * Retrieves a patient-pathology relationship by its ID.
     *
     * @param id the ID of the patient-pathology relationship to retrieve
     * @return the relationship with the given ID, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<PacientePatologia> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Saves a new patient-pathology relationship.
     *
     * @param entity the relationship to save
     * @return the saved relationship
     */
    @PostMapping
    public PacientePatologia save(@RequestBody PacientePatologia entity) {
        return service.save(entity);
    }

    /**
     * Updates an existing patient-pathology relationship.
     *
     * @param id the ID of the relationship to update
     * @param entity the updated relationship data
     * @return the updated relationship, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<PacientePatologia> update(@PathVariable Long id, @RequestBody PacientePatologia entity) {
        PacientePatologia updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /**
     * Deletes a patient-pathology relationship by its ID.
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
