import java.io.*;


public class Main {
	public int n,sp=0,Current_Component,DFS_N;
	public int[][] edges;
	public int[] stack,group;
	public node[] nodes;
	public static void main(String[] args) throws IOException{
		Main dd = new Main();
		dd.input();
		dd.run();
		dd.output();
	}
	
	public void input() throws IOException{
		String tmp;
		int t,t1,t2;
		BufferedReader in=new BufferedReader(
				new FileReader("data4.dat"));
		n=Integer.parseInt(in.readLine());
		edges=new int[n][n];
		stack=new int[n];
		group=new int [n+1];
		nodes=new node[n];
		for (int i=0;i<n;i++){
			nodes[i]=new node();
			for (int j=0;j<n;j++){
				edges[i][j]=0;
			}
		}
		tmp=in.readLine();
		while (tmp!=null){
			t=tmp.indexOf(",");
			t1=Integer.parseInt(tmp.substring(1, t));
			String str=tmp.substring(t+1, tmp.length()-1);
			t2=Integer.parseInt(str);
			edges[t1][t2]=1;
			tmp=in.readLine();
		}
	}
	public void run(){
		Current_Component=0;
		DFS_N=n;
		for (int i=0;i<n;i++){
			//int bb=nodes[i].DFS_Number;
			if (nodes[i].DFS_Number==0) SCC(i);
		}
	}
	public void SCC(int v){
		int x;
		nodes[v].DFS_Number=DFS_N;
		DFS_N--;
		stack[sp]=v;
		sp++;
		nodes[v].High=nodes[v].DFS_Number;
		for (int w=0;w<n;w++){
			if (edges[v][w]==1){
				if (nodes[w].DFS_Number==0){
					SCC(w);
					nodes[v].High=max(nodes[v].High,nodes[w].High);
				}
				else {
					if ((nodes[w].DFS_Number>nodes[v].DFS_Number) && nodes[w].Component==0){
						nodes[v].High=max(nodes[v].High,nodes[w].DFS_Number);
					}
				}
			}
		}
		if (nodes[v].High==nodes[v].DFS_Number){
			int total=0;
			Current_Component++;
			do{
				x=stack[sp-1];
				sp--;
				nodes[x].Component=Current_Component;
				total++;
			}while(x!=v);
			group[Current_Component]=total;
		}
		
	}
	public int max(int t1,int t2){
		if (t1>t2) return t1;
		else return t2;
	}
	public void output() throws IOException{
		PrintWriter out=new PrintWriter("result4.txt");
		for (int i=1;i<=Current_Component;i++){
			int j=1;
			for (int k=2;k<=Current_Component;k++){
				if (group[j]>group[k]){
					j=k;
				}
			}
			group[j]=2*n;
			System.out.print("(");
			out.print("(");
			boolean tmp=false;
			for (int k=0;k<n;k++){
				if (nodes[k].Component==j){
					if (tmp) {
						System.out.print(","+k);
						out.print(","+k);
					}
					else {
						System.out.print(k);
						out.print(k);
						tmp=true;
					}
				}
			}
			System.out.println(")");
			out.println(")");
		}
		out.close();
	}
}