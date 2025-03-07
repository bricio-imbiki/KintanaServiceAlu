/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Panel;


import Components.ActionCellEditor;
import Components.ActionCellRenderer;
import Components.ActionTableEvent;
import Components.CellStatus;
import Components.CenterTableCellRenderer;
import Form.AddChassisForm;
import Components.CustomSearchField;
import Components.RightAlignTableCellRenderer;
import Components.RollOverTable;
import Components.RoundedButton;
import Components.RoundedComboBox;
import Components.StatusCellRenderer;
import ConnexionDB.DatabaseUtils;
import Models.ModelChassis;
import Models.ModelProfileAlu;
import TableModel.ProfileTableModel;
import Scroll.ScrollPaneWin11;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import static javax.swing.SwingConstants.CENTER;
import static javax.swing.SwingConstants.RIGHT;
import javax.swing.SwingWorker;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;
import raven.modal.ModalDialog;
import raven.modal.component.SimpleModalBorder;

/**
 *
 * @author brici_6ul2f65
 */
public class ProfilePanel extends javax.swing.JPanel {

    private JPanel leftPanel;
    private RoundedButton addButton;
  
    private JPanel tablePanel;
    private JPanel titlePanel;
    private JLabel titleLabel;
    private ScrollPaneWin11 scroll;
 
 
    private CustomSearchField searchField;
  

    private ArrayList<ModelChassis> profile;
    private JTable profileTable;
    private TableRowSorter<ProfileTableModel> sorter;
    private ProfileTableModel profileTableModel;
    private RoundedComboBox<String> searchChoiceCombo;
    private JPanel profilePanel;
    private JPanel profileTablePanel;

 public ProfilePanel() throws Exception {
     
     init();
    loadDataInBackground();
     initializeListeners();
    tablePanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:1,1,1,1,$TableHeader.bottomSeparatorColor,,10");
    scroll.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:3,0,3,0,$Table.background,10,10");
    scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "hoverTrackColor:null");
   
}
 
     private void init(){
        
    setLayout(new BorderLayout());

    profilePanel = new JPanel(new BorderLayout());
    profileTablePanel = new JPanel(new BorderLayout(0,5));
    profileTablePanel.setBorder(new EmptyBorder(15, 10, 15, 10));
    tablePanel = new JPanel(new BorderLayout());
    
    titlePanel = new JPanel(new BorderLayout());
    titlePanel.setPreferredSize(new Dimension(20, 35));
    titleLabel = new JLabel("Gestion de Sctock Profilés Aluminium");
    // Configurez le style du label si nécessaire
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    titlePanel.add( titleLabel, BorderLayout.WEST);
    
     profile = new ArrayList<>();
profileTableModel = new ProfileTableModel();

profileTable = new RollOverTable(profileTableModel);


profileTable.setRowSorter(sorter);
profileTable.setFont(new Font("Dialog", Font.BOLD, 13));
profileTable.setRowHeight(30);
profileTable.setSelectionBackground(new Color(75, 110, 175));
configureActionColumnProfile(profileTable, profileTableModel);
// Configurer l'apparence du header du tableau
JTableHeader header = profileTable.getTableHeader();
((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
 header.setBackground(new Color(26, 26, 26));
header.setForeground(Color.WHITE);
header.setFont(new Font("Dialog", Font.BOLD, 12));
header.setReorderingAllowed(false);

profileTable.setFillsViewportHeight(true);
profileTable.setShowGrid(true);
profileTable.setGridColor(Color.GRAY);
profileTable.setShowHorizontalLines(true);
profileTable.setShowVerticalLines(true);
  //  tablePanelContenaire.add( titlePanel, BorderLayout.NORTH);
    scroll = new ScrollPaneWin11();
    scroll.setViewportView(profileTable);
    //tablePanelContenaire.add( titlePanel, BorderLayout.NORTH);
    tablePanel.add(scroll, BorderLayout.CENTER);
    
  
    profileTablePanel.add(tablePanel, BorderLayout.CENTER);
     profileTablePanel.add(titlePanel, BorderLayout.NORTH);
      // Configuration du panneau d'action
    JPanel actionPanel = new JPanel(new BorderLayout());
   leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 1));
    actionPanel.setBorder(new EmptyBorder(15, 5, 0, 0));
    
     //new Color(92, 184, 92)
        Font buttonFont = new Font("Dialog", Font.BOLD, 14);
        Dimension buttonSize = new Dimension(150, 40);
             addButton = new RoundedButton("Ajouter", new Color(46, 204, 113), new Color(60, 224, 133));
            addButton.setIcon(new ImageIcon(getClass().getResource("/Icon/icons8-add-26 (5).png")));
   
        JButton[] buttons = {addButton};
        for (JButton button : buttons) {
            button.setFont(buttonFont);
            button.setPreferredSize(buttonSize);
            button.setForeground(Color.WHITE); // Couleur du texte
        }
    Icon searchIcon = new ImageIcon(getClass().getResource("/Icon/search.png")); // Remplacez par le chemin de votre icône

    Icon clearIcon = new ImageIcon(getClass().getResource("/Icon/clear.png"));
    searchField = new CustomSearchField("Rechercher...", searchIcon, clearIcon);
     String[] columns = {"Choisir filtre", "N° Profile",  "Modele Profile","Type", "Couleur" , "Longueur","Prix Unitaire","Quantite Stock","Fournisseur","Etat du stock"};
    searchChoiceCombo = new RoundedComboBox<>(columns);
    searchField.setPreferredSize(new Dimension(295, 40)); // Ajustez les valeurs pour la largeur (300) et la hauteur (40)
 searchChoiceCombo.setPreferredSize(new Dimension(searchChoiceCombo.getPreferredSize().width, 40));
   leftPanel.add(addButton);
   leftPanel.add(searchField);
   leftPanel.add(searchChoiceCombo);
  
    actionPanel.add(leftPanel, BorderLayout.CENTER);
    
    profilePanel.add(profileTablePanel, BorderLayout.CENTER); // Add table panel in center
    profilePanel.add(actionPanel, BorderLayout.NORTH); // Add button panel above the table
    add( profilePanel, BorderLayout.CENTER);    
    
    profilePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tablePanel.requestFocusInWindow();
            }
        });

    }
 private void configureActionColumnProfile(JTable table, ProfileTableModel tableModel) {
        table.getColumnModel().getColumn(9).setMinWidth(200); // Ajustez selon vos besoins
        table.getColumnModel().getColumn(9).setMaxWidth(150);
         table.getColumnModel().getColumn(7).setMinWidth(140);
       
        TableColumn actionColumn = table.getColumnModel().getColumn(tableModel.getColumnCount() - 1);
        actionColumn.setCellRenderer(new ActionCellRenderer());
        actionColumn.setCellEditor(new ActionCellEditor(new ActionTableEvent() {

            @Override
            public void onEdit(int row) {
                try {
                    openEditProfileForm(row); // Appeler la méthode pour ouvrir le dialogue d'édition
                } catch (SQLException ex) {
                    Logger.getLogger(ProfilePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void onDelete(int row) {

                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
            deleteSelectedChassis(row);
                // Optionnel : Mettre à jour l'affichage ou faire d'autres actions après la suppression
            }

        }));
    }
       private void openEditProfileForm(int row) throws SQLException {
            //  int modelRow = profileTable.convertRowIndexToModel(row);

        // Récupérer le client correspondant dans le modèle
        ModelChassis profile = profileTableModel.getProfileAt(row);

        // Ouvrir la boîte de dialogue d'édition avec les informations du client
        AddChassisForm dialog = new AddChassisForm(ProfilePanel.this, profile);
       
          ModalDialog.showModal(this, new SimpleModalBorder(dialog, "Modifier Chassis", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
               loadData();
              profileTable.setRowSelectionInterval(row, row);
              profileTable.scrollRectToVisible(profileTable.getCellRect(row, 0, true)); 
            
            }));
          
            }

    private void deleteSelectedChassis(int row) {
    ModelChassis chassis = profileTableModel.getProfileAt(row); // Remplacez par la méthode appropriée pour obtenir le chassis à partir de la ligne

    // Personnalisation des boutons de JOptionPane
    Object[] options = {"Oui", "Annuler"};
    int response = JOptionPane.showOptionDialog(this,
            "Voulez-vous vraiment supprimer ce châssis ?",
            "Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE,
            null,
            options,
            options[0]);

    if (response == JOptionPane.YES_OPTION) {
        try {
            DatabaseUtils.deleteChassis(chassis.getChassisId()); // Appel à la méthode de suppression du chassis

            loadData(); // Recharger les données depuis la base de données
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du châssis: " + e.getMessage());
        } catch (Exception ex) {
            Logger.getLogger(ProfilePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

   private void initializeListeners() {

      addButton.addActionListener(e -> {
          try {
              openAddProfileAluForm();
          } catch (SQLException ex) {
              Logger.getLogger(ProfilePanel.class.getName()).log(Level.SEVERE, null, ex);
          }
      });


        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterTable();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterTable();
            }

                 private void filterTable() {
    String text = searchField.getText().trim();
    int selectedOption = searchChoiceCombo.getSelectedIndex();

    // Réinitialiser le filtre si le champ de recherche est vide ou contient le texte par défaut
    if (text.isEmpty() || text.equals("Rechercher...")) {
        profileTable.setRowSorter(null);
        return;
    }

   sorter = new TableRowSorter<>(profileTableModel);

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
        profileTable.setRowSorter(sorter);
        return;
    }

       RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
        @Override
        public boolean include(RowFilter.Entry<? extends Object, ? extends Object> entry) {
            // Vérifier si l'option sélectionnée est "Toutes colonnes"
            if (selectedOption == 0) {
                // Parcourir toutes les colonnes pour une correspondance
                for (int i = 0; i < entry.getValueCount(); i++) {
                    if (matches(entry.getValue(i), text)) {
                        return true;
                    }
                }
                return false;
            } else {
                // Vérifier la colonne spécifique correspondant à l'option sélectionnée
                int columnIndex = selectedOption - 1;
                return matches(entry.getValue(columnIndex), text);
            }
        }

        // Méthode utilitaire pour vérifier si une cellule contient le texte recherché
        private boolean matches(Object cellValue, String searchText) {
       
          if (cellValue == null) return false;
if (cellValue instanceof CellStatus) {
    CellStatus cellStatus = (CellStatus) cellValue; // Explicit cast for Java 11 compatibility
    return cellStatus.getStatut() != null && cellStatus.getStatut().toLowerCase().contains(searchText);
}
            return cellValue.toString().toLowerCase().contains(searchText);
        }
    };

      sorter.setRowFilter(filter);
    profileTable.setRowSorter(sorter);
}
        });
    }
 public void openAddProfileAluForm() throws SQLException {
         ModalDialog.showModal(this, new SimpleModalBorder(new AddChassisForm(ProfilePanel.this), "Ajouter Profile Aluminium", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
              loadData();
            }));
          
    }


 private void loadData()  {
         profile = (ArrayList<ModelChassis>) DatabaseUtils.getAllChassis(); // Récupérer les devis depuis la base de données
      profileTableModel.setProfileList(  profile); // Définir la liste de devis pour le modèle de table

        // Appliquer le renderer à la colonne ID
            profileTable.getColumnModel().getColumn(8).setCellRenderer(new StatusCellRenderer());
         profileTable.getColumnModel().getColumn(5).setCellRenderer(new RightAlignTableCellRenderer());
         for (int i : new int[]{0, 4,6}) {
           profileTable.getColumnModel().getColumn(i).setCellRenderer(new CenterTableCellRenderer());
        }


    }
 private void loadDataInBackground() {
    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() throws Exception {
            loadData();
            return null;
        }

        @Override
        protected void done() {
            // Mettez à jour l'interface utilisateur après le chargement des données si nécessaire
        }
    };
    worker.execute();
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
