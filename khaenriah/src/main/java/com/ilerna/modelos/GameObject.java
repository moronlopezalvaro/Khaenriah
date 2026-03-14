package com.ilerna.modelos;

import java.awt.Rectangle;

/**
 * Objeto general del juego con posición y tamaño.
 */
public abstract class GameObject {
    protected double x, y;
    protected int ancho, alto;
    protected int velocidad;

    /**
     * Constructor para crear un nuevo GameObject.
     * @param x Posición inicial en el eje X.
     * @param y Posición inicial en el eje Y.
     * @param ancho Ancho del objeto.
     * @param alto Alto del objeto.
     * @param velocidad Velocidad de movimiento del objeto.
     */
    public GameObject(double x, double y, int ancho, int alto, int velocidad) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.velocidad = velocidad;
    }

    /**
     * Devuelve el área de colisión del objeto.
     */
    public Rectangle getBounds() {
        return new Rectangle((int) x, (int) y, ancho, alto);
    }

    // Getters y Setters
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getAncho() {
        return ancho;
    }

    public int getAlto() {
        return alto;
    }

    public int getVelocidad() {
        return velocidad;
    }
}
