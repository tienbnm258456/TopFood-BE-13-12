package com.demo.shop.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.demo.shop.entity.Category;
import com.demo.shop.entity.Product;
import com.demo.shop.entity.Supplier;
import com.demo.shop.repository.CategoryRepository;
import com.demo.shop.repository.SupplierRepository;

@Service
@Component
public class ExcelHelper {
	
	@Autowired
	private CategoryRepository categoryRepository;
	@Autowired
	private SupplierRepository supplierRepository;

	public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	static String[] HEADERs = { "Id", "Name", "CategoryId", "SupplierId", "Price", "Description", "PriceSale", "Image",
			"Status", "Quantity", "CreateDate", "UpdateDate" };
	static String SHEET = "Products";

	public static boolean hasExcelFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static ByteArrayInputStream tutorialsToExcel(List<Product> products) {

		try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
			Sheet sheet = workbook.createSheet(SHEET);

			// Header
			Row headerRow = sheet.createRow(0);

			for (int col = 0; col < HEADERs.length; col++) {
				Cell cell = headerRow.createCell(col);
				cell.setCellValue(HEADERs[col]);
			}

			int rowIdx = 1;
			for (Product product : products) {
				Row row = sheet.createRow(rowIdx++);

				row.createCell(0).setCellValue(product.getId());
				row.createCell(1).setCellValue(product.getProductName());
				row.createCell(2).setCellValue(product.getCategoryId());
				row.createCell(3).setCellValue(product.getSupplierId());
				row.createCell(4).setCellValue(product.getPrice());
				row.createCell(5).setCellValue(product.getDescription());
				row.createCell(6).setCellValue(product.getPriceSale());
				row.createCell(7).setCellValue(product.getImage());
				row.createCell(8).setCellValue(product.getStatus());
				row.createCell(9).setCellValue(product.getCreatedDate());
				row.createCell(10).setCellValue(product.getUpdatedDate());
				row.createCell(11).setCellValue(product.getQuantity());
			}

			workbook.write(out);
			return new ByteArrayInputStream(out.toByteArray());
		} catch (IOException e) {
			throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
		}
	}

	public List<Product> excelToTutorials(InputStream is) {
		try {
			Workbook workbook = new XSSFWorkbook(is);

			Sheet sheet = workbook.getSheet(SHEET);
			Iterator<Row> rows = sheet.iterator();

			List<Product> tutorials = new ArrayList<Product>();

			int rowNumber = 0;
			while (rows.hasNext()) {
				Row currentRow = rows.next();

				// skip header
				if (rowNumber == 0) {
					rowNumber++;
					continue;
				}

				Iterator<Cell> cellsInRow = currentRow.iterator();

				Product product = new Product();

				int cellIdx = 0;
				while (cellsInRow.hasNext()) {
					Cell currentCell = cellsInRow.next();

					switch (cellIdx) {
					case 0:
						product.setId((int) currentCell.getNumericCellValue());
						break;

					case 1:
						product.setProductName(currentCell.getStringCellValue());
						break;

					case 2:
						Category category = categoryRepository.findById((int) currentCell.getNumericCellValue()).get();
						product.setCategory(category);
						break;
					case 3:
						Supplier supplier = supplierRepository.findById((int) currentCell.getNumericCellValue()).get();
						product.setSupplier(supplier);
						break;

					case 4:
						product.setPrice((int) currentCell.getNumericCellValue());
						break;
						
					case 5:
						product.setDescription(currentCell.getStringCellValue());
						break;
						
					case 6:
						product.setPriceSale((int) currentCell.getNumericCellValue());
						break;
						
					case 7:
						product.setImage(currentCell.getStringCellValue());
						break;
						
					case 8:
						product.setStatus((int) currentCell.getNumericCellValue());
						break;
//					case 8:
//						product.setCreatedDate(currentCell.getStringCellValue());
//						break;

					case 9:
						product.setQuantity((int) currentCell.getNumericCellValue());
						break;
					default:
						break;
					}

					cellIdx++;
				}

				tutorials.add(product);
			}

			workbook.close();

			return tutorials;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse Excel file: " + e.getMessage());
		} catch (Exception e) {
			 e.printStackTrace();
			 return null;
		}
	}
}
