package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ActionPanel extends JPanel {
    public ActionButton editButton;
    public ActionButton deleteButton;

    public ActionPanel() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 2, 0, 2);

        editButton = new ActionButton();
        deleteButton = new ActionButton();

        // Set icons for the buttons
        editButton.setIcon(new ImageIcon(getClass().getResource("/Icon/icons8-edit-18 (3).png")));
        deleteButton.setIcon(new ImageIcon(getClass().getResource("/Icon/icons8-delete-18 (1).png")));

        // Set text for the buttons
        editButton.setText("Modifier");
        deleteButton.setText("Supprimer");

        // Set font and color for the buttons
        Font font = new Font("Arial", Font.BOLD, 12);
        editButton.setFont(font);
        deleteButton.setFont(font);
        editButton.setForeground(Color.BLACK);
        deleteButton.setForeground(Color.BLACK);

     

        // Add buttons to the panel
        add(editButton, gbc);
        gbc.gridx = 1;
        add(deleteButton, gbc);
    }

    public void initEvent(ActionTableEvent event, int row) {
        removeAllActionListeners(editButton);
        removeAllActionListeners(deleteButton);

        // Set event listeners
        editButton.addActionListener(e -> event.onEdit(row));
        deleteButton.addActionListener(e -> event.onDelete(row));
    }

    private void removeAllActionListeners(AbstractButton button) {
        for (ActionListener listener : button.getActionListeners()) {
            button.removeActionListener(listener);
        }
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        super.paintComponent(grphcs);
        grphcs.setColor(Color.GRAY);
        grphcs.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    }
        // Méthode pour réinitialiser l'état du panneau d'action
 
}
