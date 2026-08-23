#!/bin/sh
# ══════════════════════════════════════════════════════════════
#  IYE – Entrypoint del contenedor `iye-backup`
#
#  Escribe la expresión cron (BACKUP_CRON) en la crontab de root
#  y deja el demonio crond en primer plano. La salida de cada
#  backup se loguea en /backups/backup.log (volumen iye-backups).
# ══════════════════════════════════════════════════════════════

set -eu

CRON_SCHEDULE=${BACKUP_CRON:-"0 3 * * *"}
LOG_FILE=${BACKUP_LOG:-/backups/backup.log}

mkdir -p /backups

echo "${CRON_SCHEDULE} /bin/sh /backup.sh >> ${LOG_FILE} 2>&1" > /etc/crontabs/root
echo "[backup-entrypoint] programación configurada: '${CRON_SCHEDULE}' → /backup.sh (log: ${LOG_FILE})"

exec crond -f -l 8
