package com.andreea.ireland.server;

import com.andreea.ireland.shared.Poi;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;


public class PoiFactory {
    public static Poi newInstance(String poiFile) {
    	Poi p;
    	
    	File fXmlFile = new File(poiFile);
    	DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
    	DocumentBuilder dBuilder;
    	Document doc;
    	
		try {
			dBuilder = dbFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			return null;
		}
		
    	try {
    		doc = dBuilder.parse(fXmlFile);
		} catch (SAXException | IOException e) {
			return null;
		}
    	
        String title = getNodeValue(doc, "title");
        String description = getNodeValue(doc, "description");
        float longitude = Float.valueOf(getNodeValue(doc, "longitude"));
        float latitude = Float.valueOf(getNodeValue(doc, "latitude"));
        
        p = new Poi(title, description, longitude, latitude);
        
        NodeList fields = doc.getElementsByTagName("field");
        for (int i = 0; i < fields.getLength(); i++) {
        	Node field = fields.item(i);
        	NodeList children = field.getChildNodes();
        	String name = "", value = "";
        	
        	for (int j =0; j < children.getLength(); j++) {
        		Node node = children.item(j);
        		if (node.getNodeName() == "name") {
        			name = node.getTextContent();
        		}
        		if (node.getNodeName() == "value") {
        			value = node.getTextContent();
        		}
        	}
        	
        	p.addDetail(name, value);
        }
        
        return p;
    }
    
    private static String getNodeValue(Document doc, String tag) {
    	NodeList values = doc.getElementsByTagName(tag);
    	return values.item(0).getTextContent();
    }
}