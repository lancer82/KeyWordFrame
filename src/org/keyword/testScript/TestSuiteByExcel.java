package org.keyword.testScript;

import org.testng.annotations.Test;

import java.lang.reflect.Method;

import org.apache.log4j.xml.DOMConfigurator;
import org.junit.Assert;
import org.keyword.configuration.Constants;
import org.keyword.configuration.KeyWordsAction;
import org.keyword.util.ExcelUtil;
import org.keyword.util.Log;
import org.testng.annotations.BeforeClass;

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
		  System.out.println("获取的testCaseID为："+testCaseID);
		  testCaseRunFlag = ExcelUtil.getCellData(Constants.Sheet_TestSuite, testCaseNo,
				  Constants.Col_RunFlag);
		  System.out.println("获取的testCaseRunFlag为："+testCaseRunFlag );
		  if(testCaseRunFlag .equalsIgnoreCase("y")){
			  Log.startTestcase(testCaseID);
			  testResult = true;			 
			  testStep = ExcelUtil.getFirstRowContainsTestCaseID(Constants.Sheet_TestSteps,
					  testCaseID, Constants.Col_TestCaseID);		  
			  System.out.println("获取的testStep为："+testStep);
			  testLastStep = ExcelUtil.getTestCaseLastStepRow(Constants.Sheet_TestSteps, 
					  testCaseID, testStep);
			  System.out.println("获取的testLastStep为："+testLastStep);
			  for(; testStep<testLastStep; testStep++){
				  keyword = ExcelUtil.getCellData(Constants.Sheet_TestSteps, testStep, 
						  Constants.Col_KeyWordAction);
				  System.out.println("从Excel文件中读取到的关键字是："+keyword);
				  Log.info("从Excel文件中读取到的关键字是："+keyword);
				  locatorExpression = ExcelUtil.getCellData(Constants.Sheet_TestSteps, testStep, 
						  Constants.Col_LocatorExpression);
				  System.out.println("从Excel文件中表达式是："+locatorExpression );
				  value = ExcelUtil.getCellData(Constants.Sheet_TestSteps, testStep, 
						  Constants.Col_ActionValue);
				  Log.info("从Excel文件中读取的操作值是："+value);
				  System.out.println("从Excel文件中读取的操作值是："+value );
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
					 KeyWordsAction.close_browser("", "");
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
