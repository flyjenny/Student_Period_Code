package ex5_2;

import java.io.*;
import java.util.ArrayList;


public class Main {
	static int h_num,v_num,total,num;
	
	public static void main(String[] agrs) throws IOException{
//		int[][] result;
		long beginTime =System.currentTimeMillis();
		
		int intersectionnum=0;
		Intersection itt=new Intersection(input(),h_num,v_num,total);
		System.out.println("Find the intersection");
//		result=itt.run();
		System.out.print("The intersect number is: ");
		System.out.println(intersectionnum=itt.findintersection());
		PrintWriter out=new PrintWriter("result5_3.dat");
		out.println(intersectionnum);
		/*
		for (int i=1;i<result[0][0]+1;i++){
			out.println(result[i][0]+" "+result[i][1]);
		}
		*/
		out.close();
		long endTime =System.currentTimeMillis();
		System.out.println("RunningTime:"+ (endTime - beginTime) + "ms");
		System.out.println("End");
		
	}
	public static int[] broke(String str){
		int[] t=new int[4];
		String[] strlist=str.split(" ");
		for (int i=0;i<4;i++){
			t[i]=Integer.valueOf(strlist[i]);
		}
		return t;
	}
	public static line[] input() throws IOException{
		line[] lines;
		System.out.println("Inputing...");
		BufferedReader in=new BufferedReader(
				new FileReader("input5_3.dat"));
		num=Integer.parseInt(in.readLine());
		
		String str;
		int k=0;
		int x1,x2,y1,y2;
		ArrayList<line> hline=new ArrayList<line>();
		ArrayList<line> vline=new ArrayList<line>();
	//	line[] hline = new line[10000];
	//	line[] vline = new line[10000];
		v_num = 0;
		h_num = 0;
		
		
		str=in.readLine();
		System.out.println(str);
		String[] tem=str.split(" ");
		for (int i=0;i<num*4;i+=4) {
			x1=Integer.parseInt(tem[i]);
			x2=Integer.parseInt(tem[i+2]);
			y1=Integer.parseInt(tem[i+1]);
			y2=Integer.parseInt(tem[i+3]);
			
			
			if (x1==x2) {
				line t=new line(x1, x2, y1, y2, 1);
	//			t.set(x1, x2, y1, y2, i);
		//		t.set(x1, x2, y1, y2, 1);
	//			vline[v_num] = t;
				
				vline.add(t);
				v_num++;
			}
			if (y1==y2) {
				line t = new line(x1, x2, y1, y2, 0);
//				t.set(x1, x2, y1, y2, i);
		//		t.set(x1, x2, y1, y2, 0);
				hline.add(t);
				h_num++;
			}
			
		}
		System.out.println(v_num);
		System.out.println(h_num);
		
		total=h_num*2+v_num;
		lines= new line[total];
		System.out.println("hline");
		for(int i=0;i<h_num;i++){
			System.out.print(hline.get(i).x1+" ");
			System.out.print(hline.get(i).y1+" ");
			System.out.print(hline.get(i).x2+" ");
			System.out.print(hline.get(i).y2+" ");
			System.out.print(hline.get(i).num+" ");
			System.out.println();
		}
		System.out.println("vline");
		
		for(int i=0;i<v_num;i++){
			System.out.print(vline.get(i).x1+" ");
			System.out.print(vline.get(i).y1+" ");
			System.out.print(vline.get(i).x2+" ");
			System.out.print(vline.get(i).y2+" ");
			System.out.print(vline.get(i).num+" ");
			System.out.println();
		}
		
		for (int i=0;i<h_num;i++){
/*			x1=hline.[i].x1;
			x2=hline.[i].x2;
			y1=hline[i].y1;
			y2=hline[i].y2;
	*/		//System.out.println(x1+" "+x2+" "+y1);
			x1=hline.get(i).x1;
			x2=hline.get(i).x2;
			y1=hline.get(i).y1;
			y2=hline.get(i).y2;
			line t=new line(x1, x2, y1, y2, i);
	//		t.set(x1, x2, y1, y2, i);
	//		t.set(x1, x2, y1, y2, 0);
			lines[k++]=t;
			t=new line(x2, x1, y1, y2, i);
	//		t.set(x2, x1, y1, y2, i);
	//		t.set(x2, x1, y1, y2, 0);
			lines[k++]=t;
		}
		
		for (int i=0;i<v_num;i++){
/*			y1=vline[i].y1;
			y2=vline[i].y2;
			x1=vline[i].x1;
			x2=vline[i].x2;
*/			//System.out.println(y1+" "+y2+" "+x1);
			x1=vline.get(i).x1;
			x2=vline.get(i).x2;
			y1=vline.get(i).y1;
			y2=vline.get(i).y2;

			line t=new line(x1, x2, y1, y2, h_num+i);
	//		t.set(x1, x2, y1, y2, h_num+i);
	//		t.set(x1, x2, y1, y2, 1);
			lines[k++]=t;
		}
/*		System.out.println();
		for(int i=0;i<total;i++){
			System.out.println(lines[i].x1+" "+lines[i].y1+" "+lines[i].x2+" "+lines[i].y2+"    "+lines[i].form);
		}
		System.out.println("End of Input...\nQuick sort");
		qsort.quicksort(lines, 0, total-1);
		for(int i=0;i<total;i++){
			System.out.println(lines[i].x1+" "+lines[i].y1+" "+lines[i].x2+" "+lines[i].y2+"    "+lines[i].form);
		}
		
*/		qsort.quicksort(lines, 0, total-1);
		System.out.println("Afger:");
		for(int i=0;i<total;i++){
			System.out.println(lines[i].x1+" "+lines[i].y1+" "+lines[i].x2+" "+lines[i].y2+"    "+lines[i].num);
		}
		System.out.println("End of Quick sort\nThe -x- of all lines");
		for (int i=0;i<total;i++){
			System.out.print(lines[i].x1+"  ");
		}
		System.out.println();
		return lines;
	}
}
