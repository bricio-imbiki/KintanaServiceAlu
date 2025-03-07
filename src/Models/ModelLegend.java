/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author brici_6ul2f65
 */



import java.awt.Color;

import java.awt.Color;

public class ModelLegend {

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColorLight() {
        return colorLight;
    }

    public void setColorLight(Color colorLight) {
        this.colorLight = colorLight;
    }

    public ModelLegend(String name, Color color, Color colorLight) {
        this.name = name;
        this.color = color;
        this.colorLight = colorLight;
    }
   public ModelLegend(String name, Color color) {
        this.name = name;
        this.color = color;
       
    }
    public ModelLegend() {
    }

    private String name;
    private Color color;
    private Color colorLight;
}
