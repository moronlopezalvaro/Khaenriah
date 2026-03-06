package com.ilerna.vista;

import java.awt.*;
import javax.swing.*;

public class JPanelDialogo extends JPanel {

    private Image imagenFondo;

    public JPanelDialogo() {
        this.setLayout(null);
        // Cargar la imagen solo una vez
        ImageIcon icon = new ImageIcon(getClass().getResource("/com/ilerna/resources/planeta.jpg"));
        imagenFondo = icon.getImage();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
    }
}
