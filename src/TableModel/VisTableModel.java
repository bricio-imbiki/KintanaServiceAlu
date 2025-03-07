/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package TableModel;

import Components.ActionPanel;
import Components.CellStatus;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Map;
import Models.ModelVis;
import static TableModel.FactureTableModel.formatPrice;

public class VisTableModel extends AbstractTableModel {

    private List<ModelVis> VisList;
    private final String[] columnNames = {"N° Vis", "Modele Profile ","Type","Longueur_mm","Prix Unitaire","Quantite","Fournisseur","Etat du stock","Action"};
    private int fixedRowCount = 50;
     private final Map<Integer, ActionPanel> actionPanelCache = new HashMap<>();
    public VisTableModel() {
        VisList = new ArrayList<>();
    }
  
    public void setVisList(ArrayList<ModelVis> VisList) {
        this.VisList = VisList;
        fireTableDataChanged(); // Notify the table that the data has changed
    }

    @Override
    public int getRowCount() {
        return Math.max(fixedRowCount, VisList.size());
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
        if (rowIndex >= VisList.size()) {
            return "";
        }

        ModelVis Vis = VisList.get(rowIndex);
        switch (columnIndex) {
            case 0 : {
                return Vis.getVisId();
            }
            case 1 : {

                return Vis.getProfile().getModel();
            }
            case 2 : {
                return Vis.getTypeVis(); // Utiliser la date formatée
            }
            case 3 : {
              
                return Vis.getLongueurVis();
            }
            case 4 : {
                
                 return formatPrice(Vis.getPrixUnitaire()); 
            }
            case 5 : {
 
                return Vis.getQuantiteEnStock();
            }
            case 6 : {
             return Vis.getFournisseur().getNom();
            }
                       case 7 : {
                   return new CellStatus(Vis);
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
    public void removeVis(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= VisList.size()) {
            return; // Index out of bounds, do nothing
        }
        VisList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
  public ModelVis getVisAt(int row) {
    if (row < 0 || row >= VisList.size()) {
        return null; // Renvoie null si l'index est hors de la taille de la liste
    }
    return VisList.get(row);
}

    public void addVis(ModelVis Vis) {
        VisList.add(Vis);
        fireTableRowsInserted(VisList.size() - 1, VisList.size() - 1);
        if (VisList.size() > fixedRowCount) {
            fixedRowCount++;
        }
    }

    public void updateVis(int rowIndex, ModelVis Vis) {
        if (rowIndex < 0 || rowIndex >= VisList.size()) {
            return; // Index out of bounds, do nothing
        }
        VisList.set(rowIndex, Vis);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

 
    
}
