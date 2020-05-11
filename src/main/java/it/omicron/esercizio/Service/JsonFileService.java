package it.omicron.esercizio.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.google.gson.Gson;

import it.omicron.esercizio.Model.MenuContent;

public class JsonFileService {

	private Gson gson;
	private String path;
	private BufferedReader bufferedReader;

	public JsonFileService(String path) {
		this.gson = new Gson();
		this.path = path;
	}

	public MenuContent readFromJson() {
		try {
			this.bufferedReader = new BufferedReader(new FileReader(this.path));
			MenuContent menuContent = gson.fromJson(bufferedReader, MenuContent.class);
			
			bufferedReader.close();
			return menuContent;
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}
}
