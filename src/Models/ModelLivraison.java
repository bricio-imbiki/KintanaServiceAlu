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
public class ModelLivraison {
    private int livraisonId;
    private BigDecimal fraisLivraison;

    // Constructor
    public ModelLivraison(int livraisonId, BigDecimal fraisLivraison) {
        this.livraisonId = livraisonId;
        this.fraisLivraison = fraisLivraison;
    }

    // Getters and setters
    public int getLivraisonId() {
        return livraisonId;
    }

    public void setLivraisonId(int livraisonId) {
        this.livraisonId = livraisonId;
    }

    public BigDecimal getFraisLivraison() {
        return fraisLivraison;
    }

    public void setFraisLivraison(BigDecimal fraisLivraison) {
        this.fraisLivraison = fraisLivraison;
    }
}