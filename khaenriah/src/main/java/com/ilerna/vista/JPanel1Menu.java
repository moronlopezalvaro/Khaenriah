package com.ilerna.vista;

import java.awt.*;

import javax.swing.*;

public class JPanel1Menu extends JPanel {

    private Image imagenFondo;

    public JPanel1Menu() {
        this.setLayout(null);
        // Cargar la imagen solo una vez
        ImageIcon icon = new ImageIcon(getClass().getResource("/com/ilerna/resources/portada.jpg"));
        imagenFondo = icon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
    }
}
