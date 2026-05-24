package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.Medicion;
import iye.grupo2.cronicotrak.services.MedicionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing health measurements.
 */
@RestController
@RequestMapping("/api/v1/medicion")
@RequiredArgsConstructor
public class MedicionController {
    private final MedicionService service;

    /**
     * Retrieves all health measurements.
     *
     * @return a list of all health measurements
     */
    @GetMapping
    public List<Medicion> findAll() {
        return service.findAll();
    }

    /**
     * Retrieves a health measurement by its ID.
     *
     * @param id the ID of the health measurement to retrieve
     * @return the health measurement with the given ID, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Medicion> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Saves a new health measurement.
     *
     * @param entity the health measurement to save
     * @return the saved health measurement
     */
    @PostMapping
    public Medicion save(@RequestBody Medicion entity) {
        return service.save(entity);
    }

    /**
     * Updates an existing health measurement.
     *
     * @param id the ID of the health measurement to update
     * @param entity the updated health measurement data
     * @return the updated health measurement, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Medicion> update(@PathVariable Long id, @RequestBody Medicion entity) {
        Medicion updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /**
     * Deletes a health measurement by its ID.
     *
     * @param id the ID of the health measurement to delete
     * @return a response indicating the result of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
