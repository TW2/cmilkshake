/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xrevolution;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Shape;
import java.awt.font.FontRenderContext;
import java.awt.font.TextLayout;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.BufferedImage;

/**
 * Its function is to isolate letters and syllables.
 * <p><b>EN : </b>This class is a part of XRevolution, a test project.<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * It was added in Milkshake because we have similar needs.<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * Its function is to isolate letters and syllables.</p>
 * <p><b>FR : </b>Cette classe fait partie du projet de test XRevolution.<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * Elle a été ajouté dans Milkshake car on a des besoins similaires.<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * Elle sert à isoler les lettres ou les syllabes.</p>
 * @author The Wingate 2940
 */
public class Glyph {
    
    private String glyph = "";
    private int posX, posY;
    private Font font = null;
    private int sizeX = 0;
    private int sizeY = 0;
    
    public Glyph(Font font){
        this.font = font;
    }
    
    /** On définit le texte de la glyphe */
    public void setGlyph(String glyph){
        this.glyph = glyph;
        storeSize();
    }
    
    /** On définit la position de la glyphe */
    public void setPosition(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }
    
    /** On obtient le texte de la glyphe */
    public String getGlyph(){
        return glyph;
    }
    
    /** On obtient l'abiscisse */
    public int getPosX(){
        return posX;
    }
    
    /** On obtient l'ordonnée */
    public int getPosY(){
        return posY;
    }
    
    /** On définit la police */
    public void setFont(Font font){
        this.font = font;
    }
    
    /** On obtient la police */
    public Font getFont(){
        return font;
    }
    
    /** Garde en mémoire la taille */
    private void storeSize(){
        if(glyph.isEmpty()==false){
            BufferedImage bi = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.getGraphics();
            FontMetrics fm = g.getFontMetrics(font);
            sizeX = fm.stringWidth(glyph);
            sizeY = fm.getLeading();
        }else{
            sizeX = 0;
            sizeY = 0;
        }
    }
    
    /** On obtient la taille de la glyphe */
    public int getSize(){
        return sizeX;
    }
    
    public Shape getShape(){
        TextLayout tl = new TextLayout(glyph, font, new FontRenderContext(null, true, false));
        AffineTransform at = new AffineTransform();
	at.translate(posX, posY);
        return tl.getOutline(at);
    }
    
    public Shape getFarShape(double scale){
        //Calcul de la position
        double x = sizeX*scale; double y = sizeY*scale;
        double diffX = (x - sizeX)/2; double diffY = (y - sizeY)/2;
        double newPosX = posX-diffX; double newPosY = posY+diffY;
        
        //Préparation de la forme
        TextLayout tl = new TextLayout(glyph, font, new FontRenderContext(null, true, false));
        AffineTransform at1 = new AffineTransform();
        Shape s1 = tl.getOutline(at1);
        GeneralPath gp1 = new GeneralPath(s1);
        AffineTransform at2 = new AffineTransform();
        at2.setToScale(scale, scale);
        Shape s2 = gp1.createTransformedShape(at2);
        GeneralPath gp2 = new GeneralPath(s2);
        AffineTransform at3 = new AffineTransform();
        at3.translate(newPosX, newPosY);
        return gp2.createTransformedShape(at3);
    }
}
