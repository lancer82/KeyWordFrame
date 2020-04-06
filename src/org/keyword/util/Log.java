package org.keyword.util;

import org.apache.log4j.Logger;

public class Log {
	private static Logger log = Logger.getLogger(Log.class.getName());
	//�������������ʼִ�д�ӡ��Ϣ������־�д�ӡ����������ʼִ�е���Ϣ 
	public static void startTestcase(String testCaseName){
		log.info(testCaseName);		
	}
	//�����������ִ�н�����ӡ��Ϣ������־�д�ӡ������������ִ�е���Ϣ 
	public static void endTestcase(String testCaseName){
		log.info(testCaseName);		
	}
	//�����ӡinfo������־
	public static void info(String message){
		log.info(message);
	}
	//�����ӡerror�������־
	public static void error(String message){
		log.info(message);
	}
	public static void debug(String message){
		log.info(message);
	}
	
}
