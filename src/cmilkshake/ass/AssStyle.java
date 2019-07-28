/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmilkshake.ass;

import java.awt.Color;
import java.awt.Font;

/**
 *
 * @author util2
 */
public class AssStyle {
    
    private String Name = "Default";
    private String Fontname = "Arial";
    private float Fontsize = 28;
    private String PrimaryColour = "0000FFFF";
    private String SecondaryColour = "0000FFFF";
    private String OutlineColor = "00000000";
    private String BackColour = "00000000";
    private boolean Bold = false;
    private boolean Italic = false;
    private boolean Underline = false;
    private boolean Strikeout = false;
    private float ScaleX = 100;
    private float ScaleY = 100;
    private float Spacing = 0;
    private float Angle = 0;
    private int BorderStyle = 1;
    private int Outline = 2;
    private int Shadow = 0;
    private int Alignment = 2;
    private int MarginL = 10;
    private int MarginR = 10;
    private int MarginV = 10;
    private int MarginB = 10;
    private int MarginT = 10;
    private int AlphaLevel = 0;
    private int Encoding = 0;
    private int RelativeTo = 0;
    
    /** <p>A choice of elements for ASS or ASS2 style.<br />
     * Un choix d'éléments pour un style ASS ou ASS2.</p> 
     * @see setElement
     * @see getElement */
    public enum AssStyleType{
        name, fontname, fontsize, primarycolour, secondarycolour,
        outlinecolour, backcolour, bold, italic, underline,
        strikeout, scaleX, scaleY, spacing, angle, borderstyle,
        outline, shadow, alignment, marginL, marginR, marginV,
        marginB, marginT, alphalevel, encoding, relativeto;
    }
    
    public AssStyle(){
        
    }
    
    public static AssStyle create(String raw){
        AssStyle as = new AssStyle();
        
        if(raw.startsWith("Style: ")){
            as.fromAssStyleString(raw);
            return as;
        }
        
        return null;
    }
    
    public static Font getFontFromAssStyle(AssStyle style){
        int fontstyle = Font.PLAIN;
        
        if(style.Bold && style.Italic) {
            fontstyle = Font.BOLD + Font.ITALIC;
        }else if(style.Bold){
            fontstyle = Font.BOLD;
        }else if(style.Italic){
            fontstyle = Font.ITALIC;
        }
        
        Font f  = new Font(style.Fontname, fontstyle, 12);
        
        return f.deriveFont(style.Fontsize);
    }
    
    public static Color setAssColor(String raw){
        //ABGR or BGR
        if(raw.length() == 6) { raw = "00" + raw; }
        
        int a = Integer.parseInt(raw.substring(0, 2), 16);
        int b = Integer.parseInt(raw.substring(2, 4), 16);
        int g = Integer.parseInt(raw.substring(4, 6), 16);
        int r = Integer.parseInt(raw.substring(6, 8), 16);
        
        return new Color(r, g, b, a);
    }
    
    public static String getReadableAssColor(Color c, boolean includeAlpha){
        // Alpha
        String a = Integer.toString(c.getAlpha(), 16);
        a = a.length() < 2 ? "0" + a : a;
        
        // Blue
        String b = Integer.toString(c.getBlue(), 16);
        b = b.length() < 2 ? "0" + b : b;
        
        // Green
        String g = Integer.toString(c.getGreen(), 16);
        g = g.length() < 2 ? "0" + g : g;
        
        // Red
        String r = Integer.toString(c.getRed(), 16);
        r = r.length() < 2 ? "0" + r : r;
        
        return includeAlpha ? a + b + g + r : b + g + r;
    }
    
    public static int getStyleModifier(boolean status){
        return status ? -1 : 0;
    }
    
    public static boolean setStyleModifier(int value){
        return value == -1;
    }
    
    public String toAssStyleString(){
        String s = "Style: ";
        s+=Name+","+Fontname+","+Fontsize+",&H"+PrimaryColour+
                ",&H"+SecondaryColour+",&H"+OutlineColor+",&H"+BackColour+
                ","+getStyleModifier(Bold)+","+getStyleModifier(Italic)+
                ","+getStyleModifier(Underline)+","+getStyleModifier(Strikeout)+
                ","+ScaleX+","+ScaleY+","+Spacing+","+Angle+","+BorderStyle+
                ","+Outline+","+Shadow+","+Alignment+","+MarginL+
                ","+MarginR+","+MarginV+","+Encoding;
        return s;
    }
    
    public void fromAssStyleString(String style){
        String[] table = style.split(",");
        Name = table[0].substring(7);
        Fontname = table[1];
        Fontsize = Float.parseFloat(table[2]);
        PrimaryColour = table[3].substring(2);
        SecondaryColour = table[4].substring(2);
        OutlineColor = table[5].substring(2);
        BackColour = table[6].substring(2);
        Bold = setStyleModifier(Integer.parseInt(table[7]));
        Italic = setStyleModifier(Integer.parseInt(table[8]));
        Underline = setStyleModifier(Integer.parseInt(table[9]));
        Strikeout = setStyleModifier(Integer.parseInt(table[10]));
        ScaleX = Float.parseFloat(table[11]);
        ScaleY = Float.parseFloat(table[12]);
        Spacing = Float.parseFloat(table[13]);
        Angle = Float.parseFloat(table[14]);
        BorderStyle = Integer.parseInt(table[15]);
        Outline = Math.round(Float.parseFloat(table[16]));        
        Shadow = Math.round(Float.parseFloat(table[17]));
        Alignment = Integer.parseInt(table[18]);
        MarginL = Integer.parseInt(table[19]);
        MarginR = Integer.parseInt(table[20]);
        MarginV = Integer.parseInt(table[21]);
        Encoding = Integer.parseInt(table[22]);
    }
    
    public void setElement(AssStyleType ast, String value){
        switch(ast){
            case name: Name = value; break;
            case fontname: Fontname = value; break;
            case fontsize: Fontsize = Float.parseFloat(value); break;
            case bold: Bold = setStyleModifier(Integer.parseInt(value)); break;
            case italic: Italic = setStyleModifier(Integer.parseInt(value)); break;
            case underline: Underline = setStyleModifier(Integer.parseInt(value)); break;
            case strikeout: Strikeout = setStyleModifier(Integer.parseInt(value)); break;
            case scaleX: ScaleX = Float.parseFloat(value); break;
            case scaleY: ScaleY = Float.parseFloat(value); break;
            case spacing: Spacing = Float.parseFloat(value); break;
            case angle: Angle = Float.parseFloat(value); break;
            case borderstyle: BorderStyle = Integer.parseInt(value); break;
            case outline: Outline = Integer.parseInt(value); break;
            case shadow: Shadow = Integer.parseInt(value); break;
            case marginL: MarginL = Integer.parseInt(value); break;
            case marginR: MarginR = Integer.parseInt(value); break;
            case marginV: MarginV = Integer.parseInt(value); break;
            case marginB: MarginB = Integer.parseInt(value); break;
            case marginT: MarginT = Integer.parseInt(value); break;
            case alphalevel: AlphaLevel = Integer.parseInt(value); break;
            case encoding: Encoding = Integer.parseInt(value); break;
            case relativeto: RelativeTo = Integer.parseInt(value); break;
            
            case primarycolour: PrimaryColour = value.replaceAll("&H", ""); break;
            case secondarycolour: SecondaryColour = value.replaceAll("&H", ""); break;
            case outlinecolour: OutlineColor = value.replaceAll("&H", ""); break;
            case backcolour: BackColour = value.replaceAll("&H", ""); break;
            
            case alignment: Alignment = Integer.parseInt(value); break;
        }
    }
    
    public String getElement(AssStyleType ast){
        switch(ast){
            case name: return Name;
            case fontname: return Fontname;
            case fontsize: return Integer.toString((int)Fontsize);
            case bold: return Integer.toString(getStyleModifier(Bold));
            case italic: return Integer.toString(getStyleModifier(Italic));
            case underline: return Integer.toString(getStyleModifier(Underline));
            case strikeout: return Integer.toString(getStyleModifier(Strikeout));
            case scaleX: return Integer.toString((int)ScaleX);
            case scaleY: return Integer.toString((int)ScaleY);
            case spacing: return Integer.toString((int)Spacing);
            case angle: return Integer.toString((int)Angle);
            case borderstyle: return Integer.toString(BorderStyle);
            case outline: return Integer.toString(Outline);
            case shadow: return Integer.toString(Shadow);
            case marginL: return Integer.toString(MarginL);
            case marginR: return Integer.toString(MarginR);
            case marginV: return Integer.toString(MarginV);
            case marginB: return Integer.toString(MarginB);
            case marginT: return Integer.toString(MarginT);
            case alphalevel: return Integer.toString(AlphaLevel);
            case encoding: return Integer.toString(Encoding);
            case relativeto: return Integer.toString(RelativeTo);
            
            case primarycolour: return "&H" + PrimaryColour;
            case secondarycolour: return "&H" + SecondaryColour;
            case outlinecolour: return "&H" + OutlineColor;
            case backcolour: return "&H" + BackColour;
            
            case alignment: return Integer.toString(Alignment);
            
            default: return "";
        }
    }
}
