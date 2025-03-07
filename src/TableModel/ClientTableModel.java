package TableModel;

import Components.ActionPanel;
import Models.ModelClient;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.table.AbstractTableModel;


public class ClientTableModel extends AbstractTableModel {
   
    private final String[] columnNames = {"N° Client", "Nom Client", "Adresse", "Téléphone", "Email", "Date de Creation","Action"};
    private ArrayList<ModelClient> clients;
    private final int FIXED_ROW_COUNT = 50; // Nombre de lignes fixes à afficher
    private final Map<Integer, ActionPanel> actionPanelCache = new HashMap<>();
     

    public ClientTableModel() {
        clients = new ArrayList<>();

    }

    public void setClients(ArrayList<ModelClient> clients) {
        this.clients = clients;
        fireTableDataChanged(); // Notifier la table que les données ont changé
    }

    public void addClient(ModelClient client) {
        clients.add(client);
        int row = clients.size() - 1;
        fireTableRowsInserted(row, row); // Notifier l'insertion d'une nouvelle ligne
    }

    public void removeClient(int row) {
        clients.remove(row);
        fireTableRowsDeleted(row, row); // Notifier la suppression d'une ligne
    }


    @Override
    public int getRowCount() {
        // Retourner le nombre fixe ou le nombre de clients, selon le plus grand
        return Math.max(clients.size(), FIXED_ROW_COUNT);
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
    if (rowIndex >= clients.size()) {
     return "";
        }
    ModelClient client = clients.get(rowIndex);
         switch (columnIndex) {
            case 0 :
                return client.getClientId();
                
            case 1 :
                return client.getNom();
            case 2 :
                return client.getAdresse();
            case 3 :
                return client.getTelephone();
            case 4 : 
                return client.getEmail();
            case 5 : 
                return client.getFormattedDateClient();
            case 6 :
                return actionPanelCache.computeIfAbsent(rowIndex, r -> new ActionPanel());
            
        }
        // Utiliser un cache
                return null;
}


    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 6; // Seulement la colonne "Actions" est éditable
    }
 public void notifyDataChanged() {
        fireTableDataChanged(); // Notifie les listeners que les données ont changé
    }


    public void updateClientAt(int index, ModelClient client) {
        if (index >= 0 && index < clients.size()) {
            clients.set(index, client);
            fireTableRowsUpdated(index, index); // Notifie les listeners que les données ont changé pour la ligne spécifiée
        }
    }
public  ModelClient  getClientAt(int row) {
    if (row < 0 || row >= clients.size()) {
        return null; // Renvoie null si l'index est hors de la taille de la liste
    }
    return clients.get(row);
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
