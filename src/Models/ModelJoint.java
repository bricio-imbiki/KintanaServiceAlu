/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author brici_6ul2f65
 */
import Models.ModelStatut.ModelStatutStock;
import java.math.BigDecimal;

public class ModelJoint {
    private int jointId;
    private String typeJoint; // Type de joint
    private BigDecimal prixUnitaire;
 
    private ModelFournisseur fournisseur; // Reference to Fournisseur
private  ModelProfileAlu profile;
    // Constructor
    private BigDecimal quantiteEnStock;
    private ModelStatutStock statutStock;
  
 public ModelJoint(int jointId,ModelProfileAlu profile, String typeJoint, BigDecimal prixUnitaire, 
 BigDecimal quantiteEnStock, ModelFournisseur fournisseur,ModelStatutStock statutStock) {
        this.jointId = jointId;
          this.profile=profile;
        this.typeJoint = typeJoint;
        this.prixUnitaire = prixUnitaire;
        this.quantiteEnStock = quantiteEnStock;
        this.fournisseur = fournisseur;
       this.statutStock=statutStock;
    }

    // Getters and Setters
    public int getJointId() {
        return jointId;
    }

    public void setJointId(int jointId) {
        this.jointId = jointId;
    }
 public ModelProfileAlu  getProfile() {
        return profile;
    }

    public void setProfile(ModelProfileAlu  profile) {
        this.profile = profile;
    }
    public String getTypeJoint() {
        return typeJoint;
    }

    public void setTypeJoint(String typeJoint) {
        this.typeJoint = typeJoint;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public BigDecimal getQuantiteEnStock() {
        return quantiteEnStock ;
    }
    @Override
    public String toString(){
    return quantiteEnStock +" m";
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
}
