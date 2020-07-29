/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.cmilkshake.ass;

import org.wingate.cmilkshake.util.Time;

/**
 *
 * @author util2
 */
public class AssLine {
    
    /*
    [Events]
    Format: Layer, Start, End, Style, Name, MarginL, MarginR, MarginV, Effect, Text
    */
    
    private AssLineType lineType = AssLineType.Dialogue;
    private int layer;
    private Time start;
    private Time end;
    private String style;
    private String name;
    private int ML, MR, MV;
    private String effect;
    private String text;
    
    public AssLine() {
    }
    
    public static AssLine create(String raw){
        AssLine line = new AssLine();
        
        if(raw.startsWith("Format")) { return null; }
        
        String[] table = raw.split(",", 10);
        
        line.lineType = AssLineType.identify(raw);
        line.layer = Integer.parseInt(table[0].substring((line.lineType.getType() + ": ").length()));
        line.start = Time.create(table[1]);
        line.end = Time.create(table[2]);
        line.style = table[3];
        line.name = table[4];
        line.ML = Integer.parseInt(table[5]);
        line.MR = Integer.parseInt(table[6]);
        line.MV = Integer.parseInt(table[7]);
        line.effect = table[8];
        line.text = table[9];
        
        return line;
    }
    
    public String toAssLine(){
        StringBuilder sb = new StringBuilder();
        
        sb.append(lineType.getType());
        sb.append(": ");
        
        sb.append(Integer.toString(layer));
        sb.append(", ");
        
        sb.append(start.toASSTime());
        sb.append(", ");
        
        sb.append(end.toASSTime());
        sb.append(", ");
        
        sb.append(style);
        sb.append(", ");
        
        sb.append(name);
        sb.append(", ");
        
        sb.append(Integer.toString(ML));
        sb.append(", ");
        
        sb.append(Integer.toString(MR));
        sb.append(", ");
        
        sb.append(Integer.toString(MV));
        sb.append(", ");
        
        sb.append(effect);
        sb.append(", ");
        
        sb.append(text);
        
        return sb.toString();
    }

    public AssLineType getLineType() {
        return lineType;
    }

    public void setLineType(AssLineType lineType) {
        this.lineType = lineType;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
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

    public int getML() {
        return ML;
    }

    public void setML(int ML) {
        this.ML = ML;
    }

    public int getMR() {
        return MR;
    }

    public void setMR(int MR) {
        this.MR = MR;
    }

    public int getMV() {
        return MV;
    }

    public void setMV(int MV) {
        this.MV = MV;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
    public enum SentenceState{
        Unknown, Double, Deleted, Added;
    }
    
    public enum StyleState{
        Unchanged, Double, Old, New;
    }
    
    public enum NameState{
        Unchanged, Double, Old, New;
    }
    
    public enum TimeState{
        Unchanged, Double, Old, New, Shift;
    }
    
    private SentenceState sentenceState = SentenceState.Unknown;
    private StyleState styleState = StyleState.Unchanged;
    private NameState nameState = NameState.Unchanged;
    private TimeState timeState = TimeState.Unchanged;

    public SentenceState getSentenceState() {
        return sentenceState;
    }

    public void setSentenceState(SentenceState sentenceState) {
        this.sentenceState = sentenceState;
    }

    public StyleState getStyleState() {
        return styleState;
    }

    public void setStyleState(StyleState styleState) {
        this.styleState = styleState;
    }

    public NameState getNameState() {
        return nameState;
    }

    public void setNameState(NameState nameState) {
        this.nameState = nameState;
    }

    public TimeState getTimeState() {
        return timeState;
    }

    public void setTimeState(TimeState timeState) {
        this.timeState = timeState;
    }
    
    
}
