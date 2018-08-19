/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package le.gourmet.audacieux.CuisineBeans;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import le.gourmet.audacieux.Salle.ApplicationSalle;

/**
 *
 * @author alex_
 */
public class GetRecipeBean implements Serializable {
    
    private String nomPlat;
    private int quantitePlat;
    private boolean enMarche;
    private int multipleDeclenchement;
    private int niveauxAlerte; 
    private Vector recipeListeners;
    private String[] ingredients;
    
    final int multipleDeclenchementParDefaut = 5; 

    public GetRecipeBean()
    {
        quantitePlat = 0;
        enMarche = false;
        recipeListeners = new Vector();
        multipleDeclenchement = multipleDeclenchementParDefaut;
        niveauxAlerte = 3; 
    }
    
    public GetRecipeBean(String nom, int quantite)
    {
        
        nomPlat = nom;
        quantitePlat = quantite;

        enMarche = false;
        recipeListeners = new Vector();

        try {
            
            BufferedReader reader = new BufferedReader(new FileReader("ingredients.txt"));
            
            String line;
            line = reader.readLine();
            
            while (line != null)
            {
                ingredients = line.split(":");
                
                if(ingredients[0].equals(nom))
                    line = null;
                else
                    line = reader.readLine();
            }
       
        } catch (IOException ex) {
            Logger.getLogger(ApplicationSalle.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public boolean isEnMarche() { return enMarche; }
    public void setEnMarche(boolean em) { enMarche = em; }
    public int getQuantitePlat() { return quantitePlat; };
    public String[] getIngredients() { return ingredients; };

    public void init() { setEnMarche(true); }
    public void stop() { setEnMarche(false); }
    
    public void run()
    {
        if(!isEnMarche())
        {
            System.out.println("Surveillance des alertes non enclenchée !");
            return;
        }
        
        notifyingredientsReceived(this.getQuantitePlat(), this.getIngredients());
    }
    
    public void addRecipeListener (TimeComputingBean tm) 
    {
        if(!recipeListeners.contains(tm))
        {
            recipeListeners.addElement(tm);
        }
    }
    
    public void removeRecipeListener(TimeComputingBean tm)
    {
        if(!recipeListeners.contains(tm))
        {
            recipeListeners.removeElement(tm);
        }
    }
    
    // ce qui est activé au déclenchement d'une alerte
    protected void notifyingredientsReceived(int quantite, String[] ing)
    {
        IngredientsEvent e = new IngredientsEvent(this, quantite, ing);
        
        for(int i = 0; i < recipeListeners.size(); i++)
        {
            TimeComputingBean obj = (TimeComputingBean) recipeListeners.elementAt(i);
            obj.ingredientsReceived(e);
        }
    }
    
}
