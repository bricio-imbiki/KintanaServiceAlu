package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class RoundedCardDashboard extends JPanel {

    private final int radius = 20; // Rayon des coins arrondis
    private Color defaultColor; // Couleur de fond par défaut
    private Color hoverColor = new Color(169, 169, 169, 150); // Couleur de bordure au survol (gris non opaque)
    private boolean isPressed = false;
    private final Color pressedColorBorder = new Color(115, 184, 255); // Couleur de bordure au clic
    private Color effectColor = new Color(200, 200, 200); // Couleur de fond lorsqu'enfoncé
    private final Color borderColorHover = new Color(100, 100, 100); // Couleur de la bordure au survol (gris foncé)

    private Animator animator;
    private int targetSize;
    private float animatSize;
    private Point pressedPoint;
    private float alpha;

    public RoundedCardDashboard(String text, Color color) {
        super();
        this.defaultColor = color; // Définir la couleur de fond par défaut

        setOpaque(false); // Pour rendre la couleur de fond transparente
        setBackground(defaultColor); // Initialiser la couleur de fond

        // Configurer l'animateur pour l'effet de clic
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
        animator = new Animator(300, target); // Durée plus courte pour l'animation
        animator.setAcceleration(0.7f); // Accélération
        animator.setDeceleration(0.3f); // Décélération
        animator.setResolution(0);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(hoverColor); // Changer la couleur de fond lors du survol
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Changer le curseur en main
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(defaultColor); // Revenir à la couleur de fond par défaut
                setCursor(Cursor.getDefaultCursor()); // Revenir au curseur par défaut
                repaint();
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                targetSize = Math.max(getWidth(), getHeight()) * 2;
                animatSize = 0;
                pressedPoint = e.getPoint();
                alpha = 0.5f;
                if (animator.isRunning()) {
                    animator.stop();
                }
                animator.start();
                setBackground(defaultColor); // Changer la couleur de fond lors du clic
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                setBackground(defaultColor); // Revenir à la couleur de fond par défaut après le clic
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(getBackground()); // Utiliser la couleur de fond du panneau
        g2d.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);

        // Définir le clip pour s'assurer que rien n'est dessiné en dehors du panneau
        g2d.setClip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));

        if (isPressed && pressedPoint != null) {
            g2d.setColor(pressedColorBorder); // Couleur de la bordure lors du clic
            g2d.setStroke(new BasicStroke(2)); // Épaisseur de la bordure
            g2d.drawRoundRect(1, 1, getWidth() - 2, getHeight() - 2, radius, radius);

            // Dessiner l'effet de clic à l'intérieur du panneau
            g2d.setClip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), radius, radius));
            g2d.setColor(effectColor); // Couleur de l'effet de clic
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));
            g2d.fillOval((int) (pressedPoint.x - animatSize / 2), (int) (pressedPoint.y - animatSize / 2), (int) animatSize, (int) animatSize);
        }
        super.paintComponent(g);
        g2d.dispose();
    }
}
