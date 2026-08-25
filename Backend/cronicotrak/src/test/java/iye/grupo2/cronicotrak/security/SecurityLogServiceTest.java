package iye.grupo2.cronicotrak.security;

import iye.grupo2.cronicotrak.crypto.CryptoService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class SecurityLogServiceTest {

    @Mock
    private CryptoService cryptoService;

    private SecurityLogService securityLogService;
    private final String logFile = "test-security.log";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        securityLogService = new SecurityLogService(logFile, cryptoService);
    }

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(logFile));
    }

    @Test
    void testLogEventUnencrypted() throws IOException {
        securityLogService.logEvent("user1", "TEST_ACTION", "127.0.0.1", "INFO", "some details", false);
        
        Path path = Paths.get(logFile);
        assertTrue(Files.exists(path));
        
        String content = Files.readString(path);
        assertTrue(content.contains("user1"));
        assertTrue(content.contains("TEST_ACTION"));
        assertTrue(content.contains("some details"));
    }

    @Test
    void testLogEventEncrypted() throws Exception {
        when(cryptoService.encrypt(anyString())).thenReturn("encrypted-details");

        securityLogService.logEvent("user2", "TEST_ACTION_ENC", "127.0.0.1", "WARN", "secret details", true);
        
        Path path = Paths.get(logFile);
        assertTrue(Files.exists(path));
        
        String content = Files.readString(path);
        assertTrue(content.contains("user2"));
        assertTrue(content.contains("TEST_ACTION_ENC"));
        assertTrue(content.contains("encrypted-details"));
        assertTrue(!content.contains("secret details")); // Must not contain plaintext
    }
}
