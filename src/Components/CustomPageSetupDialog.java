package Components;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.awt.print.PageFormat;
import java.awt.print.PrinterJob;

public class CustomPageSetupDialog extends JDialog {
    private JComboBox<String> orientationCombo;
    private JFormattedTextField marginTopField;
    private JFormattedTextField marginBottomField;
    private JFormattedTextField marginLeftField;
    private JFormattedTextField marginRightField;
    private JButton confirmButton;
    private PageFormat pageFormat;
    private JPanel previewPanel;

    public CustomPageSetupDialog(Frame owner, PageFormat pageFormat) {
        super(owner, "Configuration de page", true);
        this.pageFormat = pageFormat;

        setSize(500, 400);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // Panneau principal pour les paramètres
        JPanel mainPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        JPanel settingsPanel = new JPanel(new BorderLayout());
        settingsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Orientation
        mainPanel.add(new JLabel("Orientation :"));
        orientationCombo = new JComboBox<>(new String[]{"Portrait", "Paysage"});
        orientationCombo.setSelectedIndex(pageFormat.getOrientation() == PageFormat.PORTRAIT ? 0 : 1);
        orientationCombo.addActionListener(e -> updatePreview());
        mainPanel.add(orientationCombo);

        // Marges
        NumberFormat numberFormat = NumberFormat.getNumberInstance();
        numberFormat.setMaximumFractionDigits(2);

        mainPanel.add(new JLabel("Marge supérieure (mm) :"));
        marginTopField = new JFormattedTextField(numberFormat);
        marginTopField.setValue(25); // Valeur par défaut en mm
        mainPanel.add(marginTopField);

        mainPanel.add(new JLabel("Marge inférieure (mm) :"));
        marginBottomField = new JFormattedTextField(numberFormat);
        marginBottomField.setValue(25);
        mainPanel.add(marginBottomField);

        mainPanel.add(new JLabel("Marge gauche (mm) :"));
        marginLeftField = new JFormattedTextField(numberFormat);
        marginLeftField.setValue(25);
        mainPanel.add(marginLeftField);

        mainPanel.add(new JLabel("Marge droite (mm) :"));
        marginRightField = new JFormattedTextField(numberFormat);
        marginRightField.setValue(25);
        mainPanel.add(marginRightField);

        settingsPanel.add(mainPanel, BorderLayout.NORTH);
        add(settingsPanel, BorderLayout.WEST);

        // Panneau de prévisualisation
        previewPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawPagePreview(g);
            }
        };
        previewPanel.setPreferredSize(new Dimension(200, 250));
        add(previewPanel, BorderLayout.CENTER);

        // Bouton de confirmation
        confirmButton = new JButton("Confirmer");
        confirmButton.addActionListener(e -> {
            applyPageSettings();
            dispose(); // Ferme la boîte de dialogue
        });
        add(confirmButton, BorderLayout.SOUTH);

        updatePreview();
    }

    private void applyPageSettings() {
        pageFormat.setOrientation(orientationCombo.getSelectedIndex() == 0 ? PageFormat.PORTRAIT : PageFormat.LANDSCAPE);

        double topMargin = ((Number) marginTopField.getValue()).doubleValue();
        double bottomMargin = ((Number) marginBottomField.getValue()).doubleValue();
        double leftMargin = ((Number) marginLeftField.getValue()).doubleValue();
        double rightMargin = ((Number) marginRightField.getValue()).doubleValue();

        PrinterJob printerJob = PrinterJob.getPrinterJob();
        PageFormat newPageFormat = printerJob.defaultPage();
        newPageFormat.setOrientation(pageFormat.getOrientation());

        newPageFormat.getPaper().setImageableArea(
            leftMargin, topMargin,
            newPageFormat.getPaper().getWidth() - leftMargin - rightMargin,
            newPageFormat.getPaper().getHeight() - topMargin - bottomMargin
        );

        this.pageFormat = newPageFormat;
    }

    private void updatePreview() {
        previewPanel.repaint();
    }

    private void drawPagePreview(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, previewPanel.getWidth(), previewPanel.getHeight());
        
        g2d.setColor(Color.BLACK);
        g2d.drawRect(10, 10, previewPanel.getWidth() - 20, previewPanel.getHeight() - 20);

        if (orientationCombo.getSelectedIndex() == 1) { // Paysage
            g2d.drawString("Paysage", previewPanel.getWidth() / 2 - 30, previewPanel.getHeight() / 2);
        } else { // Portrait
            g2d.drawString("Portrait", previewPanel.getWidth() / 2 - 30, previewPanel.getHeight() / 2);
        }
    }

    public PageFormat showDialog() {
        setVisible(true);
        return pageFormat;
    }
}
