/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class RoundedButton extends JButton {

       private Color effectColor = new Color(255, 255, 255);
    
    private Color currentBackgroundColor;
    private Animator animator;
    private int targetSize;
    private float animatSize;
    private Point pressedPoint;
    private float alpha;

    public RoundedButton (String text, Color initialColor, Color hoverColor) {
        super(text);
          setFocusPainted(false); 
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(5, 0, 5, 0));
        setBackground(initialColor);
        currentBackgroundColor = initialColor;
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(currentBackgroundColor);
            }

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
                 setFocusable(false);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                currentBackgroundColor = initialColor;
                setBackground(currentBackgroundColor);
                 // Réinitialiser le focus après le clic
               setFocusable(true);
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
@Override
public void setText(String text) {
    super.setText(text);
    repaint(); // Force le repaint du bouton pour afficher le nouveau texte
}
  @Override
protected void paintComponent(Graphics g) {
      int width = getWidth();
    int height = getHeight();
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    // Dessiner le fond du bouton
    g2.setColor(getBackground());
    g2.fillRoundRect(0, 0, width, height, height, width);

    // Définir le clip pour s'assurer que rien n'est dessiné en dehors du bouton
    g2.setClip(new RoundRectangle2D.Double(0, 0, width, height, height, width));

    
    if (pressedPoint != null) {
        g2.setColor(effectColor);
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
        g2.fillOval((int) (pressedPoint.x - animatSize / 2), (int) (pressedPoint.y - animatSize / 2), (int) animatSize, (int) animatSize);
    }
    super.paintComponent(g);
    g2.dispose();
 
}

  
    
    
}






