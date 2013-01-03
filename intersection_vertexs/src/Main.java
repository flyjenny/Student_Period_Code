import java.io.*;


public class Main {
	static int h_num,v_num,total,num;
	public static void main(String[] agrs) throws IOException{
		int[][] result;
		Intersection itt=new Intersection(input(),h_num,v_num,total);
		System.out.println("Find the intersection");
		result=itt.run();
		System.out.println("End of Find\nOutput to file\"result4.txt\"");
		PrintWriter out=new PrintWriter("result4.txt");
		out.println(result[0][0]);
		/*
		for (int i=1;i<result[0][0]+1;i++){
			out.println(result[i][0]+" "+result[i][1]);
		}
		*/
		out.close();
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
				new FileReader("intersection2.in"));
		num=Integer.parseInt(in.readLine());
		
		String str;
		int k=0;
		int x1,x2,y1,y2;
		line[] hline = new line[10000];
		line[] vline = new line[10000];
		v_num = 0;
		h_num = 0;
		
		line t=new line();
		
		for (int i=0;i<num;i++) {
			str=in.readLine();
			int[] strlist=broke(str);
			x1=strlist[0];
			x2=strlist[2];
			y1=strlist[1];
			y2=strlist[3];
			if (x1==x2) {
				t = new line();
				t.set(x1, x2, y1, y2, i);
				vline[v_num] = t;
				v_num++;
			}
			else if (y1==y2) {
				t = new line();
				t.set(x1, x2, y1, y2, i);
				hline[h_num] = t;
				h_num++;
			}
		}
		total=h_num*2+v_num;
		lines= new line[total];
		
		for (int i=0;i<h_num;i++){
			x1=hline[i].x1;
			x2=hline[i].x2;
			y1=hline[i].y1;
			y2=hline[i].y2;
			//System.out.println(x1+" "+x2+" "+y1);
			t=new line();
			t.set(x1, x2, y1, y2, i);
			lines[k++]=t;
			t=new line();
			t.set(x2, x1, y1, y2, i);
			lines[k++]=t;
		}
		for (int i=0;i<v_num;i++){
			y1=vline[i].y1;
			y2=vline[i].y2;
			x1=vline[i].x1;
			x2=vline[i].x2;
			//System.out.println(y1+" "+y2+" "+x1);
			t=new line();
			t.set(x1, x2, y1, y2, h_num+i);
			lines[k++]=t;
		}
		System.out.println("End of Input...\nQuick sort");
		qsort.quicksort(lines, 0, total-1);
		System.out.println("End of Quick sort\nThe -x- of all lines");
		for (int i=0;i<total;i++){
			System.out.print(lines[i].x1+"  ");
		}
		System.out.println();
		return lines;
	}
}
