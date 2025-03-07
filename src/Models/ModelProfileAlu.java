package Models;

import java.math.BigDecimal;

public class ModelProfileAlu {
    private int profileAluId;

    private String model;
   private String couleur;


    // Constructor
    public ModelProfileAlu(int profileAluId,String model,String couleur) {
        this.profileAluId = profileAluId;
        this.couleur = couleur;
        this.model = model;
    }

    // Getters and Setters
    public int getProfileAluId() {
        return profileAluId;
    }

    public void setProfileAluId(int profileAluId) {
        this.profileAluId = profileAluId;
    }

     public String getCouleur() {
        return couleur;
    }

    public void setCouleur(String couleur) {
        this.couleur = couleur;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
    @Override
   public String toString() {
        return model.toUpperCase() + " " + " ALU " + " " + couleur.toUpperCase();
        
    }
}
