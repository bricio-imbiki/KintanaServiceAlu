/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Components;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

/**
 *
 * @author brici_6ul2f65
 */
public class PanelCover extends javax.swing.JPanel {
 private Image backgroundImage;
    /**
     * Creates new form PanelCover
     */
    public PanelCover() {
        initComponents();
        setOpaque(false);
       backgroundImage = new ImageIcon(getClass().getResource("/Icon/MEN.png")).getImage();
    }

 @Override
   protected void paintComponent(Graphics g) {
        // Appeler la méthode de peinture de la super classe
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;

        // Créer un dégradé
        GradientPaint gra = new GradientPaint(0, 0, Color.decode("#1CB5E0"), 0, getHeight(), Color.decode("#000046"));
        g2.setPaint(gra);
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Dessiner l'image avec un fond transparent
        if (backgroundImage != null) {
            // Créer un composite alpha pour dessiner l'image
            g2.setComposite(AlphaComposite.SrcOver);
            g2.drawImage(backgroundImage, 0, 100, getWidth(), getHeight() - 200, this);
        }

        // Ajouter le texte "KINTANA ALU" au-dessus de l'image
        g2.setComposite(AlphaComposite.SrcOver);  // Pour s'assurer que le texte ne soit pas transparent
      g2.setColor(new Color(224, 224, 224));
  
        g2.setFont(new Font("sansserif", Font.BOLD, 36));  // Police et taille du texte

        // Dessiner le texte au centre horizontalement, au-dessus de l'image
        String text = "KINTANA SERVICE";
        int textWidth = g2.getFontMetrics().stringWidth(text);
        int x = (getWidth() - textWidth) / 2;
        int y = 70;  // Position verticale du texte
        g2.drawString(text, x, y);
            g2.setFont(new Font("sansserif", Font.BOLD, 20)); // Police plus petite
    String text1 = "MENUISERIE ALUMINIUM";
    int text1Width = g2.getFontMetrics().stringWidth(text1);
    int x1 = (getWidth() - text1Width) / 2;
    int y1 = y + 30; // Position en dessous du texte principal
    g2.drawString(text1, x1, y1);
        
    }

   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

 

    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
