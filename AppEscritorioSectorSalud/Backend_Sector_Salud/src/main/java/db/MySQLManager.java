/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

/**
 *
 * @author jl4ma
 */
import java.sql.*;

public class MySQLManager {
    private static final String URL = "jdbc:mysql://localhost:3306/profesionales_db";
    private static final String USER = "root"; // tu usuario
    private static final String PASSWORD = "password"; // tu contraseña

    public static void insertPacienteAutorizado(String uuidPaciente) {
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String query = "INSERT INTO pacientes_autorizados (uuid) VALUES (?)";
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.setString(1, uuidPaciente);
            stmt.executeUpdate();
            System.out.println("Paciente autorizado guardado: " + uuidPaciente);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
