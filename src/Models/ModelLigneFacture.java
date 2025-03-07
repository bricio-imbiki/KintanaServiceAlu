package Models;

import java.math.BigDecimal;
import java.util.List;

public class ModelLigneFacture  {
    private int ligneFactureId; // Corresponds to the unique ID for the line in the invoice
    private ModelFacture facture;
    private List<ModelSousLigneFacture> sousLignesFacture;
    private int factureId;
    private int produitId;
    private String designation;
    private int totalQuantite;
    private BigDecimal totalPrix;
    // Constructors
    public ModelLigneFacture() {
       
    }

  
    
     public ModelLigneFacture(int ligneFactureId,int factureId, int produitId,String designation, int totalQuantite, BigDecimal totalPrix, 
                             List<ModelSousLigneFacture> sousLignesFacture) {
        this.sousLignesFacture =sousLignesFacture;
        this.ligneFactureId = ligneFactureId;
        this.factureId = factureId;
        this.produitId=produitId;
        this.designation=designation;
        this.totalQuantite=totalQuantite;
        this.totalPrix=totalPrix;
        
     
    }
     
      public int getProduitId() {
        return produitId;
    }

    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }
    
     public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getTotalQuantite() {
        return totalQuantite;
    }

    public void setTotalQuantite(int totalQuantite) {
        this.totalQuantite = totalQuantite;
    }

    public BigDecimal getTotalPrix() {
        return totalPrix;
    }

    public void setTotalPrix(BigDecimal totalPrix) {
        this.totalPrix = totalPrix;
    }
       public int getFactureId() {
        return factureId;
    }

    public void setFactureId(int factureId) {
        this.factureId = factureId;
    }
    // Getters and Setters
        public List<ModelSousLigneFacture> getSousLigneFacture() {
        return sousLignesFacture;
    }

    public void setSousLignesFacture(List<ModelSousLigneFacture> sousLignesFacture) {
        this.sousLignesFacture = sousLignesFacture;
    }
       public int getLigneFactureId() {
        return ligneFactureId;
    }

    public void setLigneFactureId(int ligneFactureId) {
        this.ligneFactureId = ligneFactureId;
    }
        public ModelFacture getFacture() {
        return facture;
    }

    public void setLigneFacture(ModelFacture facture) {
        this.facture = facture;
    }
 
}
