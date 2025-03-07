/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;

/**
 * Classe générique pour représenter un statut, utilisé pour les devis, commandes, et factures.
 * Chaque statut a un ID et une description (ex: "En attente", "Accepté", "En production").
 * 
 */
public class ModelStatut {
    private int statutId;   // Identifiant unique du statut
    private String statut;  // Description du statut

    // Constructeur par défaut
    public ModelStatut() {}

    // Constructeur avec paramètres
    public ModelStatut(int statutId, String statut) {
        this.statutId = statutId;
        this.statut = statut;
    }

    // Getters et Setters
    public int getStatutId() {
        return statutId;
    }

    public void setStatutId(int statutId) {
        this.statutId = statutId;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
    
   
    public static class ModelStatutDevis extends ModelStatut {
        // Constructeur avec paramètres
        public ModelStatutDevis(int statutId, String statut) {
            super(statutId, statut);  // Appel du constructeur de la classe parent
        }

        // Méthodes spécifiques à un statut de devis peuvent être ajoutées ici
    }

   
    public static class ModelStatutCommande extends ModelStatut {
        // Constructeur avec paramètres
        public ModelStatutCommande(int statutId, String statut) {
            super(statutId, statut);  // Appel du constructeur de la classe parent
        }


        // Méthodes spécifiques à un statut de commande peuvent être ajoutées ici
    }
   public static class ModelStatutStock extends ModelStatut {
        // Constructeur avec paramètres
        public ModelStatutStock(int statutId, String statut) {
            super(statutId, statut);  // Appel du constructeur de la classe parent
        }

        // Méthodes spécifiques à un statut de commande peuvent être ajoutées ici
    }
   
    public static class ModelStatutFacture extends ModelStatut {
        // Constructeur avec paramètres
        public ModelStatutFacture(int statutId, String statut) {
            super(statutId, statut);  // Appel du constructeur de la classe parent
        }

        // Méthodes spécifiques à un statut de facture peuvent être ajoutées ici
    } 
}
