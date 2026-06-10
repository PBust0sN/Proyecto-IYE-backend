package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.entities.Recordatorio;
import iye.grupo2.cronicotrak.services.RecordatorioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing reminders.
 */
@RestController
@RequestMapping("/api/v1/recordatorio")
@RequiredArgsConstructor
public class RecordatorioController {
    private final RecordatorioService service;

    /**
     * Retrieves all reminders.
     *
     * @return a list of all reminders
     */
    @GetMapping
    public List<Recordatorio> findAll() {
        return service.findAll();
    }

    /**
     * Retrieves a reminder by its ID.
     *
     * @param id the ID of the reminder to retrieve
     * @return the reminder with the given ID, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Recordatorio> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Saves a new reminder.
     *
     * @param entity the reminder to save
     * @return the saved reminder
     */
    @PostMapping
    public Recordatorio save(@RequestBody Recordatorio entity) {
        return service.save(entity);
    }

    /**
     * Updates an existing reminder.
     *
     * @param id the ID of the reminder to update
     * @param entity the updated reminder data
     * @return the updated reminder, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Recordatorio> update(@PathVariable Long id, @RequestBody Recordatorio entity) {
        Recordatorio updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /**
     * Deletes a reminder by its ID.
     *
     * @param id the ID of the reminder to delete
     * @return a response indicating the result of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
