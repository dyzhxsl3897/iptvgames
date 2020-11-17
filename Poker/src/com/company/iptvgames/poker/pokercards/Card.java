package com.company.iptvgames.poker.pokercards;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;

import com.company.iptvgames.framework.utils.ImageUtil;
import com.company.iptvgames.poker.GameConst;
import com.company.iptvgames.poker.resources.ImageRes;

public class Card {
	private Sprite cardSprite1;//�Ʊ���
	private Sprite cardSprite2;//������
	private int PosX,PosY;
	
	//numberID������ID��isBottom���Ƿ��Ե�����ʽ��ʾ
	public Card(int numberID, boolean isBottom){
		if(isBottom){
			ImageRes.getInstance().loadImage("cardBack", ImageUtil.createImage("/cards/smallID/b.png"));
			ImageRes.getInstance().loadImage("cardNo", ImageUtil.createImage("/cards/smallID/"+numberID+".png"));
		}else{
			ImageRes.getInstance().loadImage("cardBack", ImageUtil.createImage("/cards/bigID/b.png"));
			ImageRes.getInstance().loadImage("cardNo", ImageUtil.createImage("/cards/bigID/"+numberID+".png"));
		}
		
		Image cardImg1 = ImageRes.getInstance().getImage("cardBack");
		Image cardImg2 = ImageRes.getInstance().getImage("cardNo");
		cardSprite1 = new Sprite(cardImg1);
		cardSprite2 = new Sprite(cardImg2);
		cardSprite1.setVisible(false);
		cardSprite2.setVisible(false);	
	}
	
	public void setPosition(int x, int y){
		PosX = x;
		PosY = y;
		cardSprite1.setPosition(PosX,PosY);
		cardSprite2.setPosition(PosX,PosY);
	}
	
	public void setVisible(boolean visiable){
		//true:��ʾ����ֵ��false����ʾ�Ʊ���
		if(visiable){
			cardSprite2.setVisible(true);
		}else{
			cardSprite1.setVisible(true);
		}
	}
	
	public void addToscreen(LayerManager layerManager){
		layerManager.insert(cardSprite1, GameConst.GameCanvas.LAYER_card);
		layerManager.insert(cardSprite2, GameConst.GameCanvas.LAYER_card);
	}
	
	public void removeFromscreen(LayerManager layerManager){
		layerManager.remove(cardSprite1);
		layerManager.remove(cardSprite2);
	}
	
	public int getX(){
		return PosX;
	}
	
	public int getY(){
		return PosY;
	}
}
