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
        final List<View> c = new ArrayList<>();

        boolean exists;

        for(Event ea : a){
            exists = false;
            for(Event eb : b){
                if(ea.getText().equals(eb.getText())){
                    exists = true;
                    c.add(new View(ea, ViewState.SameContent));
                    break;
                }
            }
            // If exists == false then A has a old sentence
            if(!exists){
                c.add(new View(ea, ViewState.HasBeenDeleted));
            }
        }

        for(Event eb : b){
            exists = false;
            for(Event ea : a){
                if(ea.getText().equals(eb.getText())){
                    exists = true;
                    break;
                }
            }
            // If exists == false then B has a new sentence
            if(!exists){
                c.add(new View(eb, ViewState.NewContent));
            }
        }

        return c;
    }
}
