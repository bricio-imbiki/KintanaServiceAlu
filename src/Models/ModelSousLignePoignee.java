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
public class ModelSousLignePoignee {
    private int souslignePoigneeId;  // Identifiant unique de la sous-ligne
    private ModelPoignee poignee;            // Identifiant de la poignée associée
    private int sousligneDevisId;     // Identifiant de la sous-ligne de devis associée
    private int quantiteUtilisee;             // Quantité de poignées
 private BigDecimal prixTotal;
    // Constructeur
    public ModelSousLignePoignee() {}
    public ModelSousLignePoignee(int souslignePoigneeId,  int sousligneDevisId,ModelPoignee poignee, int quantiteUtilisee,BigDecimal prixTotal) {
        this.souslignePoigneeId = souslignePoigneeId;
        this.poignee = poignee;
        this.sousligneDevisId = sousligneDevisId;
        this.quantiteUtilisee = quantiteUtilisee;
         this.prixTotal = prixTotal;
    }

    // Getters et Setters
    public int getSouslignePoigneeId() {
        return souslignePoigneeId;
    }

    public void setSouslignePoigneeId(int souslignePoigneeId) {
        this.souslignePoigneeId = souslignePoigneeId;
    }

    public ModelPoignee getPoignee() {
        return poignee;
    }

    public void setPoignee(ModelPoignee poignee) {
        this.poignee = poignee;
    }

    public int getSousligneDevisId() {
        return sousligneDevisId;
    }

    public void setSousligneDevisId(int sousligneDevisId) {
        this.sousligneDevisId = sousligneDevisId;
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

