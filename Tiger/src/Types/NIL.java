package Types;

public class NIL extends Type {
	public NIL () {}
	public boolean coerceTo(Type t) {
	    Type a = t.actual();
	    return (a instanceof RECORD) || (a instanceof NIL);
        }
	public void display() {System.out.print("NIL");}
}

