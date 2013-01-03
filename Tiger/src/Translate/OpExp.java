package Translate;

import Tiger.Overall;
import Tree.CONST;
import Tree.Expv;

public class OpExp extends Exp {
	int op;
	Exp left;
	Exp right;

	public OpExp(int o, Exp l, Exp r) {
		op = o;
		left = l;
		right = r;
	}

	public Tree.Expv unEx() {
		Temp.Temp value = new Temp.Temp();
		switch (op) {
		case Absyn.OpExp.PLUS:
		case Absyn.OpExp.MINUS:
		case Absyn.OpExp.MUL:
		case Absyn.OpExp.DIV:
			Tree.TEMP result = new Tree.TEMP(value);
			Tree.Expv leftExp = left.unEx();
			Tree.Expv rightExp = right.unEx();
			Tree.Expv binopExp = new Tree.BINOP(op,leftExp,rightExp);
			return new Tree.ESEQ(new Tree.MOVE(result,binopExp),result);
		case Absyn.OpExp.EQ:
		case Absyn.OpExp.NE:
		case Absyn.OpExp.GT:
		case Absyn.OpExp.LT:
		case Absyn.OpExp.GE:
		case Absyn.OpExp.LE:
			Tree.ESEQ valueESEQ = value();
			Tree.TEMP temp = new Tree.TEMP(new Temp.Temp());
			Tree.MOVE returnTrue = new Tree.MOVE(temp,CONST.ONE);
			Tree.LABEL falseLabel = new Tree.LABEL(new Temp.Label());
			Tree.MOVE returnFalse = new Tree.MOVE(temp,CONST.ZERO);
			Tree.LABEL exit = new Tree.LABEL(new Temp.Label());
            Tree.CJUMP branch = new Tree.CJUMP(match(op),valueESEQ,CONST.ZERO,exit.label,falseLabel.label);
            return new Tree.ESEQ(new Tree.SEQ(returnTrue,new Tree.SEQ(branch,
            		new Tree.SEQ(falseLabel,new Tree.SEQ(returnFalse,exit)))),temp);
		}
		return null;
	}

	public Tree.Stm unNx() {
		switch (op) {
		case Absyn.OpExp.PLUS:
		case Absyn.OpExp.MINUS:
		case Absyn.OpExp.MUL:
		case Absyn.OpExp.DIV:
			return new Tree.EXP(new Tree.BINOP(Tree.BINOP.PLUS, left.unEx(),
					right.unEx()));
		case Absyn.OpExp.EQ:
		case Absyn.OpExp.NE:
		case Absyn.OpExp.GT:
		case Absyn.OpExp.LT:
		case Absyn.OpExp.GE:
		case Absyn.OpExp.LE:
			return  new Tree.EXP(new Tree.BINOP(
					Tree.BINOP.MINUS, left.unEx(), right.unEx()));
		}
		return null;
	}

	private int match(int op) {
        switch(op){
        case Absyn.OpExp.EQ: return Tree.CJUMP.EQ;
        case Absyn.OpExp.NE: return Tree.CJUMP.NE;
        case Absyn.OpExp.GT: return Tree.CJUMP.GT;
        case Absyn.OpExp.GE: return Tree.CJUMP.GE;
        case Absyn.OpExp.LT: return Tree.CJUMP.LT;
        case Absyn.OpExp.LE: return Tree.CJUMP.LE;
        }
		return 0;
	}
	public Tree.Stm unCx(Temp.Label t, Temp.Label f) {
		switch (op) {
		case Absyn.OpExp.PLUS:
		case Absyn.OpExp.MINUS:
		case Absyn.OpExp.MUL:
		case Absyn.OpExp.DIV:
			return new Tree.CJUMP(Tree.CJUMP.NE, value(), Tree.CONST.ZERO, t, f);
		case Absyn.OpExp.EQ:
			return new Tree.CJUMP(Tree.CJUMP.EQ, left.unEx(),right.unEx(),t,f);
		case Absyn.OpExp.NE:
			return new Tree.CJUMP(Tree.CJUMP.NE, left.unEx(),right.unEx(),t,f);
		case Absyn.OpExp.GT:
			return new Tree.CJUMP(Tree.CJUMP.GT, left.unEx(),right.unEx(),t,f);	
		case Absyn.OpExp.LT:
			return new Tree.CJUMP(Tree.CJUMP.LT, left.unEx(),right.unEx(),t,f);	
		case Absyn.OpExp.GE:
			return new Tree.CJUMP(Tree.CJUMP.GE, left.unEx(),right.unEx(),t,f);	
		case Absyn.OpExp.LE:
			return new Tree.CJUMP(Tree.CJUMP.LE, left.unEx(),right.unEx(),t,f);	
		}
		Overall.myerror.error(0, "something hasn't been done!");
		return null;
	}

	private Tree.ESEQ value() {
		Temp.Temp value = new Temp.Temp();
		return new Tree.ESEQ(
				new Tree.MOVE(new Tree.TEMP(value), new Tree.BINOP(
						Tree.BINOP.MINUS, left.unEx(), right.unEx())),
				new Tree.TEMP(value));
	}
}
