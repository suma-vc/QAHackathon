package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XML_Utility {
	private static final Logger logger = LoggerFactory.getLogger(FileUtility.class);
	EnvironmentVariables ev=new EnvironmentVariables();
	public boolean xpathValueCompare(String xpathString, Document doc, String value) {
		boolean flag=false;
		try {
			XPath xpath = XPathFactory.newInstance().newXPath();
			flag =  (boolean) xpath.evaluate(xpathString+"[text()='"+value+"']", doc, XPathConstants.BOOLEAN);	
			System.out.println(xpathString+"[text()='"+value+"']");
			

			return flag;	    
		} catch ( XPathExpressionException e) {
			e.printStackTrace();
		}
		return flag;

	} 
	public boolean xpathValueContains(String xpathString, Document doc, String value) {
		boolean flag=false;
		try {
			XPath xpath = XPathFactory.newInstance().newXPath();
			System.out.println(xpathString+"[contains(text(),'"+value+"')]");
			flag =  (boolean) xpath.evaluate(xpathString+"[contains(text(),'"+value+"')]", doc, XPathConstants.BOOLEAN);	

			return flag;	    
		} catch ( XPathExpressionException e) {
			e.printStackTrace();
		}
		return flag;

	} 
	public boolean xpathAttributeContains(String xpathString, Document doc,String attribute, String value) {
		boolean flag=false;
		try {
			XPath xpath = XPathFactory.newInstance().newXPath();
			System.out.println(xpathString+"[contains(@"+attribute+",'"+value+"')]");
			flag =  (boolean) xpath.evaluate(xpathString+"[contains(@"+attribute+",'"+value+"')]", doc, XPathConstants.BOOLEAN);	

			return flag;	    
		} catch ( XPathExpressionException e) {
			e.printStackTrace();
		}
		return flag;

	} 
	public boolean xpathAttributeCompare(String xpathString, Document doc,String attribute, String value) {
		boolean flag=false;
		try {
			XPath xpath = XPathFactory.newInstance().newXPath();
			System.out.println(xpathString+"[@"+attribute+"='"+value+"']");
			flag =  (boolean) xpath.evaluate(xpathString+"[@"+attribute+"='"+value+"']", doc, XPathConstants.BOOLEAN);	
			
			return flag;	    
		} catch ( XPathExpressionException e) {
			e.printStackTrace();
		}
		return flag;
		
	} 
	public Document parseXML(String filePath) {

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		try (InputStream is = new FileInputStream(filePath)) {

			DocumentBuilder db = dbf.newDocumentBuilder();

			Document doc = db.parse(is);

			return doc;


		} catch (ParserConfigurationException | SAXException |
				IOException e) {
			e.printStackTrace();
		}
		return null;

	}
	public Document parseXMLString(String xmlString) {
		Document doc = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();


			DocumentBuilder db = dbf.newDocumentBuilder();

			doc = db.parse(new InputSource(new StringReader(xmlString)));
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}


		return doc;

	}
	public NodeList getNodeList(String xpthString,Document doc) {
		try {
			XPath xpath = XPathFactory.newInstance().newXPath();
			NodeList nodes;
			nodes = (NodeList)
					xpath.evaluate(xpthString+"", doc, XPathConstants.NODESET);
			int count = nodes.getLength();
			System.out.println("Number of elements : " + count);

			return nodes;
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;

	}

	public List<String> getNodeValues(String xpthString,Document doc) {
		List<String> values = new ArrayList<>();
		try {
			XPath xpath = XPathFactory.newInstance().newXPath();
			NodeList nodes;
			nodes = (NodeList)
					xpath.evaluate(xpthString+"/text()", doc, XPathConstants.NODESET);
			int count = nodes.getLength();

			System.out.println("Number of elements : " + count);
			for (int i = 0; i < nodes.getLength(); i++) {
				values.add(nodes.item(i).getNodeValue());
			}
			System.out.println(values);
			return values;
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}
		return null;

	}
	public String getNodeText(String xpthString,Document doc) {
		try {
			XPath xpath = XPathFactory.newInstance().newXPath();
			NodeList nodes;
			nodes = (NodeList)
					xpath.evaluate(xpthString+"", doc, XPathConstants.NODESET);
			int count = nodes.getLength();
			System.out.println("Number of elements : " + count);

			return nodes.item(0).getTextContent();
		} catch (XPathExpressionException e) {
			e.printStackTrace();
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
