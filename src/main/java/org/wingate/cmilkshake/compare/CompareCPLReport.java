/*
 * Copyright (C) 2020 util2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.wingate.cmilkshake.compare;

import java.util.ArrayList;
import java.util.List;
import org.wingate.cmilkshake.ass.AssLine;

/**
 *
 * @author util2
 */
public class CompareCPLReport {
    //======================================================================
    // Définition de nos filtres
    ////--------------------------------------------------------------------
    // Pour les deux parties :
    private List<CplCounter> sameLines = new ArrayList<>();
    // Partie A :
    private List<CplCounter> oldLines = new ArrayList<>();
    // Partie B :
    private List<CplCounter> newLines = new ArrayList<>();
    //======================================================================

    public CompareCPLReport() {
    }

    public List<CplCounter> getSameLines() {
        return sameLines;
    }

    public void setSameLines(List<CplCounter> sameLines) {
        this.sameLines = sameLines;
    }

    public List<CplCounter> getOldLines() {
        return oldLines;
    }

    public void setOldLines(List<CplCounter> oldLines) {
        this.oldLines = oldLines;
    }

    public List<CplCounter> getNewLines() {
        return newLines;
    }

    public void setNewLines(List<CplCounter> newLines) {
        this.newLines = newLines;
    }
    
    public static class CplCounter{
        private int cpl;
        private AssLine assLine;

        public CplCounter(int cpl, AssLine assLine) {
            this.cpl = cpl;
            this.assLine = assLine;
        }

        public int getCpl() {
            return cpl;
        }

        public void setCpl(int cpl) {
            this.cpl = cpl;
        }

        public AssLine getAssLine() {
            return assLine;
        }

        public void setAssLine(AssLine assLine) {
            this.assLine = assLine;
        }
        
    }
}
