package Models;



import Models.ModelStatut.ModelStatutStock;
import java.math.BigDecimal;

public class ModelChassis {
     private int chassisId;
    private ModelProfileAlu profile;
    private ModelTypeProfileAlu type;
    private BigDecimal longueurBarre;
    private BigDecimal prixUnitaire;
    private BigDecimal quantiteEnStock;
    private ModelFournisseur fournisseur;
       private ModelStatutStock statutStock; 
    private String couleur;

    // Constructeur
    public ModelChassis( int chassisId, ModelProfileAlu profile, ModelTypeProfileAlu type, BigDecimal longueurBarre, BigDecimal prixUnitaire, BigDecimal quantiteEnStock, ModelFournisseur fournisseur,ModelStatutStock statutStock) {
       this.chassisId = chassisId;
        this.profile = profile;
        this.type= type;
        this.longueurBarre = longueurBarre;
      //  this.couleur = couleur;
        this.prixUnitaire = prixUnitaire;
        this.quantiteEnStock = quantiteEnStock;
        this.fournisseur = fournisseur;
        this.statutStock=statutStock;
    }

  public int getChassisId() {
        return chassisId;
    }

    public void setChassisId(int chassisId) {
        this.chassisId = chassisId;
    }
    public ModelProfileAlu getProfile() {
        return profile;
    }

    public void setProfile(ModelProfileAlu profile) {
        this.profile = profile;
    }

    public ModelTypeProfileAlu getType() {
        return type;
    }

    public void setType(ModelTypeProfileAlu type) {
        this.type = type;
    }

    public BigDecimal getLongueurBarre() {
        return longueurBarre;
    }

    public void setLongueurBarre(BigDecimal longueurBarre) {
        this.longueurBarre = longueurBarre;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public BigDecimal getQuantiteEnStock() {
        return quantiteEnStock;
    }

    public void setQuantiteEnStock(BigDecimal quantiteEnStock) {
        this.quantiteEnStock = quantiteEnStock;
    }

    public ModelFournisseur getFournisseur() {
        return fournisseur;
    }

    public void setFournisseur(ModelFournisseur fournisseur) {
        this.fournisseur = fournisseur;
    }
        public ModelStatutStock getStatutStock() {
        return statutStock;
    }

    public void setStatutStock(ModelStatutStock statutStock) {
        this.statutStock = statutStock;
    }

//
//    public String getCouleur() {
//        return couleur;
//    }
//
//    public void setCouleur(String couleur) {
//        this.couleur = couleur;
//    }



}
