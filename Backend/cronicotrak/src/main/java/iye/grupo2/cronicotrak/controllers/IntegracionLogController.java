package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.IntegracionLog;
import iye.grupo2.cronicotrak.services.IntegracionLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing integration logs.
 */
@RestController
@RequestMapping("/api/v1/integracion-log")
@RequiredArgsConstructor
public class IntegracionLogController {
    private final IntegracionLogService service;

    /**
     * Retrieves all integration logs.
     *
     * @return a list of all integration logs
     */
    @GetMapping
    public List<IntegracionLog> findAll() {
        return service.findAll();
    }

    /**
     * Retrieves an integration log by its ID.
     *
     * @param id the ID of the integration log to retrieve
     * @return the integration log with the given ID, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<IntegracionLog> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Saves a new integration log.
     *
     * @param entity the integration log to save
     * @return the saved integration log
     */
    @PostMapping
    public IntegracionLog save(@RequestBody IntegracionLog entity) {
        return service.save(entity);
    }

    /**
     * Updates an existing integration log.
     *
     * @param id the ID of the integration log to update
     * @param entity the updated integration log data
     * @return the updated integration log, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<IntegracionLog> update(@PathVariable Long id, @RequestBody IntegracionLog entity) {
        IntegracionLog updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /**
     * Deletes an integration log by its ID.
     *
     * @param id the ID of the integration log to delete
     * @return a response indicating the result of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
