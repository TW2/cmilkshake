/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package org.wingate.cmilkshake;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLaf;
import java.awt.EventQueue;
import java.util.HashMap;
import java.util.Map;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author util2
 */
public class Cmilkshake {
    
    private static final String SOFTWARE = "Caramel Milkshake";
    private static final String VERSION = "2.6 (2023-02)";
    private static final String SLOGAN = "Hey boy a milkshake please!";
    private static final String MAINTAINER = "TW2";

    public static void main(String[] args) {
        System.out.println("Hey boy a milkshake please!");
        EventQueue.invokeLater(()->{
            MainFrame mf = new MainFrame();            
            try{
                UIManager.setLookAndFeel(useBloodyGolden());
                SwingUtilities.updateComponentTreeUI(mf);
            }catch(UnsupportedLookAndFeelException exc){
                System.err.println("Look and feel error!");
            }
            mf.setTitle(String.format("%s %s (by %s) :: %s",
                    SOFTWARE, VERSION, MAINTAINER, SLOGAN
            ));
            mf.setSize(1900, 1000);
            mf.setLocationRelativeTo(null);
            mf.setVisible(true);
        });
    }
    
    public static FlatLaf useBloodyGolden(){
        // La classe de base / Base class
        FlatLaf laf = new FlatDarkLaf();
        
        // Les modifications / Settings
        Map<String, String> settings = new HashMap<>();
        settings.put("@accentColor", "#FF3200");
        settings.put("@foreground", "#FFCC00");
        laf.setExtraDefaults(settings);
        
        return laf;
    }
}
