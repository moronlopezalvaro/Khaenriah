package com.ilerna.modelos;

import org.junit.Test;
import static org.junit.Assert.*;

public class JefeTest {

    @Test
    public void testConstructorAndInitialState() {
        Jefe jefe = new Jefe(150, 50, 3, 50);

        assertEquals(150.0, jefe.getX(), 0.001);
        assertEquals(50.0, jefe.getY(), 0.001);
        assertEquals(3, jefe.getVelocidad());
        assertEquals(50, jefe.vida);

        // El Jefe siempre se inicializa con ancho = 100 y alto = 100
        assertEquals(100, jefe.getAncho());
        assertEquals(100, jefe.getAlto());
    }
}