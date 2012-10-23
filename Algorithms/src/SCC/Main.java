package SCC;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Stack;

public class Main {
	int num,DFS_N,Current_Component;
	int[][] edges;
	Stack stack=new Stack();
	int[] count;
	static int[][] forsort;
//	int sp=0;//栈顶指针
	Node[] nodes;
	HashMap<Integer,ArrayList<Integer>> map=new HashMap<Integer,ArrayList<Integer>>();
	
//	HashMap<Integer,Integer> record=new HashMap<Integer,Integer>();
	public static void main(String[] args) throws IOException{
		Main scc =new Main();
		scc.read();
		scc.work();
		scc.write();
		
	}
	public void read() throws IOException{
		BufferedReader in = new BufferedReader(new FileReader("input3.dat"));
		String str="";
		
		str = in.readLine();
		num = Integer.parseInt(str);
		//System.out.println(num);
		edges = new int[num][num];
		nodes = new Node[num];
	//	stack = new int[num];
		count = new int[num+1];//统计每个分支里有多少个节点
//		forsort=new int[num+1][2];
		for(int i=0;i<num;i++){
			nodes[i] = new Node();
			for(int j=0;j<num;j++)
				edges[i][j]=0;
		}
		int a,b,c;
		str = in.readLine();
		while(str!=null){
			c = str.indexOf(",");
			a = Integer.parseInt(str.substring(1, c));
			b = Integer.parseInt(str.substring(c+1, str.length()-1));
			edges[a][b]=1;
			edges[b][a]=1;
			//System.out.println(a+","+b);
			str = in.readLine();
		}
/*		for(int i=0;i<num;i++){
			for(int j=0;j<num;j++){
				System.out.print(edges[i][j]+" ");
			}
		System.out.println();
		}
*/	}
	public void work(){
		Current_Component=0;
		DFS_N = num;
		//System.out.println(num);
		for(int i=0;i<num;i++){
			if(nodes[i].DFS_Num==0){
				//System.out.println(i);
				SCC(i);
			}
		}
	}
	public  void SCC(int v){
		int x;
		nodes[v].DFS_Num = DFS_N;
		DFS_N--;
	//	stack[sp] = v;
//		sp++;
		nodes[v].High = nodes[v].DFS_Num;
		for(int w=0;w<num;w++){
			
			if(edges[v][w]==1){
				stack.push(v);
				if(nodes[w].DFS_Num==0){
					SCC(w);
					if(nodes[w].High<=nodes[v].DFS_Num){
						int count1=0;
						ArrayList<Integer> component=new ArrayList<Integer>();
						Current_Component++;
						do{
					//		x = stack[sp-1];
							x=(Integer) stack.pop();
							
					//		System.out.println(x);
				//			sp--;
							
							boolean mark=false;
							for (int i=0;i<component.size();i++){
								if(component.get(i)==x){
									mark=true;
									break;
								}
							}
							if(!mark){
								component.add(x);
							}
				//			hi.put(Current_Component,)
			//				System.out.print(x+" ");
							nodes[x].Component = Current_Component;	
							count1++;
						}while(x!=v);
						stack.push(v);
						map.put(Current_Component, component);
		//				System.out.println("count1 :"+count1);
						count[Current_Component] = count1;
		//				forsort[Current_Component][0]=Current_Component;
	//					forsort[Current_Component][1] = count1;
	//					record.put(count1, Current_Component);
		//				System.out.println();
					}
					nodes[v].High = max(nodes[v].High,nodes[w].High);
				}else{
			//		if((nodes[w].DFS_Num>nodes[v].DFS_Num)&&(nodes[w].Component==0)){
						nodes[v].High = max(nodes[v].High,nodes[w].DFS_Num);
			//		}
				}
			}
		}
/*		if(nodes[v].High==nodes[v].DFS_Num){
			int count1=0;
			Current_Component++;
			do{
				x = stack[sp-1];
				sp--;
				nodes[x].Component = Current_Component;	
				count1++;
			}while(x!=v);
			count[Current_Component] = count1;
		}
*/	}
	public int max(int a, int b){
		if(a>=b)
			return a;
		else
			return b;
	}
	
	public void write() throws IOException{
		PrintWriter out = new PrintWriter(new File("result3.dat"));
		System.out.println(Current_Component);
		out.println(Current_Component);
		forsort=new int[Current_Component+1][2];
		 
		for(int i=1;i<=Current_Component;i++){
			forsort[i][1]=map.get(i).size();
			forsort[i][0]=i;
		}
		
//			System.out.println(map.get(i));
		
/*		for(int i=0;i<num;i++){
			System.out.println(i+" "+ nodes[i].Component);
			forsort[nodes[i].Component][1]++;	
		}
		System.out.println("hello");
		for(int i=1;i<=Current_Component;i++){
			System.out.println(forsort[i][0]+" "+forsort[i][1]);
		}
		*/
/*		for(int i=1;i<=Current_Component;i++){
			int min=1;//个数最少的连通分支
			for(int k=2;k<=Current_Component;k++){
				if(count[min]>count[k])
					min=k;
			}
			count[min]=num;
			System.out.print("(");
			out.print("(");
			int a=0;
			for(int j=0;j<num;j++){
				if(nodes[j].Component==min){//个数少的连通分支先打印
					if(a!=0){
						System.out.print(","+j);
						out.print(","+j);
					}else{
						System.out.print(j);
						out.print(j);
						a++;
					}
				}
			}
			System.out.println(")");
			out.println(")");
		}
*/		//QuickSort(1,Current_Component,Current_Component);
//		Iterator<Integer> it=record.keySet().iterator();
/*		for(int i=1;i<=Current_Component;i++){
			System.out.println(forsort[i][0]+" "+forsort[i][1]);
		}
*/		quicksort(forsort, 1, Current_Component);
//		System.out.println("end");
/*		for(int i=1;i<=Current_Component;i++){
			System.out.println(forsort[i][0]+" "+forsort[i][1]);
		}
		
*/		
		int [][] tmp = null;
		int k=0;
		ArrayList<Integer> temp=new ArrayList<Integer>();
		for(int i=1;i<=Current_Component;i++){
			temp=map.get(forsort[i][0]);
			k=temp.size();
	//		System.out.println(k);
			tmp=new int[k][2];
	//		System.out.println(forsort[i][0]);
			for(int j=0;j<k;j++){
		//		System.out.println(temp);
		//		System.out.println(map.get(forsort[i][0]).get(j));
				tmp[j][1]=temp.get(j);
		//		System.out.print(tmp[j][1]+" ");
				tmp[j][0]=0;
			}
			
			quicksort(tmp, 0, k-1);
			System.out.print("(");
			out.print("(");
			for(int p=0;p<k-1;p++){
				System.out.print(tmp[p][1]+",");
				out.print(tmp[p][1]+",");
			}
			System.out.println(tmp[k-1][1]+")");
			out.println(tmp[k-1][1]+")");
			
		}
		out.close();
	}
	
	
	public static void quicksort(int[][] a, int left, int right) {
        if (left<right){
       // 	partition(a, left, right);
        	int mid =partition(a, left, right); 
            quicksort(a, left, mid-1);
            quicksort(a, mid+1, right);
        }
        
    }

    private static int partition(int[][] a, int left, int right) {
        int pivot=a[left][1];
        int l=left;        
        int r=right;
        while(l<r){
        	while ((l<=r)&&(a[l][1]<=pivot)){
        //		System.out.println(a[l][1]);
        		l++;
       // 		System.out.println("l "+l);
        	}
        	
        	while((r>=l)&&(a[r][1]>pivot)){
        		r--;
        	}
        	if(l<r){
        		int temp1=a[l][1];
                int temp2=a[l][0];
            	a[l][1] = a[r][1];
            	a[l][0] = a[r][0];
                a[r][1] = temp1;
                a[r][0] = temp2;
        	}
        	
        }
        int mid=r;
        int temp1=a[left][1];
        int temp2=a[left][0];
    	a[left][1] = a[mid][1];
    	a[left][0] = a[mid][0];
        a[mid][1] = temp1;
        a[mid][0] = temp2;
        return mid;
    }

    
	

}
