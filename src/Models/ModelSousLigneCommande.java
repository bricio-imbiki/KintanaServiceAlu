package Models;

import java.math.BigDecimal;

public class ModelSousLigneCommande {
    private int sousLigneCommandeId;
    private int ligneCommandeId;
    private Integer nbVentaux; // nombre de ventaux (optional)
    private ModelStructure structure;
    private int largeur;
    private int hauteur;
    private int quantite;
    private BigDecimal prixUnitaire;
    private BigDecimal prixTotal;
    private BigDecimal mainOeuvre;

    // Constructeur par défaut
    public ModelSousLigneCommande() {}

    // Constructeur avec tous les champs
    public ModelSousLigneCommande(int sousLigneCommandeId, int ligneCommandeId, ModelStructure structure, Integer nbVentaux,
                                  int largeur, int hauteur, int quantite, BigDecimal prixUnitaire,
                                  BigDecimal prixTotal, BigDecimal mainOeuvre) {
        this.sousLigneCommandeId = sousLigneCommandeId;
        this.ligneCommandeId = ligneCommandeId;
        this.structure = structure;
        this.nbVentaux = nbVentaux;
        this.largeur = largeur;
        this.hauteur = hauteur;
        this.quantite = quantite;
        this.prixUnitaire = prixUnitaire;
        this.prixTotal = prixTotal;
        this.mainOeuvre = mainOeuvre;
    }

    // Getters et Setters pour chaque champ
    public int getSousLigneCommandeId() {
        return sousLigneCommandeId;
    }

    public void setSousLigneCommandeId(int sousLigneCommandeId) {
        this.sousLigneCommandeId = sousLigneCommandeId;
    }

    public int getLigneCommandeId() {
        return ligneCommandeId;
    }

    public void setLigneCommandeId(int ligneCommandeId) {
        this.ligneCommandeId = ligneCommandeId;
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

    public int getLargeur() {
        return largeur;
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public int getHauteur() {
        return hauteur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    public int getQuantite() {
        return quantite;
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
