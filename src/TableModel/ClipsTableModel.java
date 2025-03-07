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
import Models.ModelClips;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import javax.swing.table.AbstractTableModel;

public class ClipsTableModel extends AbstractTableModel {

    private List<ModelClips> clipsList;
    private final String[] columnNames = {"N° Clips", "Profil Aluminium", "Type", "Prix Unitaire", "Quantité en Stock", "Fournisseur", "État du Stock", "Action"};
    private int fixedRowCount = 50;
    private final Map<Integer, ActionPanel> actionPanelCache = new HashMap<>();

    public ClipsTableModel() {
        clipsList = new ArrayList<>();
    }

    public void setClipsList(List<ModelClips> clipsList) {
        this.clipsList = clipsList;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return Math.max(fixedRowCount, clipsList.size());
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
        if (rowIndex >= clipsList.size()) {
            return "";
        }

        ModelClips clips = clipsList.get(rowIndex);
        switch (columnIndex) {
            case 0:
                return clips.getClipsId();
            case 1:
                return clips.getProfile().getModel();
            case 2:
                return clips.getType();
            case 3:
                return formatPrice(clips.getPrixUnitaire());
            case 4:
                return clips.getQuantiteEnStock();
            case 5:
                return clips.getFournisseur().getNom();
            case 6:
                return new CellStatus(clips);
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

    public void removeClips(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= clipsList.size()) {
            return;
        }
        clipsList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }

    public ModelClips getClipsAt(int row) {
        if (row < 0 || row >= clipsList.size()) {
            return null;
        }
        return clipsList.get(row);
    }

    public void addClips(ModelClips clips) {
        clipsList.add(clips);
        fireTableRowsInserted(clipsList.size() - 1, clipsList.size() - 1);
        if (clipsList.size() > fixedRowCount) {
            fixedRowCount++;
        }
    }

    public void updateClips(int rowIndex, ModelClips clips) {
        if (rowIndex < 0 || rowIndex >= clipsList.size()) {
            return;
        }
        clipsList.set(rowIndex, clips);
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
