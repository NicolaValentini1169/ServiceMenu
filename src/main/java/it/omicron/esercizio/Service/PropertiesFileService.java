package it.omicron.esercizio.Service;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesFileService {

	private String pathInputFile;
	private String pathOutputFile;
	private Properties prop;
	private FileInputStream fileInputStream;
	private String pathConfigFile = ".\\config.properties";

	public PropertiesFileService() {
		prop = new Properties();
		readFile();
	}

	private void readFile() {
		try {
			fileInputStream = new FileInputStream(pathConfigFile);
			prop.load(fileInputStream);

		} catch (FileNotFoundException e) {
			System.out.println("Errore" + e.getMessage());
		} catch (IOException e) {
			System.out.println("Errore" + e.getMessage());
		}
	}

	public String getPathInputFile() {
		return prop.getProperty("pathInputFile");
	}

	public String getPathOutputFile() {
		return prop.getProperty("pathOutputFile");
	}

}
