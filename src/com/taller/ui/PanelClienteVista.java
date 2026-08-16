package com.taller.ui;

import com.taller.dao.BitacoraDAO;
import com.taller.dao.OrdenDAO;
import com.taller.dao.UsuarioDAO;
import com.taller.dao.VehiculoDAO;
import com.taller.modelo.EstatusVehiculo;
import com.taller.modelo.ItemPresupuesto;
import com.taller.modelo.OrdenReparacion;
import com.taller.modelo.Usuario;
import com.taller.modelo.Vehiculo;
import com.taller.util.Sesion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Ventana exclusiva para el rol CLIENTE.
 * Solo puede VER el estatus de sus propios vehículos,
 * las órdenes asociadas y los comentarios del mecánico.
 * No puede modificar nada.
 */
public class PanelClienteVista extends JFrame {

    private final VehiculoDAO vehiculoDAO = new VehiculoDAO();
    private final OrdenDAO ordenDAO       = new OrdenDAO();
    private final BitacoraDAO bitacoraDAO = new BitacoraDAO();

    private JPanel panelVehiculos;
    private JLabel lblBienvenida;

    public PanelClienteVista() {
        Usuario u = Sesion.getUsuarioActual();
        setTitle("Hotwheels Tam — Mi vehículo");
        setSize(800, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(true);
        construirUI();
    }

    private void construirUI() {
        // Panel principal con fondo degradado
        JPanel root = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(15, 25, 50), 0, getHeight(), new Color(30, 50, 90));
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // ── ENCABEZADO ────────────────────────────────────────────────────────
        JPanel encabezado = new JPanel(new BorderLayout());
        encabezado.setOpaque(false);
        encabezado.setBorder(new EmptyBorder(22, 28, 16, 28));

        JPanel infoPanel = new JPanel();
        infoPanel.setOpaque(false);
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Estado de mi vehículo");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);
        titulo.setAlignmentX(Component.LEFT_ALIGNMENT);

        lblBienvenida = new JLabel();
        lblBienvenida.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblBienvenida.setForeground(Estilos.NARANJA);
        lblBienvenida.setAlignmentX(Component.LEFT_ALIGNMENT);

        infoPanel.add(titulo);
        infoPanel.add(Box.createVerticalStrut(4));
        infoPanel.add(lblBienvenida);
        encabezado.add(infoPanel, BorderLayout.WEST);

        // Botones de acción
        JPanel botonesHdr = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        botonesHdr.setOpaque(false);

        JButton btnActualizar = new JButton("↻  Actualizar");
        estilizarBoton(btnActualizar, Estilos.NARANJA);
        btnActualizar.addActionListener(e -> cargarDatos());
        botonesHdr.add(btnActualizar);

        JButton btnSalir = new JButton("Cerrar sesión");
        estilizarBoton(btnSalir, Estilos.ROJO);
        btnSalir.addActionListener(e -> cerrarSesion());
        botonesHdr.add(btnSalir);

        encabezado.add(botonesHdr, BorderLayout.EAST);
        root.add(encabezado, BorderLayout.NORTH);

        // ── ÁREA DE VEHÍCULOS (scroll) ─────────────────────────────────────────
        panelVehiculos = new JPanel();
        panelVehiculos.setOpaque(false);
        panelVehiculos.setLayout(new BoxLayout(panelVehiculos, BoxLayout.Y_AXIS));
        panelVehiculos.setBorder(new EmptyBorder(0, 24, 24, 24));

        JScrollPane scroll = new JScrollPane(panelVehiculos);
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        root.add(scroll, BorderLayout.CENTER);

        // ── PIE DE PÁGINA ────────────────────────────────────────────────────
        JPanel pie = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pie.setOpaque(false);
        JLabel lblPie = new JLabel("Hotwheels Tam © 2026  |  Tu vehículo está en buenas manos");
        lblPie.setFont(new Font("SansSerif", Font.ITALIC, 11));
        lblPie.setForeground(new Color(130, 150, 200));
        pie.add(lblPie);
        root.add(pie, BorderLayout.SOUTH);

        setContentPane(root);
        cargarDatos();
    }

    private void cargarDatos() {
        Usuario u = Sesion.getUsuarioActual();
        if (u == null) return;

        lblBienvenida.setText("Bienvenido, " + u.getUsername() + "  —  Solo puedes consultar el estatus de tus vehículos.");

        panelVehiculos.removeAll();

        if (u.getPersonaId() == null) {
            new UsuarioDAO().autoVincular(u);
        }

        if (u.getPersonaId() == null) {
            JLabel aviso = new JLabel("⚠  Tu cuenta aún no está vinculada a un cliente en el sistema.");
            aviso.setFont(new Font("SansSerif", Font.BOLD, 14));
            aviso.setForeground(Estilos.NARANJA);
            aviso.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelVehiculos.add(Box.createVerticalStrut(30));
            panelVehiculos.add(aviso);
            panelVehiculos.revalidate();
            panelVehiculos.repaint();
            return;
        }

        List<Vehiculo> vehiculos = vehiculoDAO.listarPorCliente(u.getPersonaId());

        if (vehiculos.isEmpty()) {
            JLabel aviso = new JLabel("No tienes vehículos registrados en el taller.");
            aviso.setFont(new Font("SansSerif", Font.PLAIN, 15));
            aviso.setForeground(new Color(200, 210, 230));
            aviso.setAlignmentX(Component.LEFT_ALIGNMENT);
            panelVehiculos.add(Box.createVerticalStrut(30));
            panelVehiculos.add(aviso);
        } else {
            for (Vehiculo v : vehiculos) {
                panelVehiculos.add(crearTarjetaVehiculo(v));
                panelVehiculos.add(Box.createVerticalStrut(20));
            }
        }

        panelVehiculos.revalidate();
        panelVehiculos.repaint();
        bitacoraDAO.registrar("CONSULTAR", "Cliente " + u.getUsername() + " consultó el estatus de sus vehículos");
    }

    private JPanel crearTarjetaVehiculo(Vehiculo v) {
        // Card principal con fondo semitransparente
        JPanel card = new JPanel(new BorderLayout(0, 10)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 25));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);
                g2.setColor(new Color(255, 255, 255, 40));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(18, 22, 18, 22));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 9999));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);

        // ── Encabezado de la tarjeta ──────────────────────────────────────────
        JPanel encVehiculo = new JPanel(new BorderLayout());
        encVehiculo.setOpaque(false);

        JLabel lblVehiculo = new JLabel(v.getMarca() + " " + v.getModelo() + " " + v.getAnio());
        lblVehiculo.setFont(new Font("SansSerif", Font.BOLD, 18));
        lblVehiculo.setForeground(Color.WHITE);

        JLabel lblPlacas = new JLabel("Placas: " + v.getPlacas() + "   |   Color: " + v.getColor());
        lblPlacas.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblPlacas.setForeground(new Color(190, 210, 240));

        JPanel infoVeh = new JPanel();
        infoVeh.setOpaque(false);
        infoVeh.setLayout(new BoxLayout(infoVeh, BoxLayout.Y_AXIS));
        infoVeh.add(lblVehiculo);
        infoVeh.add(Box.createVerticalStrut(3));
        infoVeh.add(lblPlacas);
        encVehiculo.add(infoVeh, BorderLayout.WEST);

        // Badge de estatus con color
        JLabel lblEstatus = crearBadgeEstatus(v.getEstatus());
        encVehiculo.add(lblEstatus, BorderLayout.EAST);

        card.add(encVehiculo, BorderLayout.NORTH);

        // ── Órdenes del vehículo ──────────────────────────────────────────────
        List<OrdenReparacion> ordenes = ordenDAO.listarPorVehiculo(v.getId());
        if (ordenes.isEmpty()) {
            JLabel sinOrd = new JLabel("Sin órdenes de reparación registradas.");
            sinOrd.setFont(new Font("SansSerif", Font.ITALIC, 13));
            sinOrd.setForeground(new Color(160, 180, 220));
            card.add(sinOrd, BorderLayout.CENTER);
        } else {
            JPanel panelOrdenes = new JPanel();
            panelOrdenes.setOpaque(false);
            panelOrdenes.setLayout(new BoxLayout(panelOrdenes, BoxLayout.Y_AXIS));

            for (OrdenReparacion o : ordenes) {
                panelOrdenes.add(crearFilaOrden(o));
                panelOrdenes.add(Box.createVerticalStrut(8));
            }
            card.add(panelOrdenes, BorderLayout.CENTER);
        }

        return card;
    }

    private JPanel crearFilaOrden(OrdenReparacion o) {
        JPanel fila = new JPanel(new BorderLayout(12, 0)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(0, 0, 0, 40));
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
            }
        };
        fila.setOpaque(false);
        fila.setBorder(new EmptyBorder(10, 14, 10, 14));
        fila.setAlignmentX(Component.LEFT_ALIGNMENT);
        fila.setMaximumSize(new Dimension(Integer.MAX_VALUE, 9999));

        // Info de la orden
        JPanel infoOrden = new JPanel();
        infoOrden.setOpaque(false);
        infoOrden.setLayout(new BoxLayout(infoOrden, BoxLayout.Y_AXIS));

        JLabel lblNum = new JLabel("Orden #" + o.getId() + "   —   " +
            o.getFechaIngreso().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        lblNum.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblNum.setForeground(Estilos.NARANJA);

        JLabel lblProblema = new JLabel("Problema: " + o.getDescripcionProblema());
        lblProblema.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblProblema.setForeground(Color.WHITE);

        // Mostrar ítems del presupuesto como comentarios del mecánico
        List<ItemPresupuesto> items = ordenDAO.listarItems(o.getId());
        StringBuilder comentarios = new StringBuilder();
        if (!items.isEmpty()) {
            comentarios.append("Trabajo: ");
            for (int i = 0; i < items.size(); i++) {
                if (i > 0) comentarios.append(", ");
                comentarios.append(items.get(i).getDescripcion());
            }
        }

        infoOrden.add(lblNum);
        infoOrden.add(Box.createVerticalStrut(4));
        infoOrden.add(lblProblema);
        if (comentarios.length() > 0) {
            JLabel lblComentarios = new JLabel(comentarios.toString());
            lblComentarios.setFont(new Font("SansSerif", Font.ITALIC, 12));
            lblComentarios.setForeground(new Color(180, 200, 240));
            infoOrden.add(Box.createVerticalStrut(2));
            infoOrden.add(lblComentarios);
        }

        fila.add(infoOrden, BorderLayout.CENTER);
        fila.add(crearBadgeEstatus(o.getEstatus()), BorderLayout.EAST);

        return fila;
    }

    private JLabel crearBadgeEstatus(EstatusVehiculo estatus) {
        String texto;
        Color color;
        switch (estatus) {
            case LISTO -> { texto = "LISTO"; color = new Color(39, 174, 96); }
            case ESPERA_PIEZAS -> { texto = "ESPERA PIEZAS"; color = new Color(230, 126, 34); }
            default -> { texto = "EN REVISIÓN"; color = new Color(52, 152, 219); }
        }
        JLabel badge = new JLabel("  " + texto + "  ");
        badge.setFont(new Font("SansSerif", Font.BOLD, 12));
        badge.setForeground(Color.WHITE);
        badge.setOpaque(true);
        badge.setBackground(color);
        badge.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 1, true),
            new EmptyBorder(6, 10, 6, 10)
        ));
        return badge;
    }

    private void estilizarBoton(JButton btn, Color color) {
        btn.setFont(new Font("SansSerif", Font.BOLD, 12));
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setBorder(new EmptyBorder(8, 16, 8, 16));
    }

    private void cerrarSesion() {
        bitacoraDAO.registrar("LOGOUT", "Cierre de sesión de " + Sesion.getUsuarioActual().getUsername());
        Sesion.cerrar();
        new LoginFrame().setVisible(true);
        dispose();
    }
}
