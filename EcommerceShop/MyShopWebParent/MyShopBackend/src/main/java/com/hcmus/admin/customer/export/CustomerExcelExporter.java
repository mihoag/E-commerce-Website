package com.hcmus.admin.customer.export;

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
import com.hcmus.common.entity.Brand;
import com.hcmus.common.entity.Customer;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;

public class CustomerExcelExporter extends AbstractExporter{
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	public CustomerExcelExporter() {
		workbook = new XSSFWorkbook();
	}
	
	private void writeHeaderLine() {
		sheet = workbook.createSheet("Customers");
		XSSFRow row = sheet.createRow(0);
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setBold(true);
		font.setFontHeight(16);
		cellStyle.setFont(font);
		
		createCell(row, 0, "Customer ID", cellStyle);
		createCell(row, 1, "Fullname", cellStyle);
		createCell(row, 2, "Email", cellStyle);
		createCell(row, 3, "Phone", cellStyle);
		createCell(row, 4, "Address 1", cellStyle);
		createCell(row, 5, "Address 2", cellStyle);
		createCell(row, 6, "City", cellStyle);
		createCell(row, 7, "State", cellStyle);
		createCell(row, 8, "Country", cellStyle);
		createCell(row, 9, "Postal code", cellStyle);
		createCell(row, 10, "Created time", cellStyle);
		createCell(row, 11, "Enabled", cellStyle);
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
	
	public void export(List<Customer> customers, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "application/octet-stream", ".xlsx", "customer_");
	
		writeHeaderLine();
		writeDataLines(customers);
		
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}

	private void writeDataLines(List<Customer> customers) {
		int rowIndex = 1;
		
		XSSFCellStyle cellStyle = workbook.createCellStyle();
		XSSFFont font = workbook.createFont();
		font.setFontHeight(14);
		cellStyle.setFont(font);
		
		for (Customer customer : customers) {
			XSSFRow row = sheet.createRow(rowIndex++);
			int columnIndex = 0;
			
			createCell(row, columnIndex++, customer.getId(), cellStyle);
			createCell(row, columnIndex++, customer.getFullName(), cellStyle);
			createCell(row, columnIndex++, customer.getEmail(), cellStyle);
			createCell(row, columnIndex++, customer.getPhoneNumber(), cellStyle);
			createCell(row, columnIndex++, customer.getAddressLine1(), cellStyle);
			createCell(row, columnIndex++, customer.getAddressLine2(), cellStyle);
			createCell(row, columnIndex++, customer.getCity(), cellStyle);
			createCell(row, columnIndex++, customer.getState(), cellStyle);
			createCell(row, columnIndex++, customer.getCountry().getName(), cellStyle);
			createCell(row, columnIndex++, customer.getPostalCode(), cellStyle);
			createCell(row, columnIndex++, customer.getCreatedTime() != null ? customer.getCreatedTime().toString() : "" , cellStyle);
			createCell(row, columnIndex++, customer.isEnabled(), cellStyle);
		}
	}
}
