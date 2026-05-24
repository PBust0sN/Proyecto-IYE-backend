package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.SincronizacionOffline;
import iye.grupo2.cronicotrak.services.SincronizacionOfflineService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing offline synchronization operations.
 */
@RestController
@RequestMapping("/api/v1/sincronizacion-offline")
@RequiredArgsConstructor
public class SincronizacionOfflineController {
    private final SincronizacionOfflineService service;

    /**
     * Retrieves all offline synchronization records.
     *
     * @return a list of all offline synchronization records
     */
    @GetMapping
    public List<SincronizacionOffline> findAll() {
        return service.findAll();
    }

    /**
     * Retrieves an offline synchronization record by its ID.
     *
     * @param id the ID of the record to retrieve
     * @return the record with the given ID, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<SincronizacionOffline> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Saves a new offline synchronization record.
     *
     * @param entity the record to save
     * @return the saved record
     */
    @PostMapping
    public SincronizacionOffline save(@RequestBody SincronizacionOffline entity) {
        return service.save(entity);
    }

    /**
     * Updates an existing offline synchronization record.
     *
     * @param id the ID of the record to update
     * @param entity the updated record data
     * @return the updated record, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<SincronizacionOffline> update(@PathVariable Long id, @RequestBody SincronizacionOffline entity) {
        SincronizacionOffline updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /**
     * Deletes an offline synchronization record by its ID.
     *
     * @param id the ID of the record to delete
     * @return a response indicating the result of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
