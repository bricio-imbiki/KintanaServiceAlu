/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Form;

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
import Models.ModelJoint;
import Models.ModelPaumelle;
import Models.ModelPoignee;
import Models.ModelProduit;
import Models.ModelRoulette;
import Models.ModelSerrure;
import Models.ModelTypeVitre;
import Models.ModelVis;
import java.sql.SQLException;
import Models.ModelVitrage;
import static Panel.DevisCreatePanel.calculateUnitPriceCloisonFixe;

import static Panel.DevisCreatePanel.configureSpinnerInteger;
import static Panel.DevisCreatePanel.intermediairePorteCheckBox;
import static Panel.DevisCreatePanel.longueurTotalJoint;
import static Panel.DevisCreatePanel.nbPaumelle;
import static Panel.DevisCreatePanel.nbPoignee;
import static Panel.DevisCreatePanel.nbRoulette;
import static Panel.DevisCreatePanel.nbSerrures;
import static Panel.DevisCreatePanel.nbVisAssemblage;
import static Panel.DevisCreatePanel.parseProfileAluString;
import static Panel.DevisCreatePanel.parseTypeVitreString;
import static Panel.DevisCreatePanel.prixChassisFixeCloison;
import static Panel.DevisCreatePanel.prixChassisOuvrantCloison;
import static Panel.DevisCreatePanel.prixJointCloison;
import static Panel.DevisCreatePanel.prixVisAssemblageCloison;
import static Panel.DevisCreatePanel.prixVitreCloison;
import static Panel.DevisCreatePanel.produitId;
import static Panel.DevisCreatePanel.quantiteBarreChassisFixeCloison;
import static Panel.DevisCreatePanel.quantiteBarreChassisOuvrantCloison;
import static Panel.DevisCreatePanel.setComboBoxRenderer;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EditCloisonForm extends JDialog {
    // Fenêtre

    private JComboBox<String> typeCloisonComboBox;
    private JComboBox<String> modelesProfileCouleursCloisonComboBox;

 

    private JComboBox<String> vitrageCloisonComboBox;
    private JComboBox<String> typeVitreCloisonComboBox;

    private JSpinner hauteurCloisonSpinner;
    private JSpinner largeurCloisonSpinner;
    private JSpinner quantiteCloisonSpinner;

    private JPanel SpinnerContainerPanelCloison;
    private JScrollPane scrollPaneSousligneCloison;
    private JButton addSousLigneCloisonButton;

    private final ArrayList<JButton> removeButtonsCloison = new ArrayList<>();
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
    private JSpinner nbVentauxCloisonSpinner;
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
    private BigDecimal prixVis;
    private Integer nbCloisonRoulette;
    private JCheckBox moustiquairePorteCheckBox;
    private JCheckBox intermediairePorteCheckBox;
    private JCheckBox impostePorteCheckBox;
    private int nombreIntermediaires;
    

    public EditCloisonForm(Component owner, String designation, List<Object[]> subRows, int selectedRow) throws Exception {

        super(SwingUtilities.getWindowAncestor(owner), "Modifier la Cloison", Dialog.ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout());
        JPanel CloisonPanel = createCloisonPanel();

        fillCloisonComboBoxes( selectedRow, subRows);
        fillSpinnersAndQuantity(subRows);

        add(CloisonPanel, BorderLayout.CENTER);

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
            // Fermer la boîte de dialogue

            saveModification(selectedRow);

        });

        // ActionListener pour le bouton Annuler
        cancelButton.addActionListener(e -> dispose());

        add(buttonPanel, BorderLayout.SOUTH);

        pack(); // Ajuste la taille de la fenêtre en fonction des composants
        setLocationRelativeTo(getOwner()); // Centre la fenêtre par rapport à son propriétaire
        getContentPane().requestFocusInWindow();
    }

    private JPanel createSpinnerCloisonPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        SpinnerContainerPanelCloison = new JPanel();
        SpinnerContainerPanelCloison.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));

        addSousLigneCloison();

        scrollPaneSousligneCloison = new JScrollPane(SpinnerContainerPanelCloison);
        scrollPaneSousligneCloison.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPaneSousligneCloison.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneSousligneCloison.setPreferredSize(new Dimension(260, 236));
        scrollPaneSousligneCloison.setBorder(BorderFactory.createEmptyBorder());

        panel.add(scrollPaneSousligneCloison, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new BorderLayout());
        addSousLigneCloisonButton = new JButton();
        addSousLigneCloisonButton.setIcon(new ImageIcon(getClass().getResource("/Icon/icons8-add-16.png")));
        addSousLigneCloisonButton.setHorizontalTextPosition(SwingConstants.CENTER);

        addSousLigneCloisonButton.addActionListener((ActionEvent e) -> {

            addSousLigneCloison();

        });

  Dimension buttonSize = new Dimension(16, 16);

       addSousLigneCloisonButton.setPreferredSize(buttonSize);

        buttonsPanel.add(addSousLigneCloisonButton, BorderLayout.EAST);
        scrollPaneSousligneCloison.setColumnHeaderView(buttonsPanel);

        return panel;
    }

    private JPanel createSousLigneCloisonPanel() {
        JPanel createsousLignePanel = new JPanel(new GridLayout(3, 2, 10, 10));
        createsousLignePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        largeurCloisonSpinner = DevisCreatePanel.configureSpinnerBigDecimal();
        hauteurCloisonSpinner = DevisCreatePanel.configureSpinnerBigDecimal();
        quantiteCloisonSpinner = DevisCreatePanel.configureSpinnerInteger();

        DevisCreatePanel.addField(createsousLignePanel, "Largeur(mm):", largeurCloisonSpinner);
        DevisCreatePanel.addField(createsousLignePanel, "Hauteur(mm):", hauteurCloisonSpinner);
        DevisCreatePanel.addField(createsousLignePanel, "Quantité:", quantiteCloisonSpinner);

        return createsousLignePanel;
    }

    private void addSousLigneCloison() {
    JPanel sousLignePanel = new JPanel(new BorderLayout(2,5));
        sousLignePanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        JPanel sousLigneContent = createSousLigneCloisonPanel();
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
            removeButtonsCloison.remove(removeSousLigneButton);
            updateRemoveButtonsVisibilityCloison();
            updateScrollBarPolicyCloison();
            SpinnerContainerPanelCloison.requestFocusInWindow();
        });

        if (removeButtonsCloison.size() == 1) {
            removeSousLigneButton.setVisible(false);
        }
         JPanel removeButtonPanel = new JPanel(new BorderLayout());
        removeButtonPanel.add(removeSousLigneButton,BorderLayout.EAST);
        sousLignePanel.add(removeButtonPanel, BorderLayout.SOUTH);

        removeButtonsCloison.add(removeSousLigneButton);
        SpinnerContainerPanelCloison.add(sousLignePanelContenaire);
        updateRemoveButtonsVisibilityCloison();
        updateScrollBarPolicyCloison();
        SpinnerContainerPanelCloison.revalidate();
        SpinnerContainerPanelCloison.repaint();

        // Animer le défilement vers la fin à droite
        SwingUtilities.invokeLater(() -> animateScrollToRightCloison());
    }

    private void saveModification(int selectedRow) {
        try {
            // Trouver l'index de la ligne principale sélectionnée
            int mainRowIndex = tableModel.findMainRowIndexForSubRow(selectedRow);

            if (mainRowIndex == -1) {
                throw new Exception("Ligne principale non trouvée.");
            }
          
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
            jointId = DatabaseUtils.getJointId(model, couleur);
            serrureId = DatabaseUtils.getJointId(model, couleur);
            // rouletteId = DatabaseUtils.getRouletteId(model, couleur);
            vitrageId = DatabaseUtils.getIdFromChoice(selectedVitrage, "vitrage", "type", "vitrage_id");
            typeVitreId = DatabaseUtils.getTypeVitreId(type, epaisseur);
           
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
          

             ModelVis vis = DatabaseUtils.getVisById(visId);
            if (vis == null) {
                JOptionPane.showMessageDialog(null, "Aucun prix unitaire trouvé pour la vis", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }else{
                   prixUnitaireVis = vis.getPrixUnitaire();
                   quantiteEnStockVis = vis.getQuantiteEnStock();

            }

            // Récupérer la nouvelle désignation à partir de l'onglet approprié
            String newDesignation = buildCloisonDesignation();  // ou buildCloisonDesignation(), etc.

            // Mettre à jour la désignation de la ligne principale
            tableModel.setValueAt(produitId, mainRowIndex, 0);
            tableModel.setValueAt(newDesignation, mainRowIndex, 13); // Mise à jour de la colonne Désignation

            // Récupérer les valeurs des spinners et les dimensions
            List<List<JSpinner>> panelSpinners = DevisCreatePanel.getSpinnerValues(SpinnerContainerPanelCloison);
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
              
                      
                      nbPoignee = null;
                       nbPaumelle = null;
                        unitePrice = calculateUnitPriceCloisonFixe( hauteur,  largeur,  nombreIntermediaires, prixUnitaireChassisFixe,  longueurChassisFixe,  prixUnitaireChassisOuvrant,  longueurChassisOuvrant,  prixUnitaireVitre,  prixUnitaireJoint,  prixUnitaireVis) ;

           
       
                   String dimensions = largeur + " mm (Lt) x " + hauteur + " mm (Ht)";
                    BigDecimal totalPrice = unitePrice.multiply(new BigDecimal(quantite)) ;

                    // Ajouter les nouvelles sous-lignes mises à jour
                     updatedSubRows.add(new Object[]{"", chassisTypeFixeId, chassisTypeOuvrantId, 0, vitrageId, typeVitreId, structureId,paumelleId ,rouletteId,  serrureId,poigneeId, visId ,jointId,dimensions, unitePrice, quantite, totalPrice,nbRoulette ,
    nbPaumelle,
      nbPoignee,
      nbVisAssemblage ,
        nbSerrures ,
 longueurTotalJoint ,
  quantiteBarreChassisFixeCloison,quantiteBarreChassisOuvrantCloison,prixChassisFixeCloison,prixChassisOuvrantCloison,prixVitreCloison,prixJointCloison,null,null,null,null,prixVisAssemblageCloison,
     null, 
     null, 
     null, 
     null, 
     null, 
     null, 
     null, 
     null, 
     null, 
     null, 
     null, 
     null});
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
    int chassisTypeOuvrantId = (int) subRow[2];
    int nbVentaux = (int) subRow[3];
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
    Integer nbroulette = null;
     Integer nbpaumelle =  null;
      Integer nbpoignee = null;
       Integer nbvis =  ( Integer) subRow[20];
        Integer nbserrure = null;
BigDecimal longueurJoint = (BigDecimal) subRow[22];
BigDecimal qteChassisFixe = (BigDecimal) subRow[23];
BigDecimal qteChassisOuvrant = (BigDecimal) subRow[24];
         prixChassisFixe  = (BigDecimal) subRow[25];
          prixChassisOuvrant = (BigDecimal) subRow[26];
         prixVitre = (BigDecimal) subRow[27];
         prixJoint =(BigDecimal) subRow[28];
         prixRoulette= null;
         prixPaumelle= null;
         prixSerrure=null;
         prixPoignee=null;
         prixVis=null;
              tableModel.addOrUpdateSubRow(mainRowIndex,chassisTypeFixeId, chassisTypeOuvrantId, nbVentaux, vitrageId, typeVitreId, structureId,paumelleId ,rouletteId,  serrureId,poigneeId, visId ,jointId,dimensions, prixUnitaire, quantite,nbroulette,nbpaumelle,nbpoignee,nbvis,nbserrure,longueurJoint,qteChassisFixe,qteChassisOuvrant,prixChassisFixe,
 prixChassisOuvrant,
         prixVitre,
         prixJoint ,
         prixRoulette,
         prixPaumelle,
         prixSerrure,
         prixPoignee, 
         prixVis, 
         null, 
     null, 
     null, 
     null, 
     null, 
     null, 
     null, 
     null, 
     null, 
     null, 
     null, 
     null);
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

    private JPanel createCloisonPanel() throws Exception {
        JPanel CloisonPanel = new JPanel(new BorderLayout());
        Color defaultColor = UIManager.getColor("ScrollBar.thumb");
        CloisonPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, defaultColor));
      JPanel panelCloisonOptionContenaire = new JPanel(new BorderLayout());
        JPanel panelCloisonComboContenaire = new JPanel(new BorderLayout());
        JPanel panelCloisonSpinnerContenaire = new JPanel(new BorderLayout());
        panelCloisonOptionContenaire.add(createOptionsPanel(), BorderLayout.EAST);
        panelCloisonComboContenaire.add(createCloisonComboBoxPanel(), BorderLayout.NORTH);
        panelCloisonSpinnerContenaire.add(createSpinnerCloisonPanel(), BorderLayout.NORTH);
        CloisonPanel.add(panelCloisonComboContenaire, BorderLayout.WEST);
        CloisonPanel.add(panelCloisonSpinnerContenaire, BorderLayout.CENTER);
         CloisonPanel.add(panelCloisonOptionContenaire, BorderLayout.EAST);

        return CloisonPanel;
    }

    private JPanel createCloisonComboBoxPanel() throws Exception {
        JPanel panel = new JPanel(new GridLayout(1, 2, 25, 0));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 0, 5));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Initialize JComboBoxes without data
        typeCloisonComboBox = new JComboBox<>();
        modelesProfileCouleursCloisonComboBox = new JComboBox<>();
        
        vitrageCloisonComboBox = new JComboBox<>();
        typeVitreCloisonComboBox = new JComboBox<>();
  typeVitreCloisonComboBox.addItem("Sélectionnez le vitrage d'abord");
         setComboBoxRenderer(typeVitreCloisonComboBox);
        DevisCreatePanel.fillComboBoxWithProducts(typeCloisonComboBox, "Cloison");
            //String selectedprofileCouleur = (String)modelesProfileCouleursCloisonComboBox.getSelectedItem();
        DevisCreatePanel.populateModeleCouleurComboBox(modelesProfileCouleursCloisonComboBox);
       

        DevisCreatePanel.populateComboBox(vitrageCloisonComboBox, "vitrage", "type");
         vitrageCloisonComboBox .addActionListener((ActionEvent e) -> {
               typeVitreCloisonComboBox.removeAllItems();
               typeVitreCloisonComboBox.addItem("Sélectionnez le vitrage d'abord");
            String selectedVitrage = (String)  vitrageCloisonComboBox.getSelectedItem();
//            updateTypeVitrePorteComboBox(selectedVitrage);
            
            if (selectedVitrage != null && !selectedVitrage.isEmpty()) {
                try {
                    DevisCreatePanel.populateTypeVitreComboBox( typeVitreCloisonComboBox,selectedVitrage);
                } catch (Exception ex) {
                    Logger.getLogger(DevisCreatePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
              
              
            } else {
                // Vider les combo boxes si aucune couleur n'est sélectionnée
                // typeVitreCloisonComboBox.removeAllItems();
                
            }
       
        });

        JPanel firstCol = new JPanel(new GridLayout(3, 1, 10, 10)); // 10 pixels horizontal gap

        DevisCreatePanel.addField(firstCol, "Type de Fenêtre:", typeCloisonComboBox);
        DevisCreatePanel.addField(firstCol, "Modèle Profile:", modelesProfileCouleursCloisonComboBox);
        JPanel secondCol = new JPanel(new GridLayout(3, 1, 10, 10)); // 10 pixels horizontal gap
        DevisCreatePanel.addField(secondCol, "Vitrage:", vitrageCloisonComboBox);
        DevisCreatePanel.addField(secondCol, "Type de Vitre:", typeVitreCloisonComboBox);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(firstCol);

        gbc.gridx++;
        panel.add(secondCol);

//    gbc.gridy++;
//    panel.add(spacer, gbc); // Add spacer to create space between rows
        return panel;
    }

    private void fillSpinnersAndQuantity(List<Object[]> subRows) {
        // Trouver tous les JSpinner existants dans le panneau
        List<JSpinner> existingSpinners = new ArrayList<>();
        DevisCreatePanel.findSpinners(SpinnerContainerPanelCloison, existingSpinners);

        // Index pour les spinners existants
        int existingSpinnerIndex = 0;

        for (Object[] subRow : subRows) {
            String dimensions = (String) subRow[13];
            LargeurAndHauteurExtractor.LargeurAndHauteur dim = LargeurAndHauteurExtractor.extractDimensionsFromDesignation(dimensions);

            int quantite = (int) subRow[15];//8

            // Si nous avons des spinners existants, les réutiliser
            if (existingSpinnerIndex < existingSpinners.size()) {
                largeurCloisonSpinner = existingSpinners.get(existingSpinnerIndex);
                hauteurCloisonSpinner = existingSpinners.get(existingSpinnerIndex + 1);
                quantiteCloisonSpinner = existingSpinners.get(existingSpinnerIndex + 2);

               largeurCloisonSpinner.setValue(dim.largeur.doubleValue());
               hauteurCloisonSpinner.setValue(dim.hauteur.doubleValue());

                quantiteCloisonSpinner.setValue(quantite);

                existingSpinnerIndex += 3; // Passer aux prochains spinners
            } else {
                // Ajouter un nouveau panneau de spinners si aucun spinner existant n'est disponible
                addSousLigneCloison();

                JPanel dernierPanneauSousLigne = (JPanel) SpinnerContainerPanelCloison.getComponent(SpinnerContainerPanelCloison.getComponentCount() - 1);
                List<JSpinner> spinners = new ArrayList<>();
                DevisCreatePanel.findSpinners(dernierPanneauSousLigne, spinners);

                // Vérifier que nous avons trouvé exactement trois spinners
                if (spinners.size() >= 3) {
                    largeurCloisonSpinner = spinners.get(0);
                    hauteurCloisonSpinner = spinners.get(1);
                    quantiteCloisonSpinner = spinners.get(2);

                    largeurCloisonSpinner.setValue(dim.largeur.doubleValue());
                    hauteurCloisonSpinner.setValue(dim.hauteur.doubleValue());
                    quantiteCloisonSpinner.setValue(quantite);
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
  private void fillCloisonComboBoxes(int selectedRow, List<Object[]> subRows) throws SQLException {
    // Récupérer l'ID à partir de la colonne 0 du mainRow (TableModel)
    Integer produitId = (Integer) tableModel.getValueAt(selectedRow, 0); // Colonne 0 contient l'ID
  

    // Vérifier si l'ID est valide (non nul)
    if (produitId != null) {
        // Récupérer le nom du produit associé à cet ID dans le ModelProduit
        ModelProduit produit = DatabaseUtils.getProduitById(produitId);
        if (produit != null) {
            String nomProduit = produit.getNom(); // Nom du produit à afficher

            // Sélectionner les éléments dans les combo boxes en utilisant le nom du produit
            selectComboBoxItem(typeCloisonComboBox, nomProduit);
      
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


            selectComboBoxItem(vitrageCloisonComboBox,  nomStructure );

        } else {
            System.out.println("SRTUCTURE non trouvé pour l'ID: " + structureId);
        }
    } else {
        System.out.println("L'ID de SRTUCTURE est nul dans la ligne: " + selectedRow);
    }
       int typevitreId = (int) subRow[5];
      if (typevitreId != -1) {
        // Récupérer le nom du produit associé à cet ID dans le ModelProduit
        ModelTypeVitre typevitre = DatabaseUtils.getTypeVitreById(vitrageId );
        if ( typevitre!= null) {
            String typeEpaisseur =  typevitre.toString(); // Nom du produit à afficher

            selectComboBoxItem(typeVitreCloisonComboBox,  typeEpaisseur );
    
        } else {
            System.out.println("SRTUCTURE non trouvé pour l'ID: " + structureId);
        }
    } else {
        System.out.println("L'ID de SRTUCTURE est nul dans la ligne: " + selectedRow);
    }
      int chassisFixeId = (int) subRow[1];
      int chassisOuvrantId = (int) subRow[2];
      if (chassisFixeId!= -1&&chassisOuvrantId!= -1) {
        // Récupérer le nom du produit associé à cet ID dans le ModelProduit
         ModelChassis chassifixe = DatabaseUtils.getChassisById(chassisFixeId );
         ModelChassis chassisouvrant = DatabaseUtils.getChassisById(chassisOuvrantId );
        if (chassifixe!= null&&chassisouvrant!= null) {
             int profilechassisfixeId =chassifixe.getProfile().getProfileAluId();
             int profilechassisouvrantId =chassisouvrant.getProfile().getProfileAluId();
             if( profilechassisfixeId!=-1&&profilechassisouvrantId!=-1&& profilechassisouvrantId ==profilechassisfixeId ){
                   String CouleurModel =  chassifixe.getProfile().toString(); // Nom du produit à afficher
                     selectComboBoxItem(modelesProfileCouleursCloisonComboBox,  CouleurModel );
             }

        } else {
            System.out.println("SRTUCTURE non trouvé pour l'ID: " + structureId);
        }
    } else {
        System.out.println("L'ID de SRTUCTURE est nul dans la ligne: " + selectedRow);
    }
      
}
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

    private void updateRemoveButtonsVisibilityCloison() {
        // Supposons que vous avez une liste de boutons de suppression pour les sous-lignes
        for (JButton button : removeButtonsCloison) {
            button.setVisible(removeButtonsCloison.size() > 1);
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
// Additional helper methods for configuring UI components (omitted for brevity)
}
