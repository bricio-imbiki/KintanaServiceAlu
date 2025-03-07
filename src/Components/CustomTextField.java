package Components;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import com.formdev.flatlaf.FlatLaf;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class CustomTextField extends JTextField {
    private String hint;
    private Icon icon;
    private Color borderColor;
    private Color focusBorderColor = new Color(135, 206, 250); // Sky Blue color
// private Color defaultColor = new Color(100, 149, 237); // Default color: Blue
//    private Color hoverColor = new Color(115, 184, 255); // Hover color: Light Blue
//    private Color focusBorderColor = new Color(135, 206, 250); // Sky Blue color

    private Color textColor;
    private Color hintColor;
    private Font hintFont;

    public CustomTextField(String hint, Icon icon) {
        this.hint = hint;
        this.icon = icon;
        setOpaque(false);
        setBorder(new EmptyBorder(10, 10, 10, 40)); // Marges internes pour le texte
        setFont(new Font("Dialog", Font.BOLD, 15)); // Police du texte normal

        // Définir la police pour le texte d'indication (gras)
        hintFont = new Font("Dialog", Font.PLAIN, 14); 

        // Initialisation des couleurs
        updateColors();

        // Placeholder behavior
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                borderColor = focusBorderColor;
                setForeground(textColor); // Couleur du texte lorsqu'on tape
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                borderColor = Color.GRAY;
                if (getText().isEmpty()) {
                    setForeground(hintColor); // Couleur du texte d'indication
                }
                repaint();
            }
        });
     addKeyBindings();
    }

    private void addKeyBindings() {
        // Key binding for DOWN arrow key
        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "moveFocusDown");
        getActionMap().put("moveFocusDown", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transferFocus();  // Move focus to the next component
            }
        });

        // Key binding for UP arrow key
        getInputMap(JComponent.WHEN_FOCUSED).put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "moveFocusUp");
        getActionMap().put("moveFocusUp", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transferFocusBackward();  // Move focus to the previous component
            }
        });
    }
    // Méthode pour mettre à jour les couleurs en fonction du thème
    private void updateColors() {
        if (FlatLaf.isLafDark()) {
            hintColor = new Color(170, 170, 170, 128); // Couleur du texte d'indication dans le thème sombre
            textColor = Color.WHITE; // Couleur du texte dans le thème sombre
            borderColor = Color.GRAY; // Couleur de la bordure dans le thème sombre
        } else {
            hintColor = new Color(170, 170, 170, 128); // Couleur du texte d'indication dans le thème clair
            textColor = Color.BLACK; // Couleur du texte dans le thème clair
            borderColor = Color.GRAY; // Couleur de la bordure dans le thème clair
        }
        setForeground(textColor); 
        setBackground(new Color(0, 0, 0, 0)); // Transparent pour le fond
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessiner les coins arrondis et la bordure
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

        // Dessiner l'icône avec la couleur adaptée
        if (icon != null) {
            Icon coloredIcon = colorizeIcon(icon, borderColor);
            int iconY = (getHeight() - coloredIcon.getIconHeight()) / 2;
            int iconX = getWidth() - coloredIcon.getIconWidth() - 10;
            coloredIcon.paintIcon(this, g2, iconX, iconY);
        }

        // Dessiner le texte d'indication
        if (getText().isEmpty() && !isFocusOwner()) {
            g2.setFont(hintFont); // Utiliser la police en gras pour le texte d'indication
            g2.setColor(hintColor); // Utiliser la couleur d'indication
            g2.drawString(hint, 10, getHeight() / 2 + getFont().getSize() / 2 - 2);
        }

        g2.dispose();
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(borderColor); // Utiliser la couleur de la bordure en fonction du focus
        g2.setStroke(new BasicStroke(isFocusOwner() ? 2 : 1)); // Épaisseur de la bordure
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
        g2.dispose();
    }

    // Méthode pour recolorer l'icône
    private Icon colorizeIcon(Icon icon, Color color) {
        if (icon == null) return null;

        Image image = ((ImageIcon) icon).getImage();
        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = bufferedImage.createGraphics();
        g2.drawImage(image, 0, 0, null);
        g2.setComposite(AlphaComposite.SrcAtop.derive(1.0f)); // Appliquer le filtre alpha
        g2.setColor(color);
        g2.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        g2.dispose();

        return new ImageIcon(bufferedImage);
    }
}
