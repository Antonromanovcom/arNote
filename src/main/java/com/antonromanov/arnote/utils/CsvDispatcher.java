package com.antonromanov.arnote.utils;


import au.com.bytecode.opencsv.CSVReader;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class CsvDispatcher {

	public String  doIt (String fileName) throws IOException {

		//инициализируем потоки
		String result = "";
		InputStream inputStream = null;
		XSSFWorkbook workBook = null;
		try {
			inputStream = new FileInputStream(fileName);
			workBook = new XSSFWorkbook(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//разбираем первый лист входного файла на объектную модель
		Sheet sheet = workBook.getSheetAt(0);
		Iterator<Row> it = sheet.iterator();
		//проходим по всему листу
		while (it.hasNext()) {
			Row row = it.next();
			Iterator<Cell> cells = row.iterator();
			while (cells.hasNext()) {
				Cell cell = cells.next();
				int cellType = cell.getCellType();
				//перебираем возможные типы ячеек
				switch (cellType) {
					case Cell.CELL_TYPE_STRING:
						result += cell.getStringCellValue() + "=";
						break;
					case Cell.CELL_TYPE_NUMERIC:
						result += "[" + cell.getNumericCellValue() + "]";
						break;

					case Cell.CELL_TYPE_FORMULA:
						result += "[" + cell.getNumericCellValue() + "]";
						break;
					default:
						result += "|";
						break;
				}
			}
			result += "\n";
		}

		return result;
	}

	@SuppressWarnings("resource")
	public  void doit2() throws Exception
	{
		//Build reader instance
		CSVReader reader = new CSVReader(new FileReader("C:\\opt\\02.csv"), ',', '"', 1);
		//Read all rows at once
		List<String[]> allRows = reader.readAll();
		//Read CSV line by line and use the string array as you want
		for(String[] row : allRows){
			System.out.println(Arrays.toString(row));
		}
	}



}
