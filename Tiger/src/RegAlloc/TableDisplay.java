package RegAlloc;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;



public class TableDisplay {
	

	public static void display(Hashtable<Graph.Node, Temp.Temp> table){
		Set<Graph.Node> keyset = table.keySet();
		Iterator<Graph.Node> itr =  keyset.iterator();
        while(itr.hasNext()){
        	Graph.Node node = itr.next();
        	node.display();
        	System.out.print("   ");
        	Temp.Temp value = table.get(node);
        	if(value == null){
        		System.out.print("null");
        	}else{
        		value.display();
        	}
        	System.out.println();
        }
	}
	
	public static void display2(Hashtable<Graph.Node,LiveInfo> table){
		Set<Graph.Node> keyset = table.keySet();
		Iterator<Graph.Node> itr =  keyset.iterator();
        while(itr.hasNext()){
        	Graph.Node node = itr.next();
        	node.display();
        	System.out.print("   ");
        	LiveInfo value = table.get(node);
        	if(value == null){
        		System.out.print("null");
        	}else{
        		value.display();
        	}
        	System.out.println();
        }
	}

	public static void display3(Hashtable<Temp.Temp, Temp.Temp> table) {
		Set<Temp.Temp> keyset = table.keySet();
		Iterator<Temp.Temp> itr =  keyset.iterator();
        while(itr.hasNext()){
        	Temp.Temp node = itr.next();
        	node.display();
        	System.out.print("   ");
        	Temp.Temp value = table.get(node);
        	if(value == null){
        		System.out.print("null");
        	}else{
        		value.display();
        	}
        	System.out.println();
        }
	}

	public static void display4(Hashtable<Temp.Label, Graph.Node> table) {
		Set<Temp.Label> keyset = table.keySet();
		Iterator<Temp.Label> itr = keyset.iterator();
		while(itr.hasNext()){
			Temp.Label label = itr.next();
			label.display();
			System.out.print("  ");
			Graph.Node node = table.get(label);
			if(node == null){
				System.out.print("null");
			}else{
				node.display();
			}
			System.out.println();
		}
	}
	
}
