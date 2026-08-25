# 🤖 Instrucciones para Agentes de IA (Backend - Cronicotrak)

Este documento define el contexto, las convenciones de arquitectura y las reglas técnicas que cualquier Agente de IA debe seguir al generar o modificar código en este repositorio.

---

## 1. Contexto del Proyecto
*   **Nombre:** Cronicotrak (Backend)
*   **Descripción:** API RESTful para el Sistema Integral de Seguimiento y Gestión de Pacientes Crónicos.
*   **Stack Principal:** Java, Spring Boot, PostgreSQL, Hibernate/JPA, Integración con Keycloak (Auth) y Evolution API (WhatsApp).

## 2. Reglas de Arquitectura y Código
1.  **Controladores (Capa API):** Expuestos en `/api/v1/`. Deben recibir y retornar DTOs, no entidades JPA directamente para evitar la exposición de datos internos.
2.  **Servicios (Capa de Negocio):** Toda la lógica de negocio debe residir aquí (ej. `MotorReglasService`). Mantener los controladores lo más delgados posible.
3.  **Entidades:** Mapean directamente a tablas en PostgreSQL. 

## 3. Lecciones Aprendidas y Reglas Específicas (Actualización PEP 2)
### Privacidad de Datos (PII - Ley 20.584)
1.  **Encriptación de Datos Sensibles:** Todo dato identificatorio en entidades (RUT, nombre, teléfono, email, contacto de emergencia, etc.) DEBE estar encriptado en la base de datos.
2.  **Uso de Converters:** Utilizar SIEMPRE `@Convert(converter = StringCryptoConverter.class)` en los atributos PII. Ningún agente tiene permitido crear nuevas columnas de texto plano para datos sensibles o médicos.

### Motor de Reglas y Automatización
1.  **Cronjobs (`@Scheduled`):** Toda nueva regla automatizada agregada al `MotorReglasService` o similares debe ser documentada. Al definir cron expressions, se debe considerar siempre el uso horario (Timezone) del servidor de producción.
2.  **Integraciones Externas:** Las llamadas a Evolution API (WhatsAppService) deben contemplar manejo de excepciones para no interrumpir el hilo principal del cronjob.

### Trazabilidad y Demos
1.  **Endpoints de Demo:** Todo endpoint diseñado exclusivamente para demostraciones (como simulaciones de alertas) debe retornar un registro claro (log) detallado de las acciones que simuló.

## 4. Proceso de Revisión (Obligatorio)
1.  **Aprobación de Pull Requests:** Ningún código generado por IA debe ser integrado directamente a la rama `main`. El agente debe proponer los cambios, y obligatoriamente **un integrante humano** debe revisarlo, comentarlo y aprobarlo en un Pull Request.
2.  **Registro de Decisiones:** Los PRs deben dejar registro escrito de por qué se aceptó la sugerencia de la IA o qué modificaciones realizó el humano antes de aprobar.

---
*Nota: Este archivo es la "fuente de la verdad" para el comportamiento agéntico en el Backend. Manténganlo actualizado con cada iteración.*
