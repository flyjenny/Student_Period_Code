package RegAlloc;

import Assem.InstrList;
import FlowGraph.AssemFlowGraph;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ReachNess {

	public HashMap<Graph.Node,ReachInfo> nodeMapToReachInfo = new HashMap<Graph.Node,ReachInfo>();
	
	public ReachNess(AssemFlowGraph assemFlowGraph) { 	
		//initial the map and the gen set
		Graph.NodeList nodePointer = assemFlowGraph.mynodes;
		while(nodePointer != null){
			ReachInfo reachInfo = new ReachInfo();
			reachInfo.gen.add(nodePointer.head);
			reachInfo.Out.add(nodePointer.head);
			nodeMapToReachInfo.put(nodePointer.head, reachInfo);
			nodePointer = nodePointer.tail;
		}
		
		//initial kill set
		nodePointer = assemFlowGraph.mynodes;
		while(nodePointer != null){
			    Temp.TempList genTemps = assemFlowGraph.def(nodePointer.head);
			    Temp.TempList genTempsPointer = genTemps;
				Graph.Node analysingNode = nodePointer.head;
			    while(genTempsPointer != null){
				    Temp.Temp genTemp = genTempsPointer.head;
					ReachInfo reachInfo = nodeMapToReachInfo.get(analysingNode);
					Graph.NodeList killElsePointer = assemFlowGraph.mynodes;
					while(killElsePointer != null){
						Graph.Node otherNode = killElsePointer.head;
						if(otherNode != analysingNode){
							Temp.TempList alsoGenTemps = assemFlowGraph.def(otherNode);
							Temp.TempList tempPointer = alsoGenTemps;
							while(tempPointer != null){
								if(genTemp == tempPointer.head 
										&& !reachInfo.kill.contains(otherNode))
									reachInfo.kill.add(otherNode);
								tempPointer = tempPointer.tail;
							}
						}
						killElsePointer = killElsePointer.tail;
					}		
					
					genTempsPointer = genTempsPointer.tail;
			    }
			nodePointer = nodePointer.tail;
		}
	
	//iterate to get in and out
	    while(true){
	    	int changeCounter = 0;
			nodePointer = assemFlowGraph.mynodes;
	        while(nodePointer != null){
	            Graph.Node analysingNode = nodePointer.head;
	            ReachInfo analysingInfo = nodeMapToReachInfo.get(analysingNode);
	            
	            //get the out set
	            HashSet<Graph.Node> newOut = new HashSet<Graph.Node>(); 
	            newOut.addAll(analysingInfo.In);
	            newOut.removeAll(analysingInfo.kill);
	            newOut.addAll(analysingInfo.gen);
	            if(!newOut.equals(analysingInfo.Out)){
	            	analysingInfo.Out = newOut;
	            	++changeCounter;
	            }
	            
	            //get the in set
	            HashSet<Graph.Node> newIn = new HashSet<Graph.Node>(); 	            
	            Graph.NodeList predPointer = analysingNode.pred();
	            while(predPointer != null){
	            	newIn.addAll((nodeMapToReachInfo.get(predPointer.head)).Out);
	            	predPointer = predPointer.tail;
	            }
	     
	            if(!newIn.equals(analysingInfo.In)){
	            	analysingInfo.In = newIn;
	            	++changeCounter;
	            }
	        
	            nodePointer = nodePointer.tail;
	        }
	        
	        if(changeCounter == 0)
	        	break;
	    } 
	    
//	    MapDisplay.display2(nodeMapToReachInfo);
	}
	
}
