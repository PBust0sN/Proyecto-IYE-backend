package iye.grupo2.cronicotrak.repositories;

import iye.grupo2.cronicotrak.entities.SincronizacionOffline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SincronizacionOfflineRepository extends JpaRepository<SincronizacionOffline, Long> {
}
