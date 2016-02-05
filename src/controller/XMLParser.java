package src.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;


public class XMLParser {
	private String simType;
	private int xLen;
	private int yLen;
	private ArrayList<HashMap<Integer[], Integer>> cellsMap = new ArrayList<HashMap<Integer[], Integer>>();
	private HashMap<Integer, String> statesMap = new HashMap<Integer, String>();
	private HashMap<String, Double> paramsMap = new HashMap<String, Double>();
	
	public XMLParser(File myFile) throws ParserConfigurationException, SAXException, IOException{
		fileCheck(myFile);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(myFile);
		doc.getDocumentElement().normalize();
		parseFile(doc);
	}
	
	public void fileCheck(File file) throws IOException{
		String fileExtension = file.getName();
		int pos = fileExtension.lastIndexOf(".");
		if(fileExtension.length() >= 4 && !(pos >= fileExtension.length()-3)){
			fileExtension = fileExtension.substring(pos+1);
		}
		if(!fileExtension.equals("xml")){
			throw new IOException("Invalid file (Load xml)."); 
		}
	}
	
	public void parseFile(Document doc){
		simType = doc.getDocumentElement().getNodeName();
		NodeList states = doc.getElementsByTagName("state");
		createStatesMap(states);
		NodeList dimensions = doc.getElementsByTagName("state");
		setDimensions(dimensions);
		NodeList cells = doc.getElementsByTagName("cells");
		createCellsMap(cells);
		NodeList params = doc.getElementsByTagName("param");
		createParamsMap(params);
	}
	
	public void createStatesMap(NodeList states){
		for(int x=0; x<states.getLength(); x++){
			Node node = states.item(x);
			NamedNodeMap map = node.getAttributes();
			Node value = map.getNamedItem("value");
			Node color = map.getNamedItem("color");
			statesMap.put(Integer.parseInt(value.getTextContent()), color.getTextContent());
		}
	}
	
	public void createParamsMap(NodeList states){
		for(int x=0; x<states.getLength(); x++){
			Node node = states.item(x);
			NamedNodeMap map = node.getAttributes();
			Node value = map.getNamedItem("param");
			paramsMap.put(value.getNodeName(), Double.parseDouble(value.getTextContent()));
		}
	}
	
	public void createCellsMap(NodeList cells){
		for(int x=0; x<cells.getLength(); x++){
			Node node = cells.item(x);
			String str = node.getTextContent();
			String[] stringArr = str.split(" ");
			Integer[] intArr = new Integer[stringArr.length];
			for(int y=0; y<intArr.length; y++){
				int state = intArr[y];
				Integer[] dimens = {x, y};
				HashMap<Integer[], Integer> cellMapping = new HashMap<Integer[], Integer>();
				cellMapping.put(dimens, state);
				cellsMap.add(cellMapping);
			}
		}
	}
	
	public void setDimensions(NodeList dimensions){
		xLen = Integer.parseInt(dimensions.item(0).getNodeName());
		yLen = Integer.parseInt(dimensions.item(1).getNodeName());
	}
}