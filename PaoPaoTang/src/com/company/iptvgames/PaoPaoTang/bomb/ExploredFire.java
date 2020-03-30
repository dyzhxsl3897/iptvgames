package com.company.iptvgames.PaoPaoTang.bomb;

import java.util.Vector;

import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

import com.company.iptvgames.PaoPaoTang.GameConst;
import com.company.iptvgames.PaoPaoTang.resources.ImageRes;

public class ExploredFire {
	private Sprite Center;
	private Sprite EdgeUp;
	private Sprite EdgeDown;
	private Sprite EdgeLeft;
	private Sprite EdgeRight;
	private Sprite Up;
	private Sprite Down;
	private Sprite Left;
	private Sprite Right;
	private Vector UpVector;
	private Vector DownVector;
	private Vector LeftVector;
	private Vector RightVector;
	private int[] showArea;
	private int posX, posY;
	
	public ExploredFire(int tempX, int tempY, int[] attackArea) {
		showArea = attackArea;
		posX = tempX;
		posY = tempY;
		UpVector = new Vector();
		DownVector = new Vector();
		LeftVector = new Vector();
		RightVector = new Vector();
		
		Center = new Sprite(ImageRes.getInstance().getImage("bombingImg"), GameConst.Bomb.fireCenterWidth, GameConst.Bomb.fireCenterWidth);
		EdgeUp = new Sprite(ImageRes.getInstance().getImage("edgeUpImg"), GameConst.Bomb.fireEdgeWidth, GameConst.Bomb.fireEdgeHeight);
		EdgeDown = new Sprite(ImageRes.getInstance().getImage("edgeDownImg"), GameConst.Bomb.fireEdgeWidth, GameConst.Bomb.fireEdgeHeight);
		EdgeLeft = new Sprite(ImageRes.getInstance().getImage("edgeLeftImg"), GameConst.Bomb.fireEdgeHeight, GameConst.Bomb.fireEdgeWidth);
		EdgeRight = new Sprite(ImageRes.getInstance().getImage("edgeRightImg"), GameConst.Bomb.fireEdgeHeight, GameConst.Bomb.fireEdgeWidth);
				
	}
	
	public void addExploredFire(LayerManager layerManager){
		int tempX = (this.posX * GameConst.GameCanvas.TILE_WIDTH) + GameConst.GameCanvas.PA_X;
		int tempY = (this.posY * GameConst.GameCanvas.TILE_HEIGHT) + GameConst.GameCanvas.PA_Y + 17;//17为人物高度减去tile高度

		//添加爆炸区最边界
		for(int i = 0; i < showArea.length - 1; i++ ){
			if(showArea[i]!=0){
				if(i%4 == 0){//上方向
					Up = new Sprite(ImageRes.getInstance().getImage("bombUpImg"), GameConst.Bomb.fireHeight, GameConst.Bomb.fireWidth);
					UpVector.addElement(Up);					
				}else if(i%4 == 1){//下方向
					Down = new Sprite(ImageRes.getInstance().getImage("bombDownImg"), GameConst.Bomb.fireHeight, GameConst.Bomb.fireWidth);
					DownVector.addElement(Down);
				}else if(i%4 == 2){//左方向
					Left = new Sprite(ImageRes.getInstance().getImage("bombLeftImg"), GameConst.Bomb.fireWidth, GameConst.Bomb.fireHeight);
					LeftVector.addElement(Left);
				}else if(i%4 == 3){//右方向
					Right = new Sprite(ImageRes.getInstance().getImage("bombRightImg"), GameConst.Bomb.fireWidth, GameConst.Bomb.fireHeight);
					RightVector.addElement(Right);				
				}
			}
		}
				
		//显示上向爆炸效果
		if(UpVector.size() != 0){
			for(int j = 0; j < UpVector.size(); j++){
				((Sprite)UpVector.elementAt(j)).setPosition(tempX, tempY - (j + 1) * GameConst.Bomb.fireWidth);
				layerManager.insert(((Sprite)(UpVector.elementAt(j))), GameConst.GameCanvas.LAYER_2);
			}
			EdgeUp.setPosition(tempX, tempY - GameConst.Bomb.fireEdgeHeight - UpVector.size()* GameConst.Bomb.fireWidth);	
			layerManager.insert(EdgeUp, GameConst.GameCanvas.LAYER_2);
		}		
				
		//显示下向爆炸效果
		if(DownVector.size() != 0){
			for(int j = 0; j <DownVector.size(); j++){
				((Sprite)DownVector.elementAt(j)).setPosition(tempX, tempY + (j + 1) * GameConst.Bomb.fireWidth);	
				layerManager.insert(((Sprite)DownVector.elementAt(j)), GameConst.GameCanvas.LAYER_2);
			}
			EdgeDown.setPosition(tempX, tempY + (DownVector.size() + 1)* GameConst.Bomb.fireWidth);	
			layerManager.insert(EdgeDown, GameConst.GameCanvas.LAYER_2);
		}					
					
				
		//显示左向爆炸效果
		if(LeftVector.size() != 0){
			for(int j = 0; j <LeftVector.size(); j++){
				((Sprite)LeftVector.elementAt(j)).setPosition(tempX - (j + 1) * GameConst.Bomb.fireWidth, tempY);	
				layerManager.insert(((Sprite)LeftVector.elementAt(j)), GameConst.GameCanvas.LAYER_2);
			}
			EdgeLeft.setPosition(tempX - GameConst.Bomb.fireEdgeHeight - LeftVector.size() * GameConst.Bomb.fireWidth, tempY);	
			layerManager.insert(EdgeLeft, GameConst.GameCanvas.LAYER_2);
		}		
				
		//显示右向爆炸效果
		if(RightVector.size() != 0){
			for(int j = 0; j <RightVector.size(); j++){
				((Sprite)RightVector.elementAt(j)).setPosition(tempX + (j +1) * GameConst.Bomb.fireWidth, tempY);
				layerManager.insert(((Sprite)RightVector.elementAt(j)), GameConst.GameCanvas.LAYER_2);
			}
			
			EdgeRight.setPosition(tempX + (RightVector.size() + 1)* GameConst.Bomb.fireWidth, tempY);
			layerManager.insert(EdgeRight, GameConst.GameCanvas.LAYER_2);
		}		
				
		//添加中心区域显示
		Center.setPosition(tempX - (GameConst.Bomb.fireCenterWidth - GameConst.GameCanvas.TILE_WIDTH)/2, tempY - (GameConst.Bomb.fireCenterWidth - GameConst.GameCanvas.TILE_WIDTH)/2);
		layerManager.insert(Center, GameConst.GameCanvas.LAYER_2);				
	}
	
	public void removeExploredFire(LayerManager layerManager){
		layerManager.remove(Center);

		//移除上向爆炸效果
		if(UpVector != null){
			for(int i = UpVector.size() -1; i >=0; i--){
				layerManager.remove(((Sprite)UpVector.elementAt(i)));			
			}
			UpVector.removeAllElements();
			layerManager.remove(EdgeUp);
		}
		
		//移除下向爆炸效果
		if(DownVector != null){
			for(int i = DownVector.size() -1; i >=0; i--){
				layerManager.remove(((Sprite)DownVector.elementAt(i)));				
			}
			DownVector.removeAllElements();
			layerManager.remove(EdgeDown);
		}
		
		//移除左向爆炸效果
		if(LeftVector != null){
			for(int i = LeftVector.size() -1; i >=0; i--){
				layerManager.remove(((Sprite)LeftVector.elementAt(i)));				
			}
			LeftVector.removeAllElements();
			layerManager.remove(EdgeLeft);
		}
		
		//移除右向爆炸效果
		if(RightVector != null){
			for(int i = RightVector.size() -1; i >=0; i--){
				layerManager.remove(((Sprite)RightVector.elementAt(i)));				
			}
			RightVector.removeAllElements();
			layerManager.remove(EdgeRight);
		}
	}

}

