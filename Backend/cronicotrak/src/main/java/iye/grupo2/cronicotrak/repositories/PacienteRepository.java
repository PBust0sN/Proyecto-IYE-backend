package iye.grupo2.cronicotrak.repositories;

import iye.grupo2.cronicotrak.DTO.PatientQuantityDTO;
import iye.grupo2.cronicotrak.entities.Paciente;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PacienteRepository extends JpaRepository<Paciente, Long> {
    @Query("SELECT new iye.grupo2.cronicotrak.DTO.PatientQuantityDTO(p.status, COUNT(p)) FROM Paciente p GROUP BY p.status")
    List<PatientQuantityDTO> countPatientsByStatus();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Paciente p WHERE p.id = :id")
    Optional<Paciente> findByIdForUpdate(@Param("id") Long id);

    Optional<Paciente> findByNombre(String nombre);
}
