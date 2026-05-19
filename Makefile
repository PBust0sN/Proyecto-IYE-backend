# ══════════════════════════════════════════════════════════════
#  IYE – Makefile de desarrollo
#  Linux / macOS : make nativo
#  Windows       : requiere Git Bash  →  https://gitforwindows.org/
#                  (ejecutar desde Git Bash, no desde cmd/PowerShell)
# ══════════════════════════════════════════════════════════════

# ── Detección de sistema operativo ────────────────────────────
ifeq ($(OS),Windows_NT)
    DETECTED_OS := Windows
    GRADLEW_CMD := gradlew.bat
    SHELL       := bash.exe
else
    DETECTED_OS := Linux
    GRADLEW_CMD := ./gradlew
    SHELL       := /bin/bash
endif
.SHELLFLAGS := -euc

# ── Rutas ──────────────────────────────────────────────────────
COMPOSE_DIR := Development
BACKEND_DIR := Backend/cronicotrak
ENV_FILE    := $(COMPOSE_DIR)/.env

# ── Colores (compatibles con bash en ambos OS) ─────────────────
GREEN  := \033[0;32m
YELLOW := \033[1;33m
CYAN   := \033[0;36m
RED    := \033[0;31m
RESET  := \033[0m

.PHONY: help db db-stop backend start stop clean logs ps env-check _check-env _wait-db _gradlew-perms

# ──────────────────────────────────────────────────────────────
## Muestra esta ayuda
# ──────────────────────────────────────────────────────────────
help:
	@printf "\n"
	@printf "$(CYAN)╔══════════════════════════════════════╗\n$(RESET)"
	@printf "$(CYAN)║     IYE – Comandos disponibles       ║\n$(RESET)"
	@printf "$(CYAN)║     OS detectado: %-18s║\n$(RESET)" "$(DETECTED_OS)"
	@printf "$(CYAN)╚══════════════════════════════════════╝\n$(RESET)"
	@printf "\n"
	@printf "  $(GREEN)make start$(RESET)    → Levanta DB + backend (en orden)\n"
	@printf "  $(GREEN)make stop$(RESET)     → Detiene todo\n"
	@printf "  $(GREEN)make db$(RESET)       → Solo levanta PostgreSQL\n"
	@printf "  $(GREEN)make db-stop$(RESET)  → Solo detiene PostgreSQL\n"
	@printf "  $(GREEN)make backend$(RESET)  → Solo levanta Spring Boot\n"
	@printf "  $(GREEN)make logs$(RESET)     → Logs de Postgres en tiempo real\n"
	@printf "  $(GREEN)make ps$(RESET)       → Estado de los contenedores\n"
	@printf "  $(GREEN)make env-check$(RESET)→ Muestra las variables de entorno cargadas\n"
	@printf "  $(GREEN)make clean$(RESET)    → Limpia build del backend\n"
	@printf "\n"

# ──────────────────────────────────────────────────────────────
## Levanta PostgreSQL en segundo plano
# ──────────────────────────────────────────────────────────────
db: _check-env
	@printf "$(YELLOW)▶ Levantando PostgreSQL...$(RESET)\n"
	docker compose -f $(COMPOSE_DIR)/compose.yml up -d
	@printf "$(GREEN)✔ PostgreSQL listo$(RESET)\n"

# ──────────────────────────────────────────────────────────────
## Detiene el contenedor de PostgreSQL
# ──────────────────────────────────────────────────────────────
db-stop:
	@printf "$(YELLOW)▶ Deteniendo PostgreSQL...$(RESET)\n"
	docker compose -f $(COMPOSE_DIR)/compose.yml down
	@printf "$(GREEN)✔ PostgreSQL detenido$(RESET)\n"

# ──────────────────────────────────────────────────────────────
## Levanta el backend Spring Boot
##   → espera que DB esté healthy
##   → carga el .env de Development/ si existe
# ──────────────────────────────────────────────────────────────
backend: _check-env _wait-db _gradlew-perms
	@printf "$(YELLOW)▶ Iniciando Spring Boot [$(DETECTED_OS)]...$(RESET)\n"
	@if [ -f "$(ENV_FILE)" ]; then \
		printf "$(CYAN)  ↳ Cargando variables desde $(ENV_FILE)$(RESET)\n"; \
		set -a && . "$(CURDIR)/$(ENV_FILE)" && set +a \
		&& cd "$(BACKEND_DIR)" && $(GRADLEW_CMD) bootRun; \
	else \
		printf "$(YELLOW)  ↳ $(ENV_FILE) no encontrado, usando defaults de application.yaml$(RESET)\n"; \
		cd "$(BACKEND_DIR)" && $(GRADLEW_CMD) bootRun; \
	fi

# ──────────────────────────────────────────────────────────────
## Levanta DB + backend en orden
# ──────────────────────────────────────────────────────────────
start: db backend

# ──────────────────────────────────────────────────────────────
## Detiene todo
# ──────────────────────────────────────────────────────────────
stop: db-stop
	@printf "$(GREEN)✔ Todo detenido$(RESET)\n"

# ──────────────────────────────────────────────────────────────
## Muestra los logs de Postgres en tiempo real
# ──────────────────────────────────────────────────────────────
logs:
	docker compose -f $(COMPOSE_DIR)/compose.yml logs -f iye-db

# ──────────────────────────────────────────────────────────────
## Estado de los contenedores
# ──────────────────────────────────────────────────────────────
ps:
	docker compose -f $(COMPOSE_DIR)/compose.yml ps

# ──────────────────────────────────────────────────────────────
## Muestra las variables que Spring recibirá desde el .env
# ──────────────────────────────────────────────────────────────
env-check:
	@if [ -f "$(ENV_FILE)" ]; then \
		printf "$(CYAN)Variables en $(ENV_FILE):$(RESET)\n"; \
		grep -v '^\s*#' "$(ENV_FILE)" | grep -v '^\s*$$' | while IFS= read -r line; do \
			key=$$(echo "$$line" | cut -d= -f1); \
			printf "  $(GREEN)$$key$(RESET) = $$(echo "$$line" | cut -d= -f2-)\n"; \
		done; \
	else \
		printf "$(RED)✘ No existe $(ENV_FILE). Copia $(ENV_FILE).example y complétalo.$(RESET)\n"; \
	fi

# ──────────────────────────────────────────────────────────────
## Limpia los artefactos de build del backend
# ──────────────────────────────────────────────────────────────
clean:
	@printf "$(YELLOW)▶ Limpiando build...$(RESET)\n"
	cd "$(BACKEND_DIR)" && $(GRADLEW_CMD) clean
	@printf "$(GREEN)✔ Build limpio$(RESET)\n"

# ── Internos ───────────────────────────────────────────────────

# Verifica que exista el archivo .env antes de continuar
_check-env:
	@if [ ! -f "$(ENV_FILE)" ]; then \
		printf "\n$(RED)╔══════════════════════════════════════════════════╗$(RESET)\n"; \
		printf "$(RED)║  ✘  Falta el archivo de configuración           ║$(RESET)\n"; \
		printf "$(RED)╚══════════════════════════════════════════════════╝$(RESET)\n"; \
		printf "  No se encontró: $(YELLOW)$(ENV_FILE)$(RESET)\n"; \
		printf "  Crea el archivo copiando el ejemplo:\n"; \
		printf "  $(CYAN)cp $(COMPOSE_DIR)/.env.example $(ENV_FILE)$(RESET)\n"; \
		printf "  Luego editá las variables y volvé a ejecutar el comando.\n\n"; \
		exit 1; \
	fi

# Espera a que el healthcheck de Postgres pase a "healthy"
_wait-db:
	@printf "$(YELLOW)⏳ Esperando a que PostgreSQL esté healthy...$(RESET)\n"
	@until docker inspect --format='{{.State.Health.Status}}' iye-db 2>/dev/null | grep -q "healthy"; do \
		printf "."; sleep 2; \
	done
	@printf "\n$(GREEN)✔ PostgreSQL healthy$(RESET)\n"

# En Linux/macOS garantiza que gradlew sea ejecutable (no aplica en Windows)
_gradlew-perms:
ifeq ($(DETECTED_OS),Linux)
	@chmod +x "$(BACKEND_DIR)/gradlew" 2>/dev/null || true
endif
