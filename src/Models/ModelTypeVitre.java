/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Models.ModelStatut.ModelStatutStock;
import java.math.BigDecimal;



public class ModelTypeVitre {
    private int typeVitreId;
    private ModelVitrage  typeVitrage; // Type de vitre
    private String type;
    private  BigDecimal epaisseur;
      private BigDecimal prixUnitaire;
      private String dimensions;
    private int quantiteEnStock;
    private ModelFournisseur fournisseur; // Reference to Fournisseur
    private ModelStatutStock statutStock;



    // Constructor
    public ModelTypeVitre(int typeVitreId, ModelVitrage  typeVitrage, /*String dimensions,*/ String type, BigDecimal epaisseur, BigDecimal prixUnitaire, 
                          int quantiteEnStock, ModelFournisseur fournisseur,ModelStatutStock statutStock) {
        this.typeVitreId = typeVitreId;
        this.typeVitrage = typeVitrage;
        this.type = type;
        this.epaisseur = epaisseur;
        //this.dimensions = dimensions;
        this.prixUnitaire = prixUnitaire;
        this.quantiteEnStock = quantiteEnStock;
        this.fournisseur = fournisseur;
        this.statutStock=statutStock;
    }

    // Getters and Setters
    public int getTypeVitreId() {
        return typeVitreId;
    }

    public void setTypeVitreId(int typeVitreId) {
        this.typeVitreId = typeVitreId;
    }
    
      public ModelVitrage  getVitrage() {
        return typeVitrage;
    }

    public void setType(ModelVitrage typeVitrage) {
        this.typeVitrage = typeVitrage;
    }
     public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

  public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
 public BigDecimal getEpaisseur() {
        return epaisseur;
    }
  public void setEpaisseur(BigDecimal epaisseur) {
       this.epaisseur= epaisseur;
    }
    @Override
    public String toString() {
        return type.toUpperCase() + " " + epaisseur + "mm";
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
