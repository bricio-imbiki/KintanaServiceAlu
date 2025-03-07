/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.awt.print.PageFormat;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.view.JasperViewer;



/**
 *
 * @author brici_6ul2f65
 */
public class ReportGenerator {


 
    public static void generateReport(String jrxmlPath,
                                      Map<String, Object> parameters,
                                      JRDataSource dataSource,
                                      OutputStream outputStream,
                                      String outputType,PageFormat pageformat) {
        try {
            // Charger et compiler le fichier JRXML
            InputStream reportStream = ReportGenerator.class.getResourceAsStream(jrxmlPath);
            if (reportStream == null) {
                throw new FileNotFoundException("Le fichier du rapport n'a pas été trouvé : " + jrxmlPath);
            }
            JasperReport jasperReport = JasperCompileManager.compileReport(reportStream);

            // Remplir le rapport avec les données
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);

            // Afficher ou exporter selon le type de sortie
            switch (outputType.toLowerCase()) {
                case "pdf":
                    showAndPrintReport(jasperPrint);
                    break;
                case "excel":
                    exportToExcel(jasperPrint, outputStream);
                    break;
                case "view":
                    JasperViewer.viewReport(jasperPrint, false);
                    break;
                default:
                    throw new IllegalArgumentException("Type de sortie non pris en charge : " + outputType);
            }

            JOptionPane.showMessageDialog(null,
                    "Le rapport a été généré avec succès.",
                    "Succès",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Erreur lors de la génération du rapport : " + e.getMessage(),
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        } finally {
            // Fermer le flux de sortie
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * Affiche le rapport dans JasperViewer et permet l'impression.
     *
     * @param jasperPrint Rapport JasperPrint à afficher.
     * @throws JRException En cas d'erreur JasperReports.
     */
    private static void showAndPrintReport(JasperPrint jasperPrint) throws JRException {
        // Afficher dans JasperViewer
        JasperViewer.viewReport(jasperPrint, false);

        // Imprimer avec orientation détectée
        boolean isLandscape = jasperPrint.getPageWidth() > jasperPrint.getPageHeight();
        if (isLandscape) {
            System.out.println("Impression en mode paysage.");
        } else {
            System.out.println("Impression en mode portrait.");
        }

  
    }


    /**
     * Exporte le rapport en PDF.
     */
    private static void exportToPDF(JasperPrint jasperPrint, OutputStream outputStream) throws JRException {
        JRPdfExporter exporter = new JRPdfExporter();
exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
        exporter.exportReport();
    }

    /**
     * Exporte le rapport en Excel.
     */
    private static void exportToExcel(JasperPrint jasperPrint, OutputStream outputStream) throws JRException {
        JRXlsxExporter exporter = new JRXlsxExporter();
exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
//   SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
//        configuration.setDetectCellType(true);
//        configuration.setCollapseRowSpan(false);
//        configuration.setOnePagePerSheet(false);
//        configuration.setRemoveEmptySpaceBetweenRows(true);
//        exporter.setConfiguration(configuration);
        exporter.exportReport();
    }
}
