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

public class ModelSerrure {
    private int SerrureId;
    private String typeSerrure; // Type de Serrure
     private String couleur; 
    private BigDecimal prixUnitaire;
    private int quantiteEnStock;
    private ModelFournisseur fournisseur; // Reference to Fournisseur
  private  ModelProfileAlu profile;
    private ModelStatutStock statutStock;
    // Constructor
    public ModelSerrure(int SerrureId, ModelProfileAlu profile,String typeSerrure,String couleur, BigDecimal prixUnitaire, 
                         int quantiteEnStock, ModelFournisseur fournisseur,ModelStatutStock statutStock) {
        this.SerrureId = SerrureId;
        this.profile=profile;
        this.typeSerrure = typeSerrure;
        this.couleur = couleur;
        this.prixUnitaire = prixUnitaire;
        this.quantiteEnStock = quantiteEnStock;
        this.fournisseur = fournisseur;
        this.statutStock=statutStock;
    }

    // Getters and Setters
    public int getSerrureId() {
        return SerrureId;
    }

    public void setSerrureId(int SerrureId) {
        this.SerrureId = SerrureId;
    }
 public ModelProfileAlu  getProfile() {
        return profile;
    }

    public void setProfile(ModelProfileAlu  profile) {
        this.profile = profile;
    }
    public String getTypeSerrure() {
        return typeSerrure;
    }

    public void setTypeSerrure(String typeSerrure) {
        this.typeSerrure = typeSerrure;
    }
  public String getCouleurSerrure() {
        return couleur;
    }

    public void setCouleurSerrure(String couleur) {
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
