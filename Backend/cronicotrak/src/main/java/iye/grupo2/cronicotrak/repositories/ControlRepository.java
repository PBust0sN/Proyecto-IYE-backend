package iye.grupo2.cronicotrak.repositories;

import iye.grupo2.cronicotrak.entities.Control;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ControlRepository extends JpaRepository<Control, Long> {
    java.util.List<Control> findByFechaProgramadaGreaterThanEqualOrderByFechaProgramadaAsc(java.time.LocalDate date);
}
