package Chart;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import javax.swing.border.EmptyBorder;

public final class YAxisPanel extends JComponent {

    private NiceScale niceScale;
    private double maxValues;
    private double minValues;
    private final BlankPlotChart chart;

    public double getMaxValues() {
        return maxValues;
    }

    public void setMaxValues(double maxValues) {
        this.maxValues = maxValues;
        niceScale.setMax(maxValues);
        repaint();
    }

    public double getMinValues() {
        return minValues;
    }

    public YAxisPanel() {
        chart = new BlankPlotChart();
        initValues(0, 10);
        setBorder(new EmptyBorder(35, 15, 45, 0));
        setPreferredSize(new Dimension(50, chart.getHeight())); // Adjust the preferred size based on space requirements
    }

    public void initValues(double minValues, double maxValues) {
        this.minValues = minValues;
        this.maxValues = maxValues;
        niceScale = new NiceScale(minValues, maxValues);
        repaint();
    }

    public void setNiceScale(NiceScale niceScale) {
        this.niceScale = niceScale;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (niceScale != null) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_LCD_HRGB);
            createValues(g2);
        }
    }

//    private void createValues(Graphics2D g2) {
//        g2.setColor(getForeground());
//        Insets insets = getInsets();
//        double height = getHeight() - (insets.top + insets.bottom);
//        double space = height / niceScale.getMaxTicks();
//        double valuesCount = niceScale.getNiceMin();
//        double locationY = insets.bottom;
//
//        FontMetrics ft = g2.getFontMetrics();
//        for (int i = 0; i <= niceScale.getMaxTicks(); i++) {
//            // Cast valuesCount to an integer to display whole numbers
//            String text = Integer.toString((int) valuesCount);
//            Rectangle2D r2 = ft.getStringBounds(text, g2);
//            double stringY = r2.getCenterY() * -1;
//            double y = getHeight() - locationY + stringY;
//            g2.drawString(text, insets.left, (int) y);
//            locationY += space;
//            valuesCount += niceScale.getTickSpacing();
//        }
//    }
       private int getLabelTextHeight(Graphics2D g2) {
        FontMetrics ft = g2.getFontMetrics();
        return ft.getHeight();
    }

        private void createValues(Graphics2D g2) {
        g2.setColor(getForeground());
        Insets insets = getInsets();
        double textHeight = getLabelTextHeight(g2);
        double height = getHeight() - (insets.top + insets.bottom) - textHeight;
        double space = height / niceScale.getMaxTicks();
        double valuesCount = niceScale.getNiceMin();
        double locationY = insets.bottom + textHeight;
        FontMetrics ft = g2.getFontMetrics();
        for (int i = 0; i <= niceScale.getMaxTicks(); i++) {
           String text = Integer.toString((int) valuesCount);
            Rectangle2D r2 = ft.getStringBounds(text, g2);
            double stringY = r2.getCenterY() * -1;
            double y = getHeight() - locationY + stringY;
            g2.drawString(text, insets.left, (int) y);
            locationY += space;
            valuesCount += niceScale.getTickSpacing();
        }
    }
}
