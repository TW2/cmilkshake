package org.wingate.cmilkshake;

import com.formdev.flatlaf.FlatDarkLaf;
import org.wingate.cmilkshake.filter.AssFileFilter;
import org.wingate.cmilkshake.filter.AssSsaFileFilter;
import org.wingate.cmilkshake.filter.SsaFileFilter;
import org.wingate.cmilkshake.sub.ASS;
import org.wingate.cmilkshake.sub.Event;
import org.wingate.cmilkshake.ui.MultiView;
import org.wingate.cmilkshake.ui.Results;
import org.wingate.cmilkshake.ui.Video;
import org.wingate.cmilkshake.ui.Wave;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CMilkshake {

    public static void main(String[] args) {
        EventQueue.invokeLater(()-> new CMilkshake(args));
    }

    private final JFrame frame;
    private Map<String, List<Event>> variants;
    private MultiView multiView;
    private Results results;
    private Video video;
    private Wave waveform;
    private JComboBox<String> comboA;
    private DefaultComboBoxModel<String> comboModelA;
    private JComboBox<String> comboB;
    private DefaultComboBoxModel<String> comboModelB;

    public CMilkshake(String[] args) {
        FlatDarkLaf.setup();
        frame = createUI();
        frame.setVisible(true);
    }

    private JFrame createUI(){
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("Caramel Milkshake v2.7 :: Hey boy! A milkshake please!");
        frame.setSize(1900, 1000);

        variants = new HashMap<>();
        results = new Results();
        video = new Video();
        waveform = new Wave();
        multiView = new MultiView(results);
        comboA = new JComboBox<>();
        comboModelA = new DefaultComboBoxModel<>();
        comboA.setModel(comboModelA);
        comboB = new JComboBox<>();
        comboModelB = new DefaultComboBoxModel<>();
        comboB.setModel(comboModelB);

        JMenuBar menuBar = new JMenuBar();
        JMenu mnuFile = new JMenu("File");
        menuBar.add(mnuFile);
        JMenuItem mFileOpenASSA = new JMenuItem("Open ASS...");
        mFileOpenASSA.addActionListener((_) -> openASSA() );
        mnuFile.add(mFileOpenASSA);
        mnuFile.add(new JSeparator());
        JMenuItem mFileQuit = new JMenuItem("Quit");
        mFileQuit.addActionListener((_) -> System.exit(0) );
        mnuFile.add(mFileQuit);
        frame.setJMenuBar(menuBar);

        frame.getContentPane().setLayout(new GridLayout(1, 2,2,2));
        // Left -> Compare scripts + select scripts
        JPanel panCompare = new JPanel(new BorderLayout());
        // Right -> Results + Media
        JPanel panResults = new JPanel(new GridLayout(2,1, 0, 0));

        // Top (just two choices of scripts to compare)
        JPanel panNorth = new JPanel(new GridLayout(1, 2, 0,0));
        panNorth.add(comboA);
        comboA.addItemListener((_) -> {
            if(variants.isEmpty()) return;
            List<Event> a = variants.get(comboModelA.getElementAt(comboA.getSelectedIndex()));
            if(a != null){
                multiView.setA(a);
                handleVariants();
            }
        });
        panNorth.add(comboB);
        comboB.addItemListener((_) -> {
            if(variants.isEmpty()) return;
            List<Event> b = variants.get(comboModelB.getElementAt(comboB.getSelectedIndex()));
            if(b != null){
                multiView.setB(b);
                handleVariants();
            }
        });
        // Left
        panCompare.add(panNorth, BorderLayout.NORTH);
        panCompare.add(multiView, BorderLayout.CENTER);
        frame.getContentPane().add(panCompare);

        // Right
        JPanel panWaveResults = new JPanel(new GridLayout(2, 1, 0, 0));
        frame.getContentPane().add(panResults);
        panResults.add(video);
        panWaveResults.add(waveform);
        panWaveResults.add(results);
        panResults.add(panWaveResults);

        frame.setLocationRelativeTo(null);
        return frame;
    }

    private void openASSA(){
        JFileChooser fcOpen = new JFileChooser();
        fcOpen.setAcceptAllFileFilterUsed(false);
        fcOpen.setMultiSelectionEnabled(true);
        fcOpen.addChoosableFileFilter(new AssSsaFileFilter());
        fcOpen.addChoosableFileFilter(new SsaFileFilter());
        fcOpen.addChoosableFileFilter(new AssFileFilter());
        int z = fcOpen.showOpenDialog(frame);
        if(z == JFileChooser.APPROVE_OPTION){
            for(File f : fcOpen.getSelectedFiles()){
                addVariants(f);
            }

        }
    }

    private void addVariants(File file){
        List<Event> events = new ArrayList<>(ASS.getEvents(file.getPath()));
        variants.put(file.getName(), events);
        comboModelA.addElement(file.getName());
        comboModelB.addElement(file.getName());
    }

    private void removeVariants(String name){
        variants.remove(name);
    }

    private void handleVariants(){
        if(comboModelA.getSize() == 0 || comboModelB.getSize() == 0) return;
        String nameA = comboModelA.getElementAt(comboA.getSelectedIndex());
        String nameB = comboModelB.getElementAt(comboB.getSelectedIndex());
        if(nameA.equalsIgnoreCase(nameB)) return;
        multiView.repaint();
        results.repaint();
    }
}
