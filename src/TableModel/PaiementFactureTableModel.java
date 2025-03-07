/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TableModel;

/**
 *
 * @author brici_6ul2f65
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */



import Components.ActionPanel;
import Models.ModelPaiementFacture;
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
public class PaiementFactureTableModel extends AbstractTableModel{
    
   private final String[] columnNames = {"N° Paiement","N° Facture", "Date de Paiement", "Mode de Paiement","Montant Payé",  "Action"};
    private ArrayList<ModelPaiementFacture> PaiementFactures;
    private final int FIXED_ROW_COUNT = 50; // Nombre de lignes fixes à afficher
    private final Map<Integer, ActionPanel> actionPanelCache = new HashMap<>();
     
//   s
    public  PaiementFactureTableModel() {
        PaiementFactures = new ArrayList<>();

    }
    

//    }
    public void setPaiementFactures(ArrayList<ModelPaiementFacture>PaiementFactures) {
        this.PaiementFactures =PaiementFactures;
        fireTableDataChanged(); // Notifier la table que les données ont changé
    }

    public void addClient(ModelPaiementFacture client) {
       PaiementFactures.add(client);
        int row =PaiementFactures.size() - 1;
        fireTableRowsInserted(row, row); // Notifier l'insertion d'une nouvelle ligne
    }

    public void removeClient(int row) {
       PaiementFactures.remove(row);
        fireTableRowsDeleted(row, row); // Notifier la suppression d'une ligne
    }

    public ModelPaiementFacture getPaiementFactureAt(int row) {
        return PaiementFactures.get(row);
    }

    @Override
    public int getRowCount() {
        // Retourner le nombre fixe ou le nombre dePaiementFactures, selon le plus grand
        return Math.max(PaiementFactures.size(), FIXED_ROW_COUNT);
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
     @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= PaiementFactures.size()) {
            return "";
        }
    ModelPaiementFacture PaiementFacture = PaiementFactures.get(rowIndex);
   switch (columnIndex) {
       case 0 :return  PaiementFacture.getNumero();
         case 1 :return  PaiementFacture.getFacture().getNumeroFacture();
         case 2 :return  PaiementFacture.getFormattedDate();
         case 3 :return  PaiementFacture.getModePaiement();
         case 4 :return  formatPrice(PaiementFacture.getMontantPaye());
         case 5 :return  actionPanelCache.computeIfAbsent(rowIndex, r -> new ActionPanel());
         default :return  null;
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
  

    public void updateClientAt(int index, ModelPaiementFacture facture) {
        if (index >= 0 && index <PaiementFactures.size()) {
           PaiementFactures.set(index, facture);
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