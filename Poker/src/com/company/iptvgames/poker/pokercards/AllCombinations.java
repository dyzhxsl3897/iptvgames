package com.company.iptvgames.poker.pokercards;

import java.util.Vector;

public class AllCombinations {
	
	boolean firstShow = false;
	
	private int[] allCards;
	private Vector singleVector = new Vector();//����
	private Vector pairVector = new Vector();//����
	private Vector tripleVector = new Vector();//����
	private Vector tripleWithOneVector = new Vector();//����һ
	private Vector straightVector = new Vector();//˳��
	private Vector straightPairVector = new Vector();//˫˳��
	private Vector straightTripleVector = new Vector();//��˳��
	private Vector planeVector = new Vector();//�ɻ�
	private Vector fourWithTwoVector = new Vector();//�Ĵ���
	private Vector bompVector = new Vector();//ը��
	private Vector rocketVector = new Vector();//���,˫��
	
	private int[] commonPokes = new int[11];//�ֱ�洢3��k���Ƹ���
	private int count2 = 0;
	private int count1 = 0;
	private int countKing = 0;
	
	private AllCombinations(){		
	}
	
	public void AIcards(int[] plCards){
		allCards= plCards;
//		allCards= new int[]{13,14,15,16,17,18,19,21,22,23,25,26,29,30,33,34};
		initAllVector();

		//�Ƹ���
		for(int i = 0; i < allCards.length; i++){
			int cardValue = PokerCard.getCardNumber(allCards[i]);
			if(cardValue == 14){
				count1++;
			}else if(cardValue == 15){
				count2++;
			}else if(cardValue == 53 || cardValue == 54){
				countKing++;
			}else{
				commonPokes[cardValue - 3]++;
			}
		}

		//��˳�ӣ�l-˳���в�ͬ�Ƶĸ�����
		boolean flag = false;		
		for(int l = 2; l < allCards.length/3; l++){
			for(int i = 0; i <= commonPokes.length - l; i++){
				if(commonPokes[i] >= 3){
					flag = true;
					//�ж�i������l�����Ƿ�Ϊ3
					for(int j = 1; j < l; j++){						
						if(commonPokes[i+ j] < 3){
							flag = false;
						}
					}
					
					if(flag){						
						int[] ss = new int[l * 3];
						int index = 0;
					
						for(int j = 0; j < allCards.length; j++){//���μ��ÿ����
							if(PokerCard.getCardNumber(allCards[j]) == i + 3){	//��ֵ��i��ͬ��˳�ӿ�ʼȡ��
								for(int k = 0; k < l; k++){//����ȡ��ͬ��ֵ���Ƹ�ȡ3��
									ss[index] = allCards[j];
									ss[index + 1] = allCards[j + 1];
									ss[index + 2] = allCards[j + 2];
									index = index  + 3;	
									j = j + commonPokes[i + k];
								}
								j = allCards.length;//˳��ȡ����ɣ������ټ�������к�����ƣ���������˳��
							}
						}
						straightTripleVector.addElement(ss);
						flag = false;
					}					
				}
			}		
		}
		
		//˫˳��
		flag = false;
		for(int l = 3; l < allCards.length/2; l++){
			for(int i = 0; i < commonPokes.length - l; i++){
				if(commonPokes[i] == 2){
					flag = true;
					//�ж�i������l�����Ƿ�Ϊ2
					for(int j = 1; j < l; j++){						
						if(commonPokes[i+ j] != 2){
							flag = false;
						}
					}
					
					if(flag){
						int[] ss = new int[l * 2];
						int index = 0;
						for(int j = 0; j < allCards.length; j++){//���μ��ÿ����
							if(PokerCard.getCardNumber(allCards[j]) == i + 3){	//��ֵ��i��ͬ��˳�ӿ�ʼȡ��
								for(int k = 0; k < l; k++){//����ȡ��ͬ��ֵ���Ƹ�ȡ3��
									ss[index] = allCards[j];
									ss[index + 1] = allCards[j + 1];
									index = index  + 2;	
									j = j + commonPokes[i + k];
								}
								j = allCards.length;//˳��ȡ����ɣ������ټ�������к�����ƣ���������˳��
							}
						}	
						straightPairVector.addElement(ss);
						flag = false;
					}					
				}
			}		
		}
		
		//˳��
		flag = false;
		for(int l = 5; l < allCards.length; l++){
			for(int i = 0; i < commonPokes.length - l; i++){
				if(commonPokes[i] > 1){
					flag = true;
					//�ж�i������l�����Ƿ�Ϊ1
					for(int j = 1; j < l; j++){						
						if(commonPokes[i+ j] < 1){
							flag = false;
						}
					}
					
					if(flag){
						int[] ss = new int[l];
						int index = 0;
						for(int j = 0; j < allCards.length; j++){
							if(PokerCard.getCardNumber(allCards[j]) == i + 3){
								for(int k= 0; k < l; k++){							
									ss[index] = allCards[j];
									index++;
									j = j + commonPokes[i + k];
								}								
							}
						}	
						straightVector.addElement(ss);
						flag = false;
					}					
				}
			}		
		}
		
		//����
		for(int i = 0; i < commonPokes.length; i++){
			if(commonPokes[i] == 3){					
				int[] ss = new int[3];
				int index = 0;
				for(int j = 0; j < allCards.length; j++){
					if(PokerCard.getCardNumber(allCards[j]) == i+3){								
						ss[index] = allCards[j];
						index++;	
					}	
				}	
				tripleVector.addElement(ss);
			}
		}	
		
		//����
		for(int i = 0; i < commonPokes.length; i++){
			if(commonPokes[i] == 2){					
				int[] ss = new int[2];
				int index = 0;
				for(int j = 0; j < allCards.length; j++){
					if(PokerCard.getCardNumber(allCards[j]) == i+3){								
						ss[index] = allCards[j];
						index++;	
					}	
				}	
				pairVector.addElement(ss);
			}
		}	
		
		//����
		for(int i = 0; i < commonPokes.length; i++){
			if(commonPokes[i] == 1){					
				int[] ss = new int[1];
				for(int j = 0; j < allCards.length; j++){
					if(PokerCard.getCardNumber(allCards[j]) == i+3){								
						ss[0] = allCards[j];	
					}	
				}	
				singleVector.addElement(ss);
			}			
		}	
		
		if(countKing == 1){
			for(int i = 0; i < allCards.length; i++){
				if(PokerCard.getCardNumber(allCards[i]) == 53 || PokerCard.getCardNumber(allCards[i]) == 54){								
					int[] ss = new int[1];
					ss[0] = allCards[i];
					singleVector.addElement(ss);
				}
			}
		}
			
		//ը��
		for(int i = 0; i < commonPokes.length; i++){
			if(commonPokes[i] == 4){					
				int[] ss = new int[4];
				int index = 0;
				for(int j = 0; j < allCards.length; j++){
					if(PokerCard.getCardNumber(allCards[j]) == i+3){								
						ss[index] = allCards[j];
						index++;	
					}	
				}	
				bompVector.addElement(ss);
			}
		}
		
		//���
		if(countKing == 2){
			int[] ss = new int[2];
			for(int i = 0; i < allCards.length; i++){
				if(PokerCard.getCardNumber(allCards[i]) == 53){
					ss[0] = allCards[i];								
				}
				
				if(PokerCard.getCardNumber(allCards[i]) == 54){
					ss[1] = allCards[i];
				}
				
				rocketVector.addElement(ss);
			}
		}
				
		//A��2����
		if(count1 > 0){
			int[] ss = new int[count1];	
			int index = 0;
			for(int i = 0; i < allCards.length; i++){				
				if(PokerCard.getCardNumber(allCards[i]) == 14){
					ss[index] = allCards[i];
					index++;
				} 
			}
			
			if(count1 == 4){
				bompVector.addElement(ss);
			}else if(count1 == 3){
				tripleVector.addElement(ss);
			}else if(count1 == 2){
				pairVector.addElement(ss);
			}else if(count1 == 1){
				singleVector.addElement(ss);
			}				
		}
		
		if(count2 > 0){
			int[] ss = new int[count2];	
			int index = 0;
			for(int i = 0; i < allCards.length; i++){				
				if(PokerCard.getCardNumber(allCards[i]) == 15){
					ss[index] = allCards[i];
					index++;
				} 
			}
			
			if(count2 == 4){
				bompVector.addElement(ss);
			}else if(count2 == 3){
				tripleVector.addElement(ss);
			}else if(count2 == 2){
				pairVector.addElement(ss);
			}else if(count2 == 1){
				singleVector.addElement(ss);
			}				
		}
		
	}
	
	public static AllCombinations getInstance() {
		return new AllCombinations();
	}
	
	public void initAllVector(){
		for(int i = 0; i < commonPokes.length; i++){
			commonPokes[i] = 0;
		}
		
		clear(straightTripleVector);
		clear(straightPairVector);
		clear(straightVector);
		clear(bompVector);
		clear(tripleVector);
		clear(pairVector);
		clear(singleVector);
		clear(tripleWithOneVector);
		clear(planeVector);
		clear(rocketVector);
		clear(fourWithTwoVector);
	}
	
	public void clear(Vector temp){
		if(temp.size() != 0){
			temp.removeAllElements();
		}
	}

	public Vector getVectorOfType(int index) {	
		switch(index){
		case 1:
			return singleVector;
		case 2:
			return pairVector;
		case 3:
			return tripleVector;
		case 4:
			return tripleWithOneVector;
		case 5:
			return straightVector;
		case 6:
			return straightPairVector;
		case 7:
			return straightTripleVector;
		case 8:
			return planeVector;
		case 9:
			return fourWithTwoVector;
		case 10:
			return bompVector;
		case 11:
			return rocketVector;
		}
		
		return null;
	}
	
	public Vector getVectorOfSingle() {
		return singleVector;
	}
	
	public Vector getVectorOfPair() {
		return pairVector;
	}

	public Vector getVectorOfTriple() {
		return tripleVector;
	}
	
	public Vector getVectorOfTripleWithOne() {
		return tripleWithOneVector;
	}
	
	public Vector getVectorOfStraight() {
		return straightVector;
	}
	
	public Vector getVectorOfStraightPair() {
		return straightPairVector;
	}
	
	public Vector getVectorOfStraightTriple() {
		return straightTripleVector;
	}
	
	public Vector getVectorOfPlane() {
		return planeVector;
	}
	
	public Vector getVectorOfFourWithTwo() {
		return fourWithTwoVector;
	}
	
	public Vector getVectorOfBomp() {
		return bompVector;
	}
	
	public Vector getVectorOfRocket() {
		return rocketVector;
	}
	
}
