package iye.grupo2.cronicotrak.crypto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StringCryptoConverterTest {

    private static final String KEY = "n+WPGF8DECxYtC80EBZur9WDZIZS3reCBLNB9zJ3ZVU=";

    private final StringCryptoConverter converter = new StringCryptoConverter();

    @BeforeEach
    void setUp() {
        CryptoProvider.setForTests(new CryptoService(KEY));
    }

    @Test
    void convertToDatabaseColumn_ciphersValue() {
        String dbValue = converter.convertToDatabaseColumn("Ana Test");
        assertNotEquals("Ana Test", dbValue);
        assertTrue(dbValue.matches("^[A-Za-z0-9+/=]+$")); // base64
    }

    @Test
    void convertToEntityAttribute_returnsPlaintext() {
        String dbValue = converter.convertToDatabaseColumn("Ana Test");
        assertEquals("Ana Test", converter.convertToEntityAttribute(dbValue));
    }

    @Test
    void nullValues_passthrough() {
        assertNull(converter.convertToDatabaseColumn(null));
        assertNull(converter.convertToEntityAttribute(null));
    }
}
