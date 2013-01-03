package RegAlloc;

import java.util.HashSet;

public class ReachInfo {
	public HashSet<Graph.Node> gen;
	public HashSet<Graph.Node> kill;
	public HashSet<Graph.Node> In;
	public HashSet<Graph.Node> Out;
	
	ReachInfo(){
		gen = new HashSet<Graph.Node>();
		kill = new HashSet<Graph.Node>();
		In = new HashSet<Graph.Node>();
		Out = new HashSet<Graph.Node>();
	}

	public void display() {
		System.out.print("gen:");
        SetDisplay.display2(gen);
		System.out.print("kill:");
		SetDisplay.display2(kill);
		System.out.print("liveIn:");
		SetDisplay.display2(In);
		System.out.print("liveOut:");
		SetDisplay.display2(Out);		
	}
	
}
