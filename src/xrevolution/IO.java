package xrevolution;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import xrevolution.Infos.AssInfosType;
import xrevolution.Style.AssStyleType;

/** 
 * This class is a module for input/output functions.
 * <p><b>EN : </b>This class is a part of XRevolution, a test project.<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * It was added in Milkshake because we have similar needs.<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * This class is a module for input/output functions.</p>
 * <p><b>FR : </b>Cette classe fait partie du projet de test XRevolution.<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * Elle a été ajouté dans Milkshake car on a des besoins similaires.<br />
 * &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
 * Elle est un module pour les fonctions d'entrée/sortie.</p>
 * @author The Wingate 2940
 */
public class IO {
    
    /** <p>Create a new IO.<br />
     * Crée un nouveau IO.</p> */
    public IO(){
        //Nothing
    }
    
    // <editor-fold defaultstate="collapsed" desc=" Ruby Editor ">
    /** <p>Open ruby file and return the contents.<br />
     * Ouvre un fichier ruby et retourne son contenu.</p> */
    public String openRubyFile(String path){
        String rubyCode = "";
        File file = new File(path);
        try{
            FileInputStream fis = new FileInputStream(file);
            java.io.BufferedReader br = new java.io.BufferedReader(
                    new java.io.InputStreamReader(fis, "UTF-8"));
            String newline;
            while((newline=br.readLine())!=null){
                rubyCode += newline+"\n";
            }
            br.close(); fis.close();
            return rubyCode;
        }catch (Exception exc){
            return "";
        }        
    }

    /** <p>Save a ruby file with the given contents (text).<br />
     * Sauvegarde un fichier ruby avec le contenu donné (text).</p> */
    public void saveRubyFile(String path, String text){
        File file = new File(path);
        try{
            text = text + "\n"; //Error treatment (see pattern)
            FileOutputStream fos = new FileOutputStream(file);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
            PrintWriter pw = new PrintWriter(bw);
            Pattern p = Pattern.compile("([^\n]*)\n");
            Matcher m = p.matcher(text);
            while(m.find()){
                pw.print(m.group());
                pw.flush();
            }
            pw.close(); bw.close(); fos.close();
        }catch(Exception exc){
            
        }
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" EntreeSortie Fichier SSA ">
    
    /* Ces méthodes permettent de créer et d'écrire dans un fichier de
     * sous-titres Sub Station Alpha (*.ssa). On écrit les éléments de:
     * ssaTime > Time
     * ssaKara > Karaoke
     * On se doit de faire des fichiers compatibles à Sub Station Alpha
     * n'utilisant pas Unicode ainsi que des fichiers non standard en Unicode
     * pour les utiliser dans les logiciels d'encodage afin
     * de supporter le japonais, le chinois, le coréen, le russe,...
     */
    
    /** <p>Read a SSA file and put it in a JTable with 9 columns.<br />
     * Lit des fichiers Sub Station Alpha et de les mettre dans un JTable de 9 colonnes.</p>
     * @param String <b color="blue">pathFile</b> - Contient le nom de fichier.
     * @param DefaultTableModel <b color="blue">t</b> - R&#233;f&#232;re au model du JTable employ&#233;. */
    public void LireFichierSSAi2(String pathFile, List<Subtitle> sublist,
            Infos ai, Map<String, Style> asc, List<String> anc,
            int width, int height){
        
        //On cr?e une cha?ne ? retourner
        String newline = "";
        //ProgramLine pl = new ProgramLine();
        
        try{
            
            // Encoding detection
            FileReader fr = new FileReader(pathFile);
            String charset = detectCharset(fr);
            fr.close();

            // Start process to read file...
            FileInputStream fis = new FileInputStream(pathFile);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(fis, charset));
        
        //On lit le fichier
        while((newline=br.readLine())!=null){
            
            try{
                
                /* Traitement de l'ent�te */
                
                if(newline.startsWith("Title") && !newline.substring(7).isEmpty()){
                    ai.setElement(AssInfosType.title, newline.substring(7));
                }
                
                if(newline.startsWith("Original Script") && !newline.substring(17).isEmpty()){
                    ai.setElement(AssInfosType.authors, newline.substring(17));
                }
                
                if(newline.startsWith("Original Translation") && !newline.substring(22).isEmpty()){
                    ai.setElement(AssInfosType.translators, newline.substring(22));
                }
                
                if(newline.startsWith("Original Editing") && !newline.substring(18).isEmpty()){
                    ai.setElement(AssInfosType.editors, newline.substring(18));
                }
                
                if(newline.startsWith("Original Timing") && !newline.substring(17).isEmpty()){
                    ai.setElement(AssInfosType.timers, newline.substring(17));
                }
                
                if(newline.startsWith("Original Script Checking") && !newline.substring(26).isEmpty()){
                    ai.setElement(AssInfosType.checkers, newline.substring(26));
                }
                
                if(newline.startsWith("Synch Point") && !newline.substring(13).isEmpty()){
                    ai.setElement(AssInfosType.synchpoint, newline.substring(13));
                }
                
                if(newline.startsWith("Script Updated By") && !newline.substring(19).isEmpty()){
                    ai.setElement(AssInfosType.updateby, newline.substring(19));
                }
                
                if(newline.startsWith("Update Details") && !newline.substring(16).isEmpty()){
                    ai.setElement(AssInfosType.updates, newline.substring(16));
                }
                
                if(newline.startsWith("ScriptType") && !newline.substring(12).isEmpty()){
                    ai.setElement(AssInfosType.scripttype, newline.substring(12));
                }
                
                if(newline.startsWith("Collisions") && !newline.substring(12).isEmpty()){
                    ai.setElement(AssInfosType.collisions, newline.substring(12));
                }
                
                if(newline.startsWith("PlayResX") && !newline.substring(10).isEmpty()){
                    ai.setElement(AssInfosType.playresx, newline.substring(10));
                }
                
                if(newline.startsWith("PlayResY") && !newline.substring(10).isEmpty()){
                    ai.setElement(AssInfosType.playresy, newline.substring(10));
                }
                
                if(newline.startsWith("PlayDepth") && !newline.substring(11).isEmpty()){
                    ai.setElement(AssInfosType.playdepth, newline.substring(11));
                }
                
                if(newline.startsWith("Timer") && !newline.substring(7).isEmpty()){
                    ai.setElement(AssInfosType.timerspeed, newline.substring(7));
                }
                
                if(newline.startsWith("Audio") && !newline.substring(7).isEmpty()){
                    ai.setElement(AssInfosType.audios, newline.substring(7));
                }
                
                if(newline.startsWith("Video") && !newline.substring(7).isEmpty()){
                    ai.setElement(AssInfosType.video, newline.substring(7));
                }
                
                /* Traitement des styles */
                
                if(newline.startsWith("Style:")){
                    String[] listStyle = newline.substring(7).split(",");
                    Style as = new Style();
                    as.setElement(AssStyleType.name, listStyle[0]);
                    as.setElement(AssStyleType.fontname, listStyle[1]);
                    as.setElement(AssStyleType.fontsize, listStyle[2]);
                    as.setElement(AssStyleType.primarycolour, listStyle[3],"");
                    as.setElement(AssStyleType.secondarycolour, listStyle[4],"");
                    as.setElement(AssStyleType.outlinecolour, listStyle[5],"");
                    as.setElement(AssStyleType.backcolour, listStyle[6],"");
                    as.setElement(AssStyleType.bold, listStyle[7]);
                    as.setElement(AssStyleType.italic, listStyle[8]);
                    as.setElement(AssStyleType.borderstyle, listStyle[9]);                    
                    as.setElement(AssStyleType.outline, listStyle[10]);
                    as.setElement(AssStyleType.shadow, listStyle[11]);
                    as.setElement(AssStyleType.alignment, listStyle[12],"");
                    as.setElement(AssStyleType.marginL, listStyle[13]);
                    as.setElement(AssStyleType.marginR, listStyle[14]);
                    as.setElement(AssStyleType.marginV, listStyle[15]);
                    as.setElement(AssStyleType.alphalevel, listStyle[16]);
                    as.setElement(AssStyleType.encoding, listStyle[17]);
                    asc.put(listStyle[0], as);
                }
                
                /* Traitement des �venements */
                
                if(newline.startsWith("Dialogue:") | 
                        newline.startsWith("Comment:") |
                        newline.startsWith("Picture:") |
                        newline.startsWith("Sound:") |
                        newline.startsWith("Movie:") |
                        newline.startsWith("Command:")){
                    Subtitle sub = new Subtitle(newline);
                    Style style = asc.get(sub.getStyle());
                    Position pos = new Position(width, height, style, sub, ai);
                    List<Subtitle> sublist2 = pos.getSubtitleList();
                    for(Subtitle sub2 : sublist2){
                        sublist.add(sub2);
                    }
                }
                
            }catch(IndexOutOfBoundsException ioobe){
                //erreurs = ioobe.getMessage();
            }
            
        }
                
        //On ferme le flux puis le fichier
        br.close();
        fis.close();
       
        } catch (java.io.FileNotFoundException e2){
            System.out.println("Fichier non trouv? - Missing File Error");
        } catch (SecurityException se){
            System.out.println("Acc?s refus? - Access denied");
        } catch (java.io.UnsupportedEncodingException uee){
            System.out.println("Encodage non support? - Unsupported encoding");
        } catch (java.io.IOException e1){
            System.out.println("Erreur lors de la lecture - Read Error");
        }
        
    }
    
    /** <p>Read a SSA file and put it in a JTable with 9 columns.<br />
     * Lit des fichiers Sub Station Alpha et de les mettre dans un JTable de 9 colonnes.</p>
     * @param String <b color="blue">pathFile</b> - Contient le nom de fichier.
     * @param DefaultTableModel <b color="blue">t</b> - R&#233;f&#232;re au model du JTable employ&#233;. */
    public void LireFichierSSAi2_Minimal(String pathFile, List<Subtitle> sublist){
        
        //On cr?e une cha?ne ? retourner
        String newline = "";
        //ProgramLine pl = new ProgramLine();
        
        try{
            
            // Encoding detection
            FileReader fr = new FileReader(pathFile);
            String charset = detectCharset(fr);
            fr.close();

            // Start process to read file...
            FileInputStream fis = new FileInputStream(pathFile);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(fis, charset));
        
        //On lit le fichier
        while((newline=br.readLine())!=null){
            
            try{
                
                /* Traitement des �venements */
                
                if(newline.startsWith("Dialogue:") | 
                        newline.startsWith("Comment:") |
                        newline.startsWith("Picture:") |
                        newline.startsWith("Sound:") |
                        newline.startsWith("Movie:") |
                        newline.startsWith("Command:")){
                    Subtitle sub = new Subtitle(newline);
                    sublist.add(sub);
                }
                
            }catch(IndexOutOfBoundsException ioobe){
                //erreurs = ioobe.getMessage();
            }
            
        }
                
        //On ferme le flux puis le fichier
        br.close();
        fis.close();
       
        } catch (java.io.FileNotFoundException e2){
            System.out.println("Fichier non trouv? - Missing File Error");
        } catch (SecurityException se){
            System.out.println("Acc?s refus? - Access denied");
        } catch (java.io.UnsupportedEncodingException uee){
            System.out.println("Encodage non support? - Unsupported encoding");
        } catch (java.io.IOException e1){
            System.out.println("Erreur lors de la lecture - Read Error");
        }
        
    }

    /** <p>Extract styles from SSA files.<br />
     * Extrait les styles d'un fichier SSA.</p> */
    public List<Style> ExtractSSAStyles(String pathFile){

        //On cr?e une cha?ne ? retourner
        String newline = "";
        List<Style> las = new ArrayList<Style>();

        try{

            // Encoding detection
            FileReader fr = new FileReader(pathFile);
            String charset = detectCharset(fr);
            fr.close();

            // Start process to read file...
            FileInputStream fis = new FileInputStream(pathFile);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(fis, charset));

        //On lit le fichier
        while((newline=br.readLine())!=null){
            try{
                if(newline.startsWith("Style:")){
                    String[] listStyle = newline.substring(7).split(",");
                    Style as = new Style();
                    as.setElement(AssStyleType.name, listStyle[0]);
                    as.setElement(AssStyleType.fontname, listStyle[1]);
                    as.setElement(AssStyleType.fontsize, listStyle[2]);
                    as.setElement(AssStyleType.primarycolour, listStyle[3],"");
                    as.setElement(AssStyleType.secondarycolour, listStyle[4],"");
                    as.setElement(AssStyleType.outlinecolour, listStyle[5],"");
                    as.setElement(AssStyleType.backcolour, listStyle[6],"");
                    as.setElement(AssStyleType.bold, listStyle[7]);
                    as.setElement(AssStyleType.italic, listStyle[8]);
                    as.setElement(AssStyleType.borderstyle, listStyle[9]);
                    as.setElement(AssStyleType.outline, listStyle[10]);
                    as.setElement(AssStyleType.shadow, listStyle[11]);
                    as.setElement(AssStyleType.alignment, listStyle[12],"");
                    as.setElement(AssStyleType.marginL, listStyle[13]);
                    as.setElement(AssStyleType.marginR, listStyle[14]);
                    as.setElement(AssStyleType.marginV, listStyle[15]);
                    as.setElement(AssStyleType.alphalevel, listStyle[16]);
                    as.setElement(AssStyleType.encoding, listStyle[17]);
                    las.add(as);
                }
            }catch(IndexOutOfBoundsException ioobe){
                //erreurs = ioobe.getMessage();
            }
        }

        //On ferme le flux puis le fichier
        br.close();
        fis.close();

        } catch (java.io.FileNotFoundException e2){
            System.out.println("Fichier non trouv? - Missing File Error");
        } catch (SecurityException se){
            System.out.println("Acc?s refus? - Access denied");
        } catch (java.io.UnsupportedEncodingException uee){
            System.out.println("Encodage non support? - Unsupported encoding");
        } catch (java.io.IOException e1){
            System.out.println("Erreur lors de la lecture - Read Error");
        }

        return las;
    }
            
    // </editor-fold>
 
    // <editor-fold defaultstate="collapsed" desc=" EntreeSortie Fichier ASS ">
    
    /** <p>Read an ASS file and fill in a table.<br />
     * Lit un fichier ASS et replit une table.</p>
     * @param pathFile The name of the file using its absolute path.
     * @param t - The model of the table to fill in. */
    public void LireFichierASSi2(String pathFile, List<Subtitle> sublist,
            Infos ai, Map<String, Style> asc, List<String> anc,
            int width, int height){
        
        //On cr?e une cha?ne ? retourner
        String newline = "";
        
        try{
            // Encoding detection
            FileReader fr = new FileReader(pathFile);
            String charset = detectCharset(fr);
            fr.close();

            // Start process to read file...
            FileInputStream fis = new FileInputStream(pathFile);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(fis, charset));
        
        //On lit le fichier
        while((newline=br.readLine())!=null){
            
            try{
                
                /* Traitement de l'ent�te */
                
                if(newline.startsWith("Title") && !newline.substring(7).isEmpty()){
                    ai.setElement(AssInfosType.title, newline.substring(7));
                }
                
                if(newline.startsWith("Original Script") && !newline.substring(17).isEmpty()){
                    ai.setElement(AssInfosType.authors, newline.substring(17));
                }
                
                if(newline.startsWith("Original Translation") && !newline.substring(22).isEmpty()){
                    ai.setElement(AssInfosType.translators, newline.substring(22));
                }
                
                if(newline.startsWith("Original Editing") && !newline.substring(18).isEmpty()){
                    ai.setElement(AssInfosType.editors, newline.substring(18));
                }
                
                if(newline.startsWith("Original Timing") && !newline.substring(17).isEmpty()){
                    ai.setElement(AssInfosType.timers, newline.substring(17));
                }
                
                if(newline.startsWith("Original Script Checking") && !newline.substring(26).isEmpty()){
                    ai.setElement(AssInfosType.checkers, newline.substring(26));
                }
                
                if(newline.startsWith("Synch Point") && !newline.substring(13).isEmpty()){
                    ai.setElement(AssInfosType.synchpoint, newline.substring(13));
                }
                
                if(newline.startsWith("Script Updated By") && !newline.substring(19).isEmpty()){
                    ai.setElement(AssInfosType.updateby, newline.substring(19));
                }
                
                if(newline.startsWith("Update Details") && !newline.substring(16).isEmpty()){
                    ai.setElement(AssInfosType.updates, newline.substring(16));
                }
                
                if(newline.startsWith("ScriptType") && !newline.substring(12).isEmpty()){
                    ai.setElement(AssInfosType.scripttype, newline.substring(12));
                }
                
                if(newline.startsWith("Collisions") && !newline.substring(12).isEmpty()){
                    ai.setElement(AssInfosType.collisions, newline.substring(12));
                }
                
                if(newline.startsWith("PlayResX") && !newline.substring(10).isEmpty()){
                    ai.setElement(AssInfosType.playresx, newline.substring(10));
                }
                
                if(newline.startsWith("PlayResY") && !newline.substring(10).isEmpty()){
                    ai.setElement(AssInfosType.playresy, newline.substring(10));
                }
                
                if(newline.startsWith("PlayDepth") && !newline.substring(11).isEmpty()){
                    ai.setElement(AssInfosType.playdepth, newline.substring(11));
                }
                
                if(newline.startsWith("Timer") && !newline.substring(7).isEmpty()){
                    ai.setElement(AssInfosType.timerspeed, newline.substring(7));
                }
                
                if(newline.startsWith("Audio") && !newline.substring(7).isEmpty()){
                    ai.setElement(AssInfosType.audios, newline.substring(7));
                }
                
                if(newline.startsWith("Video") && !newline.substring(7).isEmpty()){
                    ai.setElement(AssInfosType.video, newline.substring(7));
                }
                
                /* Traitement des styles */
                
                if(newline.startsWith("Style:") &&
                        ai.getElement(AssInfosType.scripttype).equalsIgnoreCase("V4.00+")){
                    String[] listStyle = newline.substring(7).split(",");
                    Style as = new Style();
                    as.setElement(AssStyleType.name, listStyle[0]);
                    as.setElement(AssStyleType.fontname, listStyle[1]);
                    as.setElement(AssStyleType.fontsize, listStyle[2]);
                    as.setElement(AssStyleType.primarycolour, listStyle[3],"+");
                    as.setElement(AssStyleType.secondarycolour, listStyle[4],"+");
                    as.setElement(AssStyleType.outlinecolour, listStyle[5],"+");
                    as.setElement(AssStyleType.backcolour, listStyle[6],"+");
                    as.setElement(AssStyleType.bold, listStyle[7]);
                    as.setElement(AssStyleType.italic, listStyle[8]);
                    as.setElement(AssStyleType.underline, listStyle[9]);
                    as.setElement(AssStyleType.strikeout, listStyle[10]);
                    as.setElement(AssStyleType.scaleX, listStyle[11]);
                    as.setElement(AssStyleType.scaleY, listStyle[12]);
                    as.setElement(AssStyleType.spacing, listStyle[13]);
                    as.setElement(AssStyleType.angle, listStyle[14]);
                    as.setElement(AssStyleType.borderstyle, listStyle[15]);                    
                    as.setElement(AssStyleType.outline, listStyle[16]);
                    as.setElement(AssStyleType.shadow, listStyle[17]);
                    as.setElement(AssStyleType.alignment, listStyle[18],"+");
                    as.setElement(AssStyleType.marginL, listStyle[19]);
                    as.setElement(AssStyleType.marginR, listStyle[20]);
                    as.setElement(AssStyleType.marginV, listStyle[21]);
                    as.setElement(AssStyleType.encoding, listStyle[22]);
                    asc.put(listStyle[0], as);
                }else if(newline.startsWith("Style:") &&
                        ai.getElement(AssInfosType.scripttype).equalsIgnoreCase("V4.00++")){
                    String[] listStyle = newline.substring(7).split(",");
                    Style as = new Style();
                    as.setElement(AssStyleType.name, listStyle[0]);
                    as.setElement(AssStyleType.fontname, listStyle[1]);
                    as.setElement(AssStyleType.fontsize, listStyle[2]);
                    as.setElement(AssStyleType.primarycolour, listStyle[3],"+");
                    as.setElement(AssStyleType.secondarycolour, listStyle[4],"+");
                    as.setElement(AssStyleType.outlinecolour, listStyle[5],"+");
                    as.setElement(AssStyleType.backcolour, listStyle[6],"+");
                    as.setElement(AssStyleType.bold, listStyle[7]);
                    as.setElement(AssStyleType.italic, listStyle[8]);
                    as.setElement(AssStyleType.underline, listStyle[9]);
                    as.setElement(AssStyleType.strikeout, listStyle[10]);
                    as.setElement(AssStyleType.scaleX, listStyle[11]);
                    as.setElement(AssStyleType.scaleY, listStyle[12]);
                    as.setElement(AssStyleType.spacing, listStyle[13]);
                    as.setElement(AssStyleType.angle, listStyle[14]);
                    as.setElement(AssStyleType.borderstyle, listStyle[15]);                    
                    as.setElement(AssStyleType.outline, listStyle[16]);
                    as.setElement(AssStyleType.shadow, listStyle[17]);
                    as.setElement(AssStyleType.alignment, listStyle[18],"+");
                    as.setElement(AssStyleType.marginL, listStyle[19]);
                    as.setElement(AssStyleType.marginR, listStyle[20]);
                    as.setElement(AssStyleType.marginT, listStyle[21]);
                    as.setElement(AssStyleType.marginB, listStyle[22]);
                    as.setElement(AssStyleType.encoding, listStyle[23]);
                    as.setElement(AssStyleType.relativeto, listStyle[24]);
                    asc.put(listStyle[0], as);
                }
                
                /* Traitement des �venements */
                
                if(newline.startsWith("Dialogue:") | 
                        newline.startsWith("Comment:") |
                        newline.startsWith("Picture:") |
                        newline.startsWith("Sound:") |
                        newline.startsWith("Movie:") |
                        newline.startsWith("Command:")){
                    Subtitle sub = new Subtitle(newline);
                    Style style = asc.get(sub.getStyle());
                    Position pos = new Position(width, height, style, sub, ai);
                    List<Subtitle> sublist2 = pos.getSubtitleList();
                    for(Subtitle sub2 : sublist2){
                        sublist.add(sub2);
                    }
                }
                
            }catch(IndexOutOfBoundsException ioobe){
                //erreurs = ioobe.getMessage();
            }
            
        }
                
        //On ferme le flux puis le fichier
        br.close();
        fis.close();        
       
        } catch (java.io.FileNotFoundException e2){
            System.out.println("Fichier non trouv? - Missing File Error");
        } catch (SecurityException se){
            System.out.println("Acc?s refus? - Access denied");
        } catch (java.io.UnsupportedEncodingException uee){
            System.out.println("Encodage non support? - Unsupported encoding");
        } catch (java.io.IOException e1){
            System.out.println("Erreur lors de la lecture - Read Error");
        }
        
    }
    
    /** <p>Read an ASS file and fill in a table.<br />
     * Lit un fichier ASS et replit une table.</p>
     * @param pathFile The name of the file using its absolute path.
     * @param t - The model of the table to fill in. */
    public void LireFichierASSi2_Minimal(String pathFile, List<Subtitle> sublist){
        
        //On cr?e une cha?ne ? retourner
        String newline = "";
        
        try{
            // Encoding detection
            FileReader fr = new FileReader(pathFile);
            String charset = detectCharset(fr);
            fr.close();

            // Start process to read file...
            FileInputStream fis = new FileInputStream(pathFile);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(fis, charset));
        
        //On lit le fichier
        while((newline=br.readLine())!=null){
            
            try{
                
                /* Traitement des �venements */
                
                if(newline.startsWith("Dialogue:") | 
                        newline.startsWith("Comment:") |
                        newline.startsWith("Picture:") |
                        newline.startsWith("Sound:") |
                        newline.startsWith("Movie:") |
                        newline.startsWith("Command:")){
                    Subtitle sub = new Subtitle(newline);
                    sublist.add(sub);
                }
                
            }catch(IndexOutOfBoundsException ioobe){
                //erreurs = ioobe.getMessage();
            }
            
        }
                
        //On ferme le flux puis le fichier
        br.close();
        fis.close();        
       
        } catch (java.io.FileNotFoundException e2){
            System.out.println("Fichier non trouv? - Missing File Error");
        } catch (SecurityException se){
            System.out.println("Acc?s refus? - Access denied");
        } catch (java.io.UnsupportedEncodingException uee){
            System.out.println("Encodage non support? - Unsupported encoding");
        } catch (java.io.IOException e1){
            System.out.println("Erreur lors de la lecture - Read Error");
        }
        
    }

    /** <p>Extract styles from an ASS file.<br />
     * Extrait les styles à partie d'un fichier ASS.</p> */
    public List<Style> ExtractASSStyles(String pathFile){

        //On cr?e une cha?ne ? retourner
        String newline = ""; String scriptType = "";
        List<Style> las = new ArrayList<Style>();

        try{
            // Encoding detection
            FileReader fr = new FileReader(pathFile);
            String charset = detectCharset(fr);
            fr.close();

            // Start process to read file...
            FileInputStream fis = new FileInputStream(pathFile);
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(fis, charset));

        //On lit le fichier
        while((newline=br.readLine())!=null){
            try{
                if(newline.startsWith("ScriptType") && !newline.substring(12).isEmpty()){
                    scriptType = newline.substring(12);
                }

                if(newline.startsWith("Style:") && scriptType.equalsIgnoreCase("V4.00+")){
                    String[] listStyle = newline.substring(7).split(",");
                    Style as = new Style();
                    as.setElement(AssStyleType.name, listStyle[0]);
                    as.setElement(AssStyleType.fontname, listStyle[1]);
                    as.setElement(AssStyleType.fontsize, listStyle[2]);
                    as.setElement(AssStyleType.primarycolour, listStyle[3],"+");
                    as.setElement(AssStyleType.secondarycolour, listStyle[4],"+");
                    as.setElement(AssStyleType.outlinecolour, listStyle[5],"+");
                    as.setElement(AssStyleType.backcolour, listStyle[6],"+");
                    as.setElement(AssStyleType.bold, listStyle[7]);
                    as.setElement(AssStyleType.italic, listStyle[8]);
                    as.setElement(AssStyleType.underline, listStyle[9]);
                    as.setElement(AssStyleType.strikeout, listStyle[10]);
                    as.setElement(AssStyleType.scaleX, listStyle[11]);
                    as.setElement(AssStyleType.scaleY, listStyle[12]);
                    as.setElement(AssStyleType.spacing, listStyle[13]);
                    as.setElement(AssStyleType.angle, listStyle[14]);
                    as.setElement(AssStyleType.borderstyle, listStyle[15]);
                    as.setElement(AssStyleType.outline, listStyle[16]);
                    as.setElement(AssStyleType.shadow, listStyle[17]);
                    as.setElement(AssStyleType.alignment, listStyle[18],"+");
                    as.setElement(AssStyleType.marginL, listStyle[19]);
                    as.setElement(AssStyleType.marginR, listStyle[20]);
                    as.setElement(AssStyleType.marginV, listStyle[21]);
                    as.setElement(AssStyleType.encoding, listStyle[22]);
                    las.add(as);
                }else if(newline.startsWith("Style:") && scriptType.equalsIgnoreCase("V4.00++")){
                    String[] listStyle = newline.substring(7).split(",");
                    Style as = new Style();
                    as.setElement(AssStyleType.name, listStyle[0]);
                    as.setElement(AssStyleType.fontname, listStyle[1]);
                    as.setElement(AssStyleType.fontsize, listStyle[2]);
                    as.setElement(AssStyleType.primarycolour, listStyle[3],"+");
                    as.setElement(AssStyleType.secondarycolour, listStyle[4],"+");
                    as.setElement(AssStyleType.outlinecolour, listStyle[5],"+");
                    as.setElement(AssStyleType.backcolour, listStyle[6],"+");
                    as.setElement(AssStyleType.bold, listStyle[7]);
                    as.setElement(AssStyleType.italic, listStyle[8]);
                    as.setElement(AssStyleType.underline, listStyle[9]);
                    as.setElement(AssStyleType.strikeout, listStyle[10]);
                    as.setElement(AssStyleType.scaleX, listStyle[11]);
                    as.setElement(AssStyleType.scaleY, listStyle[12]);
                    as.setElement(AssStyleType.spacing, listStyle[13]);
                    as.setElement(AssStyleType.angle, listStyle[14]);
                    as.setElement(AssStyleType.borderstyle, listStyle[15]);
                    as.setElement(AssStyleType.outline, listStyle[16]);
                    as.setElement(AssStyleType.shadow, listStyle[17]);
                    as.setElement(AssStyleType.alignment, listStyle[18],"+");
                    as.setElement(AssStyleType.marginL, listStyle[19]);
                    as.setElement(AssStyleType.marginR, listStyle[20]);
                    as.setElement(AssStyleType.marginT, listStyle[21]);
                    as.setElement(AssStyleType.marginB, listStyle[22]);
                    as.setElement(AssStyleType.encoding, listStyle[23]);
                    as.setElement(AssStyleType.relativeto, listStyle[24]);
                    las.add(as);
                }
            }catch(IndexOutOfBoundsException ioobe){
                //erreurs = ioobe.getMessage();
            }
        }

        //On ferme le flux puis le fichier
        br.close();
        fis.close();

        } catch (java.io.FileNotFoundException e2){
            System.out.println("Fichier non trouv? - Missing File Error");
        } catch (SecurityException se){
            System.out.println("Acc?s refus? - Access denied");
        } catch (java.io.UnsupportedEncodingException uee){
            System.out.println("Encodage non support? - Unsupported encoding");
        } catch (java.io.IOException e1){
            System.out.println("Erreur lors de la lecture - Read Error");
        }
        return las;
    }
    
    // </editor-fold>
    
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
        
        try {
            // Open the file using system default encoding
            FileReader fr = new FileReader(pathname);
            
            // Try to get a correct charset
            charset = detectCharset(fr);
            
            // Close the file
            fr.close();
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
        
        try {
            // Create a buffer to read the stream
            BufferedReader br = new BufferedReader(fr);
            
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
                    br.close();
                    return charset;
                }
            }
            
            // If nothing was found then set the encoding to system default.
            if (charset.length()==0){
                charset = fr.getEncoding();
            }
        
            // Close the stream
            br.close();
            
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
        }
        
        return charset;
    }
    
    // </editor-fold>
    
    
}