package com.taller.ui;

import com.taller.dao.ClienteDAO;
import com.taller.dao.VehiculoDAO;
import com.taller.modelo.Cliente;
import com.taller.modelo.EstatusVehiculo;
import com.taller.modelo.Vehiculo;
import com.taller.modelo.Usuario;
import com.taller.modelo.RolUsuario;
import com.taller.util.Sesion;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PanelVehiculos extends JPanel implements Refrescable {

    private final VehiculoDAO vehiculoDAO = new VehiculoDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();

    private final DefaultListModel<Vehiculo> modeloLista = new DefaultListModel<>();
    private final JList<Vehiculo> listaVehiculos = new JList<>(modeloLista);
    private BotonEstilizado btnArchivar;
    private JComboBox<EstatusVehiculo> comboEstatus;

    public PanelVehiculos() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Estilos.GRIS_CLARO);

        JLabel titulo = new JLabel("Recepcion de vehiculos - Inventario visual");
        titulo.setFont(Estilos.TITULO);
        add(titulo, BorderLayout.NORTH);

        listaVehiculos.setCellRenderer(new TarjetaVehiculoRenderer());
        listaVehiculos.setFixedCellHeight(70);
        listaVehiculos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        listaVehiculos.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int index = listaVehiculos.locationToIndex(e.getPoint());
                if (index == -1 || !listaVehiculos.getCellBounds(index, index).contains(e.getPoint()) || e.getClickCount() == 2) {
                    listaVehiculos.clearSelection();
                }
            }
        });

        // Listener de selección para actualizar el botón de archivar/restaurar
        listaVehiculos.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Vehiculo sel = listaVehiculos.getSelectedValue();
                if (sel != null && btnArchivar != null) {
                    if (sel.isActivo()) {
                        btnArchivar.setText("Archivar Vehículo");
                        btnArchivar.setBackground(Estilos.ROJO);
                    } else {
                        btnArchivar.setText("Restaurar Vehículo");
                        btnArchivar.setBackground(Estilos.VERDE);
                    }
                }
            }
        });

        add(new JScrollPane(listaVehiculos), BorderLayout.CENTER);

        JScrollPane scrollDerecho = new JScrollPane(construirPanelDerecho());
        scrollDerecho.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollDerecho.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollDerecho.setBorder(null);
        scrollDerecho.setPreferredSize(new Dimension(340, 100));
        add(scrollDerecho, BorderLayout.EAST);

        // Panel SOUTH: gestión del vehículo seleccionado — siempre visible
        add(construirBarraGestion(), BorderLayout.SOUTH);

        refrescar();
    }

    private JTextField txtFotoSeleccionada;
    private JComboBox<Cliente> comboCliente;

    private JPanel construirPanelDerecho() {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(320, 100));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel lbl = new JLabel("Recibir nuevo vehículo");
        lbl.setFont(new Font("SansSerif", Font.BOLD, 16));
        lbl.setForeground(Estilos.AZUL_OSCURO);
        panel.add(lbl);
        panel.add(Box.createVerticalStrut(14));

        JTextField txtPlacas = campo(panel, "Placas");
        JTextField txtMarca  = campo(panel, "Marca");
        JTextField txtModelo = campo(panel, "Modelo");
        JTextField txtAnio   = campo(panel, "Año");
        JTextField txtColor  = campo(panel, "Color");

        JLabel lblCliente = new JLabel("Cliente");
        lblCliente.setFont(Estilos.NORMAL);
        List<Cliente> clientes = clienteDAO.listarTodos();
        comboCliente = new JComboBox<>(clientes.toArray(new Cliente[0]));
        comboCliente.setMaximumSize(new Dimension(280, 32));
        comboCliente.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblCliente);
        panel.add(Box.createVerticalStrut(4));
        panel.add(comboCliente);
        panel.add(Box.createVerticalStrut(10));

        // Foto del vehículo
        JLabel lblFotoTitulo = new JLabel("Foto del vehículo (opcional)");
        lblFotoTitulo.setFont(Estilos.NORMAL);
        lblFotoTitulo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lblFotoTitulo);
        panel.add(Box.createVerticalStrut(4));

        JLabel lblPreviewFoto = new JLabel();
        lblPreviewFoto.setPreferredSize(new Dimension(280, 80));
        lblPreviewFoto.setMaximumSize(new Dimension(280, 80));
        lblPreviewFoto.setAlignmentX(Component.LEFT_ALIGNMENT);
        lblPreviewFoto.setBorder(BorderFactory.createLineBorder(new Color(200, 205, 215), 1));
        lblPreviewFoto.setHorizontalAlignment(SwingConstants.CENTER);
        lblPreviewFoto.setOpaque(true);
        lblPreviewFoto.setBackground(new Color(245, 247, 252));
        lblPreviewFoto.setText("Sin foto");
        lblPreviewFoto.setForeground(Color.GRAY);
        panel.add(lblPreviewFoto);
        panel.add(Box.createVerticalStrut(5));

        txtFotoSeleccionada = new JTextField("");
        txtFotoSeleccionada.setVisible(false);

        BotonEstilizado btnSelFoto = new BotonEstilizado("Seleccionar Foto", Estilos.AZUL_MEDIO);
        btnSelFoto.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnSelFoto.setMaximumSize(new Dimension(280, 40));
        btnSelFoto.addActionListener(e -> {
            JFileChooser fc = new JFileChooser();
            fc.setDialogTitle("Seleccionar imagen del vehículo");
            fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
                "Imágenes (jpg, png, jpeg, webp)", "jpg", "png", "jpeg", "webp"));
            if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                java.io.File f = fc.getSelectedFile();
                txtFotoSeleccionada.setText(f.getPath().replace("\\", "/"));
                try {
                    ImageIcon ic = new ImageIcon(f.getPath());
                    Image scaled = ic.getImage().getScaledInstance(276, 76, Image.SCALE_SMOOTH);
                    lblPreviewFoto.setIcon(new ImageIcon(scaled));
                    lblPreviewFoto.setText("");
                } catch (Exception ignored) {}
            }
        });
        panel.add(btnSelFoto);
        panel.add(Box.createVerticalStrut(14));

        BotonEstilizado btnRecibir = new BotonEstilizado("✔ Registrar vehículo", Estilos.NARANJA);
        btnRecibir.setAlignmentX(Component.LEFT_ALIGNMENT);
        btnRecibir.addActionListener(e -> {
            try {
                if (clientes.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Primero registra al menos un cliente en 'Clientes y mecánicos'.");
                    return;
                }
                String placas = txtPlacas.getText().trim().toUpperCase().replaceAll("\\s+", "");
                String marca  = txtMarca.getText().trim();
                String modelo = txtModelo.getText().trim();
                String anioStr = txtAnio.getText().trim();
                String color  = txtColor.getText().trim();

                if (placas.isEmpty() || marca.isEmpty() || modelo.isEmpty() || anioStr.isEmpty() || color.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Placas, Marca, Modelo, Año y Color son campos obligatorios.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                if (placas.length() < 3 || placas.length() > 10) {
                    JOptionPane.showMessageDialog(this, "Las placas deben tener entre 3 y 10 caracteres.", "Placas inválidas", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                Vehiculo vActivo = vehiculoDAO.obtenerVehiculoActivoEnTaller(placas);
                if (vActivo != null) {
                    JOptionPane.showMessageDialog(this,
                        "El vehículo con placas '" + placas + "' ya se encuentra actualmente en el taller\n" +
                        "bajo el estatus '" + vActivo.getEstatus().getEtiqueta() + "'.\n" +
                        "Debe marcarse como LISTO antes de poder registrar un nuevo ingreso para este vehículo.",
                        "Vehículo activo en taller", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int anio;
                try {
                    anio = Integer.parseInt(anioStr);
                    if (anio < 1900 || anio > 2027) {
                        JOptionPane.showMessageDialog(this, "Ingresa un año válido entre 1900 y 2027.", "Año fuera de rango", JOptionPane.WARNING_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "El año debe ser un número entero válido (ej. 2022).", "Año inválido", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Cliente clienteSel = (Cliente) comboCliente.getSelectedItem();
                int cId = clienteSel != null ? clienteSel.getId() : 0;

                String rutaFoto = txtFotoSeleccionada.getText().trim();
                Vehiculo v = new Vehiculo(0, placas, marca, modelo, anio, color, cId, EstatusVehiculo.EN_REVISION, rutaFoto.isEmpty() ? null : rutaFoto);
                vehiculoDAO.crear(v);

                JOptionPane.showMessageDialog(this, "✓ Vehículo registrado e ingresado al taller.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                txtPlacas.setText(""); txtMarca.setText(""); txtModelo.setText(""); txtAnio.setText(""); txtColor.setText("");
                txtFotoSeleccionada.setText("");
                lblPreviewFoto.setIcon(null);
                lblPreviewFoto.setText("Sin foto");
                refrescar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al registrar vehículo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        panel.add(btnRecibir);
        panel.add(Box.createVerticalStrut(16));

        return panel;
    }

    /** Barra inferior siempre visible con los controles de vehículo seleccionado */
    private JPanel construirBarraGestion() {
        JPanel barra = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        barra.setBackground(new Color(235, 238, 245));
        barra.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(200, 205, 215)));

        JLabel lbl = new JLabel("Vehículo seleccionado:");
        lbl.setFont(new Font("SansSerif", Font.BOLD, 13));
        lbl.setForeground(Estilos.AZUL_OSCURO);
        barra.add(lbl);

        comboEstatus = new JComboBox<>(EstatusVehiculo.values());
        comboEstatus.setPreferredSize(new Dimension(160, 32));
        barra.add(comboEstatus);

        BotonEstilizado btnCambiar = new BotonEstilizado("Actualizar estatus", Estilos.AZUL_MEDIO);
        btnCambiar.setPreferredSize(new Dimension(160, 32));
        btnCambiar.addActionListener(e -> {
            Vehiculo sel = listaVehiculos.getSelectedValue();
            if (sel == null) { JOptionPane.showMessageDialog(this, "Selecciona un vehículo de la lista"); return; }
            vehiculoDAO.actualizarEstatus(sel.getId(), (EstatusVehiculo) comboEstatus.getSelectedItem());
            new com.taller.dao.BitacoraDAO().registrar("CLICK", "Actualizó estatus de vehículo " + sel.getPlacas() + " a " + comboEstatus.getSelectedItem());
            refrescar();
        });
        barra.add(btnCambiar);

        BotonEstilizado btnInventario = new BotonEstilizado("Inventario Visual", Estilos.VERDE);
        btnInventario.setPreferredSize(new Dimension(150, 32));
        btnInventario.addActionListener(e -> {
            Vehiculo sel = listaVehiculos.getSelectedValue();
            if (sel == null) { JOptionPane.showMessageDialog(this, "Selecciona un vehículo de la lista"); return; }
            Window ancestor = SwingUtilities.getWindowAncestor(this);
            if (ancestor instanceof Frame) new InventarioVisualDialog((Frame) ancestor, sel, vehiculoDAO).setVisible(true);
            refrescar();
        });
        barra.add(btnInventario);

        // Botón Archivar: solo SUPERADMIN y GERENTE
        Usuario userAct = Sesion.getUsuarioActual();
        if (userAct != null && (userAct.getRol() == RolUsuario.SUPERADMIN || userAct.getRol() == RolUsuario.GERENTE)) {
            btnArchivar = new BotonEstilizado("Archivar Vehículo", Estilos.ROJO);
            btnArchivar.setPreferredSize(new Dimension(170, 32));
            btnArchivar.addActionListener(e -> {
                Vehiculo sel = listaVehiculos.getSelectedValue();
                if (sel == null) { JOptionPane.showMessageDialog(this, "Selecciona un vehículo de la lista"); return; }
                boolean esActivo = sel.isActivo();
                String msg = esActivo
                    ? "¿Archivar vehículo " + sel.getPlacas() + "?\n(Quedará atenuado para todos, solo admins pueden restaurarlo)"
                    : "¿Restaurar vehículo " + sel.getPlacas() + "?\n(Volverá a mostrarse con normalidad para todos)";
                int opt = JOptionPane.showConfirmDialog(this, msg, esActivo ? "Archivar" : "Restaurar", JOptionPane.YES_NO_OPTION);
                if (opt == JOptionPane.YES_OPTION) {
                    try {
                        vehiculoDAO.cambiarEstadoActivo(sel.getId(), !esActivo);
                        refrescar();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            barra.add(btnArchivar);
        }

        return barra;
    }

    private JTextField campo(JPanel panel, String etiqueta) {
        JLabel lbl = new JLabel(etiqueta);
        lbl.setFont(Estilos.NORMAL);
        JTextField campo = new JTextField();
        campo.setMaximumSize(new Dimension(280, 32));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lbl);
        panel.add(Box.createVerticalStrut(4));
        panel.add(campo);
        panel.add(Box.createVerticalStrut(10));
        return campo;
    }

    @Override
    public void refrescar() {
        if (comboCliente != null) {
            comboCliente.removeAllItems();
            for (Cliente c : clienteDAO.listarTodos()) comboCliente.addItem(c);
        }
        modeloLista.clear();
        // Siempre cargamos TODOS los vehículos (activos y archivados)
        // Los archivados se muestran atenuados para todos los roles
        for (Vehiculo v : vehiculoDAO.listarTodos(true)) {
            modeloLista.addElement(v);
        }
    }

    static class TarjetaVehiculoRenderer extends JPanel implements ListCellRenderer<Vehiculo> {
        private final JLabel icono = new JLabel();
        private final JLabel info = new JLabel();
        private final JLabel estatusLbl = new JLabel();

        TarjetaVehiculoRenderer() {
            setLayout(new BorderLayout(10, 0));
            setBorder(BorderFactory.createEmptyBorder(8, 10, 8, 10));
            icono.setFont(new Font("SansSerif", Font.BOLD, 12));
            icono.setPreferredSize(new Dimension(50, 50));
            icono.setHorizontalAlignment(SwingConstants.CENTER);
            estatusLbl.setOpaque(true);
            estatusLbl.setFont(Estilos.BOTON);
            estatusLbl.setForeground(Color.WHITE);
            estatusLbl.setHorizontalAlignment(SwingConstants.CENTER);
            estatusLbl.setPreferredSize(new Dimension(160, 30));
            add(icono, BorderLayout.WEST);
            add(info, BorderLayout.CENTER);
            add(estatusLbl, BorderLayout.EAST);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Vehiculo> list, Vehiculo v, int index,
                                                        boolean isSelected, boolean cellHasFocus) {
            icono.setText("AUTO");
            String propietarioInfo = "";
            try {
                Cliente c = new com.taller.dao.ClienteDAO().buscarPorId(v.getClienteId());
                if (c != null) {
                    propietarioInfo = " | Propietario: " + c.getNombre() + " (" + c.getTelefono() + ")";
                }
            } catch (Exception ignored) {}

            String placaTxt = v.isActivo() ? v.getPlacas() : "[Archivado] " + v.getPlacas();
            info.setText("<html><b>" + placaTxt + "</b> - " + v.getMarca() + " " + v.getModelo() +
                " (" + v.getAnio() + ") - " + v.getColor() + "<br><font color='gray'>" + propietarioInfo + "</font></html>");
            info.setFont(Estilos.NORMAL);
            estatusLbl.setText(v.getEstatus().getEtiqueta());
            estatusLbl.setBackground(Estilos.colorEstatus(v.getEstatus().getEtiqueta()));
            
            if (v.isActivo()) {
                setBackground(isSelected ? Estilos.GRIS_CLARO : Color.WHITE);
                info.setForeground(Color.BLACK);
            } else {
                setBackground(isSelected ? new Color(225, 230, 240) : new Color(245, 245, 245));
                info.setForeground(Color.GRAY);
            }
            setOpaque(true);
            return this;
        }
    }
}
