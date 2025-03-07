/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author brici_6ul2f65
 */
public class ModelProduit {
    private int produitId;
    private int typeProduitId;
    private String nom;

    // Constructor
    public ModelProduit(int produitId, int typeProduitId, String nom) {
        this.produitId = produitId;
        this.typeProduitId = typeProduitId;
        this.nom = nom;
    }

    // Getters and setters
    public int getProduitId() {
        return produitId;
    }

    public void setProduitId(int produitId) {
        this.produitId = produitId;
    }

    public int getTypeProduitId() {
        return typeProduitId;
    }

    public void setTypeProduitId(int typeProduitId) {
        this.typeProduitId = typeProduitId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}