/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to  this template
 */
package Form;

/**
 *
 * @author brici_6ul2f65
 */
import Components.RoundedButton;
import Components.RoundedButton;
import ConnexionDB.DatabaseUtils;
import Panel.DevisCreatePanel;
import Panel.DevisCreatePanel;
import static Panel.DevisCreatePanel.parseProfileAluString;
import static Panel.DevisCreatePanel.parseTypeVitreString;
import static Panel.DevisCreatePanel.produitId;
import static Panel.DevisCreatePanel.tableModel;
import Models.LargeurAndHauteurExtractor;
import Models.ModelChassis;
import Models.ModelJoint;
import Models.ModelPaumelle;
import Models.ModelPoignee;
import Models.ModelProduit;
import Models.ModelRoulette;
import Models.ModelSerrure;
import Models.ModelStructure;
import Models.ModelTypeVitre;
import Models.ModelVis;
import Models.ModelVitrage;
import static Panel.DevisCreatePanel.calculateUnitPricePorteBattante;
//import static Panel.DevisCreatePanel.calculateUnitPricePorteBattante;
import static Panel.DevisCreatePanel.calculateUnitPricePorteCoulissante;

import static Panel.DevisCreatePanel.longueurTotalJoint;
import static Panel.DevisCreatePanel.nbPaumelle;
import static Panel.DevisCreatePanel.nbPoignee;
import static Panel.DevisCreatePanel.nbRoulette;
import static Panel.DevisCreatePanel.nbSerrures;
import static Panel.DevisCreatePanel.nbVisAssemblage;

import static Panel.DevisCreatePanel.parseProfileAluString;
import static Panel.DevisCreatePanel.parseTypeVitreString;
import static Panel.DevisCreatePanel.prixChassisFixePorte;
import static Panel.DevisCreatePanel.prixChassisOuvrantPorte;
import static Panel.DevisCreatePanel.prixJointPorte;
import static Panel.DevisCreatePanel.prixPaumellePorte;
import static Panel.DevisCreatePanel.prixPoigneePorte;
import static Panel.DevisCreatePanel.prixRoulettePorte;
import static Panel.DevisCreatePanel.prixSerrurePorte;
import static Panel.DevisCreatePanel.prixVisAssemblagePorte;
import static Panel.DevisCreatePanel.prixVitrePorte;
import static Panel.DevisCreatePanel.produitId;

import static Panel.DevisCreatePanel.quantiteBarreChassisFixePorte;

import static Panel.DevisCreatePanel.quantiteBarreChassisOuvrantPorte;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EditPorteForm extends JDialog {

    private JComboBox<String> typePorteComboBox;
    private JComboBox<String> modelesProfileCouleursPorteComboBox;
       private JSpinner nbVentauxPorteSpinner;
    private JComboBox<String> vitragePorteComboBox;
    private JComboBox<String> typeVitrePorteComboBox;
    private JComboBox<String> structurePorteComboBox;

    private JSpinner largeurPorteSpinner;
    private JSpinner hauteurPorteSpinner;
    private JSpinner quantitePorteSpinner;

    private JPanel SpinnerContainerPanelPorte;
    private JScrollPane scrollPaneSouslignePorte;
    private JButton addSousLignePorteButton;

    private final ArrayList<JButton> removeButtonsPorte = new ArrayList<>();
    private final RoundedButton saveButton;
    private final RoundedButton cancelButton;
  
    private Integer typeOuvertureId;
    private int vitrageId;
    private int typeVitreId;
    private int structureId;
    private int chassisTypeFixeId;
    private int chassisTypeOuvrantId;
  
    private int visId;
    private int jointId;
    private int serrureId;
    private Integer paumelleId;
    private Integer rouletteId;
    private boolean allSelectedDefault;
  
    private boolean isfill;
    private int nbVentaux;
    private Integer poigneeId;
    private int profileAluId;
    private BigDecimal prixUnitaireVis;
    private int quantiteEnStockVis;
    private int quantiteEnStockSerrure;
    private BigDecimal prixUnitaireSerrure;
    private BigDecimal quantiteEnStockJoint;
    private BigDecimal prixUnitaireJoint;
    private BigDecimal prixUnitaireVitre;
    private BigDecimal quantiteEnStockChassisOuvrant;
    private BigDecimal prixUnitaireChassisOuvrant;
    private BigDecimal longueurChassisOuvrant;
    private BigDecimal quantiteEnStockChassisFixe;
    private BigDecimal prixUnitaireChassisFixe;
    private BigDecimal longueurChassisFixe;
    private BigDecimal prixUnitairePoignee;
    private int quantiteEnStockPoignee;
    private BigDecimal prixUnitairePaumelle;
    private int quantiteEnStockPaumelle;
    private BigDecimal prixUnitaireRoulette;
    private int quantiteEnStockRoulette;
    private int quantite;
    private BigDecimal prixUnitaire;
    private BigDecimal largeur;
    private BigDecimal hauteur;
  
    private Integer nbpaumelle;
    private Integer nbpoignee;
    private Integer nbvis;
    private Integer nbserrure;
    private Integer nbroulette;
    private BigDecimal qteChassisFixe;
    private BigDecimal qteChassisOuvrant;
    private BigDecimal longueurJoint;
    private BigDecimal prixChassisFixe;
    private BigDecimal prixChassisOuvrant;
    private BigDecimal prixVitre;
    private BigDecimal prixJoint;
    private BigDecimal prixRoulette;
    private BigDecimal prixPaumelle;
    private BigDecimal prixSerrure;
    private BigDecimal prixPoignee;
    private BigDecimal prixVis;
    private JCheckBox moustiquairePorteCheckBox;
 
    private JCheckBox impostePorteCheckBox;
    private int nombreIntermediaires;
    private JCheckBox intermediairePorteCheckBox;
  
  
  




    public EditPorteForm(Component owner, String designation, List<Object[]> subRows, int selectedRow) throws Exception {
        super(SwingUtilities.getWindowAncestor(owner), "Modifier la Porte", Dialog.ModalityType.APPLICATION_MODAL);
        setLayout(new BorderLayout());
        JPanel portePanel = createPortePanel();

        fillPorteComboBoxes( selectedRow, subRows);
        fillSpinnersAndQuantity(subRows);

        add(portePanel, BorderLayout.CENTER);

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
 
private void fillPorteComboBoxes(int selectedRow, List<Object[]> subRows) throws SQLException {
    // Récupérer l'ID à partir de la colonne 0 du mainRow (TableModel)
    Integer produitId = (Integer) tableModel.getValueAt(selectedRow, 0); // Colonne 0 contient l'ID
  

    // Vérifier si l'ID est valide (non nul)
    if (produitId != null) {
        // Récupérer le nom du produit associé à cet ID dans le ModelProduit
        ModelProduit produit = DatabaseUtils.getProduitById(produitId);
        if (produit != null) {
            String nomProduit = produit.getNom(); // Nom du produit à afficher

            // Sélectionner les éléments dans les combo boxes en utilisant le nom du produit
            selectComboBoxItem(typePorteComboBox, nomProduit);
      
        } else {
            System.out.println("Produit non trouvé pour l'ID: " + produitId);
        }
    } else {
        System.out.println("L'ID de produit est nul dans la ligne: " + selectedRow);
    }
    
     for (Object[] subRow : subRows) {
           Integer structureId = (Integer) subRow[6];
        // Vérifier si l'ID est valide (non nul)
    if (structureId != null) {
        // Récupérer le nom du produit associé à cet ID dans le ModelProduit
        ModelStructure structure = DatabaseUtils.getStrucureById(structureId);
        if (structure != null) {
            String nomStructure = structure.getStructure(); // Nom du produit à afficher

            selectComboBoxItem(structurePorteComboBox, nomStructure );
       
        } else {
            System.out.println("SRTUCTURE non trouvé pour l'ID: " + structureId);
        }
    } else {
        System.out.println("L'ID de SRTUCTURE est nul dans la ligne: " + selectedRow);
    }
     Integer vitrageId = (Integer) subRow[4];
      if (vitrageId != null) {
        // Récupérer le nom du produit associé à cet ID dans le ModelProduit
        ModelVitrage vitre = DatabaseUtils.getVitrageById(vitrageId );
        if ( vitre!= null) {
            String vitrage =  vitre.getTypeVitrage(); // Nom du produit à afficher
            selectComboBoxItem(vitragePorteComboBox,  vitrage );

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

            selectComboBoxItem(typeVitrePorteComboBox,  typeEpaisseur );
    
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
                     selectComboBoxItem(modelesProfileCouleursPorteComboBox,  CouleurModel );
             }

        } else {
            System.out.println("SRTUCTURE non trouvé pour l'ID: " + structureId);
        }
    } else {
        System.out.println("L'ID de SRTUCTURE est nul dans la ligne: " + selectedRow);
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
   // Désactiver le JCheckBox si la boîte de dialogue est fermée via le bouton "X"
    dialogue.addWindowListener(new java.awt.event.WindowAdapter() {
        @Override
        public void windowClosing(java.awt.event.WindowEvent e) {
            intermediairePorteCheckBox.setSelected(false);
        }
    });
     cancelButton.addActionListener(e -> {
        // Désactiver le JCheckBox si Annuler est cliqué
        intermediairePorteCheckBox.setSelected(false);
        dialogue.dispose(); // Fermer la boîte de dialogue
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
 
 
 private void fillSpinnersAndQuantity(List<Object[]> subRows) {
        // Trouver tous les JSpinner existants dans le panneau
        List<JSpinner> existingSpinners = new ArrayList<>();
        DevisCreatePanel.findSpinners(SpinnerContainerPanelPorte, existingSpinners);

        // Index pour les spinners existants
        int existingSpinnerIndex = 0;

        for (Object[] subRow : subRows) {
            String dimensions = (String) subRow[13];
            LargeurAndHauteurExtractor.LargeurAndHauteur dim = LargeurAndHauteurExtractor.extractDimensionsFromDesignation(dimensions);

            int quantite = (int) subRow[15];
                        int ventaux = (int) subRow[3];
              nbVentauxPorteSpinner.setValue(ventaux ); 
            // Si nous avons des spinners existants, les réutiliser
            if (existingSpinnerIndex < existingSpinners.size()) {
                largeurPorteSpinner = existingSpinners.get(existingSpinnerIndex);
                hauteurPorteSpinner = existingSpinners.get(existingSpinnerIndex + 1);
                quantitePorteSpinner = existingSpinners.get(existingSpinnerIndex + 2);

                largeurPorteSpinner.setValue(dim.largeur.doubleValue());
                hauteurPorteSpinner.setValue(dim.hauteur.doubleValue());
                quantitePorteSpinner.setValue(quantite);

                existingSpinnerIndex += 3; // Passer aux prochains spinners
            } else {
                // Ajouter un nouveau panneau de spinners si aucun spinner existant n'est disponible
                addSousLignePorte();

                JPanel dernierPanneauSousLigne = (JPanel) SpinnerContainerPanelPorte.getComponent(SpinnerContainerPanelPorte.getComponentCount() - 1);
                List<JSpinner> spinners = new ArrayList<>();
                DevisCreatePanel.findSpinners(dernierPanneauSousLigne, spinners);

                // Vérifier que nous avons trouvé exactement trois spinners
                if (spinners.size() >= 3) {
                    largeurPorteSpinner = spinners.get(0);
                    hauteurPorteSpinner = spinners.get(1);
                    quantitePorteSpinner = spinners.get(2);
    
                    largeurPorteSpinner.setValue(dim.largeur.doubleValue());
                    hauteurPorteSpinner.setValue(dim.hauteur.doubleValue());
                    quantitePorteSpinner.setValue(quantite);
                } else {
                    System.err.println("Le panneau ne contient pas suffisamment de composants : " + spinners.size());
                }
            }
        }
    }



    private JPanel createSpinnerPortePanel() {
        JPanel panel = new JPanel(new BorderLayout());

        SpinnerContainerPanelPorte = new JPanel();
        SpinnerContainerPanelPorte.setLayout(new FlowLayout(FlowLayout.LEFT, 15, 0));

        addSousLignePorte();

        scrollPaneSouslignePorte = new JScrollPane(SpinnerContainerPanelPorte);
        scrollPaneSouslignePorte.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPaneSouslignePorte.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneSouslignePorte.setPreferredSize(new Dimension(260, 236));
        scrollPaneSouslignePorte.setBorder(BorderFactory.createEmptyBorder());

        panel.add(scrollPaneSouslignePorte, BorderLayout.CENTER);

        JPanel buttonsPanel = new JPanel(new BorderLayout());
        addSousLignePorteButton = new JButton();
        addSousLignePorteButton.setIcon(new ImageIcon(getClass().getResource("/Icon/icons8-add-16.png")));
        addSousLignePorteButton.setHorizontalTextPosition(SwingConstants.CENTER);

        addSousLignePorteButton.addActionListener((ActionEvent e) -> {

            addSousLignePorte();

        });

           Dimension buttonSize = new Dimension(16, 16);
        addSousLignePorteButton.setPreferredSize(buttonSize);

        buttonsPanel.add(addSousLignePorteButton, BorderLayout.EAST);
        scrollPaneSouslignePorte.setColumnHeaderView(buttonsPanel);

        return panel;
    }

    private JPanel createSousLignePortePanel() {
        JPanel createsousLignePanel = new JPanel(new GridLayout(3, 2, 10, 10));
        createsousLignePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        largeurPorteSpinner = DevisCreatePanel.configureSpinnerBigDecimal();
        hauteurPorteSpinner = DevisCreatePanel.configureSpinnerBigDecimal();
        quantitePorteSpinner = DevisCreatePanel.configureSpinnerInteger();

        DevisCreatePanel.addField(createsousLignePanel, "Largeur(mm):", largeurPorteSpinner);
        DevisCreatePanel.addField(createsousLignePanel, "Hauteur(mm):", hauteurPorteSpinner);
        DevisCreatePanel.addField(createsousLignePanel, "Quantité:", quantitePorteSpinner);

        return createsousLignePanel;
    }

    private void addSousLignePorte() {
        JPanel sousLignePanel = new JPanel(new BorderLayout(2,5));
        sousLignePanel.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        JPanel sousLigneContent = createSousLignePortePanel();
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
            removeButtonsPorte.remove(removeSousLigneButton);
            updateRemoveButtonsVisibilityPorte();
            updateScrollBarPolicyPorte();
            SpinnerContainerPanelPorte.requestFocusInWindow();
        });

        if (removeButtonsPorte.size() == 1) {
            removeSousLigneButton.setVisible(false);
        }

        JPanel removeButtonPanel = new JPanel(new BorderLayout());
        removeButtonPanel.add(removeSousLigneButton,BorderLayout.EAST);
        sousLignePanel.add(removeButtonPanel, BorderLayout.SOUTH);

        removeButtonsPorte.add(removeSousLigneButton);
        SpinnerContainerPanelPorte.add(sousLignePanelContenaire);
        updateRemoveButtonsVisibilityPorte();
        updateScrollBarPolicyPorte();
        SpinnerContainerPanelPorte.revalidate();
        SpinnerContainerPanelPorte.repaint();

        // Animer le défilement vers la fin à droite
        SwingUtilities.invokeLater(() -> animateScrollToRightPorte());
    }

    private void saveModification(int selectedRow) {
        try {
            // Trouver l'index de la ligne principale sélectionnée
            int mainRowIndex = tableModel.findMainRowIndexForSubRow(selectedRow);

            if (mainRowIndex == -1) {
                throw new Exception("Ligne principale non trouvée.");
            }
           
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
            // Récupérer la nouvelle désignation à partir de l'onglet approprié
            String newDesignation = buildPorteDesignation();  // ou buildFenetreDesignation(), etc.

            // Mettre à jour la désignation de la ligne principale
            tableModel.setValueAt(produitId, mainRowIndex, 0);
           tableModel.setValueAt(newDesignation, mainRowIndex, 13); // Mise à jour de la colonne Désignation

            
            

            // Récupérer les valeurs des spinners et les dimensions
            List<List<JSpinner>> panelSpinners = DevisCreatePanel.getSpinnerValues(SpinnerContainerPanelPorte);
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
                  
                        prixUnitaire = calculateUnitPricePorteCoulissante(hauteur, largeur,nombreIntermediaires, nbVentaux, prixUnitaireChassisFixe, longueurChassisFixe, prixUnitaireChassisOuvrant, longueurChassisOuvrant, prixUnitaireVitre, prixUnitaireJoint, prixUnitaireVis, prixUnitaireRoulette, prixUnitaireSerrure);

                 
                    } else if    (typeOuvertureId == 2) {  // Pour une fenêtre battante
                       nbRoulette = null;
                       prixUnitaire = calculateUnitPricePorteBattante(hauteur, largeur,nombreIntermediaires, nbVentaux, prixUnitaireChassisFixe, longueurChassisFixe, prixUnitaireChassisOuvrant, longueurChassisOuvrant, prixUnitaireVitre, prixUnitaireJoint, prixUnitaireVis, prixUnitairePaumelle, prixUnitaireSerrure, prixUnitairePoignee);
}
                    String dimensions = largeur + " mm (Lt) x " + hauteur + " mm (Ht)";

                   
                    BigDecimal totalPrice = prixUnitaire.multiply(new BigDecimal(quantite));
      // Ajouter les nouvelles sous-lignes mises à jour
                     // Ajouter les nouvelles sous-lignes mises à jour
                    updatedSubRows.add(new Object[]{"", chassisTypeFixeId, chassisTypeOuvrantId, nbVentaux, vitrageId, typeVitreId, structureId,paumelleId ,rouletteId,  serrureId,poigneeId, visId ,jointId,dimensions, prixUnitaire, quantite, totalPrice,   nbRoulette ,
    nbPaumelle,
      nbPoignee,
      nbVisAssemblage ,
        nbSerrures ,
 longueurTotalJoint ,
  quantiteBarreChassisFixePorte,quantiteBarreChassisOuvrantPorte,prixChassisFixePorte,prixChassisOuvrantPorte,prixVitrePorte,prixJointPorte,prixRoulettePorte,prixPaumellePorte,prixSerrurePorte,prixPoigneePorte,prixVisAssemblagePorte,
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

         
               for (Object[] subRow : updatedSubRows) {
    int chassisTypeFixeId = (int) subRow[1];
    int chassisTypeOuvrantId = (int) subRow[2];
    int nbVentaux = (int) subRow[3];
    int vitrageId = (int) subRow[4];
    int typeVitreId = (int) subRow[5];
    int structureId = (int) subRow[6];
    Integer paumelleId = ( Integer) subRow[7];
    Integer rouletteId = ( Integer) subRow[8];
    int serrureId = (int) subRow[9];
    Integer poigneeId = ( Integer) subRow[10];
    int visId = (int) subRow[11];
    int jointId = (int) subRow[12];
    String dimensions = (String) subRow[13];
    BigDecimal prixUnitaire = (BigDecimal) subRow[14];
    int quantite = (int) subRow[15];
     nbroulette =  ( Integer) subRow[17];
     nbpaumelle =  ( Integer) subRow[18];
      nbpoignee =  ( Integer) subRow[19];
      nbvis =  ( Integer) subRow[20];
        nbserrure =  ( Integer) subRow[21];
 longueurJoint = (BigDecimal) subRow[22];
      qteChassisFixe = (BigDecimal) subRow[23];
 qteChassisOuvrant = (BigDecimal) subRow[24];
 prixChassisFixe  = (BigDecimal) subRow[25];
 prixChassisOuvrant = (BigDecimal) subRow[26];
         prixVitre = (BigDecimal) subRow[27];
         prixJoint =(BigDecimal) subRow[28];
         prixRoulette=(BigDecimal) subRow[29];
         prixPaumelle=(BigDecimal) subRow[30];
         prixSerrure=(BigDecimal) subRow[31];
         prixPoignee=(BigDecimal) subRow[32];
         prixVis=(BigDecimal) subRow[33];
              tableModel.addOrUpdateSubRow(mainRowIndex,chassisTypeFixeId, chassisTypeOuvrantId, nbVentaux, vitrageId, typeVitreId, structureId,paumelleId ,rouletteId,  serrureId,poigneeId, visId ,jointId,dimensions, prixUnitaire, quantite,nbroulette,nbpaumelle,nbpoignee,nbvis,nbserrure,longueurJoint,qteChassisFixe,qteChassisOuvrant,prixChassisFixe,
 prixChassisOuvrant,
         prixVitre,
         prixJoint ,
         prixRoulette,
         prixPaumelle,
         prixSerrure,
         prixPoignee,
         prixVis,null, 
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

    private JPanel createPortePanel() throws Exception {
        JPanel portePanel = new JPanel(new BorderLayout());
        Color defaultColor = UIManager.getColor("ScrollBar.thumb");
        portePanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 1, defaultColor));

        JPanel panelPorteComboContenaire = new JPanel(new BorderLayout());
        JPanel panelPorteSpinnerContenaire = new JPanel(new BorderLayout());
        JPanel panelPorteOptionContenaire = new JPanel(new BorderLayout());
           panelPorteOptionContenaire.add(createOptionsPanel(), BorderLayout.EAST);
        panelPorteComboContenaire.add(createPorteComboBoxPanel(), BorderLayout.NORTH);
        panelPorteSpinnerContenaire.add(createSpinnerPortePanel(), BorderLayout.NORTH);
        portePanel.add(panelPorteComboContenaire, BorderLayout.WEST);
        portePanel.add(panelPorteSpinnerContenaire, BorderLayout.CENTER);
         portePanel.add(panelPorteOptionContenaire, BorderLayout.EAST);
        return portePanel;
    }

    private JPanel createPorteComboBoxPanel() throws Exception {
        JPanel panel = new JPanel(new GridLayout(1, 2, 25, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.setBorder(BorderFactory.createEmptyBorder(15, 10, 0, 5));

        // Initialize JComboBoxes without data
        typePorteComboBox = new JComboBox<>();
        modelesProfileCouleursPorteComboBox = new JComboBox<>();
        nbVentauxPorteSpinner = DevisCreatePanel.configureSpinnerInteger();
        vitragePorteComboBox = new JComboBox<>();
        typeVitrePorteComboBox = new JComboBox<>();
        structurePorteComboBox = new JComboBox<>();

        //String selectedprofileCouleur = (String)  modelesProfileCouleursPorteComboBox.getSelectedItem();
        // Utilisation des méthodes de DevisCreatePanel pour remplir les JComboBoxes
        DevisCreatePanel.fillComboBoxWithProducts(typePorteComboBox, "Porte");
        DevisCreatePanel.populateModeleCouleurComboBox(modelesProfileCouleursPorteComboBox);
       
        DevisCreatePanel.populateComboBox(vitragePorteComboBox, "vitrage", "type");
         vitragePorteComboBox.addActionListener((ActionEvent e) -> {
            String selectedVitrage = (String)  vitragePorteComboBox.getSelectedItem();
//            updateTypeVitrePorteComboBox(selectedVitrage);
            
            if (selectedVitrage != null && !selectedVitrage.isEmpty()) {
                try {
                    DevisCreatePanel.populateTypeVitreComboBox(  typeVitrePorteComboBox,selectedVitrage);
                } catch (Exception ex) {
                    Logger.getLogger(DevisCreatePanel.class.getName()).log(Level.SEVERE, null, ex);
                }
              
              
            } else {
                // Vider les combo boxes si aucune couleur n'est sélectionnée
                // typeVitrePorteComboBox.removeAllItems();
                
            }
       
        });
        DevisCreatePanel.populateComboBox(structurePorteComboBox, "structure", "structure");

        JPanel firstCol = new JPanel(new GridLayout(3, 1, 10, 10));
        DevisCreatePanel.addField(firstCol, "Type de Porte:", typePorteComboBox);
        DevisCreatePanel.addField(firstCol, "Modèle Profile:", modelesProfileCouleursPorteComboBox);
        DevisCreatePanel.addField(firstCol, "Nombre de Ventaux:", nbVentauxPorteSpinner);

        JPanel secondCol = new JPanel(new GridLayout(3, 1, 10, 10));
        DevisCreatePanel.addField(secondCol, "Vitrage:", vitragePorteComboBox);
        DevisCreatePanel.addField(secondCol, "Type de Vitre:", typeVitrePorteComboBox);
        DevisCreatePanel.addField(secondCol, "Structure:", structurePorteComboBox);

        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(firstCol);

        gbc.gridx++;
        panel.add(secondCol);

        return panel;
    }

   


    private void animateScrollToRightPorte() {
        JScrollBar horizontalScrollBar = scrollPaneSouslignePorte.getHorizontalScrollBar();

        int targetValue = horizontalScrollBar.getMaximum();

        // Utilisation d'un AtomicInteger pour une valeur mutable
        AtomicInteger currentValue = new AtomicInteger(horizontalScrollBar.getValue());

        // Définir le délai et la vitesse de défilement
        int delay = 5; // Délai en millisecondes entre chaque étape de défilement
        int increment = 20; // Quantité de défilement à chaque étape

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

    private void updateRemoveButtonsVisibilityPorte() {
        // Supposons que vous avez une liste de boutons de suppression pour les sous-lignes
        for (JButton button : removeButtonsPorte) {
            button.setVisible(removeButtonsPorte.size() > 1);
        }
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


    // Additional helper methods for configuring UI components (omitted for brevity)
}
