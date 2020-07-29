/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.cmilkshake.table;

import org.wingate.cmilkshake.util.DrawColor;
import java.awt.Color;

/**
 *
 * @author util2
 */
public enum CompareColor {
    None("Normal", Color.white),
    OldLine("Old line", DrawColor.crimson.getColor()),
    NewLine("New line", DrawColor.chartreuse.getColor()),
    OldStyle("Old style", DrawColor.crimson.getColor()),
    NewStyle("New style", DrawColor.chartreuse.getColor()),
    OldName("Old name", DrawColor.crimson.getColor()),
    NewName("New name", DrawColor.chartreuse.getColor()),
    OldTime("Old time", DrawColor.crimson.getColor()),
    NewTime("New time", DrawColor.chartreuse.getColor()),
    ShiftTime("Shift time", DrawColor.light_cyan.getColor()),
    EnlargedTime("Enlarged time", DrawColor.corn_flower_blue.getColor()),
    ReducedTime("Reduced time", DrawColor.cadet_blue.getColor()),
    SameTime("Same synchro.", Color.pink);
    
    String status;
    Color color;
    
    private CompareColor(String status, Color color){
        this.status = status;
        this.color = color;
    }

    public String getStatus() {
        return status;
    }

    public Color getColor() {
        return color;
    }
    
}
