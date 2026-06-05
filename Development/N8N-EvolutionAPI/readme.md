
levantar
'''
docker compose up -d 
'''

crear instancia:
'''
curl -X POST http://localhost:8081/instance/create \
  -H "apikey: clave_secreta_123" \
  -H "Content-Type: application/json" \
  -d '{
    "instanceName": "mi_whatsapp",
    "qrcode": true
  }'
'''

obtener qr para iniciar
'''
curl -X GET http://localhost:8081/instance/connect/mi_whatsapp \
  -H "apikey: clave_secreta_123"
'''

ver qr (rapido antes que expire)
'''
docker logs evolution-api -f
'''

entrar a n8n en localhost:5678

importar My workflow.json en n8n en el modulo "HTTP Request" editar el json y poner el numero que se le quiere enviar mensajes, luego ejecute y disfrute
