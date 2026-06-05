# API de Envío de WhatsApp

Este documento detalla el uso del endpoint expuesto en el backend (Spring Boot) para enviar mensajes de WhatsApp vía Evolution API.

## Detalles del Endpoint

- **Ruta:** `/api/whatsapp/send`
- **Método HTTP:** `POST`
- **Content-Type:** `application/json`

## Payload (Cuerpo de la Petición)

El endpoint espera un objeto JSON con las siguientes propiedades:

| Campo | Tipo | Descripción | Ejemplo |
| :--- | :--- | :--- | :--- |
| `numero` | `String` | Número de teléfono en formato internacional (sin el `+`). | `"56977020724"` |
| `mensaje` | `String` | El contenido del texto que recibirá el usuario. | `"¡Hola! Su examen ya está disponible."` |

### Ejemplo de JSON
```json
{
  "numero": "56977020724", 
  "mensaje": "¡Hola, paciente! Este es un recordatorio de Cronicotrak. 🚀"
}
```

## Ejemplos de Peticiones

### Usando cURL
```bash
curl -X POST http://localhost:8080/api/whatsapp/send \
  -H "Content-Type: application/json" \
  -d '{
    "numero": "56977020724", 
    "mensaje": "¡Hola! Su examen ya está disponible. 🚀"
  }'
```

### Usando JavaScript (Fetch API en Frontend)
```javascript
const enviarWhatsApp = async (numero, mensaje) => {
  try {
    const response = await fetch('http://localhost:8080/api/whatsapp/send', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({
        numero: numero,
        mensaje: mensaje
      })
    });

    if (response.ok) {
      console.log('Mensaje enviado con éxito');
    } else {
      console.error('Error al enviar el mensaje');
    }
  } catch (error) {
    console.error('Error de red:', error);
  }
};

// Uso:
enviarWhatsApp("56977020724", "¡Hola! Prueba desde Cronicotrak.");
```

## Respuestas (HTTP Status)

- **`200 OK`**: El mensaje fue enviado a la cola de Evolution API exitosamente. (El cuerpo responderá `"Mensaje enviado exitosamente."`)
- **`500 Internal Server Error`**: Ocurrió un error en la conexión con Evolution API, la API Key es incorrecta, o la instancia está desconectada (falta leer el QR).
