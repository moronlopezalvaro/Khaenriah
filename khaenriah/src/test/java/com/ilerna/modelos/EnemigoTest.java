package com.ilerna.modelos;

import org.junit.Test;
import static org.junit.Assert.*;

public class EnemigoTest {

    @Test
    public void testConstructorAndInitialState() {
        Enemigo enemigo = new Enemigo(50.0, 100.0, 5, 40, 40);

        assertEquals(50.0, enemigo.getX(), 0.001);
        assertEquals(100.0, enemigo.getY(), 0.001);
        assertEquals(5, enemigo.getVelocidad());
        assertEquals(40, enemigo.getAncho());
        assertEquals(40, enemigo.getAlto());
        assertFalse(enemigo.explotando);
        assertEquals(0, enemigo.tiempoExplosion);
    }

    @Test
    public void testMoverWithoutExplotando() {
        Enemigo enemigo = new Enemigo(50.0, 100.0, 5, 40, 40);
        enemigo.mover();

        // Should move in Y by velocidad
        assertEquals(105.0, enemigo.getY(), 0.001);
        assertEquals(50.0, enemigo.getX(), 0.001); // X shouldn't change
    }

    @Test
    public void testMoverWhileExplotando() {
        Enemigo enemigo = new Enemigo(50.0, 100.0, 5, 40, 40);
        enemigo.explotando = true;
        enemigo.mover();

        // Should not move
        assertEquals(100.0, enemigo.getY(), 0.001);
    }
}