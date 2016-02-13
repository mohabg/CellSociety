package src.controller;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertiesReader {
    public static final String folder = "propertiesfiles";
    private static final String prefix = "cellsociety.";
    private static final String suffix = ".properties";
    Properties myProperties;
    private InputStream myFileStream;

    public void load(String language) throws IOException {

        String filename = folder + "/" + prefix + language + suffix;
        myFileStream = getClass().getClassLoader().getResourceAsStream(filename);
        if (myFileStream == null) {
            throw new FileNotFoundException(String.format("Could not load lang file: %s", filename));
        }
        myProperties = new Properties();
        myProperties.load(myFileStream);
    }
    public String getString(String key) {
        return myProperties.getProperty(key);
    }


}

