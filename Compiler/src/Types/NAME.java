package Types;

public class NAME extends Type {
   public Symbol.Symbol name;
   private Type binding;
   public NAME(Symbol.Symbol n) {name=n;}

   public boolean isLoop() {
//   		System.out.println( "  ::" + name + ":"+binding);
   		if (binding == Type._void ) return true;
   		if (binding instanceof NAME){
   			Type b = binding;
   			binding = Type._void;
   			if (((NAME) b).isLoop()){   			
//   				binding = null;
//					System.out.println("ERROR: Recursive definition of simple types.");
   				return true;
   			} else {
   				binding = b;
   				return false;
   			}
   		} else return false;
   }
      
   public Type actual() {	//return the binding type
   	return binding.actual();
   }

   public boolean coerceTo(Type t) {
		return this.actual().coerceTo(t);
   }
   public void bind(Type t) {binding = t;}
}
