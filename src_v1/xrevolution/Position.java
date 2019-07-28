/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xrevolution;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Its function is to define subtitles position.
 * <p><b>EN : </b>This class is a part of XRevolution, a test project.<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * It was added in Milkshake because we have similar needs.<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * Its function is to define subtitles position.</p>
 * <p><b>FR : </b>Cette classe fait partie du projet de test XRevolution.<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * Elle a été ajouté dans Milkshake car on a des besoins similaires.<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * Elle sert à définir la position des sous-titres.</p>
 * @author The Wingate 2940
 */
public class Position {
    
    private int width = 0;
    private int height = 0;
    private int marginL = 0;
    private int marginR = 0;
    private int marginV = 0;
    private String text = "";
    private Font font = new Font("Dialog", Font.PLAIN, 20);
    private Frame frm = new Frame();
    private FontMetrics fm;
    private Alignment align = Alignment.an2;
    private Subtitle sub = new Subtitle();
    
    public Position(int width, int height, Style as, Subtitle sub, Infos ai){
        this.width = width;
        this.height = height;
        marginL = sub.getMarginL()>0 ? sub.getMarginL() : as.getMarginL();
        marginR = sub.getMarginR()>0 ? sub.getMarginR() : as.getMarginR();
        marginV = sub.getMarginV()>0 ? sub.getMarginV() : as.getMarginV();
        text = sub.getSubtitle();
        int scrW = Integer.parseInt(ai.getElement(Infos.AssInfosType.playresx));
        int scrH = Integer.parseInt(ai.getElement(Infos.AssInfosType.playresy));
        font = setFont(width, scrW, height, scrH, as.getFont());
        align = align.getAlignment(as.getElement(Style.AssStyleType.alignment));
        this.sub = sub;
        
        fm = frm.getFontMetrics(font);
    }
    
    public enum Alignment{
        an7(5,7), an8(6,8), an9(7,9),
        an4(9,4), an5(10,5), an6(11,6),
        an1(1,1), an2(1,2), an3(1,3);
        
        int ass;
        int ssa;

        private Alignment(int ssa, int ass) {
            this.ssa = ssa;
            this.ass = ass;
        }
        
        public Alignment getAlignment(String align){
            if(align.equalsIgnoreCase("an1") | align.equalsIgnoreCase("a1")){ return an1; }
            if(align.equalsIgnoreCase("an2") | align.equalsIgnoreCase("a2")){ return an2; }
            if(align.equalsIgnoreCase("an3") | align.equalsIgnoreCase("a3")){ return an3; }
            if(align.equalsIgnoreCase("an4") | align.equalsIgnoreCase("a9")){ return an4; }
            if(align.equalsIgnoreCase("an5") | align.equalsIgnoreCase("a10")){ return an5; }
            if(align.equalsIgnoreCase("an6") | align.equalsIgnoreCase("a11")){ return an6; }
            if(align.equalsIgnoreCase("an7") | align.equalsIgnoreCase("a5")){ return an7; }
            if(align.equalsIgnoreCase("an8") | align.equalsIgnoreCase("a6")){ return an8; }
            if(align.equalsIgnoreCase("an9") | align.equalsIgnoreCase("a7")){ return an9; }
            return an2;
        }
    }
    
    private int getPosX(){
        return getPosX(text);
    }
    
    private int getPosX(String s){
        int x = 0; boolean respectMargin = true;
        int size = fm.stringWidth(s);        
        if(s.contains("\\a")){
            Pattern p = Pattern.compile("(\\\\an\\d+)");
            Matcher m = p.matcher(s);
            if(m.find()){
                align = align.getAlignment(m.group(1).substring(1));
            }
            p = Pattern.compile("(\\\\a\\d+)");
            m = p.matcher(s);
            if(m.find()){
                align = align.getAlignment(m.group(1).substring(1));
            }
        }        
        if(s.contains("\\pos")){
            Pattern p = Pattern.compile("\\\\pos\\((\\d+),(\\d+)");
            Matcher m = p.matcher(s);
            if(m.find()){
                x = Integer.parseInt(m.group(1));
                respectMargin = false;
            }
        }
        if(align == Alignment.an1 | align == Alignment.an4 | align == Alignment.an7){
            if(respectMargin==true){
                return marginL;
            }else{
                return x;
            }
        }else if(align == Alignment.an2 | align == Alignment.an5 | align == Alignment.an8){
            if(respectMargin==true){
                return (width-size)/2;
            }else{
                return x - size/2;
            }
        }else if(align == Alignment.an3 | align == Alignment.an6 | align == Alignment.an9){
            if(respectMargin==true){
                return width-size - marginR;
            }else{
                return x - size;
            }
        }
        return 0;
    }
    
    private int getPosY(){
        return getPosY(text, 1, 1);
    }
    
    private int getPosY(String s, int adl_times, int times){
        int y = 0; boolean respectMargin = true;
        int adl = fm.getHeight();
        if(s.contains("\\a")){
            Pattern p = Pattern.compile("(\\\\an\\d+)");
            Matcher m = p.matcher(s);
            if(m.find()){
                align = align.getAlignment(m.group(1).substring(1));
            }
            p = Pattern.compile("(\\\\a\\d+)");
            m = p.matcher(s);
            if(m.find()){
                align = align.getAlignment(m.group(1).substring(1));
            }
        }        
        if(s.contains("\\pos")){
            Pattern p = Pattern.compile("\\\\pos\\((\\d+),(\\d+)");
            Matcher m = p.matcher(s);
            if(m.find()){
                y = Integer.parseInt(m.group(2));
                respectMargin = false;
            }
        }
        if(align == Alignment.an1 | align == Alignment.an2 | align == Alignment.an3){
            if(respectMargin==true){
                if(adl_times==1){
                    return height - marginV;
                }else{
                    return height - marginV - adl*(adl_times-1);
                }
            }else{
                if(adl_times==1){
                    return y;
                }else{
                    return y - adl*(adl_times-1);
                }                
            }
        }else if(align == Alignment.an4 | align == Alignment.an5 | align == Alignment.an6){
            if(respectMargin==true){
                if(adl_times==1){
                    return (height-adl)/2;
                }else{
                    return (height-adl*times)/2-adl*times/2 + adl*(adl_times-1);
                }
            }else{
                if(adl_times==1){
                    return y + adl/2;
                }else{
                    return y + adl/2-adl*times/2 + adl*(adl_times-1);
                }
            }
        }else if(align == Alignment.an7 | align == Alignment.an8 | align == Alignment.an9){
            if(respectMargin==true){
                return adl*adl_times + marginV;
            }else{
                return y + adl*adl_times;
            }
        }
        return 0;
    }
    
    private Font setFont(int videowidth, int scriptwidth,
            int videoheight, int scriptheight, Font f){
        if(videowidth!=scriptwidth){
            Font newfont = new Font(f.getFamily(), f.getStyle(), f.getSize()*videowidth/scriptwidth);
            return newfont;
        }else if(videoheight!=scriptheight){
            Font newfont = new Font(f.getFamily(), f.getStyle(), f.getSize()*videoheight/scriptheight);
            return newfont;
        }else{
            return f;
        }
    }
    
    public Font getFont(){
        return font;
    }
    
    public List<Subtitle> getSubtitleList(){
        List<Subtitle> sublist = new ArrayList<>();
        if(text.contains("\\N")){
            String[] table = text.split("\\\\N");
            for(int i=0;i<table.length;i++){
                Subtitle newsub = new Subtitle(sub.getHead()+","+table[i]);
                newsub.setPosX(getPosX(table[i]));
                newsub.setPosY(getPosY(table[i], i+1, table.length));
                newsub.setFont(font);
                sublist.add(newsub);
            }
        }else{
            Subtitle newsub = new Subtitle(sub.getHead()+","+text);
            newsub.setPosX(getPosX());
            newsub.setPosY(getPosY());
            newsub.setFont(font);
            sublist.add(newsub);
        }
        return sublist;
    }
}
