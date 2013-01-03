package RegAlloc;

import java.util.ArrayList;
import java.util.HashMap;

public class RegAlloc implements Temp.TempMap{
	
	public Assem.InstrList instrs;
	public HashMap<Temp.Temp,Integer> tempMapToOffset = new HashMap<Temp.Temp,Integer>();
	
	public String tempMap(Temp.Temp temp){
		StringBuffer tempString = new StringBuffer();
		if(temp == Mips.MipsFrame.instance.at)
			tempString.append("$at");
		if(temp == Mips.MipsFrame.instance.fp)
			tempString.append("$fp");
		if(temp == Mips.MipsFrame.instance.gp)
			tempString.append("$gp");
		if(temp == Mips.MipsFrame.instance.k0)
			tempString.append("$k0");
		if(temp == Mips.MipsFrame.instance.k1)
			tempString.append("$k1");
		if(temp == Mips.MipsFrame.instance.ra)
			tempString.append("$ra");
		if(temp == Mips.MipsFrame.instance.sp)
			tempString.append("$sp");
		if(temp == Mips.MipsFrame.instance.zero)
			tempString.append("$0");
		if(temp == Mips.MipsFrame.instance.v0)
			tempString.append("$v0");
		if(temp == Mips.MipsFrame.instance.v1)
			tempString.append("$v1");
		int i;
		Temp.TempList pointer;

		for(pointer = Mips.MipsFrame.instance.argRegs,i = 0; pointer != null; pointer = pointer.tail,++i)
			if(temp == pointer.head)
				tempString.append("$a"+i);

		for(pointer = Mips.MipsFrame.instance.calleeSaveRegs,i = 0; pointer != null; pointer = pointer.tail,++i)
			if(temp == pointer.head)
				tempString.append("$s"+i);

		for(pointer = Mips.MipsFrame.callerSaveRegs,i = 0; pointer != null; pointer = pointer.tail,++i)
			if(temp == pointer.head)
				tempString.append("$t"+i);

		return tempString.toString();
	}
	
	public RegAlloc(Assem.InstrList il,Color color){
		Assem.InstrList instrPointer = il;
		while(instrPointer != null){
			Temp.TempList srcPointer = instrPointer.head.use();
			while(srcPointer != null){
				if(color.getRealTemp(srcPointer.head) != null)
				    srcPointer.head = color.getRealTemp(srcPointer.head);
				
				srcPointer = srcPointer.tail;
			}
			
			Temp.TempList dstPointer = instrPointer.head.def();
			while(dstPointer != null){
				if(color.getRealTemp(dstPointer.head) != null)
				    dstPointer.head = color.getRealTemp(dstPointer.head);
				
				dstPointer = dstPointer.tail;
			}
			instrPointer = instrPointer.tail;
		}
		instrs = il;
		removeMOVE();
	}
	
	//move the instructions like move $t1 $t1
	void removeMOVE(){
		Assem.InstrList lastOne = instrs;
		Assem.InstrList pointer = instrs.tail;
		while(pointer != null){
			if(pointer.head instanceof Assem.MOVE 
					&& ((Assem.MOVE)pointer.head).dst.head == ((Assem.MOVE)pointer.head).src.head){
		        lastOne.tail = pointer.tail;
			}else{
				lastOne = pointer;
			}
			pointer = pointer.tail;
		}
	}
	
	public ArrayList<String> finalInstr(){
		ArrayList<String> arrayInstr = new ArrayList<String>();
		Assem.InstrList instrPointer = instrs;
		int count = 0;
		while(instrPointer != null){
			String instr = instrPointer.head.format(this);
			arrayInstr.add(instr);
			instrPointer = instrPointer.tail;
			++count;
		}
		return arrayInstr;
	}

	public void spill(Frame.Frame f) {

		Mips.MipsFrame frame = (Mips.MipsFrame)f;		
		Integer frameSize = new Integer(frame.frameSize);
		ArrayList<Assem.Instr> instrsArray = new ArrayList<Assem.Instr>();
		for(Assem.InstrList pointer = instrs; pointer != null; pointer = pointer.tail){
			instrsArray.add(pointer.head);
		} 

//		Frame.Access access = f.allocLocal(true);
		for(int i = 0; i != instrsArray.size();){
			Assem.Instr instr = instrsArray.get(i);
			if(instr.dst != null
					&& isSpillTemp(instr.dst.head)){
				Temp.Temp key = instr.dst.head;
				if(!tempMapToOffset.containsKey(key)){
					frameSize += 4;
					tempMapToOffset.put(key, new Integer(-frameSize));
					instrsArray.add(i + 1,new Assem.OPER("sw $a0,"+tempMapToOffset.get(key)+"($fp)",null,null));
				    i += 2;
				}else{
					int originalOffset = tempMapToOffset.get(key);
					instrsArray.add(i + 1,new Assem.OPER("sw $a0,"+originalOffset+"($fp)",null,null));
				    i += 2;
				}
				continue;
			}
			++i;
		}
		
		for(int i = 0;i != instrsArray.size();){
			Assem.Instr instr = instrsArray.get(i);
			if(instr.src != null
					&& isSpillTemp(instr.src.head)){
				Temp.Temp key = instr.src.head;
				instrsArray.add(i,new Assem.OPER("lw $a1,"+tempMapToOffset.get(key)+"($fp)",null,null));
			    ++i;
			}
			
			if(instr.src != null
					&& instr.src.tail != null
					&& isSpillTemp(instr.src.tail.head)){
				Temp.Temp key = instr.src.tail.head;
				instrsArray.add(i,new Assem.OPER("lw $a2,"+tempMapToOffset.get(key)+"($fp)",null,null));
			    ++i;
			}
			++i;
		}

	    Temp.Temp $a0 = frame.argRegs.head;
	    Temp.Temp $a1 = frame.argRegs.tail.head;
	    Temp.Temp $a2 = frame.argRegs.tail.tail.head;
	
	    for(int i = 0; i != instrsArray.size(); ++i){
	    	Assem.Instr instr = instrsArray.get(i);
	    	Temp.TempList pointer = instr.dst;
	    	if(pointer != null
	    			&& isSpillTemp(pointer.head)){
	    		pointer.head = $a0;
	    	}
	    	
	    	pointer = instr.src;
	    	if(pointer != null
	    			&& isSpillTemp(pointer.head)){
	    		pointer.head = $a1;
	    	}
	    	
	    	if(pointer != null){
	    		pointer = pointer.tail;
	       		if(pointer != null
	    				&& isSpillTemp(pointer.head))
	    			pointer.head = $a2;	    		
	    	}
 	    }
	    
	    for(int i = 0; i != instrsArray.size(); ++i){
	    	Assem.Instr instr = instrsArray.get(i);
	    	if(isSub(instr)
	    			&& instr.dst.head == frame.sp
	    			&& instr.src.head == frame.sp){
	    		String newAssem = instr.assem.substring(0,secondCommaIndex(instr.assem)+1)+frameSize;
	    	    instr.assem = newAssem;
	    	    break;
	    	}
	    }
	    
	    instrs = null;
	    for(int i = instrsArray.size()-1; i >= 0 ; --i)
	    	instrs = new Assem.InstrList(instrsArray.get(i),instrs);
	}
	
	
	
	boolean isSpillTemp(Temp.Temp temp){
		return (tempMap(temp).length() == 0);
	}

	int getOffset(Temp.Temp temp){
		return tempMapToOffset.get(temp);
	}
	
	private boolean isSub(Assem.Instr instr){
		if(instr.assem.length() >= 3
				&& instr.assem.substring(0,3).equals("sub"))
			return true;
		return false;		
	}
	
	private int secondCommaIndex(String assem) {
		boolean findOne = false;
		for(int i = 0; i != assem.length(); ++i){
			if(assem.substring(i, i+1).equals(",")){
				if(findOne)
					return i;
				else
					findOne = true;
			}
		}
		return 0;
	}
}
