package com.ilerna.vista;

import org.junit.Test;
import static org.junit.Assert.*;

public class JPanel2JuegoTest {

    @Test
    public void testInstanciacion() {
        try {
            JPanel2Juego panel = new JPanel2Juego();
            assertNotNull(panel);
            assertNull(panel.getLayout()); // El layout por defecto es null
            assertTrue(panel.isFocusable());
        } catch (Exception e) {
            fail("La instanciación lanzó una excepción: " + e.getMessage());
        }
    }
}
