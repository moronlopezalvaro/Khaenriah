package com.ilerna.vista;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.AudioInputStream;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class VentanaDialogo extends JFrame implements ActionListener {
    JButton botonSiguiente;
    JPanelDialogo panel;
    int estadoDialogo = 0;
    private Clip clipMusica;

    public VentanaDialogo(Clip clip) {
        this.clipMusica = clip;
        this.setTitle("KHAENRI'AH");
        this.setSize(new Dimension(1080, 720));
        this.setUndecorated(true);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        java.net.URL logoUrl = getClass().getResource("/com/ilerna/resources/Logo.png");
        if (logoUrl != null) {
            ImageIcon logoIcon = new ImageIcon(logoUrl);
            this.setIconImage(logoIcon.getImage());
        }

        // Añadimos el panel que contiene la imagen de fondo
        panel = new JPanelDialogo();
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
        botonSiguiente.setBounds(390, 603, 720, 100);

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
            reproducirSonidoClick();
            if (estadoDialogo == 0) {
                panel.setImagenFondo("/com/ilerna/resources/champiplanetadentro.jpg");
                panel.setNombre("Champi");
                panel.setTexto("Hola, soy Champi, piloto de élite de la Patrulla Galáctica.");
                estadoDialogo++;
            } else if (estadoDialogo == 1) {
                panel.setImagenFondo("/com/ilerna/resources/colosonplanetadentro.jpg");
                panel.setNombre("Coloson");
                panel.setTexto(
                        "Hola, soy Coloson, mi planeta Khaenri'ah está siendo atacado por el malvado conquistador Zaroth.");
                estadoDialogo++;
            } else if (estadoDialogo == 2) {
                panel.setImagenFondo("/com/ilerna/resources/champi2planetadentro.jpg");
                panel.setNombre("Champi");
                panel.setTexto("¿Eh?... ¿Zaroth?...");
                estadoDialogo++;
            } else if (estadoDialogo == 3) {
                panel.setImagenFondo("/com/ilerna/resources/champiplanetadentro.jpg");
                panel.setNombre("Champi");
                panel.setTexto("Tranquilo Coloson, dejas el trabajo en manos del mejor piloto de la galaxia.");
                estadoDialogo++;
            } else if (estadoDialogo == 4) {
                panel.setImagenFondo("/com/ilerna/resources/coloson2planetadentro.jpg");
                panel.setNombre("Coloson");
                panel.setTexto("Gracias Champi, suerte en tu aventura.");
                estadoDialogo++;
            } else if (estadoDialogo == 5) {
                panel.setImagenFondo("/com/ilerna/resources/khaenriah.jpg");
                panel.setNombre("Khaenri'ah");
                panel.setTexto("• Sector: Sector Fronterizo Z-9.\n" + "• Sistema Estelar: Sistema Binario de Kálix.\n"
                        + "• Ubicación: borde exterior del Cinturón de Ónice.");
                estadoDialogo++;
            } else if (estadoDialogo == 6) {
                panel.setImagenFondo("/com/ilerna/resources/champiplanetafuera.jpg");
                panel.setNombre("Champi");
                panel.setTexto("¡Zaroth! He venido a acabar con tu maldita tiranía.");
                estadoDialogo++;
            } else if (estadoDialogo == 7) {
                panel.setImagenFondo("/com/ilerna/resources/zarothplanetafuera.jpg");
                panel.setNombre("Zaroth");
                panel.setTexto(
                        "JAJAJAJA, MALDITO PATRULLERO GALÁCTICO, ACABARÉ CONTIGO COMO TODOS LOS QUE HAN INTENTADO DETENERME.");
                estadoDialogo++;
            } else if (estadoDialogo == 8) {
                panel.setImagenFondo("/com/ilerna/resources/zaroth2planetafuera.jpg");
                panel.setNombre("Zaroth");
                panel.setTexto("GEOCENTINELAS, ACABAD CON ESTE DESCEREBRADO PILOTO.");
                estadoDialogo++;
            } else if (estadoDialogo == 9) {
                panel.setImagenFondo("/com/ilerna/resources/geocentinelaplanetafuera.jpg");
                panel.setNombre("Geocentinelas");
                panel.setTexto("СОРАЙЯ, ОДОБРИ НАС!!!");
                estadoDialogo++;
            } else {
                // Detener la música del menú justo antes de empezar la partida
                if (clipMusica != null && clipMusica.isRunning()) {
                    clipMusica.stop();
                }
                new VentanaJuego();
                try { Thread.sleep(150); } catch (Exception ex) {}
                this.dispose();
            }
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
