SET session_replication_role = 'replica';

TRUNCATE TABLE "alerta" CASCADE;
TRUNCATE TABLE "control" CASCADE;
TRUNCATE TABLE "establecimiento" CASCADE;
TRUNCATE TABLE "indicador" CASCADE;
TRUNCATE TABLE "integracion_log" CASCADE;
TRUNCATE TABLE "medicamento" CASCADE;
TRUNCATE TABLE "medicion" CASCADE;
TRUNCATE TABLE "paciente" CASCADE;
TRUNCATE TABLE "paciente_alergia" CASCADE;
TRUNCATE TABLE "paciente_medicamento" CASCADE;
TRUNCATE TABLE "paciente_patologia" CASCADE;
TRUNCATE TABLE "patologia" CASCADE;
TRUNCATE TABLE "prediccion" CASCADE;
TRUNCATE TABLE "recordatorio" CASCADE;
TRUNCATE TABLE "sincronizacion_offline" CASCADE;
TRUNCATE TABLE "usuario" CASCADE;

INSERT INTO "alerta" ("id", "paciente_id", "tipo", "descripcion", "fecha", "resuelta") VALUES
(1, 14, 'crisis_hipertensiva', 'PA 195/115 mmHg en control rutinario', '2026-01-04T13:04:01.355Z', true),
(2, 15, 'anemia', 'Hemoglobina 9.1 g/dL', '2026-03-23T01:48:51.275Z', true),
(3, 14, 'anemia', 'Hemoglobina 9.1 g/dL', '2025-08-17T02:37:31.705Z', true),
(4, 15, 'hiperglicemia', 'Glicemia ayunas 280 mg/dL', '2025-09-30T14:31:19.080Z', true),
(5, 2, 'dislipidemia_severa', 'Triglicéridos 580 mg/dL', '2026-04-30T17:04:17.468Z', false),
(6, 19, 'inasistencia', 'Inasistencia a control programado, sin respuesta a llamadas', '2025-11-24T23:07:29.532Z', true),
(7, 20, 'hipoxemia', 'SatO2 89% en reposo', '2025-09-01T00:20:04.171Z', true),
(8, 18, 'anemia', 'Anemia ferropénica severa', '2025-10-27T23:12:01.169Z', false),
(9, 9, 'inasistencia', '3 controles consecutivos no asistidos', '2025-05-30T10:34:46.017Z', false),
(10, 1, 'crisis_hipertensiva', 'PA 195/115 mmHg en control rutinario', '2026-01-09T21:21:47.108Z', true),
(11, 1, 'hiperglicemia', 'HbA1c 11.2% sin tratamiento', '2026-03-20T19:05:24.663Z', true),
(12, 7, 'dislipidemia_severa', 'LDL 195 mg/dL sin estatina', '2025-11-13T13:48:10.489Z', false),
(13, 12, 'erc_progresion', 'TFG cayó de 45 a 32 ml/min en 6 meses', '2025-09-01T00:31:52.288Z', false),
(14, 20, 'dislipidemia_severa', 'LDL 195 mg/dL sin estatina', '2026-05-22T12:50:12.131Z', true),
(15, 9, 'dm_descompensada', 'Descompensación metabólica sostenida', '2025-07-31T10:28:10.570Z', true),
(16, 4, 'hipoxemia', 'Saturación 88% requiere oxígeno suplementario', '2026-02-09T08:59:12.163Z', false),
(17, 8, 'hipoxemia', 'SatO2 89% en reposo', '2026-02-23T06:45:01.719Z', true),
(18, 5, 'crisis_hipertensiva', 'PA 195/115 mmHg en control rutinario', '2026-01-29T20:19:25.162Z', true),
(19, 1, 'crisis_hipertensiva', 'PA sistólica >180 mmHg en última medición', '2025-06-24T12:35:23.033Z', true),
(20, 13, 'inasistencia', '3 controles consecutivos no asistidos', '2025-10-07T13:38:40.660Z', false);


-- fabricate-flush


INSERT INTO "control" ("id", "paciente_id", "fecha_programada", "fecha_real", "asistio", "usuario_id") VALUES
(1, 15, '2026-08-17', NULL, NULL, 18),
(2, 20, '2026-06-28', NULL, NULL, 5),
(3, 5, '2026-06-12', NULL, NULL, 12),
(4, 18, '2026-08-06', NULL, NULL, 9),
(5, 14, '2026-02-26', '2026-02-27', true, 13),
(6, 6, '2026-02-08', NULL, false, 19),
(7, 7, '2025-12-18', NULL, false, 4),
(8, 5, '2026-03-12', '2026-03-14', true, 1),
(9, 18, '2026-02-27', '2026-02-27', true, 20),
(10, 6, '2025-09-24', NULL, false, 3),
(11, 15, '2025-11-28', '2025-11-28', true, 15),
(12, 19, '2025-07-04', NULL, false, 13),
(13, 10, '2025-12-05', NULL, false, 7),
(14, 7, '2025-07-12', NULL, false, 1),
(15, 2, '2025-11-14', '2025-11-14', true, 13),
(16, 16, '2025-09-19', NULL, false, 15),
(17, 18, '2025-12-04', '2025-12-04', true, 4),
(18, 2, '2025-09-18', '2025-09-16', true, 13),
(19, 3, '2026-04-07', '2026-04-07', true, 2),
(20, 4, '2025-10-27', NULL, false, 2);


-- fabricate-flush


INSERT INTO "establecimiento" ("id", "nombre", "tipo", "direccion", "comuna", "region") VALUES
(1, 'CESFAM Dr. Salvador Allende', 'CESFAM', 'Av. Bernardo O''Higgins 100', 'Santiago', 'Metropolitana'),
(2, 'CESFAM Padre Hurtado', 'CESFAM', 'Calle San Martín 137', 'Providencia', 'Metropolitana'),
(3, 'Hospital Las Condes', 'Hospital', 'Pasaje Los Olmos 174', 'Las Condes', 'Metropolitana'),
(4, 'CESFAM San José', 'CESFAM', 'Av. Manuel Rodríguez 211', 'Maipú', 'Metropolitana'),
(5, 'CESFAM Lo Franco', 'CESFAM', 'Calle Brasil 248', 'Puente Alto', 'Metropolitana'),
(6, 'CESFAM Nogales', 'CESFAM', 'Pasaje Independencia 285', 'Valparaíso', 'Valparaíso'),
(7, 'Hospital Viña del Mar', 'Hospital', 'Av. Bernardo O''Higgins 322', 'Viña del Mar', 'Valparaíso'),
(8, 'Consultorio Los Alerces', 'Consultorio', 'Calle San Martín 359', 'Concepción', 'Biobío'),
(9, 'Posta Rural Talcahuano', 'Posta Rural', 'Pasaje Los Olmos 396', 'Talcahuano', 'Biobío'),
(10, 'CESFAM La Florida', 'CESFAM', 'Av. Manuel Rodríguez 433', 'Temuco', 'La Araucanía'),
(11, 'CESFAM Quilicura', 'CESFAM', 'Calle Brasil 470', 'Antofagasta', 'Antofagasta'),
(12, 'Consultorio Estrella', 'Consultorio', 'Pasaje Independencia 507', 'Iquique', 'Tarapacá'),
(13, 'CECOSF Aurora', 'CECOSF', 'Av. Bernardo O''Higgins 544', 'Arica', 'Arica y Parinacota'),
(14, 'CECOSF La Pintana', 'CECOSF', 'Calle San Martín 581', 'Copiapó', 'Atacama'),
(15, 'CESFAM El Sauce', 'CESFAM', 'Pasaje Los Olmos 618', 'La Serena', 'Coquimbo'),
(16, 'Hospital Rancagua', 'Hospital', 'Av. Manuel Rodríguez 655', 'Rancagua', 'O''Higgins'),
(17, 'Hospital Talca', 'Hospital', 'Calle Brasil 692', 'Talca', 'Maule'),
(18, 'CECOSF Los Castaños', 'CECOSF', 'Pasaje Independencia 729', 'Chillán', 'Ñuble'),
(19, 'CESFAM San Pedro', 'CESFAM', 'Av. Bernardo O''Higgins 766', 'Valdivia', 'Los Ríos'),
(20, 'CESFAM Renacer', 'CESFAM', 'Calle San Martín 803', 'Puerto Montt', 'Los Lagos');


-- fabricate-flush


INSERT INTO "indicador" ("id", "nombre", "unidad", "lower", "upper", "patologia_id") VALUES
(1, 'Presión arterial sistólica', 'mmHg', 90, 139, 1),
(2, 'Presión arterial diastólica', 'mmHg', 60, 89, 1),
(3, 'Glicemia ayunas', 'mg/dL', 70, 99, 2),
(4, 'HbA1c', '%', 4, 6.5, 2),
(5, 'Glicemia capilar', 'mg/dL', 70, 140, 3),
(6, 'Colesterol total', 'mg/dL', 0, 200, 4),
(7, 'Colesterol LDL', 'mg/dL', 0, 100, 4),
(8, 'Triglicéridos', 'mg/dL', 0, 150, 4),
(9, 'Saturación O2', '%', 95, 100, 5),
(10, 'VEF1', '%', 80, 100, 5),
(11, 'PEF', 'L/min', 400, 700, 6),
(12, 'Creatinina', 'mg/dL', 0.6, 1.2, 7),
(13, 'TFG estimada', 'ml/min/1.73m2', 60, 120, 7),
(14, 'Fracción eyección VI', '%', 55, 70, 8),
(15, 'IMC', 'kg/m2', 18.5, 24.9, 11),
(16, 'Peso', 'kg', 40, 110, 11),
(17, 'TSH', 'mUI/L', 0.4, 4, 12),
(18, 'PHQ-9 score', 'puntos', 0, 4, 13),
(19, 'Hemoglobina', 'g/dL', 12, 16, 19),
(20, 'Densidad mineral ósea', 'T-score', -1, 2.5, 15);


-- fabricate-flush


INSERT INTO "integracion_log" ("id", "sistema", "tipo", "estado", "fecha", "detalle") VALUES
(1, 'WHATSAPP_API', 'envio_mensaje', 'exitoso', '2026-05-20T03:43:06.181Z', 'Respuesta OK - 200'),
(2, 'RAYEN', 'sync_ficha', 'exitoso', '2025-10-04T20:13:12.587Z', 'Sincronización completada sin diferencias'),
(3, 'FARMACIA_APS', 'despacho_receta', 'exitoso', '2026-01-10T20:45:31.859Z', 'Respuesta OK - 200'),
(4, 'ISAPRE', 'validacion_cobertura', 'exitoso', '2025-10-08T11:26:06.816Z', 'Sincronización completada sin diferencias'),
(5, 'WHATSAPP_API', 'envio_mensaje', 'exitoso', '2025-09-04T09:01:26.959Z', 'Sincronización completada sin diferencias'),
(6, 'SIDRA', 'reporte_programa', 'exitoso', '2025-09-05T10:59:56.710Z', 'Respuesta OK - 200'),
(7, 'WHATSAPP_API', 'envio_mensaje', 'exitoso', '2025-10-21T01:41:48.860Z', 'Consulta exitosa - datos actualizados'),
(8, 'SIDRA', 'sync_datos', 'exitoso', '2025-11-18T18:31:30.641Z', 'Registro creado en sistema remoto'),
(9, 'LAB_CENTRAL', 'solicitud_examen', 'exitoso', '2026-02-25T11:30:23.964Z', 'Registro creado en sistema remoto'),
(10, 'SIDRA', 'reporte_programa', 'error', '2025-08-01T13:04:55.106Z', 'SQLSTATE[23505] duplicate key'),
(11, 'SIDRA', 'sync_datos', 'exitoso', '2025-09-16T15:06:48.067Z', 'Consulta exitosa - datos actualizados'),
(12, 'LIS', 'recepcion_resultado', 'exitoso', '2025-10-22T10:13:41.556Z', 'Consulta exitosa - datos actualizados'),
(13, 'DEIS', 'envio_estadistica', 'exitoso', '2026-05-15T04:16:11.627Z', 'Sincronización completada sin diferencias'),
(14, 'FONASA', 'consulta_bono', 'exitoso', '2026-01-19T22:05:56.910Z', 'Respuesta OK - 200'),
(15, 'LAB_CENTRAL', 'solicitud_examen', 'error', '2025-05-27T23:55:46.163Z', 'HTTP 401 - Token expirado'),
(16, 'MINSAL', 'envio_rem', 'exitoso', '2025-07-14T13:09:40.326Z', 'Registro creado en sistema remoto'),
(17, 'SIDRA', 'reporte_programa', 'exitoso', '2025-06-26T17:45:22.068Z', 'Respuesta OK - 200'),
(18, 'WHATSAPP_API', 'envio_mensaje', 'exitoso', '2026-01-25T21:19:36.412Z', 'Registro creado en sistema remoto'),
(19, 'RNI', 'registro_vacuna', 'exitoso', '2025-07-05T16:17:59.832Z', 'Respuesta OK - 200'),
(20, 'RAYEN', 'sync_ficha', 'exitoso', '2026-01-15T23:58:52.461Z', 'Sincronización completada sin diferencias');


-- fabricate-flush


INSERT INTO "medicamento" ("id", "nombre") VALUES
(1, 'Losartán 50 mg'),
(2, 'Enalapril 10 mg'),
(3, 'Amlodipino 5 mg'),
(4, 'Hidroclorotiazida 25 mg'),
(5, 'Metformina 850 mg'),
(6, 'Glibenclamida 5 mg'),
(7, 'Insulina NPH'),
(8, 'Atorvastatina 20 mg'),
(9, 'Rosuvastatina 10 mg'),
(10, 'Salbutamol inhalador'),
(11, 'Budesonida inhalador'),
(12, 'Tiotropio'),
(13, 'Levotiroxina 50 mcg'),
(14, 'Sertralina 50 mg'),
(15, 'Fluoxetina 20 mg'),
(16, 'Aspirina 100 mg'),
(17, 'Furosemida 40 mg'),
(18, 'Carvedilol 6.25 mg'),
(19, 'Sulfato ferroso 200 mg'),
(20, 'Omeprazol 20 mg');


-- fabricate-flush


INSERT INTO "medicion" ("id", "paciente_id", "indicador_id", "valor", "fecha") VALUES
(1, 20, 9, 96.26, '2025-10-05'),
(2, 18, 18, 2.63, '2026-05-21'),
(3, 7, 9, 96.22, '2025-10-29'),
(4, 12, 9, 94.52, '2026-03-12'),
(5, 7, 9, 96.42, '2025-06-11'),
(6, 4, 6, 180.08, '2025-10-17'),
(7, 9, 16, 73.99, '2025-12-13'),
(8, 6, 3, 70.93, '2025-09-19'),
(9, 18, 10, 76.5, '2026-02-22'),
(10, 15, 18, 2.33, '2025-12-08'),
(11, 13, 9, 93.58, '2025-11-13'),
(12, 1, 2, 72.25, '2025-07-29'),
(13, 16, 2, 57.95, '2025-12-29'),
(14, 3, 5, 64.51, '2025-12-03'),
(15, 16, 1, 84.44, '2025-12-11'),
(16, 4, 7, 89.06, '2025-06-12'),
(17, 9, 15, 18.41, '2026-05-22'),
(18, 7, 9, 95.82, '2025-12-18'),
(19, 16, 7, 73.51, '2026-01-22'),
(20, 20, 8, 199.92, '2025-12-20');


-- fabricate-flush


INSERT INTO "paciente" ("id", "rut", "nombre", "fecha_nacimiento", "telefono", "direccion", "email", "tipo_sangre", "nombre_emergencia", "telefono_emergencia", "estado", "habitacion", "activo", "establecimiento_id") VALUES
(1, '8.485.264-7', 'Agustín Villalobos Riveros', '1989-04-24', '+56 475 879 2005', 'Av. El Roble 123', 'agustin.villalobos1@hotmail.com', 'A+', 'Luz Molina', '+56 955 938 0404', 'controlado', NULL, true, 12),
(2, '17.796.294-5', 'Horacio Flores Silva', '1989-11-19', '+56 315 639 0199', 'Pasaje Los Aromos 146', 'horacio.flores2@outlook.com', 'A-', 'Sofía Díaz', '+56 318 960 8710', 'en observación', 'C-104', true, 5),
(3, '7.247.710-7', 'Ignacio Zamora Pinto', '1958-03-25', '+56 970 968 3263', 'Calle Bernardo O''Higgins 169', 'ignacio.zamora3@yahoo.com', 'O+', 'Angélica Bizama', '+56 191 706 1508', 'en observación', NULL, true, 8),
(4, '19.871.753-7', 'Rafaela Inostroza Ulloa', '2000-05-01', '+56 230 947 9270', 'Av. Manuel Rodríguez 192', 'rafaela.inostroza4@gmail.com', 'B+', 'Eduardo Reyes', '+56 562 382 6723', 'crítico', NULL, true, 14),
(5, '24.750.919-6', 'Salvador Vera Olivera', '1946-04-11', '+56 021 232 2190', 'Pasaje Pedro de Valdivia 215', 'salvador.vera5@hotmail.com', 'A+', 'Paloma Jiménez', '+56 748 525 8637', 'controlado', NULL, true, 1),
(6, '20.630.401-6', 'Graciela Campos Soto', '2017-05-21', '+56 540 553 1788', 'Calle Las Acacias 238', 'graciela.campos6@outlook.com', 'A+', 'Rebeca Cortés', '+56 054 592 9542', 'en observación', 'C-121', true, 12),
(7, '11.636.887-10', 'Hugo Suárez Farías', '1949-05-28', '+56 485 922 1474', 'Av. El Roble 261', 'hugo.suarez7@yahoo.com', 'AB+', 'Amanda García', '+56 512 732 5924', 'controlado', 'D-140', true, 4),
(8, '17.940.913-5', 'Sebastián Donoso Alarcón', '1950-12-15', '+56 647 733 7726', 'Pasaje Los Aromos 284', 'sebastian.donoso8@gmail.com', 'B+', 'Rubén López', '+56 711 893 6739', 'en observación', NULL, true, 15),
(9, '9.010.501-1', 'Marcelo Gallegos Alfaro', '1987-01-02', '+56 503 580 6381', 'Calle Bernardo O''Higgins 307', 'marcelo.gallegos9@hotmail.com', 'O+', 'Martín Salinas', '+56 161 107 0633', 'en observación', 'B-136', true, 10),
(10, '17.572.939-1', 'Pablo Fuentes Ramírez', '1953-01-22', '+56 211 805 1407', 'Av. Manuel Rodríguez 330', 'pablo.fuentes10@outlook.com', 'O+', 'Alejandro Peña', '+56 125 468 7057', 'controlado', NULL, false, 17),
(11, '19.196.228-9', 'Alberto Segura Calderón', '1954-06-01', '+56 026 833 2799', 'Pasaje Pedro de Valdivia 353', 'alberto.segura11@yahoo.com', 'A-', 'Manuel Jara', '+56 316 727 7266', 'en observación', 'D-133', true, 18),
(12, '19.281.605-K', 'Ramón Acuña Vega', '1970-08-20', '+56 377 056 8650', 'Calle Las Acacias 376', 'ramon.acuna12@gmail.com', 'O+', 'Juana Vargas', '+56 280 642 8715', 'controlado', NULL, false, 13),
(13, '13.304.361-4', 'Víctor Herrera Loyola', '1983-09-10', '+56 318 436 4264', 'Av. El Roble 399', 'victor.herrera13@hotmail.com', 'A+', 'Rafaela Pacheco', '+56 605 791 9719', 'crítico', NULL, false, 11),
(14, '7.807.378-10', 'Rebeca Ponce Henríquez', '1960-05-12', '+56 082 652 6433', 'Pasaje Los Aromos 422', 'rebeca.ponce14@outlook.com', 'A+', 'Rosa Uribe', '+56 611 343 4523', 'crítico', NULL, true, 4),
(15, '20.374.196-10', 'Jorge Poblete Muñoz', '1960-11-01', '+56 671 389 1515', 'Calle Bernardo O''Higgins 445', 'jorge.poblete15@yahoo.com', 'A+', 'Carlos Córdoba', '+56 665 145 2040', 'en observación', NULL, false, 8),
(16, '10.442.344-8', 'Armando Espinoza Cáceres', '1996-04-28', '+56 676 766 3524', 'Av. Manuel Rodríguez 468', 'armando.espinoza16@gmail.com', 'O+', 'Patricio Cabrera', '+56 239 642 1255', 'en observación', NULL, true, 6),
(17, '24.826.605-2', 'Cristian Parra Álvarez', '1977-08-09', '+56 632 046 5555', 'Pasaje Pedro de Valdivia 491', 'cristian.parra17@hotmail.com', 'O+', 'Carolina González', '+56 651 108 8230', 'en observación', NULL, true, 18),
(18, '22.194.495-K', 'Jaime Zúñiga Araya', '1977-02-10', '+56 897 967 2174', 'Calle Las Acacias 514', 'jaime.zuniga18@outlook.com', 'B-', 'Maite Carrasco', '+56 469 686 6274', 'en observación', NULL, false, 14),
(19, '11.948.683-9', 'Susana Sánchez Arias', '1984-02-09', '+56 189 248 8688', 'Av. El Roble 537', 'susana.sanchez19@yahoo.com', 'A-', 'Enrique Navarro', '+56 661 116 7793', 'en observación', NULL, true, 12),
(20, '20.177.816-4', 'Bárbara Durán Morales', '1996-04-13', '+56 835 933 1222', 'Pasaje Los Aromos 560', 'barbara.duran20@gmail.com', 'A-', 'Francisca Montoya', '+56 230 607 0602', 'en observación', NULL, false, 12);


-- fabricate-flush


INSERT INTO "paciente_alergia" ("paciente_id", "alergia") VALUES
(1, 'Frutos secos'),
(2, 'Codeína'),
(3, 'Pelo de perro'),
(5, 'Picadura de abeja'),
(6, 'Sulfas'),
(13, 'Pelo de perro'),
(14, 'Ácaros'),
(15, 'Frutos secos'),
(15, 'Polen'),
(18, 'Codeína'),
(18, 'Polen'),
(20, 'Látex'),
(20, 'AAS'),
(9, 'Frutos secos'),
(13, 'Lactosa'),
(14, 'Mariscos'),
(9, 'Penicilina'),
(18, 'Látex'),
(2, 'Picadura de abeja'),
(6, 'Pelo de perro');


-- fabricate-flush


INSERT INTO "paciente_medicamento" ("id", "paciente_id", "medicamento_id", "dosis", "frecuencia") VALUES
(1, 1, 19, '1/2 comprimido', 'según necesidad'),
(2, 4, 8, '20 mg', 'antes del desayuno'),
(3, 6, 6, '1 comprimido', 'cada 12h'),
(4, 6, 5, '50 mg', 'cada 24h'),
(5, 7, 12, '1/2 comprimido', 'cada 12h'),
(6, 7, 10, '10 UI', 'cada 24h'),
(7, 7, 19, '1 comprimido', 'cada 6h'),
(8, 8, 19, '2 comprimidos', 'según necesidad'),
(9, 9, 5, '1 comprimido', 'cada 6h'),
(10, 13, 16, '1 puff', 'según necesidad'),
(11, 14, 10, '1 puff', 'cada 24h'),
(12, 14, 18, '1/2 comprimido', 'cada 8h'),
(13, 16, 3, '1 puff', 'en la noche'),
(14, 16, 2, '20 mg', '2 veces al día'),
(15, 18, 14, '2 puffs', 'según necesidad'),
(16, 18, 15, '2 puffs', '2 veces al día'),
(17, 16, 11, '1 puff', 'cada 6h'),
(18, 14, 12, '10 UI', 'en la noche'),
(19, 18, 8, '10 mg', 'según necesidad'),
(20, 14, 13, '50 mg', 'cada 8h');


-- fabricate-flush


INSERT INTO "paciente_patologia" ("id", "paciente_id", "patologia_id", "fecha_diagnostico", "notas", "fecha_ultimo_control") VALUES
(1, 14, 6, '2025-09-26', 'Requiere educación en autocuidado y dieta.', '2025-12-05'),
(2, 9, 7, '2022-01-21', 'Antecedente familiar de la patología en primer grado.', '2026-04-10'),
(3, 7, 19, '2024-06-06', 'Paciente refiere síntomas controlados desde el inicio del tratamiento.', '2025-10-14'),
(4, 1, 16, '2022-05-22', 'Derivado a especialista para evaluación.', '2024-12-05'),
(5, 16, 4, '2023-09-21', 'Antecedente familiar de la patología en primer grado.', NULL),
(6, 9, 11, '2024-06-09', 'Derivado a especialista para evaluación.', '2024-09-02'),
(7, 14, 19, '2021-09-21', 'Sin descompensaciones reportadas en último año.', '2023-08-12'),
(8, 16, 1, '2024-05-10', 'Diagnóstico confirmado por laboratorio.', '2025-01-29'),
(9, 18, 13, '2024-09-16', 'Iniciado tratamiento según GES.', '2026-05-02'),
(10, 6, 2, '2035-01-01', 'Paciente refiere síntomas controlados desde el inicio del tratamiento.', '2029-11-30'),
(11, 1, 19, '2023-03-16', 'Comorbilidad metabólica importante.', '2024-01-28'),
(12, 4, 4, '2022-12-02', 'Antecedente familiar de la patología en primer grado.', '2026-02-28'),
(13, 11, 14, '2021-10-20', 'Antecedente familiar de la patología en primer grado.', '2022-06-05'),
(14, 14, 9, '2024-04-07', 'Paciente refiere síntomas controlados desde el inicio del tratamiento.', '2024-10-16'),
(15, 7, 5, '2023-05-21', 'Control inicial dentro de rangos esperados.', '2023-12-20'),
(16, 8, 19, '2025-08-11', 'Paciente refiere síntomas controlados desde el inicio del tratamiento.', '2025-09-30'),
(17, 5, 14, '2026-03-30', 'Comorbilidad metabólica importante.', '2026-05-20'),
(18, 16, 2, '2021-08-21', 'Paciente refiere síntomas controlados desde el inicio del tratamiento.', '2022-07-28'),
(19, 8, 7, '2025-09-14', 'Paciente con buena adherencia al tratamiento farmacológico.', '2025-12-02'),
(20, 13, 10, '2025-01-01', 'Sin descompensaciones reportadas en último año.', '2025-06-18');


-- fabricate-flush


INSERT INTO "patologia" ("id", "nombre", "descripcion") VALUES
(1, 'Hipertensión arterial', 'Presión arterial sistémica elevada de manera persistente (≥140/90 mmHg).'),
(2, 'Diabetes Mellitus tipo 2', 'Trastorno metabólico crónico caracterizado por hiperglicemia y resistencia a la insulina.'),
(3, 'Diabetes Mellitus tipo 1', 'Diabetes autoinmune con destrucción de células beta y dependencia de insulina.'),
(4, 'Dislipidemia', 'Alteración de los lípidos sanguíneos (colesterol total, LDL, HDL, triglicéridos).'),
(5, 'EPOC', 'Enfermedad pulmonar obstructiva crónica, asociada a tabaquismo.'),
(6, 'Asma bronquial', 'Enfermedad inflamatoria crónica de la vía aérea con obstrucción reversible.'),
(7, 'Enfermedad renal crónica', 'Disminución progresiva de la función renal (TFG < 60 ml/min por ≥3 meses).'),
(8, 'Insuficiencia cardíaca', 'Incapacidad del corazón para bombear sangre adecuadamente.'),
(9, 'Cardiopatía coronaria', 'Obstrucción de arterias coronarias por placas ateromatosas.'),
(10, 'ACV isquémico', 'Accidente cerebrovascular por oclusión arterial.'),
(11, 'Obesidad', 'IMC ≥ 30 con riesgo cardiovascular y metabólico aumentado.'),
(12, 'Hipotiroidismo', 'Déficit de hormonas tiroideas con sintomatología sistémica.'),
(13, 'Depresión', 'Trastorno del ánimo con anhedonia, tristeza y alteración funcional persistente.'),
(14, 'Artrosis', 'Enfermedad degenerativa articular más frecuente en adultos mayores.'),
(15, 'Osteoporosis', 'Disminución de masa ósea con riesgo aumentado de fractura.'),
(16, 'Cáncer de mama', 'Neoplasia maligna mamaria, cubierto por GES.'),
(17, 'VIH/SIDA', 'Infección por virus de inmunodeficiencia humana, GES.'),
(18, 'Epilepsia', 'Trastorno neurológico con crisis convulsivas recurrentes.'),
(19, 'Anemia ferropénica', 'Anemia por déficit de hierro, frecuente en mujeres y adultos mayores.'),
(20, 'Gastritis crónica', 'Inflamación crónica de la mucosa gástrica.');


-- fabricate-flush


INSERT INTO "prediccion" ("id", "paciente_id", "riesgo_descompensacion", "riesgo_inasistencia", "cluster", "fecha") VALUES
(1, 13, 0.137, 0.45, 'bajo_riesgo_no_adherente', '2025-07-23T17:03:06.668Z'),
(2, 2, 0.249, 0.373, 'bajo_riesgo_no_adherente', '2026-05-03T10:10:55.852Z'),
(3, 11, 0.328, 0.28, 'moderado_estable', '2025-10-14T15:31:45.518Z'),
(4, 11, 0.396, 0.483, 'moderado_estable', '2025-08-07T14:51:21.301Z'),
(5, 3, 0.142, 0.589, 'bajo_riesgo_no_adherente', '2026-04-25T02:46:05.199Z'),
(6, 10, 0.05, 0.604, 'bajo_riesgo_no_adherente', '2026-02-24T03:38:56.808Z'),
(7, 6, 0.236, 0.384, 'bajo_riesgo_no_adherente', '2025-07-31T14:18:01.419Z'),
(8, 9, 0.588, 0.65, 'moderado_descompensado', '2025-11-06T05:20:33.046Z'),
(9, 3, 0.072, 0.589, 'bajo_riesgo_no_adherente', '2025-09-01T01:44:58.577Z'),
(10, 9, 0.485, 0.614, 'moderado_descompensado', '2025-09-17T05:43:18.964Z'),
(11, 4, 0.163, 0.088, 'bajo_riesgo_adherente', '2026-03-01T01:17:44.778Z'),
(12, 6, 0.359, 0.072, 'moderado_estable', '2025-08-09T13:16:19.914Z'),
(13, 1, 0.334, 0.725, 'moderado_descompensado', '2026-05-21T06:47:43.002Z'),
(14, 3, 0.05, 0.223, 'bajo_riesgo_adherente', '2025-08-15T03:11:31.229Z'),
(15, 8, 0.377, 0.244, 'moderado_estable', '2025-10-30T00:39:08.021Z'),
(16, 20, 0.203, 0.376, 'bajo_riesgo_no_adherente', '2025-07-22T17:18:34.039Z'),
(17, 7, 0.44, 0.205, 'moderado_estable', '2025-10-26T12:30:15.741Z'),
(18, 10, 0.097, 0.164, 'bajo_riesgo_adherente', '2025-11-27T08:35:15.586Z'),
(19, 13, 0.247, 0.091, 'bajo_riesgo_adherente', '2026-01-04T05:28:12.420Z'),
(20, 17, 0.177, 0.652, 'bajo_riesgo_no_adherente', '2025-12-06T19:15:08.352Z');


-- fabricate-flush


INSERT INTO "recordatorio" ("id", "paciente_id", "tipo", "canal", "fecha_envio", "estado") VALUES
(1, 14, 'control_programado', 'whatsapp', '2026-02-11T21:59:48.596Z', 'leido'),
(2, 14, 'control_programado', 'whatsapp', '2026-02-18T22:19:29.274Z', 'leido'),
(3, 14, 'control_programado', 'llamada', '2026-02-24T22:51:54.823Z', 'contestada'),
(4, 6, 'control_programado', 'llamada', '2026-01-24T23:50:52.136Z', 'contestada'),
(5, 6, 'control_programado', 'whatsapp', '2026-02-01T01:56:48.201Z', 'leido'),
(6, 6, 'control_programado', 'sms', '2026-02-07T02:08:01.312Z', 'entregado'),
(7, 7, 'control_programado', 'whatsapp', '2025-12-04T02:29:21.060Z', 'leido'),
(8, 7, 'control_programado', 'whatsapp', '2025-12-11T01:59:01.605Z', 'entregado'),
(9, 7, 'control_programado', 'whatsapp', '2025-12-16T22:41:49.622Z', 'leido'),
(10, 5, 'control_programado', 'sms', '2026-02-25T21:28:40.429Z', 'entregado'),
(11, 5, 'control_programado', 'whatsapp', '2026-03-05T00:12:39.489Z', 'no_entregado'),
(12, 5, 'control_programado', 'sms', '2026-03-10T23:19:58.638Z', 'entregado'),
(13, 18, 'control_programado', 'whatsapp', '2026-02-12T21:37:23.106Z', 'leido'),
(14, 18, 'control_programado', 'sms', '2026-02-20T00:50:01.902Z', 'entregado'),
(15, 18, 'control_programado', 'llamada', '2026-02-26T02:20:06.431Z', 'buzon'),
(16, 6, 'control_programado', 'sms', '2025-09-09T22:06:55.390Z', 'entregado'),
(17, 6, 'control_programado', 'email', '2025-09-16T23:50:17.293Z', 'entregado'),
(18, 6, 'control_programado', 'sms', '2025-09-23T00:45:28.558Z', 'entregado'),
(19, 15, 'control_programado', 'whatsapp', '2025-11-14T02:23:19.341Z', 'leido'),
(20, 15, 'control_programado', 'whatsapp', '2025-11-20T23:26:25.312Z', 'leido');


-- fabricate-flush


INSERT INTO "sincronizacion_offline" ("id", "entidad", "entidad_id", "operacion", "fecha", "sincronizado") VALUES
(1, 'control', 1, 'INSERT', '2025-11-28T08:47:31.497Z', false),
(2, 'medicion', 13, 'INSERT', '2026-03-03T20:10:11.942Z', true),
(3, 'paciente_medicamento', 9, 'INSERT', '2025-09-19T17:40:57.403Z', true),
(4, 'medicion', 4, 'UPDATE', '2025-07-10T20:59:14.059Z', false),
(5, 'medicion', 12, 'INSERT', '2026-04-07T12:22:24.154Z', true),
(6, 'recordatorio', 2, 'INSERT', '2026-04-29T03:51:07.763Z', true),
(7, 'recordatorio', 10, 'UPDATE', '2026-02-09T20:10:01.380Z', true),
(8, 'medicion', 2, 'INSERT', '2026-04-12T14:33:51.990Z', true),
(9, 'medicion', 6, 'INSERT', '2026-01-31T01:56:29.611Z', true),
(10, 'control', 6, 'UPDATE', '2025-10-22T04:33:28.209Z', true),
(11, 'control', 15, 'INSERT', '2025-12-07T20:19:09.492Z', true),
(12, 'medicion', 9, 'UPDATE', '2025-10-20T18:32:57.189Z', true),
(13, 'medicion', 9, 'INSERT', '2025-08-21T10:59:05.670Z', true),
(14, 'recordatorio', 5, 'INSERT', '2025-11-07T19:00:37.758Z', false),
(15, 'control', 12, 'INSERT', '2025-12-01T23:15:46.313Z', true),
(16, 'control', 11, 'INSERT', '2025-08-12T02:07:51.576Z', true),
(17, 'medicion', 9, 'INSERT', '2025-08-10T10:11:51.846Z', true),
(18, 'medicion', 19, 'INSERT', '2026-03-20T02:53:53.539Z', true),
(19, 'control', 11, 'UPDATE', '2025-10-12T04:44:25.922Z', true),
(20, 'control', 6, 'INSERT', '2026-04-12T22:20:11.181Z', true);


-- fabricate-flush


INSERT INTO "usuario" ("id", "nombre", "email", "password", "rol", "establecimiento_id") VALUES
(1, 'Nicolás Ramos', 'nicolas.ramos1@outlook.com', '$2b$10$pf3o3cbzhesjcbb9apt6', 'kinesiologo', 9),
(2, 'Marcelo San Martín', 'marcelo.sanmartin2@minsal.cl', '$2b$10$ltqrinrofjx3gdazn0xd', 'medico', 6),
(3, 'Patricia Velásquez', 'patricia.velasquez3@redsalud.cl', '$2b$10$mtjz20rwknfcmsop14wj', 'matrona', 4),
(4, 'Joaquín Fernández', 'joaquin.fernandez4@gmail.com', '$2b$10$3iegels7dlfv8bcsvdbn', 'enfermera', 2),
(5, 'Roberto Cisternas', 'roberto.cisternas5@outlook.com', '$2b$10$0xfyygd8zggifyqp4f2c', 'enfermera', 19),
(6, 'Pilar Valdés', 'pilar.valdes6@minsal.cl', '$2b$10$qdi0qrsbx50xtjaeuacc', 'enfermera', 15),
(7, 'Álvaro Escobar', 'alvaro.escobar7@redsalud.cl', '$2b$10$igys3bjs5byzy8jrv8ps', 'enfermera', 16),
(8, 'Luciano Calderón', 'luciano.calderon8@gmail.com', '$2b$10$easlo2wikmc7c4okal54', 'enfermera', 19),
(9, 'Elisa Ríos', 'elisa.rios9@outlook.com', '$2b$10$ujk8ht2u5xhkoabkqf51', 'kinesiologo', 17),
(10, 'Susana Arias', 'susana.arias10@minsal.cl', '$2b$10$upxlfazja1wqiogz7wcx', 'nutricionista', 14),
(11, 'Magdalena Gutiérrez', 'magdalena.gutierrez11@redsalud.cl', '$2b$10$zabxdvstg7gyxa2i70bm', 'medico', 20),
(12, 'Natalia Monsalve', 'natalia.monsalve12@gmail.com', '$2b$10$btjgfisncjdd6s1sp2mg', 'enfermera', 20),
(13, 'Eliana Maldonado', 'eliana.maldonado13@outlook.com', '$2b$10$rq5ut6zo1wwpflruwqts', 'enfermera', 11),
(14, 'Macarena Osorio', 'macarena.osorio14@minsal.cl', '$2b$10$ek275isak70orht2wr9y', 'TENS', 11),
(15, 'Diego Zamora', 'diego.zamora15@redsalud.cl', '$2b$10$j0v3fcd56fba3674pb4a', 'medico', 2),
(16, 'Paulina Carmona', 'paulina.carmona16@gmail.com', '$2b$10$4s4ix5n458to2331gmsk', 'TENS', 11),
(17, 'Cristian Torres', 'cristian.torres17@outlook.com', '$2b$10$4n7a61t1qssemjghh759', 'medico', 7),
(18, 'Francisco Vargas', 'francisco.vargas18@minsal.cl', '$2b$10$gt2l8bvr9baz5w3vxtx3', 'TENS', 5),
(19, 'Pablo Peña', 'pablo.pena19@redsalud.cl', '$2b$10$4dtwlmhd2epgzr9gkhu5', 'matrona', 17),
(20, 'Tomás Herrera', 'tomas.herrera20@gmail.com', '$2b$10$c1659smwpbjxl24pxm0w', 'TENS', 9);


-- fabricate-flush


SET session_replication_role = 'origin';
