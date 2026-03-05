package com.ilerna.modelos;

import java.awt.Rectangle;

public class Jefe {
    public int x;
    public int y;
    public int velocidad = 8;
    public int vida;
    public int ancho;
    public int alto;

    public Jefe(int x, int y, int velocidad, int vida) {
        this.x = x;
        this.y = y;
        this.velocidad = velocidad;
        this.vida = vida;
        this.ancho = 100;
        this.alto = 100;
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, ancho, alto);
    }
}
