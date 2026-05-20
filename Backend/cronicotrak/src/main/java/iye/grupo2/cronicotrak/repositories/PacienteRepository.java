package iye.grupo2.cronicotrak.repositories;

import iye.grupo2.cronicotrak.DTO.PatientQuantityDTO;
import iye.grupo2.cronicotrak.entities.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    @Query("SELECT new iye.grupo2.cronicotrak.DTO.PatientQuantityDTO(p.status, COUNT(p)) FROM Paciente p GROUP BY p.status")
    List<PatientQuantityDTO> countPatientsByStatus();
}
