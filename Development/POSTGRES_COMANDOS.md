# Comandos para levantar PostgreSQL

El archivo `../Database/scheme.sql` se ejecuta automaticamente al inicializar la base en un volumen vacio.

## 1) Levantar el contenedor

docker compose -f compose.yml up -d

## 2) Ver estado

docker compose -f compose.yml ps

docker compose -f compose.yml logs -f iye-db

## 3) Entrar a psql dentro del contenedor

docker exec -it iye-db psql -U "${DB_USER:-postgres}" -d "${DB_NAME:-iye}"

## 4) Detener

docker compose -f compose.yml down

## 5) Detener y eliminar datos (volumen)

docker compose -f compose.yml down -v

## 6) Reinicializar y volver a ejecutar el script SQL

docker compose -f compose.yml down -v

docker compose -f compose.yml up -d
