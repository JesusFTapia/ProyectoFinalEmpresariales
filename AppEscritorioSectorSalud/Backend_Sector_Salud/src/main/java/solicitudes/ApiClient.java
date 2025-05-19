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
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiClient {
    HttpClient client = HttpClient.newHttpClient();
    private String url="https://pruebaapi-sv1q.onrender.com/api/expedientes";
    
    public String getExpedientes() throws IOException, InterruptedException{
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url))
            .GET()
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
    
    public String getExpedientePorId(String id) throws IOException, InterruptedException{
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create(url+"/"+id))
            .GET()
            .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        return response.body();
    }
}
