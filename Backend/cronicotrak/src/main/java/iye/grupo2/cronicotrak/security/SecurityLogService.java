package iye.grupo2.cronicotrak.security;

import iye.grupo2.cronicotrak.crypto.CryptoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.Instant;

@Service
public class SecurityLogService {
    
    private static final Logger logger = LoggerFactory.getLogger(SecurityLogService.class);
    private final String logFilePath;
    private final CryptoService cryptoService;

    public SecurityLogService(@Value("${SECURITY_LOG_FILE:/backups/security.log}") String logFilePath, CryptoService cryptoService) {
        this.logFilePath = logFilePath;
        this.cryptoService = cryptoService;
    }

    public void logEvent(String user, String action, String ip, String severity, String details, boolean encryptDetails) {
        try {
            String processedDetails = details;
            if (encryptDetails && details != null && !details.isEmpty()) {
                processedDetails = cryptoService.encrypt(details);
            }

            String logEntry = String.format("{\"timestamp\":\"%s\",\"user\":\"%s\",\"action\":\"%s\",\"ip\":\"%s\",\"severity\":\"%s\",\"details\":\"%s\"}\n",
                    Instant.now().toString(),
                    escapeJson(user),
                    escapeJson(action),
                    escapeJson(ip),
                    escapeJson(severity),
                    escapeJson(processedDetails));

            Path path = Paths.get(logFilePath);
            if (path.getParent() != null && !Files.exists(path.getParent())) {
                Files.createDirectories(path.getParent());
            }

            Files.writeString(path, logEntry, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (Exception e) {
            logger.error("Error writing security log", e);
        }
    }

    private String escapeJson(String input) {
        if (input == null) return "";
        return input.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
    }
}
