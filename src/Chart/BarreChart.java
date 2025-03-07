/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Chart;

import Chart.BlankPlotChart;
import Chart.BlankPlotChatRender;
import Chart.SeriesSize;
import static Panel.HomePanel.months;
import Models.ModelChart;
import Models.ModelLegend;
import Scroll.ScrollPaneWin11;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingUtilities;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

/**
 *
 * @author brici_6ul2f65
 */
public final class BarreChart extends javax.swing.JPanel {

    DecimalFormat df = new DecimalFormat("#,##0.##");
    private List<ModelLegend> legends = new ArrayList<>();
    private List<ModelChart> model = new ArrayList<>();
    private final int seriesSize = 18;
    private final int seriesSpace = 10;
    private final Animator animator;
    private float animate;
    private String showLabel;
    private Point labelLocation = new Point();
    private ScrollPaneWin11 scroll;
    private BlankPlotChart blankPlotChart;
    private YAxisPanel yAxisPanel;
   
    private javax.swing.JPanel panelLegend;

    public BarreChart() {
      
        CustomInitComponent();

        scroll. getHorizontalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "hoverTrackColor:null;"
                +"background:null;"
                
                +"track: rgb(62, 66, 68)"
       );
        
            
        setOpaque(false);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                animate = fraction;
                repaint();
            }
        };
        animator = new Animator(800, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        
          blankPlotChart.setBlankPlotChatRender(new BlankPlotChatRender() {
            @Override
            public int getMaxLegend() {
                return legends.size();
            }

            @Override
            public String getLabelText(int index) {
                return model.get(index).getLabel();
            }

            @Override
            public void renderSeries(BlankPlotChart chart, Graphics2D g2, SeriesSize size, int index) {
                // Calculate total width for the series bars
                double totalSeriesWidth = (seriesSize * legends.size()) + (seriesSpace * (legends.size() - 1));
                double x = (size.getWidth() - totalSeriesWidth) / 2;
                
                // Loop through each legend to render series bars
                for (int i = 0; i < legends.size(); i++) {
                    ModelLegend legend = legends.get(i);
//                              GradientPaint gra = new GradientPaint(0, 0, new Color(86, 195, 250), 
//                            0, (int) (size.getY() + size.getHeight()), legend.getColor());
//                    g2.setPaint(gra);
                    // Create a gradient color for the bars
                    GradientPaint gra = new GradientPaint(0, 0, legend.getColor(), 
                            0, (int) (size.getY() + size.getHeight()), legend.getColorLight());
                    g2.setPaint(gra);
                    
                    // Get the value of the current series and scale it by 'animate' for animation
                    double seriesValues = chart.getSeriesValuesOf(model.get(index).getValues()[i], size.getHeight()) * animate;
                    
                    // Draw the rectangle (bar) with the animated height
                    g2.fillRect((int) (size.getX() + x), 
                                (int) (size.getY() + size.getHeight() - seriesValues), 
                                seriesSize, (int) seriesValues);
                    
                    // Update x position for next bar
                    x += seriesSpace + seriesSize;
                }
                    if (showLabel != null) {
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
                    Dimension s = getLabelWidth(showLabel, g2);
                    int space = 3;
                    int spaceTop = 5;
                    g2.setColor(new Color(30, 30, 30));
                    g2.fillRoundRect(labelLocation.x - s.width / 2 - 3, labelLocation.y - s.height - space * 2 - spaceTop, s.width + space * 2, s.height + space * 2, 10, 10);
                    g2.setColor(new Color(200, 200, 200));
                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
                    g2.drawString(showLabel, labelLocation.x - s.width / 2, labelLocation.y - spaceTop - space * 2);
                }
                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
               // scroll.getHorizontalScrollBar().setValue(scroll.getHorizontalScrollBar().getMaximum());
            
            }
            
            @Override
            public void renderSeries(BlankPlotChart chart, Graphics2D g2, SeriesSize size, int index, List<Path2D.Double> gra) {
                // Can be implemented for more complex series (e.g., line charts), left blank for now
            }

            @Override
            public void renderGraphics(Graphics2D g2, List<Path2D.Double> gra) {
                // Additional custom graphics rendering if needed, left blank for now
            }

            @Override
            public boolean mouseMoving(BlankPlotChart chart, MouseEvent evt, Graphics2D g2, SeriesSize size, int index) {
                // Handle mouse hover over the bars to show tooltip or highlight effect
                double totalSeriesWidth = (seriesSize * legends.size()) + (seriesSpace * (legends.size() - 1));
                double x = (size.getWidth() - totalSeriesWidth) / 2;
                
                for (int i = 0; i < legends.size(); i++) {
                    double seriesValues = chart.getSeriesValuesOf(model.get(index).getValues()[i], size.getHeight()) * animate;
                    int s = seriesSize / 2;
                    int sy = seriesSize / 3;
                    int px[] = {(int) (size.getX() + x), (int) (size.getX() + x + s), (int) (size.getX() + x + seriesSize), 
                                (int) (size.getX() + x + seriesSize), (int) (size.getX() + x + s), (int) (size.getX() + x)};
                    int py[] = {(int) (size.getY() + size.getHeight() - seriesValues), 
                                (int) (size.getY() + size.getHeight() - seriesValues - sy), 
                                (int) (size.getY() + size.getHeight() - seriesValues), 
                                (int) (size.getY() + size.getHeight()), 
                                (int) (size.getY() + size.getHeight() + sy), (int) (size.getY() + size.getHeight())};
                    
                    if (new Polygon(px, py, px.length).contains(evt.getPoint())) {
                        double data = model.get(index).getValues()[i];
                        showLabel = df.format(data);
                        labelLocation.setLocation((int) (size.getX() + x + s), (int) (size.getY() + size.getHeight() - seriesValues - sy));
                        chart.repaint();
                        return true;
                    }
                    
                    x += seriesSpace + seriesSize;
                }
                
                return false;
            }
        });

    }
    private void CustomInitComponent() {
        setLayout(new BorderLayout());
//        setOpaque(false);

        blankPlotChart = new BlankPlotChart();
        blankPlotChart.setForeground(new java.awt.Color(222, 222, 222));
        blankPlotChart.setBackground(new java.awt.Color(62, 66, 68));

        scroll = new ScrollPaneWin11();
        scroll.getViewport().setOpaque(false);
         scroll.setHorizontalScrollBarPolicy(ScrollPaneWin11.HORIZONTAL_SCROLLBAR_ALWAYS);
        scroll.setBorder(null);
        scroll.setViewportView(blankPlotChart);
     //JScrollBar vscroll = scroll.getHorizontalScrollBar();
      //  vscroll.setUnitIncrement(10);


       // Create a NiceScale instance
       yAxisPanel = new YAxisPanel();  // Pass NiceScale to YAxisPanel


         // YAxisPanel configuration (comme dans ton code)
        //yAxisPanel = new YAxisPanel();  

//        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, yAxisPanel, scroll);
//        splitPane.setOneTouchExpandable(false);
//        splitPane.setDividerSize(0);  
//        splitPane.setResizeWeight(0.1);  
        panelLegend = new javax.swing.JPanel();
      panelLegend.setOpaque(false);
        panelLegend.setPreferredSize(new java.awt.Dimension(573, 50));

        add(yAxisPanel, BorderLayout.WEST);
        add(scroll, BorderLayout.CENTER);
        add(panelLegend, BorderLayout.SOUTH);
    }


    public void addLegend(String name, Color color, Color color1) {
        ModelLegend data = new ModelLegend(name, color, color1);
        legends.add(data);
        panelLegend.add(new LegendItem(data));
        panelLegend.repaint();
        panelLegend.revalidate();
    }
 public void addLegend(String name, Color color) {
        ModelLegend data = new ModelLegend(name, color);
        legends.add(data);
        panelLegend.add(new LegendItem(data));
        panelLegend.repaint();
        panelLegend.revalidate();
    }
    public void addData(ModelChart data) {
        model.add(data);
        blankPlotChart.setLabelCount(model.size());
        double max = data.getMaxValues();
        if (max > blankPlotChart.getMaxValues()) {
            blankPlotChart.setMaxValues(max);
        }
    }


    public void clear() {
        animate = 0;
        showLabel = null;
        blankPlotChart.setLabelCount(0);
        model.clear();
        repaint();
    }

    public void start() {
        showLabel = null;
        if (!animator.isRunning()) {
            animator.start();
        }
    }

    public void startAnimation() {
        if (!animator.isRunning()) {
            animator.start();
        }
    }
    private Dimension getLabelWidth(String text, Graphics2D g2) {
        FontMetrics ft = g2.getFontMetrics();
        Rectangle2D r2 = ft.getStringBounds(text, g2);
        return new Dimension((int) r2.getWidth(), (int) r2.getHeight());
    }

     
                     
 
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(597, 394));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 597, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 394, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}


//        blankPlotChart.setBlankPlotChatRender(new BlankPlotChatRender() {
//            @Override
//            public int getMaxLegend() {
//                return legends.size();
//            }
//
//            @Override
//            public String getLabelText(int index) {
//                return model.get(index).getLabel();
//            }
//
//            @Override
//            public void renderSeries(BlankPlotChart chart, Graphics2D g2, SeriesSize size, int index) {
//                double totalSeriesWidth = (seriesSize * legends.size()) + (seriesSpace * (legends.size() - 1));
//                double x = (size.getWidth() - totalSeriesWidth) / 2;
//                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f));
//                for (int i = 0; i < legends.size(); i++) {
//                    ModelLegend legend = legends.get(i);
//                    double seriesValues = chart.getSeriesValuesOf(model.get(index).getValues()[i], size.getHeight()) * animate;
//                    int s = seriesSize / 2;
//                    int sy = seriesSize / 3;
//                    int px[] = {(int) (size.getX() + x), (int) (size.getX() + x + s), (int) (size.getX() + x + s), (int) (size.getX() + x)};
//                    int py[] = {(int) (size.getY() + size.getHeight() - seriesValues), (int) (size.getY() + size.getHeight() - seriesValues + sy), (int) (size.getY() + size.getHeight() + sy), (int) (size.getY() + size.getHeight())};
//                    GradientPaint gra = new GradientPaint((int) (size.getX() + x) - s, 0, legend.getColorLight(), (int) (size.getX() + x + s), 0, legend.getColor());
//                    g2.setPaint(gra);
//                    g2.fillPolygon(px, py, px.length);
//                    int px1[] = {(int) (size.getX() + x + s), (int) (size.getX() + x + seriesSize), (int) (size.getX() + x + seriesSize), (int) (size.getX() + x + s)};
//                    int py1[] = {(int) (size.getY() + size.getHeight() - seriesValues + sy), (int) (size.getY() + size.getHeight() - seriesValues), (int) (size.getY() + size.getHeight()), (int) (size.getY() + size.getHeight() + sy)};
//                    g2.setColor(legend.getColorLight());
//                    g2.fillPolygon(px1, py1, px1.length);
//                    int px2[] = {(int) (size.getX() + x), (int) (size.getX() + x + s), (int) (size.getX() + x + seriesSize), (int) (size.getX() + x + s)};
//                    int py2[] = {(int) (size.getY() + size.getHeight() - seriesValues), (int) (size.getY() + size.getHeight() - seriesValues - sy), (int) (size.getY() + size.getHeight() - seriesValues), (int) (size.getY() + size.getHeight() - seriesValues + sy)};
//                    g2.fillPolygon(px2, py2, px2.length);
//                    x += seriesSpace + seriesSize;
//                }
//                if (showLabel != null) {
//                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
//                    Dimension s = getLabelWidth(showLabel, g2);
//                    int space = 3;
//                    int spaceTop = 5;
//                    g2.setColor(new Color(30, 30, 30));
//                    g2.fillRoundRect(labelLocation.x - s.width / 2 - 3, labelLocation.y - s.height - space * 2 - spaceTop, s.width + space * 2, s.height + space * 2, 10, 10);
//                    g2.setColor(new Color(200, 200, 200));
//                    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
//                    g2.drawString(showLabel, labelLocation.x - s.width / 2, labelLocation.y - spaceTop - space * 2);
//                }
//                g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
//            }
//
//            @Override
//            public void renderSeries(BlankPlotChart chart, Graphics2D g2, SeriesSize size, int index, List<Path2D.Double> gra) {
//            }
//
//            @Override
//            public void renderGraphics(Graphics2D g2, List<Path2D.Double> gra) {
//
//            }
//
//            @Override
//            public boolean mouseMoving(BlankPlotChart chart, MouseEvent evt, Graphics2D g2, SeriesSize size, int index) {
//                double totalSeriesWidth = (seriesSize * legends.size()) + (seriesSpace * (legends.size() - 1));
//                double x = (size.getWidth() - totalSeriesWidth) / 2;
//                for (int i = 0; i < legends.size(); i++) {
//                    double seriesValues = chart.getSeriesValuesOf(model.get(index).getValues()[i], size.getHeight()) * animate;
//                    int s = seriesSize / 2;
//                    int sy = seriesSize / 3;
//                    int px[] = {(int) (size.getX() + x), (int) (size.getX() + x + s), (int) (size.getX() + x + seriesSize), (int) (size.getX() + x + seriesSize), (int) (size.getX() + x + s), (int) (size.getX() + x)};
//                    int py[] = {(int) (size.getY() + size.getHeight() - seriesValues), (int) (size.getY() + size.getHeight() - seriesValues - sy), (int) (size.getY() + size.getHeight() - seriesValues), (int) (size.getY() + size.getHeight()), (int) (size.getY() + size.getHeight() + sy), (int) (size.getY() + size.getHeight())};
//                    if (new Polygon(px, py, px.length).contains(evt.getPoint())) {
//                        double data = model.get(index).getValues()[i];
//                        showLabel = df.format(data);
//                        labelLocation.setLocation((int) (size.getX() + x + s), (int) (size.getY() + size.getHeight() - seriesValues - sy));
//                        chart.repaint();
//                        return true;
//                    }
//                    x += seriesSpace + seriesSize;
//                }
//                return false;
//            }
//        });