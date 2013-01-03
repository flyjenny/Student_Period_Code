package Semant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class RewriteAbsyn {
	
	HashMap<Symbol.Symbol,Absyn.FunctionDec> inlineMap = new HashMap<Symbol.Symbol,Absyn.FunctionDec>();
	HashMap<Symbol.Symbol,ArrayList<Symbol.Symbol>> callRecord = new HashMap<Symbol.Symbol,ArrayList<Symbol.Symbol>>();
	Symbol.Symbol main = new Symbol.Symbol("main");
	public Absyn.Exp inline(Absyn.Exp exp){
		Absyn.Exp newExp =  traverseExp(exp,main);
		newExp =  deleteDec(newExp);
		return newExp;
	}
	
	Absyn.Exp traverseExp(Absyn.Exp exp,Symbol.Symbol functionName){
		if(exp instanceof Absyn.ArrayExp)
			return traverseExp((Absyn.ArrayExp)exp,functionName);
		if(exp instanceof Absyn.AssignExp)
			return traverseExp((Absyn.AssignExp)exp,functionName);
		if(exp instanceof Absyn.BreakExp){return exp;}
		if(exp instanceof Absyn.CallExp){
			Absyn.FunctionDec functionDec = findDec(((Absyn.CallExp)exp).func);
			
			Symbol.Symbol key = functionName;
			ArrayList<Symbol.Symbol> callees = callRecord.get(key);
			if(callees != null){
				callees.add(((Absyn.CallExp)exp).func);
			}else{
				callees = new ArrayList<Symbol.Symbol>();
				callees.add(((Absyn.CallExp)exp).func);
				callRecord.put(key, callees);
			}
				
			if(functionDec != null && 
					!functionName.toString().equals(functionDec.name.toString())){								
				Absyn.LetExp ans = new Absyn.LetExp(exp.pos,Absyn.DecList.createDecList(functionDec.params,(((Absyn.CallExp)exp).args)),functionDec.body);		    				
				return ans;
			}
	        return exp;
		}
		if(exp instanceof Absyn.ForExp)
			return traverseExp((Absyn.ForExp)exp,functionName);
		if(exp instanceof Absyn.IfExp)
			return traverseExp((Absyn.IfExp)exp,functionName);
		if(exp instanceof Absyn.IntExp){return exp;}
		if(exp instanceof Absyn.LetExp)
			return traverseExp((Absyn.LetExp)exp,functionName);	
		if(exp instanceof Absyn.NilExp){return exp;}
		if(exp instanceof Absyn.OpExp)
			return traverseExp((Absyn.OpExp)exp,functionName);
		if(exp instanceof Absyn.RecordExp)
			return traverseExp((Absyn.RecordExp)exp,functionName);
		if(exp instanceof Absyn.SeqExp)
			return traverseExp((Absyn.SeqExp)exp,functionName);
		if(exp instanceof Absyn.StringExp){return exp;}
		if(exp instanceof Absyn.VarExp)
			return traverseExp((Absyn.VarExp)exp,functionName);		
		if(exp instanceof Absyn.WhileExp)
			return traverseExp((Absyn.WhileExp)exp,functionName);
		return exp;
	}
	
	Absyn.Exp traverseExp(Absyn.ArrayExp exp,Symbol.Symbol functionName){
		exp.size = traverseExp(exp.size,functionName);
		exp.init = traverseExp(exp.init,functionName);
		return exp;
	}
	
	Absyn.Exp traverseExp(Absyn.AssignExp exp,Symbol.Symbol functionName){
		exp.var = traverseVar(exp.var,functionName);
		exp.exp = traverseExp(exp.exp,functionName);
		return exp;
	}
	
	Absyn.Exp traverseExp(Absyn.ForExp exp,Symbol.Symbol functionName){
		exp.var = (Absyn.VarDec)traverseDec(exp.var,functionName);
		exp.hi = traverseExp(exp.hi,functionName);
		exp.body = traverseExp(exp.body,functionName);
		return exp;
	}
	
	Absyn.Exp traverseExp(Absyn.IfExp exp,Symbol.Symbol functionName){
		exp.test = traverseExp(exp.test,functionName);
		exp.thenclause = traverseExp(exp.thenclause,functionName);
		if(exp.elseclause != null)
			exp.elseclause = traverseExp(exp.elseclause,functionName);
		return exp;
	}	
	
	Absyn.Exp traverseExp(Absyn.LetExp exp,Symbol.Symbol functionName){
		Absyn.DecList decPointer = exp.decs;
		while(decPointer != null){
			decPointer.head = traverseDec(decPointer.head,functionName);
			decPointer = decPointer.tail;
		}
		exp.body = traverseExp(exp.body,functionName);
		return exp;
	}
	
	Absyn.Exp traverseExp(Absyn.OpExp exp,Symbol.Symbol functionName){
		exp.left = traverseExp(exp.left,functionName);
		exp.right = traverseExp(exp.right,functionName);
		return exp;
	}

	Absyn.Exp traverseExp(Absyn.RecordExp exp,Symbol.Symbol functionName){
		Absyn.FieldExpList fieldPointer = exp.fields;
		while(fieldPointer != null){
			fieldPointer.init = traverseExp(fieldPointer.init,functionName);
			fieldPointer = fieldPointer.tail;
		}	
		return exp;
	}

	Absyn.Exp traverseExp(Absyn.SeqExp exp,Symbol.Symbol functionName){
		Absyn.ExpList expPointer = exp.list;
		while(expPointer != null){
			expPointer.head = traverseExp(expPointer.head,functionName);
			expPointer = expPointer.tail;
		}
		return exp;
	}

	Absyn.Exp traverseExp(Absyn.VarExp exp,Symbol.Symbol functionName){
		exp.var = traverseVar(exp.var,functionName);
		return exp;
	}
	
	Absyn.Exp traverseExp(Absyn.WhileExp exp,Symbol.Symbol functionName){
			exp.test = traverseExp(exp.test,functionName);
			exp.body = traverseExp(exp.body,functionName);
			return exp;
	}

	Absyn.Var traverseVar(Absyn.Var var,Symbol.Symbol functionName){
		if(var instanceof Absyn.SimpleVar){return var;}
		if(var instanceof Absyn.SubscriptVar)
			return traverseVar((Absyn.SubscriptVar)var,functionName);
		if(var instanceof Absyn.FieldVar)
			return traverseVar((Absyn.FieldVar)var,functionName);
		return var;
	}
	
	Absyn.Var traverseVar(Absyn.SubscriptVar var,Symbol.Symbol functionName){
		var.index = traverseExp(var.index,functionName);
		var.var = traverseVar(var.var,functionName);
		return var;
	}
	
	Absyn.Var traverseVar(Absyn.FieldVar var,Symbol.Symbol functionName){
		var.var = traverseVar(var.var,functionName);
	    return var;
	}
	
	Absyn.Dec traverseDec(Absyn.Dec dec,Symbol.Symbol functionName){
		if(dec instanceof Absyn.VarDec)
			return traverseDec((Absyn.VarDec)dec,functionName);
		if(dec instanceof Absyn.TypeDec){return dec;}
		if(dec instanceof Absyn.FunctionDec)
			return traverseDec((Absyn.FunctionDec)dec,functionName);
		return dec;
	}
	
	Absyn.Dec traverseDec(Absyn.VarDec dec,Symbol.Symbol functionName){
		dec.init = traverseExp(dec.init,functionName);
		return dec;
	}
	
	Absyn.Dec traverseDec(Absyn.FunctionDec dec,Symbol.Symbol functionName){
		Absyn.FunctionDec decPointer = dec;
		while(decPointer != null){
		   if(!inlineMap.containsKey(decPointer.name))
		      inlineMap.put(decPointer.name, decPointer);
		   decPointer.body =traverseExp(decPointer.body,decPointer.name);
		   decPointer = decPointer.next;
		}
		return dec;
	}
	
	Absyn.FunctionDec findDec(Symbol.Symbol symbol){
		return inlineMap.get(symbol);
	}
	
	Absyn.Exp deleteDec(Absyn.Exp exp){
		if(exp instanceof Absyn.LetExp)
		return deleteDec((Absyn.LetExp)exp);
		return exp;
	}
	
	Absyn.Exp deleteDec(Absyn.LetExp exp){
		Absyn.DecList decList = exp.decs;
		while(decList != null){
			if(decList.head instanceof Absyn.FunctionDec){
				Absyn.FunctionDec functionDec = (Absyn.FunctionDec)decList.head;
				Absyn.FunctionDec funcDecPointer = functionDec;
				while( funcDecPointer != null){
					if(canBeDeleted(funcDecPointer.name)){
						funcDecPointer.result = null;
						funcDecPointer.body = new Absyn.SeqExp(0,null);
					}
					funcDecPointer = funcDecPointer.next;
				}
			}
			decList = decList.tail;
		}
		return exp;
	}
	
	boolean canBeDeleted(Symbol.Symbol functionName){
		ArrayList<Symbol.Symbol> callees = callRecord.get(functionName);
		if(callees == null)
			return true;
		boolean deleted = true;
	    for(int i = 0; i != callees.size(); ++i){
	    	HashSet<Symbol.Symbol> visitedFunc = new HashSet<Symbol.Symbol>();
	    	deleted = deleted && searchLoop(functionName,callees.get(i),visitedFunc);
	    }
	    return deleted;
	}
	
	boolean searchLoop(Symbol.Symbol caller,Symbol.Symbol callee,HashSet<Symbol.Symbol> visitedFunc){
		if(caller.toString().equals(callee.toString()))
			return false;
		visitedFunc.add(callee);
		boolean ans = true;
		ArrayList<Symbol.Symbol> deep = callRecord.get(callee);
		if(deep == null)
			return true;
		for(int i = 0; i != deep.size() ; ++i){
			if(!visitedFunc.contains(deep.get(i)))
			ans = ans && searchLoop(caller,deep.get(i),visitedFunc);
		}
		return ans;
	}
}
