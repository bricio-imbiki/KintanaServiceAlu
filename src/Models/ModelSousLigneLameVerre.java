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

public class ModelSousLigneLameVerre {
    private int sousligneLameVerreId;
    private int sousligneDevisId;   // ID de la sous-ligne de devis
    private ModelLameVerre lameVerre; // Référence au modèle de lame de verre
    private int quantiteUtilisee;   // Quantité de lame de verre utilisée
    private BigDecimal prixTotal;   // Prix total pour cette sous-ligne de lame de verre
 public ModelSousLigneLameVerre() {}
    // Constructor
    public ModelSousLigneLameVerre(int sousligneLameVerreId, int sousligneDevisId, ModelLameVerre lameVerre,
                                    int quantiteUtilisee, BigDecimal prixTotal) {
        this.sousligneLameVerreId = sousligneLameVerreId;
        this.sousligneDevisId = sousligneDevisId;
        this.lameVerre = lameVerre;
        this.quantiteUtilisee = quantiteUtilisee;
        this.prixTotal = prixTotal;
    }

    // Getters and Setters
    public int getSousligneLameVerreId() {
        return sousligneLameVerreId;
    }

    public void setSousligneLameVerreId(int sousligneLameVerreId) {
        this.sousligneLameVerreId = sousligneLameVerreId;
    }

    public int getSousligneDevisId() {
        return sousligneDevisId;
    }

    public void setSousligneDevisId(int sousligneDevisId) {
        this.sousligneDevisId = sousligneDevisId;
    }

    public ModelLameVerre getLameVerre() {
        return lameVerre;
    }

    public void setLameVerre(ModelLameVerre lameVerre) {
        this.lameVerre = lameVerre;
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
