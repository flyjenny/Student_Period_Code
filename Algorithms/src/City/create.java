package City;

import java.io.*;


public class create {
	private building[] bu;
	private int total;
	private int[] result;
	
	public create(building[] b,int t) throws IOException{
		bu=b;
		total=t;
		result=new int[total*10];
	}
	
	public int[] run(){
		result=draw(total,0,total-1);
		return result;
	}
	
	private int[] draw(int num,int l,int r){
		int[] a=new int[total*10];
		int[] b=new int[total*10];
		int[] c=new int[total*10];
		if (num==1){
			c[0]=bu[l].getleft();
			c[1]=bu[l].gethigh();
			c[2]=bu[l].getright();
			c[3]=0;
		}
		else {
			int mid=(l+r)/2;
			a=draw(mid-l+1,l,mid);
			b=draw(r-mid,mid+1,r);
			c=merge(a,b);
		}
		return c;
	}
	private int[] merge(int[] a,int[] b){
		int[] c=new int[total*10];
		int[] bu1=new int[4];
		int[] bu2=new int[4];
		int x,high;int cp;
		boolean end=false;
		
		for (int i=0;i<=2;i++){
			bu1[i]=a[i];
			bu2[i]=b[i];
		}
		bu1[3]=0;
		bu2[3]=0;
		if (a[0]<b[0]){
			x=a[0];
			high=a[1];
		}
		else {
			x=b[0];
			high=b[1];
		}
		c[0]=x;
		c[1]=high;
		cp=0;
		while (!end){
			x+=1;
			if (x==bu1[2]){
				bu1[3]++;
				bu1[0]=a[(int)bu1[3]*2];
				bu1[1]=a[(int)bu1[3]*2+1];
				bu1[2]=a[(int)bu1[3]*2+2];
				
			}
			if (x==bu2[2]){
				bu2[3]++;
				bu2[0]=b[(int)bu2[3]*2];
				bu2[1]=b[(int)bu2[3]*2+1];
				bu2[2]=b[(int)bu2[3]*2+2];
			}
			if (bu1[2]==0&&bu2[2]==0) end=true;
			boolean t1=x>=bu1[0] && x<=bu1[2];
			boolean t2=x>=bu2[0] && x<=bu2[2];
			if (t1 && t2){
				if (bu1[1]>bu2[1]){
					if (high!=bu1[1]){
						cp++;
						c[cp*2]=bu1[0];
						c[cp*2+1]=bu1[1];
						high=bu1[1];
					}
				}
				else{
					if (high!=bu2[1]){
						cp++;
						c[cp*2]=bu2[0];
						c[cp*2+1]=bu2[1];
						high=bu2[1];
					}
				}
			}
			else if (t1){
				if (high!=bu1[1]){
					cp++;
					c[cp*2]=x;
					c[cp*2+1]=bu1[1];
					high=bu1[1];
				}
			}
			else if(t2){
				if (high!=bu2[1]){
					cp++;
					c[cp*2]=x;
					c[cp*2+1]=bu2[1];
					high=bu2[1];
				}
			}
			else {
				if (high!=0){
					cp++;
					c[cp*2]=x;
					c[cp*2+1]=0;
					high=0;
				}
			}
		}
		return c;
	}

}
