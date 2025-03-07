/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Components;

import Models.ModelChassis;
import Models.ModelClips;
import Models.ModelCommande;
import Models.ModelDevis;
import Models.ModelFacture;
import Models.ModelJoint;
import Models.ModelLameVerre;
import Models.ModelOperateur;
import Models.ModelPaumelle;
import Models.ModelPoignee;
import Models.ModelRivette;
import Models.ModelRoulette;
import Models.ModelSerrure;
import Models.ModelTypeVitre;
import Models.ModelVis;
import java.awt.Color;
import java.awt.Graphics;


/**
 *
 * @author brici_6ul2f65
 */
public final class CellStatus extends javax.swing.JPanel {

    public CellStatus() {
        initComponents(); 
    }

    public CellStatus(ModelDevis devis) {
        initComponents();
        setStatutDevis(devis);  
    }

    public CellStatus(ModelCommande commande ) {
        initComponents();
       setStatutCommande(commande );  
    }
      public CellStatus(ModelFacture facture ) {
        initComponents();
       setStatutFacture (facture);  
    }
          public CellStatus(ModelChassis chassis ) {
        initComponents();
        setStatutChassis (chassis);  
    }
              public CellStatus(ModelTypeVitre vitre ) {
        initComponents();
        setStatutVitre (vitre);  
    }
    public CellStatus(ModelJoint joint ) {
        initComponents();
        setStatutJoint (joint);  
    }
      public CellStatus(ModelSerrure serrure ) {
        initComponents();
        setStatutSerrure (serrure);  
    }
         public CellStatus(ModelPaumelle paumelle ) {
        initComponents();
        setStatutPaumelle (paumelle);  
    }
              public CellStatus(ModelRoulette roulette ) {
        initComponents();
        setStatutRoulette (roulette);  
    }
        public CellStatus(ModelPoignee poignee ) {
        initComponents();
        setStatutPoignee (poignee);  
    }
            public CellStatus(ModelVis vis ) {
        initComponents();
        setStatutVis (vis);  
    }
       
        public CellStatus(ModelOperateur operateur) {
        initComponents();
        setStatutOperateur(operateur);  
    }

    public CellStatus(ModelClips clips) {
        initComponents();
        setStatutClips(clips);  
    }

    public CellStatus(ModelRivette rivette) {
        initComponents();
        setStatutRivette(rivette);  
    }
    public CellStatus(ModelLameVerre lameVerre) {
        initComponents();
        setStatutLameVerre(lameVerre);  
    }
    public void setStatutDevis(ModelDevis devis) {
        status.setStatutDevis(devis);  
        repaint();  
    }
    
 public void setStatutCommande(ModelCommande commande) {
        status.setStatutCommande(commande);
        repaint();  // Redessiner le composant
    }
     public void setStatutFacture(ModelFacture facture) {
        status.setStatutFacture(facture);  
        repaint();  
    }
  public void setStatutChassis(ModelChassis chassis) {
        status.setStatutChassis(chassis);  
        repaint();  
    }
   public void setStatutVitre(ModelTypeVitre vitre) {
        status.setStatutVitre(vitre);  
        repaint();  
    }
     public void setStatutJoint(ModelJoint joint) {
        status.setStatutJoint(joint);  
        repaint();  
    }
       public void setStatutSerrure(ModelSerrure serrure) {
        status.setStatutSerrure(serrure);  
        repaint();  
    }
           public void setStatutPaumelle(ModelPaumelle paumelle) {
        status.setStatutPaumelle(paumelle);  
        repaint();  
    }
        public void setStatutRoulette(ModelRoulette roulette) {
        status.setStatutRoulette(roulette);  
        repaint();  
    }
            public void setStatutPoignee(ModelPoignee poignee) {
        status.setStatutPoignee(poignee);  
        repaint();  
    }
      public void setStatutVis(ModelVis vis) {
        status.setStatutVis(vis);  
        repaint();  
    }
      public void setStatutLameVerre(ModelLameVerre lameVerre) {
    status.setStatutLameVerre(lameVerre);
    repaint();
}
          public void setStatutOperateur(ModelOperateur operateur) {
     status.setStatutOperateur(operateur);  
        repaint();  
    }

    public void setStatutClips(ModelClips clips) {
        status.setStatutClips(clips); 
        repaint();  
    }

    public void setStatutRivette(ModelRivette rivette) {
        status.setStatutRivette(rivette); 
        repaint();  
    }
    public String getStatut() {
        return status.getStatut(); // Récupérer le statut à partir de TableStatus
    }


    @Override
    public String toString() {
        return getStatut();  // Renvoie le texte du statut
    }
    
@Override
protected void paintComponent(Graphics grphcs) {
    super.paintComponent(grphcs);
    
    // Dessiner la ligne en dessous
    grphcs.setColor( Color.GRAY); // Couleur grise
    grphcs.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1);
    
    // Dessiner la ligne à droite
    grphcs.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight());
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        status = new Components.TableStatus();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(114, 40));

        status.setText("tableStatus1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(status, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(status, javax.swing.GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
                .addGap(2, 2, 2))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Components.TableStatus status;
    // End of variables declaration//GEN-END:variables
}
