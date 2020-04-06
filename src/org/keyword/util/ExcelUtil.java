package org.keyword.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row.MissingCellPolicy;
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
	//�趨Ҫ������Excel·��
	/**�ڶ�/дExcel��ʱ������Ҫ�趨Ҫ������Excel�ļ�·��*/
	public static void setExcelFile(String path){
		FileInputStream ExcelFile;
		try{
			//ʵ����Excel��FileInputStream����
			ExcelFile = new FileInputStream(path);
			//ʵ����Excel��XSSFWorkbook����
			ExcelWBook = new XSSFWorkbook(ExcelFile);
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			System.out.println("Excel�ļ�·���趨ʧ��");
			e.printStackTrace();
		}
	}
	//�趨Ҫ������Excel�ļ�·����Excel��Sheet����
	/**�ڶ�/дExcel��ʱ������Ҫ�趨Ҫ������Excel�ļ�·����Ҫ������Sheet����*/
	public static void setExcelFile(String path,String SheetName){
		FileInputStream ExcelFile;
		try{
			ExcelFile =new FileInputStream(path);
			ExcelWBook =new XSSFWorkbook( ExcelFile);
			ExcelWSheet = ExcelWBook.getSheet(SheetName);
			
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			System.out.println("Excel·���趨ʧ��");
			e.printStackTrace();
		}
	}
	
	//��ȡExcel�ļ�ָ����Ԫ�������˺���ֻ֧���ļ���չ��Ϊ,xlsx��Excel�ļ�
	public static String getCellData(String SheetName, int RowNum,int ColNum){
		ExcelWSheet = ExcelWBook.getSheet(SheetName);
		try{
			Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
			String CellData = Cell.getCellType()== CellType.STRING ? Cell.getStringCellValue()
					+"":String.valueOf(Math.round(Cell.getNumericCellValue()));
			return CellData;
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			e.printStackTrace();
			//��ȡ�����쳣�򷵻ؿ��ַ���
			return "";
		}
	}
	
	//��ȡSheet�����һ�е��к�
	public static int getLastRowNum(){
		return ExcelWSheet.getLastRowNum();
	}
	
	//��ȡָ��Sheet�����ݵ�������
	public static int getRowCount(String SheetName){
		ExcelWSheet =ExcelWBook.getSheet(SheetName);
		int number = ExcelWSheet.getLastRowNum();
		return number;
	}
	
	//��Excel��ָ��Sheet�У���ȡ��һ�ΰ���ָ����������������ֵ��кš�
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
	//��ȡָ��Sheet��ĳ��������������ĸ��� 
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
	//��Excel�ļ������õ�Ԫ����д�����ݣ��˺���ֻ֧���ļ���չ��Ϊ.xlsx��Excel���ļ�д��
	public static void setCellData(String sheetName,int rowNum,int colNum,String result){
		ExcelWSheet = ExcelWBook.getSheet(sheetName);
		try{
			//��ȡExcel�ļ��е��ж���
			Row = ExcelWSheet.getRow(rowNum);
			//��ȡ�ж����еĵ�Ԫ����������Ԫ��Ϊ�գ��򷵻�Null
			Cell = Row.getCell(colNum,MissingCellPolicy.RETURN_BLANK_AS_NULL);
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