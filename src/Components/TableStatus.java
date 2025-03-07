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
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JLabel;

public class TableStatus extends JLabel {

    private ModelDevis devis;
    private ModelCommande commande;
    private ModelFacture facture;
    private ModelChassis chassis;
    
    private String statut; 
    private ModelTypeVitre vitre;
    private ModelJoint joint;
    private ModelSerrure serrure;
    private ModelRoulette roulette;
    private ModelPaumelle paumelle;
    private ModelPoignee poignee;
    private ModelVis vis;
    private ModelOperateur operateur;
    private ModelClips clips;
    private ModelRivette rivette;

    public TableStatus() {
        setForeground(Color.WHITE);
        setHorizontalAlignment(CENTER); // Ensure text is centered
    }

    // Method to set the status from a Devis (estimate)
    public void setStatutDevis(ModelDevis devis) {
        this.devis = devis;
        //this.commande = null;
        //this.facture = null; // Ensure other models are null if devis is used

        if (devis != null && devis.getStatut() != null) {
            this.statut = devis.getStatut().getStatut().toUpperCase();
            setText(this.statut);  // Update displayed text
        } else {
            setText("Statut inconnu");  // Handle case where status is null
        }

        repaint();  // Redraw the component
    }

    // Method to set the status from a Commande (order)
    public void setStatutCommande(ModelCommande commande) {
        this.commande = commande;
       // this.devis = null;
       // this.facture = null; // Ensure other models are null if commande is used

        if (commande != null && commande.getStatut() != null) {
            this.statut = commande.getStatut().getStatut().toUpperCase();
            setText(this.statut);  // Update displayed text
        } else {
            setText("Statut inconnu");  // Handle case where status is null
        }

        repaint();  // Redraw the component
    }

    // Method to set the status from a Facture (invoice)
    public void setStatutFacture(ModelFacture facture) {
        this.facture = facture;
       

        if (facture != null && facture.getStatutFacture() != null) {
            this.statut = facture.getStatutFacture().getStatut().toUpperCase();
            setText(this.statut);  // Update displayed text
        } else {
            setText("Statut inconnu");  // Handle case where status is null
        }

        repaint();  // Redraw the component
    }
    
        public void setStatutChassis(ModelChassis chassis) {
        this.chassis = chassis;
       
        if (chassis != null && chassis.getStatutStock()!= null) {
            this.statut = chassis.getStatutStock().getStatut().toUpperCase();
            setText(this.statut);  // Update displayed text
        } else {
            setText("Statut inconnu");  // Handle case where status is null
        }

        repaint();  // Redraw the component
    }
  public void setStatutVitre(ModelTypeVitre vitre) {
        this.vitre = vitre;
       
        if (vitre != null && vitre.getStatutStock()!= null) {
            this.statut = vitre.getStatutStock().getStatut().toUpperCase();
            setText(this.statut);  // Update displayed text
        } else {
            setText("Statut inconnu");  // Handle case where status is null
        }

        repaint();  // Redraw the component
    }
   public void setStatutJoint(ModelJoint joint) {
        this.joint = joint;
       
        if (joint != null && joint.getStatutStock()!= null) {
            this.statut = joint.getStatutStock().getStatut().toUpperCase();
            setText(this.statut);  // Update displayed text
        } else {
            setText("Statut inconnu");  // Handle case where status is null
        }

        repaint();  // Redraw the component
    }
     public void setStatutSerrure(ModelSerrure serrure) {
        this.serrure = serrure;
       
        if (serrure != null && serrure.getStatutStock()!= null) {
            this.statut = serrure.getStatutStock().getStatut().toUpperCase();
            setText(this.statut);  // Update displayed text
        } else {
            setText("Statut inconnu");  // Handle case where status is null
        }

        repaint();  // Redraw the component
    }
         public void setStatutRoulette(ModelRoulette roulette) {
        this.roulette = roulette;
       
        if (roulette != null && roulette.getStatutStock()!= null) {
            this.statut = roulette.getStatutStock().getStatut().toUpperCase();
            setText(this.statut);  // Update displayed text
        } else {
            setText("Statut inconnu");  // Handle case where status is null
        }

        repaint();  // Redraw the component
    }
                public void setStatutPaumelle(ModelPaumelle paumelle) {
        this.paumelle = paumelle;
       
        if (paumelle != null && paumelle.getStatutStock()!= null) {
            this.statut = paumelle.getStatutStock().getStatut().toUpperCase();
            setText(this.statut);  // Update displayed text
        } else {
            setText("Statut inconnu");  // Handle case where status is null
        }

        repaint();  // Redraw the component
    }
       public void setStatutPoignee(ModelPoignee poignee) {
        this.poignee = poignee;
       
        if (poignee != null && poignee.getStatutStock()!= null) {
            this.statut = poignee.getStatutStock().getStatut().toUpperCase();
            setText(this.statut);  // Update displayed text
        } else {
            setText("Statut inconnu");  // Handle case where status is null
        }

        repaint();  // Redraw the component
    }
         public void setStatutVis(ModelVis vis) {
        this.vis = vis;
       
        if (vis != null && vis.getStatutStock()!= null) {
            this.statut = vis.getStatutStock().getStatut().toUpperCase();
            setText(this.statut);  // Update displayed text
        } else {
            setText("Statut inconnu");  // Handle case where status is null
        }

        repaint();  // Redraw the component
    }
       public void setStatutLameVerre(ModelLameVerre lameVerre) {
    if (lameVerre != null && lameVerre.getStatutStock() != null) {
        this.statut = lameVerre.getStatutStock().getStatut().toUpperCase();
        setText(this.statut); 
    } else {
       setText("Statut inconnu");
    }
    repaint();
}  
         // Méthode pour mettre à jour le statut de la Rivette
public void setStatutRivette(ModelRivette rivette) {
    this.rivette = rivette;

    if (rivette != null && rivette.getStatutStock() != null) {
        this.statut = rivette.getStatutStock().getStatut().toUpperCase(); // Récupérer et formater le statut
        setText(this.statut);  // Mettre à jour le texte affiché
    } else {
        setText("Statut inconnu");  // Gérer le cas où le statut est nul
    }

    repaint();  // Redessiner le composant
}

// Méthode pour mettre à jour le statut des Clips
public void setStatutClips(ModelClips clips) {
    this.clips = clips;

    if (clips != null && clips.getStatutStock() != null) {
        this.statut = clips.getStatutStock().getStatut().toUpperCase(); // Récupérer et formater le statut
        setText(this.statut);  // Mettre à jour le texte affiché
    } else {
        setText("Statut inconnu");  // Gérer le cas où le statut est nul
    }

    repaint();  // Redessiner le composant
}

// Méthode pour mettre à jour le statut de l'Opérateur
public void setStatutOperateur(ModelOperateur operateur) {
    this.operateur = operateur;

    if (operateur != null && operateur.getStatutStock() != null) {
        this.statut = operateur.getStatutStock().getStatut().toUpperCase(); // Récupérer et formater le statut
        setText(this.statut);  // Mettre à jour le texte affiché
    } else {
        setText("Statut inconnu");  // Gérer le cas où le statut est nul
    }

    repaint();  // Redessiner le composant
}

    // Method to get the current status
    public String getStatut() {
        return this.statut;
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Define a map of status IDs to colors
        Map<Integer, Color[]> colorMap = new HashMap<>();
        colorMap.put(1, new Color[]{new Color(186, 123, 247), new Color(167, 94, 236)}); // In Progress
        colorMap.put(2, new Color[]{new Color(241, 208, 62), new Color(211, 184, 61)}); // Pending
        colorMap.put(3, new Color[]{new Color(142, 142, 250), new Color(123, 123, 245)}); // Validated
        colorMap.put(4, new Color[]{new Color(231, 76, 60), new Color(192, 57, 43)});    // Refused
        colorMap.put(5, new Color[]{new Color(128, 128, 128), new Color(100, 100, 100)}); // Canceled

        Color[] colors = null;

        // Check if it's a devis, commande, or facture and get the appropriate colors
        if (devis != null && devis.getStatut() != null) {
            colors = colorMap.get(devis.getStatut().getStatutId());
        } else if (commande != null && commande.getStatut() != null) {
            colors = colorMap.get(commande.getStatut().getStatutId());
        } else if (facture != null && facture.getStatutFacture() != null) {
            colors = colorMap.get(facture.getStatutFacture().getStatutId());
        }else if (chassis != null && chassis.getStatutStock() != null) {
            colors = colorMap.get(chassis.getStatutStock().getStatutId());
        } else if (vitre != null && vitre.getStatutStock() != null) {
            colors = colorMap.get(vitre.getStatutStock().getStatutId());
        } else if (joint != null && joint.getStatutStock() != null) {
            colors = colorMap.get(joint.getStatutStock().getStatutId());
        }else if (serrure != null && serrure.getStatutStock() != null) {
            colors = colorMap.get(serrure.getStatutStock().getStatutId());
        }else if (paumelle != null && paumelle.getStatutStock() != null) {
            colors = colorMap.get(paumelle.getStatutStock().getStatutId());
        }else if (roulette != null && roulette.getStatutStock() != null) {
            colors = colorMap.get(roulette.getStatutStock().getStatutId());
        }else if (poignee != null && poignee.getStatutStock() != null) {
            colors = colorMap.get(poignee.getStatutStock().getStatutId());
        }else if (vis != null && vis.getStatutStock() != null) {
            colors = colorMap.get(vis.getStatutStock().getStatutId());
        }else if (clips != null && clips.getStatutStock() != null) {
            colors = colorMap.get(clips.getStatutStock().getStatutId());
        }else if (operateur != null && operateur.getStatutStock() != null) {
            colors = colorMap.get(operateur.getStatutStock().getStatutId());
        }else if (rivette != null && rivette.getStatutStock() != null) {
            colors = colorMap.get(rivette.getStatutStock().getStatutId());
        }

        // Apply the colors if found, otherwise use a default color
        if (colors != null) {
            GradientPaint g = new GradientPaint(0, 0, colors[0], 0, getHeight(), colors[1]);
            g2.setPaint(g);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        } else {
            // If no color found, set default background color
            g2.setColor(Color.LIGHT_GRAY);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        }

        super.paintComponent(grphcs);
    }
}
