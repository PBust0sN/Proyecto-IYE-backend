package iye.grupo2.cronicotrak.repositories;

import iye.grupo2.cronicotrak.entities.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    java.util.List<Alerta> findByPacienteId(Long pacienteId);
    long countByResueltaFalse();
    
    @Query("SELECT a FROM Alerta a")
    List<Alerta> findAllAlertas();
    
    @Query("SELECT a FROM Alerta a JOIN FETCH a.paciente WHERE a.fecha >= :start AND a.fecha < :end ORDER BY a.fecha DESC")
    List<Alerta> findTodayAlerts(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);
}
