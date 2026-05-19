package iye.grupo2.cronicotrak.repositories;

import iye.grupo2.cronicotrak.entities.Establecimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EstablecimientoRepository extends JpaRepository<Establecimiento, Long> {
}
