/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TableModel;

/**
 *
 * @author brici_6ul2f65
 */


import Components.ActionPanel;
import Models.ModelFournisseur;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

public class FournisseurTableModel extends AbstractTableModel {
   
    private final String[] columnNames = {"N° Fournisseur", "Nom Fournisseur", "Adresse", "Téléphone", "Email", "Action"};
    private ArrayList<ModelFournisseur> fournisseurs;
    private final int FIXED_ROW_COUNT = 50; // Nombre de lignes fixes à afficher
    private final Map<Integer, ActionPanel> actionPanelCache = new HashMap<>();

    public FournisseurTableModel() {
        fournisseurs = new ArrayList<>();
    }

    public void setFournisseurs(ArrayList<ModelFournisseur> fournisseurs) {
        this.fournisseurs = fournisseurs;
        fireTableDataChanged(); // Notifier la table que les données ont changé
    }

    public void addFournisseur(ModelFournisseur fournisseur) {
        fournisseurs.add(fournisseur);
        int row = fournisseurs.size() - 1;
        fireTableRowsInserted(row, row); // Notifier l'insertion d'une nouvelle ligne
    }

    public void removeFournisseur(int row) {
        fournisseurs.remove(row);
        fireTableRowsDeleted(row, row); // Notifier la suppression d'une ligne
    }

    @Override
    public int getRowCount() {
        // Retourner le nombre fixe ou le nombre de fournisseurs, selon le plus grand
        return Math.max(fournisseurs.size(), FIXED_ROW_COUNT);
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
        if (rowIndex >= fournisseurs.size()) {
            return "";
        }
        ModelFournisseur fournisseur = fournisseurs.get(rowIndex);
        switch (columnIndex) {
            case 0 : return fournisseur.getFournisseurId();
            case 1 : return fournisseur.getNom();
            case 2 : return fournisseur.getAdresse();
            case 3 : return fournisseur.getTelephone();
            case 4 : return fournisseur.getEmail();
            case 5 : return actionPanelCache.computeIfAbsent(rowIndex, r -> new ActionPanel());
            default : return null;
        }
        // Utiliser un cache
            }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 5; // Seulement la colonne "Actions" est éditable
    }

    public void notifyDataChanged() {
        fireTableDataChanged(); // Notifie les listeners que les données ont changé
    }

    public void updateFournisseurAt(int index, ModelFournisseur fournisseur) {
        if (index >= 0 && index < fournisseurs.size()) {
            fournisseurs.set(index, fournisseur);
            fireTableRowsUpdated(index, index); // Notifie les listeners que les données ont changé pour la ligne spécifiée
        }
    }

    public ModelFournisseur getFournisseurAt(int row) {
        if (row < 0 || row >= fournisseurs.size()) {
            return null; // Renvoie null si l'index est hors de la taille de la liste
        }
        return fournisseurs.get(row);
    }

    public boolean isRowEmpty(int rowIndex) {
        for (int i = 0; i < getColumnCount(); i++) {
            if (i == 5) {
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
