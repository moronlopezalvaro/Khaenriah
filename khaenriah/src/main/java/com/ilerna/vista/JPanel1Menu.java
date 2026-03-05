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
        Dimension dimension = getSize();
        ImageIcon icon = new ImageIcon(getClass().getResource("/resources/Menu.png"));
        g.drawImage(icon.getImage(), 0, 0, dimension.width, dimension.height, this);
    }
}
