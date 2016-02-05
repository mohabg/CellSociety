package controller;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XMLParser {
	private NodeList nodes;
	private String simType;
	private int xLen;
	private int yLen;
	private Integer[][] Grid;
	
	public XMLParser(File myFile) throws ParserConfigurationException, SAXException, IOException{
		fileCheck(myFile);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(myFile);
		nodes = doc.getDocumentElement().getChildNodes();
		parseFile();
	}
	
	public void fileCheck(File file) throws IOException{
		String fileExtension = file.getName();
		int pos = fileExtension.lastIndexOf(".");
		if(pos > 0){
			fileExtension = fileExtension.substring(pos+1);
		}
		if(pos<=0 || !fileExtension.equals("xml")){
			throw new IOException("Invalid file extension."); 
		}
	}
	
	public void parseFile(){
		
	}
}