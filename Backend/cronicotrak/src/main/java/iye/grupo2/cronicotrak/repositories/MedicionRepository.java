package iye.grupo2.cronicotrak.repositories;

import iye.grupo2.cronicotrak.entities.Medicion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicionRepository extends JpaRepository<Medicion, Long> {
    java.util.Optional<Medicion> findFirstByPacienteIdOrderByFechaDesc(Long pacienteId);
}
