package Semant;
import Types.*;

class FuncEntry extends Entry {
	public RECORD formals;
	public Type result;
	//参数表，返回类型
	FuncEntry(RECORD f, Type t){
		formals = f;
		result = t;
	}
}	