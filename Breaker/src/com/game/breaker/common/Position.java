package com.game.breaker.common;
/**
 * 地图常量
 * @author JoYoNB
 *
 */
public class Position {
	public static final int[][][] p_1_5=
	{
		{
			{114,8},{146,8},{177,8},{209,8},{240,8},{272,8},{303,8},{335,8},{367,8},{398,8},{430,8},{461,8},{493,8}
		},
		{
			{98,37},{130,37},{161,37},{193,37},{225,37},{256,37},{288,37},{319,37},{351,37},{382,37},{414,37},{446,37},{477,37},{509,37}
		},
		{
			{114,65},{146,65},{177,65},{209,65},{240,65},{272,65},{303,65},{335,65},{367,65},{398,65},{430,65},{461,65},{493,65}
		},
		{
			{98,93},{130,93},{161,93},{193,93},{225,93},{256,93},{288,93},{319,93},{351,93},{382,93},{414,93},{446,93},{477,93},{509,93}
		},
		{
			{113,121},{146,121},{177,121},{209,121},{240,121},{272,121},{303,121},{335,121},{367,121},{398,121},{430,121},{461,121},{493,121}
		},
		{
			{98,150},{130,150},{161,150},{193,150},{225,150},{256,150},{288,150},{319,150},{351,150},{382,150},{414,150},{446,150},{477,150},{509,150}
		},
		{
			{114,177},{146,177},{177,177},{209,177},{240,177},{272,177},{303,177},{335,177},{367,177},{398,177},{430,177},{461,177},{493,177}
		},
		{
			{98,206},{130,206},{161,206},{193,206},{225,206},{256,206},{288,206},{319,206},{351,206},{382,206},{414,206},{446,206},{478,206},{510,206}
		},
	};
	public static final int[][][] p_6_10=
	{
		{
			{99,8},{130,8},{162,8},{193,8},{225,8},{256,8},{288,8},{319,8},{351,8},{383,8},{414,8},{446,8},{477,8},{509,8}
		},
		{
			{114,37},{146,37},{177,37},{209,37},{241,37},{272,37},{304,37},{335,37},{367,37},{398,37},{430,37},{462,37},{493,37}
		},
		{
			{99,65},{130,65},{162,65},{193,65},{225,65},{256,65},{288,65},{319,65},{351,65},{383,65},{414,65},{446,65},{477,65},{509,65}
		},
		{
			{114,93},{146,93},{177,93},{209,93},{241,93},{272,93},{304,93},{335,93},{367,93},{398,93},{430,93},{462,93},{493,93}
		},
		{
			{97,121},{129,121},{162,121},{193,121},{225,121},{256,121},{288,121},{319,121},{351,121},{383,121},{414,121},{446,121},{477,121},{509,121}
		},
		{
			{114,150},{146,150},{177,150},{209,150},{241,150},{272,150},{304,150},{335,150},{367,150},{398,150},{430,150},{462,150},{493,150}
		},
		{
			{99,177},{130,177},{162,177},{193,177},{225,177},{256,177},{288,177},{319,177},{351,177},{383,177},{414,177},{446,177},{477,177},{509,177}
		},
		{
			{82,206},{114,206},{146,206},{177,206},{209,206},{241,206},{272,206},{304,206},{335,206},{367,206},{398,206},{430,206},{462,206},{494,206}
		},
	};
	public static int getPosition(int line,int column,int level){
		if(level==1){
			if(line==0||line==1||line==2||line==3||line==6){
				if(column==1||column==6||column==11){
					return 1;
				}
				if(column==2||column==7){
					return 2;
				}
				if(column==3||column==8){
					return 3;
				}
				if(column==5||column==10){
					return 4;
				}
				if(column==4||column==9){
					return 5;
				}
			}
		}else if(level==2){
			if(line==0||line==1){
				if(column==1||column==5){
					return 4;
				}
				if(column==2||column==6||column==9){
					return 1;
				}
				if(column==3||column==7||column==10){
					return 2;
				}
				if(column==4||column==8||column==11){
					return 3;
				}
			}
			if(line==3){
				if(column==1||column==5||column==12){
					return 4;
				}
				if(column==2||column==6||column==9){
					return 1;
				}
				if(column==3||column==7||column==10){
					return 2;
				}
				if(column==4||column==8||column==11){
					return 3;
				}
			}
			if(line==4){
				if(column==0||column==4||column==11){
					return 4;
				}
				if(column==1||column==5||column==8||column==12){
					return 1;
				}
				if(column==2||column==6||column==9){
					return 2;
				}
				if(column==3||column==7||column==10){
					return 3;
				}
			}
			if(line==4||line==6){
				if(column==0||column==4||column==11){
					return 4;
				}
				if(column==1||column==5||column==8||column==12){
					return 1;
				}
				if(column==2||column==6||column==9){
					return 2;
				}
				if(column==3||column==7||column==10){
					return 3;
				}
			}
			if(line==7){
				if(column==0||column==4||column==11){
					return 4;
				}
				if(column==1||column==5||column==8||column==12){
					return 1;
				}
				if(column==2||column==6||column==9||column==13){
					return 2;
				}
				if(column==3||column==7||column==10){
					return 3;
				}
			}
		}else if(level==3){//level3
			if(line==1||line==7){
				if(column==2||column==6||column==9){
					return 1;
				}
				if(column==3||column==7||column==10){
					return 2;
				}
				if(column==4||column==8||column==11){
					return 3;
				}
				if(column==5){
					return 4;
				}
			}
			if(line==2||line==6){
				if(column==1||column==5){
					return 4;
				}
				if(column==2||column==6||column==9){
					return 1;
				}
				if(column==3||column==7||column==10){
					return 2;
				}
				if(column==4||column==8||column==11){
					return 3;
				}
			}
			if(line==3||line==5){
				if(column==1||column==5||column==9||column==12){
					return 3;
				}
				if(column==2||column==6){
					return 4;
				}
				if(column==3||column==7||column==10){
					return 1;
				}
				if(column==4||column==8||column==11){
					return 2;
				}
			}
			if(line==4){
				if(column==0||column==4||column==8||column==11){
					return 2;
				}
				if(column==1||column==5||column==9||column==12){
					return 3;
				}
				if(column==2||column==6){
					return 4;
				}
				if(column==3||column==7||column==10){
					return 1;
				}
			}
		}else if(level==4){//level4
			if(line==0){
				if(column==1||column==5||column==12){
					return 4;
				}
				if(column==2||column==6||column==9){
					return 1;
				}
				if(column==3||column==7||column==10){
					return 2;
				}
				if(column==4||column==8||column==11){
					return 3;
				}
			}
			if(line==1){
				if(column==1||column==5||column==12){
					return 4;
				}
				if(column==2||column==6||column==9||column==13){
					return 1;
				}
				if(column==3||column==7||column==10){
					return 2;
				}
				if(column==4||column==8||column==11){
					return 3;
				}
			}
			if(line==2){
				if(column==5){
					return 4;
				}
				if(column==6||column==9){
					return 1;
				}
				if(column==7){
					return 2;
				}
				if(column==4||column==8){
					return 3;
				}
			}
			if(line==3){
				if(column==1||column==5||column==12){
					return 3;
				}
				if(column==2||column==6||column==9){
					return 4;
				}
				if(column==3||column==7||column==10){
					return 1;
				}
				if(column==4||column==8||column==11){
					return 2;
				}
			}
			if(line==4){
				if(column==1||column==5||column==12){
					return 3;
				}
				if(column==2||column==6||column==9){
					return 4;
				}
				if(column==3||column==7||column==10){
					return 1;
				}
				if(column==4||column==8||column==11){
					return 2;
				}
			}
			if(line==5){
				if(column==12){
					return 3;
				}
				if(column==2||column==6){
					return 4;
				}
				if(column==3||column==7||column==10){
					return 1;
				}
				if(column==4||column==8||column==11){
					return 2;
				}
			}
			if(line==6){
				if(column==5){
					return 4;
				}
				if(column==2||column==6){
					return 1;
				}
				if(column==3||column==7||column==10){
					return 2;
				}
				if(column==8||column==11){
					return 3;
				}
			}
			if(line==7){
				if(column==5){
					return 4;
				}
				if(column==6||column==9){
					return 1;
				}
				if(column==3||column==7){
					return 2;
				}
				if(column==8||column==11){
					return 3;
				}
			}
		}else if(level==5){
			if(line==1||line==7){
				if(column==2||column==11){
					return 4;
				}
				if(column==3||column==10){
					return 1;
				}
				if(column==6||column==7){
					return 2;
				}
				if(column==5||column==8){
					return 3;
				}
			}
			if(line==2||line==6){
				if(column==1||column==6||column==11){
					return 4;
				}
				if(column==2||column==10){
					return 1;
				}
				if(column==5||column==7){
					return 2;
				}
				if(column==4||column==8){
					return 3;
				}
			}
			if(line==3||line==5){
				if(column==1||column==6||column==7||column==12){
					return 4;
				}
				if(column==2||column==11){
					return 1;
				}
				if(column==5||column==8){
					return 2;
				}
				if(column==4||column==9){
					return 3;
				}
			}
			if(line==4){
				if(column==0||column==5||column==7||column==12){
					return 4;
				}
				if(column==1||column==6||column==11){
					return 1;
				}
				if(column==4||column==8){
					return 2;
				}
				if(column==3||column==9){
					return 3;
				}
			}
		}
		if(level==6){
			if(line==0){
				if(column==0||column==4||column==8||column==11){
					return 2;
				}
				if(column==1||column==5||column==9||column==12){
					return 3;
				}
				if(column==2||column==6||column==10||column==13){
					return 4;
				}
				if(column==3||column==7||column==11){
					return 1;
				}
			}
			if(line==1){
				if(column==0||column==4||column==8||column==11){
					return 3;
				}
				if(column==1||column==5||column==9||column==12){
					return 4;
				}
				if(column==2||column==6||column==10){
					return 1;
				}
				if(column==3||column==7||column==11){
					return 2;
				}
			}
			if(line==2){
				if(column==4||column==11){
					return 2;
				}
				if(column==1||column==9||column==12){
					return 3;
				}
				if(column==2||column==6||column==10){
					return 4;
				}
				if(column==3||column==7||column==11){
					return 1;
				}
			}
			if(line==3){
				if(column==1||column==5||column==9){
					return 3;
				}
				if(column==2||column==6){
					return 4;
				}
				if(column==3||column==7||column==10){
					return 1;
				}
				if(column==11){
					return 2;
				}
			}
			if(line==4){
				if(column==2||column==6||column==10){
					return 3;
				}
				if(column==3||column==7){
					return 4;
				}
				if(column==8||column==11){
					return 1;
				}
				if(column==5){
					return 2;
				}
			}
			if(line==5){
				if(column==2||column==6){
					return 4;
				}
				if(column==7||column==10){
					return 1;
				}
				if(column==4||column==8){
					return 2;
				}
				if(column==5){
					return 3;
				}
			}
			if(line==6){
				if(column==4||column==8){
					return 2;
				}
				if(column==5||column==9){
					return 3;
				}
				if(column==6){
					return 4;
				}
				if(column==7){
					return 1;
				}
			}
			if(line==7){
				if(column==4||column==8){
					return 2;
				}
				if(column==5||column==9){
					return 3;
				}
				if(column==6){
					return 4;
				}
				if(column==7||column==10){
					return 1;
				}
			}
		}
		if(level==7){
			if(line==0){
				if(column==0||column==13){
					return 1;
				}
				if(column==1||column==5||column==8||column==12){
					return 3;
				}
				if(column==2||column==6||column==7||column==11){
					return 2;
				}
				if(column==3||column==10){
					return 4;
				}
			}
			if(line==1){
				if(column==0||column==12){
					return 3;
				}
				if(column==1||column==5||column==7||column==11){
					return 2;
				}
				if(column==2||column==6||column==10){
					return 4;
				}
				if(column==3||column==9){
					return 1;
				}
			}
			if(line==2){
				if(column==1||column==12){
					return 2;
				}
				if(column==2||column==6||column==7||column==11){
					return 4;
				}
				if(column==3||column==10){
					return 1;
				}
				if(column==4||column==9){
					return 3;
				}
			}
			if(line==3){
				if(column==1||column==11){
					return 4;
				}
				if(column==2||column==6||column==10){
					return 1;
				}
				if(column==3||column==9){
					return 3;
				}
				if(column==4||column==8){
					return 2;
				}
			}
			if(line==4){
				if(column==2||column==11){
					return 1;
				}
				if(column==3||column==10){
					return 3;
				}
				if(column==4||column==9){
					return 2;
				}
				if(column==5||column==8){
					return 4;
				}
			}
			if(line==5){
				if(column==2||column==6||column==10){
					return 3;
				}
				if(column==3||column==9){
					return 2;
				}
				if(column==4||column==8){
					return 4;
				}
				if(column==5||column==7){
					return 1;
				}
			}
			if(line==6){
				if(column==3||column==10){
					return 2;
				}
				if(column==4||column==9){
					return 4;
				}
				if(column==5||column==8){
					return 1;
				}
				if(column==6||column==7){
					return 3;
				}
			}
			if(line==7){
				if(column==4||column==10){
					return 4;
				}
				if(column==5||column==9){
					return 1;
				}
				if(column==6||column==8){
					return 3;
				}
				if(column==7){
					return 2;
				}
			}
		}
		if(level==8){
			if(line==0){
				if(column==0||column==4||column==9||column==13){
					return 1;
				}
				if(column==1||column==5||column==8||column==12){
					return 3;
				}
				if(column==2||column==6||column==7||column==11){
					return 2;
				}
				if(column==3||column==10){
					return 4;
				}
			}
			if(line==1){
				if(column==0||column==12){
					return 3;
				}
				if(column==1||column==5||column==7||column==11){
					return 2;
				}
				if(column==6){
					return 4;
				}
			}
			if(line==2){
				if(column==0||column==13){
					return 3;
				}
				if(column==1||column==5||column==8||column==12){
					return 2;
				}
				if(column==2||column==6||column==7||column==11){
					return 4;
				}
			}
			if(line==3){
				if(column==0||column==12){
					return 2;
				}
				if(column==1||column==5||column==7||column==11){
					return 4;
				}
				if(column==6){
					return 1;
				}
			}
			if(line==4){
				if(column==0||column==4||column==9||column==13){
					return 2;
				}
				if(column==1||column==5||column==8||column==12){
					return 4;
				}
				if(column==2||column==6||column==7||column==11){
					return 1;
				}
				if(column==3||column==10){
					return 3;
				}
			}
			if(line==5){
				if(column==0||column==4||column==8||column==12){
					return 4;
				}
				if(column==1||column==5||column==7||column==11){
					return 1;
				}
				if(column==2||column==10){
					return 3;
				}
				if(column==3||column==9){
					return 2;
				}
			}
			if(line==6){
				if(column==0||column==4||column==9||column==13){
					return 4;
				}
				if(column==1||column==5||column==8||column==12){
					return 1;
				}
				if(column==2||column==11){
					return 3;
				}
				if(column==3||column==10){
					return 2;
				}
			}
			if(line==7){
				if(column==1||column==5||column==9||column==13){
					return 1;
				}
				if(column==2||column==6||column==8||column==12){
					return 3;
				}
				if(column==3||column==7||column==11){
					return 2;
				}
				if(column==4||column==8){
					return 4;
				}
			}
		}
		if(level==9){
			if(line==0){
				if(column==1||column==7||column==13){
					return 3;
				}
				if(column==3||column==9){
					return 4;
				}
				if(column==4||column==10){
					return 1;
				}
				if(column==6||column==12){
					return 2;
				}
			}
			if(line==1||line==2){
				if(column==0||column==6||column==12){
					return 3;
				}
				if(column==2||column==8){
					return 4;
				}
				if(column==3||column==9){
					return 1;
				}
				if(column==5||column==11){
					return 2;
				}
			}
			if(line==3||line==4){
				if(column==5||column==11){
					return 3;
				}
				if(column==1||column==7){
					return 4;
				}
				if(column==2||column==8){
					return 1;
				}
				if(column==4||column==10){
					return 2;
				}
			}
			if(line==5||line==6||line==7){
				if(column==4||column==10){
					return 3;
				}
				if(column==0||column==6||column==12){
					return 4;
				}
				if(column==1||column==7){
					return 1;
				}
				if(column==3||column==9){
					return 2;
				}
			}
		}
		if(level==10){
			if(line==1||line==3||line==5){
				if(column==1||column==7){
					return 3;
				}
				if(column==2||column==8){
					return 4;
				}
				if(column==4||column==10){
					return 1;
				}
				if(column==5||column==11){
					return 2;
				}
			}
			if(line==2||line==4||line==6){
				if(column==1||column==7){
					return 3;
				}
				if(column==3||column==9){
					return 4;
				}
				if(column==4||column==10){
					return 1;
				}
				if(column==5||column==12){
					return 2;
				}
			}
			if(line==7){
				if(column==2||column==8){
					return 3;
				}
				if(column==3||column==7){
					return 4;
				}
				if(column==5||column==11){
					return 1;
				}
				if(column==6||column==12){
					return 2;
				}
			}
		}
		return 0;
	}
		
}






