package Components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class ActionButton extends JButton {

    private boolean mousePress;

    public ActionButton() {
        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(3, 2, 3, 15));
//        setHorizontalAlignment(SwingConstants.LEFT); // Aligner l'icône à gauche
//        setHorizontalTextPosition(SwingConstants.RIGHT); // Positionner le texte à droite de l'icône
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                mousePress = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent me) {
                mousePress = false;
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int arcSize = height; // Taille de l'arc pour arrondir les coins

        if (mousePress) {
            g2.setColor(new Color(158, 158, 158));
        } else {
            g2.setColor(new Color(199, 199, 199));
        }

        // Dessiner un rectangle arrondi autour de l'ensemble du bouton
        g2.fill(new RoundRectangle2D.Double(0, 0, width, height, arcSize, arcSize));

        g2.dispose();
        super.paintComponent(grphcs);
    }
}
