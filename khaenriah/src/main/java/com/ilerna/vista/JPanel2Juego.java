package com.ilerna.vista;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import com.ilerna.modelos.*;

public class JPanel2Juego extends JPanel implements ActionListener, KeyListener, MouseListener {
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
    private boolean izquierda = false, derecha = false, disparo = false;
    private int cooldownDisparo = 0;

    public JPanel2Juego() {
        this.setLayout(null);
        this.setFocusable(true);
        this.addKeyListener(this);
        this.addMouseListener(this);

        // Cargar imágenes
        try {
            imagenFondo = new ImageIcon(getClass().getResource("/com/ilerna/resources/planeta.jpg")).getImage();
            imgNave = new ImageIcon(getClass().getResource("/com/ilerna/resources/nave.png")).getImage();
            imgEnemigo = new ImageIcon(getClass().getResource("/com/ilerna/resources/geocentinela.png")).getImage();
            imgBoss = new ImageIcon(getClass().getResource("/com/ilerna/resources/bossFinal.png")).getImage();
        } catch (Exception e) {
            System.out.println("Error al cargar imágenes: " + e.getMessage());
        }

        nave = new Nave(500, 600, 15, 100, 60, 60);
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
        enemigosAEliminar = 10 + (nivel * 2);

        if (nivel == 10) {
            boss = new Jefe(440, 50, 4, 500); // Vida alta para el boss
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar fondo
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);

        if (juegoTerminado) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 50));
            String msg = victoria ? "¡VICTORIA FINAL!" : "GAME OVER";
            g.drawString(msg, getWidth() / 2 - 150, getHeight() / 2);
            g.setFont(new Font("Arial", Font.BOLD, 20));
            g.drawString("Nivel alcanzado: " + nivel, getWidth() / 2 - 80, getHeight() / 2 + 50);
            return;
        }

        // Dibujar Nave
        g.drawImage(imgNave, nave.x, nave.y, nave.ancho, nave.alto, this);

        // Dibujar Enemigos
        for (Enemigo e : enemigos) {
            g.drawImage(imgEnemigo, (int) e.x, (int) e.y, e.ancho, e.alto, this);
        }

        // Dibujar Boss
        if (boss != null) {
            g.drawImage(imgBoss, boss.x, boss.y, boss.ancho, boss.alto, this);
            // Barra de vida Boss
            g.setColor(Color.RED);
            g.fillRect(boss.x, boss.y - 20, boss.ancho, 10);
            g.setColor(Color.GREEN);
            g.fillRect(boss.x, boss.y - 20, (int) (boss.ancho * (boss.vida / 500.0)), 10);
        }

        // Dibujar Proyectiles
        g.setColor(Color.YELLOW);
        for (Proyectil p : proyectiles) {
            g.fillRect((int) p.x, (int) p.y, p.ancho, p.alto);
        }

        // Interfaz de Usuario (HUD)
        dibujarHUD(g);
    }

    private void dibujarHUD(Graphics g) {
        // Barra de vida jugador
        g.setColor(Color.GRAY);
        g.fillRect(20, 20, 200, 20);
        g.setColor(Color.GREEN);
        g.fillRect(20, 20, nave.vida * 2, 20);
        g.setColor(Color.WHITE);
        g.drawRect(20, 20, 200, 20);
        g.drawString("VIDA: " + nave.vida, 20, 55);

        // Nivel
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("NIVEL: " + nivel, getWidth() - 150, 40);
        if (nivel < 10) {
            g.drawString("RESTANTES: " + enemigosAEliminar, getWidth() - 150, 70);
        } else {
            g.drawString("¡BATALLA FINAL!", getWidth() - 150, 70);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (juegoTerminado)
            return;

        actualizarNave();
        actualizarProyectiles();
        actualizarEnemigos();
        actualizarBoss();
        verificarColisiones();

        if (nivel < 10 && enemigosAEliminar <= 0 && enemigos.isEmpty()) {
            nivel++;
            iniciarNivel();
        }

        if (nave.vida <= 0) {
            juegoTerminado = true;
            victoria = false;
        }

        repaint();
    }

    private void actualizarNave() {
        if (izquierda && nave.x > 0)
            nave.x -= nave.velocidad;
        if (derecha && nave.x < getWidth() - nave.ancho)
            nave.x += nave.velocidad;

        if (disparo && cooldownDisparo <= 0) {
            proyectiles.add(new Proyectil(nave.x + (nave.ancho / 2) - 5, nave.y, 10, 10, 20));
            cooldownDisparo = 15;
        }
        if (cooldownDisparo > 0)
            cooldownDisparo--;
    }

    private void actualizarProyectiles() {
        Iterator<Proyectil> it = proyectiles.iterator();
        while (it.hasNext()) {
            Proyectil p = it.next();
            p.mover();
            if (p.y < -50)
                it.remove();
        }
    }

    private void actualizarEnemigos() {
        // Spawn de enemigos
        if (nivel < 10 && enemigosAEliminar > 0 && random.nextInt(100) < (2 + nivel)) {
            int extraVel = nivel / 2;
            enemigos.add(new Enemigo(random.nextInt(getWidth() - 60), -50, 3 + extraVel, 50, 50));
            enemigosAEliminar--;
        }

        Iterator<Enemigo> it = enemigos.iterator();
        while (it.hasNext()) {
            Enemigo e = it.next();
            e.y += e.velocidad;
            if (e.y > getHeight()) {
                it.remove();
            }
        }
    }

    private void actualizarBoss() {
        if (boss != null) {
            boss.x += boss.velocidad;
            if (boss.x <= 0 || boss.x >= getWidth() - boss.ancho) {
                boss.velocidad *= -1;
            }
        }
    }

    private void verificarColisiones() {
        Rectangle rectNave = nave.getBounds();

        // Colisión Nave - Enemigo
        Iterator<Enemigo> itE = enemigos.iterator();
        while (itE.hasNext()) {
            Enemigo e = itE.next();
            if (rectNave.intersects(e.getBounds())) {
                nave.vida -= 10;
                itE.remove();
            }
        }

        // Colisión Proyectil - Enemigo / Boss
        Iterator<Proyectil> itP = proyectiles.iterator();
        while (itP.hasNext()) {
            Proyectil p = itP.next();
            Rectangle rectP = p.getBounds();
            boolean hit = false;

            for (Enemigo e : enemigos) {
                if (rectP.intersects(e.getBounds())) {
                    enemigos.remove(e);
                    hit = true;
                    break;
                }
            }

            if (!hit && boss != null && rectP.intersects(boss.getBounds())) {
                boss.vida -= 10;
                hit = true;
                if (boss.vida <= 0) {
                    juegoTerminado = true;
                    victoria = true;
                }
            }

            if (hit)
                itP.remove();
        }

        // Colisión Nave - Boss
        if (boss != null && rectNave.intersects(boss.getBounds())) {
            nave.vida -= 1; // Daño ligero por contacto continuo
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A)
            izquierda = true;
        if (key == KeyEvent.VK_D)
            derecha = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A)
            izquierda = false;
        if (key == KeyEvent.VK_D)
            derecha = false;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            disparo();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            disparo();
        }
    }

    private void disparo() {
        proyectiles.add(new Proyectil(nave.x + (nave.ancho / 2) - 5, nave.y, 10, 10, 20));
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }
}
