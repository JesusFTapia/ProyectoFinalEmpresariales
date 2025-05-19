package presentacion;

import fachada.Fachada;
import fachada.IFachada;
import fachada.MensajeRecibidoDTO;
import fachada.PacienteAsignadoDTO;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import model.Profesional;

/**
 *
 * Clase que extiende de Java swing para representar graficamente el menu
 * principal
 *
 * @author Alejandro Gómez Vega 247313
 * @author Jesus Francisco Tapia Maldonado 245136
 * @author Jose Luis Madero Lopez 244903
 * @author Adriana Gutiérrez Robles 235633
 * @author Diego Alcantar Acosta 247122
 */
public class FrmPrincipal extends javax.swing.JFrame {

    private Profesional profesionalSesion;
    private List<MensajeRecibidoDTO> mensajes;
    IFachada fachada;

    public FrmPrincipal(Profesional profesionalSesion, List<MensajeRecibidoDTO> mensajes) {
        initComponents();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        this.fachada = new Fachada();
        this.profesionalSesion = profesionalSesion;
        this.mensajes = mensajes;

        lblNombreDoctor.setText("Dr. " + profesionalSesion.getNombre());
        lblCedulaDoctor.setText("Cédula: " + profesionalSesion.getCedula());
        

        if (contarNotificaciones() == 0) {
            lblNotificacionesNuevas.setText("Sin notificaciones nuevas");
            lblNotificacion1.setText(" ");
            lblNotificacion2.setText(" ");
        } else if (contarNotificaciones() == 1) {
            lblNotificacionesNuevas.setText(contarNotificaciones() + " nueva");
            lblNotificacion1.setText("- " + mensajes.getLast().getTipoMensaje());
            lblNotificacion2.setText(" ");
        } else {
            lblNotificacionesNuevas.setText(contarNotificaciones() + " nuevas");
            lblNotificacion1.setText("- " + mensajes.get(mensajes.size() - 2).getTipoMensaje()); // Último mensaje recibido
            lblNotificacion2.setText("- " + mensajes.get(mensajes.size() - 1).getTipoMensaje()); // Penúltimo mensaje recibido
        }
        if(!mensajes.isEmpty()){
            fachada.marcarMensajesComoVistos(mensajes);
        }
        if(contarNotificacionesCitas() == 0){
            lblCantPacientes.setText("Sin notificaciones");
            lblProximaCita.setText("");
            lblPacienteProximo.setText("");
        }else if(contarNotificacionesCitas() == 1){
            lblCantPacientes.setText("1 Paciente");
            lblProximaCita.setText(formatter.format(mensajes.get(0).getFechaCita()));
            lblPacienteProximo.setText("Paciente: "+ mensajes.get(0).getNombre());
        }
//        else{
//            lblNotificacionesNuevas.setText(contarNotificacionesCitas() + " nuevas");
//            lblNotificacion1.setText("- " + formatter.format(mensajes.get(mensajes.size() - 2).getTipoMensaje())); // Último mensaje recibido
//            lblNotificacion2.setText("- " + mensajes.get(mensajes.size() - 1).getNombre()); // Penúltimo mensaje recibido
//        }
    }
    
    public int contarNotificaciones() {
        int cantidadN = 0;
        for (MensajeRecibidoDTO mensaje : mensajes) {
            cantidadN += 1;
        }
        return cantidadN;
    }
    public int contarNotificacionesCitas() {
        int cantidadN = 0;
        for (MensajeRecibidoDTO mensaje : mensajes) {
            if(mensaje.getTipoMensaje().equalsIgnoreCase("AgendarCita")){
               cantidadN += 1; 
            }
            
        }
        return cantidadN;
    }

    public void mostrarMensajes() {
        for (MensajeRecibidoDTO mensaje : mensajes) {
            if(mensaje.getEstado().equalsIgnoreCase("Sin Ver")){
            String textoM = "Paciente: " + mensaje.getNombre() + "\nTipo: " + mensaje.getTipoMensaje();
            JOptionPane.showMessageDialog(this, textoM, "Nueva notificación", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
//    public void mostrarMensajes() {
//    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//    String textoM;
//
//    for (MensajeRecibidoDTO mensaje : mensajes) {
//        if (mensaje.getEstado().equalsIgnoreCase("Sin Ver")) {
//            textoM = ""; // Reiniciar el mensaje por cada iteración
//
//            if (mensaje.getTipoMensaje().equalsIgnoreCase("AgendarCita")) {
//                // Asegurarse de que la fecha no sea null
//                if (mensaje.getFechaCita() != null) {
//                    // Restar 1 día a la fecha de la cita
//                    Calendar calendar = Calendar.getInstance();
//                    calendar.setTime(mensaje.getFechaCita());
//                    calendar.add(Calendar.DAY_OF_MONTH, -1);
//                    Date fechaModificada = calendar.getTime();
//
//                    // Formatear fechas por separado
//                    String fechaCitaFormateada = formatter.format(mensaje.getFechaCita());
//                    String fechaLimiteFormateada = formatter.format(fechaModificada);
//
//                    textoM = "Paciente: " + mensaje.getNombre()
//                           + "\nTipo: " + mensaje.getTipoMensaje()
//                           + "\nFecha de la cita: " + fechaCitaFormateada
//                           + "\n!!!! Tienes hasta el día: " + fechaLimiteFormateada + " para solicitar el expediente del paciente !!!!";
//                } else {
//                    textoM = "Error: La fecha de la cita no está disponible para el paciente " + mensaje.getNombre();
//                }
//
//            } else if (mensaje.getTipoMensaje().equalsIgnoreCase("RespuestaSolicitud")) {
//                if (mensaje.isRespuesta()) {
//                    if (mensaje.getFechaPermiso() != null) {
//                        String fechaPermisoFormateada = formatter.format(mensaje.getFechaPermiso());
//
//                        textoM = "!!! Solicitud de Expediente ACEPTADA !!!"
//                               + "\nPaciente: " + mensaje.getNombre()
//                               + "\nTipo: " + mensaje.getTipoMensaje()
//                               + "\nTienes solo hasta esta fecha para ver el expediente: " + fechaPermisoFormateada;
//                    } else {
//                        textoM = "!!! Solicitud de Expediente ACEPTADA !!!"
//                               + "\nPaciente: " + mensaje.getNombre()
//                               + "\nTipo: " + mensaje.getTipoMensaje()
//                               + "\n(No se recibió la fecha límite para ver el expediente)";
//                    }
//                } else {
//                    textoM = "!!! Solicitud de Expediente DENEGADA !!!"
//                           + "\nPaciente: " + mensaje.getNombre()
//                           + "\nTipo: " + mensaje.getTipoMensaje()
//                           + "\nSolicítalo de nuevo.";
//                }
//            }
//
//            // Mostrar el mensaje solo si se construyó textoM
//            if (!textoM.isEmpty()) {
//                JOptionPane.showMessageDialog(this, textoM, "Nueva notificación", JOptionPane.INFORMATION_MESSAGE);
//            }
//        }
//    }
//}

//    public FrmPrincipal(Profesional profesionalSesion) {
//        initComponents();
//        this.profesionalSesion = profesionalSesion;
//        lblNombreDoctor.setText("Dr. " + profesionalSesion.getNombre());
//        lblCedulaDoctor.setText("Cédula: " + profesionalSesion.getCedula());
//    }
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
        panelCitas = new presentacion.PanelRound();
        jLabel2 = new javax.swing.JLabel();
        lblCantPacientes = new javax.swing.JLabel();
        lblProximaCita = new javax.swing.JLabel();
        lblPacienteProximo = new javax.swing.JLabel();
        btnVerAgenda = new javax.swing.JButton();
        panelNotificaciones = new presentacion.PanelRound();
        jLabel3 = new javax.swing.JLabel();
        lblNotificacionesNuevas = new javax.swing.JLabel();
        lblNotificacion1 = new javax.swing.JLabel();
        lblNotificacion2 = new javax.swing.JLabel();
        btnVerTodas = new javax.swing.JButton();
        panelActReciente = new presentacion.PanelRound();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Secretaría de Salud - Inicio");
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

        btnInicio.setBackground(new java.awt.Color(157, 36, 73));
        btnInicio.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        btnInicio.setForeground(new java.awt.Color(255, 255, 255));
        btnInicio.setText("Inicio");

        btnAgendaCitas.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
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
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bienvenido al Sistema de Expediente Clínico Electrónico");

        jSeparator1.setForeground(new java.awt.Color(204, 204, 204));

        panelCitas.setBackground(new java.awt.Color(241, 241, 241));
        panelCitas.setRoundBottomLeft(10);
        panelCitas.setRoundBottomRight(10);
        panelCitas.setRoundTopLeft(10);
        panelCitas.setRoundTopRight(10);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Citas de Hoy");

        lblCantPacientes.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblCantPacientes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblCantPacientes.setText("4 pacientes");

        lblProximaCita.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblProximaCita.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblProximaCita.setText("Próxima: 11:30 AM");

        lblPacienteProximo.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblPacienteProximo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblPacienteProximo.setText("Paciente: Adriana Gutiérrez Robles");

        btnVerAgenda.setBackground(new java.awt.Color(241, 241, 241));
        btnVerAgenda.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnVerAgenda.setText("Ver Agenda");
        btnVerAgenda.setBorderPainted(false);
        btnVerAgenda.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVerAgenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerAgendaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCitasLayout = new javax.swing.GroupLayout(panelCitas);
        panelCitas.setLayout(panelCitasLayout);
        panelCitasLayout.setHorizontalGroup(
            panelCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelCitasLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCantPacientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblProximaCita, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE)
                    .addComponent(lblPacienteProximo, javax.swing.GroupLayout.DEFAULT_SIZE, 263, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(panelCitasLayout.createSequentialGroup()
                .addGap(76, 76, 76)
                .addComponent(btnVerAgenda, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelCitasLayout.setVerticalGroup(
            panelCitasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCitasLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(lblCantPacientes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblProximaCita)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPacienteProximo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(btnVerAgenda)
                .addGap(26, 26, 26))
        );

        panelNotificaciones.setBackground(new java.awt.Color(241, 241, 241));
        panelNotificaciones.setRoundBottomLeft(10);
        panelNotificaciones.setRoundBottomRight(10);
        panelNotificaciones.setRoundTopLeft(10);
        panelNotificaciones.setRoundTopRight(10);

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Notificaciones");

        lblNotificacionesNuevas.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblNotificacionesNuevas.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNotificacionesNuevas.setText("3 nuevas");

        lblNotificacion1.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblNotificacion1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNotificacion1.setText("- Acceso a expediente");

        lblNotificacion2.setFont(new java.awt.Font("Segoe UI", 0, 13)); // NOI18N
        lblNotificacion2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNotificacion2.setText("- Acceso a expediente");

        btnVerTodas.setBackground(new java.awt.Color(241, 241, 241));
        btnVerTodas.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnVerTodas.setText("Ver Todas");
        btnVerTodas.setBorderPainted(false);
        btnVerTodas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnVerTodas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerTodasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelNotificacionesLayout = new javax.swing.GroupLayout(panelNotificaciones);
        panelNotificaciones.setLayout(panelNotificacionesLayout);
        panelNotificacionesLayout.setHorizontalGroup(
            panelNotificacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 275, Short.MAX_VALUE)
            .addGroup(panelNotificacionesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelNotificacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNotificacionesNuevas, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNotificacion1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblNotificacion2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelNotificacionesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnVerTodas, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(76, 76, 76))
        );
        panelNotificacionesLayout.setVerticalGroup(
            panelNotificacionesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelNotificacionesLayout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel3)
                .addGap(18, 18, 18)
                .addComponent(lblNotificacionesNuevas)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNotificacion1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblNotificacion2)
                .addGap(24, 24, 24)
                .addComponent(btnVerTodas)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelActReciente.setBackground(new java.awt.Color(241, 241, 241));

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Actividad Reciente");

        javax.swing.GroupLayout panelActRecienteLayout = new javax.swing.GroupLayout(panelActReciente);
        panelActReciente.setLayout(panelActRecienteLayout);
        panelActRecienteLayout.setHorizontalGroup(
            panelActRecienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
        );
        panelActRecienteLayout.setVerticalGroup(
            panelActRecienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelActRecienteLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap(160, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout panelRound1Layout = new javax.swing.GroupLayout(panelRound1);
        panelRound1.setLayout(panelRound1Layout);
        panelRound1Layout.setHorizontalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jSeparator1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRound1Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(panelActReciente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(panelRound1Layout.createSequentialGroup()
                        .addComponent(panelCitas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(panelNotificaciones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20))
        );
        panelRound1Layout.setVerticalGroup(
            panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRound1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(panelRound1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(panelCitas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(panelNotificaciones, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(30, 30, 30)
                .addComponent(panelActReciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(21, Short.MAX_VALUE))
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
                .addContainerGap(36, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 0, 650, 550));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnExpedientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExpedientesActionPerformed
        FrmExpedientes frmExpedientes = new FrmExpedientes(profesionalSesion, mensajes);
        frmExpedientes.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnExpedientesActionPerformed

    private void btnAgendaCitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgendaCitasActionPerformed
        FrmAgendaCitas frmAgendaCitas = new FrmAgendaCitas(profesionalSesion, mensajes);
        frmAgendaCitas.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnAgendaCitasActionPerformed

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        FrmInicioSesion inicio = new FrmInicioSesion();
        inicio.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    private void btnNotificacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNotificacionesActionPerformed
        FrmNotificaciones frmNotificaciones = new FrmNotificaciones(profesionalSesion, mensajes);
        frmNotificaciones.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnNotificacionesActionPerformed

    private void btnVerAgendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerAgendaActionPerformed
        FrmAgendaCitas frmAgendaCitas = new FrmAgendaCitas(profesionalSesion, mensajes);
        frmAgendaCitas.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVerAgendaActionPerformed

    private void btnVerTodasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVerTodasActionPerformed
        FrmNotificaciones frmNotificaciones = new FrmNotificaciones(profesionalSesion, mensajes);
        frmNotificaciones.setVisible(true);
        dispose();
    }//GEN-LAST:event_btnVerTodasActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAgendaCitas;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnExpedientes;
    private javax.swing.JButton btnInicio;
    private javax.swing.JButton btnNotificaciones;
    private javax.swing.JButton btnVerAgenda;
    private javax.swing.JButton btnVerTodas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel lblCantPacientes;
    private javax.swing.JLabel lblCedulaDoctor;
    private javax.swing.JLabel lblNombreDoctor;
    private javax.swing.JLabel lblNotificacion1;
    private javax.swing.JLabel lblNotificacion2;
    private javax.swing.JLabel lblNotificacionesNuevas;
    private javax.swing.JLabel lblPacienteProximo;
    private javax.swing.JLabel lblProximaCita;
    private presentacion.PanelRound panelActReciente;
    private presentacion.PanelRound panelCitas;
    private presentacion.PanelRound panelNotificaciones;
    private presentacion.PanelRound panelRound1;
    // End of variables declaration//GEN-END:variables
}
