package iye.grupo2.cronicotrak.repositories;

import iye.grupo2.cronicotrak.entities.Indicador;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndicadorRepository extends JpaRepository<Indicador, Long> {
    List<Indicador> findByPatologiaId(Long patologiaId);
}
