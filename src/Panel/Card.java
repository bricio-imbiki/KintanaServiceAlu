package Panel;

import Models.ModelCard;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;

import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;
public class Card extends javax.swing.JPanel {

    private Color color1;
    private Color color2;
    private Color colorGradient;
   private Animator animator;
    private int targetSize;
    private float animatSize;
    private Point pressedPoint;
    private float alpha;
 private final Color effectColor = new Color(255, 255, 255);
    public Card() {
        initComponents();
        setOpaque(false);
          setOpaque(false);
        setBackground(new Color(112, 69, 246));
        colorGradient = new Color(255, 255, 255);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                targetSize = Math.max(getWidth(), getHeight()) * 2;
                animatSize = 0;
                pressedPoint = me.getPoint();
                alpha = 0.5f;
                if (animator.isRunning()) {
                    animator.stop();
                }
                animator.start();
            }

          
        });

        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                if (fraction > 0.5f) {
                    alpha = 1 - fraction;
                }
                animatSize = fraction * targetSize;
                repaint();
            }
        };

        animator = new Animator(300, target);  // Reduced duration for faster effect
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.setResolution(0);
    }
    

    public Color getColorGradient() {
        return colorGradient;
    }

    public void setColorGradient(Color colorGradient) {
        this.colorGradient = colorGradient;
    }

    public Color getColor1() {
        return color1;
    }

    public void setColor1(Color color1) {
        this.color1 = color1;
    }

    public Color getColor2() {
        return color2;
    }

    public void setColor2(Color color2) {
        this.color2 = color2;
    }

    public void setData(ModelCard data) {
        lbIcon.setIcon(data.getIcon());
        lbTitle.setText(data.getTitle());
        lbValues.setText(data.getValues());

    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbTitle = new javax.swing.JLabel();
        lbValues = new javax.swing.JLabel();
        lbIcon = new javax.swing.JLabel();

        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        lbTitle.setFont(new java.awt.Font("SansSerif", 1, 18)); // NOI18N
        lbTitle.setForeground(new java.awt.Color(225, 225, 225));
        lbTitle.setText("Title");

        lbValues.setFont(new java.awt.Font("sansserif", 1, 24)); // NOI18N
        lbValues.setForeground(new java.awt.Color(225, 225, 225));
        lbValues.setText("Values");

        lbIcon.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbIcon.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbTitle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lbValues, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lbTitle, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                        .addComponent(lbValues))
                    .addComponent(lbIcon, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );
    }// </editor-fold>//GEN-END:initComponents

    @Override
    protected void paintComponent(Graphics grphcs) {
        int width = getWidth();
        int height = getHeight();
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Gradient de fond
//        GradientPaint g = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
//        g2.setPaint(g);
        GradientPaint g = new GradientPaint(0, getHeight(), getBackground(), getWidth(), 0, colorGradient);
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, width, height, 30, 30);

      
        g2.setClip(new RoundRectangle2D.Double(0, 0, width, height, 30, 30));
      
        g2.setColor(new Color(255, 255, 255, 50));

        // Dessine les ovales sans ajuster manuellement les tailles
        g2.fillOval(getWidth() - (getHeight() / 2), -10, getHeight(), getHeight());
        g2.fillOval(15, 2, getHeight(), getHeight());
        g2.fillOval(getWidth() - (getHeight() / 2), 50, getHeight() + 20, getHeight());
        g2.fillOval(getWidth() - getHeight(), getHeight() / 2 + 20, getHeight() + 20, getHeight());
   if (pressedPoint != null) {
        g2.setColor(effectColor);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        g2.fillOval((int) (pressedPoint.x - animatSize / 2), (int) (pressedPoint.y - animatSize / 2), (int) animatSize, (int) animatSize);
    }
        // Appel de la méthode paintComponent de la superclasse
        super.paintComponent(grphcs);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel lbIcon;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JLabel lbValues;
    // End of variables declaration//GEN-END:variables
}
