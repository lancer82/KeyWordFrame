package org.keyword.testScript;

import org.testng.annotations.Test;

import java.lang.reflect.Method;

import org.apache.log4j.xml.DOMConfigurator;
import org.testng.Assert;
import org.keyword.configuration.Constants;
import org.keyword.configuration.KeyWordsAction;
import org.keyword.util.ExcelUtil;
import org.keyword.util.Log;
import org.testng.annotations.*;

public class TestSuiteByExcel {
	public static Method method[];
	public static String keyword;
	public static String locatorExpression;
	public static String value;
	public static KeyWordsAction keyWordsaction ; 
	public static int testStep;
	public static int testLastStep;
	public static String testCaseID;
	public static String testCaseRunFlag;
	public static boolean testResult;	
  @Test
  public void testTestSuite()throws Exception {
	  keyWordsaction = new KeyWordsAction();	
	  method = keyWordsaction.getClass().getMethods();
	  String excelFilePath =Constants.Path_ExcelFile;
	  ExcelUtil.setExcelFile(excelFilePath,Constants.Sheet_TestSteps);
	  int testCaseCount = ExcelUtil.getRowCount(Constants.Sheet_TestSuite);
	  for(int testCaseNo =1;testCaseNo<=testCaseCount;testCaseNo++){
		  testCaseID = ExcelUtil.getCellData(Constants.Sheet_TestSuite,  testCaseNo,
				  Constants.Col_TestCaseID);
//		  System.out.println("��ȡ��testCaseIDΪ��"+testCaseID);
		  testCaseRunFlag = ExcelUtil.getCellData(Constants.Sheet_TestSuite, testCaseNo,
				  Constants.Col_RunFlag);
//		  System.out.println("��ȡ��testCaseRunFlagΪ��"+testCaseRunFlag );
		  if(testCaseRunFlag .equalsIgnoreCase("y")){
			  Log.startTestcase(testCaseID);
			  testResult = true;			 
			  testStep = ExcelUtil.getFirstRowContainsTestCaseID(Constants.Sheet_TestSteps,
					  testCaseID, Constants.Col_TestCaseID);		  
//			  System.out.println("��ȡ��testStepΪ��"+testStep);
			  testLastStep = ExcelUtil.getTestCaseLastStepRow(Constants.Sheet_TestSteps, 
					  testCaseID, testStep);
//			  System.out.println("��ȡ��testLastStepΪ��"+testLastStep);
			  for(; testStep<testLastStep; testStep++){
				  keyword = ExcelUtil.getCellData(Constants.Sheet_TestSteps, testStep, 
						  Constants.Col_KeyWordAction);
//				  System.out.println("��Excel�ļ��ж�ȡ���Ĺؼ����ǣ�"+keyword);
//				  Log.info("��Excel�ļ��ж�ȡ���Ĺؼ����ǣ�"+keyword);
				  locatorExpression = ExcelUtil.getCellData(Constants.Sheet_TestSteps, testStep, 
						  Constants.Col_LocatorExpression);
//				  System.out.println("��Excel�ļ��б��ʽ�ǣ�"+locatorExpression );
				  value = ExcelUtil.getCellData(Constants.Sheet_TestSteps, testStep, 
						  Constants.Col_ActionValue);
//				  Log.info("��Excel�ļ��ж�ȡ�Ĳ���ֵ�ǣ�"+value);
//				  System.out.println("��Excel�ļ��ж�ȡ�Ĳ���ֵ�ǣ�"+value );
				  execute_Action();
				 if(testResult == false){
					  ExcelUtil.setCellData("测试用例集合", testCaseNo, Constants.Col_TestSuiteTestResult, "测试执行失败");
					  Log.endTestcase(testCaseID);
					  break;
				 }

				 if(testResult ==true){
					 ExcelUtil.setCellData("测试用例集合", testCaseNo, Constants.Col_TestSuiteTestResult,"测试执行成功");
				 }
			  }
		  }
	  }
  }
 private static void execute_Action(){
	 try{
		 for(int i = 0;i<method.length;i++){
			 if(method[i].getName().equals(keyword)){
				method[i].invoke(keyWordsaction,locatorExpression,value);
				 if(testResult ==true){
					 ExcelUtil.setCellData(Constants.Sheet_TestSteps, testStep, Constants.Col_TestStepTestResult,
							"测试步骤执行成功");
					 break;
				 }else{
					 ExcelUtil.setCellData(Constants.Sheet_TestSteps, testStep, Constants.Col_TestStepTestResult,
							"测试步骤执行失败");
					 //KeyWordsAction.close_browser("", "");
					 break;
				 }
			 }
		 }
	 }catch(Exception e){
		 Assert.fail("测试执行出现异常，测试用例执行失败");
	 }
 }
  @BeforeClass
  public void beforeClass() {
	  DOMConfigurator.configure("log4j.xml");
  }
}
