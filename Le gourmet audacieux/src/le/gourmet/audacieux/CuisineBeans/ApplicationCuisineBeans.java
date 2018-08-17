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
    
    public static void main(String args[])
    {
        System.out.println("Gwer");
        ApplicationCuisineBeans recipe = new ApplicationCuisineBeans("CC", 3);
        
        
    }
    
    public ApplicationCuisineBeans()
    {
    
    }
    
    public ApplicationCuisineBeans(String nom, int quantite)
    {
        //GetRecipeBean recipe = new GetRecipeBean(nom, quantite);
        GetRecipeBean recipe = null;
        
        try
        {
            recipe = (GetRecipeBean) Beans.instantiate(null, "GetRecipeBean");
        } catch (IOException ex) {
            Logger.getLogger(ApplicationCuisineBeans.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ApplicationCuisineBeans.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        recipe.addRecipeListener(this);
        recipe.init();
        recipe.run();
    }
    
    public void ingredientsReceived(IngredientsEvent e)
    {
        System.out.println("+++++++++ !!!!! "+ e.toString() + "!!!!");
        
    }
    
}
