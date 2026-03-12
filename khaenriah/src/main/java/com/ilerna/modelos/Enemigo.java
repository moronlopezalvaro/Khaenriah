package com.ilerna.modelos;

import java.awt.Rectangle;

public class Enemigo {
    public double x, y;
    public int velocidad, ancho, alto;

    public boolean explotando = false;
    public int tiempoExplosion = 0;

    public Enemigo(double x, double y, int velocidad, int ancho, int alto) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.ancho = ancho;
        this.alto = alto;
    }

    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, ancho, alto);
    }
}
