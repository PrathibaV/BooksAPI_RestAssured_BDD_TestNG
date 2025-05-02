package com.books.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.NumberToTextConverter;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

	  public List<Map<String, String>> readData(String sheetName) {
	        List<Map<String, String>> dataList = new ArrayList();
	        String path = System.getProperty("user.dir") + "/src/test/resources/BooksAPIs_TestData.xlsx";
	        FileInputStream fileInputStream;
			try {
				fileInputStream = new FileInputStream(path);
			
	        Workbook workbook;
			try {
				workbook = new XSSFWorkbook(fileInputStream);
			
	        Sheet sheet = workbook.getSheet(sheetName);

	        Row headerRow = sheet.getRow(0);
	        int columnCount = headerRow.getPhysicalNumberOfCells();
	        List<String> headers = new ArrayList<>();

	        // Read header row
	        for (int i = 0; i < columnCount; i++) {
	            headers.add(headerRow.getCell(i).getStringCellValue());
	        }

	        // Read data rows
	        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
	            Row row = sheet.getRow(i);
	            Map<String, String> dataMap = new HashMap<>();
	            for (int j = 0; j < columnCount; j++) {
	            	Cell currCellValue = row.getCell(j);
	            	
	            	if (currCellValue != null) {
	                    if (currCellValue.getCellType() == CellType.STRING) {
	                        dataMap.put(headers.get(j), currCellValue.getStringCellValue());
	                    } else if (currCellValue.getCellType() == CellType.NUMERIC) {
	                        dataMap.put(headers.get(j), NumberToTextConverter.toText(currCellValue.getNumericCellValue()));
	                    } else if (currCellValue.getCellType() == CellType.BLANK) {
	                        dataMap.put(headers.get(j), "");
	                    }
	                } else {
	                    dataMap.put(headers.get(j), ""); 
	                }
	            }
	            dataList.add(dataMap);
	        }

	        workbook.close();
	        fileInputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

	        return dataList;
	    }
}
