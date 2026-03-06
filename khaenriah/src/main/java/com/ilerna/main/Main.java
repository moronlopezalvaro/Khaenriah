package com.ilerna.main;

import com.ilerna.vista.*;

public class Main {

    public static void main(String[] args) {
        VentanaMenu frame = new VentanaMenu();
        frame.setVisible(true);
        VentanaJuego frame2 = new VentanaJuego();
        frame2.setVisible(true);
        VentanaDialogo frame3 = new VentanaDialogo();
        frame3.setVisible(true);
    }
}
