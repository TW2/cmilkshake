package org.wingate.cmilkshake.filter;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class AssFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        return f.isDirectory() || f.getPath().toLowerCase().endsWith(".ass");
    }

    @Override
    public String getDescription() {
        return "ASS files";
    }
}
