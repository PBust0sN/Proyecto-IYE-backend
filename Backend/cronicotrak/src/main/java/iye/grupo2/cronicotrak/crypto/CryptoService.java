package iye.grupo2.cronicotrak.crypto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * Servicio de cifrado simétrico AES-256-GCM.
 *
 * <p>Se usa para cifrar datos sensibles de pacientes antes de persistirlos y
 * descifrarlos al leerlos, mediante {@link AttributeConverter}s de JPA.</p>
 *
 * <p>Formato del valor cifrado (base64): {@code [IV de 12 bytes][ciphertext + GCM tag]}.
 * El IV es aleatorio por operación, por lo que cifrar el mismo texto dos veces
 * produce valores distintos (seguridad semántica).</p>
 *
 * <p>La clave maestra se inyecta desde la propiedad {@code app.encryption.key},
 * alimentada por la variable de entorno {@code ENC_KEY}. En producción debe
 * apuntar a un gestor de secretos y NUNCA quedar hardcodeada.</p>
 */
@Service
public class CryptoService {

    private static final String TRANSFORMATION = "AES/GCM/NoPadding";
    private static final int IV_LENGTH = 12;
    private static final int TAG_LENGTH_BITS = 128;

    private final SecretKey secretKey;
    private final SecureRandom secureRandom = new SecureRandom();

    public CryptoService(@Value("${app.encryption.key:}") String base64Key) {
        if (base64Key == null || base64Key.isBlank()) {
            throw new IllegalStateException(
                    "app.encryption.key (ENC_KEY) no configurada. El cifrado de datos sensibles requiere una clave AES.");
        }
        this.secretKey = new SecretKeySpec(Base64.getDecoder().decode(base64Key), "AES");
    }

    /**
     * Cifra el texto y devuelve base64(IV || ciphertext || tag).
     * Devuelve {@code null} si la entrada es {@code null}.
     */
    public String encrypt(String plaintext) {
        if (plaintext == null) {
            return null;
        }
        try {
            byte[] iv = new byte[IV_LENGTH];
            secureRandom.nextBytes(iv);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, new GCMParameterSpec(TAG_LENGTH_BITS, iv));
            byte[] encrypted = cipher.doFinal(plaintext.getBytes(StandardCharsets.UTF_8));

            ByteBuffer buffer = ByteBuffer.allocate(iv.length + encrypted.length);
            buffer.put(iv);
            buffer.put(encrypted);
            return Base64.getEncoder().encodeToString(buffer.array());
        } catch (Exception e) {
            throw new IllegalStateException("Error al cifrar dato sensible", e);
        }
    }

    /**
     * Descifra un valor producido por {@link #encrypt(String)}.
     * Devuelve {@code null} si la entrada es {@code null}.
     */
    public String decrypt(String ciphertext) {
        if (ciphertext == null) {
            return null;
        }
        try {
            byte[] decoded = Base64.getDecoder().decode(ciphertext);
            ByteBuffer buffer = ByteBuffer.wrap(decoded);
            byte[] iv = new byte[IV_LENGTH];
            buffer.get(iv);
            byte[] encrypted = new byte[buffer.remaining()];
            buffer.get(encrypted);

            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(Cipher.DECRYPT_MODE, secretKey, new GCMParameterSpec(TAG_LENGTH_BITS, iv));
            return new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new IllegalStateException(
                    "Error al descifrar dato sensible (¿clave incorrecta o valor no cifrado?)", e);
        }
    }
}
