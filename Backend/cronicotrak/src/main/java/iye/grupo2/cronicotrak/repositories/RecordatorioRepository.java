package iye.grupo2.cronicotrak.repositories;

import iye.grupo2.cronicotrak.entities.Recordatorio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordatorioRepository extends JpaRepository<Recordatorio, Long> {
}
