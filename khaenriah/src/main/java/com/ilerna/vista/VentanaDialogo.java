package com.ilerna.vista;

import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

public class VentanaDialogo extends JFrame {
    public VentanaDialogo() {
        this.setTitle("KHAENRI'AH");
        this.setSize(new Dimension(1080, 720));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        // Añadimos el panel que contiene la imagen de fondo
        JPanelDialogo panel = new JPanelDialogo();
        this.add(panel);

        // Es importante llamar a setVisible al final para que los componentes se
        // rendericen bien
        this.setVisible(true);

        // Crear el botón con la imagen
        JButton botonJugar = new JButton(iconoJugar);

        // Ajustar el tamaño del botón al de la imagen reescalada
        botonJugar.setBounds(390, 500, 300, 100);
        botonJugar.addActionListener(this);
        panel.add(botonJugar);

    }

    public void actionPerformed(ActionEvent e) {
        Object botonJugar;
        if (e.getSource() == botonJugar) {
            // Instanciar y hacer visible la nueva ventana de diálogo
            VentanaJuego ventanaJuego = new VentanaJuego();
            ventanaJuego.setVisible(true);

            // Cerrar la ventana del menú actual
            this.dispose();
        }
    }

}
