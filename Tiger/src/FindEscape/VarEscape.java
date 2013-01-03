package FindEscape;

class VarEscape extends Escape{
	Absyn.VarDec vd;
	
	VarEscape(int d,Absyn.VarDec v){
		depth = d;
		vd = v;
		vd.escape = false;
	}
	
	void setEscape(){
		vd.escape = true;
		if(Tiger.Overall.debug){
		   vd.name.display();
		   System.out.print(" is setEsaped"+"\n");
		}
	}
}
