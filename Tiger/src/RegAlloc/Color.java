package RegAlloc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Stack;

import FlowGraph.AssemFlowGraph;
import Tiger.Overall;


public class Color implements Temp.TempMap{
	
	public HashMap<Temp.Temp,Temp.Temp> tempToRealTemp = new HashMap<Temp.Temp,Temp.Temp>();
	public Stack<Graph.Node> nodeStack = new Stack<Graph.Node>();
	public int realTempNum;
	public HashSet<Temp.Temp> allocedTemps;
	
	public Temp.TempList spills(){
		return null;
	};
	public String tempMap(Temp.Temp temp){
		StringBuffer tempString = new StringBuffer();
		if(temp == Mips.MipsFrame.instance.at)
			tempString.append("$at");
		if(temp == Mips.MipsFrame.instance.fp)
			tempString.append("$fp");
		if(temp == Mips.MipsFrame.instance.gp)
			tempString.append("$gp");
		if(temp == Mips.MipsFrame.instance.k0)
			tempString.append("$k0");
		if(temp == Mips.MipsFrame.instance.k1)
			tempString.append("$k1");
		if(temp == Mips.MipsFrame.instance.ra)
			tempString.append("$ra");
		if(temp == Mips.MipsFrame.instance.sp)
			tempString.append("$sp");
		if(temp == Mips.MipsFrame.instance.zero)
			tempString.append("$0");
		if(temp == Mips.MipsFrame.instance.v0)
			tempString.append("$v0");
		if(temp == Mips.MipsFrame.instance.v1)
			tempString.append("$v1");
		int i;
		Temp.TempList pointer;

		for(pointer = Mips.MipsFrame.instance.argRegs,i = 0; pointer != null; pointer = pointer.tail,++i)
			if(temp == pointer.head)
				tempString.append("$a"+i);

		for(pointer = Mips.MipsFrame.instance.calleeSaveRegs,i = 0; pointer != null; pointer = pointer.tail,++i)
			if(temp == pointer.head)
				tempString.append("$s"+i);

		for(pointer = Mips.MipsFrame.callerSaveRegs,i = 0; pointer != null; pointer = pointer.tail,++i)
			if(temp == pointer.head)
				tempString.append("$t"+i);

		return tempString.toString();
	}
	
	/*
	 * use $t0~$t9 and $s0 ~ $s7 to color
	 */
	HashSet<Temp.Temp> realTemps(){
		HashSet<Temp.Temp> reals = new HashSet<Temp.Temp>();
		for(Temp.TempList pointer = Mips.MipsFrame.instance.calleeSaveRegs;pointer != null;pointer = pointer.tail)
			reals.add(pointer.head);
		
		for(Temp.TempList pointer = Mips.MipsFrame.instance.callerSaveRegs;pointer != null;pointer = pointer.tail)
			reals.add(pointer.head);
		realTempNum = reals.size();
		return reals;
	}
	
	/*
	 * greedy algorithm
	 */
	public Color(InterferenceGraph ig, AssemFlowGraph assemFlowGraph){
	    
		//put the precolored temps into the map and the related nodes into the stack
		int tempNum = ig.nodecount; 
		allocedTemps = new HashSet<Temp.Temp>(); 
		Graph.NodeList nodePointer = ig.mynodes;
		while(nodePointer != null){
			Temp.Temp usingTemp = ig.gtemp(nodePointer.head);
			if(tempMap(usingTemp).length() != 0){
				tempToRealTemp.put(usingTemp, usingTemp);
				--tempNum;
			}
			nodePointer = nodePointer.tail;
		}
        
		HashMap<Temp.Temp,Temp.Temp> n = tempToRealTemp;
		//color the graph 
        for(int i = 0; i != tempNum ; ++i){
        	Graph.Node colorNode = FindNext(ig);
        	Temp.Temp key = ig.gtemp(colorNode);
        	HashSet<Temp.Temp> chosenSet = realTemps();    	
        	//move the interfere temps
        	for(Graph.NodeList pointer = colorNode.pred(); pointer != null ; pointer = pointer.tail){
                Temp.Temp nabourTemp = ig.gtemp(pointer.head);
                Temp.Temp chosenTemp = tempToRealTemp.get(nabourTemp);
                chosenSet.remove(chosenTemp);
        	}

       		
        	if(chosenSet.isEmpty()){
        		ig.rmEdge(colorNode);
        		if(Overall.debug)
        		   System.out.println("spilled!");
        		tempToRealTemp.put(key, null);
        	}else{
        		
        		//coalescing
        		Temp.Temp value = null;
        		if((assemFlowGraph.moveMap(key)) != null
        				&& tempToRealTemp.get(assemFlowGraph.moveMap(key)) != null
        				&& chosenSet.contains(tempToRealTemp.get(assemFlowGraph.moveMap(key)))){
        			value = tempToRealTemp.get(assemFlowGraph.moveMap(key));
        		}else{
            		value = chooseTemp(chosenSet);        			
        		}
        		allocedTemps.add(value);
        		tempToRealTemp.put(key, value);
        	}
        }
	}
	
	Graph.Node FindNext(InterferenceGraph ig){
		int colorNabourNum = -1;
		int degree = -1;
		Graph.Node nextNode = null;
		Graph.Node testNode = null;
		Graph.NodeList nodePointer = ig.mynodes;
		while(nodePointer != null){
			if(tempMap(ig.gtemp(nodePointer.head)).length() != 0 || tempToRealTemp.get(ig.gtemp(nodePointer.head)) != null){
				nodePointer = nodePointer.tail;
				continue;
			}
			
			testNode = nodePointer.head;
			int testColorNabourNum = 0;
			Graph.NodeList nabours = testNode.pred();
			for(Graph.NodeList pointer = nabours; pointer != null; pointer = pointer.tail){
				if(tempToRealTemp.get(pointer.head) != null)
					++testColorNabourNum;
			}
			
			if(testColorNabourNum > colorNabourNum){
				colorNabourNum = testColorNabourNum;
				nextNode = testNode;
				degree = nextNode.inDegree();
			}else if (testColorNabourNum == colorNabourNum){
				if(testNode.inDegree() > degree){
					nextNode = testNode;
					degree = nextNode.inDegree();
				}
			}
			
			nodePointer = nodePointer.tail;
		}
		
		return nextNode;
	}

    	
	public Temp.Temp getRealTemp(Temp.Temp t){
		return tempToRealTemp.get(t);
	}
	
	public boolean isAlloced(Temp.Temp temp){
		return allocedTemps.contains(temp);
	}
	
	Temp.Temp chooseTemp(HashSet<Temp.Temp> chosenSet){		
		Iterator<Temp.Temp> itr = chosenSet.iterator();
		ArrayList<Temp.Temp> temps = new ArrayList<Temp.Temp>();
		while(itr.hasNext())
			temps.add(itr.next());
		Temp.TempList tempPointer = Mips.MipsFrame.callerSaveRegs;
		while(tempPointer != null){
			for(int i = 0; i != temps.size(); ++i)
				if(tempPointer.head == temps.get(i))
					return temps.get(i);
			tempPointer = tempPointer.tail;
		}
		return chosenSet.iterator().next();
	}
}
