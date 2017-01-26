package org.keyword.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtil {
	//用于测试执行过程中暂停程序执行的休眠方法
	public static void sleep(long millisecond){
		try{
			//线程参数，millisecond定义的毫秒数
			Thread.sleep(millisecond);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	//显式等待页面元素出现的封装方法，参数为页面元素xpath定位字符串
	public static void waitWebElement(WebDriver driver,String xpathExpression){
		WebDriverWait  wait = new WebDriverWait(driver,10);
		//调用ExpectedConditions的presenceOfElementLocated方法判断页面元素是否出现。
		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpression)));
	}
	//支撑更多的定位表达式
	public static void waitWebElement(WebDriver driver,By by){
		WebDriverWait wait = new WebDriverWait(driver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated( by));
	}
}
