package Semant;
import Types.*;

class FuncEntry extends Entry {
	public RECORD formals;
	public Type result;
	//��������������
	FuncEntry(RECORD f, Type t){
		formals = f;
		result = t;
	}
}	