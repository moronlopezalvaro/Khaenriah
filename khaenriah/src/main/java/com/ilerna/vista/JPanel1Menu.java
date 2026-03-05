package com.ilerna.vista;

import java.awt.*;

import javax.swing.*;

public class JPanel1Menu extends JPanel {

    public JPanel1Menu() {
        this.setLayout(null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ImageIcon icon = new ImageIcon(getClass().getResource("/com/ilerna/resources/Menu.png"));
        g.drawImage(icon.getImage(), 0, 0, getWidth(), getHeight(), this);
    }
}
