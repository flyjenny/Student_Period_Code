package Types;

public class RECORD extends Type {
   public Symbol.Symbol fieldName;
   public Type fieldType;
   public RECORD tail;
   public RECORD(Symbol.Symbol n, Type t, RECORD x) {
       fieldName=n; fieldType=t; tail=x;
   }
   
   public boolean coerceTo(Type t) {
	   return this==t.actual();
   }
   
   public void display(){
	   System.out.print("RECORD(");
	   fieldName.display();
	   System.out.print(",");
	   fieldType.display();
	   System.out.print(",");
	   if(tail != null){
		   tail.display();
		   System.out.print(")");	
		   return;
	   }
	   System.out.print("null)");
   }
}
   

