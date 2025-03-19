package org.wingate.cmilkshake.compare;

import org.wingate.cmilkshake.sub.Event;
import org.wingate.cmilkshake.ui.View;
import org.wingate.cmilkshake.ui.ViewState;

import java.util.ArrayList;
import java.util.List;

public class Compare {

    private Compare(){

    }

    public static List<View> getSentenceViews(List<Event> a, List<Event> b){
        final List<View> deletedViews = new ArrayList<>();
        final List<View> addedViews = new ArrayList<>();
        final List<View> sameViews = new ArrayList<>();

        for(Event ea : a){
            String sentence_A = ea.getText();
            boolean found = false;
            for(Event eb : b){
                String sentence_B = eb.getText();
                if(sentence_A.equals(sentence_B)){
                    found = true;
                    break;
                }
            }
            if(!found){
                deletedViews.add(new View(ea, ViewState.HasBeenDeleted));
            }else{
                sameViews.add(new View(ea, ViewState.SameContent));
            }
        }

        for(Event eb : b){
            String sentence_B = eb.getText();
            boolean found = false;
            for(View deleted : deletedViews){
                if(deleted.getEvent().getText().equals(sentence_B)){
                    found = true;
                    break;
                }
            }
            for(View same : sameViews){
                if(!found && same.getEvent().getText().equals(sentence_B)){
                    found = true;
                    break;
                }
            }
            if(!found){
                addedViews.add(new View(eb, ViewState.NewContent));
            }
        }

        final List<View> c = new ArrayList<>();
        c.addAll(sameViews);
        c.addAll(deletedViews);
        c.addAll(addedViews);

        c.sort((v1, v2) -> {
            long startA = v1.getEvent().getMicrosStart();
            long startB = v2.getEvent().getMicrosStart();
            return Long.compare(startA, startB);
        });

        c.sort((v1, v2) -> {
            String startsWithA = strip(v1.getEvent().getText());
            String startsWithB = strip(v2.getEvent().getText());
            String sA = startsWithA.substring(0, Math.min(25, Math.max(0, startsWithA.length()-1)));
            String sB = startsWithB.substring(0, Math.min(25, Math.max(0, startsWithB.length()-1)));
            return sA.compareTo(sB);
        });

        return c;
    }

    private static String strip(String text){
        String str;
        if(text.contains("{")){
            try{
                str = text.replaceAll("\\{[^\\}]+\\}", "");
            }catch(Exception e){
                str = text;
            }
        }else{
            str = text;
        }
        return str;
    }
}
