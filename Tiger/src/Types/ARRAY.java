package Types;

public class ARRAY extends Type {
   public Type element;
   public ARRAY(Type e) {element = e;}
   public boolean coerceTo(Type t) {
	return this==t.actual();
   }
   public void display(){
	   System.out.print("ARRAY(");
	   element.display();
	   System.out.print(")");
   }
}

