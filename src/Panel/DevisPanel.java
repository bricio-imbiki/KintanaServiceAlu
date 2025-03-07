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
import Components.CustomPageSetupDialog;
import Components.CustomSearchField;
import Components.RightAlignTableCellRenderer;
import Components.RollOverTable;
import Components.RoundedButton;
import Components.RoundedComboBox;
import Components.RoundedPanel;
import Components.StatusCellRenderer;
import Models.ModelDevis;
import TableModel.DevisTableModel;
import ConnexionDB.DatabaseUtils;
import Form.PaymentAcompteForm;
import Models.ModelCommande;
import Models.PrinterReport;
import Scroll.ScrollPaneWin11;
import Views.Dashboard;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import javax.swing.table.TableRowSorter;
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
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import static javax.swing.SwingConstants.CENTER;
import static javax.swing.SwingConstants.RIGHT;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import raven.modal.ModalDialog;
import raven.modal.component.SimpleModalBorder;

public final class DevisPanel extends RoundedPanel {

    private DevisCreatePanel devisCreatePanel;
    private ArrayList<ModelDevis> devis;
    private DevisTableModel tableModel;
    private JTable devisTable;
    private RoundedButton createButton;
    private RoundedButton convertButton;
  
//    private RoundedButton backButton;
    private JTextField searchField;
    private TableRowSorter<DevisTableModel> sorter;
    private RoundedComboBox<String> searchChoiceCombo;
    private ImageIcon searchIcon;
    private ImageIcon clearIcon;
    private JPanel tablePanel;
    private JPanel titlePanel;
    private JLabel titleLabel;
    private ScrollPaneWin11 scroll;
    private JPanel tablePanelContenaire;
    private RoundedButton ExporteToExcelButton;
    private RoundedButton printButton;


    public DevisPanel() {
        super(20);
        initComponents();
        initializeComponents();
        layoutComponents();
        initializeListeners();
        loadDataInBackground();
          tablePanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:1,1,1,1,$TableHeader.bottomSeparatorColor,,10");

        scroll.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:3,0,3,0,$Table.background,10,10");
        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "hoverTrackColor:null");
    }

    private void initializeComponents() {
        devis = new ArrayList<>();
        tableModel = new DevisTableModel();
        // devisTable = new JTable(tableModel);
        //sorter = new TableRowSorter<>(tableModel);
        devisTable = new RollOverTable(tableModel);
        // Utiliser le CustomTableCellRenderer
      
        devisTable.setRowSorter(sorter);
        devisTable.setFont(new Font("Dialog", Font.BOLD, 13));
        devisTable.setRowHeight(30);
        devisTable.setSelectionBackground(new Color(75, 110, 175));
        configureActionColumn(devisTable, tableModel);
        // Configurer l'apparence du header du tableau
        JTableHeader header = devisTable.getTableHeader();
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
         header.setBackground(new Color(26, 26, 26));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Dialog", Font.BOLD, 12));
        header.setReorderingAllowed(false);

        devisTable.setFillsViewportHeight(true);
        devisTable.setShowGrid(true);
        devisTable.setGridColor(Color.GRAY);
        devisTable.setShowHorizontalLines(true);
        devisTable.setShowVerticalLines(true);

        Font buttonFont = new Font("Dialog", Font.BOLD, 14);
        Dimension buttonSize = new Dimension(150, 40);

        createButton = new RoundedButton("Créer un devis", new Color(46, 204, 113), new Color(60, 224, 133));

        createButton.setIcon(new ImageIcon(getClass().getResource("/Icon/icons8-add-32.png")));
//        editButton = new RoundedButton("Modifier", new Color(91, 192, 222), new Color(150, 150, 150));
        // editButton = new RoundedButton("Modifier", new Color(91, 192, 222), new Color(71, 172, 202));

//      deleteButton = new RoundedButton("Supprimer", new Color(217, 83, 79), hoverColor);
        convertButton = new RoundedButton("Convertir", new Color(240, 173, 78), new Color(255, 193, 94));
        convertButton.setIcon(new ImageIcon(getClass().getResource("/Icon/convert.png")));
    
        printButton = new RoundedButton("Imprimer", new Color(96, 125, 139), new Color(116, 145, 159));
        printButton.setIcon(new ImageIcon(getClass().getResource("/Icon/print.png")));
        ExporteToExcelButton = new RoundedButton("Exporter Excel", new Color(33, 115, 70), new Color(14, 92, 47));
        ExporteToExcelButton.setIcon(new FlatSVGIcon("Icon/export-svgrepo-com (3).svg", 24, 24));
         JButton[] buttons = {createButton, convertButton, printButton,ExporteToExcelButton};

        for (JButton button : buttons) {
            button.setFont(buttonFont);
            button.setPreferredSize(buttonSize);
            button.setForeground(Color.WHITE); // Couleur du texte
        }
        // Charger l'icône de recherche
        searchIcon = new ImageIcon(getClass().getResource("/Icon/search.png")); // Remplacez par le chemin de votre icône
        clearIcon = new ImageIcon(getClass().getResource("/Icon/clear.png"));
        searchField = new CustomSearchField("Rechercher...", searchIcon, clearIcon);

// Définir la taille préférée du searchField
        searchField.setPreferredSize(new Dimension(295, 40)); // Ajustez les valeurs pour la largeur (300) et la hauteur (40)

        setLayout(new BorderLayout());
    }

    private void layoutComponents() {
     tablePanelContenaire = new JPanel(new BorderLayout(0,5));
    tablePanelContenaire.setBorder(new EmptyBorder(15, 15, 15, 15));
     tablePanel = new JPanel(new BorderLayout());
    titlePanel = new JPanel(new BorderLayout());
     titlePanel.setPreferredSize(new Dimension(20, 35));
    titleLabel = new JLabel("Gestion des Devis");
    // Configurez le style du label si nécessaire
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    titlePanel.add( titleLabel, BorderLayout.WEST);
  
    
   
    
    scroll = new ScrollPaneWin11();
    scroll.setViewportView(devisTable);
    tablePanelContenaire.add( titlePanel, BorderLayout.NORTH);
    tablePanel.add(scroll, BorderLayout.CENTER);
        // Utilisation du RoundedComboBox
        String[] columns = { "Choisir filtre","N° Devis", "Nom Client", "Date Devis", "Total HT","% Remise","Tot.Remise","% Acompte","Acompte à Payer","% TVA","Tot.TVA", "Total TTC", "Statut"};
        
        // Créer un JComboBox avec des coins arrondis
        searchChoiceCombo = new RoundedComboBox<>(columns); // 20 = rayon des coins
        searchChoiceCombo.setPreferredSize(new Dimension(searchChoiceCombo.getPreferredSize().width, 40));
        JPanel actionPanel = new JPanel(new BorderLayout());
        actionPanel.setBorder(new EmptyBorder(15, 5, 0, 0));

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 1));
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 1));
        leftPanel.add(createButton);
        leftPanel.add(searchField);
        leftPanel.add(searchChoiceCombo);

        rightPanel.add(printButton);
       rightPanel.add(ExporteToExcelButton);
        rightPanel.add(convertButton);
        
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
        createButton.addActionListener(e -> openDevisCreatePanel());
        convertButton.addActionListener(e -> {
                 int selectedRow = devisTable.getSelectedRow();
        if (selectedRow != -1) {
                       String numeroDevis = (String) devisTable.getValueAt(selectedRow, 0);
                    int devisId = Integer.parseInt(numeroDevis) ;// Extrait "0001" et le convertit en entier
                     try {
                         // Vérifier si le devis a déjà été converti en commande
                         if (DatabaseUtils.isDevisAlreadyConverted(devisId)) {
                             JOptionPane.showMessageDialog(this,
                                     "Ce devis a déjà été converti en commande.",
                                     "Erreur",
                                     JOptionPane.ERROR_MESSAGE);
                             return; // Sortir de la méthode si le devis est déjà converti
                         }        } catch (SQLException ex) {
                         Logger.getLogger(DevisPanel.class.getName()).log(Level.SEVERE, null, ex);
                     }
            openPaymentAcompteForm(selectedRow);
        } else {
            JOptionPane.showMessageDialog(this, "Veuillez sélectionner un devis pour effectuer le paiement.");
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
        devisTable.setRowSorter(null);
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
        devisTable.setRowSorter(sorter);
        return;
    }

    RowFilter<Object, Object> customFilter = new RowFilter<Object, Object>() {
        @Override
        public boolean include(RowFilter.Entry<? extends Object, ? extends Object> entry) {
            String searchText = text.toLowerCase();

//            // Correspondance exacte pour la colonne N° Vis
//            if (selectedOption == 1) {
//                Object idValue = entry.getValue(0);
//                return idValue != null && idValue.toString().equalsIgnoreCase(searchText);
//            }

            // Correspondance partielle pour les autres colonnes
            Object cellValue = entry.getValue(selectedOption - 1);
            String cellText = (cellValue != null) ? cellValue.toString().toLowerCase().trim() : "";
            return cellText.contains(searchText);
        }
    };

    sorter.setRowFilter(customFilter);
    devisTable.setRowSorter(sorter);
}

        });
        

     ExporteToExcelButton.addActionListener(e -> {
    int selectedRowIndex = devisTable.getSelectedRow();
    if (selectedRowIndex != -1 && !tableModel.isRowEmpty(selectedRowIndex)) {
        int devisId = tableModel.getDevisAt(selectedRowIndex).getDevisId();
        SetupAndPrintDevisExcel(devisId);
    } else {
        JOptionPane.showMessageDialog(null, "Veuillez sélectionner un devis.");
    }
});
      printButton.addActionListener(e -> {
    int selectedRowIndex = devisTable.getSelectedRow();
    if (selectedRowIndex != -1 && !tableModel.isRowEmpty(selectedRowIndex)) {
        int devisId = tableModel.getDevisAt(selectedRowIndex).getDevisId();
        ModelDevis devis = DatabaseUtils.getDevisById(devisId);
        SetupAndPrintDevis(devis);
    } else {
        JOptionPane.showMessageDialog(null, "Veuillez sélectionner un devis.");
    }
});
    }
    
 
   public void SetupAndPrintDevisExcel(int devisId) {
    try {
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = printerJob.defaultPage();
        
        PageFormat userPageFormat = printerJob.pageDialog(pageFormat);
        
        if (userPageFormat != pageFormat) {
            File file = showFileChooserExcel(devisId);
            if (file != null) {
                try (FileOutputStream outputStream = new FileOutputStream(file)) {
                PrinterReport.generateDevisReport(devisId,"excel", outputStream,userPageFormat);
                    Desktop.getDesktop().open(file);
                
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
   
public void SetupAndPrintDevis( ModelDevis devis) {
    try {
          PrinterJob printerJob = PrinterJob.getPrinterJob();
        PageFormat pageFormat = printerJob.defaultPage();
        
        PageFormat userPageFormat = printerJob.pageDialog(pageFormat);
        // Sélection du fichier où enregistrer le devis
             
        File file = showFileChooser(devis);
        if (file != null) {
            try (FileOutputStream outputStream = new FileOutputStream(file)) {
                // Générer le rapport avec les paramètres de JasperReports
            PrinterReport.generateDevisReport(devis.getDevisId(),"pdf", outputStream,userPageFormat);
              
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Erreur lors de la génération du fichier Excel : " + e.getMessage());
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null, "Erreur lors de la génération du fichier Excel : " + e.getMessage());
    }
}
      
              
private File showFileChooserExcel(int devisId) {
    JFileChooser ch = new JFileChooser();
    ch.setDialogTitle("Enregistrer le Devis");
    ch.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichiers xlsx", "xlsx"));

    // Nom de fichier par défaut
    String defaultFileName = "Devis N°" + devisId + ".xlsx";
    ch.setSelectedFile(new File(defaultFileName));

    int opt = ch.showSaveDialog(null);
    if (opt == JFileChooser.APPROVE_OPTION) {
        File selectedFile = ch.getSelectedFile();

        // Vérification de l'existence du fichier
        if (selectedFile.exists()) {
            int response = JOptionPane.showConfirmDialog(null,
                "Le fichier '" + selectedFile.getName() + "' existe déjà. Voulez-vous l'écraser ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
            if (response != JOptionPane.YES_OPTION) {
                return null; // Annule l'opération
            }
        }

        // Ajout de l'extension ".pdf" si nécessaire
        String filename = selectedFile.getName();
        if (!filename.toLowerCase().endsWith(".xlsx")) {
            selectedFile = new File(selectedFile.getParent(), filename + ".xlsx");
        }

        return selectedFile;
    }
    return null;
}

          
              
private File showFileChooser(ModelDevis devis) {
    JFileChooser ch = new JFileChooser();
    ch.setDialogTitle("Enregistrer le Devis");
    ch.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Fichiers PDF", "pdf"));

    // Nom de fichier par défaut
    String defaultFileName = "Devis N°" + devis.getNumeroDevis() + ".pdf";
    ch.setSelectedFile(new File(defaultFileName));

    int opt = ch.showSaveDialog(null);
    if (opt == JFileChooser.APPROVE_OPTION) {
        File selectedFile = ch.getSelectedFile();

        // Vérification de l'existence du fichier
        if (selectedFile.exists()) {
            int response = JOptionPane.showConfirmDialog(null,
                "Le fichier '" + selectedFile.getName() + "' existe déjà. Voulez-vous l'écraser ?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
            if (response != JOptionPane.YES_OPTION) {
                return null; // Annule l'opération
            }
        }

        // Ajout de l'extension ".pdf" si nécessaire
        String filename = selectedFile.getName();
        if (!filename.toLowerCase().endsWith(".pdf")) {
            selectedFile = new File(selectedFile.getParent(), filename + ".pdf");
        }

        return selectedFile;
    }
    return null;
}

//     private void SetupAndPrintDevisWithJasper(int devisId) {
//    try {
//        // Charger le modèle Jasper (compilé en .jasper)
//        InputStream reportStream = getClass().getResourceAsStream("/reports/devis_template.jasper");
//        if (reportStream == null) {
//            JOptionPane.showMessageDialog(null, "Modèle Jasper introuvable.");
//            return;
//        }
//
//        // Récupérer les données du devis depuis la base de données
//        ModelDevis devis = DatabaseUtils.getDevisById(devisId);
//        if (devis == null) {
//            JOptionPane.showMessageDialog(null, "Le devis sélectionné n'existe pas.");
//            return;
//        }
//
//
//        // Générer le rapport
//        JasperPrint jasperPrint = JasperFillManager.fillReport(reportStream, parameters, dataSource);
//
//        // Enregistrer le fichier PDF
//        File file = showFileChooser(devisId);
//        if (file != null) {
//            JasperExportManager.exportReportToPdfFile(jasperPrint, file.getAbsolutePath());
//            Desktop.getDesktop().open(file);
//            JOptionPane.showMessageDialog(null, "Le devis a été généré avec succès.");
//        }
//    } catch (Exception ex) {
//        ex.printStackTrace();
//        JOptionPane.showMessageDialog(null, "Erreur lors de la génération du devis : " + ex.getMessage());
//    }
//}  
//      public void setupAndPrintDevis(int devisId) {
//    try {
//        PrinterJob printerJob = PrinterJob.getPrinterJob();
//        PageFormat pageFormat = printerJob.defaultPage();
//        
//        PageFormat userPageFormat = printerJob.pageDialog(pageFormat);
//        
//        if (userPageFormat != pageFormat) {
//            File file = showFileChooser(devisId);
//            if (file != null) {
//                try (FileOutputStream outputStream = new FileOutputStream(file)) {
//                    PDFDevisPrinter.generateDevisPDF(devisId, userPageFormat, outputStream);
//                    Desktop.getDesktop().open(file);
//                
//                } catch (IOException | DocumentException | SQLException e) {
//                    e.printStackTrace();
//                    JOptionPane.showMessageDialog(null, "Erreur lors de la génération du PDF : " + e.getMessage());
//                }
//            }
//        } else {
//            JOptionPane.showMessageDialog(null, "Configuration de page annulée.");
//        }
//    } catch (Exception e) {
//        e.printStackTrace();
//        JOptionPane.showMessageDialog(null, "Erreur lors de la génération du PDF : " + e.getMessage());
//    }
//}

    
    
    

public void openPaymentAcompteForm(int row) {
    int modelRow = devisTable.convertRowIndexToModel(row);

    // Retrieve the corresponding 'ModelDevis' from the table model
    ModelDevis devisToConvert = tableModel.getDevisAt(modelRow);

    // Ensure that 'devis' is not null
    if (devisToConvert != null) {
        // Create the 'PaymentAcompteForm' dialog and display it as a modal
        PaymentAcompteForm dialog = new PaymentAcompteForm(DevisPanel.this, devisToConvert);
        // style modal border
        ModalDialog.getDefaultOption()
                .getBorderOption()
                .setBorderWidth(0.5f);
        ModalDialog.showModal(this,
            new SimpleModalBorder(dialog, " Paiement Acompte",
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



    private void openDevisCreatePanel() {
        Container parent = getParent();
        while (parent != null && !(parent instanceof Dashboard)) {
            parent = parent.getParent();  // Parcourir les parents jusqu'à trouver Dashboard
        }
        if (parent != null) {
            Dashboard dashboard = (Dashboard) parent;
            dashboard.showDevisCreatePanel("");

        }
    }

    private void deleteDevis(int row) {

        ModelDevis devis = tableModel.getDevisAt(row);

        Object[] options = {"Oui", "Annuler"};
        int response = JOptionPane.showOptionDialog(this,
                "Êtes-vous sûr de vouloir supprimer ce devis ?",
                "Confirmer la suppression",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[0]);

        if (response == JOptionPane.YES_OPTION) {
            String deleteResult = DatabaseUtils.deleteDevis(devis.getDevisId());

            if (deleteResult.equals("SUCCESS")) {
                JOptionPane.showMessageDialog(this, "Devis supprimé avec succès.");
                loadData();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la suppression du devis.");
            }
        }

    }

//   private void convertDevis() throws SQLException {
//    int selectedRow = devisTable.getSelectedRow();
//    if (selectedRow != -1) {
//        int devisIdToConvert = (int) devisTable.getValueAt(selectedRow, 0); // Assuming ID is in the first column
//       // Récupérer l'objet CellStatus de la colonne du statut (ajustez l'index de la colonne)
//        Object cellValue = devisTable.getValueAt(selectedRow, 8); // Assuming status is in the 5th column
//        String statutDevis = "";
//        
//        if (cellValue instanceof CellStatus) {
//            CellStatus cellStatus = (CellStatus) cellValue;
//            statutDevis = cellStatus.getStatut(); // Obtenir le statut du devis
//        }
//
//        // Vérifier si le devis est déjà converti en commande
//        if (statutDevis.equalsIgnoreCase("accepté")) {
//            JOptionPane.showMessageDialog(this, "Ce devis a déjà été converti en commande.");
//            return; // Empêcher la conversion
//        }
//
//
//        // Définir les options de boutons pour la boîte de dialogue
//        Object[] options = {"Oui", "Annuler"};
//        int response = JOptionPane.showOptionDialog(this,
//                "Êtes-vous sûr de vouloir convertir ce devis en commande ?",
//                "Confirmer la conversion",
//                JOptionPane.YES_NO_OPTION,
//                JOptionPane.QUESTION_MESSAGE,
//                null,
//                options,
//                options[1]); // Bouton par défaut (Annuler)
//
//        if (response == JOptionPane.YES_OPTION) {
//        String convertResult = DatabaseUtils.convertDevisToCommande(devisIdToConvert);
//
//            if (convertResult.equals("SUCCESS")) {
//                JOptionPane.showMessageDialog(this, "Devis converti en commande avec succès.");
//                loadData(); // Recharger les données après la conversion si nécessaire
//            } else {
//                JOptionPane.showMessageDialog(this, "Erreur lors de la conversion du devis.");
//            }
//        } else {
//            // L'utilisateur a choisi "Annuler" ou a fermé la boîte de dialogue
//            JOptionPane.showMessageDialog(this, "Conversion annulée.");
//        }
//    } else {
//        JOptionPane.showMessageDialog(this, "Veuillez sélectionner un devis à convertir.");
//    }
//}
//


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
    public void loadData() {
        devis = (ArrayList<ModelDevis>) DatabaseUtils.getAllDevis(); // Récupérer les devis depuis la base de données
        tableModel.setDevisList(devis); // Définir la liste de devis pour le modèle de table

        // Appliquer le renderer à la colonne ID
     
          devisTable.getColumnModel().getColumn(11).setCellRenderer(new StatusCellRenderer());
         for (int i : new int[]{0, 2, 4,5,6,7,8,9,10}){
            devisTable.getColumnModel().getColumn(i).setCellRenderer(new CenterTableCellRenderer());
        }
        for (int i : new int[]{3, 5}){
            devisTable.getColumnModel().getColumn(i).setCellRenderer(new RightAlignTableCellRenderer());
        }

    }


  

private void configureActionColumn(JTable table, DevisTableModel tableModel) {
    table.getColumnModel().getColumn(12).setMinWidth(200); // Ajustez selon vos besoins
    table.getColumnModel().getColumn(12).setMaxWidth(150);
   table.getColumnModel().getColumn(11).setMinWidth(100); // Ajustez selon vos besoins

    TableColumn actionColumn = table.getColumnModel().getColumn(tableModel.getColumnCount() - 1);
    actionColumn.setCellRenderer(new ActionCellRenderer());
    actionColumn.setCellEditor(new ActionCellEditor(new ActionTableEvent() {

        @Override
        public void onEdit(int row) {
            // Vérifier le statut du devis
            ModelDevis devis = tableModel.getDevisAt(row); // Méthode pour récupérer le devis
            if (devis.getStatut().getStatut().equalsIgnoreCase("Accepté")) {
                JOptionPane.showMessageDialog(null, "Ce devis est déjà accepté et ne peut pas être modifié.");
                return; // Ne pas permettre l'édition
            }

            devisCreatePanel = new DevisCreatePanel("");
            openDevisEditPanel(row);

            DevisCreatePanel.updateButton.setVisible(true);
            devisCreatePanel.setEditMode(true);
        }

        @Override
        public void onDelete(int row) {
            // Vérifier le statut du devis
            ModelDevis devis = tableModel.getDevisAt(row); // Méthode pour récupérer le devis
            if (devis.getStatut().getStatut().equalsIgnoreCase("Accepté")) {
                JOptionPane.showMessageDialog(null, "Ce devis est déjà accepté et ne peut pas être supprimé.");
                return; // Ne pas permettre la suppression
            }

            if (table.isEditing()) {
                table.getCellEditor().stopCellEditing();
            }
            deleteDevis(row);
          // Optionnel : Mettre à jour l'affichage après la suppression
        }

    })); // Passer la table à ActionCellEditor
}


    private void openDevisEditPanel(int row) {
        // Récupérer l'ID du devis sélectionné dans la table
        int devisId = tableModel.getDevisAt(row).getDevisId();

        // Récupérer les données du devis depuis la base de données
        ModelDevis devis = DatabaseUtils.getDevisById(devisId);

        // Passer ces données à DevisCreatePanel pour pré-remplir les champs
        Container parent = getParent();
        while (parent != null && !(parent instanceof Dashboard)) {
            parent = parent.getParent();  // Parcourir les parents jusqu'à trouver Dashboard
        }
        if (parent != null) {
            Dashboard dashboard = (Dashboard) parent;
            dashboard.showDevisEditPanel(devis); // Passer le devis à éditer
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
