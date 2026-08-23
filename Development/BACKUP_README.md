# Backup automático de la base de datos (IYE)

El proyecto **Cronicotrak** maneja datos clínicos de pacientes que ingresan a diario.
Este mecanismo respalda automáticamente la base de datos PostgreSQL y conserva un
historial rotativo de copias para poder restaurar ante cualquier pérdida de datos.

## ¿Qué hace?

Un contenedor adicional `iye-backup` (definido en `compose.yml`) ejecuta de forma
programada un `pg_dump` de **ambas bases** que viven en la misma instancia PostgreSQL:

- **`iye`** → base de datos principal de la aplicación (pacientes, patologías, controles, mediciones, alertas, etc.).
- **`keycloak`** → base de usuarios y roles de autenticación.

Cada copia se genera en **formato custom** de PostgreSQL (`pg_dump -Fc`), que ya viene
comprimido y permite restaurar tablas individuales. Los archivos quedan en el volumen
Docker **`iye-backups`** con el nombre `{base}_{año}{mes}{día}_{hora}.dump`, y se conserva
un **log** en `backup.log` dentro del mismo volumen.

Después de cada ejecución se hace **rotación**: solo se conservan las últimas
`BACKUP_RETENTION` copias por base (default: 30), eliminando las más antiguas.

## ¿Cada cuánto corre?

Por defecto, **todos los días a las 03:00 AM** (hora del servidor), una hora de bajo
tráfico para no afectar a los usuarios. Esto se configura con la variable `BACKUP_CRON`
formato cron estándar de 5 campos:

| Expresión cron        | Significado                              |
|-----------------------|------------------------------------------|
| `0 3 * * *` (default) | Todos los días a las 03:00               |
| `0 3,15 * * *`        | Todos los días a las 03:00 y 15:00       |
| `0 3 * * 1`           | Todos los lunes a las 03:00              |
| `0 * * * *`           | Cada hora                                 |

> **Recomendación para datos de salud**: con pacientes ingresando todos los días, el
> mínimo aceptable es **1 copia diaria**. Si quieres reducir a menos de 24 h la pérdida
> máxima de datos (RPO), usa dos ejecuciones diarias (`0 3,15 * * *`) o configura
> *WAL archiving* (point-in-time recovery) como mejora a producción.

## Configuración

Variables disponibles (todas con valor por defecto, puedes sobrescribirlas en
`Development/.env`):

| Variable             | Default        | Descripción                                                    |
|----------------------|----------------|----------------------------------------------------------------|
| `BACKUP_CRON`        | `0 3 * * *`    | Expresión cron con la frecuencia del backup.                   |
| `BACKUP_RETENTION`   | `30`           | Nº de copias por base que se conservan antes de rotar.         |
| `BACKUP_CRYPT`       | `false`        | Activa cifrado AES-256 de los dumps (ver sección Cifrado).     |
| `BACKUP_PASSWORD`    | *(vacío)*      | Frase usada para cifrar/descifrar los dumps.                   |
| `ENC_KEY`            | *(default dev)*| Clave AES-256 (base64) usada para cifrar datos en BD. Requisito para leer los dumps. |
| `ENC_MIGRATE`        | `false`        | Re-cifra datos en claro al arrancar (una sola vez).            |

## Uso

Levantar la infraestructura (incluye el contenedor de backup):

```bash
make start          # o: docker compose -f Development/compose.yml up -d
```

Ver el log del backup:

```bash
docker compose -f Development/compose.yml logs -f iye-backup
# o ver el archivo persistente: cat (contenido del volumen iye-backups)/backup.log
```

Listar las copias generadas:

```bash
docker run --rm -v iye-backups:/backups -w /backups alpine ls -lah
```

Ejecutar un backup **manual** en cualquier momento (sin esperar al cron):

```bash
make backup
# o: docker compose -f Development/compose.yml run --rm --no-deps --entrypoint /bin/sh iye-backup /backup.sh
```

## Cómo restaurar

Los archivos `.dump` son compatibles con `pg_restore`. Para restaurar la base `iye`
(en un contenedor nuevo de PostgreSQL o deteniendo el servicio):

```bash
# 1) Volcar el backup a un archivo SQL plano
docker run --rm -v iye-backups:/backups postgres:17-alpine \
  pg_restore -f /tmp/restore.sql /backups/iye_YYYYMMDD_HHMMSS.dump

# 2) Aplicarlo a la base destino (ej. recreando el contenedor iye-db)
docker exec -i iye-db psql -U postgres -d iye < restore.sql

# Alternativa: restaurar directo contra una base vacía
docker run --rm -v iye-backups:/backups --network iye-net postgres:17-alpine \
  pg_restore -h iye-db -U postgres -d iye --clean --if-exists /backups/iye_YYYYMMDD_HHMMSS.dump
```

> **Regla de oro**: un backup que no se prueba no es un backup. Programa una
> restauración de prueba mensual en un contenedor descartable para verificar que
> las copias sirven.

## Cifrado de datos sensibles en la BD vs. cifrado del dump

La aplicación cifra los datos sensibles de pacientes (**AES-256-GCM**, campos como
`rut`, `nombre`, `telefono`, `direccion`, `email`, etc.) **en la propia base de datos**
antes de persistirlos. Ver [`ENCRYPTION_README.md`](ENCRYPTION_README.md).

Esto tiene implicaciones directas sobre los backups:

- **El `pg_dump` NO se rompe con el cifrado**: `pg_dump -Fc` copia la base a nivel de
  PostgreSQL y las columnas cifradas son solo strings base64, por lo que se vuelcan
  sin problema. El cifrado es transparente para el backup.
- **Los dumps contienen datos ya cifrados**: una restauración devuelve la base con el
  ciphertext almacenado; la aplicación lo descifra al leerlo.
- **La restauración depende de la clave `ENC_KEY`**: si restauras un dump en un entorno
  con una `ENC_KEY` distinta (o sin ella), la aplicación no podrá descifrar los datos
  y fallará al leerlos. La `ENC_KEY` es tan importante como el propio dump: respáldala
  de forma segura junto con los backups.
- **Dumps generados ANTES de activar el cifrado** contienen los datos **en claro**.
  Si restauras uno de estos en la aplicación actual, los converters intentarán
  descifrar texto plano y fallarán. Para esos dumps hay que re-migrar tras restaurar
  (arrancar una vez con `ENC_MIGRATE=true`) o descartarlos.
- **Recuperación de la clave**: si rotas o pierdes la `ENC_KEY`, todos los backups
  posteriores al cifrado quedan **inutilizables**. No la cambies sin planificar una
  re-migración completa.

> En resumen: el cifrado no interfiere con el `pg_dump`, pero hace que la `ENC_KEY`
> sea un requisito para poder leer cualquier dump generado después de la migración.

## Cifrado (opcional)

Los dumps contienen **datos clínicos sensibles** (PHI), por lo que en producción se
recomienda cifrarlos. La imagen estándar `postgres:17-alpine` **no incluye `openssl`**,
así que el cifrado está desactivado por defecto. Para activarlo:

1. Construye una imagen de backup con `openssl` instalado, por ejemplo un
   `Dockerfile` que extienda `postgres:17-alpine` con:
   ```dockerfile
   FROM postgres:17-alpine
   RUN apk add --no-cache openssl
   ```
2. En `compose.yml`, apunta el servicio `iye-backup` a esa imagen.
3. Define `BACKUP_CRYPT=true` y `BACKUP_PASSWORD=<frase-segura>` en `.env`.

Los archivos cifrados se guardan como `{base}_{stamp}.dump.enc` y se descifran con:

```bash
openssl enc -d -aes-256-cbc -pbkdf2 -pass pass:"<frase-segura>" \
  -in iye_YYYYMMDD_HHMMSS.dump.enc -out iye_YYYYMMDD_HHMMSS.dump
```

## Copia fuera del servidor (recomendado)

Un backup dentro del mismo disco del servidor no protege ante un fallo de disco.
Sincroniza el volumen `iye-backups` a otro destino (S3, Google Drive, otro servidor)
con `rclone` u otro mecanismo. Sin cifrado, los datos viajan en claro: cifra los dumps
antes de subirlos.

## Mejoras futuras (producción de salud real)

- **WAL archiving continuo** (`archive_mode=on`): permite restaurar a cualquier punto
  en el tiempo (RPO de minutos), el estándar para historias clínicas.
- **Alerta de fallo**: notificar por email o WhatsApp (Evolution API ya está integrada)
  cuando un backup falla.
- **Restauración de prueba automatizada** mensual.
