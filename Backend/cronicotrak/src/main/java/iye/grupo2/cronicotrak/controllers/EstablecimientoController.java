package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.Establecimiento;
import iye.grupo2.cronicotrak.services.EstablecimientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing establishments.
 */
@RestController
@RequestMapping("/api/v1/establecimiento")
@RequiredArgsConstructor
public class EstablecimientoController {
    private final EstablecimientoService service;

    /**
     * Retrieves all establishments.
     *
     * @return a list of all establishments
     */
    @GetMapping
    public List<Establecimiento> findAll() {
        return service.findAll();
    }

    /**
     * Retrieves an establishment by its ID.
     *
     * @param id the ID of the establishment to retrieve
     * @return the establishment with the given ID, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Establecimiento> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Saves a new establishment.
     *
     * @param entity the establishment to save
     * @return the saved establishment
     */
    @PostMapping
    public Establecimiento save(@RequestBody Establecimiento entity) {
        return service.save(entity);
    }

    /**
     * Updates an existing establishment.
     *
     * @param id the ID of the establishment to update
     * @param entity the updated establishment data
     * @return the updated establishment, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Establecimiento> update(@PathVariable Long id, @RequestBody Establecimiento entity) {
        Establecimiento updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /**
     * Deletes an establishment by its ID.
     *
     * @param id the ID of the establishment to delete
     * @return a response indicating the result of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
