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
package org.wingate.cmilkshake.util;

import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import javax.swing.JScrollBar;

/**
 *
 * @author util2
 */
public class ScrollBarsSynchronizer implements AdjustmentListener {

    private final JScrollBar sc1;
    private final JScrollBar sc2;
    
    private boolean link = false;
    private int oldValue1 = 0, oldValue2 = 0;
    private ScrollBarsSynchronizer scrollBarsSynchronizerForScrollBar1 = null;
    private ScrollBarsSynchronizer scrollBarsSynchronizerForScrollBar2 = null;

    public ScrollBarsSynchronizer(JScrollBar sc1, JScrollBar sc2) {
        this.sc1 = sc1;
        this.sc2 = sc2;
    }

    public void setLink(boolean link) {
        this.link = link;
    }

    public ScrollBarsSynchronizer getScrollBarsSynchronizerForScrollBar1() {
        return scrollBarsSynchronizerForScrollBar1;
    }

    public void setScrollBarsSynchronizerForScrollBar1(ScrollBarsSynchronizer scrollBarsSynchronizerForScrollBar1) {
        this.scrollBarsSynchronizerForScrollBar1 = scrollBarsSynchronizerForScrollBar1;
    }

    public ScrollBarsSynchronizer getScrollBarsSynchronizerForScrollBar2() {
        return scrollBarsSynchronizerForScrollBar2;
    }

    public void setScrollBarsSynchronizerForScrollBar2(ScrollBarsSynchronizer scrollBarsSynchronizerForScrollBar2) {
        this.scrollBarsSynchronizerForScrollBar2 = scrollBarsSynchronizerForScrollBar2;
    }
    
    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
        JScrollBar scrollBar = (JScrollBar)e.getSource();
        int value = scrollBar.getValue();
        
        if(link == true){
            
            int old_diff1 = oldValue1 - sc1.getMinimum();
            int old_diff2 = oldValue2 - sc2.getMinimum();
            
            int new_diff1 = value - sc1.getMinimum();            
            int new_diff2 = value - sc2.getMinimum();
            
            int unit1 = new_diff1 - old_diff1;            
            int unit2 = new_diff2 - old_diff2;
            
            if(sc1.equals(scrollBar) && scrollBarsSynchronizerForScrollBar1 != null){
                // On gère sc2 quand lien
                
                // On enlève l'écoute d'ajustement pour le ScrollBar 1
                sc1.removeAdjustmentListener(scrollBarsSynchronizerForScrollBar1);
                
                int units;
                
                units = oldValue2 + unit1 > sc2.getMaximum() ? sc2.getMaximum() : oldValue2 + unit1;
                sc2.setValue(units);
                
                oldValue2 = units;
                
                units = oldValue1 + unit1 > sc1.getMaximum() ? sc1.getMaximum() : oldValue1 + unit1;
                oldValue1 = units;
                
                // On remet l'écoute d'ajustement pour le ScrollBar 1
                sc1.addAdjustmentListener(scrollBarsSynchronizerForScrollBar1);
                
            }else if(sc2.equals(scrollBar) && scrollBarsSynchronizerForScrollBar2 != null){
                // On gère sc1 quand lien
                
                // On enlève l'écoute d'ajustement pour le ScrollBar 2
                sc2.removeAdjustmentListener(scrollBarsSynchronizerForScrollBar2);
                
                int units;
                
                units = oldValue1 + unit2 > sc1.getMaximum() ? sc1.getMaximum() : oldValue1 + unit2;
                sc1.setValue(units);
                
                oldValue1 = units;
                
                units = oldValue2 + unit2 > sc2.getMaximum() ? sc2.getMaximum() : oldValue2 + unit2;
                oldValue2 = units;
                
                // On remet l'écoute d'ajustement pour le ScrollBar 2
                sc2.addAdjustmentListener(scrollBarsSynchronizerForScrollBar2);
                
            }
        }else{
            if(sc1.equals(scrollBar)){
                // On gère sc1 quand pas de lien
                oldValue1 = value;
            }

            if(sc2.equals(scrollBar)){
                // On gère sc2 quand pas de lien
                oldValue2 = value;
            }
        }
        
    }
    
}
