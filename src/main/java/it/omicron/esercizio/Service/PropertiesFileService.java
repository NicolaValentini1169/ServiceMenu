package it.omicron.esercizio.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import it.omicron.esercizio.Exception.InvalidPathException;

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

			// pathInputFile = checkPathInputFile(prop.getProperty("pathInputFile"));
			pathInputFile = checkPath(prop.getProperty("pathInputDirectory"), prop.getProperty("nameInputFile"), false);
			pathOutputFile = checkPath(prop.getProperty("pathOutputDirectory"), prop.getProperty("nameOutputFile"),
					true);

			System.out.println("pathInputFile " + pathInputFile);
			System.out.println("pathOutputFile " + pathOutputFile);
		} catch (FileNotFoundException e) {
			System.out.println("Errore PropertiesFileService. " + e.getMessage());
		} catch (InvalidPathException e) {
			System.out.println("Errore PropertiesFileService. " + e.getMessage());
		} catch (IOException e) {
			System.out.println("Errore PropertiesFileService. " + e.getMessage());
		}
	}

	private String checkPath(String pathDirectory, String nameFile, boolean toCreate) {
		pathDirectory = checkSlashAndBackslash(pathDirectory);
		if (pathDirectory == null)
			throw new InvalidPathException("Invalid input path directory. Check the backslash. Must be \\ or /");

		// controllo se la cartella esiste
		if (!checkDirectory(pathDirectory, toCreate))
			throw new InvalidPathException("Invalid input path directory. Directory doesn't exist.");

		// creo il path completo
		String pathFile = getPathFile(pathDirectory, nameFile);

		// controllo se il file esiste
		if (!checkFile(pathFile, toCreate))
			throw new InvalidPathException("Invalid input file name. File doesn't exist.");

		return pathFile;
	}

	private String checkSlashAndBackslash(String path) {
		// per url tipo ./input/ServiceMenu.json
		if (path.contains("/"))
			path = path.replaceAll("/", "\\\\");

		// java non riconosce lo backslash singolo come un carattere
		// se leggo un path coi backslash singoli me li cancella automaticamente
		// da cosi => .\input\ServiceMenu.json
		// mi legge => .inputServiceMenu.json

		// per url tipo .\input\ServiceMenu.json
		if (!path.contains("\\"))
			return null;

		return path;
	}

	private boolean checkDirectory(String path, boolean toCreate) {
		File directory = new File(path);
		// se esiste passo dritto e ritorno true
		// se non esiste ed è da creare la creo
		// e poi esiste quindi passerà dritto e ritornerà true
		// se non esiste e non è da creare ritorna false
		if (!directory.exists() && toCreate) {
			directory.mkdirs();
		} else if (!directory.exists())
			return false;

		return true;
	}

	private String getPathFile(String pathDirectory, String nameFile) {
		if (pathDirectory.endsWith("\\"))
			return pathDirectory + nameFile;
		else
			return pathDirectory + "\\" + nameFile;
	}

	private boolean checkFile(String path, boolean toCreate) {
		File file = new File(path);
		// se esiste passo dritto e ritorno true
		// se non esiste ed è da creare la creo
		// e poi esiste quindi passerà dritto e ritornerà true
		// se non esiste e non è da creare ritorna false
		if (!file.exists() && toCreate) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println("Errore PropertiesFileService " + e.getMessage());
			}
		} else if (!file.exists())
			return false;

		return true;
	}

	public String getPathInputFile() {
		return pathInputFile;
	}

	public String getPathOutputFile() {
		return pathOutputFile;
	}

}
