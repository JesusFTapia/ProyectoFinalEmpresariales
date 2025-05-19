/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fachada;

import ProfesionalDAO.IProfesionalDAO;
import ProfesionalDAO.ProfesionalDAO;
import com.fasterxml.jackson.databind.ObjectMapper;
import conexion.Conexion;
import conexion.IConexion;
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import model.Profesional;
import org.eclipse.persistence.internal.libraries.asm.TypeReference;

/**
 *
 * @author jl4ma
 */
public class Fachada implements IFachada {

    private IConexion conexion;
    IProfesionalDAO pro;

    public Fachada() {
        conexion = new Conexion();
        pro = new ProfesionalDAO(conexion);
    }

    @Override
    public void insercion() {
        if (!pro.iniciarSesion("244903")) {
            pro.inserciones();
        } else {
            System.out.println("SIN INSERCION");
        }
    }

    @Override
    public boolean iniciarSesion(String cedula) {
        return pro.iniciarSesion(cedula);
    }

    private static final HttpClient httpClient = HttpClient.newHttpClient();
    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public List<MensajeRecibidoDTO> obtenerMensajesPorCedula(String cedula) {
        try {
            // Crear la solicitud HTTP GET
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/mensajes/profesional/" + cedula))
                    .GET()
                    .build();

            // Enviar la solicitud y obtener la respuesta
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Verificar si la respuesta fue exitosa (código 200 OK)
            if (response.statusCode() == 200) {
                // Deserializar la respuesta JSON a una lista de objetos DTO
                return mapper.readValue(response.body(), new com.fasterxml.jackson.core.type.TypeReference<List<MensajeRecibidoDTO>>() {
                });
            } else {
                // Manejo de error si la respuesta no es exitosa
                System.err.println("Error al obtener los mensajes. Código de respuesta: " + response.statusCode());
                return Collections.emptyList();
            }

        } catch (IOException e) {
            // Manejo de excepciones de entrada/salida
            e.printStackTrace();
            return Collections.emptyList();
        } catch (InterruptedException e) {
            // Manejo de interrupciones de hilo
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public boolean eliminarMensaje(Long id) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/mensajes/" + id))
                    .DELETE()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Acepta 200 (OK) o 204 (No Content) como éxito
            if (response.statusCode() == 200 || response.statusCode() == 204) {
                return true;
            } else {
                System.err.println("Error al eliminar el mensaje. Código de respuesta: " + response.statusCode());
                return false;
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean enviarMensajeSolicitud(String cedula, String paciente, String nombre) {
        try {
            // Construir los parámetros en formato application/x-www-form-urlencoded
            String requestBody = "cedulaProfesional=" + URLEncoder.encode(cedula, StandardCharsets.UTF_8)
                    + "&pacienteUuid=" + URLEncoder.encode(paciente, StandardCharsets.UTF_8)
                    + "&nombre=" + URLEncoder.encode(nombre, StandardCharsets.UTF_8);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/profesional/solicitar-expediente"))
                    .header("Content-Type", "application/x-www-form-urlencoded")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Verifica si fue exitosa la solicitud (HTTP 200)
            if (response.statusCode() == 200) {
                System.out.println("Solicitud enviada exitosamente: " + response.body());
                return true;
            } else {
                System.err.println("Error al enviar la solicitud. Código de respuesta: " + response.statusCode());
                return false;
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Profesional obtenerProfesional(String cedula) {
        return pro.getProfesionalCedula(cedula);
    }

    @Override
    public List<PacienteAsignadoDTO> obtenerPacientesAsignados(String cedula) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/api/pacientes/profesional/" + cedula))
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == 200) {
                return mapper.readValue(response.body(), new com.fasterxml.jackson.core.type.TypeReference<List<PacienteAsignadoDTO>>() {
                });
            } else {
                System.err.println("Error al obtener los pacientes asignados. Código de respuesta: " + response.statusCode());
                return Collections.emptyList();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public void marcarMensajesComoVistos(List<MensajeRecibidoDTO> mensajes) {

        for (MensajeRecibidoDTO mensaje : mensajes) {
            if(mensaje.getEstado().equalsIgnoreCase("Sin Ver")){
            Long id = mensaje.getId();
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("http://localhost:8080/api/mensajes/" + id + "/visto"))
                        .PUT(HttpRequest.BodyPublishers.noBody())
                        .build();

                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200) {
                    System.out.println("Mensaje con ID " + id + " marcado como visto.");
                } else {
                    System.err.println("Error al marcar mensaje ID " + id + ". Código: " + response.statusCode());
                }
            } catch (IOException | InterruptedException e) {
                System.err.println("Excepción al marcar mensaje ID " + id + ": " + e.getMessage());
            }
        }
        }
    }

}
