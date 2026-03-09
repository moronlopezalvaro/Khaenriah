package com.ilerna.vista;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import com.ilerna.modelos.*;

public class JPanel2Juego extends JPanel implements ActionListener, KeyListener {
    private Image imagenFondo;
    private Image imgNave;
    private Image imgEnemigo;
    private Image imgBoss;

    private Timer timer;
    private Nave nave;
    private List<Enemigo> enemigos;
    private List<Proyectil> proyectiles;
    private Jefe boss;

    private int nivel = 1;
    private int enemigosAEliminar = 10;
    private boolean juegoTerminado = false;
    private boolean victoria = false;
    private Random random = new Random();

    // Controles
    private boolean izquierda = false, derecha = false, arriba = false, abajo = false, disparo = false;
    private int cooldownDisparo = 0;

    public JPanel2Juego() {
        this.setLayout(null);
        this.setFocusable(true);
        this.addKeyListener(this);

        // Cargar imágenes
        try {
            imagenFondo = new ImageIcon(getClass().getResource("/com/ilerna/resources/planeta.jpg")).getImage();
            imgNave = new ImageIcon(getClass().getResource("/com/ilerna/resources/nave.png")).getImage();
            imgEnemigo = new ImageIcon(getClass().getResource("/com/ilerna/resources/geocentinela.png")).getImage();
            imgBoss = new ImageIcon(getClass().getResource("/com/ilerna/resources/bossFinal.png")).getImage();
        } catch (Exception e) {
            System.out.println("Error al cargar imágenes: " + e.getMessage());
        }

        nave = new Nave(500, 600, 7, 100, 60, 60);
        enemigos = new ArrayList<>();
        proyectiles = new ArrayList<>();

        timer = new Timer(20, this);
        timer.start();

        iniciarNivel();
    }

    private void iniciarNivel() {
        enemigos.clear();
        proyectiles.clear();
        boss = null;
        enemigosAEliminar = 10 + (nivel * 5);

        if (nivel == 10) {
            boss = new Jefe(440, 50, 4, 500); // Vida alta para el boss
        }

        timer.start();

    }

}
