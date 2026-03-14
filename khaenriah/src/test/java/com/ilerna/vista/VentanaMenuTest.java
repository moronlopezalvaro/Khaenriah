package com.ilerna.vista;

import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.Dimension;
import javax.swing.JFrame;

public class VentanaMenuTest {

    @Test
    public void testPropiedadesVentana() {
        try {
            VentanaMenu ventana = new VentanaMenu();

            assertEquals("KHAENRI'AH", ventana.getTitle());
            assertEquals(new Dimension(1080, 720), ventana.getSize());
            assertTrue(ventana.isUndecorated());
            assertEquals(JFrame.DISPOSE_ON_CLOSE, ventana.getDefaultCloseOperation());

            ventana.dispose(); // Limpiar recursos
        } catch (Exception e) {
            fail("La instanciación lanzó una excepción: " + e.getMessage());
        }
    }
}
