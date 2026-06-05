package iye.grupo2.cronicotrak.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WhatsAppService {

    private final RestTemplate restTemplate;

    @Value("${evolution.api.url}")
    private String evolutionApiUrl;

    @Value("${evolution.api.key}")
    private String evolutionApiKey;

    @Value("${evolution.api.instance}")
    private String evolutionApiInstance;

    public WhatsAppService() {
        this.restTemplate = new RestTemplate();
    }

    public boolean enviarMensaje(String numero, String texto) {
        String url = String.format("%s/message/sendText/%s", evolutionApiUrl, evolutionApiInstance);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("apikey", evolutionApiKey);

        // Construir el body
        String requestJson = String.format("""
            {
              "number": "%s", 
              "options": {
                "delay": 1200,
                "presence": "composing"
              },
              "textMessage": {
                "text": "%s"
              }
            }
            """, numero, texto);

        HttpEntity<String> request = new HttpEntity<>(requestJson, headers);

        try {
            restTemplate.postForObject(url, request, String.class);
            System.out.println("Mensaje enviado exitosamente a: " + numero);
            return true;
        } catch (Exception e) {
            System.err.println("Error enviando mensaje de WhatsApp: " + e.getMessage());
            return false;
        }
    }
}
