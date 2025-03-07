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

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import Components.CustomSearchField;
import Components.RightAlignTableCellRenderer;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import Components.RollOverTable;
import Components.RoundedButton;
import Components.RoundedComboBox;
import Components.RoundedPanel;
import Components.StatusCellRenderer;
import ConnexionDB.DatabaseUtils;
import TableModel.FactureTableModel;
import Models.ModelFacture;
import Models.ModelStatut.ModelStatutFacture;
import Models.PrinterReport;
import static Panel.CommandePanel.setComboBoxRenderer;
import Scroll.ScrollPaneWin11;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Pattern;
import static javax.swing.SwingConstants.CENTER;
import static javax.swing.SwingConstants.RIGHT;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

public class FacturePanel extends RoundedPanel {

    private TableRowSorter<FactureTableModel> sorter;
    public static FactureTableModel tableModel;

    private RoundedButton printButton;
    private CustomSearchField searchField;
    private JTable factureTable;
    private ArrayList<ModelFacture> factures;
    private JPanel tablePanelContenaire;
    private JPanel tablePanel;
    private JPanel titlePanel;
    private JLabel titleLabel;
    private ScrollPaneWin11 scroll;
    private RoundedComboBox<String> searchChoiceCombo;
    private RoundedButton viewButton;

    private JComboBox<String> statutCombo;
    private RoundedButton UpdateSatut;
 


    public FacturePanel() {
        super(20);
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
        factures = new ArrayList<>();
        tableModel = new FactureTableModel();
        factureTable = new JTable(tableModel);
        factureTable = new RollOverTable(tableModel);

        factureTable.setFont(new Font("Dialog", Font.BOLD, 13));
        factureTable.setRowHeight(30);
        factureTable.setSelectionBackground(new Color(75, 110, 175));
        configureActionColumn(factureTable, tableModel);
        // Configurer l'apparence du header du tableau
        JTableHeader header = factureTable.getTableHeader();
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        header.setBackground(new Color(26, 26, 26));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Dialog", Font.BOLD, 12));
        header.setReorderingAllowed(false);

        factureTable.setFillsViewportHeight(true);
        factureTable.setShowGrid(true);
        factureTable.setGridColor(Color.GRAY);
        factureTable.setShowHorizontalLines(true);
        factureTable.setShowVerticalLines(true);

        Font buttonFont = new Font("Dialog", Font.BOLD, 14);
        Dimension buttonSize = new Dimension(150, 40);
viewButton = new RoundedButton("Consulter", new Color(77, 182, 172), new Color(102, 187, 178)); // Couleur différente pour "Consulter"
viewButton.setIcon(new FlatSVGIcon("Icon/view-files-svgrepo-com (3).svg", 24, 24));

printButton = new RoundedButton("Imprimer", new Color(96, 125, 139), new Color(116, 145, 159)); // Couleur originale pour "Imprimer"
printButton.setIcon(new ImageIcon(getClass().getResource("/Icon/print.png")));
 UpdateSatut = new RoundedButton("Modifier Statut", new Color(0, 188, 212), new Color(20, 208, 232));
        UpdateSatut.setIcon(new ImageIcon(getClass().getResource("/Icon/update.png")));
      
//        backButton = new RoundedButton("Retour", initialColor, new Color(0, 0, 0));

        JButton[] buttons = {printButton,viewButton,UpdateSatut};

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
        String[] columns = { "Choisir filtre", "N° Facture","N° Commande", "Client", "Date de Facture", "Total HT","% TVA", "Total TTC", "Statut"};
        // Créer un JComboBox avec des coins arrondis
           statutCombo = new JComboBox<>();
          populateComboStatutFacture(statutCombo);
        searchChoiceCombo = new RoundedComboBox<>(columns); // 20 = rayon des coins
         searchChoiceCombo.setPreferredSize(new Dimension(searchChoiceCombo.getPreferredSize().width, 40)); 
           statutCombo.setPreferredSize(new Dimension(searchChoiceCombo.getPreferredSize().width, 40)); 

        setLayout(new BorderLayout());
    }
    public void populateComboStatutFacture(JComboBox<String> statutCombo) {
         statutCombo.removeAllItems(); // Vider les éléments existants
       statutCombo.addItem(""); // Ajouter un élément vide
    // Appeler la méthode pour récupérer les statuts depuis la base
    List<ModelStatutFacture> statutList = DatabaseUtils.getAllStatutFacture();

  

        // Ajouter les statuts au ComboBox
        for (ModelStatutFacture statut : statutList) {
            statutCombo.addItem(statut.getStatut());
              setComboBoxRenderer( statutCombo);
        }
       
    
   
}
public int getStatutIdByName(String statutName) {
    List<ModelStatutFacture> statutList = DatabaseUtils.getAllStatutFacture();
    for (ModelStatutFacture statut : statutList) {
        if (statut.getStatut().equalsIgnoreCase(statutName)) {
            return statut.getStatutId();
        }
    }
    return -1; // Retourne -1 si le statut n'existe pas
}
//  public static void setComboBoxRenderer(JComboBox<String> comboBox) {
//        comboBox.setPreferredSize(new Dimension(200, 34));  // Taille légèrement augmentée pour un look moderne
//
//
//        comboBox.setRenderer(new DefaultListCellRenderer() {
//            @Override
//            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
//                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
//
//                // Utilisation d'une police moderne
//              putClientProperty(FlatClientProperties.STYLE, "" +
//                "font:bold +2;");
//            //  setText("Sélectionnez Statut");
//               // setBorder(new EmptyBorder(8, 10, 8, 10));
//                // Gestion du texte par défaut si aucune option n'est sélectionnée
//                if (value == null || value.toString().isEmpty()) {
//
//                    setText("Sélectionnez Statut");
//                   setForeground(Color.GRAY);  // Texte grisé pour le placeholder
//                } else {
//                  // setForeground(Color.BLACK);  // Couleur normale pour les options sélectionnées
//                  // setText("Sélectionnez Statut");
//                }
//                if (isSelected || comboBox.getSelectedItem() == value) {
//                    putClientProperty(FlatClientProperties.STYLE, "" +
//                "font:bold +2;");
//                    //setBorder(new EmptyBorder(8, 10, 8, 10));
//                    //setBackground(Color.LIGHT_GRAY);  // Couleur de fond lorsque sélectionné
//                    
//
//                } else {
//                  putClientProperty(FlatClientProperties.STYLE, "" +
//                "font:bold +2;");  // Conserver la police par défaut pour les autres éléments
//                  //  setBackground(Color.WHITE);  // Couleur de fond par défaut
//                }
//
//                return this;
//            }
//        });
//
//
//    }
    private void layoutComponents() {
        tablePanelContenaire = new JPanel(new BorderLayout(0, 5));
        tablePanelContenaire.setBorder(new EmptyBorder(15, 15, 15, 15));
        tablePanel = new JPanel(new BorderLayout());
        titlePanel = new JPanel(new BorderLayout());
        titlePanel.setPreferredSize(new Dimension(20, 35));
        titleLabel = new JLabel("Gestion des Factures");
        // Configurez le style du label si nécessaire
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titlePanel.add(titleLabel, BorderLayout.WEST);
        scroll = new ScrollPaneWin11();
        scroll.setViewportView(factureTable);
        tablePanelContenaire.add(titlePanel, BorderLayout.NORTH);
        tablePanel.add(scroll, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new BorderLayout());
        actionPanel.setBorder(new EmptyBorder(15, 5, 0, 0));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 1));
         JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 1));
        leftPanel.add(viewButton);
        leftPanel.add(printButton);
        leftPanel.add(searchField);
        leftPanel.add(searchChoiceCombo);
          rightPanel.add(statutCombo);
            rightPanel.add(UpdateSatut);
        actionPanel.add(leftPanel, BorderLayout.WEST);
          actionPanel.add(rightPanel, BorderLayout.EAST);
        tablePanelContenaire.add(tablePanel, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.NORTH);
        add(tablePanelContenaire, BorderLayout.CENTER);
        titlePanel.setFocusable(true);
        tablePanel.setFocusable(true);
        actionPanel.setFocusable(true);
        titlePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                titlePanel.requestFocusInWindow();
            }
        });
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
    // Récupérer et normaliser le texte dans le champ de recherche, en ignorant les accents et en passant en minuscules
    String text = normalize(searchField.getText().trim());

    int selectedOption = searchChoiceCombo.getSelectedIndex();

    // Réinitialiser le filtre si le texte de recherche est vide ou "Rechercher..."
    if (text.isEmpty() || text.equals("rechercher")) {
        factureTable.setRowSorter(null);
        return;
    }

    // Créer un TableRowSorter avec le modèle de la table
    sorter = new TableRowSorter<>(tableModel);

    // Définir le filtre en fonction de l'option sélectionnée
    RowFilter<Object, Object> filter = new RowFilter<Object, Object>() {
        @Override
        public boolean include(RowFilter.Entry<?, ?> entry) {
            if (selectedOption == 0) {
                // Vérifier toutes les colonnes si "Toutes colonnes" est sélectionné
                for (int i = 0; i < entry.getValueCount(); i++) {
                    if (matches(entry.getValue(i), text)) {
                        return true;
                    }
                }
                return false;
            }

            // Filtrer par colonne spécifique
            return matches(entry.getValue(selectedOption - 1), text);
        }
    };

    // Appliquer le filtre à la table
    sorter.setRowFilter(filter);
    factureTable.setRowSorter(sorter);
}

// Méthode utilitaire pour normaliser les accents et passer en minuscules
private String normalize(String input) {
    String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
    return Pattern.compile("\\p{InCombiningDiacriticalMarks}+").matcher(normalized).replaceAll("").toLowerCase();
}

// Méthode utilitaire pour vérifier si une valeur correspond au texte recherché, en ignorant la casse et les accents
private boolean matches(Object value, String searchText) {
    if (value instanceof CellStatus ) {
        CellStatus cellStatus  = (CellStatus) value;
        return cellStatus.getStatut() != null && normalize(cellStatus.getStatut()).contains(searchText);
    }
    return value != null && normalize(value.toString()).contains(searchText);
}

        });
        
        
         printButton.addActionListener(e -> {
          int selectedRowIndex = factureTable.getSelectedRow();
    if (selectedRowIndex != -1&&!tableModel.isRowEmpty(selectedRowIndex)) {
        int factureId = tableModel.getFactureAt(selectedRowIndex).getFactureId();
      
           SetupAndPrintFacturePDF(factureId);  // Ouvre la boîte de dialogue personnalisée
        
    } else {
        JOptionPane.showMessageDialog(null, "Veuillez sélectionner un facture.");
    }
                 });
                 viewButton.addActionListener(e -> {
          int selectedRowIndex = factureTable.getSelectedRow();
    if (selectedRowIndex != -1&&!tableModel.isRowEmpty(selectedRowIndex)) {
        int factureId = tableModel.getFactureAt(selectedRowIndex).getFactureId();
      
         viewfacture(factureId);  // Ouvre la boîte de dialogue personnalisée
        
    } else {
        JOptionPane.showMessageDialog(null, "Veuillez sélectionner un facture.");
    }
      });
                 
       UpdateSatut.addActionListener(e -> {
    int selectedRow = factureTable.getSelectedRow();

    // Vérifier qu'une ligne est sélectionnée
    if (selectedRow == -1) {
        JOptionPane.showMessageDialog(null, "Veuillez sélectionner une facture dans le tableau.",
                                      "Aucune sélection", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Récupérer la facture depuis le modèle
    tableModel = (FactureTableModel) factureTable.getModel();
    ModelFacture selectedFacture = tableModel.getFactureAt(selectedRow);

    // Vérifier que le ComboBox a un statut sélectionné
    if (statutCombo.getSelectedIndex() == 0) {
        JOptionPane.showMessageDialog(null, "Veuillez choisir le statut d'abord.",
                                      "Erreur", JOptionPane.WARNING_MESSAGE);
        return;
    }

    // Récupérer le statut actuel et le statut sélectionné
    String currentStatut = selectedFacture.getStatutFacture().getStatut();
    String selectedStatut = (String) statutCombo.getSelectedItem();

    // Logique métier pour les statuts factures
    if ("Annulée".equalsIgnoreCase(currentStatut)) {
        JOptionPane.showMessageDialog(null, "La facture est annulée. Aucun changement de statut n'est possible.",
                                      "Action non autorisée", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if ("Payée".equalsIgnoreCase(currentStatut)) {
        if ("Impayée".equalsIgnoreCase(selectedStatut) || "Payée partiellement".equalsIgnoreCase(selectedStatut)) {
            JOptionPane.showMessageDialog(null, "Impossible de revenir à '" + selectedStatut +
                                                  "' car la facture est déjà payée.",
                                          "Action non autorisée", JOptionPane.ERROR_MESSAGE);
            return;
        }
    }

    // Vérification des transitions invalides
    if ("Non payée".equalsIgnoreCase(currentStatut) && "Annulée".equalsIgnoreCase(selectedStatut)) {
        JOptionPane.showMessageDialog(null, "Une facture non payée ne peut pas être annulée directement.",
                                      "Action non autorisée", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Mise à jour autorisée du statut
    int newStatutId = getStatutIdByName(selectedStatut);
    try {
        DatabaseUtils.updateStatutFacture(selectedFacture.getFactureId(), newStatutId);
        loadData(); // Recharger les données dans la table
        JOptionPane.showMessageDialog(null, "Statut mis à jour : " + selectedStatut,
                                      "Succès", JOptionPane.INFORMATION_MESSAGE);
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, "Erreur lors de la mise à jour du statut.",
                                      "Erreur", JOptionPane.ERROR_MESSAGE);
    }
});


    }

    public void viewfacture(int factureId) {
PrinterReport.generateFactureReport(factureId,"pdf", null, null);
   
}
      
   
   public void SetupAndPrintFacturePDF(int factureId) {
    try {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = printerJob.defaultPage();
        
        PageFormat userPageFormat = printerJob.pageDialog(pageFormat);
        
        if (userPageFormat != pageFormat) {
            File file = showFileChooser(factureId);
            if (file != null) {
                try (FileOutputStream outputStream = new FileOutputStream(file)) {
                  PrinterReport.generateFactureReport(factureId,"pdf", outputStream, pageFormat);
                   
                
                } catch (IOException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Erreur lors de la génération du PDF : " + e.getMessage());
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Configuration de page annulée.");
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Erreur lors de la génération du PDF : " + e.getMessage());
    }
}
       private void  deleteSelectedFacture(int row) {

        ModelFacture facture = tableModel.getFactureAt(row);

        Object[] options = {"Oui", "Annuler"};
        int response = JOptionPane.showOptionDialog(this,
                "Êtes-vous sûr de vouloir supprimer ce Facture ?",
                "Confirmer la suppression",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[0]);

        if (response == JOptionPane.YES_OPTION) {
            String deleteResult = DatabaseUtils.deleteFacture(facture.getFactureId());

            if (deleteResult.equals("SUCCESS")) {
                JOptionPane.showMessageDialog(this, "Facture supprimé avec succès.");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du Facture.");
            }
        }

    }       
private File showFileChooser(int factureId) {
    JFileChooser ch = new JFileChooser();
    ch.setDialogTitle("Enregistrer le Devis");
    ch.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichiers PDF", "pdf"));
    
    // Set default filename
    String defaultFileName = "Facture N°" + factureId + ".pdf";
    ch.setSelectedFile(new File(defaultFileName));
    
    int opt = ch.showSaveDialog(null);
    if (opt == JFileChooser.APPROVE_OPTION) {
        File selectedFile = ch.getSelectedFile();
        
        // Ensure the filename ends with .pdf
        String filename = selectedFile.getName();
        if (!filename.endsWith(".pdf")) {
            selectedFile = new File(selectedFile.getParent(), filename + ".pdf");
        }
        
        return selectedFile;
    }
    return null;
}
    private void editFacture() {
        // Implémentez ici la logique pour éditer un devis
    }



    private void loadData() {
        factures = (ArrayList<ModelFacture>) DatabaseUtils.getAllFacture(); // Récupérer les devis depuis la base de données
        tableModel.setfactures(factures); // Définir la liste de devis pour le modèle de table

      // Appliquer le renderer à la colonne ID
        for (int i : new int[]{4,6}) {
             factureTable.getColumnModel().getColumn(i).setCellRenderer(new RightAlignTableCellRenderer());
        }
       factureTable.getColumnModel().getColumn(7).setCellRenderer(new StatusCellRenderer());

          for (int i : new int[]{0, 1, 3,5}) {
          factureTable.getColumnModel().getColumn(i).setCellRenderer(new CenterTableCellRenderer());
        }
    }


    private void configureActionColumn(JTable table, FactureTableModel tableModel) {
        table.getColumnModel().getColumn(8).setMinWidth(200); // Ajustez selon vos besoins
        table.getColumnModel().getColumn(8).setMaxWidth(150);
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
                deleteSelectedFacture(row);
                
        }

     }));
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
