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

import it.omicron.esercizio.Model.MenuContent;
import it.omicron.esercizio.Model.MenuNode;

public class XlsxFileService {

	private String path;
	private Workbook workbook;
	private MenuContent menuContent;
	private Sheet sheet;
	private FileOutputStream fileOut;
	private Integer level;
	private Integer levelMax;
	private Integer rowsNumber;

	public XlsxFileService(String path, MenuContent menuContent) {
		this.path = path;
		this.workbook = new XSSFWorkbook();
		this.menuContent = menuContent;
		this.sheet = this.workbook.createSheet("Menu " + menuContent.getVersion());
		this.level = 0;
		this.levelMax = 0;
		this.calculateLevelMax(this.menuContent.getNodes(), 0);
		this.rowsNumber = 1;
	}

	private void calculateLevelMax(List<MenuNode> nodes, Integer level) {
		if (nodes != null) {
			for (MenuNode n : nodes) {
				// controllo se il nodo ha nodi-figli
				List<MenuNode> childrenNodes = n.getNodes();
				if (childrenNodes != null) {

					// se sono presenti nodi-figli aumento di un livello
					calculateLevelMax(childrenNodes, level += 1);
					if (this.levelMax < level)
						this.levelMax = level;

					// quando esco da un processRow torno al livello superiore
					level -= 1;
				}
			}
		}
	}

	public void createFile(String[] labels) {
		this.processRow(this.menuContent.getNodes(), this.level);

		this.createHeader(labels);

		this.closeWorkbook();
	}

	private void createHeader(String[] labels) {
		Row header = this.sheet.createRow(0);
		CellStyle headerStyle = this.workbook.createCellStyle();

		Font font = this.workbook.createFont();
		font.setBold(true);
		headerStyle.setFont(font);

		// write the 0-6 labels
		for (Integer i = 0; i <= this.levelMax; i++) {
			this.createCell(header, i, i.toString(), headerStyle);
		}

		// write the other labels
		Integer index = this.levelMax + 1;
		for (String label : labels) {
			this.createCell(header, index, label, headerStyle);
			index++;
		}
	}

	private void writeIntegeroXlsx() {
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

	private void closeWorkbook() {
		try {
			this.setSheetWidth();
			this.writeIntegeroXlsx();
			this.workbook.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
	}

	private void processRow(List<MenuNode> nodes, Integer level) {
		if (nodes != null) {
			for (MenuNode n : nodes) {
				// stampo il nodo
				this.createRow(n, level);

				// controllo se il nodo ha nodi-figli
				List<MenuNode> childrenNodes = n.getNodes();
				if (childrenNodes != null) {

					// se sono presenti nodi-figli vado a stamparli
					// tenendo presente che aumento di un livello
					processRow(childrenNodes, level += 1);

					// quando esco da un processRow vuol dire che ho
					// finito di stampare una serie di nodi-figli e
					// che quindi posso tornare al livello superiore
					level -= 1;
				}
			}
		}
	}

	private void createRow(MenuNode node, Integer level) {
		Row row = this.sheet.createRow(this.rowsNumber);

		this.createCell(row, level, "X", null);

		String[] values = this.setNodeData(node);

		int i = this.levelMax + 1;
		for (String value : values) {
			this.createCell(row, i, value, null);
			i++;
		}

		this.rowsNumber += 1;
		// this.writeIntegeroXlsx();
	}

	private String[] setNodeData(MenuNode node) {
		String nodeId = (node.getNodeId() != 0 && node.getNodeType().equals("service")) ? node.getNodeId().toString()
				: "";
		String resourceId = node.getResource() != null ? node.getResource().getId().toString() : "";

		return new String[] { nodeId, node.getNodeName(), node.getNodeType(), node.getGroupType(), node.getFlowType(),
				resourceId };
	}

	private void createCell(Row row, Integer position, String value, CellStyle style) {
		Cell cell = row.createCell(position);
		cell.setCellValue(value);
		cell.setCellStyle(style);
	}

	private void setSheetWidth() {
		for (int i = 0; i < 13; i++)
			this.sheet.autoSizeColumn(i);
	}

}