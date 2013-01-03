package RegAlloc;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.HashSet;
import java.util.Iterator;

public class ReachOptimize {

	public Hashtable<Assem.Instr,Graph.Node> instrMapToNode;
	public HashMap<Graph.Node,ReachInfo> nodeMapToReachInfo;
	public Hashtable<Graph.Node,Assem.Instr> nodeMapToInstr;
	public FlowGraph.AssemFlowGraph flowGraph;
    public ReachNess reachNess;
	public Assem.InstrList instrs;
	
	public ReachOptimize(Assem.InstrList i) {
		    instrs = i;
	  for(int j = 0; j != Tiger.Overall.reachTime;++j){
            reConstruct();
			deleteDeadCode();
			reConstruct();			
    		numInsertLeft();
    		reConstruct();
    		numInsertRight();
    		reConstruct();
    		copySpread();
     }
	}

	private void reConstruct(){
		flowGraph = new FlowGraph.AssemFlowGraph(instrs);
		reachNess = new ReachNess(flowGraph);               
		nodeMapToReachInfo = reachNess.nodeMapToReachInfo;
		instrMapToNode = flowGraph.instrMapToNode;
		nodeMapToInstr = flowGraph.nodeMapToInstr;		
	}
	
	private void copySpread() {
		Assem.InstrList instrPointer = instrs;
		while(instrPointer != null){
			Assem.Instr analysingInstr = instrPointer.head;
			if(isMove(analysingInstr)
					&& analysingInstr.dst != null){
				Graph.Node analysingNode = instrMapToNode.get(analysingInstr);
				Temp.Temp dst = analysingInstr.dst.head;
				Temp.Temp src = analysingInstr.src.head;
				HashSet<Graph.Node> visitedNodes = new HashSet<Graph.Node>();
				copySpreadOne(analysingNode,analysingNode,dst,src,visitedNodes);
			}
			instrPointer = instrPointer.tail;
		}
	}

	private void copySpreadOne(Graph.Node spreadNode,Graph.Node visitingNode,Temp.Temp dst,Temp.Temp src,HashSet<Graph.Node> visitedNodes){
		if(visitedNodes.contains(visitingNode) || isJal(nodeMapToInstr.get(visitingNode)))
			return;
		
		visitedNodes.add(visitingNode);
		if(spreadNode == visitingNode){
			Graph.NodeList succPointer = visitingNode.succ();
			while(succPointer != null){
				if(succPointer.head.pred().tail == null)
					copySpreadOne(spreadNode,succPointer.head,dst,src,visitedNodes);
				succPointer = succPointer.tail;
			}			
		}else{
			ReachInfo reachInfo = nodeMapToReachInfo.get(visitingNode);
			if(reachInfo.In.contains(spreadNode)){
				Assem.Instr visitingInstr = nodeMapToInstr.get(visitingNode);
				//spread
		
				        Temp.TempList replaceList = visitingInstr.src;
						while(replaceList != null){
							if(replaceList.head == dst)
								replaceList.head = src;
							replaceList = replaceList.tail;
						}			    	
				    
				// continue dfs
				boolean nokilled = true;
				Temp.TempList dstList = visitingInstr.dst;
				while(dstList != null){
					nokilled = nokilled && (dstList.head != src)  && (dstList.head != dst);
					dstList = dstList.tail;
				}

				if(nokilled){
					Graph.NodeList succPointer = visitingNode.succ();
					while(succPointer != null){
						if(succPointer.head.pred().tail == null)
							copySpreadOne(spreadNode,succPointer.head,dst,src,visitedNodes);
						succPointer = succPointer.tail;
					}					
				}
			}
		}		
	}
	private void numInsertLeft(){
		Assem.InstrList instrPointer = instrs;
		while(instrPointer != null){
			if(isLi(instrPointer.head)
					&& instrPointer.head.dst != null
					&& isAllocedTemps(instrPointer.head.dst.head)){
				Graph.Node spreadNode = instrMapToNode.get(instrPointer.head);
				Temp.Temp spreadTemp = instrPointer.head.dst.head;
				int spreadNum = getNumFromLi(instrPointer.head.assem);
				HashSet<Graph.Node> visitedNodes = new HashSet<Graph.Node>();
				leftSpread(spreadNode,spreadNode,spreadTemp,spreadNum,visitedNodes); 									
			}
			instrPointer = instrPointer.tail;
		}
	}

	/*
	 * we can spread the constant iff the visitingNode has only one entrance
	 * but it's very conservative,even cann't take any effects
	 */
		private void leftSpread(Graph.Node spreadNode, Graph.Node visitingNode,Temp.Temp spreadTemp,
			int spreadNum, HashSet<Graph.Node> visitedNodes) {

		if(visitedNodes.contains(visitingNode))
			return;
		
		visitedNodes.add(visitingNode);
		if(spreadNode == visitingNode){
			Graph.NodeList succPointer = visitingNode.succ();
			while(succPointer != null){
				if(succPointer.head.pred().tail == null)
					leftSpread(spreadNode,succPointer.head,spreadTemp,spreadNum,visitedNodes);
				succPointer = succPointer.tail;
			}
		}else{
			ReachInfo reachInfo = nodeMapToReachInfo.get(visitingNode);
			if(reachInfo.In.contains(spreadNode)){
				Assem.Instr visitingInstr = nodeMapToInstr.get(visitingNode);
				//spread
				if(isAdd(visitingInstr)
						&& visitingInstr.src.head == spreadTemp){
					if(visitingInstr.src.tail == null){
						int right = getNumFromASMDS(visitingInstr.assem);
						int result = right + spreadNum;
						visitingInstr.src = null;
						String newAssem = "li `d0,"+result;
						visitingInstr.assem = newAssem;
					}else{
						visitingInstr.src.head = visitingInstr.src.tail.head;
						visitingInstr.src.tail = null;
						String newAssem = "add `d0,`s0," + spreadNum;
						visitingInstr.assem = newAssem;
					}
				}
				
				if(isMul(visitingInstr)
						&& visitingInstr.src.head == spreadTemp){
					if(visitingInstr.src.tail == null){
						int right = getNumFromASMDS(visitingInstr.assem);
						int result = right * spreadNum;
						visitingInstr.src = null;
						String newAssem = "li `d0,"+result;
						visitingInstr.assem = newAssem;
					}else{
						visitingInstr.src.head = visitingInstr.src.tail.head;
						visitingInstr.src.tail = null;
						String newAssem = "mul `d0,`s0," + spreadNum;
						visitingInstr.assem = newAssem;
					}
				}

				
				if(isSll(visitingInstr)
						&& visitingInstr.src.head == spreadTemp){
					int shiftBits = getNumFromASMDS(visitingInstr.assem);
					int result = spreadNum;
					for(int i = 0; i != shiftBits;++i)
						result *= 2;
					visitingInstr.src = null;
					String newAssem = "li `d0,"+result;
					visitingInstr.assem = newAssem;
				}
				
				if(isSub(visitingInstr)
						&& visitingInstr.src.head == spreadTemp
						&& visitingInstr.src.tail == null){
					int right = getNumFromASMDS(visitingInstr.assem);
					int result = spreadNum-right;
					visitingInstr.src = null;
					String newAssem = "li `d0,"+result;
					visitingInstr.assem = newAssem;													            	
				}         
				
				if(isDiv(visitingInstr)
						&& visitingInstr.src.head == spreadTemp
						&& visitingInstr.src.tail == null){
					int right = getNumFromASMDS(visitingInstr.assem);
					int result = spreadNum/right;
					visitingInstr.src = null;
					String newAssem = "li `d0,"+result;
					visitingInstr.assem = newAssem;					
				}
        
				// continue dfs
				Graph.NodeList succPointer = visitingNode.succ();
				while(succPointer != null){
					if(succPointer.head.pred().tail == null)
						leftSpread(spreadNode,succPointer.head,spreadTemp,spreadNum,visitedNodes);
					succPointer = succPointer.tail;
				}
			}
		}
		
	}
	private void numInsertRight() {
		Assem.InstrList instrPointer = instrs;
		while(instrPointer != null){
			Assem.Instr analysingInstr = instrPointer.head;
			if(isLi(analysingInstr)
					&& analysingInstr.def() != null
					&& isAllocedTemps(analysingInstr.def().head)){
				Graph.Node spreadNode = instrMapToNode.get(analysingInstr);
				int spreadNum = getNumFromLi(analysingInstr.assem);
				HashSet<Graph.Node> visitedNodes = new HashSet<Graph.Node>();
				rightSpread(spreadNode,spreadNode,instrPointer.head.def().head,spreadNum,visitedNodes);
			}
			instrPointer = instrPointer.tail;
		}
	}
	
	private void rightSpread(Graph.Node spreadNode, Graph.Node visitingNode, Temp.Temp spreadTemp, int spreadNum,HashSet<Graph.Node> visitedNodes) {
		if(visitedNodes.contains(visitingNode))
			return;
		
		visitedNodes.add(visitingNode);
		if(spreadNode == visitingNode){
			Graph.NodeList succPointer = visitingNode.succ();
			while(succPointer != null){
				if(succPointer.head.pred().tail == null)
					rightSpread(spreadNode,succPointer.head,spreadTemp,spreadNum,visitedNodes);
				succPointer = succPointer.tail;
			}
		}else{
			ReachInfo reachInfo = nodeMapToReachInfo.get(visitingNode);
			if(reachInfo.In.contains(spreadNode)){
				Assem.Instr visitingInstr = nodeMapToInstr.get(visitingNode);
				//spread
				if(isBranch(visitingInstr)
						&& visitingInstr.src.tail != null
						&& visitingInstr.src.tail.head == spreadTemp){
					visitingInstr.src.tail = null;
					String newAssem = visitingInstr.assem.substring(0,firstCommaIndex(visitingInstr.assem)+1)
					                          + spreadNum
					                          + visitingInstr.assem.substring(secondCommaIndex(visitingInstr.assem),visitingInstr.assem.length());
					visitingInstr.assem = newAssem;
				}else if(canRightReplace(visitingInstr)
						&& visitingInstr.src.tail != null
						&& visitingInstr.src.tail.head == spreadTemp){
					visitingInstr.src.tail = null;
					String newAssem = visitingInstr.assem.substring(0,secondCommaIndex(visitingInstr.assem)+1)+spreadNum;
					visitingInstr.assem = newAssem;
				}else if(isMove(visitingInstr)
						&& visitingInstr.src != null
						&& visitingInstr.src.head == spreadTemp){
					visitingInstr.src = null;
					String newAssem = "li"+visitingInstr.assem.substring(4,firstCommaIndex(visitingInstr.assem)+1)+spreadNum;
					visitingInstr.assem = newAssem;
				}
				// continue dfs
				Graph.NodeList succPointer = visitingNode.succ();
				while(succPointer != null){
					if(succPointer.head.pred().tail == null)
						rightSpread(spreadNode,succPointer.head,spreadTemp,spreadNum,visitedNodes);
					succPointer = succPointer.tail;
				}
			}
		}
	}


	private void deleteDeadCode() {
		HashSet<Assem.Instr> deadCodes = new HashSet<Assem.Instr>();
        Assem.InstrList instrPointer = instrs;
        //scan all the instrucitions
        while(instrPointer != null){
        	HashSet<Graph.Node> visitedNodes = new HashSet<Graph.Node>();
        	Graph.Node analysingNode = instrMapToNode.get(instrPointer.head);
            Temp.TempList defList = flowGraph.def(analysingNode);
            if(defList != null){
               Temp.Temp def = defList.head;
               if(isAllocedTemps(def)
            		   && defList.tail == null){
            	   boolean isDead = isDead(def,analysingNode,analysingNode,visitedNodes);
                   if(isDead)
                	   deadCodes.add(instrPointer.head);
               }
            }
        	instrPointer = instrPointer.tail;
        }
        
        Assem.InstrList lastInstr = instrs;
        instrPointer = instrs.tail;
        while(instrPointer != null){
        	if(deadCodes.contains(instrPointer.head)){
        		lastInstr.tail = instrPointer.tail;
        	}else{
        		lastInstr = lastInstr.tail;
        	}
    		instrPointer = instrPointer.tail;
        }
	}

	/*
	 * dfs algorithm
	 * if reach the exit ,visited node or the analysing node return true
	 * if in the reach node the def is used,return false
	 * if all the path is true,return true,else return false 
	 */
	private boolean isDead(Temp.Temp def,Graph.Node analysingNode,Graph.Node visitingNode,HashSet<Graph.Node> visitedNodes) {

		if(visitedNodes.contains(visitingNode))
			return true;

		visitedNodes.add(visitingNode);
		if(analysingNode == visitingNode){
			boolean ans = true;
			Graph.NodeList successors = analysingNode.succ();
			Graph.NodeList successorPointer = successors;
			while(successorPointer != null){
				ans = ans && isDead(def,analysingNode,successorPointer.head,visitedNodes);
				successorPointer = successorPointer.tail;
			}
			return ans;			
		}
		
		ReachInfo reachInfo = nodeMapToReachInfo.get(visitingNode);
		
		if(reachInfo.In.contains(analysingNode)){
			Assem.Instr visitingInstr = nodeMapToInstr.get(visitingNode);
			Temp.TempList usePointer =  visitingInstr.src;
			while(usePointer != null){
				if(def == usePointer.head)
					return false;
				usePointer = usePointer.tail;
			}
			
            if(reachInfo.kill.contains(analysingNode)){
                return true;
            }else{
				boolean ans = true;
				Graph.NodeList successors = visitingNode.succ();
				Graph.NodeList successorPointer = successors;
				while(successorPointer != null){
					ans = ans && isDead(def,analysingNode,successorPointer.head,visitedNodes);
					successorPointer = successorPointer.tail;
				}
				return ans;				            	
            }
			
		}
		return true;
	}


	private int firstCommaIndex(String assem){
		for(int i = 0; i != assem.length(); ++i)
			if(assem.substring(i, i+1).equals(","))
					return i;
		return 0;
	}
	
	private int secondCommaIndex(String assem) {
		boolean findOne = false;
		for(int i = 0; i != assem.length(); ++i){
			if(assem.substring(i, i+1).equals(",")){
				if(findOne)
					return i;
				else
					findOne = true;
			}
		}
		return 0;
	}

	private int getNumFromLi(String assem) {
		return Integer.parseInt(assem.substring(assem.indexOf(",")+1, assem.length()));
	}
	
	private int getNumFromASMDS(String assem){
		return Integer.parseInt(assem.substring(secondCommaIndex(assem)+1,assem.length()));
	}
	
	private boolean isBranch(Assem.Instr instr){
		return isBeq(instr) || isBne(instr) || isBgt(instr) || isBge(instr) || isBlt(instr) || isBle(instr); 		
	}
	
	private boolean canRightReplace(Assem.Instr instr){
		return isAdd(instr) || isSub(instr) || isMul(instr) || isDiv(instr) || isSll(instr)
		|| isBeq(instr) || isBne(instr) || isBgt(instr) || isBge(instr) || isBlt(instr) || isBle(instr); 
	}
	
	private boolean isLi(Assem.Instr instr) {
		if(instr.assem.length() >= 2
				&& instr.assem.substring(0,2).equals("li"))
			return true;
		return false;
	}

	private boolean isAdd(Assem.Instr instr){
		if(instr.assem.length() >= 3
				&& instr.assem.substring(0,3).equals("add"))
			return true;
		return false;		
	}
	
	private boolean isSub(Assem.Instr instr){
		if(instr.assem.length() >= 3
				&& instr.assem.substring(0,3).equals("sub"))
			return true;
		return false;		
	}

	private boolean isMul(Assem.Instr instr){
		if(instr.assem.length() >= 3
				&& instr.assem.substring(0,3).equals("mul"))
			return true;
		return false;		
	}
	
	private boolean isDiv(Assem.Instr instr){
		if(instr.assem.length() >= 3
				&& instr.assem.substring(0,3).equals("div"))
			return true;
		return false;		
	}

	private boolean isSll(Assem.Instr instr){
		if(instr.assem.length() >= 3
				&& instr.assem.substring(0,3).equals("sll"))
			return true;
		return false;		
	}
	
	private boolean isBeq(Assem.Instr instr){
		if(instr.assem.length() >= 3
				&& instr.assem.substring(0,3).equals("beq"))
			return true;
		return false;		
	}
	
	private boolean isBne(Assem.Instr instr){
		if(instr.assem.length() >= 3
				&& instr.assem.substring(0,3).equals("bne"))
			return true;
		return false;		
	}

	private boolean isBgt(Assem.Instr instr){
		if(instr.assem.length() >= 3
				&& instr.assem.substring(0,3).equals("bgt"))
			return true;
		return false;		
	}

	private boolean isBge(Assem.Instr instr){
		if(instr.assem.length() >= 3
				&& instr.assem.substring(0,3).equals("bge"))
			return true;
		return false;		
	}

	private boolean isBlt(Assem.Instr instr){
		if(instr.assem.length() >= 3
				&& instr.assem.substring(0,3).equals("blt"))
			return true;
		return false;		
	}

	private boolean isBle(Assem.Instr instr){
		if(instr.assem.length() >= 3
				&& instr.assem.substring(0,3).equals("ble"))
			return true;
		return false;		
	}

	private boolean isMove(Assem.Instr instr){
		if(instr.assem.length() >= 4
				&& instr.assem.substring(0,4).equals("move"))
			return true;
		return false;		
	}

	private boolean isJal(Assem.Instr instr){
		if(instr.assem.length() >= 3
				&& instr.assem.substring(0,3).equals("jal"))
			return true;
		return false;		
	}
	
	ReachInfo getReachInfo(Assem.Instr instr){
		return nodeMapToReachInfo.get(instrMapToNode.get(instr));
	}
	
	private boolean isAllocedTemps(Temp.Temp def) {
		Temp.TempList tempPointer = Mips.MipsFrame.instance.callerSaveRegs;
		while(tempPointer != null){
			if(def == tempPointer.head)
				return true;
			tempPointer = tempPointer.tail;
		}
		
		tempPointer = Mips.MipsFrame.instance.calleeSaveRegs;
		while(tempPointer != null){
			if(def == tempPointer.head)
				return true;
			tempPointer = tempPointer.tail;
		}

		return false;
	}


}
