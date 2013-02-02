/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xrevolution;

import java.awt.Font;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import milkshake.ass.Time;

/**
 * This class is a storage for subtitles.
 * <p><b>EN : </b>This class is a part of XRevolution, a test project.<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * It was added in Milkshake because we have similar needs.<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * This class is a storage for subtitles.</p>
 * <p><b>FR : </b>Cette classe fait partie du projet de test XRevolution.<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * Elle a été ajouté dans Milkshake car on a des besoins similaires.<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * Cette classe est un espace de stockage pour les sous-tires.</p>
 * @author The Wingate 2940
 */
public class Subtitle {
    
    private String sub = "";
    private long startTimestamp = -1;
    private long endTimestamp = -1;
    private Type type = Type.Dialogue;
    private int layer = 0;
    private String style = "Default";
    private String name = "";
    private String effect = "";
    private int marginL = 0;
    private int marginR = 0;
    private int marginV = 0;
    private Origin origin = Origin.NONE;
    private int posX = 0;
    private int posY = 0;
    private Font font = new Font("Dialog", Font.PLAIN, 20);
    private String head = "";
    private List<Glyph> listGlyph = new ArrayList<>();
    
    public Subtitle(String sub, long startTimestamp, long endTimestamp){
        this.sub = sub;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
    }
    
    public Subtitle(String sub, String start, String end){
        this.sub = sub;
        startTimestamp = timeToTimestamp(start);
        endTimestamp = timeToTimestamp(end);
    }
    
    public Subtitle(String assline){
        Pattern p = Pattern.compile("([^:]+):(\\s[a-zA-Z=]*\\d+)," +
                            "(\\d+:\\d+:\\d+.\\d+)," +
                            "(\\d+:\\d+:\\d+.\\d+)," +
                            "([^,]+),([^,]*)," +
                            "(\\d+),(\\d+),(\\d+),([^,]*),(.*)");
        Matcher m = p.matcher(assline);
        m.find();
        type = type.getType(m.group(1));
        if(m.group(2).contains("=")==false){
            layer = Integer.parseInt(m.group(2).trim());
        }
        startTimestamp = timeToTimestamp(m.group(3));
        endTimestamp = timeToTimestamp(m.group(4));
        style = m.group(5);
        name = m.group(6);
        marginL = Integer.parseInt(m.group(7));
        marginR = Integer.parseInt(m.group(8));
        marginV = Integer.parseInt(m.group(9));
        effect = m.group(10);
        sub = m.group(11);
        origin = Origin.ASS;
        
        p = Pattern.compile("([^:]+:\\s[a-zA-Z=]*\\d+," +
                            "\\d+:\\d+:\\d+.\\d+," +
                            "\\d+:\\d+:\\d+.\\d+," +
                            "[^,]+,[^,]*," +
                            "\\d+,\\d+,\\d+,[^,]*),(.*)");
        m = p.matcher(assline);
        m.find();
        head = m.group(1);
    }
    
    public Subtitle(){
        
    }
    
    public void setSubtitle(String sub){
        this.sub = sub;
    }
    
    public String getSubtitle(){
        return sub;
    }
    
    public String getHead(){
        return head;
    }
    
    public void setStart(long startTimestamp){
        this.startTimestamp = startTimestamp;
    }
    
    public void setStart(String start){
        startTimestamp = timeToTimestamp(start);
    }
    
    public long getStart(){
        return startTimestamp;
    }
    
    public void setEnd(long endTimestamp){
        this.endTimestamp = endTimestamp;
    }
    
    public void setEnd(String end){
        endTimestamp = timeToTimestamp(end);
    }
    
    public long getEnd(){
        return endTimestamp;
    }
    
    public void setPosX(int posX){
        this.posX = posX;
    }
    
    public int getPosX(){
        return posX;
    }
    
    public void setPosY(int posY){
        this.posY = posY;
    }
    
    public int getPosY(){
        return posY;
    }
    
    public void setFont(Font font){
        this.font = font;
    }
    
    public Font getFont(){
        return font;
    }
    
    private long timeToTimestamp(String t){
        Pattern p = Pattern.compile("(\\d+):(\\d+):(\\d+).(\\d+)");
        Matcher m = p.matcher(t);
        
        Time time = new Time();
        
        while(m.find()){
            time.setHours(Integer.parseInt(m.group(1)));
            time.setMinutes(Integer.parseInt(m.group(2)));
            time.setSeconds(Integer.parseInt(m.group(3)));
            time.setMilliseconds(Integer.parseInt(m.group(4))*10);
            return Time.toMillisecondsTime(time);
        }
        return -1;
    }
    
    public boolean isVisible(long timestamp){
        if(timestamp >= startTimestamp & timestamp < endTimestamp & type==Type.Dialogue){
            return true;
        }else{
            return false;
        }
    }
    
    public enum Type{
        Dialogue("Dialogue"),Comment("Comment"), Picture("Picture"),
        Sound("Sound"), Movie("Movie"), Command("Command");

        protected String stype;

        /** Constructor */
        Type(String stype){
            this.stype = stype;
        }
        
        public Type getType(String stype){
            Type t = Type.Dialogue;
            for(Type ty : Type.values()){
                if(stype.equals(ty.getType())){
                    t = ty;
                }
            }
            return t;
        }
        
        public String getType(){
            return this.stype;
        }
        
    }
    
    public enum Origin{
        NONE, ASS, SRT;
    }
    
    public Origin getOrigin(){
        return origin;
    }
    
    public String getStyle(){
        return style;
    }
    
    public int getMarginL(){
        return marginL;
    }
    
    public int getMarginR(){
        return marginR;
    }
    
    public int getMarginV(){
        return marginV;
    }
    
    public List<Glyph> getGlyphList(){
        listGlyph.clear();
        int x = 0 + posX;
        char[] letters = sub.toCharArray();
        for(char letter : letters){
            String sc = String.valueOf(letter);
            Glyph g = new Glyph(font);
            g.setGlyph(sc);
            g.setPosition(x, posY);
            x += g.getSize();
            listGlyph.add(g);
        }
        return listGlyph;
    }
}
