/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TableModel;



import Components.ActionPanel;
import Components.CellStatus;
import Models.ModelPaumelle;
import static TableModel.FactureTableModel.formatPrice;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Map;

public class PaumelleTableModel extends AbstractTableModel {

    private List<ModelPaumelle> PaumelleList;
    private final String[] columnNames = {"N° Paumelle", "Modele Profile", "Type", "Couleur","Prix Unitaire","Quantite","Fournisseur","Etat du stock","Action"};
    private int fixedRowCount = 50;
     private final Map<Integer, ActionPanel> actionPanelCache = new HashMap<>();
    public PaumelleTableModel() {
        PaumelleList = new ArrayList<>();
    }
  
    public void setPaumelleList(ArrayList<ModelPaumelle> PaumelleList) {
        this.PaumelleList = PaumelleList;
        fireTableDataChanged(); // Notify the table that the data has changed
    }

    @Override
    public int getRowCount() {
        return Math.max(fixedRowCount, PaumelleList.size());
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
        if (rowIndex >= PaumelleList.size()) {
            return "";
        }

        ModelPaumelle Paumelle = PaumelleList.get(rowIndex);
        switch (columnIndex) {
            case 0 : {
                return Paumelle.getPaumelleId();
            }
             case 1 : {

                return Paumelle.getProfile().getModel();
            }
            case 2 : {

                return Paumelle.getTypePaumelle();
            }
            case 3 : {
                return Paumelle.getCouleurPaumelle(); // Utiliser la date formatée
            }
            case 4 : {
              
                return formatPrice(Paumelle.getPrixUnitaire());
            }
            case 5 : {
                
                 return Paumelle.getQuantiteEnStock(); 
            }
            case 6 : {
 
                return Paumelle.getFournisseur().getNom();
            }
                   case 7 : {
                   return new CellStatus(Paumelle);
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
        return columnIndex == 7; // Seulement la colonne "Actions" est éditable
    }
    public void removePaumelle(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= PaumelleList.size()) {
            return; // Index out of bounds, do nothing
        }
        PaumelleList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
  public ModelPaumelle getPaumelleAt(int row) {
    if (row < 0 || row >= PaumelleList.size()) {
        return null; // Renvoie null si l'index est hors de la taille de la liste
    }
    return PaumelleList.get(row);
}

    public void addPaumelle(ModelPaumelle Paumelle) {
        PaumelleList.add(Paumelle);
        fireTableRowsInserted(PaumelleList.size() - 1, PaumelleList.size() - 1);
        if (PaumelleList.size() > fixedRowCount) {
            fixedRowCount++;
        }
    }

    public void updatePaumelle(int rowIndex, ModelPaumelle Paumelle) {
        if (rowIndex < 0 || rowIndex >= PaumelleList.size()) {
            return; // Index out of bounds, do nothing
        }
        PaumelleList.set(rowIndex, Paumelle);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
    
    
}
