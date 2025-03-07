/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TableModel;

import Components.ActionPanel;
import Components.CellStatus;
import Models.ModelPoignee;
import static TableModel.FactureTableModel.formatPrice;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Map;

public class PoigneeTableModel extends AbstractTableModel {

    private List<ModelPoignee> PoigneeList;
    private final String[] columnNames = {"N° Poignee", "Modele Profile",  "Type","Couleur","Prix Unitaire","Quantite","Fournisseur","Etat du stock","Action"};
    private int fixedRowCount = 50;
     private final Map<Integer, ActionPanel> actionPanelCache = new HashMap<>();
    public PoigneeTableModel() {
        PoigneeList = new ArrayList<>();
    }
  
    public void setPoigneeList(ArrayList<ModelPoignee> PoigneeList) {
        this.PoigneeList = PoigneeList;
        fireTableDataChanged(); // Notify the table that the data has changed
    }

    @Override
    public int getRowCount() {
        return Math.max(fixedRowCount, PoigneeList.size());
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

       @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= PoigneeList.size()) {
            return "";
        }

        ModelPoignee Poignee = PoigneeList.get(rowIndex);
        switch (columnIndex) {
            case 0 : {
                return Poignee.getPoigneeId();
            }
            case 1 : {
 
                return Poignee.getProfile().getModel();
              
            }
            case 2 : {
               return Poignee.getType(); // Utiliser la date formatée
            }
            case 3 : {
              
                    return Poignee.getCouleur(); 
            }
            case 4 : {
                
                  return  formatPrice(Poignee.getPrixUnitaire());
            }
          
            case 5 : {
 
                return Poignee.getQuantiteEnStock();
            }
            case 6 : {
             return Poignee.getFournisseur().getNom();
            }
                case 7 : {
                   return new CellStatus(Poignee);
            }
               case 8 : {
 
                return actionPanelCache.computeIfAbsent(rowIndex, r -> new ActionPanel()); // Utiliser un cache
            }
            default : {
                return null;
            }
        }
    }


    @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 8; // Seulement la colonne "Actions" est éditable
    }
    public void removePoignee(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= PoigneeList.size()) {
            return; // Index out of bounds, do nothing
        }
        PoigneeList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
  public ModelPoignee getPoigneeAt(int row) {
    if (row < 0 || row >= PoigneeList.size()) {
        return null; // Renvoie null si l'index est hors de la taille de la liste
    }
    return PoigneeList.get(row);
}

    public void addPoignee(ModelPoignee Poignee) {
        PoigneeList.add(Poignee);
        fireTableRowsInserted(PoigneeList.size() - 1, PoigneeList.size() - 1);
        if (PoigneeList.size() > fixedRowCount) {
            fixedRowCount++;
        }
    }

    public void updatePoignee(int rowIndex, ModelPoignee Poignee) {
        if (rowIndex < 0 || rowIndex >= PoigneeList.size()) {
            return; // Index out of bounds, do nothing
        }
        PoigneeList.set(rowIndex, Poignee);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

 
    
}
