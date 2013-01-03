package Semant;

import Types.Type;

public class VarEntry extends Entry {
	Type ty;
	Translate.Access access;
	boolean cyclenum;

	VarEntry(Types.Type t,Translate.Access a) {
		ty = t;
		access = a;
	}
	
	void setcycle(){
		cyclenum = true;
	}
	
	boolean iscyclevar(){
		return cyclenum == true;
	}
	
	public void display(){
		System.out.print("VarEntry(");
		if(ty != null)
		    ty.display();
		else 
			System.out.print("null");
		System.out.print(")");
	}
}
