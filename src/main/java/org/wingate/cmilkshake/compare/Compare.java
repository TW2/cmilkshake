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

    public static List<View> getSentenceDiffViews(List<View> deleted, List<View> added){
        final List<View> startsViews = new ArrayList<>();
        final List<View> endsViews = new ArrayList<>();
        final List<View> differentViews = new ArrayList<>();

        for(View ea : deleted){
            String sA = strip(ea.getEvent().getText());
            String sentence_A = sA.substring(0, Math.min(25, Math.max(0, sA.length()-1)));
            boolean found = false;
            for(View eb : added){
                String sB = strip(eb.getEvent().getText());
                String sentence_B = sB.substring(0, Math.min(25, Math.max(0, sB.length()-1)));
                if(sentence_A.equals(sentence_B)){
                    found = true;
                    break;
                }
            }
            if(found){
                startsViews.add(new View(ea.getEvent(), ViewState.StartsWith));
            }
        }

        for(View ea : deleted){
            String sA = strip(ea.getEvent().getText());
            int indexA = Math.max(0, sA.length() - Math.min(25, Math.max(0, sA.length() - 1)));
            String sentence_A = sA.substring(indexA);
            boolean found = false;
            for(View eb : added){
                String sB = strip(eb.getEvent().getText());
                int indexB = Math.max(0, sB.length() - Math.min(25, Math.max(0, sB.length() - 1)));
                String sentence_B = sB.substring(indexB);
                if(sentence_A.equals(sentence_B)){
                    found = true;
                    break;
                }
            }
            if(found){
                endsViews.add(new View(ea.getEvent(), ViewState.EndsWith));
            }
        }

        for(View eb : added){
            String sentence_B = eb.getEvent().getText();
            boolean found = false;
            for(View starts : startsViews){
                if(starts.getEvent().getText().equals(sentence_B)){
                    found = true;
                    break;
                }
            }
            for(View ends : endsViews){
                if(!found && ends.getEvent().getText().equals(sentence_B)){
                    found = true;
                    break;
                }
            }
            if(!found){
                differentViews.add(new View(eb.getEvent(), ViewState.Different));
            }
        }

        final List<View> c = new ArrayList<>();
        c.addAll(startsViews);
        c.addAll(endsViews);
        c.addAll(differentViews);

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
