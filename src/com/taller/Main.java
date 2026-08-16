package com.taller;

import com.taller.dao.ConexionBD;
import com.taller.dao.UsuarioDAO;
import com.taller.modelo.RolUsuario;
import com.taller.ui.LoginFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        ConexionBD.inicializarEsquema();
        ConexionBD.sembrarDatosDefecto();

        // Crea las cuentas del sistema por defecto si aun no existen.
        // El Super Administrador es el único creado automáticamente al inicio.
        // Los demás usuarios los crea el propio admin/gerente/empleado desde la
        // interfaz.
        UsuarioDAO usuarioDAO = new UsuarioDAO();

        // ── SUPERADMIN ──────────────────────────────────────────────────────
        if (!usuarioDAO.existeUsername("admin@tallerhotwheels.com")) {
            usuarioDAO.registrar("admin@tallerhotwheels.com", "Admin123!", RolUsuario.SUPERADMIN, null);
        }

        // ── GERENTE ─────────────────────────────────────────────────────────
        if (!usuarioDAO.existeUsername("gerente@tallerhotwheels.com")) {
            usuarioDAO.registrar("gerente@tallerhotwheels.com", "Gerente1!", RolUsuario.GERENTE, null);
        }

        // ── EMPLEADO / Recepcionista ─────────────────────────────────────────
        if (!usuarioDAO.existeUsername("recepcion@tallerhotwheels.com")) {
            usuarioDAO.registrar("recepcion@tallerhotwheels.com", "Empleado1!", RolUsuario.EMPLEADO, null);
        }

        // ── MECANICOS DE PRUEBA ──────────────────────────────────────────────
        if (!usuarioDAO.existeUsername("jose.elec@gmail.com")) {
            usuarioDAO.registrar("jose.elec@gmail.com", "Taller1!", RolUsuario.MECANICO, 2);
        }

        if (!usuarioDAO.existeUsername("pedro.mec@gmail.com")) {
            usuarioDAO.registrar("pedro.mec@gmail.com", "Taller1!", RolUsuario.MECANICO, 1);
        }

        // ── CLIENTES DE PRUEBA ───────────────────────────────────────────────
        if (!usuarioDAO.existeUsername("juan.cliente@gmail.com")) {
            usuarioDAO.registrar("juan.cliente@gmail.com", "Taller1!", RolUsuario.CLIENTE, 1);
        }

        if (!usuarioDAO.existeUsername("maria.cliente@gmail.com")) {
            usuarioDAO.registrar("maria.cliente@gmail.com", "Taller1!", RolUsuario.CLIENTE, 2);
        }

        if (!usuarioDAO.existeUsername("carlos.cliente@gmail.com")) {
            usuarioDAO.registrar("carlos.cliente@gmail.com", "Taller1!", RolUsuario.CLIENTE, 3);
        }

        if (!usuarioDAO.existeUsername("ana.cliente@gmail.com")) {
            usuarioDAO.registrar("ana.cliente@gmail.com", "Taller1!", RolUsuario.CLIENTE, 4);
        }

        if (!usuarioDAO.existeUsername("luis.cliente@gmail.com")) {
            usuarioDAO.registrar("luis.cliente@gmail.com", "Taller1!", RolUsuario.CLIENTE, 5);
        }

        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}
