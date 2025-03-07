package Form;

import ConnexionDB.DatabaseUtils;
import Models.ModelCommande;
import Models.ModelDevis;
import Models.ModelLigneDevis;
import Models.ModelPaiementAcompte;
import Models.ModelSousLigneChassis;
import Models.ModelSousLigneDevis;
import Models.ModelSousLigneJoint;
import Models.ModelSousLignePaumelle;
import Models.ModelSousLignePoignee;
import Models.ModelSousLigneRoulette;
import Models.ModelSousLigneSerrure;
import Models.ModelSousLigneVis;
import Models.ModelTypeVitre;
import Panel.DevisCreatePanel;
import static Panel.DevisCreatePanel.calculerMiseAJourQuantiteChassisFixeEnStock;
import static Panel.DevisCreatePanel.calculerMiseAJourQuantiteChassisOuvrantEnStock;
import static Panel.DevisCreatePanel.calculerMiseAJourQuantiteJointEnStock;
import static Panel.DevisCreatePanel.calculerMiseAJourQuantitePaumelleEnStock;
import static Panel.DevisCreatePanel.calculerMiseAJourQuantitePoigneeEnStock;
import static Panel.DevisCreatePanel.calculerMiseAJourQuantiteRouletteEnStock;
import static Panel.DevisCreatePanel.calculerMiseAJourQuantiteSerrureEnStock;
import static Panel.DevisCreatePanel.calculerMiseAJourQuantiteVisEnStock;

import com.formdev.flatlaf.FlatClientProperties;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DatePicker;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

public class PaymentAcompteForm extends JPanel {

    private JLabel lbDevise;
    private JLabel lbDevise1;
    private JTextField txtAmount;
    private JTextField txtAcompte;
    private JComboBox<Object> comboPaymentType;
    private DatePicker datePicker;
    private JFormattedTextField dateEditor;
    private int profileAluId;
    private int chassisTypeFixeId;
    private int chassisTypeOuvrantId;
    private int rouletteId;
    private int paumelleId;
    private int serrureId;
    private int visId;
    private int jointId;
    private int poigneeId;
    private ModelDevis devisId;

    public PaymentAcompteForm(Component owner, ModelPaiementAcompte acompte) {
        initUI(acompte);
    }

    private void initUI(ModelPaiementAcompte acompte) {
        // Use MigLayout to arrange components
        setLayout(new MigLayout("wrap 2,fillx,insets n 35 n 35", "[fill,200]"));
    }

    // Assuming this model is to hold the data
    public PaymentAcompteForm(Component owner, ModelDevis devis) {
        initUI(devis);  // Pass the devis model to initialize the UI
    }

    private void initUI(ModelDevis devis) {
        setLayout(new MigLayout("wrap 2,fillx,insets n 35 n 35", "[fill,200]"));

        JLabel lbRequestDetail = new JLabel("Détails du Paiement");
        lbRequestDetail.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +2;");
        add(lbRequestDetail, "gapy 10 10,span 2");
        lbDevise = new JLabel("Ar");
        lbDevise1 = new JLabel("Ar");

        add(new JLabel("Acompte à payer"), "span 2, gaptop 10");
        txtAcompte = new JTextField();
        txtAcompte.setEditable(false);
        txtAcompte.setEnabled(false);
        txtAcompte.setText(formatPrice(devis.getTotalAcompte()));
        lbDevise1.putClientProperty(FlatClientProperties.STYLE, "border:2,2,2,10;");// Example default value, to be set dynamically
        txtAcompte.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_COMPONENT, lbDevise1);

        add(txtAcompte, "gapy n 5,span 2");

        add(new JLabel("Mode de paiement"), "span 2");
        comboPaymentType = new JComboBox<>();
        comboPaymentType.addItem("Espèces");
        comboPaymentType.addItem("Chèque");
        comboPaymentType.addItem("Virement bancaire");

        add(comboPaymentType, "gapy n 5,span 2");

        add(new JLabel("Montant"));
        add(new JLabel("Date de Paiement"));

        txtAmount = new JTextField();
        dateEditor = new JFormattedTextField();
        datePicker = new DatePicker();
        datePicker.setEditor(dateEditor);
        // Définir l'éditeur du DatePicker sur dateEditor
        datePicker.setEditor(dateEditor);

        // Obtenir la date actuelle et la formater
        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");  // Format: jour/mois/année
        String formattedDate = currentDate.format(formatter);

        // Définir la date actuelle dans le champ texte
        dateEditor.setText(formattedDate);

        txtAmount.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "0.00");
        lbDevise.putClientProperty(FlatClientProperties.STYLE, "border:2,2,2,10;");
        txtAmount.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_COMPONENT, lbDevise);

        add(txtAmount);
        add(dateEditor);

        // Action buttons
        JButton cmdCancel = new JButton("Annuler");
        JButton cmdPayment = new JButton("Valider Paiement") {
            @Override
            public boolean isDefaultButton() {
                return true;
            }
        };
        cmdPayment.putClientProperty(FlatClientProperties.STYLE, "background:#28a745");
        cmdCancel.putClientProperty(FlatClientProperties.STYLE, "background:#dc3545");

        cmdCancel.addActionListener(actionEvent -> {
            ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.CANCEL_OPTION);
        });

        cmdPayment.addActionListener(actionEvent -> {
            ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.OK_OPTION);
        });

        Dimension buttonSize = new Dimension(100, 40); // Modifiez ici pour ajuster la hauteur
        cmdPayment.setPreferredSize(buttonSize);
        cmdCancel.setPreferredSize(buttonSize);
        cmdCancel.addActionListener(actionEvent -> {
            ModalBorderAction.getModalBorderAction(this).doAction(SimpleModalBorder.CANCEL_OPTION);
        });
        cmdPayment.addActionListener(actionEvent -> {
            try {
                savePaymentAcompteAndConvertDevisToCommande(devis);
            } catch (SQLException ex) {
                Logger.getLogger(PaymentAcompteForm.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        add(cmdPayment, "grow 0");
        add(cmdCancel, "grow 0, al trailing");

    }
public static String formatPrice(BigDecimal value) {
    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
    numberFormat.setMinimumFractionDigits(2);
    numberFormat.setMaximumFractionDigits(2);
    return numberFormat.format(value);
}
    // Save payment and convert Devis to Commande
private void savePaymentAcompteAndConvertDevisToCommande(ModelDevis devis) throws SQLException {
    try {
        // Vérifiez si l'objet devis est non nul
        if (devis == null) {
            JOptionPane.showMessageDialog(this, "Le devis est nul.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Récupérer les détails du paiement depuis l'interface utilisateur
        String amountText = txtAmount.getText().trim();
        if (amountText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le montant est vide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        BigDecimal amount;
        try {
            amount = new BigDecimal(amountText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Le montant est invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String paymentMode = (String) comboPaymentType.getSelectedItem();
        if (paymentMode == null || paymentMode.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Le mode de paiement n'est pas sélectionné.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String paymentDateString = dateEditor.getText();
        if (paymentDateString == null || paymentDateString.isEmpty()) {
            JOptionPane.showMessageDialog(this, "La date de paiement est vide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        java.sql.Date paymentDate;
        try {
            java.util.Date parsedDate = dateFormat.parse(paymentDateString);
            paymentDate = new java.sql.Date(parsedDate.getTime());
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "La date de paiement est invalide.", "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convertir le devis en commande si le montant est valide
        if (amount.compareTo(BigDecimal.ZERO) > 0) {
            String conversionResult = DatabaseUtils.convertDevisToCommande(devis.getDevisId(), amount, paymentMode, paymentDate);
            if ("SUCCESS".equals(conversionResult)) {
                // Gestion des lignes de devis
                List<ModelLigneDevis> ligne = DatabaseUtils.getLignesDevisForDevis(devis.getDevisId());
                //  List<ModelSousLigneDevis> sousLigneDevis = DatabaseUtils(devis.getDevisId());
                for (ModelLigneDevis ligneDevis : ligne) {
                    if (ligneDevis == null || ligneDevis.getSousLignesDevis() == null) {
                        continue;
                    }

                    for (ModelSousLigneDevis sousLigneDevis : ligneDevis.getSousLignesDevis()) {
                        if (sousLigneDevis == null) {
                            continue;
                        }

                        // Handle chassis, roulettes, paumelles, serrures, vis, joints, poignees
                        processSousLigneDevis(sousLigneDevis);
                          updateMaterialStock(sousLigneDevis);

                    }
                }

                // Mise à jour du stock des matériaux
              

                JOptionPane.showMessageDialog(this, "Devis converti en commande avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la conversion du devis: " + conversionResult, "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Le montant doit être supérieur à zéro.", "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erreur inconnue : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
    }
}

private void processSousLigneDevis(ModelSousLigneDevis sousLigneDevis) throws SQLException {
    // Process chassis
    if (sousLigneDevis.getChassis() != null) {
        for (ModelSousLigneChassis sousligneChassis : sousLigneDevis.getChassis()) {
            if (sousligneChassis != null && sousligneChassis.getChassis() != null) {
                 profileAluId = sousligneChassis.getChassis().getProfile().getProfileAluId();
                if (profileAluId != 0) {
                    chassisTypeFixeId = DatabaseUtils.getChassisTypeFixeId(profileAluId);
                    chassisTypeOuvrantId = DatabaseUtils.getChassisTypeOuvrantId(profileAluId);
                }
            }
        }
    }

    // Process roulettes
    if (sousLigneDevis.getRoulettes() != null) {
        for (ModelSousLigneRoulette sousligneroulette : sousLigneDevis.getRoulettes()) {
            if (sousligneroulette != null && sousligneroulette.getRoulette() != null) {
               rouletteId = sousligneroulette.getRoulette().getRouletteId();
            }
        }
    }

    // Process paumelles
    if (sousLigneDevis.getPaumelles() != null) {
        for (ModelSousLignePaumelle souslignePaumelle : sousLigneDevis.getPaumelles()) {
            if (souslignePaumelle != null && souslignePaumelle.getPaumelle() != null) {
                paumelleId = souslignePaumelle.getPaumelle().getPaumelleId();
            }
        }
    }

    // Process serrures
    if (sousLigneDevis.getSerrures() != null) {
        for (ModelSousLigneSerrure sousligneSerrure : sousLigneDevis.getSerrures()) {
            if (sousligneSerrure != null && sousligneSerrure.getSerrure() != null) {
                serrureId = sousligneSerrure.getSerrure().getSerrureId();
            }
        }
    }

    // Process vis
    if (sousLigneDevis.getVis() != null) {
        for (ModelSousLigneVis sousligneVis : sousLigneDevis.getVis()) {
            if (sousligneVis != null && sousligneVis.getVis() != null) {
              visId = sousligneVis.getVis().getVisId();
            }
        }
    }

    // Process joints
    if (sousLigneDevis.getJoints() != null) {
        for (ModelSousLigneJoint sousligneJoint : sousLigneDevis.getJoints()) {
            if (sousligneJoint != null && sousligneJoint.getJoint() != null) {
              jointId = sousligneJoint.getJoint().getJointId();
            }
        }
    }

    // Process poignees
    if (sousLigneDevis.getPoignees() != null) {
        for (ModelSousLignePoignee souslignePoignee : sousLigneDevis.getPoignees()) {
            if (souslignePoignee != null && souslignePoignee.getPoignee() != null) {
                poigneeId = souslignePoignee.getPoignee().getPoigneeId();
            }
        }
    }
}

private void updateMaterialStock(ModelSousLigneDevis sousLigneDevis) throws SQLException {
    // Update stock for different materials
    BigDecimal quantiteRouletteUtiliser = calculerMiseAJourQuantiteRouletteEnStock(sousLigneDevis);
    BigDecimal quantitePaumelleUtiliser = calculerMiseAJourQuantitePaumelleEnStock(sousLigneDevis);
    BigDecimal quantiteSerrureUtiliser = calculerMiseAJourQuantiteSerrureEnStock(sousLigneDevis);
    BigDecimal quantiteVisUtiliser = calculerMiseAJourQuantiteVisEnStock(sousLigneDevis);
    BigDecimal quantiteJointUtiliser = calculerMiseAJourQuantiteJointEnStock(sousLigneDevis);
    BigDecimal quantitePoigneeUtiliser = calculerMiseAJourQuantitePoigneeEnStock(sousLigneDevis);

    // Update stock for chassis
    BigDecimal quantityUsedChassisFixe = calculerMiseAJourQuantiteChassisFixeEnStock(sousLigneDevis);
    BigDecimal quantityUsedChassisOuvrant = calculerMiseAJourQuantiteChassisOuvrantEnStock(sousLigneDevis);

    // Ensure to update stock in database if quantities are greater than zero
    if (quantiteRouletteUtiliser.compareTo(BigDecimal.ZERO) > 0) {
        DatabaseUtils.updateRouletteStock(rouletteId, quantiteRouletteUtiliser);
    }
    if (quantitePaumelleUtiliser.compareTo(BigDecimal.ZERO) > 0) {
        DatabaseUtils.updatePaumelleStock(paumelleId, quantitePaumelleUtiliser);
    }
    if (quantiteSerrureUtiliser.compareTo(BigDecimal.ZERO) > 0) {
        DatabaseUtils.updateSerrureStock(serrureId, quantiteSerrureUtiliser);
    }
    if (quantiteVisUtiliser.compareTo(BigDecimal.ZERO) > 0) {
        DatabaseUtils.updateVisStock(visId, quantiteVisUtiliser);
    }
    if (quantiteJointUtiliser.compareTo(BigDecimal.ZERO) > 0) {
        DatabaseUtils.updateJointStock(jointId, quantiteJointUtiliser);
    }
    if (quantitePoigneeUtiliser.compareTo(BigDecimal.ZERO) > 0) {
        DatabaseUtils.updatePoigneeStock(poigneeId, quantitePoigneeUtiliser);
    }

    if (quantityUsedChassisFixe.compareTo(BigDecimal.ZERO) > 0 &&
        quantityUsedChassisOuvrant.compareTo(BigDecimal.ZERO) > 0) {
        DatabaseUtils.updateChasssisStock(chassisTypeFixeId, quantityUsedChassisFixe);
        DatabaseUtils.updateChasssisStock(chassisTypeOuvrantId, quantityUsedChassisOuvrant);
    }
}

}


