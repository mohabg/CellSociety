package src.controller;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

/**
 * Created by davidyan on 2/4/16.
 */
public interface EventListener {
    void playAnimation();
    void pauseAnimation();
    void stepAnimation();
    void onSliderMove(int toUseValue);
    void onFileSelection(File file) throws NoSuchFieldException, SecurityException, ClassNotFoundException, DOMException, IllegalArgumentException, IllegalAccessException, ParserConfigurationException, SAXException, IOException;
}