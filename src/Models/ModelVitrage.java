/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author brici_6ul2f65
 */
public class ModelVitrage {
    private int vitrageId;
    private  String type;

    public ModelVitrage(int vitrageId, String type) {
        this.vitrageId = vitrageId;
        this.type = type;
    }

    public int getVitrageId() {
        return vitrageId;
    }

    public String getTypeVitrage() {
        return type;
    }
        public void setVitrageId(int vitrageId) {
        this.vitrageId = vitrageId;
    }

  public void setTypeVitrage(String type) {
        this.type = type;
    }
}