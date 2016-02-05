package controller;
import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import javafx.application.Application;
import javafx.stage.Stage;

public class MainHandler extends Application{
	
	public static void main(String[] args){
		launch(args);
	}

	public void loadFile(File file) throws ParserConfigurationException, SAXException, IOException{
		XMLParser parser = new XMLParser(file);
	}

	public void start(Stage s) throws Exception {
		
	}
}