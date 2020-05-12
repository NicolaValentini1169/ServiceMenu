package it.omicron.esercizio.Main;

import it.omicron.esercizio.Model.MenuContent;
import it.omicron.esercizio.Service.JsonFileService;
import it.omicron.esercizio.Service.XlsxFileService;

public class ServiceMenuMain {

	public static void main(String[] args) {
		System.out.println("Inizio");

		String pathInputFile = ".\\input\\ServiceMenu.json";
		String pathOutputFile = ".\\output\\ServiceMenu.xlsx";
		String[] xlsxFileLabels = new String[] { "ServiceId", "NodeName", "NodeType",
				"GroupType", "FlowType", "ResouceId" };

		JsonFileService jsonFileService = new JsonFileService(pathInputFile);
		MenuContent menuContent = jsonFileService.readFromJson();

		if (menuContent != null) {
			XlsxFileService xlsxFileService = new XlsxFileService(pathOutputFile,menuContent);
			xlsxFileService.createFile(xlsxFileLabels);
		}

		System.out.println("Fine");
	}
}
