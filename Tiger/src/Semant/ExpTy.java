package Semant;


class ExpTy {
	Translate.Exp exp;
	Types.Type type;

	ExpTy(Translate.Exp e, Types.Type ty) {
		exp = e;
		type = ty;
	}
}
