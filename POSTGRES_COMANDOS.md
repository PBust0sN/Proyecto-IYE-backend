# Comandos para levantar PostgreSQL

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
