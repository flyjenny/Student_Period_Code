package FindEscape;

import java.util.ArrayList;
import java.util.Enumeration;

public class FindEscape {
	public Symbol.Table escEnv; 
	
	public void traverseExp(int depth, Absyn.Exp e){

		if(e instanceof Absyn.ArrayExp)
			traverseExp(depth,(Absyn.ArrayExp)e);
		if(e instanceof Absyn.AssignExp)
			traverseExp(depth,(Absyn.AssignExp)e);
		if(e instanceof Absyn.BreakExp){}
		if(e instanceof Absyn.CallExp)
			traverseExp(depth,(Absyn.CallExp)e);
		if(e instanceof Absyn.ForExp)
			traverseExp(depth,(Absyn.ForExp)e);
		if(e instanceof Absyn.IfExp)
			traverseExp(depth,(Absyn.IfExp)e);		
		if(e instanceof Absyn.IntExp){}	
		if(e instanceof Absyn.LetExp)
			traverseExp(depth,(Absyn.LetExp)e);		
		if(e instanceof Absyn.NilExp){}
		if(e instanceof Absyn.OpExp)
			traverseExp(depth,(Absyn.OpExp)e);
		if(e instanceof Absyn.RecordExp)
			traverseExp(depth,(Absyn.RecordExp)e);		
		if(e instanceof Absyn.SeqExp)
			traverseExp(depth,(Absyn.SeqExp)e);				
		if(e instanceof Absyn.StringExp){}	      		
		if(e instanceof Absyn.VarExp)
			traverseExp(depth,(Absyn.VarExp)e);				
		if(e instanceof Absyn.WhileExp)
			traverseExp(depth,(Absyn.WhileExp)e);
	}

	public void traverseExp(int depth,Absyn.ArrayExp exp){
		traverseExp(depth,exp.size);
		traverseExp(depth,exp.init);		
	}
	
	public void traverseExp(int depth,Absyn.AssignExp exp){
		traverseVar(depth,exp.var);
		traverseExp(depth,exp.exp);
	}
	
	public void traverseExp(int depth,Absyn.CallExp exp){
		Absyn.ExpList pointer = exp.args;
		while(pointer != null){
			traverseExp(depth,pointer.head);
			pointer = pointer.tail;
		}
	}

	public void traverseExp(int depth,Absyn.ForExp exp){
		escEnv.beginScope();
		traverseDec(depth,exp.var);
		traverseExp(depth,exp.hi);
		traverseExp(depth,exp.body);
		escEnv.beginScope();
	}

	public void traverseExp(int depth,Absyn.IfExp exp){
		traverseExp(depth,exp.test);
		traverseExp(depth,exp.thenclause);
		if(exp.test != null)
			traverseExp(depth,exp.elseclause);
	}

	public void traverseExp(int depth,Absyn.LetExp exp){
		Absyn.DecList pointer = exp.decs;
		escEnv.beginScope();
		while(pointer != null){
			traverseDec(depth,pointer.head);
			pointer = pointer.tail;
		}
		traverseExp(depth,exp.body);
		escEnv.endScope();
	}

	public void traverseExp(int depth,Absyn.OpExp exp){
		traverseExp(depth,exp.left);
		traverseExp(depth,exp.right);
	}

	public void traverseExp(int depth,Absyn.RecordExp exp){
		Absyn.FieldExpList pointer = exp.fields;
		while(pointer != null){
			traverseExp(depth,pointer.init);
			pointer = pointer.tail;
		}
	}

	public void traverseExp(int depth,Absyn.SeqExp exp){
		Absyn.ExpList pointer = exp.list;
		while(pointer != null){
			traverseExp(depth,pointer.head);
			pointer = pointer.tail;
		}
	}

	public void traverseExp(int depth,Absyn.VarExp exp){
		traverseVar(depth,exp.var);
	}

	public void traverseExp(int depth,Absyn.WhileExp exp){
		traverseExp(depth,exp.test);
		traverseExp(depth,exp.body);
	}


	public void traverseDec(int depth, Absyn.Dec d){		
		if(d instanceof Absyn.VarDec)
			traverseDec(depth,(Absyn.VarDec)d);
		
		if(d instanceof Absyn.FunctionDec)
			traverseDec(depth,(Absyn.FunctionDec)d);
		
	}

	public void traverseDec(int depth, Absyn.VarDec dec){
		VarEscape varEscape = new VarEscape(depth,dec);
		boolean inserted = false;
		Enumeration<Symbol.Symbol> symbols = escEnv.keys();
		while(symbols.hasMoreElements()){
			Symbol.Symbol key = symbols.nextElement();
			if(key.toString().equals(dec.name.toString()))
				inserted = true;
		}
		
		if(!inserted)
		    escEnv.put(dec.name, varEscape);
		traverseExp(depth,dec.init);		
	}

	public void traverseDec(int depth, Absyn.FunctionDec dec){
		++depth;
		escEnv.beginScope();
		Absyn.FieldList pointer = dec.params;
		while(pointer != null){
			FormalEscape formalEscape = new FormalEscape(depth,pointer);
			escEnv.put(pointer.name, formalEscape);
			pointer = pointer.tail;
		}
		traverseExp(depth,dec.body);
		--depth;		
		if(dec.next != null)
			traverseDec(depth,dec.next);
		escEnv.endScope();
	}

	public void traverseVar(int depth, Absyn.Var v){
		if(v instanceof Absyn.FieldVar)
			traverseVar(depth,(Absyn.FieldVar)v);		
		if(v instanceof Absyn.SubscriptVar)			
			traverseVar(depth,(Absyn.SubscriptVar)v);
		if(v instanceof Absyn.SimpleVar)
			traverseVar(depth,(Absyn.SimpleVar)v);
	}

	public void traverseVar(int depth, Absyn.FieldVar var){
		traverseVar(depth,var.var);
	}   	
	
	public void traverseVar(int depth, Absyn.SubscriptVar var){
		traverseVar(depth,var.var);
		traverseExp(depth,var.index);
	}
	
	public void traverseVar(int depth, Absyn.SimpleVar var){
		Escape getEscape = (Escape)(escEnv.get(var.name));
		if(getEscape != null
				&& depth > getEscape.depth)
			getEscape.setEscape();
	}
	
	public FindEscape(Absyn.Exp e){
		escEnv = new Symbol.Table();
		traverseExp(0,e);
	}
	
}
