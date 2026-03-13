package com.ilerna.modelos;

import java.awt.Rectangle;

public abstract class GameObject {
    protected double x, y;
    protected int ancho, alto;
    protected int velocidad;

    public GameObject(double x, double y, int ancho, int alto, int velocidad) {
        this.x = x;
        this.y = y;
        this.ancho = ancho;
        this.alto = alto;
        this.velocidad = velocidad;
    }

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
