package Components;

import com.formdev.flatlaf.FlatLaf;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

public class CustomSearchField extends JTextField {
    private String hint;
    private Icon searchIcon;
    private Icon clearIcon;
    private Icon currentIcon;
    private Color borderColor;
    private Color focusBorderColor = new Color(135, 206, 250); // Sky Blue color
    private Color textColor;
    private Color hintColor;
    private Font hintFont;
    private boolean showClearIcon = false;
    private boolean isHoveringClearIcon = false; // For hover detection

    public CustomSearchField(String hint, Icon searchIcon, Icon clearIcon) {
        this.hint = hint;
        this.searchIcon = searchIcon;
        this.clearIcon = clearIcon;
        this.currentIcon = searchIcon; // Default icon is the search icon
        setOpaque(false);
        setBorder(new EmptyBorder(10, 10, 10, 40)); // Internal margins for text
        setFont(new Font("Dialog", Font.BOLD, 15)); // Regular text font

        // Font for the hint text (italic)
        hintFont = new Font("Dialog", Font.PLAIN, 14);

        // Initialize colors
        updateColors();

        // Placeholder behavior and icon toggle
        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                borderColor = focusBorderColor;
                setForeground(textColor); // Color when typing
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                borderColor = Color.GRAY;
                if (getText().isEmpty()) {
                    setForeground(hintColor); // Color for hint
                }
                repaint();
            }
        });

        // Key listener to change the icon based on input
addKeyListener(new KeyAdapter() {
    @Override
    public void keyReleased(KeyEvent e) {
        if (getText().isEmpty()) {
            showClearIcon = false;
            currentIcon = searchIcon; // Always show the search icon if no text
        } else {
            showClearIcon = true;
            currentIcon = clearIcon; // Change icon to clear when text is present
        }
        repaint();
    }
});


      addMouseMotionListener(new MouseMotionAdapter() {
    @Override
    public void mouseMoved(MouseEvent e) {
        int iconX = getWidth() - currentIcon.getIconWidth() - 10;

        // If hovering over the clear icon, show hand cursor
        if (showClearIcon && e.getX() >= iconX) {
            setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); // Hand cursor for icon
            isHoveringClearIcon = true;
        }
        // If hovering over the text field, show text cursor
        else if (e.getX() < iconX) {
            setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR)); // Text cursor for text area
            isHoveringClearIcon = false;
        }
        // Default cursor for other areas
        else {
            setCursor(Cursor.getDefaultCursor()); // Default cursor for non-interactive area
            isHoveringClearIcon = false;
        }
        repaint();
    }
});


        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int iconX = getWidth() - currentIcon.getIconWidth() - 10;
                if (showClearIcon && e.getX() >= iconX) {
                    setText("");
                    showClearIcon = false;
                    currentIcon = searchIcon;
                    repaint();
                }
            }
        });
    }

    // Method to update colors based on the theme
    private void updateColors() {
        if (FlatLaf.isLafDark()) {
            hintColor = new Color(170, 170, 170, 128); // Hint color in dark theme
            textColor = Color.WHITE; // Text color in dark theme
            borderColor = Color.GRAY; // Border color in dark theme
        } else {
            hintColor = new Color(170, 170, 170, 128); // Hint color in light theme
            textColor = Color.BLACK; // Text color in light theme
            borderColor = Color.GRAY; // Border color in light theme
        }
        setForeground(textColor);
        setBackground(new Color(0, 0, 0, 0)); // Transparent background
    }

@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    // Draw rounded corners and border
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    g2.setColor(getBackground());
    g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getWidth());

    // Draw the search or clear icon (icon should always be displayed)
    // Draw icon (either search or clear icon)
        if (currentIcon != null ) {
            Icon coloredIcon = colorizeIcon(currentIcon, borderColor); //  isHoveringClearIcon ? Color.RED : borderColorRed icon on hover
        int iconY = (getHeight() - coloredIcon.getIconHeight()) / 2;
        int iconX = getWidth() - coloredIcon.getIconWidth() - 10;
        coloredIcon.paintIcon(this, g2, iconX, iconY);

        // Draw vertical separator only if there is text
        if (!getText().isEmpty()) {
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRect(iconX - 10, 10, 1, getHeight() - 20);
        }
    }

    // Draw the hint text if field is empty and not focused
    if (getText().isEmpty() && !isFocusOwner()) {
        g2.setFont(hintFont); // Hint font
        g2.setColor(hintColor); // Hint color
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
        g2.drawRoundRect(1, 0, getWidth()- 2  , getHeight() - 1 , getHeight(), getWidth());
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
