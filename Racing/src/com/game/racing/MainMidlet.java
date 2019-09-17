package com.game.racing;

import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;


/**
 * 
 * @author JoYoNB
 *
 */
public class MainMidlet extends MIDlet {
	public Display dis;
	public MainGameCanvas gameCanvas;
	public MenuCanvas menuCanvas;

	public MainMidlet() throws Exception {
		dis = Display.getDisplay(this);
	}

	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub
	}

	protected void pauseApp() {
		// TODO Auto-generated method stub
		// 因为机顶盒一般不存在被中断暂停，比如需要释放摄像头等，所以这个函数为空就行
	}
	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		// 构造函数里的部分东西也可以放在这里，也可以全部放在构造函数，顺序是先执行构造函数
		gameCanvas = new MainGameCanvas(this);
		menuCanvas = new MenuCanvas(this);
		dis.setCurrent(menuCanvas);// 游戏启动首先显示菜单
	}
	public void exit(){
		try {
			System.gc();
			this.destroyApp(true);
			this.notifyDestroyed();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}