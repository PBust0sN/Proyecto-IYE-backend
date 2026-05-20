package iye.grupo2.cronicotrak.repositories;

import iye.grupo2.cronicotrak.entities.Prediccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface PrediccionRepository extends JpaRepository<Prediccion, Long> {
    @Query("SELECT p FROM Prediccion p WHERE p.riesgoDescompensacion >= 0.7")
    List<Prediccion> findAllDecompensated();
}
