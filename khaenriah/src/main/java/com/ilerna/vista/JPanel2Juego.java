package com.ilerna.vista;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.swing.*;
import com.ilerna.modelos.*;
import javax.sound.sampled.*;
import java.net.URL;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Panel donde ocurre toda la acción del juego.
 */
public class JPanel2Juego extends JPanel implements ActionListener, KeyListener, MouseListener {
    private Image imagenFondo;
    private Image imgNave;
    private Image imgEnemigo;
    private Image imgBoss;
    private Image imgBalaBoss;
    private Image imgPausa;
    private Image imgFinal;
    private Image imgGameOver;
    private Image imgVictoria;
    private Image imgExplosion;
    private ImageIcon iconProyectil;

    private Timer timer;
    private Nave nave;
    private List<Enemigo> enemigos;
    private List<Proyectil> proyectiles;
    private List<Proyectil> proyectilesBoss;
    private Jefe boss;
    private JButton btnPausaMenu;
    private JButton btnPausaSalir;
    private JButton btnFinalReiniciar;
    private JButton btnFinalSalir;
    private Clip clipMusica;
    private Clip[] clipsDisparo = new Clip[5];
    private int indiceClipActual = 0;
    private Clip[] clipsExplosion = new Clip[5];
    private int indiceClipExplosion = 0;
    private Clip clipGameOver;
    private boolean gameOverReproducido = false;
    private Clip clipVictoria;
    private boolean victoriaReproducida = false;

    private int nivel = 1;
    private int puntuacion = 0;
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
            imgBalaBoss = new ImageIcon(getClass().getResource("/com/ilerna/resources/BalaBoss.png")).getImage();
            imgPausa = new ImageIcon(getClass().getResource("/com/ilerna/resources/MenuPausa.png")).getImage();
            imgFinal = new ImageIcon(getClass().getResource("/com/ilerna/resources/botones_final.png")).getImage();
            imgGameOver = new ImageIcon(getClass().getResource("/com/ilerna/resources/GameOver.jpg")).getImage();
            imgVictoria = new ImageIcon(getClass().getResource("/com/ilerna/resources/Victoria.jpg")).getImage();
            imgExplosion = new ImageIcon(getClass().getResource("/com/ilerna/resources/geocentinelaexpl.png"))
                    .getImage();

            // Mantener como ImageIcon en lugar de extraer la Image directamente ayuda a
            // conservar la animación original a su velocidad
            iconProyectil = new ImageIcon(getClass().getResource("/com/ilerna/resources/bala.gif"));

            // Cargar clips de disparo (pool)
            URL urlDisparo = getClass().getResource("/com/ilerna/resources/Disparo.wav");
            System.out.println("LOG: URL Disparo.wav = " + urlDisparo);
            if (urlDisparo != null) {
                AudioInputStream audioDisparo = AudioSystem.getAudioInputStream(urlDisparo);
                AudioFormat format = audioDisparo.getFormat();
                System.out.println("LOG: Formato Disparo = " + format);

                // Leer todo el audio a la memoria
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int read;
                while ((read = audioDisparo.read(buffer)) != -1) {
                    baos.write(buffer, 0, read);
                }
                byte[] audioData = baos.toByteArray();
                System.out.println("LOG: Audio leído a byte[], tamaño=" + audioData.length);

                for (int i = 0; i < clipsDisparo.length; i++) {
                    ByteArrayInputStream bais = new ByteArrayInputStream(audioData);
                    AudioInputStream reusableStream = new AudioInputStream(bais, format,
                            audioData.length / format.getFrameSize());
                    clipsDisparo[i] = AudioSystem.getClip();
                    clipsDisparo[i].open(reusableStream);
                }
                System.out.println("LOG: " + clipsDisparo.length + " clips cargados correctamente.");
            } else {
                System.out.println("LOG: ¡No se encontró Disparo.wav!");
            }

            // Cargar clips de explosión (pool)
            URL urlExplosion = getClass().getResource("/com/ilerna/resources/Explosion.wav");
            if (urlExplosion != null) {
                AudioInputStream audioExplosion = AudioSystem.getAudioInputStream(urlExplosion);
                AudioFormat formatExplosion = audioExplosion.getFormat();

                // Leer todo el audio de explosión a la memoria
                ByteArrayOutputStream baosExplosion = new ByteArrayOutputStream();
                byte[] bufferExplosion = new byte[1024];
                int readExplosion;
                while ((readExplosion = audioExplosion.read(bufferExplosion)) != -1) {
                    baosExplosion.write(bufferExplosion, 0, readExplosion);
                }
                byte[] dataExplosion = baosExplosion.toByteArray();

                for (int i = 0; i < clipsExplosion.length; i++) {
                    ByteArrayInputStream baisEx = new ByteArrayInputStream(dataExplosion);
                    AudioInputStream reusableStreamEx = new AudioInputStream(baisEx, formatExplosion,
                            dataExplosion.length / formatExplosion.getFrameSize());
                    clipsExplosion[i] = AudioSystem.getClip();
                    clipsExplosion[i].open(reusableStreamEx);
                }
                System.out.println("LOG: Clips de explosión cargados.");
            } else {
                System.out.println("LOG: ¡No se encontró Explosion.wav!");
            }

            // Cargar clip Game Over
            URL urlGameOver = getClass().getResource("/com/ilerna/resources/GameOver.wav");
            if (urlGameOver != null) {
                AudioInputStream audioGameOver = AudioSystem.getAudioInputStream(urlGameOver);
                clipGameOver = AudioSystem.getClip();
                clipGameOver.open(audioGameOver);
                System.out.println("LOG: Clip Game Over cargado.");
            } else {
                System.out.println("LOG: ¡No se encontró GameOver.wav!");
            }

            // Cargar clip Victoria
            URL urlVictoria = getClass().getResource("/com/ilerna/resources/Victoria.wav");
            if (urlVictoria != null) {
                AudioInputStream audioVictoria = AudioSystem.getAudioInputStream(urlVictoria);
                clipVictoria = AudioSystem.getClip();
                clipVictoria.open(audioVictoria);
                System.out.println("LOG: Clip Victoria cargado.");
            } else {
                System.out.println("LOG: ¡No se encontró Victoria.wav!");
            }

        } catch (Exception e) {
            System.out.println("Error al cargar imágenes o sonidos: " + e.getMessage());
            e.printStackTrace();
        }

        nave = new Nave(500, 630, 20, 100, 60, 60);
        enemigos = new ArrayList<>();
        proyectiles = new ArrayList<>();
        proyectilesBoss = new ArrayList<>();

        timer = new Timer(20, this);
        timer.start();

        iniciarNivel();

        // Inicializar botones de pausa (transparentes para superponer a la imagen)
        btnPausaMenu = new JButton();
        btnPausaSalir = new JButton();

        configurarBotonPausa(btnPausaMenu, 350, 100);
        configurarBotonPausa(btnPausaSalir, 300, 80);

        btnPausaMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reproducirSonidoClick();
                System.out.println("BOTÓN MENÚ PULSADO - INICIANDO TRANSICIÓN");
                volverAlMenu();
            }
        });

        btnPausaSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reproducirSonidoClick();
                System.out.println("BOTÓN SALIR PULSADO - CERRANDO APP");
                try {
                    Thread.sleep(300);
                } catch (Exception ex) {
                }
                System.exit(0);
            }
        });

        this.add(btnPausaMenu);
        this.add(btnPausaSalir);

        // Inicializar botones de fin de juego
        btnFinalReiniciar = new JButton();
        btnFinalSalir = new JButton();

        configurarBotonPausa(btnFinalReiniciar, 150, 70);
        configurarBotonPausa(btnFinalSalir, 150, 70);

        btnFinalReiniciar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reproducirSonidoClick();
                try {
                    Thread.sleep(150);
                } catch (Exception ex) {
                }
                reiniciarJuego();
            }
        });

        btnFinalSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reproducirSonidoClick();
                try {
                    Thread.sleep(300);
                } catch (Exception ex) {
                }
                System.exit(0);
            }
        });

        this.add(btnFinalReiniciar);
        this.add(btnFinalSalir);

        // Iniciar la música de fondo
        reproducirMusica("/com/ilerna/resources/musica_arcade.wav");
    }

    public void reproducirMusica(String ruta) {
        try {
            URL url = getClass().getResource(ruta);
            if (url != null) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(url);
                clipMusica = AudioSystem.getClip();
                clipMusica.open(audioStream);
                clipMusica.loop(Clip.LOOP_CONTINUOUSLY); // Hacer que la música se repita
                clipMusica.start();
            } else {
                System.out.println("No se encontró el archivo de sonido: " + ruta);
            }
        } catch (Exception e) {
            System.out.println("Error al reproducir música: " + e.getMessage());
        }
    }

    public void reproducirSonidoDisparo() {
        if (clipsDisparo[indiceClipActual] != null) {
            // System.out.println("LOG: Reproduciendo clip disparo en índice: " +
            // indiceClipActual);
            clipsDisparo[indiceClipActual].stop();
            clipsDisparo[indiceClipActual].setFramePosition(0); // Reiniciar al principio
            clipsDisparo[indiceClipActual].start();

            indiceClipActual++;
            if (indiceClipActual >= clipsDisparo.length) {
                indiceClipActual = 0;
            }
        } else {
            // System.out.println("LOG: Fallo al disparar, clip nulo en índice: " +
            // indiceClipActual);
        }
    }

    public void reproducirSonidoExplosion() {
        if (clipsExplosion[indiceClipExplosion] != null) {
            clipsExplosion[indiceClipExplosion].stop();
            clipsExplosion[indiceClipExplosion].setFramePosition(0);
            clipsExplosion[indiceClipExplosion].start();

            indiceClipExplosion++;
            if (indiceClipExplosion >= clipsExplosion.length) {
                indiceClipExplosion = 0;
            }
        }
    }

    public void reproducirSonidoGameOver() {
        if (clipGameOver != null && !gameOverReproducido) {
            clipGameOver.setFramePosition(0);
            clipGameOver.start();
            gameOverReproducido = true;
        }
    }

    public void reproducirSonidoVictoria() {
        if (clipVictoria != null && !victoriaReproducida) {
            clipVictoria.setFramePosition(0);
            clipVictoria.start();
            victoriaReproducida = true;
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

    private void configurarBotonPausa(JButton btn, int w, int h) {
        btn.setSize(w, h);
        btn.setContentAreaFilled(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setVisible(false);
    }

    private void volverAlMenu() {
        if (timer != null) {
            timer.stop();
        }
        if (clipMusica != null && clipMusica.isRunning()) {
            clipMusica.stop();
        }

        System.out.println("LOG: Deteniendo timer y preparando nueva VentanaMenu...");

        // Ejecutamos en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // 1. Crear e instanciar el menú
                VentanaMenu menu = new VentanaMenu();
                menu.setVisible(true);
                System.out.println("LOG: VentanaMenu creada y visible.");

                // 2. Esperar un breve instante antes de cerrar la actual (usando un Timer de
                // Swing)
                Timer delayDispose = new Timer(200, event -> {
                    Window win = SwingUtilities.getWindowAncestor(this);
                    if (win != null) {
                        System.out.println("LOG: Cerrando ventana de juego antigua.");
                        win.dispose();
                    }
                });
                delayDispose.setRepeats(false);
                delayDispose.start();

            } catch (Exception ex) {
                System.err.println("ERROR CRÍTICO EN TRANSICIÓN: " + ex.getMessage());
                ex.printStackTrace();
            }
        });
    }

    /**
     * Configura los enemigos y el jefe para el nivel actual.
     */
    private void iniciarNivel() {
        enemigos.clear();
        proyectiles.clear();
        proyectilesBoss.clear();
        boss = null;
        enemigosAEliminar = 6 + nivel;

        if (nivel == 10) {
            boss = new Jefe(440, 50, 4, 500); // Vida alta para el boss
        }
    }

    /**
     * Dibuja todos los elementos del juego en la pantalla.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dibujar fondo
        g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);

        if (juegoTerminado) {
            if (victoria) {
                g.drawImage(imgVictoria, 0, 0, getWidth(), getHeight(), this);
            } else {
                g.drawImage(imgGameOver, 0, 0, getWidth(), getHeight(), this);
            }

            int imgW = 700;
            int imgH = 350;
            int imgX = getWidth() / 2 - imgW / 2;
            int imgY = getHeight() / 2 - imgH / 2 + 150;

            g.drawImage(imgFinal, imgX, imgY, imgW, imgH, this);

            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 18));
            String msg = victoria ? "HAS SALVADO A KHAENRI'AH" : "INTENTALO DE NUEVO";
            FontMetrics fm = g.getFontMetrics();
            g.drawString(msg, getWidth() / 2 - fm.stringWidth(msg) / 2, imgY - 150);

            g.setFont(new Font("Arial", Font.BOLD, 14));
            String strPuntuacion = "PUNTUACIÓN: " + puntuacion;
            FontMetrics fmPuntos = g.getFontMetrics();
            g.drawString(strPuntuacion, getWidth() / 2 - fmPuntos.stringWidth(strPuntuacion) / 2, imgY - 130);

            clipMusica.stop();

            return;
        }

        // Dibujar Nave
        g.drawImage(imgNave, (int) nave.getX(), (int) nave.getY(), nave.getAncho(), nave.getAlto(), this);

        // Dibujar Enemigos
        for (Enemigo e : enemigos) {
            if (e.explotando) {
                g.drawImage(imgExplosion, (int) e.getX(), (int) e.getY(), e.getAncho(), e.getAlto(), this);
            } else {
                g.drawImage(imgEnemigo, (int) e.getX(), (int) e.getY(), e.getAncho(), e.getAlto(), this);
            }
        }

        // Dibujar Boss
        if (boss != null) {
            g.drawImage(imgBoss, (int) boss.getX(), (int) boss.getY(), boss.getAncho(), boss.getAlto(), this);
            // Barra de vida Boss
            g.setColor(Color.RED);
            g.fillRect((int) boss.getX(), (int) boss.getY() - 20, boss.getAncho(), 10);
            g.setColor(Color.GREEN);
            g.fillRect((int) boss.getX(), (int) boss.getY() - 20, (int) (boss.getAncho() * (boss.vida / 500.0)), 10);
        }

        // Dibujar Proyectiles Jugador
        for (Proyectil p : proyectiles) {
            g.drawImage(iconProyectil.getImage(), (int) p.getX(), (int) p.getY(), p.getAncho(), p.getAlto(),
                    iconProyectil.getImageObserver());
        }

        // Dibujar Proyectiles Boss
        for (Proyectil p : proyectilesBoss) {
            g.drawImage(imgBalaBoss, (int) p.getX(), (int) p.getY(), p.getAncho(), p.getAlto(), this);
        }

        // Interfaz de Usuario (HUD)
        dibujarHUD(g);

        // Menú de Pausa
        if (pausado && !juegoTerminado) {
            g.setColor(new Color(0, 0, 0, 150));
            g.fillRect(0, 0, getWidth(), getHeight());
            int imgW = 700;
            int imgH = 350;
            int imgX = getWidth() / 2 - imgW / 2;
            int imgY = getHeight() / 2 - imgH / 2 + 30;
            g.drawImage(imgPausa, imgX, imgY, imgW, imgH, this);
            clipMusica.stop();
        } else {
            clipMusica.start();
        }

    }

    private void dibujarHUD(Graphics g) {
        // Barra de vida jugador
        g.setColor(Color.GRAY);
        g.fillRect(20, 50, 200, 20);
        if (nave.getVida() <= 30) {
            g.setColor(Color.RED);
        } else if (nave.getVida() <= 60) {
            g.setColor(Color.ORANGE);
        } else {
            g.setColor(Color.GREEN);
        }
        g.fillRect(20, 50, nave.getVida() * 2, 20);
        g.setColor(Color.WHITE);
        g.drawRect(20, 50, 200, 20);
        g.drawString("VIDA: " + nave.getVida(), 20, 85);

        // Nivel
        g.setFont(new Font("Arial", Font.BOLD, 18));
        g.drawString("NIVEL: " + nivel, getWidth() - 150, 70);
        if (nivel < 10) {
            g.drawString("RESTANTES: " + enemigosAEliminar, getWidth() - 150, 100);
        } else {
            g.drawString("¡BATALLA FINAL!", getWidth() - 150, 100);
        }

        // Puntuación
        g.setColor(Color.YELLOW);
        g.drawString("PUNTOS: " + puntuacion, 20, 115);
    }

    /**
     * Se ejecuta en cada frame del juego para actualizar posiciones y estados.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (juegoTerminado) {
            timer.stop();
            actualizarVisibilidadBotonesFinal();
            return;
        }

        if (pausado) {
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

        if (nave.getVida() <= 0) {
            juegoTerminado = true;
            victoria = false;
            reproducirSonidoGameOver();
        }

        repaint();
    }

    private void actualizarNave() {
        if (izquierda && nave.getX() > 0)
            nave.setX(nave.getX() - nave.getVelocidad());
        if (derecha && nave.getX() < getWidth() - nave.getAncho())
            nave.setX(nave.getX() + nave.getVelocidad());

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
            if (p.getY() < -100)
                it.remove();
        }

        // Proyectiles Boss
        Iterator<Proyectil> itB = proyectilesBoss.iterator();
        while (itB.hasNext()) {
            Proyectil p = itB.next();
            p.moverHaciaAbajo();
            if (p.getY() > getHeight() + 100)
                itB.remove();
        }
    }

    private void actualizarEnemigos() {
        // Spawn de enemigos
        if (nivel < 10 && enemigosAEliminar > 0 && random.nextInt(100) < (4 + nivel)) {
            int extraVel = nivel / 2;
            enemigos.add(new Enemigo(random.nextInt(getWidth() - 60), -50, 4 + extraVel, 50, 50));
            enemigosAEliminar--;
        }

        Iterator<Enemigo> it = enemigos.iterator();
        while (it.hasNext()) {
            Enemigo e = it.next();

            if (e.explotando) {
                e.tiempoExplosion++;
                if (e.tiempoExplosion > 4) {
                    it.remove();
                }
            } else {
                e.setY(e.getY() + e.getVelocidad());
                if (e.getY() > getHeight()) {
                    nave.recibirDano(3);
                    puntuacion -= 1;
                    it.remove();
                }
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

            boss.setX(boss.getX() + bossVelX);
            boss.setY(boss.getY() + bossVelY);

            // Limites de pantalla para el boss
            if (boss.getX() < 0) {
                boss.setX(0);
                bossVelX *= -1;
            }
            if (boss.getX() > getWidth() - boss.getAncho()) {
                boss.setX(getWidth() - boss.getAncho());
                bossVelX *= -1;
            }
            if (boss.getY() < 0) {
                boss.setY(0);
                bossVelY *= -1;
            }
            if (boss.getY() > 300) {
                boss.setY(300);
                bossVelY *= -1;
            } // No baja demasiado

            // Disparo del Boss (ahora los proyectiles bajan)
            if (random.nextInt(30) == 0) {
                Proyectil pBoss = new Proyectil(boss.getX() + (boss.getAncho() / 2) - 20, boss.getY() + boss.getAlto(),
                        8, 40, 40);
                proyectilesBoss.add(pBoss);
            }
        }
    }

    private void verificarColisiones() {
        Rectangle rectNave = nave.getBounds();

        // Colisión Nave - Enemigo
        Iterator<Enemigo> itE = enemigos.iterator();
        while (itE.hasNext()) {
            Enemigo e = itE.next();
            if (!e.explotando && rectNave.intersects(e.getBounds())) {
                nave.recibirDano(5);
                e.explotando = true; // Que también explote si choca con la nave
            }
        }

        // Colisión Proyectil - Enemigo / Boss
        Iterator<Proyectil> itP = proyectiles.iterator();
        while (itP.hasNext()) {
            Proyectil p = itP.next();
            Rectangle rectP = p.getBounds();
            boolean hit = false;

            Iterator<Enemigo> itEnemigo = enemigos.iterator();
            while (itEnemigo.hasNext()) {
                Enemigo e = itEnemigo.next();
                if (!e.explotando && rectP.intersects(e.getBounds())) {
                    e.explotando = true;
                    reproducirSonidoExplosion();
                    puntuacion += 2; // Sumar puntos por enemigo eliminado
                    hit = true;
                    break;
                }
            }

            if (!hit && boss != null && rectP.intersects(boss.getBounds())) {
                boss.vida -= 7;
                puntuacion += 5; // Sumar puntos por impacto al boss
                hit = true;
                if (boss.vida <= 0) {
                    juegoTerminado = true;
                    victoria = true;
                    puntuacion += 1000; // Bonus por derrotar al boss
                    reproducirSonidoVictoria();
                }
            }

            if (hit)
                itP.remove();
        }

        // Colisión Nave - Boss
        if (boss != null && rectNave.intersects(boss.getBounds())) {
            nave.recibirDano(1); // Daño ligero por contacto continuo
        }

        // Colisión Proyectil Boss - Nave
        Iterator<Proyectil> itPB = proyectilesBoss.iterator();
        while (itPB.hasNext()) {
            Proyectil p = itPB.next();
            if (rectNave.intersects(p.getBounds())) {
                nave.recibirDano(10);
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
            actualizarVisibilidadBotonesPausa();
        }
    }

    private void actualizarVisibilidadBotonesPausa() {
        if (pausado) {
            int centerX = getWidth() / 2;
            int centerY = getHeight() / 2 + 30; // Ajuste por el desplazamiento de la imagen

            // MANTENEMOS TUS POSICIONES PERO QUITAMOS EL DEPURADOR
            btnPausaMenu.setBounds(centerX - 20, centerY + 65, 150, 70);
            btnPausaSalir.setBounds(centerX - 185, centerY + 65, 150, 70);

            btnPausaMenu.setText("");
            btnPausaMenu.setContentAreaFilled(false);
            btnPausaMenu.setBorderPainted(false);
            btnPausaMenu.setBorder(null);

            btnPausaSalir.setText("");
            btnPausaSalir.setContentAreaFilled(false);
            btnPausaSalir.setBorderPainted(false);
            btnPausaSalir.setBorder(null);

            btnPausaMenu.setVisible(true);
            btnPausaSalir.setVisible(true);

            this.setComponentZOrder(btnPausaMenu, 0);
            this.setComponentZOrder(btnPausaSalir, 1);
        } else {
            btnPausaMenu.setVisible(false);
            btnPausaSalir.setVisible(false);
        }
        this.repaint();
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
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            if (!pausado && !juegoTerminado) {
                disparo();
            }
        }
    }

    private void disparo() {
        if (cooldownDisparo <= 0) {
            int velocidadBala = 10 + nivel;
            // Aumentando el tamaño de la bala: de (10, 20) a (30, 60)
            // Ajustando también el centrado X: -15
            proyectiles
                    .add(new Proyectil(nave.getX() + (nave.getAncho() / 2) - 15, nave.getY(), velocidadBala, 30, 60));
            cooldownDisparo = 5; // Aproximadamente 0.3 segundos (15 * 20ms)
            reproducirSonidoDisparo();
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

    private void actualizarVisibilidadBotonesFinal() {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2 + 150;

        // Ajustar posiciones según el diseño de botones_final.png
        // "Salir" está a la izquierda y "Reiniciar" a la derecha
        btnFinalSalir.setBounds(centerX - 150, centerY + 50, 150, 70);
        btnFinalReiniciar.setBounds(centerX + 25, centerY + 50, 150, 70);

        // Hacerlos invisibles (transparentes)
        btnFinalReiniciar.setContentAreaFilled(false);
        btnFinalReiniciar.setBorderPainted(false);
        btnFinalReiniciar.setBorder(null);
        btnFinalReiniciar.setVisible(true);

        btnFinalSalir.setContentAreaFilled(false);
        btnFinalSalir.setBorderPainted(false);
        btnFinalSalir.setBorder(null);
        btnFinalSalir.setVisible(true);

        this.setComponentZOrder(btnFinalReiniciar, 0);
        this.setComponentZOrder(btnFinalSalir, 1);
        this.repaint();
    }

    private void reiniciarJuego() {
        nivel = 1;
        puntuacion = 0;
        juegoTerminado = false;
        victoria = false;
        nave.setVida(100);
        nave.setX(500);
        nave.setY(630);
        gameOverReproducido = false;
        victoriaReproducida = false;

        btnFinalReiniciar.setVisible(false);
        btnFinalSalir.setVisible(false);

        iniciarNivel();
        if (timer != null) {
            timer.start();
        }
        repaint();
    }
}