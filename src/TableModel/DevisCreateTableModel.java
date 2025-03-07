package TableModel;


import Components.ActionPanel;
import Panel.DevisCreatePanel;
import Models.ModelChassis;
import Models.ModelLigneDevis;
import Models.ModelSousLigneChassis;
import Models.ModelSousLigneClips;
import Models.ModelSousLigneDevis;
import Models.ModelSousLigneJoint;
import Models.ModelSousLigneLameVerre;
import Models.ModelSousLigneOperateur;
import Models.ModelSousLignePaumelle;
import Models.ModelSousLignePoignee;
import Models.ModelSousLigneRivette;
import Models.ModelSousLigneRoulette;
import Models.ModelSousLigneSerrure;
import Models.ModelSousLigneVis;
import Models.ModelSousLigneVitre;
import java.math.BigDecimal;

import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
//25=25
public class DevisCreateTableModel extends DefaultTableModel {

    private static final int FIXED_ROW_COUNT = 50; // Nombre fixe de lignes
    private final List<Object[]> data;
    private final String[] columnNames = {"N° Produit",
        "ChassisTypeFixeId", "ChassisTypeOuvrantId", "Nombre de Ventaux", "vitrageId", "typeVitreId", "structureId",
                                         
         "PaumelleId","RouletteId","SerrureId","PoigneeId","VisId","JointId",
         
          "Designation", "Prix Unitaire", "Quantite", "Prix Total","qte Roulette","qte Paumelle","qte poignee","qte vis","qte serrure","qte joint"
            ,"chassis fixe","chassis ouvrant","P Fixe","P Ouvrant","p vitre","pjoint","P SER","P rou","p pau","p poign","p vis",
          "clipsId", "nbClips", "prixClips",
            "lammeVerreId", "quantiteLammeVerre", "prixLammeVerre",
                    "operateurId", "nbOperateurs", "prixOperateur",
                    "rivetteId", "nbRivettes", "prixRivette", "Action"};

    private List<List<Object[]>> subRows; // Liste de sous-lignes associées aux lignes principales
    private int vitrageId;
    private int typeVitreId;
    private BigDecimal prixVitre;
    private Integer structureId;
    private Integer lammeVerreId;
    private Integer quantiteLammeVerre;
    private BigDecimal prixLammeVerre;
    private Integer operateurId;
    private Integer nbOperateurs;
    private BigDecimal prixOperateur;
    private Integer rivetteId;
    private Integer nbRivettes;
    private BigDecimal prixRivette;
    private Integer clipsId;
    private Integer nbClips;
    private BigDecimal prixClips;
    private Integer serrureId;
    private Integer nbserrure;
    private BigDecimal prixSerrure;

     public DevisCreateTableModel(DevisCreatePanel Owener)  {
        super();
        setColumnIdentifiers(columnNames);
        data = new ArrayList<>();
    }

    @Override
    public int getRowCount() {
        if (data == null) {
            return 0;
        }
        return Math.max(FIXED_ROW_COUNT, data.size());
    }

    @Override
    public Object getValueAt(int row, int column) {
        if (row < data.size()) {
            if (column == 46) { // La colonne "Action"
                if (isMainRow(data.get(row))) {
                    return new ActionPanel();
                } else {
                    return null;
                }
            }
            return data.get(row)[column];
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int row, int column) {
        if (row < data.size()) {
            data.get(row)[column] = aValue;
            fireTableCellUpdated(row, column);
        }
    }

    @Override
    public void addRow(Object[] rowData) {
        if (rowData.length == getColumnCount()) {
            data.add(rowData);
            fireTableRowsInserted(data.size() - 1, data.size() - 1);
        }
    }

    @Override
    public boolean isCellEditable(int row, int column) {
        return column == 46; // Seule la colonne "Action" est éditable
    }

    public void addMainRow(int produitId, String designation, int quantite) {
        addRow(new Object[]{produitId, null,null,
            null, 
            null, 
            null, 
            null, 
            null, 
            null, 
            null
                , null, null, null, 
                designation, null, quantite, null, null,null, null, null, null, null, null,null,null,null,null, null, null, null, null, null,null,null, null, 
     null, 
     null, 
     null, 
     null, 
     null, 
     null, 
     null, 
     null, 
     null, 
     null,null 
     });
    }

    public void addSubRow(int mainRowIndex,int chassisTypeFixeId, int chassisTypeOuvrantId, int nbVentaux, int vitrageId, int typeVitreId, Integer structureId, Integer paumelleId ,Integer rouletteId, Integer serrureId,Integer poigneeId, int visId ,int jointId, String dimensions, BigDecimal prixUnitaire, int quantite,Integer nbRoulette,Integer nbPaumelle,Integer nbPoignee,Integer nbVis,Integer nbSerrrure,BigDecimal longueurJoint,BigDecimal qteChassiFixe,BigDecimal qteChassisOuvrant,BigDecimal PrixCFixe,BigDecimal PrixCOuvrant,BigDecimal prixVitre,BigDecimal prixJoint,BigDecimal prixSerrure,BigDecimal prixRoullette,BigDecimal prixPaumelle,BigDecimal prixPoignee,BigDecimal prixVisAss,
            Integer clipsId, 
    Integer nbClips, 
    BigDecimal prixClips, 
    Integer lammeVerreId, 
    Integer quantiteLammeVerre, 
    BigDecimal prixLammeVerre, 
    Integer operateurId, 
    Integer nbOperateurs, 
    BigDecimal prixOperateur, 
    Integer rivetteId, 
    Integer nbRivettes, 
    BigDecimal prixRivette) {
        int insertIndex = mainRowIndex + 1;

        while (insertIndex < data.size() && !isMainRow(data.get(insertIndex))) {
            insertIndex++;
        }
// Calculate total price as BigDecimal
      BigDecimal totalPrix = prixUnitaire.multiply(BigDecimal.valueOf(quantite));
        data.add(insertIndex, new Object[]{"", chassisTypeFixeId, chassisTypeOuvrantId,  nbVentaux, vitrageId, typeVitreId, structureId, paumelleId ,rouletteId,  serrureId, poigneeId,visId ,jointId , dimensions, prixUnitaire, quantite, totalPrix,nbRoulette, nbPaumelle, nbPoignee, nbVis, nbSerrrure, longueurJoint, qteChassiFixe, qteChassisOuvrant, PrixCFixe, PrixCOuvrant, prixVitre, prixJoint, prixSerrure, prixRoullette, prixPaumelle, prixPoignee, prixVisAss,clipsId, nbClips, prixClips,
                    lammeVerreId, quantiteLammeVerre, prixLammeVerre,
                    operateurId, nbOperateurs, prixOperateur,
                    rivetteId, nbRivettes, prixRivette, null});
        fireTableRowsInserted(insertIndex, insertIndex);
    }

    public void addOrUpdateRow(int produitId, int chassisTypeFixeId, int chassisTypeOuvrantId, int nbVentaux, int vitrageId, int typeVitreId, Integer structureId, Integer paumelleId ,Integer rouletteId, Integer serrureId, Integer poigneeId,int visId ,int jointId ,String designation, BigDecimal prixUnitaire, int quantite, BigDecimal hauteur, BigDecimal largeur,Integer nbRoulette,Integer nbPaumelle,Integer nbPoignee,Integer nbVis,Integer nbSerrrure,BigDecimal longueurJoint,BigDecimal qteChassiFixe,BigDecimal qteChassisOuvrant,BigDecimal PrixCFixe,BigDecimal PrixCOuvrant,BigDecimal prixVitre,BigDecimal prixJoint,BigDecimal prixSerrure,BigDecimal prixRoullette,BigDecimal prixPaumelle,BigDecimal prixPoignee,BigDecimal prixVisAss,
    Integer clipsId, 
    Integer nbClips, 
    BigDecimal prixClips, 
    Integer lammeVerreId, 
    Integer quantiteLammeVerre, 
    BigDecimal prixLammeVerre, 
    Integer operateurId, 
    Integer nbOperateurs, 
    BigDecimal prixOperateur, 
    Integer rivetteId, 
    Integer nbRivettes, 
    BigDecimal prixRivette) {
        boolean mainRowExists = false;
        int mainRowIndex = -1;

        for (int i = 0; i < data.size(); i++) {
            if (getValueAt(i, 13).equals(designation)) { // Colonne Désignation
                mainRowExists = true;
                mainRowIndex = i;
                break;
            }
        }

        if (!mainRowExists) {
            mainRowIndex = data.size();
            addMainRow(produitId, designation, quantite);
        }

        String dimensions = largeur + " mm (Lt) x " + hauteur + " mm (Ht)";
        addOrUpdateSubRow(mainRowIndex,  chassisTypeFixeId,  chassisTypeOuvrantId, nbVentaux, vitrageId, typeVitreId, structureId, paumelleId ,rouletteId,  serrureId,poigneeId, visId ,jointId , dimensions, prixUnitaire, quantite, nbRoulette, nbPaumelle, nbPoignee, nbVis, nbSerrrure, longueurJoint,qteChassiFixe, qteChassisOuvrant, PrixCFixe, PrixCOuvrant, prixVitre, prixJoint, prixSerrure, prixRoullette, prixPaumelle, prixPoignee, prixVisAss,  
       clipsId, 
     nbClips, 
     prixClips, 
     lammeVerreId, 
     quantiteLammeVerre, 
     prixLammeVerre, 
     operateurId, 
     nbOperateurs, 
     prixOperateur, 
     rivetteId, 
     nbRivettes, 
     prixRivette);

        updateMainRowTotals(mainRowIndex);
    }

    public void addOrUpdateSubRow(int mainRowIndex, int chassisTypeFixeId, int chassisTypeOuvrantId, int nbVentaux, int vitrageId, int typeVitreId, Integer structureId, Integer paumelleId ,Integer rouletteId, Integer serrureId,Integer poigneeId, int visId ,int jointId , String dimensions, BigDecimal prixUnitaire, int quantite,Integer nbRoulette,Integer nbPaumelle,Integer nbPoignee,Integer nbVis,Integer nbSerrrure,BigDecimal longueurJoint,BigDecimal qteChassiFixe,BigDecimal qteChassisOuvrant,BigDecimal PrixCFixe,BigDecimal PrixCOuvrant,BigDecimal prixVitre,BigDecimal prixJoint,BigDecimal prixSerrure,BigDecimal prixRoullette,BigDecimal prixPaumelle,BigDecimal prixPoignee,BigDecimal prixVisAss,  Integer clipsId, 
    Integer nbClips, 
    BigDecimal prixClips, 
    Integer lammeVerreId, 
    Integer quantiteLammeVerre, 
    BigDecimal prixLammeVerre, 
    Integer operateurId, 
    Integer nbOperateurs, 
    BigDecimal prixOperateur, 
    Integer rivetteId, 
    Integer nbRivettes, 
    BigDecimal prixRivette) {
        boolean subRowExists = false;
        int insertIndex = mainRowIndex + 1;

        for (int i = mainRowIndex + 1; i < data.size(); i++) {
            if (getValueAt(i, 0).equals("") && getValueAt(i, 13).equals(dimensions)) {
                int currentQuantity = (int) getValueAt(i, 15); // Colonne Quantité
                int newQuantity = currentQuantity + quantite;
                BigDecimal newTotalPrice = prixUnitaire.multiply(BigDecimal.valueOf(newQuantity));
                setValueAt(newQuantity, i, 15);
                setValueAt(newTotalPrice, i, 16);
                subRowExists = true;
                break;
            }
            if (!getValueAt(i, 0).equals("")) {
                insertIndex = i;
                break;
            } else {
                insertIndex = i + 1;
            }
        }

        if (!subRowExists) {
                BigDecimal totalPrix = prixUnitaire.multiply(new BigDecimal(quantite));

            data.add(insertIndex, new Object[]{"", chassisTypeFixeId, chassisTypeOuvrantId,  nbVentaux, vitrageId, typeVitreId, structureId, paumelleId ,rouletteId,  serrureId,poigneeId, visId ,jointId , dimensions, prixUnitaire, quantite, totalPrix, nbRoulette, nbPaumelle, nbPoignee, nbVis, nbSerrrure, longueurJoint, qteChassiFixe, qteChassisOuvrant,  PrixCFixe, PrixCOuvrant,prixVitre, prixJoint, prixSerrure, prixRoullette, prixPaumelle, prixPoignee, prixVisAss,  clipsId, 
     nbClips, 
     prixClips, 
     lammeVerreId, 
     quantiteLammeVerre, 
     prixLammeVerre, 
     operateurId, 
     nbOperateurs, 
     prixOperateur, 
     rivetteId, 
     nbRivettes, 
     prixRivette});
            fireTableRowsInserted(insertIndex, insertIndex);
        }
    }

    public void updateMainRowTotals(int mainRowIndex) {
        int totalQuantity = 0;
           BigDecimal totalPrice = BigDecimal.ZERO;


        for (int i = mainRowIndex + 1; i < data.size(); i++) {
            if (getValueAt(i, 0).equals("")) {
                totalQuantity += (int) getValueAt(i, 15); // Colonne Quantité
            totalPrice = totalPrice.add((BigDecimal) getValueAt(i, 16));
            } else {
                break;
            }
        }

        setValueAt(totalQuantity, mainRowIndex, 15); // Colonne Quantité
        setValueAt(totalPrice, mainRowIndex, 16); // Colonne Prix Total
    }

    public boolean isSubRow(int rowIndex) {
        return getValueAt(rowIndex, 0) == null || getValueAt(rowIndex, 0).toString().isEmpty();
    }

    public void clearTable() {
        int rowCount = data.size();
        data.clear();
        fireTableRowsDeleted(0, rowCount - 1);
    }

    public void removeMainRow(int mainRowIndex) {
        if (isMainRow(data.get(mainRowIndex))) {
            for (int i = mainRowIndex + 1; i < data.size(); i++) {
                if (isSubRow(i)) {
                    data.remove(i);
                    i--;
                } else {
                    break;
                }
            }
            data.remove(mainRowIndex);
            fireTableRowsDeleted(mainRowIndex, mainRowIndex);
        }
    }

    public void removeSubRow(int rowIndex) {
        if (isSubRow(rowIndex)) {
            data.remove(rowIndex);
            fireTableRowsDeleted(rowIndex, rowIndex);

            int mainRowIndex = findMainRowIndexForSubRow(rowIndex);
            if (mainRowIndex != -1) {
                updateMainRowTotals(mainRowIndex);
            }
        }
    }

    @Override
    public void removeRow(int rowIndex) {
        if (data.isEmpty()) {
            return;
        }
        if (rowIndex < 0 || rowIndex >= data.size()) {
            throw new IndexOutOfBoundsException("Index: " + rowIndex + ", Size: " + data.size());
        }
        if (isMainRow(data.get(rowIndex))) {
            removeMainRow(rowIndex);
        } else if (isSubRow(rowIndex)) {
            removeSubRow(rowIndex);
        }
    }

    private boolean isMainRow(Object[] rowData) {
        return rowData[0] != null && !rowData[0].toString().isEmpty();
    }

    public int findMainRowIndexForSubRow(int subRowIndex) {
        for (int i = subRowIndex; i >= 0; i--) {
            if (!isSubRow(i)) {
                return i;
            }
        }
        return -1;
    }

    public int getSubRowCount(int mainRowIndex) {
        int count = 0;
        for (int i = mainRowIndex + 1; i < data.size(); i++) {
            if (isSubRow(i)) {
                count++;
            } else {
                break;
            }
        }
        return count;
    }
    public int getRowIndex(Object[] row) {
    return data.indexOf(row);
}

     public String getMainRowDesignation(int mainRowIndex) {
        // Obtenir la désignation de la ligne principale
        return (String) getValueAt(mainRowIndex, 13);
    }
   
 public List<Object[]> getSubRowsForMainRow(int mainRowIndex) {
    List<Object[]> subRows = new ArrayList<>();

    // Parcourir les lignes à partir de l'index suivant la ligne principale
    for (int i = mainRowIndex + 1; i < data.size(); i++) {
        if (isSubRow(i)) {
            subRows.add(data.get(i));
        } else {
            // Si une ligne principale est rencontrée, on arrête la recherche des sous-lignes
            break;
        }
    }

    return subRows;
}

         // Méthode pour mettre à jour les sous-lignes
    public void updateSubRows(int mainRowIndex, List<Object[]> updatedSubRows) {
        subRows.set(mainRowIndex, updatedSubRows);
        fireTableRowsUpdated(mainRowIndex, mainRowIndex); // Notifier que les sous-lignes ont été mises à jour
    }

// Méthode pour obtenir l'index de la colonne 'désignation'
public int getDesignationColumnIndex() {
    return 13; // La colonne 'Désignation' est à l'index 13
}


public void setDevisLignes(List<ModelLigneDevis> lignesDevis) {
    // Effacer les données existantes dans la table
    clearTable();

    // Parcourir chaque ligne principale de devis
    for (ModelLigneDevis ligne : lignesDevis) {
        // Ajouter la ligne principale au tableau avec les informations pertinentes
        addMainRow(ligne.getProduit().getProduitId(), ligne.getDesignation(), ligne.getTotalQuantite());

        // Récupérer l'index de la ligne principale récemment ajoutée
        int currentMainRowIndex = data.size() - 1;

        // Obtenir les sous-lignes associées à cette ligne principale
        List<ModelSousLigneDevis> sousLignes = ligne.getSousLignesDevis();
        
        // Vérifier s'il y a des sous-lignes à traiter
        if (sousLignes != null && !sousLignes.isEmpty()) {
            // Parcourir chaque sous-ligne associée
            for (ModelSousLigneDevis sousLigne : sousLignes) {
                // Récupérer les châssis associés
                List<ModelSousLigneChassis> chassisList = sousLigne.getChassis();
                   // Valeurs par défaut si les châssis sont manquants
                BigDecimal qteChassisFixe = BigDecimal.ZERO;
                BigDecimal qteChassisOuvrant = BigDecimal.ZERO;
                int chassisTypeFixeId = 0;
                BigDecimal prixChassisFixe = BigDecimal.ZERO;
                int chassisTypeOuvrantId = 0;
                BigDecimal prixChassisOuvrant = BigDecimal.ZERO;

                  if (chassisList != null && !chassisList.isEmpty()) {
                    // Si un châssis fixe existe, l'utiliser
                    if (!chassisList.isEmpty()) {
                        ModelSousLigneChassis chassisFixe = chassisList.get(0);
                        qteChassisFixe = chassisFixe.getQuantiteUtilisee();
                        prixChassisFixe = chassisFixe.getPrixTotal();
                        chassisTypeFixeId = chassisFixe.getChassis().getChassisId();
                    }
                    // Si un châssis ouvrant existe, l'utiliser
                    if (chassisList.size() > 1) {
                        ModelSousLigneChassis chassisOuvrant = chassisList.get(1);
                        qteChassisOuvrant = chassisOuvrant.getQuantiteUtilisee();
                        prixChassisOuvrant = chassisOuvrant.getPrixTotal();
                        chassisTypeOuvrantId = chassisOuvrant.getChassis().getChassisId();
                    }
                    int produitId = ligne.getProduit().getTypeProduitId();
                if(produitId ==1 || produitId==2){
                               ModelSousLigneSerrure sousLigneSerrure = sousLigne.getSerrures() != null && !sousLigne.getSerrures().isEmpty() ? sousLigne.getSerrures().get(0) : null;
            serrureId = (sousLigneSerrure != null) ? sousLigneSerrure.getSerrure().getSerrureId() : -1; // Valeur par défaut si null
                  nbserrure =(sousLigneSerrure != null) ? sousLigneSerrure.getQuantiteUtilisee() : null;
                   prixSerrure= (sousLigneSerrure != null) ? sousLigneSerrure.getPrixTotal(): null;
                  // Récupérer les informations de vitrage (en supposant un seul vitrage)
                    ModelSousLigneVitre sousLigneVitre = sousLigne.getVitres().isEmpty() ? null : sousLigne.getVitres().get(0);
                   vitrageId = (sousLigneVitre != null) ? sousLigneVitre.getTypeVitre().getVitrage().getVitrageId() : -1; // Valeur par défaut si null
                  typeVitreId = (sousLigneVitre != null) ? sousLigneVitre.getTypeVitre().getTypeVitreId() : -1; // Valeur par défaut si null
                   prixVitre = (sousLigneVitre != null) ? sousLigneVitre.getPrixTotal(): null;
                
                    // Récupérer la structure (si présente)
                    structureId = (sousLigne.getStructure() != null) ? sousLigne.getStructure().getStructureId() : null;
                }else if( produitId==4){
                      ModelSousLigneVitre sousLigneVitre = sousLigne.getVitres().isEmpty() ? null : sousLigne.getVitres().get(0);
                   vitrageId = (sousLigneVitre != null) ? sousLigneVitre.getTypeVitre().getVitrage().getVitrageId() : -1; // Valeur par défaut si null
                  typeVitreId = (sousLigneVitre != null) ? sousLigneVitre.getTypeVitre().getTypeVitreId() : -1; // Valeur par défaut si null
                   prixVitre = (sousLigneVitre != null) ? sousLigneVitre.getPrixTotal(): null;
                }else if(produitId ==3){
                   // Ajouter les lammes de verre
                 ModelSousLigneLameVerre sousLigneLameVerre = sousLigne.getLames() != null && !sousLigne.getLames().isEmpty() ? sousLigne.getLames().get(0) : null;
                lammeVerreId = (sousLigneLameVerre != null) ? sousLigneLameVerre.getLameVerre().getLameVerreId() : null;
               vitrageId = (sousLigneLameVerre != null) ? sousLigneLameVerre.getLameVerre().getVitrage().getVitrageId() : -1;  
          
               quantiteLammeVerre = (sousLigneLameVerre != null) ? sousLigneLameVerre.getQuantiteUtilisee() : null;
              prixLammeVerre = (sousLigneLameVerre != null) ? sousLigneLameVerre.getPrixTotal() : null;
                // Ajouter les opérateurs
                ModelSousLigneOperateur sousLigneOperateur = sousLigne.getOperateurs() != null && !sousLigne.getOperateurs().isEmpty() ? sousLigne.getOperateurs().get(0) : null;
                operateurId = (sousLigneOperateur != null) ? sousLigneOperateur.getOperateur().getOperateurId() : null;
               nbOperateurs = (sousLigneOperateur != null) ? sousLigneOperateur.getQuantiteUtilisee() : null;
                prixOperateur = (sousLigneOperateur != null) ? sousLigneOperateur.getPrixTotal() : null;

                // Ajouter les rivettes
                ModelSousLigneRivette sousLigneRivette = sousLigne.getRivettes() != null && !sousLigne.getRivettes().isEmpty() ? sousLigne.getRivettes().get(0) : null;
                rivetteId = (sousLigneRivette != null) ? sousLigneRivette.getRivette().getRivetteId() : null;
                nbRivettes = (sousLigneRivette != null) ? sousLigneRivette.getQuantiteUtilisee() : null;
               prixRivette = (sousLigneRivette != null) ? sousLigneRivette.getPrixTotal() : null;
                    
            // Ajouter les clips
                ModelSousLigneClips sousLigneClips = sousLigne.getClips() != null && !sousLigne.getClips().isEmpty() ? sousLigne.getClips().get(0) : null;
                clipsId = (sousLigneClips != null) ? sousLigneClips.getClips().getClipsId() : null;
                nbClips = (sousLigneClips != null) ? sousLigneClips.getQuantiteUtilisee() : null;
                prixClips = (sousLigneClips != null) ? sousLigneClips.getPrixTotal() : null;
                }
                    // Récupérer les paumelles (si présentes)
                    ModelSousLignePaumelle sousLignePaumelle = sousLigne.getPaumelles() != null && !sousLigne.getPaumelles().isEmpty() ? sousLigne.getPaumelles().get(0) : null;
                    Integer paumelleId = (sousLignePaumelle != null) ? sousLignePaumelle.getPaumelle().getPaumelleId() : null;
                    Integer nbpaumelle =(sousLignePaumelle != null) ? sousLignePaumelle.getQuantiteUtilisee() : null;
                  BigDecimal prixPaumelle = (sousLignePaumelle != null) ? sousLignePaumelle.getPrixTotal(): null;
                    // Récupérer les roulettes (si présentes)
                    ModelSousLigneRoulette sousLigneRoulette = sousLigne.getRoulettes() != null && !sousLigne.getRoulettes().isEmpty() ? sousLigne.getRoulettes().get(0) : null;
                    Integer rouletteId = (sousLigneRoulette != null) ? sousLigneRoulette.getRoulette().getRouletteId() : null;
                    Integer nbroulette =(sousLigneRoulette != null) ? sousLigneRoulette.getQuantiteUtilisee() : null;
                      BigDecimal prixRoulette = (sousLigneRoulette != null) ? sousLigneRoulette.getPrixTotal(): null;
                    // Récupérer la serrure (si présente)     
                    ModelSousLignePoignee sousLignePoignee = sousLigne.getPoignees() != null && !sousLigne.getPoignees().isEmpty() ? sousLigne.getPoignees().get(0) : null;
                    Integer poigneeId = (sousLignePoignee!= null) ? sousLignePoignee.getPoignee().getPoigneeId() : null;
                     Integer nbpoignee = (sousLignePoignee!= null) ? sousLignePoignee.getQuantiteUtilisee() : null;
                     BigDecimal prixPoignee= (sousLignePoignee != null) ? sousLignePoignee.getPrixTotal(): null;

                    // Récupérer la vis (si présente)
                    ModelSousLigneVis sousLigneVis = sousLigne.getVis() != null && !sousLigne.getVis().isEmpty() ? sousLigne.getVis().get(0) : null;
                    int visId = (sousLigneVis != null) ? sousLigneVis.getVis().getVisId() : -1; // Valeur par défaut si null
                    Integer nbvis = (sousLigneVis!= null) ? sousLigneVis.getQuantiteUtilisee() : null;
                     BigDecimal prixVis= (sousLigneVis != null) ? sousLigneVis.getPrixTotal(): null;
                    // Récupérer le joint (si présent)
                    ModelSousLigneJoint sousLigneJoint = sousLigne.getJoints() != null && !sousLigne.getJoints().isEmpty() ? sousLigne.getJoints().get(0) : null;
                    int jointId = (sousLigneJoint != null) ? sousLigneJoint.getJoint().getJointId() : -1; // Valeur par défaut si null
                  BigDecimal longueurJoint = (sousLigneJoint!= null) ? sousLigneJoint.getQuantiteUtilisee() : null;
                    // Créer la chaîne de dimensions pour la sous-ligne
                     BigDecimal prixJoint= (sousLigneJoint != null) ? sousLigneJoint.getPrixTotal(): null;
                    String dimensions = sousLigne.getLargeur() + " mm (Lt) x " + sousLigne.getHauteur() + " mm (Ht)";


                
                    // Ajouter la sous-ligne juste après la ligne principale
                    addSubRow(
                    currentMainRowIndex,    // Index de la ligne principale
                    chassisTypeFixeId,      // Type de châssis fixe
                    chassisTypeOuvrantId,   // Type de châssis ouvrant
                    sousLigne.getnbVentaux(), // Nombre de ventaux
                    vitrageId,              // Type de vitrage
                    typeVitreId,            // Type de vitre
                    structureId,            // Structure
                    paumelleId,             // Paumelle
                    rouletteId,             // Roulette
                    serrureId,              // Serrure
                    poigneeId,
                    visId,                  // Vis
                    jointId,                // Joint
                    dimensions,             // Dimensions calculées
                    sousLigne.getPrixUnitaire(), // Prix unitaire
                    sousLigne.getQuantite(),  // Quantité
                            nbroulette,nbpaumelle,nbpoignee,nbvis,nbserrure,longueurJoint,qteChassisFixe,qteChassisOuvrant,prixChassisFixe,prixChassisOuvrant,
                            prixVitre,prixJoint,prixRoulette,prixPaumelle,prixSerrure,prixPoignee,prixVis,clipsId, nbClips, prixClips,
                    lammeVerreId, quantiteLammeVerre, prixLammeVerre,
                    operateurId, nbOperateurs, prixOperateur,
                    rivetteId, nbRivettes, prixRivette
                );
            }
        }

        // Mettre à jour les totaux pour la ligne principale (si nécessaire)
        updateMainRowTotals(currentMainRowIndex);
    }
}

}
}

