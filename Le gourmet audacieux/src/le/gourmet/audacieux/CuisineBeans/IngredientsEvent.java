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
    private String[] ingredients;
    
    public IngredientsEvent(Object source, int q, String[] i)
    {
        super(source);
        quantite = q;
        ingredients = i;
    }
    
    public int getQuantite()
    {
        return quantite;
    }
    
    
    public String[] getIngredients()
    {
        return ingredients;
    }
    
}
