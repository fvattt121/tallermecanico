package com.taller.ui;

import com.taller.dao.BitacoraDAO;
import com.taller.modelo.RolUsuario;
import com.taller.modelo.Usuario;
import com.taller.util.Sesion;

import javax.swing.*;
import java.awt.*;

/**
 * Panel principal del sistema. La barra lateral y los módulos visibles
 * se adaptan dinámicamente al rol del usuario en sesión:
 *
 *  SUPERADMIN → acceso total: todos los módulos + Bitácora + Gestión de Usuarios/Personas
 *  GERENTE    → igual que SUPERADMIN, excepto que no puede crear SUPERADMIN
 *  MECANICO   → solo sus órdenes asignadas + estatus general
 *  EMPLEADO   → estatus, recepción de vehículos, órdenes (sin eliminar), refacciones (solo ver)
 *               puede crear usuarios de tipo CLIENTE
 *
 * Nota: CLIENTE no llega aquí; el LoginFrame lo redirige a PanelClienteVista.
 */
public class MainDashboard extends JFrame {

    private final CardLayout cardLayout = new CardLayout();
    private final JPanel panelContenido = new JPanel(cardLayout);
    private final BitacoraDAO bitacoraDAO = new BitacoraDAO();

    public MainDashboard() {
        Usuario u = Sesion.getUsuarioActual();
        setTitle("Hotwheels Tam – " + u.getUsername() + " (" + etiquetaRol(u.getRol()) + ")");
        setSize(1100, 680);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setLayout(new BorderLayout());
        add(construirEncabezado(), BorderLayout.NORTH);
        add(construirMenuLateral(), BorderLayout.WEST);

        panelContenido.setBackground(Estilos.GRIS_CLARO);

        // Módulos según rol
        boolean esSuperAdmin = u.getRol() == RolUsuario.SUPERADMIN;
        boolean esGerente    = u.getRol() == RolUsuario.GERENTE;
        boolean esMecanico   = u.getRol() == RolUsuario.MECANICO;
        boolean esEmpleado   = u.getRol() == RolUsuario.EMPLEADO;

        // Estatus: visible para todos excepto CLIENTE (ya no llega aquí)
        panelContenido.add(crearScroll(new PanelEstatus(cardLayout, panelContenido)), "ESTATUS");

        // Vehículos: SUPERADMIN, GERENTE, EMPLEADO (recepción)
        if (esSuperAdmin || esGerente || esEmpleado) {
            panelContenido.add(crearScroll(new PanelVehiculos()), "VEHICULOS");
        }

        // Órdenes: todos excepto solo empleado con restricciones (el panel internamente controla)
        panelContenido.add(crearScroll(new PanelOrdenes()), "ORDENES");

        // Refacciones: SUPERADMIN, GERENTE, EMPLEADO (solo ver), MECANICO (solo ver)
        if (esSuperAdmin || esGerente || esEmpleado || esMecanico) {
            panelContenido.add(crearScroll(new PanelRefacciones()), "REFACCIONES");
        }

        // Gestión de personas (clientes y mecánicos): SUPERADMIN y GERENTE
        if (esSuperAdmin || esGerente) {
            panelContenido.add(crearScroll(new PanelPersonas()), "PERSONAS");
        }

        // Gestión de usuarios: SUPERADMIN, GERENTE y EMPLEADO (con permisos distintos)
        if (esSuperAdmin || esGerente || esEmpleado) {
            panelContenido.add(crearScroll(new PanelUsuarios()), "USUARIOS");
        }

        // Bitácora/Auditoría: solo SUPERADMIN y GERENTE
        if (esSuperAdmin || esGerente) {
            panelContenido.add(crearScroll(new PanelBitacora()), "BITACORA");
        }

        add(panelContenido, BorderLayout.CENTER);
        cardLayout.show(panelContenido, "ESTATUS");
    }

    private JScrollPane crearScroll(JPanel panel) {
        final JScrollPane sp = new JScrollPane(panel);
        sp.setBorder(null);
        sp.getVerticalScrollBar().setUnitIncrement(16);
        sp.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        panel.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 20, 45, 20));
        return sp;
    }

    private JPanel construirEncabezado() {
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setBackground(Estilos.AZUL_OSCURO);
        encabezado.setPreferredSize(new Dimension(100, 55));

        JLabel titulo = new JLabel("  Hotwheels Tam – Sistema de Gestión");
        titulo.setForeground(Color.WHITE);
        titulo.setFont(Estilos.SUBTITULO);
        encabezado.add(titulo, BorderLayout.WEST);

        Usuario u = Sesion.getUsuarioActual();
        JLabel usuarioLbl = new JLabel(u.getUsername() + "  ·  " + etiquetaRol(u.getRol()) + "   ");
        usuarioLbl.setForeground(Estilos.NARANJA);
        usuarioLbl.setFont(Estilos.NORMAL);
        encabezado.add(usuarioLbl, BorderLayout.EAST);
        return encabezado;
    }

    private JPanel construirMenuLateral() {
        JPanel menu = new JPanel();
        menu.setBackground(Estilos.AZUL_MEDIO);
        menu.setPreferredSize(new Dimension(220, 100));
        menu.setLayout(new BoxLayout(menu, BoxLayout.Y_AXIS));
        menu.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 15, 20, 15));

        Usuario u = Sesion.getUsuarioActual();
        boolean esSuperAdmin = u.getRol() == RolUsuario.SUPERADMIN;
        boolean esGerente    = u.getRol() == RolUsuario.GERENTE;
        boolean esMecanico   = u.getRol() == RolUsuario.MECANICO;
        boolean esEmpleado   = u.getRol() == RolUsuario.EMPLEADO;

        // Estatus siempre visible
        agregarBotonMenu(menu, "Estatus del sistema", "ESTATUS");

        // Vehículos: SUPERADMIN, GERENTE, EMPLEADO
        if (esSuperAdmin || esGerente || esEmpleado) {
            agregarBotonMenu(menu, "Recepcion de vehiculos", "VEHICULOS");
        }

        // Órdenes: todos
        agregarBotonMenu(menu, "Ordenes y presupuestos", "ORDENES");

        // Refacciones
        if (esSuperAdmin || esGerente || esEmpleado || esMecanico) {
            agregarBotonMenu(menu, "Inventario refacciones", "REFACCIONES");
        }

        // Personas: SUPERADMIN y GERENTE
        if (esSuperAdmin || esGerente) {
            agregarBotonMenu(menu, "Clientes y mecanicos", "PERSONAS");
        }

        // Usuarios: SUPERADMIN, GERENTE y EMPLEADO
        if (esSuperAdmin || esGerente || esEmpleado) {
            agregarBotonMenu(menu, "Gestion de usuarios", "USUARIOS");
        }

        // Bitácora/Auditoría: SUPERADMIN y GERENTE
        if (esSuperAdmin || esGerente) {
            agregarBotonMenu(menu, "Bitacora / Auditoria", "BITACORA");
        }

        menu.add(Box.createVerticalGlue());

        BotonEstilizado btnSalir = new BotonEstilizado("Cerrar sesion", Estilos.ROJO);
        btnSalir.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnSalir.setMaximumSize(new Dimension(190, 40));
        btnSalir.addActionListener(e -> cerrarSesion());
        menu.add(btnSalir);
        return menu;
    }

    private void agregarBotonMenu(JPanel menu, String texto, String card) {
        BotonEstilizado btn = new BotonEstilizado(texto, Estilos.AZUL_OSCURO);
        btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        btn.setMaximumSize(new Dimension(190, 40));
        btn.addActionListener(e -> {
            cardLayout.show(panelContenido, card);
            bitacoraDAO.registrar("NAVEGAR", "Usuario accedió a la pestaña: " + texto);
            for (Component c : panelContenido.getComponents()) {
                if (c.isVisible()) {
                    Component panelInterno = (c instanceof JScrollPane)
                        ? ((JScrollPane) c).getViewport().getView() : c;
                    if (panelInterno instanceof Refrescable) {
                        ((Refrescable) panelInterno).refrescar();
                    }
                }
            }
        });
        menu.add(btn);
        menu.add(Box.createVerticalStrut(8));
    }

    private void cerrarSesion() {
        bitacoraDAO.registrar("LOGOUT", "Cierre de sesion de " + Sesion.getUsuarioActual().getUsername());
        Sesion.cerrar();
        new LoginFrame().setVisible(true);
        dispose();
    }

    /** Convierte el enum de rol a una etiqueta legible en español. */
    public static String etiquetaRol(RolUsuario rol) {
        return switch (rol) {
            case SUPERADMIN -> "Super Administrador";
            case GERENTE    -> "Gerente";
            case MECANICO   -> "Mecánico";
            case EMPLEADO   -> "Empleado";
            case CLIENTE    -> "Cliente";
        };
    }
}
