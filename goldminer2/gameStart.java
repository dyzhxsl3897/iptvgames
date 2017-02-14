import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class gameStart extends MIDlet implements CommandListener {
	public Alert logo; //logo屏幕

	public Form about; //关于画面

	public Form help; //帮助画面

	public List menu; //菜单画面

	public gameScreen mc;//游戏画面

	public Display display;//屏幕控制对象

	public Command c1, c2; //确定、返回命令

	public Command cback; //返回游戏命令

	public static gameStart midlet; //全局实例对象

	public gameStart() {
		midlet = this;
		display = Display.getDisplay(this);
		c1 = new Command("确定", Command.BACK, 1);
		c2 = new Command("返回", Command.SCREEN, 1);
		logo = new Alert("");
		logo.setTimeout(5000);
		Image im = loadImage("/logo.png");
		logo.setImage(im);
		logo.setType(AlertType.INFO);
		String aboutInfo = "深圳市XX科技有限公司" + "\n客服电话：010-65280XXX"
				+ "\n客服信箱：service@XXX.com.cn" + "\n内容提供" + "\n北京XXXX科技发展有限公司";
		about = new Form("关于我们");
		about.append(aboutInfo);
		about.addCommand(c2);
		about.setCommandListener(this);

		help = new Form("游戏帮助");
		String helpinfo = "";

		help.append(helpinfo);
		help.addCommand(c2);
		help.setCommandListener(this);
		

		menu = new List("挖金矿", List.IMPLICIT);
		menu.addCommand(c1);

		menu.append("新游戏", null);
		menu.append("游戏帮助", null);
		menu.append("关于", null);
		menu.append("退出", null);
		menu.setCommandListener(this);
	}

	public static Image loadImage(String fname) {
		Image im = null;
		try {
			im = Image.createImage(fname);
		} catch (Exception e) {
			System.out.println("pic err fname=" + fname);
		}
		return im;
	}

	public void startApp() {
		display.setCurrent(logo, menu);
	}

	public void pauseApp() {
	}

	public void destroyApp(boolean f) {
	}

	public void commandAction(Command arg0, Displayable arg1) {
		if (arg0 == c1) {
			if (menu.getString(menu.getSelectedIndex()).equals("退出")) {
				this.notifyDestroyed();
			}
			if (menu.getString(menu.getSelectedIndex()).equals("游戏帮助")) {
				display.setCurrent(help);
			}
			if (menu.getString(menu.getSelectedIndex()).equals("关于")) {
				display.setCurrent(about);
			}
			if (menu.getString(menu.getSelectedIndex()).equals("新游戏")) {
				mc = new gameScreen();
				mc.initLevel(1);
				display.setCurrent(mc);			
			}

		}
		if (arg0 == c2) {
			this.display.setCurrent(menu);
		}
	}
}
