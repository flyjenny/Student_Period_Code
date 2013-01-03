package Algorithm;

import java.util.*;

public class Vertex {
	public int DFS_Number; //顶点的DFS数   
    public int High; //顶点的high值   
    public int parent; //在DFS树中的父节点   
    public boolean mark; //构造DFS树是用来标记是否被访问   
    public LinkedList<Integer> Edge;//与该顶点连接的边   
       
    Vertex(){ //构造函数   
        DFS_Number=0;
        High=0;
        parent=0;
        mark = false; 
        Edge = new LinkedList<Integer>();   
          
    } 
    
}
