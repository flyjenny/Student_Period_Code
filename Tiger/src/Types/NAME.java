package Types;

public class NAME extends Type {
   public Symbol.Symbol name;
   private Type binding;
   public NAME(Symbol.Symbol n) {name=n;}
   public boolean isLoop() {
      Type b = binding; 
      boolean any;
      binding=null;
      if (b==null) any=true;
      else if (b instanceof NAME)
            any=((NAME)b).isLoop();
      else any=false;
      binding=b;
      return any;
     }
     
   public Type actual() {
	   return binding.actual();
   }

   public boolean coerceTo(Type t) {
	return this.actual().coerceTo(t);
   }
   public void bind(Type t) {binding = t;}
   
   public void display(){
	   System.out.print("NAME(");
	   if(name != null){
		   name.display();
	   }else
		   System.out.print("null");
	   System.out.print(",");
	   if(binding != null){
		   Type type = binding;
		   if(type instanceof INT){
			   System.out.print("binding to INT");
		   }
		   if(type instanceof ARRAY){
			   System.out.print("binding to ARRAY");
		   }
		   if(type instanceof STRING){
			   System.out.print("binding to STRING");
		   }
		   if(type instanceof NIL){
			   System.out.print("binding to NIL");
		   }
		   if(type instanceof RECORD){
	    	   System.out.print("binding to RECORD");
		   }
		   if(type instanceof VOID){
			   System.out.print("binding to VOID");
		   }
		   if(type instanceof NAME){
			   System.out.print("binding to ");
			   ((NAME)type).name.display();
		   }

	   }else
		   System.out.print("null");
	   System.out.print(")");

   }
}
