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

import Components.RollOverTable;
import TableModel.CommandeTableModel;
import Components.RoundedButton;
import TableModel.DevisTableModel;
import Models.ModelCommande;
import java.sql.SQLException;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import Components.CustomSearchField;
import Components.RightAlignTableCellRenderer;
import java.util.ArrayList;
import Components.RollOverTable;
import Components.RoundedButton;
import Components.RoundedComboBox;
import Components.StatusCellRenderer;
import Models.ModelDevis;
import ConnexionDB.DatabaseUtils;
import Form.PaymentAcompteForm;
import Form.PaymentFactureForm;
import Models.ModelStatut.ModelStatutCommande;
import Scroll.ScrollPaneWin11;
import Views.Dashboard;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static javax.swing.SwingConstants.CENTER;
import static javax.swing.SwingConstants.RIGHT;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
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
public class CommandePanel extends javax.swing.JPanel {

    private ArrayList<ModelCommande> commandes;

    private TableRowSorter<CommandeTableModel> sorter;
    public static CommandeTableModel tableModel;

    private RoundedButton factureButton;
    private JPanel panel;
    private CustomSearchField searchField;
    private JTable commandeTable;
    private RoundedComboBox<String> searchChoiceCombo;
    private JPanel tablePanelContenaire;
    private JPanel tablePanel;
    private JPanel titlePanel;
    private JLabel titleLabel;
    private ScrollPaneWin11 scroll;
   
    private JComboBox<String> statutCombo;
    private RoundedButton UpdateSatutButton;
    private int newStatutId;

    /**
     * Creates new form CommandePanel
     */
    public CommandePanel() {
        initComponents();
        initializeComponents();
        layoutComponents();
        initializeListeners();
        loadData();
          tablePanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:1,1,1,1,$TableHeader.bottomSeparatorColor,,10");

        scroll.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:3,0,3,0,$Table.background,10,10");
        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "hoverTrackColor:null");
    }

    private void initializeComponents() {
        commandes = new ArrayList<>();
        tableModel = new CommandeTableModel();
        commandeTable = new JTable(tableModel);
        commandeTable = new RollOverTable(tableModel);
         commandeTable.setFont(new Font("Dialog", Font.BOLD, 13)); 
        commandeTable.setRowHeight(30);
        configureActionColumn(commandeTable, tableModel);
        // Configurer l'apparence du header du tableau
        JTableHeader header = commandeTable.getTableHeader();
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
      header.setBackground(new Color(26, 26, 26));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Dialog", Font.BOLD, 12));
        header.setReorderingAllowed(false);

        commandeTable.setFillsViewportHeight(true);
        commandeTable.setShowGrid(true);
        commandeTable.setGridColor(Color.GRAY);
        commandeTable.setShowHorizontalLines(true);
        commandeTable.setShowVerticalLines(true);
 commandeTable.setSelectionBackground(new Color(75, 110, 175));
        Font buttonFont = new Font("Dialog", Font.BOLD, 14);
        Dimension buttonSize = new Dimension(150, 40);

     factureButton = new RoundedButton("Facturé", new Color(240, 173, 78), new Color(255, 193, 94));
        factureButton.setIcon(new ImageIcon(getClass().getResource("/Icon/convert.png")));
        UpdateSatutButton = new RoundedButton("Modifier Statut", new Color(0, 188, 212), new Color(20, 208, 232));
        UpdateSatutButton.setIcon(new ImageIcon(getClass().getResource("/Icon/update.png")));
      
        JButton[] buttons = {factureButton,UpdateSatutButton};

        for (JButton button : buttons) {
            button.setFont(buttonFont);
            button.setPreferredSize(buttonSize);
            button.setForeground(Color.WHITE); // Couleur du texte
        }
        // Charger l'icône de recherche
        Icon searchIcon = new ImageIcon(getClass().getResource("/Icon/search.png")); // Remplacez par le chemin de votre icône

        Icon clearIcon = new ImageIcon(getClass().getResource("/Icon/clear.png"));
        searchField = new CustomSearchField("Rechercher...", searchIcon, clearIcon);

// Définir la taille préférée du searchField
        searchField.setPreferredSize(new Dimension(295, 40)); // Ajustez les valeurs pour la largeur (300) et la hauteur (40)

        setLayout(new BorderLayout());
    }

    private void layoutComponents() {
           // Utilisation du RoundedComboBox
        String[] columns = { "Choisir filtre", "N° Commande","N° Devis", "Client", "Date de Commande", "Total HT","% TVA", "Total TTC","Acompte Payé","Reste à Payer", "Statut"};
        // Créer un JComboBox avec des coins arrondis
        searchChoiceCombo = new RoundedComboBox<>(columns); // 20 = rayon des coins
            statutCombo = new JComboBox<>();
          populateComboStatutCommande(statutCombo);
         searchChoiceCombo.setPreferredSize(new Dimension(searchChoiceCombo.getPreferredSize().width, 40)); 
      
         statutCombo.setPreferredSize(new Dimension(searchChoiceCombo.getPreferredSize().width, 40)); 

    
      tablePanelContenaire = new JPanel(new BorderLayout(0,5));
    tablePanelContenaire.setBorder(new EmptyBorder(15, 15, 15, 15));
     tablePanel = new JPanel(new BorderLayout());
    titlePanel = new JPanel(new BorderLayout());
      titlePanel.setPreferredSize(new Dimension(20, 35));
    titleLabel = new JLabel("Gestion des Commandes");
    // Configurez le style du label si nécessaire
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    titlePanel.add( titleLabel, BorderLayout.WEST);
    scroll = new ScrollPaneWin11();
    scroll.setViewportView(commandeTable);
    tablePanelContenaire.add( titlePanel, BorderLayout.NORTH);
    tablePanel.add(scroll, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new BorderLayout());
        actionPanel.setBorder(new EmptyBorder(15, 5, 0, 0));

      

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 1));
           JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 1));
         leftPanel.add(factureButton);
        leftPanel.add(searchField);
        leftPanel.add(searchChoiceCombo);
           rightPanel.add(statutCombo);
            rightPanel.add(UpdateSatutButton);
        actionPanel.add(leftPanel, BorderLayout.WEST);
            actionPanel.add(rightPanel, BorderLayout.EAST);

     tablePanelContenaire.add(tablePanel, BorderLayout.CENTER);
    add(actionPanel, BorderLayout.NORTH);
    add(tablePanelContenaire, BorderLayout.CENTER);

        tablePanel.setFocusable(true);
        actionPanel.setFocusable(true);

        tablePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                tablePanel.requestFocusInWindow();
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
     
       factureButton.addActionListener(e -> {
                 int selectedRow = commandeTable.getSelectedRow();
        if (selectedRow != -1) {
               String numeroDevis = (String) commandeTable.getValueAt(selectedRow, 0);
                int commandeId = Integer.parseInt(numeroDevis) ;// Extrait "0001" et le convertit en entier
                     try {
                         // Vérifier si le devis a déjà été converti en commande
                         if (DatabaseUtils.isCommandeAlreadyConverted(commandeId)) {
                             JOptionPane.showMessageDialog(this,
                                     "Cette commande a déjà été convertie en facture.",
                                     "Erreur",
                                     JOptionPane.ERROR_MESSAGE);
                             return; // Sortir de la méthode si le devis est déjà converti
                         }        } catch (SQLException ex) {
                         Logger.getLogger(DevisPanel.class.getName()).log(Level.SEVERE, null, ex);
                     }
            openPaymentFactureForm(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, 
            "Veuillez sélectionner une commande pour effectuer le paiement.", 
            "Erreur", 
            JOptionPane.WARNING_MESSAGE);
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
    // Récupérer le texte de recherche et l'option sélectionnée
    String text = searchField.getText().trim().toLowerCase();
    int selectedOption = searchChoiceCombo.getSelectedIndex();

    // Si le champ de recherche est vide ou "Rechercher...", réinitialiser le filtre
    if (text.isEmpty() || text.equals("rechercher...")) {
        commandeTable.setRowSorter(null);
        return;
    }

    // Initialiser le sorter
    sorter = new TableRowSorter<>(tableModel);

    // Créer un filtre en fonction de l'option sélectionnée
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

    // Appliquer le filtre à la table
    sorter.setRowFilter(filter);
    commandeTable.setRowSorter(sorter);
}

        });
        
        
UpdateSatutButton.addActionListener(e -> {
    int selectedRow = commandeTable.getSelectedRow();

    // Vérifier qu'une ligne est sélectionnée
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(null, "Veuillez sélectionner une commande dans le tableau.",
                                      "Aucune sélection", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Récupérer la commande depuis le modèle
    CommandeTableModel tableModel = (CommandeTableModel) commandeTable.getModel();
    ModelCommande selectedCommande = tableModel.getCommandeAt(selectedRow);

    // Vérifier que le ComboBox a un statut sélectionné
    if (statutCombo.getSelectedIndex() == 0) {
        JOptionPane.showMessageDialog(null, "Veuillez choisir le statut d'abord.",
                                      "Erreur", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Récupérer le statut actuel et le statut sélectionné
    String currentStatut = selectedCommande.getStatut().getStatut();
    String selectedStatut = (String) statutCombo.getSelectedItem();

    // Vérifier si la commande est déjà convertie en facture
    boolean isAlreadyFactured;
    try {
        isAlreadyFactured = DatabaseUtils.isCommandeAlreadyConverted(selectedCommande.getCommandeId());
        if (isAlreadyFactured) {
            // Bloc spécifique pour commande déjà facturée
            if (selectedStatut.contains("En production") || 
                selectedStatut.contains("En Attente") || 
                selectedStatut.contains("Annulée")) {
                JOptionPane.showMessageDialog(null, "Impossible de changer le statut en '" + selectedStatut +
                                                      "' car la commande est déjà facturée.",
                                              "Action non autorisée", JOptionPane.ERROR_MESSAGE);
                return;
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Erreur lors de la vérification de l'état de la commande.",
                                      "Erreur", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Vérifier si le statut actuel est déjà "Facturé"
    if (currentStatut.contains("Facturé")) {
        if (selectedStatut.contains("Facturé")) {
            JOptionPane.showMessageDialog(null, "La commande est déjà facturée. Aucun changement nécessaire.",
                                          "Statut inchangé", JOptionPane.INFORMATION_MESSAGE);
            return;
        } else if ("Prête".equalsIgnoreCase(selectedStatut) || 
                   "Livrée".equalsIgnoreCase(selectedStatut)) {
            // Mise à jour autorisée
            int newStatutId = getStatutIdByName(selectedStatut);
            try {
                DatabaseUtils.updateStatutCommande(selectedCommande.getCommandeId(), newStatutId);
               loadData();
                JOptionPane.showMessageDialog(null, "Statut mis à jour : " + selectedStatut,
                                              "Succès", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erreur lors de la mise à jour du statut.",
                                              "Erreur", JOptionPane.ERROR_MESSAGE);
            }
            return;
        } else {
            JOptionPane.showMessageDialog(null, "Impossible de changer le statut en '" + selectedStatut +
                                                  "' pour une commande facturée.",
                                          "Action non autorisée", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    // Mise à jour du statut pour les autres cas
    int newStatutId = getStatutIdByName(selectedStatut);
    try {
        DatabaseUtils.updateStatutCommande(selectedCommande.getCommandeId(), newStatutId);
        loadData();
        JOptionPane.showMessageDialog(null, "Statut mis à jour : " + selectedStatut,
                                      "Succès", JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Erreur lors de la mise à jour du statut.",
                                      "Erreur", JOptionPane.ERROR_MESSAGE);
    }
});



    }

   public static void setComboBoxRenderer(JComboBox<String> comboBox) {
        comboBox.setPreferredSize(new Dimension(200, 34));  // Taille légèrement augmentée pour un look moderne


        comboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                // Utilisation d'une police moderne
              putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +2;");
               // setBorder(new EmptyBorder(8, 10, 8, 10));
                // Gestion du texte par défaut si aucune option n'est sélectionnée
                if (value == null || value.toString().isEmpty()) {

                    setText("Sélectionnez Statut");
                   setForeground(Color.GRAY);  // Texte grisé pour le placeholder
                } else {
                  // setForeground(Color.BLACK);  // Couleur normale pour les options sélectionnées
                }
                if (isSelected || comboBox.getSelectedItem() == value) {
                    putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +2;");
                    //setBorder(new EmptyBorder(8, 10, 8, 10));
                    //setBackground(Color.LIGHT_GRAY);  // Couleur de fond lorsque sélectionné
                    

                } else {
                  putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +2;");  // Conserver la police par défaut pour les autres éléments
                  //  setBackground(Color.WHITE);  // Couleur de fond par défaut
                }

                return this;
            }
        });


    }
    public void populateComboStatutCommande(JComboBox<String> statutCombo) {
         statutCombo.removeAllItems(); // Vider les éléments existants
        statutCombo.addItem(""); // Ajouter un élément vide
    // Appeler la méthode pour récupérer les statuts depuis la base
    List<ModelStatutCommande> statutList = DatabaseUtils.getAllStatutCommande();

    // Vérifier si la liste n'est pas vide
    if (statutList != null && !statutList.isEmpty()) {
        // Extraire les noms des statuts dans un tableau de String
        String[] statuts = statutList.stream()
                                     .map(ModelStatutCommande::getStatut)
                                     .toArray(String[]::new);

        // Ajouter les statuts au ComboBox
        for (String statut : statuts) {
            statutCombo.addItem(statut);
        }
    } else {
        // Gérer le cas où aucun statut n'est récupéré
        System.out.println("Aucun statut trouvé dans la base de données.");
    }
    setComboBoxRenderer( statutCombo);
}
public int getStatutIdByName(String statutName) {
    List<ModelStatutCommande> statutList = DatabaseUtils.getAllStatutCommande();
    for (ModelStatutCommande statut : statutList) {
        if (statut.getStatut().equalsIgnoreCase(statutName)) {
            return statut.getStatutId();
        }
    }
    return -1; // Retourne -1 si le statut n'existe pas
}

//    private void openDevisCreatePanel() {
//        Container parent = getParent();
//        while (parent != null && !(parent instanceof Dashboard)) {
//            parent = parent.getParent();  // Parcourir les parents jusqu'à trouver Dashboard
//        }
//        if (parent != null) {
//            Dashboard dashboard = (Dashboard) parent;
//            dashboard.showDevisCreatePanel();
//
//        }
//    }
    
    

public void openPaymentFactureForm(int row) {
    int modelRow = commandeTable.convertRowIndexToModel(row);

    // Retrieve the corresponding 'ModelDevis' from the table model
    ModelCommande commandeToConvert = tableModel.getCommandeAt(modelRow);

    // Ensure that 'devis' is not null
    if (commandeToConvert != null) {
        // Create the 'PaymentAcompteForm' dialog and display it as a modal
        PaymentFactureForm dialog = new PaymentFactureForm(CommandePanel.this, commandeToConvert);
        // style modal border
        ModalDialog.getDefaultOption()
                .getBorderOption()
                .setBorderWidth(0.5f);
        ModalDialog.showModal(this,
            new SimpleModalBorder(dialog, " Paiement Facture",
            SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
                // Reload data after the modal closes
                loadData();
            })
        );
    } else {
        // Handle the case where 'devis' is null
        JOptionPane.showMessageDialog(this, "Erreur : Le devis sélectionné est invalide.");
    }
}
    private void editDevis() {
        // Implémentez ici la logique pour éditer un devis
    }

    private void  deleteSelectedCommande(int row) {

        ModelCommande commande = tableModel.getCommandeAt(row);

        Object[] options = {"Oui", "Annuler"};
        int response = JOptionPane.showOptionDialog(this,
                "Êtes-vous sûr de vouloir supprimer ce commande ?",
                "Confirmer la suppression",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[0]);

        if (response == JOptionPane.YES_OPTION) {
            String deleteResult = DatabaseUtils.deleteCommande(commande.getCommandeId());

            if (deleteResult.equals("SUCCESS")) {
                JOptionPane.showMessageDialog(this, "Commande supprimé avec succès.");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du commande.");
            }
        }

    }

  

    private void loadData() {
         commandes = (ArrayList<ModelCommande>) DatabaseUtils.getAllCommande(); // Récupérer les devis depuis la base de données
         tableModel.setCommandeList( commandes); // Définir la liste de devis pour le modèle de table

        // Appliquer le renderer à la colonne ID
        for (int i : new int[]{4,6,7,8}) {
             commandeTable.getColumnModel().getColumn(i).setCellRenderer(new RightAlignTableCellRenderer());
        }
        commandeTable.getColumnModel().getColumn(9).setCellRenderer(new StatusCellRenderer());

          for (int i : new int[]{0, 1, 3,5}) {
          commandeTable.getColumnModel().getColumn(i).setCellRenderer(new CenterTableCellRenderer());
        }
    }

 
    private void configureActionColumn(JTable table, CommandeTableModel tableModel) {
        table.getColumnModel().getColumn(10).setMinWidth(200); // Ajustez selon vos besoins
        table.getColumnModel().getColumn(10).setMaxWidth(150);
        TableColumn actionColumn = table.getColumnModel().getColumn(tableModel.getColumnCount() - 1);
        actionColumn.setCellRenderer(new ActionCellRenderer());
        actionColumn.setCellEditor(new ActionCellEditor(new ActionTableEvent() {

            @Override
            public void onEdit(int row) {
                //openEditClientDialog(row); // Appeler la méthode pour ouvrir le dialogue d'édition
            }

            @Override
            public void onDelete(int row) {

                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                deleteSelectedCommande(row);
               // Optionnel : Mettre à jour l'affichage après la suppression
        }

    }) ); // Passer la table à ActionCellEditor
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
