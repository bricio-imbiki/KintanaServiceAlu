/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

/**
 *
 * @author brici_6ul2f65
 */
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.*;

public class RollOverTable extends JTable {

    private int rollOverRowIndex = -1;
 
    //new Color(50, 64, 77, 150).brighter();
      //new Color(50, 65, 77, 150).brighter();
    //new Color(46, 74, 97, 150).brighter();/
     //  private final Color rollOverBackground =new Color(#393939).brighter();// Lighter grey with more transparency

     private final Color rollOverBackground =new Color(50, 65, 77, 150).brighter();// Lighter grey with more transparency

    public RollOverTable(TableModel model) {
        super(model);
        RollOverListener lst = new RollOverListener();
        addMouseMotionListener(lst);
        addMouseListener(lst);
    }
    
   @Override
    public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
        Component c = super.prepareRenderer(renderer, row, column);

        if (isRowSelected(row)) {
            c.setBackground(getSelectionBackground());
        } else if (row == rollOverRowIndex) {
            c.setBackground(rollOverBackground);
        } else {
            c.setBackground(getBackground());
        }

        return c;
    }
    

    private class RollOverListener extends MouseInputAdapter {

        @Override
        public void mouseExited(MouseEvent e) {
            rollOverRowIndex = -1;
            repaint();
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int row = rowAtPoint(e.getPoint());
            if( row != rollOverRowIndex ) {
                rollOverRowIndex = row;
                repaint();
            }
        }
    }
}

