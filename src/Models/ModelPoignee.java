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

public class ModelPoignee {
    private int poigneeId;          // Identifiant unique de la poignée
    private  ModelProfileAlu profile;       // Identifiant du profil en aluminium associé
    private String type;             // Type de la poignée
    private String couleur;          // Couleur de la poignée
    private BigDecimal prixUnitaire; // Prix unitaire de la poignée
    private int quantiteEnStock;     // Quantité en stock de la poignée
    private ModelFournisseur fournisseur;       // Identifiant du fournisseur
    private ModelStatutStock statutStock;

    // Constructeur
    public ModelPoignee(int poigneeId, ModelProfileAlu profile, String type, String couleur, BigDecimal prixUnitaire, int quantiteEnStock, ModelFournisseur fournisseur,ModelStatutStock statutStock) {
        this.poigneeId = poigneeId;
        this.profile= profile;
        this.type = type;
        this.couleur = couleur;
        this.prixUnitaire = prixUnitaire;
        this.quantiteEnStock = quantiteEnStock;
        this.fournisseur = fournisseur;
        this.statutStock=statutStock;
    }

    // Getters et Setters
    public int getPoigneeId() {
        return poigneeId;
    }

    public void setPoigneeId(int poigneeId) {
        this.poigneeId = poigneeId;
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

    public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
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
