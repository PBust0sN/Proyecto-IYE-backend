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

@RestController
@RequestMapping("/api/v1/control")
@RequiredArgsConstructor
public class ControlController {
    private final ControlService service;

    @GetMapping("/future/appointments")
    public List<FutureAppointmentDTO> findFutureAppointmentsDTO() {
        return service.findFutureAppointmentsDTO();
    }

    @GetMapping
    public List<Control> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Control> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Control save(@RequestBody Control entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Control> update(@PathVariable Long id, @RequestBody Control entity) {
        Control updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/followup/quantity")
    public ResponseEntity<Map<String, Long>> getFollowupQuantity() {
        long quantity = service.countTodayFollowups();
        Map<String, Long> response = new HashMap<>();
        response.put("quantity", quantity);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get/controlrate")
    public ResponseEntity<Map<String, Double>> getControlRate() {
        double controlRate = service.getControlRate();
        Map<String, Double> response = new HashMap<>();
        response.put("controlRate", Math.round(controlRate * 100.0) / 100.0);
        return ResponseEntity.ok(response);
    }
}
