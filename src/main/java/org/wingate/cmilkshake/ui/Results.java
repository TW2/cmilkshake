package org.wingate.cmilkshake.ui;

import org.wingate.cmilkshake.sub.Event;

import javax.swing.*;
import java.awt.*;
import java.util.List;


public class Results extends JPanel {

    private List<Event> a, b;
    private boolean compared;

    public Results() {
        setDoubleBuffered(true);
        compared = false;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if(a != null && b != null){
            Graphics2D g2d = (Graphics2D)g;

            compare();
        }
    }

    public List<Event> getA() {
        return a;
    }

    public void setA(List<Event> a) {
        this.a = a;
        compared = false;
    }

    public List<Event> getB() {
        return b;
    }

    public void setB(List<Event> b) {
        this.b = b;
        compared = false;
    }

    private void compare(){
        if(compared) return;



        compared = true;
    }
}
