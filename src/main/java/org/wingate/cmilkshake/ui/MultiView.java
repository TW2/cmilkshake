package org.wingate.cmilkshake.ui;

import org.wingate.cmilkshake.compare.Compare;
import org.wingate.cmilkshake.sub.Event;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MultiView extends JPanel {

    private final Results results;

    private List<Event> a, b;
    private List<View> sentenceViews;
    private boolean compared;

    private int yLocation = 0;

    public MultiView(Results results) {
        setDoubleBuffered(true);

        this.results = results;

        compared = false;
        sentenceViews = new ArrayList<>();

        addMouseWheelListener((e) -> {
            if(!sentenceViews.isEmpty()){
                yLocation += (e.getWheelRotation() > 0 ? -23 : 23);
                yLocation = Math.min(0, yLocation);
                int h = (int)Math.ceil((double)getHeight() / 23d) - 1;
                yLocation = Math.max(-sentenceViews.size() * 23 + h * 23, yLocation);
            }

            repaint();
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(new Color(255,255,255, 32));
        for(int y=0; y<getHeight(); y+=23){
            g.drawLine(0, y, getWidth(), y);
        }

        if(a != null && b != null){
            Graphics2D g2d = (Graphics2D)g;

            compare();

            int y = yLocation, h = 22;
            int w = getWidth();

            for(View v : sentenceViews){
                if(y >= 0 && y <= getHeight()){
                    v.setArea(new Rectangle(0, y, w, h));
                    v.drawEvent(g2d);
                }
                y += h+1;
            }
        }
    }

    public void setA(List<Event> a) {
        this.a = a;
        compared = false;
    }

    public void setB(List<Event> b) {
        this.b = b;
        compared = false;
    }

    private void compare(){
        if(compared) return;

        sentenceViews = Compare.getSentenceViews(a, b);
        results.setViews(sentenceViews);

        compared = true;
    }
}
