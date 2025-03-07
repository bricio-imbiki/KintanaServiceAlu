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

public class ModelRoulette {
    private int rouletteId;                // ID unique de la roulette
    private String typeRoulette;           // Type de la roulette
    private BigDecimal tailleRoulette;         // Taille de la roulette (ex: "100mm")
    private BigDecimal prixUnitaire;       // Prix unitaire de la roulette
    private int quantiteEnStock;           // Quantité en stock de la roulette
    private ModelFournisseur fournisseur;             // ID du fournisseur
   private  ModelProfileAlu profile;
    private ModelStatutStock statutStock;
    // Constructor
    public ModelRoulette(int rouletteId, ModelProfileAlu profile,String typeRoulette, BigDecimal tailleRoulette, 
                         BigDecimal prixUnitaire, int quantiteEnStock, ModelFournisseur fournisseur,ModelStatutStock statutStock) {
        this.rouletteId = rouletteId;
         this.profile=profile;
        this.typeRoulette = typeRoulette;
        this.tailleRoulette = tailleRoulette;
        this.prixUnitaire = prixUnitaire;
        this.quantiteEnStock = quantiteEnStock;
        this.fournisseur = fournisseur;
        this.statutStock=statutStock;
    }

    // Getters and Setters
    public int getRouletteId() {
        return rouletteId;
    }

    public void setRouletteId(int rouletteId) {
        this.rouletteId = rouletteId;
    }
  public ModelProfileAlu  getProfile() {
        return profile;
    }

    public void setProfile(ModelProfileAlu  profile) {
        this.profile = profile;
    }
    public String getTypeRoulette() {
        return typeRoulette;
    }

    public void setTypeRoulette(String typeRoulette) {
        this.typeRoulette = typeRoulette;
    }

    public BigDecimal getTailleRoulette() {
        return tailleRoulette;
    }

    public void setTailleRoulette(BigDecimal tailleRoulette) {
        this.tailleRoulette = tailleRoulette;
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
