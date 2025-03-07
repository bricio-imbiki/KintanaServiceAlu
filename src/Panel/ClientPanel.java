/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Panel;


import Components.ActionCellEditor;
import Components.ActionCellRenderer;
import Components.ActionTableEvent;
import Components.CenterTableCellRenderer;
import Form.AddClientForm;
import Components.CustomSearchField;
 import Components.RoundedSpinner;
import Components.RollOverTable;
import Components.RoundedButton;
import Components.RoundedComboBox;
import Components.RoundedPanel;
import Components.StatusCellRenderer;
import TableModel.ClientTableModel;
import Models.ModelClient;
import ConnexionDB.DatabaseUtils;
import Scroll.ScrollPaneWin11;
import Views.Dashboard;
import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import javax.swing.table.TableRowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.SwingConstants.CENTER;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import raven.modal.ModalDialog;
import raven.modal.component.SimpleModalBorder;

public class ClientPanel  extends RoundedPanel {

   
    private JPopupMenu popupMenu;
    private RoundedButton addButton;
  
        private RoundedSpinner Field;
     private CustomSearchField searchField;
    private JTable clientTable;
    private TableRowSorter<ClientTableModel> sorter;
    public static ClientTableModel tableModel;
    public static ArrayList<ModelClient> clients;
    private RoundedComboBox<String> searchChoiceCombo;
    private  ScrollPaneWin11 scroll;
    private JPanel tablePanel;
    private JPanel tablePanelContenaire;
    private JLabel titleLabel; // Declare JLabel
    private JPanel titlePanel;
    public ClientPanel() {
        super(20); 
        initializeComponents();
        initializeListeners();
        layoutComponents();
         loadData();
           
        tablePanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:1,1,1,1,$TableHeader.bottomSeparatorColor,,10");

        scroll.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:3,0,3,0,$Table.background,10,10");
        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "hoverTrackColor:null");
    }

    private void initializeComponents() {
        clients = new ArrayList<>();
        tableModel = new ClientTableModel();
        clientTable = new JTable(tableModel);
        clientTable = new RollOverTable(tableModel);
        clientTable.setSelectionBackground(new Color(75, 110, 175));
        // Définir la police pour les cellules du tableau
        clientTable.setFont(new Font("Dialog", Font.BOLD, 13)); 
        configureActionColumn(clientTable, tableModel);

     
        //new Color(92, 184, 92)
        Font buttonFont = new Font("Dialog", Font.BOLD, 14);
        Dimension buttonSize = new Dimension(150, 40);
           // addButton = new RoundedButton("Ajouter", new Color(92, 184, 92), new Color(112, 204, 112));
             addButton = new RoundedButton("Ajouter", new Color(46, 204, 113), new Color(60, 224, 133));
            
           addButton.setIcon(new ImageIcon(getClass().getResource("/Icon/icons8-add-26 (5).png")));
   
        JButton[] buttons = {addButton};
        for (JButton button : buttons) {
            button.setFont(buttonFont);
            button.setPreferredSize(buttonSize);
            button.setForeground(Color.WHITE); // Couleur du texte
        }
        // Initialiser le menu contextuel
        popupMenu = new JPopupMenu();
        JMenuItem createQuoteMenuItem = new JMenuItem("Créer Nouveau Devis");

        // Charger l'icône
        URL iconUrl = getClass().getResource("/Icon/icons8-add-32.png"); // Chemin relatif vers votre icône
        if (iconUrl != null) {
            ImageIcon icon = new ImageIcon(iconUrl);
            createQuoteMenuItem.setIcon(icon);
        } else {
            System.err.println("Icon not found!");
        }
        popupMenu.add(createQuoteMenuItem);
        // Ajouter l'action pour le menu contextuel
        createQuoteMenuItem.addActionListener(e -> {
         openDevisCreatePanel();
        });

        // Ajouter le listener de souris pour afficher le menu contextuel
        clientTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = clientTable.rowAtPoint(e.getPoint());

                    // Vérifiez si la ligne sélectionnée contient des données
                    if (row != -1 && !tableModel.isRowEmpty(row)) { // Vérifie si la ligne n'est pas vide
                        clientTable.setRowSelectionInterval(row, row);
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });

        // Charger l'icône de recherche
        Icon searchIcon = new ImageIcon(getClass().getResource("/Icon/search.png")); // Remplacez par le chemin de votre icône

Icon clearIcon = new ImageIcon(getClass().getResource("/Icon/clear.png"));
 searchField = new CustomSearchField("Rechercher...", searchIcon, clearIcon);

  
// Définir la taille préférée du searchField
        searchField.setPreferredSize(new Dimension(295, 40)); // Ajustez les valeurs pour la largeur (300) et la hauteur (40)

        // Configuration de la hauteur des lignes pour une meilleure lisibilité
        clientTable.setRowHeight(30);

//        // Personnaliser les en-têtes de colonnes
        JTableHeader header = clientTable.getTableHeader();
        ((JLabel) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        header.setBackground(new Color(26, 26, 26));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Dialog", Font.BOLD, 12));
        header.setReorderingAllowed(false);

      clientTable.setFillsViewportHeight(true); // Pour que le tableau occupe tout l'espace vertical disponible dans le JScrollPane
// Créez un renderer de cellule par défaut
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) clientTable.getTableHeader().getDefaultRenderer();

//// Définissez l'alignement horizontal du renderer sur CENTER
      renderer.setHorizontalAlignment(SwingConstants.CENTER);
//
//// Appliquez le renderer à tous les en-têtes de colonnes
        for (int i = 0; i < clientTable.getColumnCount(); i++) {
            clientTable.getColumnModel().getColumn(i).setHeaderRenderer(renderer);
        }

        // Activer l'affichage des lignes de grille et définir leur couleur
        clientTable.setShowGrid(true);
        clientTable.setGridColor(Color.GRAY);


        setLayout(new BorderLayout());
  
    }

  
private void openDevisCreatePanel() {
    int selectedRow = clientTable.getSelectedRow();
    if (selectedRow != -1) {
        String selectedClientName = (String) clientTable.getValueAt(selectedRow,1);
        
        Container parent = getParent();
        while (parent != null && !(parent instanceof Dashboard)) {
            parent = parent.getParent();  // Parcourir les parents jusqu'à trouver Dashboard
        }
        if (parent != null) {
            Dashboard dashboard = (Dashboard) parent;
            dashboard.showDevisCreatePanel(selectedClientName); // Pass the selected client name
            dashboard.selectMenuIndex(2,1);  
        } else {
            System.out.println("Dashboard not found!");
        }
    } else {
        // Handle case where no row is selected
        System.out.println("No client selected.");
    }
}

private void layoutComponents() {
    String[] columns = {"Choisir filtre", "N° Client", "Nom Client", "Adresse", "Téléphone", "Email","Date"};
    searchChoiceCombo = new RoundedComboBox<>(columns);
    searchChoiceCombo.setPreferredSize(new Dimension(searchChoiceCombo.getPreferredSize().width, 40));
    
    tablePanelContenaire = new JPanel(new BorderLayout(0,5));
    tablePanelContenaire.setBorder(new EmptyBorder(15, 15, 15, 15));
    
    tablePanel = new JPanel(new BorderLayout());
    titlePanel = new JPanel(new BorderLayout());
    titlePanel.setPreferredSize(new Dimension(20, 35));
    titleLabel = new JLabel("Gestion des Clients");
    // Configurez le style du label si nécessaire
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    titlePanel.add( titleLabel, BorderLayout.WEST);
  
    
   
    
    scroll = new ScrollPaneWin11();
    scroll.setViewportView(clientTable);
    tablePanelContenaire.add( titlePanel, BorderLayout.NORTH);
    tablePanel.add(scroll, BorderLayout.CENTER);
    
    // Configuration du panneau d'action
    JPanel actionPanel = new JPanel(new BorderLayout());
    JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 1));
    actionPanel.setBorder(new EmptyBorder(15, 5, 0, 0));

   
    
    leftPanel.add(addButton);
   
     leftPanel.add(searchField);
      leftPanel.add(searchChoiceCombo);
//        leftPanel.add(Field);
    actionPanel.add(leftPanel, BorderLayout.CENTER);
    
    tablePanelContenaire.add(tablePanel, BorderLayout.CENTER);
    add(actionPanel, BorderLayout.NORTH);
    add(tablePanelContenaire, BorderLayout.CENTER);
     tablePanel.setFocusable(true);
        actionPanel.setFocusable(true);
        
        
        
      tablePanel.setFocusable(true);
    actionPanel.setFocusable(true);

    
    tablePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tablePanel.requestFocusInWindow();
            }
        });
     addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               requestFocusInWindow();
            }
        });
           titlePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                titlePanel.requestFocusInWindow();
            }
        });

    actionPanel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            actionPanel.requestFocusInWindow();
        }
    });
}


    private void initializeListeners() {

        addButton.addActionListener(e -> openAddClientForm());


        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) { filterTable();}
            @Override public void removeUpdate(DocumentEvent e) {filterTable(); }
            @Override public void changedUpdate(DocumentEvent e) { filterTable();}

                 private void filterTable() {
    String text = searchField.getText().trim();
    int selectedOption = searchChoiceCombo.getSelectedIndex();

    // Réinitialiser le filtre si le champ de recherche est vide ou contient le texte par défaut
    if (text.isEmpty() || text.equals("Rechercher...")) {
        clientTable.setRowSorter(null);
        return;
    }

   sorter = new TableRowSorter<>(tableModel);

    if (selectedOption == 0) {
        // Filtre global qui recherche dans toutes les colonnes
        RowFilter<Object, Object> globalFilter = new RowFilter<Object, Object>() {
            @Override
            public boolean include(RowFilter.Entry<? extends Object, ? extends Object> entry) {
                String searchText = text.toLowerCase();
                for (int i = 0; i < entry.getValueCount(); i++) {
                    Object cellValue = entry.getValue(i);
                    String cellText = (cellValue != null) ? cellValue.toString().toLowerCase().trim() : "";
                    if (!cellText.contains("components.actionpanel") && cellText.contains(searchText)) {
                        return true;
                    }
                }
                return false;
            }
        };

        sorter.setRowFilter(globalFilter);
        clientTable.setRowSorter(sorter);
        return;
    }

    RowFilter<Object, Object> customFilter = new RowFilter<Object, Object>() {
        @Override
        public boolean include(RowFilter.Entry<? extends Object, ? extends Object> entry) {
            String searchText = text.toLowerCase();

            // Correspondance exacte pour la colonne N° Vis
            if (selectedOption == 1) {
                Object idValue = entry.getValue(0);
                return idValue != null && idValue.toString().equalsIgnoreCase(searchText);
            }

            // Correspondance partielle pour les autres colonnes
            Object cellValue = entry.getValue(selectedOption - 1);
            String cellText = (cellValue != null) ? cellValue.toString().toLowerCase().trim() : "";
            return cellText.contains(searchText);
        }
    };

    sorter.setRowFilter(customFilter);
    clientTable.setRowSorter(sorter);
}
        });
    }

    public void openAddClientForm() {
       
          ModalDialog.showModal(this, new SimpleModalBorder(new AddClientForm(ClientPanel.this), "Ajouter Client", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
              loadData();
            })); 
        
    }

    private void configureActionColumn(JTable table, ClientTableModel tableModel) {
        
        table.getColumnModel().getColumn(6).setMinWidth(200); // Ajustez selon vos besoins
        table.getColumnModel().getColumn(6).setMaxWidth(150);
         table.getColumnModel().getColumn(4).setMinWidth(100);
       
        TableColumn actionColumn = table.getColumnModel().getColumn(tableModel.getColumnCount() - 1);
        actionColumn.setCellRenderer(new ActionCellRenderer());
        actionColumn.setCellEditor(new ActionCellEditor(new ActionTableEvent() {

            @Override
            public void onEdit(int row) {
                openEditClientForm(row); // Appeler la méthode pour ouvrir le dialogue d'édition
            }

            @Override
            public void onDelete(int row) {

                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                deleteSelectedClient(row);
                // Optionnel : Mettre à jour l'affichage ou faire d'autres actions après la suppression
            }

        }));
    }

    private void openEditClientForm(int row) {
        // Convertir l'indice de la vue en indice du modèle
        int modelRow = clientTable.convertRowIndexToModel(row);

        // Récupérer le client correspondant dans le modèle
        ModelClient client = tableModel.getClientAt(modelRow);

        // Ouvrir la boîte de dialogue d'édition avec les informations du client
        AddClientForm dialog = new AddClientForm(ClientPanel.this, client);
       
          ModalDialog.showModal(this, new SimpleModalBorder(dialog, "Modifier Client", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
             loadData();
              clientTable.setRowSelectionInterval(row, row);
              clientTable.scrollRectToVisible(clientTable.getCellRect(row, 0, true)); 
            }));
          
    }


    private void deleteSelectedClient(int row) {

        ModelClient client = tableModel.getClientAt(row);

        // Personnalisation des boutons de JOptionPane
        Object[] options = {"Oui", "Annuler"};
        int response = JOptionPane.showOptionDialog(this,
                "Voulez-vous vraiment supprimer ce client ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[0]);

        if (response == JOptionPane.YES_OPTION) {
            try {
                DatabaseUtils.deleteDevisByClientId(client.getClientId()); // Supprimer les devis associés
                DatabaseUtils.deleteClient(client.getClientId());
          

                loadData(); // Recharger les données depuis la base de données
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Erreur lors de la suppression du client: " + e.getMessage());
            } catch (Exception ex) {
                Logger.getLogger(ClientPanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

   private void loadData() {
        clients = (ArrayList<ModelClient>) DatabaseUtils.getAllClients(); // Récupérer les clients depuis la base de données
        tableModel.setClients(clients); // Mettre à jour le modèle de table avec les clients
          for (int i : new int[]{0, 5}) {
        clientTable.getColumnModel().getColumn(i).setCellRenderer(new CenterTableCellRenderer ());
          }   
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
