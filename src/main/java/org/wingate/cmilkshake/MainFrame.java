/*
 * Copyright (C) 2023 util2
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.wingate.cmilkshake;

import java.awt.BorderLayout;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import static org.wingate.cmilkshake.Cmilkshake.useBloodyGolden;
import org.wingate.cmilkshake.ass.Ass;
import org.wingate.cmilkshake.ass.AssLine;
import org.wingate.cmilkshake.compare.CompareCPLReport;
import org.wingate.cmilkshake.compare.CompareCPSReport;
import org.wingate.cmilkshake.compare.CompareTextReport;
import org.wingate.cmilkshake.compare.CompareTimeReport;
import org.wingate.cmilkshake.compare.Counter;
import org.wingate.cmilkshake.table.AssTableModel;
import org.wingate.cmilkshake.widget.TimeLinePanel;

/**
 *
 * @author util2
 */
public class MainFrame extends javax.swing.JFrame {
    
    private final TimeLinePanel tl = new TimeLinePanel();
    
    private AssTableModel tableModel_A,  tableModel_B;
    private final List<Ass> ass_list = new ArrayList<>();
    private CompareTextReport textReport = null;
    private CompareTimeReport timeReport = null;
    private CompareCPLReport cplReport = null;
    private CompareCPSReport cpsReport = null;
    
    private boolean hasforceLAFonFC = false;

    /**
     * Creates new form MainFrame
     */
    public MainFrame() {
        initComponents();
        init();
    }
    
    private void init(){
        jPanel2.setLayout(new BorderLayout());
        jPanel2.add(tl, BorderLayout.CENTER);
        
        jSplitPane1.setDividerLocation(.5d);
        jSplitPane2.setDividerLocation(.5d);
        
        tableModel_A = new AssTableModel(assTable_A);
        tableModel_B = new AssTableModel(assTable_B);
        
        assTable_A.setModel(tableModel_A);
        assTable_B.setModel(tableModel_B);
        
        assTable_A.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int sel = assTable_A.getSelectedRow();
                if(sel != -1){
                    AssLine line = tableModel_A.getLine(sel);
                    selection(line);
                }
            }
        });
        
        assTable_B.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                int sel = assTable_B.getSelectedRow();
                if(sel != -1){
                    AssLine line = tableModel_B.getLine(sel);
                    selection(line);
                }
            }
        });
        
        for(javax.swing.filechooser.FileFilter ff : fcFiles.getChoosableFileFilters()){
            fcFiles.removeChoosableFileFilter(ff);
        }
        fcFiles.addChoosableFileFilter(new javax.swing.filechooser.FileFilter() {
            @Override
            public boolean accept(File file) {
                return file.isDirectory() | file.getName().endsWith(".ass");
            }

            @Override
            public String getDescription() {
                return "ASS files";
            }
        });
        fcFiles.setMultiSelectionEnabled(true);
        
        addComponentListener(new ComponentAdapter(){
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                jSplitPane1.setDividerLocation(.5d);
                jSplitPane2.setDividerLocation(.5d);
            }            
        });
    }
	
    private void reinitTable(Ass ass_A, Ass ass_B){
        if(ass_A != null){
            // Table A (origin)
            tableModel_A.add(ass_A);
            tableModel_A.updateColumnSize(assTable_A);
        }
        
        if(ass_B != null){
            // Table B (another)
            tableModel_B.add(ass_B);
            tableModel_B.updateColumnSize(assTable_B);
        }
        
        if(ass_A != null && ass_B != null){
            textReport = Counter.compareText(ass_A, ass_B);
            tableModel_A.applyTextReport(textReport);
            tableModel_B.applyTextReport(textReport);
            timeReport = Counter.compareTime(ass_A, ass_B);
            tableModel_B.applyTimeReport(timeReport);
            cplReport = Counter.compareCPL(ass_A, ass_B);
            tableModel_B.applyCplReport(cplReport);
            cpsReport = Counter.compareCPS(ass_A, ass_B);
            tableModel_B.applyCpsReport(cpsReport);
        }
    }
    
    private void doComparison(File[] files){
        Arrays.sort(files, Comparator.comparingLong(File::lastModified));
        for(File file : files){                
            ass_list.add(Ass.read(file.getPath()));
        }
        if(ass_list.isEmpty() == false){
            if(ass_list.size() >= 2){                    
                reinitTable(ass_list.get(0), ass_list.get(1));

                assTable_A.revalidate(); assTable_B.revalidate();
                assTable_A.updateUI(); assTable_B.updateUI();
            }else{
                reinitTable(ass_list.get(0), null);

                assTable_A.revalidate();
                assTable_A.updateUI();
            }
            tl.openScripts(ass_list);
        }
    }
    
    private void selection(AssLine line){
        tl.trySelectEvent(line.getStart(), line.getEnd());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fcFiles = new javax.swing.JFileChooser();
        jSplitPane1 = new javax.swing.JSplitPane();
        jPanel2 = new javax.swing.JPanel();
        jSplitPane2 = new javax.swing.JSplitPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollBar1 = new javax.swing.JScrollBar();
        jScrollPane3 = new javax.swing.JScrollPane();
        assTable_A = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jScrollBar2 = new javax.swing.JScrollBar();
        jScrollPane4 = new javax.swing.JScrollPane();
        assTable_B = new javax.swing.JTable();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        mnuOpen = new javax.swing.JMenuItem();
        mnuQuit = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jSplitPane1.setDividerLocation(200);
        jSplitPane1.setDividerSize(10);
        jSplitPane1.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
        jSplitPane1.setOneTouchExpandable(true);

        jPanel2.setLayout(new java.awt.BorderLayout());
        jSplitPane1.setRightComponent(jPanel2);

        jSplitPane2.setDividerLocation(400);
        jSplitPane2.setDividerSize(10);
        jSplitPane2.setOneTouchExpandable(true);

        jPanel1.setLayout(new java.awt.BorderLayout());
        jPanel1.add(jScrollBar1, java.awt.BorderLayout.EAST);

        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        assTable_A.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane3.setViewportView(assTable_A);

        jPanel1.add(jScrollPane3, java.awt.BorderLayout.CENTER);

        jSplitPane2.setLeftComponent(jPanel1);

        jPanel3.setLayout(new java.awt.BorderLayout());
        jPanel3.add(jScrollBar2, java.awt.BorderLayout.EAST);

        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        assTable_B.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane4.setViewportView(assTable_B);

        jPanel3.add(jScrollPane4, java.awt.BorderLayout.CENTER);

        jSplitPane2.setRightComponent(jPanel3);

        jSplitPane1.setLeftComponent(jSplitPane2);

        getContentPane().add(jSplitPane1, java.awt.BorderLayout.CENTER);

        jMenu1.setText("File");

        mnuOpen.setText("Open scripts...");
        mnuOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuOpenActionPerformed(evt);
            }
        });
        jMenu1.add(mnuOpen);

        mnuQuit.setText("Quit");
        mnuQuit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mnuQuitActionPerformed(evt);
            }
        });
        jMenu1.add(mnuQuit);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mnuQuitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuQuitActionPerformed
        // Quit
        System.exit(0);
    }//GEN-LAST:event_mnuQuitActionPerformed

    private void mnuOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mnuOpenActionPerformed
        // Open files
        if(hasforceLAFonFC == false){
            try{
                UIManager.setLookAndFeel(useBloodyGolden());
                SwingUtilities.updateComponentTreeUI(fcFiles);
            }catch(UnsupportedLookAndFeelException exc){
                System.err.println("Look and feel error!");
            }
            hasforceLAFonFC = true;
        }
        int z = fcFiles.showOpenDialog(this);
        if(z == JFileChooser.APPROVE_OPTION){
            doComparison(fcFiles.getSelectedFiles());
        }
    }//GEN-LAST:event_mnuOpenActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable assTable_A;
    private javax.swing.JTable assTable_B;
    private javax.swing.JFileChooser fcFiles;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollBar jScrollBar1;
    private javax.swing.JScrollBar jScrollBar2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JSplitPane jSplitPane1;
    private javax.swing.JSplitPane jSplitPane2;
    private javax.swing.JMenuItem mnuOpen;
    private javax.swing.JMenuItem mnuQuit;
    // End of variables declaration//GEN-END:variables
}
