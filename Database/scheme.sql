CREATE TABLE "establecimiento" (
  "id" int PRIMARY KEY,
  "nombre" varchar,
  "tipo" varchar,
  "direccion" varchar,
  "comuna" varchar,
  "region" varchar
);

CREATE TABLE "usuario" (
  "id" int PRIMARY KEY,
  "nombre" varchar,
  "email" varchar,
  "password" varchar,
  "rol" varchar,
  "establecimiento_id" int
);

CREATE TABLE "paciente" (
  "id" int PRIMARY KEY,
  "rut" varchar,
  "nombre" varchar,
  "fecha_nacimiento" date,
  "telefono" varchar,
  "direccion" varchar,
  "email" varchar,
  "tipo_sangre" varchar,
  "nombre_emergencia" varchar,
  "telefono_emergencia" varchar,
  "estado" varchar,
  "habitacion" varchar,
  "activo" boolean NOT NULL DEFAULT true,
  "establecimiento_id" int
);

CREATE TABLE "paciente_alergia" (
  "paciente_id" int NOT NULL,
  "alergia" varchar NOT NULL
);


CREATE TABLE "patologia" (
  "id" int PRIMARY KEY,
  "nombre" varchar,
  "descripcion" text
);

CREATE TABLE "paciente_patologia" (
  "id" int PRIMARY KEY,
  "paciente_id" int,
  "patologia_id" int,
  "fecha_diagnostico" date,
  "notas" text,
  "fecha_ultimo_control" date
);

CREATE TABLE "control" (
  "id" int PRIMARY KEY,
  "paciente_id" int,
  "fecha_programada" date,
  "fecha_real" date,
  "asistio" boolean,
  "usuario_id" int
);

CREATE TABLE "indicador" (
  "id" int PRIMARY KEY,
  "nombre" varchar,
  "unidad" varchar,
  "lower" decimal,
  "upper" decimal,
  "patologia_id" int
);


CREATE TABLE "medicion" (
  "id" int PRIMARY KEY,
  "paciente_id" int,
  "indicador_id" int,
  "valor" decimal,
  "fecha" date
);

CREATE TABLE "alerta" (
  "id" int PRIMARY KEY,
  "paciente_id" int,
  "tipo" varchar,
  "descripcion" text,
  "fecha" timestamp,
  "resuelta" boolean
);

CREATE TABLE "recordatorio" (
  "id" int PRIMARY KEY,
  "paciente_id" int,
  "tipo" varchar,
  "canal" varchar,
  "fecha_envio" timestamp,
  "estado" varchar
);

CREATE TABLE "medicamento" (
  "id" int PRIMARY KEY,
  "nombre" varchar
);

CREATE TABLE "paciente_medicamento" (
  "id" int PRIMARY KEY,
  "paciente_id" int,
  "medicamento_id" int,
  "dosis" varchar,
  "frecuencia" varchar
);

CREATE TABLE "prediccion" (
  "id" int PRIMARY KEY,
  "paciente_id" int,
  "riesgo_descompensacion" decimal,
  "riesgo_inasistencia" decimal,
  "cluster" varchar,
  "fecha" timestamp
);

CREATE TABLE "sincronizacion_offline" (
  "id" int PRIMARY KEY,
  "entidad" varchar,
  "entidad_id" int,
  "operacion" varchar,
  "fecha" timestamp,
  "sincronizado" boolean
);

CREATE TABLE "integracion_log" (
  "id" int PRIMARY KEY,
  "sistema" varchar,
  "tipo" varchar,
  "estado" varchar,
  "fecha" timestamp,
  "detalle" text
);

ALTER TABLE "usuario" ADD FOREIGN KEY ("establecimiento_id") REFERENCES "establecimiento" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "paciente" ADD FOREIGN KEY ("establecimiento_id") REFERENCES "establecimiento" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "paciente_patologia" ADD FOREIGN KEY ("paciente_id") REFERENCES "paciente" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "paciente_patologia" ADD FOREIGN KEY ("patologia_id") REFERENCES "patologia" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "control" ADD FOREIGN KEY ("paciente_id") REFERENCES "paciente" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "control" ADD FOREIGN KEY ("usuario_id") REFERENCES "usuario" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "medicion" ADD FOREIGN KEY ("paciente_id") REFERENCES "paciente" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "medicion" ADD FOREIGN KEY ("indicador_id") REFERENCES "indicador" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "alerta" ADD FOREIGN KEY ("paciente_id") REFERENCES "paciente" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "recordatorio" ADD FOREIGN KEY ("paciente_id") REFERENCES "paciente" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "paciente_medicamento" ADD FOREIGN KEY ("paciente_id") REFERENCES "paciente" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "paciente_medicamento" ADD FOREIGN KEY ("medicamento_id") REFERENCES "medicamento" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "prediccion" ADD FOREIGN KEY ("paciente_id") REFERENCES "paciente" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "paciente_alergia" ADD FOREIGN KEY ("paciente_id") REFERENCES "paciente" ("id") DEFERRABLE INITIALLY IMMEDIATE;

ALTER TABLE "indicador" ADD FOREIGN KEY ("patologia_id") REFERENCES "patologia" ("id") DEFERRABLE INITIALLY IMMEDIATE;
