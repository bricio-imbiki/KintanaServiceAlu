/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Form;

import ConnexionDB.DatabaseUtils;
import static Panel.DevisCreatePanel.setComboBoxRenderer;
import Models.ModelProfileAlu;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;
import java.util.List;
public class AddProfileAluForm extends JPanel {

    private JTextField modelProfileField;
    private JTextField couleurProfileField;
    private JButton cmdSave;
    private JButton cmdCancel;

    public AddProfileAluForm() {
        setupUI();
    }

    private void setupUI() {
        setLayout(new MigLayout("wrap 2, fillx, insets n 35 n 35", "[fill, 200]"));
      
        // Label et champ pour "Model Profile" avec bouton d'ouverture
        add(new JLabel("Modèle Profilé:"), "span 2");
        modelProfileField = new JTextField();
       modelProfileField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Modele Profilé");
        modelProfileField.setPreferredSize(new Dimension(modelProfileField.getPreferredSize().width, 20));
        add(modelProfileField, "gapy n 5,span 2");

       

        // Label et champ pour "Couleur"
        add(new JLabel("Couleur:"), "span 2");
       couleurProfileField = new JTextField();
              couleurProfileField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Couleur Profile");
        couleurProfileField.setPreferredSize(new Dimension(couleurProfileField.getPreferredSize().width, 20));
        add(couleurProfileField, "gapy n 5,span 2");

        // Panneau des boutons (Enregistrer et Annuler)
        JPanel buttonsPanel = new JPanel(new MigLayout("wrap 2, fillx, insets n 3 n 3", "[fill, 200]push[fill, 200]"));

        cmdSave = new JButton("Enregistrer") {
    @Override
    public boolean isDefaultButton() {
        return true;
    }
};
      
     
        cmdSave.addActionListener((ActionEvent e) -> {
            saveProfileAlu();
        });
       

cmdSave.setIcon(new FlatSVGIcon("Icon/save.svg", 20, 20));

JButton cmdCancel = new JButton("Annuler");
cmdCancel.setIcon(new FlatSVGIcon("Icon/cancel.svg", 16, 16));

     
      cmdCancel.addActionListener(actionEvent -> {
    ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.CANCEL_OPTION);
    
});
     Dimension buttonSize = new Dimension(100, 40); // Modifiez ici pour ajuster la hauteur
cmdSave.setPreferredSize(buttonSize);
cmdCancel.setPreferredSize(buttonSize);
  buttonsPanel.add(cmdSave, "grow 0, gaptop 15");
  buttonsPanel.add(cmdCancel, "grow 0, gaptop 15, gapleft 70");

        add(buttonsPanel);
          addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
          requestFocusInWindow();
            }
        });
    }
         private void saveProfileAlu() {
    // Retrieve values from text fields
    String modelProfile = modelProfileField.getText().trim();
    String couleurProfile = couleurProfileField.getText().trim();

    // Validate inputs
    if (modelProfile.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Veuillez entrer le modèle du profil.", "Erreur", JOptionPane.ERROR_MESSAGE);
        return;
    }
    if (couleurProfile.isEmpty()) {
        JOptionPane.showMessageDialog(this, "Veuillez entrer la couleur du profil.", "Erreur", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Save the profile (Example logic - replace with actual database save logic)
    try {
        // Assuming you have a ProfileAlu model and a DatabaseUtils class for database operations
        ModelProfileAlu profile = new ModelProfileAlu(0,modelProfile,couleurProfile);
      

        // Use DatabaseUtils to save the profile to the database
        boolean success = DatabaseUtils.addProfileAlu(profile);

        if (success) {
            ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.CANCEL_OPTION);
            updateProfileComboBox(profile.toString());
            JOptionPane.showMessageDialog(this, "Profil enregistré avec succès.", "Succès", JOptionPane.INFORMATION_MESSAGE);
            
       } else {
                 ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.CANCEL_OPTION);
        
            JOptionPane.showMessageDialog(this, "Erreur lors de l'enregistrement du profil.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }

    } catch (Exception ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(this, "Une erreur s'est produite : " + ex.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}

private void updateProfileComboBox(String selectedProfile) {
    try {
        // Vider les éléments existants avant d'ajouter les nouveaux
        AddChassisForm.modelProfileAluCombo.removeAllItems();
        AddChassisForm.modelProfileAluCombo.addItem(""); // Ajouter une option vide au début, si nécessaire

        // Récupérer la liste des profils
        List<ModelProfileAlu> allProfiles = DatabaseUtils.retrieveAllProfileAlu();
        
        for (ModelProfileAlu profile : allProfiles) {
            String profileName = profile.toString();
            AddChassisForm.modelProfileAluCombo.addItem(profileName);

            // Vérifier si c'est le profil récemment ajouté pour le sélectionner
            if (profileName.equals(selectedProfile)) {
                AddChassisForm.modelProfileAluCombo.setSelectedItem(profileName);
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erreur lors du chargement des profils.", "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}


}