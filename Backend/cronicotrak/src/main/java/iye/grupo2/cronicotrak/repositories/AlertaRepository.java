package iye.grupo2.cronicotrak.repositories;

import iye.grupo2.cronicotrak.entities.Alerta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AlertaRepository extends JpaRepository<Alerta, Long> {
    long countByResueltaFalse();
    
    @Query("SELECT a FROM Alerta a")
    List<Alerta> findAllAlertas();
    
    @Query("SELECT a FROM Alerta a JOIN FETCH a.paciente WHERE DATE(a.fecha) = CURRENT_DATE ORDER BY a.fecha DESC")
    List<Alerta> findTodayAlerts();
}
