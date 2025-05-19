package fachada;

import java.util.Date;

/**
 *
 * @author adria
 */
public class PacienteAsignadoDTO {
    
    private Long id;
    private String pacienteUuid;
    private String profesionalCedula;
    private String nombre;
    private Date fecha;

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

    public String getProfesionalCedula() {
        return profesionalCedula;
    }

    public void setProfesionalCedula(String profesionalCedula) {
        this.profesionalCedula = profesionalCedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
