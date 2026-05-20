package iye.grupo2.cronicotrak.repositories;

import iye.grupo2.cronicotrak.entities.PacientePatologia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacientePatologiaRepository extends JpaRepository<PacientePatologia, Long> {
    java.util.List<PacientePatologia> findByPacienteId(Long pacienteId);
}
