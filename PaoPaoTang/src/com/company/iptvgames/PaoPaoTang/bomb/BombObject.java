package com.company.iptvgames.PaoPaoTang.bomb;

import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

import com.company.iptvgames.PaoPaoTang.GameConst;
import com.company.iptvgames.PaoPaoTang.Role.PlayerObject;
import com.company.iptvgames.PaoPaoTang.resources.ImageRes;

public class BombObject{
	private Sprite bombSprite;
	private PlayerObject owner;
	private int posX;
	private int posY;
	private int state;//1:等待爆炸，2：爆炸中，0:结束
	private int duration;//包括爆炸在内共存在的时间长度
	private int power;//炸弹威力
	private int[] attackArea;
	private ExploredFire EF;
	
	public BombObject(PlayerObject spr) {
		owner = spr;
		posX = spr.getX();
		posY = spr.getY();
		power = spr.getBombPower();
		duration = spr.getBombDuration();
		attackArea = new int[4*power+1];
		state = 1;
		this.bombSprite = new Sprite(ImageRes.getInstance().getImage("bombImg"), GameConst.Bomb.WIDTH, GameConst.Bomb.HEIGHT);
		this.bombSprite.setPosition((this.posX * GameConst.GameCanvas.TILE_WIDTH)+GameConst.GameCanvas.PA_X, (this.posY * GameConst.GameCanvas.TILE_HEIGHT)+GameConst.GameCanvas.PA_Y);
		
		computeAttackArea();
	}

	public void removeFromScreen(LayerManager layerManager) {
		layerManager.remove(bombSprite);
		owner.setBombNum(1);
	}

	public void addToScreen(LayerManager layerManager) {
		layerManager.insert(bombSprite, GameConst.GameCanvas.LAYER_2);
		owner.setBombNum(-1);
	}

	public Sprite getBombSprite() {
		return bombSprite;
	}

	public int getX() {
		return posX;
	}

	public int getY() {
		return posY;
	}
	
	public int getPower() {
		return power;
	}
	
	public int checkStates(){		
		if(duration>2){
			this.bombSprite.nextFrame();
			duration--;
		}else if(duration>0){
			state = 2;
			duration--;
			if(duration == 0){
				state = 0;
				for(int i = 0;i<attackArea.length;i++){
					attackArea[i]=0;
				}
			}
		}
		
		return state;
	} 
	
	//按照由内向外，由上下左右顺序依次添加爆炸区域所在tiled序号
	public void computeAttackArea(){
		int num =0;

		for(int i = 1;i < power + 1;i++){
			//上
			if((posY-i)>=0){
				attackArea[num] = (posY-i) * GameConst.GameCanvas.TILES_IN_WIDTH + posX + 1;
			}else{
				attackArea[num] = 0;
			}
			num++;
			
			//下
			if((posY+i)<GameConst.GameCanvas.TILES_IN_HEIGHT){
				attackArea[num] = (posY+i) * GameConst.GameCanvas.TILES_IN_WIDTH + posX + 1;
			}else{
				attackArea[num] = 0;
			}
			num++;
			
			//左
			if((posX-i)>=0){
				attackArea[num] = posY * GameConst.GameCanvas.TILES_IN_WIDTH + posX -i + 1;
			}else{
				attackArea[num] = 0;
			}
			num++;
			
			//右
			if((posX+i)<GameConst.GameCanvas.TILES_IN_WIDTH){
				attackArea[num] = posY * GameConst.GameCanvas.TILES_IN_WIDTH + posX + i + 1;
			}else{
				attackArea[num] = 0;
			}
			num++;
		}
		
		//炸弹中心位置
		attackArea[num] = posY * GameConst.GameCanvas.TILES_IN_WIDTH + posX + 1;
	}	

	public int[] getAttackArea() {
		return attackArea;
	}
	
	public void setAttackArea(int tiled, int value) {
		attackArea[tiled] = value;
	}
	
	public void addExploredEffect(LayerManager layerManager){
		EF = new ExploredFire(posX, posY, attackArea);		
		EF.addExploredFire(layerManager);		
	}
	
	public void removeExploredEffect(LayerManager layerManager){	
		EF.removeExploredFire(layerManager);		
	}
}
