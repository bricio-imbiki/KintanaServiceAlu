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
import java.text.SimpleDateFormat;
import java.util.Date;

public class ModelPaiementAcompte {
    private int acompteId;
    private ModelCommande commande;
    private Date datePaiement;
    private BigDecimal montantPaye;

    private String modePaiement;

    // Constructeurs, getters et setters
 public ModelPaiementAcompte(){
     
 }
    public ModelPaiementAcompte(int acompteId, ModelCommande commande, Date datePaiement, BigDecimal montantPaye, String modePaiement) {
        this.acompteId = acompteId;
        this.commande = commande;
        this.datePaiement = datePaiement;
        this.montantPaye = montantPaye;
        this.modePaiement = modePaiement;
    }
    // Getter pour le numéro de devis
    public String getNumero() {
      String idPart = String.format("%04d", acompteId); // Format ID sur 4 chiffres
        return  idPart;
    }
    public int getAcompteId() {
        return acompteId;
    }

    public void setAcompteId(int acompteId) {
        this.acompteId = acompteId;
    }

    public ModelCommande getCommande() {
        return commande;
    }

    public void setCommandeId(ModelCommande commande) {
        this.commande = commande;
    }

    public Date getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(Date datePaiement) {
        this.datePaiement = datePaiement;
    }

          public String getFormattedDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(datePaiement);
    }
    public BigDecimal getMontantPaye() {
        return montantPaye;
    }

    public void setMontantPaye(BigDecimal montantPaye) {
        this.montantPaye = montantPaye;
    }


    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }
}


