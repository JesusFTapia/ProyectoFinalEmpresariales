/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fachada;

import java.util.List;
import model.Profesional;

/**
 *
 * @author jl4ma
 */
public interface IFachada {
    
    public void insercion();
    public boolean iniciarSesion(String cedula);
    public List<MensajeRecibidoDTO> obtenerMensajesPorCedula(String cedula);
    Profesional obtenerProfesional(String cedula);
    public boolean enviarMensajeSolicitud(String cedula, String paciente, String nombre);
    public List<PacienteAsignadoDTO> obtenerPacientesAsignados(String cedula);
    public boolean eliminarMensaje(Long id);
    public void marcarMensajesComoVistos(List<MensajeRecibidoDTO> mensajes);

}
