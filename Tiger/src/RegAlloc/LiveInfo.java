package RegAlloc;
import java.util.HashSet;

public class LiveInfo {
	public HashSet<Temp.Temp> use;
	public HashSet<Temp.Temp> def;
	public HashSet<Temp.Temp> liveIn;
	public HashSet<Temp.Temp> liveOut;
	
	LiveInfo(){
		use = new HashSet<Temp.Temp>();
		def = new HashSet<Temp.Temp>();
		liveIn = new HashSet<Temp.Temp>();
		liveOut = new HashSet<Temp.Temp>();
	}

	public void display() {
		System.out.print("use:");
		SetDisplay.display(use);
		System.out.print("def:");
		SetDisplay.display(def);
		System.out.print("liveIn:");
		SetDisplay.display(liveIn);
		System.out.print("liveOut:");
		SetDisplay.display(liveOut);		
	}
}
