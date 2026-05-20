package iye.grupo2.cronicotrak.repositories;

import iye.grupo2.cronicotrak.entities.PacienteMedicamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacienteMedicamentoRepository extends JpaRepository<PacienteMedicamento, Long> {
    List<PacienteMedicamento> findByPacienteId(Long pacienteId);
}
