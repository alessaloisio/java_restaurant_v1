package StringSlicer;


import java.awt.Component;
import java.util.LinkedHashSet;
import java.util.Vector;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Daniel Garcia Lecloux
 * @author Alessandro Aloisio
 * @version 1.0
 */
public class StringSlicer {
    
    private String strAnalyse;
    private String strDel;
    
    /** Constructeur prennant en paramètre un String à analyser et un délimiteur.
     *
     * @param StrAnalyse
     * @param StrDel
     */
    public StringSlicer(String StrAnalyse, String StrDel)
    {
        strAnalyse = StrAnalyse;
        strDel = StrDel; 
    }
    
    /** Fournit le nombre de composantes et les affiche.

     *
     * @param afficher
     * @return int
     */
    public int getComponents(boolean afficher)
    {
        if(afficher)
        {
            String[] temp = strAnalyse.split(strDel);
                
            System.out.println("Nombre: " + temp.length);
            
            for(String str : temp)
                System.out.println(str);

            return 1;
        }
        return 0;
            
    }
    
    /** Fournit un Vector qui contient les composantes détectées.
     *
     * @return Un Vector avec toutes les composantes
     */
    public Vector listComponents()
    {
        Vector<String> compVector = new Vector<String>();
        String[] temp = strAnalyse.split(strDel);
        for(String s : temp)
            compVector.add(s);
        return compVector;
    
    }
    
    /** Fournit un LinkedHashSet qui contient les composantes détectées sans répétitions et dans
leur ordre d'apparition.
     *
     * @return Un LinkedHashSet avec les composantes uniques
     */
    public LinkedHashSet listUniqueComponents() 
    {
        LinkedHashSet<String> compHashSet = new LinkedHashSet<String>();
        String[] temp = strAnalyse.split(strDel);
        for(String s : temp)
            if(!compHashSet.contains(s))
                compHashSet.add(s);
        return compHashSet;
        
    }
}
