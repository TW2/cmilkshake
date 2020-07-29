/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmilkshake.ass;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author util2
 */
public class Ass {
    
    private List<String> names = new ArrayList<>();
    private List<AssStyle> styles = new ArrayList<>();
    private List<AssLine> lines = new ArrayList<>();
    private AssInfos infos = new AssInfos();

    public Ass() {
        
    }

    public List<String> getNames() {
        return names;
    }

    public void setNames(List<String> names) {
        this.names = names;
    }

    public List<AssStyle> getStyles() {
        return styles;
    }

    public void setStyles(List<AssStyle> styles) {
        this.styles = styles;
    }

    public List<AssLine> getLines() {
        return lines;
    }

    public void setLines(List<AssLine> lines) {
        this.lines = lines;
    }

    public AssInfos getInfos() {
        return infos;
    }

    public void setInfos(AssInfos infos) {
        this.infos = infos;
    }
    
    private void nameExtractor(){
        names.clear();
        for (AssLine line : lines){
            String name = line.getName();
            if(name != null && name.isEmpty() == false){
                names.add(name);
            }
        }
    }
    
    private boolean isValidForResearchLine(String line){
        return !(line.startsWith("[") | line.startsWith("Format")
                | line.startsWith(";") | line.isEmpty());
    }
    
    public static Ass read(String filepath){
        File file = new File(filepath);
        AssScriptPart content = AssScriptPart.Infos;
        
        if(file.exists()){
            Ass ass = new Ass();
            
            Charset cs = Charset.forName(ass.detectCharset(filepath));
            
            try(
                    InputStream is = new FileInputStream(file);
                    InputStreamReader input = new InputStreamReader(is, cs);
                    BufferedReader br = new BufferedReader(input);                   
                    ){
                
                String newline;
                
                while((newline = br.readLine()) != null){
                    content = AssScriptPart.identify(newline, content);
                    
                    if(ass.isValidForResearchLine(newline) == true){
                        switch(content){
                            case Infos: 
                                ass.infos.add(newline); 
                                break;
                            case Styles:
                                AssStyle as = AssStyle.create(newline);
                                if(as != null){
                                    ass.styles.add(as);
                                }                                
                                break;
                            case Events:
                                AssLine al = AssLine.create(newline);
                                if(al != null){
                                    ass.lines.add(al);
                                }                                
                                break;
                            default:
                                break;
                        }
                    }
                }
                
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Ass.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Ass.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            ass.nameExtractor();
            
            return ass;
        }
        return null;
    }
    
    public static void write(String filepath, Ass ass){
        Charset cs = Charset.forName("UTF-8");
        
        try(
                FileOutputStream fos = new FileOutputStream(filepath);
                OutputStreamWriter output = new OutputStreamWriter(fos, cs);
                PrintWriter pw = new PrintWriter(output);
                ){
            
            pw.println(AssScriptPart.Infos.getPart());
            pw.println("; Made by CaramelMilkshake 2.3");
            pw.println("Title: " + ass.infos.getElement(AssInfos.AssInfosType.title));
            pw.println("ScriptType: " + ass.infos.getElement(AssInfos.AssInfosType.scripttype));
            pw.println("WrapStyle: " + ass.infos.getElement(AssInfos.AssInfosType.wrapstyle));
            pw.println("");
            
            pw.println(AssScriptPart.Styles.getPart());
            pw.println("Format: Name, Fontname, Fontsize, PrimaryColour, "
                    + "SecondaryColour, OutlineColour, BackColour, Bold, "
                    + "Italic, Underline, StrikeOut, ScaleX, ScaleY, Spacing, "
                    + "Angle, BorderStyle, Outline, Shadow, Alignment, "
                    + "MarginL, MarginR, MarginV, Encoding");
            for(AssStyle style : ass.styles){
                pw.println(style.toAssStyleString());
            }
            pw.println("");
            
            pw.println(AssScriptPart.Events.getPart());
            pw.println("Format: Layer, Start, End, Style, Name, "
                    + "MarginL, MarginR, MarginV, Effect, Text");
            for(AssLine line : ass.lines){
                pw.println(line.toAssLine());
            }
            pw.println("");
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Ass.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Ass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Detection of charset ">
    
    // Byte Order mark :
    // Bytes                    Encoding Form
    // 00 00 FE FF              UTF-32, big-endian
    // FF FE 00 00              UTF-32, little-endian
    // FE FF                    UTF-16, big-endian
    // FF FE                    UTF-16, little-endian
    // EF BB BF                 UTF-8
    
    // ÿ is \u00FF and þ is \u00FE
    
    /** <p>Try to get a correct charset<br />
     * Essaie d'obtenir le bon encodage des caractères.</p> */
    public String detectCharset(String pathname){
        String charset = "";
        
        try (FileReader fr = new FileReader(pathname)){
            // Try to get a correct charset
            // Open the file using system default encoding
            // Try to get a correct charset
            charset = detectCharset(fr);
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        
        return charset;
    }
    
    /** <p>Try to get a correct charset<br />
     * Essaie d'obtenir le bon encodage des caractères.</p>
     * <table><tr><td colspan="2">Byte Order mark :</td><td></td></tr>
     * <tr><td width="100">Bytes</td><td>Encoding Form</td></tr>
     * <tr><td>00 00 FE FF</td><td>UTF-32, big-endian</td></tr>
     * <tr><td>FF FE 00 00</td><td>UTF-32, little-endian</td></tr>
     * <tr><td>FE FF</td><td>UTF-16, big-endian</td></tr>
     * <tr><td>FF FE</td><td>UTF-16, little-endian</td></tr>
     * <tr><td>EF BB BF</td><td>UTF-8</td></tr></table> */
    public String detectCharset(FileReader fr){
        String charset = ""; String newline = "";
        
        // Create a buffer to read the stream
        try(BufferedReader br = new BufferedReader(fr);) {            
            // Scan for encoding marks
            while ((newline = br.readLine()) != null) {
                
                if(newline.startsWith("[\u0000\u0000") |
                        newline.startsWith("\u00FF\u00FE\u0000\u0000")){
                    charset = "UTF-32LE";
                }else if(newline.startsWith("\u0000\u0000[") |
                        newline.startsWith("\u0000\u0000\u00FE\u00FF")){
                    charset = "UTF-32BE";
                }else if(newline.startsWith("[\u0000") |
                        newline.startsWith("\u00FF\u00FE")){
                    charset = "UTF-16LE";
                }else if(newline.startsWith("\u0000[") |
                        newline.startsWith("\u00FE\u00FF")){
                    charset = "UTF-16BE";
                }else if(newline.startsWith("\u00EF\u00BB\u00BF")){
                    charset = "UTF-8";
                }
                
                // If a charset was found then close the stream
                // and the return charset encoding.
                if (charset.length()!=0){
                    break;
                }
            }
            
            // If nothing was found then set the encoding to system default.
            if (charset.length()==0){
                charset = fr.getEncoding();
            }
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        
        return charset;
    }
    
    // </editor-fold>
}
