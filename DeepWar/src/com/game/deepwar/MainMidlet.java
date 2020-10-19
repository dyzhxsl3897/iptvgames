/**
 * 
 */
package com.game.deepwar;

import javax.microedition.lcdui.Display;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

import com.game.deepwar.MainGameCanvas;

/**
 * @author Administrator
 *
 */
public class MainMidlet extends MIDlet{

	public Display dis;
	public MainGameCanvas gameCanvas;
	public MenuCanvas menuCanvas;
	
	
	public MainMidlet() {
		dis = Display.getDisplay(this);
		gameCanvas = new MainGameCanvas(this);
		menuCanvas = new MenuCanvas(this);
		dis.setCurrent(menuCanvas);// ��Ϸ����������ʾ�˵�
	}
	
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		
	}

	protected void pauseApp() {
		// TODO Auto-generated method stub
		
	}

	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		
	}
	
	public void exit(){
		try {
			this.destroyApp(true);
			this.notifyDestroyed();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
