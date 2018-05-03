/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package le.gourmet.audacieux.Salle;

/**
 *
 * @author Alessandro Aloisio
 */
public abstract class Plat extends CategoriePlat implements Service {
    
    
    protected double prix;
    protected boolean servis = false;
    protected String code;

    public Plat() {
        
    }
    
    public Plat(double prix) {
        this.prix=prix;
    }
    
    public Plat(double prix, boolean servis) {
        this.prix=prix;
        this.servis=servis;
    }
    
    @Override
    public double getPrix() {
        return this.prix;
    }
    
    @Override
    public String getCategorie() {
        return categorie;
    }
    
}
