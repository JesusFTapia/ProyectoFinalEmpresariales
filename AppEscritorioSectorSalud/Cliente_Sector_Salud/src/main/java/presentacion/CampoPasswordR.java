package presentacion;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * Clase que representa el campo de texto redondeado de contraseña con placeholder
 * 
 * @author Alejandro Gómez Vega 247313
 * @author Jesus Francisco Tapia Maldonado 245136
 * @author Jose Luis Madero Lopez 244903
 * @author Adriana Guitiérrez Robles 235633
 * @author Diego Alcantar Acosta 247122
 */
public class CampoPasswordR extends JPasswordField {

    private String placeholder;

    public CampoPasswordR() {
        this(20);
    }

    public CampoPasswordR(int columns) {
        super(columns);
        setOpaque(false);
        setBorder(new EmptyBorder(5, 10, 5, 10));
        initListener();
    }

    public CampoPasswordR(String placeholder) {
        this(20);
        this.placeholder = placeholder;
    }

    public CampoPasswordR(int columns, String placeholder) {
        this(columns);
        this.placeholder = placeholder;
    }

    private void initListener() {
        getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                repaint();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                repaint();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo redondeado
        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10));

        super.paintComponent(g);

        // Dibujar placeholder si no hay texto
        if (getPassword().length == 0 && placeholder != null) {
            g2.setColor(Color.GRAY);
            FontMetrics fm = g2.getFontMetrics();
            int textY = (getHeight() - fm.getHeight()) / 2 + fm.getAscent();
            g2.drawString(placeholder, getInsets().left, textY);
        }

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setColor(Color.LIGHT_GRAY);
        g2.draw(new RoundRectangle2D.Float(0, 0, getWidth() - 1, getHeight() - 1, 10, 10));
        g2.dispose();
    }

    public String getPlaceholder() {
        return placeholder;
    }

    public void setPlaceholder(String placeholder) {
        this.placeholder = placeholder;
        repaint();
    }
}
