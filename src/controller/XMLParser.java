package src.controller;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javafx.scene.paint.Color;
import src.Model.Cell;
import src.View.Grid;
import src.controller.Simulation.Simulation;


public class XMLParser {
	private String simType;
	private int xLen;
	private int yLen;
	private HashMap<Integer[], Integer> cellsMap = new HashMap<Integer[], Integer>();
	private HashMap<Integer, Color> statesMap = new HashMap<Integer, Color>();
	private ArrayList<Double> paramsList = new ArrayList<Double>();
	private ArrayList<Integer> statesList = new ArrayList<Integer>();
	private HashMap<String, Simulation> simTypes = new HashMap<String, Simulation>();

	public XMLParser(File myFile, HashMap<String, Simulation> simTypes) throws ParserConfigurationException, SAXException, IOException, NoSuchFieldException, SecurityException, ClassNotFoundException, DOMException, IllegalArgumentException, IllegalAccessException{
		this.simTypes = simTypes;
		fileExtensionCheck(myFile);
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(myFile);
		doc.getDocumentElement().normalize();
		parseFile(doc);
	}

	public void fileExtensionCheck(File file) throws IOException{
		String fileExtension = file.getName();
		int pos = fileExtension.lastIndexOf(".");
		if(fileExtension.length() >= 4 && !(pos >= fileExtension.length()-3)){
			fileExtension = fileExtension.substring(pos+1);
		}
		if(!fileExtension.equals("xml")){
			throw new IOException("Invalid file (Load xml).");
		}
	}

	public void noSimulationNameError() throws IOException{
		throw new IOException("Invalid file (No simulation name).");
	}

	public void parseFile(Document doc) throws NoSuchFieldException, SecurityException, ClassNotFoundException, DOMException, IllegalArgumentException, IllegalAccessException, IOException{
		NodeList simName = doc.getElementsByTagName("simulation");
		if(simName == null)
			noSimulationNameError();
		simType = simName.item(0).getAttributes().getNamedItem("type").getTextContent();
		NodeList states = doc.getElementsByTagName("state");
		createStatesMap(states);
		NodeList dimensions = doc.getElementsByTagName("dimen");
		setDimensions(dimensions);
		NodeList cells = doc.getElementsByTagName("cells");
		createCellsMap(cells);
		NodeList params = doc.getElementsByTagName("param");
		createParamsList(params, simType);
	}

	public void createStatesMap(NodeList states) throws NoSuchFieldException, SecurityException, ClassNotFoundException, DOMException, IllegalArgumentException, IllegalAccessException{
		for(int x=0; x<states.getLength(); x++){
			Node node = states.item(x);
			NamedNodeMap map = node.getAttributes();
			Node value = map.getNamedItem("value");
			Node color = map.getNamedItem("color");
			Color myColor;
			Field field = Class.forName("javafx.scene.paint.Color").getField(color.getTextContent()); // toLowerCase because the color fields are RED or red, not Red
			myColor = (Color)field.get(null);
			int state = Integer.parseInt(value.getTextContent());
			statesMap.put(state, myColor);
		}
	}

	public void createParamsList(NodeList params, String simType){
		ArrayList<Double> paramValues = paramsErrorCheck(params, simType);
		for(int x=0; x<paramValues.size(); x++){
			paramsList.add(paramValues.get(x));
		}
	}

	public ArrayList<Double> paramsErrorCheck(NodeList params, String simType){
		ArrayList<Double> paramValues = new ArrayList<Double>();
		ArrayList<String> paramNames = simTypes.get(simType).getParameters();
		int numParams = 0;
		if(paramNames != null)
			numParams = paramNames.size();
		fillWithDefault(paramValues, numParams);
		for(int x=0; x<params.getLength(); x++){
			Node node = params.item(x);
			NamedNodeMap map = node.getAttributes();
			for(int y=0; y<paramNames.size(); y++){
				Node value = map.getNamedItem(paramNames.get(y));
				if(value != null){
					paramValues.set(paramNames.indexOf(paramNames.get(y)), Double.parseDouble(value.getTextContent()));
					break;
				}
			}
		}
		return paramValues;
	}

	public void fillWithDefault(ArrayList<Double> paramValues, int numParams){
		double DEFAULT_VALUE = -1/999;
		for(int x=0; x<numParams; x++){
			paramValues.add(DEFAULT_VALUE);
		}
	}

	public void createCellsMap(NodeList cells){
		for(int x=0; x<cells.getLength(); x++){
			Node node = cells.item(x);
			NamedNodeMap map = node.getAttributes();
			String str = map.getNamedItem("row").getTextContent();
			String[] stringArr = str.split(" ");
			Integer[] intArr = new Integer[stringArr.length];
			for(int y=0; y<intArr.length; y++){
				int state = Integer.parseInt(stringArr[y]);
				Integer[] dimens = {x, y};
				cellsMap.put(dimens, state);
			}
		}
	}

	public Grid makeCellsGrid(HashMap<Integer[], Integer> map, int xLen, int yLen){
		Grid grid = new Grid(xLen, yLen);
		Iterator<Entry<Integer[], Integer>> it = map.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<Integer[], Integer> pair = (Map.Entry)it.next();
			Integer[] dimens = pair.getKey();
			int state = map.get(dimens);
			statesList.add(state);
			Cell cell = new Cell(dimens[0], dimens[1], state);
			grid.setCell(cell);
		}
		return grid;
	}

	public void setDimensions(NodeList dimen){
		NamedNodeMap xDimen = dimen.item(0).getAttributes();
		NamedNodeMap yDimen = dimen.item(1).getAttributes();
		xLen = Integer.parseInt(xDimen.getNamedItem("xLen").getTextContent());
		yLen = Integer.parseInt(yDimen.getNamedItem("yLen").getTextContent());
	}

	public HashMap<Integer[], Integer> getCellsMap(){
		return cellsMap;
	}

	public HashMap<Integer, Color> getStatesMap(){
		return statesMap;
	}

	public ArrayList<Double> getParamsList(){
		return paramsList;
	}

	public int getXLen(){
		return xLen;
	}

	public int getYLen(){
		return yLen;
	}

	public String getSimType(){
		return simType;
	}

	public ArrayList<Integer> getStatesList(){
		return statesList;
	}
}