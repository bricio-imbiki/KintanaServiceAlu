/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */

package Panel;


import Components.ActionCellEditor;
import Components.ActionCellRenderer;
import Components.ActionTableEvent;
import Components.CenterTableCellRenderer;
import Form.AddFournisseurForm;
import Components.CustomSearchField;
import Components.RoundedSpinner;
import Components.RollOverTable;
import Components.RoundedButton;
import Components.RoundedComboBox;
import Components.RoundedPanel;

import TableModel.FournisseurTableModel;
import Models.ModelFournisseur;
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

public class FournisseurPanel extends RoundedPanel {

    private JPopupMenu popupMenu;
    private RoundedButton addButton;
    private JTable fournisseurTable;
    private CustomSearchField searchField;
    private TableRowSorter<FournisseurTableModel> sorter;
    public static FournisseurTableModel tableModel;
    public static ArrayList<ModelFournisseur> fournisseurs;
    private RoundedComboBox<String> searchChoiceCombo;
    private JPanel tablePanelContenaire;
    private JPanel tablePanel;
    private JPanel titlePanel;
    private JLabel titleLabel;
    private ScrollPaneWin11 scroll;

    public FournisseurPanel() {
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
        fournisseurs = new ArrayList<>();
        tableModel = new FournisseurTableModel();
        fournisseurTable = new JTable(tableModel);
        fournisseurTable = new RollOverTable(tableModel);
        fournisseurTable.setSelectionBackground(new Color(75, 110, 175));

        fournisseurTable.setFont(new Font("Dialog", Font.BOLD, 13));
        configureActionColumn(fournisseurTable, tableModel);

        Font buttonFont = new Font("Dialog", Font.BOLD, 14);
        Dimension buttonSize = new Dimension(150, 40);
       addButton = new RoundedButton("Ajouter", new Color(46, 204, 113), new Color(60, 224, 133));
             addButton.setIcon(new ImageIcon(getClass().getResource("/Icon/icons8-add-26 (5).png")));

        JButton[] buttons = {addButton};
        for (JButton button : buttons) {
            button.setFont(buttonFont);
            button.setPreferredSize(buttonSize);
            button.setForeground(Color.WHITE);
        }

        popupMenu = new JPopupMenu();
        JMenuItem editMenuItem = new JMenuItem("Éditer Fournisseur");
        popupMenu.add(editMenuItem);
        editMenuItem.addActionListener(e -> {
            int selectedRow = fournisseurTable.getSelectedRow();
            if (selectedRow != -1) {
                openEditFournisseurForm(selectedRow);
            }
        });

        fournisseurTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    int row = fournisseurTable.rowAtPoint(e.getPoint());
                    if (row != -1 && !tableModel.isRowEmpty(row)) {
                        fournisseurTable.setRowSelectionInterval(row, row);
                        popupMenu.show(e.getComponent(), e.getX(), e.getY());
                    }
                }
            }
        });

        Icon searchIcon = new ImageIcon(getClass().getResource("/Icon/search.png"));
        Icon clearIcon = new ImageIcon(getClass().getResource("/Icon/clear.png"));
        searchField = new CustomSearchField("Rechercher...", searchIcon, clearIcon);
        searchField.setPreferredSize(new Dimension(295, 40));

        fournisseurTable.setRowHeight(30);
        JTableHeader header = fournisseurTable.getTableHeader();
        ((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
        header.setBackground(new Color(26, 26, 26));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Dialog", Font.BOLD, 12));
        header.setReorderingAllowed(false);

        fournisseurTable.setFillsViewportHeight(true);
        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) fournisseurTable.getTableHeader().getDefaultRenderer();
        renderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < fournisseurTable.getColumnCount(); i++) {
            fournisseurTable.getColumnModel().getColumn(i).setHeaderRenderer(renderer);
        }

        fournisseurTable.setShowGrid(true);
        fournisseurTable.setGridColor(Color.GRAY);
        fournisseurTable.setShowHorizontalLines(true);
        fournisseurTable.setShowVerticalLines(true);

        setLayout(new BorderLayout());
    }

    private void layoutComponents() {
        String[] columns = {"Choisir filtre", "ID Fournisseur", "Nom Fournisseur", "Adresse", "Téléphone", "Email"};
        searchChoiceCombo = new RoundedComboBox<>(columns);
        searchChoiceCombo.setPreferredSize(new Dimension(searchChoiceCombo.getPreferredSize().width, 40));
        
        tablePanelContenaire = new JPanel(new BorderLayout(0,5));
    tablePanelContenaire.setBorder(new EmptyBorder(15, 15, 15, 15));
     tablePanel = new JPanel(new BorderLayout());
    titlePanel = new JPanel(new BorderLayout());
 
   titlePanel.setPreferredSize(new Dimension(20, 35));
    titleLabel = new JLabel("Gestion des Fournisseurs");
    // Configurez le style du label si nécessaire
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    titlePanel.add( titleLabel, BorderLayout.WEST);
    scroll = new ScrollPaneWin11();
    scroll.setViewportView(fournisseurTable);
    tablePanelContenaire.add( titlePanel, BorderLayout.NORTH);
    tablePanel.add(scroll, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new BorderLayout());
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 1));
        actionPanel.setBorder(new EmptyBorder(15, 5, 0, 0));
    
        leftPanel.add(addButton);
        leftPanel.add(searchField);
        leftPanel.add(searchChoiceCombo);
        actionPanel.add(leftPanel, BorderLayout.CENTER);

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
        addButton.addActionListener(e -> openAddFournisseurForm());

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { filterTable(); }
            @Override
            public void removeUpdate(DocumentEvent e) { filterTable(); }
            @Override
            public void changedUpdate(DocumentEvent e) { filterTable(); }

           
        });
    }
    private void filterTable() {
    String text = searchField.getText().trim();
    int selectedOption = searchChoiceCombo.getSelectedIndex();

    // Réinitialiser le filtre si le champ de recherche est vide ou contient le texte par défaut
    if (text.isEmpty() || text.equals("Rechercher...")) {
        fournisseurTable.setRowSorter(null);
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
        fournisseurTable.setRowSorter(sorter);
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
   fournisseurTable.setRowSorter(sorter);
}
    public void openAddFournisseurForm() {
    
       
          ModalDialog.showModal(this, new SimpleModalBorder(new AddFournisseurForm(FournisseurPanel.this), "Ajouter Fournissuer", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
              loadData();
            }));
          

       
        
    }
private void openEditFournisseurForm(int row) {
 
    // Fournisseur sélectionné
    ModelFournisseur selectedFournisseur = tableModel.getFournisseurAt(row);
    
    // Ouvrir le formulaire d'édition
    AddFournisseurForm dialog = new AddFournisseurForm(FournisseurPanel.this, selectedFournisseur);
    
    // Afficher le modal pour modifier le fournisseur
    ModalDialog.showModal(this, new SimpleModalBorder(dialog, "Modifier Fournisseur", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
        // Recharger les données après fermeture du formulaire
        loadData();
        
        // Restaurer la sélection de la ligne après rechargement des données
        fournisseurTable.setRowSelectionInterval(row, row);
        fournisseurTable.scrollRectToVisible(fournisseurTable.getCellRect(row, 0, true)); // S'assurer que la ligne reste visible
    }));
}

    private void configureActionColumn(JTable table, FournisseurTableModel tableModel) {
        table.getColumnModel().getColumn(5).setMinWidth(200);
        table.getColumnModel().getColumn(5).setMaxWidth(150);
        TableColumn actionColumn = table.getColumnModel().getColumn(tableModel.getColumnCount() - 1);
        actionColumn.setCellRenderer(new ActionCellRenderer());
        actionColumn.setCellEditor(new ActionCellEditor(new ActionTableEvent() {
            @Override
            public void onEdit(int row) {
                openEditFournisseurForm(row);
            }

            @Override
            public void onDelete(int row) {
              
            }
        }));
    }

    private void loadData() {
         fournisseurs = (ArrayList<ModelFournisseur>) DatabaseUtils.getAllFournisseur(); // Récupérer les clients depuis la base de données
        tableModel.setFournisseurs(fournisseurs);
          fournisseurTable.getColumnModel().getColumn(0).setCellRenderer(new CenterTableCellRenderer());
        tableModel.fireTableDataChanged();
      
         
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
