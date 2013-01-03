package FlowGraph;

import java.util.HashMap;
import java.util.Hashtable;


import Graph.Node;
import RegAlloc.MapDisplay;
import RegAlloc.TableDisplay;

public class AssemFlowGraph extends FlowGraph {
	
	public Hashtable<Graph.Node,Assem.Instr> nodeMapToInstr = new Hashtable<Graph.Node,Assem.Instr>();
	public Hashtable<Assem.Instr,Graph.Node> instrMapToNode = new Hashtable<Assem.Instr,Graph.Node>();
 
	Hashtable<Temp.Label, Node> labelMapToNode = new Hashtable<Temp.Label,Graph.Node>(); 
	HashMap<Temp.Temp,Temp.Temp> moveMap = new HashMap<Temp.Temp,Temp.Temp>();
   
	public Assem.Instr instr(Graph.Node node) {
		return nodeMapToInstr.get(node);
	}
	/*
	 * judge if it's a move instruction
	 * if the def and use are identical,remove the MOVE instruction
	 */
	@Override
	public boolean isMove(Graph.Node node) {
		return (nodeMapToInstr.get(node) instanceof Assem.MOVE);
	}
	
	@Override
	/*
	 * temps defined at this node
	 */
	public Temp.TempList def(Graph.Node node) {
		Hashtable<Graph.Node,Assem.Instr> n = nodeMapToInstr;
		return nodeMapToInstr.get(node).def();
	}

	/*
	 * temps used at this node
	 */
	@Override
	public Temp.TempList use(Graph.Node node) {
		return nodeMapToInstr.get(node).use();
	}
	

	public AssemFlowGraph(Assem.InstrList instrs) {
		Assem.InstrList instrsPointer = instrs;
		
		//map the graph node and the Assems,also record the labels which will be jumped to.
		while(instrsPointer != null){
		    Graph.Node newNode = this.newNode();
			nodeMapToInstr.put(newNode, instrsPointer.head);
	        instrMapToNode.put(instrsPointer.head, newNode);
			//record the labels which will be jumped to
			if(instrsPointer.head instanceof Assem.LABEL){
				Temp.Label newLabel = ((Assem.LABEL)instrsPointer.head).label;
				labelMapToNode.put(newLabel, newNode);
			}		
			//put the dst and use temp of the MOVE instruction in the map for coalesce
			if(instrsPointer.head instanceof Assem.MOVE
					&& isMove(instrsPointer.head)){
				moveMap.put(((Assem.MOVE)instrsPointer.head).dst.head, ((Assem.MOVE)instrsPointer.head).src.head);
				moveMap.put(((Assem.MOVE)instrsPointer.head).src.head, ((Assem.MOVE)instrsPointer.head).dst.head);
			}
			instrsPointer = instrsPointer.tail;
		}
		
		
		Graph.NodeList nodePointer = mynodes;
		while(nodePointer != null){
			Graph.Node fromNode = nodePointer.head;
			Assem.Instr instr = nodeMapToInstr.get(fromNode);
				if(instr.jumps() != null){
				     Temp.LabelList jumpLabels = instr.jumps().labels;
				     Temp.LabelList labelPointer = jumpLabels;
				     while(labelPointer != null){
					     Graph.Node toNode = labelMapToNode.get(labelPointer.head);
					     addEdge(fromNode,toNode);
					     labelPointer = labelPointer.tail;
				      }
				}else if(nodePointer.tail != null)
					addEdge(fromNode,nodePointer.tail.head);

			nodePointer = nodePointer.tail;
		}
	}

	public Temp.Temp moveMap(Temp.Temp temp){
		return moveMap.get(temp);
	}
	
	private boolean isMove(Assem.Instr instr) {
		if (instr.assem.length() >= 4
				&& instr.assem.substring(0, 4).equals("move"))
			return true;
		return false;
	}

}
