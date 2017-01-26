package org.keyword.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class KeyBoardUtil {
	//实现按Tab键的方法
	public static void pressTabKey(){
		Robot robot = null;
		try{
			robot = new Robot();
		}catch(AWTException e){
			e.printStackTrace();
		}
		robot.keyPress(KeyEvent.VK_TAB);
		robot.keyRelease(KeyEvent.VK_TAB);
	}
	//实现按回车键的方法
	public static void pressEnterKey(){
		Robot robot= null;
		try{
			robot = new Robot();
		}catch(AWTException e){
			e.printStackTrace();
		}
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
    /**将指定字符创设为剪切板内容，然后执行粘贴操作
	      将页面焦点切换到输入框后，调用此函数可以将指定字符创粘贴到输入框中
	*/
	public static void setAndctrlVClipboardData(String string){
		StringSelection stringSelection = new StringSelection(string);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
		Robot robot = null;
		try{
			robot =new Robot();
		}catch(AWTException e){
			e.printStackTrace();
		}
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
	}
}
