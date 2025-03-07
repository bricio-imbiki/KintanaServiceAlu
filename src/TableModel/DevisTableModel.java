package TableModel;

import Components.ActionPanel;
import Components.CellStatus;
import Models.ModelDevis;
import static TableModel.FactureTableModel.formatPercentage;
import static TableModel.FactureTableModel.formatPrice;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Map;

public class DevisTableModel extends AbstractTableModel {

    private List<ModelDevis> devisList;
    private final String[] columnNames = {"N° Devis", "Nom Client", "Date Devis", "Total HT","% Remise","Tot. Remise","% Acompte","Acompte à Payer","% TVA","Tot. TVA", "Total TTC", "Statut","Action"};
    private int fixedRowCount = 50;
     private final Map<Integer, ActionPanel> actionPanelCache = new HashMap<>();
    public DevisTableModel() {
        devisList = new ArrayList<>();
    }
  
    public void setDevisList(ArrayList<ModelDevis> devisList) {
        this.devisList = devisList;
        fireTableDataChanged(); // Notify the table that the data has changed
    }

    @Override
    public int getRowCount() {
        return Math.max(fixedRowCount, devisList.size());
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
        if (rowIndex >= devisList.size()) {
            return "";
        }

        ModelDevis devis = devisList.get(rowIndex);
        switch (columnIndex) {
            case 0 : {
                return devis.getNumeroDevis();
            }
            case 1 : {

                return devis.getClient().getNom();
            }
            case 2 : {
                return devis.getFormattedDateDevis(); // Utiliser la date formatée
            }
            case 3 : {
                
                
                return formatPrice(devis.getTotalHT());
            }
            case 4 : {
                
                 return formatPercentage(devis.getRemisePercentage()); 
            }
            case 5 : {
 
                return formatPrice( devis.getTotalRemise());
            }
            case 6 : {
             return formatPercentage(devis.getAcomptePercentage());
            }
            case 7 : {
              
                return formatPrice(devis.getTotalAcompte());
            }
             case 8 : {
              
                return formatPercentage(devis.getTvaPercentage());
            }
              case 9 : {
              
                return formatPrice(devis.getTotalTva());
            }
                 case 10 : {
              
                return formatPrice(devis.getTotalTTC());
            }
            case 11 : {
             return new CellStatus(devis); 
            }
            case 12 : {
                return actionPanelCache.computeIfAbsent(rowIndex, r -> new ActionPanel()); // Utiliser un cache
            }
            default : {
                return null;
            }
        }
    }


    @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 12; // Seulement la colonne "Actions" est éditable
    }
    public void removeDevis(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= devisList.size()) {
            return; // Index out of bounds, do nothing
        }
        devisList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
  public ModelDevis getDevisAt(int row) {
    if (row < 0 || row >= devisList.size()) {
        return null; // Renvoie null si l'index est hors de la taille de la liste
    }
    return devisList.get(row);
}
    public boolean isRowEmpty(int rowIndex) {
        for (int i = 0; i < getColumnCount(); i++) {
            
            Object value = getValueAt(rowIndex, i);
            if (value != "" && !value.toString().trim().isEmpty()) {
                return false; // Trouvé une valeur non vide
            }
        }
        return true; // Toutes les cellules sont nulles ou vides
    }
    public void addDevis(ModelDevis devis) {
        devisList.add(devis);
        fireTableRowsInserted(devisList.size() - 1, devisList.size() - 1);
        if (devisList.size() > fixedRowCount) {
            fixedRowCount++;
        }
    }

    public void updateDevis(int rowIndex, ModelDevis devis) {
        if (rowIndex < 0 || rowIndex >= devisList.size()) {
            return; // Index out of bounds, do nothing
        }
        devisList.set(rowIndex, devis);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
    
    
}
