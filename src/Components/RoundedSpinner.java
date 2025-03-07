package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.plaf.basic.BasicSpinnerUI;
import javax.swing.border.AbstractBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.border.EmptyBorder;

public class RoundedSpinner extends JSpinner {

    public RoundedSpinner() {
        super(new SpinnerNumberModel(0, 0, 100, 1)); // Exemple d'un modèle de nombre
        setUI(new RoundedSpinnerUI());
        setBorder(new RoundedBorder());
        setOpaque(false);
        setFont(new Font("Dialog", Font.BOLD, 15)); 
        setBackground(new Color(0, 0, 0, 0));

        // Ajout du gestionnaire de focus pour la bordure et le placement du curseur
        JTextField textField = ((JSpinner.DefaultEditor) this.getEditor()).getTextField();
        textField.setHorizontalAlignment(JTextField.RIGHT); // Place toujours le texte à droite
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(new FocusedBorder()); // Change la bordure lorsqu'il est focalisé
                // Place le curseur à la fin du texte
                SwingUtilities.invokeLater(() -> textField.setCaretPosition(textField.getText().length()));
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBorder(new RoundedBorder()); // Revenir à la bordure d'origine
            }
        });
    }

    private class RoundedSpinnerUI extends BasicSpinnerUI {

        @Override
        protected Component createNextButton() {
            return new ArrowButton(SwingConstants.NORTH);
        }

        @Override
        protected Component createPreviousButton() {
            return new ArrowButton(SwingConstants.SOUTH);
        }

        private class ArrowButton extends JButton {
            private final int direction;

            public ArrowButton(int direction) {
                super();
                this.direction = direction;
                setContentAreaFilled(false);
                setBorderPainted(false); // Pas de bordure
                setFocusPainted(false); // Pas de bordure de focus
                setOpaque(false); // Rend le bouton transparent
                // setBorder(new EmptyBorder(20,  20,20, 15)); // Espacement équilibré
                setPreferredSize(new Dimension(20, 15));
              
                 addActionListener(this::handleButtonClick);
            }

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int width = getWidth();
                int height = getHeight();

//                // Position des flèches ajustée pour être plus à gauche
//                int offsetX = 5; // Décalage à gauche
//                int arrowHeight = height-4; // Hauteur de la flèche
//                int arrowBase = (int) (height * 0.75); // Base de la flèche pour plus de centrage
//
//                if (direction == SwingConstants.NORTH) {
//                    g2.fillPolygon(
//                        new int[]{(width / 2) - offsetX, (width / 4) - offsetX, (3 * width / 4) - offsetX},
//                        new int[]{arrowHeight / 4, arrowBase, arrowBase},
//                        3
//                    );
//                } else if (direction == SwingConstants.SOUTH) {
//                    g2.fillPolygon(
//                        new int[]{(width / 2) - offsetX, (width / 4) - offsetX, (3 * width / 4) - offsetX},
//                        new int[]{arrowBase, arrowHeight / 4, arrowHeight / 4},
//                        3
//                    );
//                }
              
  

                 // Décalage horizontal à gauche
    int offsetX = 5; // Ajustez cette valeur pour décaler la flèche plus ou moins à gauche

   
    // Décalage vertical (pour rapprocher les deux flèches)
    int offsetY = -2; // Ajustez cette valeur pour coller les flèches

    if (direction == SwingConstants.NORTH) {
        g2.fillPolygon(
            new int[]{
                (width / 2) - offsetX, 
                (width / 4) - offsetX, 
                (3 * width / 4) - offsetX
            }, 
            new int[]{
                (height / 4) - offsetY, 
                (3 * height / 4) - offsetY, 
                (3 * height / 4) - offsetY
            }, 
            3
        );
    } else if (direction == SwingConstants.SOUTH) {
        g2.fillPolygon(
            new int[]{
                (width / 2) - offsetX, 
                (width / 4) - offsetX, 
                (3 * width / 4) - offsetX
            }, 
            new int[]{
                (3 * height / 4) + offsetY, 
                (height / 4) + offsetY, 
                (height / 4) + offsetY
            }, 
            3
        );
    }

                g2.dispose();
            }
               private void handleButtonClick(ActionEvent e) {
                if (direction == SwingConstants.NORTH) {
                    // Incrémente la valeur du spinner
                    ((JSpinner.DefaultEditor) RoundedSpinner.this.getEditor()).getTextField().setValue((Integer) RoundedSpinner.this.getValue() + 1);
                } else if (direction == SwingConstants.SOUTH) {
                    // Décrémente la valeur du spinner, mais s'arrête à 0
                    int currentValue = (Integer) RoundedSpinner.this.getValue();
                    if (currentValue > 0) {
                        ((JSpinner.DefaultEditor) RoundedSpinner.this.getEditor()).getTextField().setValue(currentValue - 1);
                    }
                }
            }
    
    
        }
    }

    // Bordure par défaut (arrondie)
    private static class RoundedBorder extends AbstractBorder {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(Color.GRAY); // Couleur de la bordure
            g2d.drawRoundRect(1, y, width - 2, height - 1, height, width); // Dessine la bordure
            g2d.dispose();
        }
    }

    // Bordure lors du focus (bleu ciel)
    private static class FocusedBorder extends AbstractBorder {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(new Color(135, 206, 250)); // Couleur bleu ciel
            g2d.drawRoundRect(1, y, width - 2, height - 1, height, width); // Dessine la bordure
            g2d.dispose();
        }
    }
}
