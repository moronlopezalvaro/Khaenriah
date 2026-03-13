package com.ilerna.vista;

import org.junit.Test;
import static org.junit.Assert.*;
import java.awt.Dimension;
import javax.swing.JFrame;

public class VentanaDialogoTest {

    @Test
    public void testPropiedadesVentana() {
        try {
            VentanaDialogo ventana = new VentanaDialogo(null);

            assertEquals("KHAENRI'AH", ventana.getTitle());
            assertEquals(new Dimension(1080, 720), ventana.getSize());
            assertTrue(ventana.isUndecorated());
            assertFalse(ventana.isResizable());
            assertEquals(JFrame.DISPOSE_ON_CLOSE, ventana.getDefaultCloseOperation());

            ventana.dispose(); // Limpiar recursos
        } catch (Exception e) {
            fail("La instanciación lanzó una excepción: " + e.getMessage());
        }
    }
}
