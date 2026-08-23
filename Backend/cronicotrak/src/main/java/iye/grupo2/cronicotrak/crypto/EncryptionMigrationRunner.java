package iye.grupo2.cronicotrak.crypto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Re-cifra los datos existentes que aún están en claro tras activar los
 * converters de cifrado.
 *
 * <p>Se ejecuta solo cuando {@code app.encryption.migrate=true}
 * (variable de entorno {@code ENC_MIGRATE}). Es idempotente: si el valor
 * ya se descifra correctamente se deja intacto. Correr una única vez al
 * activar el cifrado; luego desactivar la bandera.</p>
 */
@Component
public class EncryptionMigrationRunner implements CommandLineRunner {

    private static final String[] PACIENTE_COLUMNS = {
            "rut", "nombre", "telefono", "direccion", "email", "tipo_sangre", "nombre_emergencia", "telefono_emergencia"
    };

    private final JdbcTemplate jdbcTemplate;
    private final CryptoService cryptoService;
    private final boolean enabled;

    public EncryptionMigrationRunner(
            JdbcTemplate jdbcTemplate,
            CryptoService cryptoService,
            @Value("${app.encryption.migrate:false}") boolean enabled) {
        this.jdbcTemplate = jdbcTemplate;
        this.cryptoService = cryptoService;
        this.enabled = enabled;
    }

    @Override
    public void run(String... args) {
        if (!enabled) {
            return;
        }
        System.out.println("[encryption] migrando datos existentes a cifrado AES-256-GCM...");
        migratePaciente();
        migratePacienteAlergia();
        System.out.println("[encryption] migración finalizada.");
    }

    private void migratePaciente() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                "SELECT id, rut, nombre, telefono, direccion, email, tipo_sangre, nombre_emergencia, telefono_emergencia FROM paciente");
        for (Map<String, Object> row : rows) {
            long id = ((Number) row.get("id")).longValue();
            for (String column : PACIENTE_COLUMNS) {
                migrateStringColumn("paciente", column, "id", id, (String) row.get(column));
            }
        }
    }

    private void migratePacienteAlergia() {
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(
                "SELECT paciente_id, alergia FROM paciente_alergia");
        for (Map<String, Object> row : rows) {
            long pacienteId = ((Number) row.get("paciente_id")).longValue();
            migrateStringColumn("paciente_alergia", "alergia", "paciente_id", pacienteId, (String) row.get("alergia"));
        }
    }

    private void migrateStringColumn(String table, String column, String idColumn, long id, String value) {
        if (value == null || value.isEmpty() || isEncrypted(value)) {
            return;
        }
        String encrypted = cryptoService.encrypt(value);
        jdbcTemplate.update(
                "UPDATE " + table + " SET " + column + " = ? WHERE " + idColumn + " = ? AND " + column + " = ?",
                encrypted, id, value);
        System.out.println("[encryption]   " + table + "." + column + " id=" + id + " cifrado");
    }

    private boolean isEncrypted(String value) {
        try {
            cryptoService.decrypt(value);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
