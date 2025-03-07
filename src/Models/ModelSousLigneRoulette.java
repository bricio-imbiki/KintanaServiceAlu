/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;



import java.math.BigDecimal;

public class ModelSousLigneRoulette {
    private int sousLigneRouletteId; // ID unique de la Roulette
    private int sousLigneDevisId; // ID de la sous-ligne de devis associée
    private ModelRoulette roulette;
    private int quantiteUtilisee; // Quantité de Roulettes
    private BigDecimal prixTotal; // Prix total de cette ligne de Roulettes
    
    // Constructeurs
    public ModelSousLigneRoulette() {}

    public ModelSousLigneRoulette( int sousLigneRouletteId, int sousLigneDevisId, ModelRoulette roulette, int quantiteUtilisee,  BigDecimal prixTotal) {
       this.sousLigneRouletteId = sousLigneRouletteId;
        this.sousLigneDevisId = sousLigneDevisId;
        this.roulette = roulette;
        this.quantiteUtilisee = quantiteUtilisee;
        this.prixTotal = prixTotal;
    }

    // Getters et setters
    public int getSousLigneRouletteId() {
        return sousLigneRouletteId;
    }

    public void setSousLigneRouletteId(int sousLigneRouletteId) {
        this.sousLigneRouletteId = sousLigneRouletteId;
    }

    public int getSousLigneDevisId() {
        return sousLigneDevisId;
    }

    public void setSousLigneDevisId(int sousLigneDevisId) {
        this.sousLigneDevisId = sousLigneDevisId;
    }

    public ModelRoulette getRoulette() {
        return roulette;
    }

    public void setRoulette( ModelRoulette roulette) {
        this.roulette = roulette;
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

