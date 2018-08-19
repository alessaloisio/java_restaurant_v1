/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package le.gourmet.audacieux.CuisineBeans;

/**
 
 * @author alex_
 */
public class PlatAPreparer{
    public String nom;
    public double tempsCuisson;
    
    public PlatAPreparer(){};
    
    public PlatAPreparer(String nom, double temps)
    {
        this.nom = nom;
        this.tempsCuisson = temps;
    };
}
