/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

import java.awt.Color;
import java.awt.Component;
import javax.swing.BorderFactory;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author brici_6ul2f65
 */
public class StatusCellRenderer extends DefaultTableCellRenderer {

   
   
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        // Réinitialiser le fond et la bordure
      Component com = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        // Si la valeur est une instance de ActionPanel, utiliser ce panneau
        if (value instanceof CellStatus) {
            CellStatus statusPanel = ( CellStatus) value;
         
           if (isSelected == false ) {
              statusPanel.setBackground(com.getBackground());
        } else {
              statusPanel.setBackground(table.getSelectionBackground());
        }
     
            return statusPanel;
      
        }
        
        // Pour les autres valeurs, retourner le panneau avec une couleur de fond par défaut
        return this;
    }
}

