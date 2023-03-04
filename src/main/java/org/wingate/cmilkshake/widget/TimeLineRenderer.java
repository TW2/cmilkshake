/*
 * Copyright (C) 2023 util2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.wingate.cmilkshake.widget;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.Timer;
import org.wingate.cmilkshake.util.DrawColor;
import org.wingate.cmilkshake.util.Time;

/**
 *
 * @author util2
 */
public class TimeLineRenderer extends JPanel {
    
    private final List<TimeLineEvent> events = new ArrayList<>();
    private int lineHeight = 60;
    private float horizontalZoom = 1f;
    private float verticalZoom = 1f;
    
    private Color areaColor = DrawColor.light_gray.getColor(.3f);
    private Color boundColor = DrawColor.light_gray.getColor(.7f);
    private Color selectedColor = DrawColor.green_yellow.getColor();
    
    private double scaleMsTimePerPixel = 50d;
    private long msOffset = 0L;

    public TimeLineRenderer() {
        new Timer(100, (l) -> {
            repaint();
        }).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D)g;
        Stroke oldStroke = g2d.getStroke();
        
        g2d.setColor(Color.black);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
        for(TimeLineEvent ev : events){
            long msStart = Time.toMillisecondsTime(ev.getStart());
            long msEnd = Time.toMillisecondsTime(ev.getEnd());
            
            double left = (msStart + msOffset) / scaleMsTimePerPixel * horizontalZoom;
            double width = (msEnd - msStart) / scaleMsTimePerPixel * horizontalZoom;
            
            double leftLimit = msOffset / scaleMsTimePerPixel * horizontalZoom;
            double rightLimit = Math.abs(msOffset) / scaleMsTimePerPixel * horizontalZoom + getWidth();
            
            if(left + width > leftLimit && left + width <= rightLimit){
                Rectangle2D r2d = new Rectangle2D.Double(left, 0, width, lineHeight);
                g2d.setColor(ev.isSelected() ? selectedColor : areaColor);
                g2d.fill(r2d);
                g2d.setColor(boundColor);
                g2d.setStroke(new BasicStroke(2f));
                g2d.draw(r2d);
                g2d.setStroke(oldStroke);
            }
            
        }
    }
    
    public List<TimeLineEvent> getEvents(){
        return events;
    }

    public int getLineHeight() {
        return lineHeight;
    }

    public void setLineHeight(int lineHeight) {
        this.lineHeight = lineHeight;
    }

    public float getHorizontalZoom() {
        return horizontalZoom;
    }

    public void setHorizontalZoom(float horizontalZoom) {
        this.horizontalZoom = horizontalZoom;
    }

    public float getVerticalZoom() {
        return verticalZoom;
    }

    public void setVerticalZoom(float verticalZoom) {
        this.verticalZoom = verticalZoom;
    }

    public Color getAreaColor() {
        return areaColor;
    }

    public void setAreaColor(Color areaColor) {
        this.areaColor = areaColor;
    }

    public Color getBoundColor() {
        return boundColor;
    }

    public void setBoundColor(Color boundColor) {
        this.boundColor = boundColor;
    }

    public Color getSelectedColor() {
        return selectedColor;
    }

    public void setSelectedColor(Color selectedColor) {
        this.selectedColor = selectedColor;
    }

    public double getScaleMsTimePerPixel() {
        return scaleMsTimePerPixel;
    }

    public void setScaleMsTimePerPixel(double scaleMsTimePerPixel) {
        this.scaleMsTimePerPixel = scaleMsTimePerPixel;
    }

    public long getMsOffset() {
        return msOffset;
    }

    public void setMsOffset(long msOffset) {
        this.msOffset = msOffset;
    }
    
    
    
}
