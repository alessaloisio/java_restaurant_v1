/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package le.gourmet.audacieux;

import java.util.Hashtable;
import java.util.Vector;

/**
 *
 * @author Alessandro Aloisio
 */
public class Table {
    
    public static Hashtable<String, Object> tables;
    
    protected String numTable;
    public Vector<CommandePlat> plats = new Vector<>();
    protected int nbPlaces;
    public int nbCouverts = 0;
    protected double addition = 0;
    private boolean addPayer = false;
    protected String idServeur;
    
    static {
        tables = new Hashtable<String, Object>();
        
        tables.put("C13", new Table(4));
        tables.put("G1", new Table(4));
        tables.put("C11", new Table(4));
        tables.put("D1", new Table(4));
        tables.put("G2", new Table(4));
        tables.put("G3", new Table(4));
        tables.put("C21", new Table(5));
        tables.put("C22", new Table(5));
        tables.put("C12", new Table(6));
        tables.put("D2", new Table(2));
        tables.put("D3", new Table(2));
        tables.put("D4", new Table(2));
        tables.put("D5", new Table(2));
    }
    
    public Table(int nbPlaces) {
        this.nbPlaces=nbPlaces;
    }
    
    public Table(String numTable, int nbPlace) {
        this.numTable=numTable;
        this.nbPlaces=nbPlace;
    }
    
    public String toString(){
        return Integer.toString(this.nbPlaces);
    }
}
