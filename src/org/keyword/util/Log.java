package org.keyword.util;

import org.apache.log4j.Logger;

public class Log {
	private static Logger log = Logger.getLogger(Log.class.getName());
	//定义测试用例开始执行打印信息，在日志中打印测试用例开始执行的信息 
	public static void startTestcase(String testCaseName){
		log.info("——————————————         \""+testCaseName+ "\"开始执行            ——————————————————");		
	}
	//定义测试用例执行结束打印信息，在日志中打印测试用例结束执行的信息 
	public static void endTestcase(String testCaseName){
		log.info("——————————————         \""+testCaseName+ "\"测试结束            ——————————————————");		
	}
	//定义打印info界别的日志
	public static void info(String message){
		log.info(message);
	}
	//定义打印error级别的日志
	public static void error(String message){
		log.info(message);
	}
	public static void debug(String message){
		log.info(message);
	}
	
}
