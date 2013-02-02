/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package milkshake;

import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import milkshake.ass.AssIO;

/**
 *
 * @author The Wingate 2940
 */
public class StartFrame extends javax.swing.JFrame {
    
    public static String VLCpath = "";

    /**
     * Creates new form StartFrame
     */
    public StartFrame() {
        initComponents();
        init();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        fcVLC = new javax.swing.JFileChooser();
        lblImage = new javax.swing.JLabel();
        lblVLC = new javax.swing.JLabel();
        btnVLC = new javax.swing.JButton();
        tfVLC = new javax.swing.JTextField();
        btnStart = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Hey boy, a milkshake please !");
        setAlwaysOnTop(true);
        setUndecorated(true);

        lblImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/milkshake/Milkshake.png"))); // NOI18N

        lblVLC.setText("VLC 2.0.x folder :");

        btnVLC.setText("...");
        btnVLC.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVLCActionPerformed(evt);
            }
        });

        btnStart.setText("Start");
        btnStart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblImage, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblVLC)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(tfVLC)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnVLC))
            .addComponent(btnStart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblImage)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVLC)
                    .addComponent(btnVLC)
                    .addComponent(tfVLC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnStart)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnStartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartActionPerformed
        File f = new File(tfVLC.getText());
        boolean hasLibvlc = false;
        if(f.exists()){
            for(File nf : f.listFiles()){
                if(nf.getName().equalsIgnoreCase("libvlc.dll")){
                    hasLibvlc = true;
                }
            }
            if(hasLibvlc==true){
                VLCpath = tfVLC.getText();
                AssIO aio = new AssIO();
                aio.SaveConfig(VLCpath);
                dispose();
            }else{
                JOptionPane.showMessageDialog(this, "libvlc.dll not found\nPlease set a good folder.");
            }
        }else{
            JOptionPane.showMessageDialog(this, "Please set a good folder or quit the application.");
        }
    }//GEN-LAST:event_btnStartActionPerformed

    private void btnVLCActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVLCActionPerformed
        for (FileFilter f : fcVLC.getChoosableFileFilters()){
            fcVLC.removeChoosableFileFilter(f);
        }
        fcVLC.setAccessory(null);
        fcVLC.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int z = this.fcVLC.showOpenDialog(this);
        if (z == javax.swing.JFileChooser.APPROVE_OPTION){
            tfVLC.setText(fcVLC.getSelectedFile().getAbsolutePath());
        }
    }//GEN-LAST:event_btnVLCActionPerformed
    
    private void init(){        
        //Configuration du Look&Feel
        try {
            javax.swing.UIManager.setLookAndFeel(new NimbusLookAndFeel());
            javax.swing.SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception exc) {
            System.out.println("Nimbus LookAndFeel not loaded : "+exc);
        }
        
        //Configuration
        AssIO aio = new AssIO();
        if(!aio.HasConfigFile()){//If there is no file then create a new file
            aio.createConfigFile();
        }
        tfVLC.setText(aio.ReadConfig());
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StartFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new StartFrame().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnStart;
    private javax.swing.JButton btnVLC;
    private javax.swing.JFileChooser fcVLC;
    private javax.swing.JLabel lblImage;
    private javax.swing.JLabel lblVLC;
    private javax.swing.JTextField tfVLC;
    // End of variables declaration//GEN-END:variables
}