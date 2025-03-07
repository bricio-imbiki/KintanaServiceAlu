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
public class ModelSousLigneJoint {
    private int sousLigneJointId; 
    private int sousLigneDevisId; 
    private ModelJoint joint;
    private BigDecimal quantiteUtilisee; 
    private BigDecimal prixTotal; 
    
    // Constructeurs
    public ModelSousLigneJoint() {}

    public ModelSousLigneJoint(int sousLigneJointId,int sousLigneDevisId, ModelJoint joint, BigDecimal quantiteUtilisee,  BigDecimal prixTotal) {
       this.sousLigneJointId = sousLigneJointId;
        this.sousLigneDevisId = sousLigneDevisId;
        this.joint = joint;
        this.quantiteUtilisee = quantiteUtilisee;
        this.prixTotal = prixTotal;
    }

    // Getters et setters
    public int getSousLigneJointId() {
        return sousLigneJointId;
    }

    public void setSousLigneJointId(int sousLigneJointId) {
        this.sousLigneJointId = sousLigneJointId;
    }

    public int getSousLigneDevisId() {
        return sousLigneDevisId;
    }

    public void setSousLigneDevisId(int sousLigneDevisId) {
        this.sousLigneDevisId = sousLigneDevisId;
    }

    public ModelJoint getJoint() {
        return joint;
    }

    public void setJoint( ModelJoint joint) {
        this.joint = joint;
    }

    public BigDecimal getQuantiteUtilisee(){
        return quantiteUtilisee;
    }

    public void setQuantiteUtilisee(BigDecimal quantiteUtilisee) {
        this.quantiteUtilisee= quantiteUtilisee;
    }

    public BigDecimal getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(BigDecimal prixTotal) {
        this.prixTotal = prixTotal;
    }

}
