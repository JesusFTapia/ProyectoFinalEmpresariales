/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javax.persistence.Entity;
import javax.persistence.Id;


/**
 *
 * @author jl4ma
 */

@Entity
public class Profesional {
    @Id
    private String cedula;
    private String nombre;

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Profesional(String cedula, String nombre) {
        this.cedula = cedula;
        this.nombre = nombre;
    }

    public Profesional() {
    }
    
    
    
    
}
    

