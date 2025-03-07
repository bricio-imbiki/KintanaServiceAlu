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

public class ModelVis {
    private int visId;
    private String typeVis; // Type de vis
     private BigDecimal longueurVis; 
    private BigDecimal prixUnitaire;
       private  ModelProfileAlu profile;
    private int quantiteEnStock;
    private ModelFournisseur fournisseur; // Reference to Fournisseur
    private ModelStatutStock statutStock;

    // Constructor
    public ModelVis(int visId, ModelProfileAlu profile,String typeVis,BigDecimal longueurVis,BigDecimal prixUnitaire, 
                    int quantiteEnStock, ModelFournisseur fournisseur, ModelStatutStock statutStock) {
        this.visId = visId;
         this.profile=profile;
        this.typeVis = typeVis;
         this.longueurVis = longueurVis;
        this.prixUnitaire = prixUnitaire;
        this.quantiteEnStock = quantiteEnStock;
        this.fournisseur = fournisseur;
        this.statutStock=statutStock;
    }

    // Getters and Setters
    public int getVisId() {
        return visId;
    }

    public void setVisId(int visId) {
        this.visId = visId;
    }
  public ModelProfileAlu  getProfile() {
        return profile;
    }

    public void setProfile(ModelProfileAlu  profile) {
        this.profile = profile;
    }
    public String getTypeVis() {
        return typeVis;
    }

    public void setTypeVis(String typeVis) {
        this.typeVis = typeVis;
    }
  public BigDecimal getLongueurVis() {
        return longueurVis;
    }

    public void setLongueurVis(BigDecimal longueurVis) {
        this.longueurVis= longueurVis;
    }
    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public int getQuantiteEnStock() {
        return quantiteEnStock;
    }

    public void setQuantiteEnStock(int quantiteEnStock) {
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

