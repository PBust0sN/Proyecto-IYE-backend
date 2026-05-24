package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.Prediccion;
import iye.grupo2.cronicotrak.services.PrediccionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing health predictions.
 */
@RestController
@RequestMapping("/api/v1/prediccion")
@RequiredArgsConstructor
public class PrediccionController {
    private final PrediccionService service;

    /**
     * Retrieves all health predictions.
     *
     * @return a list of all health predictions
     */
    @GetMapping
    public List<Prediccion> findAll() {
        return service.findAll();
    }

    /**
     * Retrieves a health prediction by its ID.
     *
     * @param id the ID of the health prediction to retrieve
     * @return the health prediction with the given ID, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Prediccion> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Saves a new health prediction.
     *
     * @param entity the health prediction to save
     * @return the saved health prediction
     */
    @PostMapping
    public Prediccion save(@RequestBody Prediccion entity) {
        return service.save(entity);
    }

    /**
     * Updates an existing health prediction.
     *
     * @param id the ID of the health prediction to update
     * @param entity the updated health prediction data
     * @return the updated health prediction, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Prediccion> update(@PathVariable Long id, @RequestBody Prediccion entity) {
        Prediccion updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /**
     * Deletes a health prediction by its ID.
     *
     * @param id the ID of the health prediction to delete
     * @return a response indicating the result of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
