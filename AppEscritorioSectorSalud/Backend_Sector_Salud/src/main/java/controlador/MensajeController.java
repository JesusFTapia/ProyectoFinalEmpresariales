/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.List;
import lombok.RequiredArgsConstructor;
import model.MensajeRecibido;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.MensajeRecibidoRepository;

/**
 *
 * @author jl4ma
 */
@RestController
@RequestMapping("/api/mensajes")
@RequiredArgsConstructor
public class MensajeController {

    private final MensajeRecibidoRepository mensajeRepo;

    @GetMapping
    public List<MensajeRecibido> getAllMensajes() {
        return mensajeRepo.findAll();
    }

    @GetMapping("/profesional/{cedula}")
    public List<MensajeRecibido> getMensajesPorCedula(@PathVariable String cedula) {
        return mensajeRepo.findByCedulaProfesional(cedula);
    }

    @GetMapping("/{id}")
    public ResponseEntity<MensajeRecibido> getMensajePorId(@PathVariable Long id) {
        return mensajeRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarMensaje(@PathVariable Long id) {
        if (mensajeRepo.existsById(id)) {
            mensajeRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}/visto")
    public ResponseEntity<Object> marcarComoVisto(@PathVariable Long id) {
        return mensajeRepo.findById(id).map(mensaje -> {
            mensaje.setEstado("Visto");
            mensajeRepo.save(mensaje);
            return ResponseEntity.ok().build();
        }).orElse(ResponseEntity.notFound().build());
    }
}
