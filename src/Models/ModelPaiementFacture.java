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

public class ModelPaiementFacture {
    private int paiementId;
    private ModelFacture facture;
    private Date datePaiement;
    private BigDecimal montantPaye;
    private String modePaiement;

    // Constructeurs, getters et setters
   public ModelPaiementFacture(){
       
   }
    public ModelPaiementFacture(int paiementId, ModelFacture facture, Date datePaiement, BigDecimal montantPaye, String modePaiement) {
        this.paiementId = paiementId;
        this.facture = facture;
        this.datePaiement = datePaiement;
        this.montantPaye = montantPaye;
        this.modePaiement = modePaiement;
    }
  public String getNumero() {
      String idPart = String.format("%04d", paiementId); // Format ID sur 4 chiffres
        return  idPart;
    }
    public int getPaiementId() {
        return paiementId;
    }

    public void setPaiementId(int paiementId) {
        this.paiementId = paiementId;
    }

    public ModelFacture getFacture() {
        return facture;
    }

    public void setFactureId(ModelFacture facture) {
        this.facture = facture;
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

