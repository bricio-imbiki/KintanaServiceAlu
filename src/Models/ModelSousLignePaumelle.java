/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;



import java.math.BigDecimal;

public class ModelSousLignePaumelle {
    private int sousLignePaumelleId; // ID unique de la paumelle
    private int sousLigneDevisId; // ID de la sous-ligne de devis associée
    private ModelPaumelle paumelle;
    private int quantiteUtilisee; // Quantité de paumelles
    private BigDecimal prixTotal; // Prix total de cette ligne de paumelles
    
    // Constructeurs
    public ModelSousLignePaumelle() {}

    public ModelSousLignePaumelle( int sousLignePaumelleId, int sousLigneDevisId, ModelPaumelle paumelle, int quantiteUtilisee,  BigDecimal prixTotal) {
       this.sousLignePaumelleId = sousLignePaumelleId;
        this.sousLigneDevisId = sousLigneDevisId;
        this.paumelle = paumelle;
        this.quantiteUtilisee = quantiteUtilisee;
        this.prixTotal = prixTotal;
    }

    // Getters et setters
    public int getSousLignePaumelleId() {
        return sousLignePaumelleId;
    }

    public void setSousLignePaumelleId(int sousLignePaumelleId) {
        this.sousLignePaumelleId = sousLignePaumelleId;
    }

    public int getSousLigneDevisId() {
        return sousLigneDevisId;
    }

    public void setSousLigneDevisId(int sousLigneDevisId) {
        this.sousLigneDevisId = sousLigneDevisId;
    }

    public ModelPaumelle getPaumelle() {
        return paumelle;
    }

    public void setPaumelle( ModelPaumelle paumelle) {
        this.paumelle = paumelle;
    }

    public int getQuantiteUtilisee(){
        return quantiteUtilisee;
    }

    public void setQuantiteUtilisee(int quantiteUtilisee) {
        this.quantiteUtilisee= quantiteUtilisee;
    }

    public BigDecimal getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(BigDecimal prixTotal) {
        this.prixTotal = prixTotal;
    }

}

