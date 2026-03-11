package com.ilerna.vista;

import javax.swing.JFrame;

import java.awt.Dimension;

public class VentanaJuego extends JFrame {

    public VentanaJuego() {
        this.setTitle("KHAENRI'AH");
        this.setSize(new Dimension(1080, 720));
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        JPanel2Juego panel = new JPanel2Juego();
        this.add(panel);

        this.setVisible(true);
        panel.requestFocus();
    }

}
