package com.ilerna.vista;

import java.awt.Dimension;

import javax.swing.JFrame;

public class VentanaDialogo extends JFrame {
    public VentanaDialogo() {
        this.setTitle("KHAENRI'AH");
        this.setSize(new Dimension(1080, 720));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
    }

}
