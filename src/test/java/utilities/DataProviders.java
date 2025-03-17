package utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class DataProviders {

	String sheetName = "Sheet1";
	
	//DataProvider 1
	
	@DataProvider(name= "LoginData")
	public String[][] getData() throws IOException {
		
		String path = ".\\testData\\loginData.xlsx"; //taking xl file from testData
		
		ExcelUtility xlutils = new ExcelUtility(path); //creating obj fro XLUtility

		int totalRows = xlutils.getRowCount(sheetName);
		int totalCols = xlutils.getCellCount(sheetName, 1);
		String loginData[][] = new String[totalRows][totalCols]; //created for two dimensional array which can stored row and col
		
		for (int i = 1; i <= totalRows; i++) {      // 1  //read data from xl storing in two dimensional array
			
			for (int j = 0; j < totalCols; j++) {  // 0     // i is rows j is col
				
				loginData[i-1][j] = xlutils.getCellData(sheetName, i, j);    // 1, 0
			}
		}
		
		return loginData; //returning two dimensional array
	}
}
