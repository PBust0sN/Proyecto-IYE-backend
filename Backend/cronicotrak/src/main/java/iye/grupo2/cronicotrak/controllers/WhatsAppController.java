package iye.grupo2.cronicotrak.controllers;

import iye.grupo2.cronicotrak.dto.WhatsAppMessageRequest;
import iye.grupo2.cronicotrak.services.WhatsAppService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/whatsapp")
public class WhatsAppController {

    private final WhatsAppService whatsAppService;

    public WhatsAppController(WhatsAppService whatsAppService) {
        this.whatsAppService = whatsAppService;
    }

    @PostMapping("/send")
    public ResponseEntity<String> sendWhatsAppMessage(@RequestBody WhatsAppMessageRequest request) {
        boolean enviado = whatsAppService.enviarMensaje(request.getNumero(), request.getMensaje());
        
        if (enviado) {
            return ResponseEntity.ok("Mensaje enviado exitosamente.");
        } else {
            return ResponseEntity.internalServerError().body("Error al enviar el mensaje de WhatsApp.");
        }
    }
}
