package com.taller.ui;

import com.taller.dao.*;
import com.taller.modelo.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PanelOrdenes extends JPanel implements Refrescable {

    private final VehiculoDAO vehiculoDAO  = new VehiculoDAO();
    private final MecanicoDAO mecanicoDAO  = new MecanicoDAO();
    private final RefaccionDAO refaccionDAO = new RefaccionDAO();
    private final OrdenDAO ordenDAO         = new OrdenDAO();
    private final BitacoraDAO bitacoraDAO   = new BitacoraDAO();

    private final DefaultListModel<OrdenReparacion> modeloOrdenes = new DefaultListModel<>();
    private final JList<OrdenReparacion> listaOrdenes = new JList<>(modeloOrdenes);
    private final DefaultTableModel modeloItems = new DefaultTableModel(
        new Object[]{"Tipo", "Descripción", "Subtotal"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tablaItems = new JTable(modeloItems);
    private final JLabel lblTotal = new JLabel("Total: $0.00");
    private final JLabel lblOrdenInfo = new JLabel("Selecciona una orden de la lista");

    // Combos reutilizables en la orden actual
    private JComboBox<Vehiculo>  comboVehiculo;
    private JComboBox<Mecanico>  comboMecanico;
    private JComboBox<Refaccion> comboRef;
    private BotonEstilizado btnElimOrden;
    private JPanel formsPanel;

    public PanelOrdenes() {
        setLayout(new BorderLayout(0, 0));
        setBackground(Estilos.GRIS_CLARO);

        JPanel panelNorteCompuesto = new JPanel(new BorderLayout());
        panelNorteCompuesto.add(construirEncabezado(), BorderLayout.NORTH);
        panelNorteCompuesto.add(construirFormularioNuevaOrden(), BorderLayout.SOUTH);
        add(panelNorteCompuesto, BorderLayout.NORTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        split.setResizeWeight(0.30);
        split.setBorder(null);
        split.setDividerSize(5);

        split.setLeftComponent(construirPanelListaOrdenes());
        split.setRightComponent(construirPanelDetalle());

        add(split, BorderLayout.CENTER);

        refrescar();
    }

    // ──────────────────────────────────── ENCABEZADO ─────────────────────────
    private JPanel construirEncabezado() {
        JPanel p = new JPanel(new BorderLayout()) {
            @Override protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, Estilos.AZUL_OSCURO, getWidth(), 0, Estilos.AZUL_MEDIO);
                g2.setPaint(gp);
                g2.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        p.setBorder(new EmptyBorder(14, 22, 14, 22));
        JLabel t = new JLabel("Órdenes de Reparación y Presupuestos");
        t.setFont(new Font("SansSerif", Font.BOLD, 20));
        t.setForeground(Color.WHITE);
        p.add(t, BorderLayout.WEST);

        JButton btnRefrescar = new JButton("Actualizar");
        btnRefrescar.setFont(new Font("SansSerif", Font.BOLD, 12));
        btnRefrescar.setBackground(Estilos.NARANJA);
        btnRefrescar.setForeground(Color.WHITE);
        btnRefrescar.setBorderPainted(false);
        btnRefrescar.setFocusPainted(false);
        btnRefrescar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRefrescar.addActionListener(e -> refrescar());
        p.add(btnRefrescar, BorderLayout.EAST);
        return p;
    }

    // ──────────────────────────────────── LISTA IZQUIERDA ────────────────────
    private JPanel construirPanelListaOrdenes() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(210, 215, 225)));

        JLabel hdr = new JLabel("  Órdenes Registradas");
        hdr.setFont(new Font("SansSerif", Font.BOLD, 13));
        hdr.setForeground(Estilos.AZUL_OSCURO);
        hdr.setBackground(new Color(235, 239, 248));
        hdr.setOpaque(true);
        hdr.setBorder(new EmptyBorder(10, 10, 10, 10));
        p.add(hdr, BorderLayout.NORTH);

        listaOrdenes.setFont(new Font("SansSerif", Font.PLAIN, 13));
        listaOrdenes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listaOrdenes.setCellRenderer((list, orden, idx, sel, focus) -> {
            JPanel cell = new JPanel(new BorderLayout(6, 0));
            cell.setBorder(new EmptyBorder(10, 12, 10, 12));

            String vehiculoInfo = "Vehículo #" + orden.getVehiculoId();
            try {
                Vehiculo v = vehiculoDAO.listarTodos().stream()
                    .filter(x -> x.getId() == orden.getVehiculoId())
                    .findFirst().orElse(null);
                if (v != null) vehiculoInfo = v.getPlacas() + " — " + v.getMarca();
            } catch (Exception ignored) {}

            JLabel veh = new JLabel();
            if (!orden.isActivo()) {
                veh.setText("<html><s>" + vehiculoInfo + "</s> <font color='#c0392b' size='2'><b>(Archivada)</b></font></html>");
                veh.setFont(new Font("SansSerif", Font.ITALIC, 13));
                veh.setForeground(Color.GRAY);
            } else {
                veh.setText(vehiculoInfo);
                veh.setFont(new Font("SansSerif", Font.BOLD, 13));
                veh.setForeground(Estilos.AZUL_OSCURO);
            }

            JLabel est = new JLabel(orden.getEstatus().getEtiqueta());
            est.setFont(new Font("SansSerif", Font.BOLD, 11));
            est.setOpaque(true);
            est.setBorder(new EmptyBorder(3, 8, 3, 8));
            est.setForeground(Color.WHITE);
            if (!orden.isActivo()) {
                est.setBackground(Color.GRAY);
            } else {
                est.setBackground(Estilos.colorEstatus(orden.getEstatus().getEtiqueta()));
            }

            JPanel textos = new JPanel();
            textos.setOpaque(false);
            textos.setLayout(new BoxLayout(textos, BoxLayout.Y_AXIS));
            textos.add(veh);

            cell.add(textos, BorderLayout.CENTER);
            cell.add(est, BorderLayout.EAST);

            if (!orden.isActivo()) {
                cell.setBackground(sel ? new Color(230, 220, 220) : new Color(248, 242, 242));
            } else {
                cell.setBackground(sel ? new Color(230, 238, 255) : Color.WHITE);
            }
            cell.setOpaque(true);

            JPanel wrapper = new JPanel(new BorderLayout());
            wrapper.setOpaque(true);
            wrapper.setBackground(Color.WHITE);
            wrapper.add(cell, BorderLayout.CENTER);
            wrapper.add(new JSeparator(), BorderLayout.SOUTH);
            return wrapper;
        });
        listaOrdenes.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) mostrarItemsDeOrdenSeleccionada();
        });

        listaOrdenes.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int index = listaOrdenes.locationToIndex(e.getPoint());
                if (index == -1 || !listaOrdenes.getCellBounds(index, index).contains(e.getPoint()) || e.getClickCount() == 2) {
                    listaOrdenes.clearSelection();
                    mostrarItemsDeOrdenSeleccionada();
                }
            }
        });

        p.add(new JScrollPane(listaOrdenes), BorderLayout.CENTER);
        return p;
    }

    // ──────────────────────────────────── PANEL DERECHO / DETALLE ─────────────
    private JPanel construirPanelDetalle() {
        JPanel panel = new JPanel(new BorderLayout(0, 0));
        panel.setBackground(Color.WHITE);

        lblOrdenInfo.setFont(new Font("SansSerif", Font.ITALIC, 13));
        lblOrdenInfo.setForeground(Color.GRAY);
        lblOrdenInfo.setBorder(new EmptyBorder(10, 16, 10, 16));
        lblOrdenInfo.setBackground(new Color(248, 249, 252));
        lblOrdenInfo.setOpaque(true);
        panel.add(lblOrdenInfo, BorderLayout.NORTH);

        tablaItems.setRowHeight(28);
        tablaItems.setFont(new Font("SansSerif", Font.PLAIN, 13));
        tablaItems.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tablaItems.getTableHeader().setBackground(Estilos.AZUL_OSCURO);
        tablaItems.getTableHeader().setForeground(Color.WHITE);
        tablaItems.setGridColor(new Color(235, 238, 245));
        tablaItems.setSelectionBackground(new Color(225, 235, 255));
        tablaItems.setFillsViewportHeight(true);

        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        tablaItems.getColumnModel().getColumn(2).setCellRenderer(rightRenderer);

        JScrollPane scrollTabla = new JScrollPane(tablaItems);
        scrollTabla.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 0, new Color(210, 215, 225)));
        panel.add(scrollTabla, BorderLayout.CENTER);

        formsPanel = new JPanel(new GridLayout(1, 2, 8, 0));
        formsPanel.setBackground(new Color(245, 247, 252));
        formsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        formsPanel.add(construirFormRefaccion());
        formsPanel.add(construirFormManoObra());
        panel.add(formsPanel, BorderLayout.NORTH);

        // Pie con total y botones
        JPanel pie = new JPanel(new BorderLayout(10, 0));
        pie.setBackground(new Color(245, 247, 252));
        pie.setBorder(new EmptyBorder(10, 16, 10, 16));

        lblTotal.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTotal.setForeground(Estilos.AZUL_OSCURO);
        pie.add(lblTotal, BorderLayout.WEST);

        JPanel botonesPie = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        botonesPie.setOpaque(false);

        JComboBox<String> comboEstOrden = new JComboBox<>(new String[]{
            "EN_REVISION", "ESPERA_PIEZAS", "LISTO"});
        comboEstOrden.setFont(new Font("SansSerif", Font.PLAIN, 12));
        comboEstOrden.setPreferredSize(new Dimension(150, 32));

        BotonEstilizado btnCambiarEst = new BotonEstilizado("Cambiar estatus", Estilos.AMARILLO);
        btnCambiarEst.addActionListener(e -> {
            OrdenReparacion orden = listaOrdenes.getSelectedValue();
            if (orden == null) { JOptionPane.showMessageDialog(this, "Selecciona una orden primero"); return; }
            if (!orden.isActivo()) {
                JOptionPane.showMessageDialog(this, "No puedes modificar el estatus de una orden archivada.\nDebes restaurar la orden primero para realizar cambios.", "Orden Archivada", JOptionPane.WARNING_MESSAGE);
                return;
            }
            String nuevoEst = (String) comboEstOrden.getSelectedItem();
            EstatusVehiculo ev;
            try { ev = EstatusVehiculo.valueOf(nuevoEst); }
            catch (Exception ex) { ev = EstatusVehiculo.EN_REVISION; }
            ordenDAO.actualizarEstatus(orden.getId(), ev);
            bitacoraDAO.registrar("ACTUALIZAR", "Estatus de orden #" + orden.getId() + " → " + nuevoEst);
            refrescar();
        });

        BotonEstilizado btnElimItem = new BotonEstilizado("Eliminar Ítem", Estilos.ROJO);
        btnElimItem.addActionListener(e -> {
            int row = tablaItems.getSelectedRow();
            OrdenReparacion orden = listaOrdenes.getSelectedValue();
            if (row == -1 || orden == null) {
                JOptionPane.showMessageDialog(this, "Selecciona una orden y un ítem para eliminar");
                return;
            }
            if (!orden.isActivo()) {
                JOptionPane.showMessageDialog(this, "No puedes eliminar ítems de una orden archivada.\nDebes restaurar la orden primero para realizar cambios.", "Orden Archivada", JOptionPane.WARNING_MESSAGE);
                return;
            }
            // REGLA DE NEGOCIO: LISTO significa trabajo entregado/pagado, no se puede modificar
            if (orden.getEstatus() == EstatusVehiculo.LISTO) {
                JOptionPane.showMessageDialog(this,
                    "La orden #" + orden.getId() + " está en estado LISTO (entregada/pagada).\n"
                    + "No se pueden eliminar ítems de una orden cerrada.\n"
                    + "Si necesitas hacer correcciones, cambia el estatus a 'En Revisión' primero.",
                    "Orden Cerrada", JOptionPane.WARNING_MESSAGE);
                return;
            }
            List<ItemPresupuesto> items = ordenDAO.listarItems(orden.getId());
            if (row < items.size()) {
                ItemPresupuesto item = items.get(row);
                if (JOptionPane.showConfirmDialog(this, "¿Eliminar ítem del presupuesto?",
                    "Confirmar", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    if (item instanceof ItemRefaccion) {
                        ItemRefaccion ir = (ItemRefaccion) item;
                        refaccionDAO.incrementarStock(ir.getRefaccionId(), ir.getCantidad());
                    }
                    ordenDAO.eliminarItem(item.getId());
                    bitacoraDAO.registrar("ELIMINAR", "Ítem de orden #" + orden.getId() + " eliminado");
                    recargarComboRefacciones();
                    mostrarItemsDeOrdenSeleccionada();
                }
            }
        });

        btnElimOrden = new BotonEstilizado("Archivar Orden", Estilos.ROJO);
        btnElimOrden.addActionListener(e -> {
            OrdenReparacion orden = listaOrdenes.getSelectedValue();
            if (orden == null) { JOptionPane.showMessageDialog(this, "Selecciona una orden primero"); return; }
            if (orden.isActivo()) {
                if (JOptionPane.showConfirmDialog(this, "¿Archivar Orden #" + orden.getId() + "?\n(Se ocultará para mecánicos, clientes y trabajadores, pero tú podrás seguir viéndola aquí)",
                    "Archivar Orden", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    ordenDAO.cambiarActivo(orden.getId(), false);
                    bitacoraDAO.registrar("ARCHIVAR", "Orden #" + orden.getId() + " archivada");
                    refrescar();
                }
            } else {
                // REGLA DE NEGOCIO: Validar que el vehículo y el cliente padre no estén archivados
                Vehiculo v = vehiculoDAO.listarTodos(true).stream()
                    .filter(x -> x.getId() == orden.getVehiculoId()).findFirst().orElse(null);
                if (v != null && !v.isActivo()) {
                    JOptionPane.showMessageDialog(this,
                        "No puedes restaurar la Orden #" + orden.getId() + " directamente.\n\n"
                        + "El vehículo asociado '" + v.getPlacas() + "' está archivado.\n"
                        + "Para restaurar esta orden debes:\n"
                        + "  1. Ir a 'Recepción de vehículos'\n"
                        + "  2. Restaurar el vehículo '" + v.getPlacas() + "'\n"
                        + "  3. La orden se restaurará automáticamente.",
                        "Restauración bloqueada - Vehículo archivado",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (v != null) {
                    String nombreCliente = vehiculoDAO.getNombreClienteSiArchivado(v.getId());
                    if (nombreCliente != null) {
                        JOptionPane.showMessageDialog(this,
                            "No puedes restaurar la Orden #" + orden.getId() + " directamente.\n\n"
                            + "El cliente '" + nombreCliente + "' está archivado.\n"
                            + "Para restaurar esta orden debes:\n"
                            + "  1. Ir a 'Clientes y mecánicos'\n"
                            + "  2. Restaurar al cliente '" + nombreCliente + "'\n"
                            + "  3. El vehículo y la orden se restaurarán automáticamente.",
                            "Restauración bloqueada - Cliente archivado",
                            JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                }

                if (JOptionPane.showConfirmDialog(this, "¿Restaurar Orden #" + orden.getId() + "?\n(Volverá a ser visible para todos los roles)",
                    "Restaurar Orden", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                    ordenDAO.cambiarActivo(orden.getId(), true);
                    bitacoraDAO.registrar("RESTAURAR", "Orden #" + orden.getId() + " restaurada");
                    refrescar();
                }
            }
        });

        botonesPie.add(comboEstOrden);
        botonesPie.add(btnCambiarEst);

        // Botones de eliminar: solo SUPERADMIN y GERENTE
        Usuario actual = com.taller.util.Sesion.getUsuarioActual();
        if (actual != null && (actual.getRol() == RolUsuario.SUPERADMIN || actual.getRol() == RolUsuario.GERENTE)) {
            botonesPie.add(Box.createHorizontalStrut(10));
            botonesPie.add(btnElimItem);
            botonesPie.add(btnElimOrden);
        }
        pie.add(botonesPie, BorderLayout.EAST);

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.setBackground(Color.WHITE);
        panelSur.add(pie, BorderLayout.CENTER);
        panel.add(panelSur, BorderLayout.SOUTH);

        return panel;
    }

    // ──────────────────────────────────── FORM AGREGAR REFACCION ─────────────
    private JPanel construirFormRefaccion() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Estilos.AZUL_MEDIO, 1, true),
            "  Agregar Refacción  ",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 12), Estilos.AZUL_OSCURO));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        List<Refaccion> refacciones = refaccionDAO.listarTodas();
        comboRef = new JComboBox<>(refacciones.toArray(new Refaccion[0]));
        comboRef.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JTextField txtCantidad = new JTextField("1");
        txtCantidad.setPreferredSize(new Dimension(80, 30));

        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2; gbc.weightx = 1;
        p.add(comboRef, gbc);

        gbc.gridwidth = 1; gbc.gridy = 1; gbc.gridx = 0; gbc.weightx = 0;
        p.add(new JLabel("Cantidad:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        p.add(txtCantidad, gbc);

        BotonEstilizado btn = new BotonEstilizado("+ Agregar al presupuesto", Estilos.AZUL_MEDIO);
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 2; gbc.weightx = 1;
        p.add(btn, gbc);

        btn.addActionListener(e -> {
            OrdenReparacion orden = listaOrdenes.getSelectedValue();
            Refaccion r = (Refaccion) comboRef.getSelectedItem();
            if (orden == null) { JOptionPane.showMessageDialog(this, "Primero selecciona una orden de la lista"); return; }
            // REGLA DE NEGOCIO: LISTO = entregado/pagado, no se pueden agregar más cargos
            if (orden.getEstatus() == EstatusVehiculo.LISTO) {
                JOptionPane.showMessageDialog(this,
                    "La orden #" + orden.getId() + " está en estado LISTO (entregada/pagada).\n"
                    + "No se pueden agregar refacciones a una orden cerrada.\n"
                    + "Si necesitas hacer correcciones, cambia el estatus a 'En Revisión' primero.",
                    "Orden Cerrada", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (r == null) return;

            String rawStr = txtCantidad.getText().trim();
            int cant;
            try {
                cant = Integer.parseInt(rawStr);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Por favor ingresa únicamente números enteros válidos (ej. 1, 2, 5).", "Cantidad inválida", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (cant <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser estrictamente mayor a 0.", "Cantidad inválida", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Descuento atómico en base de datos
            boolean exitoDescuento = refaccionDAO.descontarStock(r.getId(), cant);
            if (!exitoDescuento) {
                Refaccion fresca = refaccionDAO.buscarPorId(r.getId());
                int stockDisponible = (fresca != null) ? fresca.getStock() : 0;
                JOptionPane.showMessageDialog(this, "Stock insuficiente en almacén.\nStock disponible actual: " + stockDisponible + " piezas.", "Sin stock suficiente", JOptionPane.WARNING_MESSAGE);
                txtCantidad.setText(String.valueOf(Math.max(0, stockDisponible)));
                recargarComboRefacciones();
                return;
            }

            ItemRefaccion item = new ItemRefaccion(0, orden.getId(), r.getId(), r.getNombre(), r.getPrecioUnitario(), cant);
            ordenDAO.agregarItem(item);
            bitacoraDAO.registrar("AGREGAR", "Refacción '" + r.getNombre() + "' x" + cant + " a orden #" + orden.getId());
            txtCantidad.setText("1");
            recargarComboRefacciones();
            mostrarItemsDeOrdenSeleccionada();
        });

        return p;
    }

    // ──────────────────────────────────── FORM MANO DE OBRA ──────────────────
    private JPanel construirFormManoObra() {
        JPanel p = new JPanel(new GridBagLayout());
        p.setBackground(Color.WHITE);
        p.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Estilos.VERDE, 1, true),
            "  Agregar Mano de Obra  ",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("SansSerif", Font.BOLD, 12), Estilos.VERDE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JTextField desc      = new JTextField("Diagnóstico y reparación");
        JTextField txtTarifa = new JTextField("300.0");
        JTextField txtHoras  = new JTextField("1.0");

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        p.add(new JLabel("Descripción:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        p.add(desc, gbc);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        p.add(new JLabel("Tarifa/hora $:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        p.add(txtTarifa, gbc);

        gbc.gridx = 0; gbc.gridy = 2; gbc.weightx = 0;
        p.add(new JLabel("Horas:"), gbc);
        gbc.gridx = 1; gbc.weightx = 1;
        p.add(txtHoras, gbc);

        BotonEstilizado btn = new BotonEstilizado("+ Agregar al presupuesto", Estilos.VERDE);
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2; gbc.weightx = 1;
        p.add(btn, gbc);

        btn.addActionListener(e -> {
            OrdenReparacion orden = listaOrdenes.getSelectedValue();
            if (orden == null) { JOptionPane.showMessageDialog(this, "Primero selecciona una orden de la lista"); return; }

            // REGLA DE NEGOCIO: LISTO = entregado/pagado, no se agrega más mano de obra
            if (orden.getEstatus() == EstatusVehiculo.LISTO) {
                JOptionPane.showMessageDialog(this,
                    "La orden #" + orden.getId() + " está en estado LISTO (entregada/pagada).\n"
                    + "No se puede agregar mano de obra a una orden cerrada.\n"
                    + "Si necesitas hacer correcciones, cambia el estatus a 'En Revisión' primero.",
                    "Orden Cerrada", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double tarifa, horas;
            try {
                tarifa = Double.parseDouble(txtTarifa.getText().trim());
                horas  = Double.parseDouble(txtHoras.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "La tarifa y las horas deben ser valores numéricos válidos (ej. 300.0 y 1.5).", "Valores inválidos", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (tarifa < 0 || horas <= 0) {
                JOptionPane.showMessageDialog(this, "La tarifa no puede ser negativa y las horas deben ser mayores a 0.", "Valores inválidos", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (desc.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Ingresa una descripción para la mano de obra.", "Descripción vacía", JOptionPane.WARNING_MESSAGE);
                return;
            }

            ItemManoObra item = new ItemManoObra(0, orden.getId(), desc.getText().trim(), tarifa, horas);
            ordenDAO.agregarItem(item);
            bitacoraDAO.registrar("AGREGAR", "Mano de obra '" + desc.getText().trim() + "' a orden #" + orden.getId());
            mostrarItemsDeOrdenSeleccionada();
        });

        return p;
    }

    // ──────────────────────────────────── FORM NUEVA ORDEN ───────────────────
    private JPanel construirFormularioNuevaOrden() {
        // Solo SUPERADMIN, GERENTE y EMPLEADO pueden crear nuevas órdenes
        com.taller.modelo.Usuario actual = com.taller.util.Sesion.getUsuarioActual();
        boolean puedeCrear = actual != null &&
            (actual.getRol() == RolUsuario.SUPERADMIN ||
             actual.getRol() == RolUsuario.GERENTE    ||
             actual.getRol() == RolUsuario.EMPLEADO);

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 10));
        panel.setBackground(new Color(245, 247, 252));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(210, 215, 225)),
            new EmptyBorder(4, 8, 4, 8)));

        if (!puedeCrear) {
            JLabel info = new JLabel("  Solo puedes ver y gestionar las órdenes asignadas a ti.");
            info.setFont(new Font("SansSerif", Font.ITALIC, 12));
            info.setForeground(Color.GRAY);
            panel.add(info);
            return panel;
        }

        JLabel hdr = new JLabel("Nueva Orden:");
        hdr.setFont(new Font("SansSerif", Font.BOLD, 13));
        hdr.setForeground(Estilos.AZUL_OSCURO);
        panel.add(hdr);

        List<Vehiculo> vehiculos = vehiculoDAO.listarTodos();
        List<Mecanico> mecanicos = mecanicoDAO.listarTodos();
        comboVehiculo = new JComboBox<>(vehiculos.toArray(new Vehiculo[0]));
        comboMecanico = new JComboBox<>(mecanicos.toArray(new Mecanico[0]));
        JTextField txtProblema = new JTextField(24);
        txtProblema.setFont(new Font("SansSerif", Font.PLAIN, 13));

        comboVehiculo.setFont(new Font("SansSerif", Font.PLAIN, 12));
        comboMecanico.setFont(new Font("SansSerif", Font.PLAIN, 12));

        panel.add(new JLabel("Vehículo:"));
        panel.add(comboVehiculo);
        panel.add(new JLabel("Mecánico:"));
        panel.add(comboMecanico);
        panel.add(new JLabel("Problema:"));
        panel.add(txtProblema);

        BotonEstilizado btnCrear = new BotonEstilizado("Crear Orden", Estilos.NARANJA);
        btnCrear.addActionListener(e -> {
            Vehiculo v = (Vehiculo) comboVehiculo.getSelectedItem();
            Mecanico m = (Mecanico) comboMecanico.getSelectedItem();
            if (v == null) { JOptionPane.showMessageDialog(this, "Registra un vehículo primero"); return; }
            String problema = txtProblema.getText().trim();
            if (problema.isEmpty()) { JOptionPane.showMessageDialog(this, "Ingresa la descripción del problema"); return; }
            OrdenReparacion orden = new OrdenReparacion(0, v.getId(), m != null ? m.getId() : null,
                LocalDateTime.now(), problema, EstatusVehiculo.EN_REVISION);
            ordenDAO.crear(orden);
            bitacoraDAO.registrar("CREAR", "Nueva orden para vehículo " + v.getPlacas() + " — " + problema);
            txtProblema.setText("");
            refrescar();
            JOptionPane.showMessageDialog(this, "✓ Orden de reparación creada exitosamente", "Orden creada", JOptionPane.INFORMATION_MESSAGE);
        });
        panel.add(btnCrear);
        return panel;
    }

    // ──────────────────────────────────── LÓGICA ──────────────────────────────
    /**
     * REGLA DE NEGOCIO por Estatus:
     * EN_REVISION    -> Activa edición libre: agregar/eliminar ítems OK.
     * ESPERA_PIEZAS  -> Orden en pausa esperando refacciones: puede seguir editándose
     *                   hasta que lleguen las piezas y se retome.
     * LISTO          -> Orden cerrada/entregada/pagada: NO se pueden agregar
     *                   ni eliminar ítems. Requiere reabrir (cambiar a EN_REVISION)
     *                   explícitamente si hay algún error de captura.
     */
    private void mostrarItemsDeOrdenSeleccionada() {
        modeloItems.setRowCount(0);
        OrdenReparacion orden = listaOrdenes.getSelectedValue();
        if (orden == null) {
            lblTotal.setText("Total: $0.00");
            lblOrdenInfo.setText("Selecciona una orden de la lista");
            actualizarEstadoPanelEdicion(false, null);
            return;
        }
        String vehiculoInfo = "Veh. " + orden.getVehiculoId();
        try {
            Vehiculo v = vehiculoDAO.listarTodos().stream()
                .filter(x -> x.getId() == orden.getVehiculoId()).findFirst().orElse(null);
            if (v != null) vehiculoInfo = v.getPlacas() + " — " + v.getMarca() + " " + v.getModelo();
        } catch (Exception ignored) {}

        String mecanicoInfo = "Sin asignar";
        if (orden.getMecanicoId() != null) {
            try {
                com.taller.modelo.Mecanico mec = mecanicoDAO.listarTodos().stream()
                    .filter(x -> x.getId() == orden.getMecanicoId().intValue()).findFirst().orElse(null);
                if (mec != null) mecanicoInfo = mec.getNombre();
            } catch (Exception ignored) {}
        }

        boolean ordenCerrada = orden.getEstatus() == EstatusVehiculo.LISTO;

        // Actualizar header con badge visual de estado
        String badge = ordenCerrada ? "  🔒 CERRADA/PAGADA" : "";
        if (!orden.isActivo()) {
            lblOrdenInfo.setBackground(new Color(245, 225, 225));
            lblOrdenInfo.setForeground(new Color(192, 57, 43));
            lblOrdenInfo.setText("  [ARCHIVADA] Orden " + orden.getId() + badge + "  |  " + vehiculoInfo +
                "  |  Mecánico: " + mecanicoInfo +
                "  |  Estatus: " + orden.getEstatus().getEtiqueta() +
                "  |  Fecha: " + orden.getFechaIngreso().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        } else {
            lblOrdenInfo.setBackground(ordenCerrada ? new Color(255, 243, 205) : new Color(248, 249, 252));
            lblOrdenInfo.setForeground(ordenCerrada ? new Color(133, 77, 14) : Color.GRAY);
            lblOrdenInfo.setText("  Orden " + orden.getId() + badge + "  |  " + vehiculoInfo +
                "  |  Mecánico: " + mecanicoInfo +
                "  |  Estatus: " + orden.getEstatus().getEtiqueta() +
                "  |  Fecha: " + orden.getFechaIngreso().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));
        }

        // Actualizar botón de archivar/restaurar
        if (btnElimOrden != null) {
            if (orden.isActivo()) {
                btnElimOrden.setText("Archivar Orden");
                btnElimOrden.setBackground(Estilos.ROJO);
            } else {
                btnElimOrden.setText("Restaurar Orden");
                btnElimOrden.setBackground(Estilos.VERDE);
            }
        }

        // Habilitar/deshabilitar formularios de edición según estatus (si está archivada, tampoco se edita)
        actualizarEstadoPanelEdicion(!ordenCerrada && orden.isActivo(), orden);

        List<ItemPresupuesto> items = ordenDAO.listarItems(orden.getId());
        double total = 0;
        for (ItemPresupuesto item : items) {
            double sub = item.calcularSubtotal();
            total += sub;
            modeloItems.addRow(new Object[]{item.getTipo(), item.getDescripcion(), String.format("$%.2f", sub)});
        }
        lblTotal.setText(String.format("Total estimado: $%,.2f", total));
    }

    /** Habilita o deshabilita el panel de formularios de edición de ítems. */
    private void actualizarEstadoPanelEdicion(boolean habilitado, OrdenReparacion orden) {
        if (formsPanel != null) {
            setEnabled(formsPanel, habilitado);
        }

        if (!habilitado && orden != null) {
            // Mostrar tooltip explicativo en el combo
            if (comboRef != null) {
                comboRef.setToolTipText("Orden LISTO: no se puede modificar. Cambia estatus a 'En Revisión' para editar.");
            }
        } else if (comboRef != null) {
            comboRef.setToolTipText(null);
        }
    }

    /** Habilita/deshabilita recursivamente todos los componentes de un contenedor. */
    private void setEnabled(java.awt.Component comp, boolean enabled) {
        comp.setEnabled(enabled);
        if (comp instanceof java.awt.Container) {
            for (java.awt.Component child : ((java.awt.Container) comp).getComponents()) {
                setEnabled(child, enabled);
            }
        }
    }

    private void recargarComboRefacciones() {
        if (comboRef != null) {
            Object sel = comboRef.getSelectedItem();
            int selId = (sel instanceof Refaccion) ? ((Refaccion) sel).getId() : -1;
            List<Refaccion> lista = refaccionDAO.listarTodas();
            DefaultComboBoxModel<Refaccion> model = new DefaultComboBoxModel<>();
            Refaccion aSeleccionar = null;
            for (Refaccion r : lista) {
                model.addElement(r);
                if (r.getId() == selId) {
                    aSeleccionar = r;
                }
            }
            comboRef.setModel(model);
            if (aSeleccionar != null) {
                comboRef.setSelectedItem(aSeleccionar);
            }
            comboRef.revalidate();
            comboRef.repaint();
        }
    }

    @Override
    public void refrescar() {
        com.taller.modelo.Usuario actual = com.taller.util.Sesion.getUsuarioActual();

        // Recargar combos
        if (comboVehiculo != null) {
            comboVehiculo.removeAllItems();
            for (Vehiculo v : vehiculoDAO.listarTodos()) comboVehiculo.addItem(v);
        }
        if (comboMecanico != null) {
            comboMecanico.removeAllItems();
            for (Mecanico m : mecanicoDAO.listarTodos()) comboMecanico.addItem(m);
        }
        recargarComboRefacciones();

        OrdenReparacion selAnterior = listaOrdenes.getSelectedValue();
        modeloOrdenes.clear();

        // Si es SUPERADMIN o GERENTE, ver todas las órdenes incluyendo ocultas/archivadas.
        // Si es MECÁNICO, solo ver sus propias órdenes.
        // De lo contrario (empleado/cliente/otros), ver solo órdenes activas.
        List<OrdenReparacion> ordenes;
        if (actual != null && (actual.getRol() == RolUsuario.SUPERADMIN || actual.getRol() == RolUsuario.GERENTE)) {
            ordenes = ordenDAO.listarTodasIncluyendoOcultas();
        } else if (actual != null && actual.getRol() == RolUsuario.MECANICO && actual.getPersonaId() != null) {
            ordenes = ordenDAO.listarPorMecanico(actual.getPersonaId());
        } else {
            ordenes = ordenDAO.listarTodas();
        }

        for (OrdenReparacion o : ordenes) {
            modeloOrdenes.addElement(o);
        }
        if (selAnterior != null) {
            for (int i = 0; i < modeloOrdenes.size(); i++) {
                if (modeloOrdenes.get(i).getId() == selAnterior.getId()) {
                    listaOrdenes.setSelectedIndex(i);
                    break;
                }
            }
        }
        mostrarItemsDeOrdenSeleccionada();
    }
}
