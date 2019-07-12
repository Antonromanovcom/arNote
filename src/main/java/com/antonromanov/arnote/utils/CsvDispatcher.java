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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CsvDispatcher {

	public CsvDispatcher() {
	}

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
	public  void doit2(String text) throws Exception
	{
		//Build reader instance
		CSVReader reader = new CSVReader(new FileReader("C:\\opt\\02.csv"), ',', '"', 1);
		//Read all rows at once
		List<String[]> allRows = reader.readAll();
		//Read CSV line by line and use the string array as you want
		for(String[] row : allRows){
	//		System.out.println(Arrays.toString(row));
		}

		Pattern pattern = Pattern.compile("^\\d{1,3}\\,");
		Pattern pattern2 = Pattern.compile("^(?:\\d{1,3}\\,).*?(.*)$");
//		Pattern pattern3 = Pattern.compile("(.*)\\,,(\\d{1,3})");
//		Pattern pattern3 = Pattern.compile(".*\\,,");
		Pattern pattern3 = Pattern.compile("(.*)(?=\\,,\\d)");



		/*allRows.stream()
				.map(strings -> String.join(",", strings))
				.collect(Collectors.toList())
				.stream().filter(pattern.asPredicate()).map(f ->{
					Matcher matcher = pattern2.matcher(f);
					return matcher.group(1);
				}).forEach(System.out::println);*/


		List<String> newList = allRows.stream()
				.map(strings -> String.join(",", strings))
				.collect(Collectors.toList())
				.stream().filter(pattern.asPredicate()).collect(Collectors.toList());

		List<String> newList2 = newList.stream().map(f->{
//			System.out.println(f);
			Matcher matcher = pattern2.matcher(f);
		//	System.out.println(matcher.matches());
			if (matcher.matches()) {
			//	System.out.println(matcher.group(1));
			}
			return matcher.group(1);
		}).collect(Collectors.toList());

		boolean match = "Ружье 1 (гладкоствольное)(beretta a400xtreme),,140,000 р.,,,".matches(".*1");
		System.out.println(match);


		String res = "Ружье 1 (гладкоствольное)(beretta a400xtreme),,140,000 р.,,,";
//		Pattern p = Pattern.compile("Ружье(.*)");
		System.out.println(text);
	//	Pattern p = Pattern.compile(text);
	//	Matcher m = p.matcher(res);
		/*if (m.find()) {
			System.out.println("Matched: " + m.group(1));
		} else {
			System.out.println("No match.");
		}
*/

		newList2.stream().map(f->{
			System.out.println(f);
			Matcher matcher2 = pattern3.matcher(f);
			//System.out.println(matcher2.matches());
		//	boolean match = "Ружье 1 (гладкоствольное)(beretta a400xtreme),,140,000 р.,,,".matches("1");
		//	System.out.println(match);
			/*if (matcher2.matches()) {
				System.out.println(matcher2.group(0));
				System.out.println(matcher2.group(1));

				return matcher2.group(0);
			} else {
				return "";
			}*/

//			Pattern p = Pattern.compile(text);
			Pattern p = Pattern.compile("(.*)(?=\\,,\\d)");
			Matcher m = p.matcher(f);


			if (m.find()) {
				System.out.println("Matched: " + m.group(1));
			} else {
				System.out.println("No match.");
			}
			return "";


		}).collect(Collectors.toList());

	}



}
