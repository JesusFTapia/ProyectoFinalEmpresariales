package presentacion;

import com.fasterxml.jackson.databind.ObjectMapper;
import fachada.MensajeRecibidoDTO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.text.SimpleDateFormat;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.imageio.ImageIO;
import model.Profesional;

public class FrmVerExpediente extends JFrame {
    Profesional profesionalSesion;
    private List<MensajeRecibidoDTO> mensajes;
    private String expedienteJson;
    private String nombrePaciente;
    private String idPaciente;
    private ExpedienteBean expediente;
    SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    

    public FrmVerExpediente(String expedienteJson, String nombrePaciente, String idPaciente, Profesional pro, List<MensajeRecibidoDTO> mensajes) {
        initComponents();
        this.profesionalSesion = pro;
        this.mensajes = mensajes;
        this.expedienteJson = expedienteJson;
        this.nombrePaciente = nombrePaciente;
        this.idPaciente = idPaciente;
        this.expediente = convertirJsonAExpediente(expedienteJson);

        lblId.setText(idPaciente);
        lblNombre.setText(nombrePaciente);
        lblfecha.setText(formato.format(expediente.getFechaCreacion()));

        cargarListas();
    }
    public FrmVerExpediente(ExpedienteBean expediente, String nombrePaciente, String idPaciente, Profesional pro, List<MensajeRecibidoDTO> mensajes) {
        initComponents();
        this.profesionalSesion = pro;
        this.mensajes = mensajes;
        this.nombrePaciente = nombrePaciente;
        this.idPaciente = idPaciente;
        this.expediente = expediente;

        lblId.setText(idPaciente);
        lblNombre.setText(nombrePaciente);
        lblfecha.setText(formato.format(expediente.getFechaCreacion()));
        cargarListas();

    }
    

    private void cargarListas() {
        cargarLista(vacunas, expediente.getVacunas());
        cargarLista(diagnosticos, expediente.getDiagnosticos());
        cargarLista(alergias, expediente.getAlergias());
        mostrarRadiografias(expediente.getRadiografias());
    }

    private void cargarLista(JList<String> list, List<String> datos) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (String dato : datos) {
            model.addElement(dato);
        }
        list.setModel(model);
    }

    private void mostrarRadiografias(List<String> imagenesBase64) {
        radiografias.removeAll();
        radiografias.setLayout(new GridLayout(0, 2, 10, 10));

        for (String base64 : imagenesBase64) {
            try {
                byte[] bytes = Base64.getDecoder().decode(base64);
                BufferedImage img = ImageIO.read(new ByteArrayInputStream(bytes));
                if (img != null) {
                    JLabel label = new JLabel(new ImageIcon(img.getScaledInstance(200, 200, Image.SCALE_SMOOTH)));
                    radiografias.add(label);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        radiografias.revalidate();
        radiografias.repaint();
    }

    private ExpedienteBean convertirJsonAExpediente(String json) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, ExpedienteBean.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
//    private void cargarDatosExpediente() {
//        try {
//            System.out.println("Contenido de expedienteJson:\n" + expedienteJson);
//            JSONObject jsonExpediente = new JSONObject(expedienteJson);
//            
//            // Cargar información general
//            agregarCampoInfoGeneral("ID Paciente", jsonExpediente.optString("pacienteId", "N/A"));
//            agregarCampoInfoGeneral("Nombre", jsonExpediente.optString("nombre", "N/A"));
//            agregarCampoInfoGeneral("Diagnóstico General", jsonExpediente.optString("diagnostico", "N/A"));
//            
//            // Si hay más información en el expediente, puedes agregarla aquí
//            
//            // Nota: Como no conocemos la estructura exacta del JSON que devuelve la API,
//            // estamos usando los campos que vimos en la clase Expediente (pacienteId, nombre, diagnostico)
//            // Las demás pestañas se quedarán con el mensaje "No hay datos disponibles" hasta que
//            // tengamos la estructura exacta del JSON
//            
//        } catch (JSONException e) {
//            JOptionPane.showMessageDialog(this, 
//                    "Error al procesar el expediente: " + e.getMessage(),
//                    "Error", JOptionPane.ERROR_MESSAGE);
//            e.printStackTrace();
//        }
//    }
    
//    private void cargarSeccion(JSONObject jsonExpediente, String seccion, JPanel panel) {
//        JSONArray items = jsonExpediente.optJSONArray(seccion);
//        if (items != null && items.length() > 0) {
//            for (int i = 0; i < items.length(); i++) {
//                JSONObject item = items.optJSONObject(i);
//                if (item != null) {
//                    JPanel panelItem = crearPanelItem(item);
//                    panel.add(panelItem);
//                    panel.add(Box.createVerticalStrut(10));
//                }
//            }
//        } else {
//            agregarMensajeSinDatos(panel);
//        }
//    }
    
//    private JPanel crearPanelItem(JSONObject item) {
//        JPanel panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
//        panel.setBorder(BorderFactory.createCompoundBorder(
//                BorderFactory.createLineBorder(new Color(200, 200, 200)),
//                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
//        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 150));
//        
//        // Fecha
//        String fecha = item.optString("fecha", "Fecha no especificada");
//        JLabel lblFecha = new JLabel("Fecha: " + fecha);
//        lblFecha.setFont(new Font("Arial", Font.BOLD, 12));
//        
//        // Descripción
//        String descripcion = item.optString("descripcion", "");
//        JTextArea txtDescripcion = new JTextArea(descripcion);
//        txtDescripcion.setEditable(false);
//        txtDescripcion.setLineWrap(true);
//        txtDescripcion.setWrapStyleWord(true);
//        txtDescripcion.setBackground(panel.getBackground());
//        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
//        scrollDescripcion.setBorder(BorderFactory.createEmptyBorder());
//        
//        // Médico
//        String medico = item.optString("medico", "No especificado");
//        JLabel lblMedico = new JLabel("Médico: " + medico);
//        
//        panel.add(lblFecha);
//        panel.add(Box.createVerticalStrut(5));
//        panel.add(scrollDescripcion);
//        panel.add(Box.createVerticalStrut(5));
//        panel.add(lblMedico);
//        
//        return panel;
//    }
//    
//    private void agregarCampoInfoGeneral(String etiqueta, String valor) {
//        JPanel panel = new JPanel();
//        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
//        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
//        panel.setBorder(BorderFactory.createEmptyBorder(5, 0, 5, 0));
//        
//        JLabel lblEtiqueta = new JLabel(etiqueta + ": ");
//        lblEtiqueta.setFont(new Font("Arial", Font.BOLD, 12));
//        
//        JLabel lblValor = new JLabel(valor);
//        
//        panel.add(lblEtiqueta);
//        panel.add(Box.createHorizontalStrut(10));
//        panel.add(lblValor);
//        panel.add(Box.createHorizontalGlue());
//        
//        panelInfoGeneral.add(panel);
//    }
//    
//    private void agregarMensajeSinDatos(JPanel panel) {
//        JLabel lblNoData = new JLabel("No hay datos disponibles");
//        lblNoData.setForeground(Color.GRAY);
//        lblNoData.setAlignmentX(Component.CENTER_ALIGNMENT);
//        
//        panel.add(Box.createVerticalGlue());
//        panel.add(lblNoData);
//        panel.add(Box.createVerticalGlue());
//    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jFileChooser1 = new javax.swing.JFileChooser();
        jFileChooser2 = new javax.swing.JFileChooser();
        jColorChooser1 = new javax.swing.JColorChooser();
        jFrame1 = new javax.swing.JFrame();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JLabel();
        jMenuItem1 = new javax.swing.JMenuItem();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jScrollPane8 = new javax.swing.JScrollPane();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblId = new javax.swing.JLabel();
        lblfecha = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnVacunas = new javax.swing.JButton();
        btnDiagnosticos = new javax.swing.JButton();
        btnAlergias = new javax.swing.JButton();
        btnRadiografias = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        vacunas = new javax.swing.JList<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        diagnosticos = new javax.swing.JList<>();
        jScrollPane6 = new javax.swing.JScrollPane();
        alergias = new javax.swing.JList<>();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane7 = new javax.swing.JScrollPane();
        radiografias = new javax.swing.JList<>();
        jButton1 = new javax.swing.JButton();

        javax.swing.GroupLayout jFrame1Layout = new javax.swing.GroupLayout(jFrame1.getContentPane());
        jFrame1.getContentPane().setLayout(jFrame1Layout);
        jFrame1Layout.setHorizontalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        jFrame1Layout.setVerticalGroup(
            jFrame1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        jLabel1.setText("jLabel1");
        jScrollPane1.setViewportView(jLabel1);

        jMenuItem1.setText("jMenuItem1");

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setText("EXPEDIENTE:");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel3.setText("Nombre:");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel4.setText("ID:");

        jLabel5.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel5.setText("Fecha:");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblNombre.setText("José Madero");

        lblId.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblId.setText("V2fDWB1zSeMunfkwI4MeSS3PESk1");

        lblfecha.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        lblfecha.setText("2025-05-19");

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel7.setText("Diagnosticos");

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel8.setText("Alergias:");

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel9.setText("Vacunas:");

        btnVacunas.setText("Agregar");
        btnVacunas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVacunasActionPerformed(evt);
            }
        });

        btnDiagnosticos.setText("Agregar");
        btnDiagnosticos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDiagnosticosActionPerformed(evt);
            }
        });

        btnAlergias.setText("Agregar");
        btnAlergias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAlergiasActionPerformed(evt);
            }
        });

        btnRadiografias.setText("Agregar");
        btnRadiografias.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRadiografiasActionPerformed(evt);
            }
        });

        vacunas.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane3.setViewportView(vacunas);

        diagnosticos.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane5.setViewportView(diagnosticos);

        alergias.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane6.setViewportView(alergias);

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel10.setText("Radiografias:");

        radiografias.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane7.setViewportView(radiografias);

        jButton1.setText("Volver");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addGap(251, 251, 251)
                            .addComponent(btnVacunas))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnDiagnosticos))
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.Alignment.LEADING))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5))
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombre)
                            .addComponent(lblId)
                            .addComponent(lblfecha)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRadiografias))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAlergias))
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 425, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(34, 34, 34)
                        .addComponent(jLabel2)))
                .addContainerGap(29, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jButton1))
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblNombre))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lblId))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(lblfecha))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(btnVacunas))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(btnDiagnosticos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(btnAlergias))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnRadiografias)
                    .addComponent(jLabel10))
                .addGap(24, 24, 24)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnVacunasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVacunasActionPerformed
        FrmAgregarVacunas vacunas = new FrmAgregarVacunas(expediente, profesionalSesion, mensajes, nombrePaciente, idPaciente);
        vacunas.setLocationRelativeTo(null);
        vacunas.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVacunasActionPerformed

    private void btnRadiografiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRadiografiasActionPerformed
        FrmAgregarRadiografia radio = new FrmAgregarRadiografia(expediente, profesionalSesion, mensajes, nombrePaciente, idPaciente);
        radio.setLocationRelativeTo(null);
        radio.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnRadiografiasActionPerformed

    private void btnAlergiasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAlergiasActionPerformed
        
        FrmAgregarAlergias alergias = new FrmAgregarAlergias(expediente, profesionalSesion, mensajes, nombrePaciente, idPaciente);
        alergias.setLocationRelativeTo(null);
        alergias.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnAlergiasActionPerformed

    private void btnDiagnosticosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDiagnosticosActionPerformed
        FrmAgregarDiagnostico diag = new FrmAgregarDiagnostico(expediente, profesionalSesion, mensajes, nombrePaciente, idPaciente);
        diag.setLocationRelativeTo(null);
        diag.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnDiagnosticosActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
        FrmPrincipal frmPrincipal = new FrmPrincipal(profesionalSesion, mensajes);
            frmPrincipal.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> alergias;
    private javax.swing.JButton btnAlergias;
    private javax.swing.JButton btnDiagnosticos;
    private javax.swing.JButton btnRadiografias;
    private javax.swing.JButton btnVacunas;
    private javax.swing.JList<String> diagnosticos;
    private javax.swing.JButton jButton1;
    private javax.swing.JColorChooser jColorChooser1;
    private javax.swing.JFileChooser jFileChooser1;
    private javax.swing.JFileChooser jFileChooser2;
    private javax.swing.JFrame jFrame1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList<String> jList1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JLabel lblId;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblfecha;
    private javax.swing.JList<String> radiografias;
    private javax.swing.JList<String> vacunas;
    // End of variables declaration//GEN-END:variables
}
