package controlador;

import java.util.List;
import lombok.RequiredArgsConstructor;
import model.PacienteAsignado;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import repository.PacientesAsignadosRepository;

/**
 *
 * @author adria
 */
@RestController
@RequestMapping("/api/pacientes")
@RequiredArgsConstructor
public class PacienteAsignadoController {

    private final PacientesAsignadosRepository pacienteRepo;

    @GetMapping
    public List<PacienteAsignado> getAllPacientesAsignados() {
        return pacienteRepo.findAll();
    }

    @GetMapping("/profesional/{cedula}")
    public List<PacienteAsignado> getPacientesPorCedula(@PathVariable String cedula) {
        return pacienteRepo.findByProfesionalCedula(cedula);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PacienteAsignado> getPacienteAsignadoPorId(@PathVariable Long id) {
        return pacienteRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarPacienteAsignado(@PathVariable Long id) {
        if (pacienteRepo.existsById(id)) {
            pacienteRepo.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

