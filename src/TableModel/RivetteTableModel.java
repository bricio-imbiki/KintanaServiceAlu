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
import Components.CellStatus;
import Models.ModelRivette;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

public class RivetteTableModel extends AbstractTableModel {

    private List<ModelRivette> rivetteList;
    private final String[] columnNames = {"N° Rivette", "Profil Aluminium", "Type", "Prix Unitaire", "Quantité en Stock", "Fournisseur", "État du Stock", "Action"};
    private int fixedRowCount = 50;
    private final Map<Integer, ActionPanel> actionPanelCache = new HashMap<>();

    public RivetteTableModel() {
        rivetteList = new ArrayList<>();
    }

    public void setRivetteList(List<ModelRivette> rivetteList) {
        this.rivetteList = rivetteList;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return Math.max(fixedRowCount, rivetteList.size());
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
        if (rowIndex >= rivetteList.size()) {
            return "";
        }

        ModelRivette rivette = rivetteList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return rivette.getRivetteId();
            case 1:
                return rivette.getProfile().getModel();
            case 2:
                return rivette.getType();
            case 3:
                return formatPrice(rivette.getPrixUnitaire());
            case 4:
                return rivette.getQuantiteEnStock();
            case 5:
                return rivette.getFournisseur().getNom();
            case 6:
                return new CellStatus(rivette);
            case 7:
                return actionPanelCache.computeIfAbsent(rowIndex, r -> new ActionPanel());
            default:
                return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 7; // Seule la colonne "Action" est éditable
    }

    public void removeRivette(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= rivetteList.size()) {
            return;
        }
        rivetteList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public ModelRivette getRivetteAt(int row) {
        if (row < 0 || row >= rivetteList.size()) {
            return null;
        }
        return rivetteList.get(row);
    }

    public void addRivette(ModelRivette rivette) {
        rivetteList.add(rivette);
        fireTableRowsInserted(rivetteList.size() - 1, rivetteList.size() - 1);
        if (rivetteList.size() > fixedRowCount) {
            fixedRowCount++;
        }
    }

    public void updateRivette(int rowIndex, ModelRivette rivette) {
        if (rowIndex < 0 || rowIndex >= rivetteList.size()) {
            return;
        }
        rivetteList.set(rowIndex, rivette);
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
