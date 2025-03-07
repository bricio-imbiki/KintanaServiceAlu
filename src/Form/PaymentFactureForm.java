/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Form;

/**
 *
 * @author brici_6ul2f65
 */

import ConnexionDB.DatabaseUtils;
import static Form.PaymentAcompteForm.formatPrice;
import Models.ModelCommande;
import Models.ModelFacture;
import Models.ModelPaiementFacture;
import com.formdev.flatlaf.FlatClientProperties;
import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import net.miginfocom.swing.MigLayout;
import raven.datetime.component.date.DatePicker;
import raven.modal.component.ModalBorderAction;
import raven.modal.component.SimpleModalBorder;

public class PaymentFactureForm extends JPanel {

    private JLabel lbDevise;
    private JLabel lbDevise1;
    private JTextField txtAmount;
    private JTextField txtAcompte;
    private JComboBox<Object> comboPaymentType;
    private DatePicker datePicker;
    private JFormattedTextField dateEditor;

    public PaymentFactureForm(Component owner, ModelPaiementFacture facture) {
        initUI(facture);
    }

    private void initUI(ModelPaiementFacture facture) {
        // Use MigLayout to arrange components
        setLayout(new MigLayout("wrap 2,fillx,insets n 35 n 35", "[fill,200]"));
    }

    // Assuming this model is to hold the data
    public PaymentFactureForm(Component owner, ModelCommande commande) {
        initUI(commande);  // Pass the devis model to initialize the UI
    }

    private void initUI(ModelCommande commande) {
        setLayout(new MigLayout("wrap 2,fillx,insets n 35 n 35", "[fill,200]"));

        JLabel lbRequestDetail = new JLabel("Détails du Paiement");
        lbRequestDetail.putClientProperty(FlatClientProperties.STYLE, ""
                + "font:bold +2;");
        add(lbRequestDetail, "gapy 10 10,span 2");
        lbDevise = new JLabel("Ar");
        lbDevise1 = new JLabel("Ar");

        add(new JLabel("Montant  à payer"), "span 2, gaptop 10");
        txtAcompte = new JTextField();
        txtAcompte.setEditable(false);
        txtAcompte.setEnabled(false);
        txtAcompte.setText(formatPrice(commande.getLeftToPaye()));
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
        cmdPayment.addActionListener(actionEvent -> savePaymentCommandeAndConvertCommandeToFacture(commande));

        add(cmdPayment, "grow 0");
        add(cmdCancel, "grow 0, al trailing");

    }

    // Save payment and convert Devis to Commande
    private void savePaymentCommandeAndConvertCommandeToFacture(ModelCommande commande) {
        try {
            // Retrieve payment details from the UI
            String amountText = txtAmount.getText().trim();
            BigDecimal amount = new BigDecimal(amountText);
            String paymentMode = (String) comboPaymentType.getSelectedItem();

            String paymentDateString = dateEditor.getText(); // Changed variable name to avoid conflict

            // Parse the payment date string to java.util.Date
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            java.util.Date parsedDate = dateFormat.parse(paymentDateString);

            // Convert java.util.Date to java.sql.Date
            java.sql.Date paymentDate = new java.sql.Date(parsedDate.getTime());

      

            // Convert the Devis to Commande
            String conversionResult = DatabaseUtils.convertCommandeToFacture(commande.getCommandeId(),amount,paymentMode,paymentDate);
            if ("SUCCESS".equals(conversionResult)) {
                JOptionPane.showMessageDialog(this, "Commande Facturé avec succès", "Succès", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de la conversion du : " + conversionResult, "Erreur", JOptionPane.ERROR_MESSAGE);
            }

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erreur inconnue : " + e.getMessage(), "Erreur", JOptionPane.ERROR_MESSAGE);
        }
    }

}