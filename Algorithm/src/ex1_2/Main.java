package ex1_2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Main {
	/*建立空白链表，分别存放输入和输出*/
	ArrayList<Integer> arry= new ArrayList<Integer>();	
	ArrayList<Integer> outline= new ArrayList<Integer>();	
	public static void main(String argv[]) throws IOException{
		
		/*设定程序计时开始位*/
		long beginTime =System.currentTimeMillis();
		
		Main O=new Main();
		O.readFile("input1_3.dat");
		
		/*调用求取建筑物的轮廓线的函数*/
		O.outline=O.merge(O.arry,0,O.arry.size()-1);
		O.outline=O.arrange();
		O.writeFile("result1_3.dat");
		

		/*设定程序结束时间标志，并计算运行时间*/
		long endTime =System.currentTimeMillis();
		System.out.println("RunningTime:"+ (endTime - beginTime) + "ms");
	}
	
	public ArrayList<Integer> arrange(){
		ArrayList<Integer> tmp=new ArrayList<Integer>();
		ArrayList<Integer> tmp1=new ArrayList<Integer>();
		outline.add(0);
		tmp.add(outline.get(0));
		//System.out.println(outline);
		for(int i=1;i<outline.size()-1;i+=2){
			int m=outline.get(i);
			int n=outline.get(i+2);
			//System.out.print(i);
			//System.out.print(": ");
			//System.out.print(m);
			//System.out.print("  ");
			//System.out.println(n);
			if(m==n){
				//System.out.println("win");
			}
			else{
				tmp.add(outline.get(i));
				tmp.add(outline.get(i+1));
			}
		}
		outline=tmp;
		int i=0;
		for(;i<outline.size()-2;i+=2){
			int m=outline.get(i);
			int n=outline.get(i+2);
			//System.out.print(i);
			//System.out.print(": ");
			//System.out.print(m);
			//System.out.print("  ");
			//System.out.println(n);
			if(m==n){
				if(outline.get(i+1)>outline.get(i+3)){
					tmp1.add(outline.get(i));
					tmp1.add(outline.get(i+1));
					i+=2;
				}
				else{
					i+=2;
					tmp1.add(outline.get(i));
					tmp1.add(outline.get(i+1));	
				}
			}
			else{
				tmp1.add(outline.get(i));
				tmp1.add(outline.get(i+1));
			}
		}
		tmp1.add(outline.get(i));
		return outline=tmp1;
	}
	
	public void readFile(String filename) throws IOException{
	    BufferedReader reader =null;
	    reader = new BufferedReader(new FileReader(filename));

		/*读取文件，并保存在arry中*/
		DataInputStream in=new DataInputStream(new FileInputStream(filename));
		String s=reader.readLine();//in.readLine();
		s=reader.readLine();//s=in.readLine();
		
		while(s!=null){
			StringTokenizer t = new StringTokenizer(s," ");
            arry.add(Integer.valueOf(t.nextToken()));
            arry.add(Integer.valueOf(t.nextToken()));
            arry.add(Integer.valueOf(t.nextToken()));
			s=reader.readLine();//s=in.readLine();
		}
		in.close();
	}
	
	public void writeFile(String filename) throws IOException{
		/*按照要求对结果输出显示，并输出到文件*/
		System.out.println(outline);
		StringBuffer outBuffer = new StringBuffer();
		for (int i=0;i<outline.size();i++){
			outBuffer.append(String.valueOf(outline.get(i))+"\r\n");
		}
		File file = new File(filename);
		FileWriter fileWriter;
		fileWriter = new FileWriter(file);
		BufferedWriter bufferWrt = new BufferedWriter(fileWriter);
		bufferWrt.write(outBuffer.toString());
		bufferWrt.flush();
		bufferWrt.close();
	}
	
	public ArrayList<Integer> merge(ArrayList<Integer> arry,int l ,int h){
		int low=l;
		int high=h;
		int mid=0;
		if((high-low)==2){
			ArrayList<Integer> arryTmp=new ArrayList<Integer>();
			arryTmp.add(arry.get(low));
			arryTmp.add(arry.get(low+1));
			arryTmp.add(arry.get(low+2));
			return arryTmp;
			}
		if((high-low+1)%2==0){mid=(low+high)/2;}
		else{mid=(high-3+low)/2;}	
		return combineoutline(merge(arry,low,mid),merge(arry,mid+1,high));
		}
	
	/*两个建筑物的轮廓合并为一个轮廓的函数
	 * 需分类计算各种可能的情况*/
	public ArrayList<Integer> combineoutline(ArrayList<Integer>c1,ArrayList<Integer>c2){
		ArrayList<Integer> result=new ArrayList<Integer>();
		int i=0;int j=0;
		while(i<c1.size()&&j<c2.size()){
			if(c1.get(i)<c2.get(j)&&(i!=c1.size()-1)){		
				if((j==0)||(c1.get(i+1)>c2.get(j-1))){
					result.add(c1.get(i));
					result.add(c1.get(i+1));
					i=i+2;
				}
				else {
					result.add(c1.get(i));
					result.add(c2.get(j-1));
					i=i+2;	
					}
			}
			else if((c2.get(j)<c1.get(i))&&(j!=c2.size()-1)) {
				if((i==0)||(c2.get(j+1)>c1.get(i-1))){		
					result.add(c2.get(j));
					result.add(c2.get(j+1));
					j+=2;
				}
				else{
					result.add(c2.get(j));
					j+=2;
					result.add(c1.get(i-1));
				}
			}
			else if((i!=c1.size()-1)&&(j!=c2.size()-1)){
					if(c1.get(i+1)>c2.get(j+1)){
					result.add(c1.get(i));
					result.add(c1.get(i+1));
					i+=2;
					j=j+2;
					}
					else{
					result.add(c2.get(j));
					result.add(c2.get(j+1));
					j+=2;
					i+=2;
				}
			}
			else{
				if((i==c1.size()-1)&&(j==c2.size()-1)){
					if(c1.get(i)<c2.get(j)){
						result.add(c1.get(i++));result.add(c2.get(j-1));result.add(c2.get(j++));}
					else{result.add(c2.get(j++));result.add(c1.get(i-1));result.add(c1.get(i++));}
				}
				else if(i==c1.size()-1){
					result.add(c1.get(i));result.add(j>1?c2.get(j-1):0);
					while(j<c2.size()){result.add(c2.get(j));j++;}
				}
				else if(j==c2.size()-1){
					result.add(c2.get(j));result.add(i>1?c1.get(i-1):0);
					while(i<c1.size()){result.add(c1.get(i));i++;}
				}
			}
		}	
		return result;
		}
}
