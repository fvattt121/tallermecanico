package com.taller.ui;

import com.taller.dao.RefaccionDAO;
import com.taller.modelo.Refaccion;
import com.taller.modelo.Usuario;
import com.taller.modelo.RolUsuario;
import com.taller.util.Sesion;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelRefacciones extends JPanel implements Refrescable {

    private final RefaccionDAO refaccionDAO = new RefaccionDAO();
    private final DefaultTableModel modelo = new DefaultTableModel(new Object[]{"ID", "Nombre", "Precio", "Stock"}, 0) {
        @Override public boolean isCellEditable(int row, int col) { return false; }
    };

    private final JTable tabla = new JTable(modelo);
    private BotonEstilizado btnArchivar;
    private Refaccion refaccionSeleccionada = null;

    private JTextField nombre;
    private JTextField precio;
    private JTextField stock;

    public PanelRefacciones() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Estilos.GRIS_CLARO);

        JLabel titulo = new JLabel("Inventario de refacciones");
        titulo.setFont(Estilos.TITULO);
        add(titulo, BorderLayout.NORTH);

        tabla.setRowHeight(26);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        form.setBackground(Color.WHITE);
        form.setBorder(BorderFactory.createTitledBorder("Registrar / Modificar refacción"));
        nombre = new JTextField(15);
        precio = new JTextField(8);
        stock = new JTextField(6);
        
        form.add(new JLabel("Nombre:")); form.add(nombre);
        form.add(new JLabel("Precio:")); form.add(precio);
        form.add(new JLabel("Stock:")); form.add(stock);

        BotonEstilizado btn = new BotonEstilizado("Agregar", Estilos.NARANJA);
        btn.addActionListener(e -> {
            try {
                String nom = nombre.getText().trim();
                if (nom.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ingresa el nombre de la refacción.", "Nombre vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if (refaccionDAO.existeNombre(nom, 0)) {
                    JOptionPane.showMessageDialog(this,
                        "Ya existe una refacción con el nombre '" + nom + "'.\nUsa un nombre diferente o actualiza la refacción existente.",
                        "Nombre Duplicado", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                double pVal = Double.parseDouble(precio.getText().trim());
                int sVal = Integer.parseInt(stock.getText().trim());
                if (pVal <= 0 || sVal < 0) {
                    JOptionPane.showMessageDialog(this, "El precio debe ser mayor a 0 y el stock no puede ser negativo.", "Valores inválidos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                Refaccion r = new Refaccion(0, nom, pVal, sVal, null, true);
                refaccionDAO.crear(r);
                limpiarFormulario();
                refrescar();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El precio y el stock deben ser valores numéricos válidos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al crear refacción: " + ex.getMessage());
            }
        });

        BotonEstilizado btnActualizar = new BotonEstilizado("Actualizar", Estilos.AZUL_MEDIO);
        btnActualizar.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row == -1 || refaccionSeleccionada == null) {
                JOptionPane.showMessageDialog(this, "Selecciona una refacción de la tabla primero");
                return;
            }
            if (!refaccionSeleccionada.isActivo()) {
                JOptionPane.showMessageDialog(this, "No puedes actualizar una refacción archivada.\nDebes restaurarla primero para realizar cambios.", "Refacción Archivada", JOptionPane.WARNING_MESSAGE);
                return;
            }
            try {
                String nom = nombre.getText().trim();
                if (nom.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ingresa el nombre de la refacción.", "Nombre vacío", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int id = refaccionSeleccionada.getId();
                if (refaccionDAO.existeNombre(nom, id)) {
                    JOptionPane.showMessageDialog(this,
                        "Ya existe otra refacción con el nombre '" + nom + "'.\nUsa un nombre diferente.",
                        "Nombre Duplicado", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                double pVal = Double.parseDouble(precio.getText().trim());
                int sVal = Integer.parseInt(stock.getText().trim());
                if (pVal <= 0 || sVal < 0) {
                    JOptionPane.showMessageDialog(this, "El precio debe ser mayor a 0 y el stock no puede ser negativo.", "Valores inválidos", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                Refaccion r = new Refaccion(id, nom, pVal, sVal, refaccionSeleccionada.getRutaFoto(), refaccionSeleccionada.isActivo());
                refaccionDAO.actualizar(r);
                limpiarFormulario();
                refrescar();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El precio y el stock deben ser valores numéricos válidos.", "Error de formato", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
            }
        });

        btnArchivar = new BotonEstilizado("Archivar / Restaurar", Estilos.ROJO);
        btnArchivar.addActionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row == -1 || refaccionSeleccionada == null) {
                JOptionPane.showMessageDialog(this, "Selecciona una refacción de la tabla primero");
                return;
            }
            boolean esActivo = refaccionSeleccionada.isActivo();
            String msg;
            if (esActivo) {
                msg = "¿Archivar la refacción '" + refaccionSeleccionada.getNombre() + "'?\n"
                    + "(Quedará archivada. Solo admins y gerentes pueden restaurarla.)";
            } else {
                msg = "¿Restaurar la refacción '" + refaccionSeleccionada.getNombre() + "'?\n"
                    + "(Nota: si fue archivada automáticamente por cascada de una orden/cliente,\n"
                    + " asegúrate de haber restaurado antes al cliente correspondiente.)";
            }
            int opt = JOptionPane.showConfirmDialog(this, msg, esActivo ? "Archivar" : "Restaurar", JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) {
                try {
                    refaccionDAO.cambiarEstadoActivo(refaccionSeleccionada.getId(), !esActivo);
                    limpiarFormulario();
                    refrescar();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Error al cambiar estado: " + ex.getMessage());
                }
            }
        });

        Usuario activeUser = Sesion.getUsuarioActual();
        if (activeUser != null && activeUser.getRol() == RolUsuario.EMPLEADO) {
            btnArchivar.setVisible(false);
            btn.setVisible(false);
            btnActualizar.setVisible(false);
            form.setVisible(false);
        }

        JPanel pnlBotones = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        pnlBotones.setBackground(Color.WHITE);
        pnlBotones.add(btn);
        pnlBotones.add(btnActualizar);
        pnlBotones.add(btnArchivar);
        form.add(pnlBotones);

        tabla.getSelectionModel().addListSelectionListener(e -> {
            int row = tabla.getSelectedRow();
            if (row != -1 && !e.getValueIsAdjusting()) {
                int id = (int) modelo.getValueAt(row, 0);
                refaccionSeleccionada = refaccionDAO.buscarPorId(id);
                if (refaccionSeleccionada != null) {
                    nombre.setText(refaccionSeleccionada.getNombre());
                    precio.setText(String.valueOf(refaccionSeleccionada.getPrecioUnitario()));
                    stock.setText(String.valueOf(refaccionSeleccionada.getStock()));
                    
                    // Actualizar botón archivar/restaurar
                    if (refaccionSeleccionada.isActivo()) {
                        btnArchivar.setText("Archivar");
                        btnArchivar.setBackground(Estilos.ROJO);
                    } else {
                        btnArchivar.setText("Restaurar");
                        btnArchivar.setBackground(Estilos.VERDE);
                    }
                }
            }
        });

        // Doble click para deseleccionar
        tabla.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                if (e.getClickCount() == 2) {
                    limpiarFormulario();
                }
            }
        });

        if (activeUser != null && (activeUser.getRol() == RolUsuario.SUPERADMIN || activeUser.getRol() == RolUsuario.GERENTE)) {
            add(form, BorderLayout.SOUTH);
        }

        refrescar();
    }

    private void limpiarFormulario() {
        tabla.clearSelection();
        refaccionSeleccionada = null;
        nombre.setText("");
        precio.setText("");
        stock.setText("");
        btnArchivar.setText("Archivar / Restaurar");
        btnArchivar.setBackground(Estilos.ROJO);
    }

    @Override
    public void refrescar() {
        modelo.setRowCount(0);
        // Siempre mostramos TODAS las refacciones para todos los roles.
        // Las archivadas aparecen en gris/atenuado para indicar que están inactivas.
        List<Refaccion> lista = refaccionDAO.listarTodas(true);
        for (Refaccion r : lista) {
            String nombreMostrar = r.isActivo() ? r.getNombre() : "[Archivado] " + r.getNombre();
            modelo.addRow(new Object[]{
                r.getId(), nombreMostrar, String.format("$%.2f", r.getPrecioUnitario()), r.getStock()
            });
        }
    }
}
