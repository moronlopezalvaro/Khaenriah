package com.ilerna.modelos;

/**
 * Jefe final del juego.
 */
public class Jefe extends GameObject {
    public int vida;

    public Jefe(int x, int y, int velocidad, int vida) {
        super(x, y, 100, 100, velocidad);
        this.vida = vida;
    }
}
