package Models;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class ModelLigneDevis {
    private int ligneDevisId;
    private int devisId;
    private ModelProduit produit;

    private String designation;
    private int totalQuantite;
    private BigDecimal totalPrix;
    private List<ModelSousLigneDevis> sousLignesDevis;
   // Default constructor
    public ModelLigneDevis() {
        // Initialize default values if necessary
    }
    // Constructeur
    public ModelLigneDevis(int ligneDevisId, int devisId, ModelProduit produit, String designation, int totalQuantite, BigDecimal totalPrix, List<ModelSousLigneDevis> sousLignesDevis) {
        this.ligneDevisId = ligneDevisId;
        this.devisId = devisId;
        this.produit = produit;
        this.designation = designation;
        this.totalQuantite = totalQuantite;
        this.totalPrix = totalPrix;
        this.sousLignesDevis = sousLignesDevis;
    }

    // Getters et Setters
    public int getLigneDevisId() {
        return ligneDevisId;
    }

    public void setLigneDevisId(int ligneDevisId) {
        this.ligneDevisId = ligneDevisId;
    }

    public int getDevisId() {
        return devisId;
    }

    public void setDevisId(int devisId) {
        this.devisId = devisId;
    }

    public ModelProduit getProduit() {
        return produit;
    }

    public void setProduit(ModelProduit produit) {
        this.produit = produit;
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

    public List<ModelSousLigneDevis> getSousLignesDevis() {
        return sousLignesDevis;
    }

    public void setSousLignesDevis(List<ModelSousLigneDevis> sousLignesDevis) {
        this.sousLignesDevis = sousLignesDevis;
    }


}
