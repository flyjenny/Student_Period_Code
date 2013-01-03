package RegAlloc;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import Graph.Node;
import Tiger.Overall;


public class Liveness extends InterferenceGraph {

	/*
	 * recode the live temp at the exit of the node
	 */
	
	//the destination of liveness is to get liveMap
	public HashMap<Graph.Node, Temp.TempList> liveMap ;
    public Hashtable<Graph.Node, LiveInfo> nodeMapToliveInfo ;
    public Hashtable<Graph.Node, Temp.Temp> nodeMapToTemp ;    
    
   
	public Liveness(FlowGraph.FlowGraph flow){
	    //initial
		liveMap = new HashMap<Graph.Node, Temp.TempList>();
		nodeMapToliveInfo = new Hashtable<Graph.Node, LiveInfo>();
		nodeMapToTemp = new Hashtable<Graph.Node, Temp.Temp>(); 
		
		//put the use and def information into the table
		FlowGraph.FlowGraph f = flow;
		Hashtable<Graph.Node, LiveInfo> n = nodeMapToliveInfo;
		Hashtable<Graph.Node, Temp.Temp> nn = nodeMapToTemp;
		
		Graph.NodeList nodePointer = flow.mynodes;
		while(nodePointer != null){
			LiveInfo newLiveInfo = new LiveInfo();

			//put in the def info
			Temp.TempList def = flow.def(nodePointer.head);
			Temp.TempList defPointer = def;
			while(defPointer != null){
				newLiveInfo.def.add(defPointer.head);
				defPointer = defPointer.tail;
			}
	
			//put in the use info
			Temp.TempList use = flow.use(nodePointer.head);
            Temp.TempList usePointer = use;
            while(usePointer != null){
            	newLiveInfo.use.add(usePointer.head);
            	usePointer = usePointer.tail;
            }
			
            Graph.Node key = nodePointer.head;
            LiveInfo value = newLiveInfo;
			nodeMapToliveInfo.put(key,value);
			nodePointer = nodePointer.tail;
		}
		
		//iteratively calculation
		while(true){
		    int changeCounter = 0;
			nodePointer = flow.mynodes;
			while(nodePointer != null){

				LiveInfo liveInfo = nodeMapToliveInfo.get(nodePointer.head);

				//calculate the liveIn
				HashSet<Temp.Temp> newliveIn = new HashSet<Temp.Temp>();
				newliveIn.addAll(liveInfo.liveOut);
				newliveIn.removeAll(liveInfo.def);
				newliveIn.addAll(liveInfo.use);
				if(!newliveIn.equals(liveInfo.liveIn)){
					++changeCounter;
					liveInfo.liveIn = newliveIn;
				}
					
				//calculate the liveOut
                HashSet<Temp.Temp> newliveOut = new HashSet<Temp.Temp>();
                Graph.NodeList successors = nodePointer.head.succ();
                while(successors != null){
                	LiveInfo successorInfo = nodeMapToliveInfo.get(successors.head);
                	newliveOut.addAll(successorInfo.liveIn);
                	successors = successors.tail;
                }
                if(!newliveOut.equals(liveInfo.liveOut)){
                	++changeCounter;
                	liveInfo.liveOut = newliveOut;
                }
				nodePointer = nodePointer.tail;
			}
			
			if(changeCounter == 0)
				break;
		}
		
		//put all the liveoutInfo into LiveMap and change the set back to the templist 
		nodePointer = flow.mynodes;
		while(nodePointer != null){
			LiveInfo liveinfo = nodeMapToliveInfo.get(nodePointer.head);
			Temp.TempList liveOutTempList = null;
			HashSet<Temp.Temp> liveOutSet= liveinfo.liveOut;
			Iterator<Temp.Temp> itr = liveOutSet.iterator();
            while(itr.hasNext()){
			    liveOutTempList = new Temp.TempList(itr.next(),liveOutTempList);
            }
			liveMap.put(nodePointer.head, liveOutTempList);
			nodePointer = nodePointer.tail;
		}
		
	}
	
	/*
	 * to add edges between interfere temps
	 */
    public void interfere(FlowGraph.FlowGraph flow){
    	   	
    	//use a hashSet to avoid a temp appear over one time in the graph;
    	HashSet<Temp.Temp> tempSet = new HashSet<Temp.Temp>(); 
    	Graph.NodeList nodePointer = flow.mynodes;
    	while(nodePointer != null){		
    		Temp.TempList def = flow.def(nodePointer.head);
    		Temp.TempList defPointer = def;
    		while(defPointer != null){
    		    tempSet.add( defPointer.head);
    			defPointer = defPointer.tail;
    		}
    		
    		Temp.TempList use = flow.use(nodePointer.head);
    		Temp.TempList usePointer = use;
    		while(usePointer != null){
    		    tempSet.add(usePointer.head);
        	    usePointer = usePointer.tail;
    		}
    		nodePointer = nodePointer.tail;
    	}

       	//put all the temps into the nodeMapToTemp of interferegraph    	
    	Iterator<Temp.Temp> itr = tempSet.iterator();
    	while(itr.hasNext())
    		nodeMapToTemp.put(newNode(), itr.next());
   	    	
    	//add the interfere edges
    	nodePointer = flow.mynodes;
    	while(nodePointer != null){
    		Temp.TempList defPointer = flow.def(nodePointer.head);
    		while(defPointer != null){
    			Temp.TempList liveOutPointer = liveMap.get(nodePointer.head);
    			while(liveOutPointer != null){
    				Graph.Node defNode = tnode(defPointer.head);
    				Graph.Node liveNode = tnode(liveOutPointer.head);
    				addEdge(defNode,liveNode);
    				addEdge(liveNode,defNode);
    				liveOutPointer = liveOutPointer.tail;
    			}
    			defPointer = defPointer.tail;
    		}
    		nodePointer = nodePointer.tail;
    	}
    }
    /*
     * relate a Temp to a node
     */
	public Graph.Node tnode(Temp.Temp temp) {
		Set<Node> keys = nodeMapToTemp.keySet();
		Iterator<Node> itr = keys.iterator();
        while(itr.hasNext()){
        	Graph.Node key = itr.next();
        	Temp.Temp value = nodeMapToTemp.get(key);
        	if(value == temp)
        		return key;
        }
		Overall.myerror.error(0, "something wrong in the tnode function");
		return null;
	}

    /*
     * relate a node to a Temp 
     */
    public Temp.Temp gtemp(Graph.Node node) {
		return nodeMapToTemp.get(node);
	}

	public void show(java.io.PrintStream out){
		for(Graph.NodeList pointer = mynodes; pointer != null ; pointer = pointer.tail){
			Temp.Temp temp = gtemp(pointer.head);
			temp.display();
			out.print(":");
			for(Graph.NodeList succs = pointer.head.succ(); succs != null ; succs = succs.tail){
				Temp.Temp succ = gtemp(succs.head);
				succ.display();
				out.print("  ");
			}
		    out.println();
		}
		out.println();
	}
}
