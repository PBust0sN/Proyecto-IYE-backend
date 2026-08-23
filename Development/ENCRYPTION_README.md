# Cifrado de datos sensibles (IYE)

Cronicotrak maneja datos personales y clínicos de pacientes. Para protegerlos en
reposo (cumpliendo con las exigencias de datos de salud), los campos sensibles se
**cifran en la base de datos** usando el algoritmo **AES-256-GCM**.

## ¿Qué se cifra?

Campos de la entidad `Paciente` (`Backend/cronicotrak/src/main/java/iye/grupo2/cronicotrak/entities/Paciente.java`):

| Columna (BD)   | Campo JPA          | Dato |
|----------------|--------------------|------|
| `rut`          | `rut`              | RUT del paciente |
| `nombre`       | `nombre`           | Nombre completo |
| `telefono`     | `phone`            | Teléfono de contacto |
| `direccion`    | `direccion`        | Domicilio |
| `email`        | `email`            | Correo electrónico |
| `tipo_sangre`  | `tipoSangre`       | Grupo sanguíneo |
| `nombre_emergencia` | `nombreEmergencia` | Nombre de contacto de emergencia |
| `telefono_emergencia` | `telefonoEmergencia` | Teléfono de emergencia |
| `alergia` (tabla `paciente_alergia`) | `alergias` | Alergias del paciente |

El resto de campos (fechas, valores de medición, indicadores, etc.) no contienen
información personal identificable y quedan en claro.

## Algoritmo y formato

- **Cifrado**: **AES-256-GCM** (modo autenticado) vía `Cipher.getInstance("AES/GCM/NoPadding")`.
- **Clave**: 32 bytes (256 bits), recibida en base64 desde la propiedad
  `app.encryption.key`, alimentada por la variable de entorno **`ENC_KEY`**.
- **IV**: 12 bytes aleatorios por operación (`SecureRandom`). Un IV nuevo por
  cifrado produce valores distintos para el mismo texto (seguridad semántica).
- **Tag de autenticación**: 128 bits (GCM), detecta manipulación del dato cifrado.
- **Formato persistido**: `base64( IV || ciphertext || tag )`.

> GCM es un cifrado **autenticado**: además de confidencialidad, detecta si el dato
> fue alterado en la base o si se usa una clave incorrecta.

## Componentes

Todo el código vive en `Backend/cronicotrak/src/main/java/iye/grupo2/cronicotrak/crypto/`:

| Clase | Responsabilidad |
|-------|-----------------|
| `CryptoService` | Cifra/descifra AES-256-GCM con IV aleatorio. |
| `StringCryptoConverter` | `AttributeConverter` de JPA: cifra al persistir y descifra al leer. |
| `CryptoProvider` | Expone `CryptoService` estáticamente a los converters (Hibernate los crea fuera de Spring). |
| `EncryptionMigrationRunner` | Re-cifra datos preexistentes que aún están en claro (una sola vez). |

El converter se aplica a cada campo sensible con `@Convert(converter = StringCryptoConverter.class)`.

## Configuración

En `Backend/cronicotrak/src/main/resources/application.yaml`:

```yaml
app:
  encryption:
    key: ${ENC_KEY:n+WPGF8DECxYtC80EBZur9WDZIZS3reCBLNB9zJ3ZVU=}
    migrate: ${ENC_MIGRATE:false}
```

- **`ENC_KEY`** (obligatorio en producción): clave AES-256 en base64. Debe venir de un
  gestor de secretos (Keycloak/secret manager), **nunca** hardcodeada. El default solo
  sirve para desarrollo local y tests.
- **`ENC_MIGRATE`**: `true` re-cifra los datos históricos en claro al arrancar.

> ⚠️ Si cambias la clave después de cifrar datos, no podrás descifrar lo existente.
> Respaldala de forma segura y no la rotes sin planificar una re-cifra.

### Generar una clave

```bash
openssl rand -base64 32
```

## Migración de datos existentes

1. Activa el cifrado en la configuración.
2. Arranca una única vez con `ENC_MIGRATE=true`: el runner recorre `paciente` y
   `paciente_alergia`, descifra los valores que ya estén cifrados (idempotente) y
   cifra los que aún están en claro.
3. Vuelve a `ENC_MIGRATE=false` y verifica lecturas/escrituras normales.

La migración también puede dispararse desde el script de despliegue o el `Makefile`.

## Tests

En `Backend/cronicotrak/src/test/java/iye/grupo2/cronicotrak/crypto/`:

| Test | Verifica |
|------|----------|
| `CryptoServiceTest` | Round-trip, IV aleatorio, rechazo de texto manipulado/clave incorrecta. |
| `StringCryptoConverterTest` | El converter cifra en BD y descifra al leer. |
| `PacienteRepositoryEncryptionTest` | Guardar/leer un `Paciente` con H2 devuelve texto en claro y en BD quedan valores cifrados. |

Los tests JPA usan **H2 en modo PostgreSQL** (config en
`src/test/resources/application.yaml`), por lo que no requieren una base externa.

## Notas

- No existen consultas que busquen por `rut`/`nombre`/`email` en claro, por lo que el
  cifrado no afecta a los repositorios existentes.
- Para autenticación, las contraseñas de usuarios se manejan en Keycloak (fuera de
  esta base); el campo `password` de `usuario` no se usa para login local.
