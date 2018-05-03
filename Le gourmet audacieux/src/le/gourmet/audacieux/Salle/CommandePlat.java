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
public class CommandePlat {
    
    public Plat plat;
    public int quantite;
    
    public CommandePlat(Plat plat, int quantite) {
        this.plat = plat;
        this.quantite = quantite;
    }
    
}
