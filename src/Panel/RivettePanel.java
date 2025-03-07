/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Panel;

/**
 *
 * @author brici_6ul2f65
 */


import Components.ActionCellEditor;
import Components.ActionCellRenderer;
import Components.ActionTableEvent;
import Components.CenterTableCellRenderer;
import Components.CustomSearchField;
import Components.RightAlignTableCellRenderer;
import Components.RollOverTable;
import Components.RoundedButton;
import Components.RoundedComboBox;
import Components.StatusCellRenderer;
import ConnexionDB.DatabaseUtils;
import Form.AddRivetteForm;
import Form.AddRivetteForm;
import TableModel.RivetteTableModel;
import Models.ModelRivette;
import Models.ModelRivette;
import Models.ModelTypeVitre;
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
public class RivettePanel extends javax.swing.JPanel {

    /**
     * Creates new form RivettePanel
     */
private JPanel tablePanel;
    private JPanel titlePanel;
    private JLabel titleLabel;
    private ArrayList<ModelRivette> Rivette;
    private RivetteTableModel RivetteTableModel;
   
    private TableRowSorter<RivetteTableModel> sorter;
    private ScrollPaneWin11 scroll;
    private JPanel leftPanel;
    private RoundedButton addButton;
    private CustomSearchField searchField;
    private RollOverTable RivetteTable;
    private RoundedComboBox<String> searchChoiceCombo;

    /**
     * Creates new form NewJPanel1
     * @throws java.lang.Exception
     */
    public RivettePanel() throws Exception {
     initComponents();
     init();
     initializeListeners();
     loadData();
    tablePanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:1,1,1,1,$TableHeader.bottomSeparatorColor,,10");
    scroll.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:3,0,3,0,$Table.background,10,10");
    scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "hoverTrackColor:null");
    }
   private void init(){
        
    setLayout(new BorderLayout());

    JPanel RivettePanel = new JPanel(new BorderLayout());
    JPanel RivetteTablePanel = new JPanel(new BorderLayout(0,5));
    RivetteTablePanel.setBorder(new EmptyBorder(15, 10, 15, 10));
    tablePanel = new JPanel(new BorderLayout());
    
    titlePanel = new JPanel(new BorderLayout());
   
    
    tablePanel = new JPanel(new BorderLayout());
    titlePanel = new JPanel(new BorderLayout());
    titlePanel.setPreferredSize(new Dimension(20, 35));
    titlePanel.setPreferredSize(new Dimension(20, 35));
    titleLabel = new JLabel("Gestion de Sctock Rivette");
    // Configurez le style du label si nécessaire
    titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
    titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    titlePanel.add( titleLabel, BorderLayout.WEST);
    
     Rivette = new ArrayList<>();
RivetteTableModel = new RivetteTableModel();

RivetteTable = new RollOverTable(RivetteTableModel);
// Utiliser le CustomTableCellRenderer

RivetteTable.setRowSorter(sorter);
RivetteTable.setFont(new Font("Dialog", Font.BOLD, 13));
RivetteTable.setRowHeight(30);
RivetteTable.setSelectionBackground(new Color(75, 110, 175));
configureActionColumnRivette(RivetteTable, RivetteTableModel);
// Configurer l'apparence du header du tableau
JTableHeader header = RivetteTable.getTableHeader();
((DefaultTableCellRenderer) header.getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
 header.setBackground(new Color(26, 26, 26));
header.setForeground(Color.WHITE);
header.setFont(new Font("Dialog", Font.BOLD, 12));
header.setReorderingAllowed(false);

RivetteTable.setFillsViewportHeight(true);
RivetteTable.setShowGrid(true);
RivetteTable.setGridColor(Color.GRAY);
RivetteTable.setShowHorizontalLines(true);
RivetteTable.setShowVerticalLines(true);
  //  tablePanelContenaire.add( titlePanel, BorderLayout.NORTH);
    scroll = new ScrollPaneWin11();
    scroll.setViewportView(RivetteTable);
    //tablePanelContenaire.add( titlePanel, BorderLayout.NORTH);
    tablePanel.add(scroll, BorderLayout.CENTER);
    
  
    RivetteTablePanel.add(tablePanel, BorderLayout.CENTER);
     RivetteTablePanel.add(titlePanel, BorderLayout.NORTH);
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
    searchField.setPreferredSize(new Dimension(295, 40)); // Ajustez les valeurs pour la largeur (300) et la hauteur (40)
  String[] columns = {"Choisir filtre","N° Rivette", "Modele Rivette", "Type","Prix Unitaire","Quantite","Fournisseur","Etat du stock"};
    searchChoiceCombo = new RoundedComboBox<>(columns);
    searchChoiceCombo.setPreferredSize(new Dimension(searchChoiceCombo.getPreferredSize().width, 40));
    
   leftPanel.add(addButton);
   leftPanel.add(searchField);
   leftPanel.add(searchChoiceCombo);
    actionPanel.add(leftPanel, BorderLayout.CENTER);
    
    RivettePanel.add(RivetteTablePanel, BorderLayout.CENTER); // Add table panel in center
    RivettePanel.add(actionPanel, BorderLayout.NORTH); // Add button panel above the table
    add( RivettePanel, BorderLayout.CENTER);   
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

    private void configureActionColumnRivette(JTable table, RivetteTableModel tableModel) {
        table.getColumnModel().getColumn(7).setMinWidth(200); // Ajustez selon vos besoins
        table.getColumnModel().getColumn(7).setMaxWidth(150);
        table.getColumnModel().getColumn(5).setMinWidth(140);
       
        TableColumn actionColumn = table.getColumnModel().getColumn(tableModel.getColumnCount() - 1);
        actionColumn.setCellRenderer(new ActionCellRenderer());
        actionColumn.setCellEditor(new ActionCellEditor(new ActionTableEvent() {


            @Override
            public void onEdit(int row) {
                try {
                    openEditRivetteForm(row); // Appeler la méthode pour ouvrir le dialogue d'édition
                } catch (SQLException ex) {
                    Logger.getLogger(RivettePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void onDelete(int row) {

                if (table.isEditing()) {
                    table.getCellEditor().stopCellEditing();
                }
                try {
                    deleteSelectedRivette(row);
                    // Optionnel : Mettre à jour l'affichage ou faire d'autres actions après la suppression
                } catch (Exception ex) {
                    Logger.getLogger(RivettePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

        }));
    }
          private void initializeListeners() {

      addButton.addActionListener(e -> {
          try {
              openAddRivetteForm();
          } catch (SQLException ex) {
              Logger.getLogger(RivettePanel.class.getName()).log(Level.SEVERE, null, ex);
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
        RivetteTable.setRowSorter(null);
        return;
    }

   sorter = new TableRowSorter<>(RivetteTableModel);

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
        RivetteTable.setRowSorter(sorter);
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
    RivetteTable.setRowSorter(sorter);
}

        });
    }
     public void openAddRivetteForm() throws SQLException {
         ModalDialog.showModal(this, new SimpleModalBorder(new AddRivetteForm(RivettePanel.this), "Ajouter Rivette", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
             try {
                 loadData();
             } catch (Exception ex) {
                 Logger.getLogger(RivettePanel.class.getName()).log(Level.SEVERE, null, ex);
             }
            }));
          
    }
        private void openEditRivetteForm(int row) throws SQLException {
              int modelRow = RivetteTable.convertRowIndexToModel(row);

        // Récupérer le client correspondant dans le modèle
        ModelRivette Rivette = RivetteTableModel.getRivetteAt(modelRow);

        // Ouvrir la boîte de dialogue d'édition avec les informations du client
        AddRivetteForm dialog = new AddRivetteForm(RivettePanel.this, Rivette);
       
          ModalDialog.showModal(this, new SimpleModalBorder(dialog, "Modifier Rivette", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
                  try {
                      loadData();
                  } catch (Exception ex) {
                      Logger.getLogger(SerrurePanel.class.getName()).log(Level.SEVERE, null, ex);
                  }
              RivetteTable.setRowSelectionInterval(row, row);
              RivetteTable.scrollRectToVisible(RivetteTable.getCellRect(row, 0, true)); 
            }));
            }
        private void deleteSelectedRivette(int row) throws Exception {
    ModelRivette Rivette = RivetteTableModel.getRivetteAt(row); // Remplacez par la méthode appropriée pour obtenir le Serrure à partir de la ligne

    // Personnalisation des boutons de JOptionPane
    Object[] options = {"Oui", "Annuler"};
    int response = JOptionPane.showOptionDialog(this,
            "Voulez-vous vraiment supprimer cette serrure ?",
            "Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE,
            null,
            options,
            options[0]);

    if (response == JOptionPane.YES_OPTION) {
  
            DatabaseUtils.deleteRivette(Rivette.getRivetteId()); // Appel à la méthode de suppression du Serrure

            loadData(); // Recharger les données depuis la base de données
        
    }
}
private void loadData() throws Exception  {
        Rivette = (ArrayList<ModelRivette>) DatabaseUtils.getAllRivettes(); // Récupérer les devis depuis la base de données
    RivetteTableModel.setRivetteList(Rivette); // Définir la liste de devis pour le modèle de table

        // Appliquer le renderer à la colonne ID
         RivetteTable.getColumnModel().getColumn(6).setCellRenderer(new StatusCellRenderer());
        RivetteTable.getColumnModel().getColumn(3).setCellRenderer(new RightAlignTableCellRenderer());
         for (int i : new int[]{0,4}) {
            RivetteTable.getColumnModel().getColumn(i).setCellRenderer(new CenterTableCellRenderer());
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
