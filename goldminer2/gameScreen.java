import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;



public class gameScreen extends GameCanvas implements Runnable{
	public final static int GAME = 1;

	public final static int WIN = 2;

	public final static int LOST = 3;

	public final static int LEVELINFO = 0;

	public final static int GAMEEND = 4;

	public final static int PAUSE = 5;

	public  int gameState = LEVELINFO;
	
	private Graphics g=this.getGraphics();

	private int levelScore[] = { 0, 650, 1000 };


	private int levelTime[] = { 0, 60, 60 };

	private int level;

	private Image gameback;

	private Image crook;

	private Image goldlarge;

	private Image goldmedium;

	private Image goldsmall;

	private Image mandown;

	private Image manup;

	private Image[] man;
	
	private Image rocklarge;

	private Image rockmedium;

	private Image scoremenu;
	
	private OreSprite[] allOre;
	
	private CrookSprite crookSprite;
	
	public static  int score;

	private int count=0;

	public static int nowFrame;

	public int nowTime;



	public static  boolean fire;


	
	protected gameScreen() {
		super(true);
		crook = gameStart.loadImage("/crook.png");
		gameback = gameStart.loadImage("/gameback.png");
		goldlarge = gameStart.loadImage("/goldlarge.png");
		goldmedium = gameStart.loadImage("/goldmedium.png");
		goldsmall = gameStart.loadImage("/goldsmall.png");
		mandown = gameStart.loadImage("/mandown.png");
		manup = gameStart.loadImage("/manup.png");
		man=new Image[2];
		man[0]=mandown;
		man[1]=manup;
		rocklarge = gameStart.loadImage("/rocklarge.png");
		rockmedium = gameStart.loadImage("/rockmedium.png");
		scoremenu = gameStart.loadImage("/scoremenu.png");
		Thread t = new Thread(this);
		t.start();
	}
	public void initLevel(int n) {
		this.gameState = LEVELINFO;
		fire=false;
		level=n;
		nowTime=levelTime[level];
		nowFrame=1;
		crookSprite=new CrookSprite(crook,24,24);
		crookSprite.init();
		if (n == 1) {
			score = 0;
			allOre = new OreSprite[9];
			allOre[0]=new OreSprite(goldsmall,3,50,25,105);
			allOre[1]=new OreSprite(goldsmall,3,50,110,125);
			allOre[2]=new OreSprite(rocklarge,1,50,145,85);
			allOre[3]=new OreSprite(rockmedium,2,20,45,155);
			allOre[4]=new OreSprite(goldmedium,2,250,15,200);
			allOre[5]=new OreSprite(goldmedium,2,250,185,185);
			allOre[6]=new OreSprite(goldlarge,1,500,110,215);
			allOre[7]=new OreSprite(goldlarge,1,500,35,255);
			allOre[8]=new OreSprite(goldsmall,3,50,180,255);
			
		}
		if (n == 2) {
			allOre = new OreSprite[9];
			allOre[0]=new OreSprite(rockmedium,2,20,25,105);
			allOre[1]=new OreSprite(rocklarge,1,50,110,125);
			allOre[2]=new OreSprite(rocklarge,1,50,145,85);
			allOre[3]=new OreSprite(rockmedium,2,20,45,155);
			allOre[4]=new OreSprite(goldsmall,3,50,15,200);
			allOre[5]=new OreSprite(goldmedium,2,250,185,185);
			allOre[6]=new OreSprite(goldmedium,2,250,110,215);
			allOre[7]=new OreSprite(goldsmall,3,50,35,255);
			allOre[8]=new OreSprite(goldsmall,3,50,180,255);
			
		}
	}
	public void run() {
		while (true) {
		long currTime = System.currentTimeMillis();
		game(this.gameState);
		long lastTime = currTime;	
		long delay = 100 - (currTime - lastTime);
			try {
				if (delay <= 0)
					delay = 1;
				Thread.sleep(delay);
			} catch (Exception exception) {
			}
		}
	}
	public void game(int g) {
		switch (g) {
		case LEVELINFO:
			drawInfo();
			count++;
			if (count >= 30) {
				count = 0;
				this.gameState = GAME;
			}
			break;
		case GAME:
			drawBack();
			drawMan();
			drawCrook();
			drawOre();
			count++;
			if (count >= 10) {
				count = 0;
				nowTime--;
				if (nowTime <= 0) {
					if (score >= levelScore[level]) {
						if (level >= 2)
							this.gameState = GAMEEND;
						else
							this.gameState = WIN;
					} else
						this.gameState = LOST;
				}
			}
			key();
			crookSprite.upData(allOre);
			break;
		case WIN:	
			drawWin();
			count++;
			if (count >= 30) {
				count = 0;
				initLevel(level+1);
				this.gameState = LEVELINFO;
			}
			break;
		case LOST:	
			drawLost();
			count++;
			if (count >= 30) {
				count = 0;
				gotoMenu();
			}
			break;
		case GAMEEND:	
			drawGameEnd();
			count++;
			if (count >= 30) {
				count = 0;
				gotoMenu();
			}
			break;	
		default:
			break;
		}
		this.flushGraphics();
	}
	private void drawGameEnd() {
		g.setColor(0);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(0Xffff00);
		g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
		g.drawString("恭喜您通过了所有关", 60, 110, 0);
		g.drawString("真是太厉害了",80, 130, 0);
	
		
	}
	private void gotoMenu() {
		gameStart.midlet.display.setCurrent(gameStart.midlet.menu);
		this.gameState = PAUSE;
	
		
	}
	private void drawLost() {
		g.setColor(0);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(0Xffff00);
		g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
		g.drawString("GameOver", 80, 110, 0);
	
	}
	private void drawWin() {
		g.setColor(0);
		g.fillRect(0, 0, this.getWidth(), this.getHeight());
		g.setColor(0Xffff00);
		g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
		g.drawString("恭喜您通过了第"+level+"关", 60, 110, 0);
		g.drawString("请购买道具", 80, 130, 0);
	}
	private void key() {

		if(!fire){
			int key =this.getKeyStates();
			if((key&GameCanvas.FIRE_PRESSED)!=0){
				fire=true;
				nowFrame=0;
				crookSprite.CrookSpriteState=CrookSprite.STRETCHING;
				crookSprite.setNowSpeed(8);
			}
		}
		
	}
	private void drawCrook() {
		crookSprite.drawCrook(g);
	}
	private void drawOre() {
		for (int i = 0; i < allOre.length; i++) {
			allOre[i].paint(g);
		}
	}
	private void drawMan() {
		g.drawImage(man[nowFrame], 90, 8, 0);
	}
	private void drawBack() {
		g.drawImage(gameback, 0, 0, 0);
		g.setColor(0Xffff00);
		g.drawString(String.valueOf(score), 30, -2, 0);
		g.drawString(String.valueOf(levelScore[level]), 45, 22, 0);
		g.drawString(String.valueOf(nowTime), 220, 0, 0);
		g.drawString(String.valueOf(level), 212, 22, 0);
	}
	private void drawInfo() {
		g.drawImage(scoremenu, 0, 0, 0);
		g.setColor(0);
		g.setFont(Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_LARGE));
		g.drawString("第"+level+"关", 105, 110, 0);
		g.drawString("目标分数"+levelScore[level], 80, 130, 0);

	}


}
