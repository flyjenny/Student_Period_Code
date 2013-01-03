package Translate;


public class Nx extends Exp{
	Tree.Stm stm;
	
	public Nx(Tree.Stm s){
		stm = s;
	}
	
	public Tree.Expv unEx(){
		return null;
	}
	
	public Tree.Stm unNx(){
		return stm;
	}
	
	public Tree.Stm unCx(Temp.Label t,Temp.Label f){
		return null;
	}
	
}
