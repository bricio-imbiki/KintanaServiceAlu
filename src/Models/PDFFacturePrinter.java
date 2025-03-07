///*
// * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
// * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
// */
//package Models;
//
//import ConnexionDB.DatabaseUtils;
//import static Models.PDFDevisPrinter.formatCurrency;
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.Image;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.Phrase;
//import com.itextpdf.text.Rectangle;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfWriter;
//import java.awt.print.PageFormat;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.math.BigDecimal;
//import java.sql.SQLException;
//import javax.swing.JOptionPane;
//
///**
// *
// * @author brici_6ul2f65
// */
//public class PDFFacturePrinter {
//    
//
//
//
//    // Méthode principale pour générer un PDF du devis
//    public static void generateFacturePDF(int factureId, PageFormat pageFormat, FileOutputStream outputStream) throws DocumentException, IOException, SQLException {
//    ModelFacture facture = DatabaseUtils.getFactureById(factureId);
//
//      if (facture == null) {
//    JOptionPane.showMessageDialog(null, 
//        "La facture avec l'ID " + factureId + " n'existe pas.", 
//        "Facture Introuvable", 
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
//        companyDetailsCell.addElement(new Paragraph("NIF : 5000 487 757", companyInfoFont));
//        companyDetailsCell.addElement(new Paragraph("STAT : 471 90 31 1998 0 00270", companyInfoFont));
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
//        clientInfoCell.addElement(new Paragraph("Numéro de Facture : " + facture.getFactureId(), clientInfoFont));
//         clientInfoCell.addElement(new Paragraph("Date d'Emission : " + facture.getFormattedDateFacture(), clientInfoFont));
//        clientInfoCell.addElement(new Paragraph("Client : " + facture.getClient().getNom(), clientInfoFont));
//        clientInfoCell.addElement(new Paragraph("Adresse : " + facture.getClient().getAdresse(), clientInfoFont));
//        clientInfoCell.addElement(new Paragraph("Téléphone : " + facture.getClient().getTelephone(), clientInfoFont));
//        clientInfoCell.addElement(new Paragraph("Email : " + facture.getClient().getEmail(), clientInfoFont));
//        infoTable.addCell(clientInfoCell);
//        document.add(infoTable);
//
//        document.add(new Paragraph(" "));
//        Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
//        Paragraph title = new Paragraph("Facture", titleFont);
//        title.setAlignment(Element.ALIGN_CENTER);
//        document.add(title);
//        document.add(new Paragraph(" "));
//
//        // Table des lignes de devis
//        PdfPTable table = new PdfPTable(4);
//    table.setWidthPercentage(100);
//    table.setSpacingBefore(10f);
//
//    // Dynamically allocate more space to columns 2, 3, and 4 for flexible adjustment
//         table.setWidths(new float[]{ 1.5f, 4f, 2f, 2f});
//    addTableHeader(table);
//
//
//// Ajout des lignes de facture et des sous-lignes au PDF
//   // Débogage : Afficher l'ID de la facture
// 
//    // ... (le reste du code pour configurer le document PDF)
//
//    // Ajout des lignes de facture et des sous-lignes au PDF
//    for (ModelLigneFacture ligneFacture : facture.getLignesFacture()) {
//        // Débogage : Afficher l'ID de la ligne de facture associée
//      
//        // Ajoute la ligne principale de la facture
//        addLigneFactureToTable(table, ligneFacture);
//        
//        // Ajoute chaque sous-ligne de la ligne principale
//        for (ModelSousLigneFacture sousLigneFacture : ligneFacture.getSousLigneFacture()) {
//            // Débogage : Afficher l'ID de la sous-ligne de facture associée
//           
//            addSousLigneFactureToTable(table, sousLigneFacture); // Assurez-vous que cette méthode formate les sous-lignes
//        }
//    }
//        document.add(table);
//
//// Création du tableau de total avec bordures
//PdfPTable totalTable = new PdfPTable(2);
//totalTable.setWidthPercentage(40);
//totalTable.setHorizontalAlignment(Element.ALIGN_RIGHT);
//totalTable.setSpacingBefore(10f);
//
//// Définition des largeurs des colonnes : la colonne des valeurs est deux fois plus large que celle des labels
//float[] columnTotalWidths = {1.5f, 1.5f}; // 1 pour les labels, 2 pour les valeurs
//totalTable.setWidths(columnTotalWidths);
//
//// Libellés et valeurs pour chaque ligne de total
//
//String[] labels = {"Total HT", "Remise (" + facture.getRemiseFacturePercentage() + "%)", "TVA (" + facture.getTvaFacturePercentage() + "%)", "Total TTC"};
//BigDecimal[] values = {
// facture.getTotalFactureHT(),
//    facture.getTotalFactureRemise(),
//    facture.getTotalFactureTva(),
//  facture.getTotalFactureTTC()
//};
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
//        document.add(totalTable);
//
//        document.close();
// JOptionPane.showMessageDialog(null, "Le facture a été généré avec succès.");
//       
//    }
//
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
//    private static void addLigneFactureToTable(PdfPTable table, ModelLigneFacture ligneFacture) {
//        PdfPCell cell;
//
//        cell = new PdfPCell(new Phrase(String.valueOf(ligneFacture.getTotalQuantite())));
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);   // Centre verticalement
//        table.addCell(cell);
//        
//        cell = new PdfPCell(new Phrase(ligneFacture.getDesignation(), new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD)));
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
//        table.addCell(cell);
//
//        cell = new PdfPCell(new Phrase(""));
//        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        table.addCell(cell);
//
//     
//
//        cell = new PdfPCell(new Phrase(formatCurrency(ligneFacture.getTotalPrix())));
//        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        cell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
//        table.addCell(cell);
//    }
//
//    private static void addSousLigneFactureToTable(PdfPTable table, ModelSousLigneFacture sousLigneFacture) {
//           PdfPCell QuantiteCell = new PdfPCell(new Phrase(String.valueOf(sousLigneFacture.getQuantite())));
//        QuantiteCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//              QuantiteCell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
//        
//        table.addCell(QuantiteCell);
// 
//        PdfPCell dimensionCell = new PdfPCell(new Phrase(sousLigneFacture.toString()));
//        dimensionCell.setHorizontalAlignment(Element.ALIGN_CENTER);
//        dimensionCell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
//        table.addCell(dimensionCell);
//
//        PdfPCell prixUnitaireCell = new PdfPCell(new Phrase(formatCurrency(sousLigneFacture.getPrixUnitaire())));
//        prixUnitaireCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
//        prixUnitaireCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
//        table.addCell(prixUnitaireCell);
//
//     
//        
//          PdfPCell PrixTotaleCell = new PdfPCell(new Phrase(formatCurrency(sousLigneFacture.getPrixTotal())));
//         
//        PrixTotaleCell.setHorizontalAlignment(Element.ALIGN_RIGHT); 
//         PrixTotaleCell.setVerticalAlignment(Element.ALIGN_MIDDLE); 
//        table.addCell(PrixTotaleCell);
//      
//    }
//
//  
//}
