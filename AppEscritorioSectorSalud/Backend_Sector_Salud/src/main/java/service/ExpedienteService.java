/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author jl4ma
 */

import org.springframework.stereotype.Service;
import solicitudes.ApiClient;

@Service
public class ExpedienteService {

    private final ApiClient apiClient = new ApiClient();

    public String obtenerExpedientePorId(String idPaciente) throws Exception {
        // Aquí simulamos que primero se verifica la huella dactilar
        boolean huellaVerificada = simularLecturaHuella();

        if (!huellaVerificada) {
            throw new Exception("Autenticación fallida: huella no verificada.");
        }

        return apiClient.getExpedientePorId(idPaciente);
    }

    private boolean simularLecturaHuella() {
        // TODO: aquí se puede integrar con lector real, por ahora simulamos que sí
        return true;
    }
}
