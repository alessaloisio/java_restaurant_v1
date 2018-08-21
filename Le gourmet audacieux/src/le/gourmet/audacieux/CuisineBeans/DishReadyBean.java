/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package le.gourmet.audacieux.CuisineBeans;

import java.beans.*;
import java.io.Serializable;
import javax.swing.JOptionPane;

/**
 *
 * @author alex_
 */

//https://kodejava.org/how-do-i-listen-for-beans-property-change-event/
public class DishReadyBean implements Serializable {
    
    public static final String PROP_SAMPLE_PROPERTY = "sampleProperty";
    
    private String sampleProperty;
    public int n;
    
    private PropertyChangeSupport propertySupport;
    
    public DishReadyBean() {
        propertySupport = new PropertyChangeSupport(this);
    }
    
    public DishReadyBean(PlatAPreparer apreparer) {
        
        Object[] options = {"OK"};
        
        n = JOptionPane.showOptionDialog(null,
            "Le plat "+apreparer.nom+" sera prêt dans "+apreparer.tempsCuisson+" min",
            "Un plat a été lancé !",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,     //do not use a custom Icon
            options,  //the titles of buttons
            options[0]
        );
        
        propertySupport = new PropertyChangeSupport(this);
    }
    
    
    public String getSampleProperty() {
        return sampleProperty;
    }
    
    public void setSampleProperty(String value) {
        String oldValue = sampleProperty;
        sampleProperty = value;
        propertySupport.firePropertyChange(PROP_SAMPLE_PROPERTY, oldValue, sampleProperty);
    }
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.addPropertyChangeListener(listener);
    }
    
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        propertySupport.removePropertyChangeListener(listener);
    }
    
}
