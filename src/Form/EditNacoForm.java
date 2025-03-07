/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Form;

/**
 *
 * @author brici_6ul2f65
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


/**
 *
 * @author brici_6ul2f65
 */
import Components.RoundedButton;
import Components.RoundedButton;
import ConnexionDB.DatabaseUtils;
import Models.LargeurAndHauteurExtractor;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import Panel.DevisCreatePanel;
import Panel.DevisCreatePanel;
import static Panel.DevisCreatePanel.parseProfileAluString;
import static Panel.DevisCreatePanel.parseTypeVitreString;
import static Panel.DevisCreatePanel.populateTypeVitreComboBox;
import static Panel.DevisCreatePanel.produitId;
import static Panel.DevisCreatePanel.selectComboBoxItem;
import static Panel.DevisCreatePanel.tableModel;
import Models.ModelChassis;
import Models.ModelClips;
import Models.ModelJoint;
import Models.ModelLameVerre;
import Models.ModelOperateur;
import Models.ModelPaumelle;
import Models.ModelPoignee;
import Models.ModelProduit;
import Models.ModelRivette;
import Models.ModelRoulette;
import Models.ModelSerrure;
import Models.ModelTypeVitre;
import Models.ModelVis;
import java.sql.SQLException;
import Models.ModelVitrage;
import static Panel.DevisCreatePanel.NJT;
import static Panel.DevisCreatePanel.SpinnerContainerPanelNaco;
import static Panel.DevisCreatePanel.addField;
import static Panel.DevisCreatePanel.calculateUnitPriceFenetreJalousieBattante;
import static Panel.DevisCreatePanel.calculateUnitPriceFenetreJalousieFixe;

import static Panel.DevisCreatePanel.configureSpinnerInteger;
import static Panel.DevisCreatePanel.fillComboBoxWithProducts;
import static Panel.DevisCreatePanel.getSpinnerValues;
import static Panel.DevisCreatePanel.hauteur;
import static Panel.DevisCreatePanel.largeur;
import static Panel.DevisCreatePanel.longueurChassisFixe;
import static Panel.DevisCreatePanel.longueurChassisOuvrant;
import static Panel.DevisCreatePanel.longueurTotalJoint;
import static Panel.DevisCreatePanel.nbClips;
import static Panel.DevisCreatePanel.nbElement;
import static Panel.DevisCreatePanel.nbLame;
import static Panel.DevisCreatePanel.nbPaumelle;
import static Panel.DevisCreatePanel.nbPoignee;
import static Panel.DevisCreatePanel.nbRivette;
import static Panel.DevisCreatePanel.nbRoulette;
import static Panel.DevisCreatePanel.nbSerrures;
import static Panel.DevisCreatePanel.nbVentaux;
import static Panel.DevisCreatePanel.nbVisAssemblage;
import static Panel.DevisCreatePanel.parseProfileAluString;
import static Panel.DevisCreatePanel.parseTypeVitreString;
import static Panel.DevisCreatePanel.populateComboBox;
import static Panel.DevisCreatePanel.populateLargeurLameComboBox;
import static Panel.DevisCreatePanel.populateModeleCouleurComboBox;
import static Panel.DevisCreatePanel.populateTypeVerreComboBox;
import static Panel.DevisCreatePanel.prixChassisFixeNaco;
import static Panel.DevisCreatePanel.prixClips;
import static Panel.DevisCreatePanel.prixLames;
import static Panel.DevisCreatePanel.prixOperateurBarre;
import static Panel.DevisCreatePanel.prixRivettes;
import static Panel.DevisCreatePanel.prixUnitaireChassisFixe;
import static Panel.DevisCreatePanel.prixUnitaireChassisOuvrant;
import static Panel.DevisCreatePanel.prixUnitaireJoint;
import static Panel.DevisCreatePanel.prixUnitairePaumelle;
import static Panel.DevisCreatePanel.prixUnitairePoignee;
import static Panel.DevisCreatePanel.prixUnitaireSerrure;
import static Panel.DevisCreatePanel.prixUnitaireVis;
import static Panel.DevisCreatePanel.prixUnitaireVitre;
import static Panel.DevisCreatePanel.prixVis;
import static Panel.DevisCreatePanel.produitId;
import static Panel.DevisCreatePanel.quantiteEnStockChassisFixe;
import static Panel.DevisCreatePanel.quantiteEnStockPaumelle;
import static Panel.DevisCreatePanel.quantiteEnStockPoignee;
import static Panel.DevisCreatePanel.quantiteEnStockVis;
import static Panel.DevisCreatePanel.quantiteLamme;
import static Panel.DevisCreatePanel.quantiteOperateurBarre;
import static Panel.DevisCreatePanel.setComboBoxRenderer;
import static Panel.DevisCreatePanel.tableModel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EditNacoForm extends JDialog {
    // Fenêtre

    private JComboBox<String> typeNacoComboBox;
    private JComboBox<String> modelesProfileCouleursNacoComboBox;

 

    private JComboBox<String> vitrageNacoComboBox;
    private JComboBox<String> typeVitreNacoComboBox;

    private JSpinner hauteurNacoSpinner;
    private JSpinner largeurNacoSpinner;
    private JSpinner quantiteNacoSpinner;

    private JPanel SpinnerContainerPanelNaco;
    private JScrollPane scrollPaneSousligneNaco;
    private JButton addSousLigneNacoButton;

    private final ArrayList<JButton> removeButtonsNaco = new ArrayList<>();
    private final RoundedButton saveButton;
    private final RoundedButton cancelButton;
    private int profileAluId;
    private int typeOuvertureId;
    private int vitrageId;
    private int typeVitreId;
    private Integer structureId = null;
//    private int profileAluIdtypeFixe;
//    private int profileAluIdtypeOuvrant;
    private int chassisTypeFixeId;
    private int chassisTypeOuvrantId;
   
    private int visId;
    private int jointId;
    private int serrureId;
     private Integer paumelleId;
    private Integer rouletteId;
    private Integer poigneeId;
    private int nbVentaux;
    private JSpinner nbVentauxNacoSpinner;
    private boolean allSelectedDefault;
    private boolean isfill;
    private BigDecimal largeur;
    private BigDecimal hauteur;
    private BigDecimal prixUnitaireSerrure;
    private int quantiteEnStockSerrure;
    private BigDecimal quantiteEnStockJoint;
    private BigDecimal prixUnitaireJoint;
    private BigDecimal prixUnitaireVitre;
    private BigDecimal quantiteEnStockChassisOuvrant;
    private BigDecimal prixUnitaireChassisOuvrant;
    private BigDecimal longueurChassisOuvrant;
    private BigDecimal quantiteEnStockChassisFixe;
    private BigDecimal prixUnitaireChassisFixe;
    private BigDecimal longueurChassisFixe;
    private BigDecimal prixUnitairePaumelle;
    private int quantiteEnStockPaumelle;
    private BigDecimal prixUnitairePoignee;
    private int quantiteEnStockPoignee;
    private BigDecimal prixUnitaireRoulette;
    private int quantiteEnStockRoulette;
    private BigDecimal prixUnitaireVis;
    private int quantiteEnStockVis;

    private int quantite;
    private BigDecimal unitePrice;
    private BigDecimal prixChassisFixe;
    private BigDecimal prixChassisOuvrant;
    private BigDecimal prixVitre;
    private BigDecimal prixJoint;
    private BigDecimal prixRoulette;
    private BigDecimal prixPaumelle;
    private BigDecimal prixSerrure;
    private BigDecimal prixPoignee;
    private Integer nbNacoRoulette;
    private JCheckBox moustiquairePorteCheckBox;
    private JCheckBox intermediairePorteCheckBox;
    private JCheckBox impostePorteCheckBox;
    private int nombreIntermediaires;
      private JComboBox<String>  verreNacoComboBox;
       private JComboBox<String> largeurLameNacoComboBox;
    private int lameVerreId;
    private Integer clipsId;
    private Integer rivetteId;
    private Integer operateurId;
    private BigDecimal prixUnitaireLame;
    private BigDecimal largeurLame;
    private BigDecimal prixUnitaireClips;
    private BigDecimal prixUnitaireRivettes;
    private BigDecimal prixUnitaireOperateurBarre;
    private BigDecimal quantiteBarreChassisOuvrantNaco;
    private BigDecimal longueurJoint;
    private BigDecimal prixJointNaco;
    private BigDecimal prixPaumelleNaco;
    private BigDecimal prixSerrureNaco;
    private BigDecimal prixPoigneeNaco;
    
//    private BigDecimal prixUnitaire;
    private BigDecimal prixChassisFixeNaco;
    private BigDecimal prixChassisOuvrantNaco;
    private BigDecimal prixRouletteNaco;
  
 
    private Integer ClipsId;
  
  
    private Integer LameVerreId;

    
    private Integer OperateurId;
   
   
    private Integer RivetteId;
    private Integer nbLameVerre;
    private BigDecimal prixLameVerre;
    private Integer nbOperateur;
    private BigDecimal prixOperateur;
    private BigDecimal prixRivette;

   
    

    public EditNacoForm(Component owner, String designation, List<Object[]> subRows, int selectedRow) throws Exception {

        super(SwingUtilities.getWindowAncestor(owner), "Modifier la Naco", Dialog.ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout());
        JPanel NacoPanel = createNacoPanel();

        fillNacoComboBoxes( selectedRow, subRows);
        fillSpinnersAndQuantity(subRows);

        add(NacoPanel, BorderLayout.CENTER);

        // Ajouter les boutons "Sauvegarder" et "Annuler"
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        Font buttonFont = new Font("Dialog", Font.BOLD, 14);
        Dimension buttonSize = new Dimension(150, 40);

        saveButton = new RoundedButton("Enregistrer", new Color(0, 74, 120), new Color(11, 81, 161));
        saveButton.setIcon(new ImageIcon(getClass().getResource("/Icon/save.png")));
        cancelButton = new RoundedButton("Annuler", new Color(28, 28, 28), new Color(0, 0, 0));
        cancelButton.setIcon(new ImageIcon(getClass().getResource("/Icon/cancel.png")));

        JButton[] buttons = {saveButton, cancelButton};

        for (JButton button : buttons) {
            button.setFont(buttonFont);
            button.setPreferredSize(buttonSize);
            button.setForeground(Color.WHITE); // Couleur du texte
        }
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);

        // Ajouter l'action pour le bouton "Sauvegarder"
        saveButton.addActionListener((ActionEvent e) -> {
            try {
                // Fermer la boîte de dialogue

                saveModification(selectedRow);
            } catch (Exception ex) {
                Logger.getLogger(EditNacoForm.class.getName()).log(Level.SEVERE, null, ex);
            }

        });

        // ActionListener pour le bouton Annuler
        cancelButton.addActionListener(e -> dispose());

        add(buttonPanel, BorderLayout.SOUTH);

        pack(); // Ajuste la taille de la fenêtre en fonction des composants
        setLocationRelativeTo(getOwner()); // Centre la fenêtre par rapport à son propriétaire
        getContentPane().requestFocusInWindow();
    }

    private JPanel createSpinnerNacoPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        SpinnerContainerPanelNaco = new JPanel();
        SpinnerContainerPanelNaco.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));

        addSousLigneNaco();

        scrollPaneSousligneNaco = new JScrollPane(SpinnerContainerPanelNaco);
        scrollPaneSousligneNaco.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPaneSousligneNaco.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneSousligneNaco.setPreferredSize(new Dimension(260, 236));
        scrollPaneSousligneNaco.setBorder(BorderFactory.createEmptyBorder());

        panel.add(scrollPaneSousligneNaco, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new BorderLayout());
        addSousLigneNacoButton = new JButton();
        addSousLigneNacoButton.setIcon(new ImageIcon(getClass().getResource("/Icon/icons8-add-16.png")));
        addSousLigneNacoButton.setHorizontalTextPosition(SwingConstants.CENTER);

        addSousLigneNacoButton.addActionListener((ActionEvent e) -> {

            addSousLigneNaco();

        });

  Dimension buttonSize = new Dimension(16, 16);

       addSousLigneNacoButton.setPreferredSize(buttonSize);

        buttonsPanel.add(addSousLigneNacoButton, BorderLayout.EAST);
        scrollPaneSousligneNaco.setColumnHeaderView(buttonsPanel);

        return panel;
    }

    private JPanel createSousLigneNacoPanel() {
        JPanel createsousLignePanel = new JPanel(new GridLayout(3, 2, 10, 10));
        createsousLignePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        largeurNacoSpinner = DevisCreatePanel.configureSpinnerBigDecimal();
        hauteurNacoSpinner = DevisCreatePanel.configureSpinnerBigDecimal();
        quantiteNacoSpinner = DevisCreatePanel.configureSpinnerInteger();

        DevisCreatePanel.addField(createsousLignePanel, "Largeur(mm):", largeurNacoSpinner);
        DevisCreatePanel.addField(createsousLignePanel, "Hauteur(mm):", hauteurNacoSpinner);
        DevisCreatePanel.addField(createsousLignePanel, "Quantité:", quantiteNacoSpinner);

        return createsousLignePanel;
    }

    private void addSousLigneNaco() {
    JPanel sousLignePanel = new JPanel(new BorderLayout(2,5));
        sousLignePanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        JPanel sousLigneContent = createSousLigneNacoPanel();
        sousLigneContent.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));
        sousLignePanel.add(sousLigneContent, BorderLayout.CENTER);

        JPanel sousLignePanelContenaire = new JPanel(new BorderLayout());
        sousLignePanelContenaire.add(sousLignePanel, BorderLayout.CENTER);
        sousLignePanelContenaire.setBorder(BorderFactory.createEmptyBorder(0, 2, 5, 5));

        JButton removeSousLigneButton = new JButton();
         removeSousLigneButton.setIcon(new ImageIcon(getClass().getResource("/Icon/minus.png")));
            Dimension buttonSize = new Dimension(16, 16);
        removeSousLigneButton.setPreferredSize(buttonSize);
        removeSousLigneButton.addActionListener((ActionEvent e) -> {
            DevisCreatePanel.removeSousLigne(sousLignePanelContenaire);
            removeButtonsNaco.remove(removeSousLigneButton);
            updateRemoveButtonsVisibilityNaco();
            updateScrollBarPolicyNaco();
            SpinnerContainerPanelNaco.requestFocusInWindow();
        });

        if (removeButtonsNaco.size() == 1) {
            removeSousLigneButton.setVisible(false);
        }
         JPanel removeButtonPanel = new JPanel(new BorderLayout());
        removeButtonPanel.add(removeSousLigneButton,BorderLayout.EAST);
        sousLignePanel.add(removeButtonPanel, BorderLayout.SOUTH);

        removeButtonsNaco.add(removeSousLigneButton);
        SpinnerContainerPanelNaco.add(sousLignePanelContenaire);
        updateRemoveButtonsVisibilityNaco();
        updateScrollBarPolicyNaco();
        SpinnerContainerPanelNaco.revalidate();
        SpinnerContainerPanelNaco.repaint();

        // Animer le défilement vers la fin à droite
        SwingUtilities.invokeLater(() -> animateScrollToRightNaco());
    }

    private void saveModification(int selectedRow) throws SQLException, Exception {
        try {
            // Trouver l'index de la ligne principale sélectionnée
            int mainRowIndex = tableModel.findMainRowIndexForSubRow(selectedRow);

            if (mainRowIndex == -1) {
                throw new Exception("Ligne principale non trouvée.");
            }
            
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

  
            // Récupérer la nouvelle désignation à partir de l'onglet approprié
            String newDesignation = buildNacoDesignation();  // ou buildNacoDesignation(), etc.

            // Mettre à jour la désignation de la ligne principale
            tableModel.setValueAt(produitId, mainRowIndex, 0);
            tableModel.setValueAt(newDesignation, mainRowIndex, 13); // Mise à jour de la colonne Désignation

            // Récupérer les valeurs des spinners et les dimensions
            List<List<JSpinner>> panelSpinners = DevisCreatePanel.getSpinnerValues(SpinnerContainerPanelNaco);
            List<Object[]> updatedSubRows = new ArrayList<>();

            for (int i = 0; i < panelSpinners.size(); i++) {
                List<JSpinner> spinners = panelSpinners.get(i);
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
             
                  unitePrice = calculateUnitPriceFenetreJalousieFixe( hauteur,  largeur,  largeurLame,longueurChassisFixe,
         prixUnitaireChassisFixe,  prixUnitaireLame,  prixUnitaireOperateurBarre,
         prixUnitaireClips,  prixUnitaireRivettes,
         prixUnitaireVis);

       
    } else  if (typeOuvertureId == 2) { //  battante 
         nbRoulette = null;
  unitePrice = calculateUnitPriceFenetreJalousieBattante(hauteur, largeur, nbVentaux, prixUnitaireChassisFixe,longueurChassisFixe,prixUnitaireChassisOuvrant,longueurChassisOuvrant,prixUnitaireVitre,prixUnitaireJoint,prixUnitaireVis,prixUnitairePaumelle,prixUnitaireSerrure,prixUnitairePoignee);

    }

                   String dimensions = largeur + " mm (Lt) x " + hauteur + " mm (Ht)";
                    BigDecimal totalPrice = unitePrice.multiply(new BigDecimal(quantite)) ;

                    // Ajouter les nouvelles sous-lignes mises à jour
       updatedSubRows.add(new Object[]{"", 
chassisTypeFixeId,
0, 
nbElement, 
vitrageId, lameVerreId,
null,
paumelleId ,
rouletteId,  
serrureId,
poigneeId, 
visId ,
0,
dimensions
,unitePrice,
quantite,
totalPrice,
null ,
    nbPaumelle,
      nbPoignee,
      nbVisAssemblage ,
        nbSerrures ,
 longueurJoint ,
  NJT,
null,
prixChassisFixeNaco,
null,
null,
prixJointNaco,
prixRouletteNaco,
prixPaumelleNaco,
prixSerrureNaco,
prixPoigneeNaco,
prixVis,
clipsId, 
  nbClips,
    prixClips,
    lameVerreId,
   quantiteLamme,
    prixLames,
    operateurId,
    quantiteOperateurBarre,
  prixOperateurBarre,
   rivetteId,
   nbRivette,
    prixRivettes
   
  
    });
//            tableModel.addOrUpdateRow(produitId, chassisTypeFixeId, 0, nbElement, vitrageId, 
//lameVerreId, structureId, paumelleId, rouletteId, serrureId, poigneeId, visId, jointId, designation,
//prixUnitaire, quantite, hauteur, largeur,nbRoulette,nbPaumelle,nbPoignee,nbVisAssemblage,nbSerrures,longueurJoint,NJT,
//rreChassisOuvrantNaco,prixChassisFixeNaco,null,prixLames,prixJointNaco,null,
//prixPaumelleNaco,prixSerrureNaco,prixPoigneeNaco,prixVis,
//                  clipsId,nbClips,prixClips,lameVerreId, quantiteLamme,prixLames,operateurId,
//quantiteOperateurBarre,prixOperateurBarre,rivetteId,nbRivette,prixRivettes);
//            resetNacoFields();
      //  }
               }
            }

            // Supprimer les anciennes sous-lignes
            List<Object[]> currentSubRows = tableModel.getSubRowsForMainRow(mainRowIndex);
            for (Object[] row : currentSubRows) {
                int rowIndex = tableModel.getRowIndex(row); // Méthode à ajouter dans le modèle pour obtenir l'index d'une ligne
                if (rowIndex != -1) {
                    tableModel.removeSubRow(rowIndex);
                }
            }

          
             // Ajouter les nouvelles sous-lignes
            for (Object[] subRow : updatedSubRows) {
    int chassisTypeFixeId = (int) subRow[1];
   // int chassisTypeOuvrantId = (int) subRow[2];
   //int nbVentaux = (int) subRow[3];
    int vitrageId = (int) subRow[4];
    int typeVitreId = (int) subRow[5];
     structureId = (Integer) subRow[6];
    Integer paumelleId = ( Integer) subRow[7];
    Integer rouletteId = ( Integer) subRow[8];
    int serrureId = (int) subRow[9];
    Integer poigneeId = ( Integer) subRow[10];
    int visId = (int) subRow[11];
    int jointId = (int) subRow[12];
    String dimensions = (String) subRow[13];
  BigDecimal prixUnitaire = (BigDecimal) subRow[14];
    int quantite = (int) subRow[15];
    //Integer nbroulette =  ( Integer) subRow[17];
    // Integer nbpaumelle =  ( Integer) subRow[18];
      //Integer nbpoignee =  ( Integer) subRow[19];
       Integer nbvis =  ( Integer) subRow[20];
        //Integer nbserrure =  ( Integer) subRow[21];
//BigDecimal longueurJoint = (BigDecimal) subRow[22];
BigDecimal qteChassisFixe = (BigDecimal) subRow[23];
//BigDecimal qteChassisOuvrant = (BigDecimal) subRow[24];
         BigDecimal prixChassisFixe  = (BigDecimal) subRow[25];
        // prixChassisOuvrant = (BigDecimal) subRow[26];
        BigDecimal prixVitre = (BigDecimal) subRow[27];
         //prixJoint =(BigDecimal) subRow[28];
         //prixRoulette=(BigDecimal) subRow[29];
         //prixPaumelle=(BigDecimal) subRow[30];
         //prixSerrure=(BigDecimal) subRow[31];
        // prixPoignee=(BigDecimal) subRow[32];
       BigDecimal  prixVis=(BigDecimal) subRow[33];
         
   Integer  ClipsId = (Integer) subRow[34];
       Integer  nbClips = (Integer) subRow[35];
       BigDecimal prixClips = (BigDecimal) subRow[36];
            
           Integer LameVerreId = (Integer) subRow[37];
       Integer nbLameVerre = (Integer) subRow[38];
        BigDecimal  prixLameVerre = (BigDecimal) subRow[39];
        
         
      Integer  OperateurId = (Integer) subRow[40];
          
   Integer nbOperateur = (Integer) subRow[41];
         BigDecimal prixOperateur =(BigDecimal) subRow[42];
         
          Integer RivetteId = (Integer) subRow[43];
      Integer nbRivette = (Integer) subRow[44];
  BigDecimal prixRivette =(BigDecimal) subRow[45];
  
                tableModel.addOrUpdateSubRow(mainRowIndex,chassisTypeFixeId, 0, nbElement, vitrageId, 0, structureId,0 ,0,  0,0, visId ,0,dimensions, prixUnitaire, quantite,0,0,0,nbvis,0,null,qteChassisFixe,null,prixChassisFixe,
 prixChassisOuvrant,
         prixVitre,
         prixJoint ,
         prixRoulette,
         prixPaumelle,
         prixSerrure,
         prixPoignee, 
         prixVis, 
         ClipsId, 
     nbClips, 
     prixClips, 
     LameVerreId, 
     nbLameVerre, 
     prixLameVerre, 
     OperateurId, 
     nbOperateur, 
     prixOperateur, 
     RivetteId, 
     nbRivette, 
     prixRivette);
            }
            
            // Mettre à jour les totaux de la ligne principale
            tableModel.updateMainRowTotals(mainRowIndex);

            // Mettre à jour les totaux globaux (si nécessaire)
            DevisCreatePanel.updateTotals();

            dispose();
            // Afficher un message de confirmation
            JOptionPane.showMessageDialog(null, "Données modifiées avec succès.");
        } catch (Exception ex) {
            Logger.getLogger(DevisCreatePanel.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, "Une erreur est survenue lors de la modification des données.");
        }
    }

    private JPanel createNacoPanel() throws Exception {
        JPanel NacoPanel = new JPanel(new BorderLayout());
        Color defaultColor = UIManager.getColor("ScrollBar.thumb");
        NacoPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, defaultColor));
      JPanel panelNacoOptionContenaire = new JPanel(new BorderLayout());
        JPanel panelNacoComboContenaire = new JPanel(new BorderLayout());
        JPanel panelNacoSpinnerContenaire = new JPanel(new BorderLayout());
        panelNacoOptionContenaire.add(createOptionsPanel(), BorderLayout.EAST);
        panelNacoComboContenaire.add(createNacoComboBoxPanel(), BorderLayout.NORTH);
        panelNacoSpinnerContenaire.add(createSpinnerNacoPanel(), BorderLayout.NORTH);
        NacoPanel.add(panelNacoComboContenaire, BorderLayout.WEST);
        NacoPanel.add(panelNacoSpinnerContenaire, BorderLayout.CENTER);
         NacoPanel.add(panelNacoOptionContenaire, BorderLayout.EAST);

        return NacoPanel;
    }
 
//    private JPanel createNacoComboBoxPanel() throws Exception {
//        JPanel panel = new JPanel(new GridLayout(1, 2, 25, 0));
//        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 0, 5));
//        GridBagConstraints gbc = new GridBagConstraints();
//        gbc.insets = new Insets(5, 5, 5, 5);
//        gbc.fill = GridBagConstraints.HORIZONTAL;
//
//        // Initialize JComboBoxes without data
//        typeNacoComboBox = new JComboBox<>();
//        modelesProfileCouleursNacoComboBox = new JComboBox<>();
//        nbVentauxNacoSpinner = configureSpinnerInteger();
//        vitrageNacoComboBox = new JComboBox<>();
//        typeVitreNacoComboBox = new JComboBox<>();
//
//        DevisCreatePanel.fillComboBoxWithProducts(typeNacoComboBox, "Naco");
//            //String selectedprofileCouleur = (String)modelesProfileCouleursNacoComboBox.getSelectedItem();
//        DevisCreatePanel.populateModeleCouleurComboBox(modelesProfileCouleursNacoComboBox);
//       
//
//        DevisCreatePanel.populateComboBox(vitrageNacoComboBox, "vitrage", "type");
//         vitrageNacoComboBox .addActionListener((ActionEvent e) -> {
//            String selectedVitrage = (String)  vitrageNacoComboBox.getSelectedItem();
////            updateTypeVitrePorteComboBox(selectedVitrage);
//            
//            if (selectedVitrage != null && !selectedVitrage.isEmpty()) {
//                try {
//                    DevisCreatePanel.populateTypeVitreComboBox( typeVitreNacoComboBox,selectedVitrage);
//                } catch (Exception ex) {
//                    Logger.getLogger(DevisCreatePanel.class.getName()).log(Level.SEVERE, null, ex);
//                }
//              
//              
//            } else {
//                // Vider les combo boxes si aucune couleur n'est sélectionnée
//                 typeVitreNacoComboBox.removeAllItems();
//                
//            }
//       
//        });
//
//        JPanel firstCol = new JPanel(new GridLayout(3, 1, 10, 10)); // 10 pixels horizontal gap
//
//        DevisCreatePanel.addField(firstCol, "Type de Fenêtre:", typeNacoComboBox);
//        DevisCreatePanel.addField(firstCol, "Modèle Profile:", modelesProfileCouleursNacoComboBox);
//        DevisCreatePanel.addField(firstCol, "Nombre de Ventaux:", nbVentauxNacoSpinner);
//
//        JPanel secondCol = new JPanel(new GridLayout(3, 1, 10, 10)); // 10 pixels horizontal gap
//        DevisCreatePanel.addField(secondCol, "Vitrage:", vitrageNacoComboBox);
//        DevisCreatePanel.addField(secondCol, "Type de Vitre:", typeVitreNacoComboBox);
//
//        gbc.gridx = 0;
//        gbc.gridy = 0;
//        panel.add(firstCol);
//
//        gbc.gridx++;
//        panel.add(secondCol);
//
////    gbc.gridy++;
////    panel.add(spacer, gbc); // Add spacer to create space between rows
//        return panel;
//    }

    private void fillSpinnersAndQuantity(List<Object[]> subRows) {
        // Trouver tous les JSpinner existants dans le panneau
        List<JSpinner> existingSpinners = new ArrayList<>();
        DevisCreatePanel.findSpinners(SpinnerContainerPanelNaco, existingSpinners);

        // Index pour les spinners existants
        int existingSpinnerIndex = 0;

        for (Object[] subRow : subRows) {
            String dimensions = (String) subRow[13];
            LargeurAndHauteurExtractor.LargeurAndHauteur dim = LargeurAndHauteurExtractor.extractDimensionsFromDesignation(dimensions);

            int quantite = (int) subRow[15];//8
 //int ventaux = (int) subRow[3];
            //  nbVentauxNacoSpinner.setValue(ventaux ); 
            // Si nous avons des spinners existants, les réutiliser
            if (existingSpinnerIndex < existingSpinners.size()) {
                largeurNacoSpinner = existingSpinners.get(existingSpinnerIndex);
                hauteurNacoSpinner = existingSpinners.get(existingSpinnerIndex + 1);
                quantiteNacoSpinner = existingSpinners.get(existingSpinnerIndex + 2);

               largeurNacoSpinner.setValue(dim.largeur.doubleValue());
               hauteurNacoSpinner.setValue(dim.hauteur.doubleValue());

                quantiteNacoSpinner.setValue(quantite);

                existingSpinnerIndex += 3; // Passer aux prochains spinners
            } else {
                // Ajouter un nouveau panneau de spinners si aucun spinner existant n'est disponible
                addSousLigneNaco();

                JPanel dernierPanneauSousLigne = (JPanel) SpinnerContainerPanelNaco.getComponent(SpinnerContainerPanelNaco.getComponentCount() - 1);
                List<JSpinner> spinners = new ArrayList<>();
                DevisCreatePanel.findSpinners(dernierPanneauSousLigne, spinners);

                // Vérifier que nous avons trouvé exactement trois spinners
                if (spinners.size() >= 3) {
                    largeurNacoSpinner = spinners.get(0);
                    hauteurNacoSpinner = spinners.get(1);
                    quantiteNacoSpinner = spinners.get(2);

                    largeurNacoSpinner.setValue(dim.largeur.doubleValue());
                    hauteurNacoSpinner.setValue(dim.hauteur.doubleValue());
                    quantiteNacoSpinner.setValue(quantite);
                } else {
                    System.err.println("Le panneau ne contient pas suffisamment de composants : " + spinners.size());
                }
            }
        }
    }
// Méthode pour sélectionner un item dans un ComboBox
private void selectComboBoxItem(JComboBox<String> comboBox, String value) {
    if (value != null && !value.isEmpty()) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            if (comboBox.getItemAt(i).equalsIgnoreCase(value)) {
                comboBox.setSelectedIndex(i);
                return;
            }
        }
    }
}

    private JPanel createNacoComboBoxPanel() throws Exception {
   JPanel panel = new JPanel(new GridLayout(1, 3, 0, 0));
        //Color defaultColor = UIManager.getColor("ScrollBar.thumb");
       // panel.setBorder(BorderFactory.createMatteBorder(1, 0, 1, 1, defaultColor));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize JComboBoxes without data
        typeNacoComboBox = new JComboBox<>();
        modelesProfileCouleursNacoComboBox = new JComboBox<>();
        vitrageNacoComboBox = new JComboBox<>();
      verreNacoComboBox= new JComboBox<>();
        verreNacoComboBox.addItem("Sélectionnez le vitrage d'abord");
        setComboBoxRenderer(verreNacoComboBox);
        largeurLameNacoComboBox = new JComboBox<>();
        
       // populateLargeurLameComboBox(largeurLameNacoComboBox);
        fillComboBoxWithProducts(typeNacoComboBox, "Naco");
        populateModeleCouleurComboBox(modelesProfileCouleursNacoComboBox);
  
        largeurLameNacoComboBox.addItem("Sélectionnez le type de verre");
        setComboBoxRenderer(largeurLameNacoComboBox);
        populateComboBox(vitrageNacoComboBox, "vitrage", "type");
        vitrageNacoComboBox.addActionListener((ActionEvent e) -> {
            String selectedVitrage = (String) vitrageNacoComboBox.getSelectedItem();
            
//            updateTypeVitrePorteComboBox(selectedVitrage);

            if (selectedVitrage != null && !selectedVitrage.isEmpty()) {
                try {
                    populateTypeVerreComboBox(verreNacoComboBox, selectedVitrage);
                } catch (Exception ex) {
                    Logger.getLogger(DevisCreatePanel.class.getName()).log(Level.SEVERE, null, ex);
                }

            } 

        });
  verreNacoComboBox.addActionListener((ActionEvent e) -> {
    String selectedVerre = (String) verreNacoComboBox.getSelectedItem();

    // Réinitialiser largeurLameNacoComboBox
    largeurLameNacoComboBox.removeAllItems();
    largeurLameNacoComboBox.addItem("Sélectionnez le type de verre");

    if (selectedVerre != null && !selectedVerre.equals("Sélectionnez le vitrage d'abord") && !selectedVerre.isEmpty()) {
        try {
            // Parse le type de verre pour récupérer ses détails
            String[] typeVitreParts = parseTypeVitreString(selectedVerre);
            if (typeVitreParts != null && typeVitreParts.length == 2) {
                String typeVitre = typeVitreParts[0];
                int epaisseur = Integer.parseInt(typeVitreParts[1]);
                populateLargeurLameComboBox(largeurLameNacoComboBox, epaisseur, typeVitre);
            } else {
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
      

            panel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
              panel.requestFocusInWindow();
             
               
            }
        });
        return panel;
    } 
  private void fillNacoComboBoxes(int selectedRow, List<Object[]> subRows) throws SQLException {
    // Récupérer l'ID à partir de la colonne 0 du mainRow (TableModel)
    Integer produitId = (Integer) tableModel.getValueAt(selectedRow, 0); // Colonne 0 contient l'ID
  

    // Vérifier si l'ID est valide (non nul)
    if (produitId != null) {
        // Récupérer le nom du produit associé à cet ID dans le ModelProduit
        ModelProduit produit = DatabaseUtils.getProduitById(produitId);
        if (produit != null) {
            String nomProduit = produit.getNom(); // Nom du produit à afficher

            // Sélectionner les éléments dans les combo boxes en utilisant le nom du produit
            selectComboBoxItem(typeNacoComboBox, nomProduit);
      
        } else {
            System.out.println("Produit non trouvé pour l'ID: " + produitId);
        }
    } else {
        System.out.println("L'ID de produit est nul dans la ligne: " + selectedRow);
    }
    
     for (Object[] subRow : subRows) {
 
     Integer vitrageId = (Integer) subRow[4];
      if (vitrageId != null) {
        // Récupérer le nom du produit associé à cet ID dans le ModelProduit
        ModelVitrage vitre = DatabaseUtils.getVitrageById(vitrageId );
        if ( vitre!= null) {
            String nomStructure =  vitre.getTypeVitrage(); // Nom du produit à afficher


            selectComboBoxItem(vitrageNacoComboBox,  nomStructure );

        } else {
            System.out.println("SRTUCTURE non trouvé pour l'ID: " + structureId);
        }
    } else {
        System.out.println("L'ID de SRTUCTURE est nul dans la ligne: " + selectedRow);
    }
        Integer lameVerreId = (Integer) subRow[37];
      if (lameVerreId != null) {
        // Récupérer le nom du produit associé à cet ID dans le ModelProduit
        ModelLameVerre typevitre = DatabaseUtils.getLameVerreById(lameVerreId );
        if ( typevitre!= null) {
            String typeEpaisseur =  typevitre.toStringTypeEpesseur(); // Nom du produit à afficher
            String lageur =  typevitre.toString(); 
            selectComboBoxItem(verreNacoComboBox,  typeEpaisseur );
            selectComboBoxItem(largeurLameNacoComboBox,  lageur );
    
        } else {
            System.out.println("SRTUCTURE non trouvé pour l'ID: " + structureId);
        }
    } else {
        System.out.println("L'ID de SRTUCTURE est nul dans la ligne: " + selectedRow);
    }
      int chassisFixeId = (int) subRow[1];
     
      if (chassisFixeId!= -1) {
        // Récupérer le nom du produit associé à cet ID dans le ModelProduit
         ModelChassis chassifixe = DatabaseUtils.getChassisById(chassisFixeId );
        
        if (chassifixe!= null) {
             int profilechassisfixeId =chassifixe.getProfile().getProfileAluId();
         
             if( profilechassisfixeId!=-1){
                   String CouleurModel =  chassifixe.getProfile().toString(); // Nom du produit à afficher
                     selectComboBoxItem(modelesProfileCouleursNacoComboBox,  CouleurModel );
             }

        } else {
            System.out.println("SRTUCTURE non trouvé pour l'ID: " + structureId);
        }
    } else {
        System.out.println("L'ID de SRTUCTURE est nul dans la ligne: " + selectedRow);
    }
      
}
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

    private void updateRemoveButtonsVisibilityNaco() {
        // Supposons que vous avez une liste de boutons de suppression pour les sous-lignes
        for (JButton button : removeButtonsNaco) {
            button.setVisible(removeButtonsNaco.size() > 1);
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
 private void ouvrirDialogueIntermediaire() {
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
    } 
});

        // Ajouter les checkboxes dans le panel
        optionsPanel.add(moustiquairePorteCheckBox);
        optionsPanel.add(intermediairePorteCheckBox);
        optionsPanel.add(impostePorteCheckBox);
   

        return optionsPanel;
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

    if (nbLame != null && nbLame.compareTo(BigDecimal.ZERO) > 0) {
        // Arrondi au nombre entier supérieur
        BigDecimal roundedNL = nbLame.setScale(0, RoundingMode.CEILING);
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

    // Additional helper methods for configuring UI components (omitted for brevity)
}
