package main;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import presentacion.FrmInicioSesion;

/**
 *
 * Clase main que inicializa el programa
 *
 * @author Alejandro Gómez Vega 247313
 * @author Jesus Francisco Tapia Maldonado 245136
 * @author Jose Luis Madero Lopez 244903
 * @author Adriana Guitiérrez Robles 235633
 * @author Diego Alcantar Acosta 247122
 */
public class MainPresentacion {

    /**
     * Metodo principal que inicializa la presentación
     */
    public static void main(String[] args) {
        try {
            for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception e) {
        }
        FrmInicioSesion frmInicio = new FrmInicioSesion();
        frmInicio.setVisible(true);
    }
}
