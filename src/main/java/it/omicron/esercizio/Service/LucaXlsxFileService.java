package it.omicron.esercizio.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import it.omicron.esercizio.Model.MenuContent;
import it.omicron.esercizio.Model.MenuNode;

public class LucaXlsxFileService {

	private String path;
	private Workbook workbook;
	private MenuContent menuContent;
	private Sheet sheet;
	private FileOutputStream fileOut;
	private Integer levelMax = 0;
	private Integer rowsNumber = 1;
	String[] labels = new String[] { "ServiceId", "NodeName", "NodeType",
			"GroupType", "FlowType", "ResouceId" };

	public LucaXlsxFileService(String path, MenuContent menuContent) {
		this.path = path;
		workbook = new XSSFWorkbook();
		this.menuContent = menuContent;
		sheet = workbook.createSheet("Menu " + menuContent.getVersion());
		if(menuContent.getNodes() != null && !menuContent.getNodes().isEmpty())
			calculateLevelMax(menuContent.getNodes(), 0);
	}
	
	public void createFile() {
		processRow(menuContent.getNodes(), 0);
		createHeader();
		closeWorkbook();
	}

	private void calculateLevelMax(List<MenuNode> nodes, Integer level) {
		for (MenuNode n : nodes) {			
			checkLevelMax(level);
			if(n.getNodes() != null && !n.getNodes().isEmpty()) {
				calculateLevelMax(n.getNodes(), level+1);
			}			
		}
	}

	private void checkLevelMax(Integer level) {
		if (level > levelMax) {
			levelMax = level;
		}
	}

	private void createHeader() {
		Row header = sheet.createRow(0);
		CellStyle headerStyle = workbook.createCellStyle();

		Font font = workbook.createFont();
		font.setBold(true);
		headerStyle.setFont(font);

		// write the 0-6 labels
		for (Integer i = 0; i <= levelMax; i++) {
			createCell(header, i, i.toString(), headerStyle);
		}

		// write the other labels
		Integer index = levelMax + 1;
		for (String label : labels) {
			createCell(header, index, label, headerStyle);
			index++;
		}
	}

	private void closeWorkbook() {
		try {
			setSheetWidth();
			writeIntoXlsx();
			workbook.close();
		} catch (IOException e) {
			System.out.println("Errore LucaXlsxFileService " + e.getMessage());
		}
	}
	
	private void writeIntoXlsx() {
		try {
			fileOut = new FileOutputStream(path);
			workbook.write(fileOut);
			fileOut.close();
		} catch (IOException e) {
			System.out.println("Errore LucaXlsxFileService " + e.getMessage());
		}
	}
	
	private void setSheetWidth() {
		int columns = labels.length+levelMax+1;
		for (int i = 0; i < columns; i++)
			sheet.autoSizeColumn(i);
	}

	private void processRow(List<MenuNode> nodes, Integer level) {
		if (nodes != null) {
			for (MenuNode n : nodes) {
				// stampo il nodo
				createRow(n, level);
				
				if(n.getNodes() != null && !n.getNodes().isEmpty()) {
					// se sono presenti nodi-figli vado a stamparli
					// tenendo presente che aumento di un livello
					processRow(n.getNodes(), level +1);
				}
			}
		}
	}

	private void createRow(MenuNode node, Integer level) {
		Row row = sheet.createRow(rowsNumber);

		createCell(row, level, "X", null);

		String[] values = setNodeData(node);

		int i = levelMax + 1;
		for (String value : values) {
			createCell(row, i++, value, null);			
		}

		rowsNumber++;
	}

	private String[] setNodeData(MenuNode node) {
		String nodeId = (node.getNodeId() != 0 && node.getNodeType().equals("service")) ? node.getNodeId().toString(): "";
		String resourceId = node.getResource() != null ? node.getResource().getId().toString() : "";

		return new String[] { nodeId, node.getNodeName(), node.getNodeType(), node.getGroupType(), node.getFlowType(), resourceId };
	}

	private void createCell(Row row, Integer position, String value, CellStyle style) {
		Cell cell = row.createCell(position);
		cell.setCellValue(value);
		cell.setCellStyle(style);
	}

	

}