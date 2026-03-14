package com.ilerna.modelos;

import org.junit.Test;
import static org.junit.Assert.*;

public class ProyectilTest {

    @Test
    public void testConstructorAndInitialState() {
        Proyectil proyectil = new Proyectil(200.0, 300.0, 10, 5, 15);

        assertEquals(200.0, proyectil.getX(), 0.001);
        assertEquals(300.0, proyectil.getY(), 0.001);
        assertEquals(10, proyectil.getVelocidad());
        assertEquals(5, proyectil.getAncho());
        assertEquals(15, proyectil.getAlto());
    }

    @Test
    public void testMover() {
        Proyectil proyectil = new Proyectil(200.0, 300.0, 10, 5, 15);
        proyectil.mover();

        // Normal move should decrease Y (move up)
        assertEquals(290.0, proyectil.getY(), 0.001);
        assertEquals(200.0, proyectil.getX(), 0.001);
    }

    @Test
    public void testMoverHaciaAbajo() {
        Proyectil proyectil = new Proyectil(200.0, 300.0, -10, 5, 15); // Negative speed normally handling below
        proyectil.moverHaciaAbajo();

        // HaciaAbajo should add Math.abs(velocidad) to Y
        assertEquals(310.0, proyectil.getY(), 0.001);
    }
}