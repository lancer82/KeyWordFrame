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
//import org.openqa.selenium.os.WindowsUtils;
import org.testng.Assert;

public class KeyWordsAction {
	
	public static WebDriver driver;
	private static ObjectMap objectMap = new ObjectMap(Constants.Path_ConfigurationFile);
	static{
		DOMConfigurator.configure("log4j.xml");
	}
	//open_browser
	public static void open_browser(String string,String browserName){
		  if(browserName.equalsIgnoreCase("firefox")){
			  driver = new FirefoxDriver();	
			  Log.info("火狐浏览器已经声明");
		  }else if(browserName.equalsIgnoreCase("ie")){
			  System.setProperty("webdriver.ie.driver","D:\\Drivers\\IEDriverServer.exe");
/*			  DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
			  caps.setCapability(InternetExplorerDriver.FORCE_CREATE_PROCESS,false); //是否在打开IE前清除本地缓存文件
			  caps.setCapability(InternetExplorerDriver.IE_SWITCHES, "-private");//使用本地端口
			  caps.setCapability("ignoreZoomSetting",true);   //忽略IE本地设置
		      driver =new InternetExplorerDriver(caps);*/
               driver =new InternetExplorerDriver();
			  Log.info("ie浏览器已经声明");
		  }else{
			  System.setProperty("webdriver.Chrome.driver","D:\\Drivers\\chromedriver.exe");
			  driver=new ChromeDriver();
			  Log.info("Chrome浏览器已经声明");
		  }	
		  driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	//navigate
	public static void navigate(String string,String Url){
		driver.get(Url);
		WaitUtil.sleep(5000);
		Log.info("浏览器访问地址"+Url);
	}
	//login_input
	public static void login_input(String locatorExpression,String inputString){
		try{		
			driver.switchTo().frame(driver.findElement(By.id("x-URS-iframe")));
			driver.findElement(objectMap.getLocator(locatorExpression)).clear();
			Log.info("清除"+locatorExpression+"输入框中所有内容");
			driver.findElement(objectMap.getLocator(locatorExpression)).sendKeys(inputString);
			Log.info("输入框中输入"+inputString);
			driver.switchTo().defaultContent();
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			Log.info("在"+locatorExpression+"输入框中输入"+inputString+"时出现异常。"
					+ "异常信息"+e.getMessage());
			e.printStackTrace();
		}
	}
	//input
	public static void input(String locatorExpression,String inputString){
		try{
			driver.findElement(objectMap.getLocator(locatorExpression)).clear();
			Log.info("清除"+locatorExpression+"输入框中所有内容");
			driver.findElement(objectMap.getLocator(locatorExpression)).sendKeys(inputString);
			Log.info("输入框中输入"+inputString);
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			Log.info("在"+locatorExpression+"输入框中输入"+inputString+"时出现异常。"
					+ "异常信息"+e.getMessage());
			e.printStackTrace();
		}
	}
	//login_click
	public static void login_click(String locatorExpression,String String){
		try{
			driver.switchTo().frame(driver.findElement(By.id("x-URS-iframe")));			
			driver.findElement(objectMap.getLocator(locatorExpression)).click();
			Thread.sleep(3000);
			Log.info("单击"+locatorExpression+"页面元素成功");
			driver.switchTo().defaultContent();
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			Log.info("单击"+locatorExpression+"页面元素失败，具体异常信息："+e.getMessage());
			e.printStackTrace();
		}
	}
	//click
	public static void click(String locatorExpression,String String){
		try{
			driver.findElement(objectMap.getLocator(locatorExpression)).click();
			Log.info("单击"+locatorExpression+"页面元素成功");
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			Log.info("单击"+locatorExpression+"页面元素失败，具体异常信息："+e.getMessage());
			e.printStackTrace();
		}
	}
	//WaitFor_Element
	public static void WaitFor_Element(String locatorExpression, String string){
		try{
			WaitUtil.waitWebElement(driver,objectMap.getLocator(locatorExpression) );
			Log.info("显式等待元素出现成功，元素是"+locatorExpression);
		}catch(Exception e){
		    TestSuiteByExcel.testResult=false;
		    Log.info("显式等待元素出现异常，具体异常信息"+e.getMessage());
		    e.printStackTrace();
		}
	}
	//press_Tab
	public static void press_Tab(String string1,String string2){
		try{
			Thread.sleep(3000);
			KeyBoardUtil.pressTabKey();
			Log.info("按Tab键成功");
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			Log.info("按Tab键时出现异常，具体异常信息"+e.getMessage());
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
			Log.info("成功粘贴邮件正文："+pasteContent);
			driver.switchTo().defaultContent();
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			Log.info("在输入框粘贴邮件正文是出现异常，具体异常信息："+e.getMessage());
			e.printStackTrace();
		}
	}
	//pressEnter
	public static void pressEnter(String string1, String string2){
		try{
			Thread.sleep(2000);
			KeyBoardUtil.pressEnterKey();
			Log.info("按Enter键成功");
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			Log.info("按Enter键时出现异常，具体异常信息"+e.getMessage());
			e.printStackTrace();
		}
	}
	//sleep
	public static void sleep(String string, String sleepTime){
		try{
			WaitUtil.sleep(Integer.parseInt(sleepTime));
			Log.info("休眠"+Integer.parseInt(sleepTime)/1000+"秒成功");
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			Log.info("线程休眠出现异常，具体异常信息"+e.getMessage());
			e.printStackTrace();
		}
	}
	//click_sendMailButton
	public static void  click_sendMailButton(String locatorExpression,String string){
		try{		
			List<WebElement> buttons =  driver.findElements(objectMap.getLocator(locatorExpression));
			buttons.get(0).click();
			Log.info("单击发送邮件按钮成功");
			System.out.println("发送按钮被成功单击");
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			Log.info("单击发送按钮出现异常，具体异常信息"+e.getMessage());
			e.printStackTrace();
		}
	}
	//Assert_String
	public static void Assert_String(String string,String assertString){
		try{
			Assert.assertTrue(driver.getPageSource().contains(assertString));
			Log.info("成功断言关键字“"+assertString+"“");
		}catch(Exception e){
			TestSuiteByExcel.testResult = false;
			Log.info("断言关键字出现异常，具体异常信息"+e.getMessage());
			e.printStackTrace();
		}
	}
//	public static void close_browser(String string,String browserName){
//		try{
//			  if(browserName.equalsIgnoreCase("firefox")){
//				  WindowsUtils.killByName("geckodriver.exe");	
//				  Log.info("火狐浏览器已经关闭");
//			  }else if(browserName.equalsIgnoreCase("ie")){
//				  WindowsUtils.killByName("IEDriverServer.exe");
//				  driver.quit();
//				  Log.info("ie浏览器已经关闭");
//			  }else{
//				  WindowsUtils.killByName("chromedriver.exe");			  
//				  Log.info("Chrome浏览器已经关闭");
//			  }	
//		}catch(Exception e){
//			TestSuiteByExcel.testResult = false;
//			Log.info("关闭浏览器出现异常，具体异常信息"+e.getMessage());
//			e.printStackTrace();
//		}
//	}
}
