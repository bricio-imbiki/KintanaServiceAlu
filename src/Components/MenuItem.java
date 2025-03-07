/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Components;

import Panel.Menu;
import Event.EventMenu;
import Event.EventMenuSelected;
import Models.ModelMenu;
import Swing.MenuButton;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.RoundRectangle2D;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import net.miginfocom.swing.MigLayout;

public class MenuItem extends javax.swing.JPanel {
    private boolean selected;
    private boolean over;
    private final ModelMenu menu;
    private boolean open;
    private EventMenuSelected eventSelected;
    private final int index;
    private boolean isMenuVisible;
    
    private int currentWidth;
    private int targetWidth;
  
    private Timer animationTimer;
    private Timer textAnimationTimer;
    private float alpha;
 
//      public void setAlpha(float alpha) {
//        this.alpha = alpha;
//    }

   


    public MenuItem(ModelMenu menu, EventMenu event, EventMenuSelected eventSelected, int index) {
        initComponents();
        this.menu = menu;
        this.eventSelected = eventSelected;
        this.index = index;
        setOpaque(false);
        
        setLayout(new MigLayout("wrap, fillx, insets 0", "[fill]", "[fill, 40!]"));

        MenuButton menuItemButton = new MenuButton(menu.getIcon(), "      " + menu.getMenuName());
        // Ajoutez l'écouteur de redimensionnement du parent (MainPanel)
    addComponentListener(new ComponentAdapter() {
        @Override
        public void componentResized(ComponentEvent e) {
            int newWidth = getParent().getWidth();

            // Détecter si le panneau parent s'élargit ou se rétrécit
            if (newWidth > currentWidth) {
                // Le MainPanel s'élargit
                setTargetWidth(getWidth() - 16 * 5); // Largeur pour l'agrandissement
            } else {
                // Le MainPanel se rétrécit
                setTargetWidth(40); // Largeur pour le rétrécissement
            }
        }
    });

        menuItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (eventSelected != null) {
                    eventSelected.selected(index); // Appel de l'événement de sélection
                    // Mettre à jour la couleur des boutons du menu
                    Menu menu = findMenuParent(MenuItem.this);
                    if (menu != null) {
                        menu.setSelectedMenuItem(MenuItem.this);
                    }
                } else {
                    System.err.println("EventMenuSelected is null.");
                }
            }
        });

        // Ajout des effets de survol
        menuItemButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                setOver(true); // Active l'état survolé
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                setOver(false); // Désactive l'état survolé
            }
        });

        add(menuItemButton);
        // Configure l'animation lors du changement de visibilité du menu
        setOpen(menu.isShowMenu());
        setupAnimation();
//        setupTextAnimation();
    }

//    public void startAnimation(boolean showMenu) {
//        // Définir la largeur cible en fonction de la visibilité du menu
//        int targetWidth = showMenu ? (getWidth() - 16 * 5) : 40;
//        // Déclencher l'animation pour atteindre la largeur cible
//        setTargetWidth(targetWidth);
//    }



//// Animation du texte
//private void setupTextAnimation() {
//    textAnimationTimer = new Timer(2, new ActionListener() {
//        @Override
//        public void actionPerformed(ActionEvent e) {
//            if (currentWidth <232) {
//                hideText();
//            } else if  (currentWidth > 10){
//               showText();
//            }
//
//            if (currentWidth == targetWidth) {
//                textAnimationTimer.stop();  // Arrête l'animation du texte une fois la largeur cible atteinte
//            }
//        }
//    });
//}
//  private void showText() {
//        Component[] components = getComponents();
//        for (Component component : components) {
//            if (component instanceof MenuButton) {
//                MenuButton menuItemButton = (MenuButton) component;
//                if (menuItemButton.getText().isEmpty()) {
//                    // Ajoutez une animation douce pour le texte
//                    menuItemButton.setText("      " + menu.getMenuName());
//                    menuItemButton.revalidate();
//                }
//            }
//        }
//    }
////
//    private void hideText() {
//        Component[] components = getComponents();
//        for (Component component : components) {
//            if (component instanceof MenuButton) {
//                MenuButton menuItemButton = (MenuButton) component;
//                menuItemButton.setText("");  // Masquer progressivement le texte
////                menuItemButton.revalidate();
//            }
//        }
//    }

// Fonction de configuration de l'animation
private void setupAnimation() {
    animationTimer = new Timer(5, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int step = 10;  // Réduisez la taille de l'animation pour la rendre plus douce

            if (currentWidth < targetWidth) {
                currentWidth += step*5;
                if (currentWidth > targetWidth) {
                    currentWidth = targetWidth;
                }
            } else if (currentWidth > targetWidth) {
                currentWidth -= step*2000;
                if (currentWidth < targetWidth) {
                    currentWidth = targetWidth;
                }
            }

            repaint();

            if (currentWidth == targetWidth) {
                animationTimer.stop();
            }
        }
    });
}


    public void setTargetWidth(int targetWidth) {
        this.targetWidth = targetWidth;
        if (!animationTimer.isRunning()) {
            animationTimer.start();
        }
    }


    @Override
    protected void paintComponent(Graphics grphcs) {
    Graphics2D g2 = (Graphics2D) grphcs;
    g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);


    // Parcourir la hiérarchie des parents pour trouver une instance de Menu
    Component parent = getParent();
    while (parent != null) {
        if (parent instanceof Menu) {
            Menu parentMenu = (Menu) parent;
            boolean isMenuVisible = parentMenu.isShowMenu();
            //System.out.println("Menu ouvert : " + isMenuVisible);
            // Définir la largeur de l'effet en fonction de la visibilité du menu
            setTargetWidth(isMenuVisible ? (getWidth() - 16 * 5) : 40);
            break;
        }
        parent = parent.getParent();
    }



    // Créer un rectangle arrondi pour l'effet de survol ou sélection
    RoundRectangle2D roundedRect = new RoundRectangle2D.Double(10, 0, currentWidth, getHeight(), 20, 20);
   

    // Vérifie si l'élément est sélectionné ou survolé
    if (selected) {
        g2.setColor(new Color(255, 255, 255, 80));  // Couleur de sélection
        g2.fillRoundRect(10, 0, currentWidth, getHeight(), 20, 20);
       
    } else if (over) {
        g2.setColor(new Color(255, 255, 255, 20));  // Couleur de survol
       g2.fillRoundRect(10, 0, currentWidth, getHeight(), 20, 20); // Remplit la zone de l'effet de survol
    }
  g2.clip(roundedRect);
    super.paintComponent(grphcs);  // Continue avec le dessin par défaut
}
    
  public ModelMenu getMenu() {
        return menu;
    }

    public boolean isOpen() {
        return open;
    }
    public void setOpen(boolean open) {
        this.open = open;
        repaint();
    }


    public EventMenuSelected getEventSelected() {
        return eventSelected;
    }

    public void setEventSelected(EventMenuSelected eventSelected) {
        this.eventSelected = eventSelected;
    }

    public int getIndex() {
        return index;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint(); // Redessine le composant pour appliquer l'effet visuel
    }

    public void unselectAll() {
        setSelected(false); // Désélectionne le bouton de menu
    }

    public void setOver(boolean over) {
        this.over = over;
        repaint(); // Redessine le composant pour appliquer l'effet visuel
    }

   
    public void setMenuVisible(boolean visible) {
        isMenuVisible = visible;
    }

    public boolean isMenuVisible() {
        return isMenuVisible;
    }

  
    private Menu findMenuParent(Component component) {
        if (component == null) {
            return null;
        }
        if (component instanceof Menu) {
            return (Menu) component;
        }
        return findMenuParent(component.getParent());
    }

     

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
