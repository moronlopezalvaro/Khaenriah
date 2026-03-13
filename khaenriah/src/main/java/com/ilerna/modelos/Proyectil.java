package com.ilerna.modelos;

public class Proyectil extends GameObject {

    public Proyectil(double x, double y, int velocidad, int ancho, int alto) {
        super(x, y, ancho, alto, velocidad);
    }

    public void mover() {
        y -= velocidad;
    }
    
    // Para proyectiles que bajan (como los del Boss)
    public void moverHaciaAbajo() {
        y += Math.abs(velocidad);
    }
}
