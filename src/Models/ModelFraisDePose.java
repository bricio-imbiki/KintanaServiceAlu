/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author brici_6ul2f65
 */
public class ModelFraisDePose {
    private int fraisPoseId;
    private double fraisPose;

    // Constructor
    public ModelFraisDePose(int fraisPoseId, double fraisPose) {
        this.fraisPoseId = fraisPoseId;
        this.fraisPose = fraisPose;
    }

    // Getters and setters
    public int getFraisPoseId() {
        return fraisPoseId;
    }

    public void setFraisPoseId(int fraisPoseId) {
        this.fraisPoseId = fraisPoseId;
    }

    public double getFraisPose() {
        return fraisPose;
    }

    public void setFraisPose(double fraisPose) {
        this.fraisPose = fraisPose;
    }
}