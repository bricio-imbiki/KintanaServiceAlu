/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author brici_6ul2f65
 */


import java.math.BigDecimal;

public class ModelSousLigneRivette {
    private int sousligneRivetteId;
    private int sousligneDevisId;  // ID de la sous-ligne de devis
    private ModelRivette rivette;   // Référence au modèle de rivette
    private int quantiteUtilisee;  // Quantité de rivettes utilisée
    private BigDecimal prixTotal;  // Prix total pour cette sous-ligne de rivette
 public ModelSousLigneRivette() {}
    // Constructor
    public ModelSousLigneRivette(int sousligneRivetteId, int sousligneDevisId, ModelRivette rivette,
                                 int quantiteUtilisee, BigDecimal prixTotal) {
        this.sousligneRivetteId = sousligneRivetteId;
        this.sousligneDevisId = sousligneDevisId;
        this.rivette = rivette;
        this.quantiteUtilisee = quantiteUtilisee;
        this.prixTotal = prixTotal;
    }

    // Getters and Setters
    public int getSousligneRivetteId() {
        return sousligneRivetteId;
    }

    public void setSousligneRivetteId(int sousligneRivetteId) {
        this.sousligneRivetteId = sousligneRivetteId;
    }

    public int getSousligneDevisId() {
        return sousligneDevisId;
    }

    public void setSousligneDevisId(int sousligneDevisId) {
        this.sousligneDevisId = sousligneDevisId;
    }

    public ModelRivette getRivette() {
        return rivette;
    }

    public void setRivette(ModelRivette rivette) {
        this.rivette = rivette;
    }

    public int getQuantiteUtilisee() {
        return quantiteUtilisee;
    }

    public void setQuantiteUtilisee(int quantiteUtilisee) {
        this.quantiteUtilisee = quantiteUtilisee;
    }

    public BigDecimal getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(BigDecimal prixTotal) {
        this.prixTotal = prixTotal;
    }
}
