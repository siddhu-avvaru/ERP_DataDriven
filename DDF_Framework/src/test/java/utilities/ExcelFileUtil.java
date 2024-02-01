package utilities;


import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelFileUtil {
	Workbook wb;
	public ExcelFileUtil(String Excelpath) throws Throwable
	{
		FileInputStream fi = new FileInputStream(Excelpath);
		wb = WorkbookFactory.create(fi);
	}
	public int rowCount(String sheetname)
	{
		return wb.getSheet(sheetname).getLastRowNum();
	}
	public String getCellData(String sheetname, int row, int column)
	{
		String data ="";
		if(wb.getSheet(sheetname).getRow(row).getCell(column).getCellType()==CellType.NUMERIC)
		{
			int celldata = (int) wb.getSheet(sheetname).getRow(row).getCell(column).getNumericCellValue();
			data = String.valueOf(celldata);
		}
		else
		{
		data = wb.getSheet(sheetname).getRow(row).getCell(column).getStringCellValue();	
		}
		return data;
	}
	public void setCellData(String sheetname, int row, int column, String status,String WriteExcel) throws Throwable 
	{
		Sheet ws = wb.getSheet(sheetname);
		Row rowNum = ws.getRow(row);
		Cell cell = rowNum.createCell(column);
		cell.setCellValue(status);
		if(status.equalsIgnoreCase("Pass"))
		{
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.GREEN.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(column).setCellStyle(style);
		}
		else if (status.equalsIgnoreCase("Fail"))
		{
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.RED.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(column).setCellStyle(style);
		}
		else if (status.equalsIgnoreCase("Blocked"))
		{
			CellStyle style = wb.createCellStyle();
			Font font = wb.createFont();
			font.setColor(IndexedColors.BLUE_GREY.getIndex());
			font.setBold(true);
			style.setFont(font);
			ws.getRow(row).getCell(column).setCellStyle(style);
		} 
		FileOutputStream fo = new FileOutputStream(WriteExcel);
		wb.write(fo);	
	}
	public static void main(String[] args) throws Throwable {
		ExcelFileUtil xl = new ExcelFileUtil("D:\\New folder\\SDLC & STLC\\Manual Testing & Selenium/Sample testing file.xlsx");
		int rc = xl.rowCount("EMP");
		System.out.println(rc);
		for (int i=1; i<=rc; i++)
		{
			String fname = xl.getCellData("EMP", i, 0);
			String mname = xl.getCellData("EMP", i, 1);
			String lname = xl.getCellData("EMP", i, 2);
			String e_id = xl.getCellData("EMP", i, 3);
			System.out.println(fname+"     "+mname+"     "+lname+"      "+e_id);
			xl.setCellData("EMP", i, 4, "Pass", "D:\\New folder\\SDLC & STLC\\Manual Testing & Selenium/result.xlsx");
		}
	}
}