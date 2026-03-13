package com.ilerna.modelos;

import org.junit.Test;
import static org.junit.Assert.*;

public class NaveTest {

    @Test
    public void testConstructorAndGetters() {
        Nave nave = new Nave(100, 200, 10, 3, 50, 60);

        assertEquals(100.0, nave.getX(), 0.001);
        assertEquals(200.0, nave.getY(), 0.001);
        assertEquals(10, nave.getVelocidad());
        assertEquals(3, nave.getVida());
        assertEquals(50, nave.getAncho());
        assertEquals(60, nave.getAlto());
    }

    @Test
    public void testSetVida() {
        Nave nave = new Nave(100, 200, 10, 3, 50, 60);
        nave.setVida(5);
        assertEquals(5, nave.getVida());
    }

    @Test
    public void testRecibirDano() {
        Nave nave = new Nave(100, 200, 10, 10, 50, 60);
        nave.recibirDano(4);
        assertEquals(6, nave.getVida());

        nave.recibirDano(6);
        assertEquals(0, nave.getVida());
    }
}