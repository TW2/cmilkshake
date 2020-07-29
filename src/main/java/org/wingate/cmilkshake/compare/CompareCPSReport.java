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
public class CompareCPSReport {
    //======================================================================
    // Définition de nos filtres
    ////--------------------------------------------------------------------
    // Pour les deux parties :
    private List<CpsCounter> sameLines = new ArrayList<>();
    // Partie A :
    private List<CpsCounter> oldLines = new ArrayList<>();
    // Partie B :
    private List<CpsCounter> newLines = new ArrayList<>();
    //======================================================================

    public CompareCPSReport() {
    }

    public List<CpsCounter> getSameLines() {
        return sameLines;
    }

    public void setSameLines(List<CpsCounter> sameLines) {
        this.sameLines = sameLines;
    }

    public List<CpsCounter> getOldLines() {
        return oldLines;
    }

    public void setOldLines(List<CpsCounter> oldLines) {
        this.oldLines = oldLines;
    }

    public List<CpsCounter> getNewLines() {
        return newLines;
    }

    public void setNewLines(List<CpsCounter> newLines) {
        this.newLines = newLines;
    }
    
    public static class CpsCounter{
        private double cps;
        private AssLine assLine;

        public CpsCounter(double cps, AssLine assLine) {
            this.cps = cps;
            this.assLine = assLine;
        }

        public double getCps() {
            return cps;
        }

        public void setCps(double cps) {
            this.cps = cps;
        }

        public AssLine getAssLine() {
            return assLine;
        }

        public void setAssLine(AssLine assLine) {
            this.assLine = assLine;
        }
        
    }
}
