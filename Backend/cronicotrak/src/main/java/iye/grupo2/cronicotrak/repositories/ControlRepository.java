package iye.grupo2.cronicotrak.repositories;

import iye.grupo2.cronicotrak.entities.Control;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface ControlRepository extends JpaRepository<Control, Long> {

    @Query("SELECT c FROM Control c WHERE c.paciente.id = :pacienteId AND c.fechaReal IS NOT NULL ORDER BY c.fechaReal DESC LIMIT 1")
    Optional<Control> findLastControlByPacienteId(@Param("pacienteId") Long pacienteId);

    @Query("SELECT c FROM Control c WHERE c.paciente.id = :pacienteId AND c.fechaProgramada >= :today ORDER BY c.fechaProgramada ASC LIMIT 1")
    Optional<Control> findNextControlByPacienteId(@Param("pacienteId") Long pacienteId, @Param("today") LocalDate today);

    @Query("SELECT COUNT(c) FROM Control c WHERE c.fechaReal = :fecha")
    long countByFechaReal(LocalDate fecha);

    @Query("SELECT COUNT(c) FROM Control c WHERE c.asistio = true")
    long countByAsistioTrue();

    @Query("SELECT c FROM Control c WHERE c.asistio = true")
    List<Control> findAllControlled();
}
