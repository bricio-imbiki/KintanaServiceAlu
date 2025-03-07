
package Components;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.util.EventObject;

public class ActionCellEditor extends DefaultCellEditor {
    private final ActionTableEvent event;
 
    private ActionPanel actionPanel;

    public ActionCellEditor(ActionTableEvent event) {
          super(new JCheckBox());
        this.event = event;
      
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        if (value instanceof ActionPanel) {
            actionPanel = new ActionPanel();
            actionPanel.initEvent(event, row);

            // Coloration uniforme des cellules d'action pendant l'édition
       
         
          
               actionPanel.setBackground(table.getSelectionBackground());
            
            return actionPanel;
        }
        return null;
    }

    @Override
    public Object getCellEditorValue() {
        return null;  // Retourne null car un nouveau ActionPanel est créé pour chaque édition
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        return true;
    }

    @Override
    public boolean shouldSelectCell(EventObject anEvent) {
        return true;
    }

    @Override
    public boolean stopCellEditing() {
        fireEditingStopped();
        return true;
    }

    @Override
    public void cancelCellEditing() {
        fireEditingCanceled();
    }
}
