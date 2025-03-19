package org.wingate.cmilkshake.sub;

public enum EventType {
    Dialogue("Dialogue"),
    Comment("Comment"),
    Other("");

    final String name;

    EventType(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static EventType get(String name){
        EventType type = EventType.Other;

        for(EventType t : values()){
            if(t.getName().equalsIgnoreCase(name)){
                type = t;
                break;
            }
        }

        return type;
    }
}
