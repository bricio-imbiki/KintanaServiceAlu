/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Models;



import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public class ModelSousLigneDevis {
    private int sousLigneDevisId; // Id de la dimension (peut être auto-incrémenté)
    private int ligneDevisId; // Id de la ligne de devis à laquelle cette dimension est associée
    private Integer nbVentaux;
      private List<ModelSousLigneJoint> joints; // Liste des joints utilisés
    private List<ModelSousLigneRoulette> roulettes; // Liste des roulettes utilisées
        private List<ModelSousLigneChassis> chassis; 
 
    private List<ModelSousLigneVitre> vitres; // Liste des vitres utilisées
    private List<ModelSousLigneVis> vis; // Liste des vis utilisées
     private BigDecimal largeur; // Largeur de la dimension
    private BigDecimal hauteur; // Hauteur de la dimension
    private int quantite; // Quantité de cette dimension
    private BigDecimal prixUnitaire; // Prix unitaire pour cette dimension
    private BigDecimal prixTotal; // Prix total pour cette dimension
    
    private ModelStructure structure; // Change en Integer
   
    private BigDecimal mainOeuvre; // Main d'oeuvre pour cette dimension
  

    private List<ModelSousLignePaumelle> paumelles; // Liste des paumelles utilisées
  
      private List<ModelSousLigneSerrure> serrures; 
    private List<ModelSousLignePoignee> poignees;
    
        // Newly added fields
    private List<ModelSousLigneOperateur> operateurs; // List of operators
    private List<ModelSousLigneLameVerre> lames; // List of slats
    private List<ModelSousLigneClips> clips; // List of clips
    private List<ModelSousLigneRivette> rivettes; // List of rivets
  // Constructors
    public ModelSousLigneDevis() {
    }

    // Constructeur avec tous les champs
public ModelSousLigneDevis(
    int sousLigneDevisId,
    int ligneDevisId,
    ModelStructure structure,
    Integer nbVentaux,
    BigDecimal largeur,
    BigDecimal hauteur,
    int quantite,
    BigDecimal prixUnitaire,
    BigDecimal prixTotal,
    List<ModelSousLigneChassis> chassis,
    List<ModelSousLigneVitre> vitres,
    List<ModelSousLigneJoint> joints,
    List<ModelSousLignePaumelle> paumelles,
    List<ModelSousLigneRoulette> roulettes,
    List<ModelSousLigneSerrure> serrures,
    List<ModelSousLignePoignee> poignees,
    List<ModelSousLigneVis> vis,
    List<ModelSousLigneOperateur> operateurs,
    List<ModelSousLigneLameVerre> lames,
    List<ModelSousLigneClips> clips,
    List<ModelSousLigneRivette> rivettes
) {
    this.sousLigneDevisId = sousLigneDevisId;
    this.ligneDevisId = ligneDevisId;
    this.nbVentaux = nbVentaux;
    this.structure = structure;
    this.largeur = largeur;
    this.hauteur = hauteur;
    this.quantite = quantite;
    this.prixUnitaire = prixUnitaire;
    this.prixTotal = prixTotal;
    this.chassis = chassis;
    this.vitres = vitres;
    this.joints = joints;
    this.paumelles = paumelles;
    this.roulettes = roulettes;
    this.serrures = serrures;
    this.poignees = poignees;
    this.vis = vis;
    this.operateurs = operateurs;
    this.lames = lames;
    this.clips = clips;
    this.rivettes = rivettes;
}

    // Getters et setters
    public int getSousLigneDevisId() {
        return sousLigneDevisId;
    }

    public void setSousLigneDevisId(int sousLigneDevisId) {
        this.sousLigneDevisId = sousLigneDevisId;
    }
   
    public int getLigneDevisId() {
        return ligneDevisId;
    }

    public void setLigneDevisId(int ligneDevisId) {
        this.ligneDevisId = ligneDevisId;
    }
    public Integer getnbVentaux() {
        return nbVentaux;
    }

    public void setnbVentaux(Integer nbVentaux) {
        this.nbVentaux = nbVentaux;
    }
    
        public ModelStructure getStructure() {
        return structure;
    }

    public void setStructure(ModelStructure structure) {
        this.structure = structure;
    }

    public BigDecimal getLargeur() {
        return largeur;
    }

    public void setLargeur(BigDecimal largeur) {
        this.largeur = largeur;
    }

    public BigDecimal getHauteur() {
        return hauteur;
    }

    public void setHauteur(BigDecimal hauteur) {
        this.hauteur = hauteur;
    }
  @Override
    public String toString() {
        return largeur + " mm (Lt) x " + hauteur + " mm (Ht)";
    }
    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public BigDecimal getPrixUnitaire() {
        return prixUnitaire;
    }

    public void setPrixUnitaire(BigDecimal prixUnitaire) {
        this.prixUnitaire = prixUnitaire;
    }

    public BigDecimal getPrixTotal() {
        return prixTotal;
    }

    public void setPrixTotal(BigDecimal prixTotal) {
        this.prixTotal = prixTotal;
    }

    public BigDecimal getMainOeuvre() {
        return mainOeuvre;
    }

    public void setMainOeuvre(BigDecimal mainOeuvre) {
        this.mainOeuvre = mainOeuvre;
    }

    public List<ModelSousLigneChassis> getChassis() {
        return chassis;
    }
    
    public void setChassis(List<ModelSousLigneChassis> chassis ){
        this.chassis = chassis;
    }

    public List<ModelSousLigneVitre> getVitres() {
        return vitres;
    }

    public void setVitres(List<ModelSousLigneVitre> vitres) {
        this.vitres = vitres;
    }

    public List<ModelSousLigneVis> getVis() {
        return vis;
    }

    public void setVis(List<ModelSousLigneVis> vis) {
        this.vis = vis;
    }

    public List<ModelSousLignePaumelle> getPaumelles() {
        return paumelles;
    }

    public void setPaumelles(List<ModelSousLignePaumelle> paumelles) {
        this.paumelles = paumelles;
    }

    public List<ModelSousLigneJoint> getJoints() {
        return joints;
    }

    public void setJoints(List<ModelSousLigneJoint> joints) {
        this.joints = joints;
    }

    public List<ModelSousLigneRoulette> getRoulettes() {
        return roulettes;
    }

    public void setRoulettes(List<ModelSousLigneRoulette> roulettes) {
        this.roulettes = roulettes;
    }
    
        public List<ModelSousLigneSerrure> getSerrures() {
        return serrures;
    }

    public void setSerrures(List<ModelSousLigneSerrure> serrures) {
        this.serrures = serrures;
    }
      public List<ModelSousLignePoignee> getPoignees() {
        return poignees;
    }

    public void setPoignees(List<ModelSousLignePoignee> poignees) {
        this.poignees = poignees;
    }

    public List<ModelSousLigneOperateur> getOperateurs() {
        return operateurs;
    }

    public void setOperateurs(List<ModelSousLigneOperateur> operateurs) {
        this.operateurs = operateurs;
    }

    public List<ModelSousLigneLameVerre> getLames() {
        return lames;
    }

    public void setLames(List<ModelSousLigneLameVerre> lames) {
        this.lames = lames;
    }

    public List<ModelSousLigneClips> getClips() {
        return clips;
    }

    public void setClips(List<ModelSousLigneClips> clips) {
        this.clips = clips;
    }

    public List<ModelSousLigneRivette> getRivettes() {
        return rivettes;
    }

    public void setRivettes(List<ModelSousLigneRivette> rivettes) {
        this.rivettes = rivettes;
    }


}
