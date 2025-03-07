package Models;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ModelDevis {
    private int devisId;
    private ModelClient client;
    private Date dateDevis;
    private BigDecimal totalHT;         // Nouveau champ pour le total hors taxes
    private BigDecimal remisePercentage; // Nouveau champ pour le pourcentage de remise
    private BigDecimal totalAcompte;      // Nouveau champ pour le total net hors taxes après remise
    private BigDecimal tvaPercentage;   // Nouveau champ pour le pourcentage de TVA
    private BigDecimal totalTTC;
    private ModelStatut statut;
    private List<ModelLigneDevis> lignesDevis;
    private BigDecimal totalRemise;
    private BigDecimal totalTva;
    private BigDecimal acomptePercentage;
    private String numeroDevis;

    // Constructeur avec les nouveaux champs
    public ModelDevis(int devisId, ModelClient client, Date dateDevis, BigDecimal totalHT, BigDecimal remisePercentage, BigDecimal totalRemise,BigDecimal acomptePercentage, BigDecimal totalAcompte, BigDecimal tvaPercentage,BigDecimal totalTva, BigDecimal totalTTC, ModelStatut statut, List<ModelLigneDevis> lignesDevis) {
        this.devisId = devisId;
        this.client = client;
        this.dateDevis = dateDevis;
        this.totalHT = totalHT;
        this.remisePercentage = remisePercentage;
        this.totalRemise = totalRemise;
        this.acomptePercentage = acomptePercentage;
          this.totalAcompte = totalAcompte;
        this.totalTva = totalTva;
      
        this.tvaPercentage = tvaPercentage;
        this.totalTTC = totalTTC;
        this.statut = statut;
        this.lignesDevis = lignesDevis;
       
    }

    public ModelDevis() {
       
    }
   

    // Getter pour le numéro de devis
    public String getNumeroDevis() {
      String idPart = String.format("%04d", devisId); // Format ID sur 4 chiffres
        return  idPart;
    }
    // Getters et Setters pour les nouveaux champs
    public BigDecimal getTotalHT() {
        return totalHT;
    }

    public void setTotalHT(BigDecimal totalHT) {
        this.totalHT = totalHT;
    }

    public BigDecimal getRemisePercentage() {
        return remisePercentage;
    }

    public void setRemisePercentage(BigDecimal remisePercentage) {
        this.remisePercentage = remisePercentage;
    }
     public BigDecimal getTotalRemise() {
        return totalRemise;
    }

    public void setTotalRemise(BigDecimal totalRemise) {
        this.totalRemise = totalRemise;
    }
 public BigDecimal getAcomptePercentage() {
        return acomptePercentage;
    }

    public void setAcomptePercentage(BigDecimal acomptePercentage) {
        this.acomptePercentage = acomptePercentage;
    }
    public BigDecimal getTotalAcompte() {
        return totalAcompte;
    }

    public void setTotalAcompte(BigDecimal totalAcompte) {
        this.totalAcompte = totalAcompte;
    }

    public BigDecimal getTvaPercentage() {
        return tvaPercentage;
    }

    public void setTvaPercentage(BigDecimal tvaPercentage) {
        this.tvaPercentage = tvaPercentage;
    }
  public BigDecimal getTotalTva() {
        return totalTva;
    }

    public void setTotalTva(BigDecimal totalTva) {
        this.totalTva = totalTva;
    }

    // Autres Getters et Setters existants
    public int getDevisId() {
        return devisId;
    }

    public void setDevisId(int devisId) {
        this.devisId = devisId;
    }

    public ModelClient getClient() {
        return client;
    }

    public void setClient(ModelClient client) {
        this.client = client;
    }

    public Date getDateDevis() {
        return dateDevis;
    }

    public void setDateDevis(Date dateDevis) {
        this.dateDevis = dateDevis;
    }

    public BigDecimal getTotalTTC() {
        return totalTTC;
    }

    public void setTotalTTC(BigDecimal totalTTC) {
        this.totalTTC = totalTTC;
    }

 
    public ModelStatut getStatut() {
        return statut;
    }

    public void setStatut(ModelStatut statut) {
        this.statut = statut;
    }

    public List<ModelLigneDevis> getLignesDevis() {
        return lignesDevis;
    }

    public void setLignesDevis(List<ModelLigneDevis> lignesDevis) {
        this.lignesDevis = lignesDevis;
    }

    // Méthode pour obtenir la date formatée
    public String getFormattedDateDevis() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(dateDevis);
    }
   


}
