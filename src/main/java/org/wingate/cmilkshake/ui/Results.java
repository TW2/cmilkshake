package org.wingate.cmilkshake.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;


public class Results extends JPanel {

    private static final double RADIUS = 100d;
    private static final double DIAMETER = RADIUS * 2;
    private static final double X_CIRCLE = 10d;
    private static final double Y_CIRCLE = 10d;

    private static final double BAR_CHART_LIMIT = 200d;
    private static final double BAR_CHART_HEIGHT = 27d;

    private List<View> views;

    private int same, added, deleted;
    private double sameStart, addedStart, deletedStart;
    private double sameAngle, addedAngle, deletedAngle;

    public Results() {
        setDoubleBuffered(true);
        views = new ArrayList<>();
        same = -1; sameStart = 0d; sameAngle = 0d;
        added = -1; addedStart = 0d; addedAngle = 0d;
        deleted = -1; deletedStart = 0d; deletedAngle = 0d;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.black);
        g.fillRect(0, 0, getWidth(), getHeight());

        if(!views.isEmpty()){
            Graphics2D g2d = (Graphics2D)g;

            g2d.setRenderingHint(
                    RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON
            );

            Font oldFont = g2d.getFont();
            g2d.setFont(g2d.getFont().deriveFont(Font.BOLD));
            g2d.setFont(g2d.getFont().deriveFont(14f));


            if(same != -1 && added != -1 && deleted != -1){
                // Display the PIE CHART

                //=================================
                // BACKGROUND
                //=================================

                // Same content background
                g2d.setColor(ViewState.SameContent.getColor());
                Arc2D arc1 = new Arc2D.Double(Arc2D.PIE);
                arc1.setFrame(X_CIRCLE, Y_CIRCLE, DIAMETER, DIAMETER);
                arc1.setAngleStart(sameStart);
                arc1.setAngleExtent(sameAngle);
                g2d.fill(arc1);

                // Deleted content background
                g2d.setColor(ViewState.HasBeenDeleted.getColor());
                Arc2D arc2 = new Arc2D.Double(Arc2D.PIE);
                arc2.setFrame(X_CIRCLE, Y_CIRCLE, DIAMETER, DIAMETER);
                arc2.setAngleStart(deletedStart);
                arc2.setAngleExtent(deletedAngle);
                g2d.fill(arc2);

                // Added content background
                g2d.setColor(ViewState.NewContent.getColor());
                Arc2D arc3 = new Arc2D.Double(Arc2D.PIE);
                arc3.setFrame(X_CIRCLE, Y_CIRCLE, DIAMETER, DIAMETER);
                arc3.setAngleStart(addedStart);
                arc3.setAngleExtent(addedAngle);
                g2d.fill(arc3);

                //=================================
                // FOREGROUND
                //=================================

                g2d.setColor(Color.black);

                // Same content background
                arc1.setAngleStart(sameStart);
                arc1.setAngleExtent(sameAngle);
                g2d.draw(arc1);
                drawPercent(
                        g2d,
                        "Same",
                        (double)same / Math.max(1d, views.size()),
                        sameStart,
                        sameAngle
                );

                // Deleted content background
                arc2.setAngleStart(deletedStart);
                arc2.setAngleExtent(deletedAngle);
                g2d.draw(arc2);
                drawPercent(
                        g2d,
                        "Deleted",
                        (double)deleted / Math.max(1d, views.size()),
                        deletedStart,
                        deletedAngle
                );

                // Added content background
                arc3.setAngleStart(addedStart);
                arc3.setAngleExtent(addedAngle);
                g2d.draw(arc3);
                drawPercent(
                        g2d,
                        "Added",
                        (double)added / Math.max(1d, views.size()),
                        addedStart,
                        addedAngle
                );

                // Display the BAR CHART
                drawBarChart(g2d);


            }

            g2d.setFont(oldFont);
        }
    }

    private void compare(){
        same = 0;
        added = 0;
        deleted = 0;

        for(View v : views){
            switch (v.getState()){
                case SameContent -> same++;
                case HasBeenDeleted -> deleted++;
                case NewContent -> added++;
            }
        }

        double total = views.size();

        sameStart = 0d;
        sameAngle = same / Math.max(1d, total) * 360d;

        deletedStart = sameStart + sameAngle;
        deletedAngle = deleted / Math.max(1d, total) * 360d;

        addedStart = deletedStart + deletedAngle;
        addedAngle = 360d - (sameAngle + deletedAngle);
    }

    public void setViews(List<View> views) {
        this.views = views;
        compare();
        repaint();
    }

    private void drawPercent(Graphics2D g, String label, double percent,
                             double angleStart, double angleExtent){
        double xCenter = RADIUS + X_CIRCLE;
        double yCenter = RADIUS + Y_CIRCLE;
        double angle = angleStart + angleExtent / 2;
        double a = Math.toRadians(-angle);
        double r = RADIUS / 2d;
        double x = Math.cos(a) * r + xCenter;
        double y = Math.sin(a) * r + yCenter;

        String s = String.format("%s %d%%", label, (int)Math.round(percent * 100d));

        int size = g.getFontMetrics().stringWidth(s);
        g.drawString(s, (float)x - size / 2f, (float)y);
    }

    private void drawBarChart(Graphics2D g){
        // Draw the base
        g.setColor(Color.white);
        Rectangle2D r = new Rectangle2D.Double(230.0, 10.0, 3d, BAR_CHART_LIMIT);
        g.fill(r);

        g.setColor(new Color(0,0,63));
        r = new Rectangle2D.Double(230.0 + 5d, 10.0, BAR_CHART_LIMIT, BAR_CHART_LIMIT);
        g.fill(r);

        g.setColor(new Color(0,0,255));
        for(double y=0d; y<BAR_CHART_LIMIT + 1; y+=BAR_CHART_LIMIT/10d){
            Line2D l = new Line2D.Double(
                    230d + 5d,
                    10d + y,
                    230d + 5d + BAR_CHART_LIMIT,
                    10d + y);
            g.draw(l);
        }

        g.setColor(new Color(0,0,127));
        for(double x=0d; x<BAR_CHART_LIMIT + 1; x+=BAR_CHART_LIMIT/10d){
            Line2D l = new Line2D.Double(
                    230d + 5d + x,
                    10d,
                    230d + 5d + x,
                    10d + BAR_CHART_LIMIT);
            g.draw(l);
        }

        // Draw same
        g.setColor(Color.black);
        Rectangle2D rSame = new Rectangle2D.Double(
                230.0 + 5d,
                10.0 + (BAR_CHART_LIMIT - 3 * BAR_CHART_HEIGHT),
                ((double)same / Math.max(1d, views.size())) * BAR_CHART_LIMIT,
                BAR_CHART_HEIGHT
        );
        g.fill(rSame);
        g.setColor(ViewState.SameContent.getColor());
        g.drawString(
                Integer.toString(same),
                (float)(230.0 + 7d),
                (float)(10.0 + (BAR_CHART_LIMIT - 3 * BAR_CHART_HEIGHT)) + 15f
        );

        // Draw deleted
        g.setColor(Color.black);
        Rectangle2D rDeleted = new Rectangle2D.Double(
                230.0 + 5d,
                10.0 + (BAR_CHART_LIMIT - 3 * BAR_CHART_HEIGHT) * 1/3,
                ((double)deleted / Math.max(1d, views.size())) * BAR_CHART_LIMIT,
                BAR_CHART_HEIGHT
        );
        g.fill(rDeleted);
        g.setColor(ViewState.HasBeenDeleted.getColor());
        g.drawString(
                Integer.toString(deleted),
                (float)(230.0 + 7d),
                (float)(10.0 + (BAR_CHART_LIMIT - 3 * BAR_CHART_HEIGHT) * 1/3) + 15f
        );

        // Draw added
        g.setColor(Color.black);
        Rectangle2D rAdded = new Rectangle2D.Double(
                230.0 + 5d,
                10.0 + (BAR_CHART_LIMIT - 3 * BAR_CHART_HEIGHT) * 2/3,
                    ((double)added / Math.max(1d, views.size())) * BAR_CHART_LIMIT,
                BAR_CHART_HEIGHT
        );
        g.fill(rAdded);
        g.setColor(ViewState.NewContent.getColor());
        g.drawString(
                Integer.toString(added),
                (float)(230.0 + 7d),
                (float)(10.0 + (BAR_CHART_LIMIT - 3 * BAR_CHART_HEIGHT) * 2/3) + 15f
        );
    }
}
