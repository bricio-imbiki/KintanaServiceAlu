/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TableModel;

import Components.ActionPanel;
import Components.CellStatus;
import Models.ModelCommande;
import Models.ModelStatut.ModelStatutCommande;
import static TableModel.FactureTableModel.formatPercentage;
import static TableModel.FactureTableModel.formatPrice;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author brici_6ul2f65
 */
public class CommandeTableModel extends AbstractTableModel {
    
 private String[] columnNames = {"N° Commande","N° Devis", "Client", "Date de Commande", "Total HT","% TVA", "Total TTC","Acompte Payé","Reste à Payer", "Statut", "Action"};
    private ArrayList<ModelCommande> commandes;
    private final int FIXED_ROW_COUNT = 50; // Nombre de lignes fixes à afficher
    private Map<Integer, ActionPanel> actionPanelCache = new HashMap<>();
     
//   s
    public CommandeTableModel() {
        commandes = new ArrayList<>();

    }
      public ModelCommande getCommandeAt(int row) {
    if (row < 0 || row >= commandes.size()) {
        return null; // Renvoie null si l'index est hors de la taille de la liste
    }
    return commandes.get(row);
}

//    }
    public void setCommandeList(ArrayList<ModelCommande> commandes) {
        this.commandes = commandes;
        fireTableDataChanged(); // Notifier la table que les données ont changé
    }

    public void addClient(ModelCommande client) {
        commandes.add(client);
        int row = commandes.size() - 1;
        fireTableRowsInserted(row, row); // Notifier l'insertion d'une nouvelle ligne
    }

    public void removeClient(int row) {
        commandes.remove(row);
        fireTableRowsDeleted(row, row); // Notifier la suppression d'une ligne
    }

    public ModelCommande getClientAt(int row) {
        return commandes.get(row);
    }

    @Override
    public int getRowCount() {
        // Retourner le nombre fixe ou le nombre de commandes, selon le plus grand
        return Math.max(commandes.size(), FIXED_ROW_COUNT);
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
 @Override
public Object getValueAt(int rowIndex, int columnIndex) {
    if (rowIndex >= commandes.size()) {
      
          return "";
        }
    ModelCommande commande = commandes.get(rowIndex);
     switch (columnIndex) {
         case 0 : return commande.getNumeroCommande();
         case 1 : return commande.getNumeroDevis();
         case 2 : return commande.getClient().getNom();
         case 3 : return commande.getFormattedDateCommande();
         case 4 : return formatPrice(commande.getTotalHT());
         case 5 : return formatPercentage(commande.getTvaPercentage());
         case 6 : return  formatPrice(commande.getTotalTTC());   
         case 7 : return formatPrice(commande.getAcomptePayed());  
         case 8 : return formatPrice(commande.getLeftToPaye());  
         case 9 : return new CellStatus(commande); 
         case 10 :return actionPanelCache.computeIfAbsent(rowIndex, r -> new ActionPanel());
     default : {
                return null;
            }
        }
    }
  


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 10; // Seulement la colonne "Actions" est éditable
    }
 public void notifyDataChanged() {
        fireTableDataChanged(); // Notifie les listeners que les données ont changé
    }
  

    public void updateClientAt(int index, ModelCommande client) {
        if (index >= 0 && index < commandes.size()) {
            commandes.set(index, client);
            fireTableRowsUpdated(index, index); // Notifie les listeners que les données ont changé pour la ligne spécifiée
        }
    }
public void updateStatutAt(int rowIndex, int newStatutId, String newStatut) {
    if (rowIndex >= 0 && rowIndex < commandes.size()) {
        // Mettre à jour le statut avec l'ID et le nom du statut
        commandes.get(rowIndex).setStatut(new ModelStatutCommande(newStatutId, newStatut));
        
        // Notifie que la ligne a été mise à jour dans le modèle de table
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
}

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    public boolean isRowEmpty(int rowIndex) {
        for (int i = 0; i < getColumnCount(); i++) {
            if (i == 8) {
                continue; // Ignorer la colonne "Action"
            }
            Object value = getValueAt(rowIndex, i);
            if (value != "" && !value.toString().trim().isEmpty()) {
                return false; // Trouvé une valeur non vide
            }
        }
        return true; // Toutes les cellules sont nulles ou vides
    }



}
