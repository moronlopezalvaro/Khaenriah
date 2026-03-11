package com.ilerna.vista;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class VentanaMenu extends JFrame implements ActionListener {

    JPanel1Menu panel = new JPanel1Menu();
    JButton botonStart;

    public VentanaMenu() {

        this.setTitle("KHAENRI'AH");
        this.setSize(new Dimension(1080, 720));
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel.setLayout(null);

        // Cargar la imagen original
        ImageIcon iconoOriginal = new ImageIcon(getClass().getResource("/com/ilerna/resources/StartIcono.png"));

        // Reescalar la imagen al tamaño que quieras (por ejemplo 150x50)
        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(300, 200, Image.SCALE_SMOOTH);

        // Crear un nuevo icono con la imagen reescalada
        ImageIcon iconoStart = new ImageIcon(imagenEscalada);

        // Crear el botón con la imagen
        botonStart = new JButton(iconoStart);

        // Ajustar el tamaño del botón al de la imagen reescalada
        botonStart.setBounds(440, 555, 190, 90);

        // Quitar borde y fondo
        botonStart.setBorderPainted(false);
        botonStart.setContentAreaFilled(false);
        botonStart.setFocusPainted(false);

        botonStart.addActionListener(this);
        panel.add(botonStart);
        this.add(panel);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonStart) {
            // Instanciar y hacer visible la nueva ventana de diálogo
            VentanaDialogo ventanaDialogo = new VentanaDialogo();
            ventanaDialogo.setVisible(true);

            // Cerrar la ventana del menú actual
            this.dispose();
        }
    }

    public void ReproducirSonido(String nombreSonido) {
        try {
            AudioInputStream BandaSonora = AudioSystem.getAudioInputStream(new File(nombreSonido).getAbsoluteFile());
            Clip clip = AudioSystem.getClip();
            clip.open(BandaSonora);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            System.out.println("Error al reproducir sonido");
        }
    }

}
