package Algorithm;

import java.util.*;

public class Vertex {
	public int DFS_Number; //�����DFS��   
    public int High; //�����highֵ   
    public int parent; //��DFS���еĸ��ڵ�   
    public boolean mark; //����DFS������������Ƿ񱻷���   
    public LinkedList<Integer> Edge;//��ö������ӵı�   
       
    Vertex(){ //���캯��   
        DFS_Number=0;
        High=0;
        parent=0;
        mark = false; 
        Edge = new LinkedList<Integer>();   
          
    } 
    
}
