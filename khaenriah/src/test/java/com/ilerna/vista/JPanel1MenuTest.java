package com.ilerna.vista;

import org.junit.Test;
import static org.junit.Assert.*;

public class JPanel1MenuTest {

    @Test
    public void testInstanciacion() {
        // Simplemente verificamos que se puede instanciar sin lanzar excepciones
        // de recursos faltantes.
        try {
            JPanel1Menu panel = new JPanel1Menu();
            assertNotNull(panel);
            assertNull(panel.getLayout()); // El layout se pone a null en el constructor
        } catch (Exception e) {
            fail("La instanciación lanzó una excepción: " + e.getMessage());
        }
    }
}
