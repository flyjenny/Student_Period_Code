package RegAlloc;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;

import Graph.Node;

public class SetDisplay {

	public static void display(HashSet<Temp.Temp> set) {
		Iterator<Temp.Temp> itr = set.iterator();
		if(!itr.hasNext())
			System.out.print("null   ");
        while(itr.hasNext()){
        	itr.next().display();
        	System.out.print("    ");
        }
	}

	public static void display2(HashSet<Graph.Node> set) {
		Iterator<Graph.Node> itr = set.iterator();
		if(!itr.hasNext())
			System.out.print("null   ");
        while(itr.hasNext()){
        	itr.next().display();
        	System.out.print("    ");
        }		
	}
}
