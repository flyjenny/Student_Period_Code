package Semant;

import Types.RECORD;
import Types.Type;

public class FunEntry extends Entry {
	public RECORD formals;
	public Type result;
	public Translate.Level level;
	public Temp.Label name;
    public boolean isStandard;
    
	public FunEntry(RECORD f, Type r,Translate.Level l,Temp.Label n) {
		formals = f;
		result = r;
		level = l;
		name = n;
		isStandard = false;
	}

	public FunEntry(RECORD f, Type r,Translate.Level l,Temp.Label n,boolean b) {
		formals = f;
		result = r;
		level = l;
		name = n;
        isStandard = b;
	}

	public void display(){
		System.out.print("FunEntry(");
		if(formals != null)
		   formals.display();
		else
			System.out.print("null");
		System.out.print("-->");
		if(result != null)
		   result.display();
		else
			System.out.print("null");
		System.out.print(")");
	}
}