package org.wingate.cmilkshake.sub;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ASS {

    private ASS(){
    }

    public static List<Event> getEvents(String path){
        List<Event> events = new ArrayList<>();

        try(FileReader fr = new FileReader(path); BufferedReader br = new BufferedReader(fr)) {
            String line;
            while((line = br.readLine()) != null){
                if(line.startsWith(EventType.Dialogue.getName()) || line.startsWith(EventType.Comment.getName())){
                    events.add(Event.createFromASSA(line));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return events;
    }
}
