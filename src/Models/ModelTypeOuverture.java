/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author brici_6ul2f65
 */
public class ModelTypeOuverture {
    private int typeOuvertureId;
    private String nom;

    public ModelTypeOuverture(int typeOuvertureId, String nom) {
        this.typeOuvertureId = typeOuvertureId;
        this.nom = nom;
    }

    public int getTypeOuvertureId() {
        return typeOuvertureId;
    }

    public String getNom() {
        return nom;
    }
}