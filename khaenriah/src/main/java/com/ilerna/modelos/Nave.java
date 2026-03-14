package com.ilerna.modelos;

/**
 * La nave que controla el jugador.
 */
public class Nave extends GameObject {
    private int vida;

    public Nave(int x, int y, int velocidad, int vida, int ancho, int alto) {
        super(x, y, ancho, alto, velocidad);
        this.vida = vida;
    }

    public int getVida() {
        return vida;
    }

    public void setVida(int vida) {
        this.vida = vida;
    }

    public void recibirDano(int cantidad) {
        this.vida -= cantidad;
    }
}
