package org.wingate.cmilkshake.ui;

import javax.swing.*;
import java.awt.*;

public class Wave extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());


    }
}
