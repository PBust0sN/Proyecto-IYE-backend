package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.DTO.GETPatient;
import iye.grupo2.cronicotrak.DTO.PatientDetailDTO;
import iye.grupo2.cronicotrak.entities.Paciente;
import iye.grupo2.cronicotrak.services.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/paciente")
@RequiredArgsConstructor
public class PacienteController {
    private final PacienteService service;

    @GetMapping("/detail/{id}")
    public ResponseEntity<PatientDetailDTO> findPatientDetailById(@PathVariable Long id) {
        PatientDetailDTO detail = service.findPatientDetailById(id);
        return detail != null ? ResponseEntity.ok(detail) : ResponseEntity.notFound().build();
    }

    @GetMapping("/patients")
    public List<GETPatient> findAllPatientsDTO() {
        return service.findAllPatientsDTO();
    }

    @GetMapping
    public List<Paciente> findAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Paciente save(@RequestBody Paciente entity) {
        return service.save(entity);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> update(@PathVariable Long id, @RequestBody Paciente entity) {
        Paciente updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
