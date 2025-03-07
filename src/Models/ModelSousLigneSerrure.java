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
public class ModelSousLigneSerrure {
    private int sousLigneSerrureId;
    private int sousLigneDevisId; // ID de la sous-ligne de devis associée
    private ModelSerrure serrure;
    private int quantiteUtilisee; // Quantité de paumelles
    private BigDecimal prixTotal; // Prix total de cette ligne de paumelles
    
    // Constructeurs
    public ModelSousLigneSerrure() {}

    public ModelSousLigneSerrure( int sousLigneSerrureId, int sousLigneDevisId, ModelSerrure serrure, int quantiteUtilisee,  BigDecimal prixTotal) {
       this.sousLigneSerrureId= sousLigneSerrureId;
        this.sousLigneDevisId = sousLigneDevisId;
        this.serrure = serrure;
        this.quantiteUtilisee = quantiteUtilisee;
        this.prixTotal = prixTotal;
    }

    // Getters et setters
    public int getSousLigneSerrureId() {
        return sousLigneSerrureId;
    }

    public void setSousLigneSerrureId(int sousLigneSerrureId) {
        this.sousLigneSerrureId = sousLigneSerrureId;
    }

    public int getSousLigneDevisId() {
        return sousLigneDevisId;
    }

    public void setSousLigneDevisId(int sousLigneDevisId) {
        this.sousLigneDevisId = sousLigneDevisId;
    }

    public ModelSerrure getSerrure() {
        return serrure;
    }

    public void setSerrure( ModelSerrure serrure) {
        this.serrure = serrure;
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
