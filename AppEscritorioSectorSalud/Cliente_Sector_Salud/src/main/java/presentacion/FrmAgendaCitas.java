package presentacion;

import fachada.MensajeRecibidoDTO;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.Profesional;

/**
 *
 * Clase que extiende de Java swing para representar graficamente las agenda de citas
 * 
 * @author Alejandro Gómez Vega 247313
 * @author Jesus Francisco Tapia Maldonado 245136
 * @author Jose Luis Madero Lopez 244903
 * @author Adriana Gutiérrez Robles 235633
 * @author Diego Alcantar Acosta 247122
 */
public class FrmAgendaCitas extends javax.swing.JFrame {
    Profesional profesionalSesion;
    private List<MensajeRecibidoDTO> mensajes;

    public FrmAgendaCitas(Profesional profesionalSesion, List<MensajeRecibidoDTO> mensajes) {
        initComponents();
        this.mensajes = mensajes;
        this.profesionalSesion = profesionalSesion;
        lblNombreDoctor.setText("Dr. " + profesionalSesion.getNombre());
        lblCedulaDoctor.setText("Cédula: " + profesionalSesion.getCedula());
        llenarTablaCitas(mensajes);
    }
    
    private void llenarTablaCitas(List<MensajeRecibidoDTO> mensajes) {
        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new String[]{"Nombre paciente", "ID paciente", "Fecha"});

        SimpleDateFormat formatoFecha = new SimpleDateFormat("dd-MM-yyyy HH:mm");

        for (MensajeRecibidoDTO mensaje : mensajes) {
            if ("AgendarCita".equals(mensaje.getTipoMensaje()) && mensaje.getFechaCita() != null) {
                String nombre = mensaje.getNombre();
                String idPaciente = mensaje.getPacienteUuid();
                String fecha = formatoFecha.format(mensaje.getFechaCita());

                modelo.addRow(new Object[]{nombre, idPaciente, fecha});
            }
        }
        tablaCitas.setModel(modelo);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblNombreDoctor = new javax.swing.JLabel();
        lblCedulaDoctor = new javax.swing.JLabel();
        btnInicio = new javax.swing.JButton();
        btnAgendaCitas = new javax.swing.JButton();
        btnExpedientes = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();
        btnNotificaciones = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        panelRound1 = new presentacion.PanelRound();
        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaCitas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Secretaría de Salud - Agenda de Citas");
        setResizable(false);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBackground(new java.awt.Color(157, 36, 73));

        lblNombreDoctor.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblNombreDoctor.setForeground(new java.awt.Color(255, 255, 255));
        lblNombreDoctor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNombreDoctor.setText("Dr. Juan Pérez");

        lblCedulaDoctor.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCedulaDoctor.setForeground(new java.awt.Color(255, 255, 255));
        lblCedulaDoctor.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCedulaDoctor.setText("Cédula: 12345678");

        btnInicio.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnInicio.setText("Inicio");
        btnInicio.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInicioActionPerformed(evt);
            }
        });

        btnAgendaCitas.setBackground(new java.awt.Color(157, 36, 73));
        btnAgendaCitas.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnAgendaCitas.setForeground(new java.awt.Color(255, 255, 255));
        btnAgendaCitas.setText("Agenda de Citas");
        btnAgendaCitas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAgendaCitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgendaCitasActionPerformed(evt);
            }
        });

        btnExpedientes.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnExpedientes.setText("Expedientes");
        btnExpedientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExpedientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExpedientesActionPerformed(evt);
            }
        });

        btnCerrarSesion.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnCerrarSesion.setText("Cerrar Sesión");
        btnCerrarSesion.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCerrarSesionActionPerformed(evt);
            }
        });

        btnNotificaciones.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnNotificaciones.setText("Notificaciones");
        btnNotificaciones.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNotificaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNotificacionesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblNombreDoctor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(lblCedulaDoctor, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAgendaCitas, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExpedientes, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnNotificaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(lblNombreDoctor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblCedulaDoctor)
                .addGap(32, 32, 32)
                .addComponent(btnInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAgendaCitas, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnExpedientes, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnNotificaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 206, Short.MAX_VALUE)
                .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(32, 32, 32))
        );

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 200, 550));

        jPanel2.setBackground(new java.awt.Color(241, 241, 241));

        panelRound1.setBackground(new java.awt.Color(255, 255, 255));
        panelRound1.setRoundBottomLeft(10);
        panelRound1.setRoundBottomRight(10);
        panelRound1.setRoundTopLeft(10);
        panelRound1.setRoundTopRight(10);

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel1.setText("Citas Agendadas");

        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));

        tablaCitas.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tablaCitas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tablaCitas.setEnabled(false);
        jScrollPane2.setViewportView(tablaCitas);

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 578, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 395, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addComponent(panelRound1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 650, 550));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInicioActionPerformed
        FrmPrincipal frmPrincipal = new FrmPrincipal(profesionalSesion, mensajes);
        frmPrincipal.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnInicioActionPerformed

    private void btnExpedientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExpedientesActionPerformed
        FrmExpedientes frmExpedientes = new FrmExpedientes(profesionalSesion, mensajes);
        frmExpedientes.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnExpedientesActionPerformed

    private void btnAgendaCitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgendaCitasActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAgendaCitasActionPerformed

    private void btnNotificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNotificacionesActionPerformed
        FrmNotificaciones frmNotificaciones = new FrmNotificaciones(profesionalSesion, mensajes);
        frmNotificaciones.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnNotificacionesActionPerformed

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        FrmInicioSesion inicio = new FrmInicioSesion();
        inicio.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgendaCitas;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnExpedientes;
    private javax.swing.JButton btnInicio;
    private javax.swing.JButton btnNotificaciones;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblCedulaDoctor;
    private javax.swing.JLabel lblNombreDoctor;
    private presentacion.PanelRound panelRound1;
    private javax.swing.JTable tablaCitas;
    // End of variables declaration//GEN-END:variables
}
