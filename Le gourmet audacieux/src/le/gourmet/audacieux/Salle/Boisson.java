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
public class Boisson extends Plat{
    
    private String nom = "Boissons avec repas";
    
    static {
        categorie = CategoriePlat.BOISSON;
    }
    
    public Boisson(double prix, boolean servis) {
        super(prix, servis);
        categorie = CategoriePlat.BOISSON;
    }
    
    public String toString(){
        return this.nom + " ("+prix+" EUR)";
    }
    
    @Override
    public String getLibelle() {
        return nom;
    }
}
