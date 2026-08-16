package com.taller.ui;

import javax.swing.*;
import java.awt.*;

public class BotonEstilizado extends JButton {
    private final Color colorFondo;

    public BotonEstilizado(String texto, Color colorFondo) {
        super(texto);
        this.colorFondo = colorFondo;
        setFont(Estilos.BOTON);
        setForeground(Color.WHITE);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        setBorderPainted(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        if (getModel().isPressed()) {
            g2.setColor(colorFondo.darker());
        } else if (getModel().isRollover()) {
            g2.setColor(colorFondo.brighter());
        } else {
            g2.setColor(colorFondo);
        }
        
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        g2.dispose();
        
        super.paintComponent(g);
    }
}
