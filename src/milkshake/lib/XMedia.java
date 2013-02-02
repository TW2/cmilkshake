/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package milkshake.lib;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaTool;
import com.xuggle.mediatool.MediaToolAdapter;
import com.xuggle.mediatool.ToolFactory;
import com.xuggle.mediatool.event.IVideoPictureEvent;
import com.xuggle.xuggler.IContainer;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;
import milkshake.ass.Time;

/**
 *
 * @author The Wingate 2940
 */
public class XMedia extends JPanel implements Runnable {
    
    Thread encodeThread = null;
    String videoPath = null;
    BufferedImage biOutput = new BufferedImage(1280, 720, BufferedImage.TYPE_INT_ARGB);
    Graphics2D gra;
    
    public XMedia(){
        gra = biOutput.createGraphics();
        setSize(1280,720);
    }
    
    //MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
    //   Encode
    //MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
    
    public int whenEncode(){      
        IMediaReader mediaReader = ToolFactory.makeReader(videoPath);
        mediaReader.setBufferedImageTypeToGenerate(BufferedImage.TYPE_3BYTE_BGR);
        IContainer ic = mediaReader.getContainer();
        IMediaTool imageMediaTool = new SubtitleImageMediaTool(ic);
        mediaReader.addListener(imageMediaTool);
        while(mediaReader.readPacket() == null){}
        return 1;
    }
    
    public void startEncode(){
        encodeThread = new Thread(this);
        encodeThread.start();
    }
    
    public void stopEncode(){
        if(encodeThread != null){
            encodeThread.interrupt();
        }
        encodeThread = null;
    }
    
    private class SubtitleImageMediaTool extends MediaToolAdapter {
		
        private BufferedImage argb_image;
        private IContainer cont;

        public SubtitleImageMediaTool(IContainer cont) {
            this.cont = cont;
        }

        @Override
        public void onVideoPicture(IVideoPictureEvent event) {
            BufferedImage image = event.getImage();
            Long timestamp = event.getTimeStamp();
            
            gra.drawImage(image, null, 0, 0);
            
            if(cont.isOpened()){
                gra.setFont(new Font("Arial",Font.BOLD,16));
                gra.setColor(Color.red);
                Time t = Time.fromMillisecondsTime(timestamp/1000);
                gra.drawString(t.toASSTime(), 10, 10);
            }
            
            super.onVideoPicture(event);
            repaint();
        }

    }
    
    //MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
    //   General
    //MMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMMM
    
    @Override
    public void run() {
        while(encodeThread != null){
            if(whenEncode()==1){
                stopEncode();
            }
        }
    }
    
    @Override
    public void paint(Graphics g){
        g.setColor(Color.blue);
        g.fillRect(0, 0, 1280, 720);
        
        if(biOutput!=null){
            g.drawImage(biOutput, 0, 0, null);
        }
    }
    
    public void setPath(String path){
        videoPath = path;
    }
    
}
