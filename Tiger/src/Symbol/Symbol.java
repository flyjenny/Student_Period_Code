package Symbol;
public class Symbol
{
  public static int counter;
  private String name;
  public Symbol(String n) {
    name=n;
  }
  private static java.util.Dictionary dict = new java.util.Hashtable();

  public String toString() {
	return name;
  }

  public void display(){
	System.out.print(name);  
  }
  
  public static Symbol symbol(String n) {
	++counter;
	String u = n.intern();
	Symbol s = (Symbol)dict.get(u);
	if (s==null) {
		s = new Symbol(u);
		dict.put(u,s);
	}
	return s;
  }
  
}

