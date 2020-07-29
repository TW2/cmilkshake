/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.cmilkshake.compare;

import java.util.ArrayList;
import java.util.List;
import org.wingate.cmilkshake.ass.AssLine;

/**
 *
 * @author util2
 */
public class CompareTimeReport {
    //======================================================================
    // Définition de nos filtres
    ////--------------------------------------------------------------------
    private List<AssLine> sameTimeLines = new ArrayList<>();
    private List<AssLine> shiftTimeLines = new ArrayList<>();
    private List<AssLine> enlargedTimeLines = new ArrayList<>();
    private List<AssLine> reducedTimeLines = new ArrayList<>();
    
    private List<AssLine> sameStartTimeLines = new ArrayList<>();
    private List<AssLine> sameEndTimeLines = new ArrayList<>();
    //======================================================================

    public CompareTimeReport() {
    }

    public List<AssLine> getSameTimeLines() {
        return sameTimeLines;
    }

    public void setSameTimeLines(List<AssLine> sameTimeLines) {
        this.sameTimeLines = sameTimeLines;
    }

    public List<AssLine> getShiftTimeLines() {
        return shiftTimeLines;
    }

    public void setShiftTimeLines(List<AssLine> shiftTimeLines) {
        this.shiftTimeLines = shiftTimeLines;
    }

    public List<AssLine> getEnlargedTimeLines() {
        return enlargedTimeLines;
    }

    public void setEnlargedTimeLines(List<AssLine> enlargedTimeLines) {
        this.enlargedTimeLines = enlargedTimeLines;
    }

    public List<AssLine> getReducedTimeLines() {
        return reducedTimeLines;
    }

    public void setReducedTimeLines(List<AssLine> reducedTimeLines) {
        this.reducedTimeLines = reducedTimeLines;
    }

    public List<AssLine> getSameStartTimeLines() {
        return sameStartTimeLines;
    }

    public void setSameStartTimeLines(List<AssLine> sameStartTimeLines) {
        this.sameStartTimeLines = sameStartTimeLines;
    }

    public List<AssLine> getSameEndTimeLines() {
        return sameEndTimeLines;
    }

    public void setSameEndTimeLines(List<AssLine> sameEndTimeLines) {
        this.sameEndTimeLines = sameEndTimeLines;
    }
    
}
