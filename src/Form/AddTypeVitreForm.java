
package Form;

import ConnexionDB.DatabaseUtils;
import static Form.AddChassisForm.populateFournisseurComboBox;
import Models.ModelFournisseur;
import Models.ModelProfileAlu;
import Models.ModelStatut;
import Models.ModelTypeVitre;
import Models.ModelVitrage;
import static Panel.DevisCreatePanel.setComboBoxRenderer;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import net.miginfocom.swing.MigLayout;
import raven.modal.ModalDialog;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;
import java.util.List;

public class AddTypeVitreForm extends JPanel {

    private ModelTypeVitre typeVitre;
    private JTextField epaisseurField;
    private JTextField prixUnitaireField;
    private JTextField quantiteField;
    private JComboBox<String> fournisseurCombo;
    
    private int fournisseurId;
    private JTextField typeField;
   
    private JComboBox<String> typeVitrageCombo;
    private int vitrageId;

    public AddTypeVitreForm(Component owner) throws SQLException {
        setupUI(null);
    }

    public AddTypeVitreForm(Component owner, ModelTypeVitre typeVitre) throws SQLException {
        this.typeVitre = typeVitre;
        setupUI(typeVitre);
    }

private void setupUI(ModelTypeVitre typeVitre) throws SQLException {
    setLayout(new MigLayout("wrap 2, fillx, insets n 35 n 35", "[fill, 200]"));

    // Initialisation du champ "Type vitrage"
    add(new JLabel("Type vitrage"), "span 2");
    typeVitrageCombo = new JComboBox<>();
     populateVitrageComboBox(typeVitrageCombo);
    typeVitrageCombo.setPreferredSize(new Dimension(typeVitrageCombo.getPreferredSize().width, 35));
    add(typeVitrageCombo, "gapy 5, span 2");

    // Initialisation du champ "Fournisseur"
    add(new JLabel("Fournisseur"), "span 2");
    fournisseurCombo = new JComboBox<>();
    populateFournisseurComboBox(fournisseurCombo);
    fournisseurCombo.setPreferredSize(new Dimension(fournisseurCombo.getPreferredSize().width, 35));
    add(fournisseurCombo, "gapy 5, span 2");

    // Initialisation des autres champs
    add(new JLabel("Type de Vitre"), "span 2");
    typeField = new JTextField();
    typeField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Type de typeVitre");
    typeField.setPreferredSize(new Dimension(typeField.getPreferredSize().width, 40));
    add(typeField, "gapy 5, span 2");

    add(new JLabel("Epaisseur"), "span 2");
    epaisseurField = new JTextField();
    epaisseurField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Epaisseur en mm");
    epaisseurField.setPreferredSize(new Dimension(epaisseurField.getPreferredSize().width, 40));
    add(epaisseurField, "gapy 5, span 2");

    add(new JLabel("Prix Unitaire"), "span 2");
    prixUnitaireField = new JTextField();
    prixUnitaireField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Prix Unitaire");
    prixUnitaireField.setPreferredSize(new Dimension(prixUnitaireField.getPreferredSize().width, 40));
    add(prixUnitaireField, "gapy 5, span 2");

    add(new JLabel("Quantité"), "span 2");
    quantiteField = new JTextField();
    quantiteField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Quantité");
    quantiteField.setPreferredSize(new Dimension(quantiteField.getPreferredSize().width, 40));
    add(quantiteField, "gapy 5, span 2");

    if (typeVitre != null) {
        typeVitrageCombo.setSelectedItem(typeVitre.getVitrage().getTypeVitrage());
        // Remplissage des champs avec les données de typeVitre
        typeField.setText(typeVitre.getType());
        epaisseurField.setText(typeVitre.getEpaisseur().toString());
        prixUnitaireField.setText(typeVitre.getPrixUnitaire().toString());
        quantiteField.setText(String.valueOf(typeVitre.getQuantiteEnStock()));
         fournisseurCombo.setSelectedItem(typeVitre.getFournisseur().getNom());

    }



        JPanel buttonsPanel = new JPanel(new MigLayout("wrap 2, fillx, insets n 3 n 3", "[fill, 200]push[fill, 200]"));
        JButton cmdSave = new JButton(typeVitre == null ? "Enregistrer" : "Mettre à jour") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };
        cmdSave.setIcon(new FlatSVGIcon("Icon/save.svg", 20, 20));

        JButton cmdCancel = new JButton("Annuler");
        cmdCancel.setIcon(new FlatSVGIcon("Icon/cancel.svg", 16, 16));
        cmdCancel.addActionListener(actionEvent -> 
            ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.CANCEL_OPTION)
        );

        cmdSave.addActionListener((ActionEvent e) -> {
            try {
                savetypeVitre();
            } catch (Exception ex) {
                ex.printStackTrace();
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
     private void populateVitrageComboBox(JComboBox<String> comboBox) {
             comboBox.addItem("");
         
        // Récupérer tous les vitrages de la base de données
        List<ModelVitrage> vitrages = DatabaseUtils.retrieveAllVitrages();

        // Ajouter chaque vitrage au combo box
        for (ModelVitrage vitrage : vitrages) {
            comboBox.addItem(vitrage.getTypeVitrage());
        }
         setComboBoxRenderer(comboBox);
    }
//        public static void populateFournisseurComboBox(JComboBox<String> comboBox) {
//                  comboBox.addItem("");
//        List<ModelFournisseur> fournisseur = DatabaseUtils.getAllFournisseur(); // Exemple de récupération des types depuis la DB
//        for (ModelFournisseur nom : fournisseur) {
//            comboBox.addItem(nom.getNom());
//
//        }
//         setComboBoxRenderer(comboBox);
//    }
    private void savetypeVitre() throws Exception {
        try {
            String selectedVitrage = (String) typeVitrageCombo.getSelectedItem();   
             vitrageId = DatabaseUtils.getIdFromChoice(selectedVitrage, "vitrage", "type", "vitrage_id");
            BigDecimal epaisseur = new BigDecimal(epaisseurField.getText().trim());
            BigDecimal prixUnitaire = new BigDecimal(prixUnitaireField.getText().trim());
            int quantite = Integer.parseInt(quantiteField.getText().trim());
            String type = typeField.getText();

            if (fournisseurCombo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ModelVitrage vitrage = DatabaseUtils.getVitrageById(vitrageId);
            fournisseurId = DatabaseUtils.getIdFromChoice((String) fournisseurCombo.getSelectedItem(), "fournisseur", "nom", "fournisseur_id");
            ModelFournisseur frns = DatabaseUtils.getFournisseurById(fournisseurId);

            if (typeVitre == null) {
                typeVitre = new ModelTypeVitre(DatabaseUtils.getNextVitreId(),  vitrage,type,epaisseur, prixUnitaire, quantite, frns,new ModelStatut.ModelStatutStock(1,"Normale"));
                if ("SUCCESS".equals(DatabaseUtils.addVitre(typeVitre))) {
                    ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.CANCEL_OPTION);
                    JOptionPane.showMessageDialog(this, "typeVitre ajouté avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la typeVitre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                typeVitre.getVitrage().setVitrageId(vitrageId);
                typeVitre.setType(type);
                typeVitre.setEpaisseur(epaisseur);
                typeVitre.setPrixUnitaire(prixUnitaire);
                typeVitre.setQuantiteEnStock(quantite);
                typeVitre.getFournisseur().setFournisseurId(fournisseurId);
                
                if ("SUCCESS".equals(DatabaseUtils.updateVitre(typeVitre))) {
                    ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.CANCEL_OPTION);
                    JOptionPane.showMessageDialog(this, "typeVitre mis à jour avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour de la typeVitre.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs numériques valides.", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
        }
    }
}
