/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Panel;

import Components.ActionCellEditor;
import Components.ActionCellRenderer;
import Components.ActionTableEvent;
import Components.CenterTableCellRenderer;
import Components.CustomSearchField;
import Components.RightAlignTableCellRenderer;
import Components.RollOverTable;
import Components.RoundedButton;
import Components.RoundedComboBox;
import ConnexionDB.DatabaseUtils;
import Form.AddChassisForm;
import Form.PaymentFactureForm;
import Form.PaymentFactureForm;
import Models.ModelPaiementFacture;
import Scroll.ScrollPaneWin11;
import TableModel.PaiementFactureTableModel;
import com.formdev.flatlaf.FlatClientProperties;
import java.awt.BorderLayout;
import java.awt.Color;
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
public class PaiementFacture extends javax.swing.JPanel {

    private JPanel leftPanel;
//    private RoundedButton addButton;
  
    private JPanel tablePanel;
    private JPanel titlePanel;
    private JLabel titleLabel;
    private ScrollPaneWin11 scroll;
 
 
    private CustomSearchField searchField;
  

    private ArrayList<ModelPaiementFacture> factures;
    private JTable facturesTable;
    private TableRowSorter<PaiementFactureTableModel> sorter;
    private PaiementFactureTableModel factureTableModel;
    private RoundedComboBox<String> searchChoiceCombo;
    private JPanel facturesPanel;
    private JPanel facturesTablePanel;

 public PaiementFacture() throws Exception {
     
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

    facturesPanel = new JPanel(new BorderLayout());
    facturesTablePanel = new JPanel(new BorderLayout(0,5));
    facturesTablePanel.setBorder(new EmptyBorder(15, 10, 15, 10));
    tablePanel = new JPanel(new BorderLayout());
    
    titlePanel = new JPanel(new BorderLayout());
    titlePanel.setPreferredSize(new Dimension(20, 35));
    titleLabel = new JLabel("Paiement Factures");
    // Configurez le style du label si nécessaire
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    titlePanel.add( titleLabel, BorderLayout.WEST);
    
     factures = new ArrayList<>();
factureTableModel = new PaiementFactureTableModel();

facturesTable = new RollOverTable(factureTableModel);


facturesTable.setRowSorter(sorter);
facturesTable.setFont(new Font("Dialog", Font.BOLD, 13));
facturesTable.setRowHeight(30);
facturesTable.setSelectionBackground(new Color(75, 110, 175));
configureActionColumnfactures(facturesTable, factureTableModel);
// Configurer l'apparence du header du tableau
JTableHeader header = facturesTable.getTableHeader();
((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
 header.setBackground(new Color(26, 26, 26));
header.setForeground(Color.WHITE);
header.setFont(new Font("Dialog", Font.BOLD, 12));
header.setReorderingAllowed(false);

facturesTable.setFillsViewportHeight(true);
facturesTable.setShowGrid(true);
facturesTable.setGridColor(Color.GRAY);
facturesTable.setShowHorizontalLines(true);
facturesTable.setShowVerticalLines(true);
  //  tablePanelContenaire.add( titlePanel, BorderLayout.NORTH);
    scroll = new ScrollPaneWin11();
    scroll.setViewportView(facturesTable);
    //tablePanelContenaire.add( titlePanel, BorderLayout.NORTH);
    tablePanel.add(scroll, BorderLayout.CENTER);
    
  
    facturesTablePanel.add(tablePanel, BorderLayout.CENTER);
     facturesTablePanel.add(titlePanel, BorderLayout.NORTH);
      // Configuration du panneau d'action
    JPanel actionPanel = new JPanel(new BorderLayout());
   leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 1));
    actionPanel.setBorder(new EmptyBorder(15, 5, 0, 0));
    
     //new Color(92, 184, 92)
        Font buttonFont = new Font("Dialog", Font.BOLD, 14);
        Dimension buttonSize = new Dimension(150, 40);
//             addButton = new RoundedButton("Ajouter", new Color(46, 204, 113), new Color(60, 224, 133));
//            addButton.setIcon(new ImageIcon(getClass().getResource("/Icon/icons8-add-26 (5).png")));
//   
//        JButton[] buttons = {addButton};
//        for (JButton button : buttons) {
//            button.setFont(buttonFont);
//            button.setPreferredSize(buttonSize);
//            button.setForeground(Color.WHITE); // Couleur du texte
//        }
    Icon searchIcon = new ImageIcon(getClass().getResource("/Icon/search.png")); // Remplacez par le chemin de votre icône

    Icon clearIcon = new ImageIcon(getClass().getResource("/Icon/clear.png"));
    searchField = new CustomSearchField("Rechercher...", searchIcon, clearIcon);
  String[] columns = {"Choisir filtre", "N° Paiement","N° Fcture", "Date de Paiement", "Mode de Paiement","Montant Payé"};
   searchChoiceCombo = new RoundedComboBox<>(columns);
   searchField.setPreferredSize(new Dimension(295, 40)); // Ajustez les valeurs pour la largeur (300) et la hauteur (40)
   searchChoiceCombo.setPreferredSize(new Dimension(searchChoiceCombo.getPreferredSize().width, 40));
   //leftPanel.add(addButton);
   leftPanel.add(searchField);
   leftPanel.add(searchChoiceCombo);
  
    actionPanel.add(leftPanel, BorderLayout.CENTER);
    
    facturesPanel.add(facturesTablePanel, BorderLayout.CENTER); // Add table panel in center
    facturesPanel.add(actionPanel, BorderLayout.NORTH); // Add button panel above the table
    add( facturesPanel, BorderLayout.CENTER);    
    
    facturesPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tablePanel.requestFocusInWindow();
            }
        });

    }
 private void configureActionColumnfactures(JTable table, PaiementFactureTableModel tableModel) {
        table.getColumnModel().getColumn(5).setMinWidth(200); // Ajustez selon vos besoins
        table.getColumnModel().getColumn(5).setMaxWidth(150);
      
       
        TableColumn actionColumn = table.getColumnModel().getColumn(tableModel.getColumnCount() - 1);
        actionColumn.setCellRenderer(new ActionCellRenderer());
        actionColumn.setCellEditor(new ActionCellEditor(new ActionTableEvent() {

            @Override
            public void onEdit(int row) {
                try {
                    openEditfacturesForm(row); // Appeler la méthode pour ouvrir le dialogue d'édition
                } catch (SQLException ex) {
                    Logger.getLogger(PaiementFacture.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void onDelete(int row) {

                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
            deleteSelectedfacture(row);
                // Optionnel : Mettre à jour l'affichage ou faire d'autres actions après la suppression
            }

        }));
    }
       private void openEditfacturesForm(int row) throws SQLException {
            //  int modelRow = facturesTable.convertRowIndexToModel(row);

        // Récupérer le client correspondant dans le modèle
        ModelPaiementFacture facture = factureTableModel.getPaiementFactureAt(row);

        // Ouvrir la boîte de dialogue d'édition avec les informations du client
        PaymentFactureForm dialog = new PaymentFactureForm(PaiementFacture.this, facture);
       
          ModalDialog.showModal(this, new SimpleModalBorder(dialog, "Modifier L'facture", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
            try {
                loadData();
            } catch (SQLException ex) {
                Logger.getLogger(PaiementFacture.class.getName()).log(Level.SEVERE, null, ex);
            }
              facturesTable.setRowSelectionInterval(row, row);
              facturesTable.scrollRectToVisible(facturesTable.getCellRect(row, 0, true)); 
            
            }));
          
            }

    private void deleteSelectedfacture(int row) {
    ModelPaiementFacture facture = factureTableModel.getPaiementFactureAt(row); // Remplacez par la méthode appropriée pour obtenir le chassis à partir de la ligne

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
            DatabaseUtils.deletePaiement(facture.getFacture().getFactureId()); // Appel à la méthode de suppression du chassis

            loadData(); // Recharger les données depuis la base de données
        } catch (Exception ex) {
            Logger.getLogger(PaiementFacture.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

   private void initializeListeners() {

//      addButton.addActionListener(e -> {
//          try {
//              openAddfacturesAluForm();
//          } catch (SQLException ex) {
//              Logger.getLogger(PaiementFacture.class.getName()).log(Level.SEVERE, null, ex);
//          }
//      });


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
        facturesTable.setRowSorter(null);
        return;
    }

   sorter = new TableRowSorter<>(factureTableModel);

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
        facturesTable.setRowSorter(sorter);
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
    facturesTable.setRowSorter(sorter);
}
        });
    }
// public void openAddfacturesAluForm() throws SQLException {
//         ModalDialog.showModal(this, new SimpleModalBorder(new AddChassisForm(PaiementFacture.this), "Ajouter factures Aluminium", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
//             try {
//                 loadData();
//             } catch (SQLException ex) {
//                 Logger.getLogger(PaiementFacture.class.getName()).log(Level.SEVERE, null, ex);
//             }
//            }));
//          
//    }


 private void loadData() throws SQLException  {
         factures = (ArrayList<ModelPaiementFacture>) DatabaseUtils.getAllFacturePaiements(); // Récupérer les devis depuis la base de données
      factureTableModel.setPaiementFactures(  factures); // Définir la liste de devis pour le modèle de table

     
         
              facturesTable.getColumnModel().getColumn(4).setCellRenderer(new RightAlignTableCellRenderer());
         for (int i : new int[]{0,1,2,3}) {
           facturesTable.getColumnModel().getColumn(i).setCellRenderer(new CenterTableCellRenderer());
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
