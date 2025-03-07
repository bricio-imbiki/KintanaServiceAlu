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

public class ModelPaumelle {
    private int paumelleId;
    private String typePaumelle; // Type de paumelle
    private String couleur; 
    private  ModelProfileAlu profile;
    private BigDecimal prixUnitaire;
    private int quantiteEnStock;
    private ModelFournisseur fournisseur; // Reference to Fournisseur
    private ModelStatutStock statutStock;

    // Constructor
    public ModelPaumelle(int paumelleId,ModelProfileAlu profile, String typePaumelle,String couleur, BigDecimal prixUnitaire, 
                         int quantiteEnStock, ModelFournisseur fournisseur,ModelStatutStock statutStock) {
        this.paumelleId = paumelleId;
        this.profile=profile;
        this.typePaumelle = typePaumelle;
         this.couleur = couleur;
        this.prixUnitaire = prixUnitaire;
        this.quantiteEnStock = quantiteEnStock;
        this.fournisseur = fournisseur;
        this.statutStock=statutStock;
    }

    // Getters and Setters
    
    public int getPaumelleId() {
        return paumelleId;
    }

    public void setPaumelleId(int paumelleId) {
        this.paumelleId = paumelleId;
    }
  public ModelProfileAlu  getProfile() {
        return profile;
    }

    public void setProfile(ModelProfileAlu  profile) {
        this.profile = profile;
    }
    public String getTypePaumelle() {
        return typePaumelle;
    }

    public void setTypePaumelle(String typePaumelle) {
        this.typePaumelle = typePaumelle;
    }
  public String getCouleurPaumelle() {
        return couleur;
    }

    public void setCouleurPaumelle(String couleur) {
        this.couleur = couleur;
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
