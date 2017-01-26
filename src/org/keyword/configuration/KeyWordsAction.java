package org.keyword.configuration;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.xml.DOMConfigurator;
import org.keyword.testScript.TestSuiteByExcel;
import org.keyword.util.KeyBoardUtil;
import org.keyword.util.Log;
import org.keyword.util.ObjectMap;
import org.keyword.util.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.os.WindowsUtils;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;

public class KeyWordsAction {
	private static final int APP = 0;
	public static WebDriver driver;
	private static ObjectMap objectMap = new ObjectMap(Constants.Path_ConfigurationFile);
	static{
		DOMConfigurator.configure("log4j.xml");
	}
	//open_browser
	public static void open_browser(String string,String browserName){
		  if(browserName.equalsIgnoreCase("firefox")){
			  driver = new FirefoxDriver();	
			  Log.info("���������Ѿ�����");
		  }else if(browserName.equalsIgnoreCase("ie")){
			  System.setProperty("webdriver.ie.driver","D:\\Drivers\\IEDriverServer.exe");
/*			  DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
			  caps.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS,false); //�Ƿ��ڴ�IEǰ������ػ����ļ�
			  caps.setCapability(InternetExplorerDriver.IE_SWITCHES, "-private");//ʹ�ñ��ض˿�
			  caps.setCapability("ignoreZoomSetting",true);   //����IE��������
		      driver =new InternetExplorerDriver(caps);*/
               driver =new InternetExplorerDriver();
			  Log.info("ie������Ѿ�����");
		  }else{
			  System.setProperty("webdriver.Chrome.driver","D:\\Drivers\\chromedriver.exe");
			  driver=new ChromeDriver();
			  Log.info("Chrome������Ѿ�����");
		  }	
		  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	//navigate
	public static void navigate(String string,String Url){
		driver.get(Url);
		WaitUtil.sleep(5000);
		Log.info("��������ʵ�ַ"+Url);
	}
	//login_input
	public static void login_input(String locatorExpression,String inputString){
		try{		
			driver.switchTo().frame(driver.findElement(By.id("x-URS-iframe")));
			driver.findElement(objectMap.getLocator(locatorExpression)).clear();
			Log.info("���"+locatorExpression+"���������������");
			driver.findElement(objectMap.getLocator(locatorExpression)).sendKeys(inputString);
			Log.info("�����������"+inputString);
			driver.switchTo().defaultContent();
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			Log.info("��"+locatorExpression+"�����������"+inputString+"ʱ�����쳣��"
					+ "�쳣��Ϣ"+e.getMessage());
			e.printStackTrace();
		}
	}
	//input
	public static void input(String locatorExpression,String inputString){
		try{
			driver.findElement(objectMap.getLocator(locatorExpression)).clear();
			Log.info("���"+locatorExpression+"���������������");
			driver.findElement(objectMap.getLocator(locatorExpression)).sendKeys(inputString);
			Log.info("�����������"+inputString);
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			Log.info("��"+locatorExpression+"�����������"+inputString+"ʱ�����쳣��"
					+ "�쳣��Ϣ"+e.getMessage());
			e.printStackTrace();
		}
	}
	//login_click
	public static void login_click(String locatorExpression,String String){
		try{
			driver.switchTo().frame(driver.findElement(By.id("x-URS-iframe")));			
			driver.findElement(objectMap.getLocator(locatorExpression)).click();
			Thread.sleep(3000);
			Log.info("����"+locatorExpression+"ҳ��Ԫ�سɹ�");
			driver.switchTo().defaultContent();
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			Log.info("����"+locatorExpression+"ҳ��Ԫ��ʧ�ܣ������쳣��Ϣ��"+e.getMessage());
			e.printStackTrace();
		}
	}
	//click
	public static void click(String locatorExpression,String String){
		try{
			driver.findElement(objectMap.getLocator(locatorExpression)).click();
			Log.info("����"+locatorExpression+"ҳ��Ԫ�سɹ�");
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			Log.info("����"+locatorExpression+"ҳ��Ԫ��ʧ�ܣ������쳣��Ϣ��"+e.getMessage());
			e.printStackTrace();
		}
	}
	//WaitFor_Element
	public static void WaitFor_Element(String locatorExpression, String string){
		try{
			WaitUtil.waitWebElement(driver,objectMap.getLocator(locatorExpression) );
			Log.info("��ʽ�ȴ�Ԫ�س��ֳɹ���Ԫ����"+locatorExpression);
		}catch(Exception e){
		    TestSuiteByExcel.testResult=false;
		    Log.info("��ʽ�ȴ�Ԫ�س����쳣�������쳣��Ϣ"+e.getMessage());
		    e.printStackTrace();
		}
	}
	//press_Tab
	public static void press_Tab(String string1,String string2){
		try{
			Thread.sleep(3000);
			KeyBoardUtil.pressTabKey();
			Log.info("��Tab���ɹ�");
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			Log.info("��Tab��ʱ�����쳣�������쳣��Ϣ"+e.getMessage());
			e.printStackTrace();
		}
	}
	//pasteString
	public static void pasteString(String string,String pasteContent){		
		try{
			driver.switchTo().frame(driver.findElement(By.className("APP-editor-iframe")));
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("document.getElementsByTagName('body')[0].innerHTML ='<b>"+pasteContent+"<b>'");
			Thread.sleep(3000);
			//KeyBoardUtil.setAndctrlVClipboardData(pasteContent);
			Log.info("�ɹ�ճ���ʼ����ģ�"+pasteContent);
			driver.switchTo().defaultContent();
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			Log.info("�������ճ���ʼ������ǳ����쳣�������쳣��Ϣ��"+e.getMessage());
			e.printStackTrace();
		}
	}
	//pressEnter
	public static void pressEnter(String string1, String string2){
		try{
			Thread.sleep(2000);
			KeyBoardUtil.pressEnterKey();
			Log.info("��Enter���ɹ�");
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			Log.info("��Enter��ʱ�����쳣�������쳣��Ϣ"+e.getMessage());
			e.printStackTrace();
		}
	}
	//sleep
	public static void sleep(String string, String sleepTime){
		try{
			WaitUtil.sleep(Integer.parseInt(sleepTime));
			Log.info("����"+Integer.parseInt(sleepTime)/1000+"��ɹ�");
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			Log.info("�߳����߳����쳣�������쳣��Ϣ"+e.getMessage());
			e.printStackTrace();
		}
	}
	//click_sendMailButton
	public static void  click_sendMailButton(String locatorExpression,String string){
		try{		
			List<WebElement> buttons =  driver.findElements(objectMap.getLocator(locatorExpression));
			buttons.get(0).click();
			Log.info("���������ʼ���ť�ɹ�");
			System.out.println("���Ͱ�ť���ɹ�����");
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			Log.info("�������Ͱ�ť�����쳣�������쳣��Ϣ"+e.getMessage());
			e.printStackTrace();
		}
	}
	//Assert_String
	public static void Assert_String(String string,String assertString){
		try{
			Assert.assertTrue(driver.getPageSource().contains(assertString));
			Log.info("�ɹ����Թؼ��֡�"+assertString+"��");
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			Log.info("���Թؼ��ֳ����쳣�������쳣��Ϣ"+e.getMessage());
			e.printStackTrace();
		}
	}
	public static void close_browser(String string,String browserName){
		try{
			  if(browserName.equalsIgnoreCase("firefox")){
				  WindowsUtils.killByName("geckodriver.exe");	
				  Log.info("���������Ѿ��ر�");
			  }else if(browserName.equalsIgnoreCase("ie")){
				  WindowsUtils.killByName("IEDriverServer.exe");
				  driver.quit();
				  Log.info("ie������Ѿ��ر�");
			  }else{
				  WindowsUtils.killByName("chromedriver.exe");			  
				  Log.info("Chrome������Ѿ��ر�");
			  }	
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			Log.info("�ر�����������쳣�������쳣��Ϣ"+e.getMessage());
			e.printStackTrace();
		}
	}
}
