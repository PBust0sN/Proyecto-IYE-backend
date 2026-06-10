package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.DTO.GETPatient;
import iye.grupo2.cronicotrak.DTO.PatientDetailDTO;
import iye.grupo2.cronicotrak.DTO.PatientQuantityDTO;
import iye.grupo2.cronicotrak.entities.Paciente;
import iye.grupo2.cronicotrak.services.PacienteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Controller for managing patient operations.
 */
@RestController
@RequestMapping("/api/v1/paciente")
@RequiredArgsConstructor
public class PacienteController {
    private final PacienteService service;

    /**
     * Retrieves quantities of patients.
     *
     * @return a list of patient quantities by category
     */
    @GetMapping("/patients/quantities")
    public List<PatientQuantityDTO> getPatientsQuantities() {
        return service.getPatientsQuantities();
    }

    /**
     * Retrieves detailed information of a patient by ID.
     *
     * @param id the ID of the patient
     * @return detailed information of the patient, or 404 if not found
     */
    @GetMapping("/detail/{id}")
    public ResponseEntity<PatientDetailDTO> findPatientDetailById(@PathVariable Long id) {
        PatientDetailDTO detail = service.findPatientDetailById(id);
        return detail != null ? ResponseEntity.ok(detail) : ResponseEntity.notFound().build();
    }

    /**
     * Retrieves all patients as DTOs.
     *
     * @return a list of all patients as DTOs
     */
    @GetMapping("/patients")
    public List<GETPatient> findAllPatientsDTO() {
        return service.findAllPatientsDTO();
    }

    /**
     * Retrieves all patient entities.
     *
     * @return a list of all patient entities
     */
    @GetMapping
    public List<Paciente> findAll() {
        return service.findAll();
    }

    /**
     * Retrieves a patient by its ID.
     *
     * @param id the ID of the patient to retrieve
     * @return the patient with the given ID, or 404 if not found
     */
    @GetMapping("/{id}")
    public ResponseEntity<Paciente> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Saves a new patient.
     *
     * @param entity the patient to save
     * @return the saved patient
     */
    @PostMapping
    public Paciente save(@RequestBody Paciente entity) {
        return service.save(entity);
    }

    /**
     * Updates an existing patient.
     *
     * @param id the ID of the patient to update
     * @param entity the updated patient data
     * @return the updated patient, or 404 if not found
     */
    @PutMapping("/{id}")
    public ResponseEntity<Paciente> update(@PathVariable Long id, @RequestBody Paciente entity) {
        Paciente updated = service.update(id, entity);
        return updated != null ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build();
    }

    /**
     * Deletes a patient by its ID.
     *
     * @param id the ID of the patient to delete
     * @return a response indicating the result of the operation
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Retrieves the total quantity of patients.
     *
     * @return a map containing the total quantity of patients
     */
    @GetMapping("/get/all/patients/quantity")
    public ResponseEntity<Map<String, Long>> getPatientsQuantity() {
        long quantity = service.countAll();
        Map<String, Long> response = new HashMap<>();
        response.put("quantity", quantity);
        return ResponseEntity.ok(response);
    }
}
