package Translate;

public class ProcFrag extends Frag{
	public Frame.Frame frame;
	public Tree.Stm stm;
	
	public ProcFrag(Frame.Frame f,Tree.Stm s,ProcFrag p){
		frame = f;
		stm = s;
		tail = p;
	}
	
	public void display(){
		System.out.println(frame.frameName()+":");
		Semant.Semant.IRprint.prStm(stm);
	}
}
