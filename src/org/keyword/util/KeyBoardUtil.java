package org.keyword.util;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;

public class KeyBoardUtil {
	//ʵ�ְ�Tab���ķ���
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
