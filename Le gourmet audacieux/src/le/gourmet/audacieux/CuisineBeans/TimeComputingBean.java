/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package le.gourmet.audacieux.CuisineBeans;

import java.util.*;
        
/**
 *
 * @author alex_
 */
//
public interface TimeComputingBean extends EventListener 
{
    public void ingredientsReceived(IngredientsEvent e);
    //public void getPlat();
}
