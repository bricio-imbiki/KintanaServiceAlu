/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Form;

import ConnexionDB.DatabaseUtils;
import Models.ModelFournisseur;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

public class AddFournisseurForm extends JPanel {

    private ModelFournisseur fournisseur;
    private JTextField nomField;
    private JTextField adresseField;
    private JTextField telephoneField;
    private JTextField emailField;

    public AddFournisseurForm(Component owner) {
        initUI(null);
        
         
    }

    public AddFournisseurForm(Component owner, ModelFournisseur fournisseur) {
        this.fournisseur = fournisseur;
        initUI(fournisseur);
       
         
    }

    private void initUI(ModelFournisseur fournisseur) {
        setLayout(new MigLayout("wrap 2, fillx, insets n 35 n 35", "[fill, 200]"));

        // Ajouter les champs avec une hauteur augmentée et icônes
        add(new JLabel("Nom"), "span 2");
        nomField = new JTextField();
        nomField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nom");
        nomField.setPreferredSize(new Dimension(nomField.getPreferredSize().width, 44)); 
        nomField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new ImageIcon(getClass().getResource("/Icon/user.png")));
        add(nomField, "gapy n 5,span 3");

        add(new JLabel("Email"), "span 2");
        emailField = new JTextField();
        emailField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "example@mail.com");
        emailField.setPreferredSize(new Dimension(nomField.getPreferredSize().width, 44)); 
        emailField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new ImageIcon(getClass().getResource("/Icon/icons8-mail-22.png")));
        add(emailField, "gapy n 5,span 2");

        add(new JLabel("Adresse"), "span 2");
        adresseField = new JTextField();
        adresseField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Adresse");
        adresseField.setPreferredSize(new Dimension(nomField.getPreferredSize().width, 44)); 
        adresseField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new ImageIcon(getClass().getResource("/Icon/icons8-location-24 (2).png")));
        add(adresseField, "gapy n 5,span 2");

        add(new JLabel("Téléphone"), "span 2");
        telephoneField = new JTextField();
        telephoneField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Téléphone");
        telephoneField.setPreferredSize(new Dimension(nomField.getPreferredSize().width, 44)); 
        telephoneField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new ImageIcon(getClass().getResource("/Icon/icons8-téléphone-24 (1).png")));
        add(telephoneField, "gapy n 5,span 2");

        // Pre-fill fields if editing an existing fournisseur
        if (fournisseur != null) {
            nomField.setText(fournisseur.getNom());
            emailField.setText(fournisseur.getEmail());
            adresseField.setText(fournisseur.getAdresse());
            telephoneField.setText(fournisseur.getTelephone());
        }

         // Créer le panneau des boutons avec une mise en page MigLayout
     JPanel buttonsPanel = new JPanel(new MigLayout("wrap 2, fillx, insets n 3 n 3", "[fill, 200]push[fill, 200]"));
// Le "20" spécifie un espace de 20px entre les deux colonnes de boutons

// Créer les boutons avec leurs icônes
JButton cmdSave = new JButton(fournisseur == null ? "Enregistrer" : "Mettre à jour") {
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
cmdSave.addActionListener((ActionEvent e) -> saveFournisseur());

Dimension buttonSize = new Dimension(100, 40); // Modifiez ici pour ajuster la hauteur
cmdSave.setPreferredSize(buttonSize);
cmdCancel.setPreferredSize(buttonSize);
  buttonsPanel.add(cmdSave, "grow 0, gaptop 15");
  buttonsPanel.add(cmdCancel, "grow 0, gaptop 15, gapleft 70");

// Ajouter le panneau de boutons au formulaire
add(buttonsPanel);

    }

    private void saveFournisseur() {
        String nom = nomField.getText().trim();
        if (nom.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le nom est obligatoire.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String adresse = adresseField.getText().trim();
        String telephone = telephoneField.getText().trim();
        String email = emailField.getText().trim();

        if (fournisseur == null) {
            // Add a new fournisseur
            if (DatabaseUtils.isFournisseurDuplicateNom(nom, -1)) {
                JOptionPane.showMessageDialog(this, "Le nom '" + nom + "' est déjà utilisé par un autre fournisseur.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (DatabaseUtils.isFournisseurDuplicateTelephone(telephone, -1)) {
                JOptionPane.showMessageDialog(this, "Le téléphone '" + telephone + "' est déjà utilisé par un autre fournisseur.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (DatabaseUtils.isFournisseurDuplicateEmail(email, -1)) {
                JOptionPane.showMessageDialog(this, "L'email '" + email + "' est déjà utilisé par un autre fournisseur.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int nextFournisseurId = DatabaseUtils.getNextFournisseurId();
            fournisseur = new ModelFournisseur(nextFournisseurId, nom, adresse, telephone, email);
            String result = DatabaseUtils.addFournisseur(fournisseur);
            if ("SUCCESS".equals(result)) {
                 ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.CANCEL_OPTION); // Close the modal
                JOptionPane.showMessageDialog(this, "Fournisseur ajouté avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du fournisseur.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Update an existing fournisseur
            if (DatabaseUtils.isFournisseurDuplicateNom(nom, fournisseur.getFournisseurId())) {
                JOptionPane.showMessageDialog(this, "Le nom '" + nom + "' est déjà utilisé par un autre fournisseur.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (DatabaseUtils.isFournisseurDuplicateTelephone(telephone, fournisseur.getFournisseurId())) {
                JOptionPane.showMessageDialog(this, "Le téléphone '" + telephone + "' est déjà utilisé par un autre fournisseur.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (DatabaseUtils.isFournisseurDuplicateEmail(email, fournisseur.getFournisseurId())) {
                JOptionPane.showMessageDialog(this, "L'email '" + email + "' est déjà utilisé par un autre fournisseur.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }

            fournisseur.setNom(nom);
            fournisseur.setAdresse(adresse);
            fournisseur.setTelephone(telephone);
            fournisseur.setEmail(email);
            String result = DatabaseUtils.updateFournisseur(fournisseur);
            if ("SUCCESS".equals(result)) {
                 ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.CANCEL_OPTION); // Close the modal
                JOptionPane.showMessageDialog(this, "Fournisseur mis à jour avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
                 
          // Remettre le focus sur la table sans sélectionner de cellule

            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour du fournisseur.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public ModelFournisseur getFournisseur() {
        return fournisseur;
    }

    public String getFournisseurName() {
        return nomField.getText().trim();
    }
}
