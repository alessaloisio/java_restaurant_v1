/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package le.gourmet.audacieux.Salle;

import java.io.Serializable;

/**
 *
 * @author Alessandro Aloisio
 */
public class CategoriePlat implements Serializable {
    
    protected String categorie = null;
    
    static final String PLAT_PRINCIPAL = "PP";
    static final String DESSERT = "D";
    static final String BOISSON = "B";
    static final String ALCOOLS = "A";
    
    public CategoriePlat() {
        categorie = null;
    }
    
    public CategoriePlat(String categorie){
        this.categorie=categorie;
    }

}
