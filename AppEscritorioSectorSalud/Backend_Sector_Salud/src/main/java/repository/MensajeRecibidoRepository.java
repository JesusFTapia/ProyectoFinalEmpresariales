/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

/**
 *
 * @author jl4ma
 */

import java.util.List;
import java.util.Optional;
import model.MensajeRecibido;
import model.PacienteAsignado;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MensajeRecibidoRepository extends JpaRepository<MensajeRecibido, Long> {
    List<MensajeRecibido> findByCedulaProfesional(String cedulaProfesional);
    
}
