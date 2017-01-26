package org.keyword.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.By;

public class ObjectMap {
	Properties properties = null;
	public ObjectMap(String propfile){
		properties = new Properties();
		try{
			FileInputStream in =new FileInputStream(propfile);
			properties.load(in);
			in.close();
		}catch(IOException e){
			System.out.println("读取配置文件异常");
			e.printStackTrace();
		}
	}
	public By getLocator(String ElementNameInPropfile) throws Exception{
	   //从属性配置文件中获取对应的配置对象 
		String locator = properties.getProperty(ElementNameInPropfile);
		//分别用locatorType变量存储定位类型，用locator存储定位表达式
		String locatorType = locator.split(">")[0];
		String locatorValue = locator.split(">")[1];
		locatorValue= new String(locatorValue.getBytes("ISO-8859-1"),"UTF-8");
		System.out.println("获取的定位类型是"+locatorType+"\t获取的定位表达式为"+locatorValue);
		//用locatorType判断返回的各种定位方式的by对象
		if(locatorType.toLowerCase().equals("id")){
			return By.id(locatorValue);
		}else if(locatorType.toLowerCase().equals("name")){
			return By.name(locatorValue);
		}else if(locatorType.toLowerCase().equals("classname") 
				||(locatorType.toLowerCase().equals("class"))){
			return By.className(locatorValue);
		}else if((locatorType.toLowerCase().equals("tagname"))
				|| (locatorType.toLowerCase().equals("tag"))){
			return By.tagName(locatorValue);
		}else if((locatorType.toLowerCase().equals("linktext"))
				|| (locatorType.toLowerCase().equals("link"))){
			return By.linkText(locatorValue);
		}else if(locatorType.toLowerCase().equals("partiallinktext")){
			return By.partialLinkText(locatorValue);
		}else if((locatorType.toLowerCase().equals("cssselector"))
				|| (locatorType.toLowerCase().equals("css"))){
			return By.cssSelector(locatorValue);
		}else if(locatorType.toLowerCase().equals("xpath")){
			return By.xpath(locatorValue);
		}else{
			throw new Exception("输入的locatorType未在程序中定义："+locatorType);
		}
	}
}
