package com.company.iptvgames.RunCool.Role;

import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

import com.company.iptvgames.RunCool.Role.states.PJumpState;
import com.company.iptvgames.RunCool.Role.states.PState;
import com.company.iptvgames.RunCool.GameConst;
import com.company.iptvgames.RunCool.Role.states.PWalkState;
import com.company.iptvgames.RunCool.resources.ImageRes;

public class PlayerObject {
	private PState state;
	private PState walkState;
	private PState jumpState;
	private Sprite walkSprite;
	private Sprite jumpSprite;
	//private int jumpSequence[]= {0,1,1,2,2,2,3,3,3,4,4,5,5,6,6,7};
	private int jumpSequence[] = GameConst.Player.JUMP_FRAME_SEQUENCE;

	private int life;//��ɫ����ֵ
	private int posX;//��ɫ����λ��x
	private int posY;//��ɫ����λ��y
	private int superTimeLeft;//��ɫ��ʧ����������ʱ�䣬�ڼ䲻������ײ��Ϊ0���ʾ����״̬��������״̬
	
	public PlayerObject() {
		walkSprite = new Sprite(ImageRes.getInstance().getImage("pbRunImg"), GameConst.Player.WALKWIDTH/GameConst.Player.ROLE_FRAME, GameConst.Player.WALKHEIGHT);		
		walkSprite.defineCollisionRectangle(GameConst.Player.COLLIDE_X, GameConst.Player.COLLIDE_Y, GameConst.Player.COLLIDE_W,
				GameConst.Player.COLLIDE_H);
		walkSprite.setVisible(true);
		
		jumpSprite = new Sprite(ImageRes.getInstance().getImage("pbJumpImg"), GameConst.Player.JUMPWIDTH/GameConst.Player.ROLE_FRAME, GameConst.Player.JUMPHEIGHT);		
		jumpSprite.defineCollisionRectangle(GameConst.Player.COLLIDE_X, GameConst.Player.COLLIDE_Y, GameConst.Player.COLLIDE_W,
				GameConst.Player.COLLIDE_H);
		jumpSprite.setVisible(false);
		jumpSprite.setFrameSequence(jumpSequence);

		posX = GameConst.Player.POS_X;
		posY = GameConst.Player.POS_Y;
		setPosition(posX,posY);
		
		life = GameConst.Player.LIFE;
		superTimeLeft = 5;
		
		walkState = new PWalkState(this);
		jumpState = new PJumpState(this);

		updateState(walkState);

	}
	
	public void addToScreen(LayerManager layerManager) {
		layerManager.insert(walkSprite, GameConst.Player.LAYER_player);
		layerManager.insert(jumpSprite, GameConst.Player.LAYER_player);
	}

	public void removeToScreen(LayerManager layerManager) {
		layerManager.remove(walkSprite);
		layerManager.remove(jumpSprite);
	}

	public void updateState(PState newState) {
		if (this.state != null) {
			this.state.exitState();
		}
		this.state = newState; 
		this.state.intoState();
	}
	
	public void nextFrame() {		

		if(superTimeLeft >= 1){
			if(superTimeLeft%2 == 0){
				if(state == walkState){
					walkSprite.setVisible(false);
				}else{
					jumpSprite.setVisible(false);
				}					
			}else{
				if(state == walkState){
					walkSprite.setVisible(true);
				}else{
					jumpSprite.setVisible(true);
				}	
			}
			superTimeLeft = superTimeLeft - 1;
		}
			
		this.state.nextFrame();
				
		if(state == jumpState){
			//�����Ծ��״̬����Ϊ��
			if(jumpSprite.getFrame() == 0){
				updateState(walkState);
			}else{
				jump();	
			}				
		}			
	}
	
	public boolean isInWalkState() {
		return this.getState() instanceof PWalkState;
	}

	public boolean isInJumpState() {
		return this.getState() instanceof PJumpState;
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
	
	public Sprite getJumpSprite() {
		return jumpSprite;
	}

	public PState getJumpState() {
		return jumpState;
	}
	
	public int getX() {
		return posX;
	}
	
	public int getY() {
		return posY;
	}
	
	public void setPosition(int x, int y) {
		this.posX = x;
		this.posY = y;
		this.walkSprite.setPosition(x, y);
		this.jumpSprite.setPosition(x, y);
	}
	
	public int getLife(){
		return life;
	}
	
	public int getSuperTimeLeft() {
		return superTimeLeft;
	}
	
	public void setLife(int num){		
		this.life = life + num;
		
		if(life < 0){
			life = 0;			
		}else{
			setSuperTimeLeft();//����ֵ��0ʱ����ʧ�������ɫ����
		}
	}
	
	public void setSuperTimeLeft() {
		this.superTimeLeft = 10;
	}
	
	public void drop(){
		setPosition(getX(),getY() + GameConst.Player.ADD_JUMP_HEIGHT);
	}
	
	public void jump(){
		int frameNo = 0;
		
		frameNo = jumpSprite.getFrame();
		if(frameNo < GameConst.Player.JUMP_FRAME/2){
			setPosition(getX(),getY() - GameConst.Player.ADD_JUMP_HEIGHT);
		}else{
			setPosition(getX(),getY() + GameConst.Player.ADD_JUMP_HEIGHT);
		}
	}
}
