package com.ilerna.modelos;

import java.awt.Rectangle;

public class Nave {
    public int x;
    public int y;
    public int velocidad;
    public int vida;
    public int ancho;
    public int alto;

    public Nave(int x, int y, int velocidad, int vida, int ancho, int alto) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.vida = vida;
        this.ancho = ancho;
        this.alto = alto;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto);
    }
}
