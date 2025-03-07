package ConnexionDB;

import Models.ModelChassis;
import Models.ModelUser;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import Models.ModelClient;
import Models.ModelClips;
import Models.ModelCommande;
import Models.ModelDevis;
import Models.ModelFacture;
import Models.ModelFournisseur;
import Models.ModelJoint;
import Models.ModelLameVerre;
import Models.ModelLigneCommande;
import Models.ModelLigneDevis;
import Models.ModelLigneFacture;
import Models.ModelOperateur;
import Models.ModelPaiementAcompte;
import Models.ModelPaiementFacture;
import Models.ModelPaumelle;
import Models.ModelPoignee;
import Models.ModelProduit;
import Models.ModelProfileAlu;
import Models.ModelRivette;
import Models.ModelRoulette;
import Models.ModelSerrure;
import Models.ModelSousLigneChassis;
import Models.ModelSousLigneClips;
import Models.ModelSousLigneCommande;
import Models.ModelSousLigneDevis;
import Models.ModelSousLigneFacture;
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
import Models.ModelStatut.ModelStatutCommande;
import Models.ModelStatut.ModelStatutDevis;
import Models.ModelStatut.ModelStatutFacture;
import Models.ModelStatut.ModelStatutStock;
import Models.ModelStructure;
import Models.ModelTypeProfileAlu;

import Models.ModelTypeVitre;
import Models.ModelVis;
import Models.ModelVitrage;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Statement;
import java.sql.Date;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DatabaseUtils {

    private static final String URL = "jdbc:mysql://localhost:3306/kintana_service_alu";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
// private static String URL;
//    private static String USER;
//    private static String PASSWORD;
//
//    static {
//        try (FileInputStream input = new FileInputStream(System.getProperty("user.dir") + "/resources/config.properties")) {
//            Properties properties = new Properties();
//            properties.load(input);
//
//            URL = properties.getProperty("db.url");
//            USER = properties.getProperty("db.user");
//            PASSWORD = properties.getProperty("db.password");
//        } catch (IOException e) {
//            Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, "Error loading database config", e);
//        }
//    }
//
//    public static Connection getConnection() throws SQLException {
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException ex) {
//            Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return DriverManager.getConnection(URL, USER, PASSWORD);
//    }
    public static ModelUser validateLogin(String email, String password) {
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setString(2, hashPassword(password));
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int userID = rs.getInt("user_id");
                String username = rs.getString("username");
                String userEmail = rs.getString("email");
                String userPassword = rs.getString("password");
                return new ModelUser(userID, username, userEmail, userPassword);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public static String registerUser(ModelUser user) {
        try (Connection conn = getConnection()) {
            // Check if username already exists
            String checkUsernameSql = "SELECT * FROM users WHERE username = ?";
            PreparedStatement checkUsernameStmt = conn.prepareStatement(checkUsernameSql);
            checkUsernameStmt.setString(1, user.getUsername());
            ResultSet rsUsername = checkUsernameStmt.executeQuery();
            if (rsUsername.next()) {
                return "USERNAME_EXISTS";
            }

            // Check if email already exists
            String checkEmailSql = "SELECT * FROM users WHERE email = ?";
            PreparedStatement checkEmailStmt = conn.prepareStatement(checkEmailSql);
            checkEmailStmt.setString(1, user.getEmail());
            ResultSet rsEmail = checkEmailStmt.executeQuery();
            if (rsEmail.next()) {
                return "EMAIL_EXISTS";
            }

            // If both username and email are unique, proceed with registration
            String sql = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, hashPassword(user.getPassword())); // Hash the password
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0 ? "SUCCESS" : "ERROR";
        } catch (Exception e) {
            return "ERROR";
        }
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();
            for (byte hashByte : hashedPassword) {
                String hex = Integer.toHexString(0xff & hashByte);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    // Méthode pour ajouter un client à la base de données
    // Updated addClient method with duplicate check
public static String addClient(ModelClient client) {
    // Vérifier si le client existe déjà sans tenir compte de l'ID (puisque c'est un nouveau client)
    if (isClientDuplicateNom(client.getNom(), -1)) {
        return "DUPLICATE_NOM";
    }
    if (isClientDuplicateTelephone(client.getTelephone(), -1)) {
        return "DUPLICATE_TELEPHONE";
    }
    if (isClientDuplicateEmail(client.getEmail(), -1)) {
        return "DUPLICATE_EMAIL";
    }

    int nextClientId = getNextClientId();
    try (Connection conn = getConnection()) {
        String sql = "INSERT INTO clients (client_id, nom, adresse, telephone, email,date_creation) VALUES (?, ?, ?, ?, ?,?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, nextClientId);
        pstmt.setString(2, client.getNom());
        pstmt.setString(3, client.getAdresse().isEmpty() ? null : client.getAdresse());
        pstmt.setString(4, client.getTelephone());
        pstmt.setString(5, client.getEmail());
        pstmt.setDate(6, new java.sql.Date(client.getDateCreation().getTime()));
        
        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "ERROR";
    } catch (Exception e) {
        return "ERROR";
    }
}



public static int getNextVitreId() {
    try (Connection conn = getConnection()) {
        String sql = "SELECT COUNT(*) FROM type_vitre";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        if (result.next()) {
            int count = result.getInt(1);
            if (count == 0) {
                return 1; // Return 1 if the table is empty
            } else {
                sql = "SELECT MAX(type_vitre_id) FROM type_vitre";
                stmt = conn.prepareStatement(sql);
                result = stmt.executeQuery();
                if (result.next()) {
                    return result.getInt(1) + 1;
                } else {
                    return 1;
                }
            }
        } else {
            return 1;
        }
    } catch (Exception e) {
        return 1;
    }
}
public static int getNextJointId() {
    try (Connection conn = getConnection()) {
        String sql = "SELECT COUNT(*) FROM joint";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        if (result.next()) {
            int count = result.getInt(1);
            if (count == 0) {
                return 1; // Return 1 if the table is empty
            } else {
                sql = "SELECT MAX(joint_id) FROM joint";
                stmt = conn.prepareStatement(sql);
                result = stmt.executeQuery();
                if (result.next()) {
                    return result.getInt(1) + 1;
                } else {
                    return 1;
                }
            }
        } else {
            return 1;
        }
    } catch (Exception e) {
        return 1;
    }
}
public static int getNextPaumelleId() {
    try (Connection conn = getConnection()) {
        String sql = "SELECT COUNT(*) FROM paumelle";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        if (result.next()) {
            int count = result.getInt(1);
            if (count == 0) {
                return 1; // Return 1 if the table is empty
            } else {
                sql = "SELECT MAX(paumelle_id) FROM paumelle";
                stmt = conn.prepareStatement(sql);
                result = stmt.executeQuery();
                if (result.next()) {
                    return result.getInt(1) + 1;
                } else {
                    return 1;
                }
            }
        } else {
            return 1;
        }
    } catch (Exception e) {
        return 1;
    }
}
public static int getNextVisId() {
    try (Connection conn = getConnection()) {
        String sql = "SELECT COUNT(*) FROM vis";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        if (result.next()) {
            int count = result.getInt(1);
            if (count == 0) {
                return 1; // Return 1 if the table is empty
            } else {
                sql = "SELECT MAX(vis_id) FROM vis";
                stmt = conn.prepareStatement(sql);
                result = stmt.executeQuery();
                if (result.next()) {
                    return result.getInt(1) + 1;
                } else {
                    return 1;
                }
            }
        } else {
            return 1;
        }
    } catch (Exception e) {
        return 1;
    }
}
public static int getNextRouletteId() {
    try (Connection conn = getConnection()) {
        String sql = "SELECT COUNT(*) FROM roulette";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        if (result.next()) {
            int count = result.getInt(1);
            if (count == 0) {
                return 1; // Return 1 if the table is empty
            } else {
                sql = "SELECT MAX(roulette_id) FROM roulette";
                stmt = conn.prepareStatement(sql);
                result = stmt.executeQuery();
                if (result.next()) {
                    return result.getInt(1) + 1;
                } else {
                    return 1;
                }
            }
        } else {
            return 1;
        }
    } catch (Exception e) {
        return 1;
    }
}
public static int getNextPoigneeId() {
    try (Connection conn = getConnection()) {
        String sql = "SELECT COUNT(*) FROM poignee";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        if (result.next()) {
            int count = result.getInt(1);
            if (count == 0) {
                return 1; // Return 1 if the table is empty
            } else {
                sql = "SELECT MAX(poignee_id) FROM poignee";
                stmt = conn.prepareStatement(sql);
                result = stmt.executeQuery();
                if (result.next()) {
                    return result.getInt(1) + 1;
                } else {
                    return 1;
                }
            }
        } else {
            return 1;
        }
    } catch (Exception e) {
        return 1;
    }
}
public static int getNextSerrureId() {
    try (Connection conn = getConnection()) {
        String sql = "SELECT COUNT(*) FROM serrure";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        if (result.next()) {
            int count = result.getInt(1);
            if (count == 0) {
                return 1; // Return 1 if the table is empty
            } else {
                sql = "SELECT MAX(serrure_id) FROM serrure";
                stmt = conn.prepareStatement(sql);
                result = stmt.executeQuery();
                if (result.next()) {
                    return result.getInt(1) + 1;
                } else {
                    return 1;
                }
            }
        } else {
            return 1;
        }
    } catch (Exception e) {
        return 1;
    }
}
public static int getNextClientId() {
    try (Connection conn = getConnection()) {
        String sql = "SELECT COUNT(*) FROM clients";
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet result = stmt.executeQuery();
        if (result.next()) {
            int count = result.getInt(1);
            if (count == 0) {
                return 1; // Return 1 if the table is empty
            } else {
                sql = "SELECT MAX(client_id) FROM clients";
                stmt = conn.prepareStatement(sql);
                result = stmt.executeQuery();
                if (result.next()) {
                    return result.getInt(1) + 1;
                } else {
                    return 1;
                }
            }
        } else {
            return 1;
        }
    } catch (Exception e) {
        return 1;
    }
}

    public static boolean isClientDuplicateNom(String nom, int currentClientId) {
        try (Connection conn = getConnection()) {
            String sql = "SELECT COUNT(*) FROM clients WHERE nom =? AND client_id!=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.setInt(2, currentClientId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
        }
        return false;
    }

public static boolean isClientDuplicateTelephone(String telephone, int currentClientId) {
    if (telephone == null || telephone.trim().isEmpty()) {
        return false; // champ vide, pas de duplication
    }
    try (Connection conn = getConnection()) {
        String sql = "SELECT COUNT(*) FROM clients WHERE telephone =? AND client_id!=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, telephone);
        stmt.setInt(2, currentClientId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0;
        }
    } catch (Exception e) {
    }
    return false;
}

public static boolean isClientDuplicateEmail(String email, int currentClientId) {
    if (email == null || email.trim().isEmpty()) {
        return false; // champ vide, pas de duplication
    }
    try (Connection conn = getConnection()) {
        String sql = "SELECT COUNT(*) FROM clients WHERE email =? AND client_id!=?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, email);
        stmt.setInt(2, currentClientId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            int count = rs.getInt(1);
            return count > 0;
        }
    } catch (Exception e) {
    }
    return false;
}

public static String updateClient(ModelClient client) {
    try (Connection conn = getConnection()) {
        String sql = "UPDATE clients SET nom = ?, adresse = ?, telephone = ?, email = ? WHERE client_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, client.getNom());
        pstmt.setString(2, client.getAdresse());
        pstmt.setString(3, client.getTelephone());
        pstmt.setString(4, client.getEmail());
        
        pstmt.setInt(5, client.getClientId());
        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "ERROR";
    } catch (Exception e) {
        return "ERROR";
    }
}

    // Méthode pour obtenir tous les clients de la base de données
    public static List<ModelClient> getAllClients() {
        List<ModelClient> clients = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM clients";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                clients.add(new ModelClient(
                        rs.getInt("client_id"),
                        rs.getString("nom"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getString("email"),
                         rs.getDate("date_creation")
                ));
            }
        } catch (Exception e) {
        }
        return clients;
    }
        // Méthode pour supprimer un client de la base de données
 
public static void deleteClient(int clientId) throws SQLException, Exception {
    Connection conn = getConnection();
    try {
        // Start a transaction
        conn.setAutoCommit(false);
        
        // Delete related devis entries
        String deleteDevisSQL = "DELETE FROM devis WHERE client_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(deleteDevisSQL)) {
            ps.setInt(1, clientId);
            ps.executeUpdate();
        }
        
        // Delete the client
        String deleteClientSQL = "DELETE FROM clients WHERE client_id = ?";
        try (PreparedStatement ps = conn.prepareStatement(deleteClientSQL)) {
            ps.setInt(1, clientId);
            ps.executeUpdate();
        }
        
        // Commit the transaction
        conn.commit();
    } catch (SQLException e) {
        // Rollback the transaction in case of an error
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
            }
        }
        throw e;
    } finally {
        // Restore the auto-commit mode
        if (conn != null) {
            conn.setAutoCommit(true);
            conn.close();
        }
    }
}

 // Méthode pour ajouter un fournisseur
    public static String addFournisseur(ModelFournisseur fournisseur) {
        // Vérifier si le fournisseur existe déjà sans tenir compte de l'ID (puisque c'est un nouveau fournisseur)
        if (isFournisseurDuplicateNom(fournisseur.getNom(), -1)) {
            return "DUPLICATE_NOM";
        }
        if (isFournisseurDuplicateTelephone(fournisseur.getTelephone(), -1)) {
            return "DUPLICATE_TELEPHONE";
        }
        if (isFournisseurDuplicateEmail(fournisseur.getEmail(), -1)) {
            return "DUPLICATE_EMAIL";
        }

        int nextFournisseurId = getNextFournisseurId();
        try (Connection conn = getConnection()) {
            String sql = "INSERT INTO fournisseur (fournisseur_id, nom, adresse, telephone, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, nextFournisseurId);
            pstmt.setString(2, fournisseur.getNom());
            pstmt.setString(3, fournisseur.getAdresse().isEmpty() ? null : fournisseur.getAdresse());
            pstmt.setString(4, fournisseur.getTelephone());
            pstmt.setString(5, fournisseur.getEmail());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0 ? "SUCCESS" : "ERROR";
        } catch (Exception e) {
            return "ERROR";
        }
    }

    // Méthode pour obtenir l'ID du prochain fournisseur
    public static int getNextFournisseurId() {
        try (Connection conn = getConnection()) {
            String sql = "SELECT COUNT(*) FROM fournisseur";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet result = stmt.executeQuery();
            if (result.next()) {
                int count = result.getInt(1);
                if (count == 0) {
                    return 1; // Return 1 si la table est vide
                } else {
                    sql = "SELECT MAX(fournisseur_id) FROM fournisseur";
                    stmt = conn.prepareStatement(sql);
                    result = stmt.executeQuery();
                    if (result.next()) {
                        return result.getInt(1) + 1;
                    } else {
                        return 1;
                    }
                }
            } else {
                return 1;
            }
        } catch (Exception e) {
            return 1;
        }
    }

    // Vérifier si le nom du fournisseur existe déjà
    public static boolean isFournisseurDuplicateNom(String nom, int currentFournisseurId) {
        try (Connection conn = getConnection()) {
            String sql = "SELECT COUNT(*) FROM fournisseur WHERE nom =? AND fournisseur_id!=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nom);
            stmt.setInt(2, currentFournisseurId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
        }
        return false;
    }

    // Vérifier si le téléphone du fournisseur existe déjà
    public static boolean isFournisseurDuplicateTelephone(String telephone, int currentFournisseurId) {
        if (telephone == null || telephone.trim().isEmpty()) {
            return false; // champ vide, pas de duplication
        }
        try (Connection conn = getConnection()) {
            String sql = "SELECT COUNT(*) FROM fournisseur WHERE telephone =? AND fournisseur_id!=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, telephone);
            stmt.setInt(2, currentFournisseurId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
        }
        return false;
    }

    // Vérifier si l'email du fournisseur existe déjà
    public static boolean isFournisseurDuplicateEmail(String email, int currentFournisseurId) {
        if (email == null || email.trim().isEmpty()) {
            return false; // champ vide, pas de duplication
        }
        try (Connection conn = getConnection()) {
            String sql = "SELECT COUNT(*) FROM fournisseur WHERE email =? AND fournisseur_id!=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            stmt.setInt(2, currentFournisseurId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0;
            }
        } catch (Exception e) {
        }
        return false;
    }

    // Méthode pour mettre à jour un fournisseur
    public static String updateFournisseur(ModelFournisseur fournisseur) {
        try (Connection conn = getConnection()) {
            String sql = "UPDATE fournisseur SET nom = ?, adresse = ?, telephone = ?, email = ? WHERE fournisseur_id = ?";
            PreparedStatement pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, fournisseur.getNom());
            pstmt.setString(2, fournisseur.getAdresse());
            pstmt.setString(3, fournisseur.getTelephone());
            pstmt.setString(4, fournisseur.getEmail());
            pstmt.setInt(5, fournisseur.getFournisseurId());
            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0 ? "SUCCESS" : "ERROR";
        } catch (Exception e) {
           
    e.printStackTrace(); // Affiche les détails de l'erreur dans la console


            return "ERROR";
        }
    }

    // Méthode pour obtenir tous les fournisseur de la base de données
    public static List<ModelFournisseur> getAllFournisseur() {
        List<ModelFournisseur> fournisseur = new ArrayList<>();
        try (Connection conn = getConnection()) {
            String sql = "SELECT * FROM fournisseur";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                fournisseur.add(new ModelFournisseur(
                        rs.getInt("fournisseur_id"),
                        rs.getString("nom"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getString("email")
                ));
            }
        } catch (Exception e) {
        }
        return fournisseur;
    }

    // Méthode pour supprimer un fournisseur de la base de données
    public static void deleteFournisseur(int fournisseurId) throws SQLException, Exception {
        Connection conn = getConnection();
        try {
            // Start a transaction
            conn.setAutoCommit(false);
            
            // Delete related entries if necessary
            // (ajouter ici si vous avez des relations avec d'autres tables)

            // Delete the fournisseur
            String deleteFournisseurQL = "DELETE FROM fournisseur WHERE fournisseur_id = ?";
            try (PreparedStatement ps = conn.prepareStatement(deleteFournisseurQL)) {
                ps.setInt(1, fournisseurId);
                ps.executeUpdate();
            }
            
            // Commit the transaction
            conn.commit();
        } catch (SQLException e) {
            // Rollback the transaction in case of an error
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                }
            }
            throw e;
        } finally {
            // Restore the auto-commit mode
            if (conn != null) {
                conn.setAutoCommit(true);
                conn.close();
            }
        }
    }

  public static void deleteDevisByClientId(int clientId) throws SQLException, Exception {
    String sql = "DELETE FROM devis WHERE client_id = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, clientId);
        stmt.executeUpdate();
    }
}
  

 // Méthode pour récupérer un client par son nom
    public static ModelClient getClientByName(String clientName) {
        try (Connection conn = getConnection()) {
            // Requête SQL pour sélectionner un client par son nom
            String sql = "SELECT * FROM clients WHERE nom =?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, clientName);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Création d'un objet ModelClient à partir des données de la base de données
                return new ModelClient(
                        rs.getInt("client_id"),
                        rs.getString("nom"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getString("email"),
                         rs.getDate("date_creation")
                );
            }
        } catch (Exception e) {
        }
        return null;
    }

    // Méthode pour récupérer un client par son ID
    public static ModelClient getClientById(int clientId) {
        try (Connection conn = getConnection()) {
            // Requête SQL pour sélectionner un client par son ID
            String sql = "SELECT * FROM clients WHERE client_id =?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, clientId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Création d'un objet ModelClient à partir des données de la base de données
                return new ModelClient(
                        rs.getInt("client_id"),
                        rs.getString("nom"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getString("email"),
                         rs.getDate("date_creation")
                );
            }
        } catch (Exception e) {
        }
        return null;
    }

       // Méthode pour récupérer un client par son ID
    public static ModelFournisseur getFournisseurById(int fournisseurId) {
        try (Connection conn = getConnection()) {
            // Requête SQL pour sélectionner un client par son ID
            String sql = "SELECT * FROM fournisseur WHERE fournisseur_id =?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, fournisseurId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Création d'un objet ModelClient à partir des données de la base de données
                return new ModelFournisseur(
                        rs.getInt("fournisseur_id"),
                        rs.getString("nom"),
                        rs.getString("adresse"),
                        rs.getString("telephone"),
                        rs.getString("email")
                );
            }
        } catch (Exception e) {
        }
        return null;
    }

 

public static ModelStatutDevis getStatutDevisById(int statutId) {
    ModelStatutDevis statutDevis = null;
    try (Connection conn = getConnection()) {
        String sqlStatut = "SELECT * FROM statut_devis WHERE statut_devis_id = ?";
        PreparedStatement stmtStatut = conn.prepareStatement(sqlStatut);
        stmtStatut.setInt(1, statutId);
        ResultSet rsStatut = stmtStatut.executeQuery();

        if (rsStatut.next()) {
            int id = rsStatut.getInt("statut_devis_id");
            String statut = rsStatut.getString("statut");
            statutDevis = new ModelStatutDevis(id, statut);
        }
    } catch (Exception e) {
        // Handle exception
        e.printStackTrace(); // or log the exception
    }
    return statutDevis;
}
public static ModelStatutStock getStatutStockById(int statutId) {
    ModelStatutStock statutStock = null;
    try (Connection conn = getConnection()) {
        String sqlStatut = "SELECT * FROM statut_stock WHERE statut_stock_id = ?";
        PreparedStatement stmtStatut = conn.prepareStatement(sqlStatut);
        stmtStatut.setInt(1, statutId);
        ResultSet rsStatut = stmtStatut.executeQuery();

        if (rsStatut.next()) {
            int id = rsStatut.getInt("statut_stock_id");
            String statut = rsStatut.getString("statut");
            statutStock = new ModelStatutStock(id, statut);
        }
    } catch (Exception e) {
        // Handle exception
        e.printStackTrace(); // or log the exception
    }
    return statutStock;
}


   // Méthode pour récupérer tous les statuts
 // Méthode pour récupérer tous les statuts des devis
public static List<ModelStatutDevis> getAllStatutsDevis() throws Exception {
    List<ModelStatutDevis> statuts = new ArrayList<>();
    String query = "SELECT statut_devis_id, statut FROM statut_devis"; // Vérifier que statut est bien le nom de la colonne
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("statut_devis_id");
            String statut = rs.getString("statut");
            statuts.add(new ModelStatutDevis(id, statut));
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Log the exception
    }
    return statuts;
}
public static List<ModelStatutStock> getAllStatutsStock() throws Exception {
    List<ModelStatutStock> statuts = new ArrayList<>();
    String query = "SELECT * FROM statut_stock"; // Vérifier que statut est bien le nom de la colonne
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            int id = rs.getInt("statut_stock_id");
            String statut = rs.getString("statut");
            statuts.add(new ModelStatutStock(id, statut));
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Log the exception
    }
    return statuts;
}

public static void updateStatutCommande(int commandeId, int newStatutId) throws SQLException {
    String sql = "UPDATE commande SET statut_commande_id = ? WHERE commande_id = ?";

    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        // Paramètres pour la requête SQL
        pstmt.setInt(1, newStatutId);
        pstmt.setInt(2, commandeId);

        // Exécuter la mise à jour
        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Statut mis à jour avec succès pour la commande ID : " + commandeId);
        } else {
            System.out.println("Aucune commande trouvée avec l'ID : " + commandeId);
        }
    } catch (SQLException ex) {
        System.err.println("Erreur lors de la mise à jour du statut de la commande ID : " + commandeId);
        throw ex; // Relancer l'exception pour la gestion en amont
    }
}
public static void updateStatutFacture(int factureId, int newStatutId) throws SQLException {
    String sql = "UPDATE facture SET statut_facture_id = ? WHERE facture_id = ?";

    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        // Paramètres pour la requête SQL
        pstmt.setInt(1, newStatutId);
        pstmt.setInt(2, factureId);

        // Exécuter la mise à jour
        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            System.out.println("Statut mis à jour avec succès pour la facture ID : " + factureId);
        } else {
            System.out.println("Aucune facture trouvée avec l'ID : " + factureId);
        }
    } catch (SQLException ex) {
        System.err.println("Erreur lors de la mise à jour du statut de la facture ID : " + factureId);
        throw ex; // Relancer l'exception pour la gestion en amont
    }
}

public static String getStatutNameByDevisId(int devisId) throws Exception {
    // Récupérer le devis depuis la base de données
    ModelDevis devis = DatabaseUtils.getDevisById(devisId);
    if (devis != null) {
        // Obtenir l'ID du statut depuis le devis
        int statutId = devis.getStatut().getStatutId();

        // Récupérer tous les statuts disponibles
        List<ModelStatutDevis> statuts = getAllStatutsDevis();

        // Parcourir les statuts pour trouver celui qui correspond à l'ID du statut
        for (ModelStatutDevis statut : statuts) {
            if (statut.getStatutId() == statutId) {
                // Retourner le nom du statut (libellé)
                return statut.getStatut();
            }
        }
    }
    return null; // Si le statut n'est pas trouvé
}

public static int getStatutIdByName(String statutName) throws Exception {
    List<ModelStatutDevis> statuts = getAllStatutsDevis();
    for (ModelStatutDevis statut : statuts) {
        if (statut.getStatut().equals(statutName)) {
            return statut.getStatutId();
        }
    }
    return -1; // Si le statut n'est pas trouvé
}
public static int getNextDevisId() {
    try (Connection conn = getConnection()) {
        String sqlMaxId = "SELECT MAX(devis_id) FROM devis";
        PreparedStatement stmtMaxId = conn.prepareStatement(sqlMaxId);
        ResultSet rsMaxId = stmtMaxId.executeQuery();

        if (rsMaxId.next()) {
            int maxId = rsMaxId.getInt(1);
            return (rsMaxId.wasNull()) ? 1 : maxId + 1;
        } else {
            return 1;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return 1; // En cas d'erreur, retournez 1 comme valeur par défaut
    }
}

public static int getNextLigneDevisId() throws SQLException {
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(ligne_devis_id) FROM ligne_devis");
         ResultSet rs = pstmt.executeQuery()) {

        if (rs.next()) {
            int maxId = rs.getInt(1);
            return (rs.wasNull()) ? 1 : maxId + 1;
        } else {
            return 1;
        }
    }
}

public static int getNextSousLigneDevisId() throws SQLException {
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement("SELECT MAX(sousligne_devis_id) FROM sous_ligne_devis");
         ResultSet rs = pstmt.executeQuery()) {

        if (rs.next()) {
            int maxId = rs.getInt(1);
            return (rs.wasNull()) ? 1 : maxId + 1;
        } else {
            return 1;
        }
    }
}
public static ModelProduit getProduitById(int produitId) throws SQLException {
    String sqlProduit = "SELECT * FROM produit WHERE produit_id = ?";
    
    try (Connection conn = getConnection(); 
         PreparedStatement pstmtProduit = conn.prepareStatement(sqlProduit)) {
        
        // Paramétrer la requête
        pstmtProduit.setInt(1, produitId);
        
        // Exécuter la requête et traiter le résultat
        try (ResultSet rsProduit = pstmtProduit.executeQuery()) {
            if (rsProduit.next()) {
                // Créer et retourner un objet ModelProduit
                return new ModelProduit(
                    rsProduit.getInt("produit_id"),
                    rsProduit.getInt("type_produit_id"),
                    rsProduit.getString("nom")
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e; // Relancer l'exception ou gérer l'erreur de manière appropriée
    }
    
    return null; // Aucun produit trouvé avec l'ID donné
}

    


public static int getTypeProduitId(int produitId) {
    int typeProduitId = -1; // Valeur par défaut si le type n'est pas trouvé
    String query = "SELECT type_produit_id FROM produit WHERE produit_id = ?";
    
    try (Connection conn = DatabaseUtils.getConnection(); 
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        stmt.setInt(1, produitId);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            typeProduitId = rs.getInt("type_produit_id");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    
    return typeProduitId;
}

public static int getNextCommandeId() {
    try (Connection conn = getConnection()) {
        String sqlMaxId = "SELECT MAX(commande_id) FROM commande";
        PreparedStatement stmtMaxId = conn.prepareStatement(sqlMaxId);
        ResultSet rsMaxId = stmtMaxId.executeQuery();

        if (rsMaxId.next()) {
            int maxId = rsMaxId.getInt(1);
            return (rsMaxId.wasNull()) ? 1 : maxId + 1;
        } else {
            return 1;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return 1; // En cas d'erreur, retournez 1 comme valeur par défaut
    }
}
public static int getNextFactureId() {
    try (Connection conn = getConnection()) {
        String sqlMaxId = "SELECT MAX(facture_id) FROM facture";
        PreparedStatement stmtMaxId = conn.prepareStatement(sqlMaxId);
        ResultSet rsMaxId = stmtMaxId.executeQuery();

        if (rsMaxId.next()) {
            int maxId = rsMaxId.getInt(1);
            return (rsMaxId.wasNull()) ? 1 : maxId + 1;
        } else {
            return 1;
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return 1; // En cas d'erreur, retournez 1 comme valeur par défaut
    }
}
public static void resetAutoIncrementIfEmptyFacture(Connection conn) throws SQLException {
    // Vérifier et réinitialiser pour la table commande
    String sqlCheckCommande = "SELECT COUNT(*) AS count FROM facture";
    try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckCommande);
         ResultSet rs = pstmt.executeQuery()) {
        if (rs.next() && rs.getInt("count") == 0) {
            String sqlResetCommande = "ALTER TABLE facture AUTO_INCREMENT = 1";
            try (PreparedStatement pstmtReset = conn.prepareStatement(sqlResetCommande)) {
                pstmtReset.execute();
            }
        }
    }

    // Vérifier et réinitialiser pour la table ligne_commande
    String sqlCheckLigneCommande = "SELECT COUNT(*) AS count FROM ligne_facture";
    try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckLigneCommande);
         ResultSet rs = pstmt.executeQuery()) {
        if (rs.next() && rs.getInt("count") == 0) {
            String sqlResetLigneCommande = "ALTER TABLE ligne_facture AUTO_INCREMENT = 1";
            try (PreparedStatement pstmtReset = conn.prepareStatement(sqlResetLigneCommande)) {
                pstmtReset.execute();
            }
        }
    }

    // Vérifier et réinitialiser pour la table sous_ligne_commande
    String sqlCheckSousLigneCommande = "SELECT COUNT(*) AS count FROM sous_ligne_facture";
    try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckSousLigneCommande);
         ResultSet rs = pstmt.executeQuery()) {
        if (rs.next() && rs.getInt("count") == 0) {
            String sqlResetSousLigneCommande = "ALTER TABLE sous_ligne_facture AUTO_INCREMENT = 1";
            try (PreparedStatement pstmtReset = conn.prepareStatement(sqlResetSousLigneCommande)) {
                pstmtReset.execute();
            }
        }
    }
}

public static void resetAutoIncrementIfEmptyCommande(Connection conn) throws SQLException {
    // Vérifier et réinitialiser pour la table commande
    String sqlCheckCommande = "SELECT COUNT(*) AS count FROM commande";
    try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckCommande);
         ResultSet rs = pstmt.executeQuery()) {
        if (rs.next() && rs.getInt("count") == 0) {
            String sqlResetCommande = "ALTER TABLE commande AUTO_INCREMENT = 1";
            try (PreparedStatement pstmtReset = conn.prepareStatement(sqlResetCommande)) {
                pstmtReset.execute();
            }
        }
    }

    // Vérifier et réinitialiser pour la table ligne_commande
    String sqlCheckLigneCommande = "SELECT COUNT(*) AS count FROM ligne_commande";
    try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckLigneCommande);
         ResultSet rs = pstmt.executeQuery()) {
        if (rs.next() && rs.getInt("count") == 0) {
            String sqlResetLigneCommande = "ALTER TABLE ligne_commande AUTO_INCREMENT = 1";
            try (PreparedStatement pstmtReset = conn.prepareStatement(sqlResetLigneCommande)) {
                pstmtReset.execute();
            }
        }
    }

    // Vérifier et réinitialiser pour la table sous_ligne_commande
    String sqlCheckSousLigneCommande = "SELECT COUNT(*) AS count FROM sous_ligne_commande";
    try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckSousLigneCommande);
         ResultSet rs = pstmt.executeQuery()) {
        if (rs.next() && rs.getInt("count") == 0) {
            String sqlResetSousLigneCommande = "ALTER TABLE sous_ligne_commande AUTO_INCREMENT = 1";
            try (PreparedStatement pstmtReset = conn.prepareStatement(sqlResetSousLigneCommande)) {
                pstmtReset.execute();
            }
        }
    }
}

 // Method to get all facture payments
    public static List<ModelPaiementFacture> getAllFacturePaiements() throws SQLException {
        List<ModelPaiementFacture> paiementList = new ArrayList<>();
        String query = "SELECT * FROM paiement_factures";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                ModelFacture facture = getFactureById(rs.getInt("facture_id"));
                ModelPaiementFacture paiement = new ModelPaiementFacture(
                        rs.getInt("paiement_id"),
                       
                        facture,
                        rs.getDate("date_paiement"),
                        rs.getBigDecimal("montant_paye"),
                        rs.getString("mode_paiement")
                );
                paiementList.add(paiement);
            }
        }
        return paiementList;
    }

    // Method to get a paiement by ID
       public static ModelPaiementFacture getPaiementById(int paiementId) throws SQLException {
        String query = "SELECT * FROM paiement_factures WHERE paiement_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, paiementId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                       ModelFacture facture = getFactureById(rs.getInt("facture_id"));
                    return new ModelPaiementFacture(
                            rs.getInt("paiement_id"),
                            facture,
                            rs.getDate("date_paiement"),
                            rs.getBigDecimal("montant_paye"),
                            rs.getString("mode_paiement")
                            
                          
               
                    );
                }
            }
        }
        return null;
    }

    // Method to add a new paiement
     public static boolean addPaiement(ModelPaiementFacture paiement) throws SQLException {
        String query = "INSERT INTO paiement_factures (facture_id, date_paiement, montant_paye, mode_paiement) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, paiement.getFacture().getFactureId());
            stmt.setDate(2, new java.sql.Date(paiement.getDatePaiement().getTime()));
            stmt.setBigDecimal(3, paiement.getMontantPaye());
            stmt.setString(4, paiement.getModePaiement());
            return stmt.executeUpdate() > 0;
        }
    }

    // Method to update a paiement
       public static boolean updatePaiement(ModelPaiementFacture paiement) throws SQLException {
        String query = "UPDATE paiement_factures SET facture_id = ?, date_paiement = ?, montant_paye = ?, mode_paiement = ? WHERE paiement_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, paiement.getFacture().getFactureId());
            stmt.setDate(2, new java.sql.Date(paiement.getDatePaiement().getTime()));
            stmt.setBigDecimal(3, paiement.getMontantPaye());
            stmt.setString(4, paiement.getModePaiement());
            stmt.setInt(5, paiement.getPaiementId());
            return stmt.executeUpdate() > 0;
        }
    }

    // Method to delete a paiement by ID
       public static boolean deletePaiement(int paiementId) throws SQLException {
        String query = "DELETE FROM paiement_facture WHERE paiement_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, paiementId);
            return stmt.executeUpdate() > 0;
        }
    }
    public static List<ModelPaiementAcompte> getAllAcompte() throws SQLException {
        List<ModelPaiementAcompte> acomptes = new ArrayList<>();
        String query = "SELECT * FROM paiement_acomptes";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                 ModelCommande commande = getCommandeById(rs.getInt("commande_id"));
                ModelPaiementAcompte acompte = new ModelPaiementAcompte(
                    rs.getInt("acompte_id"),
                  commande,
                    rs.getDate("date_paiement"),
                    rs.getBigDecimal("montant_paye"),
                    rs.getString("mode_paiement")
                );
                acomptes.add(acompte);
            }
        }
        return acomptes;
    }

    public static boolean deleteAcompte(int acompteId) throws SQLException {
        String query = "DELETE FROM paiement_acomptes WHERE acompte_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, acompteId);
            return stmt.executeUpdate() > 0;
        }
    }

    public static boolean updateAcompte(ModelPaiementAcompte acompte) throws SQLException {
        String query = "UPDATE paiement_acomptes SET commande_id = ?, date_paiement = ?, montant_paye = ?, mode_paiement = ? WHERE acompte_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, acompte.getCommande().getCommandeId());
            stmt.setDate(2, new java.sql.Date(acompte.getDatePaiement().getTime()));
            stmt.setBigDecimal(3, acompte.getMontantPaye());
            stmt.setString(4, acompte.getModePaiement());
            stmt.setInt(5, acompte.getAcompteId());
            return stmt.executeUpdate() > 0;
        }
    }

    public static ModelPaiementAcompte getAcompteById(int acompteId) throws SQLException {
        String query = "SELECT * FROM paiement_acomptes WHERE acompte_id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, acompteId);
             
            try (ResultSet rs = stmt.executeQuery()) {
                ModelCommande commande = getCommandeById(rs.getInt("commande_id"));
                if (rs.next()) {
                    return new ModelPaiementAcompte(
                        rs.getInt("acompte_id"),
                          commande,
                        rs.getDate("date_paiement"),
                        rs.getBigDecimal("montant_paye"),
                        rs.getString("mode_paiement")
                    );
                }
            }
        }
        return null;
    }

    public static boolean addAcompte(ModelPaiementAcompte acompte) throws SQLException {
        String query = "INSERT INTO paiement_acomptes (commande_id, date_paiement, montant_paye, mode_paiement) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, acompte.getCommande().getCommandeId());
            stmt.setDate(2, new java.sql.Date(acompte.getDatePaiement().getTime()));
            stmt.setBigDecimal(3, acompte.getMontantPaye());
            stmt.setString(4, acompte.getModePaiement());
            return stmt.executeUpdate() > 0;
        }
    }


public static String convertDevisToCommande(int devisId, BigDecimal amount, String paymentMode, Date paymentDate) throws SQLException {
    Connection conn = null;
    PreparedStatement pstmtInsertCommande = null;
    PreparedStatement pstmtInsertLigneCommande = null;
    PreparedStatement pstmtInsertSousLigneCommande = null;
    PreparedStatement pstmtUpdateDevis = null;

    try {
        // Récupérer la connexion
        conn = getConnection();
        conn.setAutoCommit(false);  // Démarrer une transaction
        resetAutoIncrementIfEmptyCommande(conn); // Vérifier et réinitialiser si les tables sont vides

        int nextCommandeId = getNextCommandeId(); // Obtenir le prochain ID de commande

        // 1. Récupérer les informations du devis à convertir
        ModelDevis devis = getDevisById(devisId);
        if (devis == null) {
            return "ERROR: Devis not found";
        }

        // 4. Créer un objet ModelCommande basé sur le devis
        ModelCommande commande = new ModelCommande();
        commande.setCommandeId(nextCommandeId);
        commande.setDevisId(devis.getDevisId());
        commande.setClient(devis.getClient());
        commande.setDateCommande(new java.util.Date());
        commande.setTotalHT(devis.getTotalHT());
        commande.setRemisePercentage(devis.getRemisePercentage());
        commande.setTotalRemise(devis.getTotalRemise());
        commande.setAcomptePercentage(devis.getAcomptePercentage());
        commande.setTotalAcompte(devis.getTotalAcompte());
        commande.setTvaPercentage(devis.getTvaPercentage());
        commande.setTotalTva(devis.getTotalTva());
        commande.setTotalTTC(devis.getTotalTTC());
        commande.setStatutCommande(new ModelStatutCommande(2, "En production"));

        // 5. Insérer la commande dans la base de données
        String sqlInsertCommande = "INSERT INTO commande (commande_id, devis_id, client_id, date_commande, total_HT, remise_percentage, total_remise, acompte_percentage, total_acompte, tva_percentage, total_tva, total_TTC,acompte_paye,reste_a_payer, statut_commande_id) "
                                 + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?,?)";
        pstmtInsertCommande = conn.prepareStatement(sqlInsertCommande);
        pstmtInsertCommande.setInt(1, commande.getCommandeId());
        pstmtInsertCommande.setInt(2, commande.getDevisId());
        pstmtInsertCommande.setInt(3, commande.getClient().getClientId());
        pstmtInsertCommande.setDate(4, new java.sql.Date(commande.getDateCommande().getTime()));
        pstmtInsertCommande.setBigDecimal(5, commande.getTotalHT());
        pstmtInsertCommande.setBigDecimal(6, commande.getRemisePercentage());
        pstmtInsertCommande.setBigDecimal(7, commande.getTotalRemise());
        pstmtInsertCommande.setBigDecimal(8, commande.getAcomptePercentage());
        pstmtInsertCommande.setBigDecimal(9, commande.getTotalAcompte());
        pstmtInsertCommande.setBigDecimal(10, commande.getTvaPercentage());
        pstmtInsertCommande.setBigDecimal(11, commande.getTotalTva());
        pstmtInsertCommande.setBigDecimal(12, commande.getTotalTTC());
        pstmtInsertCommande.setBigDecimal(13, amount);
        pstmtInsertCommande.setBigDecimal(14, commande.getTotalTTC().subtract(amount));
        pstmtInsertCommande.setInt(15, 2);
        pstmtInsertCommande.executeUpdate();
        // 4. Insérer le paiement d'acompte
                     
                    String insertPaiementSQL =  "INSERT INTO paiement_acomptes (commande_id, date_paiement, montant_paye, mode_paiement) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement stmtPaiement = conn.prepareStatement(insertPaiementSQL)) {
                        stmtPaiement.setInt(1, commande.getCommandeId());
                           stmtPaiement.setDate(2, new java.sql.Date(paymentDate.getTime()));
                        stmtPaiement.setBigDecimal(3, amount);
                        stmtPaiement.setString(4, paymentMode);
                        stmtPaiement.executeUpdate();
                    }

        // 5. Insérer les lignes de commande à partir du devis
        List<ModelLigneDevis> lignesDevis = getLignesDevisForDevis(devisId);

        String sqlInsertLigneCommande = "INSERT INTO ligne_commande (ligne_commande_id, commande_id, produit_id, designation, total_quantite, total_prix) "
                                        + "VALUES (?, ?, ?, ?, ?, ?)";
        pstmtInsertLigneCommande = conn.prepareStatement(sqlInsertLigneCommande);

        for (ModelLigneDevis ligneDevis : lignesDevis) {
            pstmtInsertLigneCommande.setInt(1, ligneDevis.getLigneDevisId());
            pstmtInsertLigneCommande.setInt(2, commande.getCommandeId());
            pstmtInsertLigneCommande.setInt(3, ligneDevis.getProduit().getProduitId());
            pstmtInsertLigneCommande.setString(4, ligneDevis.getDesignation());
            pstmtInsertLigneCommande.setInt(5, ligneDevis.getTotalQuantite());
            pstmtInsertLigneCommande.setBigDecimal(6, ligneDevis.getTotalPrix());
            pstmtInsertLigneCommande.executeUpdate();

            // Insérer les sous-lignes de commande
            List<ModelSousLigneDevis> sousLignesDevis = getSousLigneForLigneDevis(ligneDevis.getLigneDevisId());

            String sqlInsertSousLigneCommande = "INSERT INTO sous_ligne_commande (sousligne_commande_id, ligne_commande_id, largeur, hauteur, quantite, prix_unitaire, prix_total, main_oeuvre) "
                                                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            pstmtInsertSousLigneCommande = conn.prepareStatement(sqlInsertSousLigneCommande);

            for (ModelSousLigneDevis sousLigneDevis : sousLignesDevis) {
                pstmtInsertSousLigneCommande.setInt(1, sousLigneDevis.getSousLigneDevisId());
                pstmtInsertSousLigneCommande.setInt(2, ligneDevis.getLigneDevisId());
                pstmtInsertSousLigneCommande.setBigDecimal(3, sousLigneDevis.getLargeur());
                pstmtInsertSousLigneCommande.setBigDecimal(4, sousLigneDevis.getHauteur());
                pstmtInsertSousLigneCommande.setInt(5, sousLigneDevis.getQuantite());
                pstmtInsertSousLigneCommande.setBigDecimal(6, sousLigneDevis.getPrixUnitaire());
                pstmtInsertSousLigneCommande.setBigDecimal(7, sousLigneDevis.getPrixTotal());
                pstmtInsertSousLigneCommande.setBigDecimal(8, sousLigneDevis.getMainOeuvre());
                pstmtInsertSousLigneCommande.executeUpdate();
            }
        }

        // 6. Mettre à jour le statut du devis en 'Accepté'
        String sqlUpdateDevis = "UPDATE devis SET statut_devis_id = 3 WHERE devis_id = ?";
        pstmtUpdateDevis = conn.prepareStatement(sqlUpdateDevis);
        pstmtUpdateDevis.setInt(1, devisId);
        pstmtUpdateDevis.executeUpdate();

        // 7. Commit de la transaction
        conn.commit();
        return "SUCCESS";

    } catch (SQLException ex) {
        if (conn != null) {
            try {
                conn.rollback();  // Rollback en cas d'erreur
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        ex.printStackTrace();
        return "ERROR: " + ex.getMessage();
    } finally {
        closeQuietly(pstmtInsertCommande);
        closeQuietly(pstmtInsertLigneCommande);
        closeQuietly(pstmtInsertSousLigneCommande);
        closeQuietly(pstmtUpdateDevis);
        closeQuietly(conn);
    }
}


public static boolean isCommandeAlreadyConverted(int commandeId) throws SQLException {
    String query = "SELECT COUNT(*) FROM facture WHERE commande_id = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, commandeId);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0; // Si le résultat est supérieur à 0, la commande est déjà convertie en facture
            }
        }
    }
    return false; // Si aucun résultat trouvé, la commande n'est pas convertie
}
public static boolean isDevisAlreadyConverted(int devisId) throws SQLException {
    String query = "SELECT COUNT(*) FROM commande WHERE devis_id = ?";
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, devisId);
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1) > 0; // Si le résultat est supérieur à 0, la commande est déjà convertie en facture
            }
        }
    }
    return false; // Si aucun résultat trouvé, la commande n'est pas convertie
}
public static String convertCommandeToFacture(int commandeId, BigDecimal amount, String paymentMode, Date paymentDate) throws SQLException {
    Connection conn = null;
    PreparedStatement pstmtInsertFacture = null;
    PreparedStatement pstmtInsertLigneFacture = null;
    PreparedStatement pstmtInsertSousLigneFacture = null;
    PreparedStatement pstmtUpdateCommande = null;

    try {
        // Récupérer la connexion
        conn = getConnection();
        conn.setAutoCommit(false);  // Démarrer une transaction
        resetAutoIncrementIfEmptyFacture(conn); // Vérifier et réinitialiser si les tables sont vides

        int nextFactureId = getNextFactureId(); // Obtenir le prochain ID de facture

        // 1. Récupérer les informations de la commande à convertir
        ModelCommande commande = getCommandeById(commandeId);

        if (commande == null) {
            return "ERROR: Commande not found";
        }

        // 2. Créer un objet ModelFacture basé sur la commande
        ModelFacture facture = new ModelFacture();
        facture.setFactureId(nextFactureId);
        facture.setCommande(commande);
        facture.setClient(commande.getClient());
        facture.setDateFacture(new java.util.Date());
        facture.setTotalFactureHT(commande.getTotalHT());
        facture.setRemiseFacturePercentage(commande.getRemisePercentage());
        facture.setTotalFactureRemise(commande.getTotalRemise());
        facture.setTvaFacturePercentage(commande.getTvaPercentage());
        facture.setTotalFactureTva(commande.getTotalTva());
        facture.setTotalFactureTTC(commande.getTotalTTC());
        facture.setStatutFacture(new ModelStatutFacture(3, "Payée"));

        // 3. Insérer la facture dans la base de données
        String sqlInsertFacture = "INSERT INTO facture (facture_id, commande_id, client_id, date_facture, total_HT, remise_percentage,total_remise, tva_percentage,total_tva, total_TTC, statut_facture_id) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        pstmtInsertFacture = conn.prepareStatement(sqlInsertFacture);
        pstmtInsertFacture.setInt(1, facture.getFactureId());
        pstmtInsertFacture.setInt(2, facture.getCommande().getCommandeId());
        pstmtInsertFacture.setInt(3, facture.getClient().getClientId());
        pstmtInsertFacture.setDate(4, new java.sql.Date(facture.getDateFacture().getTime()));
        pstmtInsertFacture.setBigDecimal(5, facture.getTotalFactureHT());
        pstmtInsertFacture.setBigDecimal(6, facture.getRemiseFacturePercentage());
        pstmtInsertFacture.setBigDecimal(7, facture.getTotalFactureRemise());
        pstmtInsertFacture.setBigDecimal(8, facture.getTvaFacturePercentage());
        pstmtInsertFacture.setBigDecimal(9, facture.getTotalFactureTva());
         pstmtInsertFacture.setBigDecimal(10, facture.getTotalFactureTTC());
        pstmtInsertFacture.setInt(11, 3); // Statut de la facture
        pstmtInsertFacture.executeUpdate();
        
        
                String insertPaiementSQL =  "INSERT INTO paiement_factures (facture_id, date_paiement, montant_paye, mode_paiement) VALUES (?, ?, ?, ?)";
                    try (PreparedStatement stmtPaiement = conn.prepareStatement(insertPaiementSQL)) {
                        stmtPaiement.setInt(1, facture.getFactureId());
                           stmtPaiement.setDate(2, new java.sql.Date(paymentDate.getTime()));
                        stmtPaiement.setBigDecimal(3, amount);
                        stmtPaiement.setString(4, paymentMode);
                        stmtPaiement.executeUpdate();
                    }

     
        // 4. Insérer les lignes de facture basées sur les lignes de commande
        List<ModelLigneCommande> lignesCommande =  getLignesCommandeForCommande(commandeId);

        String sqlInsertLigneFacture = "INSERT INTO ligne_facture (ligne_facture_id, facture_id, produit_id,designation, total_quantite, total_prix) "
                                        + "VALUES (?, ?, ?, ?, ?, ?)";
        pstmtInsertLigneFacture = conn.prepareStatement(sqlInsertLigneFacture);

        for (ModelLigneCommande ligneCommande : lignesCommande) {
            pstmtInsertLigneFacture.setInt(1, ligneCommande.getLigneCommandeId());  // Utiliser l'ID de ligneCommande
            pstmtInsertLigneFacture.setInt(2, facture.getFactureId());
            pstmtInsertLigneFacture.setInt(3, ligneCommande.getProduitId());

            pstmtInsertLigneFacture.setString(4, ligneCommande.getDesignation());
            pstmtInsertLigneFacture.setInt(5, ligneCommande.getTotalQuantite());
            pstmtInsertLigneFacture.setBigDecimal(6, ligneCommande.getTotalPrix());
            pstmtInsertLigneFacture.executeUpdate();

            // 5. Insérer les sous-lignes associées avec les mêmes IDs que sous_ligne_commande
            List<ModelSousLigneCommande> sousLignesCommande = getSousLigneForLigneCommande(ligneCommande.getLigneCommandeId());

            String sqlInsertSousLigneFacture = "INSERT INTO sous_ligne_facture (sousligne_facture_id, ligne_facture_id,  largeur, hauteur, quantite, prix_unitaire, prix_total, main_oeuvre) "
                                                + "VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";
            pstmtInsertSousLigneFacture = conn.prepareStatement(sqlInsertSousLigneFacture);

            for (ModelSousLigneCommande sousLigneCommande : sousLignesCommande) {
                pstmtInsertSousLigneFacture.setInt(1, sousLigneCommande.getSousLigneCommandeId());  // Utiliser l'ID de sousLigneCommande
                pstmtInsertSousLigneFacture.setInt(2, ligneCommande.getLigneCommandeId());  // Utiliser l'ID de ligneCommande comme ligne_facture_id
                pstmtInsertSousLigneFacture.setInt(3, sousLigneCommande.getLargeur());
                pstmtInsertSousLigneFacture.setInt(4, sousLigneCommande.getHauteur());
                pstmtInsertSousLigneFacture.setInt(5, sousLigneCommande.getQuantite());
                pstmtInsertSousLigneFacture.setBigDecimal(6, sousLigneCommande.getPrixUnitaire());
                pstmtInsertSousLigneFacture.setBigDecimal(7, sousLigneCommande.getPrixTotal());
                pstmtInsertSousLigneFacture.setBigDecimal(8, sousLigneCommande.getMainOeuvre());
                pstmtInsertSousLigneFacture.executeUpdate();
            }
        }

        // 6. Mettre à jour le statut de la commande en 'Facturé'
        String sqlUpdateCommande = "UPDATE commande SET statut_commande_id = '3' WHERE commande_id = ?";
        pstmtUpdateCommande = conn.prepareStatement(sqlUpdateCommande);
        pstmtUpdateCommande.setInt(1, commandeId);
        pstmtUpdateCommande.executeUpdate();

        // 7. Commit de la transaction
        conn.commit();
        return "SUCCESS";

    } catch (SQLException ex) {
        if (conn != null) {
            try {
                conn.rollback();  // Rollback si une erreur survient
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        ex.printStackTrace();
        return "ERROR: " + ex.getMessage();
    } finally {
        closeQuietly(pstmtInsertFacture);
        closeQuietly(pstmtInsertLigneFacture);
        closeQuietly(pstmtInsertSousLigneFacture);
        closeQuietly(pstmtUpdateCommande);
        closeQuietly(conn);
    }
}
public static ModelCommande getCommandeById(int commandeId) throws SQLException {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    try {
        conn = getConnection();  // Connexion à la base de données
        String sql = "SELECT c.*, cl.client_id, cl.nom, cl.adresse, cl.telephone, cl.email "  // Retirer la virgule supplémentaire après 'email'
                   + "FROM commande c "
                   + "JOIN clients cl ON c.client_id = cl.client_id "
                   + "WHERE c.commande_id = ?";
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, commandeId);
        rs = pstmt.executeQuery();

        if (rs.next()) {
            // Création de l'objet ModelCommande
            ModelCommande commande = new ModelCommande();
            commande.setCommandeId(rs.getInt("commande_id"));
            commande.setDateCommande(rs.getDate("date_commande"));
            commande.setTotalHT(rs.getBigDecimal("total_HT"));
            commande.setRemisePercentage(rs.getBigDecimal("tva_percentage"));
            commande.setTotalRemise(rs.getBigDecimal("total_remise"));
            commande.setTotalAcompte(rs.getBigDecimal("total_acompte"));
            
            commande.setTvaPercentage(rs.getBigDecimal("tva_percentage"));
            commande.setTotalTva(rs.getBigDecimal("total_tva"));
            commande.setTotalTTC(rs.getBigDecimal("total_TTC"));

            // Création de l'objet ModelClient
            ModelClient client = new ModelClient();
            client.setClientId(rs.getInt("client_id"));
            client.setNom(rs.getString("nom"));
            client.setAdresse(rs.getString("adresse"));
            client.setTelephone(rs.getString("telephone"));
            client.setEmail(rs.getString("email"));

            // Associer le client à la commande
            commande.setClient(client);

            return commande;
        } else {
            return null;  // Commande non trouvée
        }

    } finally {
        if (rs != null) rs.close();
        if (pstmt != null) pstmt.close();
        if (conn != null) conn.close();
    }
}


public static  List<ModelLigneCommande> getLignesCommandeForCommande(int commandeId) throws SQLException {
    String query = "SELECT * FROM ligne_commande WHERE commande_id = ?";
    List<ModelLigneCommande> lignesCommande = new ArrayList<>();
    
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        
        statement.setInt(1, commandeId);
        ResultSet resultSet = statement.executeQuery();
        
        while (resultSet.next()) {
            ModelLigneCommande ligneCommande = new ModelLigneCommande();
            ligneCommande.setLigneCommandeId(resultSet.getInt("ligne_commande_id"));
            ligneCommande.setProduitId(resultSet.getInt("produit_id"));
            ligneCommande.setDesignation(resultSet.getString("designation"));
            ligneCommande.setTotalQuantite(resultSet.getInt("total_quantite"));
            ligneCommande.setTotalPrix(resultSet.getBigDecimal("total_prix"));
            
            lignesCommande.add(ligneCommande);
        }
    }
    return lignesCommande;
}
public static  List<ModelSousLigneCommande> getSousLigneForLigneCommande(int ligneCommandeId) throws SQLException {
    String query = "SELECT * FROM sous_ligne_commande WHERE ligne_commande_id = ?";
    List<ModelSousLigneCommande> sousLignesCommande = new ArrayList<>();
    
    try (Connection connection = DatabaseUtils.getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        
        statement.setInt(1, ligneCommandeId);
        ResultSet resultSet = statement.executeQuery();
        
        while (resultSet.next()) {
            ModelSousLigneCommande sousLigneCommande = new ModelSousLigneCommande();
            sousLigneCommande.setSousLigneCommandeId(resultSet.getInt("sousligne_commande_id"));
            sousLigneCommande.setLargeur(resultSet.getInt("largeur"));
            sousLigneCommande.setHauteur(resultSet.getInt("hauteur"));
            sousLigneCommande.setQuantite(resultSet.getInt("quantite"));
            sousLigneCommande.setPrixUnitaire(resultSet.getBigDecimal("prix_unitaire"));
            sousLigneCommande.setPrixTotal(resultSet.getBigDecimal("prix_total"));
           
            sousLigneCommande.setMainOeuvre(resultSet.getBigDecimal("main_oeuvre"));
            
            sousLignesCommande.add(sousLigneCommande);
        }
    }
    return sousLignesCommande;
}




public static void closeQuietly(AutoCloseable closeable) {
    if (closeable != null) {
        try {
            closeable.close();
        } catch (Exception e) {
            // Ignorer l'exception
            e.printStackTrace();
        }
    }
}
//public static List<ModelLigneCommande> getLignesCommandeForCommande(int commandeId) throws SQLException {
//    List<ModelLigneCommande> lignesCommandeList = new ArrayList<>();
//    String sqlLignesCommande = "SELECT * FROM ligne_commande WHERE commande_id = ?";
//    try (Connection conn = getConnection();
//         PreparedStatement pstmtLignesCommande = conn.prepareStatement(sqlLignesCommande)) {
//        pstmtLignesCommande.setInt(1, commandeId);
//        ResultSet rsLignesCommande = pstmtLignesCommande.executeQuery();
//       
//        // Parcours des résultats de la table 'ligne_commande'
//        while (rsLignesCommande.next()) {
//             ModelCommande commande= getCommandeById(rsLignesCommande.getInt("commande_id"));
//            int ligneCommandeId = rsLignesCommande.getInt("ligne_commande_id");
//
//            ModelLigneCommande ligneCommande = new ModelLigneCommande(
//                ligneCommandeId,
//                commande,
//                rsLignesCommande.getInt("produit_id"),
//                rsLignesCommande.getString("designation"),
//                rsLignesCommande.getInt("quantite"),
//                rsLignesCommande.getBigDecimal("prix_total"),
//                getSousLignesCommandeForLigneCommande(ligneCommandeId)
//            );
//            lignesCommandeList.add(ligneCommande);
//        }
//    }
//    return lignesCommandeList;
//}
//public static List<ModelSousLigneCommande> getSousLignesCommandeForLigneCommande(int ligneCommandeId) throws SQLException {
//    List<ModelSousLigneCommande> sousLigneList = new ArrayList<>();
//    String sqlSousLigneCommande = "SELECT * FROM sous_ligne_commande WHERE ligne_commande_id = ?";
//    try (Connection conn = getConnection();
//         PreparedStatement pstmtSousLigneCommande = conn.prepareStatement(sqlSousLigneCommande)) {
//        pstmtSousLigneCommande.setInt(1, ligneCommandeId);
//        ResultSet rsSousLigneCommande = pstmtSousLigneCommande.executeQuery();
//
//        // Parcours des résultats de la table 'sous_ligne_commande'
//        while (rsSousLigneCommande.next()) {
//            ModelStructure structure = getStrucureById(rsSousLigneCommande.getInt("structure_id"));
//
//            ModelSousLigneCommande sousLigneCommande = new ModelSousLigneCommande(
//                rsSousLigneCommande.getInt("sousligne_commande_id"),
//                rsSousLigneCommande.getInt("ligne_commande_id"),
//                structure,
//                rsSousLigneCommande.getInt("nbre_ventaux"),
//                rsSousLigneCommande.getInt("largeur"),
//                rsSousLigneCommande.getInt("hauteur"),
//                rsSousLigneCommande.getInt("quantite"),
//                rsSousLigneCommande.getBigDecimal("prix_unitaire"),
//                rsSousLigneCommande.getBigDecimal("prix_total"),
//                rsSousLigneCommande.getBigDecimal("main_oeuvre"),
//                getSousLigneChassis(rsSousLigneCommande.getInt("sousligne_commande_id")),
//                getSousLigneVitre(rsSousLigneCommande.getInt("sousligne_commande_id")),
//                getSousLigneJoint(rsSousLigneCommande.getInt("sousligne_commande_id")),
//                getSousLignePaumelle(rsSousLigneCommande.getInt("sousligne_commande_id")),
//                getSousLigneRoulette(rsSousLigneCommande.getInt("sousligne_commande_id")),
//                getSousLigneSerrure(rsSousLigneCommande.getInt("sousligne_commande_id")),
//                getSousLignePoignee(rsSousLigneCommande.getInt("sousligne_commande_id")),
//                getSousLigneVis(rsSousLigneCommande.getInt("sousligne_commande_id"))
//            );
//            sousLigneList.add(sousLigneCommande);
//        }
//    }
//    return sousLigneList;
//}

//public static  List<ModelLigneCommande> getLignesCommandeForCommande(int commandeId) throws SQLException {
//    String query = "SELECT * FROM ligne_commande WHERE commande_id = ?";
//    List<ModelLigneCommande> lignesCommande = new ArrayList<>();
//    
//    try (Connection connection = DatabaseUtils.getConnection();
//         PreparedStatement statement = connection.prepareStatement(query)) {
//        
//        statement.setInt(1, commandeId);
//        ResultSet resultSet = statement.executeQuery();
//        
//        while (resultSet.next()) {
//            ModelLigneCommande ligneCommande = new ModelLigneCommande();
//            ligneCommande.setLigneCommandeId(resultSet.getInt("ligne_commande_id"));
//            ligneCommande.setProduitId(resultSet.getInt("produit_id"));
//            ligneCommande.setDesignation(resultSet.getString("designation"));
//            ligneCommande.setTotalQuantite(resultSet.getInt("total_quantite"));
//            ligneCommande.setTotalPrix(resultSet.getBigDecimal("total_prix"));
//            
//            lignesCommande.add(ligneCommande);
//        }
//    }
//    return lignesCommande;
//}

//public static  List<ModelSousLigneCommande> getSousLigneForLigneCommande(int ligneCommandeId) throws SQLException {
//    String query = "SELECT * FROM sous_ligne_commande WHERE ligne_commande_id = ?";
//    List<ModelSousLigneCommande> sousLignesCommande = new ArrayList<>();
//    
//    try (Connection connection = DatabaseUtils.getConnection();
//         PreparedStatement statement = connection.prepareStatement(query)) {
//        
//        statement.setInt(1, ligneCommandeId);
//        ResultSet resultSet = statement.executeQuery();
//        
//        while (resultSet.next()) {
//            ModelSousLigneCommande sousLigneCommande = new ModelSousLigneCommande();
//            sousLigneCommande.setSousLigneCommandeId(resultSet.getInt("sousligne_commande_id"));
//            sousLigneCommande.setLargeur(resultSet.getInt("largeur"));
//            sousLigneCommande.setHauteur(resultSet.getInt("hauteur"));
//            sousLigneCommande.setQuantite(resultSet.getInt("quantite"));
//            sousLigneCommande.setPrixUnitaire(resultSet.getBigDecimal("prix_unitaire"));
//            sousLigneCommande.setPrixTotal(resultSet.getBigDecimal("prix_total"));
//           
//            sousLigneCommande.setMainOeuvre(resultSet.getBigDecimal("main_oeuvre"));
//            
//            sousLignesCommande.add(sousLigneCommande);
//        }
//    }
//    return sousLignesCommande;
//}
//


// Méthode pour obtenir une facture par son ID
//public static ModelFacture getFactureById(int factureId) {
//    ModelFacture facture = null;
//    try (Connection conn = getConnection()) {
//        String sqlFacture = "SELECT * FROM facture WHERE facture_id = ?";
//        PreparedStatement stmtFacture = conn.prepareStatement(sqlFacture);
//        stmtFacture.setInt(1, factureId);
//        ResultSet rsFacture = stmtFacture.executeQuery();
//
//        if (rsFacture.next()) {
//            // Récupération des informations du client associé à la facture
//            ModelClient client = getClientById(rsFacture.getInt("client_id"));
//            Date dateFacture = rsFacture.getDate("date_facture");
//            ModelCommande commande = getCommandeById(rsFacture.getInt("commande_id"));
//            ModelStatutFacture statutFacture = getStatutFactureById(rsFacture.getInt("statut_facture_id"));
//            // Récupération des montants de la facture
//            BigDecimal totalHT = rsFacture.getBigDecimal("total_HT");
//            BigDecimal tvaPercentage = rsFacture.getBigDecimal("tva_percentage");
//            BigDecimal totalTTC = rsFacture.getBigDecimal("total_TTC");
//            
//            // Récupération des lignes de facture associées
//            List<ModelLigneFacture> lignesFacture = getLignesFactureForFacture(factureId);
//
//            // Construction de l'objet ModelFacture avec les informations obtenues
//            facture = new ModelFacture(
//                factureId,
//                commande,
//                client,
//                dateFacture,
//                totalHT,
//                tvaPercentage,
//                totalTTC,
//                statutFacture,
//                lignesFacture
//            );
//        }
//    } catch (SQLException e) {
//        System.err.println("Erreur lors de la récupération de la facture : " + e.getMessage());
//    }
//    return facture;
//}

public static ModelDevis getDevisById(int devisId) {
    ModelDevis devis = null;
    try (Connection conn = getConnection()) {
        String sqlDevis = "SELECT * FROM devis WHERE devis_id = ?";
        PreparedStatement stmtDevis = conn.prepareStatement(sqlDevis);
        stmtDevis.setInt(1, devisId);
        ResultSet rsDevis = stmtDevis.executeQuery();

        if (rsDevis.next()) {
            // Récupération des informations du client associé au devis
            ModelClient client = getClientById(rsDevis.getInt("client_id"));
            Date dateDevis = rsDevis.getDate("date_devis");
            
            // Récupération des nouvelles colonnes pour la gestion des montants
            BigDecimal totalHT = rsDevis.getBigDecimal("total_HT");
            BigDecimal remisePercentage = rsDevis.getBigDecimal("remise_percentage");
             BigDecimal totalRemise = rsDevis.getBigDecimal("total_remise");
            BigDecimal acomptePercentage = rsDevis.getBigDecimal("acompte_percentage");
            BigDecimal totalAcompte = rsDevis.getBigDecimal("total_acompte");
            BigDecimal tvaPercentage = rsDevis.getBigDecimal("tva_percentage");
             BigDecimal totalTva = rsDevis.getBigDecimal("total_tva");
            BigDecimal totalTTC = rsDevis.getBigDecimal("total_TTC");
            
            // Récupération du statut du devis
            ModelStatutDevis statut = getStatutDevisById(rsDevis.getInt("statut_devis_id"));

            // Récupération des lignes de devis associées
            List<ModelLigneDevis> lignesDevis = getLignesDevisForDevis(devisId);

            // Construction de l'objet ModelDevis avec les informations obtenues
            devis = new ModelDevis(
                devisId,
                client,
                dateDevis,
                totalHT,
                remisePercentage,
                    totalRemise,
                    acomptePercentage,
                totalAcompte,
                tvaPercentage,
                    totalTva,
                totalTTC,
                statut,
                lignesDevis
            );
        }
    } catch (SQLException e) {
        System.err.println("Erreur lors de la récupération du devis : " + e.getMessage());
    }
    return devis;
}

public static List<ModelLigneDevis> getLignesDevisForDevis(int devisId) throws SQLException {
    List<ModelLigneDevis> lignesDevisList = new ArrayList<>();
    String sqlLignesDevis = "SELECT * FROM ligne_devis WHERE devis_id = ?";
      try (Connection conn = getConnection();
       PreparedStatement pstmtLignesDevis = conn.prepareStatement(sqlLignesDevis)) {
        pstmtLignesDevis.setInt(1, devisId);
        ResultSet rsLignesDevis = pstmtLignesDevis.executeQuery();

        // Parcours des résultats de la table 'ligne_devis'
        while (rsLignesDevis.next()) {
            int ligneDevisId = rsLignesDevis.getInt("ligne_devis_id");
ModelProduit produit = getProduitById( rsLignesDevis.getInt("produit_id"));
            ModelLigneDevis ligneDevis = new ModelLigneDevis(
                ligneDevisId,
                rsLignesDevis.getInt("devis_id"),
               produit ,
                rsLignesDevis.getString("designation"),
                rsLignesDevis.getInt("total_quantite"),
                rsLignesDevis.getBigDecimal("total_prix"),
                getSousLigneForLigneDevis(ligneDevisId)
            );
            lignesDevisList.add(ligneDevis);
        }
    }
    return lignesDevisList;
}


public static List<ModelSousLigneDevis> getSousLigneForLigneDevis(int ligneDevisId) throws SQLException {
    List<ModelSousLigneDevis> sousLigneList = new ArrayList<>();
    String sqlSousLigne = "SELECT * FROM sous_ligne_devis WHERE ligne_devis_id = ?";
      try (Connection conn = getConnection();
     PreparedStatement pstmtSousLigne = conn.prepareStatement(sqlSousLigne)) {
        pstmtSousLigne.setInt(1, ligneDevisId);
        ResultSet rsSousLigne = pstmtSousLigne.executeQuery();

        // Parcours des résultats de la table 'sous_ligne_devis'
        while (rsSousLigne.next()) {
             ModelStructure structure = getStrucureById(rsSousLigne.getInt("structure_id"));
              ModelSousLigneDevis sousLigne = new ModelSousLigneDevis(
                rsSousLigne.getInt("sousligne_devis_id"),
                rsSousLigne.getInt("ligne_devis_id"),
                 structure ,
                rsSousLigne.getInt("nbre_ventaux"),
                rsSousLigne.getBigDecimal("largeur"),
                rsSousLigne.getBigDecimal("hauteur"),
                rsSousLigne.getInt("quantite"),
                rsSousLigne.getBigDecimal("prix_unitaire"),
                rsSousLigne.getBigDecimal("prix_total"),
               // rsSousLigne.getBigDecimal("main_oeuvre"),
                getSousLigneChassis(rsSousLigne.getInt("sousligne_devis_id")),
                getSousLigneVitre(rsSousLigne.getInt("sousligne_devis_id")),
                getSousLigneJoint(rsSousLigne.getInt("sousligne_devis_id")),
                getSousLignePaumelle(rsSousLigne.getInt("sousligne_devis_id")),
                getSousLigneRoulette(rsSousLigne.getInt("sousligne_devis_id")),
                getSousLigneSerrure(rsSousLigne.getInt("sousligne_devis_id")),
                getSousLignePoignee(rsSousLigne.getInt("sousligne_devis_id")),
                getSousLigneVis(rsSousLigne.getInt("sousligne_devis_id")),
                getSousLigneOperateur(rsSousLigne.getInt("sousligne_devis_id")), // Added for operateurs
                 getSousLigneLameVerre(rsSousLigne.getInt("sousligne_devis_id")), // Added for lame_verre
                 getSousLigneClips(rsSousLigne.getInt("sousligne_devis_id")),     // Added for clips
                  getSousLigneRivette(rsSousLigne.getInt("sousligne_devis_id"))    // Added for rivettes
            );
            sousLigneList.add(sousLigne);
        }
    }
    return sousLigneList;
}


    public static ModelFacture getFactureById(int factureId) {
        ModelFacture facture = null;
        try (Connection conn = getConnection()) {
            String sqlFacture = "SELECT * FROM facture WHERE facture_id = ?";
            PreparedStatement stmtFacture = conn.prepareStatement(sqlFacture);
            stmtFacture.setInt(1, factureId);
            ResultSet rsFacture = stmtFacture.executeQuery();

            if (rsFacture.next()) {
                ModelClient client = getClientById(rsFacture.getInt("client_id"));
                Date dateFacture = rsFacture.getDate("date_facture");
                ModelCommande commande = getCommandeById(rsFacture.getInt("commande_id"));
                ModelStatutFacture statutFacture = getStatutFactureById(rsFacture.getInt("statut_facture_id"));
                BigDecimal totalHT = rsFacture.getBigDecimal("total_HT");     
            BigDecimal remisePercentage = rsFacture.getBigDecimal("remise_percentage");
             BigDecimal totalRemise = rsFacture.getBigDecimal("total_remise");
            BigDecimal tvaPercentage = rsFacture.getBigDecimal("tva_percentage");
             BigDecimal totalTva = rsFacture.getBigDecimal("total_tva");
            BigDecimal totalTTC = rsFacture.getBigDecimal("total_TTC");
           

                List<ModelLigneFacture> lignesFacture = getLignesFactureForFacture(factureId);

                facture = new ModelFacture(
                    factureId,
                    commande,
                    client,
                    dateFacture,
                    totalHT,
                        remisePercentage,
                        totalRemise,
                    tvaPercentage,
                    totalTva,
                    totalTTC,
                    statutFacture,
                    lignesFacture
                );
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de la facture : " + e.getMessage());
        }
        return facture;
    }

public static List<ModelLigneFacture> getLignesFactureForFacture(int factureId) throws SQLException {
    List<ModelLigneFacture> lignesFactureList = new ArrayList<>();
    String sqlLignesFacture = "SELECT * FROM ligne_facture WHERE facture_id = ?";
      try (Connection conn = getConnection();
       PreparedStatement pstmtLignesFacture = conn.prepareStatement(sqlLignesFacture)) {
        pstmtLignesFacture.setInt(1, factureId);
        ResultSet rsLignesFacture = pstmtLignesFacture.executeQuery();

        // Parcours des résultats de la table 'ligne_devis'
        while (rsLignesFacture.next()) {
            int ligneFactureId = rsLignesFacture.getInt("ligne_facture_id");

            ModelLigneFacture ligneDevis = new ModelLigneFacture(
                ligneFactureId,
                rsLignesFacture.getInt("facture_id"),
                rsLignesFacture.getInt("produit_id"),
                rsLignesFacture.getString("designation"),
                rsLignesFacture.getInt("total_quantite"),
                rsLignesFacture.getBigDecimal("total_prix"),
                getSousLigneForLigneFacture(ligneFactureId)
            );
            lignesFactureList.add(ligneDevis);
        }
    }
    return lignesFactureList;
}


public static List<ModelSousLigneFacture> getSousLigneForLigneFacture(int ligneFactureId) throws SQLException {
    List<ModelSousLigneFacture> sousLigneList = new ArrayList<>();
    String sqlSousLigne = "SELECT * FROM sous_ligne_facture WHERE ligne_facture_id = ?";
      try (Connection conn = getConnection();
     PreparedStatement pstmtSousLigne = conn.prepareStatement(sqlSousLigne)) {
         pstmtSousLigne.setInt(1, ligneFactureId);
        ResultSet rsSousLigne = pstmtSousLigne.executeQuery();

        // Parcours des résultats de la table 'sous_ligne_devis'
        while (rsSousLigne.next()) {
          
              ModelSousLigneFacture sousLigne = new ModelSousLigneFacture(
                rsSousLigne.getInt("sousligne_facture_id"),
                rsSousLigne.getInt("ligne_facture_id"),
          
                rsSousLigne.getBigDecimal("largeur"),
                rsSousLigne.getBigDecimal("hauteur"),
                rsSousLigne.getInt("quantite"),
                rsSousLigne.getBigDecimal("prix_unitaire"),
                rsSousLigne.getBigDecimal("prix_total"),
                rsSousLigne.getBigDecimal("main_oeuvre")
              
            );
            sousLigneList.add(sousLigne);
        }
    }
    return sousLigneList;
}



// Méthode pour réinitialiser le compteur d'AUTO_INCREMENT si les tables sont vides
public static void resetAutoIncrementIfEmptyDevis(Connection conn) throws SQLException {
    // Vérifier et réinitialiser pour la table ligne_devis
    String sqlCheckLigne = "SELECT COUNT(*) AS count FROM ligne_devis";
    try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckLigne);
         ResultSet rs = pstmt.executeQuery()) {
        if (rs.next() && rs.getInt("count") == 0) {
            String sqlResetLigne = "ALTER TABLE ligne_devis AUTO_INCREMENT = 1";
            try (PreparedStatement pstmtReset = conn.prepareStatement(sqlResetLigne)) {
                pstmtReset.execute();
            }
        }
    }

    // Vérifier et réinitialiser pour la table sousligne_devis
    String sqlCheckSousLigne = "SELECT COUNT(*) AS count FROM sous_ligne_devis";
    try (PreparedStatement pstmt = conn.prepareStatement(sqlCheckSousLigne);
         ResultSet rs = pstmt.executeQuery()) {
        if (rs.next() && rs.getInt("count") == 0) {
            String sqlResetSousLigne = "ALTER TABLE sous_ligne_devis AUTO_INCREMENT = 1";
            try (PreparedStatement pstmtReset = conn.prepareStatement(sqlResetSousLigne)) {
                pstmtReset.execute();
            }
        }
    }
}

 public static ModelStructure getStrucureById(Integer structureId) {
    ModelStructure structure = null;
    try {
        Connection conn = getConnection();
        String query = "SELECT * FROM structure WHERE structure_id = ?";
        PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, structureId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("structure_id");
            String structureName = rs.getString("structure");

            structure = new ModelStructure(id, structureName);
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Gérer l'exception comme vous le souhaitez
    } finally {
        // Fermez la connexion, PreparedStatement et ResultSet si nécessaire ici
    }
    return structure;
}



   
public void updateDevisStatut(int devisId) {
    String sql = "UPDATE devis SET statut_devis_id = ? WHERE devis_id = ?";
    try (Connection conn = DatabaseUtils.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        stmt.setInt(1, 3);  // 3 correspond au statut "Accepté"
        stmt.setInt(2, devisId);
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public static int getTypeVitreId(String type, int epaisseur) throws Exception {
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(
                 "SELECT type_vitre_id FROM type_vitre WHERE type_de_verre = ? AND epaisseur_mm = ?")) {
        stmt.setString(1, type);
        stmt.setInt(2,  epaisseur);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt("type_vitre_id");
        }
    } catch (SQLException ex) {
       
    }
    return -1; // Retourne -1 si aucun ID trouvé
}
//public static int getTypeVerreId(String type, int epaisseur,String largeur) throws Exception {
//    try (Connection conn = getConnection();
//         PreparedStatement stmt = conn.prepareStatement(
//                 "SELECT lame_verre_id FROM lame_verre WHERE type_verre = ? AND epaisseur_mm = ? AND largeur_mm = ?")) {
//        stmt.setString(1, type);
//        stmt.setInt(2,  epaisseur);
//        stmt.setString(1, largeur);
//        ResultSet rs = stmt.executeQuery();
//        if (rs.next()) {
//            return rs.getInt("lame_verre_id");
//        }
//    } catch (SQLException ex) {
//       
//    }
//    return -1; // Retourne -1 si aucun ID trouvé
//}
public static int getIdFromChoice(String choice, String tableName, String columnName, String idColumnName) throws Exception {
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(
                 "SELECT " + idColumnName + " FROM " + tableName + " WHERE " + columnName + " = ?")) {
        stmt.setString(1, choice);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            return rs.getInt(idColumnName);
        }
    } catch (SQLException ex) {
      
    }
    return -1; // Retourne -1 si aucun ID trouvé
}
//public static Integer getTypeOuvertureIdFromProduit(int produitId) throws SQLException {
//    String query = "SELECT type_ouverture_id FROM produit WHERE produit_id = ?";
//    
//    try (Connection connection = getConnection();
//         PreparedStatement statement = connection.prepareStatement(query)) {
//
//        statement.setInt(1, produitId);
//        
//        try (ResultSet resultSet = statement.executeQuery()) {
//            if (resultSet.next()) {
//                return resultSet.getInt("type_ouverture_id");
//            } else {
//                return null; // Aucun produit trouvé avec cet ID
//            }
//        }
//    }
//}
public static Integer getTypeOuvertureIdFromProduit(int produitId) throws SQLException {
    String query = "SELECT p.type_ouverture_id, t.type_ouverture " +
                   "FROM produit p " +
                   "INNER JOIN type_ouverture t ON p.type_ouverture_id = t.type_ouverture_id " +
                   "WHERE p.produit_id = ?";
    
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setInt(1, produitId);
        
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
              
                return resultSet.getInt("type_ouverture_id");
            } else {
                return null; // Aucun produit trouvé avec cet ID
            }
        }
    }
}
public static int getLamVerreId(String type,int epaisseur,String largeur) throws SQLException {
    // Remove the "mm" suffix
    String selectedLargeurStr = largeur.replace("mm", "").trim();
    BigDecimal selectedLargeur = new BigDecimal(selectedLargeurStr);

    String query = "SELECT lame_verre_id FROM lame_verre WHERE type_verre = ? AND epaisseur_mm = ? AND largeur_mm = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setString(1, type);
        statement.setInt(2,  epaisseur);
          statement.setBigDecimal(3, selectedLargeur);
    
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("lame_verre_id"); // Return the lame_verre_id
            } else {
                return -1; // Return -1 if no record is found
            }
        }
    }
}

   public static int getProductId(String productName) throws Exception {
        int productId = -1;
        String query = "SELECT produit_id FROM produit WHERE nom = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, productName);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    productId = rs.getInt("produit_id");
                }
            }
        } catch (SQLException e) {
        }
       return productId;
    }  
     public static ModelStatutFacture getStatutFactureById(int statutFactureId) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ModelStatutFacture  statutFacture  = null;
    
    try {
        // Récupérer la connexion
        conn = getConnection();
        
        // Requête SQL pour récupérer le statut de commande par ID
        String sql = "SELECT statut_facture_id, statut FROM statut_facture  WHERE statut_facture_id = ?";
        
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, statutFactureId);
        rs = pstmt.executeQuery();
        
        // Si un résultat est trouvé, créer l'objet ModelStatutCommande
        if (rs.next()) {
          statutFacture  = new ModelStatutFacture (rs.getInt("statut_facture_id"), rs.getString("statut"));

        }
        
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        closeQuietly(rs);
        closeQuietly(pstmt);
        closeQuietly(conn);
    }
    
    return statutFacture ;
}

     
       // Méthode pour récupérer toutes les structures de la table `structure`
    public static List<ModelStructure> getStructures() throws SQLException {
        List<ModelStructure> structures = new ArrayList<>();

        String query = "SELECT * FROM structure";
        
        try (Connection conn = getConnection(); 
             PreparedStatement stmt = conn.prepareStatement(query); 
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int structureId = rs.getInt("structure_id");
                String structure = rs.getString("structure");

                // Créer une instance de ModelStructure et l'ajouter à la liste
                structures.add(new ModelStructure(structureId, structure));
            }
        }
        return structures;
    }

  public static ModelStatutCommande getStatutCommandeById(int statutCommandeId) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    ModelStatutCommande statutCommande = null;
    
    try {
        // Récupérer la connexion
        conn = getConnection();
        
        // Requête SQL pour récupérer le statut de commande par ID
        String sql = "SELECT * FROM statut_commande WHERE statut_commande_id = ?";
        
        pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, statutCommandeId);
        rs = pstmt.executeQuery();
        
        // Si un résultat est trouvé, créer l'objet ModelStatutCommande
        if (rs.next()) {
            statutCommande = new ModelStatutCommande(rs.getInt("statut_commande_id"), rs.getString("statut"));
        }
        
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        closeQuietly(rs);
        closeQuietly(pstmt);
        closeQuietly(conn);
    }
    
    return statutCommande;
}
public static List<ModelStatutCommande> getAllStatutCommande() {
    List<ModelStatutCommande> statuts = new ArrayList<>();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
        // Récupérer la connexion
        conn = getConnection();
        
        // Requête SQL pour récupérer tous les statuts
        String sql = "SELECT * FROM statut_commande";
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);

        // Ajouter chaque statut à la liste
        while (rs.next()) {
            statuts.add(new ModelStatutCommande(rs.getInt("statut_commande_id"), rs.getString("statut")));
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        closeQuietly(rs);
        closeQuietly(stmt);
        closeQuietly(conn);
    }
    
    return statuts;
}
public static List<ModelStatutFacture> getAllStatutFacture() {
    List<ModelStatutFacture> statuts = new ArrayList<>();
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    try {
        // Récupérer la connexion
        conn = getConnection();
        
        // Requête SQL pour récupérer tous les statuts
        String sql = "SELECT * FROM statut_facture";
        stmt = conn.createStatement();
        rs = stmt.executeQuery(sql);

        // Ajouter chaque statut à la liste
        while (rs.next()) {
            statuts.add(new ModelStatutFacture(rs.getInt("statut_facture_id"), rs.getString("statut")));
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        closeQuietly(rs);
        closeQuietly(stmt);
        closeQuietly(conn);
    }
    
    return statuts;
}

public static List<ModelCommande> getAllCommande() {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<ModelCommande> commandes = new ArrayList<>();
    
    try {
        // Récupérer la connexion
        conn = getConnection();
        
        // Requête SQL pour récupérer toutes les commandes
        String sql = "SELECT *"
                   + "FROM commande";
        
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();
        
        // Parcourir les résultats
        while (rs.next()) {
            ModelCommande commande = new ModelCommande();
            commande.setCommandeId(rs.getInt("commande_id"));
            commande.setDevisId(rs.getInt("devis_id"));
            
            // Récupérer le client via une méthode (à ajuster selon votre implémentation)
            ModelClient client = getClientById(rs.getInt("client_id"));
            commande.setClient(client);
            commande.setDateCommande(rs.getDate("date_commande"));
            commande.setTotalHT(rs.getBigDecimal("total_HT"));
            commande.setRemisePercentage(rs.getBigDecimal("remise_percentage"));
            commande.setTotalRemise(rs.getBigDecimal("total_remise"));
              commande.setAcomptePercentage(rs.getBigDecimal("acompte_percentage"));
            commande.setTotalAcompte(rs.getBigDecimal("total_acompte"));
            commande.setTvaPercentage(rs.getBigDecimal("tva_percentage"));
            commande.setTotalTva(rs.getBigDecimal("total_tva"));
            commande.setTotalTTC(rs.getBigDecimal("total_TTC"));
            commande.setAcomptePayed(rs.getBigDecimal("acompte_paye"));
             commande.setLeftToPaye(rs.getBigDecimal("reste_a_payer"));
            
            // Récupérer le statut via une méthode ou directement
            ModelStatutCommande statut = getStatutCommandeById(rs.getInt("statut_commande_id"));
            commande.setStatutCommande(statut);
            
            // Ajouter la commande à la liste
            commandes.add(commande);
        }
        
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        closeQuietly(rs);
        closeQuietly(pstmt);
        closeQuietly(conn);
    }
    
    return commandes;
}



public static List<ModelFacture> getAllFacture() {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    List<ModelFacture> factures = new ArrayList<>();
    
    try {
        // Récupérer la connexion
        conn = getConnection();
        
        // Requête SQL pour récupérer toutes les commandes
        String sql = "SELECT * "
                   + "FROM facture";
        
        pstmt = conn.prepareStatement(sql);
        rs = pstmt.executeQuery();
        
        // Parcourir les résultats
        while (rs.next()) {
               int factureId = rs.getInt("facture_id");
            ModelClient client = getClientById(rs.getInt("client_id"));
            Date dateFacture = rs.getDate("date_facture");

            // Récupérer les colonnes optionnelles, vérifier si elles ne sont pas nulles
                BigDecimal totalHT = rs.getBigDecimal("total_HT");     
            BigDecimal remisePercentage = rs.getBigDecimal("remise_percentage");
             BigDecimal totalRemise = rs.getBigDecimal("total_remise");
            BigDecimal tvaPercentage = rs.getBigDecimal("tva_percentage");
             BigDecimal totalTva = rs.getBigDecimal("total_tva");
            BigDecimal totalTTC = rs.getBigDecimal("total_TTC");
            ModelCommande commandeId = getCommandeById(rs.getInt("commande_id"));
            ModelStatutFacture statutFacure = getStatutFactureById(rs.getInt("statut_facture_id"));
            List<ModelLigneFacture> lignesFacture = getLignesFactureForFacture(factureId);

            // Construction de l'objet ModelDevis
            ModelFacture facture = new ModelFacture(
                factureId,
                commandeId,
                client,
                dateFacture,
                totalHT,
                    remisePercentage,
                    totalRemise,
                    tvaPercentage,
                    totalTva,
                totalTTC,
                statutFacure,
                lignesFacture
            );
               factures.add(facture);
    
        }
        
    } catch (SQLException ex) {
        ex.printStackTrace();
    } finally {
        closeQuietly(rs);
        closeQuietly(pstmt);
        closeQuietly(conn);
    }
    
    return factures;
}


public static List<ModelDevis> getAllDevis() {
    List<ModelDevis> devisList = new ArrayList<>();
    try (Connection conn = getConnection()) {
        String sqlDevis = "SELECT * FROM devis";
        PreparedStatement stmtDevis = conn.prepareStatement(sqlDevis);
        ResultSet rsDevis = stmtDevis.executeQuery();

        // Parcours des résultats de la table 'devis'
        while (rsDevis.next()) {
            int devisId = rsDevis.getInt("devis_id");
            
            ModelClient client = getClientById(rsDevis.getInt("client_id"));
            Date dateDevis = rsDevis.getDate("date_devis");

            // Récupérer les colonnes optionnelles, vérifier si elles ne sont pas nulles
            BigDecimal totalHT = rsDevis.getBigDecimal("total_HT");
            BigDecimal remisePercentage = rsDevis.getBigDecimal("remise_percentage");
             BigDecimal totalRemise = rsDevis.getBigDecimal("total_remise");
            BigDecimal acomptePercentage = rsDevis.getBigDecimal("acompte_percentage");
            BigDecimal totalAcompte = rsDevis.getBigDecimal("total_acompte");
            BigDecimal tvaPercentage = rsDevis.getBigDecimal("tva_percentage");
             BigDecimal totalTva = rsDevis.getBigDecimal("total_tva");
            BigDecimal totalTTC = rsDevis.getBigDecimal("total_TTC");

            ModelStatutDevis statut = getStatutDevisById(rsDevis.getInt("statut_devis_id"));
           // List<ModelLigneDevis> lignesDevis = getLignesDevisForDevis(devisId);

            // Construction de l'objet ModelDevis
            ModelDevis devis = new ModelDevis(
                devisId,
    
                client,
                dateDevis,
                totalHT,
                remisePercentage,
                    totalRemise,
                    acomptePercentage,
                totalAcompte,
                tvaPercentage,
                    totalTva,
                totalTTC,
                statut,
                null
            );
            devisList.add(devis);
        }
    } catch (SQLException e) {
        System.err.println("Error getting all devis: " + e.getMessage());
    }
    return devisList;
}



public static List<ModelSousLigneChassis> getSousLigneChassis(int sousligneDevisId) throws SQLException {
    List<ModelSousLigneChassis> chassisList = new ArrayList<>();
    String sql = "SELECT * FROM sousligne_chassis WHERE sousligne_devis_id = ?";
   try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {  // Initialize PreparedStatement here
        pstmt.setInt(1, sousligneDevisId);
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()) {
            ModelChassis chassis = getChassisById(rs.getInt("chassis_id"));
            ModelSousLigneChassis sousLigneChassis = new ModelSousLigneChassis(
                rs.getInt("sousligne_chassis_id"),
                rs.getInt("sousligne_devis_id"),
                chassis,
                rs.getBigDecimal("quantite_utilisee"),
                rs.getBigDecimal("prix_total")
            );
            chassisList.add(sousLigneChassis);
        }
    }
    return chassisList;
}

public static List<ModelSousLigneVitre> getSousLigneVitre(int sousligneDevisId) throws SQLException {
    List<ModelSousLigneVitre> vitreList = new ArrayList<>();
    String sql = "SELECT * FROM sousligne_vitre WHERE sousligne_devis_id = ?";
   try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {  // Initialize PreparedStatement here
        pstmt.setInt(1, sousligneDevisId);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ModelTypeVitre vitre = getTypeVitreById(rs.getInt("type_vitre_id"));
                ModelSousLigneVitre sousLigneVitre = new ModelSousLigneVitre(
                    rs.getInt("sousligne_vitre_id"),
                       rs.getInt("sousligne_devis_id"),  
                    vitre,
                    rs.getInt("quantite_utilisee"),
                    rs.getBigDecimal("prix_total")
                );
                vitreList.add(sousLigneVitre);
            }
        }
    }
    return vitreList;
}

public static List<ModelSousLigneJoint> getSousLigneJoint(int sousligneDevisId) throws SQLException {
    List<ModelSousLigneJoint> jointList = new ArrayList<>();
    String sql = "SELECT * FROM sousligne_joint WHERE sousligne_devis_id = ?";
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {  // Initialize PreparedStatement here
        pstmt.setInt(1, sousligneDevisId);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ModelJoint joint = getJointById(rs.getInt("joint_id"));
                ModelSousLigneJoint sousLigneJoint = new ModelSousLigneJoint(
                    rs.getInt("sousligne_joint_id"),
                        rs.getInt("sousligne_devis_id"),
                    joint,
                    rs.getBigDecimal("quantite_utilisee"),
                    rs.getBigDecimal("prix_total")
                );
                jointList.add(sousLigneJoint);
            }
        }
    }
    return jointList;
}

public static List<ModelSousLigneSerrure> getSousLigneSerrure(int sousligneDevisId) throws SQLException {
    List<ModelSousLigneSerrure> serrureList = new ArrayList<>();
    String sql = "SELECT * FROM sousligne_serrure WHERE sousligne_devis_id = ?";
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {  // Initialize PreparedStatement here
        pstmt.setInt(1, sousligneDevisId);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ModelSerrure serrure = getSerrureById(rs.getInt("serrure_id"));
                ModelSousLigneSerrure sousLigneSerrure = new ModelSousLigneSerrure(
                    rs.getInt("sousligne_serrure_id"),
                        rs.getInt("sousligne_devis_id"),
                    serrure,
                    rs.getInt("quantite_utilisee"),
                    rs.getBigDecimal("prix_total")
                );
                serrureList.add(sousLigneSerrure);
            }
        }
    }
    return serrureList;
}
public static List<ModelSousLignePoignee> getSousLignePoignee(int sousligneDevisId) throws SQLException {
    List<ModelSousLignePoignee> PoigneeList = new ArrayList<>();
    String sql = "SELECT * FROM sousligne_poignee WHERE sousligne_devis_id = ?";
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {  // Initialize PreparedStatement here
        pstmt.setInt(1, sousligneDevisId);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ModelPoignee poignee = getPoigneeById(rs.getInt("poignee_id"));
                ModelSousLignePoignee sousLignepoignee = new ModelSousLignePoignee(
                    rs.getInt("sousligne_poignee_id"),
                        rs.getInt("sousligne_devis_id"),
                    poignee,
                    rs.getInt("quantite_utilisee"),
                    rs.getBigDecimal("prix_total")
                );
                PoigneeList.add(sousLignepoignee);
            }
        }
    }
    return PoigneeList;
}

public static List<ModelSousLignePaumelle> getSousLignePaumelle(int sousligneDevisId) throws SQLException {
    List<ModelSousLignePaumelle> paumelleList = new ArrayList<>();
    String sql = "SELECT * FROM sousligne_paumelle WHERE sousligne_devis_id = ?";
   try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {  // Initialize PreparedStatement here
        pstmt.setInt(1, sousligneDevisId);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ModelPaumelle paumelle = getPaumelleById(rs.getInt("paumelle_id"));
                ModelSousLignePaumelle sousLignePaumelle = new ModelSousLignePaumelle(
                    rs.getInt("sousligne_paumelle_id"),
            rs.getInt("sousligne_devis_id"),

                    paumelle,
                    rs.getInt("quantite_utilisee"),
                    rs.getBigDecimal("prix_total")
                );
                paumelleList.add(sousLignePaumelle);
            }
        }
    }
    return paumelleList;
}

public static List<ModelSousLigneRoulette> getSousLigneRoulette(int sousligneDevisId) throws SQLException {
    List<ModelSousLigneRoulette> rouletteList = new ArrayList<>();
    String sql = "SELECT * FROM sousligne_roulette WHERE sousligne_devis_id = ?";
     try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {  // Initialize PreparedStatement here
        pstmt.setInt(1, sousligneDevisId);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ModelRoulette roulette = getRouletteById(rs.getInt("roulette_id"));
                ModelSousLigneRoulette sousLigneRoulette = new ModelSousLigneRoulette(
                    rs.getInt("sousligne_roulette_id"),
                        rs.getInt("sousligne_devis_id"),
                    roulette,
                    rs.getInt("quantite_utilisee"),
                    rs.getBigDecimal("prix_total")
                );
                rouletteList.add(sousLigneRoulette);
            }
        }
    }
       
    return rouletteList;
}

public static List<ModelSousLigneVis> getSousLigneVis(int sousligneDevisId) throws SQLException {
    List<ModelSousLigneVis> visList = new ArrayList<>();
    String sql = "SELECT * FROM sousligne_vis WHERE sousligne_devis_id = ?";

    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {  // Initialize PreparedStatement here

        pstmt.setInt(1, sousligneDevisId);  // Set parameter value

        try (ResultSet rs = pstmt.executeQuery()) {  // Execute the query and process the result
            while (rs.next()) {
                ModelVis vis = getVisById(rs.getInt("vis_id"));
                ModelSousLigneVis sousLigneVis = new ModelSousLigneVis(
                    rs.getInt("sousligne_vis_id"),
                    rs.getInt("sousligne_devis_id"),
                    vis,
                    rs.getInt("quantite_utilisee"),
                    rs.getBigDecimal("prix_total")
                );
                visList.add(sousLigneVis);
            }
        }
    }
    return visList;
}

public static List<ModelSousLigneLameVerre> getSousLigneLameVerre(int sousligneDevisId) throws SQLException {
    List<ModelSousLigneLameVerre> lameVerreList = new ArrayList<>();
    String sql = "SELECT * FROM sousligne_lame_verre WHERE sousligne_devis_id = ?";
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) { 
        pstmt.setInt(1, sousligneDevisId);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ModelLameVerre lameVerre = getLameVerreById(rs.getInt("lame_verre_id"));
                ModelSousLigneLameVerre sousLigneLameVerre = new ModelSousLigneLameVerre(
                    rs.getInt("sousligne_lame_verre_id"),
                    rs.getInt("sousligne_devis_id"),
                    lameVerre,
                    rs.getInt("quantite_utilisee"),
                    rs.getBigDecimal("prix_total")
                );
                lameVerreList.add(sousLigneLameVerre);
            }
        }
    }
    return lameVerreList;
}
public static List<ModelSousLigneClips> getSousLigneClips(int sousligneDevisId) throws SQLException {
    List<ModelSousLigneClips> clipsList = new ArrayList<>();
    String sql = "SELECT * FROM sousligne_clips WHERE sousligne_devis_id = ?";
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, sousligneDevisId);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ModelClips clips = getClipsById(rs.getInt("clips_id"));
                ModelSousLigneClips sousLigneClips = new ModelSousLigneClips(
                    rs.getInt("sousligne_clips_id"),
                    rs.getInt("sousligne_devis_id"),
                    clips,
                    rs.getInt("quantite_utilisee"),
                    rs.getBigDecimal("prix_total")
                );
                clipsList.add(sousLigneClips);
            }
        }
    }
    return clipsList;
}
public static List<ModelSousLigneRivette> getSousLigneRivette(int sousligneDevisId) throws SQLException {
    List<ModelSousLigneRivette> rivetteList = new ArrayList<>();
    String sql = "SELECT * FROM sousligne_rivette WHERE sousligne_devis_id = ?";
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, sousligneDevisId);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ModelRivette rivette = getRivetteById(rs.getInt("rivette_id"));
                ModelSousLigneRivette sousLigneRivette = new ModelSousLigneRivette(
                    rs.getInt("sousligne_rivette_id"),
                    rs.getInt("sousligne_devis_id"),
                    rivette,
                    rs.getInt("quantite_utilisee"),
                    rs.getBigDecimal("prix_total")
                );
                rivetteList.add(sousLigneRivette);
            }
        }
    }
    return rivetteList;
}
public static List<ModelSousLigneOperateur> getSousLigneOperateur(int sousligneDevisId) throws SQLException {
    List<ModelSousLigneOperateur> operateurList = new ArrayList<>();
    String sql = "SELECT * FROM sousligne_operateur WHERE sousligne_devis_id = ?";
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, sousligneDevisId);
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                ModelOperateur operateur = getOperateurById(rs.getInt("operateur_id"));
                ModelSousLigneOperateur sousLigneOperateur = new ModelSousLigneOperateur(
                    rs.getInt("sousligne_operateur_id"),
                    rs.getInt("sousligne_devis_id"),
                    operateur,
                    rs.getInt("quantite_utilisee"),
                    rs.getBigDecimal("prix_total")
                );
                operateurList.add(sousLigneOperateur);
            }
        }
    }
    return operateurList;
}

// Méthode pour obtenir une facture par son ID
// Méthode pour obtenir une facture par son ID

//
//// Méthode pour obtenir les lignes de facture pour une facture spécifique
//public static List<ModelLigneFacture> getLignesFactureForFacture(int factureId) throws SQLException {
//    List<ModelLigneFacture> lignesFactureList = new ArrayList<>();
//    String sqlLignesFacture = "SELECT * FROM ligne_facture WHERE facture_id = ?";
//     
//    
//    try (Connection connection = getConnection();
//         PreparedStatement statement = connection.prepareStatement(sqlLignesFacture)) {
//        
//        statement.setInt(1, factureId);
//        ResultSet resultSet = statement.executeQuery();
//        
//        while (resultSet.next()) {
//            ModelLigneFacture ligneFacture = new ModelLigneFacture();
//            ligneFacture.setLigneCommandeId(resultSet.getInt("ligne_facture_id"));
//            ligneFacture.setProduitId(resultSet.getInt("produit_id"));
//            ligneFacture.setDesignation(resultSet.getString("designation"));
//            ligneFacture.setTotalQuantite(resultSet.getInt("total_quantite"));
//            ligneFacture.setTotalPrix(resultSet.getBigDecimal("total_prix"));
//            
//            lignesFactureList.add(ligneFacture);
//        }
//    }
//    return lignesFactureList;
//}
//    


// Méthode pour obtenir les sous-lignes pour une ligne de facture spécifique
//public static List<ModelSousLigneFacture> getSousLigneForLigneFacture(int ligneFactureId) throws SQLException {
//    List<ModelSousLigneFacture> sousLigneList = new ArrayList<>();
//    String sqlSousLigne = "SELECT * FROM sous_ligne_facture WHERE ligne_facture_id = ?";
//    try (Connection conn = getConnection();
//         PreparedStatement pstmtSousLigne = conn.prepareStatement(sqlSousLigne)) {
//        pstmtSousLigne.setInt(1, ligneFactureId);
//        ResultSet rsSousLigne = pstmtSousLigne.executeQuery();
//
//        while (rsSousLigne.next()) {
//            ModelSousLigneFacture sousLigne = new ModelSousLigneFacture(
//                rsSousLigne.getInt("sousligne_facture_id"),
//                rsSousLigne.getInt("ligne_facture_id"),
//                rsSousLigne.getInt("nbre_ventaux"),
//                rsSousLigne.getInt("largeur"),
//                rsSousLigne.getInt("hauteur"),
//                rsSousLigne.getInt("quantite"),
//                rsSousLigne.getBigDecimal("prix_unitaire"),
//                rsSousLigne.getBigDecimal("prix_total"),
//                rsSousLigne.getBigDecimal("main_oeuvre"),
//                getSousLigneChassis(rsSousLigne.getInt("sousligne_facture_id")),
//                getSousLigneVitre(rsSousLigne.getInt("sousligne_facture_id")),
//                getSousLigneJoint(rsSousLigne.getInt("sousligne_facture_id")),
//                getSousLignePaumelle(rsSousLigne.getInt("sousligne_facture_id")),
//                getSousLigneRoulette(rsSousLigne.getInt("sousligne_facture_id")),
//                getSousLigneSerrure(rsSousLigne.getInt("sousligne_facture_id")),
//                getSousLignePoignee(rsSousLigne.getInt("sousligne_facture_id")),
//                getSousLigneVis(rsSousLigne.getInt("sousligne_facture_id"))
//            );
//            sousLigneList.add(sousLigne);
//        }
//    }
//    return sousLigneList;
//}

// Méthode pour obtenir un devis par son ID


public static ModelVis getVisById(int visId) {
    String sql = "SELECT * FROM vis WHERE vis_id = ?";
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
         
        stmt.setInt(1, visId);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                // Fetch related entities such as profile and fournisseur by their IDs
                ModelProfileAlu profile = getProfileById(rs.getInt("profile_alu_id"));
                ModelFournisseur fournisseur = null;
                 ModelStatutStock statut = getStatutStockById(rs.getInt("statut_stock_id"));
                // Check if fournisseur_id is not null in the result set
                int fournisseurId = rs.getInt("fournisseur_id");
                if (!rs.wasNull()) {
                    fournisseur = getFournisseurById(fournisseurId);
                }
                
                // Create and return the ModelVis object with the retrieved data
                return new ModelVis(
                    rs.getInt("vis_id"),
                    profile,
                    rs.getString("type"),
                    rs.getBigDecimal("longueur_mm"),
                    rs.getBigDecimal("prix_unitaire"),
                    rs.getInt("quantite_en_stock"),
                    fournisseur,
                        statut
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Optionally log the error using a logging framework
        System.err.println("Error fetching vis with ID: " + visId + " - " + e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
        // Handle any other potential exceptions
        System.err.println("Unexpected error fetching vis: " + e.getMessage());
    }
    
    return null; // Return null if no vis is found or in case of an error
}
public static ModelTypeProfileAlu getTypeProfileAluById(int typeId) {
    try (Connection conn = getConnection()) {
        // Requête SQL corrigée pour sélectionner les colonnes correctes
        String sql = "SELECT * FROM type_profile_alu WHERE type_profile_alu_id = ?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, typeId);
        ResultSet rs = stmt.executeQuery();
        
        if (rs.next()) {
            // Retourne un objet ModelTypeProfileAlu avec les bonnes colonnes
            return new ModelTypeProfileAlu(
                rs.getInt("type_profile_alu_id"), // ID du type de profil alu
                rs.getString("type_profile_alu")   // Nom du type de profil alu (par ex. Fixe ou Ouvrant)
            );
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return null;
}
public static List<ModelTypeProfileAlu> getAllTypeProfileAlu()  {
     List<ModelTypeProfileAlu> typeProfileAlu = new ArrayList<>();

        // Requête SQL corrigée pour sélectionner les colonnes correctes
        String sql = "SELECT * FROM type_profile_alu ";
    try (Connection connection = getConnection()) {
        
        // Exécute la requête pour récupérer tous les châssis
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            // Parcourt les résultats
            while (rs.next()) {
                // Récupère les objets liés comme le profile, fournisseur et type de profil alu
                // Crée un nouvel objet ModelChassis avec les données extraites
                ModelTypeProfileAlu type = new ModelTypeProfileAlu(
                rs.getInt("type_profile_alu_id"), // ID du type de profil alu
                rs.getString("type_profile_alu") 
                );
                typeProfileAlu.add(type);  // Ajoute chaque châssis à la liste
            }
        
         }
   } catch (Exception e) {
        // Handle exception
        e.printStackTrace(); // or log the exception
    }

    return typeProfileAlu;  // Retourne la liste des châssis
}
public static <T> List<T> getAllMaterials(String tableName, Function<ResultSet, T> mapper) {
    List<T> materials = new ArrayList<>();

    // Mise à jour dynamique avant de récupérer les données
    updateStatutStock();

    String query = "SELECT * FROM " + tableName;

    try (Connection connection = getConnection();
         PreparedStatement stmt = connection.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        // Mappe chaque ligne du résultat à un objet
        while (rs.next()) {
            materials.add(mapper.apply(rs));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return materials;
}

public static List<ModelChassis> getAllChassis() {
    return getAllMaterials("chassis", rs -> {
        try {
            // Récupère les objets liés comme le profile, fournisseur et type de profil alu
            ModelProfileAlu profile = getProfileById(rs.getInt("profile_alu_id"));
            ModelFournisseur fournisseur = getFournisseurById(rs.getInt("fournisseur_id"));
            ModelTypeProfileAlu typeProfile = getTypeProfileAluById(rs.getInt("type_profile_alu_id"));
            ModelStatutStock statut = getStatutStockById(rs.getInt("statut_stock_id"));

            // Retourne un nouvel objet ModelChassis avec les données extraites
            return new ModelChassis(
                    rs.getInt("chassis_id"),
                    profile,
                    typeProfile,
                    rs.getBigDecimal("longueur_mm"),
                    rs.getBigDecimal("prix_unitaire"),
                    rs.getBigDecimal("quantite_en_stock"),
                    fournisseur,
                    statut
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    });
}

public static void updateStatutStock() { 
    String[] tables = {"chassis", "roulette", "joint", "serrure", "type_vitre", "paumelle", "poignee", "vis","operateur","rivettes","clips"};
    try (Connection conn = DatabaseUtils.getConnection()) {
        conn.setAutoCommit(false); // Début d'une transaction

        for (String table : tables) {
            // Cas 1 : Quantité = 0 → En Rupture
            String sqlRupture = "UPDATE " + table + " " +
                                "SET statut_stock_id = 4 " +
                                "WHERE quantite_en_stock = 0 AND statut_stock_id != 4";

            // Cas 2 : Quantité entre 1 et 10 inclus → Faible
            String sqlFaible = "UPDATE " + table + " " +
                               "SET statut_stock_id = 2 " +
                               "WHERE quantite_en_stock > 0 AND quantite_en_stock <= 10 AND statut_stock_id != 2";

            // Cas 3 : Quantité > 10 → En Stock
            String sqlStock = "UPDATE " + table + " " +
                              "SET statut_stock_id = 3 " +
                              "WHERE quantite_en_stock > 10 AND statut_stock_id != 3";

            try (PreparedStatement pstmtRupture = conn.prepareStatement(sqlRupture);
                 PreparedStatement pstmtFaible = conn.prepareStatement(sqlFaible);
                 PreparedStatement pstmtStock = conn.prepareStatement(sqlStock)) {

                int rowsRupture = pstmtRupture.executeUpdate();
                int rowsFaible = pstmtFaible.executeUpdate();
                int rowsStock = pstmtStock.executeUpdate();

                if (rowsRupture > 0) {
                    System.out.println("Table " + table + ": " + rowsRupture + " ligne(s) mise(s) à jour en 'Rupture'.");
                }
                if (rowsFaible > 0) {
                    System.out.println("Table " + table + ": " + rowsFaible + " ligne(s) mise(s) à jour en 'Faible'.");
                }
                if (rowsStock > 0) {
                    System.out.println("Table " + table + ": " + rowsStock + " ligne(s) mise(s) à jour en 'En Stock'.");
                }
            }
        }

        conn.commit(); // Valide les mises à jour
    } catch (SQLException ex) {
        ex.printStackTrace();
        try (Connection conn = DatabaseUtils.getConnection()) {
            if (conn != null) {
                conn.rollback(); // Annule les modifications en cas d'erreur
            }
        } catch (SQLException rollbackEx) {
            rollbackEx.printStackTrace();
        }
    }
}



public static int getNextChassisId() {
    String query = "SELECT MAX(chassis_id) FROM chassis";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        if (rs.next()) {
            return rs.getInt(1) + 1; // Retourne le prochain ID (MAX + 1)
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
    return 1; // Retourne 1 si la table est vide ou en cas d'erreur
}
    // Method to save a ProfileAlu object to the database
public static boolean addProfileAlu(ModelProfileAlu profile) throws SQLException {
        String insertSQL = "INSERT INTO profile_alu (model, couleur) VALUES (?, ?)";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {
            
            // Set parameters for the prepared statement
            pstmt.setString(1, profile.getModel());
            pstmt.setString(2, profile.getCouleur());
            
            // Execute the insert statement and check if a row was inserted
            int rowsInserted = pstmt.executeUpdate();
            return rowsInserted > 0; // Return true if insertion was successful

        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Propagate the exception to handle it in the calling method
        }
    }
public static String addChassis(ModelChassis chassis) {
     chassis.setChassisId(getNextChassisId()); 
    String query = "INSERT INTO chassis (chassis_id, profile_alu_id, type_profile_alu_id, longueur_mm, prix_unitaire, quantite_en_stock, fournisseur_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, chassis.getChassisId()); // ID unique
        stmt.setInt(2, chassis.getProfile().getProfileAluId());
        stmt.setInt(3, chassis.getType().getTypeProfileAluId());
        stmt.setBigDecimal(4, chassis.getLongueurBarre());
        stmt.setBigDecimal(5, chassis.getPrixUnitaire());
        stmt.setBigDecimal(6, chassis.getQuantiteEnStock());
        stmt.setInt(7, chassis.getFournisseur().getFournisseurId());

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "FAILURE";

    } catch (SQLException e) {
        e.printStackTrace();
        return "ERROR: " + e.getMessage();
    }
}
public static String updateChassis(ModelChassis chassis) {
    String query = "UPDATE chassis SET profile_alu_id = ?, type_profile_alu_id = ?, longueur_mm = ?, prix_unitaire = ?, quantite_en_stock = ?, fournisseur_id = ? WHERE chassis_id = ?";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, chassis.getProfile().getProfileAluId());
        stmt.setInt(2, chassis.getType().getTypeProfileAluId());
        stmt.setBigDecimal(3, chassis.getLongueurBarre());
        stmt.setBigDecimal(4, chassis.getPrixUnitaire());
        stmt.setBigDecimal(5, chassis.getQuantiteEnStock());
        stmt.setInt(6, chassis.getFournisseur().getFournisseurId());
        stmt.setInt(7, chassis.getChassisId());

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "FAILURE";

    } catch (SQLException e) {
        e.printStackTrace();
        return "ERROR: " + e.getMessage();
    }
}
public static boolean deleteChassis(int chassisId) throws SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        // Établir la connexion
        conn = DatabaseUtils.getConnection();
        conn.setAutoCommit(false); // Désactiver l'auto-commit pour gérer la transaction

        String sql = "DELETE FROM chassis WHERE chassis_id = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, chassisId);

        int rowsAffected = stmt.executeUpdate();
        conn.commit(); // Commit de la transaction si l'opération est réussie

        return rowsAffected > 0; // Retourne vrai si une ligne a été supprimée

    } catch (SQLException e) {
        // Rollback de la transaction en cas d'erreur
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        e.printStackTrace();
        return false;

    } finally {
        // Restauration de l'auto-commit et fermeture des ressources
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

public static List<ModelTypeVitre> getAllVitre() throws Exception {
        return getAllMaterials("type_vitre", rs -> {
        try {
                // Récupère les objets liés comme le profile, fournisseur et type de profil alu
              ModelVitrage vitrage = getVitrageById(rs.getInt("type_vitrage_id"));
                ModelFournisseur fournisseur = getFournisseurById(rs.getInt("fournisseur_id"));
              ModelStatutStock statut = getStatutStockById(rs.getInt("statut_stock_id"));
                // Crée un nouvel objet ModelChassis avec les données extraites
           
    
     return new ModelTypeVitre(
                    rs.getInt("type_vitre_id"),
                    vitrage,
                    rs.getString("type_de_verre"),
                     rs.getBigDecimal("epaisseur_mm"),
                    rs.getBigDecimal("prix_unitaire"),
                    rs.getInt("quantite_en_stock"),
                    fournisseur,
                        statut
                );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    });
  
}
public static String addVitre(ModelTypeVitre vitre) {
       vitre.setTypeVitreId(getNextVitreId());
    String query = "INSERT INTO type_vitre (type_vitre_id, type_vitrage_id, type_de_verre, epaisseur_mm,  prix_unitaire,quantite_en_stock, fournisseur_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, vitre.getTypeVitreId()); // ID unique pour la vitre
        stmt.setInt(2, vitre.getVitrage().getVitrageId());
        stmt.setString(3, vitre.getType());
        stmt.setBigDecimal(4, vitre.getEpaisseur());
        stmt.setBigDecimal(5, vitre.getPrixUnitaire());
        stmt.setInt(6, vitre.getQuantiteEnStock());
        stmt.setInt(7, vitre.getFournisseur().getFournisseurId());

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "FAILURE";

    } catch (SQLException e) {
        e.printStackTrace();
        return "ERROR: " + e.getMessage();
    }
}
public static String updateVitre(ModelTypeVitre vitre) {
    String query = "UPDATE type_vitre SET type_vitrage_id = ?, type_de_verre = ?, epaisseur_mm = ?, prix_unitaire = ?, quantite_en_stock = ?, fournisseur_id = ? WHERE type_vitre_id = ?";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        // ID unique pour la vitre
        stmt.setInt(1, vitre.getVitrage().getVitrageId());
        stmt.setString(2, vitre.getType());
        stmt.setBigDecimal(3, vitre.getEpaisseur());
        stmt.setBigDecimal(4, vitre.getPrixUnitaire());
        stmt.setInt(5, vitre.getQuantiteEnStock());
        stmt.setInt(6, vitre.getFournisseur().getFournisseurId());
         stmt.setInt(7, vitre.getTypeVitreId());

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "FAILURE";

    } catch (SQLException e) {
        e.printStackTrace();
        return "ERROR: " + e.getMessage();
    }
}
public static boolean deleteVitre(int vitreId) throws SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        // Établir la connexion
        conn = DatabaseUtils.getConnection();
        conn.setAutoCommit(false); // Désactiver l'auto-commit pour gérer la transaction

        String sql = "DELETE FROM type_vitre WHERE type_vitre_id = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, vitreId);

        int rowsAffected = stmt.executeUpdate();
        conn.commit(); // Commit de la transaction si l'opération est réussie

        return rowsAffected > 0; // Retourne vrai si une ligne a été supprimée

    } catch (SQLException e) {
        // Rollback de la transaction en cas d'erreur
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        e.printStackTrace();
        return false;

    } finally {
        // Restauration de l'auto-commit et fermeture des ressources
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

public static List<ModelJoint> getAllJoint() throws Exception {
     return getAllMaterials("joint", rs -> {
        try {
            // Récupère les objets liés comme le profile, fournisseur et type de profil alu
            ModelProfileAlu profile = getProfileById(rs.getInt("profile_alu_id"));
                ModelFournisseur fournisseur = getFournisseurById(rs.getInt("fournisseur_id"));
                  ModelStatutStock statut = getStatutStockById(rs.getInt("statut_stock_id"));

            // Retourne un nouvel objet ModelChassis avec les données extraites
            return  new ModelJoint(
                    rs.getInt("Joint_id"),
                    profile,
                    rs.getString("type"),
                    rs.getBigDecimal("prix_unitaire"),
                    rs.getBigDecimal("quantite_en_stock"),
                    fournisseur,
                  statut
                );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    });
}
  
   
public static String addJoint(ModelJoint joint) {
         joint.setJointId(getNextJointId());
    String query = "INSERT INTO joint (joint_id, profile_alu_id, type, prix_unitaire, quantite_en_stock, fournisseur_id) VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, joint.getJointId()); // ID unique pour le joint
        stmt.setInt(2, joint.getProfile().getProfileAluId());
        stmt.setString(3, joint.getTypeJoint());
        stmt.setBigDecimal(4, joint.getPrixUnitaire());
        stmt.setBigDecimal(5, joint.getQuantiteEnStock());
        stmt.setInt(6, joint.getFournisseur().getFournisseurId());
       

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "FAILURE";

    } catch (SQLException e) {
        e.printStackTrace();
        return "ERROR: " + e.getMessage();
    }
}
public static String updateJoint(ModelJoint joint) {
    String query = "UPDATE joint SET profile_alu_id = ?, type = ?, prix_unitaire = ?, quantite_en_stock = ?, fournisseur_id = ? WHERE joint_id = ?";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

     
        stmt.setInt(1, joint.getProfile().getProfileAluId());
        stmt.setString(2, joint.getTypeJoint());
        stmt.setBigDecimal(3, joint.getPrixUnitaire());
        stmt.setBigDecimal(4, joint.getQuantiteEnStock());
        stmt.setInt(5, joint.getFournisseur().getFournisseurId());
         stmt.setInt(6, joint.getJointId()); // ID unique pour le joint

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "FAILURE";

    } catch (SQLException e) {
        e.printStackTrace();
        return "ERROR: " + e.getMessage();
    }
}
public static boolean deleteJoint(int jointId) throws SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        // Établir la connexion
        conn = DatabaseUtils.getConnection();
        conn.setAutoCommit(false); // Désactiver l'auto-commit pour gérer la transaction

        String sql = "DELETE FROM joint WHERE joint_id = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, jointId);

        int rowsAffected = stmt.executeUpdate();
        conn.commit(); // Commit de la transaction si l'opération est réussie

        return rowsAffected > 0; // Retourne vrai si une ligne a été supprimée

    } catch (SQLException e) {
        // Rollback de la transaction en cas d'erreur
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        e.printStackTrace();
        return false;

    } finally {
        // Restauration de l'auto-commit et fermeture des ressources
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

public static List<ModelRoulette> getAllRoulette() throws Exception {
      return getAllMaterials("roulette", rs -> {
        try {
         
                // Récupère les objets liés comme le profile, fournisseur et type de profil alu
               ModelProfileAlu profile = getProfileById(rs.getInt("profile_alu_id"));
                ModelFournisseur fournisseur = getFournisseurById(rs.getInt("fournisseur_id"));
              ModelStatutStock statut = getStatutStockById(rs.getInt("statut_stock_id"));
                // Crée un nouvel objet ModelChassis avec les données extraites
           
    
     return new ModelRoulette(
                    rs.getInt("roulette_id"),
                    profile,
                    rs.getString("type"),
                    rs.getBigDecimal("taille"),
                    rs.getBigDecimal("prix_unitaire"),
                    rs.getInt("quantite_en_stock"),
                    fournisseur,statut
                );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    });
    
}
public static String addRoulette(ModelRoulette roulette) {
          roulette.setRouletteId(getNextRouletteId()); 
    String query = "INSERT INTO roulette (roulette_id, profile_alu_id, type, taille, prix_unitaire,quantite_en_stock, fournisseur_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, roulette.getRouletteId()); // ID unique pour la roulette
        stmt.setInt(2, roulette.getProfile().getProfileAluId());
        stmt.setString(3, roulette.getTypeRoulette());
        stmt.setBigDecimal(4, roulette.getTailleRoulette());
        stmt.setBigDecimal(5, roulette.getPrixUnitaire());
        stmt.setInt(6, roulette.getQuantiteEnStock());
        stmt.setInt(7, roulette.getFournisseur().getFournisseurId());

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "FAILURE";

    } catch (SQLException e) {
        e.printStackTrace();
        return "ERROR: " + e.getMessage();
    }
}
public static String updateRoulette(ModelRoulette roulette) {
    String query = "UPDATE roulette SET profile_alu_id = ?, type = ?, taille = ?, prix_unitaire = ?, quantite_en_stock = ?,fournisseur_id = ? WHERE roulette_id = ?";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

 
        stmt.setInt(1, roulette.getProfile().getProfileAluId());
        stmt.setString(2, roulette.getTypeRoulette());
        stmt.setBigDecimal(3, roulette.getTailleRoulette());
        stmt.setBigDecimal(4, roulette.getPrixUnitaire());
        stmt.setInt(5, roulette.getQuantiteEnStock());
        stmt.setInt(6, roulette.getFournisseur().getFournisseurId());
        stmt.setInt(7, roulette.getRouletteId()); // ID unique pour la roulette
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "FAILURE";

    } catch (SQLException e) {
        e.printStackTrace();
        return "ERROR: " + e.getMessage();
    }
}
public static boolean deleteRoulette(int rouletteId) throws SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        conn = DatabaseUtils.getConnection();
        conn.setAutoCommit(false);

        String sql = "DELETE FROM roulette WHERE roulette_id = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, rouletteId);

        int rowsAffected = stmt.executeUpdate();
        conn.commit();

        return rowsAffected > 0;

    } catch (SQLException e) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        e.printStackTrace();
        return false;

    } finally {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


public static List<ModelPaumelle> getAllPaumelle() throws Exception {
     return getAllMaterials("paumelle", rs -> {
        try {
         
                // Récupère les objets liés comme le profile, fournisseur et type de profil alu
               ModelProfileAlu profile = getProfileById(rs.getInt("profile_alu_id"));
                ModelFournisseur fournisseur = getFournisseurById(rs.getInt("fournisseur_id"));
              ModelStatutStock statut = getStatutStockById(rs.getInt("statut_stock_id"));
                // Crée un nouvel objet ModelChassis avec les données extraites
           
    
     return new ModelPaumelle(
                    rs.getInt("Paumelle_id"),
                    profile,
                    rs.getString("type"),
                            rs.getString("couleur"),
                    rs.getBigDecimal("prix_unitaire"),
                    rs.getInt("quantite_en_stock"),
                    fournisseur,statut
                );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    });
}
  

public static String addPaumelle(ModelPaumelle paumelle) {
       paumelle.setPaumelleId(getNextPaumelleId()); 
    String query = "INSERT INTO paumelle (paumelle_id, profile_alu_id, type, couleur, prix_unitaire, quantite_en_stock, fournisseur_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, paumelle.getPaumelleId()); // ID unique de la paumelle
        stmt.setInt(2, paumelle.getProfile().getProfileAluId());
        stmt.setString(3, paumelle.getTypePaumelle());
        stmt.setString(4, paumelle.getCouleurPaumelle());
        stmt.setBigDecimal(5, paumelle.getPrixUnitaire());
        stmt.setInt(6, paumelle.getQuantiteEnStock());
        stmt.setInt(7, paumelle.getFournisseur().getFournisseurId());

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "FAILURE";

    } catch (SQLException e) {
        e.printStackTrace();
        return "ERROR: " + e.getMessage();
    }
}

public static void updateChasssisStock( int chassisId,BigDecimal quantityUsed) {
    String query = "UPDATE chassis SET quantite_en_stock = ? WHERE chassis_id = ?";
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setBigDecimal(1, quantityUsed);
        stmt.setInt(2, chassisId);
        stmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
        // Log the error or handle it as needed
    }
}
public static void updateRouletteStock( int rouletteId,BigDecimal quantityUsed) {
    String query = "UPDATE roulette SET quantite_en_stock = ? WHERE roulette_id = ?";
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setBigDecimal(1, quantityUsed);
        stmt.setInt(2, rouletteId);
        stmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
        // Log the error or handle it as needed
    }
}
public static void updatePaumelleStock( int paumelleId,BigDecimal quantityUsed) {
    String query = "UPDATE paumelle SET quantite_en_stock = ? WHERE paumelle_id = ?";
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setBigDecimal(1, quantityUsed);
        stmt.setInt(2, paumelleId);
        stmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
        // Log the error or handle it as needed
    }
}
public static void updateSerrureStock( int serrureId,BigDecimal quantityUsed) {
    String query = "UPDATE serrure SET quantite_en_stock = ? WHERE serrure_id = ?";
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setBigDecimal(1, quantityUsed);
        stmt.setInt(2, serrureId);
        stmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
        // Log the error or handle it as needed
    }
}
public static void updateJointStock( int jointId,BigDecimal quantityUsed) {
    String query = "UPDATE joint SET quantite_en_stock = ? WHERE joint_id = ?";
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setBigDecimal(1, quantityUsed);
        stmt.setInt(2, jointId);
        stmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
        // Log the error or handle it as needed
    }
}
public static void updateVisStock( int visId,BigDecimal quantityUsed) {
    String query = "UPDATE vis SET quantite_en_stock = ? WHERE vis_id = ?";
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setBigDecimal(1, quantityUsed);
        stmt.setInt(2, visId);
        stmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
        // Log the error or handle it as needed
    }
}
public static void updatePoigneeStock( int poigneeId,BigDecimal quantityUsed) {
    String query = "UPDATE poignee SET quantite_en_stock = ? WHERE poignee_id = ?";
    try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setBigDecimal(1, quantityUsed);
        stmt.setInt(2, poigneeId);
        stmt.executeUpdate();
    } catch (SQLException ex) {
        ex.printStackTrace();
        // Log the error or handle it as needed
    }
}
public static String updatePaumelle(ModelPaumelle paumelle) {
    String query = "UPDATE paumelle SET profile_alu_id = ?, type = ?, couleur = ?, prix_unitaire = ?, quantite_en_stock = ?,fournisseur_id = ? WHERE paumelle_id = ?";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

      stmt.setInt(1, paumelle.getProfile().getProfileAluId());
        stmt.setString(2, paumelle.getTypePaumelle());
        stmt.setString(3, paumelle.getCouleurPaumelle());
        stmt.setBigDecimal(4, paumelle.getPrixUnitaire());
        stmt.setInt(5, paumelle.getQuantiteEnStock());
        stmt.setInt(6, paumelle.getFournisseur().getFournisseurId());
       stmt.setInt(7, paumelle.getPaumelleId()); // ID unique de la paumelle
       
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "FAILURE";

    } catch (SQLException e) {
        e.printStackTrace();
        return "ERROR: " + e.getMessage();
    }
}
public static boolean deletePaumelle(int paumelleId) throws SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        conn = DatabaseUtils.getConnection();
        conn.setAutoCommit(false);

        String sql = "DELETE FROM paumelle WHERE paumelle_id = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, paumelleId);

        int rowsAffected = stmt.executeUpdate();
        conn.commit();

        return rowsAffected > 0;

    } catch (SQLException e) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        e.printStackTrace();
        return false;

    } finally {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

public static List<ModelSerrure> getAllSerrure() throws Exception {
    
      return getAllMaterials("serrure", rs -> {
        try {
         
                // Récupère les objets liés comme le profile, fournisseur et type de profil alu
               ModelProfileAlu profile = getProfileById(rs.getInt("profile_alu_id"));
                ModelFournisseur fournisseur = getFournisseurById(rs.getInt("fournisseur_id"));
              ModelStatutStock statut = getStatutStockById(rs.getInt("statut_stock_id"));
                // Crée un nouvel objet ModelChassis avec les données extraites
           
    
     return new ModelSerrure(
                    rs.getInt("serrure_id"),
                    profile,
                    rs.getString("type"),
                         rs.getString("couleur"),
                    rs.getBigDecimal("prix_unitaire"),
                    rs.getInt("quantite_en_stock"),
                    fournisseur,statut
                );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    });
    
}
public static String addSerrure(ModelSerrure serrure) {
     serrure.setSerrureId(getNextSerrureId());
    String query = "INSERT INTO serrure (serrure_id, profile_alu_id, type, couleur, prix_unitaire, quantite_en_stock,fournisseur_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, serrure.getSerrureId()); // ID unique de la serrure
        stmt.setInt(2, serrure.getProfile().getProfileAluId());
        stmt.setString(3, serrure.getTypeSerrure());
        stmt.setString(4, serrure.getCouleurSerrure());
        stmt.setBigDecimal(5, serrure.getPrixUnitaire());
        stmt.setInt(6, serrure.getQuantiteEnStock());
        stmt.setInt(7, serrure.getFournisseur().getFournisseurId());

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "FAILURE";

    } catch (SQLException e) {
        e.printStackTrace();
        return "ERROR: " + e.getMessage();
    }
}
public static String updateSerrure(ModelSerrure serrure) {
    String query = "UPDATE serrure SET profile_alu_id = ?, type = ?, couleur = ?, prix_unitaire = ?, quantite_en_stock = ? ,fournisseur_id = ? WHERE serrure_id = ?";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

   stmt.setInt(1, serrure.getProfile().getProfileAluId());
        stmt.setString(2, serrure.getTypeSerrure());
        stmt.setString(3, serrure.getCouleurSerrure());
        stmt.setBigDecimal(4, serrure.getPrixUnitaire());
        stmt.setInt(5, serrure.getQuantiteEnStock());
        stmt.setInt(6, serrure.getFournisseur().getFournisseurId());
         stmt.setInt(7, serrure.getSerrureId()); // ID unique de la serrure
        

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "FAILURE";

    } catch (SQLException e) {
        e.printStackTrace();
        return "ERROR: " + e.getMessage();
    }
}
public static boolean deleteSerrure(int serrureId) throws SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        conn = DatabaseUtils.getConnection();
        conn.setAutoCommit(false);

        String sql = "DELETE FROM serrure WHERE serrure_id = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, serrureId);

        int rowsAffected = stmt.executeUpdate();
        conn.commit();

        return rowsAffected > 0;

    } catch (SQLException e) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        e.printStackTrace();
        return false;

    } finally {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

public static List<ModelPoignee> getAllPoignee() throws Exception {
       return getAllMaterials("poignee", rs -> {
        try {
         
                // Récupère les objets liés comme le profile, fournisseur et type de profil alu
               ModelProfileAlu profile = getProfileById(rs.getInt("profile_alu_id"));
                ModelFournisseur fournisseur = getFournisseurById(rs.getInt("fournisseur_id"));
              ModelStatutStock statut = getStatutStockById(rs.getInt("statut_stock_id"));
                // Crée un nouvel objet ModelChassis avec les données extraites
           
    
     return new ModelPoignee(
                    rs.getInt("Poignee_id"),
                    profile,
                    rs.getString("type"),
                    rs.getString("couleur"),
                    rs.getBigDecimal("prix_unitaire"),
                    rs.getInt("quantite_en_stock"),
                    fournisseur,
                     statut
                );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    });
    
   
}
public static String addPoignee(ModelPoignee poignee) {
      poignee.setPoigneeId(getNextPoigneeId()); 
    String query = "INSERT INTO poignee (poignee_id, profile_alu_id, type, couleur, prix_unitaire,quantite_en_stock, fournisseur_id) VALUES (?, ?, ?, ?, ?, ?,?)";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, poignee.getPoigneeId()); // ID unique de la poignée
        stmt.setInt(2, poignee.getProfile().getProfileAluId());
        stmt.setString(3, poignee.getType());
        stmt.setString(4, poignee.getCouleur());
        stmt.setBigDecimal(5, poignee.getPrixUnitaire());
        stmt.setInt(6, poignee.getQuantiteEnStock());
        stmt.setInt(7, poignee.getFournisseur().getFournisseurId());

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "FAILURE";

    } catch (SQLException e) {
        e.printStackTrace();
        return "ERROR: " + e.getMessage();
    }
}
public static String updatePoignee(ModelPoignee poignee) {
    String query = "UPDATE poignee SET profile_alu_id = ?, type = ?, couleur = ?,prix_unitaire = ?, quantite_en_stock = ?, fournisseur_id = ? WHERE poignee_id = ?";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

     stmt.setInt(1, poignee.getProfile().getProfileAluId());
        stmt.setString(2, poignee.getType());
        stmt.setString(3, poignee.getCouleur());
        stmt.setBigDecimal(4, poignee.getPrixUnitaire());
        stmt.setInt(5, poignee.getQuantiteEnStock());
        stmt.setInt(6, poignee.getFournisseur().getFournisseurId());
  stmt.setInt(7, poignee.getPoigneeId()); // ID unique de la poignée
        
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "FAILURE";

    } catch (SQLException e) {
        e.printStackTrace();
        return "ERROR: " + e.getMessage();
    }
}
public static boolean deletePoignee(int poigneeId) throws SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        conn = DatabaseUtils.getConnection();
        conn.setAutoCommit(false);

        String sql = "DELETE FROM poignee WHERE poignee_id = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, poigneeId);

        int rowsAffected = stmt.executeUpdate();
        conn.commit();

        return rowsAffected > 0;

    } catch (SQLException e) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        e.printStackTrace();
        return false;

    } finally {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}


public static List<ModelVis> getAllVis() throws Exception {
   
      return getAllMaterials("vis", rs -> {
        try {
                // Récupère les objets liés comme le profile, fournisseur et type de profil alu
               ModelProfileAlu profile = getProfileById(rs.getInt("profile_alu_id"));
                ModelFournisseur fournisseur = getFournisseurById(rs.getInt("fournisseur_id"));
              ModelStatutStock statut = getStatutStockById(rs.getInt("statut_stock_id"));
                // Crée un nouvel objet ModelChassis avec les données extraites
           
    
     return new ModelVis(
                rs.getInt("vis_id"),
                profile,
                     rs.getString("type"),
                rs.getBigDecimal("longueur_mm"),
                rs.getBigDecimal("prix_unitaire"),
                rs.getInt("quantite_en_stock"),
                fournisseur,statut
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    });
}
public static String addVis(ModelVis vis) {
    vis.setVisId(getNextVisId()); 
    String query = "INSERT INTO vis (vis_id, profile_alu_id, type, longueur_mm, prix_unitaire, quantite_en_stock, fournisseur_id) VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        stmt.setInt(1, vis.getVisId());
        stmt.setInt(2, vis.getProfile().getProfileAluId());
        stmt.setString(3, vis.getTypeVis());
        stmt.setBigDecimal(4, vis.getLongueurVis());
        stmt.setBigDecimal(5, vis.getPrixUnitaire());
        stmt.setInt(6, vis.getQuantiteEnStock());
        stmt.setInt(7, vis.getFournisseur().getFournisseurId());

        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "FAILURE";

    } catch (SQLException e) {
        e.printStackTrace();
        return "ERROR: " + e.getMessage();
    }
}
public static String updateVis(ModelVis vis) {
    String query = "UPDATE vis SET profile_alu_id = ?, type = ?,longueur_mm = ?, prix_unitaire = ?, quantite_en_stock = ?, fournisseur_id = ? WHERE vis_id = ?";

    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {


        stmt.setInt(1, vis.getProfile().getProfileAluId());
        stmt.setString(2, vis.getTypeVis());
        stmt.setBigDecimal(3, vis.getLongueurVis());
        stmt.setBigDecimal(4, vis.getPrixUnitaire());
        stmt.setInt(5, vis.getQuantiteEnStock());
        stmt.setInt(6, vis.getFournisseur().getFournisseurId());
        stmt.setInt(7, vis.getVisId());
        int rowsAffected = stmt.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "FAILURE";

    } catch (SQLException e) {
        e.printStackTrace();
        return "ERROR: " + e.getMessage();
    }
}

public static boolean deleteVis(int visId) throws SQLException {
    Connection conn = null;
    PreparedStatement stmt = null;

    try {
        conn = DatabaseUtils.getConnection();
        conn.setAutoCommit(false);

        String sql = "DELETE FROM vis WHERE vis_id = ?";
        stmt = conn.prepareStatement(sql);
        stmt.setInt(1, visId);

        int rowsAffected = stmt.executeUpdate();
        conn.commit();

        return rowsAffected > 0;

    } catch (SQLException e) {
        if (conn != null) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        e.printStackTrace();
        return false;

    } finally {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.setAutoCommit(true);
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

public static ModelVis getvisId(int Id) {
     ModelVis  vis = null;
    try (Connection conn = getConnection()) {
        String sqlVis = "SELECT * FROM vis WHERE vis_id = ?";
        PreparedStatement stmtVis = conn.prepareStatement(sqlVis);
        stmtVis.setInt(1, Id);
        ResultSet rsVis = stmtVis.executeQuery();
      
        if (rsVis.next()) {
                         int visId = rsVis.getInt("vis_id");
            ModelProfileAlu profile = getProfileById(rsVis.getInt("profile_alu_id"));
             ModelStatutStock statut = getStatutStockById(rsVis.getInt("statut_stock_id"));
             String typeVis = rsVis.getString("type");
              BigDecimal tailleVis= rsVis.getBigDecimal("longueur_mm");
              BigDecimal prixUnitaire = rsVis.getBigDecimal("prix_unitaire");
            int quantiteEnStock = rsVis.getInt("quantite_en_stock");
              ModelFournisseur fournisseur = getFournisseurById(rsVis.getInt("fournisseur_id"));
            
            vis = new  ModelVis(visId, profile,typeVis,tailleVis,prixUnitaire,quantiteEnStock,fournisseur,statut);
        }
    } catch (Exception e) {
        // Handle exception
        e.printStackTrace(); // or log the exception
    }
    return vis;
}

       // Méthode pour récupérer un client par son ID
public static ModelRoulette getRouletteById(int rouletteId) {
        try (Connection conn = getConnection()) {
            // Requête SQL pour sélectionner un client par son ID
            String sql = "SELECT * FROM roulette WHERE roulette_id =?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, rouletteId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                 ModelProfileAlu profile = getProfileById(rs.getInt("profile_alu_id"));
                 ModelFournisseur fournisseur = getFournisseurById(rs.getInt("fournisseur_id"));
                    ModelStatutStock statut = getStatutStockById(rs.getInt("statut_stock_id"));
                // Création d'un objet ModelClient à partir des données de la base de données
                return new ModelRoulette(
                        rs.getInt("roulette_id"),
                        profile,
                        rs.getString("type"),
                        rs.getBigDecimal("taille"),
                        rs.getBigDecimal("prix_unitaire"),
                        rs.getInt("quantite_en_stock"),
                        fournisseur,statut
                        
                );
            }
        } catch (Exception e) {
        }
        return null;
    }
public static ModelPaumelle getPaumelleById(int paumelleId) {
    String sql = "SELECT * FROM paumelle WHERE paumelle_id = ?";
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
         
        stmt.setInt(1, paumelleId);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                // Fetch related entities such as profile and fournisseur by their IDs
                ModelProfileAlu profile = getProfileById(rs.getInt("profile_alu_id"));
                ModelFournisseur fournisseur = getFournisseurById(rs.getInt("fournisseur_id"));
                 ModelStatutStock statut = getStatutStockById(rs.getInt("statut_stock_id"));
                // Check if fournisseur_id is not null in the result set
                int fournisseurId = rs.getInt("fournisseur_id");
                if (!rs.wasNull()) {
                    fournisseur = getFournisseurById(fournisseurId);
                }
                
                // Create and return the ModelPaumelle object with the retrieved data
                return new ModelPaumelle(
                    rs.getInt("paumelle_id"),
                    profile,
                    rs.getString("type"),
                    rs.getString("couleur"),
                    rs.getBigDecimal("prix_unitaire"),
                    rs.getInt("quantite_en_stock"),
                    fournisseur,statut
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Optionally log the error using a logging framework
        System.err.println("Error fetching paumelle with ID: " + paumelleId + " - " + e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
        // Handle any other potential exceptions
        System.err.println("Unexpected error fetching paumelle: " + e.getMessage());
    }
    
    return null; // Return null if no paumelle is found or in case of an error
}
public static ModelSerrure getSerrureById(int serrureId) {
    String sql = "SELECT * FROM serrure WHERE serrure_id = ?";
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
         
        stmt.setInt(1, serrureId);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                // Fetch related entities such as profile and fournisseur by their IDs
                ModelProfileAlu profile = getProfileById(rs.getInt("profile_alu_id"));
                ModelFournisseur fournisseur = getFournisseurById(rs.getInt("fournisseur_id"));
                 ModelStatutStock statut = getStatutStockById(rs.getInt("statut_stock_id"));
                // Check if fournisseur_id is not null in the result set
                int fournisseurId = rs.getInt("fournisseur_id");
                if (!rs.wasNull()) {
                    fournisseur = getFournisseurById(fournisseurId);
                }
                
                // Create and return the ModelSerrure object with the retrieved data
                return new ModelSerrure(
                    rs.getInt("serrure_id"),
                    profile,
                    rs.getString("type"),
                    rs.getString("couleur"),
                    rs.getBigDecimal("prix_unitaire"),
                    rs.getInt("quantite_en_stock"),
                    fournisseur,statut
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Optionally log the error using a logging framework
        System.err.println("Error fetching serrure with ID: " + serrureId + " - " + e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
        // Handle any other potential exceptions
        System.err.println("Unexpected error fetching serrure: " + e.getMessage());
    }
    
    return null; // Return null if no serrure is found or in case of an error
}
public static ModelPoignee getPoigneeById(int poigneeId) {
    String sql = "SELECT * FROM poignee WHERE poignee_id = ?";
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
         
        stmt.setInt(1, poigneeId);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                // Fetch related entities such as profile and fournisseur by their IDs
                ModelProfileAlu profile = getProfileById(rs.getInt("profile_alu_id"));
               ModelFournisseur fournisseur = getFournisseurById(rs.getInt("fournisseur_id"));
                    ModelStatutStock statut = getStatutStockById(rs.getInt("statut_stock_id"));
                // Check if fournisseur_id is not null in the result set
                int fournisseurId = rs.getInt("fournisseur_id");
                if (!rs.wasNull()) {
                    fournisseur = getFournisseurById(fournisseurId);
                }
                
                // Create and return the ModelPoignee object with the retrieved data
                return new ModelPoignee(
                    rs.getInt("poignee_id"),
                    profile,
                    rs.getString("type"),
                    rs.getString("couleur"),
                    rs.getBigDecimal("prix_unitaire"),
                    rs.getInt("quantite_en_stock"),
                    fournisseur,statut
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Optionally log the error using a logging framework
        System.err.println("Error fetching Poignee with ID: " + poigneeId + " - " + e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
        // Handle any other potential exceptions
        System.err.println("Unexpected error fetching Poignee: " + e.getMessage());
    }
    
    return null; // Return null if no Poignee is found or in case of an error
}


    // Méthode pour récupérer les noms des produits associés à un devis
    public static List<String> getProduitsFromLigneDevis(int devisId) throws SQLException {
        String sql = "SELECT p.nom " + // Corrigé pour inclure un espace après p.nom
                     "FROM ligne_devis ld " +
                     "JOIN produit p ON ld.produit_id = p.produit_id " +
                     "WHERE ld.devis_id = ?";

        List<String> produits = new ArrayList<>();
        
        // Connexion dans try-with-resources pour être fermée automatiquement
        try (Connection conn = getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, devisId);  // Lier l'ID du devis
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    String nomProduit = rs.getString("nom");  // Utiliser le bon nom de colonne
                    produits.add(nomProduit);  // Ajouter à la liste des produits
                }
            }
        }
        return produits;
    }
    
public static int getLigneDevisIdFromDevis(int devisId) throws SQLException {
    String sql = "SELECT ligne_devis_id FROM ligne_devis WHERE devis_id = ?";
    
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, devisId);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("ligne_devis_id");
            } else {
                throw new SQLException("Aucune ligne de devis trouvée pour le devis_id: " + devisId);
            }
        }
    }
}
public static int getSousLigneDevisIdFromLigneDevis(int ligneDevisId) throws SQLException {
    String sql = "SELECT sousligne_devis_id FROM sous_ligne_devis WHERE ligne_devis_id = ?";
    
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, ligneDevisId);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("sousligne_devis_id");
            } else {
                throw new SQLException("Aucune sous-ligne de devis trouvée pour le ligne_devis_id: " + ligneDevisId);
            }
        }
    }
}


public static List<String> getModelesCouleursFromSousLigneChassis(int sousligneDevisId) throws SQLException {
    String sql = "SELECT pa.model, pa.couleur " +
                 "FROM sousligne_chassis slc " +
                 "JOIN chassis c ON slc.chassis_id = c.chassis_id " +
                 "JOIN profile_alu pa ON c.profile_alu_id = pa.profile_alu_id " +
                 "WHERE slc.sousligne_devis_id = ?";

    List<String> modelesCouleurs = new ArrayList<>();
    
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
        pstmt.setInt(1, sousligneDevisId);
        
        try (ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                String model = rs.getString("model");
                String couleur = rs.getString("couleur");
                modelesCouleurs.add(model + " " + " ALU " + " " + couleur); // Combinaison modèle et couleur
            }
        }
    }
    
    return modelesCouleurs;
}


//   
public static ModelJoint getJointById(int jointId) {
    String sql = "SELECT * FROM joint WHERE joint_id = ?";
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
         
        stmt.setInt(1, jointId);
        
        try (ResultSet rs = stmt.executeQuery()) {
            if (rs.next()) {
                // Fetch related entities such as profile and fournisseur by their IDs
                ModelProfileAlu profile = getProfileById(rs.getInt("profile_alu_id"));
                ModelFournisseur fournisseur = getFournisseurById(rs.getInt("fournisseur_id"));
                  ModelStatutStock statut = getStatutStockById(rs.getInt("statut_stock_id"));
                // Create and return the ModelJoint object with the retrieved data
                return new ModelJoint(
                    rs.getInt("joint_id"),
                    profile,
                    rs.getString("type"),
                    rs.getBigDecimal("prix_unitaire"),
                    rs.getBigDecimal("quantite_en_stock"),
                    fournisseur,statut
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        // Optionally log the error using a logging framework
        System.err.println("Error fetching joint with ID: " + jointId + " - " + e.getMessage());
    } catch (Exception e) {
        e.printStackTrace();
        // Handle any other potential exceptions
        System.err.println("Unexpected error fetching joint: " + e.getMessage());
    }
    
    return null; // Return null if no joint is found or in case of an error
}


public static ModelSerrure getserrureId(int serrureId) {
    try (Connection conn = getConnection()) {
        // Requête SQL pour sélectionner un client par son ID
        String sql = "SELECT * FROM serrure WHERE serrure_id =?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, serrureId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
             ModelProfileAlu profile = getProfileById(rs.getInt("profile_alu_id"));
             ModelFournisseur fournisseur = getFournisseurById(rs.getInt("fournisseur_id"));
             ModelStatutStock statut = getStatutStockById(rs.getInt("statut_stock_id"));
            // Création d'un objet ModelClient à partir des données de la base de données
            return new ModelSerrure(
                    rs.getInt("serrure_id"),
                    profile,
                    rs.getString("type"),
                    rs.getString("couleur"),
                    rs.getBigDecimal("prix_unitaire"),
                    rs.getInt("quantite_en_stock"),
                    fournisseur,statut
                    
            );
        }
    } catch (Exception e) {
    }
    return null;
}

public static String addDevis(ModelDevis devis) {
        Connection conn = null;
        try {
            conn = getConnection();
            conn.setAutoCommit(false); // Start the transaction

            resetAutoIncrementIfEmptyDevis(conn); // Check and reset if necessary
            int nextDevisId = getNextDevisId(); // Get the next ID of devis

            // Add the devis
            String sqlDevis = "INSERT INTO devis (devis_id, client_id, date_devis, total_HT, remise_percentage,total_remise, acompte_percentage ,total_acompte, tva_percentage,total_tva, total_TTC, statut_devis_id) VALUES (?,?,?,?, ?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmtDevis = conn.prepareStatement(sqlDevis, Statement.RETURN_GENERATED_KEYS)) {
                pstmtDevis.setInt(1, nextDevisId);
                pstmtDevis.setInt(2, devis.getClient().getClientId());
                pstmtDevis.setDate(3, new java.sql.Date(devis.getDateDevis().getTime()));
                pstmtDevis.setBigDecimal(4, devis.getTotalHT());
                pstmtDevis.setBigDecimal(5, devis.getRemisePercentage());
                pstmtDevis.setBigDecimal(6, devis.getTotalRemise());
                pstmtDevis.setBigDecimal(7, devis.getAcomptePercentage());
                pstmtDevis.setBigDecimal(8, devis.getTotalAcompte());
                pstmtDevis.setBigDecimal(9, devis.getTvaPercentage());
                pstmtDevis.setBigDecimal(10, devis.getTotalTva());
                pstmtDevis.setBigDecimal(11, devis.getTotalTTC());
                pstmtDevis.setInt(12, devis.getStatut().getStatutId());

                int rowsAffectedDevis = pstmtDevis.executeUpdate();
                if (rowsAffectedDevis == 0) {
                    conn.rollback();
                    return "ERROR";
                }

                try (ResultSet generatedKeys = pstmtDevis.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int devisIdGenerated = generatedKeys.getInt(1);

                        // Add the devis lines
                        for (ModelLigneDevis ligneDevis : devis.getLignesDevis()) {
                            String sqlLigneDevis = "INSERT INTO ligne_devis (devis_id, produit_id, designation, total_quantite, total_prix) VALUES (?, ?, ?, ?, ?)";
                            try (PreparedStatement pstmtLigneDevis = conn.prepareStatement(sqlLigneDevis, Statement.RETURN_GENERATED_KEYS)) {
                                pstmtLigneDevis.setInt(1, devisIdGenerated);
                                pstmtLigneDevis.setInt(2, ligneDevis.getProduit().getProduitId());
                                pstmtLigneDevis.setString(3, ligneDevis.getDesignation());
                                pstmtLigneDevis.setInt(4, ligneDevis.getTotalQuantite());
                                pstmtLigneDevis.setBigDecimal(5, ligneDevis.getTotalPrix());

                                int rowsAffectedLigneDevis = pstmtLigneDevis.executeUpdate();
                                if (rowsAffectedLigneDevis == 0) {
                                    conn.rollback();
                                    return "ERROR";
                                }

                                try (ResultSet ligneGeneratedKeys = pstmtLigneDevis.getGeneratedKeys()) {
                                    if (ligneGeneratedKeys.next()) {
                                        int ligneDevisIdGenerated = ligneGeneratedKeys.getInt(1);

                                        // Add the sous-lignes de devis
                                        for (ModelSousLigneDevis sousLigne : ligneDevis.getSousLignesDevis()) {
                                           // Get the generated ligne devis ID
                           
                                        String sqlSousLigne = "INSERT INTO sous_ligne_devis (ligne_devis_id, structure_id, nbre_ventaux, largeur, hauteur, quantite, prix_unitaire, prix_total) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                                        try (PreparedStatement pstmtSousLigne = conn.prepareStatement(sqlSousLigne, Statement.RETURN_GENERATED_KEYS)) {
                                            pstmtSousLigne.setInt(1, ligneDevisIdGenerated);

                                            // Handle nullable structure_id
                                            // Vérifier si sousLigne.getStructure() n'est pas nul avant de récupérer l'ID
                                            if (sousLigne.getStructure() != null) {
                                                Integer structureId = sousLigne.getStructure().getStructureId();
                                                if (structureId != null) {
                                                    pstmtSousLigne.setInt(2, structureId);
                                                } else {
                                                    pstmtSousLigne.setNull(2, java.sql.Types.INTEGER);
                                                }
                                            } else {
                                                // Si sousLigne.getStructure() est nul, on met aussi la colonne à NULL
                                                pstmtSousLigne.setNull(2, java.sql.Types.INTEGER);
                                            }

                                            pstmtSousLigne.setInt(3, sousLigne.getnbVentaux());
                                            pstmtSousLigne.setBigDecimal(4, sousLigne.getLargeur());
                                            pstmtSousLigne.setBigDecimal(5, sousLigne.getHauteur());
                                            pstmtSousLigne.setInt(6, sousLigne.getQuantite());
                                            pstmtSousLigne.setBigDecimal(7, sousLigne.getPrixUnitaire());
                                            pstmtSousLigne.setBigDecimal(8, sousLigne.getPrixTotal());
                                          //  pstmtSousLigne.setBigDecimal(9, sousLigne.getMainOeuvre());

                                            int rowsAffectedSousLigneDevis = pstmtSousLigne.executeUpdate();
                                            if (rowsAffectedSousLigneDevis == 0) {
                                                conn.rollback();
                                                return "ERROR";
                                            }
                                             try (ResultSet sousLigneGeneratedKeys = pstmtSousLigne.getGeneratedKeys()) {
                                                if (sousLigneGeneratedKeys.next()) {
                                                    int sousLigneDevisIdGenerated = sousLigneGeneratedKeys.getInt(1);

                                                    // Insérer les châssis 
                                                for (ModelSousLigneChassis chassis :sousLigne.getChassis()) {
                                                    String sqlChassisFixe = "INSERT INTO sousligne_chassis (sousligne_devis_id, chassis_id, quantite_utilisee, prix_total) VALUES (?, ?, ?, ?)";
                                                    try (PreparedStatement pstmtChassisFixe = conn.prepareStatement(sqlChassisFixe)) {
                                                        pstmtChassisFixe.setInt(1, sousLigneDevisIdGenerated);
                                                        pstmtChassisFixe.setInt(2, chassis.getChassis().getChassisId());
                                                        pstmtChassisFixe.setBigDecimal(3, chassis.getQuantiteUtilisee());
                                                        pstmtChassisFixe.setBigDecimal(4, chassis.getPrixTotal());

                                                        int rowsAffectedChassisFixe = pstmtChassisFixe.executeUpdate();
                                                        if (rowsAffectedChassisFixe == 0) {
                                                            conn.rollback();
                                                            return "ERROR";
                                                        }
                                                    }
                                                }
                                                
                                                  // Insérer les vitres
                                                for (ModelSousLigneVitre vitre : sousLigne.getVitres()) {
                                                    String sqlVitre = "INSERT INTO sousligne_vitre (sousligne_devis_id, type_vitre_id, quantite_utilisee, prix_total) VALUES (?, ?,?,?)";
                                                    try (PreparedStatement pstmtVitre = conn.prepareStatement(sqlVitre)) {
                                                        pstmtVitre.setInt(1, sousLigneDevisIdGenerated);
                                                        pstmtVitre.setInt(2, vitre.getTypeVitre().getTypeVitreId());
                                                        pstmtVitre.setInt(3, vitre.getQuantiteUtilisee());
                                                        pstmtVitre.setBigDecimal(4, vitre.getPrixTotal());
                                                        int rowsAffectedSousLigneVitre = pstmtVitre.executeUpdate();
                                                        if (rowsAffectedSousLigneVitre == 0) {
                                                            conn.rollback();
                                                            return "ERROR";
                                                        }
                                                    }
                                                }
                                                // Insertion des joints

                                                for (ModelSousLigneJoint joint : sousLigne.getJoints()) {
                                                    String sqlJoint = "INSERT INTO sousligne_joint (sousligne_devis_id, joint_id, quantite_utilisee, prix_total) VALUES (?, ?, ?, ?)";
                                                    try (PreparedStatement pstmtJoint = conn.prepareStatement(sqlJoint)) {
                                                        pstmtJoint.setInt(1, sousLigneDevisIdGenerated);
                                                        pstmtJoint.setInt(2, joint.getJoint().getJointId());
                                                        pstmtJoint.setBigDecimal(3, joint.getQuantiteUtilisee());
                                                        pstmtJoint.setBigDecimal(4, joint.getPrixTotal());

                                                        int rowsAffectedSousLigneJoint = pstmtJoint.executeUpdate();
                                                        if (rowsAffectedSousLigneJoint == 0) {
                                                            conn.rollback();
                                                            return "ERROR";
                                                        }
                                                    }
                                                }

                                               // Insertion des serrures
                                                for (ModelSousLigneSerrure serrure : sousLigne.getSerrures()) {
                                                    String sqlSerrure = "INSERT INTO sousligne_serrure (sousligne_devis_id, serrure_id, quantite_utilisee, prix_total) VALUES (?, ?, ?, ?)";
                                                    try (PreparedStatement pstmtSerrure = conn.prepareStatement(sqlSerrure)) {
                                                        pstmtSerrure.setInt(1, sousLigneDevisIdGenerated);
                                                        pstmtSerrure.setInt(2, serrure.getSerrure().getSerrureId());
                                                        pstmtSerrure.setInt(3, serrure.getQuantiteUtilisee());
                                                        pstmtSerrure.setBigDecimal(4, serrure.getPrixTotal());
                                                        int rowsAffectedSousLigneSerrure = pstmtSerrure.executeUpdate();
                                                        if (rowsAffectedSousLigneSerrure == 0) {
                                                            conn.rollback();
                                                            return "ERROR";
                                                        }
                                                    }
                                                }
                                               // Insertion des paumelles
                  
                                                for (ModelSousLignePaumelle paumelle : sousLigne.getPaumelles()) {
                                                    // Insérer les paumelles sans doublons
                                                    String sqlPaumelle = "INSERT INTO sousligne_paumelle (sousligne_devis_id, paumelle_id, quantite_utilisee, prix_total) VALUES (?, ?, ?, ?)";
                                                    try (PreparedStatement pstmtPaumelle = conn.prepareStatement(sqlPaumelle)) {
                                                        pstmtPaumelle.setInt(1, sousLigneDevisIdGenerated);
                                                        pstmtPaumelle.setInt(2, paumelle.getPaumelle().getPaumelleId());
                                                        pstmtPaumelle.setInt(3, paumelle.getQuantiteUtilisee());
                                                        pstmtPaumelle.setBigDecimal(4, paumelle.getPrixTotal());

                                                        int rowsAffectedPaumelle = pstmtPaumelle.executeUpdate();
                                                        if (rowsAffectedPaumelle == 0) {
                                                            conn.rollback();
                                                            return "ERROR";
                                                        }
                                                    }
                                                }

                                                //roulette
                                                for (ModelSousLigneRoulette roulette : sousLigne.getRoulettes()) {
                                                    String sqlroulette = "INSERT INTO sousligne_roulette (sousligne_devis_id, roulette_id, quantite_utilisee, prix_total) VALUES (?, ?,?,?)";
                                                    try (PreparedStatement pstmtroulette = conn.prepareStatement(sqlroulette)) {
                                                        pstmtroulette.setInt(1, sousLigneDevisIdGenerated);
                                                        pstmtroulette.setInt(2, roulette.getRoulette().getRouletteId());
                                                        pstmtroulette.setInt(3, roulette.getQuantiteUtilisee());
                                                        pstmtroulette.setBigDecimal(4, roulette.getPrixTotal());
                                                        int rowsAffectedSousLigneroulette = pstmtroulette.executeUpdate();
                                                        if (rowsAffectedSousLigneroulette == 0) {
                                                            conn.rollback();
                                                            return "ERROR";
                                                        }
                                                    }
                                                }
                                               // Insertion des vis
                                                for (ModelSousLigneVis vis : sousLigne.getVis()) {
                                                    String sqlVis = "INSERT INTO sousligne_vis (sousligne_devis_id, vis_id, quantite_utilisee, prix_total) VALUES (?, ?, ?, ?)";
                                                    try (PreparedStatement pstmtVis = conn.prepareStatement(sqlVis)) {
                                                        pstmtVis.setInt(1, sousLigneDevisIdGenerated);
                                                        pstmtVis.setInt(2, vis.getVis().getVisId());
                                                        pstmtVis.setInt(3, vis.getQuantiteUtilisee());
                                                        pstmtVis.setBigDecimal(4, vis.getPrixTotal());
                                                        int rowsAffectedModelSousLigneVis = pstmtVis.executeUpdate();
                                                        if (rowsAffectedModelSousLigneVis == 0) {
                                                            conn.rollback();
                                                            return "ERROR";
                                                        }
                                                    }
                                                }
                                                //    // Insertion for poignee
                                            for (ModelSousLignePoignee poignee : sousLigne.getPoignees()) {
                                                String sqlPoignee = "INSERT INTO sousligne_poignee (sousligne_devis_id, poignee_id, quantite_utilisee, prix_total) VALUES (?, ?, ?, ?)";
                                                try (PreparedStatement pstmtPoignee = conn.prepareStatement(sqlPoignee)) {
                                                    pstmtPoignee.setInt(1, sousLigneDevisIdGenerated);
                                                    pstmtPoignee.setInt(2, poignee.getPoignee().getPoigneeId());
                                                    pstmtPoignee.setInt(3, poignee.getQuantiteUtilisee());
                                                    pstmtPoignee.setBigDecimal(4, poignee.getPrixTotal());
                                                        int rowsAffectedModelSousLignePoignee = pstmtPoignee.executeUpdate();
                                                        if (rowsAffectedModelSousLignePoignee == 0) {
                                                            conn.rollback();
                                                            return "ERROR";
                                                        }
                                                }
                                            }
                                            
                                                  for (ModelSousLigneLameVerre LameVerre : sousLigne.getLames()) {
                                                String sqlLameVerre = "INSERT INTO sousligne_lame_verre (sousligne_devis_id, lame_Verre_id, quantite_utilisee, prix_total) VALUES (?, ?, ?, ?)";
                                                try (PreparedStatement pstmtLameVerre = conn.prepareStatement(sqlLameVerre)) {
                                                    pstmtLameVerre.setInt(1, sousLigneDevisIdGenerated);
                                                    pstmtLameVerre.setInt(2, LameVerre.getLameVerre().getLameVerreId());
                                                    pstmtLameVerre.setInt(3, LameVerre.getQuantiteUtilisee());
                                                    pstmtLameVerre.setBigDecimal(4, LameVerre.getPrixTotal());
                                                        int rowsAffectedModelSousLigneLameVerre = pstmtLameVerre.executeUpdate();
                                                        if (rowsAffectedModelSousLigneLameVerre == 0) {
                                                            conn.rollback();
                                                            return "ERROR";
                                                        }
                                                }
                                            }
                                                     for (ModelSousLigneOperateur Operateur : sousLigne.getOperateurs()) {
                                                String sqlOperateur = "INSERT INTO sousligne_Operateur (sousligne_devis_id, operateur_id, quantite_utilisee, prix_total) VALUES (?, ?, ?, ?)";
                                                try (PreparedStatement pstmtOperateur = conn.prepareStatement(sqlOperateur)) {
                                                    pstmtOperateur.setInt(1, sousLigneDevisIdGenerated);
                                                    pstmtOperateur.setInt(2, Operateur.getOperateur().getOperateurId());
                                                    pstmtOperateur.setInt(3, Operateur.getQuantiteUtilisee());
                                                    pstmtOperateur.setBigDecimal(4, Operateur.getPrixTotal());
                                                        int rowsAffectedModelSousLigneOperateur = pstmtOperateur.executeUpdate();
                                                        if (rowsAffectedModelSousLigneOperateur == 0) {
                                                            conn.rollback();
                                                            return "ERROR";
                                                        }
                                                }
                                            }
                                                        for (ModelSousLigneRivette Rivette : sousLigne.getRivettes()) {
                                                String sqlRivette = "INSERT INTO sousligne_Rivette (sousligne_devis_id, rivette_id, quantite_utilisee, prix_total) VALUES (?, ?, ?, ?)";
                                                try (PreparedStatement pstmtRivette = conn.prepareStatement(sqlRivette)) {
                                                    pstmtRivette.setInt(1, sousLigneDevisIdGenerated);
                                                    pstmtRivette.setInt(2, Rivette.getRivette().getRivetteId());
                                                    pstmtRivette.setInt(3, Rivette.getQuantiteUtilisee());
                                                    pstmtRivette.setBigDecimal(4, Rivette.getPrixTotal());
                                                        int rowsAffectedModelSousLigneRivette = pstmtRivette.executeUpdate();
                                                        if (rowsAffectedModelSousLigneRivette == 0) {
                                                            conn.rollback();
                                                            return "ERROR";
                                                        }
                                                }
                                            }
                                                           for (ModelSousLigneClips Clips : sousLigne.getClips()) {
                                                String sqlClips = "INSERT INTO sousligne_Clips (sousligne_devis_id, clips_id, quantite_utilisee, prix_total) VALUES (?, ?, ?, ?)";
                                                try (PreparedStatement pstmtClips = conn.prepareStatement(sqlClips)) {
                                                    pstmtClips.setInt(1, sousLigneDevisIdGenerated);
                                                    pstmtClips.setInt(2, Clips.getClips().getClipsId());
                                                    pstmtClips.setInt(3, Clips.getQuantiteUtilisee());
                                                    pstmtClips.setBigDecimal(4, Clips.getPrixTotal());
                                                        int rowsAffectedModelSousLigneClips = pstmtClips.executeUpdate();
                                                        if (rowsAffectedModelSousLigneClips == 0) {
                                                            conn.rollback();
                                                            return "ERROR";
                                                        }
                                                }
                                            }

                                                }
                                            }
                                               
                                              

                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }

            conn.commit(); // Commit the transaction
            return "SUCCESS"; // Indicate successful addition
        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback transaction on error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            return "ERROR"; // Return error status
        } finally {
            if (conn != null) {
                try {
                    conn.close(); // Ensure the connection is closed
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }



    public static String updateDevis(ModelDevis devis) {
      Connection conn = null;
    try {
        conn = getConnection();
        conn.setAutoCommit(false); // Début de transaction
        
     
// Vérifier les modifications

            // Update the main devis record
            String sqlDevis = "UPDATE devis SET client_id = ?, date_devis = ?, total_HT = ?, remise_percentage = ?,total_remise = ?, acompte_percentage = ?, total_acompte = ?, tva_percentage = ?, total_tva = ?, total_TTC = ?, statut_devis_id = ? WHERE devis_id = ?";
            try (PreparedStatement pstmtDevis = conn.prepareStatement(sqlDevis)) {
                pstmtDevis.setInt(1, devis.getClient().getClientId());
                pstmtDevis.setDate(2, new java.sql.Date(devis.getDateDevis().getTime()));
                pstmtDevis.setBigDecimal(3, devis.getTotalHT());
                pstmtDevis.setBigDecimal(4, devis.getRemisePercentage());
                pstmtDevis.setBigDecimal(5, devis.getTotalRemise());
                pstmtDevis.setBigDecimal(6, devis.getAcomptePercentage());
                pstmtDevis.setBigDecimal(7, devis.getTotalAcompte());
                pstmtDevis.setBigDecimal(8, devis.getTvaPercentage());
                pstmtDevis.setBigDecimal(9, devis.getTotalTva());
                pstmtDevis.setBigDecimal(10, devis.getTotalTTC());
                  pstmtDevis.setInt(11, devis.getStatut().getStatutId());
                   pstmtDevis.setInt(12, devis.getDevisId());

                int rowsAffectedDevis = pstmtDevis.executeUpdate();
                if (rowsAffectedDevis == 0) {
                    conn.rollback();
                    return "ERROR: No rows updated in devis";
                }
            }
// Vérifier les modifications

// Implémentez cette méthode dans ModelDevis
    String sqlDeleteLignesDevis = "DELETE FROM ligne_devis WHERE devis_id = ?";
    try (PreparedStatement pstmtDeleteLignesDevis = conn.prepareStatement(sqlDeleteLignesDevis)) {
        pstmtDeleteLignesDevis.setInt(1, devis.getDevisId());
        pstmtDeleteLignesDevis.executeUpdate();

            resetAutoIncrementIfEmptyDevis(conn); // Reset auto-increment if necessary

            // Insert new lignes and sous-lignes
            for (ModelLigneDevis ligneDevis : devis.getLignesDevis()) {
                String sqlLigneDevis = "INSERT INTO ligne_devis (devis_id, produit_id, designation, total_quantite, total_prix) VALUES (?, ?, ?, ?, ?)";
                try (PreparedStatement pstmtLigneDevis = conn.prepareStatement(sqlLigneDevis, Statement.RETURN_GENERATED_KEYS)) {
                    pstmtLigneDevis.setInt(1, devis.getDevisId());
                    pstmtLigneDevis.setInt(2, ligneDevis.getProduit().getProduitId());
                    pstmtLigneDevis.setString(3, ligneDevis.getDesignation());
                    pstmtLigneDevis.setInt(4, ligneDevis.getTotalQuantite());
                    pstmtLigneDevis.setBigDecimal(5, ligneDevis.getTotalPrix());

                    int rowsAffectedLigneDevis = pstmtLigneDevis.executeUpdate();
                    if (rowsAffectedLigneDevis == 0) {
                        conn.rollback();
                        return "ERROR: No ligne_devis inserted";
                    }

                    // Get the generated ID of the inserted ligne_devis
                    try (ResultSet ligneGeneratedKeys = pstmtLigneDevis.getGeneratedKeys()) {
                        if (ligneGeneratedKeys.next()) {
                            int ligneDevisIdGenerated = ligneGeneratedKeys.getInt(1);

                            // Add the sous-lignes de devis
                            for (ModelSousLigneDevis sousLigne : ligneDevis.getSousLignesDevis()) {
                                // Get the generated ligne devis ID

                                String sqlSousLigne = "INSERT INTO sous_ligne_devis (ligne_devis_id, structure_id, nbre_ventaux, largeur, hauteur, quantite, prix_unitaire, prix_total) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                                try (PreparedStatement pstmtSousLigne = conn.prepareStatement(sqlSousLigne, Statement.RETURN_GENERATED_KEYS)) {
                                    pstmtSousLigne.setInt(1, ligneDevisIdGenerated);

                                    // Handle nullable structure_id
                                    // Vérifier si sousLigne.getStructure() n'est pas nul avant de récupérer l'ID
                                    if (sousLigne.getStructure() != null) {
                                        Integer structureId = sousLigne.getStructure().getStructureId();
                                        if (structureId != null) {
                                            pstmtSousLigne.setInt(2, structureId);
                                        } else {
                                            pstmtSousLigne.setNull(2, java.sql.Types.INTEGER);
                                        }
                                    } else {
                                        // Si sousLigne.getStructure() est nul, on met aussi la colonne à NULL
                                        pstmtSousLigne.setNull(2, java.sql.Types.INTEGER);
                                    }

                                    pstmtSousLigne.setInt(3, sousLigne.getnbVentaux());
                                    pstmtSousLigne.setBigDecimal(4, sousLigne.getLargeur());
                                    pstmtSousLigne.setBigDecimal(5, sousLigne.getHauteur());
                                    pstmtSousLigne.setInt(6, sousLigne.getQuantite());
                                    pstmtSousLigne.setBigDecimal(7, sousLigne.getPrixUnitaire());
                                    pstmtSousLigne.setBigDecimal(8, sousLigne.getPrixTotal());
//                                    pstmtSousLigne.setBigDecimal(9, sousLigne.getMainOeuvre());

                                    int rowsAffectedSousLigneDevis = pstmtSousLigne.executeUpdate();
                                    if (rowsAffectedSousLigneDevis == 0) {
                                        conn.rollback();
                                        return "ERROR";
                                    }
                                    try (ResultSet sousLigneGeneratedKeys = pstmtSousLigne.getGeneratedKeys()) {
                                        if (sousLigneGeneratedKeys.next()) {
                                            int sousLigneDevisIdGenerated = sousLigneGeneratedKeys.getInt(1);

                                            // Insérer les châssis 
                                            for (ModelSousLigneChassis chassis : sousLigne.getChassis()) {
                                                String sqlChassis = "INSERT INTO sousligne_chassis (sousligne_devis_id, chassis_id, quantite_utilisee, prix_total) VALUES (?, ?, ?, ?)";
                                                try (PreparedStatement pstmtChassis = conn.prepareStatement(sqlChassis)) {
                                                    pstmtChassis.setInt(1, sousLigneDevisIdGenerated);
                                                    pstmtChassis.setInt(2, chassis.getChassis().getChassisId());
                                                    pstmtChassis.setBigDecimal(3, chassis.getQuantiteUtilisee());
                                                   
                                                    pstmtChassis.setBigDecimal(4, chassis.getPrixTotal());

                                                    int rowsAffectedChassisFixe = pstmtChassis.executeUpdate();
                                                    if (rowsAffectedChassisFixe == 0) {
                                                        conn.rollback();
                                                        return "ERROR";
                                                    }
                                                }
                                            }

                                            // Insérer les vitres
                                            for (ModelSousLigneVitre vitre : sousLigne.getVitres()) {
                                                String sqlVitre = "INSERT INTO sousligne_vitre (sousligne_devis_id, type_vitre_id, quantite_utilisee, prix_total) VALUES (?, ?,?,?)";
                                                try (PreparedStatement pstmtVitre = conn.prepareStatement(sqlVitre)) {
                                                    pstmtVitre.setInt(1, sousLigneDevisIdGenerated);
                                                    pstmtVitre.setInt(2, vitre.getTypeVitre().getTypeVitreId());
                                                    pstmtVitre.setInt(3, vitre.getQuantiteUtilisee());
                                                    pstmtVitre.setBigDecimal(4, vitre.getPrixTotal());
                                                    int rowsAffectedSousLigneVitre = pstmtVitre.executeUpdate();
                                                    if (rowsAffectedSousLigneVitre == 0) {
                                                        conn.rollback();
                                                        return "ERROR";
                                                    }
                                                }
                                            }
                                            // Insertion des joints

                                            for (ModelSousLigneJoint joint : sousLigne.getJoints()) {
                                                String sqlJoint = "INSERT INTO sousligne_joint (sousligne_devis_id, joint_id, quantite_utilisee, prix_total) VALUES (?, ?, ?, ?)";
                                                try (PreparedStatement pstmtJoint = conn.prepareStatement(sqlJoint)) {
                                                    pstmtJoint.setInt(1, sousLigneDevisIdGenerated);
                                                    pstmtJoint.setInt(2, joint.getJoint().getJointId());
                                                    pstmtJoint.setBigDecimal(3, joint.getQuantiteUtilisee());
                                                    pstmtJoint.setBigDecimal(4, joint.getPrixTotal());

                                                    int rowsAffectedSousLigneJoint = pstmtJoint.executeUpdate();
                                                    if (rowsAffectedSousLigneJoint == 0) {
                                                        conn.rollback();
                                                        return "ERROR";
                                                    }
                                                }
                                            }

                                            // Insertion des serrures
                                            for (ModelSousLigneSerrure serrure : sousLigne.getSerrures()) {
                                                String sqlSerrure = "INSERT INTO sousligne_serrure (sousligne_devis_id, serrure_id, quantite_utilisee, prix_total) VALUES (?, ?, ?, ?)";
                                                try (PreparedStatement pstmtSerrure = conn.prepareStatement(sqlSerrure)) {
                                                    pstmtSerrure.setInt(1, sousLigneDevisIdGenerated);
                                                    pstmtSerrure.setInt(2, serrure.getSerrure().getSerrureId());
                                                    pstmtSerrure.setInt(3, serrure.getQuantiteUtilisee());
                                                    pstmtSerrure.setBigDecimal(4, serrure.getPrixTotal());
                                                    int rowsAffectedSousLigneSerrure = pstmtSerrure.executeUpdate();
                                                    if (rowsAffectedSousLigneSerrure == 0) {
                                                        conn.rollback();
                                                        return "ERROR";
                                                    }
                                                }
                                            }
                                            // Insertion des paumelles

                                            ///Set<ModelSousLignePaumelle> uniquePaumelles = new HashSet<>(sousLigne.getPaumelles());

                                            for (ModelSousLignePaumelle paumelle : sousLigne.getPaumelles()) {
                                                // Insérer les paumelles sans doublons
                                                String sqlPaumelle = "INSERT INTO sousligne_paumelle (sousligne_devis_id, paumelle_id, quantite_utilisee, prix_total) VALUES (?, ?, ?, ?)";
                                                try (PreparedStatement pstmtPaumelle = conn.prepareStatement(sqlPaumelle)) {
                                                    pstmtPaumelle.setInt(1, sousLigneDevisIdGenerated);
                                                    pstmtPaumelle.setInt(2, paumelle.getPaumelle().getPaumelleId());
                                                    pstmtPaumelle.setInt(3, paumelle.getQuantiteUtilisee());
                                                    pstmtPaumelle.setBigDecimal(4, paumelle.getPrixTotal());

                                                    int rowsAffectedPaumelle = pstmtPaumelle.executeUpdate();
                                                    if (rowsAffectedPaumelle == 0) {
                                                        conn.rollback();
                                                        return "ERROR";
                                                    }
                                                }
                                            }

                                            //roulette
                                            for (ModelSousLigneRoulette roulette : sousLigne.getRoulettes()) {
                                                String sqlroulette = "INSERT INTO sousligne_roulette (sousligne_devis_id, roulette_id, quantite_utilisee, prix_total) VALUES (?, ?,?,?)";
                                                try (PreparedStatement pstmtroulette = conn.prepareStatement(sqlroulette)) {
                                                    pstmtroulette.setInt(1, sousLigneDevisIdGenerated);
                                                    pstmtroulette.setInt(2, roulette.getRoulette().getRouletteId());
                                                    pstmtroulette.setInt(3, roulette.getQuantiteUtilisee());
                                                    pstmtroulette.setBigDecimal(4, roulette.getPrixTotal());
                                                    int rowsAffectedSousLigneroulette = pstmtroulette.executeUpdate();
                                                    if (rowsAffectedSousLigneroulette == 0) {
                                                        conn.rollback();
                                                        return "ERROR";
                                                    }
                                                }
                                            }
                                            // Insertion des vis
                                            for (ModelSousLigneVis vis : sousLigne.getVis()) {
                                                String sqlVis = "INSERT INTO sousligne_vis (sousligne_devis_id, vis_id, quantite_utilisee, prix_total) VALUES (?, ?, ?, ?)";
                                                try (PreparedStatement pstmtVis = conn.prepareStatement(sqlVis)) {
                                                    pstmtVis.setInt(1, sousLigneDevisIdGenerated);
                                                    pstmtVis.setInt(2, vis.getSousLigneVisId());
                                                    pstmtVis.setInt(3, vis.getQuantiteUtilisee());
                                                    pstmtVis.setBigDecimal(4, vis.getPrixTotal());
                                                    int rowsAffectedModelSousLigneVis = pstmtVis.executeUpdate();
                                                    if (rowsAffectedModelSousLigneVis == 0) {
                                                        conn.rollback();
                                                        return "ERROR";
                                                    }
                                                }
                                            }
                                            //    // Insertion for poignee
                                            for (ModelSousLignePoignee poignee : sousLigne.getPoignees()) {
                                                String sqlPoignee = "INSERT INTO sousligne_poignee (sousligne_devis_id, poignee_id, quantite_utilisee, prix_total) VALUES (?, ?, ?, ?)";
                                                try (PreparedStatement pstmtPoignee = conn.prepareStatement(sqlPoignee)) {
                                                    pstmtPoignee.setInt(1, sousLigneDevisIdGenerated);
                                                    pstmtPoignee.setInt(2, poignee.getPoignee().getPoigneeId());
                                                    pstmtPoignee.setInt(3, poignee.getQuantiteUtilisee());
                                                    pstmtPoignee.setBigDecimal(4, poignee.getPrixTotal());
                                                    int rowsAffectedModelSousLignePoignee = pstmtPoignee.executeUpdate();
                                                    if (rowsAffectedModelSousLignePoignee == 0) {
                                                        conn.rollback();
                                                        return "ERROR";
                                                    }
                                                }
                                            }
                                            
                                              for (ModelSousLigneLameVerre LameVerre : sousLigne.getLames()) {
                                                String sqlLameVerre = "INSERT INTO sousligne_lame_verre (sousligne_devis_id, lame_Verre_id, quantite_utilisee, prix_total) VALUES (?, ?, ?, ?)";
                                                try (PreparedStatement pstmtLameVerre = conn.prepareStatement(sqlLameVerre)) {
                                                    pstmtLameVerre.setInt(1, sousLigneDevisIdGenerated);
                                                    pstmtLameVerre.setInt(2, LameVerre.getLameVerre().getLameVerreId());
                                                    pstmtLameVerre.setInt(3, LameVerre.getQuantiteUtilisee());
                                                    pstmtLameVerre.setBigDecimal(4, LameVerre.getPrixTotal());
                                                        int rowsAffectedModelSousLigneLameVerre = pstmtLameVerre.executeUpdate();
                                                        if (rowsAffectedModelSousLigneLameVerre == 0) {
                                                            conn.rollback();
                                                            return "ERROR";
                                                        }
                                                }
                                            }
                                                     for (ModelSousLigneOperateur Operateur : sousLigne.getOperateurs()) {
                                                String sqlOperateur = "INSERT INTO sousligne_Operateur (sousligne_devis_id, operateur_id, quantite_utilisee, prix_total) VALUES (?, ?, ?, ?)";
                                                try (PreparedStatement pstmtOperateur = conn.prepareStatement(sqlOperateur)) {
                                                    pstmtOperateur.setInt(1, sousLigneDevisIdGenerated);
                                                    pstmtOperateur.setInt(2, Operateur.getOperateur().getOperateurId());
                                                    pstmtOperateur.setInt(3, Operateur.getQuantiteUtilisee());
                                                    pstmtOperateur.setBigDecimal(4, Operateur.getPrixTotal());
                                                        int rowsAffectedModelSousLigneOperateur = pstmtOperateur.executeUpdate();
                                                        if (rowsAffectedModelSousLigneOperateur == 0) {
                                                            conn.rollback();
                                                            return "ERROR";
                                                        }
                                                }
                                            }
                                                        for (ModelSousLigneRivette Rivette : sousLigne.getRivettes()) {
                                                String sqlRivette = "INSERT INTO sousligne_Rivette (sousligne_devis_id, rivette_id, quantite_utilisee, prix_total) VALUES (?, ?, ?, ?)";
                                                try (PreparedStatement pstmtRivette = conn.prepareStatement(sqlRivette)) {
                                                    pstmtRivette.setInt(1, sousLigneDevisIdGenerated);
                                                    pstmtRivette.setInt(2, Rivette.getRivette().getRivetteId());
                                                    pstmtRivette.setInt(3, Rivette.getQuantiteUtilisee());
                                                    pstmtRivette.setBigDecimal(4, Rivette.getPrixTotal());
                                                        int rowsAffectedModelSousLigneRivette = pstmtRivette.executeUpdate();
                                                        if (rowsAffectedModelSousLigneRivette == 0) {
                                                            conn.rollback();
                                                            return "ERROR";
                                                        }
                                                }
                                            }
                                                           for (ModelSousLigneClips Clips : sousLigne.getClips()) {
                                                String sqlClips = "INSERT INTO sousligne_Clips (sousligne_devis_id, clips_id, quantite_utilisee, prix_total) VALUES (?, ?, ?, ?)";
                                                try (PreparedStatement pstmtClips = conn.prepareStatement(sqlClips)) {
                                                    pstmtClips.setInt(1, sousLigneDevisIdGenerated);
                                                    pstmtClips.setInt(2, Clips.getClips().getClipsId());
                                                    pstmtClips.setInt(3, Clips.getQuantiteUtilisee());
                                                    pstmtClips.setBigDecimal(4, Clips.getPrixTotal());
                                                        int rowsAffectedModelSousLigneClips = pstmtClips.executeUpdate();
                                                        if (rowsAffectedModelSousLigneClips == 0) {
                                                            conn.rollback();
                                                            return "ERROR";
                                                        }
                                                }
                                            }
                                               }
}

                                        }
                                    }

                                }

                            }
                        }
                    }
                }
            

            // Commit the transaction if everything is successful
            conn.commit();
            return "SUCCESS";

        } catch (SQLException e) {
            e.printStackTrace();
            if (conn != null) {
                try {
                    conn.rollback(); // Rollback transaction on error
                } catch (SQLException rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            return "ERROR: " + e.getMessage(); // Return detailed error message
        } finally {
            if (conn != null) {
                try {
                    conn.setAutoCommit(true); // Restore default auto-commit behavior
                    conn.close(); // Close the connection
                } catch (SQLException closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }
public static String deleteFacture(int factureId) {
    String sql = "DELETE FROM facture WHERE facture_id = ?";
    try (Connection conn = getConnection();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {
         
        pstmt.setInt(1, factureId);
        int rowsAffected = pstmt.executeUpdate();

        if (rowsAffected > 0) {
            return "SUCCESS";
        } else {
            return "NOT_FOUND";
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
        return "ERROR";
    }
}

public static String deleteCommande(int commandeIdToDelete) {
    try (Connection conn = getConnection()) {
        // Étape 1: Récupérer l'ID du devis associé à la commande
        int devisId = 0;
        String selectSql = "SELECT devis_id FROM commande WHERE commande_id = ?";
        
        try (PreparedStatement selectPstmt = conn.prepareStatement(selectSql)) {
            selectPstmt.setInt(1, commandeIdToDelete);
            ResultSet rs = selectPstmt.executeQuery();
            if (rs.next()) {
                devisId = rs.getInt("devis_id");
            }
        }

        // Étape 2: Supprimer la commande
        String deleteSql = "DELETE FROM commande WHERE commande_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(deleteSql);
        pstmt.setInt(1, commandeIdToDelete);
        int rowsAffected = pstmt.executeUpdate();

        // Étape 3: Mettre à jour le statut du devis si la suppression a réussi
        if (rowsAffected > 0) {
            updateDevisStatut(devisId, 2); // Mettre à jour le statut du devis à 2 (par exemple, "En attente")
        }

        return rowsAffected > 0 ? "SUCCESS" : "ERROR";
    } catch (SQLException e) {
        e.printStackTrace(); // Affiche la trace de l'exception
        return "ERROR";
    }
}

// Méthode pour mettre à jour le statut du devis
private static void updateDevisStatut(int devisId, int newStatutId) {
    String updateSql = "UPDATE devis SET statut_devis_id = ? WHERE devis_id = ?";
    try (Connection conn = getConnection();
         PreparedStatement updatePstmt = conn.prepareStatement(updateSql)) {
        updatePstmt.setInt(1, newStatutId);
        updatePstmt.setInt(2, devisId);
        updatePstmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace(); // Affiche la trace de l'exception
    }
}

 public static String deleteDevis(int devisIdToDelete) {
    try (Connection conn = getConnection()) {
        String sql = "DELETE FROM devis WHERE devis_id = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, devisIdToDelete);
        int rowsAffected = pstmt.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "ERROR";
    } catch (SQLException e) {
        e.printStackTrace(); // Ajoutez cette ligne pour afficher la trace de l'exception
        return "ERROR";
    }
}

  public static List<ModelVitrage> retrieveAllVitrages() {
        List<ModelVitrage> vitrages = new ArrayList<>();

        String query = "SELECT vitrage_id, type FROM vitrage";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int vitrageId = resultSet.getInt("vitrage_id");
                String type = resultSet.getString("type");

                ModelVitrage vitrage = new ModelVitrage(vitrageId, type);
                vitrages.add(vitrage);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vitrages;
    }
  public static ModelVitrage getVitrageById(int vitrageId) {
    try (Connection conn = getConnection()) {
       
        String sql = "SELECT * FROM vitrage WHERE vitrage_id =?";
        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, vitrageId);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {

            return new ModelVitrage(
                    rs.getInt("vitrage_id"),
                    rs.getString("type")
           
            );
        }
    } catch (Exception e) {
    }
    return null;
}

    public static ModelTypeVitre getTypeVitreById(int typeVitreId, int typeVitrageId) {
    // Logique pour rechercher le type de vitre dans la base de données
    String query = "SELECT * FROM type_vitre WHERE type_vitre_id = ? AND type_vitrage_id = ?";
   try (Connection conn = getConnection();
         PreparedStatement  ps = conn.prepareStatement(query)) {
        ps.setInt(1, typeVitreId);
        ps.setInt(2, typeVitrageId);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                     ModelVitrage vitrage = getVitrageById (rs.getInt("type_vitrage_id"));
              ModelFournisseur fournisseur = getFournisseurById ( rs.getInt("fournisseur_id"));
               ModelStatutStock statut = getStatutStockById(rs.getInt("statut_stock_id"));
                return new ModelTypeVitre(
                    rs.getInt("type_vitre_id"),
                   vitrage,
                    rs.getString("type_de_verre"),
                    rs.getBigDecimal("epaisseur_mm"),
                    rs.getBigDecimal("prix_unitaire"),
                    rs.getInt("quantite_en_stock"),
                    fournisseur,statut
                );
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return null;  // Retourne null si aucun type de vitre n'est trouvé
}

    public static ModelTypeVitre getTypeVitreById(int typeVitreId) {
    ModelTypeVitre typeVitre = null;
    try {
        Connection conn = getConnection();
        String query = "SELECT * FROM type_vitre WHERE type_vitre_id = ?";
         PreparedStatement ps = conn.prepareStatement(query);
        ps.setInt(1, typeVitreId);
        ResultSet rs = ps.executeQuery();

        if (rs.next()) {
             ModelVitrage vitrage = getVitrageById (rs.getInt("type_vitrage_id"));
              ModelFournisseur fournisseur = getFournisseurById ( rs.getInt("fournisseur_id"));
              ModelStatutStock statut = getStatutStockById(rs.getInt("statut_stock_id"));
            String typeDeVerre = rs.getString("type_de_verre");
            BigDecimal epaisseurMm = rs.getBigDecimal("epaisseur_mm");
            BigDecimal prixUnitaire = rs.getBigDecimal("prix_unitaire");
            int quantiteEnStock = rs.getInt("quantite_en_stock");
           // int fournisseurId = rs.getInt("fournisseur_id");

            // Créer un objet ModelTypeVitre avec les valeurs récupérées
            typeVitre = new ModelTypeVitre(
                typeVitreId,
               vitrage,
                typeDeVerre,
                epaisseurMm,
                prixUnitaire,
                quantiteEnStock,
                fournisseur ,statut
            );
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Gérer les exceptions comme vous le souhaitez
    } finally {
      
    }

    return typeVitre;
}
    
    public static List<ModelLameVerre> retrieveLargeurVerre(int epaisseur, String typeverre) throws SQLException {
    List<ModelLameVerre> largeurLameList = new ArrayList<>();
    
    // Requête SQL pour récupérer les largeurs de verre en fonction de l'épaisseur et du type de verre
    String query = "SELECT * FROM lame_verre WHERE epaisseur_mm = ? AND type_verre = ?";
    
    try (Connection conn = DatabaseUtils.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        
        // Définir les paramètres de la requête
        stmt.setInt(1, epaisseur); // Filtrer par épaisseur
        stmt.setString(2, typeverre); // Filtrer par type de verre
        
        try (ResultSet rs = stmt.executeQuery()) {
            // Parcourir les résultats de la requête
           
            while (rs.next()) {
                 ModelVitrage typeVitrage = getVitrageById(rs.getInt("vitrage_id"));
               ModelStatutStock statut = getStatutStockById(rs.getInt("statut_stock_id"));

     ModelFournisseur fournisseur = getFournisseurById( rs.getInt("fournisseur_id"));
                // Créer un objet ModelLameVerre pour chaque ligne de la table
                ModelLameVerre lame = new ModelLameVerre(
                    rs.getInt("lame_verre_id"),    // ID de la lame de verre
                   typeVitrage,       // ID de vitrage
                    rs.getString("type_verre"),    // Type de verre
                    rs.getInt("epaisseur_mm"),     // Épaisseur en mm
                    rs.getBigDecimal("largeur_mm"),       // Largeur en mm
                    rs.getBigDecimal("prix_unitaire"), // Prix unitaire
                    rs.getInt("quantite_en_stock"), // Quantité en stock
                    fournisseur,
                  statut  // Statut du stock
                );
                largeurLameList.add(lame); // Ajouter l'objet à la liste
            }
        }
    }
    
    return largeurLameList; // Retourner la liste des largeurs de verre
}

public static List<ModelLameVerre> retrieveTypesEpaisseursVerre(String vitrageType) throws Exception {
    List<ModelLameVerre> typesEpaisseurs = new ArrayList<>();
    
    // Requête SQL pour filtrer par type de vitrage
    String query = "SELECT *" +
                   "FROM lame_verre lv " +
                   "JOIN vitrage v ON lv.vitrage_id = v.vitrage_id " +
                   "WHERE v.type = ?";
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        // Passer le type de vitrage en paramètre
        stmt.setString(1, vitrageType);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // Récupérer le vitrage correspondant à l'ID
                ModelVitrage typeVitrage = getVitrageById(rs.getInt("vitrage_id"));
               ModelStatutStock statut = getStatutStockById(rs.getInt("statut_stock_id"));
                // Extraire les autres données du ResultSet
                int typeVitreId = rs.getInt("lame_verre_id");
                String typeVerre = rs.getString("type_verre");
                BigDecimal largeur = rs.getBigDecimal("largeur_mm");
                int epaisseur = rs.getInt("epaisseur_mm");
                BigDecimal prixUnitaire = rs.getBigDecimal("prix_unitaire");
                int quantiteEnStock = rs.getInt("quantite_en_stock");
                int fournisseurId = rs.getInt("fournisseur_id");
                
                // Récupérer le fournisseur
                ModelFournisseur fournisseur = getFournisseurById(fournisseurId);

                // Ajouter le nouvel objet à la liste
                typesEpaisseurs.add(new ModelLameVerre(typeVitreId, typeVitrage, typeVerre, epaisseur,largeur, prixUnitaire, quantiteEnStock, fournisseur,statut));
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        throw new Exception("Error retrieving types and thickness of glass", e);
    }

    return typesEpaisseurs;
}

public static List<ModelTypeVitre> retrieveTypesEpaisseursVitrage(String vitrageType) throws Exception {
    List<ModelTypeVitre> typesEpaisseurs = new ArrayList<>();
    
    // Requête SQL pour filtrer par type de vitrage
    String query = "SELECT *" +
                   "FROM type_vitre tv " +
                   "JOIN vitrage v ON tv.type_vitrage_id = v.vitrage_id " +
                   "WHERE v.type = ?";
    
    try (Connection conn = getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {

        // Passer le type de vitrage en paramètre
        stmt.setString(1, vitrageType);

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                // Récupérer le vitrage correspondant à l'ID
                ModelVitrage typeVitrage = getVitrageById(rs.getInt("type_vitrage_id"));
 ModelStatutStock statut = getStatutStockById(rs.getInt("statut_stock_id"));
                // Extraire les autres données du ResultSet
                int typeVitreId = rs.getInt("type_vitre_id");
                String typeVerre = rs.getString("type_de_verre");
                BigDecimal epaisseur = rs.getBigDecimal("epaisseur_mm");
                BigDecimal prixUnitaire = rs.getBigDecimal("prix_unitaire");
                int quantiteEnStock = rs.getInt("quantite_en_stock");
                int fournisseurId = rs.getInt("fournisseur_id");
                
                // Récupérer le fournisseur
                ModelFournisseur fournisseur = getFournisseurById(fournisseurId);

                // Ajouter le nouvel objet à la liste
                typesEpaisseurs.add(new ModelTypeVitre(typeVitreId, typeVitrage, typeVerre, epaisseur, prixUnitaire, quantiteEnStock, fournisseur,statut));
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
        throw new Exception("Error retrieving types and thickness of glass", e);
    }

    return typesEpaisseurs;
}

 public static List<String> retrieveValuesFromTable(String tableName, String columnName) {
        List<String> values = new ArrayList<>();
        String query = "SELECT " + columnName + " FROM " + tableName;
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                values.add(rs.getString(columnName));
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Gérer l'erreur ici selon vos besoins
        }
        return values;
    }

public static List<ModelProfileAlu> retrieveAllProfileAlu() throws SQLException {
    // Query to retrieve all profiles
    String query = "SELECT *FROM profile_alu";
    
    // Create a list to store all ModelProfileAlu objects
    List<ModelProfileAlu> profiles = new ArrayList<>();
    
    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery()) {
        
        // Iterate over the result set and create ModelProfileAlu objects
        while (resultSet.next()) {
            int profileAluId = resultSet.getInt("profile_alu_id");
            String model = resultSet.getString("model");
            String couleur = resultSet.getString("couleur");
            
            // Create ModelProfileAlu object and add it to the list
            ModelProfileAlu profile = new ModelProfileAlu(profileAluId, model, couleur);
            profiles.add(profile);
        }
    }
    
    // Return the list of profiles
    return profiles;
}

   public static int getProfileAluId(String model, String couleur) {
        int profileAluId = -1;  // -1 signifie que l'ID n'a pas été trouvé
        String query = "SELECT profile_alu_id FROM profile_alu WHERE model = ? AND couleur = ?";

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            // Assigner les paramètres
            statement.setString(1, model);
            statement.setString(2, couleur);

            // Exécuter la requête
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Récupérer l'ID du profil
                    profileAluId = resultSet.getInt("profile_alu_id");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer les exceptions si nécessaire, par exemple avec une journalisation
        }

        return profileAluId;
    }

public static int getChassisTypeFixeId(int profileAluId) throws SQLException {
    // Requête SQL avec filtre sur le type de profil "Fixe" (type_profile_alu_id = 1)
    String query = "SELECT cf.chassis_id " +
                   "FROM chassis cf " +
                   "JOIN type_profile_alu tp ON cf.type_profile_alu_id = tp.type_profile_alu_id " +
                   "WHERE cf.profile_alu_id = ? AND tp.type_profile_alu_id = 1";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        // Paramètre de la requête (profileAluId)
        statement.setInt(1, profileAluId);

        // Exécution de la requête
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("chassis_id"); // Retourne l'ID du châssis trouvé
            } else {
                return -1; // Indique qu'aucun résultat n'a été trouvé
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
        throw e; // Relancer l'exception pour la gérer ailleurs si nécessaire
    }
}



public static int getChassisTypeOuvrantId(int profileAluId) throws SQLException {
    // Requête SQL avec filtre sur le type de profil "Ouvrant" (type_profile_alu_id = 2)
    String query = "SELECT co.chassis_id " +
                   "FROM chassis co " +
                    "JOIN type_profile_alu tp ON co.type_profile_alu_id = tp.type_profile_alu_id " +
                       "WHERE co.profile_alu_id = ? AND tp.type_profile_alu_id = 2";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

 // Paramètre de la requête (profileAluId)
            statement.setInt(1, profileAluId);

            // Exécution de la requête
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("chassis_id"); // Retourne l'ID du châssis trouvé
                } else {
                    return -1; // Indique qu'aucun résultat n'a été trouvé
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Relancer l'exception pour la gérer ailleurs si nécessaire
        }
    }


   public static ModelProfileAlu getProfileById(int profileId) {
   
  try (Connection conn = getConnection()) {
            // Requête SQL pour sélectionner un client par son ID
          String sql = "SELECT * FROM profile_alu WHERE profile_alu_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, profileId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                // Création d'un objet ModelClient à partir des données de la base de données
                return new ModelProfileAlu(
                        rs.getInt("profile_alu_id"),
                        rs.getString("model"),
                       rs.getString("couleur")
                      
                );
            }
        } catch (Exception e) {
        }
        return null;
    }
   
   public static ModelClips getClipsById(int clipsId) throws SQLException {
    String query = "SELECT * " +
                   "FROM clips WHERE clips_id = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
         
        statement.setInt(1, clipsId);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int id = resultSet.getInt("clips_id");
                ModelProfileAlu profileAlu = getProfileById(resultSet.getInt("profile_alu_id"));
                ModelFournisseur fournisseur = getFournisseurById(resultSet.getInt("fournisseur_id"));
                ModelStatutStock statut = getStatutStockById(resultSet.getInt("statut_stock_id"));
                String type = resultSet.getString("type");
                BigDecimal prixUnitaire = resultSet.getBigDecimal("prix_unitaire");
                int quantiteEnStock = resultSet.getInt("quantite_en_stock");

                return new ModelClips(id, profileAlu, type, prixUnitaire, quantiteEnStock, fournisseur, statut);
            } else {
                return null; // Aucun clip trouvé pour cet ID
            }
        }
    }
}
   public static String addClips(ModelClips clip) throws SQLException {
    String query = "INSERT INTO clips (profile_alu_id, type, prix_unitaire, quantite_en_stock, fournisseur_id, statut_stock_id) VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, clip.getProfile().getProfileAluId());
        statement.setString(2, clip.getType());
        statement.setBigDecimal(3, clip.getPrixUnitaire());
        statement.setInt(4, clip.getQuantiteEnStock());
        statement.setInt(5, clip.getFournisseur().getFournisseurId());
        statement.setInt(6, clip.getStatutStock().getStatutId());
  int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "FAILURE";

    } catch (SQLException e) {
        e.printStackTrace();
        return "ERROR: " + e.getMessage();
    }
}
public static List<ModelClips> getAllClips() throws SQLException {
  
    
     return getAllMaterials("clips", rs -> {
        try {
     return new ModelClips(
                rs.getInt("clips_id"),
                getProfileById(rs.getInt("profile_alu_id")),
                rs.getString("type"),
                rs.getBigDecimal("prix_unitaire"),
                rs.getInt("quantite_en_stock"),
                getFournisseurById(rs.getInt("fournisseur_id")),
                getStatutStockById(rs.getInt("statut_stock_id"))
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    });
}
public static void deleteClips(int clipsId) throws SQLException {
    String query = "DELETE FROM clips WHERE clips_id = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, clipsId);
        statement.executeUpdate();
    }
}
public static String updateClips(ModelClips clip) throws SQLException {
    String query = "UPDATE clips SET profile_alu_id = ?, type = ?, prix_unitaire = ?, quantite_en_stock = ?, fournisseur_id = ?, statut_stock_id = ? WHERE clips_id = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
        statement.setInt(1, clip.getProfile().getProfileAluId());
        statement.setString(2, clip.getType());
        statement.setBigDecimal(3, clip.getPrixUnitaire());
        statement.setInt(4, clip.getQuantiteEnStock());
        statement.setInt(5, clip.getFournisseur().getFournisseurId());
        statement.setInt(6, clip.getStatutStock().getStatutId());
        statement.setInt(7, clip.getClipsId());

       int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "FAILURE";

    } catch (SQLException e) {
        e.printStackTrace();
        return "ERROR: " + e.getMessage();
    }
}
public static ModelRivette getRivetteById(int rivetteId) throws SQLException {
    String query = "SELECT * " +
                   "FROM rivettes WHERE rivette_id = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
         
        statement.setInt(1, rivetteId);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int id = resultSet.getInt("rivette_id");
                ModelProfileAlu profileAlu = getProfileById(resultSet.getInt("profile_alu_id"));
                ModelFournisseur fournisseur = getFournisseurById(resultSet.getInt("fournisseur_id"));
                ModelStatutStock statut = getStatutStockById(resultSet.getInt("statut_stock_id"));
                String type = resultSet.getString("type");
                BigDecimal prixUnitaire = resultSet.getBigDecimal("prix_unitaire");
                int quantiteEnStock = resultSet.getInt("quantite_en_stock");

                return new ModelRivette(id, profileAlu, type, prixUnitaire, quantiteEnStock, fournisseur, statut);
            } else {
                return null; // Aucune rivette trouvée pour cet ID
            }
        }
    }
}
public static String addRivette(ModelRivette rivette) throws SQLException {
    String query = "INSERT INTO rivettes (profile_alu_id, type, prix_unitaire, quantite_en_stock, fournisseur_id, statut_stock_id) " +
                   "VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setInt(1, rivette.getProfile().getProfileAluId());
        statement.setString(2, rivette.getType());
        statement.setBigDecimal(3, rivette.getPrixUnitaire());
        statement.setInt(4, rivette.getQuantiteEnStock());
        statement.setInt(5, rivette.getFournisseur().getFournisseurId());
        statement.setInt(6, rivette.getStatutStock().getStatutId());

      int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "FAILURE";

    } catch (SQLException e) {
        e.printStackTrace();
        return "ERROR: " + e.getMessage();
    }
}
public static List<ModelRivette> getAllRivettes() throws SQLException {
    return getAllMaterials("rivettes", rs -> {
        try {
     return new ModelRivette(
                rs.getInt("rivette_id"),
                getProfileById(rs.getInt("profile_alu_id")),
                rs.getString("type"),
                rs.getBigDecimal("prix_unitaire"),
                rs.getInt("quantite_en_stock"),
                getFournisseurById(rs.getInt("fournisseur_id")),
                getStatutStockById(rs.getInt("statut_stock_id"))
            );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    });

}
public static void deleteRivette(int rivetteId) throws SQLException {
    String query = "DELETE FROM rivettes WHERE rivette_id = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setInt(1, rivetteId);
        statement.executeUpdate();
    }
}
public static String updateRivette(ModelRivette rivette) throws SQLException {
    String query = "UPDATE rivettes SET profile_alu_id = ?, type = ?, prix_unitaire = ?, quantite_en_stock = ?, fournisseur_id = ?, statut_stock_id = ? " +
                   "WHERE rivette_id = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setInt(1, rivette.getProfile().getProfileAluId());
        statement.setString(2, rivette.getType());
        statement.setBigDecimal(3, rivette.getPrixUnitaire());
        statement.setInt(4, rivette.getQuantiteEnStock());
        statement.setInt(5, rivette.getFournisseur().getFournisseurId());
        statement.setInt(6, rivette.getStatutStock().getStatutId());
        statement.setInt(7, rivette.getRivetteId());
  int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "FAILURE";

    } catch (SQLException e) {
        e.printStackTrace();
        return "ERROR: " + e.getMessage();
    }
}


public static ModelOperateur getOperateurById(int operateurId) throws SQLException {
    String query = "SELECT * " +
                   "FROM operateur WHERE operateur_id = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
         
        statement.setInt(1, operateurId);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int id = resultSet.getInt("operateur_id");
                ModelProfileAlu profileAlu = getProfileById(resultSet.getInt("profile_alu_id"));
                ModelFournisseur fournisseur = getFournisseurById(resultSet.getInt("fournisseur_id"));
                ModelStatutStock statut = getStatutStockById(resultSet.getInt("statut_stock_id"));
                String type = resultSet.getString("type");
                BigDecimal prixUnitaire = resultSet.getBigDecimal("prix_unitaire");
                int quantiteEnStock = resultSet.getInt("quantite_en_stock");

                return new ModelOperateur(id, profileAlu, type, prixUnitaire, quantiteEnStock, fournisseur, statut);
            } else {
                return null; // Aucun opérateur trouvé pour cet ID
            }
        }
    }
}
public static String addOperateur(ModelOperateur operateur) throws SQLException {
    String query = "INSERT INTO operateur (profile_alu_id, type, prix_unitaire, quantite_en_stock, fournisseur_id, statut_stock_id) " +
                   "VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setInt(1, operateur.getProfile().getProfileAluId());
        statement.setString(2, operateur.getType());
        statement.setBigDecimal(3, operateur.getPrixUnitaire());
        statement.setInt(4, operateur.getQuantiteEnStock());
        statement.setInt(5, operateur.getFournisseur().getFournisseurId());
        statement.setInt(6, operateur.getStatutStock().getStatutId());

   int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "FAILURE";

    } catch (SQLException e) {
        e.printStackTrace();
        return "ERROR: " + e.getMessage();
    }
}
public static List<ModelOperateur> getAllOperateurs() throws SQLException {
     return getAllMaterials("operateur", rs -> {
        try {
     return new ModelOperateur(
              
                rs.getInt("operateur_id"),
                getProfileById(rs.getInt("profile_alu_id")),
                rs.getString("type"),
                rs.getBigDecimal("prix_unitaire"),
                rs.getInt("quantite_en_stock"),
                getFournisseurById(rs.getInt("fournisseur_id")),
                getStatutStockById(rs.getInt("statut_stock_id"))
                );
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    });
   
}
public static void deleteOperateur(int operateurId) throws SQLException {
    String query = "DELETE FROM operateur WHERE operateur_id = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setInt(1, operateurId);
        statement.executeUpdate();
    }
}
public static String updateOperateur(ModelOperateur operateur) throws SQLException {
    String query = "UPDATE operateur SET profile_alu_id = ?, type = ?, prix_unitaire = ?, quantite_en_stock = ?, fournisseur_id = ?, statut_stock_id = ? " +
                   "WHERE operateur_id = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setInt(1, operateur.getProfile().getProfileAluId());
        statement.setString(2, operateur.getType());
        statement.setBigDecimal(3, operateur.getPrixUnitaire());
        statement.setInt(4, operateur.getQuantiteEnStock());
        statement.setInt(5, operateur.getFournisseur().getFournisseurId());
        statement.setInt(6, operateur.getStatutStock().getStatutId());
        statement.setInt(7, operateur.getOperateurId());

     int rowsAffected = statement.executeUpdate();
        return rowsAffected > 0 ? "SUCCESS" : "FAILURE";

    } catch (SQLException e) {
        e.printStackTrace();
        return "ERROR: " + e.getMessage();
    }
}


public static ModelLameVerre getLameVerreById(int lameVerreId) throws SQLException {
    String query = "SELECT * " +
                   "FROM lame_verre WHERE lame_verre_id = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setInt(1, lameVerreId);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                int id = resultSet.getInt("lame_verre_id");
                ModelVitrage vitrage = getVitrageById(resultSet.getInt("vitrage_id"));
                String typeVerre = resultSet.getString("type_verre");
                int epaisseur = resultSet.getInt("epaisseur_mm");
                BigDecimal largeur = resultSet.getBigDecimal("largeur_mm");
                BigDecimal prixUnitaire = resultSet.getBigDecimal("prix_unitaire");
                int quantiteEnStock = resultSet.getInt("quantite_en_stock");
                ModelFournisseur fournisseur = getFournisseurById(resultSet.getInt("fournisseur_id"));
                ModelStatutStock statutStock = getStatutStockById(resultSet.getInt("statut_stock_id"));

                return new ModelLameVerre(id, vitrage, typeVerre, epaisseur, largeur, prixUnitaire, quantiteEnStock, fournisseur, statutStock);
            } else {
                return null; // Aucun verre trouvé pour cet ID
            }
        }
    }
}
public static void addLameVerre(ModelLameVerre lameVerre) throws SQLException {
    String query = "INSERT INTO lame_verre (vitrage_id, type_verre, epaisseur_mm, largeur_mm, prix_unitaire, quantite_en_stock, fournisseur_id, statut_stock_id) " +
                   "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setInt(1, lameVerre.getVitrage().getVitrageId());
        statement.setString(2, lameVerre.getTypeVerre());
        statement.setInt(3, lameVerre.getEpaisseur());
        statement.setBigDecimal(4, lameVerre.getLargeur());
        statement.setBigDecimal(5, lameVerre.getPrixUnitaire());
        statement.setInt(6, lameVerre.getQuantiteEnStock());
        statement.setInt(7, lameVerre.getFournisseur().getFournisseurId());
        statement.setInt(8, lameVerre.getStatutStock().getStatutId());

        statement.executeUpdate();
    }
}
public static List<ModelLameVerre> getAllLamesVerres() throws SQLException {
    String query = "SELECT * FROM lame_verre";
    List<ModelLameVerre> lamesVerres = new ArrayList<>();

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query);
         ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {
            int id = resultSet.getInt("lame_verre_id");
            ModelVitrage vitrage = getVitrageById(resultSet.getInt("vitrage_id"));
            String typeVerre = resultSet.getString("type_verre");
            int epaisseur = resultSet.getInt("epaisseur_mm");
            BigDecimal largeur = resultSet.getBigDecimal("largeur_mm");
            BigDecimal prixUnitaire = resultSet.getBigDecimal("prix_unitaire");
            int quantiteEnStock = resultSet.getInt("quantite_en_stock");
            ModelFournisseur fournisseur = getFournisseurById(resultSet.getInt("fournisseur_id"));
            ModelStatutStock statutStock = getStatutStockById(resultSet.getInt("statut_stock_id"));

            lamesVerres.add(new ModelLameVerre(id, vitrage, typeVerre, epaisseur, largeur, prixUnitaire, quantiteEnStock, fournisseur, statutStock));
        }
    }

    return lamesVerres;
}
public static void deleteLameVerre(int lameVerreId) throws SQLException {
    String query = "DELETE FROM lame_verre WHERE lame_verre_id = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setInt(1, lameVerreId);

        statement.executeUpdate();
    }
}
public static void updateLameVerre(ModelLameVerre lameVerre) throws SQLException {
    String query = "UPDATE lame_verre SET " +
                   "vitrage_id = ?, type_verre = ?, epaisseur_mm = ?, largeur_mm = ?, prix_unitaire = ?, quantite_en_stock = ?, fournisseur_id = ?, statut_stock_id = ? " +
                   "WHERE lame_verre_id = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setInt(1, lameVerre.getVitrage().getVitrageId());
        statement.setString(2, lameVerre.getTypeVerre());
        statement.setInt(3, lameVerre.getEpaisseur());
        statement.setBigDecimal(4, lameVerre.getLargeur());
        statement.setBigDecimal(5, lameVerre.getPrixUnitaire());
        statement.setInt(6, lameVerre.getQuantiteEnStock());
        statement.setInt(7, lameVerre.getFournisseur().getFournisseurId());
        statement.setInt(8, lameVerre.getStatutStock().getStatutId());
        statement.setInt(9, lameVerre.getLameVerreId());

        statement.executeUpdate();
    }
}

public static ModelChassis getChassisById(int chassisId) throws SQLException {
    String query = "SELECT * " +
                   "FROM chassis WHERE chassis_id = ?";
    
    try (Connection connection = getConnection(); // Ensure you have a method getConnection()
         PreparedStatement statement = connection.prepareStatement(query)) {
         
        statement.setInt(1, chassisId);
        
        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                // Assuming profile_alu_id is a valid column
                   int id = resultSet.getInt("chassis_id");
                ModelProfileAlu profileAlu =  getProfileById(resultSet.getInt("profile_alu_id"));
                ModelFournisseur fournisseur = getFournisseurById(resultSet.getInt("fournisseur_id"));
                ModelTypeProfileAlu type = getTypeProfileAluById(resultSet.getInt("type_profile_alu_id"));
               ModelStatutStock statut = getStatutStockById(resultSet.getInt("statut_stock_id"));
                BigDecimal longueur = resultSet.getBigDecimal("longueur_mm");
                BigDecimal prixUnitaire = resultSet.getBigDecimal("prix_unitaire");
              BigDecimal quantiteEnStock = resultSet.getBigDecimal("quantite_en_stock");
                
                return new ModelChassis(id, profileAlu, type, longueur, prixUnitaire, quantiteEnStock, fournisseur,statut);
            } else {
                return null; // No chassis found for this ID
            }
        }
    }
}


    
  public static int getJointId(String model, String couleur) throws SQLException {
    String query = "SELECT j.joint_id " +
                   "FROM joint j " +
                   "JOIN profile_alu pa ON j.profile_alu_id = pa.profile_alu_id " +
                   "WHERE pa.model = ? AND pa.couleur = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, model);
        statement.setString(2, couleur);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("joint_id");
            } else {
            
                return -1;
            }
        }
    }
}
  
  
  
public static int getSerrureId(String model, String couleur) throws SQLException {
    String query = "SELECT s.serrure_id " +
                   "FROM serrure s " +
                   "JOIN profile_alu pa ON s.profile_alu_id = pa.profile_alu_id " +
                   "WHERE pa.model = ? AND pa.couleur = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, model);
        statement.setString(2, couleur);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("serrure_id");
            } else {
              
                return -1;
            }
        }
    }
}
public static Integer getRouletteId(String model, String couleur) throws SQLException {
    String query = "SELECT r.roulette_id " +
                   "FROM roulette r " +
                   "JOIN profile_alu pa ON r.profile_alu_id = pa.profile_alu_id " +
                   "WHERE pa.model = ? AND pa.couleur = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, model);
        statement.setString(2, couleur);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("roulette_id");
            } else {
              
                return null;
            }
        }
    }
}

public static Integer getClipsId(String model, String couleur) throws SQLException {
    String query = "SELECT c.clips_id " +
                   "FROM clips c " +
                   "JOIN profile_alu pa ON c.profile_alu_id = pa.profile_alu_id " +
                   "WHERE pa.model = ? AND pa.couleur = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, model);
        statement.setString(2, couleur);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("clips_id");
            } else {
                return null;
            }
        }
    }
}
public static Integer getRivetteId(String model, String couleur) throws SQLException {
    String query = "SELECT r.rivette_id " +
                   "FROM rivettes r " +
                   "JOIN profile_alu pa ON r.profile_alu_id = pa.profile_alu_id " +
                   "WHERE pa.model = ? AND pa.couleur = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, model);
        statement.setString(2, couleur);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("rivette_id");
            } else {
                return null;
            }
        }
    }
}
public static Integer getOperateurId(String model, String couleur) throws SQLException {
    String query = "SELECT o.operateur_id " +
                   "FROM operateur o " +
                   "JOIN profile_alu pa ON o.profile_alu_id = pa.profile_alu_id " +
                   "WHERE pa.model = ? AND pa.couleur = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, model);
        statement.setString(2, couleur);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("operateur_id");
            } else {
                return null;
            }
        }
    }
}

public static Integer getPaumelleId(String model, String couleur) throws SQLException {
    String query = "SELECT p.paumelle_id " +
                   "FROM paumelle p " +
                   "JOIN profile_alu pa ON p.profile_alu_id = pa.profile_alu_id " +
                   "WHERE pa.model = ? AND pa.couleur = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, model);
        statement.setString(2, couleur);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("paumelle_id");
            } else {
              
                return null;
            }
        }
    }
}
public static int getVisId(String model, String couleur) throws SQLException {
    String query = "SELECT v.vis_id " +
                   "FROM vis v " +
                   "JOIN profile_alu pa ON v.profile_alu_id = pa.profile_alu_id " +
                   "WHERE pa.model = ? AND pa.couleur = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, model);
        statement.setString(2, couleur);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("vis_id");
            } else {
             
                return -1;
            }
        }
    }
}
public static int getPoigneeId(String model, String couleur) throws SQLException {
    String query = "SELECT p.poignee_id " +
                   "FROM poignee p " +
                   "JOIN profile_alu pa ON p.profile_alu_id = pa.profile_alu_id " +
                   "WHERE pa.model = ? AND pa.couleur = ?";

    try (Connection connection = getConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {

        statement.setString(1, model);
        statement.setString(2, couleur);

        try (ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) {
                return resultSet.getInt("poignee_id");
            } else {
             
                return -1;
            }
        }
    }
}

public static  List<String> getProduitsByType(String typeProduit) {
        List<String> produits = new ArrayList<>();
        String query = "SELECT p.nom FROM produit p JOIN type_produit tp ON p.type_produit_id = tp.type_produit_id WHERE tp.type = ?";
        
         try (Connection conn = getConnection();
             PreparedStatement statement =  conn.prepareStatement(query)) {
            
            statement.setString(1, typeProduit);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    produits.add(resultSet.getString("nom"));
                }
            }
        } catch (Exception e) {
        }
        return produits;
    }
}


 