/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package le.gourmet.audacieux.Salle;

import Properties.Props;
import Properties.XMLSerial;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Vector;

/**
 *
 * @author Alessandro Aloisio
 */
public class Serveur {
    
    private static Hashtable<String, String> logins;
    
    public String Nom;
    public String Prenom;   
    
    private String login;
    
    static private Properties prop;
    
    static {
        String mdp = null;
        logins = new Hashtable<String, String>();

        Vector<String> servers = XMLSerial.getServers();
        prop = Props.getProperties();
        for(int i=0; i<servers.size(); i++)
        {
            mdp = prop.getProperty(servers.elementAt(i), null);
            
            if(mdp != null)
            {
                logins.put(servers.elementAt(i), mdp);
                System.out.println("LOGIN" + servers.elementAt(i) + mdp);
            }
        }
    }
    
    public Serveur(String login) {
        this.login=login;
    }
    
    public String getLogin() {
        return this.login;
    }
    
    static Serveur authenticate(String login, String password) {
        
        if(login.length() > 0)
        {
            
            if(logins.containsKey(login))
            {
                
                if(password.length() > 0)
                {

                    if(logins.get(login).equals(password))
                    {
                        return new Serveur(login);
                    }else{
                        System.out.println("Aucun utilisateur trouvé");
                    }

                }else{
                    System.out.println("Vous devez entrer un mot de passe");
                }
                   
                
            }else{
                System.out.println("Aucun utilisateur trouver");
            }

        }else{
            System.out.println("Vous devez entrer un nom d'utilisateur");
        }
        
        return null;
        //return new Serveur("aloisio");
    }

}
