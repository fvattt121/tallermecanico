package com.taller.ui;

import com.taller.dao.BitacoraDAO;
import com.taller.dao.ClienteDAO;
import com.taller.dao.MecanicoDAO;
import com.taller.dao.UsuarioDAO;
import com.taller.modelo.RolUsuario;
import com.taller.modelo.Usuario;
import com.taller.util.Sesion;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

/**
 * Panel de gestión de cuentas de usuario del sistema.
 * Accesible para SUPERADMIN, GERENTE y EMPLEADO, con permisos distintos:
 *
 *  SUPERADMIN : puede crear cualquier rol (SUPERADMIN, GERENTE, MECANICO, EMPLEADO, CLIENTE).
 *               Puede eliminar cualquier cuenta.
 *  GERENTE    : puede crear MECANICO, EMPLEADO y CLIENTE. NO puede crear/eliminar SUPERADMIN o GERENTE.
 *  EMPLEADO   : solo puede crear cuentas de tipo CLIENTE.
 *               No puede eliminar cuentas.
 */
public class PanelUsuarios extends JPanel implements Refrescable {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();
    private final ClienteDAO clienteDAO = new ClienteDAO();
    private final MecanicoDAO mecanicoDAO = new MecanicoDAO();
    private final BitacoraDAO bitacoraDAO = new BitacoraDAO();

    // Columnas visibles: ID, Usuario, Rol
    // Columnas ocultas: activo(col3), personaId(col4), rolEnum(col5)
    private final DefaultTableModel modeloTabla = new DefaultTableModel(
        new Object[]{"ID", "Usuario", "Rol", "_activo", "_personaId", "_rol"}, 0) {
        @Override public boolean isCellEditable(int r, int c) { return false; }
    };
    private final JTable tabla = new JTable(modeloTabla);

    // Form fields
    private JTextField txtUsername;
    private JPasswordField txtClave;
    private JComboBox<RolUsuario> cmbRol;

    public PanelUsuarios() {
        setLayout(new BorderLayout(15, 15));
        setBackground(Estilos.GRIS_CLARO);

        // ---- Título ----
        JLabel titulo = new JLabel("Gestión de Usuarios del Sistema");
        titulo.setFont(Estilos.TITULO);
        add(titulo, BorderLayout.NORTH);

        // ---- Tabla ----
        tabla.setRowHeight(28);
        tabla.setFont(Estilos.NORMAL);
        tabla.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        tabla.getTableHeader().setBackground(Estilos.AZUL_OSCURO);
        tabla.getTableHeader().setForeground(Color.WHITE);
        tabla.setSelectionBackground(Estilos.AZUL_MEDIO);
        tabla.setSelectionForeground(Color.WHITE);
        // Renderer para destacar usuarios archivados en rojo
        tabla.setDefaultRenderer(Object.class, new javax.swing.table.DefaultTableCellRenderer() {
            @Override
            public java.awt.Component getTableCellRendererComponent(
                    JTable t, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                super.getTableCellRendererComponent(t, value, isSelected, hasFocus, row, col);
                boolean archivado = false;
                if (t.getColumnCount() > 3) {
                    Object actObj = t.getValueAt(row, 3);
                    archivado = actObj instanceof Boolean && !(Boolean) actObj;
                }
                if (!isSelected) {
                    if (archivado) {
                        setBackground(new Color(255, 220, 220));
                        setForeground(new Color(150, 30, 30));
                    } else {
                        setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 248, 255));
                        setForeground(Color.BLACK);
                    }
                }
                return this;
            }
        });
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(new Color(200, 210, 225)));
        add(scroll, BorderLayout.CENTER);

        // ---- Formulario ----
        add(construirFormulario(), BorderLayout.SOUTH);

        refrescar();
    }

    private JPanel construirFormulario() {
        Usuario actual = Sesion.getUsuarioActual();
        RolUsuario rolActual = actual != null ? actual.getRol() : RolUsuario.EMPLEADO;
        boolean esSuperAdmin = rolActual == RolUsuario.SUPERADMIN;
        boolean esGerente    = rolActual == RolUsuario.GERENTE;

        JPanel contenedor = new JPanel(new BorderLayout(10, 0));
        contenedor.setBackground(Color.WHITE);
        contenedor.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 210, 225)),
            new EmptyBorder(14, 18, 14, 18)
        ));

        // Título del formulario
        JLabel lblTitulo = new JLabel("Crear nueva cuenta de acceso");
        lblTitulo.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblTitulo.setForeground(Estilos.AZUL_OSCURO);
        contenedor.add(lblTitulo, BorderLayout.NORTH);

        // Campos
        JPanel campos = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 8));
        campos.setBackground(Color.WHITE);

        campos.add(new JLabel("Usuario:"));
        txtUsername = new JTextField(20);
        txtUsername.setFont(Estilos.NORMAL);
        campos.add(txtUsername);

        campos.add(new JLabel("Contraseña:"));
        txtClave = new JPasswordField(14);
        txtClave.setFont(Estilos.NORMAL);
        campos.add(txtClave);

        campos.add(new JLabel("Rol:"));

        // Roles disponibles según quien esté logueado
        RolUsuario[] opcionesRol;
        if (esSuperAdmin) {
            opcionesRol = new RolUsuario[]{
                RolUsuario.CLIENTE, RolUsuario.EMPLEADO,
                RolUsuario.MECANICO, RolUsuario.GERENTE, RolUsuario.SUPERADMIN
            };
        } else if (esGerente) {
            opcionesRol = new RolUsuario[]{
                RolUsuario.CLIENTE, RolUsuario.EMPLEADO, RolUsuario.MECANICO
            };
        } else {
            opcionesRol = new RolUsuario[]{RolUsuario.CLIENTE};
        }

        cmbRol = new JComboBox<>(opcionesRol);
        cmbRol.setFont(Estilos.NORMAL);
        campos.add(cmbRol);

        contenedor.add(campos, BorderLayout.CENTER);

        // Botones
        JPanel botones = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        botones.setBackground(Color.WHITE);

        BotonEstilizado btnCrear = new BotonEstilizado("Crear cuenta", Estilos.VERDE);
        btnCrear.addActionListener(e -> crearCuenta());
        botones.add(btnCrear);

        if (esSuperAdmin) {
            BotonEstilizado btnArchivarUsr = new BotonEstilizado("Archivar/Restaurar", Estilos.ROJO);
            btnArchivarUsr.addActionListener(e -> archivarCuenta());
            botones.add(btnArchivarUsr);
        }

        contenedor.add(botones, BorderLayout.SOUTH);

        return contenedor;
    }

    private void crearCuenta() {
        try {
            // Normalizar dominio a minúsculas (GMAIL.COM → gmail.com, parte local se respeta)
            String username = com.taller.util.SeguridadUtil.normalizeEmail(txtUsername.getText().trim());
            String clave = new String(txtClave.getPassword());
            RolUsuario rol = (RolUsuario) cmbRol.getSelectedItem();
            Usuario actual = Sesion.getUsuarioActual();

            if (username.isEmpty() || clave.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Completa el usuario y la contraseña.", "Datos incompletos", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (!com.taller.util.SeguridadUtil.esEmailValido(username)) {
                JOptionPane.showMessageDialog(this,
                    "El usuario debe ser un correo electrónico válido\n(ejemplo: nombre@empresa.com).",
                    "Formato inválido", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (!com.taller.util.SeguridadUtil.esPasswordFuerte(clave)) {
                JOptionPane.showMessageDialog(this,
                    "La contraseña debe tener al menos 8 caracteres,\nuna mayúscula, un número y un carácter especial.",
                    "Contraseña débil", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (usuarioDAO.existeUsername(username)) {
                JOptionPane.showMessageDialog(this, "Ese usuario ya está registrado en el sistema.", "Usuario duplicado", JOptionPane.WARNING_MESSAGE);
                return;
            }

            // Validar que roles staff obligatoriamente tengan dominio @tallerhotwheels.com
            if (rol == RolUsuario.SUPERADMIN || rol == RolUsuario.GERENTE || rol == RolUsuario.EMPLEADO) {
                if (!username.toLowerCase().endsWith("@tallerhotwheels.com")) {
                    JOptionPane.showMessageDialog(this,
                        "Las cuentas de personal del taller (Administrador, Gerente, Empleado) deben utilizar obligatoriamente un correo corporativo que termine en @tallerhotwheels.com.",
                        "Correo corporativo requerido", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Verificar que el GERENTE no esté intentando crear SUPERADMIN/GERENTE
            if (actual.getRol() == RolUsuario.GERENTE &&
                (rol == RolUsuario.SUPERADMIN || rol == RolUsuario.GERENTE)) {
                JOptionPane.showMessageDialog(this,
                    "El Gerente no puede crear cuentas con ese nivel de acceso.",
                    "Acceso denegado", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Verificar que el EMPLEADO solo cree CLIENTE
            if (actual.getRol() == RolUsuario.EMPLEADO && rol != RolUsuario.CLIENTE) {
                JOptionPane.showMessageDialog(this,
                    "El Empleado solo puede crear cuentas de tipo Cliente.",
                    "Acceso denegado", JOptionPane.ERROR_MESSAGE);
                return;
            }

            usuarioDAO.registrar(username, clave, rol, null);
            JOptionPane.showMessageDialog(this,
                "Cuenta creada: " + username + " (" + MainDashboard.etiquetaRol(rol) + ")",
                "Éxito", JOptionPane.INFORMATION_MESSAGE);
            txtUsername.setText("");
            txtClave.setText("");
            refrescar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al crear cuenta: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void archivarCuenta() {
        Usuario actual = Sesion.getUsuarioActual();
        if (actual.getRol() != RolUsuario.SUPERADMIN) {
            JOptionPane.showMessageDialog(this,
                "Solo el Super Administrador puede archivar cuentas de usuario.",
                "Acceso denegado", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int row = tabla.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona un usuario de la tabla para archivar/restaurar.", "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int id          = (int) modeloTabla.getValueAt(row, 0);
        String usr      = (String) modeloTabla.getValueAt(row, 1);
        boolean estaActivo = modeloTabla.getColumnCount() > 3
            ? (Boolean) modeloTabla.getValueAt(row, 3)
            : true;
        Object personaIdObj = modeloTabla.getColumnCount() > 4 ? modeloTabla.getValueAt(row, 4) : null;
        Object rolObj       = modeloTabla.getColumnCount() > 5 ? modeloTabla.getValueAt(row, 5) : null;

        // Nadie puede archivarse a sí mismo
        if (id == actual.getId()) {
            JOptionPane.showMessageDialog(this, "No puedes archivarte a ti mismo.", "Operación no permitida", JOptionPane.ERROR_MESSAGE);
            return;
        }

        RolUsuario rolUsuario = (rolObj instanceof RolUsuario) ? (RolUsuario) rolObj : null;
        Integer personaId     = (personaIdObj instanceof Integer) ? (Integer) personaIdObj : null;

        // Construir mensaje de confirmación que explique el impacto en cascada
        String accion = estaActivo ? "Archivar" : "Restaurar";
        String impactoCascada = "";
        if (personaId != null && rolUsuario != null) {
            if (rolUsuario == RolUsuario.CLIENTE) {
                impactoCascada = estaActivo
                    ? "\n\n⚠ Cascada: también se archivarán el cliente vinculado, sus vehículos y órdenes."
                    : "\n\n⚠ Cascada: también se restaurará el cliente vinculado, sus vehículos y órdenes.";
            } else if (rolUsuario == RolUsuario.MECANICO) {
                impactoCascada = estaActivo
                    ? "\n\n⚠ Cascada: también se archivará el mecánico vinculado."
                    : "\n\n⚠ Cascada: también se restaurará el mecánico vinculado.";
            }
        }

        String msg = estaActivo
            ? "¿Archivar la cuenta '" + usr + "'? El usuario no podrá iniciar sesión." + impactoCascada
            : "¿Restaurar la cuenta '" + usr + "'? Volverá a poder iniciar sesión." + impactoCascada;

        int opt = JOptionPane.showConfirmDialog(this, msg, accion, JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (opt != JOptionPane.YES_OPTION) return;

        // 1. Cambiar estado del usuario
        usuarioDAO.cambiarEstadoActivo(id, !estaActivo);

        // 2. Cascada a persona vinculada (CLIENTE o MECANICO)
        if (personaId != null && rolUsuario != null) {
            try {
                if (rolUsuario == RolUsuario.CLIENTE) {
                    // Archivar/restaurar cliente en cascada (incluye vehículos y órdenes)
                    clienteDAO.cambiarEstadoActivo(personaId, !estaActivo);
                } else if (rolUsuario == RolUsuario.MECANICO) {
                    mecanicoDAO.cambiarEstadoActivo(personaId, !estaActivo);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Cuenta " + (estaActivo ? "archivada" : "restaurada") + ", pero hubo un error en la cascada: " + ex.getMessage(),
                    "Advertencia", JOptionPane.WARNING_MESSAGE);
            }
        }

        JOptionPane.showMessageDialog(this,
            estaActivo ? "✓ Cuenta archivada correctamente." : "✓ Cuenta restaurada correctamente.",
            "Éxito", JOptionPane.INFORMATION_MESSAGE);
        refrescar();
    }

    private void eliminarCuenta() {
        // Mantener por compatibilidad, redirige a archivarCuenta
        archivarCuenta();
    }

    @Override
    public void refrescar() {
        modeloTabla.setRowCount(0);
        Usuario sesion = Sesion.getUsuarioActual();
        boolean esAdmin = sesion != null && sesion.getRol() == RolUsuario.SUPERADMIN;
        List<Usuario> lista = usuarioDAO.listarTodos(esAdmin);
        for (Usuario u : lista) {
            String nombreMostrar = u.isActivo() ? u.getUsername() : "[Archivado] " + u.getUsername();
            modeloTabla.addRow(new Object[]{
                u.getId(),
                nombreMostrar,
                MainDashboard.etiquetaRol(u.getRol()),
                u.isActivo(),          // col 3: activo (oculta, para renderer)
                u.getPersonaId(),      // col 4: personaId (oculta, para cascada)
                u.getRol()             // col 5: RolUsuario enum (oculta, para cascada)
            });
        }
        // Ocultar columnas de metadatos (col 3, 4, 5)
        for (int col = 3; col <= 5 && tabla.getColumnCount() > col; col++) {
            tabla.getColumnModel().getColumn(col).setMinWidth(0);
            tabla.getColumnModel().getColumn(col).setMaxWidth(0);
            tabla.getColumnModel().getColumn(col).setWidth(0);
        }
    }
}
