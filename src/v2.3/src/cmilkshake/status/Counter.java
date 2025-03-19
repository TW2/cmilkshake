/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cmilkshake.status;

import cmilkshake.ass.Ass;
import cmilkshake.ass.AssLine;
import java.util.List;

/**
 *
 * @author util2
 */
public class Counter {

    public Counter() {
    }
    
    // Pour chaque ligne de texte on compare et filtre
    public static CompareTextReport compareText(Ass ass_A, Ass ass_B){
        CompareTextReport report = new CompareTextReport();
        
        List<AssLine> partA = ass_A.getLines();
        List<AssLine> partB = ass_B.getLines();
        
        for(AssLine alA : partA){
            String sentence_A = alA.getText();
            boolean found = false;
            for(AssLine alB : partB){
                String sentence_B = alB.getText();
                if(sentence_A.equals(sentence_B)){                    
                    found = true;
                    break;
                }
            }
            if(found == false){
                report.getDeletedLines().add(alA);
            }else{
                report.getSameLines().add(alA);
            }
        }
        
        for(AssLine alB : partB){
            String sentence_B = alB.getText();
            boolean found = false;
            for(AssLine deleted : report.getDeletedLines()){
                if(deleted.getText().equals(sentence_B)){
                    found = true;
                    break;
                }
            }
            for(AssLine same : report.getSameLines()){
                if(found != true && same.getText().equals(sentence_B)){
                    found = true;
                    break;
                }
            }
            if(found == false){
                report.getAddedLines().add(alB);
            }
        }
        
        System.out.println("Same text : " + report.getSameLines().size());
        System.out.println("Deleted text : " + report.getDeletedLines().size());
        System.out.println("Added text : " + report.getAddedLines().size());
        
        return report;
    }
}
