
package TableModel;

import Components.ActionPanel;
import Components.CellStatus;
import Models.ModelSerrure;
import static TableModel.FactureTableModel.formatPrice;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Map;

public class SerrureTableModel extends AbstractTableModel {

    private List<ModelSerrure> SerrureList;
    private final String[] columnNames = {"N° Serrure", "Modele Profile","Type", "Couleur","Prix Unitaire","Quantite","Fournisseur","Etat du stock","Action"};
    private int fixedRowCount = 50;
     private final Map<Integer, ActionPanel> actionPanelCache = new HashMap<>();
    public SerrureTableModel() {
        SerrureList = new ArrayList<>();
    }
  
    public void setSerrureList(ArrayList<ModelSerrure> SerrureList) {
        this.SerrureList = SerrureList;
        fireTableDataChanged(); // Notify the table that the data has changed
    }

    @Override
    public int getRowCount() {
        return Math.max(fixedRowCount, SerrureList.size());
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
        if (rowIndex >= SerrureList.size()) {
            return "";
        }

        ModelSerrure Serrure = SerrureList.get(rowIndex);
        switch (columnIndex) {
            case 0 : {
                return Serrure.getSerrureId();
            }
            case 1 : {

                return Serrure.getProfile().getModel();
            }
            case 2 : {

                return Serrure.getTypeSerrure();
            }
            case 3 : {
                return Serrure.getCouleurSerrure(); // Utiliser la date formatée
            }
            case 4 : {
              
                return  formatPrice(Serrure.getPrixUnitaire());
            }
            case 5 : {
                
                 return Serrure.getQuantiteEnStock(); 
            }
           
            case 6 : {
         
             return Serrure.getFournisseur().getNom();
            }
                          case 7 : {
                   return new CellStatus(Serrure);
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
        return columnIndex == 8 ; // Seulement la colonne "Actions" est éditable
    }
    public void removeSerrure(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= SerrureList.size()) {
            return; // Index out of bounds, do nothing
        }
        SerrureList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
  public ModelSerrure getSerrureAt(int row) {
    if (row < 0 || row >= SerrureList.size()) {
        return null; // Renvoie null si l'index est hors de la taille de la liste
    }
    return SerrureList.get(row);
}

    public void addSerrure(ModelSerrure Serrure) {
        SerrureList.add(Serrure);
        fireTableRowsInserted(SerrureList.size() - 1, SerrureList.size() - 1);
        if (SerrureList.size() > fixedRowCount) {
            fixedRowCount++;
        }
    }

    public void updateSerrure(int rowIndex, ModelSerrure Serrure) {
        if (rowIndex < 0 || rowIndex >= SerrureList.size()) {
            return; // Index out of bounds, do nothing
        }
        SerrureList.set(rowIndex, Serrure);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
    
    
}
