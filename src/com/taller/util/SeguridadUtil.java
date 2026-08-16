package com.taller.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class SeguridadUtil {

    private SeguridadUtil() { }

    public static String hashClave(String claveTextoPlano) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(claveTextoPlano.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Algoritmo de hash no disponible", e);
        }
    }

    /**
     * Normaliza un email: la parte local se respeta (case-sensitive),
     * el dominio se convierte siempre a minúsculas.
     * Ejemplo: "EMILIANO@GMAIL.COM" → "EMILIANO@gmail.com"
     */
    public static String normalizeEmail(String email) {
        if (email == null) return null;
        String trimmed = email.trim();
        int at = trimmed.lastIndexOf('@');
        if (at < 0) return trimmed;
        return trimmed.substring(0, at) + "@" + trimmed.substring(at + 1).toLowerCase();
    }

    /**
     * Valida que el correo electrónico tenga un formato válido.
     * Acepta cualquier dominio (empresa, gmail, hotmail, etc.).
     * No acepta nombres de usuario sin arroba por seguridad.
     */
    public static boolean esEmailValido(String email) {
        if (email == null || email.isBlank()) return false;
        String e = email.trim();
        return e.matches("^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$");
    }

    public static boolean esPasswordFuerte(String password) {
        if (password == null || password.length() < 8) return false;
        boolean tieneMayuscula = false;
        boolean tieneNumero = false;
        boolean tieneSigno = false;
        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                tieneMayuscula = true;
            } else if (Character.isDigit(c)) {
                tieneNumero = true;
            } else if (!Character.isLetterOrDigit(c)) {
                tieneSigno = true;
            }
        }
        return tieneMayuscula && tieneNumero && tieneSigno;
    }
}

