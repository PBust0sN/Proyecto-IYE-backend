# Proyecto-IYE

> [!IMPORTANT]
> Requerimientos Funcionales y No Funcionales.
> https://docs.google.com/document/d/1dQAGP7NCzi1SXdNiB8yUY1qqnKRwD5wTsfc4YD5L3zw/edit?tab=t.0
> 
> https://docs.google.com/spreadsheets/d/1QQ-ydGqLdNEoDRQm_WkOLupYOAwdG_v6ct51CUr_FJU/edit?usp=sharing
>
> Repositorio de frontend
> 
> https://github.com/MateoVL/Proyecto-IyE-Front

## Backup de la base de datos

El respaldo automático de PostgreSQL (dumps diarios programados, rotación y guía de
restauración) está documentado en
[`Development/BACKUP_README.md`](Development/BACKUP_README.md).

## Cifrado de datos sensibles

Los datos personales y clínicos de pacientes (RUT, nombre, teléfono, dirección,
email, tipo de sangre, contacto de emergencia y alergias) se cifran en reposo con
**AES-256-GCM**. Detalles, configuración de la clave `ENC_KEY`, migración de datos
existentes y tests en [`Development/ENCRYPTION_README.md`](Development/ENCRYPTION_README.md).
