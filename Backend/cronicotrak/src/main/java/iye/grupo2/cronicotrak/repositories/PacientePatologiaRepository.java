package iye.grupo2.cronicotrak.repositories;

import iye.grupo2.cronicotrak.entities.PacientePatologia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PacientePatologiaRepository extends JpaRepository<PacientePatologia, Long> {
    java.util.List<PacientePatologia> findByPacienteId(Long pacienteId);
    List<PacientePatologia> findByPacienteId(Long pacienteId);
}
