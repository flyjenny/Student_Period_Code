package Intersection;

import java.util.ArrayList;

public class line {
	public double x1,y1,x2,y2;
	public ArrayList<String> candidate = new ArrayList<String>();
	public line(int a,int b,int c,int d){
		x1=a;y1=b;x2=c;y2=d;
	}
	public double getCurrent_y(double a){
		if((x2-x1)!=0){
			double k = (y2-y1)/(x2-x1);//Ïß¶ÎĞ±ÂÊ
			return (double)y1+(a-x1)*k;
		}else
			return y2;
		
	}

}
