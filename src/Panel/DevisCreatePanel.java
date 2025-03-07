package Panel;


import Form.EditPorteForm;
import Form.EditFenetreForm;
import Components.ActionCellEditor;
import Components.ActionCellRenderer;
import Components.ActionTableEvent;
import Form.AddClientForm;
import Components.RollOverTable;
import Components.RoundedButton;
import Components.TabbedPaneCustom;
import ConnexionDB.DatabaseUtils;
import Form.EditCloisonForm;
import Form.EditNacoForm;
import Models.LargeurAndHauteurExtractor.LargeurAndHauteur;
import static Models.LargeurAndHauteurExtractor.extractDimensionsFromDesignation;
import Models.ModelChassis;
import Models.ModelClient;
import Models.ModelClips;
import TableModel.DevisCreateTableModel;

import Models.ModelDevis;
import Models.ModelJoint;
import Models.ModelLameVerre;
import Models.ModelLigneDevis;
import Models.ModelOperateur;
import Models.ModelPaumelle;
import Models.ModelPoignee;
import Models.ModelProduit;
import Models.ModelProfileAlu;
import Models.ModelRivette;
import Models.ModelRoulette;
import Models.ModelSerrure;
import Models.ModelSousLigneChassis;
import Models.ModelSousLigneClips;
import Models.ModelSousLigneDevis;
import Models.ModelSousLigneJoint;
import Models.ModelSousLigneLameVerre;
import Models.ModelSousLigneOperateur;
import Models.ModelSousLignePaumelle;
import Models.ModelSousLignePoignee;
import Models.ModelSousLigneRivette;
import Models.ModelSousLigneRoulette;
import Models.ModelSousLigneSerrure;
import Models.ModelSousLigneVis;
import Models.ModelSousLigneVitre;

import Models.ModelStatut.ModelStatutDevis;
import Models.ModelStructure;
import Models.ModelTypeVitre;
import Models.ModelVis;
import Scroll.ScrollPaneWin11;
import static TableModel.FactureTableModel.formatPercentage;
import Views.Dashboard;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.FlatDarkLaf; // Pour le thème sombre
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.sql.Connection;
import java.awt.BorderLayout;
import java.awt.Dimension;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import javax.swing.*;
import java.awt.*;
import java.util.Locale;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import javax.swing.BorderFactory;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.util.Date;

import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.border.EmptyBorder;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DefaultFormatter;
import javax.swing.text.DefaultFormatterFactory;
import javax.swing.text.NumberFormatter;
import raven.datetime.component.date.DatePicker;
import raven.modal.ModalDialog;
import raven.modal.component.SimpleModalBorder;

public class DevisCreatePanel extends javax.swing.JPanel {

    public static boolean reset = false;
    public static DevisCreateTableModel tableModel;
    //private  DevisFrame devisFrame;

    public static JComboBox<String> remiseComboBox;
    public static JComboBox<String> tvaComboBox;
    private static BigDecimal barresChassisFixeUtilisees;
    private static BigDecimal barresChassisOuvrantUtilisees;
    private static BigDecimal quantiteChassisFixeUtilisee;
    private static BigDecimal miseAJourQuantiteChassisFixeEnStock;
    private static int profileId;
    private static int chassisFixeId;

    private static BigDecimal miseAJourQuantiteChassisOuvrantEnStock;
    private static int chassisOuvrantId;

    public static BigDecimal quantiteChassisOuvrantUtilisee;
    public static BigDecimal quantiteBarreChassisFixeFenetre;
    public static BigDecimal quantiteBarreChassisFixePorte;
    public static BigDecimal quantiteBarreChassisOuvrantFenetre;
    public static BigDecimal quantiteBarreChassisOuvrantPorte;
    public static BigDecimal prixChassisOuvrantPorte;
     public static  BigDecimal prixVitrePorte;
      public static  BigDecimal prixChassisFixePorte;
      public static  BigDecimal prixRoulettePorte;
    public static BigDecimal prixJointPorte;
    public static BigDecimal prixSerrurePorte;
    public static BigDecimal prixVisAssemblagePorte;
    public static BigDecimal prixChassisFixeFenetre;
    public static BigDecimal prixChassisOuvrantFenetre;
    public static BigDecimal prixVitreFenetre;
    public static BigDecimal prixRouletteFenetre;
    public static BigDecimal prixJointFenetre;
    public static BigDecimal prixSerrureFenetre;
    public static BigDecimal prixVisAssemblageFenetre;
    public static BigDecimal prixPaumelleFenetre;
    public static BigDecimal prixPoigneeFenetre;
    public static BigDecimal prixPaumellePorte;
    public static BigDecimal prixPoigneePorte;
    public static BigDecimal nombreElement;
    public static BigDecimal nbLame;
     public static BigDecimal prixChassisFixeNaco;
    public static BigDecimal prixOperateurBarre;
    public static BigDecimal prixClips;
     public static BigDecimal prixRivettes;
    public static BigDecimal NR;
     public static BigDecimal NC;
     public static BigDecimal NOBT;
     public static BigDecimal prixVis;
    public static BigDecimal prixLames;
    public static BigDecimal NJT;
    public static int nbElement;
    private static BigDecimal TROIS_DEMI_POUCE = new BigDecimal("88.9");
  
private static BigDecimal QUATRE_VINT_CINQ_POUCE = new BigDecimal("107.95");
 public static int quantiteLamme;
  public static  int quantiteOperateurBarre;
 public static  int nbRivette;
    private static BigDecimal roundedNL;
  public static BigDecimal quantiteBarreChassisFixeCloison;
   public static BigDecimal prixChassisFixeCloison;
     public static BigDecimal quantiteBarreChassisOuvrantCloison;
     public static BigDecimal prixChassisOuvrantCloison;
     public static BigDecimal prixVitreCloison;
     public static BigDecimal prixJointCloison;
    public static BigDecimal prixVisAssemblageCloison;


    private JPanel panel;
    private JTable mainTable;
    private JTable totalsTable;


    // Composants spécifiques à chaque type de produit
    // Porte
    private JComboBox<String> modelesProfileCouleursPorteComboBox;
    private JComboBox<String> typePorteComboBox;
    private JComboBox<String> structurePorteComboBox;
    private JComboBox<String> vitragePorteComboBox;
    private JComboBox<String> typeVitrePorteComboBox;
    private JSpinner nbVentauxPorteSpinner;
    
    public static JPanel SpinnerContainerPanelPorte;
    public static JScrollPane scrollPaneSouslignePorte;
    public static List<JButton> removeButtonsPorte = new ArrayList<>();
    
    private JSpinner hauteurPorteSpinner;
    private JSpinner largeurPorteSpinner;
    private JSpinner quantitePorteSpinner;

    // Fenêtre
    private JComboBox<String> typeFenetreComboBox;
    private JComboBox<String> modelesProfileCouleursFenetreComboBox;
    private JComboBox<String> vitrageFenetreComboBox;
    private JComboBox<String> typeVitreFenetreComboBox;
    private JSpinner nbVentauxFenetreSpinner;
    
    public static JPanel SpinnerContainerPanelFenetre;
    public static JScrollPane scrollPaneSousligneFenetre;
    private List<JButton> removeButtonsFenetre = new ArrayList<>();
    
    private JSpinner hauteurFenetreSpinner;
    private JSpinner largeurFenetreSpinner;
    private JSpinner quantiteFenetreSpinner;

    // Naco
    private JComboBox<String> typeNacoComboBox;
    private JComboBox<String> modelesProfileCouleursNacoComboBox;
    private JComboBox<String> nbLamesNacoComboBox;
    private JComboBox<String> largeurLameNacoComboBox;
    private JComboBox<String> vitrageNacoComboBox;
    private JComboBox<String> typeVitreNacoComboBox;
   private JComboBox<String> verreNacoComboBox;
    private JSpinner nbVentauxNacoSpinner;
    
    
    public static JPanel SpinnerContainerPanelNaco;
    public static JScrollPane scrollPaneSousligneNaco;
    private List<JButton> removeButtonsNaco = new ArrayList<>();
    
    private JSpinner hauteurNacoSpinner;
    private JSpinner largeurNacoSpinner;
    private JSpinner quantiteNacoSpinner;

    //Cloison
    private JComboBox<String> modelesProfileCouleursCloisonComboBox;
    private JComboBox<String> typeCloisonComboBox;
    private JComboBox<String> vitrageCloisonComboBox;
    private JComboBox<String> typeVitreCloisonComboBox;
    
    public static JPanel SpinnerContainerPanelCloison;
    public static JScrollPane scrollPaneSousligneCloison;
    private List<JButton> removeButtonsCloison = new ArrayList<>();
    
    private JSpinner hauteurCloisonSpinner;
    private JSpinner largeurCloisonSpinner;
    private JSpinner quantiteCloisonSpinner;

    public static DefaultTableModel totalsTableModel;

    public static JComboBox<String> clientCombo;
    public static JComboBox<String> statutComboBox;

    private TabbedPaneCustom tabbedPane;
    public static int produitId;

    private Integer vitrageId;
    private int typeVitreId;
    private Integer structureId;
    private int selectedIndex;
   
     public static  BigDecimal largeur; 
      public static BigDecimal hauteur;
     
    private int quantite;

    private RoundedButton addButton;
    public static RoundedButton saveButton;
    private RoundedButton backButton;
    private boolean isEditMode = false;
    public static RoundedButton updateButton;
    private JPanel leftPanel;
    private JPanel rightPanel;
    public static int devisId;  // Variable pour stocker l'ID du devis

    private ScrollPaneWin11 scroll;
    private JPanel mainTablePanel;
    private JPanel mainTablePanelContenaire;
    private JPanel totalsPanel;
    private JPanel subPanel;



    private int chassisTypeFixeId;
    private int chassisTypeOuvrantId;
    private int visId;

    private int jointId;
    private int serrureId;
    private Integer paumelleId;
    private Integer rouletteId;
       public static int nbVentaux;

    private Integer structure;
    private Integer  typeOuvertureId;
   private String designation;
  private  BigDecimal prixUnitaire;

            // Vérification des sélections pour chaque onglet
            boolean allSelectedDefault;
            boolean isfill;
    private Integer poigneeId;
   public static JFormattedTextField dateEditor;
    private DatePicker datePicker;
    private int profileAluId;
    public static BigDecimal prixUnitaireChassisFixe;
    public static BigDecimal prixUnitaireChassisOuvrant;
    public static BigDecimal prixUnitaireVitre;
    public static BigDecimal prixUnitaireVis;
    public static BigDecimal prixUnitaireJoint;
    public static BigDecimal prixUnitairePaumelle;
    public static BigDecimal prixUnitaireSerrure;
    public static BigDecimal prixUnitairePoignee;
    public static BigDecimal longueurChassisFixe;
    public static BigDecimal longueurChassisOuvrant;

   // BigDecimal constants for unit conversions (example: inch to mm conversions)
     public static  BigDecimal  SIX_POUCE = new BigDecimal("152.4"); 
   public static  BigDecimal QUART_POUCE = new BigDecimal("6.35"); 
   public static  BigDecimal DEMI_POUCE = new BigDecimal("12.7");
    public static BigDecimal UN_POUCE = new BigDecimal("25.4"); 
    public static  BigDecimal DEUX_POUCES = new BigDecimal("50.8"); 
    public static  BigDecimal QUATRE_POUCES = new BigDecimal("101.6"); 
    public static  BigDecimal MILLIMETER_TO_INCH = new BigDecimal("25.4");
    private BigDecimal prixUnitaireRoulette;

    public static BigDecimal quantiteEnStockChassisOuvrant;
 
    public static BigDecimal quantiteEnStockChassisFixe;

  
   public static  BigDecimal quantiteBarreChassisOuvrantUtilisee;
 
    
    public static int quantiteEnStockPoignee;
   public static int quantiteEnStockPaumelle;
    public static int quantiteEnStockRoulette;
    public static BigDecimal quantiteEnStockJoint;
    public static int quantiteEnStockVis;
    public static int quantiteEnStockSerrure;
    public static int quantiteEnStockVitre;
   public static  int nbSerrures;
   public static  int nbVisAssemblage;
    public static  Integer nbPaumelle;
    public static  Integer nbPoignee;
    public static  BigDecimal longueurTotalJoint;
    public static int nbRouletteUtilisees;
    public static int nbPaumelleUtilisees;
    public static int nbSerrureUtilisees;
    public static int nbVisUtilisees;
    public static BigDecimal nbJointUtilisees;
    public static int nbPoigneeUtilisees;
     public static JComboBox<String> acompteComboBox;
      public static int nombreVentaux;
      public static LargeurAndHauteur dims;
   public static BigDecimal lonChassisFixe;
  public static BigDecimal lonChassisOuvrant;

    private BigDecimal longueurJoint;
    public static Integer nbRoulette;
    private JCheckBox moustiquairePorteCheckBox;
   public static JCheckBox intermediairePorteCheckBox;
    private JCheckBox impostePorteCheckBox;
   private static int nombreIntermediaires ;
    private int ClipsId;
    private Integer operateurId;
    private Integer rivetteId;
    private Integer clipsId;
      public static Integer nbClips;
       private BigDecimal prixUnitaireClips;
       
    private int lameVerreId;
    private BigDecimal largeurLame;
    private BigDecimal prixUnitaireLame;
   
    private BigDecimal prixUnitaireRivettes;
    private BigDecimal prixUnitaireOperateurBarre;
    private BigDecimal quantiteBarreChassisOuvrantNaco;
    private BigDecimal prixJointNaco;
    private BigDecimal prixPaumelleNaco;
    private BigDecimal prixSerrureNaco;
    private BigDecimal prixPoigneeNaco;
    private BigDecimal prixVisAssemblageNaco;
  
    
   
    
  
  
    
    
       public DevisCreatePanel(String clientName) {
        initComponents();
        
        
     try {
            customInitComponents();
           
            setClientComboBox(clientName);
        } catch (Exception ex) {
            Logger.getLogger(DevisCreatePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        totalsTable.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:1,1,1,1,$TableHeader.bottomSeparatorColor,,10");
        mainTablePanel.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:1,1,1,1,$TableHeader.bottomSeparatorColor,,10");
        scroll.putClientProperty(FlatClientProperties.STYLE, ""
                + "border:3,0,3,0,$Table.background,10,10");
        scroll.getVerticalScrollBar().putClientProperty(FlatClientProperties.STYLE, ""
                + "hoverTrackColor:null");
    }

     public static void fillDevisForm(ModelDevis devis) {
        DevisCreatePanel.devisId = devis.getDevisId();
        // Remplir les champs de texte, les ComboBoxes, les Spinners, etc. avec les informations du devis
        clientCombo.setSelectedItem(devis.getClient().getNom());
         dateEditor.setText(devis.getFormattedDateDevis());


    // Remplir la table avec les lignes de devis
        tableModel.setDevisLignes(devis.getLignesDevis());

        // Remplir la remiseComboBox avec la valeur du pourcentage de remise
        String remisePercentage = formatPercentage(devis.getRemisePercentage());
        remiseComboBox.setSelectedItem(remisePercentage);
  String acomptePercentage = formatPercentage(devis.getAcomptePercentage());
        acompteComboBox.setSelectedItem(acomptePercentage);
        // Remplir la tvaComboBox avec la valeur du pourcentage de TVA
        String tvaPercentage = formatPercentage(devis.getTvaPercentage());
        tvaComboBox.setSelectedItem(tvaPercentage);

        // Mettre à jour les valeurs dans le modèle de la table des totaux
        updateTotalsTable(devis);
    }
private static void updateTotalsTable(ModelDevis devis) {
    // Initialisation des totaux
    BigDecimal totalHt = BigDecimal.ZERO;
    BigDecimal remisePercent = devis.getRemisePercentage().divide(BigDecimal.valueOf(100)); // Division sécurisée
    BigDecimal tvaPercent = devis.getTvaPercentage().divide(BigDecimal.valueOf(100)); // Division sécurisée
    BigDecimal acomptePercent = devis.getAcomptePercentage().divide(BigDecimal.valueOf(100)); // Division sécurisée

    // Calculer le total HT
    for (ModelLigneDevis ligneDevis : devis.getLignesDevis()) {
        totalHt = totalHt.add(ligneDevis.getTotalPrix()); // Assurez-vous que getTotalPrix() retourne un BigDecimal correct
    }

    // Calculer la remise
    BigDecimal remise = totalHt.multiply(remisePercent);

    // Calculer le total net HT après remise
    BigDecimal totalNetHt = totalHt.subtract(remise);

    // Calculer l'acompte
    BigDecimal totalAcompte = totalNetHt.multiply(acomptePercent);

    // Calculer le total TVA
    BigDecimal totalTva = totalNetHt.multiply(tvaPercent);

    // Calculer le total TTC ajusté avec l'acompte
    BigDecimal totalTtc = totalNetHt.add(totalTva).subtract(totalAcompte);

    // Mettre à jour les valeurs dans le modèle de la table des totaux
    totalsTableModel.setValueAt(totalHt, 0, 0); // Total HT
    totalsTableModel.setValueAt(remise, 1, 0); // Remise
    totalsTableModel.setValueAt(totalAcompte, 2, 0); // Acompte
    totalsTableModel.setValueAt(totalTva, 3, 0); // Total TVA
    totalsTableModel.setValueAt(totalTtc, 4, 0); // Total TTC

    // Informer Swing que les données du modèle ont changé
    totalsTableModel.fireTableDataChanged();
}



 private JPanel addAddClientButton() {
    // Panneau principal avec BorderLayout
    JPanel buttonContainer = new JPanel(new BorderLayout());

    // Panneau interne pour contenir le bouton
    JPanel buttonPanel = new JPanel(new BorderLayout());

    // Création du bouton d'ajout de client
    JButton addClientButton = new JButton();
    addClientButton.setIcon(new ImageIcon(getClass().getResource("/Icon/icons8-add-user-male-26 (1).png")));
    addClientButton.setHorizontalTextPosition(SwingConstants.CENTER);
    addClientButton.setPreferredSize(new Dimension(26, 26));
    addClientButton.setBackground(Color.decode("#009FE3")); // Couleur de fond

addClientButton.addActionListener(e -> {
    AddClientForm dialog = new AddClientForm(DevisCreatePanel.this);
          ModalDialog.showModal(this, new SimpleModalBorder(dialog, "Ajouter Client", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
             
            }));
  // Clear existing items before adding new ones
        clientCombo.removeAllItems();
    // Mettre à jour le JComboBox des clients après la fermeture du dialogue
    List<ModelClient> updatedClientList = DatabaseUtils.getAllClients();
      // Ajouter chaque client dans le JComboBox en tant qu'objet ModelClient
    for (ModelClient client : updatedClientList) {
        clientCombo.addItem(client.getNom()); // Ajoute l'objet ModelClient au JComboBox
       
    }
    
  
});


    // Ajout du bouton dans le panel
    buttonPanel.add(addClientButton, BorderLayout.CENTER);
    buttonPanel.setPreferredSize(new Dimension(26, 26));

    // Espacement à gauche du bouton
   // buttonContainer.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
    buttonContainer.add(buttonPanel, BorderLayout.CENTER);

    return buttonContainer;
}

    private void customInitComponents() throws Exception {
      
        setLayout(new BorderLayout());

        // Panel principal pour contenir tous les composants
        panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(4, 0, 2, 0));
        add(panel,BorderLayout.CENTER);

        // Panel pour le JTabbedPane et le panel Date Client
        JPanel tabPanel = new JPanel(new BorderLayout());
           tabPanel.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
    }
});
        panel.add(tabPanel, BorderLayout.NORTH);

        // JTabbedPane pour gérer les différents types de produits
        tabbedPane = new TabbedPaneCustom();
        tabbedPane.putClientProperty(FlatClientProperties.STYLE, ""
              
                + "background:$TabbedPane.background");
           tabbedPane.setSelectedColor(new Color(49, 158, 242));
        tabbedPane.setUnselectedColor(new Color(185, 214, 255));
    tabbedPane.addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
    }
});

        tabPanel.add(tabbedPane, BorderLayout.WEST);
        Dimension tabPanelSize = new Dimension(0, 280);
        tabPanel.setPreferredSize(tabPanelSize);
        // Panel pour Date et Client à droite du JTabbedPane
        // tabPanel.add(dateClientPanel, BorderLayout.EAST);

        // Ajouter le panel des boutons en dessous du JTabbedPane
        JPanel buttonPanel = createButtonPanel();
        tabPanel.add(buttonPanel, BorderLayout.SOUTH); // Ajoutez le panel en bas
        // Onglet pour les Portes

        tabbedPane.addTab("Porte", createPortePanel());

        // Onglet pour les Fenêtres
    
        tabbedPane.addTab("Fenêtre", createFenetrePanel());
  // Onglet pour les Nacos
          tabbedPane.addTab("Naco",  createNacoPanel());
          // Onglet pour les Cloison
        tabbedPane.addTab("Cloison",  createCloisonPanel());
     

        JPanel optionsAndInfoPanel = createOptionsAndInfoPanel();
        tabPanel.add(optionsAndInfoPanel, BorderLayout.CENTER);

        // Panel pour le tableau principal
        mainTablePanelContenaire = new JPanel(new BorderLayout());
        mainTablePanel = new JPanel(new BorderLayout());
        mainTablePanelContenaire.setBorder(new EmptyBorder(0, 10, 0, 10));
        // Ajouter le tableau
        tableModel = new DevisCreateTableModel(this);
        mainTable = new JTable(tableModel);

        mainTable = new RollOverTable(tableModel);
        mainTable.setSelectionBackground(new Color(75, 110, 175));
        // Configuration de la largeur des colonnes
        customizeTable(mainTable); // Méthode pour personnaliser le tableau
        // Configurer le renderer pour la colonne Action
        configureActionColumn(mainTable, tableModel);
        // Add the mainTable to the mainTablePanel

        scroll = new ScrollPaneWin11();
        scroll.setViewportView(mainTable);

        mainTablePanel.add(scroll, BorderLayout.CENTER);
        JPanel subPanel = setupTotalsPanel();
        panel.add(subPanel, BorderLayout.SOUTH); // Ajout en bas

        // Ajouter le southPanel au sud du panel principal
        mainTablePanelContenaire.add(mainTablePanel, BorderLayout.CENTER);
        panel.add(mainTablePanelContenaire, BorderLayout.CENTER);
          panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
             // panel.requestFocusInWindow();
              KeyboardFocusManager.getCurrentKeyboardFocusManager().clearGlobalFocusOwner();
               
            }
        });

        // Sélectionner le premier client et mettre à jour les détails du client
        selectFirstClient();

    }

    private void addSousLignePorte() {
        // Créer un panneau pour la sous-ligne avec un bouton de suppression
        JPanel sousLignePanel = new JPanel(new BorderLayout());

        // Créer et ajouter la sous-ligne
        JPanel sousLigneContent = createSousLignePortePanel();

        sousLignePanel.add(sousLigneContent, BorderLayout.NORTH);

        JPanel sousLignePanelContenaire = new JPanel(new BorderLayout(0, 5));
        sousLignePanelContenaire.add(sousLignePanel, BorderLayout.NORTH);

        // Créer et ajouter le bouton de suppression pour cette sous-ligne
        JButton removeSousLigneButton = new JButton();
        removeSousLigneButton.setIcon(new ImageIcon(getClass().getResource("/Icon/minus.png")));
        Dimension buttonSize = new Dimension(16, 16);
        removeSousLigneButton.setPreferredSize(buttonSize);
        removeSousLigneButton.addActionListener((ActionEvent e) -> {
            removeSousLigne(sousLignePanelContenaire);
            removeButtonsPorte.remove(removeSousLigneButton);
            updateRemoveButtonsVisibilityPorte();
            updateScrollBarPolicyPorte();
            SpinnerContainerPanelPorte.requestFocusInWindow();
        });

        // Rendre le bouton de suppression invisible si c'est la première sous-ligne
        if (removeButtonsPorte.size() == 1) {
            removeSousLigneButton.setVisible(false);
        }

        sousLignePanelContenaire.add(removeSousLigneButton, BorderLayout.EAST);

        // Ajouter la sous-ligne au conteneur
        SpinnerContainerPanelPorte.add(sousLignePanelContenaire);
        removeButtonsPorte.add(removeSousLigneButton);

        // Revalider le panneau pour afficher les modifications
        SpinnerContainerPanelPorte.revalidate();
        SpinnerContainerPanelPorte.repaint();

        updateRemoveButtonsVisibilityPorte();
        updateScrollBarPolicyPorte();
        // Animer le défilement vers la fin à droite
        SwingUtilities.invokeLater(() -> animateScrollToRightPorte());

    }

      public static JSpinner configureSpinnerInteger() {
        // Utiliser 0 comme valeur initiale, puis la masquer dans l'affichage
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(0, 0, Integer.MAX_VALUE, 1));
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "#");

        spinner.setPreferredSize(new Dimension(200, 33));
        JFormattedTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
        textField.setHorizontalAlignment(JTextField.RIGHT);

        // Configurer le formatter pour permettre un champ vide
        DefaultFormatter formatter = (DefaultFormatter) textField.getFormatter();
        formatter.setAllowsInvalid(true); // Autoriser les entrées invalides pour permettre un champ vide

        // Comportement pour effacer la valeur lors du premier focus
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                
                if (spinner.getValue().equals(0)) {
                    textField.setText(""); // Effacer l'affichage si la valeur est 0
                }
                
                SwingUtilities.invokeLater(textField::selectAll);
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Si le champ est vide, remettre la valeur à zéro pour le modèle de données
                if (textField.getText().trim().isEmpty()) {
                    spinner.setValue(0);
                }
            }
        });

        spinner.setEditor(editor);
        return spinner;
    }

    public static JSpinner configureSpinnerBigDecimal() {
    // Modèle pour Double : valeur initiale, valeur minimale, valeur maximale, pas d'incrément de 0,1
    SpinnerNumberModel model = new SpinnerNumberModel(
        0.0,             // Valeur initiale
        null,             // Valeur minimale
        Double.MAX_VALUE, // Valeur maximale
        0.1              // Pas d'incrément de 0,1
    );

    JSpinner spinner = new JSpinner(model); // example values

        // Configurer l'éditeur pour afficher deux décimales
        JSpinner.NumberEditor editor = new JSpinner.NumberEditor(spinner, "#.##");
        spinner.setEditor(editor);

        // Configurer la taille et l'alignement du champ texte
        spinner.setPreferredSize(new Dimension(200, 33));
        JFormattedTextField textField = ((JSpinner.DefaultEditor) editor).getTextField();
        textField.setHorizontalAlignment(JTextField.RIGHT);

        // Comportement pour effacer la valeur lors du premier focus
        textField.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                if (spinner.getValue().equals(0.0)) {
                    textField.setText(""); // Effacer l'affichage si la valeur est 0
                }
                SwingUtilities.invokeLater(textField::selectAll);
            }

            @Override
            public void focusLost(FocusEvent e) {
                // Si le champ est vide, remettre la valeur à zéro pour le modèle de données
                if (textField.getText().trim().isEmpty()) {
                    spinner.setValue(0.0);
                }
            }
        });

    
       

        return spinner;
    }

  

    private void updateScrollBarPolicyPorte() {
        if (scrollPaneSouslignePorte != null) { // Vérifiez si scrollPane est initialisé
            if (SpinnerContainerPanelPorte.getComponentCount() > 1) {
                // Activer le défilement horizontal si plus d'une sous-ligne
                scrollPaneSouslignePorte.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            } else {
                // Désactiver le défilement horizontal si une seule sous-ligne
                scrollPaneSouslignePorte.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            }
        }
    }

    private void animateScrollToRightPorte() {
        JScrollBar horizontalScrollBar = scrollPaneSouslignePorte.getHorizontalScrollBar();

        int targetValue = horizontalScrollBar.getMaximum();

        // Utilisation d'un AtomicInteger pour une valeur mutable
        AtomicInteger currentValue = new AtomicInteger(horizontalScrollBar.getValue());

        // Définir le délai et la vitesse de défilement
        int delay = 5; // Délai en millisecondes entre chaque étape de défilement
        int increment = 20;

        Timer timer = new Timer(delay, null);
        timer.addActionListener(e -> {
            if (currentValue.get() < targetValue) {
                int newValue = currentValue.addAndGet(increment);
                if (newValue > targetValue) {
                    currentValue.set(targetValue);
                }
                horizontalScrollBar.setValue(currentValue.get());
            } else {
                timer.stop();
            }
        });

        timer.start();
    }

    public void configureActionColumn(JTable table, DevisCreateTableModel tableModel) {

        TableColumn actionColumn = table.getColumnModel().getColumn(tableModel.getColumnCount() - 1);
        actionColumn.setCellRenderer(new ActionCellRenderer());
        actionColumn.setCellEditor(new ActionCellEditor(new ActionTableEvent() {

            @Override
            public void onEdit(int row) {

                openEditDialog(row);
            }

            @Override
            public void onDelete(int row) {
                int confirm = JOptionPane.showConfirmDialog(
                        null,
                        "Êtes-vous sûr de vouloir supprimer cette ligne ?",
                        "Confirmation de la suppression",
                        JOptionPane.YES_NO_OPTION
                );

                if (confirm == JOptionPane.YES_OPTION) {
                    if (table.isEditing()) {
                        table.getCellEditor().stopCellEditing();
                    }
                    tableModel.removeRow(row);
                    updateTotals();

                    if (areAllRowsEmpty(tableModel)) {
                        resetTotals();
                        tableModel.clearTable();

                    }
                }
                // Optionnel : Mettre à jour l'affichage après la suppression
            }

        })); // Passer la table à ActionCellEditor
    }

  private void openEditDialog(int row) {
    int mainRowIndex = tableModel.findMainRowIndexForSubRow(row);
    String designation = tableModel.getMainRowDesignation(mainRowIndex);
    List<Object[]> subRows = tableModel.getSubRowsForMainRow(mainRowIndex);
    int selectedRow = mainTable.getSelectedRow();

    try {

        if (designation.contains("PO")) {
            // Ouvrir le dialogue pour éditer une porte
            EditPorteForm editPorteDialog = new EditPorteForm(this, designation, subRows, selectedRow );
            editPorteDialog.setVisible(true);
        } else if (designation.contains("FE")) {
            // Ouvrir le dialogue pour éditer une fenêtre
            EditFenetreForm editFenetreDialog = new EditFenetreForm(this, designation, subRows, selectedRow);
            editFenetreDialog.setVisible(true);
        } else if (designation.contains("NA")) {
            // Ouvrir le dialogue pour éditer une fenêtre
            EditNacoForm editNacoDialog = new EditNacoForm(this, designation, subRows, selectedRow);
            editNacoDialog.setVisible(true);
        }
        else if (designation.contains("CL")) {
            // Ouvrir le dialogue pour éditer une fenêtre
            EditCloisonForm editCloisonDialog = new EditCloisonForm(this, designation, subRows, selectedRow);
            editCloisonDialog.setVisible(true);
        }
    } catch (Exception e) {
        // Afficher l'erreur dans la console pour identifier l'origine
        e.printStackTrace();
        
        // Afficher le message d'erreur à l'utilisateur
        JOptionPane.showMessageDialog(null, 
            "Erreur lors de l'ouverture du dialogue d'édition : " + e.getMessage(),
            "Erreur", 
            JOptionPane.ERROR_MESSAGE);
    }
}

    public void setEditMode(boolean editMode) {
        isEditMode = editMode;

        if (editMode) {
            updateButton.setVisible(true);
            saveButton.setVisible(false);
        } else {
            updateButton.setVisible(false);
            saveButton.setVisible(true);
        }

        // Forcer le rafraîchissement de l'interface
        updateButton.revalidate();
        updateButton.repaint();
        saveButton.revalidate();
        saveButton.repaint();
    }
   

     public static void setClientComboBox(String clientName){ 
        if (clientName != null && !clientName.isEmpty()) {
            clientCombo.setSelectedItem(clientName);  // Select the client in the comboBox
        } else {
            selectFirstClient();  // If no client is passed, select the first client
        }
    }
    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new BorderLayout()); // Utilisation de FlowLayout pour centrer les boutons avec un espacement de 10 pixels
        buttonPanel.setBorder(new EmptyBorder(0, 5, 0, 5));
        leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 1 / 2));
        rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 1 / 2));
        Font buttonFont = new Font("Dialog", Font.BOLD, 14);
        Dimension buttonSize = new Dimension(150, 40);

        //addButton = new RoundedButton("Ajouter", new Color(92, 184, 92), new Color(112, 204, 112));
          addButton = new RoundedButton("Ajouter", new Color(46, 204, 113), new Color(60, 224, 133));
            addButton.setIcon(new ImageIcon(getClass().getResource("/Icon/icons8-add-32.png")));
        // saveButton = new RoundedButton("Enregistrer",   new Color(0, 74, 120), new Color(11, 81, 161));
        saveButton = new RoundedButton("Enregistrer", new Color(0, 128, 192), new Color(0, 148, 212));
        //saveButton = new RoundedButton("Enregistrer", new Color(0, 127, 255), new Color(51, 153, 255));

        saveButton.setIcon(new ImageIcon(getClass().getResource("/Icon/save.png")));
        updateButton = new RoundedButton("Mettre à jour", new Color(0, 188, 212), new Color(20, 208, 232));

        updateButton.setIcon(new ImageIcon(getClass().getResource("/Icon/update.png")));
       backButton = new RoundedButton("Retour", new Color(60, 98, 105), new Color(80, 118, 125));

        backButton.setIcon(new ImageIcon(getClass().getResource("/Icon/icons8-go-back-24.png")));

        JButton[] buttons = {addButton, updateButton, saveButton, backButton};

        for (JButton button : buttons) {
            button.setFont(buttonFont);
            button.setPreferredSize(buttonSize);
            button.setForeground(Color.WHITE); // Couleur du texte
        }

        leftPanel.add(backButton);
        rightPanel.add(updateButton);

        rightPanel.add(saveButton);
        rightPanel.add(addButton);
        
        setEditMode(isEditMode);
        buttonPanel.add(leftPanel, BorderLayout.WEST);
        buttonPanel.add(rightPanel, BorderLayout.EAST);

        // Ajout de l'ActionListener pour le bouton Retour
        backButton.addActionListener(e -> retourAction());

        updateButton.addActionListener((ActionEvent e) -> {
            try {

                updateDevisToDatabase(DevisCreatePanel.devisId);

            } catch (Exception ex) {
                Logger.getLogger(DevisCreatePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        
      addButton.addActionListener((ActionEvent e) -> {
    selectedIndex = tabbedPane.getSelectedIndex();

    switch (selectedIndex) {
        case 0:
            handlePorteSelection();
            break;
        case 1:
            handleFenetreSelection();
            break;
        case 2:
            handleNacoSelection();
            break;
    case 3:
            handleCloisonSelection();
            break;
        default:
            throw new IllegalStateException("Unexpected value: " + selectedIndex);
    }

    // Mise à jour de la table des totaux après l'ajout d'un produit
    updateTotals();
});

        saveButton.addActionListener((ActionEvent e) -> {
            try {

                    saveDevisToDatabase();

            } catch (Exception ex) {
                Logger.getLogger(DevisCreatePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        return buttonPanel;
    }
   public void handleFenetreSelection() {
        try {

            isfill = (nbVentauxFenetreSpinner.getValue().equals(0));
            if (isfill) {
                JOptionPane.showMessageDialog(null, "Le nombre de ventaux pour la fenêtre doit être supérieur à 0.");
                return; // Arrête l'exécution ici si la validation échoue
            }
            // Ensuite, vérification des sélections dans les JComboBox
            allSelectedDefault = (typeFenetreComboBox.getSelectedIndex() == 0
                    || typeVitreFenetreComboBox.getSelectedIndex() == 0
                    || modelesProfileCouleursFenetreComboBox.getSelectedIndex() == 0
                    || vitrageFenetreComboBox.getSelectedIndex() == 0);

            if (allSelectedDefault) {
                JOptionPane.showMessageDialog(null, "Sélectionnez une option pour chaque critère de fenêtre.");
                return;
            }

            // Récupérer et stocker les IDs des choix sans les afficher
            String selectedFenetre = (String) typeFenetreComboBox.getSelectedItem();
            String selectedProfileAlu = (String) modelesProfileCouleursFenetreComboBox.getSelectedItem();
            nbVentaux = (int) nbVentauxFenetreSpinner.getValue();
            String selectedVitrage = (String) vitrageFenetreComboBox.getSelectedItem();
            String selectedTypeVitre = (String) typeVitreFenetreComboBox.getSelectedItem();

            String[] profileAluParts = parseProfileAluString(selectedProfileAlu);
            String model = profileAluParts[0];
            String couleur = profileAluParts[1];

            String[] typeVitreParts = parseTypeVitreString(selectedTypeVitre);
            String type = typeVitreParts[0];
            int epaisseur = Integer.parseInt(typeVitreParts[1]);

            produitId = DatabaseUtils.getProductId(selectedFenetre);
            typeOuvertureId = DatabaseUtils.getTypeOuvertureIdFromProduit(produitId);
          if(typeOuvertureId ==1 &&nbVentauxFenetreSpinner.getValue().equals(1)){
                JOptionPane.showMessageDialog(null, "Le nombre de ventaux pour la fenêtre coulissante doit être supérieur ou égale a deux.");
                return;
                
            }
            // paumelleId = DatabaseUtils.getPaumelleId(model, couleur);
            visId = DatabaseUtils.getVisId(model, couleur);
            jointId = DatabaseUtils.getJointId(model, couleur);
            serrureId = DatabaseUtils.getJointId(model, couleur);
            // rouletteId = DatabaseUtils.getRouletteId(model, couleur);
            vitrageId = DatabaseUtils.getIdFromChoice(selectedVitrage, "vitrage", "type", "vitrage_id");
            typeVitreId = DatabaseUtils.getTypeVitreId(type, epaisseur);
            structureId = null;
            // Get IDs and check for null values
            profileAluId = DatabaseUtils.getProfileAluId(model, couleur);
            chassisTypeFixeId = DatabaseUtils.getChassisTypeFixeId(profileAluId);
         
   if (chassisTypeFixeId == -1) {
                JOptionPane.showMessageDialog(null, "Aucun châssis fixe trouvé pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            chassisTypeOuvrantId = DatabaseUtils.getChassisTypeOuvrantId(profileAluId);
            if (chassisTypeOuvrantId == -1) {
                JOptionPane.showMessageDialog(null, "Aucun châssis ouvrant trouvé pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            visId = DatabaseUtils.getVisId(model, couleur);
            if (visId == -1) {
                JOptionPane.showMessageDialog(null, "Aucune vis trouvée pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            jointId = DatabaseUtils.getJointId(model, couleur);
            if (jointId == -1) {
                JOptionPane.showMessageDialog(null, "Aucun joint trouvé pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            serrureId = DatabaseUtils.getSerrureId(model, couleur);
            if (serrureId == -1) {
                JOptionPane.showMessageDialog(null, "Aucune serrure trouvée pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Logique en fonction du type d'ouverture
            if (typeOuvertureId == 1) { // Coulissante
                paumelleId = null; // Coulissante -> pas de paumelle
                poigneeId = null;
                rouletteId = DatabaseUtils.getRouletteId(model, couleur); // Roulette requise

                if (rouletteId == null) {
                    JOptionPane.showMessageDialog(null, "Aucune roulette trouvée pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    // Retrieve the unit price for the roulette
                    ModelRoulette roulette = DatabaseUtils.getRouletteById(rouletteId);
                    prixUnitaireRoulette = roulette.getPrixUnitaire();
                    quantiteEnStockRoulette = roulette.getQuantiteEnStock();
                
                }

            } else if (typeOuvertureId == 2||typeOuvertureId == 3) { // Battante
                rouletteId = null; // Battante -> pas de roulette
                poigneeId = DatabaseUtils.getPoigneeId(model, couleur);
                paumelleId = DatabaseUtils.getPaumelleId(model, couleur);

                if (paumelleId == null) {
                    JOptionPane.showMessageDialog(null, "Aucune paumelle trouvée pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (poigneeId == null) {
                    JOptionPane.showMessageDialog(null, "Aucune poignee trouvée pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    // Retrieve the unit prices for paumelle and poignee
                    ModelPaumelle paumelle = DatabaseUtils.getPaumelleById(paumelleId);
                    prixUnitairePaumelle = paumelle.getPrixUnitaire();
                    quantiteEnStockPaumelle = paumelle.getQuantiteEnStock();
                    ModelPoignee poignee = DatabaseUtils.getPoigneeById(poigneeId);
                    prixUnitairePoignee = poignee.getPrixUnitaire();
                    quantiteEnStockPoignee = poignee.getQuantiteEnStock();
                    
                }
            }

            // Fetch ModelChassis instance and retrieve prixUnitaire
            ModelChassis fixedChassis = DatabaseUtils.getChassisById(chassisTypeFixeId);
            if (fixedChassis == null) {
                JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour le châssis fixe.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            quantiteEnStockChassisFixe = fixedChassis.getQuantiteEnStock();
            prixUnitaireChassisFixe = fixedChassis.getPrixUnitaire();
            longueurChassisFixe = fixedChassis.getLongueurBarre();

            System.out.println("Prix unitaire du châssis fixe: " + prixUnitaireChassisFixe + " Longueur châssis Fixe: " + longueurChassisFixe);

            // Fetch ModelChassis instance and retrieve prixUnitaire
            ModelChassis ouvrantChassis = DatabaseUtils.getChassisById(chassisTypeOuvrantId);
            if (ouvrantChassis == null) {
                JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour le châssis Ouvrant.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            quantiteEnStockChassisOuvrant = ouvrantChassis.getQuantiteEnStock();
            prixUnitaireChassisOuvrant = ouvrantChassis.getPrixUnitaire();
            longueurChassisOuvrant = ouvrantChassis.getLongueurBarre();
            System.out.println("Prix unitaire du châssis Ouvrant: " + prixUnitaireChassisOuvrant + " Longueur châssis Ouvrant: " + longueurChassisOuvrant);

            ModelTypeVitre vitre = DatabaseUtils.getTypeVitreById(typeVitreId);
            if (vitre == null) {
                JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour la vitre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                 prixUnitaireVitre = vitre.getPrixUnitaire();
                // quantiteEnStockVitre = vitre.getQuantiteEnStock();
            }
           

            ModelJoint joint = DatabaseUtils.getJointById(jointId);
            if (joint == null) {
                JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour le joint", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                  prixUnitaireJoint = joint.getPrixUnitaire();
                  quantiteEnStockJoint = joint.getQuantiteEnStock();
            }
          

          
         
            ModelSerrure serrure = DatabaseUtils.getSerrureById(serrureId);
            if (serrure == null) {
                JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour la serrure", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                  prixUnitaireSerrure = serrure.getPrixUnitaire();
                  quantiteEnStockSerrure = serrure.getQuantiteEnStock();

            }
             ModelVis vis = DatabaseUtils.getVisById(visId);
            if (vis == null) {
                JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour la vis", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                   prixUnitaireVis = vis.getPrixUnitaire();
                   quantiteEnStockVis = vis.getQuantiteEnStock();

            }
 
          designation = buildFenetreDesignation();
       
       

        List<List<JSpinner>> panelSpinners = getSpinnerValues(SpinnerContainerPanelFenetre);
        for (List<JSpinner> spinners : panelSpinners) {
            if (spinners.size() == 3) {
              largeur =  BigDecimal.valueOf((Double) spinners.get(0).getValue());
              hauteur = BigDecimal.valueOf((Double) spinners.get(1).getValue());

if (largeur == null || hauteur == null) {
    // Gérer le cas où largeur ou hauteur est null
    System.out.println("Largeur ou hauteur est null");
    return; // ou lancez une exception, ou affichez un message d'erreur
}else{
     System.out.println("Largeur "+largeur+" hauteur "+ hauteur);
}

                quantite = (int) spinners.get(2).getValue();

                if (quantite == 0) {
                    JOptionPane.showMessageDialog(null, "La quantité ne peut pas être égale à 0.");
                    return;
                }
                  if (typeOuvertureId == 1 ) {  // Pour une fenêtre coulissante
                  
                      nbPoignee = null;
                      nbPaumelle = null;
                      longueurJoint = longueurTotalJoint;
                      prixUnitaire = calculateUnitPriceFenetreCoulissante(hauteur, largeur, nbVentaux, prixUnitaireChassisFixe,longueurChassisFixe,prixUnitaireChassisOuvrant,longueurChassisOuvrant,prixUnitaireVitre,prixUnitaireJoint,prixUnitaireVis,prixUnitaireRoulette,prixUnitaireSerrure);

           
        } else  if (typeOuvertureId == 2||typeOuvertureId == 3) { // Pour une fenêtre battante 
             nbRoulette = null;
      prixUnitaire = calculateUnitPriceFenetreBattante(hauteur, largeur, nbVentaux, prixUnitaireChassisFixe,longueurChassisFixe,prixUnitaireChassisOuvrant,longueurChassisOuvrant,prixUnitaireVitre,prixUnitaireJoint,prixUnitaireVis,prixUnitairePaumelle,prixUnitaireSerrure,prixUnitairePoignee);

        }

                tableModel.addOrUpdateRow(produitId, chassisTypeFixeId, chassisTypeOuvrantId, nbVentaux, 
                        vitrageId, typeVitreId, structureId, paumelleId, rouletteId, serrureId, poigneeId, visId, jointId, designation, prixUnitaire, quantite, hauteur, largeur,nbRoulette,nbPaumelle,nbPoignee,nbVisAssemblage,nbSerrures,longueurJoint,quantiteBarreChassisFixeFenetre,quantiteBarreChassisOuvrantFenetre,prixChassisFixeFenetre,prixChassisOuvrantFenetre,prixVitreFenetre,prixJointFenetre,prixRouletteFenetre,prixPaumelleFenetre,prixSerrureFenetre,prixPoigneeFenetre,prixVisAssemblageFenetre,
                        null,null,null,null,null,null,null,null,null,null,null,null);
                resetFenetreFields();
            }
        }

    } catch (Exception ex) {
        Logger.getLogger(DevisCreatePanel.class.getName()).log(Level.SEVERE, null, ex);
    }
}

   
     public static BigDecimal calculateUnitPriceFenetreCoulissante(
            BigDecimal hauteur, BigDecimal largeur, int nbVentaux,
            BigDecimal prixUnitaireChassisFixe, BigDecimal longueurChassisFixe,
            BigDecimal prixUnitaireChassisOuvrant, BigDecimal longueurChassisOuvrant,
            BigDecimal prixUnitaireVitre, BigDecimal prixUnitaireJoint, BigDecimal prixUnitaireVis,
            BigDecimal prixUnitaireRoulette,
            BigDecimal prixUnitaireSerrure) {

        try {
            // Châssis fixe
            BigDecimal largeurChassisFixe = largeur.multiply(new BigDecimal(2));//largeur du fenetre * 2 cote haut et bas
            BigDecimal hauteurChassisFixe = hauteur.subtract(DEMI_POUCE).multiply(new BigDecimal(2));//hauteur du fenetre - demi pouce
            BigDecimal longueurTotalChassisFixe = largeurChassisFixe.add(hauteurChassisFixe);//total du chassis fixe
           quantiteBarreChassisFixeFenetre = longueurTotalChassisFixe.divide(longueurChassisFixe, RoundingMode.CEILING);
           prixChassisFixeFenetre  = prixUnitaireChassisFixe.multiply(quantiteBarreChassisFixeFenetre);

//            // Logs pour débogage
//            System.out.println("Largeur Chassis Fixe: " + largeurChassisFixe);
//            System.out.println("Hauteur Chassis Fixe: " + hauteurChassisFixe);
//          System.out.println("Quantité Barre Chassis Fixe Neccessaire: " + quantiteBarreChassisFixeFenetre);
           System.out.println("Prix Total Chassis Fixe Fenetre coulissante: " + prixChassisFixeFenetre);

            // Châssis ouvrant
            BigDecimal largeurChassisOuvrant = largeur.divide(new BigDecimal(nbVentaux)).subtract(QUART_POUCE).multiply(new BigDecimal(2)).multiply(new BigDecimal(nbVentaux));
            BigDecimal hauteurChassisOuvrant = hauteur.subtract(UN_POUCE).multiply(new BigDecimal(2)).multiply(new BigDecimal(nbVentaux));
            BigDecimal longueurTotalChassisOuvrant = largeurChassisOuvrant.add(hauteurChassisOuvrant);
            quantiteBarreChassisOuvrantFenetre = longueurTotalChassisOuvrant.divide(longueurChassisOuvrant, RoundingMode.CEILING);
          prixChassisOuvrantFenetre  = prixUnitaireChassisOuvrant.multiply(quantiteBarreChassisOuvrantFenetre);

//            // Logs pour débogage
//            System.out.println("Largeur Chassis Ouvrant: " + largeurChassisOuvrant);
//            System.out.println("Hauteur Chassis Ouvrant: " + hauteurChassisOuvrant);
//           System.out.println("Quantité Barre Chassis Ouvrant Neccessaire: " + quantiteBarreChassisOuvrantFenetre);
           System.out.println("Prix Total Chassis Ouvrant Fenetre coulissante: " + prixChassisOuvrantFenetre);

            // Calcul de la vitre
            BigDecimal largeurVitre = largeur.divide(new BigDecimal(nbVentaux)).subtract(DEUX_POUCES).divide(new BigDecimal(1000));
            BigDecimal hauteurVitre = hauteur.subtract(QUATRE_POUCES).divide(new BigDecimal(1000));
            BigDecimal dimensionVitre = largeurVitre.multiply(hauteurVitre);
            BigDecimal quantiteVitre = new BigDecimal(nbVentaux);
           prixVitreFenetre  = prixUnitaireVitre.multiply(dimensionVitre).multiply(quantiteVitre);
//
//            // Logs pour débogage
//            System.out.println("Largeur Vitre: " + largeurVitre);
//            System.out.println("Hauteur Vitre: " + hauteurVitre);
//            System.out.println("Dimension Vitre: " + dimensionVitre);
//            System.out.println("Quantite Vitre: " + quantiteVitre);
//            System.out.println("Prix Total Vitre: " + prixVitre);

            // **Roulette**
            nbRoulette = nbVentaux * 2;
           prixRouletteFenetre  = prixUnitaireRoulette.multiply(new BigDecimal(nbRoulette));
            // Logs pour débogage
//            System.out.println("Nombre Roulette: " + nbRoulette);
//            System.out.println("Prix Total Roulette: " + prixRoulette);

            // **Joint**
            // BigDecimal longueurJointChassisFixe = longueurTotalChassisFixe;
            BigDecimal longueurJointVitre = hauteurVitre.add(largeurVitre).multiply(new BigDecimal(2));
            BigDecimal QuantiteJoint = quantiteVitre;
            longueurTotalJoint = longueurJointVitre.multiply(QuantiteJoint);
            prixJointFenetre  = prixUnitaireJoint.multiply(longueurTotalJoint);

            // **Serrure**
            nbSerrures = nbVentaux;  // une serrure par ventail ouvrant
           prixSerrureFenetre  = prixUnitaireSerrure.multiply(new BigDecimal(nbSerrures));

            // Logs pour débogage
//            System.out.println("Longueur Joint Vitre: " + longueurTotalJoint);
//            System.out.println("Prix Total Joint: " + prixJoint);
//            System.out.println("Nombre Serrures: " + nbSerrures);
//            System.out.println("Prix Total Serrure: " + prixSerrure);
            // Calcul des vis
            nbVisAssemblage = nbVentaux * 6;
            prixVisAssemblageFenetre  = prixUnitaireVis.multiply(new BigDecimal(nbVisAssemblage));

            // Logs pour débogage
//            System.out.println("Nombre Vis Assemblage: " + nbVisAssemblage);
//            System.out.println("Prix Total Vis: " + prixVisAssemblage);

            // Somme totale de tous les coûts
            BigDecimal CoutFenetreCoulissante = prixChassisFixeFenetre.add(prixChassisOuvrantFenetre)
                    .add(prixVitreFenetre ).add(prixVisAssemblageFenetre ).add(prixRouletteFenetre )
                    .add(prixJointFenetre ).add(prixSerrureFenetre );
                  BigDecimal mainOeuvre = CoutFenetreCoulissante.multiply(new BigDecimal("0.25"));
        BigDecimal totalCoutFenetreCoulissante = CoutFenetreCoulissante.add(mainOeuvre).setScale(2, RoundingMode.CEILING);


            System.out.println("Coût Total Fenêtre: " + totalCoutFenetreCoulissante);

            return totalCoutFenetreCoulissante;

        } catch (Exception ex) {
            ex.printStackTrace();
            return BigDecimal.ZERO;
        }
    }
     
         public static BigDecimal calculateUnitPriceFenetreBattante(BigDecimal hauteur,
                 BigDecimal largeur, int nombreVentaux, BigDecimal prixUnitaireChassisFixe, BigDecimal longueurChassisFixe,
                 BigDecimal prixUnitaireChassisOuvrant, BigDecimal longueurChassisOuvrant, 
                 BigDecimal prixUnitaireVitre, BigDecimal prixUnitaireJoint, BigDecimal prixUnitaireVis, 
                 BigDecimal prixUnitairePaumelle, BigDecimal prixUnitaireSerrure, BigDecimal prixUnitairePoignee) {
      try {
        // Châssis fixe
        BigDecimal largeurChassisFixe = largeur.multiply(new BigDecimal(2));//largeur du fenetre * 2 cote haut et bas
        BigDecimal hauteurChassisFixe = hauteur.subtract(DEMI_POUCE).multiply(new BigDecimal(2));//hauteur du fenetre - demi pouce
        BigDecimal longueurTotalChassisFixe = largeurChassisFixe.add(hauteurChassisFixe);//total du chassis fixe
         quantiteBarreChassisFixeFenetre = longueurTotalChassisFixe.divide(longueurChassisFixe, RoundingMode.CEILING);
       prixChassisFixeFenetre  = prixUnitaireChassisFixe.multiply(quantiteBarreChassisFixeFenetre); 

        // Logs pour débogage
//        System.out.println("Largeur Chassis Fixe: " + largeurChassisFixe);
//        System.out.println("Hauteur Chassis Fixe: " + hauteurChassisFixe);
//        System.out.println("Quantité Barre Chassis Fixe Neccessaire: " +  quantiteBarreChassisFixeFenetre);
//        System.out.println("Prix Total Chassis Fixe: " + prixChassisFixe);

        // Châssis ouvrant
        BigDecimal largeurChassisOuvrant = largeur.divide(new BigDecimal(nombreVentaux)).subtract(QUART_POUCE).multiply(new BigDecimal(2)).multiply(new BigDecimal(nombreVentaux));
        BigDecimal hauteurChassisOuvrant = hauteur.subtract(UN_POUCE).multiply(new BigDecimal(2)).multiply(new BigDecimal(nombreVentaux));
        BigDecimal longueurTotalChassisOuvrant = largeurChassisOuvrant.add(hauteurChassisOuvrant);
        quantiteBarreChassisOuvrantFenetre = longueurTotalChassisOuvrant.divide(longueurChassisOuvrant, RoundingMode.CEILING);
     prixChassisOuvrantFenetre  = prixUnitaireChassisOuvrant.multiply(quantiteBarreChassisOuvrantFenetre);

  
        // Logs pour débogage
//        System.out.println("Largeur Chassis Ouvrant: " + largeurChassisOuvrant);
//        System.out.println("Hauteur Chassis Ouvrant: " + hauteurChassisOuvrant);
//        System.out.println("Quantité Barre Chassis Ouvrant Neccessaire: " + quantiteBarreChassisOuvrantFenetre);
//        System.out.println("Prix Total Chassis Ouvrant: " + prixChassisOuvrant);

        // Calcul de la vitre
        BigDecimal largeurVitre = largeur.divide(new BigDecimal(nombreVentaux)).subtract(DEUX_POUCES).divide(new BigDecimal(1000));
        BigDecimal hauteurVitre = hauteur.subtract(QUATRE_POUCES).divide(new BigDecimal(1000));
        BigDecimal dimensionVitre = largeurVitre.multiply(hauteurVitre);
        BigDecimal quantiteVitre = new BigDecimal(nombreVentaux);
        prixVitreFenetre  = prixUnitaireVitre.multiply(dimensionVitre).multiply(quantiteVitre);

        // Logs pour débogage
//        System.out.println("Largeur Vitre: " + largeurVitre);
//        System.out.println("Hauteur Vitre: " + hauteurVitre);
//        System.out.println("Dimension Vitre: " + dimensionVitre);
//        System.out.println("Quantite Vitre: " + quantiteVitre);
//        System.out.println("Prix Total Vitre: " + prixVitre);

         // **Joint**
     
        BigDecimal longueurJointVitre = hauteurVitre.add(largeurVitre).multiply(new BigDecimal(2));
        BigDecimal QuantiteJoint = quantiteVitre;
        longueurTotalJoint = longueurJointVitre.multiply(QuantiteJoint);
         prixJointFenetre  = prixUnitaireJoint.multiply(longueurTotalJoint);

    
        // **Serrure**
        nbSerrures = 1;  // une serrure par ventail ouvrant
        prixSerrureFenetre  = prixUnitaireSerrure.multiply(new BigDecimal(nbSerrures));

        // Logs pour débogage
//        System.out.println("Longueur Joint Vitre: " + longueurTotalJoint);
//        System.out.println("Prix Total Joint: " + prixJoint);
//        System.out.println("Nombre Serrures: " + nbSerrures);
//        System.out.println("Prix Total Serrure: " + prixSerrure);
        // Calcul des vis
        nbVisAssemblage =nombreVentaux * 6;
        prixVisAssemblageFenetre  = prixUnitaireVis.multiply(new BigDecimal(nbVisAssemblage));

        // Logs pour débogage
//        System.out.println("Nombre Vis Assemblage: " + nbVisAssemblage);
//        System.out.println("Prix Total Vis: " + prixVisAssemblage);
//        
         nbPaumelle = nombreVentaux * 2;
         prixPaumelleFenetre  = prixUnitairePaumelle.multiply(new BigDecimal(nbPaumelle));
         nbPoignee = 2;
        prixPoigneeFenetre  = prixUnitairePoignee.multiply(new BigDecimal(nbPoignee));

        // Logs pour débogage des matériels
//        System.out.println("Nombre de  Paumelle: " + nbPaumelle);
//        System.out.println("Prix Total Paumelle: " + prixPaumelle);
//        System.out.println("Nombre de Poignée: " + nbPoignee);
//        System.out.println("Prix Total Poignée: " + prixPoignee);

        // Somme totale de tous les coûts
        BigDecimal CoutFenetreBattante = prixChassisFixeFenetre .add(prixChassisOuvrantFenetre )
                .add(prixVitreFenetre ).add(prixVisAssemblageFenetre )
               .add(prixPaumelleFenetre ).add(prixPoigneeFenetre ).add(prixSerrureFenetre ).add(prixJointFenetre );
              BigDecimal mainOeuvre = CoutFenetreBattante.multiply(new BigDecimal("0.25"));
        BigDecimal totalCoutFenetreBattante = CoutFenetreBattante.add(mainOeuvre).setScale(2, RoundingMode.CEILING);

        System.out.println("Coût Total Fenêtre: " + totalCoutFenetreBattante);

        return totalCoutFenetreBattante;

    } catch (Exception ex) {
        ex.printStackTrace();
        return BigDecimal.ZERO;
    }
}
    public void handlePorteSelection() {
        try {

            isfill = (nbVentauxPorteSpinner.getValue().equals(0));
            if (isfill) {
                JOptionPane.showMessageDialog(null, "Le nombre de ventaux pour la fenêtre doit être supérieur à 0.");
                return; // Arrête l'exécution ici si la validation échoue
            }
            // Ensuite, vérification des sélections dans les JComboBox
            allSelectedDefault = (typePorteComboBox.getSelectedIndex() == 0
                    || modelesProfileCouleursPorteComboBox.getSelectedIndex() == 0
                    || structurePorteComboBox.getSelectedIndex() == 0
                    || vitragePorteComboBox.getSelectedIndex() == 0
                    || typeVitrePorteComboBox.getSelectedIndex() == 0);

            if (allSelectedDefault) {
                JOptionPane.showMessageDialog(null, "Sélectionnez une option pour chaque critère de porte.");
                return;
            }

            // Récupérer et stocker les IDs des choix sans les afficher
            String selectedPorte = (String) typePorteComboBox.getSelectedItem();
            String selectedProfileAlu = (String) modelesProfileCouleursPorteComboBox.getSelectedItem();
            nbVentaux = (int) nbVentauxPorteSpinner.getValue();
            String selectedStructure = (String) structurePorteComboBox.getSelectedItem();
            String selectedVitrage = (String) vitragePorteComboBox.getSelectedItem();
            String selectedTypeVitre = (String) typeVitrePorteComboBox.getSelectedItem();

            String[] profileAluParts = parseProfileAluString(selectedProfileAlu);
            String model = profileAluParts[0];
            String couleur = profileAluParts[1];

            String[] typeVitreParts = parseTypeVitreString(selectedTypeVitre);
            String type = typeVitreParts[0];
            int epaisseur = Integer.parseInt(typeVitreParts[1]);

            produitId = DatabaseUtils.getProductId(selectedPorte);
            typeOuvertureId = DatabaseUtils.getTypeOuvertureIdFromProduit(produitId);
            if (typeOuvertureId == 1 && nbVentauxPorteSpinner.getValue().equals(1)) {
                JOptionPane.showMessageDialog(null, "Le nombre de ventaux pour la fenêtre coulissante doit être supérieur ou égale a deux.");
                return;

            }
            // paumelleId = DatabaseUtils.getPaumelleId(model, couleur);
            visId = DatabaseUtils.getVisId(model, couleur);
            jointId = DatabaseUtils.getJointId(model, couleur);
            serrureId = DatabaseUtils.getSerrureId(model, couleur);
            // rouletteId = DatabaseUtils.getRouletteId(model, couleur);
            vitrageId = DatabaseUtils.getIdFromChoice(selectedVitrage, "vitrage", "type", "vitrage_id");
            typeVitreId = DatabaseUtils.getTypeVitreId(type, epaisseur);
            structureId = DatabaseUtils.getIdFromChoice(selectedStructure, "structure", "structure", "structure_id");
            // Get IDs and check for null values
            profileAluId = DatabaseUtils.getProfileAluId(model, couleur);
            chassisTypeFixeId = DatabaseUtils.getChassisTypeFixeId(profileAluId);

            if (chassisTypeFixeId == -1) {
                JOptionPane.showMessageDialog(null, "Aucun châssis fixe trouvé pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            chassisTypeOuvrantId = DatabaseUtils.getChassisTypeOuvrantId(profileAluId);
            if (chassisTypeOuvrantId == -1) {
                JOptionPane.showMessageDialog(null, "Aucun châssis ouvrant trouvé pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            visId = DatabaseUtils.getVisId(model, couleur);
            if (visId == -1) {
                JOptionPane.showMessageDialog(null, "Aucune vis trouvée pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            jointId = DatabaseUtils.getJointId(model, couleur);
            if (jointId == -1) {
                JOptionPane.showMessageDialog(null, "Aucun joint trouvé pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            serrureId = DatabaseUtils.getSerrureId(model, couleur);
            if (serrureId == -1) {
                JOptionPane.showMessageDialog(null, "Aucune serrure trouvée pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Logique en fonction du type d'ouverture
            if (typeOuvertureId == 1) { // Coulissante
                paumelleId = null; // Coulissante -> pas de paumelle
                poigneeId = null;
                rouletteId = DatabaseUtils.getRouletteId(model, couleur); // Roulette requise

                if (rouletteId == null) {
                    JOptionPane.showMessageDialog(null, "Aucune roulette trouvée pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    // Retrieve the unit price for the roulette
                    ModelRoulette roulette = DatabaseUtils.getRouletteById(rouletteId);
                    prixUnitaireRoulette = roulette.getPrixUnitaire();
                    quantiteEnStockRoulette = roulette.getQuantiteEnStock();
                
                }

            } else if (typeOuvertureId == 2) { // Battante
                rouletteId = null; // Battante -> pas de roulette
                poigneeId = DatabaseUtils.getPoigneeId(model, couleur);
                paumelleId = DatabaseUtils.getPaumelleId(model, couleur);

                if (paumelleId == null) {
                    JOptionPane.showMessageDialog(null, "Aucune paumelle trouvée pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                } else if (poigneeId == null) {
                    JOptionPane.showMessageDialog(null, "Aucune poignee trouvée pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
                    return;
                } 
                    // Retrieve the unit prices for paumelle and poignee
                    ModelPaumelle paumelle = DatabaseUtils.getPaumelleById(paumelleId);
                    prixUnitairePaumelle = paumelle.getPrixUnitaire();
                    quantiteEnStockPaumelle = paumelle.getQuantiteEnStock();
                    ModelPoignee poignee = DatabaseUtils.getPoigneeById(poigneeId);
                    prixUnitairePoignee = poignee.getPrixUnitaire();
                    quantiteEnStockPoignee = poignee.getQuantiteEnStock();
                    
                
            }

            // Fetch ModelChassis instance and retrieve prixUnitaire
            ModelChassis fixedChassis = DatabaseUtils.getChassisById(chassisTypeFixeId);
            if (fixedChassis == null) {
                JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour le châssis fixe.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            quantiteEnStockChassisFixe = fixedChassis.getQuantiteEnStock(); 
            prixUnitaireChassisFixe = fixedChassis.getPrixUnitaire();
            longueurChassisFixe = fixedChassis.getLongueurBarre();

            System.out.println("Prix unitaire du châssis fixe: " + prixUnitaireChassisFixe + " Longueur châssis Fixe: " + longueurChassisFixe);

            // Fetch ModelChassis instance and retrieve prixUnitaire
            ModelChassis ouvrantChassis = DatabaseUtils.getChassisById(chassisTypeOuvrantId);
            if (ouvrantChassis == null) {
                JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour le châssis Ouvrant.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            quantiteEnStockChassisOuvrant = ouvrantChassis.getQuantiteEnStock();
            prixUnitaireChassisOuvrant = ouvrantChassis.getPrixUnitaire();
            longueurChassisOuvrant = ouvrantChassis.getLongueurBarre();
            System.out.println("Prix unitaire du châssis Ouvrant: " + prixUnitaireChassisOuvrant + " Longueur châssis Ouvrant: " + longueurChassisOuvrant);

            ModelTypeVitre vitre = DatabaseUtils.getTypeVitreById(typeVitreId);
            if (vitre == null) {
                JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour la vitre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                 prixUnitaireVitre = vitre.getPrixUnitaire();
                // quantiteEnStockVitre = vitre.getQuantiteEnStock();
            }
           

            ModelJoint joint = DatabaseUtils.getJointById(jointId);
            if (joint == null) {
                JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour le joint", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                  prixUnitaireJoint = joint.getPrixUnitaire();
                  quantiteEnStockJoint = joint.getQuantiteEnStock();
            }
          

          
         
            ModelSerrure serrure = DatabaseUtils.getSerrureById(serrureId);
            if (serrure == null) {
                JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour la serrure", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                  prixUnitaireSerrure = serrure.getPrixUnitaire();
                  quantiteEnStockSerrure = serrure.getQuantiteEnStock();

            }
             ModelVis vis = DatabaseUtils.getVisById(visId);
            if (vis == null) {
                JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour la vis", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                   prixUnitaireVis = vis.getPrixUnitaire();
                   quantiteEnStockVis = vis.getQuantiteEnStock();

            }

            designation = buildPorteDesignation();

            List<List<JSpinner>> panelSpinners = getSpinnerValues(SpinnerContainerPanelPorte);
            for (List<JSpinner> spinners : panelSpinners) {
                if (spinners.size() == 3) {
                    largeur = BigDecimal.valueOf((Double) spinners.get(0).getValue());
                    hauteur = BigDecimal.valueOf((Double) spinners.get(1).getValue());

                    if (largeur == null || hauteur == null) {
                        // Gérer le cas où largeur ou hauteur est null
                        System.out.println("Largeur ou hauteur est null");
                        return; // ou lancez une exception, ou affichez un message d'erreur
                    } else {
                        System.out.println("Largeur " + largeur + " hauteur " + hauteur);
                    }

                    quantite = (int) spinners.get(2).getValue();

                    if (quantite == 0) {
                        JOptionPane.showMessageDialog(null, "La quantité ne peut pas être égale à 0.");
                        return;
                    }
                               if (typeOuvertureId == 1) {  // Pour une fenêtre coulissante
                    
                      nbPoignee = null;
                      nbPaumelle = null;
                      longueurJoint = longueurTotalJoint;
                        prixUnitaire = calculateUnitPricePorteCoulissante(hauteur, largeur, nombreIntermediaires,nbVentaux, prixUnitaireChassisFixe,longueurChassisFixe,prixUnitaireChassisOuvrant,longueurChassisOuvrant,prixUnitaireVitre,prixUnitaireJoint,prixUnitaireVis,prixUnitaireRoulette,prixUnitaireSerrure);

           
        } else if (typeOuvertureId == 2) { // Pour une fenêtre battante 
             nbRoulette = null;
      prixUnitaire = calculateUnitPricePorteBattante(hauteur, largeur,nombreIntermediaires, nbVentaux, prixUnitaireChassisFixe,longueurChassisFixe,prixUnitaireChassisOuvrant,longueurChassisOuvrant,prixUnitaireVitre,prixUnitaireJoint,prixUnitaireVis,prixUnitairePaumelle,prixUnitaireSerrure,prixUnitairePoignee);

        }

                tableModel.addOrUpdateRow(produitId, chassisTypeFixeId, chassisTypeOuvrantId, nbVentaux, vitrageId, typeVitreId, structureId, paumelleId, rouletteId, serrureId, poigneeId, visId, jointId, designation, prixUnitaire, quantite, hauteur, largeur,nbRoulette,nbPaumelle,nbPoignee,nbVisAssemblage,nbSerrures,longueurJoint,quantiteBarreChassisFixePorte,quantiteBarreChassisOuvrantPorte,prixChassisFixePorte,prixChassisOuvrantPorte,prixVitrePorte,prixJointPorte,prixRoulettePorte,prixPaumellePorte,prixSerrurePorte,prixPoigneePorte,prixVisAssemblagePorte,null,null,null,null,null,null,null,null,null,null,null,null);
             resetPorteFields();
                }
            }

        } catch (Exception ex) {
            Logger.getLogger(DevisCreatePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
  
public static BigDecimal calculateUnitPricePorteCoulissante(
    BigDecimal hauteur, BigDecimal largeur,int nombreIntermediaires, int nombreVentaux,
    BigDecimal prixUnitaireChassisFixe, BigDecimal longueurChassisFixe,
    BigDecimal prixUnitaireChassisOuvrant, BigDecimal longueurChassisOuvrant,
    BigDecimal prixUnitaireVitre, BigDecimal prixUnitaireJoint, BigDecimal prixUnitaireVis,
    BigDecimal prixUnitaireRoulette,
    BigDecimal prixUnitaireSerrure) {

try {
    // Châssis fixe
    BigDecimal largeurChassisFixe = largeur.multiply(new BigDecimal(2));//largeur du Porte * 2 cote haut et bas
    BigDecimal hauteurChassisFixe = hauteur.subtract(DEMI_POUCE).multiply(new BigDecimal(2));//hauteur du Porte - demi pouce
    BigDecimal longueurTotalChassisFixe = largeurChassisFixe.add(hauteurChassisFixe);//total du chassis fixe
    quantiteBarreChassisFixePorte = longueurTotalChassisFixe.divide(longueurChassisFixe, RoundingMode.CEILING);
    prixChassisFixePorte = prixUnitaireChassisFixe.multiply(quantiteBarreChassisFixePorte);

    // Logs pour débogage
    System.out.println("Largeur Chassis Fixe: " + largeurChassisFixe);
    System.out.println("Hauteur Chassis Fixe: " + hauteurChassisFixe);
    System.out.println("Quantité Barre Chassis Fixe Neccessaire: " + quantiteBarreChassisFixePorte);
    System.out.println("Prix Total Chassis Fixe porte coulissante: " + prixChassisFixePorte);

    // Châssis ouvrant
    BigDecimal largeurChassisOuvrant = largeur.divide(new BigDecimal(nombreVentaux)).subtract(QUART_POUCE).multiply(new BigDecimal(2)).multiply(new BigDecimal(nombreVentaux));
    BigDecimal hauteurChassisOuvrant = hauteur.subtract(UN_POUCE).multiply(new BigDecimal(2)).multiply(new BigDecimal(nombreVentaux));
    BigDecimal longueurTotalChassisOuvrant = largeurChassisOuvrant.add(hauteurChassisOuvrant);
    quantiteBarreChassisOuvrantPorte = longueurTotalChassisOuvrant.divide(longueurChassisOuvrant, RoundingMode.CEILING);
 prixChassisOuvrantPorte = prixUnitaireChassisOuvrant.multiply(quantiteBarreChassisOuvrantPorte);
  BigDecimal tige = largeur.subtract(SIX_POUCE).multiply(new BigDecimal(nombreIntermediaires));
   System.out.println("L tige: " + tige);
   BigDecimal prixtige = tige.divide(longueurChassisOuvrant, RoundingMode.CEILING).multiply(prixUnitaireChassisOuvrant);
  
    // Logs pour débogage
//    System.out.println("Largeur Chassis Ouvrant: " + largeurChassisOuvrant);
//    System.out.println("Hauteur Chassis Ouvrant: " + hauteurChassisOuvrant);
//    System.out.println("Quantité Barre Chassis Ouvrant Neccessaire: " + quantiteBarreChassisOuvrantPorte);
   System.out.println("Prix Total Chassis Ouvrant  porte coulissante: " + prixChassisOuvrantPorte);

    // Calcul de la vitre
    BigDecimal largeurVitre = largeur.divide(new BigDecimal(nombreVentaux)).subtract(DEUX_POUCES).divide(new BigDecimal(1000));
    BigDecimal hauteurVitre = hauteur.subtract(QUATRE_POUCES).divide(new BigDecimal(1000));
    BigDecimal dimensionVitre = largeurVitre.multiply(hauteurVitre);
    BigDecimal quantiteVitre = new BigDecimal(nombreVentaux);
   prixVitrePorte = prixUnitaireVitre.multiply(dimensionVitre).multiply(quantiteVitre);

    // Logs pour débogage
//    System.out.println("Largeur Vitre: " + largeurVitre);
//    System.out.println("Hauteur Vitre: " + hauteurVitre);
//    System.out.println("Dimension Vitre: " + dimensionVitre);
//    System.out.println("Quantite Vitre: " + quantiteVitre);
//    System.out.println("Prix Total Vitre: " + prixVitre);

    // **Roulette**
    nbRoulette= nombreVentaux * 2;
   prixRoulettePorte = prixUnitaireRoulette.multiply(new BigDecimal(nbRoulette));
    // Logs pour débogage
//    System.out.println("Nombre Roulette: " + nbRoulette);
//    System.out.println("Prix Total Roulette: " + prixRoulette);

    // **Joint**
    // BigDecimal longueurJointChassisFixe = longueurTotalChassisFixe;
    BigDecimal longueurJointVitre = hauteurVitre.add(largeurVitre).multiply(new BigDecimal(2));
    BigDecimal QuantiteJoint = quantiteVitre;
     longueurTotalJoint = longueurJointVitre.multiply(QuantiteJoint);
    prixJointPorte = prixUnitaireJoint.multiply(longueurTotalJoint);

    // **Serrure**
    nbSerrures = nombreVentaux;  // une serrure par ventail ouvrant
    prixSerrurePorte = prixUnitaireSerrure.multiply(new BigDecimal(nbSerrures));

    // Logs pour débogage
//    System.out.println("Longueur Joint Vitre: " + longueurTotalJoint);
//    System.out.println("Prix Total Joint: " + prixJoint);
//    System.out.println("Nombre Serrures: " + nbSerrures);
//    System.out.println("Prix Total Serrure: " + prixSerrure);
    // Calcul des vis
    nbVisAssemblage = nombreVentaux * 6;
    prixVisAssemblagePorte = prixUnitaireVis.multiply(new BigDecimal(nbVisAssemblage));

    // Logs pour débogage
//    System.out.println("Nombre Vis Assemblage: " + nbVisAssemblage);
//    System.out.println("Prix Total Vis: " + prixVisAssemblage);

    

    // Somme totale de tous les coûts
    BigDecimal CoutPorteCoulissante = prixChassisFixePorte.add(prixChassisOuvrantPorte)
            .add(prixVitrePorte).add(prixVisAssemblagePorte).add(prixRoulettePorte)
            .add(prixJointPorte).add(prixSerrurePorte).add(prixtige);
        BigDecimal mainOeuvre = CoutPorteCoulissante.multiply(new BigDecimal("0.25"));
        BigDecimal totalCoutPorteCoulissante = CoutPorteCoulissante.add(mainOeuvre).setScale(2, RoundingMode.CEILING);

    System.out.println("Coût Total Porte: " + totalCoutPorteCoulissante);

    return totalCoutPorteCoulissante;

} catch (Exception ex) {
    ex.printStackTrace();
    return BigDecimal.ZERO;
}
}

   public static BigDecimal calculateUnitPricePorteBattante(BigDecimal hauteur, BigDecimal largeur, int nombreIntermediaires,int nombreVentaux, BigDecimal prixUnitaireChassisFixe, BigDecimal longueurChassisFixe, BigDecimal prixUnitaireChassisOuvrant, BigDecimal longueurChassisOuvrant, BigDecimal prixUnitaireVitre, BigDecimal prixUnitaireJoint, BigDecimal prixUnitaireVis, BigDecimal prixUnitairePaumelle, BigDecimal prixUnitaireSerrure, BigDecimal prixUnitairePoignee) {
  try{   // Châssis fixe 
  BigDecimal largeurChassisFixe = largeur ;//largeur du Porte haut seuelemet
  BigDecimal hauteurChassisFixe = hauteur.subtract(DEMI_POUCE).multiply(new BigDecimal(2));//hauteur du Porte - demi pouce
  BigDecimal longueurTotalChassisFixe = largeurChassisFixe.add(hauteurChassisFixe);//total du chassis fixe
   quantiteBarreChassisFixePorte = longueurTotalChassisFixe.divide(longueurChassisFixe, RoundingMode.CEILING);
 prixChassisFixePorte = prixUnitaireChassisFixe.multiply(quantiteBarreChassisFixePorte); 

  // Logs pour débogage
//  System.out.println("Largeur Chassis Fixe: " + largeurChassisFixe);
//  System.out.println("Hauteur Chassis Fixe: " + hauteurChassisFixe);
//  System.out.println("Quantité Barre Chassis Fixe Neccessaire: " +  quantiteBarreChassisFixePorte);
//  System.out.println("Prix Total Chassis Fixe: " + prixChassisFixePorte);

   BigDecimal tige = largeur.subtract(SIX_POUCE).multiply(new BigDecimal(nombreIntermediaires));
   System.out.println("L tige: " + tige);
   BigDecimal prixtige = tige.divide(longueurChassisOuvrant, RoundingMode.CEILING).multiply(prixUnitaireChassisOuvrant);
  // Châssis ouvrant
  BigDecimal largeurChassisOuvrant = largeur.divide(new BigDecimal(nombreVentaux)).subtract(QUART_POUCE).multiply(new BigDecimal(2)).multiply(new BigDecimal(nombreVentaux));
  BigDecimal hauteurChassisOuvrant = hauteur.subtract(UN_POUCE).multiply(new BigDecimal(2)).multiply(new BigDecimal(nombreVentaux));
  BigDecimal longueurTotalChassisOuvrant = largeurChassisOuvrant.add(hauteurChassisOuvrant);
  quantiteBarreChassisOuvrantPorte = longueurTotalChassisOuvrant.divide(longueurChassisOuvrant, RoundingMode.CEILING);
  prixChassisOuvrantPorte = prixUnitaireChassisOuvrant.multiply(quantiteBarreChassisOuvrantPorte);


  // Logs pour débogage
//  System.out.println("Largeur Chassis Ouvrant: " + largeurChassisOuvrant);
//  System.out.println("Hauteur Chassis Ouvrant: " + hauteurChassisOuvrant);
//  System.out.println("Quantité Barre Chassis Ouvrant Neccessaire: " + quantiteBarreChassisOuvrantPorte);
//  System.out.println("Prix Total Chassis Ouvrant: " + prixChassisOuvrantPorte);

  // Calcul de la vitre
  BigDecimal largeurVitre = largeur.divide(new BigDecimal(nombreVentaux)).subtract(DEUX_POUCES).divide(new BigDecimal(1000));
  BigDecimal hauteurVitre = hauteur.subtract(QUATRE_POUCES).divide(new BigDecimal(1000));
  BigDecimal dimensionVitre = largeurVitre.multiply(hauteurVitre);
  BigDecimal quantiteVitre = new BigDecimal(nombreVentaux);
   prixVitrePorte = prixUnitaireVitre.multiply(dimensionVitre).multiply(quantiteVitre);

  // Logs pour débogage
//  System.out.println("Largeur Vitre: " + largeurVitre);
//  System.out.println("Hauteur Vitre: " + hauteurVitre);
//  System.out.println("Dimension Vitre: " + dimensionVitre);
//  System.out.println("Quantite Vitre: " + quantiteVitre);
//  System.out.println("Prix Total Vitre: " + prixVitrePorte);

   // **Joint**

  BigDecimal longueurJointVitre = hauteurVitre.add(largeurVitre).multiply(new BigDecimal(2));
  BigDecimal QuantiteJoint = quantiteVitre;
   longueurTotalJoint = longueurJointVitre.multiply(QuantiteJoint);
 prixJointPorte = prixUnitaireJoint.multiply(longueurTotalJoint);


  // **Serrure**
   nbSerrures = 1;  // une serrure par ventail ouvrant
   prixSerrurePorte = prixUnitaireSerrure.multiply(new BigDecimal(nbSerrures));

  // Logs pour débogage
//  System.out.println("Longueur Joint Vitre: " + longueurTotalJoint);
//  System.out.println("Prix Total Joint: " + prixJointPorte);
//  System.out.println("Nombre Serrures: " + nbSerrures);
//  System.out.println("Prix Total Serrure: " + prixSerrurePorte);
  // Calcul des vis
  nbVisAssemblage = nombreVentaux * 6;
  prixVisAssemblagePorte = prixUnitaireVis.multiply(new BigDecimal(nbVisAssemblage));

  // Logs pour débogage
//  System.out.println("Nombre Vis Assemblage: " + nbVisAssemblage);
//  System.out.println("Prix Total Vis: " + prixVisAssemblagePorte);
  
  nbPaumelle = 3* nombreVentaux;
   prixPaumellePorte = prixUnitairePaumelle.multiply(new BigDecimal(nbPaumelle));
    nbPoignee = 2;
    prixPoigneePorte = prixUnitairePoignee.multiply(new BigDecimal(nbPoignee));

  // Logs pour débogage des matériels
//  System.out.println("Nombre de  Paumelle: " + nbPaumelle);
//  System.out.println("Prix Total Paumelle: " + prixPaumellePorte);
//  System.out.println("Nombre de Poignée: " + nbPoignee);
//  System.out.println("Prix Total Poignée: " + prixPoigneePorte);

  // Somme totale de tous les coûts
  BigDecimal CoutPorteBattante = prixChassisFixePorte.add(prixChassisOuvrantPorte)
          .add(prixVitrePorte).add(prixVisAssemblagePorte)
         .add(prixPaumellePorte).add(prixPoigneePorte).add(prixJointPorte).add(prixSerrurePorte).add(prixtige);
    BigDecimal mainOeuvre = CoutPorteBattante.multiply(new BigDecimal("0.25"));
        BigDecimal totalCoutPorteBattante = CoutPorteBattante.add(mainOeuvre).setScale(2, RoundingMode.CEILING);

  System.out.println("Coût Total Porte: " + totalCoutPorteBattante);

  return totalCoutPorteBattante;

} catch (Exception ex) {
  ex.printStackTrace();
  return BigDecimal.ZERO;
}
       }
public void handleNacoSelection() {
    try {

//        isfill = (nbVentauxNacoSpinner.getValue().equals(0));
//        if (isfill) {
//            JOptionPane.showMessageDialog(null, "Le nombre d'Element pour la fenêtre doit être supérieur à 0.");
//            return; // Arrête l'exécution ici si la validation échoue
//        }
        // Ensuite, vérification des sélections dans les JComboBox
        allSelectedDefault = (typeNacoComboBox.getSelectedIndex() == 0
                || verreNacoComboBox.getSelectedIndex() == 0
                || modelesProfileCouleursNacoComboBox.getSelectedIndex() == 0
                || largeurLameNacoComboBox.getSelectedIndex() == 0
                || vitrageNacoComboBox.getSelectedIndex() == 0);

        if (allSelectedDefault) {
            JOptionPane.showMessageDialog(null, "Sélectionnez une option pour chaque critère de fenêtre.");
            return;
        }

        // Récupérer et stocker les IDs des choix sans les afficher
        String selectedNaco = (String) typeNacoComboBox.getSelectedItem();
          String selectedLargeur = (String) largeurLameNacoComboBox.getSelectedItem();
        String selectedProfileAlu = (String) modelesProfileCouleursNacoComboBox.getSelectedItem();
        //nbVentaux = (int) nbVentauxNacoSpinner.getValue();
        String selectedVitrage = (String) vitrageNacoComboBox.getSelectedItem();
        String selectedTypeVitre = (String) verreNacoComboBox.getSelectedItem();

        String[] profileAluParts = parseProfileAluString(selectedProfileAlu);
        String model = profileAluParts[0];
        String couleur = profileAluParts[1];

        String[] typeVitreParts = parseTypeVitreString(selectedTypeVitre);
        String type = typeVitreParts[0];
        int epaisseur = Integer.parseInt(typeVitreParts[1]);
       //lameVerreId = DatabaseUtils.getTypeVerreId(type, epaisseur,selectedLargeur);
       lameVerreId = DatabaseUtils.getLamVerreId(type,epaisseur,selectedLargeur);
        produitId = DatabaseUtils.getProductId(selectedNaco);
        typeOuvertureId = DatabaseUtils.getTypeOuvertureIdFromProduit(produitId);
    
        // paumelleId = DatabaseUtils.getPaumelleId(model, couleur);
        visId = DatabaseUtils.getVisId(model, couleur);
        serrureId = DatabaseUtils.getJointId(model, couleur);
        // rouletteId = DatabaseUtils.getRouletteId(model, couleur);
        vitrageId = DatabaseUtils.getIdFromChoice(selectedVitrage, "vitrage", "type", "vitrage_id");
     
        structureId = null;
        // Get IDs and check for null values
        profileAluId = DatabaseUtils.getProfileAluId(model, couleur);
        chassisTypeFixeId = DatabaseUtils.getChassisTypeFixeId(profileAluId);
        clipsId = DatabaseUtils.getClipsId(model, couleur);
       rivetteId = DatabaseUtils.getRivetteId(model, couleur);
       operateurId = DatabaseUtils.getOperateurId(model, couleur);
if (chassisTypeFixeId == -1) {
            JOptionPane.showMessageDialog(null, "Aucun châssis fixe trouvé pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        visId = DatabaseUtils.getVisId(model, couleur);
        if (visId == -1) {
            JOptionPane.showMessageDialog(null, "Aucune vis trouvée pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Logique en fonction du type d'ouverture
        if (typeOuvertureId == 3) { // Fixe
            paumelleId = null; 
            poigneeId = null;
            rouletteId = null;


        } else if (typeOuvertureId == 2) { // Battante
            rouletteId = null; // Battante -> pas de roulette
            poigneeId = DatabaseUtils.getPoigneeId(model, couleur);
            paumelleId = DatabaseUtils.getPaumelleId(model, couleur);

            if (paumelleId == null) {
                JOptionPane.showMessageDialog(null, "Aucune paumelle trouvée pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            } else if (poigneeId == null) {
                JOptionPane.showMessageDialog(null, "Aucune poignee trouvée pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                // Retrieve the unit prices for paumelle and poignee
                ModelPaumelle paumelle = DatabaseUtils.getPaumelleById(paumelleId);
                prixUnitairePaumelle = paumelle.getPrixUnitaire();
                quantiteEnStockPaumelle = paumelle.getQuantiteEnStock();
                ModelPoignee poignee = DatabaseUtils.getPoigneeById(poigneeId);
                prixUnitairePoignee = poignee.getPrixUnitaire();
                quantiteEnStockPoignee = poignee.getQuantiteEnStock();
                
            }
        }

        // Fetch ModelChassis instance and retrieve prixUnitaire
        ModelChassis fixedChassis = DatabaseUtils.getChassisById(chassisTypeFixeId);
        if (fixedChassis == null) {
            JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour le châssis fixe.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        quantiteEnStockChassisFixe = fixedChassis.getQuantiteEnStock();
        prixUnitaireChassisFixe = fixedChassis.getPrixUnitaire();
        longueurChassisFixe = fixedChassis.getLongueurBarre();

        System.out.println("Prix unitaire du châssis fixe: " + prixUnitaireChassisFixe + " Longueur châssis Fixe: " + longueurChassisFixe);
        
        
       ModelLameVerre verre = DatabaseUtils.getLameVerreById(lameVerreId);
        if (verre == null) {
            JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour le châssis fixe.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
          prixUnitaireLame = verre.getPrixUnitaire();
          largeurLame = verre.getLargeur();
          
          ModelClips clips = DatabaseUtils.getClipsById(clipsId);
        if (clips == null) {
            JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour le châssis fixe.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
          prixUnitaireClips = clips.getPrixUnitaire();
               ModelRivette rivette = DatabaseUtils.getRivetteById(rivetteId);
        if (rivette == null) {
            JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour le châssis fixe.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
          prixUnitaireRivettes = rivette.getPrixUnitaire();
                     ModelOperateur operateur = DatabaseUtils.getOperateurById(operateurId);
        if (operateur == null) {
            JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour le châssis fixe.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
          prixUnitaireOperateurBarre = operateur.getPrixUnitaire();
          
//     
//        ModelSerrure serrure = DatabaseUtils.getSerrureById(serrureId);
//        if (serrure == null) {
//            JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour la serrure", "Erreur", JOptionPane.ERROR_MESSAGE);
//            return;
//        }else{
//              prixUnitaireSerrure = serrure.getPrixUnitaire();
//              quantiteEnStockSerrure = serrure.getQuantiteEnStock();
//
//        }
         ModelVis vis = DatabaseUtils.getVisById(visId);
        if (vis == null) {
            JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour la vis", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }else{
               prixUnitaireVis = vis.getPrixUnitaire();
               quantiteEnStockVis = vis.getQuantiteEnStock();

        }

    List<List<JSpinner>> panelSpinners = getSpinnerValues(SpinnerContainerPanelNaco);
    for (List<JSpinner> spinners : panelSpinners) {
        if (spinners.size() == 3) {
          largeur =  BigDecimal.valueOf((Double) spinners.get(0).getValue());
          hauteur = BigDecimal.valueOf((Double) spinners.get(1).getValue());

if (largeur == null || hauteur == null) {
// Gérer le cas où largeur ou hauteur est null
System.out.println("Largeur ou hauteur est null");
return; // ou lancez une exception, ou affichez un message d'erreur
}else{
 System.out.println("Largeur "+largeur+" hauteur "+ hauteur);
}

            quantite = (int) spinners.get(2).getValue();

            if (quantite == 0) {
                JOptionPane.showMessageDialog(null, "La quantité ne peut pas être égale à 0.");
                return;
            }
              if (typeOuvertureId == 3 ) {  //fixe
                  quantiteBarreChassisOuvrantNaco = null;
                  nbPoignee = null;
                  nbPaumelle = null;
                  longueurJoint = null;
                  prixJointNaco = null;
                  prixPaumelleNaco=null;
                  prixSerrureNaco=null;
                  prixSerrureNaco=null;
                  prixPoigneeNaco=null;
              prixVisAssemblageNaco=null;
                  prixUnitaire = calculateUnitPriceFenetreJalousieFixe( hauteur,  largeur,  largeurLame,longueurChassisFixe,
         prixUnitaireChassisFixe,  prixUnitaireLame,  prixUnitaireOperateurBarre,
         prixUnitaireClips,  prixUnitaireRivettes,
         prixUnitaireVis);
    } else  if (typeOuvertureId == 2) { //  battante 
         nbRoulette = null;
  prixUnitaire = calculateUnitPriceFenetreJalousieBattante(hauteur, largeur, nbVentaux, prixUnitaireChassisFixe,longueurChassisFixe,prixUnitaireChassisOuvrant,longueurChassisOuvrant,prixUnitaireVitre,prixUnitaireJoint,prixUnitaireVis,prixUnitairePaumelle,prixUnitaireSerrure,prixUnitairePoignee);

    }
 designation = buildNacoDesignation();
          tableModel.addOrUpdateRow(produitId, chassisTypeFixeId, 0, nbElement, vitrageId, lameVerreId, structureId, paumelleId, rouletteId, serrureId, poigneeId, visId, jointId, designation, prixUnitaire, quantite, hauteur, largeur,nbRoulette,nbPaumelle,nbPoignee,nbVisAssemblage,nbSerrures,longueurJoint,NJT,quantiteBarreChassisOuvrantNaco,prixChassisFixeNaco,null,prixLames,prixJointNaco,null,prixPaumelleNaco,prixSerrureNaco,prixPoigneeNaco,prixVis,
                  clipsId,nbClips,prixClips,lameVerreId, quantiteLamme,prixLames,operateurId,quantiteOperateurBarre,prixOperateurBarre,rivetteId,nbRivette,prixRivettes);
            resetNacoFields();
        }
    }

} catch (Exception ex) {
    Logger.getLogger(DevisCreatePanel.class.getName()).log(Level.SEVERE, null, ex);
}
}


public static BigDecimal calculateUnitPriceFenetreJalousieFixe(
        BigDecimal hauteur, BigDecimal largeur, BigDecimal largeurLame,BigDecimal longueurChassisFixe,
        BigDecimal prixUnitaireChassisFixe, BigDecimal prixUnitaireLame, BigDecimal prixUnitaireOperateurBarre,
        BigDecimal prixUnitaireClips, BigDecimal prixUnitaireRivettes,
        BigDecimal prixUnitaireVis) {

    try {
        
    
      BigDecimal hauteurChassisFixe = hauteur.subtract(DEMI_POUCE).multiply(new BigDecimal(2));//hauteur du Porte - demi pouce
        // Nombre de lames de vitre (NL)
         nbLame = hauteurChassisFixe.divide((TROIS_DEMI_POUCE),RoundingMode.CEILING);
         roundedNL = nbLame.setScale(0, RoundingMode.CEILING);
      
        System.out.println("Nombre de lames de vitre (NL) : " + nbLame);
        
        quantiteLamme =  roundedNL.intValue();
        //  standard du jamb (MSTJ)
        BigDecimal MSTJ = nbLame.subtract(BigDecimal.ONE).multiply(TROIS_DEMI_POUCE).add(QUATRE_VINT_CINQ_POUCE).divide(longueurChassisFixe, RoundingMode.CEILING);
          //quantiteBarreChassisFixePorte = longueurTotalChassisFixe.divide(longueurChassisFixe, RoundingMode.CEILING);
        System.out.println("Mesure standard du jamb (MSTJ) : " + MSTJ);

        // Nombre de ventaux (nombre de lames pour chaque largeur).divide(longueurChassisFixe, RoundingMode.CEILING)
         nombreElement = largeur.divide(largeurLame, RoundingMode.CEILING);
         nbElement = nombreElement.intValue();
        System.out.println("Nombre de ventaux (nombreElement) : " + nbElement);

        // Nombre de jamb total (NJT)
         NJT = MSTJ.multiply(new BigDecimal("2")).multiply(nombreElement);
         
        System.out.println("Nombre de jamb total (NJT) : " + NJT);

        // Mesure standard opérateur barre total (MSTOB)
        BigDecimal MSTOB = nbLame.subtract(BigDecimal.ONE).multiply(TROIS_DEMI_POUCE).add(new BigDecimal("1")).divide(longueurChassisFixe, RoundingMode.CEILING);

        // Si NL > 12, diviser les lames entre deux opérateurs
        if (nbLame.compareTo(new BigDecimal("12")) > 0) {
            BigDecimal NLOP1 = nbLame.divide(new BigDecimal("2"), RoundingMode.CEILING);
            BigDecimal NLOP2 = nbLame.subtract(NLOP1);

            BigDecimal MSTOB1 = NLOP1.subtract(BigDecimal.ONE).multiply(TROIS_DEMI_POUCE).add(new BigDecimal("1"));
            BigDecimal MSTOB2 = NLOP2.subtract(BigDecimal.ONE).multiply(TROIS_DEMI_POUCE).add(new BigDecimal("1"));

            MSTOB = MSTOB1.add(MSTOB2);
        }
        System.out.println("Mesure standard opérateur barre total (MSTOB) : " + MSTOB);

        // Nombre de barres d'opérateurs total (NOBT)
         NOBT = MSTOB.divide(longueurChassisFixe, RoundingMode.CEILING).multiply(new BigDecimal("2")).multiply(nombreElement).divide(longueurChassisFixe, RoundingMode.CEILING);
        System.out.println("Nombre de barres d'opérateurs total (NOBT) : " + NOBT);
quantiteOperateurBarre =NOBT.intValue();
        // Nombre de clips (NC)
         NC = nbLame.multiply(new BigDecimal("2")).multiply(nombreElement);
         nbClips = NC.intValue();
        System.out.println("Nombre de clips (NC) : " + NC);

        // Nombre de rivettes (NR)
         NR = nbLame.multiply(new BigDecimal("4")).multiply(nombreElement);
        System.out.println("Nombre de rivettes (NR) : " + NR);

        
        quantiteBarreChassisFixePorte = NJT.divide(longueurChassisFixe, RoundingMode.CEILING);
    
        // Prix des jambs
         prixChassisFixeNaco = quantiteBarreChassisFixePorte.multiply(prixUnitaireChassisFixe);
        System.out.println("Prix des jambs : " + prixChassisFixeNaco);

        // Prix des lames
         prixLames = nbLame.multiply(nombreElement).multiply(prixUnitaireLame);
        System.out.println("Prix des lames : " + prixLames);

        // Prix des opérateurs barres
         prixOperateurBarre = NOBT.multiply(prixUnitaireOperateurBarre);
        System.out.println("Prix des opérateurs barres : " + prixOperateurBarre);

        // Prix des clips
         prixClips = NC.multiply(prixUnitaireClips);
        System.out.println("Prix des clips : " + prixClips);

        // Prix des rivettes
         prixRivettes = NR.multiply(prixUnitaireRivettes);
        System.out.println("Prix des rivettes : " + prixRivettes);
nbRivette = NR.intValue();
        // Prix des vis
         nbVisAssemblage = 1;
         prixVis = new BigDecimal(nbVisAssemblage).multiply(prixUnitaireVis);
        System.out.println("Prix des vis : " + prixVis);

        // **Coût total**
        BigDecimal CoutFenetreJalousie = prixChassisFixeNaco.add(prixLames)
                .add(prixOperateurBarre).add(prixClips)
                .add(prixRivettes)
                .add(prixVis);
        System.out.println("Coût Fenêtre Jalousie (avant main d'œuvre) : " + CoutFenetreJalousie);

        // Main d'œuvre (25 % du coût)
        BigDecimal mainOeuvre = CoutFenetreJalousie.multiply(new BigDecimal("0.25"));
        System.out.println("Main d'œuvre : " + mainOeuvre);

        // Total coût final
        BigDecimal totalCoutFenetreJalousie = CoutFenetreJalousie.add(mainOeuvre).setScale(2, RoundingMode.CEILING);
        System.out.println("Coût Total Fenêtre Jalousie : " + totalCoutFenetreJalousie);

        return totalCoutFenetreJalousie;

    } catch (Exception e) {
        e.printStackTrace();
        return BigDecimal.ZERO;
    }
}


private String buildNacoDesignation() {
    StringBuilder designationBuilder = new StringBuilder();
    
    String typeNaco = (String) typeNacoComboBox.getSelectedItem();
    if (typeNacoComboBox.getSelectedIndex() != 0) {
        designationBuilder.append(typeNaco).append(" ");
    }

    String modeleNaco = (String) modelesProfileCouleursNacoComboBox.getSelectedItem();
    if (modelesProfileCouleursNacoComboBox.getSelectedIndex() != 0) {
        designationBuilder.append(modeleNaco).append(" ");
    }

    if (roundedNL != null && roundedNL.compareTo(BigDecimal.ZERO) > 0) {
        // Arrondi au nombre entier supérieur
     
        designationBuilder.append(roundedNL.toPlainString()).append(" ").append(
            roundedNL.equals(BigDecimal.ONE) ? "LAME" : "LAMES").append(" ");
    } else {
        System.out.println("DEBUG: NL est null ou égal à zéro.");
    }

    String vitrageNaco = (String) vitrageNacoComboBox.getSelectedItem();
    if (vitrageNacoComboBox.getSelectedIndex() != 0) {
        designationBuilder.append(vitrageNaco).append(" ");
    }

    String typeVitreNaco = (String) verreNacoComboBox.getSelectedItem();
    if (verreNacoComboBox.getSelectedIndex() != 0) {
        designationBuilder.append(typeVitreNaco).append(" ");
    }

    System.out.println("DEBUG: Désignation finale : " + designationBuilder.toString());
    return designationBuilder.toString().trim().toUpperCase();
}

   public static BigDecimal calculateUnitPriceFenetreJalousieBattante(BigDecimal hauteur, BigDecimal largeur, int nbVentaux, BigDecimal prixUnitaireChassisFixe, BigDecimal longueurChassisFixe, BigDecimal prixUnitaireChassisOuvrant, BigDecimal longueurChassisOuvrant, BigDecimal prixUnitaireVitre, BigDecimal prixUnitaireJoint, BigDecimal prixUnitaireVis, BigDecimal prixUnitairePaumelle, BigDecimal prixUnitaireSerrure, BigDecimal prixUnitairePoignee) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    
    public void handleCloisonSelection() {
    try {

    
        // Ensuite, vérification des sélections dans les JComboBox
        allSelectedDefault = (typeCloisonComboBox.getSelectedIndex() == 0
                || typeVitreCloisonComboBox.getSelectedIndex() == 0
                || modelesProfileCouleursCloisonComboBox.getSelectedIndex() == 0
                || vitrageCloisonComboBox.getSelectedIndex() == 0);

        if (allSelectedDefault) {
            JOptionPane.showMessageDialog(null, "Sélectionnez une option pour chaque critère de fenêtre.");
            return;
        }

        // Récupérer et stocker les IDs des choix sans les afficher
        String selectedCloison = (String) typeCloisonComboBox.getSelectedItem();
        String selectedProfileAlu = (String) modelesProfileCouleursCloisonComboBox.getSelectedItem();
      
        String selectedVitrage = (String) vitrageCloisonComboBox.getSelectedItem();
        String selectedTypeVitre = (String) typeVitreCloisonComboBox.getSelectedItem();

        String[] profileAluParts = parseProfileAluString(selectedProfileAlu);
        String model = profileAluParts[0];
        String couleur = profileAluParts[1];

        String[] typeVitreParts = parseTypeVitreString(selectedTypeVitre);
        String type = typeVitreParts[0];
        int epaisseur = Integer.parseInt(typeVitreParts[1]);

        produitId = DatabaseUtils.getProductId(selectedCloison);
        typeOuvertureId = DatabaseUtils.getTypeOuvertureIdFromProduit(produitId);
      if(typeOuvertureId ==1 ){
            JOptionPane.showMessageDialog(null, "Le nombre de ventaux pour la fenêtre coulissante doit être supérieur ou égale a deux.");
            return;
            
        }
        // paumelleId = DatabaseUtils.getPaumelleId(model, couleur);
        visId = DatabaseUtils.getVisId(model, couleur);
      
     
        // rouletteId = DatabaseUtils.getRouletteId(model, couleur);
        vitrageId = DatabaseUtils.getIdFromChoice(selectedVitrage, "vitrage", "type", "vitrage_id");
        typeVitreId = DatabaseUtils.getTypeVitreId(type, epaisseur);
        structureId = null;
        // Get IDs and check for null values
        profileAluId = DatabaseUtils.getProfileAluId(model, couleur);
        chassisTypeFixeId = DatabaseUtils.getChassisTypeFixeId(profileAluId);
     
if (chassisTypeFixeId == -1) {
            JOptionPane.showMessageDialog(null, "Aucun châssis fixe trouvé pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        chassisTypeOuvrantId = DatabaseUtils.getChassisTypeOuvrantId(profileAluId);
        if (chassisTypeOuvrantId == -1) {
            JOptionPane.showMessageDialog(null, "Aucun châssis ouvrant trouvé pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        visId = DatabaseUtils.getVisId(model, couleur);
        if (visId == -1) {
            JOptionPane.showMessageDialog(null, "Aucune vis trouvée pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        jointId = DatabaseUtils.getJointId(model, couleur);
        if (jointId == -1) {
            JOptionPane.showMessageDialog(null, "Aucun joint trouvé pour le modèle '" + model + "' et la couleur '" + couleur + "'.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }


       

        // Fetch ModelChassis instance and retrieve prixUnitaire
        ModelChassis fixedChassis = DatabaseUtils.getChassisById(chassisTypeFixeId);
        if (fixedChassis == null) {
            JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour le châssis fixe.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        quantiteEnStockChassisFixe = fixedChassis.getQuantiteEnStock();
        prixUnitaireChassisFixe = fixedChassis.getPrixUnitaire();
        longueurChassisFixe = fixedChassis.getLongueurBarre();

        System.out.println("Prix unitaire du châssis fixe: " + prixUnitaireChassisFixe + " Longueur châssis Fixe: " + longueurChassisFixe);

        // Fetch ModelChassis instance and retrieve prixUnitaire
        ModelChassis ouvrantChassis = DatabaseUtils.getChassisById(chassisTypeOuvrantId);
        if (ouvrantChassis == null) {
            JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour le châssis Ouvrant.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        quantiteEnStockChassisOuvrant = ouvrantChassis.getQuantiteEnStock();
        prixUnitaireChassisOuvrant = ouvrantChassis.getPrixUnitaire();
        longueurChassisOuvrant = ouvrantChassis.getLongueurBarre();
        System.out.println("Prix unitaire du châssis Ouvrant: " + prixUnitaireChassisOuvrant + " Longueur châssis Ouvrant: " + longueurChassisOuvrant);

        ModelTypeVitre vitre = DatabaseUtils.getTypeVitreById(typeVitreId);
        if (vitre == null) {
            JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour la vitre.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }else{
             prixUnitaireVitre = vitre.getPrixUnitaire();
            // quantiteEnStockVitre = vitre.getQuantiteEnStock();
        }
       

        ModelJoint joint = DatabaseUtils.getJointById(jointId);
        if (joint == null) {
            JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour le joint", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
              prixUnitaireJoint = joint.getPrixUnitaire();
              quantiteEnStockJoint = joint.getQuantiteEnStock();
        
      

  
         ModelVis vis = DatabaseUtils.getVisById(visId);
        if (vis == null) {
            JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour la vis", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }else{
               prixUnitaireVis = vis.getPrixUnitaire();
               quantiteEnStockVis = vis.getQuantiteEnStock();

        }

      designation = buildCloisonDesignation();
   
   

    List<List<JSpinner>> panelSpinners = getSpinnerValues(SpinnerContainerPanelCloison);
    for (List<JSpinner> spinners : panelSpinners) {
        if (spinners.size() == 3) {
          largeur =  BigDecimal.valueOf((Double) spinners.get(0).getValue());
          hauteur = BigDecimal.valueOf((Double) spinners.get(1).getValue());

if (largeur == null || hauteur == null) {
// Gérer le cas où largeur ou hauteur est null
System.out.println("Largeur ou hauteur est null");
return; // ou lancez une exception, ou affichez un message d'erreur
}else{
 System.out.println("Largeur "+largeur+" hauteur "+ hauteur);
}

            quantite = (int) spinners.get(2).getValue();

            if (quantite == 0) {
                JOptionPane.showMessageDialog(null, "La quantité ne peut pas être égale à 0.");
                return;
            }
              if (typeOuvertureId == 3 ) {  // Pour une fenêtre coulissante
              
                 
                 
                  prixUnitaire = calculateUnitPriceCloisonFixe(hauteur, largeur,nombreIntermediaires, prixUnitaireChassisFixe,longueurChassisFixe,prixUnitaireChassisOuvrant,longueurChassisOuvrant,prixUnitaireVitre,prixUnitaireJoint,prixUnitaireVis);

       
    } 

         tableModel.addOrUpdateRow(produitId, chassisTypeFixeId, chassisTypeOuvrantId, nbVentaux, vitrageId, typeVitreId, structureId, null, null, null, null, visId, jointId, designation, prixUnitaire, quantite, hauteur, largeur,0,0,0,nbVisAssemblage,0,longueurTotalJoint,quantiteBarreChassisFixeCloison,quantiteBarreChassisOuvrantCloison,prixChassisFixeCloison,prixChassisOuvrantCloison,prixVitreCloison,prixJointCloison,null,null,null,null,prixVisAssemblageCloison
         ,null,null,null,null,null,null,null,null,null,null,null,null);
         resetCloisonFields();
        }
    }

} catch (Exception ex) {
    Logger.getLogger(DevisCreatePanel.class.getName()).log(Level.SEVERE, null, ex);
}
}
 public static BigDecimal calculateUnitPriceCloisonFixe(BigDecimal hauteur, BigDecimal largeur, int nombreIntermediaires,BigDecimal prixUnitaireChassisFixe, BigDecimal longueurChassisFixe, BigDecimal prixUnitaireChassisOuvrant, BigDecimal longueurChassisOuvrant, BigDecimal prixUnitaireVitre, BigDecimal prixUnitaireJoint, BigDecimal prixUnitaireVis) {
  try{   // Châssis fixe 
  BigDecimal largeurChassisFixe = largeur ;//largeur du Cloison haut seuelemet
  BigDecimal hauteurChassisFixe = hauteur.subtract(DEMI_POUCE).multiply(new BigDecimal(2));//hauteur du Cloison - demi pouce
  BigDecimal longueurTotalChassisFixe = largeurChassisFixe.add(hauteurChassisFixe);//total du chassis fixe
   quantiteBarreChassisFixeCloison = longueurTotalChassisFixe.divide(longueurChassisFixe, RoundingMode.CEILING);
 prixChassisFixeCloison = prixUnitaireChassisFixe.multiply(quantiteBarreChassisFixeCloison); 

  // Logs pour débogage
//  System.out.println("Largeur Chassis Fixe: " + largeurChassisFixe);
//  System.out.println("Hauteur Chassis Fixe: " + hauteurChassisFixe);
//  System.out.println("Quantité Barre Chassis Fixe Neccessaire: " +  quantiteBarreChassisFixeCloison);
//  System.out.println("Prix Total Chassis Fixe: " + prixChassisFixeCloison);

   BigDecimal tige = largeur.subtract(SIX_POUCE).multiply(new BigDecimal(nombreIntermediaires));
   System.out.println("L tige: " + tige);
   BigDecimal prixtige = tige.divide(longueurChassisOuvrant, RoundingMode.CEILING).multiply(prixUnitaireChassisOuvrant);
  // Châssis ouvrant
  if(nombreIntermediaires!=0){
  BigDecimal largeurChassisOuvrant = largeur.divide(new BigDecimal(nombreIntermediaires)).subtract(QUART_POUCE).multiply(new BigDecimal(2)).multiply(new BigDecimal(nombreIntermediaires));
  BigDecimal hauteurChassisOuvrant = hauteur.subtract(UN_POUCE).multiply(new BigDecimal(2)).multiply(new BigDecimal(nombreIntermediaires));
  BigDecimal longueurTotalChassisOuvrant = largeurChassisOuvrant.add(hauteurChassisOuvrant);
  quantiteBarreChassisOuvrantCloison = longueurTotalChassisOuvrant.divide(longueurChassisOuvrant, RoundingMode.CEILING);
  prixChassisOuvrantCloison = prixUnitaireChassisOuvrant.multiply(quantiteBarreChassisOuvrantCloison);
  }else {
       BigDecimal largeurChassisOuvrant = largeur.subtract(QUART_POUCE).multiply(new BigDecimal(2));
  BigDecimal hauteurChassisOuvrant = hauteur.subtract(UN_POUCE).multiply(new BigDecimal(2));
  BigDecimal longueurTotalChassisOuvrant = largeurChassisOuvrant.add(hauteurChassisOuvrant);
  quantiteBarreChassisOuvrantCloison = longueurTotalChassisOuvrant.divide(longueurChassisOuvrant, RoundingMode.CEILING);
  prixChassisOuvrantCloison = prixUnitaireChassisOuvrant.multiply(quantiteBarreChassisOuvrantCloison);
  }

  // Logs pour débogage
//  System.out.println("Largeur Chassis Ouvrant: " + largeurChassisOuvrant);
//  System.out.println("Hauteur Chassis Ouvrant: " + hauteurChassisOuvrant);
//  System.out.println("Quantité Barre Chassis Ouvrant Neccessaire: " + quantiteBarreChassisOuvrantCloison);
//  System.out.println("Prix Total Chassis Ouvrant: " + prixChassisOuvrantCloison);

  // Calcul de la vitre
   BigDecimal largeurVitre = null ;
      BigDecimal quantiteVitre = null;
  if(nombreIntermediaires!=0){
 largeurVitre = largeur.divide(new BigDecimal(nombreIntermediaires)).subtract(DEUX_POUCES).divide(new BigDecimal(1000));
   nbVisAssemblage = nombreIntermediaires * 6;
   quantiteVitre = new BigDecimal(nombreIntermediaires).add(BigDecimal.ONE);
  }else {
      largeurVitre = largeur.subtract(DEUX_POUCES).divide(new BigDecimal(1000));
     nbVisAssemblage =  6;
      quantiteVitre = BigDecimal.ONE;
  }
  BigDecimal hauteurVitre = hauteur.subtract(QUATRE_POUCES).divide(new BigDecimal(1000));
  BigDecimal dimensionVitre = largeurVitre.multiply(hauteurVitre);
 
   prixVitreCloison = prixUnitaireVitre.multiply(dimensionVitre).multiply(quantiteVitre);

  // Logs pour débogage
//  System.out.println("Largeur Vitre: " + largeurVitre);
//  System.out.println("Hauteur Vitre: " + hauteurVitre);
//  System.out.println("Dimension Vitre: " + dimensionVitre);
//  System.out.println("Quantite Vitre: " + quantiteVitre);
//  System.out.println("Prix Total Vitre: " + prixVitreCloison);

   // **Joint**
  BigDecimal longueurJointVitre = hauteurVitre.add(largeurVitre).multiply(new BigDecimal(2));
  BigDecimal QuantiteJoint = quantiteVitre;
  
   longueurTotalJoint = longueurJointVitre.multiply(QuantiteJoint);
 prixJointCloison = prixUnitaireJoint.multiply(longueurTotalJoint);



  // Logs pour débogage
//  System.out.println("Longueur Joint Vitre: " + longueurTotalJoint);
//  System.out.println("Prix Total Joint: " + prixJointCloison);
//  System.out.println("Nombre Serrures: " + nbSerrures);
//  System.out.println("Prix Total Serrure: " + prixSerrureCloison);
  // Calcul des vis

  prixVisAssemblageCloison = prixUnitaireVis.multiply(new BigDecimal(nbVisAssemblage));

  // Logs pour débogage
//  System.out.println("Nombre Vis Assemblage: " + nbVisAssemblage);
//  System.out.println("Prix Total Vis: " + prixVisAssemblageCloison);
  


  // Logs pour débogage des matériels
//  System.out.println("Nombre de  Paumelle: " + nbPaumelle);
//  System.out.println("Prix Total Paumelle: " + prixPaumelleCloison);
//  System.out.println("Nombre de Poignée: " + nbPoignee);
//  System.out.println("Prix Total Poignée: " + prixPoigneeCloison);

  // Somme totale de tous les coûts
  BigDecimal CoutCloisonBattante = prixChassisFixeCloison.add(prixChassisOuvrantCloison)
          .add(prixVitreCloison).add(prixVisAssemblageCloison).add(prixJointCloison)
       .add(prixtige);
    BigDecimal mainOeuvre = CoutCloisonBattante.multiply(new BigDecimal("0.25"));
        BigDecimal totalCoutCloisonBattante = CoutCloisonBattante.add(mainOeuvre).setScale(2, RoundingMode.CEILING);

  System.out.println("Coût Total Cloison: " + totalCoutCloisonBattante);

  return totalCoutCloisonBattante;

} catch (Exception ex) {
  ex.printStackTrace();
  return BigDecimal.ZERO;
}
       }
 
 
public static BigDecimal calculerMiseAJourQuantiteChassisFixeEnStock(ModelSousLigneDevis sousLigneDevis) throws SQLException {

  

    // Utilisez une map pour regrouper les quantités par type de profil
    Map<Integer, BigDecimal> quantitesParType = new HashMap<>();
            for (ModelSousLigneChassis souslignechassis : sousLigneDevis.getChassis()) {
                int type = souslignechassis.getChassis().getType().getTypeProfileAluId();
                if (type == 1) { // Châssis fixe
                    BigDecimal quantiteUtilisee = souslignechassis.getQuantiteUtilisee();

                    // Regroupez les quantités utilisées pour chaque type
                    quantitesParType.put(type, quantitesParType.getOrDefault(type, BigDecimal.ZERO).add(quantiteUtilisee));
                }
    }

    // Traitement des quantités regroupées
    for (Map.Entry<Integer, BigDecimal> entry : quantitesParType.entrySet()) {
        int profileId = entry.getKey();
        BigDecimal quantiteTotaleUtilisee = entry.getValue();

        // Récupérez le stock actuel
        ModelChassis fixedChassis = DatabaseUtils.getChassisById(profileId);
       BigDecimal quantiteStockChassisFixe = fixedChassis.getQuantiteEnStock();

    System.out.println("quantiteChassisFixeUtilisee: " + quantiteTotaleUtilisee);

        barresChassisFixeUtilisees = quantiteTotaleUtilisee.setScale(0, RoundingMode.CEILING);
        BigDecimal barreChassisFixeNonUtilisee = barresChassisFixeUtilisees.subtract(quantiteTotaleUtilisee);

        if (barreChassisFixeNonUtilisee.compareTo(new BigDecimal("0.5")) < 0 &&
            barreChassisFixeNonUtilisee.compareTo(BigDecimal.ZERO) > 0) {
            miseAJourQuantiteChassisFixeEnStock = quantiteStockChassisFixe.subtract(
                quantiteTotaleUtilisee.add(barreChassisFixeNonUtilisee));
        } else {
            miseAJourQuantiteChassisFixeEnStock = quantiteStockChassisFixe.subtract(quantiteTotaleUtilisee);
        }

      System.out.println("Quantité en stock après mise à jour: " + miseAJourQuantiteChassisFixeEnStock);
    }

    return miseAJourQuantiteChassisFixeEnStock;
}


public static BigDecimal calculerMiseAJourQuantiteChassisOuvrantEnStock(ModelSousLigneDevis sousLigneDevis) throws SQLException {

  
    // Utilisez une map pour regrouper les quantités par type de profil
    Map<Integer, BigDecimal> quantitesParType = new HashMap<>();
    BigDecimal miseAJourQuantiteChassisOuvrantEnStock = BigDecimal.ZERO;


            for (ModelSousLigneChassis souslignechassis : sousLigneDevis.getChassis()) {
                int type = souslignechassis.getChassis().getType().getTypeProfileAluId();
                if (type == 2) { // Châssis ouvrant
                    BigDecimal quantiteUtilisee = souslignechassis.getQuantiteUtilisee();

                    // Regroupez les quantités utilisées pour chaque type
                    quantitesParType.put(type, quantitesParType.getOrDefault(type, BigDecimal.ZERO).add(quantiteUtilisee));
                }
            }
        
    

    // Traitement des quantités regroupées
    for (Map.Entry<Integer, BigDecimal> entry : quantitesParType.entrySet()) {
        int profileId = entry.getKey();
        BigDecimal quantiteTotaleUtilisee = entry.getValue();

        // Récupérez le stock actuel
        ModelChassis ouvrantChassis = DatabaseUtils.getChassisById(profileId);
        BigDecimal quantiteStockChassisOuvrant = ouvrantChassis.getQuantiteEnStock();

       System.out.println("quantiteChassisOuvrantUtilisee: " + quantiteTotaleUtilisee);

        BigDecimal barresChassisOuvrantUtilisees = quantiteTotaleUtilisee.setScale(0, RoundingMode.CEILING);
        BigDecimal barreChassisOuvrantNonUtilisee = barresChassisOuvrantUtilisees.subtract(quantiteTotaleUtilisee);

        if (barreChassisOuvrantNonUtilisee.compareTo(new BigDecimal("0.5")) < 0 &&
            barreChassisOuvrantNonUtilisee.compareTo(BigDecimal.ZERO) > 0) {
            miseAJourQuantiteChassisOuvrantEnStock = quantiteStockChassisOuvrant.subtract(
                quantiteTotaleUtilisee.add(barreChassisOuvrantNonUtilisee));
        } else {
            miseAJourQuantiteChassisOuvrantEnStock = quantiteStockChassisOuvrant.subtract(quantiteTotaleUtilisee);
        }

       System.out.println("Quantité en stock après mise à jour: " + miseAJourQuantiteChassisOuvrantEnStock);
    }

    return miseAJourQuantiteChassisOuvrantEnStock;
}




   public static BigDecimal calculerMiseAJourQuantiteRouletteEnStock(ModelSousLigneDevis sousLigneDevis) throws SQLException {
        
    
            for (ModelSousLigneRoulette sousligneRoulette : sousLigneDevis.getRoulettes()) {
              quantiteEnStockRoulette = sousligneRoulette.getRoulette().getQuantiteEnStock();
                     nbRouletteUtilisees = sousligneRoulette.getQuantiteUtilisee();

                
            }
        
    

        BigDecimal miseAJourQuantiteRouletteEnStock;


            // La partie non utilisée n'est pas considérée comme déchet
            miseAJourQuantiteRouletteEnStock = new BigDecimal(quantiteEnStockRoulette).subtract(new BigDecimal(nbRouletteUtilisees));
     
        System.out.println("Quantité en stock roulette après mise à jour: " + miseAJourQuantiteRouletteEnStock);

        return miseAJourQuantiteRouletteEnStock;
    }
    
    
  public static BigDecimal calculerMiseAJourQuantitePaumelleEnStock(ModelSousLigneDevis sousLigneDevis) throws SQLException {
      
    

            for (ModelSousLignePaumelle souslignePaumelle : sousLigneDevis.getPaumelles()) {
              quantiteEnStockPaumelle = souslignePaumelle.getPaumelle().getQuantiteEnStock();
                     nbPaumelleUtilisees = souslignePaumelle.getQuantiteUtilisee();

                
            
        
    }
       
 
        BigDecimal miseAJourQuantitePaumelleEnStock;


            // La partie non utilisée n'est pas considérée comme déchet
            miseAJourQuantitePaumelleEnStock = new BigDecimal(quantiteEnStockPaumelle).subtract(new BigDecimal(nbPaumelleUtilisees));
     
        System.out.println("Quantité en stock paumelle après mise à jour: " + miseAJourQuantitePaumelleEnStock);

        return miseAJourQuantitePaumelleEnStock;
    }
    

    public static BigDecimal calculerMiseAJourQuantiteSerrureEnStock(ModelSousLigneDevis sousLigneDevis) throws SQLException {
    
            for (ModelSousLigneSerrure sousligneSerrure : sousLigneDevis.getSerrures()) {
              quantiteEnStockSerrure = sousligneSerrure.getSerrure().getQuantiteEnStock();
                     nbSerrureUtilisees = sousligneSerrure.getQuantiteUtilisee();

                
            
        
    }
        BigDecimal miseAJourQuantiteSerrureEnStock;


            // La partie non utilisée n'est pas considérée comme déchet
            miseAJourQuantiteSerrureEnStock = new BigDecimal(quantiteEnStockSerrure).subtract(new BigDecimal(nbSerrureUtilisees));
     
        System.out.println("Quantité en stock Serrure après mise à jour: " + miseAJourQuantiteSerrureEnStock);

        return miseAJourQuantiteSerrureEnStock;
    }
    
  public static BigDecimal calculerMiseAJourQuantiteVisEnStock(ModelSousLigneDevis sousLigneDevis) throws SQLException {
    

            for (ModelSousLigneVis sousligneVis : sousLigneDevis.getVis()) {
              quantiteEnStockVis = sousligneVis.getVis().getQuantiteEnStock();
                     nbVisUtilisees = sousligneVis.getQuantiteUtilisee();

                
            }
        
  
        BigDecimal miseAJourQuantiteVisEnStock;


            // La partie non utilisée n'est pas considérée comme déchet
            miseAJourQuantiteVisEnStock = new BigDecimal(quantiteEnStockVis).subtract(new BigDecimal(nbVisUtilisees));
     
        System.out.println("Quantité en stock Vis après mise à jour: " + miseAJourQuantiteVisEnStock);

        return miseAJourQuantiteVisEnStock;
    }
    

       public static BigDecimal calculerMiseAJourQuantiteJointEnStock(ModelSousLigneDevis sousLigneDevis) throws SQLException {
     
            for (ModelSousLigneJoint sousligneJoint : sousLigneDevis.getJoints()) {
              quantiteEnStockJoint = sousligneJoint.getJoint().getQuantiteEnStock();
                     nbJointUtilisees = sousligneJoint.getQuantiteUtilisee();

                
            }
       
       
        BigDecimal miseAJourQuantiteJointEnStock;


            // La partie non utilisée n'est pas considérée comme déchet
            miseAJourQuantiteJointEnStock = quantiteEnStockJoint.subtract(nbJointUtilisees);
     
        System.out.println("Quantité en stock Joint après mise à jour: " + miseAJourQuantiteJointEnStock);

        return miseAJourQuantiteJointEnStock;
    }
    

   public static BigDecimal calculerMiseAJourQuantitePoigneeEnStock(ModelSousLigneDevis sousLigneDevis) throws SQLException {
       
      
            for (ModelSousLignePoignee souslignePoignee : sousLigneDevis.getPoignees()) {
              quantiteEnStockPoignee = souslignePoignee.getPoignee().getQuantiteEnStock();
                     nbPoigneeUtilisees = souslignePoignee.getQuantiteUtilisee();

           
    }
 
        BigDecimal miseAJourQuantitePoigneeEnStock;


            // La partie non utilisée n'est pas considérée comme déchet
            miseAJourQuantitePoigneeEnStock = new BigDecimal(quantiteEnStockPoignee).subtract(new BigDecimal(nbPoigneeUtilisees));
     
        System.out.println("Quantité en stock Poignee après mise à jour: " + miseAJourQuantitePoigneeEnStock);

        return miseAJourQuantitePoigneeEnStock;
    }
    

    @SuppressWarnings("CallToPrintStackTrace")
    public void updateDevisToDatabase(int devisId) throws Exception {
        DevisCreatePanel.devisId = devisId;
        Connection conn = null;
        try {
            conn = DatabaseUtils.getConnection();
            conn.setAutoCommit(false); // Démarrer la transaction

            // Récupérer le nom du client sélectionné depuis JComboBox
            String selectedClientName = (String) clientCombo.getSelectedItem();
            ModelClient client = DatabaseUtils.getClientByName(selectedClientName);
            if (client == null) {
                JOptionPane.showMessageDialog(this, "Client non trouvé.");
                return;
            }

            // Récupérer la date du devis depuis un champ de texte
            String dateString = dateEditor.getText(); // Exemple : "2024-07-08"
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date parsedDate = dateFormat.parse(dateString);
            java.sql.Date dateDevis = new java.sql.Date(parsedDate.getTime());

            String selectedStatutName = (String) statutComboBox.getSelectedItem();
            int selectedStatutId = DatabaseUtils.getStatutIdByName(selectedStatutName);

            if (selectedStatutName.equals(statutComboBox.getItemAt(0)) && selectedStatutId != -1) {
                selectedStatutName = DatabaseUtils.getStatutNameByDevisId(devisId);
                selectedStatutId = DatabaseUtils.getStatutIdByName(selectedStatutName);
            }

   // Créer une liste pour les lignes de devis
        List<ModelLigneDevis> lignesDevis = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (isMainRow(tableModel, i)) {
                String designation = getStringValueAt(tableModel, i, 13);
                int totalQuantite = getIntValueAt(tableModel, i, 15);
                BigDecimal totalPrix = getBigDecimalValueAt(tableModel, i, 16);
                int produitId = (int) tableModel.getValueAt(i, 0);
  ModelProduit produit =DatabaseUtils.getProduitById(produitId);

                ModelLigneDevis ligneDevis = new ModelLigneDevis(
                        lignesDevis.size() + 1, 0, produit, designation, totalQuantite, totalPrix, new ArrayList<>()
                );

                // Sous-lignes de devis
         // Sous-lignes de devis
List<ModelSousLigneDevis> sousLignesDevis = new ArrayList<>();
List<ModelSousLigneChassis> chassisList = new ArrayList<>();
List<ModelSousLigneVitre> vitreList = new ArrayList<>();
List<ModelSousLigneJoint> jointList = new ArrayList<>();
List<ModelSousLignePaumelle> paumelleList = new ArrayList<>();
List<ModelSousLigneRoulette> rouletteList = new ArrayList<>();
List<ModelSousLigneSerrure> serrureList = new ArrayList<>();
List<ModelSousLignePoignee> poigneeList = new ArrayList<>();
List<ModelSousLigneVis> visList = new ArrayList<>();
List<ModelSousLigneClips> clipsList = new ArrayList<>();
List<ModelSousLigneLameVerre> lameVerreList = new ArrayList<>();
List<ModelSousLigneOperateur> operateurList = new ArrayList<>();
List<ModelSousLigneRivette> rivetteList = new ArrayList<>();

                     for (int j = i + 1; j < tableModel.getRowCount(); j++) {
                    if (isSubRow(tableModel, j)) {
                        // Récupération des détails de la sous-ligne
                       nombreVentaux = getIntValueAt(tableModel, j, 3);
                        Integer structureId = getIntegerValueAt(tableModel, j, 6);
                      
                        int typeOuvertureId = DatabaseUtils.getTypeOuvertureIdFromProduit(produitId);

                        if (produit == null) {
                            JOptionPane.showMessageDialog(this, "Produit non trouvé.");
                            return;
                        }

                      ModelStructure structure = (produit.getTypeProduitId() == 1) ? DatabaseUtils.getStrucureById(structureId) : null;


                        // Création de la sous-ligne
                        String subDesignation = getStringValueAt(tableModel, j, 13);
                         dims = extractDimensionsFromDesignation(subDesignation);
                         largeur = dims.largeur;
                        hauteur = dims.hauteur;
                        BigDecimal prixUnitaire = getBigDecimalValueAt(tableModel, j, 14);
                        int quantite = getIntValueAt(tableModel, j, 15);
                        BigDecimal prixTotal = getBigDecimalValueAt(tableModel, j, 16);
                           Integer nbroulette =  getIntegerValueAt(tableModel, j, 17);
     Integer nbpaumelle =   getIntegerValueAt(tableModel, j, 18);
      Integer nbpoignee =   getIntegerValueAt(tableModel, j, 19);
       Integer nbvis =   getIntegerValueAt(tableModel, j, 20);
        Integer nbserrure =   getIntegerValueAt(tableModel, j, 21);
BigDecimal longueurJoint = getBigDecimalValueAt(tableModel, j, 22);
 BigDecimal qteChassisFixe = getBigDecimalValueAt(tableModel, j, 23);
 BigDecimal qteChassisOuvrant = getBigDecimalValueAt(tableModel, j, 24);
BigDecimal prixChassisFixe  = getBigDecimalValueAt(tableModel, j, 25);
 BigDecimal  prixChassisOuvrant = getBigDecimalValueAt(tableModel, j, 26);
        BigDecimal prixVitre = getBigDecimalValueAt(tableModel, j, 27);
         BigDecimal prixJoint =getBigDecimalValueAt(tableModel, j, 28); 
         BigDecimal prixRoulette=getBigDecimalValueAt(tableModel, j, 29);
        BigDecimal prixPaumelle=getBigDecimalValueAt(tableModel, j, 30);
        BigDecimal prixSerrure=getBigDecimalValueAt(tableModel, j, 31);
        BigDecimal prixPoignee=getBigDecimalValueAt(tableModel, j, 32);
        BigDecimal prixVis=getBigDecimalValueAt(tableModel, j, 33);
     
     
                        ModelSousLigneDevis sousLigneDevis = new ModelSousLigneDevis(
                                sousLignesDevis.size() + 1, 0,  structure, nombreVentaux,
                                largeur, hauteur, quantite, prixUnitaire, prixTotal,
                                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
                        );
                   
   int chassisFixeId = getIntValueAt(tableModel, j, 1);
                        ModelChassis chassisFixe = DatabaseUtils.getChassisById(chassisFixeId);
                     

                        int chassisOuvrantId = getIntValueAt(tableModel, j, 2);
                        ModelChassis chassisOuvrant = DatabaseUtils.getChassisById(chassisOuvrantId);
                      
                        // Gestion des composants
                        // 1. Gestion des châssis

  if (produit.getTypeProduitId() == 1||produit.getTypeProduitId() == 2||produit.getTypeProduitId() == 4){
         ModelSousLigneChassis sousLigneChassis = new ModelSousLigneChassis(
        chassisList.size() + 1, 0, chassisFixe, qteChassisFixe, prixChassisFixe);
    chassisList.add(sousLigneChassis);

    ModelSousLigneChassis sousLigneChassisOuvrant = new ModelSousLigneChassis(
        chassisList.size() + 1, 0, chassisOuvrant, qteChassisOuvrant, prixChassisOuvrant);
    chassisList.add(sousLigneChassisOuvrant);
  }else if (produit.getTypeProduitId() == 3){
                        // Retrieve additional sub-line details
        Integer ClipsId = getIntegerValueAt(tableModel, j, 34);
          ModelClips clipsId= DatabaseUtils.getClipsById(ClipsId);
          Integer nbClips = getIntegerValueAt(tableModel, j, 35);
           BigDecimal prixClips = getBigDecimalValueAt(tableModel, j, 36);
            
           Integer LameVerreId = getIntegerValueAt(tableModel, j, 37);
        Integer nbLameVerre = getIntegerValueAt(tableModel, j, 38);
         BigDecimal prixLameVerre = getBigDecimalValueAt(tableModel, j, 39);
         ModelLameVerre lameId = DatabaseUtils.getLameVerreById(LameVerreId);
         
          Integer OperateurId = getIntegerValueAt(tableModel, j, 40);
          ModelOperateur operateurId = DatabaseUtils.getOperateurById(OperateurId);
        Integer nbOperateur = getIntegerValueAt(tableModel, j, 41);
         BigDecimal prixOperateur =getBigDecimalValueAt(tableModel, j, 42);
         
           Integer RivetteId = getIntegerValueAt(tableModel, j, 43);
             ModelRivette rivetteId = DatabaseUtils.getRivetteById(RivetteId);
        Integer nbRivette = getIntegerValueAt(tableModel, j, 44);
        BigDecimal prixRivette =getBigDecimalValueAt(tableModel, j, 45);
  
            ModelSousLigneChassis sousLigneChassis = new ModelSousLigneChassis(
        chassisList.size() + 1, 0, chassisFixe, qteChassisFixe, prixChassisFixe);
    chassisList.add(sousLigneChassis);
    
    
        ModelSousLigneClips clips = new ModelSousLigneClips(
      clipsList.size() + 1, 0, clipsId, nbClips, prixClips);
    clipsList.add(clips);
       ModelSousLigneOperateur operateur = new ModelSousLigneOperateur(
      operateurList.size() + 1, 0, operateurId, nbOperateur, prixOperateur);
    operateurList.add(operateur);
    
     ModelSousLigneLameVerre lameVerre = new ModelSousLigneLameVerre(
        lameVerreList.size() + 1, 0, lameId, nbLameVerre, prixLameVerre);
    lameVerreList.add(lameVerre);
 
      ModelSousLigneRivette rivette = new ModelSousLigneRivette(
      rivetteList.size() + 1, 0, rivetteId, nbRivette, prixRivette);
    rivetteList.add(rivette);
      
  }
 
                        // 2. Gestion des vitrages
                        int typeVitreId = getIntValueAt(tableModel, j, 5);
                        int typeVitrageId = getIntValueAt(tableModel, j, 4);
                        ModelTypeVitre typeVitre = DatabaseUtils.getTypeVitreById(typeVitreId, typeVitrageId);
                        if (typeVitre != null) {
                            ModelSousLigneVitre sousLigneVitre = new ModelSousLigneVitre(
                                vitreList.size() + 1,0 ,typeVitre, 10,prixVitre
                            );
                            vitreList.add(sousLigneVitre);
                        }

                        // 3. Gestion des joints
                        int jointId = getIntValueAt(tableModel, j, 11);
                        ModelJoint joint = DatabaseUtils.getJointById(jointId);
                        if (joint != null) {
                            ModelSousLigneJoint sousLigneJoint = new ModelSousLigneJoint(
                                jointList.size() + 1, 0, joint, longueurJoint,
                          prixJoint);
                            jointList.add(sousLigneJoint);
                        }

                        // 4. Gestion des paumelles (seulement pour ouvrant)
                        if (typeOuvertureId == 2) {
                            int paumelleId = getIntValueAt(tableModel, j, 7);
                            ModelPaumelle paumelle = DatabaseUtils.getPaumelleById(paumelleId);
                            if (paumelle != null) {
                                ModelSousLignePaumelle sousLignePaumelle = new ModelSousLignePaumelle(
                                    paumelleList.size() + 1, 0, paumelle, nbpaumelle, prixPaumelle
                                );
                                paumelleList.add(sousLignePaumelle);
                            }
                        }

                        // 5. Gestion des roulettes (seulement pour coulissante)
                        if (typeOuvertureId == 1) {
                            int rouletteId = getIntValueAt(tableModel, j, 8);
                            ModelRoulette roulette = DatabaseUtils.getRouletteById(rouletteId);
                            if (roulette != null) {
                                ModelSousLigneRoulette sousLigneRoulette = new ModelSousLigneRoulette(
                                    rouletteList.size() + 1, 0, roulette, nbroulette ,prixRoulette
                                );
                                rouletteList.add(sousLigneRoulette);
                            }
                        }

                        // 6. Gestion des serrures
                        int serrureId = getIntValueAt(tableModel, j, 9);
                        ModelSerrure serrure = DatabaseUtils.getSerrureById(serrureId);
                        if (serrure != null) {
                            ModelSousLigneSerrure sousLigneSerrure = new ModelSousLigneSerrure(
                                serrureList.size() + 1, 0, serrure, nbserrure, prixSerrure
                            );
                            serrureList.add(sousLigneSerrure);
                        }
                       // 7. Gestion des poignee
                        int poigneeId = getIntValueAt(tableModel, j, 10);
                        ModelPoignee poignee = DatabaseUtils.getPoigneeById(poigneeId);
                        if (poignee != null) {
                            ModelSousLignePoignee sousLignePoignee= new ModelSousLignePoignee(
                                serrureList.size() + 1, 0, poignee, nbpoignee, prixPoignee
                            );
                            poigneeList.add(sousLignePoignee);
                        }
                        // 8. Gestion des vis
                        int visId = getIntValueAt(tableModel, j, 11);
                        ModelVis vis = DatabaseUtils.getVisById(visId);
                        if (vis != null) {
                            ModelSousLigneVis sousLigneVis = new ModelSousLigneVis(
                                visList.size() + 1, 0, vis, nbvis, prixVis
                            );
                            visList.add(sousLigneVis);
                        }

                        sousLigneDevis.setChassis(chassisList);
                        sousLigneDevis.setVitres(vitreList);
                        sousLigneDevis.setJoints(jointList);
                        sousLigneDevis.setPaumelles(paumelleList);
                        sousLigneDevis.setRoulettes(rouletteList);
                        sousLigneDevis.setSerrures(serrureList);
                        sousLigneDevis.setPoignees(poigneeList);
                        sousLigneDevis.setVis(visList);
                          sousLigneDevis.setVis(visList);
                         sousLigneDevis.setLames(lameVerreList);
                          sousLigneDevis.setClips(clipsList);
                           sousLigneDevis.setOperateurs(operateurList);
                            sousLigneDevis.setRivettes(rivetteList);

                        sousLignesDevis.add(sousLigneDevis);
                    } else {
                        break;
                    }
                 }
               

                ligneDevis.setSousLignesDevis(sousLignesDevis);
                lignesDevis.add(ligneDevis);
            }
        }

           // Calcul des totaux
        BigDecimal totalHT = (BigDecimal) totalsTableModel.getValueAt(0, 0);
         BigDecimal totalRemise = (BigDecimal) totalsTableModel.getValueAt(1, 0);
        BigDecimal totalAcompte = (BigDecimal) totalsTableModel.getValueAt(2, 0);
         BigDecimal totalTva = (BigDecimal) totalsTableModel.getValueAt(3, 0);
      BigDecimal totalTTC = (BigDecimal) totalsTableModel.getValueAt(4, 0);
            if (totalHT == null) {
                JOptionPane.showMessageDialog(this, "Veuillez ajouter des produits avant de mettre a jour le devis.");
                return;
            }
          
            if (totalTTC == null) {
                JOptionPane.showMessageDialog(this, "Veuillez ajouter des produits avant de mettre a jour le devis.");
                return;
            }

            if (totalAcompte == null) {
                JOptionPane.showMessageDialog(this, "Veuillez ajouter des produits avant de mettre a jour le devis.");
                return;
            }
        
       // Récupération de la remise et de la TVA
        BigDecimal remisePercentage = new BigDecimal(
                remiseComboBox.getSelectedItem().toString().replace("%", "").trim()
        );
        BigDecimal acomptePourcentage = new BigDecimal(
                acompteComboBox.getSelectedItem().toString().replace("%", "").trim()
        );
        BigDecimal tvaPercentage = new BigDecimal(
                tvaComboBox.getSelectedItem().toString().replace("%", "").trim()
        );

            // Récupérer l'ID du devis à partir de la classe (déjà défini via setDevisId)
            // Créer l'objet ModelDevis
            ModelDevis devis = new ModelDevis(
                    devisId, // Utiliser l'ID du devis
               
                    client,
                    dateDevis,
                    totalHT, // Total HT
                    remisePercentage, // Remise
                    totalRemise,
                    acomptePourcentage,
                    totalAcompte, // Total Net HT
                    tvaPercentage, // TVA
                    totalTva,
                    totalTTC, // Total TTC
                    new ModelStatutDevis(selectedStatutId, selectedStatutName),
                    lignesDevis // Lignes de devis
            );

            // Mettre à jour le devis dans la base de données
            String result = DatabaseUtils.updateDevis(devis);

            if ("SUCCESS".equals(result)) {

                resetAllFields();
                retourAction();
                JOptionPane.showMessageDialog(this, "Le devis a été mis à jour avec succès !");
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour du devis : " + result);
            }

            // Commit transaction
            conn.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback(); // Annuler la transaction en cas d'erreur
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            throw e;
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true);
                    conn.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
   

public void saveDevisToDatabase() throws Exception {
    Connection conn = null;
    String successMessage = ""; // Message de succès
    try {
        conn = DatabaseUtils.getConnection();
        conn.setAutoCommit(false); // Démarrer la transaction

        // Récupérer le nom du client sélectionné depuis JComboBox
        String selectedClientName = (String) clientCombo.getSelectedItem();
        ModelClient client = DatabaseUtils.getClientByName(selectedClientName);
        if (client == null) {
            JOptionPane.showMessageDialog(this, "Client non trouvé.");
            return;
        }

        // Parser la date
        String dateString;
        dateString = dateEditor.getText();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date dateDevis;
        try {
            java.util.Date parsedDate = dateFormat.parse(dateString);
            dateDevis = new java.sql.Date(parsedDate.getTime());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Format de date invalide.");
            return;
        }

        // Gérer le statut
        String selectedStatutName = (String) statutComboBox.getSelectedItem();
        int selectedStatutId = DatabaseUtils.getStatutIdByName(selectedStatutName);
        if (selectedStatutId == -1) {
            JOptionPane.showMessageDialog(this, "Statut non trouvé.");
            return;
        }
        // Si "En attente" est sélectionné par défaut
        if (selectedStatutName.equals(statutComboBox.getItemAt(0))) {
            selectedStatutId = DatabaseUtils.getStatutIdByName("En attente");
            selectedStatutName = "En attente";
        }

        // Créer une liste pour les lignes de devis
        List<ModelLigneDevis> lignesDevis = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (isMainRow(tableModel, i)) {
                String designation = getStringValueAt(tableModel, i, 13);
                int totalQuantite = getIntValueAt(tableModel, i, 15);
                BigDecimal totalPrix = getBigDecimalValueAt(tableModel, i, 16);
                int produitId = (int) tableModel.getValueAt(i, 0);
                
              ModelProduit produit =DatabaseUtils.getProduitById(produitId);

                ModelLigneDevis ligneDevis = new ModelLigneDevis(
                        lignesDevis.size() + 1, 0, produit, designation, totalQuantite, totalPrix, new ArrayList<>()
                );

                // Sous-lignes de devis
              List<ModelSousLigneDevis> sousLignesDevis = new ArrayList<>();
List<ModelSousLigneChassis> chassisList = new ArrayList<>();
List<ModelSousLigneVitre> vitreList = new ArrayList<>();
List<ModelSousLigneJoint> jointList = new ArrayList<>();
List<ModelSousLignePaumelle> paumelleList = new ArrayList<>();
List<ModelSousLigneRoulette> rouletteList = new ArrayList<>();
List<ModelSousLigneSerrure> serrureList = new ArrayList<>();
List<ModelSousLignePoignee> poigneeList = new ArrayList<>();
List<ModelSousLigneVis> visList = new ArrayList<>();
List<ModelSousLigneClips> clipsList = new ArrayList<>();
List<ModelSousLigneLameVerre> lameVerreList = new ArrayList<>();
List<ModelSousLigneOperateur> operateurList = new ArrayList<>();
List<ModelSousLigneRivette> rivetteList = new ArrayList<>();

                     for (int j = i + 1; j < tableModel.getRowCount(); j++) {
                    if (isSubRow(tableModel, j)) {
                        // Récupération des détails de la sous-ligne
                       nombreVentaux = getIntValueAt(tableModel, j, 3);
                        Integer structureId = getIntegerValueAt(tableModel, j, 6);
                       
                        int typeOuvertureId = DatabaseUtils.getTypeOuvertureIdFromProduit(produitId);

                        if (produit == null) {
                            JOptionPane.showMessageDialog(this, "Produit non trouvé.");
                            return;
                        }

                      ModelStructure structure = (produit.getTypeProduitId() == 1) ? DatabaseUtils.getStrucureById(structureId) : null;


                        // Création de la sous-ligne
                        String subDesignation = getStringValueAt(tableModel, j, 13);
                         dims = extractDimensionsFromDesignation(subDesignation);
                         largeur = dims.largeur;
                        hauteur = dims.hauteur;
                        BigDecimal prixUnitaire = getBigDecimalValueAt(tableModel, j, 14);
                        int quantite = getIntValueAt(tableModel, j, 15);
                        BigDecimal prixTotal = getBigDecimalValueAt(tableModel, j, 16);
                           Integer nbroulette =  getIntegerValueAt(tableModel, j, 17);
     Integer nbpaumelle =   getIntegerValueAt(tableModel, j, 18);
      Integer nbpoignee =   getIntegerValueAt(tableModel, j, 19);
       Integer nbvis =   getIntegerValueAt(tableModel, j, 20);
        Integer nbserrure =   getIntegerValueAt(tableModel, j, 21);
BigDecimal longueurJoint = getBigDecimalValueAt(tableModel, j, 22);
 BigDecimal qteChassisFixe = getBigDecimalValueAt(tableModel, j, 23);
 BigDecimal qteChassisOuvrant = getBigDecimalValueAt(tableModel, j, 24);
BigDecimal prixChassisFixe  = getBigDecimalValueAt(tableModel, j, 25);
 BigDecimal  prixChassisOuvrant = getBigDecimalValueAt(tableModel, j, 26);
        BigDecimal prixVitre = getBigDecimalValueAt(tableModel, j, 27);
         BigDecimal prixJoint =getBigDecimalValueAt(tableModel, j, 28); 
         BigDecimal prixRoulette=getBigDecimalValueAt(tableModel, j, 29);
        BigDecimal prixPaumelle=getBigDecimalValueAt(tableModel, j, 30);
        BigDecimal prixSerrure=getBigDecimalValueAt(tableModel, j, 31);
        BigDecimal prixPoignee=getBigDecimalValueAt(tableModel, j, 32);
        BigDecimal prixVis=getBigDecimalValueAt(tableModel, j, 33);
                        
                          // Retrieve additional sub-line details
     
  
     
                        ModelSousLigneDevis sousLigneDevis = new ModelSousLigneDevis(
                                sousLignesDevis.size() + 1, 0,  structure, nombreVentaux,
                                largeur, hauteur, quantite, prixUnitaire, prixTotal,
                                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),
                                new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>()
                        );
                   
   int chassisFixeId = getIntValueAt(tableModel, j, 1);
                        ModelChassis chassisFixe = DatabaseUtils.getChassisById(chassisFixeId);
                     

                        int chassisOuvrantId = getIntValueAt(tableModel, j, 2);
                        ModelChassis chassisOuvrant = DatabaseUtils.getChassisById(chassisOuvrantId);
                      
                        // Gestion des composants
                        // 1. Gestion des châssis

  if (produit.getTypeProduitId() == 1||produit.getTypeProduitId() == 2||produit.getTypeProduitId() == 4){
         ModelSousLigneChassis sousLigneChassis = new ModelSousLigneChassis(
        chassisList.size() + 1, 0, chassisFixe, qteChassisFixe, prixChassisFixe);
    chassisList.add(sousLigneChassis);

    ModelSousLigneChassis sousLigneChassisOuvrant = new ModelSousLigneChassis(
        chassisList.size() + 1, 0, chassisOuvrant, qteChassisOuvrant, prixChassisOuvrant);
    chassisList.add(sousLigneChassisOuvrant);
    
    
                        // 2. Gestion des vitrages
                        int typeVitreId = getIntValueAt(tableModel, j, 5);
                        int typeVitrageId = getIntValueAt(tableModel, j, 4);
                        ModelTypeVitre typeVitre = DatabaseUtils.getTypeVitreById(typeVitreId, typeVitrageId);
                        if (typeVitre != null) {
                            ModelSousLigneVitre sousLigneVitre = new ModelSousLigneVitre(
                                vitreList.size() + 1,0 ,typeVitre, 10,prixVitre
                            );
                            vitreList.add(sousLigneVitre);
                        }

                        // 3. Gestion des joints
                        int jointId = getIntValueAt(tableModel, j, 11);
                        ModelJoint joint = DatabaseUtils.getJointById(jointId);
                        if (joint != null) {
                            ModelSousLigneJoint sousLigneJoint = new ModelSousLigneJoint(
                                jointList.size() + 1, 0, joint, longueurJoint,
                          prixJoint);
                            jointList.add(sousLigneJoint);
                        }
  }
  if (produit.getTypeProduitId() == 3){
        Integer ClipsId = getIntegerValueAt(tableModel, j, 34);
          ModelClips clipId = DatabaseUtils.getClipsById(ClipsId);
          Integer nbClips = getIntegerValueAt(tableModel, j, 35);
           BigDecimal prixClips = getBigDecimalValueAt(tableModel, j, 36);
            
           Integer LameVerreId = getIntegerValueAt(tableModel, j, 37);
        Integer nbLameVerre = getIntegerValueAt(tableModel, j, 38);
         BigDecimal prixLameVerre = getBigDecimalValueAt(tableModel, j, 39);
         ModelLameVerre lameId = DatabaseUtils.getLameVerreById(LameVerreId);
         
          Integer OperateurId = getIntegerValueAt(tableModel, j, 40);
          ModelOperateur operateurId = DatabaseUtils.getOperateurById(OperateurId);
        Integer nbOperateur = getIntegerValueAt(tableModel, j, 41);
         BigDecimal prixOperateur =getBigDecimalValueAt(tableModel, j, 42);
         
           Integer RivetteId = getIntegerValueAt(tableModel, j, 43);
             ModelRivette rivetteId = DatabaseUtils.getRivetteById(RivetteId);
        Integer nbRivette = getIntegerValueAt(tableModel, j, 44);
        BigDecimal prixRivette =getBigDecimalValueAt(tableModel, j, 45);
            ModelSousLigneChassis sousLigneChassis = new ModelSousLigneChassis(
        chassisList.size() + 1, 0, chassisFixe, qteChassisFixe, prixChassisFixe);
    chassisList.add(sousLigneChassis);
    
        ModelSousLigneClips clips = new ModelSousLigneClips(
      clipsList.size() + 1, 0, clipId, nbClips, prixClips);
    clipsList.add(clips);
       ModelSousLigneOperateur operateur = new ModelSousLigneOperateur(
      operateurList.size() + 1, 0, operateurId, nbOperateur, prixOperateur);
    operateurList.add(operateur);
    
     ModelSousLigneLameVerre lameVerre = new ModelSousLigneLameVerre(
        lameVerreList.size() + 1, 0, lameId, nbLameVerre, prixLameVerre);
    lameVerreList.add(lameVerre);
 
      ModelSousLigneRivette rivette = new ModelSousLigneRivette(
      rivetteList.size() + 1, 0, rivetteId, nbRivette, prixRivette);
    rivetteList.add(rivette);
      
  }


                        // 4. Gestion des paumelles (seulement pour ouvrant)
                        if (typeOuvertureId == 2) {
                            int paumelleId = getIntValueAt(tableModel, j, 7);
                            ModelPaumelle paumelle = DatabaseUtils.getPaumelleById(paumelleId);
                            if (paumelle != null) {
                                ModelSousLignePaumelle sousLignePaumelle = new ModelSousLignePaumelle(
                                    paumelleList.size() + 1, 0, paumelle, nbpaumelle, prixPaumelle
                                );
                                paumelleList.add(sousLignePaumelle);
                            }
                        }

                        // 5. Gestion des roulettes (seulement pour coulissante)
                        if (typeOuvertureId == 1) {
                            int rouletteId = getIntValueAt(tableModel, j, 8);
                            ModelRoulette roulette = DatabaseUtils.getRouletteById(rouletteId);
                            if (roulette != null) {
                                ModelSousLigneRoulette sousLigneRoulette = new ModelSousLigneRoulette(
                                    rouletteList.size() + 1, 0, roulette, nbroulette ,prixRoulette
                                );
                                rouletteList.add(sousLigneRoulette);
                            }
                        }

                        // 6. Gestion des serrures
                        int serrureId = getIntValueAt(tableModel, j, 9);
                        ModelSerrure serrure = DatabaseUtils.getSerrureById(serrureId);
                        if (serrure != null) {
                            ModelSousLigneSerrure sousLigneSerrure = new ModelSousLigneSerrure(
                                serrureList.size() + 1, 0, serrure, nbserrure, prixSerrure
                            );
                            serrureList.add(sousLigneSerrure);
                        }
                       // 7. Gestion des poignee
                        int poigneeId = getIntValueAt(tableModel, j, 10);
                        ModelPoignee poignee = DatabaseUtils.getPoigneeById(poigneeId);
                        if (poignee != null) {
                            ModelSousLignePoignee sousLignePoignee= new ModelSousLignePoignee(
                                serrureList.size() + 1, 0, poignee, nbpoignee, prixPoignee
                            );
                            poigneeList.add(sousLignePoignee);
                        }
                        // 8. Gestion des vis
                        int visId = getIntValueAt(tableModel, j, 11);
                        ModelVis vis = DatabaseUtils.getVisById(visId);
                        if (vis != null) {
                            ModelSousLigneVis sousLigneVis = new ModelSousLigneVis(
                                visList.size() + 1, 0, vis, nbvis, prixVis
                            );
                            visList.add(sousLigneVis);
                        }

                        sousLigneDevis.setChassis(chassisList);
                        sousLigneDevis.setVitres(vitreList);
                        sousLigneDevis.setJoints(jointList);
                        sousLigneDevis.setPaumelles(paumelleList);
                        sousLigneDevis.setRoulettes(rouletteList);
                        sousLigneDevis.setSerrures(serrureList);
                        sousLigneDevis.setPoignees(poigneeList);
                        sousLigneDevis.setVis(visList);
                         sousLigneDevis.setLames(lameVerreList);
                          sousLigneDevis.setClips(clipsList);
                           sousLigneDevis.setOperateurs(operateurList);
                            sousLigneDevis.setRivettes(rivetteList);

                        sousLignesDevis.add(sousLigneDevis);
                    } else {
                        break;
                    }
                 }
               
               

                ligneDevis.setSousLignesDevis(sousLignesDevis);
                lignesDevis.add(ligneDevis);
            }
        }

        if (lignesDevis.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Veuillez ajouter des produits avant d'enregistrer le devis.");
            return;
        }

   // Calcul des totaux
 BigDecimal totalHT = (BigDecimal) totalsTableModel.getValueAt(0, 0);
         BigDecimal totalRemise = (BigDecimal) totalsTableModel.getValueAt(1, 0);
        BigDecimal totalAcompte = (BigDecimal) totalsTableModel.getValueAt(2, 0);
         BigDecimal totalTva = (BigDecimal) totalsTableModel.getValueAt(3, 0);
      BigDecimal totalTTC = (BigDecimal) totalsTableModel.getValueAt(4, 0);

        // Récupération de la remise et de la TVA
        BigDecimal remisePercentage = new BigDecimal(
                remiseComboBox.getSelectedItem().toString().replace("%", "").trim()
        );
        BigDecimal acomptePourcentage = new BigDecimal(
                acompteComboBox.getSelectedItem().toString().replace("%", "").trim()
        );
             BigDecimal tvaPercentage = new BigDecimal(
                tvaComboBox.getSelectedItem().toString().replace("%", "").trim()
        );

        // Création de l'objet devis
        int devisId = DatabaseUtils.getNextDevisId();
        ModelDevis devis = new ModelDevis(
                devisId, client, dateDevis, totalHT, remisePercentage,totalRemise,acomptePourcentage, totalAcompte, tvaPercentage,totalTva, totalTTC,
                new ModelStatutDevis(selectedStatutId, selectedStatutName), lignesDevis
        );

        // Sauvegarder le devis dans la base de données
        String result = DatabaseUtils.addDevis(devis);
        if ("SUCCESS".equals(result)) {
            successMessage = "Le devis a été enregistré avec succès.";
            
            if (selectedStatutName.equalsIgnoreCase("Accepté")) {
                  // Appeler la méthode de conversion avec paiement d'acompte
//            String convertResult = DatabaseUtils.convertDevisToCommande(devisId, montantPaye, modePaiement);
//                successMessage += " " + ("SUCCESS".equals(convertResult) ? "et converti en commande." : "mais une erreur est survenue lors de la conversion en commande.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement du devis.");
            return;
        }

        // Commit de la transaction
        conn.commit();
        JOptionPane.showMessageDialog(this, successMessage);
        resetAllFields(); // Réinitialiser les champs
           retourAction();

    } catch (SQLException | ParseException e) {
        if (conn != null) {
            conn.rollback(); // Annuler la transaction en cas d'erreur
        }
        JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement du devis.");
        e.printStackTrace();
    } finally {
        if (conn != null) {
            conn.setAutoCommit(true);
            conn.close();
        }
    }
}

    public static void removeSousLigne(JPanel sousLignePanel) {
        if (sousLignePanel != null && sousLignePanel.getParent() != null) {
            Container parent = sousLignePanel.getParent();
            parent.remove(sousLignePanel);
            parent.revalidate();
            parent.repaint();
        }
    }

    private JPanel createOptionsAndInfoPanel() {
        JPanel optionsAndInfoPanel = new JPanel(new GridLayout(1, 2, 2, 2));
        JPanel dateClientPanel = createDateClientPanel();
        JPanel optionsPanel = createOptionsPanel();
        optionsAndInfoPanel.add(optionsPanel);
        optionsAndInfoPanel.add(dateClientPanel);
        optionsAndInfoPanel.setBorder(BorderFactory.createEmptyBorder(30, 0, 0, 0));
        return optionsAndInfoPanel;
    }

    private JPanel createOptionsPanel() {
        JPanel optionsPanel = new JPanel(new GridLayout(4, 1, 0, 0)); // Une colonne, lignes ajustables
        optionsPanel.setBorder(BorderFactory.createTitledBorder("Options"));

        // Checkboxes for additional options
        moustiquairePorteCheckBox = new JCheckBox("Moustiquaire");
        intermediairePorteCheckBox = new JCheckBox("Intermédiaire");
        impostePorteCheckBox = new JCheckBox("Imposte");
       intermediairePorteCheckBox.addActionListener(e -> {
    if (intermediairePorteCheckBox.isSelected()) {
        ouvrirDialogueIntermediaire(); // Ouvrir le dialogue pour saisir le nombre d'intermédiaires
    } else {
        nombreIntermediaires = 0; // Réinitialiser si la checkbox est décochée
    }
});

        // Ajouter les checkboxes dans le panel
        optionsPanel.add(moustiquairePorteCheckBox);
        optionsPanel.add(intermediairePorteCheckBox);
        optionsPanel.add(impostePorteCheckBox);
   

        return optionsPanel;
    }

    private JPanel createDateClientPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Info General"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(2, 5, 5, 5); // Add 5 pixels of spacing around each component

        // Première ligne : Date et Choix de la date
        JPanel firstRow = new JPanel(new GridLayout(1, 2, 2, 5));
        addField(firstRow, "Date:", createDateField()); // Méthode pour créer le champ de texte de la date
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(firstRow, gbc);

        // Deuxième ligne : Client et Ajouter Client
        JPanel secondRow = new JPanel(new GridLayout(1, 2, 2, 2));
        addField(secondRow, "Client:", createClientComboBox()); // Méthode pour créer le JComboBox des clients
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(secondRow, gbc);

        // Troisième ligne :  Statut du devis
        JPanel thirdRow = new JPanel(new GridLayout(1, 2));
        addField(thirdRow, "Statut:", createStatutComboBox()); // Méthode pour créer le JXComboBox des statuts
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(1, 5, 5, 5); // Add 10 pixels of spacing above this component
        panel.add(thirdRow, gbc);

        return panel;
    }

    private JPanel createSpinnerPortePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        SpinnerContainerPanelPorte = new JPanel();
        SpinnerContainerPanelPorte.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));

        addSousLignePorte();

        scrollPaneSouslignePorte = new JScrollPane(SpinnerContainerPanelPorte);
        scrollPaneSouslignePorte.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPaneSouslignePorte.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneSouslignePorte.setPreferredSize(new Dimension(230, 100));
        scrollPaneSouslignePorte.setBorder(BorderFactory.createEmptyBorder());

        panel.add(scrollPaneSouslignePorte, BorderLayout.CENTER);

        // Boutons d'ajout et de suppression
        JPanel buttonsPanel = new JPanel(new BorderLayout());
       JButton addSousLignePorteButton = new JButton();
        addSousLignePorteButton.setIcon(new ImageIcon(getClass().getResource("/Icon/icons8-add-16.png")));
        addSousLignePorteButton.setHorizontalTextPosition(SwingConstants.CENTER);

        // Ajouter un écouteur d'événements pour le bouton
        addSousLignePorteButton.addActionListener((ActionEvent e) -> {

            addSousLignePorte();
        });

        Dimension buttonSize = new Dimension(16, 16);
        addSousLignePorteButton.setPreferredSize(buttonSize);

        buttonsPanel.add(addSousLignePorteButton, BorderLayout.EAST);
        scrollPaneSouslignePorte.setColumnHeaderView(buttonsPanel);

        return panel;
    }

    private JPanel createSpinnerFenetrePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        SpinnerContainerPanelFenetre = new JPanel();
         SpinnerContainerPanelFenetre.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              SpinnerContainerPanelFenetre.requestFocusInWindow();
             
               
            }
        });
        SpinnerContainerPanelFenetre.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));

        addSousLigneFenetre();

        scrollPaneSousligneFenetre = new JScrollPane(SpinnerContainerPanelFenetre);
        scrollPaneSousligneFenetre.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPaneSousligneFenetre.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneSousligneFenetre.setPreferredSize(new Dimension(230, 100));
        scrollPaneSousligneFenetre.setBorder(BorderFactory.createEmptyBorder());
          scrollPaneSousligneFenetre.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                scrollPaneSousligneFenetre.requestFocusInWindow();
            }
        });
            panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                panel.requestFocusInWindow();
            }
        });

        panel.add(scrollPaneSousligneFenetre, BorderLayout.CENTER);

        // Boutons d'ajout et de suppression
        JPanel buttonsPanel = new JPanel(new BorderLayout());
        JButton addSousLigneButton = new JButton();
        addSousLigneButton.setIcon(new ImageIcon(getClass().getResource("/Icon/icons8-add-16.png")));
        // addSousLigneButton .setBackground(Color.decode("#DDDDDD"));
        addSousLigneButton.setHorizontalTextPosition(SwingConstants.CENTER);
        addSousLigneButton.addActionListener((ActionEvent e) -> {
            addSousLigneFenetre();
        });

        Dimension buttonSize = new Dimension(16, 16);
        addSousLigneButton.setPreferredSize(buttonSize);

        buttonsPanel.add(addSousLigneButton, BorderLayout.EAST);
        scrollPaneSousligneFenetre.setColumnHeaderView(buttonsPanel);

        return panel;
    }
private JPanel createSpinnerNacoPanel() {
    JPanel panel = new JPanel(new BorderLayout());

    SpinnerContainerPanelNaco = new JPanel();
     SpinnerContainerPanelNaco.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          SpinnerContainerPanelNaco.requestFocusInWindow();
         
           
        }
    });
    SpinnerContainerPanelNaco.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));

    addSousLigneNaco();

    scrollPaneSousligneNaco = new JScrollPane(SpinnerContainerPanelNaco);
    scrollPaneSousligneNaco.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    scrollPaneSousligneNaco.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPaneSousligneNaco.setPreferredSize(new Dimension(230, 100));
    scrollPaneSousligneNaco.setBorder(BorderFactory.createEmptyBorder());
      scrollPaneSousligneNaco.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            scrollPaneSousligneNaco.requestFocusInWindow();
        }
    });
        panel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            panel.requestFocusInWindow();
        }
    });

    panel.add(scrollPaneSousligneNaco, BorderLayout.CENTER);

    // Boutons d'ajout et de suppression
    JPanel buttonsPanel = new JPanel(new BorderLayout());
    JButton addSousLigneButton = new JButton();
    addSousLigneButton.setIcon(new ImageIcon(getClass().getResource("/Icon/icons8-add-16.png")));
    // addSousLigneButton .setBackground(Color.decode("#DDDDDD"));
    addSousLigneButton.setHorizontalTextPosition(SwingConstants.CENTER);
    addSousLigneButton.addActionListener((ActionEvent e) -> {
        addSousLigneNaco();
    });

    Dimension buttonSize = new Dimension(16, 16);
    addSousLigneButton.setPreferredSize(buttonSize);

    buttonsPanel.add(addSousLigneButton, BorderLayout.EAST);
    scrollPaneSousligneNaco.setColumnHeaderView(buttonsPanel);

    return panel;
}
private JPanel createSpinnerCloisonPanel() {
    JPanel panel = new JPanel(new BorderLayout());

    SpinnerContainerPanelCloison = new JPanel();
     SpinnerContainerPanelCloison.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          SpinnerContainerPanelCloison.requestFocusInWindow();
         
           
        }
    });
    SpinnerContainerPanelCloison.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));

    addSousLigneCloison();

    scrollPaneSousligneCloison = new JScrollPane(SpinnerContainerPanelCloison);
    scrollPaneSousligneCloison.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
    scrollPaneSousligneCloison.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    scrollPaneSousligneCloison.setPreferredSize(new Dimension(230, 100));
    scrollPaneSousligneCloison.setBorder(BorderFactory.createEmptyBorder());
      scrollPaneSousligneCloison.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            scrollPaneSousligneCloison.requestFocusInWindow();
        }
    });
        panel.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            panel.requestFocusInWindow();
        }
    });

    panel.add(scrollPaneSousligneCloison, BorderLayout.CENTER);

    // Boutons d'ajout et de suppression
    JPanel buttonsPanel = new JPanel(new BorderLayout());
    JButton addSousLigneButton = new JButton();
    addSousLigneButton.setIcon(new ImageIcon(getClass().getResource("/Icon/icons8-add-16.png")));
    // addSousLigneButton .setBackground(Color.decode("#DDDDDD"));
    addSousLigneButton.setHorizontalTextPosition(SwingConstants.CENTER);
    addSousLigneButton.addActionListener((ActionEvent e) -> {
        addSousLigneCloison();
    });

    Dimension buttonSize = new Dimension(16, 16);
    addSousLigneButton.setPreferredSize(buttonSize);

    buttonsPanel.add(addSousLigneButton, BorderLayout.EAST);
    scrollPaneSousligneCloison.setColumnHeaderView(buttonsPanel);

    return panel;
}

    private JPanel createSousLignePortePanel() {
        JPanel createsousLignePanel = new JPanel(new GridLayout(3, 2, 0, 2));
       
        largeurPorteSpinner = configureSpinnerBigDecimal();
        hauteurPorteSpinner = configureSpinnerBigDecimal();
        quantitePorteSpinner = configureSpinnerInteger();

        addField(createsousLignePanel, "Largeur(mm):", largeurPorteSpinner);
        addField(createsousLignePanel, "Hauteur(mm):", hauteurPorteSpinner);
        addField(createsousLignePanel, "Quantité:", quantitePorteSpinner);

        return createsousLignePanel;
    }

    private JPanel createSousLigneFenetrePanel() {
        JPanel sousLignePanel = new JPanel(new GridLayout(3, 2, 0, 2));
        //sousLignePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Espace interne
        largeurFenetreSpinner = configureSpinnerBigDecimal();
        hauteurFenetreSpinner = configureSpinnerBigDecimal();
        quantiteFenetreSpinner = configureSpinnerInteger();

        addField(sousLignePanel, "Largeur(mm):", largeurFenetreSpinner);
        addField(sousLignePanel, "Hauteur(mm):", hauteurFenetreSpinner);
        addField(sousLignePanel, "Quantité:", quantiteFenetreSpinner);

        return sousLignePanel;
    }
   private JPanel createSousLigneNacoPanel() {
        JPanel sousLignePanel = new JPanel(new GridLayout(3, 2, 0, 2));
        //sousLignePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Espace interne
        largeurNacoSpinner = configureSpinnerBigDecimal();
        hauteurNacoSpinner = configureSpinnerBigDecimal();
        quantiteNacoSpinner = configureSpinnerInteger();

        addField(sousLignePanel, "Largeur(mm):", largeurNacoSpinner);
        addField(sousLignePanel, "Hauteur(mm):", hauteurNacoSpinner);
        addField(sousLignePanel, "Quantité:", quantiteNacoSpinner);

        return sousLignePanel;
    }
   private JPanel createSousLigneCloisonPanel() {
        JPanel sousLignePanel = new JPanel(new GridLayout(3, 2, 0, 2));
        //sousLignePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Espace interne
        largeurCloisonSpinner = configureSpinnerBigDecimal();
        hauteurCloisonSpinner = configureSpinnerBigDecimal();
        quantiteCloisonSpinner = configureSpinnerInteger();

        addField(sousLignePanel, "Largeur(mm):", largeurCloisonSpinner);
        addField(sousLignePanel, "Hauteur(mm):", hauteurCloisonSpinner);
        addField(sousLignePanel, "Quantité:", quantiteCloisonSpinner);

        return sousLignePanel;
    }

    private void addSousLigneFenetre() {
        // Créer un panneau pour la sous-ligne avec un bouton de suppression
        JPanel sousLignePanel = new JPanel(new BorderLayout());

        // Créer et ajouter la sous-ligne
        JPanel sousLigneContent = createSousLigneFenetrePanel();

        sousLignePanel.add(sousLigneContent, BorderLayout.NORTH);

        JPanel sousLignePanelContenaire = new JPanel(new BorderLayout(0, 5));
        sousLignePanelContenaire.add(sousLignePanel, BorderLayout.NORTH);

        // Créer et ajouter le bouton de suppression pour cette sous-ligne
        JButton removeSousLigneButton = new JButton();
        removeSousLigneButton.setIcon(new ImageIcon(getClass().getResource("/Icon/minus.png")));
        Dimension buttonSize = new Dimension(16, 16);
        removeSousLigneButton.setPreferredSize(buttonSize);
        removeSousLigneButton.addActionListener((ActionEvent e) -> {
            removeSousLigne(sousLignePanelContenaire);
            removeButtonsFenetre.remove(removeSousLigneButton);
            updateRemoveButtonsVisibilityFenetre();
            updateScrollBarPolicyFenetre();
            SpinnerContainerPanelFenetre.requestFocusInWindow();
        });

        // Rendre le bouton de suppression invisible si c'est la première sous-ligne
        if (removeButtonsFenetre.size() == 1) {
            removeSousLigneButton.setVisible(false);
        }

        sousLignePanelContenaire.add(removeSousLigneButton, BorderLayout.EAST);

        // Ajouter la sous-ligne au conteneur
        SpinnerContainerPanelFenetre.add(sousLignePanelContenaire);
        removeButtonsFenetre.add(removeSousLigneButton);

        // Revalider le panneau pour afficher les modifications
        SpinnerContainerPanelFenetre.revalidate();
        SpinnerContainerPanelFenetre.repaint();

        updateRemoveButtonsVisibilityFenetre();
        updateScrollBarPolicyFenetre();
        // Animer le défilement vers la fin à droite
        SwingUtilities.invokeLater(() -> animateScrollToRightFenetre());

    }
  private void addSousLigneNaco() {
        // Créer un panneau pour la sous-ligne avec un bouton de suppression
        JPanel sousLignePanel = new JPanel(new BorderLayout());

        // Créer et ajouter la sous-ligne
        JPanel sousLigneContent = createSousLigneNacoPanel();

        sousLignePanel.add(sousLigneContent, BorderLayout.NORTH);

        JPanel sousLignePanelContenaire = new JPanel(new BorderLayout(0, 5));
        sousLignePanelContenaire.add(sousLignePanel, BorderLayout.NORTH);

        // Créer et ajouter le bouton de suppression pour cette sous-ligne
        JButton removeSousLigneButton = new JButton();
        removeSousLigneButton.setIcon(new ImageIcon(getClass().getResource("/Icon/minus.png")));
        Dimension buttonSize = new Dimension(16, 16);
        removeSousLigneButton.setPreferredSize(buttonSize);
        removeSousLigneButton.addActionListener((ActionEvent e) -> {
            removeSousLigne(sousLignePanelContenaire);
            removeButtonsNaco.remove(removeSousLigneButton);
            updateRemoveButtonsVisibilityNaco();
            updateScrollBarPolicyNaco();
            SpinnerContainerPanelNaco.requestFocusInWindow();
        });

        // Rendre le bouton de suppression invisible si c'est la première sous-ligne
        if (removeButtonsNaco.size() == 1) {
            removeSousLigneButton.setVisible(false);
        }

        sousLignePanelContenaire.add(removeSousLigneButton, BorderLayout.EAST);

        // Ajouter la sous-ligne au conteneur
        SpinnerContainerPanelNaco.add(sousLignePanelContenaire);
        removeButtonsNaco.add(removeSousLigneButton);

        // Revalider le panneau pour afficher les modifications
        SpinnerContainerPanelNaco.revalidate();
        SpinnerContainerPanelNaco.repaint();

        updateRemoveButtonsVisibilityNaco();
        updateScrollBarPolicyNaco();
        // Animer le défilement vers la fin à droite
        SwingUtilities.invokeLater(() -> animateScrollToRightNaco());

    }
    private void addSousLigneCloison() {
        // Créer un panneau pour la sous-ligne avec un bouton de suppression
        JPanel sousLignePanel = new JPanel(new BorderLayout());

        // Créer et ajouter la sous-ligne
        JPanel sousLigneContent = createSousLigneCloisonPanel();

        sousLignePanel.add(sousLigneContent, BorderLayout.NORTH);

        JPanel sousLignePanelContenaire = new JPanel(new BorderLayout(0, 5));
        sousLignePanelContenaire.add(sousLignePanel, BorderLayout.NORTH);

        // Créer et ajouter le bouton de suppression pour cette sous-ligne
        JButton removeSousLigneButton = new JButton();
        removeSousLigneButton.setIcon(new ImageIcon(getClass().getResource("/Icon/minus.png")));
        Dimension buttonSize = new Dimension(16, 16);
        removeSousLigneButton.setPreferredSize(buttonSize);
        removeSousLigneButton.addActionListener((ActionEvent e) -> {
            removeSousLigne(sousLignePanelContenaire);
            removeButtonsCloison.remove(removeSousLigneButton);
            updateRemoveButtonsVisibilityCloison();
            updateScrollBarPolicyCloison();
            SpinnerContainerPanelCloison.requestFocusInWindow();
        });

        // Rendre le bouton de suppression invisible si c'est la première sous-ligne
        if (removeButtonsCloison.size() == 1) {
            removeSousLigneButton.setVisible(false);
        }

        sousLignePanelContenaire.add(removeSousLigneButton, BorderLayout.EAST);

        // Ajouter la sous-ligne au conteneur
        SpinnerContainerPanelCloison.add(sousLignePanelContenaire);
        removeButtonsCloison.add(removeSousLigneButton);

        // Revalider le panneau pour afficher les modifications
        SpinnerContainerPanelCloison.revalidate();
        SpinnerContainerPanelCloison.repaint();

        updateRemoveButtonsVisibilityCloison();
        updateScrollBarPolicyCloison();
        // Animer le défilement vers la fin à droite
        SwingUtilities.invokeLater(() -> animateScrollToRightCloison());

    }
  private void updateRemoveButtonsVisibilityCloison() {
    // Get the number of spinner panels
    int panelCount = SpinnerContainerPanelCloison.getComponentCount();

    // Show or hide the "Supprimer" button based on the number of spinner panels
    for (JButton button : removeButtonsCloison) {
        if (panelCount > 1) {
            button.setVisible(true);
        } else {
            button.setVisible(false);
        }
    }
}
  private void updateRemoveButtonsVisibilityNaco() {
    // Get the number of spinner panels
    int panelCount = SpinnerContainerPanelNaco.getComponentCount();

    // Show or hide the "Supprimer" button based on the number of spinner panels
    for (JButton button : removeButtonsNaco) {
        if (panelCount > 1) {
            button.setVisible(true);
        } else {
            button.setVisible(false);
        }
    }
}
    private void updateRemoveButtonsVisibilityFenetre() {
        // Get the number of spinner panels
        int panelCount = SpinnerContainerPanelFenetre.getComponentCount();

        // Show or hide the "Supprimer" button based on the number of spinner panels
        for (JButton button : removeButtonsFenetre) {
            if (panelCount > 1) {
                button.setVisible(true);
            } else {
                button.setVisible(false);
            }
        }
    }

    public static void updateRemoveButtonsVisibilityPorte() {
        // Get the number of spinner panels
        int panelCount = SpinnerContainerPanelPorte.getComponentCount();

        // Show or hide the "Supprimer" button based on the number of spinner panels
        for (JButton button : removeButtonsPorte) {
            if (panelCount > 1) {
                button.setVisible(true);
            } else {
                button.setVisible(false);
            }
        }
    }

    private void updateScrollBarPolicyFenetre() {

        if (scrollPaneSousligneFenetre != null) { // Vérifiez si scrollPane est initialisé
            if (SpinnerContainerPanelFenetre.getComponentCount() > 1) {
                // Activer le défilement horizontal si plus d'une sous-ligne
                scrollPaneSousligneFenetre.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            } else {
                // Désactiver le défilement horizontal si une seule sous-ligne
                scrollPaneSousligneFenetre.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            }
        }
    }
private void updateScrollBarPolicyNaco() {

    if (scrollPaneSousligneNaco != null) { // Vérifiez si scrollPane est initialisé
        if (SpinnerContainerPanelNaco.getComponentCount() > 1) {
            // Activer le défilement horizontal si plus d'une sous-ligne
            scrollPaneSousligneNaco.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        } else {
            // Désactiver le défilement horizontal si une seule sous-ligne
            scrollPaneSousligneNaco.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        }
    }
}

private void updateScrollBarPolicyCloison() {

    if (scrollPaneSousligneCloison != null) { // Vérifiez si scrollPane est initialisé
        if (SpinnerContainerPanelCloison.getComponentCount() > 1) {
            // Activer le défilement horizontal si plus d'une sous-ligne
            scrollPaneSousligneCloison.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        } else {
            // Désactiver le défilement horizontal si une seule sous-ligne
            scrollPaneSousligneCloison.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        }
    }
}
public static void resetSpinners(Container container) {
    for (Component component : container.getComponents()) {
        if (component instanceof JSpinner) {
            JSpinner spinner = (JSpinner) component;
            spinner.setValue(0); // Reset the spinner value to 0
        } else if (component instanceof Container) {
            resetSpinners((Container) component); // Recursively traverse the component hierarchy
        }
    }
}


public static List<List<JSpinner>> getSpinnerValues(Container container) {
    List<List<JSpinner>> spinners = new ArrayList<>();
    for (Component component : container.getComponents()) {
        if (component instanceof JPanel) {
            JPanel panel = (JPanel) component; // Explicit cast for Java 11
            List<JSpinner> panelSpinners = new ArrayList<>();
            findSpinners(panel, panelSpinners);
            spinners.add(panelSpinners);
        }
    }
    return spinners;
}

public static void findSpinners(JComponent component, List<JSpinner> spinners) {
    if (component instanceof JSpinner) {
        spinners.add((JSpinner) component); // Explicit cast for Java 11
    } else if (component instanceof Container) {
        Container container = (Container) component; // Explicit cast for Java 11
        for (Component child : container.getComponents()) {
            if (child instanceof JComponent) {
                findSpinners((JComponent) child, spinners); // Recursive call
            }
        }
    }
}

private void animateScrollToRightFenetre() {
        JScrollBar horizontalScrollBar = scrollPaneSousligneFenetre.getHorizontalScrollBar();
        int targetValue = horizontalScrollBar.getMaximum();

        // Utilisation d'un AtomicInteger pour une valeur mutable
        AtomicInteger currentValue = new AtomicInteger(horizontalScrollBar.getValue());

        // Définir le délai et la vitesse de défilement
        int delay = 5; // Délai en millisecondes entre chaque étape de défilement
        int increment = 20;

        Timer timer = new Timer(delay, null);
        timer.addActionListener(e -> {
            if (currentValue.get() < targetValue) {
                int newValue = currentValue.addAndGet(increment);
                if (newValue > targetValue) {
                    currentValue.set(targetValue);
                }
                horizontalScrollBar.setValue(currentValue.get());
            } else {
                timer.stop();
            }
        });

        timer.start();
    }
private void animateScrollToRightNaco() {
    JScrollBar horizontalScrollBar = scrollPaneSousligneNaco.getHorizontalScrollBar();
    int targetValue = horizontalScrollBar.getMaximum();

    // Utilisation d'un AtomicInteger pour une valeur mutable
    AtomicInteger currentValue = new AtomicInteger(horizontalScrollBar.getValue());

    // Définir le délai et la vitesse de défilement
    int delay = 5; // Délai en millisecondes entre chaque étape de défilement
    int increment = 20;

    Timer timer = new Timer(delay, null);
    timer.addActionListener(e -> {
        if (currentValue.get() < targetValue) {
            int newValue = currentValue.addAndGet(increment);
            if (newValue > targetValue) {
                currentValue.set(targetValue);
            }
            horizontalScrollBar.setValue(currentValue.get());
        } else {
            timer.stop();
        }
    });

    timer.start();
}
private void animateScrollToRightCloison() {
    JScrollBar horizontalScrollBar = scrollPaneSousligneCloison.getHorizontalScrollBar();
    int targetValue = horizontalScrollBar.getMaximum();

    // Utilisation d'un AtomicInteger pour une valeur mutable
    AtomicInteger currentValue = new AtomicInteger(horizontalScrollBar.getValue());

    // Définir le délai et la vitesse de défilement
    int delay = 5; // Délai en millisecondes entre chaque étape de défilement
    int increment = 20;

    Timer timer = new Timer(delay, null);
    timer.addActionListener(e -> {
        if (currentValue.get() < targetValue) {
            int newValue = currentValue.addAndGet(increment);
            if (newValue > targetValue) {
                currentValue.set(targetValue);
            }
            horizontalScrollBar.setValue(currentValue.get());
        } else {
            timer.stop();
        }
    });

    timer.start();
}
    public static void selectComboBoxItem(JComboBox<String> comboBox, String part) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            String item = comboBox.getItemAt(i).toLowerCase();
            if (item.contains(part)) {
                comboBox.setSelectedIndex(i);
                return;
            }
        }
    }

    private boolean areAllRowsEmpty(DevisCreateTableModel model) {
        for (int row = 0; row < model.getRowCount(); row++) {
            boolean isRowEmpty = true;
            for (int col = 0; col < model.getColumnCount(); col++) {
                if (model.getValueAt(row, col) != null) {
                    isRowEmpty = false;
                    break;
                }
            }
            if (!isRowEmpty) {
                return false; // Si une ligne n'est pas vide, retourner false
            }
        }
        return true; // Si toutes les lignes sont vides, retourner true
    }

    private void retourAction() {
        Container parent = getParent();
        while (parent != null && !(parent instanceof Dashboard)) {
            parent = parent.getParent();  // Parcourir les parents jusqu'à trouver Dashboard
        }
        if (parent != null) {
            Dashboard dashboard = (Dashboard) parent;
            dashboard.showDevisPanel();
            dashboard.selectMenuIndex(2,1);
        }
    }

    public static String[] parseProfileAluString(String profileAlu) {
        // Supposons que le format soit "MODELE ALU COULEUR"
        String[] parts = profileAlu.split(" ALU ");
        if (parts.length == 2) {
            String model = parts[0].trim();
            String couleur = parts[1].trim();
            return new String[]{model, couleur};
        }
        return null;
    }

public static void ouvrirDialogueIntermediaire() {
    // Création du JDialog
    JDialog dialogue = new JDialog((Frame) null, "Nombre d'Intermédiaires", true);
    dialogue.setSize(350, 150);
    dialogue.setLayout(new BorderLayout(10, 10)); // Espacement entre les composants
    dialogue.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

    // Panel principal avec marges
    JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
    mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Marges internes

    // Champ de saisie
    JLabel label = new JLabel("Veuillez saisir le nombre d'intermédiaires :");
    label.setFont(new Font("Arial", Font.PLAIN, 14));

    // Ajuster la hauteur du JTextField
    JTextField textField = new JTextField(10); // Limite la taille par défaut
    textField.setFont(new Font("Arial", Font.PLAIN, 14));
    textField.setPreferredSize(new Dimension(300, 5)); // Hauteur réduite à 25 pixels

    JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
    inputPanel.add(label, BorderLayout.NORTH);
    inputPanel.add(textField, BorderLayout.CENTER);
    inputPanel.setPreferredSize(new Dimension(300, 5));

    // Boutons OK et Annuler
    JButton okButton = new JButton("OK");
    okButton.setFont(new Font("Arial", Font.PLAIN, 14));
    okButton.setBackground(new Color(0, 123, 255));
    okButton.setForeground(Color.WHITE);

    JButton cancelButton = new JButton("Annuler");
    cancelButton.setFont(new Font("Arial", Font.PLAIN, 14));
    cancelButton.setBackground(new Color(220, 53, 69));
    cancelButton.setForeground(Color.WHITE);

    // Actions des boutons
    okButton.addActionListener(e -> {
        try {
            int valeurSaisie = Integer.parseInt(textField.getText().trim());
            if (valeurSaisie < 0) {
                JOptionPane.showMessageDialog(dialogue, "Le nombre doit être positif.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } else {
                nombreIntermediaires = valeurSaisie;
                dialogue.dispose(); // Fermer la boîte de dialogue
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(dialogue, "Veuillez saisir un nombre valide.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    });

    cancelButton.addActionListener(e -> {
        // Désactiver le JCheckBox si Annuler est cliqué
        intermediairePorteCheckBox.setSelected(false);
        dialogue.dispose(); // Fermer la boîte de dialogue
    });

    // Désactiver le JCheckBox si la boîte de dialogue est fermée via le bouton "X"
    dialogue.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
            intermediairePorteCheckBox.setSelected(false);
        }
    });

    // Panel pour les boutons
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
    buttonPanel.add(cancelButton);
    buttonPanel.add(okButton);

    // Ajouter les composants au dialogue
    mainPanel.add(inputPanel, BorderLayout.CENTER);
    mainPanel.add(buttonPanel, BorderLayout.SOUTH);
    dialogue.add(mainPanel);

    // Centrer la fenêtre et la rendre visible
    dialogue.setLocationRelativeTo(null);
    dialogue.setVisible(true);
}

 public String buildPorteDesignation() {
    StringBuilder designationBuilder = new StringBuilder();
    
    String typePorte = (String) typePorteComboBox.getSelectedItem();
    if (typePorteComboBox.getSelectedIndex() != 0) {
        designationBuilder.append(typePorte).append(" ");
    }

    String modelePorte = (String) modelesProfileCouleursPorteComboBox.getSelectedItem();
    if (modelesProfileCouleursPorteComboBox.getSelectedIndex() != 0) {
        designationBuilder.append(modelePorte).append(" ");
    }

    int ventauxPorte = (int) nbVentauxPorteSpinner.getValue();
    if (ventauxPorte != 0) {
        designationBuilder.append(ventauxPorte)
                .append(" ")
                .append(ventauxPorte == 1 ? "VENTAIL" : "VANTAUX")
                .append(" ");
    }

    String vitragePorte = (String) vitragePorteComboBox.getSelectedItem();
    if (vitragePorteComboBox.getSelectedIndex() != 0) {
        designationBuilder.append(vitragePorte).append(" ");
    }

    String typeVitrePorte = (String) typeVitrePorteComboBox.getSelectedItem();
    if (typeVitrePorteComboBox.getSelectedIndex() != 0) {
        designationBuilder.append(typeVitrePorte).append(" ");
    }

    String structurePorte = (String) structurePorteComboBox.getSelectedItem();
    if (structurePorteComboBox.getSelectedIndex() != 0) {
        designationBuilder.append(structurePorte).append(" ");
    }

    // Gestion des intermédiaires
    if (intermediairePorteCheckBox.isSelected() && nombreIntermediaires > 0) {
        designationBuilder.append("AVEC ")
                .append(nombreIntermediaires)
                .append(" ")
                .append(nombreIntermediaires == 1 ? "INTERMÉDIAIRE" : "INTERMÉDIAIRES")
                .append(" ");
    }

    return designationBuilder.toString().trim().toUpperCase();
}

    private String buildFenetreDesignation() {
        StringBuilder designationBuilder = new StringBuilder();
        String typeFenetre = (String) typeFenetreComboBox.getSelectedItem();
        if (typeFenetreComboBox.getSelectedIndex() != 0) {
            designationBuilder.append(typeFenetre).append(" ");
        }

        String modeleFenetre = (String) modelesProfileCouleursFenetreComboBox.getSelectedItem();
        if (modelesProfileCouleursFenetreComboBox.getSelectedIndex() != 0) {
            designationBuilder.append(modeleFenetre).append(" ");
        }

        int ventauxFenetre = (int) nbVentauxFenetreSpinner.getValue(); // Utilisation de getValue() pour récupérer la valeur du JSpinner
        if (ventauxFenetre != 0) { // Vérifie que la valeur n'est pas égale à 0
            designationBuilder.append(ventauxFenetre)
                    .append(" ")
                    .append(ventauxFenetre == 1 ? "VENTAIL" : "VANTAUX") // Choix singulier/pluriel
                    .append(" ");
        }

        String vitrageFenetre = (String) vitrageFenetreComboBox.getSelectedItem();
        if (vitrageFenetreComboBox.getSelectedIndex() != 0) {
            designationBuilder.append(vitrageFenetre).append(" ");
        }

        String typeVitreFenetre = (String) typeVitreFenetreComboBox.getSelectedItem();
        if (typeVitreFenetreComboBox.getSelectedIndex() != 0) {
            designationBuilder.append(typeVitreFenetre).append(" ");
        }

        return designationBuilder.toString().trim().toUpperCase();
    }

  

   

    private String buildCloisonDesignation() {
        StringBuilder designationBuilder = new StringBuilder();
        String typeCloison = (String) typeCloisonComboBox.getSelectedItem();
        if (typeCloisonComboBox.getSelectedIndex() != 0) {
            designationBuilder.append(typeCloison).append(" ");
        }

        String modeleCloison = (String) modelesProfileCouleursCloisonComboBox.getSelectedItem();
        if (modelesProfileCouleursCloisonComboBox.getSelectedIndex() != 0) {
            designationBuilder.append(modeleCloison).append(" ");
        }

        String vitrageCloison = (String) vitrageCloisonComboBox.getSelectedItem();
        if (vitrageCloisonComboBox.getSelectedIndex() != 0) {
            designationBuilder.append(vitrageCloison).append(" ");
        }

        String typeVitreCloison = (String) typeVitreCloisonComboBox.getSelectedItem();
        if (typeVitreCloisonComboBox.getSelectedIndex() != 0) {
            designationBuilder.append(typeVitreCloison).append(" ");
        }

        // Gestion des intermédiaires
    if (intermediairePorteCheckBox.isSelected() && nombreIntermediaires > 0) {
        designationBuilder.append("AVEC ")
                .append(nombreIntermediaires)
                .append(" ")
                .append(nombreIntermediaires == 1 ? "INTERMÉDIAIRE" : "INTERMÉDIAIRES")
                .append(" ");
    }
        return designationBuilder.toString().trim().toUpperCase();
    }

 private JPanel createPortePanel() throws Exception {
    // Créer un panneau conteneur pour le PortePanel
    JPanel porteContainer = new JPanel(new BorderLayout());
    
    // Créer le PortePanel
    JPanel portePanel = new JPanel(new GridLayout(1, 3, 0, 0));
    Color defaultColor = UIManager.getColor("ScrollBar.thumb");
    portePanel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, defaultColor));

    GridBagConstraints gbc = new GridBagConstraints();
    gbc.fill = GridBagConstraints.HORIZONTAL;

    // Initialisation des composants
    typePorteComboBox = new JComboBox<>();
    modelesProfileCouleursPorteComboBox = new JComboBox<>();
    nbVentauxPorteSpinner =configureSpinnerInteger();
    vitragePorteComboBox = new JComboBox<>();
    typeVitrePorteComboBox = new JComboBox<>();
   
    populateComboBox(vitragePorteComboBox, "vitrage", "type");
 typeVitrePorteComboBox.addItem("Sélectionnez le vitrage d'abord");
        setComboBoxRenderer(typeVitrePorteComboBox);
    vitragePorteComboBox.addActionListener((ActionEvent e) -> {
         typeVitrePorteComboBox.removeAllItems();
         typeVitrePorteComboBox.addItem("Sélectionnez le vitrage d'abord");
        String selectedVitrage = (String) vitragePorteComboBox.getSelectedItem();
        if (selectedVitrage != null && !selectedVitrage.isEmpty()) {
            try {
                populateTypeVitreComboBox(typeVitrePorteComboBox, selectedVitrage);
            } catch (Exception ex) {
                Logger.getLogger(DevisCreatePanel.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else{
            // typeVitreFenetreComboBox.removeAllItems();
        }
    });

    structurePorteComboBox = new JComboBox<>();
    fillComboBoxWithProducts(typePorteComboBox, "Porte");
    populateModeleCouleurComboBox(modelesProfileCouleursPorteComboBox);
    populateComboBox(structurePorteComboBox, "structure", "structure");

    // Ajout des champs avec espacement
    JPanel firstCol = new JPanel(new GridLayout(3, 1, 4, 2));
    firstCol.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));
    addField(firstCol, "Type de Porte:", typePorteComboBox);
    addField(firstCol, "Modèle Profile:", modelesProfileCouleursPorteComboBox);
    addField(firstCol, "Nombre de Ventaux:", nbVentauxPorteSpinner);

    JPanel secondCol = new JPanel(new GridLayout(3, 1, 4, 2));
    secondCol.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));
    addField(secondCol, "Vitrage:", vitragePorteComboBox);
    addField(secondCol, "Type de Vitre:", typeVitrePorteComboBox);
    addField(secondCol, "Structure:", structurePorteComboBox);
    
    JPanel thirdColContenaire = createSpinnerPortePanel();

    JPanel firstColContenaire = new JPanel(new BorderLayout());
    JPanel secondColContenaire = new JPanel(new BorderLayout());
    firstColContenaire.add(firstCol, BorderLayout.NORTH);
    secondColContenaire.add(secondCol, BorderLayout.NORTH);

    gbc.gridx = 0;
    gbc.gridy = 0;
    portePanel.add(firstColContenaire);

    gbc.gridx = 1;
    gbc.gridy = 0;
    portePanel.add(secondColContenaire);

    gbc.gridx++;
    portePanel.add(thirdColContenaire);


    // Ajouter le portePanel au conteneur
    porteContainer.add(portePanel, BorderLayout.CENTER);
       porteContainer.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              porteContainer.requestFocusInWindow();
             
               
            }
        });
    
    return porteContainer; // Retourner le conteneur
}


    private JPanel createFenetrePanel() throws Exception {
        JPanel panel = new JPanel(new GridLayout(1, 3, 0, 0));
        Color defaultColor = UIManager.getColor("ScrollBar.thumb");
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, defaultColor));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize JComboBoxes without data
        typeFenetreComboBox = new JComboBox<>();
        modelesProfileCouleursFenetreComboBox = new JComboBox<>();
        nbVentauxFenetreSpinner =configureSpinnerInteger();
        vitrageFenetreComboBox = new JComboBox<>();

        typeVitreFenetreComboBox = new JComboBox<>();

        fillComboBoxWithProducts(typeFenetreComboBox, "Fenetre");
        populateModeleCouleurComboBox(modelesProfileCouleursFenetreComboBox);
        typeVitreFenetreComboBox.addItem("Sélectionnez le vitrage d'abord");
        setComboBoxRenderer(typeVitreFenetreComboBox);

        populateComboBox(vitrageFenetreComboBox, "vitrage", "type");
        vitrageFenetreComboBox.addActionListener((ActionEvent e) -> {
            String selectedVitrage = (String) vitrageFenetreComboBox.getSelectedItem();
//            updateTypeVitrePorteComboBox(selectedVitrage);
         typeVitreFenetreComboBox.removeAllItems();
         typeVitreFenetreComboBox.addItem("Sélectionnez le vitrage d'abord");
            if (selectedVitrage != null && !selectedVitrage.isEmpty()) {
                try {
                    populateTypeVitreComboBox(typeVitreFenetreComboBox, selectedVitrage);
                } catch (Exception ex) {
                    Logger.getLogger(DevisCreatePanel.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else {
                // Vider les combo boxes si aucune couleur n'est sélectionnée
             // typeVitreFenetreComboBox.removeAllItems();

            }

        });

        JPanel firstCol = new JPanel(new GridLayout(3, 1, 4, 2)); // 10 pixels horizontal gap
        firstCol.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));

        addField(firstCol, "Type de Fenêtre:", typeFenetreComboBox);
        addField(firstCol, "Modèle Profile:", modelesProfileCouleursFenetreComboBox);
        addField(firstCol, "Nombre de Ventaux:", nbVentauxFenetreSpinner);

        JPanel secondCol = new JPanel(new GridLayout(3, 1, 4, 2)); // 10 pixels horizontal gap
        secondCol.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));
        addField(secondCol, "Vitrage:", vitrageFenetreComboBox);
        addField(secondCol, "Type de Vitre:", typeVitreFenetreComboBox);
        JPanel thirdColContenaire = createSpinnerFenetrePanel();
        JPanel firstColContenaire = new JPanel(new BorderLayout());
        JPanel secondColContenaire = new JPanel(new BorderLayout());
        firstColContenaire.add(firstCol, BorderLayout.NORTH);
        secondColContenaire.add(secondCol, BorderLayout.NORTH);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(firstColContenaire);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(secondColContenaire);
        gbc.gridx++;
        panel.add(thirdColContenaire);

            panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              panel.requestFocusInWindow();
             
               
            }
        });
        return panel;
    }
    
   
    private JPanel createNacoPanel() throws Exception {
   JPanel panel = new JPanel(new GridLayout(1, 3, 0, 0));
        Color defaultColor = UIManager.getColor("ScrollBar.thumb");
        panel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, defaultColor));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize JComboBoxes without data
        typeNacoComboBox = new JComboBox<>();
        fillComboBoxWithProducts(typeNacoComboBox, "Naco");
        modelesProfileCouleursNacoComboBox = new JComboBox<>();
        populateModeleCouleurComboBox(modelesProfileCouleursNacoComboBox);
        
        
        vitrageNacoComboBox = new JComboBox<>();
        populateComboBox(vitrageNacoComboBox, "vitrage", "type");
      
        verreNacoComboBox= new JComboBox<>();
         verreNacoComboBox.addItem("Sélectionnez le vitrage d'abord");
         setComboBoxRenderer(verreNacoComboBox);
             
        largeurLameNacoComboBox = new JComboBox<>();
          largeurLameNacoComboBox.addItem("Sélectionnez le type de verre");
        setComboBoxRenderer(largeurLameNacoComboBox);
       // populateLargeurLameComboBox(largeurLameNacoComboBox);
        
   
      
        vitrageNacoComboBox.addActionListener((ActionEvent e) -> {
             verreNacoComboBox.removeAllItems();
             verreNacoComboBox.addItem("Sélectionnez le vitrage d'abord");
            String selectedVitrage = (String) vitrageNacoComboBox.getSelectedItem();
           
//            updateTypeVitrePorteComboBox(selectedVitrage);

            if (selectedVitrage != null && !selectedVitrage.isEmpty()) {
                try {
                    populateTypeVerreComboBox(verreNacoComboBox, selectedVitrage);
                } catch (Exception ex) {
                    Logger.getLogger(DevisCreatePanel.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else{
               // verreNacoComboBox.removeAllItems();
            }

        });
   verreNacoComboBox.addActionListener((ActionEvent e) -> {
          largeurLameNacoComboBox.removeAllItems();
         largeurLameNacoComboBox.addItem("Sélectionnez le type de verre");
    String selectedVerre = (String) verreNacoComboBox.getSelectedItem();
    
    // Check if the selected item is not the default message
    if (selectedVerre != null && !selectedVerre.equals("Sélectionnez le vitrage d'abord") && !selectedVerre.isEmpty()) {
        try {
            // Parse the selected verre string
            String[] typeVitreParts = parseTypeVitreString(selectedVerre);
            if (typeVitreParts != null && typeVitreParts.length == 2) {
                String typeVitre = typeVitreParts[0];
                int epaisseur = Integer.parseInt(typeVitreParts[1]);
                populateLargeurLameComboBox(largeurLameNacoComboBox, epaisseur, typeVitre);
            } else {
                 largeurLameNacoComboBox.removeAllItems();
                // Handle invalid format case if needed
                Logger.getLogger(DevisCreatePanel.class.getName()).log(Level.WARNING, "Invalid typeVitre format: " + selectedVerre);
            }
        } catch (Exception ex) {
            Logger.getLogger(DevisCreatePanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
});


        JPanel firstCol = new JPanel(new GridLayout(3, 1, 4, 2)); // 10 pixels horizontal gap
        firstCol.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));

        addField(firstCol, "Type de Naco:", typeNacoComboBox);
        addField(firstCol, "Modèle Profile:", modelesProfileCouleursNacoComboBox);
        addField(firstCol, "Vitrage:", vitrageNacoComboBox);

        JPanel secondCol = new JPanel(new GridLayout(3, 1, 4, 2)); // 10 pixels horizontal gap
        secondCol.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));
        addField(secondCol, "Type de Verre:",verreNacoComboBox );
        addField(secondCol, "Largeur Lame :", largeurLameNacoComboBox);
        JPanel thirdColContenaire = createSpinnerNacoPanel();
        JPanel firstColContenaire = new JPanel(new BorderLayout());
        JPanel secondColContenaire = new JPanel(new BorderLayout());
        firstColContenaire.add(firstCol, BorderLayout.NORTH);
        secondColContenaire.add(secondCol, BorderLayout.NORTH);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(firstColContenaire);
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(secondColContenaire);
        gbc.gridx++;
        panel.add(thirdColContenaire);

            panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              panel.requestFocusInWindow();
             
               
            }
        });
        return panel;
    } 
   public static String[] parseTypeVitreString(String typeVitre) {
    // Supposons que le format soit "TYPE EPaisseur_mm mm"
    String[] parts = typeVitre.split(" ");
    if (parts.length == 2) {
        String type = parts[0].trim();
        int epaisseur = -1;
        try {
            epaisseur = Integer.parseInt(parts[1].replace("mm", "").trim());
        } catch (NumberFormatException e) {
            epaisseur = -1; // Default value in case of error
        }
        return new String[]{type, Integer.toString(epaisseur)};
    }
    // Log or handle invalid format
    Logger.getLogger(DevisCreatePanel.class.getName()).log(Level.WARNING, "Invalid typeVitre format: " + typeVitre);
    return null;  // Return null if format is invalid
}

        public static void populateTypeVerreComboBox(JComboBox<String> comboBox, String vitrageType) throws Exception {

        comboBox.removeAllItems(); // Vider les éléments existants
        comboBox.addItem(""); // Ajouter un élément vide

        // Récupérer le vitrage sélectionné
        // Récupérer les types de verre correspondant au type de vitrage sélectionné
        List<ModelLameVerre> typesEpaisseurs = DatabaseUtils.retrieveTypesEpaisseursVerre(vitrageType);

        // Ajouter chaque type de verre dans le comboBox
        for (ModelLameVerre typeVitre : typesEpaisseurs) {
            comboBox.addItem(typeVitre.toStringTypeEpesseur());
        }

        setComboBoxRenderer(comboBox); // Optionnel : si vous utilisez un renderer personnalisé
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

                    setText("Sélectionnez une option");
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
  public void resetNacoFields() {
  if (typeNacoComboBox != null && typeNacoComboBox.getModel().getSize() > 0) {
    typeNacoComboBox.setSelectedIndex(0);
} else {
    System.out.println("Combo box model is empty or null.");
}
    if (modelesProfileCouleursNacoComboBox != null && modelesProfileCouleursNacoComboBox.getItemCount() > 0) {
        modelesProfileCouleursNacoComboBox.setSelectedIndex(0);
    }
    if (largeurLameNacoComboBox != null && largeurLameNacoComboBox.getItemCount() > 0) {
        largeurLameNacoComboBox.setSelectedIndex(0);
    }
    if (vitrageNacoComboBox != null && vitrageNacoComboBox.getItemCount() > 0) {
        vitrageNacoComboBox.setSelectedIndex(0);
    }
    if (verreNacoComboBox != null && verreNacoComboBox.getItemCount() > 0) {
        verreNacoComboBox.setSelectedIndex(0);
    }

    // Ensure SpinnerContainerPanelNaco is initialized
    if (SpinnerContainerPanelNaco != null) {
        while (SpinnerContainerPanelNaco.getComponentCount() > 1) {
            SpinnerContainerPanelNaco.remove(1);
        }
        resetSpinners((Container) SpinnerContainerPanelNaco.getComponent(0));
    }

    if (SpinnerContainerPanelNaco.getComponentCount() == 1) {
        for (JButton button : removeButtonsPorte) {
            if (button != null) {
                button.setVisible(false);
            }
        }
    }

    updateRemoveButtonsVisibilityNaco();
    SpinnerContainerPanelNaco.revalidate();
    SpinnerContainerPanelNaco.repaint();
}

private JPanel createCloisonPanel() throws Exception {
    JPanel panel = new JPanel(new GridLayout(1, 3, 0, 0));
         Color defaultColor = UIManager.getColor("ScrollBar.thumb");
         panel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, defaultColor));
         GridBagConstraints gbc = new GridBagConstraints();
         gbc.insets = new Insets(5, 5, 5, 5);
         gbc.fill = GridBagConstraints.HORIZONTAL;
 
          //Initialize JComboBoxes without data
         typeCloisonComboBox = new JComboBox<>();
         modelesProfileCouleursCloisonComboBox = new JComboBox<>();
    
         vitrageCloisonComboBox = new JComboBox<>();
 
         typeVitreCloisonComboBox = new JComboBox<>();
 
         fillComboBoxWithProducts(typeCloisonComboBox, "Cloison");
         populateModeleCouleurComboBox(modelesProfileCouleursCloisonComboBox);
         typeVitreCloisonComboBox.addItem("Sélectionnez le vitrage d'abord");
         setComboBoxRenderer(typeVitreCloisonComboBox);
 
         populateComboBox(vitrageCloisonComboBox, "vitrage", "type");
         vitrageCloisonComboBox.addActionListener((ActionEvent e) -> {
              typeVitreCloisonComboBox.removeAllItems();
               typeVitreCloisonComboBox.addItem("Sélectionnez le vitrage d'abord");
             String selectedVitrage = (String) vitrageCloisonComboBox.getSelectedItem();
            // updateTypeVitreCloisonComboBox(selectedVitrage);
 
             if (selectedVitrage != null && !selectedVitrage.isEmpty()) {
                 try {
                     populateTypeVitreComboBox(typeVitreCloisonComboBox, selectedVitrage);
                 } catch (Exception ex) {
                     Logger.getLogger(DevisCreatePanel.class.getName()).log(Level.SEVERE, null, ex);
                 }
 
             } else {
                 // Vider les combo boxes si aucune couleur n'est sélectionnée
                //  typeVitreCloisonComboBox.removeAllItems();
 
             }
 
         });
 
         JPanel firstCol = new JPanel(new GridLayout(3, 1, 4, 2)); // 10 pixels horizontal gap
         firstCol.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));
 
         addField(firstCol, "Type de Cloison:", typeCloisonComboBox);
         addField(firstCol, "Modèle Profile:", modelesProfileCouleursCloisonComboBox);


         JPanel secondCol = new JPanel(new GridLayout(3, 1, 4, 2)); // 10 pixels horizontal gap
         secondCol.setBorder(BorderFactory.createEmptyBorder(15, 15, 0, 15));
       
         addField(secondCol, "Vitrage:", vitrageCloisonComboBox);
         addField(secondCol, "Type de Vitre:", typeVitreCloisonComboBox);
         JPanel thirdColContenaire = createSpinnerCloisonPanel();
         JPanel firstColContenaire = new JPanel(new BorderLayout());
         JPanel secondColContenaire = new JPanel(new BorderLayout());
         firstColContenaire.add(firstCol, BorderLayout.NORTH);
         secondColContenaire.add(secondCol, BorderLayout.NORTH);
         gbc.gridx = 0;
         gbc.gridy = 0;
         panel.add(firstColContenaire);
         gbc.gridx = 0;
         gbc.gridy = 0;
         panel.add(secondColContenaire);
         gbc.gridx++;
         panel.add(thirdColContenaire);
 
             panel.addMouseListener(new MouseAdapter() {
             @Override
             public void mouseClicked(MouseEvent e) {
               panel.requestFocusInWindow();
              
                
             }
         });
         return panel;
     }
 

  
    public static void populateTypeVitreComboBox(JComboBox<String> comboBox, String vitrageType) throws Exception {

        //comboBox.removeAllItems(); // Vider les éléments existants
        comboBox.addItem(""); // Ajouter un élément vide

        // Récupérer le vitrage sélectionné
        // Récupérer les types de verre correspondant au type de vitrage sélectionné
        List<ModelTypeVitre> typesEpaisseurs = DatabaseUtils.retrieveTypesEpaisseursVitrage(vitrageType);

        // Ajouter chaque type de verre dans le comboBox
        for (ModelTypeVitre typeVitre : typesEpaisseurs) {
            comboBox.addItem(typeVitre.toString());
        }

        setComboBoxRenderer(comboBox); // Optionnel : si vous utilisez un renderer personnalisé
    }

    public static void populateModeleCouleurComboBox(JComboBox<String> comboBox) throws SQLException{
        // Ajouter un élément vide au début
        comboBox.addItem("");
         List<ModelProfileAlu> allProfiles = DatabaseUtils.retrieveAllProfileAlu();

        // Ajouter chaque modèle de profil au comboBox
        for (ModelProfileAlu profile : allProfiles) {
            comboBox.addItem(profile.toString()); // Assurez-vous que ModelProfileAlu.toString() renvoie une chaîne appropriée
        }
        
        // Ajouter un renderer personnalisé si nécessaire
        setComboBoxRenderer(comboBox);
    }
   public static void populateLargeurLameComboBox(JComboBox<String> comboBox, int epaisseur, String typeverre) throws SQLException, Exception{
        // Ajouter un élément vide au début
      // comboBox.removeAllItems(); // Vider les éléments existants
        comboBox.addItem(""); // Ajouter un élément vide
         List<ModelLameVerre> largeurLame = DatabaseUtils.retrieveLargeurVerre(epaisseur,typeverre);

        // Ajouter chaque modèle de profil au comboBox
        for (ModelLameVerre lame : largeurLame) {
            comboBox.addItem(lame.toString()); // Assurez-vous que ModelProfileAlu.toString() renvoie une chaîne appropriée
        }
        
        // Ajouter un renderer personnalisé si nécessaire
        setComboBoxRenderer(comboBox);
    }


    public static void populateComboBox(JComboBox<String> comboBox, String tableName, String columnName) {
        // Ajouter un élément vide au début
        comboBox.addItem("");

        List<String> values = DatabaseUtils.retrieveValuesFromTable(tableName, columnName);
        for (String value : values) {
            comboBox.addItem(value.toUpperCase());
        }
        setComboBoxRenderer(comboBox);
    }

    public static void fillComboBoxWithProducts(JComboBox<String> comboBox, String typeProduit) {
        comboBox.addItem("");
        List<String> produits = DatabaseUtils.getProduitsByType(typeProduit);
        for (String produit : produits) {
            comboBox.addItem(produit.toUpperCase());
        }
        setComboBoxRenderer(comboBox);
    }

  

    public void resetPorteFields() {
        typePorteComboBox.setSelectedIndex(0);
        modelesProfileCouleursPorteComboBox.setSelectedIndex(0);
        //couleurAluPorteComboBox.setSelectedIndex(0);
        // Réinitialiser le JSpinner à zéro
        nbVentauxPorteSpinner.setValue(0); // Remettre la valeur à 0
        structurePorteComboBox.setSelectedIndex(0);
        vitragePorteComboBox.setSelectedIndex(0);
        typeVitrePorteComboBox.setSelectedIndex(0);
intermediairePorteCheckBox.setSelected(false);
        // Remove all spinner panels except the first one
        while (SpinnerContainerPanelPorte.getComponentCount() > 1) {
            SpinnerContainerPanelPorte.remove(1);

        }

// Reset the first spinner panel
        resetSpinners((Container) SpinnerContainerPanelPorte.getComponent(0));

        // Hide the "Supprimer" button if there's only one spinner panel
        if (SpinnerContainerPanelPorte.getComponentCount() == 1) {
            for (JButton button : removeButtonsPorte) {
                button.setVisible(false);
            }

        }
        updateRemoveButtonsVisibilityPorte();
        SpinnerContainerPanelPorte.revalidate();
        SpinnerContainerPanelPorte.repaint();

    }

    public void resetFenetreFields() {
        typeFenetreComboBox.setSelectedIndex(0);
        modelesProfileCouleursFenetreComboBox.setSelectedIndex(0);
        //couleurAluFenetreComboBox.setSelectedIndex(0);
        nbVentauxFenetreSpinner.setValue(0);

        vitrageFenetreComboBox.setSelectedIndex(0);
          if (typeVitreFenetreComboBox != null && typeVitreCloisonComboBox.getItemCount() > 0) {
        typeVitreFenetreComboBox.setSelectedIndex(0);
    }
      

        // Remove all spinner panels except the first one
        while (SpinnerContainerPanelFenetre.getComponentCount() > 1) {
            SpinnerContainerPanelFenetre.remove(1);

        }

// Reset the first spinner panel
        resetSpinners((Container) SpinnerContainerPanelFenetre.getComponent(0));

        // Hide the "Supprimer" button if there's only one spinner panel
        if (SpinnerContainerPanelFenetre.getComponentCount() == 1) {
            for (JButton button : removeButtonsPorte) {
                button.setVisible(false);
            }

        }
        updateRemoveButtonsVisibilityFenetre();
        SpinnerContainerPanelFenetre.revalidate();
        SpinnerContainerPanelFenetre.repaint();

    }
 
//
    public void resetCloisonFields() {
        typeCloisonComboBox.setSelectedIndex(0);
        modelesProfileCouleursCloisonComboBox.setSelectedIndex(0);
        vitrageCloisonComboBox.setSelectedIndex(0);
       // typeVitreCloisonComboBox.setSelectedIndex(0);
          if (typeVitreCloisonComboBox != null && typeVitreCloisonComboBox.getItemCount() > 0) {
        typeVitreCloisonComboBox.setSelectedIndex(0);
    }

        // Remove all spinner panels except the first one
        while (SpinnerContainerPanelCloison.getComponentCount() > 1) {
            SpinnerContainerPanelCloison.remove(1);

        }

// Reset the first spinner panel
        resetSpinners((Container) SpinnerContainerPanelCloison.getComponent(0));

        // Hide the "Supprimer" button if there's only one spinner panel
        if (SpinnerContainerPanelCloison.getComponentCount() == 1) {
            for (JButton button : removeButtonsPorte) {
                button.setVisible(false);
            }

        }
        updateRemoveButtonsVisibilityCloison();
        SpinnerContainerPanelCloison.revalidate();
        SpinnerContainerPanelCloison.repaint();

    }


    public void resetFields() {
        // Réinitialisation des champs pour chaque type de produit
        resetPorteFields();
        resetFenetreFields();
    resetNacoFields();
resetCloisonFields();
    }

    private void resetAllFields() {
        resetTotals();
        // Réinitialiser les champs pour chaque onglet
        resetFields();
        // Effacer le contenu du tableau principal
        tableModel.clearTable();
    }

    private void resetTotals() {
        // Réinitialiser les valeurs dans totalsTableModel
        int rowCount = totalsTableModel.getRowCount();
        int columnCount = totalsTableModel.getColumnCount();

        // Réinitialiser chaque cellule à null
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                totalsTableModel.setValueAt(null, row, col);
            }
        }

        // Réinitialiser les ComboBox de remise et de TVA
        remiseComboBox.setSelectedItem("0%");
        tvaComboBox.setSelectedItem("0%");

        // Informer Swing que les données du modèle ont changé
        totalsTableModel.fireTableDataChanged();

        reset = true;
        updateTotals();
        reset = false;
    }
public static void updateTotals() {
    BigDecimal totalHt = BigDecimal.ZERO;
    BigDecimal acomptePercent = BigDecimal.valueOf(parsePercent(acompteComboBox.getSelectedItem().toString())).divide(BigDecimal.valueOf(100));
    BigDecimal remisePercent = BigDecimal.valueOf(parsePercent(remiseComboBox.getSelectedItem().toString())).divide(BigDecimal.valueOf(100));
    BigDecimal tvaPercent = BigDecimal.valueOf(parsePercent(tvaComboBox.getSelectedItem().toString())).divide(BigDecimal.valueOf(100));

    if (reset) {
        // Réinitialiser les valeurs dans totalsTableModel
        for (int row = 0; row < totalsTableModel.getRowCount(); row++) {
            totalsTableModel.setValueAt(null, row, 0);
        }
        reset = false;
    } else {
        // Calculer le total HT
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 16) != null) {
                // Vérifier si c'est une ligne principale
                if (!isSubRow(tableModel, i)) {
                    totalHt = totalHt.add((BigDecimal) tableModel.getValueAt(i, 16)); // Assurez-vous que la colonne 16 contient le montant correct
                }
            }
        }

        // Calculer la remise
        BigDecimal remise = totalHt.multiply(remisePercent);

        // Calculer le total HT après remise
        BigDecimal totalNetHt = totalHt.subtract(remise);

        // Calculer le total de l'acompte
        BigDecimal totalAcompte = totalNetHt.multiply(acomptePercent);

        // Calculer le total TVA basé sur le total HT net
        BigDecimal totalTva = totalNetHt.multiply(tvaPercent);

        // Calculer le total TTC
        BigDecimal totalTtc = totalNetHt.add(totalTva).subtract(totalAcompte);

        // Mettre à jour les valeurs dans le modèle de la table des totaux
        totalsTableModel.setValueAt(totalHt, 0, 0); // Total HT
        totalsTableModel.setValueAt(remise, 1, 0); // Remise
        totalsTableModel.setValueAt(totalAcompte, 2, 0); // Acompte
        totalsTableModel.setValueAt(totalTva, 3, 0); // Total TVA
        totalsTableModel.setValueAt(totalTtc, 4, 0); // Total TTC

        // Informer Swing que les données du modèle ont changé
        totalsTableModel.fireTableDataChanged();
    }
}

 

  




    private static class PriceTotalTableCellRenderer extends DefaultTableCellRenderer {

        private final NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);

        public PriceTotalTableCellRenderer() {
            numberFormat.setMinimumFractionDigits(2);
            numberFormat.setMaximumFractionDigits(2);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Appliquer l'alignement à droite au contenu de la cellule
            setHorizontalAlignment(JLabel.RIGHT);

            // Définir le style de police pour "Total TTC:"
            if (row == 4) { // Supposons que "Total TTC:" soit toujours dans la cinquième ligne (index 4)
                setFont(getFont().deriveFont(Font.BOLD)); // Rendre la police en gras
            } else {
                setFont(getFont().deriveFont(Font.PLAIN)); // Sinon, utiliser la police normale
            }

            // Formater et définir le texte en fonction du type de valeur
            if (value == null || value.toString().trim().isEmpty()) {
                setText(""); // Si la valeur est null ou vide, afficher une chaîne vide
            } else if (value instanceof BigDecimal) {
                setText(numberFormat.format(value) + " Ar");
            } else {
                setText(""); // Pour les autres cas, afficher une chaîne vide
            }

            return this;
        }
    }

    private JPanel setupTotalsPanel() {
        // Panel principal pour les totaux
        totalsPanel = new JPanel(new BorderLayout());
        totalsPanel.setBorder(BorderFactory.createTitledBorder("Totals"));

        // Modèle de table pour les totaux
        totalsTableModel = new DefaultTableModel(new Object[]{"Value"}, 5) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Rendre les cellules non éditables
            }
        };

        // Panel pour les labels et les valeurs des totaux
        JPanel labelsAndValuesPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(2, 5, 2, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        String[] labels = {"Total HT:", "Remise (%):","Acompte (%):", "Tot. TVA (%):", "Total TTC:"};
        JLabel[] labelComponents = new JLabel[labels.length];

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            labelComponents[i] = new JLabel(labels[i]);
            labelComponents[i].setFont(new Font("Arial", Font.BOLD, 12));
            labelsAndValuesPanel.add(labelComponents[i], gbc);

            gbc.gridx = 1;

         switch (i) {
    case 1: {
        // Combo box pour la remise
        remiseComboBox = new JComboBox<>(new String[]{"0%", "5%", "10%", "15%"});
        remiseComboBox.setSelectedItem("0%"); // Par défaut à 0%
        remiseComboBox.addActionListener(e -> updateTotals());
        labelsAndValuesPanel.add(remiseComboBox, gbc);
        break;
    }
    case 2: {
        // Combo box pour l'acompte
        acompteComboBox = new JComboBox<>(new String[]{"0%", "5%", "10%", "15%", "20%", "25%", "30%", "35%", "40%", 
                                                       "45%", "50%", "55%", "60%", "65%", "70%", "75%", "80%", 
                                                       "85%", "90%", "95%", "100%"});
        acompteComboBox.setSelectedItem("0%"); // Par défaut à 0%
        acompteComboBox.addActionListener(e -> updateTotals());
        labelsAndValuesPanel.add(acompteComboBox, gbc);
        break;
    }
    case 3: {
        // Combo box pour la TVA
        tvaComboBox = new JComboBox<>(new String[]{"0%", "5%", "10%", "20%"});
        tvaComboBox.setSelectedItem("0%"); // Par défaut à 0%
        tvaComboBox.addActionListener(e -> updateTotals());
        labelsAndValuesPanel.add(tvaComboBox, gbc);
        break;
    }
    default: {
        JLabel valueLabel = new JLabel("");
        valueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        labelsAndValuesPanel.add(valueLabel, gbc);
        break;
    }
}
        }


        // Créer la JTable pour afficher les totaux
        totalsTable = new JTable(totalsTableModel);
        totalsTable.setRowHeight(30); // Hauteur des lignes
        totalsTable.setFont(new Font("Arial", Font.PLAIN, 12)); // Police de caractères

        totalsTable.setSelectionBackground(new Color(75, 110, 175));
        // Afficher la grille dans la JTable
        totalsTable.setShowGrid(true);
         totalsTable.setGridColor(Color.GRAY);
      //  totalsTable = new RollOverTable(totalsTableModel);
        totalsTable.setPreferredScrollableViewportSize(new Dimension(250, totalsTable.getRowHeight() * totalsTable.getRowCount()));
        // Supprimer l'en-tête de la table
        totalsTable.getTableHeader().setVisible(false);
        // Définir la largeur des colonnes 
        totalsTable.getColumnModel().getColumn(0).setPreferredWidth(250); // Ajuster la largeur comme nécessaire
        // Rendu personnalisé pour aligner le texte à droite avec un séparateur de milliers
        totalsTable.getColumnModel().getColumn(0).setCellRenderer(new PriceTotalTableCellRenderer());

        // Ajouter la JTable directement au panel des totaux sans JScrollPane
        totalsPanel.add(totalsTable, BorderLayout.EAST);

        subPanel = new JPanel(new BorderLayout());
        subPanel.add(totalsPanel, BorderLayout.EAST); // Aligner totalsPanel à l'est dans subPanel
        subPanel.setBorder(new EmptyBorder(5, 10, 0, 10));
        // Ajouter le sous-panel au panel principal des totaux
        totalsPanel.add(labelsAndValuesPanel, BorderLayout.WEST);

        return subPanel;
    }

    public static void addField(JPanel panel, String label, JComponent field) {
        JPanel fieldPanel = new JPanel(new BorderLayout());
       
        JLabel lab = new JLabel(label);
         lab.putClientProperty(FlatClientProperties.STYLE, "" +
                "font:bold +1;");
        fieldPanel.add(lab, BorderLayout.NORTH);
        fieldPanel.add(field, BorderLayout.CENTER);
        panel.add(fieldPanel);
    }

     private JPanel createDateField() {
        JPanel panel = new JPanel(new BorderLayout());
        dateEditor = new JFormattedTextField();
        dateEditor.setPreferredSize(new Dimension(200, 33));

        // Créer un DatePicker
        datePicker = new DatePicker();
        
        // Définir l'éditeur du DatePicker sur dateEditor
        datePicker.setEditor(dateEditor);

        // Obtenir la date actuelle et la formater
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");  // Format: jour/mois/année
        String formattedDate = currentDate.format(formatter);

        // Définir la date actuelle dans le champ texte
        dateEditor.setText(formattedDate);

        // Ajouter l'éditeur de date au panel
        panel.add(dateEditor, BorderLayout.EAST);

        return panel;
    }

// Méthode pour créer le JComboBox des clientss
    private JPanel createClientComboBox() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);
       
        clientCombo = new JComboBox<>();
      clientCombo.setBackground(new Color(0, 0, 0, 0));
         clientCombo.setOpaque(false);
//  clientCombo.putClientProperty(FlatClientProperties.STYLE, ""
//                + "background:$ComboBox.background");
        List<ModelClient> allClient = DatabaseUtils.getAllClients();
        for (ModelClient client : allClient) {
            clientCombo.addItem(client.getNom());
        }
        AutoCompleteDecorator.decorate(clientCombo);

        // Add action listener to update client details when selection changes
        JPanel panelAddClientButton = addAddClientButton();
        panel.setPreferredSize(new Dimension(200, 33));
        panel.add(clientCombo, BorderLayout.CENTER);
        panel.add(panelAddClientButton, BorderLayout.EAST);
         //setComboBoxRenderer(clientCombo);
        return panel;
    }

    public static void selectFirstClient() {
        // Sélectionner le premier client dans le JComboBox
        if (clientCombo.getItemCount() > 0) {
            String firstClient = (String) clientCombo.getItemAt(0);
            clientCombo.setSelectedItem(firstClient);

        }
    }

// Méthode pour créer le champ de texte de la date
    @SuppressWarnings("CallToPrintStackTrace")
    private JPanel createStatutComboBox() {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panelcontenaire = new JPanel(new BorderLayout());
        statutComboBox = new JComboBox<>();
      
        try {
            List<ModelStatutDevis> statuts = DatabaseUtils.getAllStatutsDevis();
            for (ModelStatutDevis statut : statuts) {
                statutComboBox.addItem(statut.getStatut());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        statutComboBox.setPreferredSize(new Dimension(200, 33));
        panel.add(statutComboBox, BorderLayout.CENTER);
        //panelcontenaire.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 30));
        panelcontenaire.add(panel, BorderLayout.CENTER);
        setComboBoxRenderer(statutComboBox);
        return panelcontenaire;
    }

// Méthode utilitaire pour convertir une chaîne de pourcentage en double
    public static double parsePercent(String percentString) {
        return Double.parseDouble(percentString.replace("%", ""));
    }

    public static boolean isSubRow(DevisCreateTableModel tableModel, int rowIndex) {
        // On considère que c'est une sous-ligne si la colonne "Reference" est vide mais au moins une autre colonne n'est pas vide
        String reference = getStringValueAt(tableModel, rowIndex, 0);
        if (reference == null || reference.trim().isEmpty()) {
            // Vérifier les autres colonnes (excluant la colonne 0 "Reference")
            for (int col = 1; col < tableModel.getColumnCount(); col++) {
                Object value = tableModel.getValueAt(rowIndex, col);
                if (value != null && !value.toString().trim().isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isMainRow(DevisCreateTableModel tableModel, int rowIndex) {
        
        int produitId = getIntValueAt(tableModel, rowIndex, 0);


        String designation = getStringValueAt(tableModel, rowIndex, 13);
        BigDecimal prixUnitaire = getBigDecimalValueAt(tableModel, rowIndex, 14);
        int quantite = getIntValueAt(tableModel, rowIndex, 15);
        BigDecimal prixTotal = getBigDecimalValueAt(tableModel, rowIndex, 16);

        // Condition pour déterminer si c'est une ligne principale
        return produitId > 0 &&/* ProfileAluId > 0 && type_ouverture_id > 0 && vitrage_id > 0 && type_vitre_id > 0
                &&*/ designation != null && !designation.trim().isEmpty()
                && prixUnitaire != null && quantite > 0 && prixTotal != null;
    }

    public static String getStringValueAt(DevisCreateTableModel tableModel, int rowIndex, int columnIndex) {
        Object value = tableModel.getValueAt(rowIndex, columnIndex);
        return value != null ? value.toString() : "";
    }

    private int getIntValueAt(DevisCreateTableModel model, int row, int column) {
        Object value = model.getValueAt(row, column);
        if (value == null) {
            return 0;
        }
        String stringValue = value.toString();
        if (stringValue.isEmpty()) {
            return 0;
        }
        try {
            return Integer.parseInt(stringValue);
        } catch (NumberFormatException e) {
            // handle invalid input format
            return 0; // or some default value
        }
    }
   private Integer getIntegerValueAt(DevisCreateTableModel model, int row, int column) {
        Object value = model.getValueAt(row, column);
        if (value == null) {
            return null;
        }
        String stringValue = value.toString();
        if (stringValue.isEmpty()) {
            return null;
        }
        try {
            return Integer.valueOf(stringValue);
        } catch (NumberFormatException e) {
            // handle invalid input format
            return null; // or some default value
        }
    }
    private BigDecimal getBigDecimalValueAt(DevisCreateTableModel tableModel, int rowIndex, int columnIndex) {
        Object value = tableModel.getValueAt(rowIndex, columnIndex);
        return value != null && !value.toString().isEmpty() ? new BigDecimal(value.toString()) : BigDecimal.ZERO;
    }

    private void customizeTable(JTable table) {
        TableColumn column;

        // Masquer la colonne "Produit ID" (index 0)
        TableColumnModel columnModel = table.getColumnModel();

// Masquer les colonnes de 0 à 12
//        for (int i = 0; i <= 12 && i <=33; i++) {
//            
//            column = columnModel.getColumn(i);
//            column.setMinWidth(0);
//            column.setMaxWidth(0);
//            column.setPreferredWidth(0);
//            column.setWidth(0);
//            column.setResizable(false);
//        }
//         for (int i = 0; i <= 12; i++) {
//            
//            column = columnModel.getColumn(i);
//            column.setMinWidth(0);
//            column.setMaxWidth(0);
//            column.setPreferredWidth(0);
//            column.setWidth(0);
//            column.setResizable(false);
//        }
         //Personnaliser les autres colonnes
        for (int i = 0; i < table.getColumnCount(); i++) {
            column = table.getColumnModel().getColumn(i);
            switch (i) {
                case 13: { // Colonne "Designation"
                    column.setPreferredWidth(500); // Largeur préférée pour la colonne "Designation"
                       break;
    }
                         case 14: { // Colonne "Designation"
                     column.setPreferredWidth(50); // Largeur préférée pour la colonne "Designation"
                       break;
    }
                                     case 15: { // Colonne "Designation"
                     column.setPreferredWidth(50); // Largeur préférée pour la colonne "Designation"
                       break;
    }
                                                         case 16: { // Colonne "Designation"
                     column.setPreferredWidth(50); // Largeur préférée pour la colonne "Designation"
                       break;
    }
                case 46 : {
                    column.setMinWidth(200); // Ajustez selon vos besoins
                    column.setMaxWidth(150);
                                           break;
                }
                  default: { 
                     
            // column.setPreferredWidth(50);    
            column = columnModel.getColumn(i);
            column.setMinWidth(0);
            column.setMaxWidth(0);
            column.setPreferredWidth(0);
            column.setWidth(0);
            column.setResizable(false);
                       break;
                  } // Autres colonnes
            }
        }
        table.setPreferredScrollableViewportSize(new Dimension(800, 200));
        table.setFillsViewportHeight(true);
        table.setFont(new Font("Dialog", Font.PLAIN, 12));
        // Personnalisation des en-têtes de colonnes
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(26, 26, 26));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Dialog", Font.BOLD, 14));
        header.setReorderingAllowed(false);

        DefaultTableCellRenderer renderer = (DefaultTableCellRenderer) header.getDefaultRenderer();
        renderer.setHorizontalAlignment(JLabel.CENTER);

        table.setShowGrid(true);
        table.setGridColor(Color.GRAY);
        table.setRowHeight(30);

        table.getColumnModel().getColumn(0).setCellRenderer(new CenterCellRenderer()); // Colonne "Designation"
        table.getColumnModel().getColumn(13).setCellRenderer(new CenterCellRenderer()); // Colonne "Designation"
        table.getColumnModel().getColumn(14).setCellRenderer(new PriceCellRenderer()); // Prix Unitaire
        table.getColumnModel().getColumn(15).setCellRenderer(new CenterCellRenderer()); // Quantité
        table.getColumnModel().getColumn(16).setCellRenderer(new PriceCellRenderer()); // Prix Total

    }
private static class PriceCellRenderer extends DefaultTableCellRenderer {

    private final NumberFormat numberFormat;

    public PriceCellRenderer() {
        // Configure NumberFormat to use comma as thousand separator and dot for decimals
        numberFormat = NumberFormat.getNumberInstance(Locale.US);
      //  numberFormat.setGroupingUsed(true); // Enables thousand separator
        numberFormat.setMinimumFractionDigits(2);
        numberFormat.setMaximumFractionDigits(2);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        JLabel label = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        label.setHorizontalAlignment(JLabel.RIGHT);

        // Bold font for main rows
        DevisCreateTableModel tableModel = (DevisCreateTableModel) table.getModel();
        if (!tableModel.isSubRow(row)) {
            label.setFont(label.getFont().deriveFont(Font.BOLD));
        } else {
            label.setFont(label.getFont().deriveFont(Font.PLAIN));
        }

        // Format and set the text based on value type
        //if (value instanceof BigDecimal aBigDecimal) {
if (value instanceof BigDecimal) {
    BigDecimal aBigDecimal = (BigDecimal) value;

            label.setText(numberFormat.format(aBigDecimal) + " Ar");
        } else if (value instanceof String ) {
            String string=(String) value;
            label.setText(formatStringCurrency(string) + " Ar");
        } else {
            label.setText(value != null ? value.toString() : "");
        }

        return label;
    }

    // Helper method to convert String to BigDecimal and format
    private String formatStringCurrency(String value) {
        try {
            BigDecimal number = new BigDecimal(value);
            return numberFormat.format(number);
        } catch (NumberFormatException e) {
            return value; // Return original string if not numeric
        }
    }
}


    class CenterCellRenderer extends DefaultTableCellRenderer {

        public CenterCellRenderer() {
            super();
            setHorizontalAlignment(JLabel.CENTER); // Centrer le texte dans la cellule
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            // Récupérer le modèle de tableau pour vérifier si c'est une ligne principale
            DevisCreateTableModel tableModel = (DevisCreateTableModel) table.getModel();

            // Vérifier si c'est une ligne principale (main row)
            if (!tableModel.isSubRow(row)) {
                setFont(getFont().deriveFont(Font.BOLD)); // Mettre en gras
            } else {
                setFont(getFont().deriveFont(Font.PLAIN)); // Ne pas mettre en gras
            }

            return this;
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
