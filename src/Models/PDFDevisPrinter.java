//package Models;
//
//import ConnexionDB.DatabaseUtils;
//import com.lowagie.text.BadElementException;
//import com.lowagie.text.Image;
//import com.lowagie.text.Rectangle;
//import com.lowagie.text.pdf.PdfWriter;
//import java.awt.print.PageFormat;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.sql.SQLException;
//import java.text.DecimalFormat;
//import java.text.NumberFormat;
//import java.util.Locale;
//import javax.swing.JOptionPane;
//import javax.swing.text.Document;
//
//
//public class PDFDevisPrinter {
//
//
//    // Méthode principale pour générer un PDF du devis
//    public static void generateDevisPDF(int devisId, PageFormat pageFormat, FileOutputStream outputStream) throws IOException, SQLException, BadElementException {
//        ModelDevis devis = DatabaseUtils.getDevisById(devisId);
//
//       if (devis == null) {
//    JOptionPane.showMessageDialog(null, 
//        "La devis avec l'ID " + devisId + " n'existe pas.", 
//        "Devis Introuvable", 
//        JOptionPane.WARNING_MESSAGE);
//    return; // Exit the method after showing the dialog
//}
//     
//        Image logo = Image.getInstance("src/Icon/kintana.png");
//        float width = (float) pageFormat.getWidth();
//        float height = (float) pageFormat.getHeight();
//        Rectangle pageSize = new Rectangle(width, height);
//
//        Document document = new Document(pageSize);
//        PdfWriter.getInstance(document, outputStream);
//        document.open();
//
//        // Section des informations de l'entreprise et du client
//        PdfPTable infoTable = new PdfPTable(3);
//        infoTable.setWidthPercentage(100);
//        float[] columnWidths = {2f, 0.2f, 1.5f};
//        infoTable.setWidths(columnWidths);
//
//        // Infos de l'entreprise avec logo
//        PdfPCell companyInfoCell = new PdfPCell();
//        companyInfoCell.setBorder(Rectangle.BOX);
//        companyInfoCell.setPadding(10);
//        PdfPTable companyTable = new PdfPTable(2);
//        float[] innerColumnWidths = {1f, 3f};
//        companyTable.setWidths(innerColumnWidths);
//        companyTable.setWidthPercentage(100);
//        
//        logo.scaleToFit(50, 50);
//        PdfPCell logoCell = new PdfPCell(logo, true);
//        logoCell.setBorder(Rectangle.NO_BORDER);
//        companyTable.addCell(logoCell);
//
//        PdfPCell companyDetailsCell = new PdfPCell();
//        companyDetailsCell.setBorder(Rectangle.NO_BORDER);
//        Font companyInfoFont = new Font(Font.FontFamily.HELVETICA, 10);
//        Font companyNameFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
//        Paragraph companyNameParagraph = new Paragraph("KINTANA SERVICE ALUMINIUM", companyNameFont);
//        companyDetailsCell.addElement(companyNameParagraph);
//        companyDetailsCell.addElement(new Paragraph("Expertise en Menuserie Aluminium", companyInfoFont));
//        companyDetailsCell.addElement(new Paragraph("105 Sainte-Marie, Madagascar", companyInfoFont));
//        companyDetailsCell.addElement(new Paragraph("Téléphone: +261 32 12 345 67", companyInfoFont));
//        companyDetailsCell.addElement(new Paragraph("Email: kintanaServiceAlu@gmail.com", companyInfoFont));
//         companyDetailsCell.addElement(new Paragraph("NIF : 5000 487 757", companyInfoFont));
//         companyDetailsCell.addElement(new Paragraph("STAT : 471 90 31 1998 0 00270", companyInfoFont));
//        companyTable.addCell(companyDetailsCell);
//        companyInfoCell.addElement(companyTable);
//        infoTable.addCell(companyInfoCell);
//
//        PdfPCell spacerCell = new PdfPCell(new Paragraph(" "));
//        spacerCell.setBorder(Rectangle.NO_BORDER);
//        infoTable.addCell(spacerCell);
//
//        PdfPCell clientInfoCell = new PdfPCell();
//        clientInfoCell.setBorder(Rectangle.BOX);
//        clientInfoCell.setPadding(10);
//        Font clientInfoFont = new Font(Font.FontFamily.HELVETICA, 10);
//        clientInfoCell.addElement(new Paragraph("Numéro de Devis : " + devis.getDevisId(), clientInfoFont));
//         clientInfoCell.addElement(new Paragraph("Date de Mesure : " + devis.getFormattedDateDevis(), clientInfoFont));
//        clientInfoCell.addElement(new Paragraph("Client : " + devis.getClient().getNom(), clientInfoFont));
//        clientInfoCell.addElement(new Paragraph("Adresse : " + devis.getClient().getAdresse(), clientInfoFont));
//        clientInfoCell.addElement(new Paragraph("Téléphone : " + devis.getClient().getTelephone(), clientInfoFont));
//        clientInfoCell.addElement(new Paragraph("Email : " + devis.getClient().getEmail(), clientInfoFont));
//        infoTable.addCell(clientInfoCell);
//        document.add(infoTable);
//
//        document.add(new Paragraph(" "));
//        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
//        Paragraph title = new Paragraph("Devis", titleFont);
//        title.setAlignment(Element.ALIGN_CENTER);
//        document.add(title);
//        document.add(new Paragraph(" "));
//
//        // Table des lignes de devis
//        PdfPTable table = new PdfPTable(4);
//        table.setWidthPercentage(100);
//        table.setSpacingBefore(10f);
//        table.setSpacingAfter(10f);
//          table.setWidths(new float[]{ 1.5f, 4f, 2f, 2f});
//        addTableHeader(table);
//
//        for (ModelLigneDevis ligneDevis : devis.getLignesDevis()) {
//            addLigneDevisToTable(table, ligneDevis);
//            for (ModelSousLigneDevis sousLigneDevis : ligneDevis.getSousLignesDevis()) {
//                addSousLigneDevisToTable(table, sousLigneDevis);
//            }
//        }
//        document.add(table);
//
//// Création du tableau de total avec bordures
//PdfPTable totalTable = new PdfPTable(2);
//totalTable.setWidthPercentage(40);
//totalTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
//totalTable.setSpacingBefore(10f);
//
//// Définition des largeurs des colonnes : la colonne des valeurs est deux fois plus large que celle des labels
//float[] columnTotalWidths = {1.3f, 1.5f}; // 1 pour les labels, 2 pour les valeurs
//totalTable.setWidths(columnTotalWidths);
//
//String[] labels = {"Total HT", "Remise (" + devis.getRemisePercentage() + "%)", "Acompte  (" + devis.getAcomptePercentage() + "%)", "TVA (" + devis.getTvaPercentage() + "%)", "Total TTC"};
//BigDecimal[] values = {
//    devis.getTotalHT(),
//    devis.getTotalRemise(),
//    devis.getTotalAcompte(),
//    devis.getTotalTva(),
//    devis.getTotalTTC()
//};
//
//// Remplissage des lignes avec bordures
//for (int i = 0; i < labels.length; i++) {
//    // Use bold font for labels
//    Font labelFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
//
//    // Make the "Total TTC" value bold by using a conditional check
//    Font valueFont = (labels[i].equals("Total TTC")) 
//        ? new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD) 
//        : new Font(Font.FontFamily.HELVETICA, 12, Font.NORMAL);
//
//    PdfPCell labelCell = new PdfPCell(new Phrase(labels[i], labelFont));
//    labelCell.setHorizontalAlignment(Element.ALIGN_LEFT); // Alignement à gauche pour les libellés
//    labelCell.setBackgroundColor(BaseColor.LIGHT_GRAY);
//    totalTable.addCell(labelCell);
//
//    PdfPCell valueCell = new PdfPCell(new Phrase(formatCurrency(values[i]), valueFont));
//    valueCell.setHorizontalAlignment(Element.ALIGN_RIGHT); // Alignement à droite pour les valeurs
//    totalTable.addCell(valueCell);
//}
//
//
//
//        document.add(totalTable);
//        
//        // Ajout des conditions de paiement
//        document.add(new Paragraph("\nConditions de Paiement :", new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD)));
//        document.add(new Paragraph("Un acompte de "+ devis.getAcomptePercentage() + "% est requis à la signature. Le solde restant doit être réglé à la livraison." ,new Font(Font.FontFamily.HELVETICA, 10)));
//        document.add(new Paragraph("\n\n"));
//// Ajouter un espace pour la signature du client
//PdfPTable signatureTable = new PdfPTable(1);
//signatureTable.setWidthPercentage(40); // Largeur ajustée pour occuper la partie gauche
//signatureTable.setHorizontalAlignment(Element.ALIGN_RIGHT); // Alignement à gauche
//Font infoFont = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD);
//PdfPCell signatureCell = new PdfPCell(new Phrase("Signature du client ", infoFont));
//signatureCell.setBorder(Rectangle.NO_BORDER);
//signatureCell.setFixedHeight(50); // Hauteur fixe pour l'espace de signature
//
//signatureTable.addCell(signatureCell);
//
//// Ajouter la table de signature au document
//document.add(signatureTable);
//
//        document.close();
// JOptionPane.showMessageDialog(null, "Le devis a été généré avec succès.");
//       
//    }
//// Fonction de formatage pour BigDecimal
//public static String formatCurrency(BigDecimal value) {
//    NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.US);
//    numberFormat.setMinimumFractionDigits(2);
//    numberFormat.setMaximumFractionDigits(2);
//    return numberFormat.format(value) + " Ar";
//}
//    private static void addTableHeader(PdfPTable table) {
//        String[] headers = {"Quantité", "Designation", "Prix Unitaire", "Prix Total"};
//        Font headerFont = new Font(Font.FontFamily.HELVETICA, 12, Font.BOLD);
//        for (String headerTitle : headers) {
//            PdfPCell header = new PdfPCell(new Phrase(headerTitle, headerFont));
//            header.setHorizontalAlignment(Element.ALIGN_CENTER);
//            header.setVerticalAlignment(Element.ALIGN_MIDDLE); 
//            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
//            table.addCell(header);
//        }
//    }
//
//    private static void addLigneDevisToTable(PdfPTable table, ModelLigneDevis ligneDevis) {
//        PdfPCell cell;
//
//        cell = new PdfPCell(new Phrase(String.valueOf(ligneDevis.getTotalQuantite())));
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);   // Centre verticalement
//        table.addCell(cell);
//        
//        cell = new PdfPCell(new Phrase(ligneDevis.getDesignation(), new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase(""));
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        table.addCell(cell);
//
//
//        cell = new PdfPCell(new Phrase(formatCurrency(ligneDevis.getTotalPrix())));
//        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
//        table.addCell(cell);
//    }
//
//    private static void addSousLigneDevisToTable(PdfPTable table, ModelSousLigneDevis sousLigneDevis) {
//              PdfPCell QuantiteCell = new PdfPCell(new Phrase(String.valueOf(sousLigneDevis.getQuantite())));
//        QuantiteCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//              QuantiteCell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
//        
//        table.addCell(QuantiteCell);
//     
//        PdfPCell dimensionCell = new PdfPCell(new Phrase(sousLigneDevis.toString()));
//        dimensionCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        dimensionCell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
//        table.addCell(dimensionCell);
//
//        PdfPCell prixUnitaireCell = new PdfPCell(new Phrase(formatCurrency(sousLigneDevis.getPrixUnitaire())));
//        prixUnitaireCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        prixUnitaireCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        table.addCell(prixUnitaireCell);
//
//  
//        
//       PdfPCell PrixTotaleCell = new PdfPCell(new Phrase(formatCurrency(sousLigneDevis.getPrixTotal())));
//         
//        PrixTotaleCell.setHorizontalAlignment(Element.ALIGN_RIGHT); 
//         PrixTotaleCell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
//        table.addCell(PrixTotaleCell);
//      
//    }
//
//  
//}
