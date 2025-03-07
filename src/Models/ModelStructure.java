/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 *
 * @author brici_6ul2f65
 */


public class ModelStructure {
    private Integer structureId;  // ID de la structure
    private String structure; // Nom de la structure (ex. "Tout vitrée", "Semi vitrée", "Pleine")

    // Constructeur
    public ModelStructure(Integer structureId, String structure) {
        this.structureId = structureId;
        this.structure = structure;
    }

    // Getters et Setters
    public Integer getStructureId() {
        return structureId;
    }

    public void setStructureId(Integer structureId) {
        this.structureId = structureId;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

}
