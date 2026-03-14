package com.ilerna.modelos;

import org.junit.Test;
import java.awt.Rectangle;
import static org.junit.Assert.*;

public class GameObjectTest {

    // Concrete implementation to test the abstract GameObject
    private static class DummyGameObject extends GameObject {
        public DummyGameObject(double x, double y, int ancho, int alto, int velocidad) {
            super(x, y, ancho, alto, velocidad);
        }
    }

    @Test
    public void testGettersAndSetters() {
        GameObject obj = new DummyGameObject(10.0, 20.0, 30, 40, 5);

        assertEquals(10.0, obj.getX(), 0.001);
        assertEquals(20.0, obj.getY(), 0.001);
        assertEquals(30, obj.getAncho());
        assertEquals(40, obj.getAlto());
        assertEquals(5, obj.getVelocidad());

        obj.setX(15.5);
        obj.setY(25.5);

        assertEquals(15.5, obj.getX(), 0.001);
        assertEquals(25.5, obj.getY(), 0.001);
    }

    @Test
    public void testGetBounds() {
        GameObject obj = new DummyGameObject(10.5, 20.5, 30, 40, 5);
        Rectangle bounds = obj.getBounds();

        assertEquals(10, bounds.x); // Casta a (int)
        assertEquals(20, bounds.y); // Casta a (int)
        assertEquals(30, bounds.width);
        assertEquals(40, bounds.height);
    }
}