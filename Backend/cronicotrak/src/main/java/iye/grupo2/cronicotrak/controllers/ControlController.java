package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.DTO.FutureAppointmentDTO;
import iye.grupo2.cronicotrak.entities.Control;
import iye.grupo2.cronicotrak.services.ControlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for managing medical controls (appointments).
 */
@RestController
@RequestMapping("/api/v1/control")
@RequiredArgsConstructor
public class ControlController {
    private final ControlService service;

    /**
     * Retrieves all future appointments.
     *
     * @return a list of future appointments as DTOs
     */
    @GetMapping("/future/appointments")
    public List<FutureAppointmentDTO> findFutureAppointmentsDTO() {
        return service.findFutureAppointmentsDTO();
    }

    /**
     * Retrieves all medical controls.
     *
     * @return a list of all medical controls
     */
    @GetMapping
    public List<Control> findAll() {
        return service.findAll();
    }

    /**
     * Retrieves a medical control by its ID.
     *
     * @param id the ID of the medical control to retrieve
     * @return the medical control with the given ID, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Control> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Saves a new medical control.
     *
     * @param entity the medical control to save
     * @return the saved medical control
     */
    @PostMapping
    public Control save(@RequestBody Control entity) {
        return service.save(entity);
    }

    /**
     * Updates an existing medical control.
     *
     * @param id the ID of the medical control to update
     * @param entity the updated medical control data
     * @return the updated medical control, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Control> update(@PathVariable Long id, @RequestBody Control entity) {
        Control updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /**
     * Deletes a medical control by its ID.
     *
     * @param id the ID of the medical control to delete
     * @return a response indicating the result of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves the quantity of today's follow-ups.
     *
     * @return a map containing the quantity of today's follow-ups
     */
    @GetMapping("/get/followup/quantity")
    public ResponseEntity<Map<String, Long>> getFollowupQuantity() {
        long quantity = service.countTodayFollowups();
        Map<String, Long> response = new HashMap<>();
        response.put("quantity", quantity);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves the control rate.
     *
     * @return a map containing the control rate
     */
    @GetMapping("/get/controlrate")
    public ResponseEntity<Map<String, Double>> getControlRate() {
        double controlRate = service.getControlRate();
        Map<String, Double> response = new HashMap<>();
        response.put("controlRate", Math.round(controlRate * 100.0) / 100.0);
        return ResponseEntity.ok(response);
    }
}
