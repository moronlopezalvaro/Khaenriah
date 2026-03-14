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
    private Clip clipMenu;

    public VentanaMenu() {

        this.setTitle("KHAENRI'AH");
        this.setSize(new Dimension(1080, 720));
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        java.net.URL logoUrl = getClass().getResource("/com/ilerna/resources/Logo.png");
        if (logoUrl != null) {
            this.setIconImage(new ImageIcon(logoUrl).getImage());
        }
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

        // Iniciar la música del menú
        ReproducirSonido("/com/ilerna/resources/musicainicio.wav");
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == botonStart) {
            reproducirSonidoClick();
            // Pasamos el clip a la ventana de diálogo para que no se corte la música
            VentanaDialogo ventanaDialogo = new VentanaDialogo(clipMenu);
            ventanaDialogo.setVisible(true);

            // Quitamos la referencia de esta ventana para que no se detenga al hacer dispose
            clipMenu = null;
            try { Thread.sleep(150); } catch (Exception ex) {}
            this.dispose();
        }
    }

    public void ReproducirSonido(String nombreSonido) {
        try {
            java.net.URL url = getClass().getResource(nombreSonido);
            if (url != null) {
                AudioInputStream bandaSonora = AudioSystem.getAudioInputStream(url);
                clipMenu = AudioSystem.getClip();
                clipMenu.open(bandaSonora);
                clipMenu.loop(Clip.LOOP_CONTINUOUSLY); // Bucle infinito
                clipMenu.start();
            } else {
                System.out.println("No se encontró " + nombreSonido);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ex) {
            System.out.println("Error al reproducir sonido: " + ex.getMessage());
        }
    }

    private void reproducirSonidoClick() {
        try {
            java.net.URL url = getClass().getResource("/com/ilerna/resources/SonidoClick.wav");
            if (url != null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
                Clip clipClick = AudioSystem.getClip();
                clipClick.open(audioStream);
                clipClick.start();
            }
        } catch (Exception ex) {
            System.out.println("Error al reproducir sonido click: " + ex.getMessage());
        }
    }
}
