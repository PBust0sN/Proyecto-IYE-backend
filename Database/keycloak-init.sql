-- ═══════════════════════════════════════════════════════════════
--  Crea la base de datos de Keycloak dentro del mismo Postgres
--  Este script corre al inicializar el contenedor (solo 1ra vez)
-- ═══════════════════════════════════════════════════════════════

SELECT 'CREATE DATABASE keycloak'
WHERE NOT EXISTS (
    SELECT FROM pg_database WHERE datname = 'keycloak'
)\gexec
