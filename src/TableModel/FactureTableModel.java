/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TableModel;

import Components.ActionPanel;
import Components.CellStatus;
import Models.ModelFacture;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author brici_6ul2f65
 */
public class FactureTableModel extends AbstractTableModel{
    
   private final String[] columnNames = {"N° Facture","N° Commande", "Client", "Date de Facture", "Total HT","% TVA", "Total TTC", "Statut", "Action"};
    private ArrayList<ModelFacture> factures;
    private final int FIXED_ROW_COUNT = 50; // Nombre de lignes fixes à afficher
    private final Map<Integer, ActionPanel> actionPanelCache = new HashMap<>();
     
//   s
    public  FactureTableModel() {
        factures = new ArrayList<>();

    }
    

//    }
    public void setfactures(ArrayList<ModelFacture>factures) {
        this.factures =factures;
        fireTableDataChanged(); // Notifier la table que les données ont changé
    }

    public void addClient(ModelFacture client) {
       factures.add(client);
        int row =factures.size() - 1;
        fireTableRowsInserted(row, row); // Notifier l'insertion d'une nouvelle ligne
    }

    public void removeClient(int row) {
       factures.remove(row);
        fireTableRowsDeleted(row, row); // Notifier la suppression d'une ligne
    }

    public ModelFacture getFactureAt(int row) {
        return factures.get(row);
    }

    @Override
    public int getRowCount() {
        // Retourner le nombre fixe ou le nombre defactures, selon le plus grand
        return Math.max(factures.size(), FIXED_ROW_COUNT);
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
     @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= factures.size()) {
            return "";
        }
    ModelFacture facture = factures.get(rowIndex);
    switch (columnIndex) {
         case 0 :return facture.getNumeroFacture();
         case 1 :return facture.getCommande().getNumeroCommande();
         case 2 :return facture.getClient().getNom();
         case 3 :return facture.getFormattedDateFacture();
         case 4 :return formatPrice(facture.getTotalFactureHT());
         case 5 :return formatPercentage(facture.getTvaFacturePercentage());
         case 6 :return formatPrice(facture.getTotalFactureTTC());   
         case 7 :return new CellStatus(facture); 
         case 8 :return actionPanelCache.computeIfAbsent(rowIndex, r -> new ActionPanel());
        default : {
                return null;
            }
        }
    }
 public static String formatPrice(BigDecimal price) {
    if (price == null) {
        return "N/A"; // ou toute autre valeur par défaut appropriée
    }
    NumberFormat format = NumberFormat.getNumberInstance(Locale.US);
    format.setMinimumFractionDigits(2);
    format.setMaximumFractionDigits(2);
    format.setGroupingUsed(true);
    return format.format(price) + " Ar";
}

    public static String formatPercentage(BigDecimal percentage) {
        if (percentage == null) {
            return "0%";
        }
        return percentage + "%";
    }
    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 8; // Seulement la colonne "Actions" est éditable
    }
 public void notifyDataChanged() {
        fireTableDataChanged(); // Notifie les listeners que les données ont changé
    }
  

    public void updateClientAt(int index, ModelFacture facture) {
        if (index >= 0 && index <factures.size()) {
           factures.set(index, facture);
            fireTableRowsUpdated(index, index); // Notifie les listeners que les données ont changé pour la ligne spécifiée
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
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

}