/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;


/**
 *
 * @author jl4ma
 */
public class Conexion implements IConexion{
    
    @Override
    public EntityManager crearConexion() {
        try {
            // Creamos el EntityManagerFactory.
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("profesionales_db");
            
            // Creamos el EntityManager.
            EntityManager em = emf.createEntityManager();

            // Retornamos el EntityManager.
            return em;
        } catch (PersistenceException e) {
            System.err.println("Error al crear el EntityManager: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        }
}
