/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package TableModel;

import Components.ActionPanel;
import Components.CellStatus;
import Models.ModelChassis;
import Models.ModelProfileAlu;
import static TableModel.FactureTableModel.formatPrice;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import javax.swing.table.AbstractTableModel;
import java.util.List;
import java.util.Map;

public class ProfileTableModel extends AbstractTableModel {

    private List<ModelChassis> ProfileList;
  
    private final String[] columnNames = {"N° Profile",  "Modele Profile","Type", "Couleur" , "Longueur","Prix Unitaire","Quantite Stock","Fournisseur","Etat du stock","Action"};
    private int fixedRowCount = 50;
     private final Map<Integer, ActionPanel> actionPanelCache = new HashMap<>();
    public ProfileTableModel() {
        ProfileList = new ArrayList<>();
    }
  
    public void setProfileList(ArrayList<ModelChassis> ProfileList) {
        this.ProfileList = ProfileList;
        fireTableDataChanged(); // Notify the table that the data has changed
    }

    @Override
    public int getRowCount() {
        return Math.max(fixedRowCount, ProfileList.size());
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
        if (rowIndex >= ProfileList.size()) {
            return "";
        }

        ModelChassis Profile = ProfileList.get(rowIndex);
        switch (columnIndex) {
            case 0 : {
                return Profile.getChassisId();
            }
            case 1 : {

                return Profile.getProfile().getModel();
              
            }
            
            case 2 : {
                  return Profile.getType().getTypeProfileAlu();
            }
            case 3 : {
              
                return Profile.getProfile().getCouleur();
            }
            case 4 : {
                
                 return Profile.getLongueurBarre()+" mm"; 
            }
            case 5 : {
 
                return formatPrice(Profile.getPrixUnitaire());
            }
            case 6 : {
             return Profile.getQuantiteEnStock()+" Barre";
            }
            case 7 : {
         
             return Profile.getFournisseur().getNom();
            }
            case 8 : {
                 return new CellStatus(Profile); 
              
            }
             case 9 : {
                return actionPanelCache.computeIfAbsent(rowIndex, r -> new ActionPanel()); // Utiliser un cache
            }
            
            default : {
                return null;
            }
        }
    }
  
   
    @Override
 public boolean isCellEditable(int rowIndex, int columnIndex) {
    return columnIndex == 9; // The "Action" column is at index 8
}
    public void removeProfile(int rowIndex) {
        if (rowIndex < 0 || rowIndex >= ProfileList.size()) {
            return; // Index out of bounds, do nothing
        }
        ProfileList.remove(rowIndex);
        fireTableRowsDeleted(rowIndex, rowIndex);
    }
  public ModelChassis getProfileAt(int row) {
    if (row < 0 || row >= ProfileList.size()) {
        return null; // Renvoie null si l'index est hors de la taille de la liste
    }
    return ProfileList.get(row);
}

    public void addProfile(ModelChassis Profile) {
        ProfileList.add(Profile);
        fireTableRowsInserted(ProfileList.size() - 1, ProfileList.size() - 1);
        if (ProfileList.size() > fixedRowCount) {
            fixedRowCount++;
        }
    }

    public void updateProfile(int rowIndex, ModelChassis Profile) {
        if (rowIndex < 0 || rowIndex >= ProfileList.size()) {
            return; // Index out of bounds, do nothing
        }
        ProfileList.set(rowIndex, Profile);
        fireTableRowsUpdated(rowIndex, rowIndex);
    }
    
    
}
