/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package le.gourmet.audacieux.Salle;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import static le.gourmet.audacieux.Salle.Dessert.plats;

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
        
        String line;

        try {

            BufferedReader bufferreader = new BufferedReader(new FileReader("plats.txt"));


            while ((line = bufferreader.readLine()) != null) {     
                
                String[] infosPlat = line.split(" & ");
                System.out.println(infosPlat[0] + " " + infosPlat[1]);
                plats.put(infosPlat[0], new PlatPrincipal(infosPlat[1], Double.parseDouble(infosPlat[2])));
            }

        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
//        plats.put("FE", new PlatPrincipal("Filet de boeuf Enfer des papilles", 16.8));
//        plats.put("VRH", new PlatPrincipal("Veau au rollmops sauce herve", 15.75));
//        plats.put("CC", new PlatPrincipal("Cabillaud chantilly de Terre Neuve", 16.9));
//        plats.put("GF", new PlatPrincipal("Gruyère farci aux rognons-téquilla", 13.4));
//        plats.put("PA", new PlatPrincipal("Potée auvergnate au miel", 12.5));
        
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
