/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author jl4ma
 */
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.ExpedienteService;
import service.RabbitMQSenderService;

@RestController
@RequestMapping("/profesional")
@RequiredArgsConstructor
public class ProfesionalControlador {

    private final RabbitMQSenderService senderService;
    private final ExpedienteService expedienteService;

    // Endpoint para enviar la solicitud de expediente
    @PostMapping("/solicitar-expediente")
    public ResponseEntity<String> solicitarExpediente(@RequestParam String cedulaProfesional,
                                                      @RequestParam String pacienteUuid, 
                                                      @RequestParam String nombre) {
        try {
            senderService.enviarSolicitudExpediente(cedulaProfesional, pacienteUuid, nombre);
            return ResponseEntity.ok("Solicitud de expediente enviada correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al enviar solicitud de expediente: " + e.getMessage());
        }
    }

    // Endpoint para ver el expediente
    @GetMapping("/ver-expediente/{pacienteId}")
    public ResponseEntity<String> verExpediente(@PathVariable String pacienteId) {
        try {
            String expediente = expedienteService.obtenerExpedientePorId(pacienteId);
            return ResponseEntity.ok(expediente);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Expediente no encontrado para el paciente con ID: " + pacienteId);
        }
    }
}

