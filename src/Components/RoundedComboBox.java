package Components;

import Scroll.ScrollBarCustom;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.LineBorder;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.ComboPopup;

public class RoundedComboBox<E> extends JComboBox<E> {
    // Constructeur par défaut
    public RoundedComboBox() {
        super(); // Appeler le constructeur par défaut de JComboBox
        setOpaque(false);
        setUI(new RoundedComboBoxUI());
        setFocusable(false);
        setBackground(new Color(0, 0, 0, 0));
    }

    public RoundedComboBox(E[] items) {
        super(items);
        setOpaque(false); // Rendre le composant transparent pour permettre le rendu personnalisé
        setUI(new RoundedComboBoxUI());
        setFocusable(false);
        setBackground(new Color(0, 0, 0, 0));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Définir la couleur de fond et arrondir les coins
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getWidth());

        // Dessiner le texte et les autres éléments par-dessus
        super.paintComponent(g);

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Définir la couleur de la bordure selon l'élément sélectionné
        if (getSelectedIndex() != 0) {
            g2.setColor(new Color(135, 206, 250));
            g2.setStroke(new BasicStroke(2));// Bleu ciel si l'index sélectionné n'est pas 0
        } else {
            g2.setColor(new Color(128, 128, 128)); // Couleur par défaut sinon
        }

        g2.drawRoundRect(1, 0, getWidth()- 2  , getHeight() - 1 , getHeight(), getWidth());
        g2.dispose();
    }

    @Override
    public void setPopupVisible(boolean visible) {
        super.setPopupVisible(visible);
    }

    private class RoundedComboBoxUI extends BasicComboBoxUI {
        @Override
        protected JButton createArrowButton() {
            JButton arrowButton = new JButton() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2 = (Graphics2D) g.create();
                    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                    // Définir la couleur de la flèche
                    g2.setColor(getForeground());

                    // Coordonnées pour dessiner la flèche vers le bas
                    int x = getWidth() / 2 - 4;  // Position X pour débuter la flèche
                    int y = getHeight() / 2 - 3; // Position Y pour la hauteur de la flèche
                    int size = 4;

                    // Dessiner la flèche
                    g2.drawLine(x, y, x + size, y + size);
                    g2.drawLine(x + size, y + size, x + 2 * size, y);

                    g2.dispose();
                }
            };

            arrowButton.setBorder(BorderFactory.createEmptyBorder());
            arrowButton.setContentAreaFilled(false);
            arrowButton.setFocusPainted(false);
            arrowButton.setOpaque(false);
            arrowButton.setForeground(Color.GRAY);

            // Ajouter un écouteur pour changer la couleur au survol et clic
            arrowButton.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    arrowButton.setForeground(Color.WHITE); // Changer la couleur au survol
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent evt) {
                    arrowButton.setForeground(Color.GRAY); // Restaurer la couleur quand la souris quitte
                }

                @Override
                public void mousePressed(java.awt.event.MouseEvent evt) {
                    arrowButton.setForeground(Color.WHITE); // Changer la couleur au clic
                }

                @Override
                public void mouseReleased(java.awt.event.MouseEvent evt) {
                    arrowButton.setForeground(Color.GRAY); // Restaurer la couleur après le clic
                }
            });

            return arrowButton;
        }
  @Override
        protected ComboPopup createPopup() {
            BasicComboPopup pop = new BasicComboPopup(comboBox) {
                @Override
                protected JScrollPane createScroller() {
                    list.setFixedCellHeight(25);
                    JScrollPane scroll = new JScrollPane(list);
                   // scroll.setBackground(Color.WHITE);
                    ScrollBarCustom sb = new ScrollBarCustom();
                    sb.setUnitIncrement(30);
                    sb.setForeground(new Color(180,  180, 180));
                    scroll.setVerticalScrollBar(sb);
                    return scroll;
                }
            };
   pop.setBorder(new LineBorder(new Color(128, 128, 128), 1));
            return pop;
        }
        @Override
        public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
            // Empêcher le fond du bouton d'être peint
        }
    }

    @Override
    public Dimension getPreferredSize() {
        Dimension size = super.getPreferredSize();
        size.width += 15; // Ajuster la hauteur pour inclure les coins arrondis
        return size;
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                return c;
            }
        });
    }
}
