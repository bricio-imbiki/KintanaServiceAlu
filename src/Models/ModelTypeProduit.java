/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author brici_6ul2f65
 */
public class ModelTypeProduit {
    private int typeProduitId;
    private String type;

    // Constructor
    public ModelTypeProduit(int typeProduitId, String type) {
        this.typeProduitId = typeProduitId;
        this.type = type;
    }

    // Getters and setters
    public int getTypeProduitId() {
        return typeProduitId;
    }

    public void setTypeProduitId(int typeProduitId) {
        this.typeProduitId = typeProduitId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}