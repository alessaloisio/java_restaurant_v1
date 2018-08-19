/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package le.gourmet.audacieux.CuisineBeans;

import java.beans.*;
import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Alessandro Aloisio
 */
class ApplicationCuisineBeans implements TimeComputingBean {
    
    //private final PropertyChangeSupport changeSupport;
    
    public static void main(String args[])
    {
        ApplicationCuisineBeans recipe = new ApplicationCuisineBeans("FE", 3);
        
    }
    
    public ApplicationCuisineBeans()
    {
    }
    
    public ApplicationCuisineBeans(String nom, int quantite)
    {
        GetRecipeBean recipe = new GetRecipeBean(nom, quantite);
        
        recipe.addRecipeListener(this);
        recipe.init();
        recipe.run();
    }
    
    public void ingredientsReceived(IngredientsEvent e)
    {
        System.out.println("+++++++++ !!!!! "+ e.toString() + "!!!!");
        String[] plat = e.getIngredients();
        String[] ingr = plat[1].split(",");
        
        PlatAPreparer infosPlat = new PlatAPreparer(plat[0], ingr.length / 3 * 10);
        
        //DishReadyBean dishReady = new DishReadyBean();

    }
    
}
