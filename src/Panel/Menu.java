/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Panel;

import Components.MenuItem;
import Components.MenuItem;
import Event.EventMenu;
import Event.EventMenuSelected;
import Models.ModelMenu;
import Scroll.ScrollBarCustom;
import Swing.MenuAnimation;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import net.miginfocom.swing.MigLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Menu extends javax.swing.JPanel {


    private MenuItem selectedMenuItem; // Référence à l'élément actuellement sélectionné
    private final MigLayout layout;
    private EventMenuSelected event;
    private boolean enableMenu = true;
    private boolean showMenu = true;
    
  public boolean isShowMenu() {
     
        return showMenu;
    }

    public void addEvent(EventMenuSelected event) {
        this.event = event;
    }

    public void setEnableMenu(boolean enableMenu) {
        this.enableMenu = enableMenu;
    }
   
    public void showMenu(boolean isVisible) {
        // logic to show or hide the menu
        if (isVisible) {
            // Show menu
        } else {
            // Hide menu
        }
    }
   public void setShowMenu(boolean showMenu) {
    this.showMenu = showMenu;
    for (Component com : panel.getComponents()) {
        com.revalidate();  // Forcer le rafraîchissement de chaque élément du menu
         com.repaint(); 
    }
}
//  public void setAlpha(float alpha) {
//        selectedMenuItem.setAlpha(alpha);
//       
//    }

    public Menu() {
        initComponents();
        setOpaque(false);
        sp.getViewport().setOpaque(false);
        sp.setVerticalScrollBar(new ScrollBarCustom());
        layout = new MigLayout("wrap, fillx, insets 0 0 20 0", "[fill]", "[]0[]");
        panel.setLayout(layout);
        
    }

    public void initMenuItem() {
        addMenu(new ModelMenu(new FlatSVGIcon("Icon/1.svg",24,24), "Acceuil"));
        addMenu(new ModelMenu(new FlatSVGIcon("Icon/2.svg"), "Clients"));
        addMenu(new ModelMenu(new FlatSVGIcon("Icon/3.svg",24,24), "Devis"));
        addMenu(new ModelMenu(new FlatSVGIcon("Icon/4.svg",24,24), "Commande"));
        addMenu(new ModelMenu(new FlatSVGIcon("Icon/5.svg",24,24), "Facture"));
        addMenu(new ModelMenu(new FlatSVGIcon("Icon/6.svg",24,24), "Paiements"));
        addMenu(new ModelMenu(new FlatSVGIcon("Icon/package-box-svgrepo-com (1).svg",24,24), "Materiaux"));
        addMenu(new ModelMenu(new FlatSVGIcon("Icon/8.svg",24,24), "Fournisseur"));
        addEmpty();
        addMenu(new ModelMenu(new FlatSVGIcon("Icon/logout.svg",24,24), "Déconnexion"));
    }

private void addEmpty() {
     // Create an empty JLabel with push and grow to add flexible space
    panel.add(new JLabel(), "push");  
    // Add another JLabel to create a fixed padding space between the "Déconnexion" button and the bottom
    //panel.add(new JLabel(), "h 30!, wrap");  // Adds a 30-pixel padding at the bottom
}
private void addMenu(ModelMenu menu) {
    MenuItem menuItem = new MenuItem(menu, getEventMenu(), event, panel.getComponentCount());
    menuItem.setEventSelected((index) -> {
        setSelectedMenuItem(menuItem);
    });
    panel.add(menuItem, "h 40!");
   
}


  public void setSelectedMenuItem(MenuItem menuItem) {
        if (selectedMenuItem != null) {
            selectedMenuItem.setSelected(false);
        }
        selectedMenuItem = menuItem;
        if (selectedMenuItem != null) {
            selectedMenuItem.setSelected(true);
        }
    }

    private EventMenu getEventMenu() {
        return (Component com, boolean open) -> {
            if (enableMenu) {
                if (isShowMenu()) {
                    if (open) {
                        new MenuAnimation(layout, com).openMenu();
                    } else {
                        new MenuAnimation(layout, com).closeMenu();
                    }
                    return true;
                }
            }
            return false;
        };
    }

    public void hideallMenu() {
        for (Component com : panel.getComponents()) {
            MenuItem item = (MenuItem) com;
            if (item.isOpen()) {
                new MenuAnimation(layout, com, 500).closeMenu();
                item.setOpen(false);
            }
        }
    }
public void selectMenuItem(int index) {
    if (index >= 0 && index < panel.getComponentCount()) {
        // Récupérer le MenuItem correspondant à l'index
        MenuItem menuItem = (MenuItem) panel.getComponent(index);
        // Sélectionner cet élément
        setSelectedMenuItem(menuItem);
    } else {
        System.err.println("Index out of bounds: " + index);
    }
}
public void fireEventMenuSelected(int index) {
    if (event != null) {
        event.selected(index);
    }
}


    @Override
    protected void paintChildren(Graphics grphcs) {
        Graphics2D g2 = (Graphics2D) grphcs;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        GradientPaint g = new GradientPaint(0, 0, Color.decode("#1CB5E0"), 0, getHeight(), Color.decode("#000046"));
        g2.setPaint(g);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
        g2.fillRect(getWidth() - 20, 0, getWidth(), getHeight());
        super.paintChildren(grphcs);
    }
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        sp = new javax.swing.JScrollPane();
        panel = new javax.swing.JPanel();
        company = new Panel.Company();

        setPreferredSize(new java.awt.Dimension(124, 238));

        sp.setBorder(null);
        sp.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        sp.setViewportBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        sp.setOpaque(false);

        panel.setMinimumSize(new java.awt.Dimension(0, 0));
        panel.setOpaque(false);
        panel.setPreferredSize(new java.awt.Dimension(312, 523));

        javax.swing.GroupLayout panelLayout = new javax.swing.GroupLayout(panel);
        panel.setLayout(panelLayout);
        panelLayout.setHorizontalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 312, Short.MAX_VALUE)
        );
        panelLayout.setVerticalGroup(
            panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 523, Short.MAX_VALUE)
        );

        sp.setViewportView(panel);

        company.setMinimumSize(new java.awt.Dimension(183, 64));
        company.setPreferredSize(new java.awt.Dimension(300, 92));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(company, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(sp, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(company, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sp, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private Panel.Company company;
    private javax.swing.JPanel panel;
    private javax.swing.JScrollPane sp;
    // End of variables declaration//GEN-END:variables
}
