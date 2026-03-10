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
    JPanelDialogo panel;
    int estadoDialogo = 0;

    public VentanaDialogo() {
        this.setTitle("KHAENRI'AH");
        this.setSize(new Dimension(1080, 720));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setResizable(false);

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
            if (estadoDialogo == 0) {
                panel.setImagenFondo("/com/ilerna/resources/dialogochampi.jpg");
                panel.setTexto("Hola, soy Champi, piloto de élite de la Patrulla.");
                estadoDialogo++;
            } else if (estadoDialogo == 1) {
                panel.setImagenFondo("/com/ilerna/resources/dialogocoloson.jpg");
                panel.setTexto(
                        "Hola, soy Coloson, mi planeta Khaenri'ah está siendo atacado por el malvado conquistador Zaroth.");
                estadoDialogo++;
            } else if (estadoDialogo == 2) {
                panel.setImagenFondo("/com/ilerna/resources/dialogochampi.jpg");
                panel.setTexto("Tranquilo Coloson, dejas el trabajo en manos del mejor piloto de la galaxia.");
                estadoDialogo++;
            } else if (estadoDialogo == 3) {
                panel.setImagenFondo("/com/ilerna/resources/dialogocoloson.jpg");
                panel.setTexto("Gracias Champi, suerte en tu aventura.");
                estadoDialogo++;
            } else if (estadoDialogo == 4) {
                panel.setImagenFondo("/com/ilerna/resources/dialogochampi.jpg");
                panel.setTexto("Zaroth, he venido a acabar con tu maldita tiranía.");
                estadoDialogo++;
            } else if (estadoDialogo == 5) {
                panel.setImagenFondo("/com/ilerna/resources/dialogozaroth.jpg");
                panel.setTexto(
                        "JAJAJAJA, MALDITO PATRULLERO GALÁCTICO, ACABARÉ CONTIGO COMO TODOS LOS QUE HAN INTENTADO DETENERME.");
                estadoDialogo++;
            } else if (estadoDialogo == 6) {
                panel.setImagenFondo("/com/ilerna/resources/dialogozaroth.jpg");
                panel.setTexto("GEOCENTINELAS, ACABAD CON ESTE DESCEREBRADO PILOTO.");
                estadoDialogo++;
            } else if (estadoDialogo == 7) {

                panel.setImagenFondo("/com/ilerna/resources/dialogogeocentinela.jpg");
                panel.setTexto("GEOCENTINELAS: ¡A la orden, amo Zaroth! ¡Eliminaremos al intruso!");
                estadoDialogo++;
            } else {

                new VentanaJuego();
                this.dispose();
            }
        }
    }

}
