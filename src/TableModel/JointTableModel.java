
package TableModel;

import Components.ActionPanel;
import Components.CellStatus;
import Models.ModelJoint;
import static TableModel.FactureTableModel.formatPrice;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Map;

public class JointTableModel extends AbstractTableModel {

    private List<ModelJoint> JointList;
    private final String[] columnNames = {"N° Joint", "Modele Profile", "Type","Prix Unitaire","Quantite","Fournisseur","Etat du stock","Action"};
    private int fixedRowCount = 50;
     private final Map<Integer, ActionPanel> actionPanelCache = new HashMap<>();
    public JointTableModel() {
        JointList = new ArrayList<>();
    }
  
    public void setJointList(ArrayList<ModelJoint> JointList) {
        this.JointList = JointList;
        fireTableDataChanged(); // Notify the table that the data has changed
    }

    @Override
    public int getRowCount() {
        return Math.max(fixedRowCount, JointList.size());
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
        if (rowIndex >= JointList.size()) {
            return "";
        }

        ModelJoint Joint = JointList.get(rowIndex);
        switch (columnIndex) {
            case 0 : {
                return Joint.getJointId();
            }
            case 1 : {

                return Joint.getProfile().getModel();
            }
            case 2 : {
                return Joint.getTypeJoint(); // Utiliser la date formatée
            }
            case 3 : {
              
                return formatPrice(Joint.getPrixUnitaire());
            }
            case 4 : {
                
                 return Joint.toString(); 
            }
            case 5 : {
 
                return Joint.getFournisseur().getNom();
            }
          
            case 6 : {
                   return new CellStatus(Joint);
            }
               case 7 : {
 
                return actionPanelCache.computeIfAbsent(rowIndex, r -> new ActionPanel()); // Utiliser un cache
            }
            default : {
                return null;
            }
        }
    }

 
    @Override
  public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 7; // Seulement la colonne "Actions" est éditable
    }
    public void removeJoint(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= JointList.size()) {
            return; // Index out of bounds, do nothing
        }
        JointList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
  public ModelJoint getJointAt(int row) {
    if (row < 0 || row >= JointList.size()) {
        return null; // Renvoie null si l'index est hors de la taille de la liste
    }
    return JointList.get(row);
}

    public void addJoint(ModelJoint Joint) {
        JointList.add(Joint);
        fireTableRowsInserted(JointList.size() - 1, JointList.size() - 1);
        if (JointList.size() > fixedRowCount) {
            fixedRowCount++;
        }
    }

    public void updateJoint(int rowIndex, ModelJoint Joint) {
        if (rowIndex < 0 || rowIndex >= JointList.size()) {
            return; // Index out of bounds, do nothing
        }
        JointList.set(rowIndex, Joint);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }

 
    
    
}
