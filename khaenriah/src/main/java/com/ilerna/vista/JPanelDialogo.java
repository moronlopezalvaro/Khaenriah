package com.ilerna.vista;

import java.awt.*;
import javax.swing.*;

public class JPanelDialogo extends JPanel {

    private Image imagenFondo;
    private JTextArea textoDialogo;
    private JLabel nombrePersonaje;

    private Font fuente8Bits;

    public JPanelDialogo() {
        this.setLayout(null);

        // Intentar cargar la fuente 8 bits
        try {
            java.io.InputStream is = getClass().getResourceAsStream("/com/ilerna/resources/PressStart2P-Regular.ttf");
            if (is != null) {
                fuente8Bits = Font.createFont(Font.TRUETYPE_FONT, is);
                GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(fuente8Bits);
            } else {
                System.out.println("LOG: ¡No se encontró la fuente en el diálogo!");
                fuente8Bits = new Font("Arial", Font.BOLD, 14);
            }
        } catch (Exception e) {
            e.printStackTrace();
            fuente8Bits = new Font("Arial", Font.BOLD, 14);
        }

        // Cargar la imagen solo una vez
        ImageIcon icon = new ImageIcon(getClass().getResource("/com/ilerna/resources/colosonplanetadentro.jpg"));
        imagenFondo = icon.getImage();

        // Inicializar el nombre del personaje
        nombrePersonaje = new JLabel("Coloson");
        nombrePersonaje.setFont(fuente8Bits.deriveFont(Font.PLAIN, 18f));
        nombrePersonaje.setForeground(Color.YELLOW);
        nombrePersonaje.setBounds(250, 530, 400, 30);
        this.add(nombrePersonaje);

        // Inicializar el área de texto
        textoDialogo = new JTextArea();
        textoDialogo.setText("Solicito ayuda a la Patrulla Galáctica, mi planeta está en peligro.");
        textoDialogo.setFont(fuente8Bits.deriveFont(Font.PLAIN, 14f));
        textoDialogo.setForeground(Color.WHITE);

        // Hacerlo transparente y de solo lectura
        textoDialogo.setOpaque(false);
        textoDialogo.setEditable(false);
        textoDialogo.setFocusable(false);

        // Saltos de línea automáticos
        textoDialogo.setLineWrap(true);
        textoDialogo.setWrapStyleWord(true);

        // Posicionarlo en el panel (ajusta estos números luego)
        textoDialogo.setBounds(250, 560, 400, 100);

        this.add(textoDialogo);
    }

    public void setTexto(String nuevoTexto) {
        textoDialogo.setText(nuevoTexto);
    }

    public void setNombre(String nombre) {
        nombrePersonaje.setText(nombre);
    }

    public void setImagenFondo(String rutaImagen) {
        ImageIcon icon = new ImageIcon(getClass().getResource(rutaImagen));
        imagenFondo = icon.getImage();
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
    }
}
