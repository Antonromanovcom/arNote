package com.antonromanov.arnote.utils;

import au.com.bytecode.opencsv.CSVReader;
import com.antonromanov.arnote.model.Wish;
import com.antonromanov.arnote.repositoty.WishRepository;
import com.antonromanov.arnote.service.MainService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class CsvDispatcher {


	/**
	 * Инжектим сервис.
	 */
	@Autowired
	WishRepository wishRepository;

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


	public  void doit2(String text) throws Exception
	{
		CSVReader reader = new CSVReader(new FileReader("C:\\opt\\02.csv"), ',', '"', 1);

		List<String[]> allRows = reader.readAll();
		Pattern pattern = Pattern.compile("^\\d{1,3}\\,");
		Pattern pattern2 = Pattern.compile("^(?:\\d{1,3}\\,).*?(.*)$");
		/*
		List<String> newList = allRows.stream()
				.map(strings -> String.join(",", strings))
				.collect(Collectors.toList())
				.stream().filter(pattern.asPredicate()).collect(Collectors.toList());

		List<String> newList2 = newList.stream().map(f->{
			Matcher matcher = pattern2.matcher(f);
			return matcher.group(1);
		}).collect(Collectors.toList());

		newList2.stream().map(f->{

			Pattern p = Pattern.compile("(.*)(?=\\,,\\d)");
			Pattern p2 = Pattern.compile("(?:,,)(\\d.*)(р.)");
			Matcher m = p.matcher(f);
			Matcher m2 = p2.matcher(f);

			if (m.find()) {
				System.out.println("Matched m1: " + m.group(1));
			} else {
				System.out.println("No match.");
			}

			System.out.println("=========================================");

			if (m2.find()) {
				System.out.println("Matched m2: " + m2.group(1));
			} else {
				System.out.println("No match.");
			}

			return "";
		}).collect(Collectors.toList());*/

		List<String> resultList = allRows.stream()
				.map(strings -> String.join(",", strings))
				.filter(pattern.asPredicate())
				.map(f->{
					System.out.println("-------- >" + f);
					//Matcher matcher = pattern2.matcher(f);
//					return matcher.group(1);
					return f;

				})
				.map(f->{

					Pattern p = Pattern.compile("^\\d{1,3},(.*)(?=\\,,\\d)");
					Pattern p2 = Pattern.compile("(?:,,)(\\d.*)(р.)");
					Matcher m = p.matcher(f);
					Matcher m2 = p2.matcher(f);
					String localWish = "";

					if (m.find()) {
						localWish = m.group(1);
						System.out.println("Matched m1: " + localWish);
					} else {
						System.out.println("No match.");
					}

					System.out.println("=========================================");

					if (m2.find()) {
						System.out.println("Matched m2: " + m2.group(1));
					} else {
						System.out.println("No match.");
					}
//					 public Wish(String wish, Integer price, Integer priority, Boolean ac, String description, String url) {

					List<Wish> wishes = wishRepository.getWishesByName(localWish).orElseGet(() -> new ArrayList<>());

					if (wishes.size() > 1) {
						// удаляем и ничего не делаем больше
					} else if (wishes.size() < 1) {
						//нету? добавляем
						wishRepository.save(new Wish("11", 111, 1, false, "from csv", ""));
					}


					return "";
				})
				.collect(Collectors.toList())
				.stream().filter(pattern.asPredicate()).collect(Collectors.toList());
	}
}
