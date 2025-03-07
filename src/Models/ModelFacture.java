package Models;

import Models.ModelStatut.ModelStatutFacture;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ModelFacture {
    private int factureId;
    private Date dateFacture;
    private BigDecimal totalFactureHT;
    private BigDecimal tvaFacturePercentage;
    private BigDecimal totalFactureTTC;
    private ModelClient client; // Référence au client lié à la facture
    private ModelStatutFacture statutFacture; // Example: "Payée", "En attente", etc.
    private List<ModelLigneFacture> lignesFacture;
    private ModelCommande commande;
    private BigDecimal totalFactureTva;
    private BigDecimal totalFactureNetHt;
    private BigDecimal totalFactureRemise;
    private BigDecimal remiseFacturePercentage;


    
    
    // Constructeur par défaut
    public ModelFacture() {}

    // Constructeur avec paramètres
    public ModelFacture(int factureId,ModelCommande commande, ModelClient client, Date dateFacture, BigDecimal totalFactureHT,BigDecimal remiseFacturePercentage,BigDecimal totalFactureRemise,
                     BigDecimal tvaFacturePercentage,BigDecimal totalFactureTva, BigDecimal totalFactureTTC, ModelStatutFacture statutFacture, 
                        List<ModelLigneFacture> lignesFacture) {
        this.factureId = factureId;
        this.statutFacture = statutFacture;
        this.factureId = factureId;
        this.statutFacture = statutFacture;
        this.lignesFacture=lignesFacture;
        this.dateFacture=dateFacture;
        this.client = client;
        this.totalFactureHT = totalFactureHT;
        this.remiseFacturePercentage=remiseFacturePercentage;
        this.totalFactureRemise=totalFactureRemise;
        this.tvaFacturePercentage=tvaFacturePercentage;
        this.totalFactureTva=totalFactureTva;
        this.totalFactureTTC=totalFactureTTC;
        this.commande=commande;
    }
  
  public String getNumeroFacture() {
      String idPart = String.format("%04d", factureId); // Format ID sur 4 chiffres
        return  idPart;
    }
   public List<ModelLigneFacture> getLignesFacture() {
        return lignesFacture;
    }


    public void setLignesFacture(List<ModelLigneFacture> lignesFacture) {
        this.lignesFacture = lignesFacture;
    }
    // Getters et Setters
    public int getFactureId() {
        return factureId;
    }

    public void setFactureId(int factureId) {
        this.factureId = factureId;
    }

    public Date getDateFacture() {
        return dateFacture;
    }

    public void setDateFacture(Date dateFacture) {
        this.dateFacture = dateFacture;
    }

    public BigDecimal getTotalFactureHT() {
        return totalFactureHT;
    }

    public void setTotalFactureHT(BigDecimal totalFactureHT) {
        this.totalFactureHT = totalFactureHT;
    }
   public BigDecimal getRemiseFacturePercentage() {
        return remiseFacturePercentage;
    }

    public void setRemiseFacturePercentage(BigDecimal remiseFacturePercentage) {
        this.remiseFacturePercentage = remiseFacturePercentage;
    }
      public BigDecimal getTotalFactureRemise() {
        return totalFactureRemise;
    }

    public void setTotalFactureRemise(BigDecimal totalFactureRemise) {
        this.totalFactureRemise = totalFactureRemise;
    }
          public BigDecimal getTotalFactureNetHT() {
        return totalFactureNetHt;
    }

    public void setTotalFactureNetHT(BigDecimal totalFactureNetHt) {
        this.totalFactureNetHt = totalFactureNetHt;
    }
    
    public BigDecimal getTvaFacturePercentage() {
        return tvaFacturePercentage;
    }

    public void setTvaFacturePercentage(BigDecimal tvaFacturePercentage) {
        this.tvaFacturePercentage = tvaFacturePercentage;
    }
   
          public BigDecimal getTotalFactureTva() {
        return totalFactureTva;
    }
           public void setTotalFactureTva(BigDecimal totalFactureTva) {
        this.totalFactureTva = totalFactureTva;
    }
    public BigDecimal getTotalFactureTTC() {
        return totalFactureTTC;
    }

    public void setTotalFactureTTC(BigDecimal totalFactureTTC) {
        this.totalFactureTTC = totalFactureTTC;
    }

 
    public ModelClient getClient() {
        return client;
    }

    
    public void setClient(ModelClient client) {
        this.client = client;
    }
        public ModelCommande getCommande() {
        return commande;
    }

    
    public void setCommande(ModelCommande commande) {
        this.commande = commande;
    }
  public ModelStatutFacture getStatutFacture() {
        return statutFacture;
    }

    public void setStatutFacture(ModelStatutFacture statutFacture) {
        this.statutFacture = statutFacture;
    }
     public String getFormattedDateFacture() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(dateFacture);
    }
    
}
