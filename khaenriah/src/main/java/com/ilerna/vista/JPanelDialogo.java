package com.ilerna.vista;

import java.awt.*;
import javax.swing.*;

public class JPanelDialogo extends JPanel {

    private Image imagenFondo;
    private JTextArea textoDialogo;

    public JPanelDialogo() {
        this.setLayout(null);
        // Cargar la imagen solo una vez
        ImageIcon icon = new ImageIcon(getClass().getResource("/com/ilerna/resources/dialogocoloson.jpg"));
        imagenFondo = icon.getImage();

        // Inicializar el área de texto
        textoDialogo = new JTextArea();
        textoDialogo.setText("¡Hola! Soy Coloson. Bienvenido a Khaenriah. \nAquí es donde empieza tu aventura.");
        textoDialogo.setFont(new Font("Arial", Font.BOLD, 18));
        textoDialogo.setForeground(Color.WHITE);

        // Hacerlo transparente y de solo lectura
        textoDialogo.setOpaque(false);
        textoDialogo.setEditable(false);
        textoDialogo.setFocusable(false);

        // Saltos de línea automáticos
        textoDialogo.setLineWrap(true);
        textoDialogo.setWrapStyleWord(true);

        // Posicionarlo en el panel (ajusta estos números luego)
        textoDialogo.setBounds(50, 400, 700, 100);

        this.add(textoDialogo);
    }

    public void setTexto(String nuevoTexto) {
        textoDialogo.setText(nuevoTexto);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
    }
}
