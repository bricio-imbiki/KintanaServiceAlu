/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import java.math.BigDecimal;

public class ModelSousLigneClips {
    private int sousligneClipsId;
    private int sousligneDevisId;  // ID de la sous-ligne de devis
    private ModelClips clips;      // Référence au modèle de clips
    private int quantiteUtilisee;  // Quantité de clips utilisée
    private BigDecimal prixTotal;  // Prix total pour cette sous-ligne de clips
public ModelSousLigneClips() {}
    // Constructor
    public ModelSousLigneClips(int sousligneClipsId, int sousligneDevisId, ModelClips clips,
                                int quantiteUtilisee, BigDecimal prixTotal) {
        this.sousligneClipsId = sousligneClipsId;
        this.sousligneDevisId = sousligneDevisId;
        this.clips = clips;
        this.quantiteUtilisee = quantiteUtilisee;
        this.prixTotal = prixTotal;
    }

    // Getters and Setters
    public int getSousligneClipsId() {
        return sousligneClipsId;
    }

    public void setSousligneClipsId(int sousligneClipsId) {
        this.sousligneClipsId = sousligneClipsId;
    }

    public int getSousligneDevisId() {
        return sousligneDevisId;
    }

    public void setSousligneDevisId(int sousligneDevisId) {
        this.sousligneDevisId = sousligneDevisId;
    }

    public ModelClips getClips() {
        return clips;
    }

    public void setClips(ModelClips clips) {
        this.clips = clips;
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

