package Translate;

import Mips.MipsFrame;

/**
 *translate.Level records the parent child relation of functions
*/
public class Level {
	 /** the frame of current level */
	public Frame.Frame frame;
	/** parent level */
    public Level        parent;
	public AccessList formals;

    public static Level instance = new Level(null,null,null);
    
	public Level(Level p, Temp.Label name,Util.BoolList formalbools){
		parent = p;
		frame = MipsFrame.instance.newFrame(name, formalbools);
		Frame.AccessList pointer = frame.getFormals();
		while(pointer != null){
			Access access = new Access(this,pointer.head);
			formals = new AccessList(access,formals);
			pointer = pointer.tail;
		}
	}
	
	public void allocformal(Util.BoolList formalbools){
		//create a frame for the level
		frame = MipsFrame.instance.newFrame(frame.frameName(), formalbools);
		//get the formals in frame to construct the formals in level
		Frame.AccessList pointer = frame.getFormals();
		while(pointer != null){
			Access access = new Access(this,pointer.head);
			formals = new AccessList(access,formals);
			pointer = pointer.tail;
		}	
		formals = formals.reverse();
	}
	
	public Access allocLocal(boolean escape){
		return new Access(this,frame.allocLocal(escape));
	}
	
	public AccessList getformals(){
		return formals;
	}
	
	public Tree.Expv externalCall(String func,Tree.ExpList args){
    	return frame.externalCall(func, args);
    } 
	
	public int wordsize(){
		return frame.wordsize();
	}
	
	public Frame.Access staticLink(){
		return frame.staticLink();
	}
	
	public Temp.Temp FP(){
		return frame.FP();
	}
	
	public int size(){
		return ((Mips.MipsFrame)frame).frameSize;
	}
}
