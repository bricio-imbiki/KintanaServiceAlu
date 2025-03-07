/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.math.BigDecimal;

/**
 *
 * @author brici_6ul2f65
 */
public class ModelSousLigneVis {
    private int sousLigneVisId;
    private int sousLigneDevisId; // ID de la sous-ligne de devis associée
    private ModelVis vis;
    private int quantiteUtilisee; // Quantité de paumelles
    private BigDecimal prixTotal; // Prix total de cette ligne de paumelles
    
    // Constructeurs
    public ModelSousLigneVis (){}

    public ModelSousLigneVis( int sousLigneVisId, int sousLigneDevisId, ModelVis vis, int quantiteUtilisee,  BigDecimal prixTotal) {
       this.sousLigneVisId= sousLigneVisId;
        this.sousLigneDevisId = sousLigneDevisId;
        this.vis = vis;
        this.quantiteUtilisee = quantiteUtilisee;
        this.prixTotal = prixTotal;
    }

    // Getters et setters
    public int getSousLigneVisId() {
        return sousLigneVisId;
    }

    public void setSousLigneVisId(int sousLigneVisId) {
        this.sousLigneVisId = sousLigneVisId;
    }

    public int getSousLigneDevisId() {
        return sousLigneDevisId;
    }

    public void setSousLigneDevisId(int sousLigneDevisId) {
        this.sousLigneDevisId = sousLigneDevisId;
    }

    public ModelVis getVis() {
        return vis;
    }

    public void setVis( ModelVis vis) {
        this.vis = vis;
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
