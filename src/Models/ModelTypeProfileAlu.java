/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author brici_6ul2f65
 */
public class ModelTypeProfileAlu {
    private int typeProfileAluId;
    private String typeProfileAlu;

    // Constructor
    public ModelTypeProfileAlu(int typeProfileAluId, String typeProfileAlu) {
        this.typeProfileAluId = typeProfileAluId;
        this.typeProfileAlu = typeProfileAlu;
    }

    // Getters and Setters
    public int getTypeProfileAluId() {
        return typeProfileAluId;
    }

    public void setTypeProfileAluId(int typeProfileAluId) {
        this.typeProfileAluId = typeProfileAluId;
    }

    public String getTypeProfileAlu() {
        return typeProfileAlu;
    }

    public void setTypeProfileAlu(String typeProfileAlu) {
        this.typeProfileAlu = typeProfileAlu;
    }


}
