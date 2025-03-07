/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Components;

/**
 *
 * @author brici_6ul2f65
 */

import javax.swing.JSpinner;

public class Spinner extends JSpinner {

    public void setLabelText(String text) {
        SpinnerUI.Editor editor = (SpinnerUI.Editor) getEditor();
        editor.setLabelText(text);
    }

    public String getLabelText() {
        SpinnerUI.Editor editor = (SpinnerUI.Editor) getEditor();
        return editor.getLabelText();
    }

    public Spinner() {
        setOpaque(false);
        setUI(new SpinnerUI());
    }
}
