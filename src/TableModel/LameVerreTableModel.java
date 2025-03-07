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
import Models.ModelLameVerre;
import javax.swing.table.AbstractTableModel;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class LameVerreTableModel extends AbstractTableModel {
    private List<ModelLameVerre> lameVerreList;
    private final String[] columnNames = {
        "ID", "Vitrage", "Type de Verre", "Epaisseur (mm)", "Largeur (mm)",
        "Prix Unitaire", "Quantité en Stock", "Fournisseur", "Statut Stock", "Action"
    };
    private final Map<Integer, ActionPanel> actionPanelCache = new HashMap<>();

    public LameVerreTableModel() {
        lameVerreList = new ArrayList<>();
    }

    public void setLameVerreList(List<ModelLameVerre> lameVerreList) {
        this.lameVerreList = lameVerreList;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return lameVerreList.size();
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
        if (rowIndex >= lameVerreList.size()) {
            return "";
        }

        ModelLameVerre lameVerre = lameVerreList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return lameVerre.getLameVerreId();
            case 1:
                return lameVerre.getVitrage().getTypeVitrage();
            case 2:
                return lameVerre.getTypeVerre();
            case 3:
                return lameVerre.getEpaisseur();
            case 4:
                return lameVerre.getLargeur();
            case 5:
                return formatPrice(lameVerre.getPrixUnitaire());
            case 6:
                return lameVerre.getQuantiteEnStock();
            case 7:
                return lameVerre.getFournisseur().getNom();
            case 8:
                return lameVerre.getStatutStock(); // Statut du stock
            case 9:
                return actionPanelCache.computeIfAbsent(rowIndex, r -> new ActionPanel());
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 9; // Seulement la colonne "Action" est éditable
    }

    private String formatPrice(BigDecimal price) {
        if (price == null) {
            return "";
        }
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE);
        return currencyFormat.format(price);
    }
}
