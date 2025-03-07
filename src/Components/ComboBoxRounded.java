/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

/**
 *
 * @author brici_6ul2f65
 */
import javax.swing.JComboBox;

public class ComboBoxRounded<E> extends JComboBox<E> {

    public ComboBoxRounded() {
        setUI(new ComboRoundedUI());
    }
}
