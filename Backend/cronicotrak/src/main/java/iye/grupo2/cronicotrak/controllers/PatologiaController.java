package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.Patologia;
import iye.grupo2.cronicotrak.services.PatologiaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing pathology operations.
 */
@RestController
@RequestMapping("/api/v1/patologia")
@RequiredArgsConstructor
public class PatologiaController {
    private final PatologiaService service;

    /**
     * Retrieves all pathologies.
     *
     * @return a list of all pathologies
     */
    @GetMapping
    public List<Patologia> findAll() {
        return service.findAll();
    }

    /**
     * Retrieves a pathology by its ID.
     *
     * @param id the ID of the pathology to retrieve
     * @return the pathology with the given ID, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Patologia> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Saves a new pathology.
     *
     * @param entity the pathology to save
     * @return the saved pathology
     */
    @PostMapping
    public Patologia save(@RequestBody Patologia entity) {
        return service.save(entity);
    }

    /**
     * Updates an existing pathology.
     *
     * @param id the ID of the pathology to update
     * @param entity the updated pathology data
     * @return the updated pathology, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Patologia> update(@PathVariable Long id, @RequestBody Patologia entity) {
        Patologia updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /**
     * Deletes a pathology by its ID.
     *
     * @param id the ID of the pathology to delete
     * @return a response indicating the result of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
