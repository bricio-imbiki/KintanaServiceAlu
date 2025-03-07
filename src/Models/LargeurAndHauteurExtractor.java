/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author brici_6ul2f65
 */
import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LargeurAndHauteurExtractor {

    private static BigDecimal largeur;
    private static BigDecimal hauteur;

  

    public static LargeurAndHauteur extractDimensionsFromDesignation(String designation) {
        // Initialiser largeur et hauteur à BigDecimal.ZERO
        BigDecimal largeur = BigDecimal.ZERO;
        BigDecimal hauteur = BigDecimal.ZERO;

        if (designation != null && !designation.trim().isEmpty()) {
            try {
                // Expression régulière mise à jour pour capturer les valeurs décimales
                Pattern pattern = Pattern.compile("(\\d+(\\.\\d+)?) mm \\(Lt\\) x (\\d+(\\.\\d+)?) mm \\(Ht\\)");
                Matcher matcher = pattern.matcher(designation);
                if (matcher.find()) {
                    // Extraire les valeurs et les convertir en BigDecimal
                    largeur = new BigDecimal(matcher.group(1));
                    hauteur = new BigDecimal(matcher.group(3));
                }
            } catch (Exception e) {
                // Gérer les exceptions pour les formats inattendus
                System.err.println("Erreur lors de l'extraction des dimensions : " + e.getMessage());
            }
        }

        return new LargeurAndHauteur(largeur, hauteur);
    }

    public static class LargeurAndHauteur {

        public final BigDecimal largeur;
        public final BigDecimal hauteur;
      

        public LargeurAndHauteur(BigDecimal largeur, BigDecimal hauteur) {
            this.largeur = largeur;
            this.hauteur = hauteur;
        }

        public BigDecimal getLargeur() {
            return largeur;
        }

        public BigDecimal getHauteur() {
            return hauteur;
        }

       
    }

    public static class extractDimensionsFromDesignation {

        public extractDimensionsFromDesignation() {
        }
    }
}
