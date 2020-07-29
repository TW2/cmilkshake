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
public class CompareTextReport {
    //======================================================================
    // Définition de nos filtres
    ////--------------------------------------------------------------------
    // Pour les deux parties :
    private List<AssLine> sameLines = new ArrayList<>();
    // Partie A :
    private List<AssLine> deletedLines = new ArrayList<>();
    // Partie B :
    private List<AssLine> addedLines = new ArrayList<>();
    //======================================================================

    public CompareTextReport() {
    }

    public List<AssLine> getSameLines() {
        return sameLines;
    }

    public void setSameLines(List<AssLine> sameLines) {
        this.sameLines = sameLines;
    }

    public List<AssLine> getDeletedLines() {
        return deletedLines;
    }

    public void setDeletedLines(List<AssLine> deletedLines) {
        this.deletedLines = deletedLines;
    }

    public List<AssLine> getAddedLines() {
        return addedLines;
    }

    public void setAddedLines(List<AssLine> addedLines) {
        this.addedLines = addedLines;
    }
    
    
}
