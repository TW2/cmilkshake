package org.wingate.cmilkshake.sub;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Event {
    private EventType eventType;
    private int layer;
    private long microsStart;
    private long microsEnd;
    private String style;
    private String name;
    private int marginL;
    private int marginR;
    private int marginV;
    private String effects;
    private String text;

    public Event() {
    }

    public static Event createFromASSA(String rawLine){
        // SSA:
        // Format: Marked, Start, End, Style, Name, MarginL, MarginR, MarginV, Effect, Text
        // ASS:
        // Format: Layer, Start, End, Style, Name, MarginL, MarginR, MarginV, Effect, Text
        Event ev = new Event();

        String[] t = rawLine.split(",", 10);
        String[] t2 = t[0].split(":");
        ev.eventType = EventType.get(t2[0]);
        ev.layer = t2[1].toLowerCase().contains("marked") ? 0 : Integer.parseInt(t2[1].trim());
        ev.microsStart = ASSAtoMicros(t[1]);
        ev.microsEnd = ASSAtoMicros(t[2]);
        ev.style = t[3];
        ev.name = t[4];
        ev.marginL = Integer.parseInt(t[5]);
        ev.marginR = Integer.parseInt(t[6]);
        ev.marginV = Integer.parseInt(t[7]);
        ev.effects = t[8];
        ev.text = t[9];

        return ev;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public int getLayer() {
        return layer;
    }

    public void setLayer(int layer) {
        this.layer = layer;
    }

    public long getMicrosStart() {
        return microsStart;
    }

    public void setMicrosStart(long microsStart) {
        this.microsStart = microsStart;
    }

    public long getMicrosEnd() {
        return microsEnd;
    }

    public void setMicrosEnd(long microsEnd) {
        this.microsEnd = microsEnd;
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

    public String getEffects() {
        return effects;
    }

    public void setEffects(String effects) {
        this.effects = effects;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    /**
     * Convert 0:00:00.00 to microseconds
     * @param sTime raw time in ASSA
     * @return time in microseconds
     */
    public static long ASSAtoMicros(String sTime){
        Pattern p = Pattern.compile("(?<h>\\d+):(?<m>\\d+):(?<s>\\d+).(?<c>\\d+)");
        Matcher m = p.matcher(sTime);

        long micros = 0L;

        if(m.find()){
            micros =
                    Long.parseLong(m.group("h")) * 3_600_000_000L
                    + Long.parseLong(m.group("m")) * 60_000_000L
                    + Long.parseLong(m.group("s")) * 1_000_000L
                    + Long.parseLong(m.group("c")) * 10_000L;
        }

        return micros;
    }

    /**
     * Convert microseconds to 0:00:00.00
     * @param micros time in microseconds
     * @return raw time in ASSA
     */
    public static String microsToASSA(long micros){
        long h = micros / 3_600_000_000L;
        long m = (micros - 3_600_000_000L * h) / 60_000_000L;
        long s = (micros - 3_600_000_000L * h - 60_000_000L * m) / 1_000_000L;
        long c = (micros - 3_600_000_000L * h - 60_000_000L * m - 1_000_000L * s) / 10_000L;

        return String.format("%d:%2d:%2d.%2d", h, m, s, c);
    }
}
