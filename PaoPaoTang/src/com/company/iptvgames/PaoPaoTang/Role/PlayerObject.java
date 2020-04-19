package com.company.iptvgames.PaoPaoTang.Role;

import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

import com.company.iptvgames.PaoPaoTang.GameConst;
import com.company.iptvgames.PaoPaoTang.Role.states.PState;
import com.company.iptvgames.PaoPaoTang.Role.states.PWalkState;
import com.company.iptvgames.PaoPaoTang.resources.ImageRes;

public class PlayerObject {
	private PState state;
	private PState walkState;

	private Sprite walkSprite;
	
	private int life;//��ɫ����ֵ
	private int speed;//��ɫ�ƶ�Ƶ��
	private int posX;//��ɫ����ˮƽ����tileID
	private int posY;//��ɫ���ڴ�ֱ����tileID
	private int bombLeft;//ʣ���Ͷ����ը����
	private int bombPower;//��ɫ��Ͷ��ը��������
	private int bombDuration;//��ɫ��Ͷ��ը������ʱʱ��
	private String imageURL;
	private int direction = 0;//�ƶ�����,1��2��3��4��
	private boolean allowMove;//�Ƿ����ƶ�����
	private boolean allowExplored;//�Ƿ�����ը
	private int timeForDontMove;//״̬����ʱ��
	private int timeForDontExplored;//״̬����ʱ��
	private int superTimeLeft;//��ɫ��ʧ����������ʱ�䣬�ڼ䲻������ײ��Ϊ0���ʾ����״̬��������״̬
	
	public PlayerObject(String str) {
		this.imageURL = str;
		bombLeft = GameConst.Bomb.bombMAX;
		bombPower = GameConst.Bomb.bombPOWER;
		bombDuration = GameConst.Bomb.bombDURATION;
		life = GameConst.Player.LIFE;
		speed = GameConst.Player.SPEED;
		
		walkSprite = new Sprite(ImageRes.getInstance().getImage(imageURL+"DownImg"), GameConst.Player.WIDTH, GameConst.Player.HEIGHT);
		walkSprite.defineCollisionRectangle(GameConst.Player.COLLIDE_X, GameConst.Player.COLLIDE_Y, GameConst.Player.COLLIDE_W,
				GameConst.Player.COLLIDE_H);

		walkState = new PWalkState(this);
		allowMove = true;
		allowExplored = true;
		timeForDontMove = 0;
		timeForDontExplored = 0;		
		superTimeLeft = 0;
		
		updateState(walkState);
	}
	
	public void addToScreen(LayerManager layerManager) {
		layerManager.insert(walkSprite, GameConst.GameCanvas.LAYER_1);
	}

	public void removeToScreen(LayerManager layerManager) {
		layerManager.remove(walkSprite);
	}

	public void updateState(PState newState) {
		if (this.state != null) {
			this.state.exitState();
		}
		this.state = newState;
		this.state.intoState();
	}
	
	public void faceLeft() {
		this.walkSprite.setImage(ImageRes.getInstance().getImage(imageURL+"LeftImg"), GameConst.Player.WIDTH, GameConst.Player.HEIGHT);
		this.direction = 3;
	}

	public void faceRight() {
		this.walkSprite.setImage(ImageRes.getInstance().getImage(imageURL+"RightImg"), GameConst.Player.WIDTH, GameConst.Player.HEIGHT);
		this.direction = 4;
	}
	
	public void faceUp() {
		this.walkSprite.setImage(ImageRes.getInstance().getImage(imageURL+"UpImg"), GameConst.Player.WIDTH, GameConst.Player.HEIGHT);
		this.direction = 1;
	}
	
	public void faceDown() {
		this.walkSprite.setImage(ImageRes.getInstance().getImage(imageURL+"DownImg"), GameConst.Player.WIDTH, GameConst.Player.HEIGHT);
		this.direction = 2;
	}
	
	public boolean move(int dx, int dy) {
		boolean moveResult = true;
		int newX = 0;
		int newY = 0;
		
		if(allowMove){
			newX = this.posX + dx;
			newY = this.posY + dy;
			
			if(newX >= GameConst.GameCanvas.TILES_IN_WIDTH || newX < 0){
				moveResult = false;
			}
			
			if(newY >= GameConst.GameCanvas.TILES_IN_HEIGHT || newY < 0){
				moveResult = false;
			}
			
			if(moveResult){
				this.setPosition(newX, newY);
			}	
		}
		
		return moveResult;
		
	}
	
	public void setPosition(int x, int y) {
		if(x < GameConst.GameCanvas.TILES_IN_WIDTH && x >= 0 && y < GameConst.GameCanvas.TILES_IN_HEIGHT && y >= 0){
			this.posX = x;
			this.posY = y;
			this.walkSprite.setPosition((this.posX * GameConst.GameCanvas.TILE_WIDTH)+GameConst.GameCanvas.PA_X, (this.posY * GameConst.GameCanvas.TILE_HEIGHT)+GameConst.GameCanvas.PA_Y);
		}		
	}
	
	public void nextFrame() {		
		if(timeForDontExplored > 0){
			timeForDontExplored--;
		}else{
			allowExplored = true;
		}
		if(timeForDontMove > 0){
			timeForDontMove--;
		}else{
			allowMove = true;
		}
		
		if(allowMove){
			if(superTimeLeft >= 1){
				if(superTimeLeft%2 == 0){
					walkSprite.setVisible(false);					
				}else{
					walkSprite.setVisible(true);	
				}
				superTimeLeft = superTimeLeft - 1;
			}
			this.state.nextFrame();
		}
	}
	
	public void setSuperTimeLeft() {
		this.superTimeLeft = 10;
	}
	
	public int getSuperTimeLeft() {
		return superTimeLeft;
	}
	
	public boolean isInWalkState() {
		return this.getState() instanceof PWalkState;
	}

	public PState getState() {
		return state;
	}
	
	public Sprite getWalkSprite() {
		return walkSprite;
	}

	public PState getWalkState() {
		return walkState;
	}
	
	public int getX() {
		return posX;
	}
	
	public int getY() {
		return posY;
	}

	public int getDirection() {
		return direction;
	}
	
	public int getBombNum(){
		return bombLeft;
	}
	
	public void setBombNum(int num){
		this.bombLeft = bombLeft + num;
	}
	
	public int getBombPower(){
		return bombPower;
	}
	
	public void setBombPower(int num){
		this.bombPower = bombPower + num;
	}
	
	public int getBombDuration(){
		return bombDuration;
	}
	
	public void setBombDuration(int num){
		this.bombDuration = bombDuration + num;
	}
	
	public int getLife(){
		return life;
	}
	
	public void setLife(int num){		
		if(num < 0){
			if(allowExplored)//��ұ�ը
			{
				this.life = life + num;
				allowMove = false;//��ը���ֹ�ƶ�����
				allowExplored = false;//����������ը
				timeForDontExplored = 10;
				timeForDontMove = 3;
			}
		}else{
			this.life = life + num;			
		}
		
		if(life < 0){
			life = 0;			
		}else{
			setSuperTimeLeft();//����ֵ��0ʱ����ʧ�������ɫ����
		}
	}

	public int getSpeed(){
		return speed;
	}
	
	public void setSpeed(int num){
		this.speed = speed / num;
	}
	
}
