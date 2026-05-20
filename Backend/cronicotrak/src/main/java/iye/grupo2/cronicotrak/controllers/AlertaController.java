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

@RestController
@RequestMapping("/api/v1/alerta")
@RequiredArgsConstructor
public class AlertaController {
    private final AlertaService service;
    private final RecentAlertService recentAlertService;

    @GetMapping
    public List<Alerta> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alerta> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Alerta save(@RequestBody Alerta entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alerta> update(@PathVariable Long id, @RequestBody Alerta entity) {
        Alerta updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/active/alerts/quantity")
    public ResponseEntity<Map<String, Long>> getActiveAlertsQuantity() {
        long quantity = service.countActiveAlerts();
        Map<String, Long> response = new HashMap<>();
        response.put("quantity", quantity);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/recent/alerts")
    public ResponseEntity<List<RecentAlertDto>> getRecentAlerts() {
        List<RecentAlertDto> alerts = recentAlertService.getTodayAlerts();
        return ResponseEntity.ok(alerts);
    }
}
