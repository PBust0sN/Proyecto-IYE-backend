#!/bin/sh
# ══════════════════════════════════════════════════════════════
#  IYE – Backup de PostgreSQL (iye + keycloak)
#
#  Se ejecuta automáticamente por cron dentro del contenedor
#  `iye-backup` definido en compose.yml, o manualmente con:
#
#    docker compose -f Development/compose.yml run --rm --no-deps \
#      --entrypoint /bin/sh iye-backup /backup.sh
#
#  Genera dumps en formato custom (pg_dump -Fc, comprimido) de cada
#  base y rota conservando solo las últimas BACKUP_RETENTION copias.
# ══════════════════════════════════════════════════════════════

set -eu

# ── Variables (todas con defaults seguros) ────────────────────
DB_HOST=${DB_HOST:-iye-db}
DB_PORT=${DB_PORT:-5432}
DB_USER=${DB_USER:-postgres}
DB_PASSWORD=${DB_PASSWORD:-admin}
BACKUP_DATABASES=${BACKUP_DATABASES:-"iye keycloak"}
BACKUP_DIR=${BACKUP_DIR:-/backups}
BACKUP_RETENTION=${BACKUP_RETENTION:-30}
BACKUP_CRYPT=${BACKUP_CRYPT:-false}
BACKUP_PASSWORD=${BACKUP_PASSWORD:-}

STAMP=$(date +%Y%m%d_%H%M%S)
export PGPASSWORD="${DB_PASSWORD}"

if ! command -v pg_dump >/dev/null 2>&1; then
  echo "[backup] ✗ pg_dump no disponible. Abortando."
  exit 1
fi

echo "[backup] iniciando backup de: ${BACKUP_DATABASES}"

for db in ${BACKUP_DATABASES}; do
  FILE="${BACKUP_DIR}/${db}_${STAMP}.dump"
  echo "[backup] dump de '${db}' → ${FILE}"

  pg_dump -h "${DB_HOST}" -p "${DB_PORT}" -U "${DB_USER}" -d "${db}" -Fc -f "${FILE}"

  if [ ! -s "${FILE}" ]; then
    echo "[backup] ✗ dump de '${db}' vacío o falló. Eliminando archivo parcial."
    rm -f -- "${FILE}"
    exit 1
  fi

  if [ "${BACKUP_CRYPT}" = "true" ]; then
    if command -v openssl >/dev/null 2>&1 && [ -n "${BACKUP_PASSWORD}" ]; then
      ENC_FILE="${FILE}.enc"
      openssl enc -aes-256-cbc -salt -pbkdf2 -pass pass:"${BACKUP_PASSWORD}" \
        -in "${FILE}" -out "${ENC_FILE}"
      rm -f -- "${FILE}"
      FILE="${ENC_FILE}"
      echo "[backup] ✓ cifrado AES-256 aplicado (${FILE})"
    else
      echo "[backup] ⚠ BACKUP_CRYPT=true pero la imagen no trae openssl o BACKUP_PASSWORD está vacío. Se guarda SIN cifrar."
    fi
  fi

  echo "[backup] ✓ '${db}' respaldado (${FILE})"
done

# ── Rotación: conserva solo las últimas BACKUP_RETENTION por base ──
echo "[backup] rotación: conservando las últimas ${BACKUP_RETENTION} copias por base"
for db in ${BACKUP_DATABASES}; do
  n=0
  for f in $(ls -1t "${BACKUP_DIR}/${db}_"* 2>/dev/null || true); do
    n=$((n + 1))
    if [ "${n}" -gt "${BACKUP_RETENTION}" ]; then
      echo "[backup]   eliminando ${f}"
      rm -f -- "${f}"
    fi
  done
done

echo "[backup] ✔ backup completado (${STAMP})"
