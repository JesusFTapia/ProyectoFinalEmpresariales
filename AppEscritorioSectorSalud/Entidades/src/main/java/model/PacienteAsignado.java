package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author jl4ma
 */
@Entity
public class PacienteAsignado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String pacienteUuid;

    private String profesionalCedula;
    
    private String nombre;
    private Date fecha;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public String getProfesionalCedula() {
        return profesionalCedula;
    }

    public void setProfesionalCedula(String profesionalCedula) {
        this.profesionalCedula = profesionalCedula;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}