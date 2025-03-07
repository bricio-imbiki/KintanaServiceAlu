package Models;

import java.math.BigDecimal;

public class ModelSousLigneFacture {
    private int sousLigneFactureId;
    private int ligneFactureId;
    private Integer nbVentaux; // nombre de ventaux (optionnel)
    private ModelStructure structure;
    private BigDecimal largeur;
    private BigDecimal hauteur;
    private int quantite;
    private BigDecimal prixUnitaire;
    private BigDecimal prixTotal;
    private BigDecimal mainOeuvre;

    // Constructeur par défaut
    public ModelSousLigneFacture() {}

    // Constructeur avec tous les champs
    public ModelSousLigneFacture(int sousLigneFactureId, int ligneFactureId,
                                 BigDecimal largeur, BigDecimal hauteur, int quantite, BigDecimal prixUnitaire,
                                 BigDecimal prixTotal, BigDecimal mainOeuvre) {
        this.sousLigneFactureId = sousLigneFactureId;
        this.ligneFactureId = ligneFactureId;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.prixTotal = prixTotal;
        this.mainOeuvre = mainOeuvre;
    }

    // Getters et Setters pour chaque champ
    public int getSousLigneFactureId() {
        return sousLigneFactureId;
    }

    public void setSousLigneFactureId(int sousLigneFactureId) {
        this.sousLigneFactureId = sousLigneFactureId;
    }

    public int getLigneFactureId() {
        return ligneFactureId;
    }

    public void setLigneFactureId(int ligneFactureId) {
        this.ligneFactureId = ligneFactureId;
    }

    public ModelStructure getStructure() {
        return structure;
    }

    public void setStructure(ModelStructure structure) {
        this.structure = structure;
    }

    public Integer getNbVentaux() {
        return nbVentaux;
    }

    public void setNbVentaux(Integer nbVentaux) {
        this.nbVentaux = nbVentaux;
    }

    public BigDecimal getLargeur() {
        return largeur;
    }

    public void setLargeur(BigDecimal largeur) {
        this.largeur = largeur;
    }

    public BigDecimal getHauteur() {
        return hauteur;
    }

    public void setHauteur(BigDecimal hauteur) {
        this.hauteur = hauteur;
    }

    public int getQuantite() {
        return quantite;
    }
 @Override
    public String toString() {
        return largeur + " mm (Lt) x " + hauteur + " mm (Ht)";
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public BigDecimal getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(BigDecimal prixTotal) {
        this.prixTotal = prixTotal;
    }

    public BigDecimal getMainOeuvre() {
        return mainOeuvre;
    }

    public void setMainOeuvre(BigDecimal mainOeuvre) {
        this.mainOeuvre = mainOeuvre;
    }

//    // Méthode pour calculer le prix total si nécessaire
//    public void calculerPrixTotal() {
//        if (prixUnitaire != null && quantite > 0) {
//            this.prixTotal = prixUnitaire.multiply(BigDecimal.valueOf(quantite)).add(mainOeuvre);
//        }
//    }
}
