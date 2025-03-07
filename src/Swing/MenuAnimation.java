/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Swing;




import java.awt.Component;
import menu.MenuItem;
import net.miginfocom.swing.MigLayout;
import org.jdesktop.animation.timing.Animator;
import org.jdesktop.animation.timing.TimingTarget;
import org.jdesktop.animation.timing.TimingTargetAdapter;

public class MenuAnimation {

    private final MigLayout layout;
    private final MenuItem menuItem;
    private Animator animator;
    private boolean open;

    public MenuAnimation(MigLayout layout, Component component) {
        this.layout = layout;
        this.menuItem = (MenuItem) component;
        initAnimator(component, 200);
    }

    public MenuAnimation(MigLayout layout, Component component, int duration) {
        this.layout = layout;
        this.menuItem = (MenuItem) component;
        initAnimator(component, duration);
    }

    private void initAnimator(Component component, int duration) {
    int height = component.getPreferredSize().height;
    
    TimingTarget target = new TimingTargetAdapter() {
        @Override
        public void timingEvent(float fraction) {
            float h;
            if (open) {
                h = 40 + ((height - 40) * fraction);  // Animation d'ouverture
               
            } else {
                h = 40 + ((height - 40) * (1f - fraction));  // Animation de fermeture
              
            }

            // Appliquer la nouvelle hauteur animée
            layout.setComponentConstraints(menuItem, "h " + h + "!");
            component.revalidate();
            component.repaint();
        }

    
    };

    // Configuration de l'animation
    animator = new Animator(duration, target);
    animator.setResolution(0);  // Pour des animations fluides
    animator.setDeceleration(0.5f);  // Décélération pour plus de douceur
    animator.setAcceleration(0.5f);  // Accélération au début pour effet d'ouverture/fermeture
}


public void openMenu() {
    open = true;
    animator.start();
    menuItem.revalidate();  // Appelle revalidate() sur le menuItem
    menuItem.repaint();     // Appelle repaint() sur le menuItem
}

public void closeMenu() {
    open = false;
    animator.start();
    menuItem.revalidate();  // Appelle revalidate() sur le menuItem
    menuItem.repaint();     // Appelle repaint() sur le menuItem
}

}
