# Prompt: logs de seguridad (Keycloak + contenedor de dump)

> Prompt para compartir con quien vaya a implementar la funcionalidad.

---

Necesito que implementes un sistema de **logs de seguridad** en el proyecto Cronicotrak (backend Spring Boot 4 + PostgreSQL + Keycloak). El objetivo es que los eventos de seguridad se registren en el **contenedor de backup** (`iye-backup`) y queden **persistidos en el volumen de dumps** cada vez que se ejecute un respaldo.

## Contexto del proyecto (léelo antes de empezar)

El repositorio es `Proyecto-IYE-backend`. Lee estos documentos, que explican el estado actual:

- **`Development/BACKUP_README.md`** → cómo funciona el dump de PostgreSQL: el contenedor `iye-backup` (imagen `postgres:17-alpine`) ejecuta `pg_dump -Fc` de las bases `iye-db` y `keycloak` vía cron (`BACKUP_CRON`, default `0 3 * * *`), rota conservando `BACKUP_RETENTION` copias (default 30) en el volumen Docker `iye-backups` (montado en `/backups`). El log del backup se escribe en `/backups/backup.log`.
- **`Development/ENCRYPTION_README.md`** → cómo se cifran los datos sensibles de pacientes con **AES-256-GCM** (clave `ENC_KEY` en base64, 32 bytes; IV de 12 bytes aleatorio por operación; formato `base64(IV || ciphertext || tag)`). La clave se inyecta vía variable de entorno `ENC_KEY` (el compose la pasa desde `Development/.env`; en producción debe venir de un secret manager). `ENC_MIGRATE` re-cifra datos existentes una sola vez.
- **`Development/KEYCLOAK_SETUP.md`** → autenticación/roles vía Keycloak 26 (realm `iye`, issuer `http://localhost:8180/realms/iye`), y cómo Spring valida los JWT (resource server).

## Archivos donde buscar el funcionamiento actual

- `Development/compose.yml` → servicio `iye-backup` (líneas ~139-171): variables `BACKUP_CRON`, `BACKUP_RETENTION`, `BACKUP_CRYPT`, `BACKUP_PASSWORD`, `BACKUP_LOG`, `DB_HOST`, `DB_USER`, `DB_PASSWORD`; volumen `iye-backups:/backups`; entrypoint `backup-entrypoint.sh`; monta `backup.sh`.
- `Development/backup.sh` → script de dump: genera `{base}_{stamp}.dump`, cifrado opcional con `openssl` (AES-256-CBC) si `BACKUP_CRYPT=true`, y rotación.
- `Development/backup-entrypoint.sh` → escribe el cron y redirige la salida a `backup.log`.
- `Backend/cronicotrak/src/main/resources/application.yaml` → propiedad `app.encryption.key` (alimentada por `ENC_KEY`) y `app.encryption.migrate`.
- `Backend/cronicotrak/src/main/java/iye/grupo2/cronicotrak/crypto/` → `CryptoService`, `StringCryptoConverter`, `CryptoProvider`, `EncryptionMigrationRunner`.
- `Backend/cronicotrak/src/main/java/iye/grupo2/cronicotrak/security/` → `SecurityConfig` y `KeycloakJwtRolesConverter` (dónde Spring valida JWT y roles).
- `Development/.env` → aquí se definen `ENC_KEY`, `ENC_MIGRATE`, `DB_USER`, `DB_PASSWORD`, `KC_ISSUER_URI`, etc.

## Qué necesito que implementes

1. **Logs de seguridad de Keycloak y del backend**: registrar eventos como login exitoso/fallido, accesos a endpoints protegidos, cambios en datos sensibles de pacientes (crear/actualizar/eliminar), y cualquier evento relacionado con la autenticación. Indícame el diseño que propongas (formato, campos mínimos: timestamp, usuario, acción, IP, resultado, nivel de severidad).

2. **Los logs deben aparecer en el contenedor `iye-backup`**: al igual que hoy `backup.sh` escribe en `/backups/backup.log`, necesito que los logs de seguridad también queden visibles en ese contenedor (por ejemplo, un archivo dedicado tipo `/backups/security.log`, o sumarlos al `backup.log`).

3. **Que se persistan al hacer el dump**: cada vez que el cron o un backup manual ejecute `backup.sh`, los logs de seguridad acumulados deben quedar guardados en el volumen `iye-backups` (ya sea dentro del propio dump o como archivo adicional rotado con la misma política).

4. **Consistencia con el cifrado**: si los logs de seguridad contienen datos sensibles, deben cifrarse con la misma `ENC_KEY`/AES-256-GCM siguiendo lo documentado en `ENCRYPTION_README.md`. Si son solo metadatos de auditoría, dime cómo lo planteas.

5. **Configuración**: agrega las variables necesarias a `Development/.env` / `.env.example` y al servicio `iye-backup` en `compose.yml`, documentadas.

## Entregables

- Código implementado y funcionando (puedes probar levantando con `make start` o `docker compose -f Development/compose.yml up -d`).
- Documentación breve del diseño (dónde se guardan los logs, formato, cómo rotan, cómo se integran con el dump y con `ENC_KEY`).
- Verificación de que tras un backup los logs de seguridad quedan en el volumen `iye-backups`.

Avisa si necesitas aclarar algo del alcance antes de codificar.
