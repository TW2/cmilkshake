package org.wingate.cmilkshake.ui;

import org.wingate.cmilkshake.sub.Event;
import org.wingate.cmilkshake.sub.EventType;

import java.awt.*;

public class View {
    private Event event;
    private ViewState state;
    private Rectangle area;

    public View(Event event, ViewState state) {
        this.event = event;
        this.state = state;
    }

    public View(Event event) {
        this(event, ViewState.SameContent);
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public ViewState getState() {
        return state;
    }

    public void setState(ViewState state) {
        this.state = state;
    }

    public Rectangle getArea() {
        return area;
    }

    public void setArea(Rectangle area) {
        this.area = area;
    }

    public void drawEvent(Graphics2D g){
        Rectangle r = area;
        g.setColor(state.getColor());

        Font oldFont = g.getFont();
        if(event.getEventType() == EventType.Comment){
            g.setFont(g.getFont().deriveFont(Font.ITALIC));
            g.setColor(new Color(255, 255, 255, 83));
        }

        g.drawString(event.getText(), r.x + 3, r.y + r.height - 6);

        g.setFont(oldFont);
    }
}
