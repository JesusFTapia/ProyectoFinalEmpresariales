/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ProfesionalDAO;

import conexion.Conexion;
import conexion.IConexion;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import model.Profesional;

/**
 *
 * @author jl4ma
 */
public class ProfesionalDAO implements IProfesionalDAO{
    
    EntityManager em;

    public ProfesionalDAO(IConexion conexion) {
        this.em = conexion.crearConexion();
    }
    
    @Override
    public void inserciones(){
        em.getTransaction().begin();
        List<Profesional> profesionales = new ArrayList<>();
        profesionales.add(new Profesional("235633", "Adriana Gutiérrez"));
        profesionales.add(new Profesional("247122", "Diego Alcantar"));
        profesionales.add(new Profesional("245136", "Jesús Tapia"));
        profesionales.add(new Profesional("247313", "Alejandro Gomez"));
        profesionales.add(new Profesional("244903", "José Madero"));
        
        for(Profesional pro: profesionales){
            em.persist(pro);
        }
        em.getTransaction().commit();
    }
    
    @Override
    public boolean iniciarSesion(String cedula){
         Profesional proBuscado = em.find(Profesional.class, cedula);
         if (proBuscado != null) {
            return true;
        }else{
             return false;
         }
    }

    @Override
    public Profesional getProfesionalCedula(String cedula) {
        return em.find(Profesional.class, cedula);
    }
}
