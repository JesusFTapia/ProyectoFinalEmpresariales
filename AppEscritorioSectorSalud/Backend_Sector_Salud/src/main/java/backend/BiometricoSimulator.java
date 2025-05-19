/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

/**
 *
 * @author jl4ma
 */
import java.util.Random;

public class BiometricoSimulator {

    public static boolean validarHuella() {
        Random random = new Random();
        return random.nextBoolean(); // true = huella válida, false = huella inválida
    }
}
