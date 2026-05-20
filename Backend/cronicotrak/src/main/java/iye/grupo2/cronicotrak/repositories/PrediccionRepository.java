package iye.grupo2.cronicotrak.repositories;

import iye.grupo2.cronicotrak.entities.Prediccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrediccionRepository extends JpaRepository<Prediccion, Long> {
    java.util.List<Prediccion> findByPacienteId(Long pacienteId);
}
