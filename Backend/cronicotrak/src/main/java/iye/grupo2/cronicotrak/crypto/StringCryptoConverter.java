package iye.grupo2.cronicotrak.crypto;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Convierte atributos String cifrados a texto cifrado en la base y viceversa.
 * Aplica AES-256-GCM con IV aleatorio (ver {@link CryptoService}).
 */
@Converter
public class StringCryptoConverter implements AttributeConverter<String, String> {

    @Override
    public String convertToDatabaseColumn(String attribute) {
        return CryptoProvider.get().encrypt(attribute);
    }

    @Override
    public String convertToEntityAttribute(String dbData) {
        return CryptoProvider.get().decrypt(dbData);
    }
}
