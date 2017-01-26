package org.keyword.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.keyword.configuration.Constants;
import org.keyword.testScript.TestSuiteByExcel;

public class ExcelUtil {
	private static XSSFSheet ExcelWSheet;
	private static XSSFWorkbook ExcelWBook;
	private static XSSFCell Cell;
	private static XSSFRow Row;
	//设定要操作的Excel路径
	/**在读/写Excel的时候，首先要设定要操作的Excel文件路径*/
	public static void setExcelFile(String path){
		FileInputStream ExcelFile;
		try{
			//实例化Excel的FileInputStream对象；
			ExcelFile = new FileInputStream(path);
			//实例化Excel的XSSFWorkbook对象
			ExcelWBook = new XSSFWorkbook(ExcelFile);
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			System.out.println("Excel文件路径设定失败");
			e.printStackTrace();
		}
	}
	//设定要操作的Excel文件路径和Excel的Sheet名称
	/**在读/写Excel的时候，首先要设定要操作的Excel文件路径和要操作的Sheet名称*/
	public static void setExcelFile(String path,String SheetName){
		FileInputStream ExcelFile;
		try{
			ExcelFile =new FileInputStream(path);
			ExcelWBook =new XSSFWorkbook( ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			System.out.println("Excel路径设定失败");
			e.printStackTrace();
		}
	}
	
	//读取Excel文件指定单元格函数，此函数只支持文件扩展名为,xlsx的Excel文件
	public static String getCellData(String SheetName, int RowNum,int ColNum){
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		try{
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			String CellData = Cell.getCellType()==XSSFCell.CELL_TYPE_STRING ?Cell.getStringCellValue()
					+"":String.valueOf(Math.round(Cell.getNumericCellValue()));
			return CellData;
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			e.printStackTrace();
			//读取遇到异常则返回空字符串
			return "";
		}
	}
	
	//获取Sheet中最后一行的行号
	public static int getLastRowNum(){
		return ExcelWSheet.getLastRowNum();
	}
	
	//获取指定Sheet中数据的总行数
	public static int getRowCount(String SheetName){
		ExcelWSheet =ExcelWBook.getSheet(SheetName);
		int number = ExcelWSheet.getLastRowNum();
		return number;
	}
	
	//在Excel的指定Sheet中，获取第一次包含指定测试用例序号文字的行号。
	public static int getFirstRowContainsTestCaseID(String sheetName,String testCaseName,int colNum){		
		int i ;
		try{
			ExcelWSheet = ExcelWBook.getSheet(sheetName);
			int RowCount = ExcelUtil.getRowCount(sheetName);
			for(i=0;i<RowCount;i++){
				if(ExcelUtil.getCellData(sheetName, i, colNum).equalsIgnoreCase(testCaseName)){
					break;
				}
			}
			return i;
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			return 0;
		}
	}
	//获取指定Sheet中某个测试用例步骤的个数 
	public static int getTestCaseLastStepRow(String sheetName,String testCaseID,int testCaseStartRowNum){
		try{			
			ExcelWSheet = ExcelWBook.getSheet(sheetName);
			for(int i = testCaseStartRowNum;i<ExcelUtil.getRowCount(sheetName);i++){				
				if(!testCaseID.equals(ExcelUtil.getCellData(sheetName, i, Constants.Col_TestCaseID))){
					int number = i ;
					return number;	
				}
			}
			int number =ExcelWSheet.getLastRowNum()+1;		
			return number;			
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			return 0;
		}
	}
	//在Excel文件中设置单元格中写入数据，此函数只支持文件扩展名为.xlsx的Excel的文件写入
	public static void setCellData(String sheetName,int rowNum,int colNum,String result){
		ExcelWSheet = ExcelWBook.getSheet(sheetName);
		try{
			//获取Excel文件中的行对象
			Row = ExcelWSheet.getRow(rowNum);
			//获取行对象中的单元格对象，如果单元格为空，则返回Null
			Cell = Row.getCell(colNum,Row.RETURN_BLANK_AS_NULL);
			if(Cell==null){
				Cell=Row.createCell(colNum);
				Cell.setCellValue(result);
			}else{
				Cell.setCellValue(result);
			}	
			FileOutputStream fileOut= new FileOutputStream(Constants.Path_ExcelFile);
			ExcelWBook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			e.printStackTrace();
		}
	}	
}