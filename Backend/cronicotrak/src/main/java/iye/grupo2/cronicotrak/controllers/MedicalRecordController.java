package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.dto.MedicalRecordDTO;
import iye.grupo2.cronicotrak.services.MedicalRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/medical/record")
@RequiredArgsConstructor
public class MedicalRecordController {

    private final MedicalRecordService service;

    /**
     * GET /api/v1/medical/record/{idPatient}
     * Retorna la ficha médica completa del paciente.
     */
    @GetMapping("/{idPatient}")
    public ResponseEntity<MedicalRecordDTO> getMedicalRecord(@PathVariable Long idPatient) {
        return service.getMedicalRecord(idPatient)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * PUT /api/v1/medical/record/{idPatient}
     * Actualiza la ficha médica del paciente.
     */
    @PutMapping("/{idPatient}")
    public ResponseEntity<MedicalRecordDTO> updateMedicalRecord(
            @PathVariable Long idPatient,
            @RequestBody MedicalRecordDTO dto) {
        return service.updateMedicalRecord(idPatient, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * DELETE /api/v1/medical/record/{idPatient}
     * Soft delete: oculta al paciente (activo = false). No elimina datos.
     */
    @DeleteMapping("/{idPatient}")
    public ResponseEntity<Void> hideMedicalRecord(@PathVariable Long idPatient) {
        boolean hidden = service.hideMedicalRecord(idPatient);
        return hidden ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
