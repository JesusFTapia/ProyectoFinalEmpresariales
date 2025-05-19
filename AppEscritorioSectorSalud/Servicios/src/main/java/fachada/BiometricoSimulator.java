package fachada;

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
