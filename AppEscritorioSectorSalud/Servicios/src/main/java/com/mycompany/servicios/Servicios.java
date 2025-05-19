/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.servicios;

import fachada.Fachada;
import fachada.IFachada;
import fachada.MensajeRecibidoDTO;
import java.io.IOException;
import java.util.List;
import solicitudes.ApiClient;

/**
 *
 * @author jl4ma
 */
    public class Servicios {

        public static void main(String[] args) throws IOException, InterruptedException {

            IFachada f = new Fachada();
//            f.insercion();
//
//            System.out.println(f.iniciarSesion("244903"));
//        List<MensajeRecibidoDTO> mensajes = f.obtenerMensajesPorCedula("235633");
//
//        for (MensajeRecibidoDTO mensaje : mensajes) {
//            System.out.println(mensaje);
//        }
//        
        ApiClient cliente = new ApiClient();
        String expediente = cliente.getExpedientePorId("SP0LD7iOzNfNY804ZKLs6VA2vmh2", "244903");
        if(expediente.isBlank()){
            System.out.println("No tienes permiso");
        }else{
            System.out.println(expediente);
        }
//        if(f.enviarMensajeSolicitud("244903", "12345", "Dr. Madero")){
//            System.out.println("Se envio");
//        }else{
//            System.out.println("No se envio");
//        }

//          if(f.eliminarMensaje(7L)){
//              System.out.println("Eliminado");
//          }else{
//              System.out.println("No se elimino");
//          }
        }
        
        
    }
