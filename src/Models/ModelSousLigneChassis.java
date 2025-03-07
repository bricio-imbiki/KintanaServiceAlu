package Models;



import java.math.BigDecimal;

// Classe abstraite pour les sous-lignes de profilé alu
public class ModelSousLigneChassis{
    private int sousligneChassisId;
    private  ModelChassis chassis;
    private int sousligneDevisId;
    private BigDecimal quantiteUtilisee;
    private BigDecimal prixTotal;

    // Constructeur
    public ModelSousLigneChassis(int sousligneChassisId,  int sousligneDevisId, ModelChassis chassis, BigDecimal quantiteUtilisee, BigDecimal prixTotal) {
        this.sousligneChassisId = sousligneChassisId;
         this.chassis = chassis;
        this.sousligneDevisId = sousligneDevisId;
        this.quantiteUtilisee = quantiteUtilisee;
        this.prixTotal = prixTotal;
    }

  // Getters et Setters
    public int getSousligneChassisId() {
        return sousligneChassisId;
    }

    public void setSousligneChassisId(int sousligneChassisId) {
        this.sousligneChassisId = sousligneChassisId;
    }
  public ModelChassis getChassis() {
        return chassis;
    }

    public void setChassis(ModelChassis chassis) {
        this.chassis = chassis;
    }

    public int getSousligneDevisId() {
        return sousligneDevisId;
    }

    public void setSousligneDevisId(int sousligneDevisId) {
        this.sousligneDevisId = sousligneDevisId;
    }

    public BigDecimal getQuantiteUtilisee() {
        return quantiteUtilisee;
    }

    public void setQuantiteUtilisee(BigDecimal quantiteUtilisee) {
        this.quantiteUtilisee = quantiteUtilisee;
    }

    public BigDecimal getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(BigDecimal prixTotal) {
        this.prixTotal = prixTotal;
    }



    
}
