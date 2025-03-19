/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wingate.cmilkshake.compare;

import java.util.List;
import org.wingate.cmilkshake.ass.Ass;
import org.wingate.cmilkshake.ass.AssLine;
import org.wingate.cmilkshake.util.Time;

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
    
    // Pour chaque ligne de texte on compare et filtre
    public static CompareTimeReport compareTime(Ass ass_A, Ass ass_B){
        CompareTimeReport report = new CompareTimeReport();
        
        List<AssLine> partA = ass_A.getLines();
        List<AssLine> partB = ass_B.getLines();
        
        for(AssLine alA : partA){
            Time start_A = alA.getStart();
            Time end_A = alA.getEnd();
            
            for(AssLine alB : partB){
                Time start_B = alB.getStart();
                Time end_B = alB.getEnd();
                boolean found = false;
                
                boolean sameStart = Time.isSameStartTime(start_A, start_B);
                boolean sameEnd = Time.isSameEndTime(end_A, end_B);
                boolean sameDuration = Time.isSameDurationTime(start_A, end_A, start_B, end_B);
                
                if(alA.getText().equals(alB.getText()) == true
                        && sameStart | sameEnd | sameDuration){
                    // Same time = start_A == start_B & end_A == end_B                
                    // Shift time = start_A != start_B & total_A == total_B
                    // Enlarged = total_A < total_B
                    // Reduced = total_A > total_B
                    if(sameStart & sameEnd){ // Same time
                        report.getSameTimeLines().add(alB); found = true;
                    }else if(sameDuration){ // Shift time
                        report.getShiftTimeLines().add(alB); found = true;
                    }else if(Time.isEnlargedTime(start_A, end_A, start_B, end_B) == true){
                        report.getEnlargedTimeLines().add(alB); found = true;
                    }else if(Time.isReducedTime(start_A, end_A, start_B, end_B) == true){
                        report.getReducedTimeLines().add(alB); found = true;
                    }
//                    if(Time.isSameTime(start_A, end_A, start_B, end_B) == true){
//                        report.getSameTimeLines().add(alB); found = true;
//                    }else if(Time.isShiftTime(start_A, end_A, start_B, end_B) == true){
//                        report.getShiftTimeLines().add(alB); found = true;
//                    }else if(Time.isEnlargedTime(start_A, end_A, start_B, end_B) == true){
//                        report.getEnlargedTimeLines().add(alB); found = true;
//                    }else if(Time.isReducedTime(start_A, end_A, start_B, end_B) == true){
//                        report.getReducedTimeLines().add(alB); found = true;
//                    }

                    // Same start time = start_A == start_B
                    // Same end time = end_A == end_B
                    if(Time.isSameStartTime(start_A, start_B) == true){
                        report.getSameStartTimeLines().add(alB);
                    }
                    if(Time.isSameEndTime(end_A, end_B) == true){
                        report.getSameEndTimeLines().add(alB);
                    }
                }
                
                if(found == true){
                    break;
                }
            }
        }
        
        System.out.println("Same time : " + report.getSameTimeLines().size());
        System.out.println("Shift time : " + report.getShiftTimeLines().size());
        System.out.println("Enlarged time : " + report.getEnlargedTimeLines().size());
        System.out.println("Reduced time : " + report.getReducedTimeLines().size());
        
        System.out.println("Same start time : " + report.getSameStartTimeLines().size());
        System.out.println("Same end time : " + report.getSameEndTimeLines().size());
        
        return report;
    }
    
    public static double getCPS(AssLine line){
        String text = line.getText();
        String str;
        if(text.contains("{\\")){
            try{
                str = text.replaceAll("\\{[^\\}]+\\}", "");
            }catch(Exception e){
                str = text;
            }
        }else{
            str = text;
        }
        str = str.replaceAll("\\\\N", "");
        str = str.replaceAll("\\\\n", "");
        str = str.replaceAll("\\\\q\\d+", "");
        str = str.replaceAll("\\\\h", "");
        long s = Time.toMillisecondsTime(line.getStart());
        long e = Time.toMillisecondsTime(line.getEnd());
        double seconds = (e - s) / 1000d;
        return str.length() / (seconds != 0 ? seconds : 1);
    }
    
    // Pour chaque ligne de texte on compare et filtre
    public static CompareCPSReport compareCPS(Ass ass_A, Ass ass_B){
        CompareCPSReport report = new CompareCPSReport();
        
        List<AssLine> partA = ass_A.getLines();
        List<AssLine> partB = ass_B.getLines();
        
        for(AssLine alA : partA){
            double large_A = getCPS(alA);
            boolean found = false;
            for(AssLine alB : partB){
                double large_B = getCPS(alB);
                if(large_A == large_B){
                    found = true;
                    break;
                }
            }
            if(found == false){
                report.getOldLines().add(new CompareCPSReport.CpsCounter(large_A, alA));
            }else{
                report.getSameLines().add(new CompareCPSReport.CpsCounter(large_A, alA));
            }
        }
        
        for(AssLine alB : partB){
            double large_B = getCPS(alB);
            boolean found = false;
            for(CompareCPSReport.CpsCounter deleted : report.getOldLines()){
                if(deleted.getCps() == large_B){
                    found = true;
                    break;
                }
            }
            for(CompareCPSReport.CpsCounter same : report.getSameLines()){
                if(found != true && same.getCps() == large_B){
                    found = true;
                    break;
                }
            }
            if(found == false){
                report.getNewLines().add(new CompareCPSReport.CpsCounter(large_B, alB));
            }
        }
        
        System.out.println("Same CPS : " + report.getSameLines().size());
        System.out.println("Old CPS : " + report.getOldLines().size());
        System.out.println("New CPS : " + report.getNewLines().size());
        
        return report;
    }
    
    public static int getCPL(AssLine line){
        String text = line.getText();
        String str;
        if(text.contains("{\\")){
            try{
                str = text.replaceAll("\\{[^\\}]+\\}", "");
            }catch(Exception e){
                str = text;
            }
        }else{
            str = text;
        }
        str = str.replaceAll("\\\\N", "");
        str = str.replaceAll("\\\\n", "");
        str = str.replaceAll("\\\\q\\d+", "");
        str = str.replaceAll("\\\\h", "");
        return str.length();
    }
    
    // Pour chaque ligne de texte on compare et filtre
    public static CompareCPLReport compareCPL(Ass ass_A, Ass ass_B){
        CompareCPLReport report = new CompareCPLReport();
        
        List<AssLine> partA = ass_A.getLines();
        List<AssLine> partB = ass_B.getLines();
        
        for(AssLine alA : partA){
            int large_A = getCPL(alA);
            boolean found = false;
            for(AssLine alB : partB){
                int large_B = getCPL(alB);
                if(large_A == large_B){
                    found = true;
                    break;
                }
            }
            if(found == false){
                report.getOldLines().add(new CompareCPLReport.CplCounter(large_A, alA));
            }else{
                report.getSameLines().add(new CompareCPLReport.CplCounter(large_A, alA));
            }
        }
        
        for(AssLine alB : partB){
            int large_B = getCPL(alB);
            boolean found = false;
            for(CompareCPLReport.CplCounter deleted : report.getOldLines()){
                if(deleted.getCpl() == large_B){
                    found = true;
                    break;
                }
            }
            for(CompareCPLReport.CplCounter same : report.getSameLines()){
                if(found != true && same.getCpl() == large_B){
                    found = true;
                    break;
                }
            }
            if(found == false){
                report.getNewLines().add(new CompareCPLReport.CplCounter(large_B, alB));
            }
        }
        
        System.out.println("Same CPL : " + report.getSameLines().size());
        System.out.println("Old CPL : " + report.getOldLines().size());
        System.out.println("New CPL : " + report.getNewLines().size());
        
        return report;
    }
}
