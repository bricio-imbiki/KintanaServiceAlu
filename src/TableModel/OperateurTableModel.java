/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TableModel;

import Components.ActionPanel;
import Components.CellStatus;
import Models.ModelOperateur;
import Models.ModelProfileAlu;
import Models.ModelFournisseur;
import Models.ModelStatut.ModelStatutStock;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

public class OperateurTableModel extends AbstractTableModel {

    private List<ModelOperateur> operateurList;
    private final String[] columnNames = {"N° Opérateur", "Profil Aluminium", "Type", "Prix Unitaire", "Quantité en Stock", "Fournisseur", "État du Stock", "Action"};
    private int fixedRowCount = 50;
    private final Map<Integer, ActionPanel> actionPanelCache = new HashMap<>();

    public OperateurTableModel() {
        operateurList = new ArrayList<>();
    }

    public void setOperateurList(List<ModelOperateur> operateurList) {
        this.operateurList = operateurList;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return Math.max(fixedRowCount, operateurList.size());
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
        if (rowIndex >= operateurList.size()) {
            return "";
        }

        ModelOperateur operateur = operateurList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return operateur.getOperateurId();
            case 1:
                return operateur.getProfile() != null ? operateur.getProfile().getModel() : "";
            case 2:
                return operateur.getType();
            case 3:
                return formatPrice(operateur.getPrixUnitaire());
            case 4:
                return operateur.getQuantiteEnStock();
            case 5:
                return operateur.getFournisseur() != null ? operateur.getFournisseur().getNom() : "";
            case 6:
                return new CellStatus(operateur);
            case 7:
                return actionPanelCache.computeIfAbsent(rowIndex, r -> new ActionPanel());
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 7; // Only the "Action" column is editable
    }

    public void removeOperateur(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= operateurList.size()) {
            return;
        }
        operateurList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public ModelOperateur getOperateurAt(int row) {
        if (row < 0 || row >= operateurList.size()) {
            return null;
        }
        return operateurList.get(row);
    }

    public void addOperateur(ModelOperateur operateur) {
        operateurList.add(operateur);
        fireTableRowsInserted(operateurList.size() - 1, operateurList.size() - 1);
        if (operateurList.size() > fixedRowCount) {
            fixedRowCount++;
        }
    }

    public void updateOperateur(int rowIndex, ModelOperateur operateur) {
        if (rowIndex < 0 || rowIndex >= operateurList.size()) {
            return;
        }
        operateurList.set(rowIndex, operateur);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

    private String formatPrice(BigDecimal price) {
        if (price == null) {
            return "";
        }
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE);
        return currencyFormat.format(price);
    }
}

