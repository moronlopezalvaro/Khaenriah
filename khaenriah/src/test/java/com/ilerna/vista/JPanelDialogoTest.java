package com.ilerna.vista;

import org.junit.Test;
import static org.junit.Assert.*;

public class JPanelDialogoTest {

    @Test
    public void testSetTextoYNombre() {
        try {
            JPanelDialogo panel = new JPanelDialogo();
            assertNotNull(panel);

            // Verificamos que los métodos se pueden llamar sin causar errores
            panel.setNombre("Test Nombre");
            panel.setTexto("Test Texto");
            panel.setImagenFondo("/com/ilerna/resources/champiplanetadentro.jpg");

            // Si llega aquí sin excepciones, la prueba es pasable
            assertTrue(true);
        } catch (Exception e) {
            fail("La prueba lanzó una excepción: " + e.getMessage());
        }
    }
}
