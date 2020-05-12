package it.omicron.esercizio.Service;

import java.io.FileNotFoundException;
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

import it.omicron.esercizio.Model.MenuNode;

public class XlsxFileService {

	private String path;
	private Workbook workbook;
	private Sheet sheet;
	private FileOutputStream fileOut;
	private int level;
	private int rowsNumber;

	public XlsxFileService(String path, String nameSheet) {
		this.path = path;
		this.workbook = new XSSFWorkbook();
		this.sheet = this.workbook.createSheet(nameSheet);
		this.level = 0;
		this.rowsNumber = 1;
	}

	public void createHeader(String[] labels) {
		Row header = this.sheet.createRow(0);
		CellStyle headerStyle = this.workbook.createCellStyle();

		Font font = this.workbook.createFont();
		font.setBold(true);
		headerStyle.setFont(font);

		int index = 0;
		for (String label : labels) {
			Cell cell = header.createCell(index);
			cell.setCellValue(label);
			cell.setCellStyle(headerStyle);
			index++;
		}
		this.writeIntoXlsx();
	}

	public void writeIntoXlsx() {
		try {
			this.fileOut = new FileOutputStream(this.path);
			this.workbook.write(this.fileOut);
			this.fileOut.close();
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void closeWorkbook() {
		try {
			this.setSheetWidth();
			this.writeIntoXlsx();

			this.workbook.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	public void createRow(List<MenuNode> nodes) {
		if (nodes != null) {
			for (MenuNode n : nodes) {
				// stampo il nodo
				this.setRowData(n);

				// controllo se il nodo ha nodi-figli
				List<MenuNode> childrenNodes = n.getNodes();
				if (childrenNodes != null) {
					// se sono presenti nodi-figli vado a stamparli
					// tenendo presente che aumento di un livello
					this.level += 1;
					createRow(childrenNodes);
				}
			}
			// quando esco da un loop vuol dire che ho finito di stampare
			// una serie di nodi-figli e quindi posso tornare al livello superiore
			this.level -= 1;
		} else {
			return;
		}
	}

	private void setRowData(MenuNode node) {
		Row row = this.sheet.createRow(this.rowsNumber);

		Cell cell = row.createCell(this.level);
		cell.setCellValue("X");

		Cell cellServiceId = row.createCell(7);
		Object nodeId = (node.getNodeId() != 0 && node.getNodeType().equals("service")) ? node.getNodeId() : "";
		cellServiceId.setCellValue(nodeId.toString());

		Cell cellNodeName = row.createCell(8);
		cellNodeName.setCellValue(node.getNodeName());

		Cell cellNodeType = row.createCell(9);
		cellNodeType.setCellValue(node.getNodeType());

		Cell cellGroupType = row.createCell(10);
		cellGroupType.setCellValue(node.getGroupType());

		Cell cellFlowType = row.createCell(11);
		cellFlowType.setCellValue(node.getFlowType());

		Cell cellResouceId = row.createCell(12);
		Object resourceId = node.getResource() != null ? node.getResource().getId() : "";
		cellResouceId.setCellValue(resourceId.toString());

		this.rowsNumber += 1;
		this.writeIntoXlsx();
	}

	private void setSheetWidth() {
		for (int i = 0; i < 13; i++)
			this.sheet.autoSizeColumn(i);
	}

}