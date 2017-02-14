import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

public class CrookSprite extends Sprite {

	public final static int CIRCUMGYRATEING = 11;

	public final static int STRETCHING = 12;

	public final static int BACKING = 13;

	public final static int CATCHING = 14;

	public int CrookSpriteState = CIRCUMGYRATEING;

	public static final double[] SIN = { Math.sin(75 * Math.PI / 180),
			Math.sin(60 * Math.PI / 180), Math.sin(45 * Math.PI / 180),
			Math.sin(30 * Math.PI / 180), Math.sin(15 * Math.PI / 180),
			Math.sin(0 * Math.PI / 180), Math.sin(-15 * Math.PI / 180),
			Math.sin(-30 * Math.PI / 180), Math.sin(-45 * Math.PI / 180),
			Math.sin(-60 * Math.PI / 180), Math.sin(-75 * Math.PI / 180) };

	public static final double[] COS = { Math.cos(75 * Math.PI / 180),
			Math.cos(60 * Math.PI / 180), Math.cos(45 * Math.PI / 180),
			Math.cos(30 * Math.PI / 180), Math.cos(15 * Math.PI / 180),
			Math.cos(0 * Math.PI / 180), Math.cos(-15 * Math.PI / 180),
			Math.cos(-30 * Math.PI / 180), Math.cos(-45 * Math.PI / 180),
			Math.cos(-60 * Math.PI / 180), Math.cos(-75 * Math.PI / 180) };

	private int nowSpeed;
	
	private int nowValue;

	private int direction;

	private boolean isRight;

	private int lineEndX;

	private int lineEndY;
	
	private int linelength;

	private int catchOre;

	public CrookSprite(Image arg0, int arg1, int arg2) {
		super(arg0, arg1, arg2);
	}

	public void upData(OreSprite[] allOre) {
		switch (CrookSpriteState) {
		case CIRCUMGYRATEING:
			if (isRight) {
				direction++;
				if (direction >= 10)
					isRight = false;
			} else {
				direction--;
				if (direction <= 0)
					isRight = true;
			}

			
			break;
		case STRETCHING:
			linelength+=nowSpeed;
			checkCollide(allOre);
			break;
		case BACKING:
			if(gameScreen.nowFrame==0)
				gameScreen.nowFrame=1;
			else
				gameScreen.nowFrame=0;
			linelength-=nowSpeed;
			if(linelength<=16){
				this.CrookSpriteState=CIRCUMGYRATEING;
				gameScreen.fire=false;
				gameScreen.nowFrame=1;
			}
			break;
		case CATCHING:
			if(gameScreen.nowFrame==0)
				gameScreen.nowFrame=1;
			else
				gameScreen.nowFrame=0;
			linelength-=nowSpeed;
			allOre[catchOre].setPosition(lineEndX-allOre[catchOre].getWidth()/2, lineEndY);
			if(linelength<=16){
				this.CrookSpriteState=CIRCUMGYRATEING;
				gameScreen.score+=nowValue;
				nowValue=0;
				gameScreen.fire=false;
				gameScreen.nowFrame=1;
				allOre[catchOre].setVisible(false);
			}
			break;
		default:
			break;
		}
		lineEndX = (int) (110 - linelength * SIN[direction]);
		lineEndY = (int) (39 + linelength * COS[direction]);
		checkBounds();
		if (direction > 5) {
			this.setFrame(5 - (direction - 5));
			this.setTransform(Sprite.TRANS_MIRROR);
			this.setPosition(lineEndX - 9, lineEndY - 10);
		} else {
			this.setFrame(direction);
			this.setTransform(Sprite.TRANS_NONE);
			this.setPosition(lineEndX - 15, lineEndY - 10);
		}

		
	}

	private void checkBounds() {
		if(lineEndX<=0||lineEndX>=240||lineEndY>=291){
			this.CrookSpriteState=BACKING;
		}
		
	}

	private void checkCollide(OreSprite[] allOre) {
		for (int i = 0; i < allOre.length; i++) {
			if(this.collidesWith(allOre[i], true)){
				this.CrookSpriteState=CATCHING;
				nowSpeed=allOre[i].getSpeed();
				nowValue=allOre[i].getValue();
				catchOre=i;
			}
		}
	
	}

	public void init() {
		isRight = true;
		nowSpeed=0;
		nowValue=0;
		direction = 5;
		lineEndX = 110;
		lineEndY = 55;
		linelength=16;
		catchOre=-1;
		setFrame(5);

	}

	public void drawCrook(Graphics g) {
		g.setColor(0);
		g.drawLine(110, 39, lineEndX, lineEndY);
		this.paint(g);
	}

	public void setNowSpeed(int nowSpeed) {
		this.nowSpeed = nowSpeed;
	}

}
