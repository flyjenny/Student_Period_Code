package Types;

public class STRING extends Type {
	public STRING(){}
	public boolean coerceTo(Type t) {return (t.actual() instanceof STRING);}
	public void display() {System.out.print("STRING");}
}

