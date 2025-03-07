package Models;

import Models.ModelStatut.ModelStatutStock;
import java.math.BigDecimal;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

public class ModelLameVerre {

    private int lameVerreId;
    private ModelVitrage vitrage; // Référence au modèle Vitrage
    private String typeVerre; // Type de verre
    private int epaisseur; // Épaisseur en mm
    private BigDecimal largeur; // Largeur en mm
    private BigDecimal prixUnitaire; // Prix unitaire
    private int quantiteEnStock; // Quantité en stock
    private ModelFournisseur fournisseur; // Référence au fournisseur
    private ModelStatutStock statutStock; // Référence au statut du stock

    // Constructeur
    public ModelLameVerre(int lameVerreId, ModelVitrage vitrage, String typeVerre, 
                          int epaisseur, BigDecimal largeur, BigDecimal prixUnitaire, 
                          int quantiteEnStock, ModelFournisseur fournisseur, ModelStatutStock statutStock) {
        this.lameVerreId = lameVerreId;
        this.vitrage = vitrage;
        this.typeVerre = typeVerre;
        this.epaisseur = epaisseur;
        this.largeur = largeur;
        this.prixUnitaire = prixUnitaire;
        this.quantiteEnStock = quantiteEnStock;
        this.fournisseur = fournisseur;
        this.statutStock = statutStock;
    }

    // Getters et Setters
    public int getLameVerreId() {
        return lameVerreId;
    }

    public void setLameVerreId(int lameVerreId) {
        this.lameVerreId = lameVerreId;
    }

    public ModelVitrage getVitrage() {
        return vitrage;
    }

    public void setVitrage(ModelVitrage vitrage) {
        this.vitrage = vitrage;
    }

    public String getTypeVerre() {
        return typeVerre;
    }

    public void setTypeVerre(String typeVerre) {
        this.typeVerre = typeVerre;
    }

    public int getEpaisseur() {
        return epaisseur;
    }

    public void setEpaisseur(int epaisseur) {
        this.epaisseur = epaisseur;
    }

    public BigDecimal getLargeur() {
        return largeur;
    }

    public void setLargeur(BigDecimal largeur) {
        this.largeur = largeur;
    }
    @Override
    public String toString() {
        return largeur +"mm";
    }
    
       public String toStringTypeEpesseur() {
        return typeVerre.toUpperCase() + " " + epaisseur + "mm";
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
