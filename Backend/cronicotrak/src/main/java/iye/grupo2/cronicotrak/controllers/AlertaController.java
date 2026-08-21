package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.dtos.RecentAlertDto;
import iye.grupo2.cronicotrak.entities.Alerta;
import iye.grupo2.cronicotrak.services.AlertaService;
import iye.grupo2.cronicotrak.services.RecentAlertService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for managing alerts.
 */
@RestController
@RequestMapping("/api/v1/alerta")
@RequiredArgsConstructor
public class AlertaController {
    private final AlertaService service;
    private final RecentAlertService recentAlertService;
    private final iye.grupo2.cronicotrak.services.MotorReglasService motorReglasService;

    /**
     * Retrieves all alerts.
     *
     * @return a list of all alerts
     */
    @GetMapping
    public List<Alerta> findAll() {
        return service.findAll();
    }

    /**
     * Retrieves an alert by its ID.
     *
     * @param id the ID of the alert to retrieve
     * @return the alert with the given ID, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Alerta> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Saves a new alert.
     *
     * @param entity the alert to save
     * @return the saved alert
     */
    @PostMapping
    public Alerta save(@RequestBody Alerta entity) {
        return service.save(entity);
    }

    /**
     * Updates an existing alert.
     *
     * @param id the ID of the alert to update
     * @param entity the updated alert data
     * @return the updated alert, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Alerta> update(@PathVariable Long id, @RequestBody Alerta entity) {
        Alerta updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /**
     * Deletes an alert by its ID.
     *
     * @param id the ID of the alert to delete
     * @return a response indicating the result of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves the quantity of active alerts.
     *
     * @return a map containing the quantity of active alerts
     */
    @GetMapping("/get/active/alerts/quantity")
    public ResponseEntity<Map<String, Long>> getActiveAlertsQuantity() {
        long quantity = service.countActiveAlerts();
        Map<String, Long> response = new HashMap<>();
        response.put("quantity", quantity);
        return ResponseEntity.ok(response);
    }

    /**
     * Retrieves recent alerts.
     *
     * @return a list of recent alerts
     */
    @GetMapping("/get/recent/alerts")
    public ResponseEntity<List<RecentAlertDto>> getRecentAlerts() {
        List<RecentAlertDto> alerts = recentAlertService.getTodayAlerts();
        return ResponseEntity.ok(alerts);
    }

    /**
     * Gatilla la demostración de alertas preventivas.
     *
     * @param phone the phone number to send alerts to
     * @return summary of executed alerts
     */
    @PostMapping("/trigger-demo")
    public ResponseEntity<Map<String, String>> triggerDemo(@RequestParam String phone) {
        String reporte = motorReglasService.ejecutarDemo(phone);
        Map<String, String> response = new HashMap<>();
        response.put("reporte", reporte);
        return ResponseEntity.ok(response);
    }
}
