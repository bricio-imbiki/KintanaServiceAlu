package Components;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;

public class ActionCellRenderer extends DefaultTableCellRenderer {

   
   
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
   
      Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
       
        if (value instanceof ActionPanel) {
            ActionPanel actionPanel = (ActionPanel) value; 
            table.setShowGrid(true);
           if (isSelected == false ) {
              actionPanel.setBackground(com.getBackground());
        } else {
              actionPanel.setBackground(table.getSelectionBackground());
        }
            return actionPanel;
      
        }
        
      
        return this;
    }
}
