//    /*
//     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
//     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
//     */
package service;
//

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import model.MensajeRecibido;
import model.PacienteAsignado;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import rabbitConfig.RabbitMQConfigListener;
import repository.MensajeRecibidoRepository;
import repository.PacientesAsignadosRepository;

/**
 *
 * @author jl4ma
 */
@Service
@RequiredArgsConstructor
public class RabbitMQListenerService {

    private final MensajeRecibidoRepository mensajeRepo;
    private final PacientesAsignadosRepository pacienteRepo;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @RabbitListener(queues = RabbitMQConfigListener.CLIENTE_SERVIDOR_QUEUE)
    public void recibirMensajes(String mensajeJson) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        DateTimeFormatter formatter2 = DateTimeFormatter.ISO_OFFSET_DATE_TIME;

        try {
            MensajeRecibido mensaje = new MensajeRecibido();
            JsonNode root = objectMapper.readTree(mensajeJson);
            String tipo = root.get("tipo").asText();
            String nombre = root.get("nombre").asText();
            String cedulaProfesional = root.get("idProfesional").asText();
            String uuid = root.get("idPaciente").asText();

            mensaje.setCedulaProfesional(cedulaProfesional);
            mensaje.setPacienteUuid(uuid);
            mensaje.setTipoMensaje(tipo);
            mensaje.setNombre(nombre);
            mensaje.setEstado("Sin Ver");
            // Procesa el mensaje según el tipo
            if ("AgendarCita".equals(tipo)) {
                // Extraer y parsear fecha
                String fecha = root.get("fecha").asText();
                // ajusta según tu formato
                Date fechaCita = sdf.parse(fecha);

                // Guardar mensaje
                mensaje.setFechaCita(fechaCita);
                mensajeRepo.save(mensaje);

                // Buscar si ya existe
                Optional<PacienteAsignado> existente = pacienteRepo.findByPacienteUuidAndProfesionalCedula(uuid, cedulaProfesional);

                if (existente.isPresent()) {
                    PacienteAsignado existentePaciente = existente.get();
                    existentePaciente.setFecha(fechaCita); // solo actualizas la fecha
                    pacienteRepo.save(existentePaciente);
                } else {
                    // Si no existe, creas uno nuevo
                    PacienteAsignado nuevo = new PacienteAsignado();
                    nuevo.setPacienteUuid(uuid);
                    nuevo.setProfesionalCedula(cedulaProfesional);
                    nuevo.setFecha(fechaCita);
                    nuevo.setNombre(nombre);
                    pacienteRepo.save(nuevo);
                }
            } else if ("RespuestaSolicitud".equals(tipo)) {
                String respuesta = root.get("respuesta").asText();
                boolean resultado = Boolean.parseBoolean(respuesta);
                if (resultado) {
                    String fecha = root.get("fecha_permiso").asText();
                    OffsetDateTime odt = OffsetDateTime.parse(fecha);
                    Date fechaPermiso = Date.from(odt.toInstant());
                    mensaje.setFechaPermiso(fechaPermiso);
                }
                mensaje.setRespuesta(resultado);

                mensajeRepo.save(mensaje);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
