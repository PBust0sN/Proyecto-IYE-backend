package iye.grupo2.cronicotrak.repositories;

import iye.grupo2.cronicotrak.entities.Control;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;

@Repository
public interface ControlRepository extends JpaRepository<Control, Long> {
    @Query("SELECT COUNT(c) FROM Control c WHERE c.fechaReal = :fecha")
    long countByFechaReal(LocalDate fecha);
}
