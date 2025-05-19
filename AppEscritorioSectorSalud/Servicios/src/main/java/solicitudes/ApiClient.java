/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package solicitudes;

/**
 *
 * @author jl4ma
 */
import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ApiClient {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String url = "https://pruebaapi-sv1q.onrender.com/api";

    public String getExpedientes() throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getExpedientePorId(String idPaciente, String cedulaProfesional) throws IOException, InterruptedException {
        // Ajusta los nombres de los parámetros a los que tu API espera: idDocto y idPaciente
        String endpoint = String.format("%s/expediente?idDoctor=%s&idPaciente=%s", url, cedulaProfesional, idPaciente);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public String getPacientesAsignados(String cedulaProfesional) throws IOException, InterruptedException {
        String endpoint = String.format("%s/asignados?cedula=%s", url, cedulaProfesional);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(endpoint))
                .GET()
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }

    public boolean agregarContenidoAExpediente(String idPaciente, String tipo, String contenido) {
        try {
            if (tipo.equalsIgnoreCase("vacunas") || tipo.equalsIgnoreCase("diagnosticos") || tipo.equalsIgnoreCase("radiografias") || tipo.equalsIgnoreCase("alergias")) {
                String json = String.format("""
            {
                "idPaciente": "%s",
                "tipo": "%s",
                "contenido": "%s"
            }
        """, idPaciente, tipo, contenido);
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(url + "/expedientes/actualizar"))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(json))
                        .build();

                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

                if (response.statusCode() == 200 || response.statusCode() == 201) {
                    return true;
                } else {
                    System.err.println("Error al agregar contenido al expediente. Código: " + response.statusCode());
                    System.err.println("Respuesta: " + response.body());
                    return false;
                }
            }
            System.err.println("Tipo no valido");
            return false;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean solicitarExpediente(String cedulaProfesional, String pacienteUuid, String nombre) {
        try {
            String endpoint = String.format("%s/profesional/solicitar-expediente?cedulaProfesional=%s&pacienteUuid=%s&nombre=%s",
                    url,
                    URLEncoder.encode(cedulaProfesional, StandardCharsets.UTF_8),
                    URLEncoder.encode(pacienteUuid, StandardCharsets.UTF_8),
                    URLEncoder.encode(nombre, StandardCharsets.UTF_8));

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(endpoint))
                    .POST(HttpRequest.BodyPublishers.noBody())
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.statusCode() == 200;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }

}
