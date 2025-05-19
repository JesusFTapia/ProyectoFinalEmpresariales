/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fachada;

import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author jl4ma
 */
public class MensajeRecibidoDTO {

    private Long id;

    private String cedulaProfesional;

    private String pacienteUuid;

    private String tipoMensaje;

    private String nombre;

    private Date fechaCita;

    private Date fechaPermiso;

    private boolean respuesta;
    
    private String estado;

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getCedulaProfesional() {
        return cedulaProfesional;
    }

    public void setCedulaProfesional(String cedulaProfesional) {
        this.cedulaProfesional = cedulaProfesional;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPacienteUuid() {
        return pacienteUuid;
    }

    public void setPacienteUuid(String pacienteUuid) {
        this.pacienteUuid = pacienteUuid;
    }

    public String getTipoMensaje() {
        return tipoMensaje;
    }

    public void setTipoMensaje(String tipoMensaje) {
        this.tipoMensaje = tipoMensaje;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaCita() {
        return fechaCita;
    }

    public void setFechaCita(Date fechaCita) {
        this.fechaCita = fechaCita;
    }

    public Date getFechaPermiso() {
        return fechaPermiso;
    }

    public void setFechaPermiso(Date fechaPermiso) {
        this.fechaPermiso = fechaPermiso;
    }

    public boolean isRespuesta() {
        return respuesta;
    }

    public void setRespuesta(boolean respuesta) {
        this.respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "MensajeRecibidoDTO{" + "id=" + id + ", cedulaProfesional=" + cedulaProfesional + ", pacienteUuid=" + pacienteUuid + ", tipoMensaje=" + tipoMensaje + ", nombre=" + nombre + ", fechaCita=" + fechaCita + ", fechaPermiso=" + fechaPermiso + ", respuesta=" + respuesta + '}';
    }

}
