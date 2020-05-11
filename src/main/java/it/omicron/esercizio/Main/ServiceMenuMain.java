package it.omicron.esercizio.Main;

import it.omicron.esercizio.Model.MenuContent;
import it.omicron.esercizio.Service.JsonFileService;
import it.omicron.esercizio.Service.XlsxFileService;

public class ServiceMenuMain {

	public static void main(String[] args) {
		System.out.println("Inizio");

		String pathInputFile = ".\\input\\ServiceMenu.json";
		String pathOutputFile = ".\\output\\ServiceMenu.xlsx";
		String[] xlsxFileLabels = new String[] { "0", "1", "2", "3", "4", "5", "6", "ServiceId", "NodeName", "NodeType",
				"GroupType", "FlowType", "ResouceId" };

		JsonFileService jsonFileService = new JsonFileService(pathInputFile);
		MenuContent menuContent = jsonFileService.readFromJson();

		if (menuContent != null) {
			XlsxFileService xlsxFileService = new XlsxFileService(pathOutputFile, "Menu" + menuContent.getVersion());
			xlsxFileService.createHeader(xlsxFileLabels);
			
			xlsxFileService.createRow(menuContent.getNodes());
			
			xlsxFileService.closeWorkbook();
		}

		System.out.println("Fine");
	}
}
