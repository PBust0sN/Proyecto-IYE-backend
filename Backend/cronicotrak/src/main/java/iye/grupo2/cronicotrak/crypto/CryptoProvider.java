package iye.grupo2.cronicotrak.crypto;

import org.springframework.stereotype.Component;

/**
 * Puente entre los {@code AttributeConverter}s de JPA y el bean de Spring.
 *
 * <p>Hibernate instancia los converters fuera del contenedor de Spring, por lo
 * que no pueden recibir dependencias por inyección. Este holder expone el
 * {@link CryptoService} de forma estática una vez que el contexto arranca.</p>
 */
@Component
public class CryptoProvider {

    private static CryptoService cryptoService;

    public CryptoProvider(CryptoService cryptoService) {
        CryptoProvider.cryptoService = cryptoService;
    }

    public static CryptoService get() {
        if (cryptoService == null) {
            throw new IllegalStateException("CryptoService no inicializado (¿contexto de Spring no cargado?)");
        }
        return cryptoService;
    }

    /** Solo para pruebas unitarias fuera de Spring. */
    public static void setForTests(CryptoService service) {
        CryptoProvider.cryptoService = service;
    }
}
