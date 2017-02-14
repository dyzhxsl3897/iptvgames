import javax.microedition.midlet.*;
import javax.microedition.lcdui.*;

public class gameStart extends MIDlet implements CommandListener {
	public Alert logo; //logo��Ļ

	public Form about; //���ڻ���

	public Form help; //��������

	public List menu; //�˵�����

	public gameScreen mc;//��Ϸ����

	public Display display;//��Ļ���ƶ���

	public Command c1, c2; //ȷ������������

	public Command cback; //������Ϸ����

	public static gameStart midlet; //ȫ��ʵ������

	public gameStart() {
		midlet = this;
		display = Display.getDisplay(this);
		c1 = new Command("ȷ��", Command.BACK, 1);
		c2 = new Command("����", Command.SCREEN, 1);
		logo = new Alert("");
		logo.setTimeout(5000);
		Image im = loadImage("/logo.png");
		logo.setImage(im);
		logo.setType(AlertType.INFO);
		String aboutInfo = "������XX�Ƽ����޹�˾" + "\n�ͷ��绰��010-65280XXX"
				+ "\n�ͷ����䣺service@XXX.com.cn" + "\n�����ṩ" + "\n����XXXX�Ƽ���չ���޹�˾";
		about = new Form("��������");
		about.append(aboutInfo);
		about.addCommand(c2);
		about.setCommandListener(this);

		help = new Form("��Ϸ����");
		String helpinfo = "";

		help.append(helpinfo);
		help.addCommand(c2);
		help.setCommandListener(this);
		

		menu = new List("�ڽ��", List.IMPLICIT);
		menu.addCommand(c1);

		menu.append("����Ϸ", null);
		menu.append("��Ϸ����", null);
		menu.append("����", null);
		menu.append("�˳�", null);
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
			if (menu.getString(menu.getSelectedIndex()).equals("�˳�")) {
				this.notifyDestroyed();
			}
			if (menu.getString(menu.getSelectedIndex()).equals("��Ϸ����")) {
				display.setCurrent(help);
			}
			if (menu.getString(menu.getSelectedIndex()).equals("����")) {
				display.setCurrent(about);
			}
			if (menu.getString(menu.getSelectedIndex()).equals("����Ϸ")) {
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
