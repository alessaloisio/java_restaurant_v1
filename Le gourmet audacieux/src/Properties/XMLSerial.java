/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Properties;

import java.io.File;
import java.io.IOException;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author Danie
 */
public class XMLSerial {
    
    
    static private String fileNameServer = "src\\le\\gourmet\\audacieux\\lib\\serveurs.xml";
    
    static public Vector<String> getServers()
    {
        
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            	
        try {
            Vector<String> serveursStr = new Vector<String>();
            
            final DocumentBuilder builder = factory.newDocumentBuilder();
	    final Document document= builder.parse(new File(fileNameServer));
	    final Element racine = document.getDocumentElement();
	    final NodeList racineNoeuds = racine.getChildNodes();
            
	    final int nbRacineNoeuds = racineNoeuds.getLength();
			
	    for (int i = 0; i<nbRacineNoeuds; i++) {
	        if(racineNoeuds.item(i).getNodeType() == Node.ELEMENT_NODE) {
	            final Element serveur = (Element) racineNoeuds.item(i);

                    serveursStr.add(serveur.getTextContent());
                    System.out.println(serveur.getTextContent());
	        }				
	    }
            return serveursStr;
        }
        catch (final ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (final SAXException e) {
            e.printStackTrace();
        }
        catch (final IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    static public void addServeur(String nom, String pwd) {
        
        
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        
        try {

            
            final DocumentBuilder builder = factory.newDocumentBuilder();
            final Document document= builder.parse(new File(fileNameServer));
            final Element serveur = document.createElement("serveur");
            serveur.appendChild(document.createTextNode(nom));

            final Element racine = document.getDocumentElement();
            System.out.println(racine.getNodeName());
            racine.appendChild(serveur);
            
            
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            Result output = new StreamResult(fileNameServer);
            Source input = new DOMSource(document);

            transformer.transform(input, output);
            
            Props.setProperties(nom, pwd);
        }
        catch (final ParserConfigurationException e) {
            e.printStackTrace();
        }
        catch (final SAXException e) {
            e.printStackTrace();
        }
        catch (final IOException e) {
            e.printStackTrace();
        } catch (TransformerConfigurationException ex) {
            Logger.getLogger(XMLSerial.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(XMLSerial.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
