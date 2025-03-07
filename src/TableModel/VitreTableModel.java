/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package TableModel;

import Components.ActionPanel;
import Components.CellStatus;
import Models.ModelTypeVitre;
import static TableModel.FactureTableModel.formatPrice;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Map;

public class VitreTableModel extends AbstractTableModel {

    private List<ModelTypeVitre> VitreList;
    private final String[] columnNames = {"N° Vitre", "Vitrage", "Type vitre", "Epaisseur ","Prix Unitaire","Quantite Stock","Fournisseur","Etat du stock","Action"};
    private int fixedRowCount = 50;
     private final Map<Integer, ActionPanel> actionPanelCache = new HashMap<>();
    public VitreTableModel() {
        VitreList = new ArrayList<>();
    }
  
    public void setVitreList(ArrayList<ModelTypeVitre> VitreList) {
        this.VitreList = VitreList;
        fireTableDataChanged(); // Notify the table that the data has changed
    }

    @Override
    public int getRowCount() {
        return Math.max(fixedRowCount, VitreList.size());
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
        if (rowIndex >= VitreList.size()) {
            return "";
        }

        ModelTypeVitre Vitre = VitreList.get(rowIndex);
        switch (columnIndex) {
            case 0 : {
                return Vitre.getTypeVitreId();
            }
            case 1 : {

                return Vitre.getVitrage().getTypeVitrage();
            }
            case 2 : {
                return Vitre.getType(); 
            }
            case 3 : {
              
                return Vitre.getEpaisseur()+" mm";
            }
            case 4 : {
                
                 return formatPrice(Vitre.getPrixUnitaire()); 
            }
              case 5: {
 
                return Vitre.getQuantiteEnStock();
            }
            case 6 : {
 
                return Vitre.getFournisseur().getNom();
            }
          
            case 7 : {
                   return new CellStatus(Vitre);
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
    public void removeVitre(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= VitreList.size()) {
            return; // Index out of bounds, do nothing
        }
        VitreList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
  public ModelTypeVitre getVitreAt(int row) {
    if (row < 0 || row >= VitreList.size()) {
        return null; // Renvoie null si l'index est hors de la taille de la liste
    }
    return VitreList.get(row);
}

    public void addVitre(ModelTypeVitre Vitre) {
        VitreList.add(Vitre);
        fireTableRowsInserted(VitreList.size() - 1, VitreList.size() - 1);
        if (VitreList.size() > fixedRowCount) {
            fixedRowCount++;
        }
    }

    public void updateVitre(int rowIndex, ModelTypeVitre Vitre) {
        if (rowIndex < 0 || rowIndex >= VitreList.size()) {
            return; // Index out of bounds, do nothing
        }
        VitreList.set(rowIndex, Vitre);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

 
    
    
}
