package com.taller.util;

import com.taller.modelo.Usuario;

/**
 * Mantiene el usuario que inicio sesion (singleton simple).
 */
public final class Sesion {
    private static Usuario usuarioActual;

    private Sesion() { }

    public static void iniciar(Usuario usuario) {
        usuarioActual = usuario;
    }

    public static Usuario getUsuarioActual() {
        return usuarioActual;
    }

    public static void cerrar() {
        usuarioActual = null;
    }
}
