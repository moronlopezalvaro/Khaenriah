package com.ilerna.modelos;

/**
 * Enemigo básico del juego.
 */
public class Enemigo extends GameObject {
    public boolean explotando = false;
    public int tiempoExplosion = 0;

    public Enemigo(double x, double y, int velocidad, int ancho, int alto) {
        super(x, y, ancho, alto, velocidad);
    }
    
    public void mover() {
        if (!explotando) {
            y += velocidad;
        }
    }
}
