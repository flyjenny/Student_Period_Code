package ex5_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


public class Intersection {
	private int h_num,v_num,total,inter_num=0;
	private line[] lines;
	private static HashMap<Integer,line> candidate=new HashMap<Integer,line>();
	private int[][] result;
	public Intersection(line[] l,int h,int v,int t){
		lines=l;
		h_num=h;
		v_num=v;
		total=t;
	}
	
	public int findintersection(){
		int i=0;
		while((i<lines.length)&&(lines[i].x1==lines[i].x2)){	//开始扫描，碰到第一跟水平线之前的竖直线都忽略
			i++;
		}
		int j=i;
		while(j<lines.length){
/*			if((j+1<lines.length && lines[j+1].x1==lines[j].x1)||(j==lines.length-1)){
				int k=j;
				while((k+1<lines.length && lines[k+1].x1==lines[k].x1)||(k==lines.length-1)){
					if(lines[k].x1<lines[k].x2){	//碰到的是水平线的左端点
	//					System.out.println("in inwhile hl");
		//				System.out.println(lines[k].x1+" "+lines[k].y1+" "+lines[k].x2+" "+lines[k].y2+"    "+lines[k].num);
						candidate.put(lines[k].num, lines[k]);
					}
					k++;
				}
				k=j;
				while((k+1<lines.length && lines[k+1].x1==lines[k].x1)||(k==lines.length-1)){
					if(lines[k].x1==lines[k].x2){	//碰到竖直线
		//				System.out.println("in inwhile v");
	//					System.out.println(lines[k].x1+" "+lines[k].y1+" "+lines[k].x2+" "+lines[k].y2+"    "+lines[k].num);
	//					System.out.println("candidate.isEmpty():"+candidate.isEmpty());
						if(!candidate.isEmpty()){
		//					System.out.println("candidate.size(): "+candidate.size());
							inter_num+=findlines(lines[k].y1,lines[k].y2).size();
						}
					}
					k++;
				}
				k=j;
				while((k+1<lines.length && lines[k+1].x1==lines[k].x1)||(k==lines.length-1)){
					if(lines[k].x1>lines[k].x2){	//碰到的是水平线的右端点
	//					System.out.println("in inwhile hr");
	//					System.out.println(lines[k].x1+" "+lines[k].y1+" "+lines[k].x2+" "+lines[k].y2+"    "+lines[k].num);
						candidate.remove(lines[k].num);
					}
					k++;
				}
					j+=k;
			}
			else{   
*/			//	System.out.println("you are in");
				
				if(lines[j].x1<lines[j].x2){	//碰到的是水平线的左端点
	//				System.out.println("in outwhile hl");
					candidate.put(lines[j].num, lines[j]);
				}
				if(lines[j].x1==lines[j].x2){	//碰到竖直线
	//				System.out.println("in outwhile v");
					if(!candidate.isEmpty()){
		//				System.out.println("candidate.size(): "+candidate.size());
						inter_num+=findlines(lines[j].y1,lines[j].y2).size();
					}
				}
				if(lines[j].x1>lines[j].x2){	//碰到的是水平线的右端点
	//				System.out.println("in outwhile hr");
					candidate.remove(lines[j].num);
				}
	//			System.out.println(lines[j].x1+" "+lines[j].y1+" "+lines[j].x2+" "+lines[j].y2+"    "+lines[j].num);
				j++;
					
		}
		return inter_num;
	}
	
	public ArrayList<line> findlines(int y1,int y2){
		int high=0;
		int low=0;
		if(y1>=y2){
			high=y1;
			low=y2;
		}
		else{
			high=y2;
			low=y1;
		}
		ArrayList<line> betweentline=new ArrayList<line>();
	
		for(Iterator<Integer> it=candidate.keySet().iterator();it.hasNext();){
			int key=it.next();
			int current_y=candidate.get(key).y1;
			if(current_y>=low && current_y<=high ){
				betweentline.add(candidate.get(key));
			}
		}
		return betweentline;
		
		
	}
	
	public int[][] run(){
		int[] h_line=new int[h_num];
		int hn=0,i;
		int k=0,amount,h_head=0;
		result=new int[h_num*v_num+1][2];
		while (k<total){
			amount=1;
			while (k+amount<total&&lines[k].x1==lines[k+amount].x1) amount++;
			int count=amount;
			for (int j=0;j<amount && count>0;j++)
				if (lines[k+j].x1<lines[k+j].x2){
					h_line[hn++]=k+j;
					count--;
				}
			for (int j=0;j<amount && count>0;j++)
				if (lines[k+j].x1==lines[k+j].x2) {
					for (i=h_head;i<hn;i++)
						if (h_line[i]!=-1){
							int y=lines[h_line[i]].y1;
							if ((lines[k+j].y1<=y) && (y<=lines[k+j].y2)){
								inter_num++;
								result[inter_num][0]=lines[k+j].x1;
								result[inter_num][1]=y;
								//System.out.println(inter_num+": "+lines[k+j].x1+"\t"+y);
							}
						}
					count--;
				}
			for (int j=0;j<amount && count>0;j++)
				if (lines[k+j].x1>lines[k+j].x2){
					for (i=h_head;i<hn;i++)
						if (h_line[i]!=-1 && lines[h_line[i]].num==lines[k+j].num) break;
					h_line[i]=-1;
					while (h_head<h_num&&h_line[h_head]==-1) h_head++;
					count--;
				}
			k+=amount;
			/*
			if (lines[k].x1>lines[k].x2){
				for (i=0;i<hn;i++)
					if (h_line[i]!=-1 && lines[h_line[i]].form==lines[k].form) break;
				h_line[i]=-1;
			}else if (lines[k].x1<lines[k].x2){
				h_line[hn++]=k;
			}else if (lines[k].x1==lines[k].x2){
				for (i=0;i<hn;i++){
					if (h_line[i]!=-1){
						int y=lines[h_line[i]].y1;
						if ((lines[k].y1<=y) && (y<=lines[k].y2)){
							System.out.println(lines[k].x1+" "+y);
						}
					}
				}
			}
			*/
			//k++;
		}
		result[0][0]=inter_num;
		return result;
	}
	
}
