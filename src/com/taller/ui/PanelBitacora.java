package com.taller.ui;

import com.taller.dao.BitacoraDAO;
import com.taller.modelo.RegistroBitacora;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class PanelBitacora extends JPanel implements Refrescable {

    private final BitacoraDAO bitacoraDAO = new BitacoraDAO();
    private static final DateTimeFormatter FMT_VISUAL = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private final DefaultTableModel modelo = new DefaultTableModel(
        new Object[]{"ID", "Fecha y hora", "Usuario", "Accion", "Detalle"}, 0) {
        @Override public boolean isCellEditable(int row, int col) { return false; }
    };

    public PanelBitacora() {
        setLayout(new BorderLayout(15, 15));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setBackground(Estilos.GRIS_CLARO);

        JLabel titulo = new JLabel("Modulo de auditoria");
        titulo.setFont(Estilos.TITULO);
        add(titulo, BorderLayout.NORTH);

        JTable tabla = new JTable(modelo);
        tabla.setRowHeight(26);
        tabla.getColumnModel().getColumn(4).setPreferredWidth(350);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        BotonEstilizado btnActualizar = new BotonEstilizado("Actualizar auditoria", Estilos.NARANJA);
        btnActualizar.addActionListener(e -> refrescar());
        JPanel sur = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sur.setBackground(Estilos.GRIS_CLARO);
        sur.add(btnActualizar);
        add(sur, BorderLayout.SOUTH);

        refrescar();
    }

    @Override
    public void refrescar() {
        modelo.setRowCount(0);
        for (RegistroBitacora r : bitacoraDAO.listarTodos()) {
            modelo.addRow(new Object[]{
                r.getId(), r.getFechaHora().format(FMT_VISUAL), r.getUsername(), r.getAccion(), r.getDetalle()
            });
        }
    }
}
