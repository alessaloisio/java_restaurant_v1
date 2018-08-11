/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package le.gourmet.audacieux.CuisineBeans;

import java.util.EventObject;

/**
 *
 * @author alex_
 */
public class IngredientsEvent extends EventObject {
    
    private int quantite;
    
    public IngredientsEvent(Object source, int q)
    {
        super(source);
        quantite = q;
    }
    
    public int getQuantite()
    {
        return quantite;
    }
    
}
