package org.wingate.cmilkshake.ui;

import java.awt.*;

public enum ViewState {
    SameContent("SameContent", Color.white),
    HasChanged("HasChanged", Color.yellow),
    NewContent("NewContent", Color.green),
    HasBeenDeleted("HasBeenDeleted", Color.red),
    StartsWith("StartsWith", Color.pink),
    EndsWith("EndsWith", Color.magenta),
    Different("Different", Color.orange);

    final String name;
    final Color color;

    ViewState(String name, Color color){
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }
}
