package com.taller.ui;

import com.taller.dao.ClienteDAO;
import com.taller.dao.MecanicoDAO;
import com.taller.dao.UsuarioDAO;
import com.taller.modelo.Cliente;
import com.taller.modelo.Mecanico;
import com.taller.modelo.Persona;
import com.taller.modelo.RolUsuario;
import com.taller.modelo.Usuario;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelPersonas extends JPanel implements Refrescable {

    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final MecanicoDAO mecanicoDAO = new MecanicoDAO();
    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    private final DefaultListModel<Persona> modeloLista = new DefaultListModel<>();
    private final JList<Persona> lista = new JList<>(modeloLista);

    private JTextField cNombre, cTel, cEmail, cDir;
    private JComboBox<UsuarioComboItem> cmbClienteUsuario;

    private JTextField mNombre, mTel, mEmail, mEsp;
    private JComboBox<UsuarioComboItem> cmbMecanicoUsuario;

    // Helper para los ítems del combo de usuario
    private static class UsuarioComboItem {
        final int id;
        final String username;

        UsuarioComboItem(int id, String username) {
            this.id = id;
            this.username = username;
        }

        @Override
        public String toString() {
            return username;
        }
    }

    public PanelPersonas() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Estilos.GRIS_CLARO);

        JLabel titulo = new JLabel("Clientes y mecánicos");
        titulo.setFont(Estilos.TITULO);
        lista.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(titulo, BorderLayout.NORTH);

        lista.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            String texto = value.resumen();
            if (value instanceof Cliente) {
                try {
                    List<com.taller.modelo.Vehiculo> vehs = new com.taller.dao.VehiculoDAO().listarPorCliente(value.getId());
                    if (!vehs.isEmpty()) {
                        StringBuilder sb = new StringBuilder(" | Autos: ");
                        for (int i = 0; i < vehs.size(); i++) {
                            if (i > 0) sb.append(", ");
                            sb.append(vehs.get(i).getPlacas()).append(" (").append(vehs.get(i).getMarca()).append(")");
                        }
                        texto += sb.toString();
                    } else {
                        texto += " | Sin vehículos registrados";
                    }
                } catch (Exception ignored) {}
            }
            
            // Mostrar cuenta vinculada en el resumen si tiene
            try {
                Usuario u = usuarioDAO.buscarPorPersona(
                    value instanceof Cliente ? RolUsuario.CLIENTE : RolUsuario.MECANICO,
                    value.getId()
                );
                if (u != null) {
                    texto += " [Acceso: " + u.getUsername() + "]";
                }
            } catch (Exception ignored) {}

            JLabel l = new JLabel("  " + texto);
            l.setOpaque(true);
            l.setFont(Estilos.NORMAL);
            l.setBackground(isSelected ? Estilos.AZUL_MEDIO : (index % 2 == 0 ? Color.WHITE : Estilos.GRIS_CLARO));
            l.setForeground(isSelected ? Color.WHITE : Color.BLACK);
            l.setBorder(BorderFactory.createEmptyBorder(6, 6, 6, 6));
            return l;
        });

        lista.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Persona p = lista.getSelectedValue();
                if (p instanceof Cliente c) {
                    cNombre.setText(c.getNombre());
                    cTel.setText(c.getTelefono());
                    cEmail.setText(c.getEmail());
                    cDir.setText(c.getDireccion());
                    actualizarCombosUsuario(c, null);
                } else if (p instanceof Mecanico m) {
                    mNombre.setText(m.getNombre());
                    mTel.setText(m.getTelefono());
                    mEmail.setText(m.getEmail());
                    mEsp.setText(m.getEspecialidad());
                    actualizarCombosUsuario(null, m);
                } else {
                    // Si es null, limpiar campos
                    limpiarCamposCliente();
                    limpiarCamposMecanico();
                }
            }
        });

        // Permitir deselección y limpieza rápida al hacer clic en zona vacía o doble clic en el seleccionado
        lista.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent e) {
                int index = lista.locationToIndex(e.getPoint());
                if (index == -1 || !lista.getCellBounds(index, index).contains(e.getPoint())) {
                    lista.clearSelection();
                } else {
                    if (lista.getSelectedIndex() == index && e.getClickCount() == 2) {
                        lista.clearSelection();
                    }
                }
            }
        });

        add(new JScrollPane(lista), BorderLayout.CENTER);
        add(construirFormularios(), BorderLayout.SOUTH);

        refrescar();
    }

    private JPanel construirFormularios() {
        JPanel contenedor = new JPanel(new GridLayout(1, 2, 15, 0));
        contenedor.setBackground(Estilos.GRIS_CLARO);

        // ── FORM CLIENTE ─────────────────────────────────────────────────────
        JPanel formCliente = new JPanel();
        formCliente.setLayout(new BoxLayout(formCliente, BoxLayout.Y_AXIS));
        formCliente.setBorder(BorderFactory.createTitledBorder("Gestión de Cliente"));
        formCliente.setBackground(Color.WHITE);
        cNombre = campo(formCliente, "Nombre");
        cTel = campo(formCliente, "Teléfono");
        cEmail = campo(formCliente, "Email de contacto (Personal)");
        cDir = campo(formCliente, "Dirección");
        
        // Agregar ComboBox de cuenta de usuario
        JLabel lblUser = new JLabel("Cuenta de Acceso (Usuario)");
        lblUser.setAlignmentX(Component.LEFT_ALIGNMENT);
        formCliente.add(lblUser);
        cmbClienteUsuario = new JComboBox<>();
        cmbClienteUsuario.setMaximumSize(new Dimension(300, 28));
        cmbClienteUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        formCliente.add(cmbClienteUsuario);
        formCliente.add(Box.createVerticalStrut(10));

        JPanel pnlBotonesCliente = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlBotonesCliente.setBackground(Color.WHITE);

        BotonEstilizado btnCliente = new BotonEstilizado("Agregar", Estilos.NARANJA);
        btnCliente.addActionListener(e -> {
            try {
                String nom = cNombre.getText().trim();
                String tel = cTel.getText().trim();
                String email = cEmail.getText().trim();
                String dir = cDir.getText().trim();

                if (nom.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El nombre del cliente es obligatorio.", "Campo vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!email.isEmpty() && !com.taller.util.SeguridadUtil.esEmailValido(email)) {
                    JOptionPane.showMessageDialog(this, "Email de contacto inválido.", "Formato Inválido", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!email.isEmpty() && email.trim().toLowerCase().endsWith("@tallerhotwheels.com")) {
                    JOptionPane.showMessageDialog(this, "No se pueden registrar clientes con correos corporativos (@tallerhotwheels.com).", "Correo no permitido", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!tel.isEmpty() && !tel.matches("^[0-9+\\-\\s()]{7,20}$")) {
                    JOptionPane.showMessageDialog(this, "Teléfono inválido.", "Teléfono Inválido", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!email.isEmpty() && clienteDAO.existeEmail(email, 0)) {
                    JOptionPane.showMessageDialog(this, "Ya existe un cliente registrado con ese correo electrónico.", "Email Duplicado", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Validar teléfono duplicado
                if (!tel.isEmpty() && clienteDAO.existeTelefono(tel, 0)) {
                    JOptionPane.showMessageDialog(this, "Ya existe un cliente registrado con el teléfono '" + tel + "'.", "Teléfono Duplicado", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                int nuevoId = clienteDAO.crear(new Cliente(0, nom, tel, email, dir));
                
                // Vincular cuenta seleccionada obligatoria
                UsuarioComboItem item = (UsuarioComboItem) cmbClienteUsuario.getSelectedItem();
                if (item == null || item.id <= 0) {
                    JOptionPane.showMessageDialog(this, "Debe seleccionar una Cuenta de Acceso obligatoriamente.\nSi no hay cuentas disponibles en la lista, primero cree una cuenta de rol CLIENTE en 'Gestión de usuarios'.", "Cuenta de Acceso Requerida", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                usuarioDAO.vincularPersona(item.id, nuevoId);

                JOptionPane.showMessageDialog(this, "✓ Cliente agregado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCamposCliente();
                refrescar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        pnlBotonesCliente.add(btnCliente);

        BotonEstilizado btnActCliente = new BotonEstilizado("Actualizar", Estilos.AZUL_MEDIO);
        btnActCliente.addActionListener(e -> {
            Persona p = lista.getSelectedValue();
            if (!(p instanceof Cliente c)) {
                JOptionPane.showMessageDialog(this, "Selecciona un cliente de la lista", "Sin Selección", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String nom = cNombre.getText().trim();
                String tel = cTel.getText().trim();
                String email = cEmail.getText().trim();
                String dir = cDir.getText().trim();

                if (nom.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El nombre del cliente es obligatorio.", "Campo vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!email.isEmpty() && !com.taller.util.SeguridadUtil.esEmailValido(email)) {
                    JOptionPane.showMessageDialog(this, "Email de contacto inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!email.isEmpty() && email.trim().toLowerCase().endsWith("@tallerhotwheels.com")) {
                    JOptionPane.showMessageDialog(this, "No se pueden registrar clientes con correos corporativos (@tallerhotwheels.com).", "Correo no permitido", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!tel.isEmpty() && !tel.matches("^[0-9+\\-\\s()]{7,20}$")) {
                    JOptionPane.showMessageDialog(this, "Teléfono inválido.", "Teléfono Inválido", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!email.isEmpty() && clienteDAO.existeEmail(email, c.getId())) {
                    JOptionPane.showMessageDialog(this, "Ese correo ya está registrado en otro cliente.", "Email Duplicado", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Validar teléfono duplicado al actualizar
                if (!tel.isEmpty() && clienteDAO.existeTelefono(tel, c.getId())) {
                    JOptionPane.showMessageDialog(this, "Ya existe otro cliente registrado con el teléfono '" + tel + "'.", "Teléfono Duplicado", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                c.setNombre(nom);
                c.setTelefono(tel);
                c.setEmail(email);
                c.setDireccion(dir);
                clienteDAO.actualizar(c);

                // Modificar vinculación de cuenta (obligatoria)
                UsuarioComboItem item = (UsuarioComboItem) cmbClienteUsuario.getSelectedItem();
                if (item == null || item.id <= 0) {
                    JOptionPane.showMessageDialog(this, "Debe seleccionar una Cuenta de Acceso obligatoriamente.\nSi no hay cuentas disponibles en la lista, primero cree una cuenta de rol CLIENTE en 'Gestión de usuarios'.", "Cuenta de Acceso Requerida", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                usuarioDAO.desvincularPersona(RolUsuario.CLIENTE, c.getId());
                usuarioDAO.vincularPersona(item.id, c.getId());

                JOptionPane.showMessageDialog(this, "✓ Cliente actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCamposCliente();
                refrescar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        pnlBotonesCliente.add(btnActCliente);

        BotonEstilizado btnEliCliente = new BotonEstilizado("Ocultar", Estilos.ROJO);
        btnEliCliente.addActionListener(e -> {
            Persona p = lista.getSelectedValue();
            if (!(p instanceof Cliente c)) {
                JOptionPane.showMessageDialog(this, "Selecciona un cliente de la lista");
                return;
            }
            int opt = JOptionPane.showConfirmDialog(this, "¿Desactivar/Ocultar al cliente " + c.getNombre() + "?", "Confirmar borrado lógico", JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) {
                try {
                    usuarioDAO.desvincularPersona(RolUsuario.CLIENTE, c.getId());
                    clienteDAO.eliminar(c.getId());
                    limpiarCamposCliente();
                    refrescar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        });
        pnlBotonesCliente.add(btnEliCliente);
        formCliente.add(pnlBotonesCliente);

        // ── FORM MECANICO ────────────────────────────────────────────────────
        JPanel formMecanico = new JPanel();
        formMecanico.setLayout(new BoxLayout(formMecanico, BoxLayout.Y_AXIS));
        formMecanico.setBorder(BorderFactory.createTitledBorder("Gestión de Mecánico"));
        formMecanico.setBackground(Color.WHITE);
        mNombre = campo(formMecanico, "Nombre");
        mTel = campo(formMecanico, "Teléfono");
        mEmail = campo(formMecanico, "Email de contacto (Personal)");
        mEsp = campo(formMecanico, "Especialidad");

        // Agregar ComboBox de cuenta de usuario
        JLabel lblUserMec = new JLabel("Cuenta de Acceso (Usuario)");
        lblUserMec.setAlignmentX(Component.LEFT_ALIGNMENT);
        formMecanico.add(lblUserMec);
        cmbMecanicoUsuario = new JComboBox<>();
        cmbMecanicoUsuario.setMaximumSize(new Dimension(300, 28));
        cmbMecanicoUsuario.setAlignmentX(Component.LEFT_ALIGNMENT);
        formMecanico.add(cmbMecanicoUsuario);
        formMecanico.add(Box.createVerticalStrut(10));

        JPanel pnlBotonesMecanico = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlBotonesMecanico.setBackground(Color.WHITE);

        BotonEstilizado btnMecanico = new BotonEstilizado("Agregar", Estilos.NARANJA);
        btnMecanico.addActionListener(e -> {
            try {
                String nom = mNombre.getText().trim();
                String tel = mTel.getText().trim();
                String email = mEmail.getText().trim();
                String esp = mEsp.getText().trim();

                if (nom.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El nombre del mecánico es obligatorio.", "Campo vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!email.isEmpty() && !com.taller.util.SeguridadUtil.esEmailValido(email)) {
                    JOptionPane.showMessageDialog(this, "Email de contacto inválido.", "Email Inválido", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!email.isEmpty() && email.trim().toLowerCase().endsWith("@tallerhotwheels.com")) {
                    JOptionPane.showMessageDialog(this, "No se pueden registrar mecánicos con correos corporativos (@tallerhotwheels.com).", "Correo no permitido", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!tel.isEmpty() && !tel.matches("^[0-9+\\-\\s()]{7,20}$")) {
                    JOptionPane.showMessageDialog(this, "Teléfono inválido.", "Teléfono Inválido", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!email.isEmpty() && mecanicoDAO.existeEmail(email, 0)) {
                    JOptionPane.showMessageDialog(this, "Ya existe un mecánico registrado con ese correo electrónico.", "Email Duplicado", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Validar teléfono duplicado
                if (!tel.isEmpty() && mecanicoDAO.existeTelefono(tel, 0)) {
                    JOptionPane.showMessageDialog(this, "Ya existe un mecánico registrado con el teléfono '" + tel + "'.", "Teléfono Duplicado", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                int nuevoId = mecanicoDAO.crear(new Mecanico(0, nom, tel, email, esp, true));

                // Vincular cuenta seleccionada obligatoria
                UsuarioComboItem item = (UsuarioComboItem) cmbMecanicoUsuario.getSelectedItem();
                if (item == null || item.id <= 0) {
                    JOptionPane.showMessageDialog(this, "Debe seleccionar una Cuenta de Acceso obligatoriamente.\nSi no hay cuentas disponibles en la lista, primero cree una cuenta de rol MECANICO en 'Gestión de usuarios'.", "Cuenta de Acceso Requerida", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                usuarioDAO.vincularPersona(item.id, nuevoId);

                JOptionPane.showMessageDialog(this, "✓ Mecánico registrado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCamposMecanico();
                refrescar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        pnlBotonesMecanico.add(btnMecanico);

        BotonEstilizado btnActMec = new BotonEstilizado("Actualizar", Estilos.AZUL_MEDIO);
        btnActMec.addActionListener(e -> {
            Persona p = lista.getSelectedValue();
            if (!(p instanceof Mecanico m)) {
                JOptionPane.showMessageDialog(this, "Selecciona un mecánico de la lista", "Sin Selección", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String nom = mNombre.getText().trim();
                String tel = mTel.getText().trim();
                String email = mEmail.getText().trim();
                String esp = mEsp.getText().trim();

                if (nom.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "El nombre del mecánico es obligatorio.", "Campo vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!email.isEmpty() && !com.taller.util.SeguridadUtil.esEmailValido(email)) {
                    JOptionPane.showMessageDialog(this, "Email de contacto inválido.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!email.isEmpty() && email.trim().toLowerCase().endsWith("@tallerhotwheels.com")) {
                    JOptionPane.showMessageDialog(this, "No se pueden registrar mecánicos con correos corporativos (@tallerhotwheels.com).", "Correo no permitido", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!tel.isEmpty() && !tel.matches("^[0-9+\\-\\s()]{7,20}$")) {
                    JOptionPane.showMessageDialog(this, "Teléfono inválido.", "Teléfono Inválido", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (!email.isEmpty() && mecanicoDAO.existeEmail(email, m.getId())) {
                    JOptionPane.showMessageDialog(this, "Ese correo ya está registrado en otro mecánico.", "Email Duplicado", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                // Validar teléfono duplicado al actualizar
                if (!tel.isEmpty() && mecanicoDAO.existeTelefono(tel, m.getId())) {
                    JOptionPane.showMessageDialog(this, "Ya existe otro mecánico registrado con el teléfono '" + tel + "'.", "Teléfono Duplicado", JOptionPane.WARNING_MESSAGE);
                    return;
                }


                m.setNombre(nom);
                m.setTelefono(tel);
                m.setEmail(email);
                m.setEspecialidad(esp);
                mecanicoDAO.actualizar(m);

                // Modificar vinculación de cuenta (obligatoria)
                UsuarioComboItem item = (UsuarioComboItem) cmbMecanicoUsuario.getSelectedItem();
                if (item == null || item.id <= 0) {
                    JOptionPane.showMessageDialog(this, "Debe seleccionar una Cuenta de Acceso obligatoriamente.\nSi no hay cuentas disponibles en la lista, primero cree una cuenta de rol MECANICO en 'Gestión de usuarios'.", "Cuenta de Acceso Requerida", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                usuarioDAO.desvincularPersona(RolUsuario.MECANICO, m.getId());
                usuarioDAO.vincularPersona(item.id, m.getId());

                JOptionPane.showMessageDialog(this, "✓ Mecánico actualizado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarCamposMecanico();
                refrescar();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        pnlBotonesMecanico.add(btnActMec);

        BotonEstilizado btnEliMec = new BotonEstilizado("Ocultar", Estilos.ROJO);
        btnEliMec.addActionListener(e -> {
            Persona p = lista.getSelectedValue();
            if (!(p instanceof Mecanico m)) {
                JOptionPane.showMessageDialog(this, "Selecciona un mecánico de la lista");
                return;
            }

            String mensajeConfirm = "¿Desactivar/Ocultar al mecánico " + m.getNombre() + "?";
            if (mecanicoDAO.tieneOrdenesActivas(m.getId())) {
                mensajeConfirm = "⚠ El mecánico " + m.getNombre() + " tiene órdenes EN REVISIÓN o EN ESPERA.\n" +
                    "Si lo desactivas, quedará oculto en el sistema.\n\n¿Confirmas desactivarlo?";
            }
            int opt = JOptionPane.showConfirmDialog(this, mensajeConfirm, "Confirmar borrado lógico", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            if (opt == JOptionPane.YES_OPTION) {
                try {
                    usuarioDAO.desvincularPersona(RolUsuario.MECANICO, m.getId());
                    mecanicoDAO.eliminar(m.getId());
                    limpiarCamposMecanico();
                    refrescar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, ex.getMessage());
                }
            }
        });
        pnlBotonesMecanico.add(btnEliMec);
        formMecanico.add(pnlBotonesMecanico);

        contenedor.add(formCliente);
        contenedor.add(formMecanico);
        return contenedor;
    }

    private void limpiarCamposCliente() {
        cNombre.setText(""); cTel.setText(""); cEmail.setText(""); cDir.setText("");
        cmbClienteUsuario.setSelectedIndex(-1);
    }

    private void limpiarCamposMecanico() {
        mNombre.setText(""); mTel.setText(""); mEmail.setText(""); mEsp.setText("");
        cmbMecanicoUsuario.setSelectedIndex(-1);
    }


    private void actualizarCombosUsuario(Cliente cEdit, Mecanico mEdit) {
        // ---- 1. Llenar combo Cliente ----
        cmbClienteUsuario.removeAllItems();
        
        List<Usuario> libresClientes = usuarioDAO.listarSinPersona(RolUsuario.CLIENTE);
        for (Usuario u : libresClientes) {
            cmbClienteUsuario.addItem(new UsuarioComboItem(u.getId(), u.getUsername()));
        }

        if (cEdit != null) {
            Usuario vinculado = usuarioDAO.buscarPorPersona(RolUsuario.CLIENTE, cEdit.getId());
            if (vinculado != null) {
                UsuarioComboItem itemVinc = new UsuarioComboItem(vinculado.getId(), vinculado.getUsername());
                // Evitar duplicar
                boolean existe = false;
                for (int i = 0; i < cmbClienteUsuario.getItemCount(); i++) {
                    if (cmbClienteUsuario.getItemAt(i).id == vinculado.getId()) {
                        existe = true;
                        break;
                    }
                }
                if (!existe) cmbClienteUsuario.addItem(itemVinc);
                cmbClienteUsuario.setSelectedItem(itemVinc);
            }
        }

        // ---- 2. Llenar combo Mecánico ----
        cmbMecanicoUsuario.removeAllItems();

        List<Usuario> libresMecanicos = usuarioDAO.listarSinPersona(RolUsuario.MECANICO);
        for (Usuario u : libresMecanicos) {
            cmbMecanicoUsuario.addItem(new UsuarioComboItem(u.getId(), u.getUsername()));
        }

        if (mEdit != null) {
            Usuario vinculado = usuarioDAO.buscarPorPersona(RolUsuario.MECANICO, mEdit.getId());
            if (vinculado != null) {
                UsuarioComboItem itemVinc = new UsuarioComboItem(vinculado.getId(), vinculado.getUsername());
                boolean existe = false;
                for (int i = 0; i < cmbMecanicoUsuario.getItemCount(); i++) {
                    if (cmbMecanicoUsuario.getItemAt(i).id == vinculado.getId()) {
                        existe = true;
                        break;
                    }
                }
                if (!existe) cmbMecanicoUsuario.addItem(itemVinc);
                cmbMecanicoUsuario.setSelectedItem(itemVinc);
            }
        }
    }


    private JTextField campo(JPanel panel, String etiqueta) {
        JLabel lbl = new JLabel(etiqueta);
        JTextField campo = new JTextField();
        campo.setMaximumSize(new Dimension(300, 28));
        campo.setAlignmentX(Component.LEFT_ALIGNMENT);
        lbl.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(lbl);
        panel.add(campo);
        panel.add(Box.createVerticalStrut(6));
        return campo;
    }

    @Override
    public void refrescar() {
        modeloLista.clear();
        List<Persona> todos = new ArrayList<>();
        todos.addAll(clienteDAO.listarTodos());
        todos.addAll(mecanicoDAO.listarTodos());
        for (Persona p : todos) {
            modeloLista.addElement(p);
        }
        
        // Al refrescar todo el panel, recargar los combos vacíos por defecto
        actualizarCombosUsuario(null, null);
    }
}
