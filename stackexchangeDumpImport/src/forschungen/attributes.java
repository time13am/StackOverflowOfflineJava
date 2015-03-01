/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package forschungen;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.SAXException;
        
public class attributes {
    
    public static List<String> ATTRIBUTES;
    
    public static void main(String[] args)throws SAXException, IOException, ParserConfigurationException {
        
        ATTRIBUTES = new ArrayList<String>();
        
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(false);
        DocumentBuilder db = dbf.newDocumentBuilder();
        
        Document doc = db.parse(new FileInputStream(new File("G:\\Temp\\german\\Posts.xml")));
        
        NodeList entries = doc.getElementsByTagName("row");
        
        int num = entries.getLength();
        
        for (int i = 0; i < num; i++) {
            Element node = (Element) entries.item(i);
            listAllAttributes(node, i);
        }
        
    }
    
    public static void listAllAttributes(Element element, int iteration) {
        
        NamedNodeMap attributes = element.getAttributes();
        
        int numAttrs = attributes.getLength();
        
        for (int i = 0; i < numAttrs; i++) {
            Attr attr = (Attr) attributes.item(i);
            
            String attrName = attr.getNodeName();
            String attrValue = attr.getNodeValue();
            
            if (!ATTRIBUTES.contains(attrName)){
                ATTRIBUTES.add(attrName);
                System.out.println(iteration + ": " + attrName);
            }
        }
    }
    
}
