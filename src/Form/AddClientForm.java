package Form;

import ConnexionDB.DatabaseUtils;
import Models.ModelClient;
import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import net.miginfocom.swing.MigLayout;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

public class AddClientForm extends JPanel {

    private ModelClient client;
    private JTextField nomField;
    private JTextField adresseField;
    private JTextField telephoneField;
    private JTextField emailField;
 

       public AddClientForm (Component owner) {
        initUI(null);

    }

    public AddClientForm(Component owner, ModelClient client) {
        this.client = client;
        initUI(client);
 
       
    }

private void initUI(ModelClient client) {
    setLayout(new MigLayout("wrap 2, fillx, insets n 35 n 35", "[fill, 200]"));
  //  setPreferredSize(new Dimension(400, getPreferredSize().height));
    // Ajouter les champs avec une hauteur augmentée et icônes
    add(new JLabel("Nom"),"span 2");
    nomField = new JTextField();
    nomField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nom");
  nomField.setPreferredSize(new Dimension(nomField.getPreferredSize().width, 44)); 
    nomField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new ImageIcon(getClass().getResource("/Icon/user.png")));
    add(nomField, "gapy n 5,span 3");

    add(new JLabel("Email"),"span 2");
    emailField = new JTextField();
    emailField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "example@mail.com");
     emailField.setPreferredSize(new Dimension(nomField.getPreferredSize().width, 44)); 
    emailField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON,  new ImageIcon(getClass().getResource("/Icon/icons8-mail-22.png")));
    add(emailField,"gapy n 5,span 2");

    add(new JLabel("Adresse"),"span 2");
    adresseField = new JTextField();
    adresseField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Adresse");
    adresseField.setPreferredSize(new Dimension(nomField.getPreferredSize().width, 44)); 
    adresseField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new ImageIcon(getClass().getResource("/Icon/icons8-location-24 (2).png")));
    add(adresseField,"gapy n 5,span 2");

    add(new JLabel("Téléphone"),"span 2");
    telephoneField = new JTextField();
    telephoneField.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Téléphone");
    telephoneField.setPreferredSize(new Dimension(nomField.getPreferredSize().width, 44)); 
    telephoneField.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON,  new ImageIcon(getClass().getResource("/Icon/icons8-téléphone-24 (1).png")));
    add(telephoneField,"gapy n 5,span 2");

    // Pre-fill fields if editing an existing client
        if (client != null) {
            nomField.setText(client.getNom());
            emailField.setText(client.getEmail());
            adresseField.setText(client.getAdresse());
            telephoneField.setText(client.getTelephone());
        }



  // Créer le panneau des boutons avec une mise en page MigLayout
 JPanel buttonsPanel = new JPanel(new MigLayout("wrap 2, fillx, insets n 3 n 3", "[fill, 200]push[fill, 200]"));
// Le "20" spécifie un espace de 20px entre les deux colonnes de boutons

// Créer les boutons avec leurs icônes
JButton cmdSave = new JButton(client == null ? "Enregistrer" : "Mettre à jour") {
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
cmdSave.addActionListener((ActionEvent e) -> saveClient());
Dimension buttonSize = new Dimension(100, 40); // Modifiez ici pour ajuster la hauteur
cmdSave.setPreferredSize(buttonSize);
//cmdSave.putClientProperty(FlatClientProperties.STYLE, ""
//                + "$Button.background");
cmdCancel.setPreferredSize(buttonSize);
  buttonsPanel.add(cmdSave, "grow 0, gaptop 15");
  buttonsPanel.add(cmdCancel, "grow 0, gaptop 15, gapleft 70");


// Ajouter le panneau de boutons au formulaire
add(buttonsPanel);
}

    private void saveClient() {
         String nom = nomField.getText().trim();
        String adresse = adresseField.getText().trim();
        String telephone = telephoneField.getText().trim();
        String email = emailField.getText().trim();
        if (nom.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le nom est obligatoire.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

    

        if (client == null) {
               if (DatabaseUtils.isClientDuplicateNom(nom, -1)) {
                JOptionPane.showMessageDialog(this, "Le nom '" + nom + "' est déjà utilisé par un autre client.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
                    if (DatabaseUtils.isClientDuplicateTelephone(telephone, -1)) {
                JOptionPane.showMessageDialog(this, "Le téléphone '" + telephone + "' est déjà utilisé par un autre client.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
             if (DatabaseUtils.isClientDuplicateEmail(email, -1)) {
                JOptionPane.showMessageDialog(this, "Email '" + email + "' eest déjà utilisé par un autre client.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } 
            
            // Additional duplicate checks
            Date dateCreation = new Date();
            client = new ModelClient(DatabaseUtils.getNextClientId(), nom, adresse, telephone, email, dateCreation);
            if ("SUCCESS".equals(DatabaseUtils.addClient(client))) {
                  ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.CANCEL_OPTION); // Close the modal
                  
                JOptionPane.showMessageDialog(this, "Client ajouté avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
             
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du client.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
                if (DatabaseUtils.isClientDuplicateNom(nom, client.getClientId())) {
                JOptionPane.showMessageDialog(this, "Le nom '" + nom + "' est déjà utilisé par un autre client.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
                    if (DatabaseUtils.isClientDuplicateTelephone(telephone, client.getClientId())) {
                JOptionPane.showMessageDialog(this, "Le téléphone '" + telephone + "' est déjà utilisé par un autre client.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
             if (DatabaseUtils.isClientDuplicateEmail(email, client.getClientId())) {
                JOptionPane.showMessageDialog(this, "Email '" + email + "' eest déjà utilisé par un autre client.", "Erreur", JOptionPane.ERROR_MESSAGE);
            } 
            // Update client logic
            client.setNom(nom);
            client.setAdresse(adresse);
            client.setTelephone(telephone);
            client.setEmail(email);
            
            if ("SUCCESS".equals(DatabaseUtils.updateClient(client))) {
                 ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.CANCEL_OPTION); // Close the modal
                JOptionPane.showMessageDialog(this, "Client mis à jour avec succès!", "Succès", JOptionPane.INFORMATION_MESSAGE);
              
            }else{
                JOptionPane.showMessageDialog(this, "Erreur lors de la mise à jour du client.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
       public ModelClient getClient() {
        return client;
    }

    public String getClientName() {
        return nomField.getText().trim();
    }
    
}
