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
//			System.out.println("��ȡ�����ļ��쳣");
			e.printStackTrace();
		}
	}
	public By getLocator(String ElementNameInPropfile) throws Exception{
	   //�����������ļ��л�ȡ��Ӧ�����ö��� 
		String locator = properties.getProperty(ElementNameInPropfile);
		//�ֱ���locatorType�����洢��λ���ͣ���locator�洢��λ���ʽ
		String locatorType = locator.split(">")[0];
		String locatorValue = locator.split(">")[1];
		locatorValue= new String(locatorValue.getBytes("ISO-8859-1"),"UTF-8");
//		System.out.println("��ȡ�Ķ�λ������"+locatorType+"\t��ȡ�Ķ�λ���ʽΪ"+locatorValue);
		//��locatorType�жϷ��صĸ��ֶ�λ��ʽ��by����
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
			throw new Exception("输入的locatorType不正确:"+locatorType);
		}
	}
}
