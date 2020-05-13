package it.omicron.esercizio.Main;

import it.omicron.esercizio.Model.MenuContent;
import it.omicron.esercizio.Service.JsonFileService;
import it.omicron.esercizio.Service.LucaXlsxFileService;
import it.omicron.esercizio.Service.PropertiesFileService;

public class ServiceMenuMain {

	public static void main(String[] args) {
		System.out.println("Inizio");

		PropertiesFileService pfs = new PropertiesFileService();

		/*JsonFileService jsonFileService = new JsonFileService(pfs.getPathInputFile());
		MenuContent menuContent = jsonFileService.readFromJson();

		if (menuContent != null) {
			LucaXlsxFileService xlsxFileService = new LucaXlsxFileService(pfs.getPathOutputFile(), menuContent);
			xlsxFileService.createFile();
		}
*/
		System.out.println("Fine");
	}
}
