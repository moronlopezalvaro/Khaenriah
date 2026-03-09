package com.ilerna.vista;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class VentanaDialogo extends JFrame implements ActionListener {
    JButton botonSiguiente;

    public VentanaDialogo() {
        this.setTitle("KHAENRI'AH");
        this.setSize(new Dimension(1080, 720));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

        // Añadimos el panel que contiene la imagen de fondo
        JPanelDialogo panel = new JPanelDialogo();
        panel.setLayout(null);

        // Cargar la imagen original
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/com/ilerna/resources/siguiente.png"));

        // Reescalar la imagen
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(300, 100, Image.SCALE_SMOOTH);

        // Crear un nuevo icono con la imagen reescalada
        ImageIcon iconoBoton = new ImageIcon(imagenEscalada);

        // Crear el botón con la imagen
        botonSiguiente = new JButton(iconoBoton);

        // Ajustar el tamaño del botón al de la imagen reescalada
        botonSiguiente.setBounds(390, 573, 720, 100);

        // Quitar borde y fondo
        botonSiguiente.setBorderPainted(false);
        botonSiguiente.setContentAreaFilled(false);
        botonSiguiente.setFocusPainted(false);

        botonSiguiente.addActionListener(this);
        panel.add(botonSiguiente);
        this.add(panel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonSiguiente) {
            // Instanciar la nueva ventana de juego (ya se hace visible en su constructor)
            new VentanaJuego();

            // Cerrar la ventana de diálogo
            this.dispose();
        }
    }

}
