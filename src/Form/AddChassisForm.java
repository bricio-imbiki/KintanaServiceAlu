package Form;

import ConnexionDB.DatabaseUtils;
import Panel.DevisCreatePanel;
import static Panel.DevisCreatePanel.parseProfileAluString;
import static Panel.DevisCreatePanel.setComboBoxRenderer;
import Models.ModelChassis;
import Models.ModelClient;
import Models.ModelFournisseur;
import Models.ModelProfileAlu;
import Models.ModelStatut.ModelStatutStock;
import Models.ModelTypeProfileAlu;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.miginfocom.swing.MigLayout;
import raven.modal.ModalDialog;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

public class AddChassisForm extends JPanel {


    private ModelChassis chassis;
    private JTextField longueurField; // BigDecimal (doit être converti dans saveProfile)
    private JTextField prixUnitaireField; // BigDecimal (doit être converti dans saveProfile)
    private JTextField quantiteField; // int (doit être converti dans saveProfile)
   
    private JComboBox<String> fournisseurCombo;
    public static JComboBox<String>  modelProfileAluCombo;
    private JComboBox<String>  typeProfileAluCombo;
    private int profileAluId;
    private int typeProfileId;
    private int fournisseurId;
    private boolean allSelectedDefault;
 

    // Constructeur pour ajout d'un profil
    public AddChassisForm(Component owner) throws SQLException  {
     
        setupUI(null);
         //  setPreferredSize(new Dimension(400, 400)); // Taille préférée de la boîte de dialogue
    }

    // Constructeur pour modification d'un profil existant
    public AddChassisForm(Component owner, ModelChassis chassis) throws SQLException {
       
        this.chassis = chassis;
        setupUI(chassis);
         // setPreferredSize(new Dimension(400, 400)); // Taille préférée de la boîte de dialogue
    }

    private void setupUI(ModelChassis chassis) throws SQLException  {
         setLayout(new MigLayout("wrap 2, fillx, insets n 35 n 35", "[fill, 200]"));

    // Ajouter les champs avec une hauteur augmentée et icônes

    // Ajouter le champ "Model Profile" avec le bouton d'ouverture de fenêtre
    add(new JLabel("Model Profile"), "span 2");

    modelProfileAluCombo = new JComboBox<String>();
    DevisCreatePanel.populateModeleCouleurComboBox(modelProfileAluCombo);
   modelProfileAluCombo.setPreferredSize(new Dimension(modelProfileAluCombo.getPreferredSize().width, 35));
    add(modelProfileAluCombo, "split 2, span, growx");

    // Bouton pour ouvrir une nouvelle fenêtre
    JButton openAddProfileModel = new JButton("...");
    openAddProfileModel.setPreferredSize(new Dimension(5, 30)); // Alignement en hauteur avec le combo box
    openAddProfileModel.addActionListener(e -> {
        // Code pour ouvrir une nouvelle fenêtre
     openAddProfileForm();
    });
    add(openAddProfileModel, "gapy n 5,span 2");  


     add(new JLabel("Type Profilé"), "span 2");
     typeProfileAluCombo = new JComboBox<String>();
    populateTypeProfileComboBox(typeProfileAluCombo);
      typeProfileAluCombo.setPreferredSize(new Dimension(typeProfileAluCombo.getPreferredSize().width, 35)); 
    add(typeProfileAluCombo, "gapy n 5,span 2");
    
    add(new JLabel("Fournisseur"), "span 2");
     fournisseurCombo = new JComboBox<String>();
    populateFournisseurComboBox(fournisseurCombo);
     fournisseurCombo.setPreferredSize(new Dimension(typeProfileAluCombo.getPreferredSize().width, 35)); 
    add(fournisseurCombo, "gapy n 5,span 2");
    
    add(new JLabel("Longueur "),"span 2");
    longueurField = new JTextField();
    longueurField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Longueur en mm");
    longueurField.setPreferredSize(new Dimension(longueurField.getPreferredSize().width, 40)); 
    add(longueurField,"gapy n 5,span 2");

    add(new JLabel("Prix Unitaire"),"span 2");
    prixUnitaireField = new JTextField();
    prixUnitaireField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Prix Unitaire");
    prixUnitaireField.setPreferredSize(new Dimension(prixUnitaireField.getPreferredSize().width, 40)); 
  
    add(prixUnitaireField,"gapy n 5,span 2");

    add(new JLabel("Quantité"),"span 2");
    quantiteField = new JTextField();
    quantiteField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Quantité");
    quantiteField.setPreferredSize(new Dimension(quantiteField.getPreferredSize().width, 40)); 
   
    
    add(quantiteField,"gapy n 5,span 2");

        // Si un modèle existe déjà (modification), remplir les champs
        if (chassis != null) {
            modelProfileAluCombo.setSelectedItem(chassis.getProfile().toString());
            typeProfileAluCombo.setSelectedItem(chassis.getType().getTypeProfileAlu()); // SetSelectedItem pour ComboBox
            longueurField.setText(chassis.getLongueurBarre().toString()); // Convertir en String
            prixUnitaireField.setText(chassis.getPrixUnitaire().toString()); // Convertir en String
            quantiteField.setText(String.valueOf(chassis.getQuantiteEnStock())); // Convertir en String
            fournisseurCombo.setSelectedItem(chassis.getFournisseur().getNom()); // SetSelectedItem pour ComboBox
        }

  
  // Créer le panneau des boutons avec une mise en page MigLayout
 JPanel buttonsPanel = new JPanel(new MigLayout("wrap 2, fillx, insets n 3 n 3", "[fill, 200]push[fill, 200]"));
// Le "20" spécifie un espace de 20px entre les deux colonnes de boutons

// Créer les boutons avec leurs icônes
JButton cmdSave = new JButton(chassis == null ? "Enregistrer" : "Mettre à jour") {
    @Override
    public boolean isDefaultButton() {
        return true;
    }
};
cmdSave.setIcon(new FlatSVGIcon("Icon/save.svg", 20, 20));

JButton cmdCancel = new JButton("Annuler");
cmdCancel.setIcon(new FlatSVGIcon("Icon/cancel.svg", 16, 16));




cmdCancel.addActionListener(actionEvent -> {
    ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.CANCEL_OPTION);
});
cmdSave.addActionListener((ActionEvent e) -> {
             try {
                 saveChassis();
             } catch (Exception ex) {
                 Logger.getLogger(AddChassisForm.class.getName()).log(Level.SEVERE, null, ex);
             }
         });

Dimension buttonSize = new Dimension(100, 40); // Modifiez ici pour ajuster la hauteur
cmdSave.setPreferredSize(buttonSize);
cmdCancel.setPreferredSize(buttonSize);
  buttonsPanel.add(cmdSave, "grow 0, gaptop 15");
  buttonsPanel.add(cmdCancel, "grow 0, gaptop 15, gapleft 100");

    add(buttonsPanel);
      // Ajouter un écouteur global pour retirer le focus quand on clique en dehors
  addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
          requestFocusInWindow();
            }
        });
    
}

private void saveChassis() throws Exception {
          allSelectedDefault = (modelProfileAluCombo.getSelectedIndex() == 0
                                || typeProfileAluCombo.getSelectedIndex() == 0
                                || fournisseurCombo.getSelectedIndex() == 0);

                        if (allSelectedDefault) {
                            JOptionPane.showMessageDialog(null, "Sélectionnez une option pour chaque critère.");
                            return;
                        }
    // Récupérer et valider les valeurs des champs
    String modelProfile = (String) modelProfileAluCombo.getSelectedItem();   
            String[] profileAluParts = parseProfileAluString(modelProfile);
            String model = profileAluParts[0];
            String couleur = profileAluParts[1];
             profileAluId = DatabaseUtils.getProfileAluId(model, couleur);
             // Récupérer les valeurs sélectionnées dans chaque JComboBox

    String typeProfile = (String) typeProfileAluCombo.getSelectedItem();
    String fournisseur = (String) fournisseurCombo.getSelectedItem();
 
    typeProfileId = DatabaseUtils.getIdFromChoice(typeProfile, "type_profile_alu", "type_profile_alu", "type_profile_alu_id");
    fournisseurId = DatabaseUtils.getIdFromChoice(fournisseur, "fournisseur", "nom", "fournisseur_id");
     
    try {
        BigDecimal longueur = new BigDecimal(longueurField.getText().trim());
        BigDecimal prixUnitaire = new BigDecimal(prixUnitaireField.getText().trim());
        BigDecimal quantite =  new BigDecimal(quantiteField.getText().trim());

        // Vérifier si tous les champs requis sont renseignés
        if (modelProfile == null || typeProfile == null || fournisseur == null) {
            JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Vérification de doublon lors de l'ajout
        if (chassis == null) {
            
                 ModelProfileAlu Profile = DatabaseUtils.getProfileById(profileAluId);
                 ModelTypeProfileAlu type = DatabaseUtils.getTypeProfileAluById(typeProfileId);
                 ModelFournisseur frns = DatabaseUtils.getFournisseurById(fournisseurId);
            // Création d'un nouveau châssis
            chassis = new ModelChassis(DatabaseUtils.getNextChassisId(), Profile, type, longueur, prixUnitaire, quantite, frns,new ModelStatutStock(1,"Normale"));
            if ("SUCCESS".equals(DatabaseUtils.addChassis(chassis))) {
                ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.CANCEL_OPTION);
                JOptionPane.showMessageDialog(this, "Châssis ajouté avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du châssis.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Mise à jour du châssis existant
            chassis.getProfile().setProfileAluId(profileAluId);
            chassis.getType().setTypeProfileAluId(typeProfileId);
            chassis.setLongueurBarre(longueur);
            chassis.setPrixUnitaire(prixUnitaire);
            chassis.setQuantiteEnStock(quantite);
            chassis.getFournisseur().setFournisseurId(fournisseurId);

            if ("SUCCESS".equals(DatabaseUtils.updateChassis(chassis))) {
                ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.CANCEL_OPTION);
                JOptionPane.showMessageDialog(this, "Châssis mis à jour avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour du châssis.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs numériques valides pour la longueur, le prix unitaire et la quantité.", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
    } catch (Exception e) {
        JOptionPane.showMessageDialog(this, "Une erreur inattendue est survenue: " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}


 

    // Méthode pour peupler la comboBox typeProfileAlu
    private void populateTypeProfileComboBox(JComboBox<String> comboBox) {
           comboBox.addItem("");
        List<ModelTypeProfileAlu> types = DatabaseUtils.getAllTypeProfileAlu(); // Exemple de récupération des types depuis la DB
        for (ModelTypeProfileAlu type : types) {
            comboBox.addItem(type.getTypeProfileAlu());

        }
         setComboBoxRenderer(comboBox);
    }

    public static void populateFournisseurComboBox(JComboBox<String> comboBox) {
                  comboBox.addItem("");
        List<ModelFournisseur> fournisseur = DatabaseUtils.getAllFournisseur(); // Exemple de récupération des types depuis la DB
        for (ModelFournisseur nom : fournisseur) {
            comboBox.addItem(nom.getNom());

        }
         setComboBoxRenderer(comboBox);
    }

    private void openAddProfileForm() {
           ModalDialog.showModal(this, new SimpleModalBorder(new AddProfileAluForm(), "Ajouter Modèle Profile et Couleur ", SimpleModalBorder.DEFAULT_OPTION, (controller, action) -> {
            
            })); 
           
    }
   

  
}
