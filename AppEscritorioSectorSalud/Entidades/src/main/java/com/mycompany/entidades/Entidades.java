/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.entidades;

import ProfesionalDAO.IProfesionalDAO;
import ProfesionalDAO.ProfesionalDAO;
import conexion.Conexion;
import conexion.IConexion;

/**
 *
 * @author jl4ma
 */
public class Entidades {

    public static void main(String[] args) {
        IConexion con = new Conexion();
        IProfesionalDAO pro = new ProfesionalDAO(con);
        pro.inserciones();
    }
}
