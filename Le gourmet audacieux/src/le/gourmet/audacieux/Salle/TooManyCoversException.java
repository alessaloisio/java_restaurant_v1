/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package le.gourmet.audacieux.Salle;

import javax.swing.JOptionPane;

/**
 *
 * @author Alessandro Aloisio
 */
public class TooManyCoversException extends Exception {

    public int n;
    
    public TooManyCoversException() {
        
        Object[] options = {"Oui, continuer", "Non, merci"};
        
        n = JOptionPane.showOptionDialog(null,
            "Etes vous sûr de vouloir continuer ?",
            "TooManyCovers",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,     //do not use a custom Icon
            options,  //the titles of buttons
            options[0]
        ); //default button title
        
    }
    
}
