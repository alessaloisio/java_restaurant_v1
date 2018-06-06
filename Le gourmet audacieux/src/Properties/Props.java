/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Properties;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Danie
 */
public class Props {
    
    static public void setProperties(String key, String value)
    {
                
	try {

            if(Props.getProperties().getProperty(key) != null)
            {
                String oldContent = "";
                BufferedReader reader = new BufferedReader(new FileReader("config.properties"));
                String line = reader.readLine();
                while (line != null) 
                {
                        
                        if(line.contains(key))
                        {
                            oldContent = oldContent + key+ "=" + value + System.lineSeparator();
                            break;
                        }
                        oldContent = oldContent + line + System.lineSeparator();
                        line = reader.readLine();
                }
                
                FileWriter fileWritter = new FileWriter("config.properties");
                BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                bufferWritter.write(oldContent);
                reader.close();
                bufferWritter.close();
            }
            else
            {
                FileWriter fileWritter = new FileWriter("config.properties", true);
                BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
                bufferWritter.append(key+ "=" + value); 
                bufferWritter.newLine();
                bufferWritter.close();
            }

            

	} 
        catch (IOException ex) {
            Logger.getLogger(Props.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    static public Properties getProperties()
    {
        Properties prop = new Properties();
	InputStream input = null;

	try {

		input = new FileInputStream("config.properties");

		// load a properties file
		prop.load(input);
                return prop;


	} catch (IOException ex) {
                System.out.println("Fichier properties non créé.");
		ex.printStackTrace();
	} finally {
            if (input != null) {
                try {
                        input.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
            }
        }
        return null;
    }
}
    
