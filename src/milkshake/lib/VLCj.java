/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package milkshake.lib;

import com.sun.jna.NativeLibrary;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import milkshake.StartFrame;
import milkshake.VMilkshake;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.player.MediaPlayer;
import uk.co.caprica.vlcj.player.MediaPlayerEventListener;
import xrevolution.Subtitle;

/**
 *
 * @author The Wingate 2940
 */
public class VLCj extends JLayeredPane {
    
    private EmbeddedMediaPlayerComponent VLCcomp;
    private String videopath = null;
    private String asspath = null;
    private State state = State.NONE;
    
    SubPanel spanel = new SubPanel();
    
    List<Subtitle> subList1 = new ArrayList<>();
    List<Subtitle> subList2 = new ArrayList<>();
    
    public enum State{
        NONE, PLAY, PAUSE, STOP;
    }
    
    public VLCj(){
        //NativeLibrary.addSearchPath("libvlc", "C:\\Program Files (x86)\\VideoLAN\\VLC");
//        setSize(1280, 420);
//        VLCcomp = new EmbeddedMediaPlayerComponent();
//        VLCcomp.setSize(1280, 360);
//        VLCcomp.setLocation(0, 0);
//        setLayout(null); //setLayout(new BorderLayout());
//        add(VLCcomp, 1); //add(VLCcomp, BorderLayout.CENTER);
//        
//        spanel.setLocation(0, 360);
//        spanel.setSubtitle("First ASS","Second ASS");
//        add(spanel, 0);
    }
    
    public State getState(){
        return state;
    }
    
    public void configure(String videopath, String asspath){
        this.videopath = videopath;
        this.asspath = asspath;
        if(videopath!=null){
            VLCcomp.getMediaPlayer().startMedia(videopath);
            VLCcomp.getMediaPlayer().stop();
            state = State.STOP;
        }
        if(asspath!=null){
            VLCcomp.getMediaPlayer().setSubTitleFile(new File(asspath));
        }
        
    }
    
    public void setSubLists(List<Subtitle> subList1, List<Subtitle> subList2){
        this.subList1 = subList1;
        this.subList2 = subList2;
    }
    
    public void setupVLCComponent(){
        NativeLibrary.addSearchPath("libvlc", StartFrame.VLCpath);
        setSize(1280, 420);
        VLCcomp = new EmbeddedMediaPlayerComponent();
        VLCcomp.setSize(1280, 360);
        VLCcomp.setLocation(0, 0);
        setLayout(null); //setLayout(new BorderLayout());
        add(VLCcomp, 1); //add(VLCcomp, BorderLayout.CENTER);
        
        spanel.setLocation(0, 360);
        spanel.setSubtitle("First ASS","Second ASS");
        add(spanel, 0);
    }
    
    public void play(){
        if(state==State.STOP || state==State.PAUSE){
            state = State.PLAY;
            VLCcomp.getMediaPlayer().play();
        }
    }
    
    public void pause(){
        if(state==State.PLAY){
            state = State.PAUSE;
            VLCcomp.getMediaPlayer().pause();
        }
    }
    
    public void stop(){
        if(state==State.PLAY || state==State.PAUSE){
            state = State.STOP;
            VLCcomp.getMediaPlayer().stop();
        }
    }
    
    public void gotoAndPlay(){
        VLCcomp.getMediaPlayer().setPosition(0.5f);
        VLCcomp.getMediaPlayer().play();
        System.out.println("ASS : "+VLCcomp.getMediaPlayer().getPosition());
    }
    
    public void initVLC(final JSlider slider){
        VLCcomp.getMediaPlayer().addMediaPlayerEventListener(new MediaPlayerEventListener() {
            @Override
            public void mediaChanged(MediaPlayer mp, libvlc_media_t l, String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void opening(MediaPlayer mp) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void buffering(MediaPlayer mp, float f) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void playing(MediaPlayer mp) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void paused(MediaPlayer mp) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void stopped(MediaPlayer mp) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void forward(MediaPlayer mp) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void backward(MediaPlayer mp) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void finished(MediaPlayer mp) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void timeChanged(MediaPlayer mp, long l) {
                String s1 = "---", s2 = "---"; long das1 = 0; long das2 = 0;
                for(Subtitle sub : subList1){
                    if(sub.isVisible(l)){
                        if(das1==l){
                            s1 = s1+" | "+sub.getSubtitle();
                        }else{
                            s1 = sub.getSubtitle();
                            das1 = l;
                        }
                    }
                }
                for(Subtitle sub : subList2){
                    if(sub.isVisible(l)){
                        if(das2==l){
                            s2 = s2+" | "+sub.getSubtitle();
                        }else{
                            s2 = sub.getSubtitle();
                            das2 = l;
                        }
                    }
                }
                spanel.setSubtitle(s1,s2);
                VMilkshake.updateTime(l);
            }

            @Override
            public void positionChanged(MediaPlayer mp, float f) {
                int value = Math.round(f * 100);
                slider.setValue(value);
            }

            @Override
            public void seekableChanged(MediaPlayer mp, int i) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void pausableChanged(MediaPlayer mp, int i) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void titleChanged(MediaPlayer mp, int i) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void snapshotTaken(MediaPlayer mp, String string) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void lengthChanged(MediaPlayer mp, long l) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void videoOutput(MediaPlayer mp, int i) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void error(MediaPlayer mp) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mediaMetaChanged(MediaPlayer mp, int i) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mediaSubItemAdded(MediaPlayer mp, libvlc_media_t l) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mediaDurationChanged(MediaPlayer mp, long l) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mediaParsedChanged(MediaPlayer mp, int i) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mediaFreed(MediaPlayer mp) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void mediaStateChanged(MediaPlayer mp, int i) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void newMedia(MediaPlayer mp) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void subItemPlayed(MediaPlayer mp, int i) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void subItemFinished(MediaPlayer mp, int i) {
                throw new UnsupportedOperationException("Not supported yet.");
            }

            @Override
            public void endOfSubItems(MediaPlayer mp) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        });
    }
    
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    // GetSet informations
    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
    
    public float getPosition(){
        return VLCcomp.getMediaPlayer().getPosition();
    }
    
    public void setPosition(float pos){
        VLCcomp.getMediaPlayer().setPosition(pos);
    }
    
    public class SubPanel extends JPanel {
        
        BufferedImage biOutput = new BufferedImage(1280, 60, BufferedImage.TYPE_INT_ARGB);
        Graphics2D gra;
        
        public SubPanel(){
            gra = biOutput.createGraphics();
            setSize(1280,60);
        }
        
        public void setSubtitle(String sub1, String sub2){
            gra.setColor(Color.black);
            gra.clearRect(0, 0, 1280, 60);
            gra.setFont(new Font("Arial", Font.BOLD, 16));
            gra.setColor(new Color(51,153,255));
            FontMetrics fm = gra.getFontMetrics();            
            gra.drawString(sub1, (1280-fm.stringWidth(sub1))/2, 25);
            gra.setColor(new Color(255,153,51));
            gra.drawString(sub2, (1280-fm.stringWidth(sub2))/2, 50);
            repaint();
        }
        
        @Override
        public void paint(Graphics g){
            g.drawImage(biOutput, 0, 0, null);
        }
    }
    
}
