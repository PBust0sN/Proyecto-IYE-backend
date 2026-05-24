package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.Indicador;
import iye.grupo2.cronicotrak.services.IndicadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing health indicators.
 */
@RestController
@RequestMapping("/api/v1/indicador")
@RequiredArgsConstructor
public class IndicadorController {
    private final IndicadorService service;

    /**
     * Retrieves all health indicators.
     *
     * @return a list of all health indicators
     */
    @GetMapping
    public List<Indicador> findAll() {
        return service.findAll();
    }

    /**
     * Retrieves a health indicator by its ID.
     *
     * @param id the ID of the health indicator to retrieve
     * @return the health indicator with the given ID, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Indicador> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Saves a new health indicator.
     *
     * @param entity the health indicator to save
     * @return the saved health indicator
     */
    @PostMapping
    public Indicador save(@RequestBody Indicador entity) {
        return service.save(entity);
    }

    /**
     * Updates an existing health indicator.
     *
     * @param id the ID of the health indicator to update
     * @param entity the updated health indicator data
     * @return the updated health indicator, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Indicador> update(@PathVariable Long id, @RequestBody Indicador entity) {
        Indicador updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /**
     * Deletes a health indicator by its ID.
     *
     * @param id the ID of the health indicator to delete
     * @return a response indicating the result of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
