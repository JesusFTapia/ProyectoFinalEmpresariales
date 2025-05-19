/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import rabbitConfig.RabbitMQConfig;
import rabbitConfig.RabbitMQConfigListener;

/**
 *
 * @author jl4ma
 */
@Service
@RequiredArgsConstructor
public class RabbitMQSenderService {

    private final RabbitTemplate rabbitTemplate;

    public void enviarSolicitudExpediente(String cedulaProfesional, String pacienteUuid, String nombre) {
        String mensaje = String.format("{\"cedulaProfesional\":\"%s\",\"pacienteUuid\":\"%s\",\"nombre\":\"%s\",\"tipo\":\"SolicitudExpediente\"}",
                cedulaProfesional, pacienteUuid, nombre);

        // Envío del mensaje a RabbitMQ
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.EXPEDIENTE_ROUTING_KEY, mensaje);
    }
    
    public void enviarAgendarCita(String cedulaProfesional, String pacienteUuid, String nombre, String fecha) {
        String mensaje = String.format(
            "{" +
            "\"tipo\":\"AgendarCita\"," +
            "\"nombre\":\"%s\"," +
            "\"idProfesional\":\"%s\"," +
            "\"idPaciente\":\"%s\"," +
            "\"fecha\":\"%s\"" +
            "}",
            nombre, cedulaProfesional, pacienteUuid, fecha
        );

        rabbitTemplate.convertAndSend(RabbitMQConfigListener.EXCHANGE_NAME, RabbitMQConfigListener.EXPEDIENTE_ROUTING_KEY, mensaje);
    }

    public void enviarRespuestaSolicitud(String cedulaProfesional, String pacienteUuid, String nombre, boolean respuesta, String fechaPermiso) {
        String mensaje = String.format(
            "{" +
            "\"tipo\":\"RespuestaSolicitud\"," +
            "\"nombre\":\"%s\"," +
            "\"idProfesional\":\"%s\"," +
            "\"idPaciente\":\"%s\"," +
            "\"respuesta\":\"%s\"," +
            "\"fechaPermiso\":\"%s\"" +
            "}",
            nombre, cedulaProfesional, pacienteUuid, String.valueOf(respuesta), fechaPermiso
        );

        rabbitTemplate.convertAndSend(RabbitMQConfigListener.EXCHANGE_NAME, RabbitMQConfigListener.EXPEDIENTE_ROUTING_KEY, mensaje);
    }
}

