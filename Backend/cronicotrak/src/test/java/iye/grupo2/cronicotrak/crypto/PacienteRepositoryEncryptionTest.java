package iye.grupo2.cronicotrak.crypto;

import iye.grupo2.cronicotrak.entities.Paciente;
import iye.grupo2.cronicotrak.repositories.PacienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Verifica que el cifrado por converter no rompe la persistencia JPA:
 * guardar/leer un Paciente devuelve los datos en claro, y en la base
 * quedan valores cifrados (no legibles).
 */
@DataJpaTest
class PacienteRepositoryEncryptionTest {

    @Autowired
    private PacienteRepository repository;

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        CryptoProvider.setForTests(new CryptoService("n+WPGF8DECxYtC80EBZur9WDZIZS3reCBLNB9zJ3ZVU="));
    }

    @Test
    void saveAndRead_roundTrip_returnsPlaintext() {
        Paciente paciente = Paciente.builder()
                .rut("11111111-1")
                .nombre("Ana Test")
                .phone("56912345678")
                .direccion("Av. Siempre Viva 123")
                .age(45)
                .build();

        Paciente saved = repository.save(paciente);
        entityManager.flush();
        entityManager.clear();

        Paciente loaded = repository.findById(saved.getId()).orElseThrow();
        assertEquals("11111111-1", loaded.getRut());
        assertEquals("Ana Test", loaded.getNombre());
        assertEquals("56912345678", loaded.getPhone());
        assertEquals("Av. Siempre Viva 123", loaded.getDireccion());
    }

    @Test
    void storedColumns_containCiphertext_notPlaintext() {
        Paciente saved = repository.save(Paciente.builder()
                .rut("22222222-2")
                .nombre("Luis Test")
                .phone("56999999999")
                .direccion("Calle 1")
                .build());
        entityManager.flush();

        String rawNombre = jdbcTemplate.queryForObject(
                "SELECT nombre FROM paciente WHERE id = ?", String.class, saved.getId());
        String rawRut = jdbcTemplate.queryForObject(
                "SELECT rut FROM paciente WHERE id = ?", String.class, saved.getId());

        assertNotEquals("Luis Test", rawNombre);
        assertNotEquals("22222222-2", rawRut);
        assertTrue(rawNombre.matches("^[A-Za-z0-9+/=]+$"));
    }
}
