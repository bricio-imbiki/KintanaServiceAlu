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

public class ModelSousLigneVitre {
     private int sousligneVitreId;
    private int sousligneDevisId; // ID de la sous-ligne de devis
    private ModelTypeVitre  vitre;           // ID de la vitre
    private int quantiteUtilisee; // Quantité de vitre utilisée
    private BigDecimal prixTotal;  // Prix total pour cette sous-ligne de vitre

    // Constructor
    public ModelSousLigneVitre(int sousligneVitreId,int sousligneDevisId, ModelTypeVitre  vitre, 
                                int quantiteUtilisee, BigDecimal prixTotal) {
          this.sousligneVitreId = sousligneVitreId;
        this.sousligneDevisId = sousligneDevisId;
        this.vitre = vitre;
        this.quantiteUtilisee = quantiteUtilisee;
        this.prixTotal = prixTotal;
    }

    // Getters and Setters
       public int getSousligneVitreId() {
        return sousligneVitreId;
    }

    public void setSousligneVitre(int sousligneVitreId) {
        this.sousligneVitreId = sousligneVitreId;
    }
    public int getSousligneDevisId() {
        return sousligneDevisId;
    }

    public void setSousligneDevisId(int sousligneDevisId) {
        this.sousligneDevisId = sousligneDevisId;
    }

    public ModelTypeVitre getTypeVitre() {
        return vitre;
    }

    public void setTypeVitre(ModelTypeVitre vitre) {
        this.vitre = vitre;
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
