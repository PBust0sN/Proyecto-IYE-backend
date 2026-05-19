package iye.grupo2.cronicotrak.repositories;

import iye.grupo2.cronicotrak.entities.Patologia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatologiaRepository extends JpaRepository<Patologia, Long> {
}
