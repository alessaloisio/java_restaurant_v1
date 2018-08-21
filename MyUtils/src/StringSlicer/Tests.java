/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StringSlicer;

import StringSlicer.*;
import java.util.LinkedHashSet;
import java.util.Vector;

/**
 *
 * @author Daniel
 */
public class Tests {
    public static void main (String[] args){
        
        String test = "CC;FE;CC;TT";
        StringSlicer t = new StringSlicer(test, ";");
        System.out.println("");
        System.out.println("Affichage de getComponents() !");
        t.getComponents(true);
        System.out.println("Affichage de listComponents() !");
        Vector<String> hashSet = t.listComponents();
        for(String s: hashSet)
        {
            System.out.println(s);
        }
        System.out.println("Affichage de listUniqueComponents() !");
        LinkedHashSet<String> hashSetUnique = t.listUniqueComponents();
        for(String s: hashSetUnique)
        {
            System.out.println(s);
        }
        
        
    }
}
