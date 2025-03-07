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
import Components.CellStatus;
import Models.ModelPaiementAcompte;
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
public class PaiementAcompteTableModel extends AbstractTableModel{
    
   private final String[] columnNames = {"N° Paiement","N° Commande", "Date de Paiement", "Mode de Paiement","Montant Payé",  "Action"};
    private ArrayList<ModelPaiementAcompte> PaiementAcomptes;
    private final int FIXED_ROW_COUNT = 50; // Nombre de lignes fixes à afficher
    private final Map<Integer, ActionPanel> actionPanelCache = new HashMap<>();
     
//   s
    public  PaiementAcompteTableModel() {
        PaiementAcomptes = new ArrayList<>();

    }
    

//    }
    public void setPaiementAcomptes(ArrayList<ModelPaiementAcompte>PaiementAcomptes) {
        this.PaiementAcomptes =PaiementAcomptes;
        fireTableDataChanged(); // Notifier la table que les données ont changé
    }

    public void addClient(ModelPaiementAcompte client) {
       PaiementAcomptes.add(client);
        int row =PaiementAcomptes.size() - 1;
        fireTableRowsInserted(row, row); // Notifier l'insertion d'une nouvelle ligne
    }

    public void removeClient(int row) {
       PaiementAcomptes.remove(row);
        fireTableRowsDeleted(row, row); // Notifier la suppression d'une ligne
    }

    public ModelPaiementAcompte getPaiementAcompteAt(int row) {
        return PaiementAcomptes.get(row);
    }

    @Override
    public int getRowCount() {
        // Retourner le nombre fixe ou le nombre dePaiementAcomptes, selon le plus grand
        return Math.max(PaiementAcomptes.size(), FIXED_ROW_COUNT);
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
     @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        if (rowIndex >= PaiementAcomptes.size()) {
            return "";
        }
    ModelPaiementAcompte PaiementAcompte = PaiementAcomptes.get(rowIndex);
  switch (columnIndex) {
         case 0 : return PaiementAcompte.getNumero();
         case 1 : return PaiementAcompte.getCommande().getNumeroCommande();
         case 2 : return PaiementAcompte.getFormattedDate();
         case 3 : return PaiementAcompte.getModePaiement();
         case 4 : return formatPrice(PaiementAcompte.getMontantPaye());
         case 5 :return actionPanelCache.computeIfAbsent(rowIndex, r -> new ActionPanel());
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
  

    public void updateClientAt(int index, ModelPaiementAcompte acompte) {
        if (index >= 0 && index <PaiementAcomptes.size()) {
           PaiementAcomptes.set(index, acompte);
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