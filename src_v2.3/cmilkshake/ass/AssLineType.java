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
public enum AssLineType {
    Dialogue("Dialogue"),
    Comment("Comment");
    
    String type;
    
    private AssLineType(String type){
        this.type = type;
    }

    public String getType() {
        return type;
    }
    
    public static AssLineType identify(String line){
        return line.startsWith("Dialogue") ? Dialogue : Comment;
    }
}
