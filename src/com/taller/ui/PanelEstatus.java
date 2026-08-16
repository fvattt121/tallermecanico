package com.taller.ui;

import com.taller.dao.BitacoraDAO;
import com.taller.dao.ConexionBD;
import com.taller.util.Sesion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PanelEstatus extends JPanel implements Refrescable {

    private final BitacoraDAO bitacoraDAO = new BitacoraDAO();
    private final CardLayout cardLayout;
    private final JPanel panelContenido;

    private JLabel lblClientes, lblMecanicos, lblVehiculos, lblOrdenes, lblRefacciones, lblBitacora;
    private JLabel lblUltimoEvento;
    private JLabel lblBienvenida;

    public PanelEstatus(CardLayout cardLayout, JPanel panelContenido) {
        this.cardLayout = cardLayout;
        this.panelContenido = panelContenido;
        setLayout(new BorderLayout(0, 0));
        setBackground(Estilos.GRIS_CLARO);

        add(construirEncabezado(), BorderLayout.NORTH);
        add(construirCuerpo(), BorderLayout.CENTER);
        add(construirPiePagina(), BorderLayout.SOUTH);

        refrescar();
    }

    private JPanel construirEncabezado() {
        JPanel p = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, Estilos.AZUL_OSCURO, getWidth(), 0, Estilos.AZUL_MEDIO);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        p.setLayout(new BorderLayout());
        p.setBorder(new EmptyBorder(22, 28, 22, 28));

        JPanel izq = new JPanel();
        izq.setOpaque(false);
        izq.setLayout(new BoxLayout(izq, BoxLayout.Y_AXIS));

        JLabel titulo = new JLabel("Panel de Control — Estatus del Sistema");
        titulo.setFont(new Font("SansSerif", Font.BOLD, 22));
        titulo.setForeground(Color.WHITE);

        lblBienvenida = new JLabel();
        lblBienvenida.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblBienvenida.setForeground(Estilos.NARANJA);

        izq.add(titulo);
        izq.add(Box.createVerticalStrut(4));
        izq.add(lblBienvenida);
        p.add(izq, BorderLayout.WEST);

        JLabel reloj = new JLabel(LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy  HH:mm")));
        reloj.setFont(new Font("SansSerif", Font.PLAIN, 13));
        reloj.setForeground(new Color(180, 200, 255));
        p.add(reloj, BorderLayout.EAST);
        return p;
    }

    private JPanel construirCuerpo() {
        JPanel cuerpo = new JPanel(new BorderLayout(20, 20));
        cuerpo.setBackground(Estilos.GRIS_CLARO);
        cuerpo.setBorder(new EmptyBorder(24, 24, 10, 24));

        com.taller.modelo.Usuario actual = Sesion.getUsuarioActual();
        boolean esAdmin = actual != null && (actual.getRol() == com.taller.modelo.RolUsuario.SUPERADMIN
            || actual.getRol() == com.taller.modelo.RolUsuario.GERENTE);

        // ---- Tarjetas de métricas ----
        int numColumnas = esAdmin ? 3 : 2;
        JPanel gridTarjetas = new JPanel(new GridLayout(esAdmin ? 2 : 3, numColumnas, 16, 16));
        gridTarjetas.setBackground(Estilos.GRIS_CLARO);

        lblClientes    = new JLabel("0");
        lblMecanicos   = new JLabel("0");
        lblVehiculos   = new JLabel("0");
        lblOrdenes     = new JLabel("0");
        lblRefacciones = new JLabel("0");
        lblBitacora    = new JLabel("0");

        gridTarjetas.add(crearTarjeta("Clientes", lblClientes, new Color(41, 128, 185)));
        gridTarjetas.add(crearTarjeta("Mecánicos", lblMecanicos, new Color(39, 174, 96)));
        gridTarjetas.add(crearTarjeta("Vehículos", lblVehiculos, new Color(142, 68, 173)));
        gridTarjetas.add(crearTarjeta("Órdenes Activas", lblOrdenes, new Color(230, 126, 34)));
        gridTarjetas.add(crearTarjeta("Refacciones", lblRefacciones, new Color(192, 57, 43)));

        // Solo muestra la tarjeta de Auditoría a Administradores y Gerentes
        if (esAdmin) {
            gridTarjetas.add(crearTarjeta("Auditoría", lblBitacora, new Color(44, 62, 80)));
        }

        cuerpo.add(gridTarjetas, BorderLayout.CENTER);

        // ---- Panel de acciones rápidas ----
        JPanel acciones = new JPanel();
        acciones.setBackground(Color.WHITE);
        acciones.setLayout(new BoxLayout(acciones, BoxLayout.Y_AXIS));
        acciones.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(220, 225, 235), 1),
            new EmptyBorder(16, 16, 16, 16)
        ));

        lblUltimoEvento = new JLabel("—");
        lblUltimoEvento.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblUltimoEvento.setForeground(Color.DARK_GRAY);
        lblUltimoEvento.setAlignmentX(Component.LEFT_ALIGNMENT);

        // Solo muestra el último evento registrado a SuperAdmin y Gerente
        if (esAdmin) {
            JLabel lblAcc = new JLabel("Último evento registrado:");
            lblAcc.setFont(new Font("SansSerif", Font.BOLD, 13));
            lblAcc.setForeground(Estilos.AZUL_OSCURO);
            lblAcc.setAlignmentX(Component.LEFT_ALIGNMENT);

            acciones.add(lblAcc);
            acciones.add(Box.createVerticalStrut(6));
            acciones.add(lblUltimoEvento);
            acciones.add(Box.createVerticalStrut(16));
        }

        // Botones rápidos
        JLabel lblAccR = new JLabel("Acciones Rápidas:");
        lblAccR.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblAccR.setForeground(Estilos.AZUL_OSCURO);
        lblAccR.setAlignmentX(Component.LEFT_ALIGNMENT);
        acciones.add(lblAccR);
        acciones.add(Box.createVerticalStrut(10));

        String[] nombresBtn;
        Color[] colores;
        String[] destinos;

        if (esAdmin) {
            nombresBtn = new String[]{"Actualizar datos", "Ver auditoria", "Ir a vehículos"};
            colores = new Color[]{Estilos.NARANJA, Estilos.AZUL_MEDIO, Estilos.VERDE};
            destinos = new String[]{null, "BITACORA", "VEHICULOS"};
        } else {
            nombresBtn = new String[]{"Actualizar datos", "Ir a vehículos", "Ir a órdenes"};
            colores = new Color[]{Estilos.NARANJA, Estilos.AZUL_MEDIO, Estilos.VERDE};
            destinos = new String[]{null, "VEHICULOS", "ORDENES"};
        }

        for (int i = 0; i < nombresBtn.length; i++) {
            BotonEstilizado b = new BotonEstilizado(nombresBtn[i], colores[i]);
            b.setAlignmentX(Component.LEFT_ALIGNMENT);
            b.setMaximumSize(new Dimension(Integer.MAX_VALUE, 36));
            final int idx = i;
            final String destino = destinos[i];
            b.addActionListener(ev -> {
                if (idx == 0) {
                    refrescar();
                } else {
                    cardLayout.show(panelContenido, destino);
                    bitacoraDAO.registrar("NAVEGAR", "Acceso rápido desde Estatus → " + destino);
                    for (Component c : panelContenido.getComponents()) {
                        if (c.isVisible()) {
                            Component inner = (c instanceof JScrollPane)
                                ? ((JScrollPane) c).getViewport().getView() : c;
                            if (inner instanceof Refrescable) ((Refrescable) inner).refrescar();
                        }
                    }
                }
            });
            acciones.add(b);
            acciones.add(Box.createVerticalStrut(8));
        }

        JPanel derecha = new JPanel(new BorderLayout());
        derecha.setBackground(Estilos.GRIS_CLARO);
        derecha.setPreferredSize(new Dimension(260, 100));
        derecha.add(acciones, BorderLayout.CENTER);
        cuerpo.add(derecha, BorderLayout.EAST);

        return cuerpo;
    }

    private JPanel crearTarjeta(String titulo, JLabel lblNumero, Color color) {
        JPanel card = new JPanel(new BorderLayout(0, 8)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, color, 0, getHeight(), color.darker());
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 18, 18);
            }
        };
        card.setOpaque(false);
        card.setBorder(new EmptyBorder(18, 20, 18, 20));

        JLabel tituloLbl = new JLabel(titulo);
        tituloLbl.setFont(new Font("SansSerif", Font.BOLD, 14));
        tituloLbl.setForeground(new Color(220, 235, 255));

        lblNumero.setFont(new Font("SansSerif", Font.BOLD, 42));
        lblNumero.setForeground(Color.WHITE);
        lblNumero.setHorizontalAlignment(SwingConstants.CENTER);

        card.add(tituloLbl, BorderLayout.NORTH);
        card.add(lblNumero, BorderLayout.CENTER);
        return card;
    }

    private JPanel construirPiePagina() {
        JPanel pie = new JPanel(new FlowLayout(FlowLayout.LEFT, 16, 10));
        pie.setBackground(new Color(225, 228, 235));
        pie.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 210, 225)));

        JLabel info = new JLabel("Hotwheels Tam © 2026  |  Sistema de Gestión de Taller");
        info.setFont(new Font("SansSerif", Font.PLAIN, 11));
        info.setForeground(Color.GRAY);
        pie.add(info);
        return pie;
    }

    @Override
    public void refrescar() {
        com.taller.modelo.Usuario actual = Sesion.getUsuarioActual();
        if (actual != null) {
            String rolLabel = MainDashboard.etiquetaRol(actual.getRol());
            lblBienvenida.setText("Bienvenido, " + actual.getUsername() + " — " + rolLabel);
        }

        try (Statement st = ConexionBD.getConexion().createStatement()) {
            lblClientes.setText(String.valueOf(contar(st, "clientes")));
            lblMecanicos.setText(String.valueOf(contar(st, "mecanicos")));
            lblVehiculos.setText(String.valueOf(contar(st, "vehiculos")));
            lblOrdenes.setText(String.valueOf(contar(st, "ordenes")));
            lblRefacciones.setText(String.valueOf(contar(st, "refacciones")));
            lblBitacora.setText(String.valueOf(contar(st, "bitacora")));
        } catch (Exception e) {
            System.err.println("Error al actualizar estatus: " + e.getMessage());
        }

        // Último evento
        if (lblUltimoEvento != null) {
            try {
                java.util.List<com.taller.modelo.RegistroBitacora> lista = bitacoraDAO.listarTodos();
                if (!lista.isEmpty()) {
                    com.taller.modelo.RegistroBitacora ult = lista.get(0);
                    lblUltimoEvento.setText("[" + ult.getAccion() + "] " + ult.getDetalle() +
                        " — " + ult.getFechaHora().format(DateTimeFormatter.ofPattern("dd/MM HH:mm")));
                }
            } catch (Exception ignored) {}
        }
    }

    private int contar(Statement st, String tabla) throws java.sql.SQLException {
        String sql = "bitacora".equals(tabla) ? "SELECT COUNT(*) FROM bitacora" : "SELECT COUNT(*) FROM " + tabla + " WHERE activo = 1";
        try (ResultSet rs = st.executeQuery(sql)) {
            rs.next();
            return rs.getInt(1);
        }
    }
}
