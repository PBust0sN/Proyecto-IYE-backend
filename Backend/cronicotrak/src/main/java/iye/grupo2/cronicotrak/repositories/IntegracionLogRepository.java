package iye.grupo2.cronicotrak.repositories;

import iye.grupo2.cronicotrak.entities.IntegracionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntegracionLogRepository extends JpaRepository<IntegracionLog, Long> {
}
