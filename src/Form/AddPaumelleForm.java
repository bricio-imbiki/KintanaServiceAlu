

package Form;

import ConnexionDB.DatabaseUtils;
import static Form.AddChassisForm.populateFournisseurComboBox;
import Models.ModelFournisseur;
import Models.ModelProfileAlu;
import Models.ModelPaumelle;
import Models.ModelStatut;
import Panel.DevisCreatePanel;
import static Panel.DevisCreatePanel.parseProfileAluString;
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

public class AddPaumelleForm extends JPanel {

    private ModelPaumelle paumelle;
    private JTextField couleurField;
    private JTextField prixUnitaireField;
    private JTextField quantiteField;
    private JComboBox<String> fournisseurCombo;
    private JComboBox<String> modelProfileAluCombo;
    private int fournisseurId;
    private JTextField typeField;
    private int profileAluId;

    public AddPaumelleForm(Component owner) throws SQLException {
        setupUI(null);
    }

    public AddPaumelleForm(Component owner, ModelPaumelle paumelle) throws SQLException {
        this.paumelle = paumelle;
        setupUI(paumelle);
    }

    private void setupUI(ModelPaumelle paumelle) throws SQLException {
     setLayout(new MigLayout("wrap 2, fillx, insets n 35 n 35", "[fill, 200]"));


        add(new JLabel("Model Profile"), "span 2");
        modelProfileAluCombo = new JComboBox<>();
        DevisCreatePanel.populateModeleCouleurComboBox(modelProfileAluCombo);
        modelProfileAluCombo.setPreferredSize(new Dimension(modelProfileAluCombo.getPreferredSize().width, 35));
        add(modelProfileAluCombo, "gapy 5, span 2");

        add(new JLabel("Fournisseur"), "span 2");
        fournisseurCombo = new JComboBox<>();
        populateFournisseurComboBox(fournisseurCombo);
        fournisseurCombo.setPreferredSize(new Dimension(fournisseurCombo.getPreferredSize().width, 35));
        add(fournisseurCombo, "gapy 5, span 2");

        add(new JLabel("Type de paumelle"), "span 2");
        typeField = new JTextField();
        typeField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Type de paumelle");
        typeField.setPreferredSize(new Dimension(typeField.getPreferredSize().width, 40));
        add(typeField, "gapy 5, span 2");

        add(new JLabel("Couleur"), "span 2");
        couleurField = new JTextField();
        couleurField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Couleur");
        couleurField.setPreferredSize(new Dimension(couleurField.getPreferredSize().width, 40));
        add(couleurField, "gapy 5, span 2");

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

        if (paumelle != null) {
            typeField.setText(paumelle.getTypePaumelle());
            couleurField.setText(paumelle.getCouleurPaumelle());
            prixUnitaireField.setText(paumelle.getPrixUnitaire().toString());
            quantiteField.setText(String.valueOf(paumelle.getQuantiteEnStock()));
            modelProfileAluCombo.setSelectedItem(paumelle.getProfile().toString());
            fournisseurCombo.setSelectedItem(paumelle.getFournisseur().getNom());
        }

        JPanel buttonsPanel = new JPanel(new MigLayout("wrap 2, fillx, insets n 3 n 3", "[fill, 200]push[fill, 200]"));
 JButton cmdSave = new JButton(paumelle == null ? "Enregistrer" : "Mettre à jour") {
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
                savepaumelle();
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

    private void savepaumelle() throws Exception {
        try {
            String modelProfile = (String) modelProfileAluCombo.getSelectedItem();   
            String[] profileAluParts = parseProfileAluString(modelProfile);
            String model = profileAluParts[0];
            String couleur = profileAluParts[1];
            profileAluId = DatabaseUtils.getProfileAluId(model, couleur);
            ModelProfileAlu profile = DatabaseUtils.getProfileById(profileAluId);

            String couleurpaumelle= couleurField.getText();
            BigDecimal prixUnitaire = new BigDecimal(prixUnitaireField.getText().trim());
            int quantite = Integer.parseInt(quantiteField.getText().trim());
            String type = typeField.getText();

            if (fournisseurCombo.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            fournisseurId = DatabaseUtils.getIdFromChoice((String) fournisseurCombo.getSelectedItem(), "fournisseur", "nom", "fournisseur_id");
            ModelFournisseur frns = DatabaseUtils.getFournisseurById(fournisseurId);

            if (paumelle == null) {
                paumelle = new ModelPaumelle(DatabaseUtils.getNextPaumelleId(), profile, type, couleurpaumelle, prixUnitaire, quantite, frns,new ModelStatut.ModelStatutStock(1,"Normale"));
                if ("SUCCESS".equals(DatabaseUtils.addPaumelle(paumelle))) {
                    ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.CANCEL_OPTION);
                    JOptionPane.showMessageDialog(this, "paumelle ajouté avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout de la paumelle.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                paumelle.getProfile().setProfileAluId(profileAluId);
                paumelle.setTypePaumelle(type);
                paumelle.setCouleurPaumelle(couleurpaumelle);
                paumelle.setPrixUnitaire(prixUnitaire);
                paumelle.setQuantiteEnStock(quantite);
                paumelle.getFournisseur().setFournisseurId(fournisseurId);
                
                if ("SUCCESS".equals(DatabaseUtils.updatePaumelle(paumelle))) {
                    ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.CANCEL_OPTION);
                    JOptionPane.showMessageDialog(this, "paumelle mis à jour avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour de la paumelle.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Veuillez entrer des valeurs numériques valides.", "Erreur de saisie", JOptionPane.ERROR_MESSAGE);
        }
    }
} 

