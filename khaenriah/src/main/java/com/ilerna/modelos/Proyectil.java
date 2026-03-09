package com.ilerna.modelos;

import java.awt.Rectangle;

public class Proyectil {
    public double x, y;
    public int velocidad = 20, ancho, alto;

    public Proyectil(double x, double y, int velocidad, int ancho, int alto) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.ancho = ancho;
        this.alto = alto;
    }

    // Para cuando el proyectil
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, ancho, alto);
    }

    // Para mover el proyectil hacia arriba.
    public void mover() {
        y -= velocidad;
    }
}
