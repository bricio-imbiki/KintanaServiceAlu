package Models;

import java.math.BigDecimal;
import java.util.List;

public class ModelLigneCommande {
    private int ligneCommandeId;
    private ModelCommande commande;
    private int produitId;
    private int profileAluId;
    private int typeOuvertureId;
    private int vitrageId;
    private int typeVitreId;
    private Integer structureId; // Can be null
    private String designation;
    private int totalQuantite;
    private BigDecimal totalPrix;
    private List<ModelSousLigneCommande> sousLignesCommande;
    public ModelLigneCommande() {
        // You can initialize default values here if needed
    }
    public ModelLigneCommande(int ligneCommandeId, ModelCommande commande, int produitId,
                              String designation, int totalQuantite, BigDecimal totalPrix, List<ModelSousLigneCommande> sousLignesCommande) {
        this.ligneCommandeId = ligneCommandeId;
        this.commande = commande;
        this.produitId = produitId;
        this.designation = designation;
        this.totalQuantite = totalQuantite;
        this.totalPrix = totalPrix;
        this.sousLignesCommande = sousLignesCommande;
    }

    // Getters and Setters
    public int getLigneCommandeId() { return ligneCommandeId; }
    public void setLigneCommandeId(int ligneCommandeId) { this.ligneCommandeId = ligneCommandeId; }

    public ModelCommande getCommande() { return commande; }
    public void setCommande(ModelCommande commande) { this.commande = commande; }

    public int getProduitId() { return produitId; }
    public void setProduitId(int produitId) { this.produitId = produitId; }

    public int getProfileAluId() { return profileAluId; }
    public void setProfileAluId(int profileAluId) { this.profileAluId = profileAluId; }

    public int getTypeOuvertureId() { return typeOuvertureId; }
    public void setTypeOuvertureId(int typeOuvertureId) { this.typeOuvertureId = typeOuvertureId; }

    public int getVitrageId() { return vitrageId; }
    public void setVitrageId(int vitrageId) { this.vitrageId = vitrageId; }

    public int getTypeVitreId() { return typeVitreId; }
    public void setTypeVitreId(int typeVitreId) { this.typeVitreId = typeVitreId; }

    public Integer getStructureId() { return structureId; }
    public void setStructureId(Integer structureId) { this.structureId = structureId; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public int getTotalQuantite() { return totalQuantite; }
    public void setTotalQuantite(int totalQuantite) { this.totalQuantite = totalQuantite; }

    public BigDecimal getTotalPrix() { return totalPrix; }
    public void setTotalPrix(BigDecimal totalPrix) { this.totalPrix = totalPrix; }

    public List<ModelSousLigneCommande> getSousLignesCommande() { return sousLignesCommande; }
    public void setSousLignesCommande(List<ModelSousLigneCommande> sousLignesCommande) { this.sousLignesCommande = sousLignesCommande; }
}
