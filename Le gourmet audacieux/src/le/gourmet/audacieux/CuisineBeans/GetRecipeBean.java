/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package le.gourmet.audacieux.CuisineBeans;

import java.util.Vector;

/**
 *
 * @author alex_
 */
public class GetRecipeBean {
    
    private String nomPlat;
    private int quantitePlat;
    private boolean enMarche;
    private int multipleDeclenchement;
    private int niveauxAlerte; 
    private Vector recipeListeners;
    
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
    }
    
    public boolean isEnMarche() { return enMarche; }
    public void setEnMarche(boolean em) { enMarche = em; }
    public int getPeriode() { return multipleDeclenchement; }
    public void setPeriode(int m) { multipleDeclenchement = m; }
    public int getNiveauxAlerte() { return niveauxAlerte; }
    public void setNiveauxAlerte (int na) {niveauxAlerte = na; }

    
    public void init() { setEnMarche(true); }
    public void stop() { setEnMarche(false); }
    
    public void run()
    {
        if(!isEnMarche())
        {
            System.out.println("Surveillance des alertes non enclenchée !");
            return;
        }
        
        notifyingredientsReceived(1);
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
    protected void notifyingredientsReceived(int quantite)
    {
        IngredientsEvent e = new IngredientsEvent(this, quantite);
        
        for(int i = 0; i < recipeListeners.size(); i++)
        {
            TimeComputingBean obj = (TimeComputingBean) recipeListeners.elementAt(i);
            obj.ingredientsReceived(e);
        }
    }
    
}