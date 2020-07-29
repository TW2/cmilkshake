/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmilkshake.ass;

/**
 *
 * @author util2
 */
public enum AssScriptPart {
    Infos("[Script Info]"),
    Styles("[V4+ Styles]"),
    Events("[Events]");
    
    String part;
    
    private AssScriptPart(String part){
        this.part = part;
    }

    public String getPart() {
        return part;
    }
    
    public static AssScriptPart identify(String line, AssScriptPart last){
        AssScriptPart asp = last;
        
        for(AssScriptPart sp : values()){
            if(line.startsWith(sp.getPart())){
                asp = sp;
                break;
            }
        }
        
        return asp;
    }
}
