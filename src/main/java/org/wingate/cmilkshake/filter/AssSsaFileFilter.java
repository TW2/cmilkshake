package org.wingate.cmilkshake.filter;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class AssSsaFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        return f.isDirectory()
                || f.getPath().toLowerCase().endsWith(".ass")
                || f.getPath().toLowerCase().endsWith(".ssa");
    }

    @Override
    public String getDescription() {
        return "ASS/SSA files";
    }
}
