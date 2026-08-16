package com.taller.ui;

import com.taller.modelo.Vehiculo;
import com.taller.dao.VehiculoDAO;
import com.taller.dao.BitacoraDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class InventarioVisualDialog extends JDialog {

    private final Vehiculo vehiculo;
    private final VehiculoDAO vehiculoDAO;
    private final BitacoraDAO bitacoraDAO = new BitacoraDAO();

    // Checkboxes
    private JCheckBox chkLlantas;
    private JCheckBox chkParabrisas;
    private JCheckBox chkCarroceria;
    private JCheckBox chkFaros;

    // Fuel level JComboBox
    private JComboBox<String> cmbGasolina;

    // Interactive Car Panel State (Zone -> isDamaged)
    private final Map<String, Boolean> zonasDano = new HashMap<>();
    private JTextField txtNotas;
    private String rutaImagenAuto;

    public InventarioVisualDialog(Frame parent, Vehiculo vehiculo, VehiculoDAO vehiculoDAO) {
        super(parent, "Inventario Visual - Vehículo: " + vehiculo.getPlacas(), true);
        this.vehiculo = vehiculo;
        this.vehiculoDAO = vehiculoDAO;
        
        setSize(700, 580);
        setLocationRelativeTo(parent);
        setResizable(false);

        // Initialize zones
        zonasDano.put("Frente", false);
        zonasDano.put("Atrás", false);
        zonasDano.put("Izquierda", false);
        zonasDano.put("Derecha", false);
        zonasDano.put("Techo", false);

        cargarDatosPreexistentes();
        construirUI();
    }

    private void cargarDatosPreexistentes() {
        String data = vehiculo.getRutaFoto();
        if (data == null) return;
        
        if (data.trim().toLowerCase().endsWith(".png") || data.trim().toLowerCase().endsWith(".jpg")) {
            rutaImagenAuto = data.trim();
            return;
        }
        
        if (!data.startsWith("INV:")) return;
        try {
            String[] partes = data.substring(4).split("\\|");
            int startIndex = 0;
            if (partes[0].trim().startsWith("img=")) {
                rutaImagenAuto = partes[0].trim().replace("img=", "");
                startIndex = 1;
            }
            
            String[] checks = partes[startIndex].trim().split(",");
            for (String c : checks) {
                String[] kv = c.split("=");
                if (kv.length == 2) {
                    boolean val = kv[1].trim().equals("1");
                    if (kv[0].trim().equalsIgnoreCase("llantas")) chkLlantas = new JCheckBox("", val);
                    if (kv[0].trim().equalsIgnoreCase("parabrisas")) chkParabrisas = new JCheckBox("", val);
                    if (kv[0].trim().equalsIgnoreCase("carroceria")) chkCarroceria = new JCheckBox("", val);
                    if (kv[0].trim().equalsIgnoreCase("faros")) chkFaros = new JCheckBox("", val);
                }
            }

            if (partes.length > startIndex + 1) {
                String[] gasKv = partes[startIndex + 1].trim().split("=");
                if (gasKv.length == 2) {
                    String val = gasKv[1].trim();
                    // Handle retro-compatibility for numeric gas levels (slider values 0-100)
                    if (val.matches("\\d+")) {
                        int numVal = Integer.parseInt(val);
                        if (numVal <= 15) val = "Muy Bajo";
                        else if (numVal <= 40) val = "Bajo";
                        else if (numVal <= 65) val = "Medio";
                        else if (numVal <= 85) val = "Alto";
                        else val = "Full";
                    }
                    cmbGasolina = new JComboBox<>(new String[]{"Muy Bajo", "Bajo", "Medio", "Alto", "Full"});
                    cmbGasolina.setSelectedItem(val);
                }
            }

            if (partes.length > startIndex + 2) {
                String[] zonList = partes[startIndex + 2].replace("zonas:", "").trim().split(",");
                for (String z : zonList) {
                    String[] kv = z.split("=");
                    if (kv.length == 2) {
                        zonasDano.put(kv[0].trim(), kv[1].trim().equals("1"));
                    }
                }
            }

            if (partes.length > startIndex + 3) {
                txtNotas = new JTextField(partes[startIndex + 3].replace("notas:", "").trim());
            }

        } catch (Exception e) {
            System.err.println("Error al parsear inventario guardado: " + e.getMessage());
        }
    }

    private void construirUI() {
        setLayout(new BorderLayout());
        
        // Header
        JPanel pnlHeader = new JPanel();
        pnlHeader.setBackground(Estilos.AZUL_OSCURO);
        JLabel lblTitulo = new JLabel("Recepción y Estado Físico del Vehículo");
        lblTitulo.setFont(Estilos.SUBTITULO);
        lblTitulo.setForeground(Color.WHITE);
        pnlHeader.add(lblTitulo);
        add(pnlHeader, BorderLayout.NORTH);

        // Body split
        JPanel pnlBody = new JPanel(new GridLayout(1, 2, 10, 10));
        pnlBody.setBackground(Estilos.GRIS_CLARO);
        pnlBody.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Left Panel: Form / Checklists
        JPanel pnlLeft = new JPanel();
        pnlLeft.setLayout(new BoxLayout(pnlLeft, BoxLayout.Y_AXIS));
        pnlLeft.setBackground(Color.WHITE);
        pnlLeft.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("Lista de Verificación"),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        if (chkLlantas == null) chkLlantas = new JCheckBox("Llantas en buen estado");
        else chkLlantas.setText("Llantas en buen estado");
        chkLlantas.setAlignmentX(Component.LEFT_ALIGNMENT);
        chkLlantas.setBackground(Color.WHITE);
        
        if (chkParabrisas == null) chkParabrisas = new JCheckBox("Parabrisas sin estrellar");
        else chkParabrisas.setText("Parabrisas sin estrellar");
        chkParabrisas.setAlignmentX(Component.LEFT_ALIGNMENT);
        chkParabrisas.setBackground(Color.WHITE);
        
        if (chkCarroceria == null) chkCarroceria = new JCheckBox("Carrocería sin abolladuras");
        else chkCarroceria.setText("Carrocería sin abolladuras");
        chkCarroceria.setAlignmentX(Component.LEFT_ALIGNMENT);
        chkCarroceria.setBackground(Color.WHITE);
        
        if (chkFaros == null) chkFaros = new JCheckBox("Faros y luces funcionales");
        else chkFaros.setText("Faros y luces funcionales");
        chkFaros.setAlignmentX(Component.LEFT_ALIGNMENT);
        chkFaros.setBackground(Color.WHITE);

        if (rutaImagenAuto != null && new java.io.File(rutaImagenAuto).exists()) {
            try {
                ImageIcon icon = new ImageIcon(rutaImagenAuto);
                Image img = icon.getImage().getScaledInstance(200, 120, Image.SCALE_SMOOTH);
                JLabel lblFoto = new JLabel(new ImageIcon(img));
                lblFoto.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
                
                JPanel pnlFotoWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
                pnlFotoWrapper.setBackground(Color.WHITE);
                pnlFotoWrapper.setMaximumSize(new Dimension(Short.MAX_VALUE, 130));
                pnlFotoWrapper.setAlignmentX(Component.LEFT_ALIGNMENT);
                pnlFotoWrapper.add(lblFoto);
                
                pnlLeft.add(Box.createVerticalStrut(6));
                pnlLeft.add(pnlFotoWrapper);
                pnlLeft.add(Box.createVerticalStrut(10));
            } catch (Exception e) {
                System.err.println("Error al cargar la imagen: " + e.getMessage());
            }
        }

        pnlLeft.add(chkLlantas);
        pnlLeft.add(Box.createVerticalStrut(8));
        pnlLeft.add(chkParabrisas);
        pnlLeft.add(Box.createVerticalStrut(8));
        pnlLeft.add(chkCarroceria);
        pnlLeft.add(Box.createVerticalStrut(8));
        pnlLeft.add(chkFaros);
        pnlLeft.add(Box.createVerticalStrut(15));

        // Fuel level
        JLabel lblGas = new JLabel("Nivel de Gasolina/Combustible:");
        lblGas.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlLeft.add(lblGas);
        
        String[] nivelesGas = {"Muy Bajo", "Bajo", "Medio", "Alto", "Full"};
        if (cmbGasolina == null) {
            cmbGasolina = new JComboBox<>(nivelesGas);
            cmbGasolina.setSelectedItem("Medio");
        }
        cmbGasolina.setMaximumSize(new Dimension(280, 32));
        cmbGasolina.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlLeft.add(cmbGasolina);
        pnlLeft.add(Box.createVerticalStrut(15));

        JLabel lblObs = new JLabel("Observaciones / Detalles adicionales:");
        lblObs.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlLeft.add(lblObs);
        
        if (txtNotas == null) {
            txtNotas = new JTextField();
        }
        txtNotas.setPreferredSize(new Dimension(280, 32));
        txtNotas.setMinimumSize(new Dimension(280, 32));
        txtNotas.setMaximumSize(new Dimension(280, 32));
        txtNotas.setAlignmentX(Component.LEFT_ALIGNMENT);
        pnlLeft.add(txtNotas);
        pnlBody.add(pnlLeft);

        // Right Panel: Interactive 2D car map
        JPanel pnlRight = new JPanel(new BorderLayout());
        pnlRight.setBackground(Color.WHITE);
        pnlRight.setBorder(BorderFactory.createTitledBorder("Daño por Zona (Haz Click para marcar)"));

        CarCanvas canvas = new CarCanvas();
        pnlRight.add(canvas, BorderLayout.CENTER);
        
        JLabel lblInstruc = new JLabel("Verde = Sin Daño | Rojo = Dañado", SwingConstants.CENTER);
        lblInstruc.setFont(new Font("SansSerif", Font.BOLD, 12));
        pnlRight.add(lblInstruc, BorderLayout.SOUTH);

        pnlBody.add(pnlRight);
        add(pnlBody, BorderLayout.CENTER);

        // Footer Actions
        JPanel pnlFooter = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        pnlFooter.setBackground(Estilos.GRIS_CLARO);

        BotonEstilizado btnGuardar = new BotonEstilizado("Guardar Estado", Estilos.VERDE);
        btnGuardar.addActionListener(e -> guardarInventario());

        BotonEstilizado btnCancelar = new BotonEstilizado("Cancelar", Estilos.ROJO);
        btnCancelar.addActionListener(e -> dispose());

        pnlFooter.add(btnGuardar);
        pnlFooter.add(btnCancelar);
        add(pnlFooter, BorderLayout.SOUTH);
    }

    private void guardarInventario() {
        // Build representation string
        StringBuilder sb = new StringBuilder();
        sb.append("INV: ");
        if (rutaImagenAuto != null && !rutaImagenAuto.isEmpty()) {
            sb.append("img=").append(rutaImagenAuto).append(" | ");
        }
        sb.append("llantas=").append(chkLlantas.isSelected() ? "1" : "0").append(",");
        sb.append("parabrisas=").append(chkParabrisas.isSelected() ? "1" : "0").append(",");
        sb.append("carroceria=").append(chkCarroceria.isSelected() ? "1" : "0").append(",");
        sb.append("faros=").append(chkFaros.isSelected() ? "1" : "0");
        sb.append(" | gas=").append(cmbGasolina.getSelectedItem());
        sb.append(" | zonas:");
        
        int i = 0;
        for (Map.Entry<String, Boolean> entry : zonasDano.entrySet()) {
            if (i > 0) sb.append(",");
            sb.append(entry.getKey()).append("=").append(entry.getValue() ? "1" : "0");
            i++;
        }
        sb.append(" | notas:").append(txtNotas.getText().trim());

        vehiculo.setRutaFoto(sb.toString());
        
        // Update database
        try {
            String sql = "UPDATE vehiculos SET ruta_foto = ? WHERE id = ?";
            try (java.sql.PreparedStatement ps = com.taller.dao.ConexionBD.getConexion().prepareStatement(sql)) {
                ps.setString(1, vehiculo.getRutaFoto());
                ps.setInt(2, vehiculo.getId());
                ps.executeUpdate();
            }
            bitacoraDAO.registrar("ACTUALIZAR", "Inventario visual registrado para vehículo placas: " + vehiculo.getPlacas());
            JOptionPane.showMessageDialog(this, "Inventario visual guardado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Custom 2D vehicle drawer & selector
    private class CarCanvas extends JComponent {
        
        // Zone shapes
        private final Rectangle rectFrente = new Rectangle(110, 30, 80, 50);
        private final Rectangle rectTecho = new Rectangle(110, 110, 80, 120);
        private final Rectangle rectAtras = new Rectangle(110, 260, 80, 50);
        private final Rectangle rectIzquierda = new Rectangle(40, 90, 60, 150);
        private final Rectangle rectDerecha = new Rectangle(200, 90, 60, 150);

        private int getTranslationX() {
            int drawingWidth = 300; // El dibujo abarca de X=35 a X=265 aprox.
            return Math.max(0, (getWidth() - drawingWidth) / 2);
        }

        public CarCanvas() {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    Point p = new Point(e.getPoint());
                    p.x -= getTranslationX(); // Ajustar coordenada según la traslación horizontal
                    
                    if (rectFrente.contains(p)) toggle("Frente");
                    else if (rectAtras.contains(p)) toggle("Atrás");
                    else if (rectTecho.contains(p)) toggle("Techo");
                    else if (rectIzquierda.contains(p)) toggle("Izquierda");
                    else if (rectDerecha.contains(p)) toggle("Derecha");
                    repaint();
                }
            });
        }

        private void toggle(String zona) {
            zonasDano.put(zona, !zonasDano.get(zona));
            bitacoraDAO.registrar("CLICK", "Clic en zona de vehículo: " + zona + " (Estado: " + (zonasDano.get(zona) ? "Dañado" : "OK") + ")");
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Background grid pattern
            g2.setColor(new Color(245, 245, 245));
            for (int i = 0; i < getWidth(); i += 20) {
                g2.drawLine(i, 0, i, getHeight());
            }
            for (int j = 0; j < getHeight(); j += 20) {
                g2.drawLine(0, j, getWidth(), j);
            }

            // Aplicar traslación para centrar el carro
            int tx = getTranslationX();
            g2.translate(tx, 0);

            // Draw regions
            drawZone(g2, rectFrente, "Frente", zonasDano.get("Frente"));
            drawZone(g2, rectTecho, "Techo", zonasDano.get("Techo"));
            drawZone(g2, rectAtras, "Atrás", zonasDano.get("Atrás"));
            drawZone(g2, rectIzquierda, "Izquierda", zonasDano.get("Izquierda"));
            drawZone(g2, rectDerecha, "Derecha", zonasDano.get("Derecha"));

            // Draw general outline details (windshield, trunk lines, wheels)
            g2.setColor(Color.DARK_GRAY);
            g2.setStroke(new BasicStroke(2));
            
            // Wheels
            g2.fillOval(35, 60, 15, 30);
            g2.fillOval(250, 60, 15, 30);
            g2.fillOval(35, 230, 15, 30);
            g2.fillOval(250, 230, 15, 30);
        }

        private void drawZone(Graphics2D g2, Rectangle r, String label, boolean isDamaged) {
            // Fill color
            Color fill = isDamaged ? new Color(230, 75, 75, 200) : new Color(75, 185, 110, 200);
            g2.setColor(fill);
            g2.fillRoundRect(r.x, r.y, r.width, r.height, 10, 10);

            // Border color
            g2.setColor(isDamaged ? new Color(180, 40, 40) : new Color(40, 130, 70));
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(r.x, r.y, r.width, r.height, 10, 10);

            // Draw Text Label
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("SansSerif", Font.BOLD, 12));
            FontMetrics fm = g2.getFontMetrics();
            int lx = r.x + (r.width - fm.stringWidth(label)) / 2;
            int ly = r.y + (r.height + fm.getAscent()) / 2 - 2;
            g2.drawString(label, lx, ly);
        }
    }
}
