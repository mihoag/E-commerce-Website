package com.hcmus.admin.category.export;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.hcmus.admin.AbstractExporter;
import com.hcmus.common.entity.Category;
import com.hcmus.common.entity.User;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class CategoryExcelExporter extends AbstractExporter {
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	public CategoryExcelExporter() {
		workbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLine() {
		sheet = workbook.createSheet("Categories");
		XSSFRow row = sheet.createRow(0);
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		cellStyle.setFont(font);
		
		createCell(row, 0, "Category Id", cellStyle);
		createCell(row, 1, "Category Name", cellStyle);
		createCell(row, 2, "Category Alias", cellStyle);
	}
	
	private void createCell(XSSFRow row, int columnIndex, Object value, CellStyle style) {
		XSSFCell cell = row.createCell(columnIndex);
		sheet.autoSizeColumn(columnIndex);
		
		if (value instanceof Integer) {
			cell.setCellValue((Integer) value);
		} else if (value instanceof Boolean) {
			cell.setCellValue((Boolean) value);
		} else {
			cell.setCellValue((String) value);
		}
		
		cell.setCellStyle(style);		
	}
	
	public void export(List<Category> categories, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "application/octet-stream", ".xlsx", "categories_");
	
		writeHeaderLine();
		writeDataLines(categories);
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}

	private void writeDataLines(List<Category> categories) {
		int rowIndex = 1;
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		cellStyle.setFont(font);
		
		for (Category cate : categories) {
			XSSFRow row = sheet.createRow(rowIndex++);
			int columnIndex = 0;
			cate.setName(cate.getName().replace("--", ""));
			createCell(row, columnIndex++, cate.getId(), cellStyle);
			createCell(row, columnIndex++, cate.getName(), cellStyle);
			createCell(row, columnIndex++, cate.getAlias(), cellStyle);
		}
	}
}
