/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Views;

import Components.Message;
import Components.PanelCover;
import Components.PanelLoading;
import Components.PanelLoginAndRegister;
import ConnexionDB.DatabaseUtils;
import Models.ModelUser;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.fonts.roboto.FlatRobotoFont;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;
import com.formdev.flatlaf.themes.FlatMacLightLaf;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class Connexion extends javax.swing.JFrame {

    private Dashboard dashboard;
    private MigLayout layout;
    private PanelCover cover;
    private PanelLoading loading;
    private PanelLoginAndRegister loginAndRegister;
    private final double addSize = 30;
    private final double coverSize = 46;
    private final double loginSize = 60;

    public Connexion() {
        initComponents();
        init();
      setIconImage(new ImageIcon(getClass().getResource("/Icon/kintana.png")).getImage()); // Définir l'icône
    }

    private void init() {

        layout = new MigLayout("fill,insets 0");
        cover = new PanelCover();
        loading = new PanelLoading();
        ActionListener eventRegister = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                register();
            }
        };
        ActionListener eventLogin = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }

        };

        loginAndRegister = new PanelLoginAndRegister(eventRegister, eventLogin);
        bg.setLayout(layout);
        bg.setLayer(loading, JLayeredPane.POPUP_LAYER);
        bg.add(loading, "pos 0 0 100% 100%");
        bg.add(cover, "width " + coverSize + "%, pos 0al 0 n 100%");
        bg.add(loginAndRegister, "width " + loginSize + "%, pos 1al 0 n 100%");
    }

    private void register() {
        // Retrieve user inputs from the registration fields
        String username = loginAndRegister.getRegisterUsername();
        String email = loginAndRegister.getRegisterEmail();
        String password = loginAndRegister.getRegisterPassword();

        // Validate inputs
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            showMessage(Message.MessageType.ERROR, "Veuillez remplir tous les champs");
            return;
        }
        if (!loginAndRegister.isValidUsername(username)) {
            showMessage(Message.MessageType.ERROR, "Le nom d'utilisateur doit avoir au moins 5 caractères et ne contenir que des lettres, chiffres, underscores et espaces");
            return;
        }
        if (!loginAndRegister.isValidEmail(email)) {
            showMessage(Message.MessageType.ERROR, "Format d'email invalide");
            return;
        }
        if (!loginAndRegister.isValidPassword(password)) {
            showMessage(Message.MessageType.ERROR, "Le mot de passe doit contenir au moins 8 caractères et des caractères spéciaux");
            return;
        }

        ModelUser newUser = new ModelUser(0, username, email, password);
        String result = DatabaseUtils.registerUser(newUser);

        switch (result) {
            case "USERNAME_EXISTS":
                showMessage(Message.MessageType.ERROR, "Nom d'utilisateur déjà existant");
                loginAndRegister.clearRegisterFields(); // Clear fields after error
                break;
            case "EMAIL_EXISTS":
                showMessage(Message.MessageType.ERROR, "Adresse e-mail déjà existante");
                loginAndRegister.clearRegisterFields(); // Clear fields after error
                break;
            case "ERROR":
                showMessage(Message.MessageType.ERROR, "Erreur lors de l'inscription");
                loginAndRegister.clearRegisterFields(); // Clear fields after error
                break;
            case "SUCCESS":
                showMessage(Message.MessageType.SUCCESS, "Inscription réussie");
                loginAndRegister.clearRegisterFields(); // Clear fields after success
                loginAndRegister.showLoginPanel(); // Switch to login panel
                break;
        }
    }

    private void login() {
        String email = loginAndRegister.getLoginEmail();
        String password = loginAndRegister.getLoginPassword();

        // Vérifiez si les champs sont vides
        if (email.isEmpty() || password.isEmpty()) {
            showMessage(Message.MessageType.ERROR, "Veuillez remplir tous les champs");
            return;
        }
            // Vérifiez d'abord si l'utilisateur existe dans la base de données
        ModelUser user = DatabaseUtils.validateLogin(email, password);
        if (user != null) {
            // L'utilisateur est inscrit, affichez le tableau de bord
            showDashboard(user);
        } else {
            // Vérifiez si l'email est conforme à la norme internationale
            if (!loginAndRegister.isValidEmail(email)) {
                showMessage(Message.MessageType.ERROR, "Format d'email invalide");
                loginAndRegister.clearLoginFields();
            } else {
                // L'utilisateur n'est pas inscrit, affichez un message pour l'inviter à s'inscrire
                showMessage(Message.MessageType.INFO, "Vous n'êtes pas encore inscrit. Veuillez vous inscrire d'abord.");
                loginAndRegister.clearLoginFields();
            }
        }
    }

    private void showDashboard(ModelUser user) {
 // Dashboard dashboard = new Dashboard(user);       dashboard.setVisible(true);
        this.dispose(); // Hide the current login/register window
    }

    private void showMessage(Message.MessageType messageType, String message) {
        Message ms = new Message();
        ms.showMessage(messageType, message);
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void begin() {
                if (!ms.isShow()) {
                    bg.add(ms, "pos 0.5al -30", 0); //  Insert to bg fist index 0
                    ms.setVisible(true);
                    bg.repaint();
                }
            }

            @Override
            public void timingEvent(float fraction) {
                float f;
                if (ms.isShow()) {
                    f = 40 * (1f - fraction);
                } else {
                    f = 40 * fraction;
                }
                layout.setComponentConstraints(ms, "pos 0.5al " + (int) (f - 30));
                bg.repaint();
                bg.revalidate();
            }

            @Override
            public void end() {
                if (ms.isShow()) {
                    bg.remove(ms);
                    bg.repaint();
                    bg.revalidate();
                } else {
                    ms.setShow(true);
                }
            }
        };
        Animator animator = new Animator(300, target);
        animator.setResolution(0);
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        animator.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    animator.start();
                } catch (InterruptedException e) {
                    System.err.println(e);
                }
            }
        }).start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        bg = new javax.swing.JLayeredPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Kintana Service | Connexion");

        bg.setBackground(new java.awt.Color(255, 255, 255));
        bg.setOpaque(true);

        javax.swing.GroupLayout bgLayout = new javax.swing.GroupLayout(bg);
        bg.setLayout(bgLayout);
        bgLayout.setHorizontalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 975, Short.MAX_VALUE)
        );
        bgLayout.setVerticalGroup(
            bgLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 539, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bg)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

//    public static void main(String args[]) {
//    FlatRobotoFont.install();
//        FlatLaf.registerCustomDefaultsSource("Proprieties");
//     //FlatDarculaLaf.setup();
//      //FlatLightLaf.setup();
//     //FlatIntelliJLaf.setup();
//     FlatMacLightLaf.setup();
//        UIManager.put("defaultFont", new Font(FlatRobotoFont.FAMILY, Font.PLAIN, 13));
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Connexion().setVisible(true);
//            }
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLayeredPane bg;
    // End of variables declaration//GEN-END:variables

}
