package Models;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ModelClient {
    private int clientId;
    private String nom;
    private String adresse;
    private String telephone;
    private String email;
    private Date dateCreation;
   public ModelClient(){
   }
    // Constructor
    public ModelClient(int clientId, String nom, String adresse, String telephone, String email,Date dateCreation) {
        this.clientId = clientId;
        this.nom = nom;
        this.adresse = adresse;
        this.telephone = telephone;
        this.email = email;
        this.dateCreation = dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    // Getters and setters
    public int getClientId() {
        return clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
        public String getFormattedDateClient() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(dateCreation);
    }
 public boolean isEmpty() {
    return (nom == null || nom.trim().isEmpty()) ||
           (adresse == null || adresse.trim().isEmpty()) ||
           (telephone == null || telephone.trim().isEmpty()) ||
           (email == null || email.trim().isEmpty()) ||
            (dateCreation == null);
}
}
