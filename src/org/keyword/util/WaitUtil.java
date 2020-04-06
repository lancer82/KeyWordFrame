package org.keyword.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitUtil {

	public static void sleep(long millisecond){
		try{
			Thread.sleep(millisecond);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static void waitWebElement(WebDriver driver,String xpathExpression){
		WebDriverWait  wait = new WebDriverWait(driver,10);

		wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathExpression)));
	}

	public static void waitWebElement(WebDriver driver,By by){
		WebDriverWait wait = new WebDriverWait(driver,10);
		wait.until(ExpectedConditions.presenceOfElementLocated( by));
	}
}
