/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package le.gourmet.audacieux;

import java.util.Hashtable;

/**
 *
 * @author Alessandro Aloisio
 */
public class PlatPrincipal extends Plat {
    
    static Hashtable<String, Object> plats;
   
    private String nom;
    
    static {
        
        categorie = CategoriePlat.PLAT_PRINCIPAL; 
        
        plats = new Hashtable<String, Object>();
        
        plats.put("FE", new PlatPrincipal("Filet de boeuf Enfer des papilles", 16.8));
        plats.put("VRH", new PlatPrincipal("Veau au rollmops sauce herve", 15.75));
        plats.put("CC", new PlatPrincipal("Cabillaud chantilly de Terre Neuve", 16.9));
        plats.put("GF", new PlatPrincipal("Gruyère farci aux rognons-téquilla", 13.4));
        plats.put("PA", new PlatPrincipal("Potée auvergnate au miel", 12.5));
        
    }
    
    public PlatPrincipal(String nom, double prix) {
        super(prix);
        this.nom=nom;
    }
    
    public PlatPrincipal(String nom) {
        code = nom;
        String[] splitPlat = plats.get(nom).toString().split(" \\(");
        this.nom = splitPlat[0];
        prix = Double.parseDouble(splitPlat[1].split(" ")[0]);
        categorie = CategoriePlat.PLAT_PRINCIPAL; 
    }
    
    public String toString(){
        return this.nom + " ("+prix+" EUR)";
    }
    
    @Override
    public String getLibelle() {
        return nom;
    }
}
