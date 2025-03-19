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

import org.wingate.cmilkshake.ass.AssLine;
import org.wingate.cmilkshake.util.Time;

/**
 *
 * @author util2
 */
public class TimeLineEvent {
    
    private boolean selected = false;
    
    private Time start;         // ssa, ass, srt
    private Time end;           // ssa, ass, srt
    private String text;        // ssa, ass, srt
    private int index;          // srt
    private int layer;          // ass
    private String style;       // ssa, ass
    private String name;        // ssa, ass
    private int marginL;        // ssa, ass
    private int marginR;        // ssa, ass
    private int marginV;        // ssa, ass
    private String effect;      // ssa, ass

    public TimeLineEvent() {
        start = Time.create(0L);
        end = Time.create(0L);
        text = "";
        index = 0;
        layer = 0;
        style = "Default";
        name = "";
        marginL = 10;
        marginR = 10;
        marginV = 10;
        effect = "";
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public Time getStart() {
        return start;
    }

    public void setStart(Time start) {
        this.start = start;
    }

    public Time getEnd() {
        return end;
    }

    public void setEnd(Time end) {
        this.end = end;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMarginL() {
        return marginL;
    }

    public void setMarginL(int marginL) {
        this.marginL = marginL;
    }

    public int getMarginR() {
        return marginR;
    }

    public void setMarginR(int marginR) {
        this.marginR = marginR;
    }

    public int getMarginV() {
        return marginV;
    }

    public void setMarginV(int marginV) {
        this.marginV = marginV;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }
    
    public static TimeLineEvent fromAssEvent(AssLine line, int lineIndex){
        TimeLineEvent event = new TimeLineEvent();
        
        event.start = line.getStart();
        event.end = line.getEnd();
        event.text = line.getText();
        event.index = lineIndex;
        event.layer = line.getLayer();
        event.style = line.getStyle();
        event.name = line.getName();
        event.marginL = line.getML();
        event.marginR = line.getMR();
        event.marginV = line.getMV();
        event.effect = line.getEffect();
        
        return event;
    }
    
    public static AssLine toAssLine(TimeLineEvent event){
        AssLine line = new AssLine();
        
        line.setStart(event.start);
        line.setEnd(event.end);
        line.setText(event.text);
        line.setLayer(event.layer);
        line.setStyle(event.style);
        line.setName(event.name);
        line.setML(event.marginL);
        line.setMR(event.marginR);
        line.setMV(event.marginV);
        line.setEffect(event.effect);
        
        return line;
    }
    
}
