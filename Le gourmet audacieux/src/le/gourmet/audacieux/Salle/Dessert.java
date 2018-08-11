/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package le.gourmet.audacieux.Salle;

import java.util.Hashtable;

/**
 *
 * @author Alessandro Aloisio
 */
public class Dessert extends Plat {
    
    static Hashtable<String, Object> plats;
   
    private String nom;
    
    static {
        
        plats = new Hashtable<String, Object>();
        
        plats.put("D_MC", new Dessert("Mousse au choclot salé", 5.35));
        plats.put("D_SC", new Dessert("Sorbet citron courgette Colonel", 6.85));
        plats.put("D_CJ", new Dessert("Duo de crèpes Juliettes", 6.00));
        plats.put("D_DG", new Dessert("Dame grise", 5.55));
        plats.put("D_CB", new Dessert("Crème très brulée Carbone", 7.00));
        
    }
    
    public Dessert(String nom, double prix) {
        super(prix);
        this.nom=nom;
        this.categorie = CategoriePlat.DESSERT; 
    }
    
    public Dessert(String nom) {
        code = nom;
        String[] splitPlat = plats.get(nom).toString().split(" \\(");
        this.nom = splitPlat[0];
        prix = Double.parseDouble(splitPlat[1].split(" ")[0]);
        categorie = CategoriePlat.DESSERT; 
    }
    
    public String toString(){
        return this.nom + " ("+prix+" EUR)";
    }
    
    @Override
    public String getLibelle() {
        return nom;
    }
}