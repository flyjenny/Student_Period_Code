package Semant;
import Absyn.Exp;
import Types.Type;

class ExpTy {
	Exp exp;
	Type ty;
	ExpTy(Exp e, Type t) { exp = e;	ty = t;	}
}