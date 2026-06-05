# Configuración Inicial de Evolution API (WhatsApp)

Dado que Evolution API se ejecuta localmente junto con el backend (en el contenedor `iye-evolution-api`), necesitas crear la instancia y escanear el código QR por primera vez para vincular tu número de WhatsApp.

Sigue estos pasos en orden. Asegúrate de que los contenedores de Docker estén corriendo (`docker compose up -d` desde esta misma carpeta `Development`).

---

## Paso 1: Crear la Instancia

Por defecto en el código de backend de Spring Boot utilizamos el nombre de instancia `mi_whatsapp`. Debemos crearla en Evolution API.

Ejecuta el siguiente comando cURL en tu terminal:

```bash
curl -X POST http://localhost:8081/instance/create \
  -H "apikey: clave_secreta_123" \
  -H "Content-Type: application/json" \
  -d '{
    "instanceName": "mi_whatsapp",
    "token": "",
    "qrcode": true
  }'
```

**Nota:** El parámetro `"qrcode": true` le indica a Evolution que, una vez creada la instancia, nos devuelva inmediatamente el QR en formato base64 en la respuesta.

## Paso 2: Conectar la Instancia (Si ya existe pero está desconectada)

Si ya habías creado la instancia pero perdiste la sesión o reiniciaste el contenedor borrando volúmenes, puedes solicitar un nuevo código QR con este comando:

```bash
curl -X GET http://localhost:8081/instance/connect/mi_whatsapp \
  -H "apikey: clave_secreta_123"
```

## Paso 3: Escanear el QR

Para escanear el código QR, Evolution API nos da un par de opciones:

**Opción A: Ver el QR desde la terminal (Logs)**
1. Corre este comando en tu terminal para seguir los logs del contenedor:
   ```bash
   docker logs iye-evolution-api -f
   ```
2. La API de Evolution dibujará el código QR directamente en formato ASCII (texto) en tu consola.
3. Abre WhatsApp en tu celular > Dispositivos Vinculados > Vincular un dispositivo.
4. Apunta la cámara de tu teléfono a la pantalla de tu computador.

**Opción B: Usar el Base64**
Si revisas la respuesta del cURL del *Paso 1* o *Paso 2*, verás que retorna un campo llamado `"base64"`. 
1. Copia ese texto largo que empieza con `data:image/png;base64,...`.
2. Pégalo en tu navegador (en la barra de URLs) o en una página como [Base64 to Image](https://codebeautify.org/base64-to-image-converter).
3. Escanea el código QR que aparecerá.

---

## Paso 4: Verificar Estado

Una vez escaneado el QR, WhatsApp comenzará a sincronizar tus chats. Puedes verificar el estado de la conexión con este comando:

```bash
curl -X GET http://localhost:8081/instance/connectionState/mi_whatsapp \
  -H "apikey: clave_secreta_123"
```

Si la respuesta tiene un `"state": "open"`, significa que **ya estás listo** para empezar a mandar mensajes desde el Backend usando el endpoint de Spring Boot (`/api/whatsapp/send`).
