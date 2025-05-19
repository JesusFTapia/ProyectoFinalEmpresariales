/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package ProfesionalDAO;

import model.Profesional;

/**
 *
 * @author jl4ma
 */
public interface IProfesionalDAO {
    
    public void inserciones();
    public boolean iniciarSesion(String cedula);
    Profesional getProfesionalCedula(String cedula);
}
