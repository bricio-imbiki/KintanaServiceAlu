/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

import Models.ModelStatut.ModelStatutCommande;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 *
 * @author brici_6ul2f65
 */
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class ModelCommande extends ModelDevis{
    private int commandeId;
    private Date dateCommande;
    private Date dateLivraison;
    private ModelStatutCommande statutCommande; // Par exemple: "En production", "Livré", etc.
    List<ModelLigneCommande> lignesCommande;
    private BigDecimal acomptePayed;
    private BigDecimal leftTopaye;
    private String numeroCommande;


    public ModelCommande() {
    }
    // Constructor with arguments
    public ModelCommande(int commandeId, ModelDevis devis, ModelClient client, Date dateCommande, BigDecimal totalHT,  BigDecimal remisePercentage, BigDecimal totalRemise,BigDecimal acomptePercentage, BigDecimal totalAcompte,BigDecimal tvaPercentage,BigDecimal totalTva, BigDecimal totalTTC, BigDecimal acomptePayed, BigDecimal leftTopaye, ModelStatutCommande statutCommande,  List<ModelLigneCommande> lignesCommande) {
        this.commandeId = commandeId;
        this.lignesCommande=lignesCommande;
       this.acomptePayed= acomptePayed;
       this.leftTopaye=leftTopaye;
       // this.numeroCommande = generateNumeroCommande(); // Génération automatique
    }
    
    
  
    // Getter pour le numéro de devis
    public String getNumeroCommande() {
      String idPart = String.format("%04d", commandeId); // Format ID sur 4 chiffres
        return  idPart;
    }

      public BigDecimal getAcomptePayed() {
        return acomptePayed;
    }

    public void setAcomptePayed(BigDecimal acomptePayed) {
        this.acomptePayed = acomptePayed;
    }
     public BigDecimal getLeftToPaye() {
        return leftTopaye;
    }

    public void setLeftToPaye(BigDecimal leftTopaye) {
        this.leftTopaye = leftTopaye;
    }
    public int getCommandeId() {
        return commandeId;
    }

    public void setCommandeId(int commandeId) {
        this.commandeId = commandeId;
         // this.numeroCommande = generateNumeroCommande(); // Met à jour le numéro de devis si l'ID change
    }

    public Date getDateCommande() {
        return dateCommande;
    }

    public void setDateCommande(Date dateCommande) {
        this.dateCommande = dateCommande;
           // this.numeroCommande = generateNumeroDevis(); // Met à jour le numéro de devis si l'ID change
    }

    public Date getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(Date dateLivraison) {
        this.dateLivraison = dateLivraison;
    }

    @Override
    public ModelStatutCommande getStatut() {
        return statutCommande;
    }

    public void setStatutCommande(ModelStatutCommande statutCommande) {
        this.statutCommande = statutCommande;
    }

    public List<ModelLigneCommande> getLignesCommande() {
        return lignesCommande;
    }

    public void setLignesCommande(List<ModelLigneCommande> lignesCommande) {
        this.lignesCommande = lignesCommande;
    }

   // Format dateCommande as a string
    public String getFormattedDateCommande() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateCommande != null ? dateFormat.format(dateCommande) : null;
    }

    // Format dateLivraison as a string
    public String getFormattedDateLivraison() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateLivraison != null ? dateFormat.format(dateLivraison) : null;
    }

}
