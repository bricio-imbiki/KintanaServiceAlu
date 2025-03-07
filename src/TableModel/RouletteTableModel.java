/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TableModel;

import Components.ActionPanel;
import Components.CellStatus;
import Models.ModelRoulette;
import static TableModel.FactureTableModel.formatPrice;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Map;

public class RouletteTableModel extends AbstractTableModel {

    private List<ModelRoulette> RouletteList;
    private final String[] columnNames = {"N° Roulette", "Modele Profile ","Type","taille","Prix Unitaire","Quantite","Fournisseur","Etat du stock","Action"};
    private int fixedRowCount = 50;
     private final Map<Integer, ActionPanel> actionPanelCache = new HashMap<>();
    public RouletteTableModel() {
        RouletteList = new ArrayList<>();
    }
  
    public void setRouletteList(ArrayList<ModelRoulette> RouletteList) {
        this.RouletteList = RouletteList;
        fireTableDataChanged(); // Notify the table that the data has changed
    }

    @Override
    public int getRowCount() {
        return Math.max(fixedRowCount, RouletteList.size());
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
        if (rowIndex >= RouletteList.size()) {
            return "";
        }

        ModelRoulette Roulette = RouletteList.get(rowIndex);
        switch (columnIndex) {
            case 0 : {
                return Roulette.getRouletteId();
            }
            case 1 : {

                return Roulette.getProfile().getModel();
            }
            case 2 : {
                return Roulette.getTypeRoulette(); // Utiliser la date formatée
            }
            case 3 : {
              
                return Roulette.getTailleRoulette();
            }
            case 4 : {
                
                 return formatPrice(Roulette.getPrixUnitaire()); 
            }
            case 5 : {
 
                return Roulette.getQuantiteEnStock();
            }
            case 6 : {
             return Roulette.getFournisseur().getNom();
            }
              case 7 : {
                   return new CellStatus(Roulette);
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
    public void removeRoulette(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= RouletteList.size()) {
            return; // Index out of bounds, do nothing
        }
        RouletteList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
  public ModelRoulette getRouletteAt(int row) {
    if (row < 0 || row >= RouletteList.size()) {
        return null; // Renvoie null si l'index est hors de la taille de la liste
    }
    return RouletteList.get(row);
}

    public void addRoulette(ModelRoulette Roulette) {
        RouletteList.add(Roulette);
        fireTableRowsInserted(RouletteList.size() - 1, RouletteList.size() - 1);
        if (RouletteList.size() > fixedRowCount) {
            fixedRowCount++;
        }
    }

    public void updateRoulette(int rowIndex, ModelRoulette Roulette) {
        if (rowIndex < 0 || rowIndex >= RouletteList.size()) {
            return; // Index out of bounds, do nothing
        }
        RouletteList.set(rowIndex, Roulette);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

 
    
}
