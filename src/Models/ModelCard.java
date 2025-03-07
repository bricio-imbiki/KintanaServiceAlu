/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author brici_6ul2f65
 */


import javax.swing.Icon;

import javax.swing.Icon;

public class ModelCard {

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

   
 

    public ModelCard(Icon icon, String title, String values) {
        this.icon = icon;
        this.title = title;
        this.values = values;
       
    }

    public ModelCard() {
    }

    private Icon icon;
    private String title;
    private String values;
   
}
