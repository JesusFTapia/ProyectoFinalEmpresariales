/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package presentacion;

import java.util.Date;
import java.util.List;

/**
 *
 * @author jl4ma
 */
public class ExpedienteBean {
    
    Date fechaCreacion;
   
    List<String> diagnosticos;
    List<String> vacunas;
    List<String> radiografias;
    List<String> alergias;

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
    String notasAdicionales;

    public ExpedienteBean(Date fechaCreacion, List<String> diagnosticos, List<String> vacunas, List<String> radiografias, List<String> alergias, String notasAdicionales) {
        this.fechaCreacion = fechaCreacion;
        this.diagnosticos = diagnosticos;
        this.vacunas = vacunas;
        this.radiografias = radiografias;
        this.alergias = alergias;
        this.notasAdicionales = notasAdicionales;
    }

    public ExpedienteBean() {
    }

    @Override
    public String toString() {
        return "ExpedienteBean{" + "fechaCreacion=" + fechaCreacion + ", diagnosticos=" + diagnosticos + ", vacunas=" + vacunas + ", radiografias=" + radiografias + ", alergias=" + alergias + ", notasAdicionales=" + notasAdicionales + '}';
    }
    
    
   
   

 

    public List<String> getDiagnosticos() {
        return diagnosticos;
    }

    public void setDiagnosticos(List<String> diagnosticos) {
        this.diagnosticos = diagnosticos;
    }

    public List<String> getVacunas() {
        return vacunas;
    }

    public void setVacunas(List<String> vacunas) {
        this.vacunas = vacunas;
    }

    public List<String> getRadiografias() {
        return radiografias;
    }

    public void setRadiografias(List<String> radiografias) {
        this.radiografias = radiografias;
    }

    public List<String> getAlergias() {
        return alergias;
    }

    public void setAlergias(List<String> alergias) {
        this.alergias = alergias;
    }

    public String getNotasAdicionales() {
        return notasAdicionales;
    }

    public void setNotasAdicionales(String notasAdicionales) {
        this.notasAdicionales = notasAdicionales;
    }
   
   
   
    
}
