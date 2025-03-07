/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author brici_6ul2f65
 */
public class ModelMainOeuvre {
    private int mainOeuvreId;
    private double pourcentageMainOeuvre;

    // Constructor
    public ModelMainOeuvre(int mainOeuvreId, double pourcentageMainOeuvre) {
        this.mainOeuvreId = mainOeuvreId;
        this.pourcentageMainOeuvre = pourcentageMainOeuvre;
    }

    // Getters and setters
    public int getMainOeuvreId() {
        return mainOeuvreId;
    }

    public void setMainOeuvreId(int mainOeuvreId) {
        this.mainOeuvreId = mainOeuvreId;
    }

    public double getPourcentageMainOeuvre() {
        return pourcentageMainOeuvre;
    }

    public void setPourcentageMainOeuvre(double pourcentageMainOeuvre) {
        this.pourcentageMainOeuvre = pourcentageMainOeuvre;
    }
}
