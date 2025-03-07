package Models;

import ConnexionDB.DatabaseUtils;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import javax.swing.*;
import java.awt.print.PageFormat;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PrinterReport {
    private static InputStream logoStream;
    private static InputStream starStream2;
    private static InputStream starStream1;
 

    
  public static void generateDevisReport(int devisId, String outputType, OutputStream outputStream,PageFormat pageformat) {
    try {
        // Charger les données du devis
        ModelDevis devis = DatabaseUtils.getDevisById(devisId);
        if (devis == null) {
            JOptionPane.showMessageDialog(null,
                    "Le devis avec l'ID " + devisId + " n'existe pas.",
                    "Devis Introuvable",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Préparer les paramètres et la source de données
        Map<String, Object> parameters = getDevisParameters(devis);
        JRBeanCollectionDataSource dataSource = getLigneDevisDataSource(devis);

        // Générer le rapport (PDF ou Excel)
        ReportGenerator.generateReport("/print/DevisReport.jrxml", parameters, dataSource, outputStream, outputType,pageformat);
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null,
                "Erreur lors de la génération du rapport : " + e.getMessage(),
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
    }
}
  private static Map<String, Object> getDevisParameters(ModelDevis devis) throws FileNotFoundException {
        Map<String, Object> parameters = new HashMap<>();
         // Charger le logo
         
        logoStream = (InputStream)(new FileInputStream("src/Icon/menualu.png"));
        starStream1 =(InputStream)(new FileInputStream("src/Icon/kintana.png"));
        starStream2 =(InputStream)(new FileInputStream("src/Icon/kintana.png"));
        parameters.put("logo", logoStream);
        
        parameters.put("star1", starStream1);
        parameters.put("star2", starStream2);
       
        parameters.put("devisId", devis.getNumeroDevis());
        parameters.put("date", devis.getFormattedDateDevis());
        parameters.put("nom", devis.getClient().getNom());
        parameters.put("adresse", devis.getClient().getAdresse());
        parameters.put("telephone", devis.getClient().getTelephone());
        parameters.put("email", devis.getClient().getEmail());
        
        
         parameters.put("totalHT", devis.getTotalHT());
         parameters.put("remise", devis.getTotalRemise());
         parameters.put("remisePercentage", devis.getRemisePercentage());
         parameters.put("acomptePercentage", devis.getAcomptePercentage());
         parameters.put("tvaPercentage", devis.getTvaPercentage());
         parameters.put("acompte", devis.getTotalAcompte());
         parameters.put("totalTVA", devis.getTotalTva());
         parameters.put("totalTTC", devis.getTotalTTC());
          
       
        
        return parameters;
    }
  private static JRBeanCollectionDataSource getLigneDevisDataSource(ModelDevis devis) {
    // Préparer une liste qui inclut à la fois les lignes de devis et leurs sous-lignes
    List<Map<String, Object>> lignesAvecSousLignes = new ArrayList<>();

    for (ModelLigneDevis ligneDevis : devis.getLignesDevis()) {
        Map<String, Object> ligneMap = new HashMap<>();
        ligneMap.put("designation", ligneDevis.getDesignation());
        ligneMap.put("totalQuantite", ligneDevis.getTotalQuantite());
        ligneMap.put("totalPrix", ligneDevis.getTotalPrix());

        // Préparer les sous-lignes de cette ligne
        JRBeanCollectionDataSource sousLigneDataSource = new JRBeanCollectionDataSource(ligneDevis.getSousLignesDevis());
        ligneMap.put("sousLigneDevis", sousLigneDataSource);

        lignesAvecSousLignes.add(ligneMap);
    }

    return new JRBeanCollectionDataSource(lignesAvecSousLignes);
}

  
public static void generateFactureReport(int factureId, String outputType, OutputStream outputStream, PageFormat pageformat) {
    try {
        // Charger les données du devis
        ModelFacture facture = DatabaseUtils.getFactureById(factureId);
        if (facture == null) {
            JOptionPane.showMessageDialog(null,
                    "Le facture avec l'ID " + factureId + " n'existe pas.",
                    "Facture Introuvable",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Préparer les paramètres et la source de données
        Map<String, Object> parameters = getFactureParameters(facture);
        JRBeanCollectionDataSource dataSource = getLigneFactureDataSource(facture);

        // Générer le rapport (PDF ou Excel)
        ReportGenerator.generateReport("/print/FactureReport.jrxml", parameters, dataSource, outputStream, outputType, pageformat);
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(null,
                "Erreur lors de la génération du rapport : " + e.getMessage(),
                "Erreur",
                JOptionPane.ERROR_MESSAGE);
    }
}
private static Map<String, Object> getFactureParameters(ModelFacture facture) throws FileNotFoundException {
        Map<String, Object> parameters = new HashMap<>();
         // Charger le logo
         
        logoStream = (InputStream)(new FileInputStream("src/Icon/menualu.png"));
        starStream1 =(InputStream)(new FileInputStream("src/Icon/kintana.png"));
        starStream2 =(InputStream)(new FileInputStream("src/Icon/kintana.png"));
        parameters.put("logo", logoStream);
        
        parameters.put("star1", starStream1);
        parameters.put("star2", starStream2);
       
        parameters.put("factureId", facture.getNumeroFacture());
        parameters.put("date", facture.getFormattedDateFacture());
        parameters.put("nom", facture.getClient().getNom());
        parameters.put("adresse", facture.getClient().getAdresse());
        parameters.put("telephone", facture.getClient().getTelephone());
        parameters.put("email", facture.getClient().getEmail());
        
        
         parameters.put("totalHT", facture.getTotalFactureHT());
         parameters.put("remise", facture.getTotalFactureRemise());
         parameters.put("remisePercentage", facture.getRemiseFacturePercentage());
         parameters.put("tvaPercentage", facture.getTvaFacturePercentage());
     
         parameters.put("totalTVA", facture.getTotalFactureTva());
         parameters.put("totalTTC", facture.getTotalFactureTTC());
          
       
        
        return parameters;
    }   
private static JRBeanCollectionDataSource getLigneFactureDataSource(ModelFacture facture) {
    // Préparer une liste qui inclut à la fois les lignes de devis et leurs sous-lignes
    List<Map<String, Object>> lignesAvecSousLignes = new ArrayList<>();

    for (ModelLigneFacture ligneFacture : facture.getLignesFacture()) {
        Map<String, Object> ligneMap = new HashMap<>();
        ligneMap.put("designation", ligneFacture.getDesignation());
        ligneMap.put("totalQuantite", ligneFacture.getTotalQuantite());
        ligneMap.put("totalPrix", ligneFacture.getTotalPrix());

        // Préparer les sous-lignes de cette ligne
        JRBeanCollectionDataSource sousLigneDataSource = new JRBeanCollectionDataSource(ligneFacture.getSousLigneFacture());
        ligneMap.put("sousLigneFacture", sousLigneDataSource);

        lignesAvecSousLignes.add(ligneMap);
    }

    return new JRBeanCollectionDataSource(lignesAvecSousLignes);
}
 

}
