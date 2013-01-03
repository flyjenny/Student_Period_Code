package Algorithm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Stack;

public class ex3 {
	public static int DFS_N;   
    public static Vertex[] V; //顶点邻接表首节点   
    public static int component=0; //双连通分支数量   
    public static Stack<Integer> sta = new Stack<Integer>();   
       
    public static void main(String[] args){
    	Collection<Integer> co=new ArrayList<Integer>();
    	ArrayList<Integer> a=new ArrayList<Integer>();
    	a.add(1);
    	a.add(2);
    	a.add(3);
    	String d="(1,2,3,4,5)";
    	co.add(Arrays.asList(d));
    	co.addAll(Arrays.asList(5,6,7,8,9));
    	System.out.println(co);
    	
    }
}
