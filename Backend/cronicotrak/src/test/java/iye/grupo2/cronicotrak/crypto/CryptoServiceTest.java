package iye.grupo2.cronicotrak.crypto;

import org.junit.jupiter.api.Test;

import java.util.Base64;

import static org.junit.jupiter.api.Assertions.*;

class CryptoServiceTest {

    private static final String KEY = "n+WPGF8DECxYtC80EBZur9WDZIZS3reCBLNB9zJ3ZVU=";

    private final CryptoService crypto = new CryptoService(KEY);

    @Test
    void roundTrip_returnsOriginalPlaintext() {
        String plain = "Juan Perez";
        String encrypted = crypto.encrypt(plain);
        assertNotEquals(plain, encrypted);
        assertEquals(plain, crypto.decrypt(encrypted));
    }

    @Test
    void encrypt_samePlaintextTwice_producesDifferentCiphertext() {
        String a = crypto.encrypt("RUT 11111111-1");
        String b = crypto.encrypt("RUT 11111111-1");
        assertNotEquals(a, b);
    }

    @Test
    void nullInput_returnsNull() {
        assertNull(crypto.encrypt(null));
        assertNull(crypto.decrypt(null));
    }

    @Test
    void tamperedCiphertext_failsToDecrypt() {
        String encrypted = crypto.encrypt("dato sensible");
        byte[] bytes = Base64.getDecoder().decode(encrypted);
        bytes[bytes.length - 1] ^= 0x01; // corrompe el tag GCM
        String tampered = Base64.getEncoder().encodeToString(bytes);
        assertThrows(IllegalStateException.class, () -> crypto.decrypt(tampered));
    }

    @Test
    void plaintextInput_failsToDecrypt() {
        assertThrows(IllegalStateException.class, () -> crypto.decrypt("esto no esta cifrado"));
    }

    @Test
    void wrongKey_failsToDecrypt() {
        String encrypted = crypto.encrypt("secreto");
        CryptoService other = new CryptoService("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=");
        assertThrows(IllegalStateException.class, () -> other.decrypt(encrypted));
    }
}
