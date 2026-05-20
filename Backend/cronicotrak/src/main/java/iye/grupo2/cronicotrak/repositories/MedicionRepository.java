package iye.grupo2.cronicotrak.repositories;

import iye.grupo2.cronicotrak.entities.Medicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MedicionRepository extends JpaRepository<Medicion, Long> {
    List<Medicion> findByPacienteIdAndIndicadorId(Long pacienteId, Long indicadorId);

    @Query("SELECT m FROM Medicion m WHERE m.paciente.id = :pacienteId AND m.indicador.id = :indicadorId ORDER BY m.fecha DESC LIMIT 1")
    Optional<Medicion> findLatestByPacienteIdAndIndicadorId(@Param("pacienteId") Long pacienteId, @Param("indicadorId") Long indicadorId);
}
