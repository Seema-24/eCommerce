package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtility {

	FileInputStream fis;
	FileOutputStream fos;
	XSSFWorkbook wb;
	XSSFSheet sheet;
	XSSFRow row;
	XSSFCell cell;
	XSSFCellStyle style;
	String path;
	
	public ExcelUtility(String path) {
		this.path = path;
	}
	
	public int getRowCount(String sheetName) throws IOException {
		fis = new FileInputStream(path);
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheet(sheetName);
		int rowCount = sheet.getLastRowNum();
		
		wb.close();
		fis.close();
		return rowCount;
	}
	
	public int getCellCount(String sheetName, int rowNum) throws IOException {
		fis = new FileInputStream(path);
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheet(sheetName); 
		row = sheet.getRow(rowNum);
		short cellCount = row.getLastCellNum();
		
		wb.close();
		fis.close();
		return cellCount;
	}
	
	public String getCellData(String sheetName, int rowNum, int colNum) throws IOException {
		
		fis = new FileInputStream(path);
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		cell = row.getCell(colNum); 
		
		String cellData; 
		DataFormatter format = new DataFormatter();
		try {
			cellData  = format.formatCellValue(cell);
		} catch (Exception e) {
			cellData = "";
		}
		
		return cellData;
	}
	
	public void setCellData(String sheetName, int rowNum, int colNum, String data) throws IOException {
		 
		File xlFile = new File(path);
		if(!xlFile.exists()) {       //If file not exists then create new File
			wb = new XSSFWorkbook();
			fos = new FileOutputStream(path);
			wb.write(fos);
		}
		
		fis = new FileInputStream(path);
		wb = new XSSFWorkbook(fis);
		
		if (wb.getSheetIndex(sheetName) == -1) {  //If Sheet not exist then create new Sheet
			wb.createSheet(sheetName);
			sheet = wb.getSheet(sheetName);
		}
		
		if(sheet.getRow(rowNum) == null) //if row not exist then create new Row
			sheet.createRow(rowNum);
		row = sheet.getRow(rowNum);
		cell = row.createCell(colNum);
		cell.setCellValue(data);
		
		fos = new FileOutputStream(path);
		wb.write(fos);
		
		wb.close();
		fis.close();
		fos.close();
		
	}
	
	public void filledColor(String sheetName, int rowNum, int colNum) throws IOException {
		fis = new FileInputStream(path);
		wb = new XSSFWorkbook(fis);
		sheet = wb.getSheet(sheetName);
		row = sheet.getRow(rowNum);
		cell = row.getCell(colNum);
		
		style = wb.createCellStyle();
		style.setFillBackgroundColor(IndexedColors.RED.getIndex());
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		
		cell.setCellStyle(style);
		
		wb.write(fos);
		wb.close();
		fis.close();
		
	}
	
	
	
	
	
	
}
