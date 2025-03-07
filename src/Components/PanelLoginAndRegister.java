/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Components;


import com.formdev.flatlaf.FlatClientProperties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author brici_6ul2f65
 */
public class PanelLoginAndRegister extends javax.swing.JLayeredPane {

    private JTextField txtLoginEmail;
    private JPasswordField txtLoginPassword;

    private JTextField txtRegisterUsername;
    private JTextField txtRegisterEmail;
    private JPasswordField txtRegisterPass;
    private RoundedButton cmd;
    private RoundedButton cmdRegister;


    public PanelLoginAndRegister(ActionListener eventRegister, ActionListener eventLogin) {
        initComponents();
        initRegister(eventRegister);
        initLogin(eventLogin);
 
        login.setVisible(true);
        register.setVisible(false);
      

    }
  
    private void initLogin(ActionListener eventLogin) {
        login.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        // Ajouter un espace vertical en haut pour descendre les éléments

        JLabel label = new JLabel("Connexion");
        label.setFont(new Font("sansserif", Font.BOLD, 30));
        label.setForeground(new Color(33, 14, 6));
        login.add(label, "gapbottom 20,gaptop 80");

        txtLoginEmail = new JTextField();
       
       //txtLoginEmail.setBackground(new Color(240, 240, 240));
     //   txtLoginEmail.setOpaque(false);
      // txtLoginEmail. setForeground(Color.decode("#7A8C8D"));
      //  txtLoginEmail.setFont(new java.awt.Font("sansserif", Font.PLAIN, 13));
           txtLoginEmail.setFont(new java.awt.Font("sansserif",Font.PLAIN, 13));
          txtLoginEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "example@mail.com");
        txtLoginEmail.setPreferredSize(new Dimension(txtLoginEmail.getPreferredSize().width, 44)); 
        txtLoginEmail.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new ImageIcon(getClass().getResource("/Icon/icons8-mail-22.png")));
        login.add(txtLoginEmail, "w 60%");

        
        txtLoginPassword = new JPasswordField();
    //txtLoginPassword.setBackground(new Color(240, 240, 240));
     //  txtLoginPassword. setForeground(Color.decode("#7A8C8D"));
        //txtLoginPassword.setFont(new java.awt.Font("sansserif",Font.PLAIN, 13));
           txtLoginPassword.setFont(new java.awt.Font("sansserif",Font.PLAIN, 13));
       txtLoginPassword.setPreferredSize(new Dimension(txtLoginEmail.getPreferredSize().width, 44)); 
        txtLoginPassword.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Password");
        
             txtLoginPassword.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true;"
                + "showCapsLock:true");   
        login.add(txtLoginPassword, "w 60%");

        // Button for forgot password
        JLabel cmdForget = new JLabel("Mot de passe oublié ?");
        cmdForget.setForeground(new Color(0, 120, 215));
        cmdForget.setFont(new Font("sansserif", Font.BOLD, 12));
        cmdForget.setCursor(new Cursor(Cursor.HAND_CURSOR));
        login.add(cmdForget, " gapbottom 10, gapleft 180");

        // Augmenter la marge gauche pour décaler le texte à droite
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Add your action listener code here
            }

        });
        // Adjusting alignment to be more to the right
        cmd = new RoundedButton("SE CONNECTER", new Color(0, 0, 0), new Color(0, 0, 0));
        cmd.setForeground(new Color(250, 250, 250));
        cmd.addActionListener(eventLogin);
      
        login.add(cmd, "w 40%, h 40");
         

        // Ajout de la ligne horizontale
        JSeparator separator = new JSeparator();
        login.add(separator, "w 50%");

        JLabel signupPromptLabel = new JLabel("Vous n'avez pas de compte ?");
        signupPromptLabel.setForeground(Color.BLACK);
        login.add(signupPromptLabel, " split 2, gapleft 100,gapbottom 120");

        // Using JLabel instead of JButton for "Sign Up"
        JLabel signupLabel = new JLabel("S'inscrire");
        signupLabel.setForeground(new Color(0, 120, 215));
        signupLabel.setFont(new Font("sansserif", Font.BOLD, 12));
        signupLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showRegisterPanel();
            }

        });
        login.add(signupLabel, " gapright 100 ,gapbottom 120");

    }

    private void initRegister(ActionListener eventRegister) {
        register.setLayout(new MigLayout("wrap", "push[center]push", "push[]25[]10[]10[]25[]push"));
        JLabel label = new JLabel("Inscription");
        label.setFont(new Font("sansserif", Font.BOLD, 30));
        label.setForeground(new Color(33, 14, 6));
        register.add(label, "gapbottom 20, gaptop 80, align center");

        txtRegisterUsername = new JTextField();
           txtRegisterUsername.setFont(new java.awt.Font("sansserif",Font.PLAIN, 13));
                  txtRegisterUsername.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Nom Utilisateur");
        txtRegisterUsername.setPreferredSize(new Dimension(txtRegisterUsername.getPreferredSize().width, 44)); 
        txtRegisterUsername.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new ImageIcon(getClass().getResource("/Icon/user.png")));
        
        register.add(txtRegisterUsername, "w 60%");

        txtRegisterEmail = new JTextField();
         txtRegisterEmail.setFont(new java.awt.Font("sansserif",Font.PLAIN, 13));
        txtRegisterEmail.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Email");
        txtRegisterEmail.setPreferredSize(new Dimension(txtRegisterEmail.getPreferredSize().width, 44)); 
        txtRegisterEmail.putClientProperty(FlatClientProperties.TEXT_FIELD_TRAILING_ICON, new ImageIcon(getClass().getResource("/Icon/icons8-mail-22.png")));
        
        register.add(txtRegisterEmail, "w 60%");

        txtRegisterPass = new JPasswordField();
       //txtLoginPassword.setBackground(new Color(240, 240, 240));
      // txtRegisterPass. setForeground(Color.decode("#7A8C8D"));
        txtRegisterPass.setFont(new java.awt.Font("sansserif",Font.PLAIN, 13));
        txtRegisterPass.putClientProperty(FlatClientProperties.STYLE, ""
                + "showRevealButton:true;"
                + "showCapsLock:true");  
        txtRegisterPass.putClientProperty(FlatClientProperties.PLACEHOLDER_TEXT, "Password");
        txtRegisterPass.setPreferredSize(new Dimension(txtRegisterPass.getPreferredSize().width, 44)); 
      
        register.add(txtRegisterPass, "w 60%");

        cmdRegister = new RoundedButton("S'INSCRIRE", new Color(0, 0, 0), new Color(0, 0, 0));
        cmdRegister.setBackground(new Color(0, 0, 0));
        cmdRegister.setForeground(new Color(250, 250, 250));
        cmdRegister.addActionListener(eventRegister);
    
        register.add(cmdRegister, "w 40%, h 40");

        // Ajout de la ligne horizontale
        JSeparator separator = new JSeparator();
        register.add(separator, "w 50%");

        JLabel loginPromptLabel = new JLabel("Vous avez déjà un compte ?");
        loginPromptLabel.setForeground(Color.BLACK);
        register.add(loginPromptLabel, " split 2, gapleft 100,gapbottom 100");

        // Using JLabel instead of JButton for "Sign Up"
        JLabel loginLabel = new JLabel("Se connecter");
        loginLabel.setForeground(new Color(0, 120, 215));
        loginLabel.setFont(new Font("sansserif", Font.BOLD, 12));
        loginLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showLoginPanel();
            }
        });
        register.add(loginLabel, " gapright 100 ,gapbottom 100");

    }

    public boolean isValidUsername(String username) {
        // Ajoutez ici votre logique de validation du nom d'utilisateur (ex: longueur minimale, caractères autorisés, etc.)
        return username.length() >= 5 && username.matches("[a-zA-Z0-9_ ]+");
    }

    public boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean isValidPassword(String password) {
        // Ajoutez ici votre logique de validation du mot de passe (ex: longueur minimale, caractères spéciaux, etc.)
        return password.length() >= 8 && password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*");
    }

    public String getRegisterUsername() {
        return txtRegisterUsername.getText();
    }

    public String getRegisterEmail() {
        return txtRegisterEmail.getText();
    }

    public String getRegisterPassword() {
        return new String(txtRegisterPass.getPassword());
    }

    public void clearRegisterFields() {
        txtRegisterUsername.setText("");
        txtRegisterEmail.setText("");
        txtRegisterPass.setText("");
    }

    public void showRegisterPanel() {
        login.setVisible(false);
        register.setVisible(true);
    }

    public String getLoginEmail() {
        return txtLoginEmail.getText();
    }

    public String getLoginPassword() {
        return new String(txtLoginPassword.getPassword()); 
    }

    public void clearLoginFields() {
        txtLoginEmail.setText("");
        txtLoginPassword.setText("");
    }

    public void showLoginPanel() {
        login.setVisible(true);
        register.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        login = new javax.swing.JPanel();
        register = new javax.swing.JPanel();

        setLayout(new java.awt.CardLayout());

        login.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout loginLayout = new javax.swing.GroupLayout(login);
        login.setLayout(loginLayout);
        loginLayout.setHorizontalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        loginLayout.setVerticalGroup(
            loginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(login, "card2");

        register.setBackground(new java.awt.Color(255, 255, 255));

        javax.swing.GroupLayout registerLayout = new javax.swing.GroupLayout(register);
        register.setLayout(registerLayout);
        registerLayout.setHorizontalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        registerLayout.setVerticalGroup(
            registerLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        add(register, "card3");
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel login;
    private javax.swing.JPanel register;
    // End of variables declaration//GEN-END:variables
}
