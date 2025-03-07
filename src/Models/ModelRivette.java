/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Models.ModelStatut.ModelStatutStock;
import java.math.BigDecimal;

/**
 *
 * @author brici_6ul2f65
 */
public class ModelRivette {
    private int rivetteId;
    private ModelProfileAlu profile;
    private String type;
    private BigDecimal prixUnitaire;
    private int quantiteEnStock;
    private ModelFournisseur fournisseur;
    private ModelStatutStock statutStock;
public ModelRivette(){
    
}
    // Constructeur
    public ModelRivette(int rivetteId, ModelProfileAlu profile, String type, BigDecimal prixUnitaire, int quantiteEnStock, ModelFournisseur fournisseur,ModelStatutStock statutStock) {
        this.rivetteId = rivetteId;
        this.profile = profile;
        this.type = type;
        this.prixUnitaire = prixUnitaire;
        this.quantiteEnStock = quantiteEnStock;
        this.fournisseur = fournisseur;
        this.statutStock = statutStock;
    }

    // Getters et Setters
    public int getRivetteId() {
        return rivetteId;
    }

    public void setRivetteId(int rivetteId) {
        this.rivetteId = rivetteId;
    }

    public ModelProfileAlu getProfile() {
        return profile;
    }

    public void setProfile(ModelProfileAlu profile) {
        this.profile = profile;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
