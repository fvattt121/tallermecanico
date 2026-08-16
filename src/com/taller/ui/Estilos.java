package com.taller.ui;

import java.awt.*;

public final class Estilos {
    private Estilos() { }

    public static final Color AZUL_OSCURO = new Color(20, 33, 61);
    public static final Color AZUL_MEDIO = new Color(41, 70, 130);
    public static final Color NARANJA = new Color(252, 130, 41);
    public static final Color GRIS_CLARO = new Color(240, 242, 245);
    public static final Color BLANCO = Color.WHITE;
    public static final Color VERDE = new Color(46, 160, 67);
    public static final Color ROJO = new Color(200, 40, 40);
    public static final Color AMARILLO = new Color(214, 158, 10);

    public static final Font TITULO = new Font("SansSerif", Font.BOLD, 24);
    public static final Font SUBTITULO = new Font("SansSerif", Font.BOLD, 16);
    public static final Font NORMAL = new Font("SansSerif", Font.PLAIN, 14);
    public static final Font BOTON = new Font("SansSerif", Font.BOLD, 14);

    public static Color colorEstatus(String etiqueta) {
        if (etiqueta == null) return GRIS_CLARO;
        switch (etiqueta) {
            case "En revision":
                return AMARILLO;
            case "En espera de piezas":
                return ROJO;
            case "Listo":
                return VERDE;
            default:
                return GRIS_CLARO;
        }
    }
}
