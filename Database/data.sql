SET session_replication_role = 'replica';

TRUNCATE TABLE "alerta" CASCADE;
TRUNCATE TABLE "control" CASCADE;
TRUNCATE TABLE "establecimiento" CASCADE;
TRUNCATE TABLE "indicador" CASCADE;
TRUNCATE TABLE "integracion_log" CASCADE;
TRUNCATE TABLE "medicamento" CASCADE;
TRUNCATE TABLE "medicion" CASCADE;
TRUNCATE TABLE "paciente" CASCADE;
TRUNCATE TABLE "paciente_medicamento" CASCADE;
TRUNCATE TABLE "paciente_patologia" CASCADE;
TRUNCATE TABLE "patologia" CASCADE;
TRUNCATE TABLE "prediccion" CASCADE;
TRUNCATE TABLE "recordatorio" CASCADE;
TRUNCATE TABLE "sincronizacion_offline" CASCADE;
TRUNCATE TABLE "usuario" CASCADE;





INSERT INTO "establecimiento" ("id", "nombre", "tipo", "direccion", "comuna", "region") VALUES
(1, 'CECOSF El Mirador General Lagos', 'CECOSF', 'Irarrázaval 1962', 'General Lagos', 'Arica y Parinacota'),
(2, 'SAPU Arica', 'SAPU', 'Errázuriz 1186', 'Arica', 'Arica y Parinacota'),
(3, 'Hospital San Borja Arriarán Camarones', 'Hospital', 'Irarrázaval 1971', 'Camarones', 'Arica y Parinacota'),
(4, 'CESFAM Los Volcanes Pica', 'CESFAM', 'San Martín 1134', 'Pica', 'Tarapacá'),
(5, 'Hospital San Borja Arriarán Huara', 'Hospital', 'Arturo Prat 3295', 'Huara', 'Tarapacá'),
(6, 'CESFAM Los Volcanes Huara', 'CESFAM', 'Eleuterio Ramírez 2223', 'Huara', 'Tarapacá'),
(7, 'CECOSF El Mirador Mejillones', 'CECOSF', 'Freire 2525', 'Mejillones', 'Antofagasta'),
(8, 'CESFAM Nueva Esperanza Taltal', 'CESFAM', 'Freire 1896', 'Taltal', 'Antofagasta'),
(9, 'SAPU Calama', 'SAPU', 'Eleuterio Ramírez 3845', 'Calama', 'Antofagasta'),
(10, 'Posta Rural Los Maquis', 'Posta Rural', 'Apoquindo 1363', 'Taltal', 'Antofagasta'),
(11, 'CESFAM Pablo Neruda Tocopilla', 'CESFAM', 'Prat 4570', 'Tocopilla', 'Antofagasta'),
(12, 'CESFAM Nueva Esperanza Tocopilla', 'CESFAM', 'OHiggins 3080', 'Tocopilla', 'Antofagasta'),
(13, 'SAPU Chañaral', 'SAPU', 'Bulnes 1862', 'Chañaral', 'Atacama'),
(14, 'SAPU Vallenar', 'SAPU', 'OHiggins 1510', 'Vallenar', 'Atacama'),
(15, 'CESFAM Dr. Alfonso Leng Caldera', 'CESFAM', 'Colón 3042', 'Caldera', 'Atacama'),
(16, 'Posta Rural San Pedro', 'Posta Rural', 'San Martín 2162', 'Illapel', 'Coquimbo'),
(17, 'Consultorio Vicuña', 'Consultorio', 'Colón 1578', 'Vicuña', 'Coquimbo'),
(18, 'CECOSF Villa Nueva Vicuña', 'CECOSF', 'Arturo Prat 3642', 'Vicuña', 'Coquimbo'),
(19, 'CECOSF Los Copihues Illapel', 'CECOSF', 'Las Acacias 2478', 'Illapel', 'Coquimbo'),
(20, 'CESFAM Dr. Luis Ferrada Coquimbo', 'CESFAM', 'Pedro de Valdivia 308', 'Coquimbo', 'Coquimbo');

INSERT INTO "indicador" ("id", "nombre", "unidad") VALUES
(1, 'Presión Arterial Sistólica', 'mmHg'),
(2, 'Presión Arterial Diastólica', 'mmHg'),
(3, 'Glicemia en Ayunas', 'mg/dL'),
(4, 'Hemoglobina Glicosilada', '%'),
(5, 'Colesterol Total', 'mg/dL'),
(6, 'Colesterol LDL', 'mg/dL'),
(7, 'Colesterol HDL', 'mg/dL'),
(8, 'Triglicéridos', 'mg/dL'),
(9, 'Índice de Masa Corporal', 'kg/m²'),
(10, 'Peso Corporal', 'kg'),
(11, 'Talla', 'cm'),
(12, 'Perímetro Abdominal', 'cm'),
(13, 'Frecuencia Cardíaca', 'lpm'),
(14, 'Saturación de Oxígeno', '%'),
(15, 'Creatinina Sérica', 'mg/dL'),
(16, 'Tasa de Filtración Glomerular', 'mL/min/1.73m²'),
(17, 'Microalbuminuria', 'mg/g'),
(18, 'TSH', 'μUI/mL'),
(19, 'Hemoglobina', 'g/dL');

INSERT INTO "integracion_log" ("id", "sistema", "tipo", "estado", "fecha", "detalle") VALUES
(1, 'ISAPRE', 'validacion_cobertura', 'exitoso', '2024-09-05T00:08:24.541Z', 'Respuesta OK - 200'),
(2, 'WHATSAPP_API', 'envio_mensaje', 'exitoso', '2025-08-12T15:09:14.595Z', 'Batch procesado correctamente'),
(3, 'RAYEN', 'agendamiento', 'exitoso', '2025-06-26T20:45:55.257Z', 'Batch procesado correctamente'),
(4, 'FARMACIA_APS', 'consulta_stock', 'exitoso', '2025-02-08T16:15:48.318Z', 'Respuesta OK - 200'),
(5, 'DEIS', 'envio_estadistica', 'exitoso', '2025-07-18T00:59:20.657Z', 'Sincronización completada sin diferencias'),
(6, 'WHATSAPP_API', 'envio_mensaje', 'exitoso', '2026-01-10T23:59:22.641Z', 'Sincronización completada sin diferencias'),
(7, 'ISAPRE', 'validacion_cobertura', 'exitoso', '2026-03-21T01:00:46.589Z', 'Consulta exitosa - datos actualizados'),
(8, 'LIS', 'recepcion_resultado', 'exitoso', '2025-03-12T23:20:18.372Z', 'Sincronización completada sin diferencias'),
(9, 'WHATSAPP_API', 'reporte_lectura', 'exitoso', '2025-02-16T06:23:39.305Z', 'Batch procesado correctamente'),
(10, 'LAB_CENTRAL', 'recepcion_resultado', 'exitoso', '2026-03-09T06:34:34.805Z', 'Consulta exitosa - datos actualizados'),
(11, 'FARMACIA_APS', 'despacho_receta', 'exitoso', '2024-12-11T23:25:49.962Z', 'Registro creado en sistema remoto'),
(12, 'FARMACIA_APS', 'consulta_stock', 'error', '2024-11-08T23:31:37.676Z', 'SQLSTATE[23505] – duplicate key en tabla destino'),
(13, 'LIS', 'recepcion_resultado', 'exitoso', '2025-04-24T23:35:01.158Z', 'Sincronización completada sin diferencias'),
(14, 'HIS', 'derivacion', 'exitoso', '2025-08-14T11:19:07.094Z', 'Sincronización completada sin diferencias'),
(15, 'FARMACIA_APS', 'despacho_receta', 'exitoso', '2024-06-10T19:45:10.848Z', 'Sincronización completada sin diferencias'),
(16, 'LIS', 'solicitud_examen', 'exitoso', '2025-07-11T16:56:20.444Z', 'Batch procesado correctamente'),
(17, 'RNI', 'consulta_esquema', 'exitoso', '2025-06-26T08:17:45Z', 'Registro creado en sistema remoto'),
(18, 'FONASA', 'consulta_tramo', 'exitoso', '2025-04-27T14:19:12.086Z', 'Registro creado en sistema remoto'),
(19, 'FONASA', 'validacion_previsional', 'exitoso', '2025-11-03T11:32:45.434Z', 'Consulta exitosa - datos actualizados'),
(20, 'DEIS', 'envio_estadistica', 'exitoso', '2024-07-17T02:13:15.436Z', 'Batch procesado correctamente');

INSERT INTO "medicamento" ("id", "nombre") VALUES
(1, 'Losartán 50 mg'),
(2, 'Losartán 100 mg'),
(3, 'Enalapril 10 mg'),
(4, 'Enalapril 20 mg'),
(5, 'Captopril 25 mg'),
(6, 'Captopril 50 mg'),
(7, 'Amlodipino 5 mg'),
(8, 'Amlodipino 10 mg'),
(9, 'Nifedipino Retard 20 mg'),
(10, 'Hidroclorotiazida 25 mg'),
(11, 'Hidroclorotiazida 50 mg'),
(12, 'Furosemida 40 mg'),
(13, 'Espironolactona 25 mg'),
(14, 'Atenolol 50 mg'),
(15, 'Atenolol 100 mg'),
(16, 'Carvedilol 6.25 mg'),
(17, 'Carvedilol 25 mg'),
(18, 'Propranolol 40 mg'),
(19, 'Bisoprolol 2.5 mg'),
(20, 'Bisoprolol 5 mg');

INSERT INTO "medicion" ("id", "paciente_id", "indicador_id", "valor", "fecha") VALUES
(1, 19979, 17, 146, '2025-06-23'),
(2, 19979, 1, 112, '2025-06-23'),
(3, 19979, 2, 75, '2025-06-23'),
(4, 19979, 18, 11.6, '2025-08-02'),
(5, 19979, 19, 9.5, '2025-08-02'),
(6, 19979, 3, 94, '2025-08-02'),
(7, 19979, 4, 8, '2025-08-02'),
(8, 19979, 9, 21.8, '2025-08-02'),
(9, 19979, 10, 46.7, '2025-08-02'),
(10, 19979, 15, 1, '2025-08-02'),
(11, 19979, 16, 109, '2025-08-02'),
(12, 19979, 17, 113, '2025-08-02'),
(13, 19979, 1, 120, '2025-08-02'),
(14, 19979, 2, 69, '2025-08-02'),
(15, 19980, 1, 144, '2025-09-05'),
(16, 19980, 2, 95, '2025-09-05'),
(17, 19980, 13, 89, '2025-09-05'),
(18, 19980, 10, 58.4, '2025-09-05'),
(19, 19980, 12, 84, '2025-09-05'),
(20, 19981, 9, 30, '2024-04-23');

INSERT INTO "paciente" ("id", "rut", "nombre", "fecha_nacimiento", "telefono", "direccion", "establecimiento_id") VALUES
(1, '21.496.958-0', 'Jaime Fuentes Castillo', '1982-09-22', '+56 9 4441 4688', 'San Martín 342, Ñuñoa', 46),
(2, '11.754.644-6', 'Ignacio Carmona Riveros', '2005-04-03', '+56 9 9454 5270', 'Gabriela Mistral 1445, Quillota', 30),
(3, '15.501.217-K', 'Viviana Monsalve Muñoz', '1948-09-03', '+56 9 6018 3548', 'Las Acacias 1220, Caldera', 15),
(4, '9.753.076-9', 'Emilio Castro Vega', '2012-03-09', '+56 9 4812 3495', 'Las Rosas 4771, Cabo de Hornos', 165),
(5, '5.079.649-3', 'Fabiola Palma Flores', '2006-12-27', '+56 9 4513 0509', 'Los Aromos 1620, San Fernando', 111),
(6, '22.944.224-4', 'Raquel Serrano Monsalve', '1978-01-10', '+56 9 4568 4205', 'Los Álamos 4500, Rancagua', 116),
(7, '11.701.722-2', 'Mercedes Soto Medina', '1938-12-22', '+56 9 2975 4716', 'Los Aromos 1254, Frutillar', 161),
(8, '19.394.118-4', 'Andrés Retamal Guerrero', '1972-04-10', '+56 9 7213 6397', 'San Martín 4355, Puente Alto', 106),
(9, '6.752.565-5', 'Norma Espinoza Fernández', '1988-05-28', '+56 9 2116 4315', 'Los Aromos 5078, Quilicura', 101),
(10, '18.717.466-K', 'Juana Cabrera Ortiz', '1949-08-25', '+56 9 3792 4243', 'Los Pinos 5007, Vicuña', 18),
(11, '8.935.804-3', 'Luis Cárdenas Cortés', '1943-08-08', '+56 9 3298 7973', 'Las Acacias 617, Quintero', 33),
(12, '20.155.207-9', 'Mateo Romero Alvarado', '1994-06-25', '+56 9 5348 2400', 'Las Rosas 4255, Quintero', 38),
(13, '13.808.696-8', 'Esperanza Velásquez Farías', '1953-07-15', '+56 9 4271 4544', 'Colón 3377, Rengo', 113),
(14, '12.179.060-2', 'Stephanie Paredes Loyola', '1959-03-10', '+56 9 5727 6768', 'Los Álamos 1964, Talcahuano', 134),
(15, '21.827.428-5', 'Rayen Cortés Sepúlveda', '1940-07-21', '+56 9 8858 4205', 'El Boldo 2726, Temuco', 147),
(16, '19.304.929-K', 'Fernanda Alvarado Sandoval', '1993-01-10', '+56 9 6077 1741', 'Los Aromos 2169, Machalí', 110),
(17, '5.540.632-4', 'Fernando Leiva Pavez', '2005-03-26', '+56 9 1674 6597', 'Los Pinos 3849, Melipilla', 103),
(18, '18.151.032-3', 'Martín Silva Saavedra', '1951-05-12', '+56 9 5248 1335', 'Gabriela Mistral 3548, Quirihue', 128),
(19, '17.175.409-7', 'Rodrigo Rivera Moreno', '1988-04-28', '+56 9 4678 4451', 'Pablo Neruda 3439, Cochrane', 162),
(20, '12.442.714-2', 'Alejandra Herrera Valenzuela', '1959-06-04', '+56 9 9405 7479', 'Condell 4420, Cerro Navia', 42);

INSERT INTO "paciente_medicamento" ("id", "paciente_id", "medicamento_id", "dosis", "frecuencia") VALUES
(1, 19610, 45, '1 comprimido', 'cada 12 horas'),
(2, 19610, 43, '1 comprimido', '2 veces al día'),
(3, 19610, 149, '1 comprimido', 'cada 8 horas'),
(4, 19611, 3, '1 inhalación', '2 veces al día'),
(5, 19611, 2, '1 comprimido', '1 vez al día'),
(6, 19611, 1, '1 comprimido', 'cada 8 horas'),
(7, 19611, 21, '2 inhalaciones', 'cada 6 horas según necesidad'),
(8, 19613, 65, '1 comprimido', '1 vez al día'),
(9, 19613, 60, '2 inhalaciones', 'cada 6 horas según necesidad'),
(10, 19613, 62, '2 comprimidos', '1 vez al día'),
(11, 19614, 99, '1 comprimido', 'cada 8 horas'),
(12, 19614, 3, '1 comprimido', '1 vez al día'),
(13, 19614, 2, '1 comprimido', '1 vez al día'),
(14, 19614, 1, '1 comprimido', '2 veces al día'),
(15, 19614, 95, '2 comprimidos', '1 vez al día'),
(16, 19617, 53, '1 comprimido', 'cada 12 horas'),
(17, 19617, 47, '1/2 comprimido', '1 vez al día'),
(18, 19617, 42, '1/2 comprimido', '1 vez al día'),
(19, 19619, 148, '1 comprimido', '2 veces al día'),
(20, 19619, 149, '1 comprimido', '2 veces al día');

INSERT INTO "paciente_patologia" ("id", "paciente_id", "patologia_id", "fecha_diagnostico") VALUES
(1, 19760, 5, '2019-09-19'),
(2, 19760, 1, '2019-03-04'),
(3, 19760, 14, '2017-08-16'),
(4, 19761, 12, '2023-07-24'),
(5, 19761, 1, '2022-10-26'),
(6, 19762, 47, '2017-09-30'),
(7, 19762, 4, '2025-12-25'),
(8, 19762, 2, '2020-07-13'),
(9, 19763, 1, '2021-07-17'),
(10, 19763, 37, '2018-08-12'),
(11, 19763, 2, '2021-01-11'),
(12, 19764, 2, '2025-10-28'),
(13, 19765, 5, '2020-09-14'),
(14, 19765, 50, '2020-09-14'),
(15, 19766, 2, '2023-05-07'),
(16, 19766, 20, '2025-01-06'),
(17, 19767, 2, '2017-09-29'),
(18, 19767, 5, '2018-04-08'),
(19, 19768, 8, '2021-12-21'),
(20, 19771, 34, '2025-02-22');

INSERT INTO "patologia" ("id", "nombre", "descripcion") VALUES
(1, 'Hipertensión Arterial Esencial', 'Elevación crónica de la presión arterial sistólica ≥140 o diastólica ≥90 mmHg. Patología GES.'),
(2, 'Diabetes Mellitus Tipo 2', 'Trastorno metabólico caracterizado por hiperglicemia crónica por resistencia a la insulina. Patología GES.'),
(3, 'Diabetes Mellitus Tipo 1', 'Déficit absoluto de insulina por destrucción autoinmune de células beta pancreáticas. Patología GES.'),
(4, 'Dislipidemia', 'Alteración en los niveles de lípidos séricos, incluyendo colesterol y triglicéridos.'),
(5, 'Obesidad', 'IMC igual o superior a 30 kg/m². Factor de riesgo cardiovascular y metabólico.'),
(6, 'EPOC', 'Enfermedad pulmonar obstructiva crónica, limitación progresiva del flujo aéreo. Patología GES.'),
(7, 'Asma Bronquial', 'Enfermedad inflamatoria crónica de la vía aérea. Patología GES en menores de 15 años y adultos.'),
(8, 'Enfermedad Renal Crónica', 'Daño renal persistente con disminución de la tasa de filtración glomerular. Patología GES.'),
(9, 'Insuficiencia Cardíaca', 'Incapacidad del corazón para bombear sangre suficiente. Patología GES.'),
(10, 'Cardiopatía Isquémica', 'Enfermedad por reducción del flujo sanguíneo coronario.'),
(11, 'Accidente Cerebrovascular', 'Déficit neurológico agudo de origen vascular. Patología GES.'),
(12, 'Artrosis de Cadera y Rodilla', 'Enfermedad degenerativa articular. GES en mayores de 55 años.'),
(13, 'Artritis Reumatoide', 'Enfermedad inflamatoria crónica autoinmune que afecta articulaciones. Patología GES.'),
(14, 'Depresión', 'Trastorno del ánimo caracterizado por tristeza persistente y anhedonia. Patología GES.'),
(15, 'Esquizofrenia', 'Trastorno psicótico crónico. Patología GES en primer brote.'),
(16, 'Trastorno Bipolar', 'Trastorno del ánimo con episodios de manía/hipomanía y depresión. Patología GES.'),
(17, 'Epilepsia', 'Trastorno neurológico con crisis recurrentes. Patología GES.'),
(18, 'Enfermedad de Parkinson', 'Trastorno neurodegenerativo. Patología GES.'),
(19, 'Alzheimer', 'Demencia degenerativa progresiva. Patología GES en mayores de 60 años.'),
(20, 'Hipotiroidismo', 'Déficit de hormonas tiroideas. Patología GES en menores de 15 años y embarazadas.');

INSERT INTO "prediccion" ("id", "paciente_id", "riesgo_descompensacion", "riesgo_inasistencia", "cluster", "fecha") VALUES
(1, 19852, 0.196, 0.689, 'bajo_riesgo_no_adherente', '2025-10-22T21:19:06.315Z'),
(2, 19852, 0.318, 0.614, 'moderado_descompensado', '2026-01-23T15:43:50.110Z'),
(3, 19853, 0.53, 0.268, 'moderado_estable', '2024-04-23T16:45:12.402Z'),
(4, 19853, 0.389, 0.366, 'moderado_estable', '2024-07-23T21:17:48.017Z'),
(5, 19853, 0.532, 0.431, 'moderado_descompensado', '2024-10-23T12:41:50.958Z'),
(6, 19853, 0.373, 0.366, 'moderado_estable', '2025-01-23T00:46:27.845Z'),
(7, 19853, 0.427, 0.446, 'moderado_descompensado', '2025-04-23T11:04:33.974Z'),
(8, 19853, 0.497, 0.374, 'moderado_estable', '2025-07-23T04:38:47.607Z'),
(9, 19853, 0.374, 0.336, 'moderado_estable', '2025-10-23T15:33:29.753Z'),
(10, 19853, 0.356, 0.396, 'moderado_estable', '2026-01-22T15:07:33.906Z'),
(11, 19854, 0.079, 0.247, 'bajo_riesgo_adherente', '2024-04-23T05:02:33.302Z'),
(12, 19854, 0.196, 0.222, 'bajo_riesgo_adherente', '2024-07-22T11:02:26.013Z'),
(13, 19854, 0.162, 0.152, 'bajo_riesgo_adherente', '2024-10-23T02:07:51.527Z'),
(14, 19854, 0.14, 0.264, 'bajo_riesgo_adherente', '2025-01-22T21:36:37.903Z'),
(15, 19854, 0.08, 0.324, 'bajo_riesgo_adherente', '2025-04-22T13:11:42.020Z'),
(16, 19854, 0.01, 0.24, 'bajo_riesgo_adherente', '2025-07-23T03:37:23.541Z'),
(17, 19854, 0.02, 0.35, 'bajo_riesgo_adherente', '2025-10-22T02:22:43.026Z'),
(18, 19854, 0.128, 0.335, 'bajo_riesgo_adherente', '2026-01-22T18:41:25.355Z'),
(19, 19855, 0.033, 0.182, 'bajo_riesgo_adherente', '2024-04-23T05:11:21.156Z'),
(20, 19855, 0.191, 0.192, 'bajo_riesgo_adherente', '2024-07-22T16:30:47.380Z');

INSERT INTO "recordatorio" ("id", "paciente_id", "tipo", "canal", "fecha_envio", "estado") VALUES
(1, 19944, 'control_programado', 'whatsapp', '2025-09-08T23:19:54.569Z', 'leido'),
(2, 19944, 'control_programado', 'whatsapp', '2025-09-14T23:59:36.766Z', 'fallido'),
(3, 19944, 'control_programado', 'whatsapp', '2025-10-11T03:59:18.677Z', 'fallido'),
(4, 19944, 'control_programado', 'llamada', '2025-10-18T02:27:08.454Z', 'contestada'),
(5, 19944, 'control_programado', 'whatsapp', '2025-10-24T00:50:50.046Z', 'entregado'),
(6, 19944, 'control_programado', 'whatsapp', '2025-11-12T23:47:39.698Z', 'leido'),
(7, 19944, 'control_programado', 'whatsapp', '2025-11-19T23:53:20.177Z', 'entregado'),
(8, 19944, 'control_programado', 'whatsapp', '2025-11-25T18:14:45.834Z', 'leido'),
(9, 19944, 'control_programado', 'whatsapp', '2025-12-31T03:31:07.995Z', 'leido'),
(10, 19944, 'control_programado', 'email', '2026-01-07T00:31:20.756Z', 'entregado'),
(11, 19944, 'control_programado', 'whatsapp', '2026-01-13T03:42:30.659Z', 'leido'),
(12, 19944, 'control_programado', 'whatsapp', '2026-02-15T01:49:06.452Z', 'entregado'),
(13, 19944, 'control_programado', 'sms', '2026-02-22T04:54:46.130Z', 'entregado'),
(14, 19944, 'control_programado', 'whatsapp', '2026-02-27T21:36:41.482Z', 'leido'),
(15, 19944, 'control_programado', 'llamada', '2026-03-23T02:55:54.113Z', 'contestada'),
(16, 19944, 'control_programado', 'whatsapp', '2026-03-30T01:54:02.959Z', 'entregado'),
(17, 19944, 'control_programado', 'whatsapp', '2026-04-05T01:07:01.698Z', 'leido'),
(18, 19945, 'control_programado', 'whatsapp', '2024-08-08T01:11:15.679Z', 'leido'),
(19, 19945, 'control_programado', 'whatsapp', '2024-08-15T02:27:34.906Z', 'leido'),
(20, 19945, 'control_programado', 'whatsapp', '2024-08-21T01:51:30.688Z', 'leido');



INSERT INTO "usuario" ("id", "nombre", "email", "password", "rol", "establecimiento_id") VALUES
(1, 'Gerardo Espinoza Pacheco', 'gerardo.espinoza@redsalud.gob.cl', '$2b$10$lvabrj08tpyv2bgw8gcwb', 'Administrativo', 99),
(2, 'Hernán Sandoval Reyes', 'hernan.sandoval@outlook.com', '$2b$10$8bkpn86p96kee2l0cixder', 'Matrona', 99),
(3, 'Constanza Concha Mondaca', 'constanza.concha@redsalud.gob.cl', '$2b$10$tjxmqj48lfhsf74ogi9s', 'Enfermera', 99),
(4, 'Santiago Vásquez Acuña', 'santiago.vasquez@redsalud.gob.cl', '$2b$10$rgygrm0fa4kv53969h02o', 'Médico', 99),
(5, 'Lucas Mendoza Olivera', 'lucas.mendoza@outlook.com', '$2b$10$tqt133xnbwxxvqx696qor', 'Enfermera', 100),
(6, 'Manuel Salazar García', 'manuel.salazar@redsalud.gob.cl', '$2b$10$gvc46p9285nqlotm8cetnl', 'Enfermera', 100),
(7, 'Renato Vargas Medina', 'renato.vargas@yahoo.com', '$2b$10$1256oy3aalpt6g808bsyp', 'Médico', 100),
(8, 'Julio Rivera Ríos', 'julio.rivera@redsalud.gob.cl', '$2b$10$brhnb3dk4bljdadkxbyhof', 'Médico', 100),
(9, 'Olivia Riquelme Santana', 'olivia.riquelme@live.cl', '$2b$10$3z6tfupgwurz7lc62gs8c', 'Enfermera', 100),
(10, 'Sandra Figueroa Ávila', 'sandra.figueroa@gmail.com', '$2b$10$may91b47vz33bufrrahqw', 'Asistente Social', 100),
(11, 'Valeria Beltrán Bernal', 'valeria.beltran@live.cl', '$2b$10$y03d3jh5jvqli7mn4oh5nd', 'TENS', 100),
(12, 'Lorenzo Saavedra Alvarado', 'lorenzo.saavedra@redsalud.gob.cl', '$2b$10$nm7h70nbkh4jqfn4v3h5', 'TENS', 100),
(13, 'Macarena Durán López', 'macarena.duran@redsalud.gob.cl', '$2b$10$q1zpwmk339nla2dl79smg', 'Administrativo', 100),
(14, 'Colomba Barrera Córdoba', 'colomba.barrera@live.cl', '$2b$10$243pcoeww49amngzmztqp', 'Matrona', 101),
(15, 'Norma Cáceres Zamora', 'norma.caceres@yahoo.com', '$2b$10$ndw9md3hnh93gp8wie8a', 'Químico Farmacéutico', 101),
(16, 'Pedro Rojas Núñez', 'pedro.rojas@gmail.com', '$2b$10$osod1sq8wsdn3ihkv4ntnj', 'Químico Farmacéutico', 101),
(17, 'Camilo Calderón Yévenes', 'camilo.calderon@redsalud.gob.cl', '$2b$10$o569towr3dtd5wkylaq1yg', 'Enfermera', 101),
(18, 'Loreto Carmona Díaz', 'loreto.carmona@outlook.com', '$2b$10$kfmyio6ar5c4w30q86k82y', 'Médico', 101),
(19, 'Marcelo Guerrero Fernández', 'marcelo.guerrero@redsalud.gob.cl', '$2b$10$25iue4xx0vsvedponn19zc', 'Administrativo', 101),
(20, 'Rosa Paredes Alarcón', 'rosa.paredes@hotmail.cl', '$2b$10$knf2k4qheprjic1slvddyq', 'TENS', 101);