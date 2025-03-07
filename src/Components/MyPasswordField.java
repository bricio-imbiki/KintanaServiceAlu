package Components;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JPasswordField;
import javax.swing.KeyStroke;
import javax.swing.UIManager;

public class MyPasswordField extends JPasswordField {

    public enum IconPosition {
        LEFT, RIGHT
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public Icon getPrefixIcon() {
        return prefixIcon;
    }

    public void setPrefixIcon(Icon prefixIcon) {
        this.prefixIcon = prefixIcon;
        initBorder();
    }

    public Icon getSuffixIcon() {
        return suffixIcon;
    }

    public void setSuffixIcon(Icon suffixIcon) {
        this.suffixIcon = suffixIcon;
        initBorder();
    }

    public IconPosition getPrefixIconPosition() {
        return prefixIconPosition;
    }

    public void setPrefixIconPosition(IconPosition prefixIconPosition) {
        this.prefixIconPosition = prefixIconPosition;
        initBorder();
    }

    public IconPosition getSuffixIconPosition() {
        return suffixIconPosition;
    }

    public void setSuffixIconPosition(IconPosition suffixIconPosition) {
        this.suffixIconPosition = suffixIconPosition;
        initBorder();
    }

    private Icon prefixIcon;
    private Icon suffixIcon;
    private IconPosition prefixIconPosition = IconPosition.LEFT;
    private IconPosition suffixIconPosition = IconPosition.RIGHT;
    private String hint = "";
    private boolean passwordVisible = false;
    private Icon visibleIcon = new ImageIcon(getClass().getResource("/Icon/icons8-visible-24.png"));
    private Icon invisibleIcon = new ImageIcon(getClass().getResource("/Icon/icons8-invisible-24.png"));

    public MyPasswordField() {
        setOpaque(false);
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setBackground(new Color(0, 0, 0, 0));
        setForeground(Color.decode("#7A8C8D"));
        setFont(new java.awt.Font("sansserif", 0, 13));
        setSelectionColor(new Color(75, 175, 152));

        setSuffixIcon(invisibleIcon);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (isSuffixIconClicked(e)) {
                    togglePasswordVisibility();
                }
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (isSuffixIconClicked(e)) {
                    setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
                } else {
                    setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
                }
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
    private boolean isSuffixIconClicked(MouseEvent e) {
        if (suffixIcon != null) {
            int iconWidth = suffixIcon.getIconWidth();
            int iconHeight = suffixIcon.getIconHeight();
            int iconX = getWidth() - getInsets().right - iconWidth + 25; // Adjust to match the actual drawing position
            int iconY = (getHeight() - iconHeight) / 2;
            return e.getX() >= iconX && e.getX() <= iconX + iconWidth &&
                   e.getY() >= iconY && e.getY() <= iconY + iconHeight;
        }
        return false;
    }

    private void togglePasswordVisibility() {
        passwordVisible = !passwordVisible;
        setSuffixIcon(passwordVisible ? visibleIcon : invisibleIcon);
        setEchoChar(passwordVisible ? '\u0000' : (Character) UIManager.get("PasswordField.echoChar"));
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(new Color(240, 240, 240));
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), getHeight(), getWidth());
        paintIcon(g);
        super.paintComponent(g);
    }

  @Override
public void paint(Graphics g) {
    super.paint(g);
    if (getText().length() == 0) {
        int h = getHeight();
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        Insets ins = getInsets();
        FontMetrics fm = g.getFontMetrics();
        g.setColor(new Color(200, 200, 200));  // Hint text color
        
        // Adjust 'x' to position the hint text closer to the blinking cursor
        int cursorWidth = 2; // Rough width of the blinking cursor
        int padding = 1/2;     // Padding between the hint and the cursor
        int x = ins.left + cursorWidth + padding; // Add cursor width and padding
        
        if (prefixIcon == null && suffixIcon != null && suffixIconPosition == IconPosition.LEFT) {
            x += suffixIcon.getIconWidth() + 5;
        } else if (prefixIcon != null && prefixIconPosition == IconPosition.LEFT) {
            x += prefixIcon.getIconWidth() + 5;
        }
        
        g.drawString(hint, x, h / 2 + fm.getAscent() / 2 - 2); // Draw the hint text
    }
}


    private void paintIcon(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        if (prefixIcon != null) {
            Image prefix = ((ImageIcon) prefixIcon).getImage();
            int y = (getHeight() - prefixIcon.getIconHeight()) / 2;
            if (prefixIconPosition == IconPosition.LEFT) {
                g2.drawImage(prefix, 10, y, this);
            } else {
                g2.drawImage(prefix, getWidth() - prefixIcon.getIconWidth() - 10, y, this);
            }
        }
        if (suffixIcon != null) {
            Image suffix = ((ImageIcon) suffixIcon).getImage();
            int y = (getHeight() - suffixIcon.getIconHeight()) / 2;
            if (suffixIconPosition == IconPosition.RIGHT) {
                g2.drawImage(suffix, getWidth() - suffixIcon.getIconWidth() - 10, y, this);
            } else {
                g2.drawImage(suffix, 10, y, this);
            }
        }
    }

    private void initBorder() {
        int left = 15;
        int right = 15;
        if (prefixIcon != null && prefixIconPosition == IconPosition.LEFT) {
            left = prefixIcon.getIconWidth() + 15;
        }
        if (suffixIcon != null && suffixIconPosition == IconPosition.RIGHT) {
            right = suffixIcon.getIconWidth() + 15;
        }
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, left, 10, right));
    }
}
