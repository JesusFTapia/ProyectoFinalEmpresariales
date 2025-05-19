package presentacion;

import fachada.BiometricoSimulator;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

/**
 *
 * Clase que extiende de Java swing dialog para representar graficamente un dialogo sobre la autentificacion biometrica
 * 
 * @author Alejandro Gómez Vega 247313
 * @author Jesus Francisco Tapia Maldonado 245136
 * @author Jose Luis Madero Lopez 244903
 * @author Adriana Gutiérrez Robles 235633
 * @author Diego Alcantar Acosta 247122
 */
public class DlgAutenticacionBiometrica extends javax.swing.JDialog {

    private boolean autenticado = false;

    public DlgAutenticacionBiometrica(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblImagenHuella = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        btnVerificar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Por favor, coloque su huella dactilar en el lector");

        lblImagenHuella.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblImagenHuella.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/huellaD2.png"))); // NOI18N

        lblEstado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblEstado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblEstado.setText("Estado: Esperando huella dactilar");

        btnVerificar.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnVerificar.setText("Verificar Huella");
        btnVerificar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVerificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerificarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblImagenHuella, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblEstado, javax.swing.GroupLayout.DEFAULT_SIZE, 330, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnVerificar)
                .addGap(97, 97, 97))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addGap(26, 26, 26)
                .addComponent(lblImagenHuella)
                .addGap(18, 18, 18)
                .addComponent(lblEstado)
                .addGap(18, 18, 18)
                .addComponent(btnVerificar)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 330, 270));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnVerificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerificarActionPerformed
        verificarHuella();
    }//GEN-LAST:event_btnVerificarActionPerformed

    private void verificarHuella() {
        lblEstado.setText("Estado: Verificando huella...");
        
        //Simula un pequeño delay para que parezca que está procesando
        Timer timer = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                boolean huellaValida = BiometricoSimulator.validarHuella();

                if (huellaValida) {
                    lblEstado.setText("Estado: Huella verificada correctamente");
                    lblEstado.setForeground(new Color(0, 128, 0)); //Verde
                    autenticado = true;
                    Timer closeTimer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            dispose();
                        }
                    });
                    closeTimer.setRepeats(false);
                    closeTimer.start();
                } else {
                    lblEstado.setText("Estado: Huella inválida. Intente nuevamente.");
                    lblEstado.setForeground(Color.RED);
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public boolean isAutenticado() {
        return autenticado;
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnVerificar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblImagenHuella;
    // End of variables declaration//GEN-END:variables
}
