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
    private Image imgPausa;

    private Timer timer;
    private Nave nave;
    private List<Enemigo> enemigos;
    private List<Proyectil> proyectiles;
    private List<Proyectil> proyectilesBoss;
    private Jefe boss;

    private int nivel = 1;
    private int enemigosAEliminar = 10;
    private boolean juegoTerminado = false;
    private boolean victoria = false;
    private boolean pausado = false;
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
            imgPausa = new ImageIcon(getClass().getResource("/com/ilerna/resources/Pausa.png")).getImage();

        } catch (Exception e) {
            System.out.println("Error al cargar imágenes: " + e.getMessage());
        }

        nave = new Nave(500, 600, 15, 100, 60, 60);
        enemigos = new ArrayList<>();
        proyectiles = new ArrayList<>();
        proyectilesBoss = new ArrayList<>();

        timer = new Timer(20, this);
        timer.start();

        iniciarNivel();
    }

    private void iniciarNivel() {
        enemigos.clear();
        proyectiles.clear();
        proyectilesBoss.clear();
        boss = null;
        enemigosAEliminar = 5 + nivel;

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

        // Dibujar Proyectiles Jugador
        g.setColor(Color.CYAN);
        for (Proyectil p : proyectiles) {
            g.fillRect((int) p.x, (int) p.y, p.ancho, p.alto);
        }

        // Dibujar Proyectiles Boss
        g.setColor(Color.RED);
        for (Proyectil p : proyectilesBoss) {
            g.fillRect((int) p.x, (int) p.y, p.ancho, p.alto);
        }

        // Interfaz de Usuario (HUD)
        dibujarHUD(g);

        // Menú de Pausa
        if (pausado && !juegoTerminado) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());

            // Dibujar imagen de pausa nueva (ya incluye los textos y el botón SALIR)
            int imgW = 600; // Tamaño ampliado para mejor legibilidad
            int imgH = 450;
            g.drawImage(imgPausa, getWidth() / 2 - imgW / 2, getHeight() / 2 - imgH / 2, imgW, imgH, this);
        }
    }

    private void dibujarHUD(Graphics g) {
        // Barra de vida jugador
        g.setColor(Color.GRAY);
        g.fillRect(20, 20, 200, 20);
        if (nave.vida <= 30) {
            g.setColor(Color.RED);
        } else if (nave.vida <= 60) {
            g.setColor(Color.ORANGE);
        } else {
            g.setColor(Color.GREEN);
        }
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
        if (juegoTerminado || pausado) {
            repaint(); // Seguimos repintando para ver el menú de pausa
            return;
        }

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

        if (disparo) {
            disparo();
        }
        if (cooldownDisparo > 0)
            cooldownDisparo--;
    }

    private void actualizarProyectiles() {
        // Proyectiles Jugador
        Iterator<Proyectil> it = proyectiles.iterator();
        while (it.hasNext()) {
            Proyectil p = it.next();
            p.mover();
            if (p.y < -100)
                it.remove();
        }

        // Proyectiles Boss
        Iterator<Proyectil> itB = proyectilesBoss.iterator();
        while (itB.hasNext()) {
            Proyectil p = itB.next();
            p.mover(); // Moverá hacia abajo si velocidad es negativa
            if (p.y > getHeight() + 100)
                itB.remove();
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
                nave.vida -= 3;
                it.remove();
            }
        }
    }

    // BOSS FINAL
    private int bossVelX = 5;
    private int bossVelY = 3;

    private void actualizarBoss() {
        if (boss != null) {
            // Movimiento aleatorio suavizado
            if (random.nextInt(50) == 0)
                bossVelX = (random.nextInt(11) - 5); // Cambio de dirección X
            if (random.nextInt(100) == 0)
                bossVelY = (random.nextInt(7) - 3); // Cambio de dirección Y

            boss.x += bossVelX;
            boss.y += bossVelY;

            // Limites de pantalla para el boss
            if (boss.x < 0) {
                boss.x = 0;
                bossVelX *= -1;
            }
            if (boss.x > getWidth() - boss.ancho) {
                boss.x = getWidth() - boss.ancho;
                bossVelX *= -1;
            }
            if (boss.y < 0) {
                boss.y = 0;
                bossVelY *= -1;
            }
            if (boss.y > 300) {
                boss.y = 300;
                bossVelY *= -1;
            } // No baja demasiado

            // Disparo del Boss
            if (random.nextInt(30) == 0) {
                proyectilesBoss.add(new Proyectil(boss.x + (boss.ancho / 2) - 5, boss.y + boss.alto, -20, 10, 20));
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
                nave.vida -= 5;
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
                boss.vida -= 7;
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

        // Colisión Proyectil Boss - Nave
        Iterator<Proyectil> itPB = proyectilesBoss.iterator();
        while (itPB.hasNext()) {
            Proyectil p = itPB.next();
            if (rectNave.intersects(p.getBounds())) {
                nave.vida -= 10;
                itPB.remove();
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_A)
            izquierda = true;
        if (key == KeyEvent.VK_D)
            derecha = true;

        if (key == KeyEvent.VK_ESCAPE) {
            pausado = !pausado;
        }
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
        if (pausado && !juegoTerminado) {
            int mx = e.getX();
            int my = e.getY();
            // Detectar clic en el botón SALIR de la nueva imagen
            // (El botón está centrado en la parte inferior del panel)
            int btnW = 200;
            int btnH = 60;
            int btnX = getWidth() / 2 - btnW / 2;
            int btnY = getHeight() / 2 + 100;

            if (mx >= btnX && mx <= btnX + btnW &&
                    my >= btnY && my <= btnY + btnH) {
                System.exit(0);
            }
        }
        if (e.getButton() == MouseEvent.BUTTON1 && !pausado) {
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
        if (cooldownDisparo <= 0) {
            int velocidadBala = 10 + nivel;
            // He puesto los valores que tenías: velocidad 8 y centrado -5
            proyectiles.add(new Proyectil(nave.x + (nave.ancho / 2) - 5, nave.y, velocidadBala, 10, 20));
            cooldownDisparo = 5; // Aproximadamente 0.3 segundos (15 * 20ms)

        }
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
