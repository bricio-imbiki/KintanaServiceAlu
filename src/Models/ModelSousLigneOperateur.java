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

public class ModelSousLigneOperateur {
    private int sousligneOperateurId;
    private int sousligneDevisId;  // ID de la sous-ligne de devis
    private ModelOperateur operateur;  // Référence au modèle d'opérateur
    private int quantiteUtilisee;  // Quantité d'opérateur utilisée
    private BigDecimal prixTotal;  // Prix total pour cette sous-ligne d'opérateur
   public ModelSousLigneOperateur() {}
    // Constructor
    public ModelSousLigneOperateur(int sousligneOperateurId, int sousligneDevisId, ModelOperateur operateur,
                                   int quantiteUtilisee, BigDecimal prixTotal) {
        this.sousligneOperateurId = sousligneOperateurId;
        this.sousligneDevisId = sousligneDevisId;
        this.operateur = operateur;
        this.quantiteUtilisee = quantiteUtilisee;
        this.prixTotal = prixTotal;
    }

    // Getters and Setters
    public int getSousligneOperateurId() {
        return sousligneOperateurId;
    }

    public void setSousligneOperateurId(int sousligneOperateurId) {
        this.sousligneOperateurId = sousligneOperateurId;
    }

    public int getSousligneDevisId() {
        return sousligneDevisId;
    }

    public void setSousligneDevisId(int sousligneDevisId) {
        this.sousligneDevisId = sousligneDevisId;
    }

    public ModelOperateur getOperateur() {
        return operateur;
    }

    public void setOperateur(ModelOperateur operateur) {
        this.operateur = operateur;
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
