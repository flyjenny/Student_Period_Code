package RegAlloc;
import java.util.HashSet;

import Mips.MipsFrame;

public class ProcRedundency {
	public void moveRedundency(Assem.InstrList instrs,Color color){
		Assem.InstrList lastInstr = findhead(instrs);
		Assem.InstrList pointer = lastInstr.tail;
		for(int i = 0;i != 8;++i,pointer = pointer.tail){
			if(color.isAlloced(pointer.head.use().tail.head)){
				lastInstr = pointer;
			}else{
				lastInstr.tail = pointer.tail;
			}
		}
		
		lastInstr = findtail(instrs);
		pointer = lastInstr.tail;
		for(int i = 0;i != 8;++i,pointer = pointer.tail){
			if(color.isAlloced(pointer.head.def().head)){
				lastInstr = pointer;
			}else{
				lastInstr.tail = pointer.tail;
			}
		}
	}
	
	Assem.InstrList findhead(Assem.InstrList instrs){

		Assem.InstrList pointer = instrs;
		while(pointer != null){
			if(	    pointer.tail.head.assem.length() >= 2
					&& pointer.tail.head.assem.substring(0,2).equals("sw")
					&& pointer.tail.head.use().tail.head == Mips.MipsFrame.calleeSaveRegs.tail.tail.tail.tail.tail.tail.tail.head
					&& pointer.tail.tail.head.assem.substring(0,2).equals("sw")
					&& pointer.tail.tail.head.use().tail.head == Mips.MipsFrame.calleeSaveRegs.tail.tail.tail.tail.tail.tail.head
					&& pointer.tail.tail.tail.head.assem.substring(0,2).equals("sw")
					&& pointer.tail.tail.tail.head.use().tail.head == Mips.MipsFrame.calleeSaveRegs.tail.tail.tail.tail.tail.head
					&& pointer.tail.tail.tail.tail.head.assem.substring(0,2).equals("sw")
					&& pointer.tail.tail.tail.tail.head.use().tail.head == Mips.MipsFrame.calleeSaveRegs.tail.tail.tail.tail.head
					&& pointer.tail.tail.tail.tail.tail.head.assem.substring(0,2).equals("sw")
					&& pointer.tail.tail.tail.tail.tail.head.use().tail.head == Mips.MipsFrame.calleeSaveRegs.tail.tail.tail.head
					&& pointer.tail.tail.tail.tail.tail.tail.head.assem.substring(0,2).equals("sw")
				    && pointer.tail.tail.tail.tail.tail.tail.head.use().tail.head == Mips.MipsFrame.calleeSaveRegs.tail.tail.head
					&& pointer.tail.tail.tail.tail.tail.tail.tail.head.assem.substring(0,2).equals("sw")
				    && pointer.tail.tail.tail.tail.tail.tail.tail.head.use().tail.head == Mips.MipsFrame.calleeSaveRegs.tail.head
					&& pointer.tail.tail.tail.tail.tail.tail.tail.tail.head.assem.substring(0,2).equals("sw")
					&& pointer.tail.tail.tail.tail.tail.tail.tail.tail.head.use().tail.head == Mips.MipsFrame.calleeSaveRegs.head
					)
				return pointer;
			pointer = pointer.tail;
		}
		return null;
	} 
	
	Assem.InstrList findtail(Assem.InstrList instrs){
		Assem.InstrList pointer = instrs;
		while(pointer != null){
			if(	         pointer.tail.head.assem.substring(0,2).equals("lw")
					&& pointer.tail.head.def().head == Mips.MipsFrame.calleeSaveRegs.head
					&& pointer.tail.tail.head.assem.substring(0,2).equals("lw")
					&& pointer.tail.tail.head.def().head == Mips.MipsFrame.calleeSaveRegs.tail.head
					&& pointer.tail.tail.tail.head.assem.substring(0,2).equals("lw")
					&& pointer.tail.tail.tail.head.def().head == Mips.MipsFrame.calleeSaveRegs.tail.tail.head
					&& pointer.tail.tail.tail.tail.head.assem.substring(0,2).equals("lw")
					&& pointer.tail.tail.tail.tail.head.def().head == Mips.MipsFrame.calleeSaveRegs.tail.tail.tail.head
					&& pointer.tail.tail.tail.tail.tail.head.assem.substring(0,2).equals("lw")
					&& pointer.tail.tail.tail.tail.tail.head.def().head == Mips.MipsFrame.calleeSaveRegs.tail.tail.tail.tail.head
					&& pointer.tail.tail.tail.tail.tail.tail.head.assem.substring(0,2).equals("lw")
				    && pointer.tail.tail.tail.tail.tail.tail.head.def().head == Mips.MipsFrame.calleeSaveRegs.tail.tail.tail.tail.tail.head
					&& pointer.tail.tail.tail.tail.tail.tail.tail.head.assem.substring(0,2).equals("lw")
				    && pointer.tail.tail.tail.tail.tail.tail.tail.head.def().head == Mips.MipsFrame.calleeSaveRegs.tail.tail.tail.tail.tail.tail.head
					&& pointer.tail.tail.tail.tail.tail.tail.tail.tail.head.assem.substring(0,2).equals("lw")
					&& pointer.tail.tail.tail.tail.tail.tail.tail.tail.head.def().head == Mips.MipsFrame.calleeSaveRegs.tail.tail.tail.tail.tail.tail.tail.head
					)
				return pointer;
			pointer = pointer.tail;
		}
		return null;

	}
	
	public void moveRedundency2(Assem.InstrList instrsAnalyzing) {
		Assem.InstrList instrPointer = instrsAnalyzing;
		while(instrPointer != null){
			if((isAdd(instrPointer.head)
					|| isSub(instrPointer.head))
					&& instrPointer.head.src != null
					&& instrPointer.head.src.tail == null){
				int right = Integer.parseInt(instrPointer.head.assem.substring(secondCommaIndex(instrPointer.head.assem)+1,instrPointer.head.assem.length()));
			    if(right == 0){
                    String newString = "";
			    	if(instrPointer.head.dst.head == instrPointer.head.src.head){
			    		newString = "";
					    instrPointer.head.dst = null;
					    instrPointer.head.src = null;
			    	}else{
			    		newString = "move `d0,`s0";
			    	}
			    	instrPointer.head.assem = newString;
			    	
			    }
			}
			instrPointer = instrPointer.tail;	
		}
	}
	
	public void moveRedundency3(Assem.InstrList instrsAnalyzing){
		Assem.InstrList swRaLast = instrsAnalyzing;
        Assem.InstrList swRaLast2 = null; 
		Assem.InstrList lwRaLast = instrsAnalyzing;
		Assem.InstrList lwRaLast2 = null;
		Assem.InstrList instrsPointer = instrsAnalyzing.tail;
		boolean disregardRa = true;
        while(instrsPointer != null){
        	if(isSw(instrsPointer.head)
        			&& instrsPointer.head.src.head == Mips.MipsFrame.fp
        			&& instrsPointer.head.src.tail.head == Mips.MipsFrame.ra){
        		swRaLast2 = swRaLast;
        	}
        	
        	if(isLw(instrsPointer.head)
        			&& instrsPointer.head.dst.head == Mips.MipsFrame.ra
        			&& instrsPointer.head.src.head == Mips.MipsFrame.fp){
        		lwRaLast2 = lwRaLast;
        	}
        	if(isJal(instrsPointer.head)){
        		disregardRa = false;
        	}
        	swRaLast = swRaLast.tail;
        	lwRaLast = lwRaLast.tail;
        	instrsPointer = instrsPointer.tail;
        }
        
        if(disregardRa){
        	swRaLast2.tail = swRaLast2.tail.tail;
        	lwRaLast2.tail = lwRaLast2.tail.tail;
        }
	}
	
	private int firstCommaIndex(String assem) {
		for (int i = 0; i != assem.length(); ++i)
			if (assem.substring(i, i + 1).equals(","))
				return i;
		return 0;
	}

	private int secondCommaIndex(String assem) {
		boolean findOne = false;
		for (int i = 0; i != assem.length(); ++i) {
			if (assem.substring(i, i + 1).equals(",")) {
				if (findOne)
					return i;
				else
					findOne = true;
			}
		}
		return 0;
	}

	private boolean isJal(Assem.Instr instr) {
		if (instr.assem.length() >= 3
				&& instr.assem.substring(0, 3).equals("jal"))
			return true;
		return false;
	}

	private boolean isSw(Assem.Instr instr) {
		if (instr.assem.length() >= 2
				&& instr.assem.substring(0, 2).equals("sw"))
			return true;
		return false;
	}

	private boolean isLw(Assem.Instr instr) {
		if (instr.assem.length() >= 2
				&& instr.assem.substring(0, 2).equals("lw"))
			return true;
		return false;
	}

	private int getNumFromLi(String assem) {
		return Integer.parseInt(assem.substring(assem.indexOf(",") + 1, assem
				.length()));
	}

	private int getNumFromASMDS(String assem) {
		return Integer.parseInt(assem.substring(secondCommaIndex(assem) + 1,
				assem.length()));
	}

	private boolean isBranch(Assem.Instr instr) {
		return isBeq(instr) || isBne(instr) || isBgt(instr) || isBge(instr)
				|| isBlt(instr) || isBle(instr);
	}

	private boolean canRightReplace(Assem.Instr instr) {
		return isAdd(instr) || isSub(instr) || isMul(instr) || isDiv(instr)
				|| isSll(instr) || isBeq(instr) || isBne(instr) || isBgt(instr)
				|| isBge(instr) || isBlt(instr) || isBle(instr);
	}

	private boolean isLi(Assem.Instr instr) {
		if (instr.assem.length() >= 2
				&& instr.assem.substring(0, 2).equals("li"))
			return true;
		return false;
	}

	private boolean isAdd(Assem.Instr instr) {
		if (instr.assem.length() >= 3
				&& instr.assem.substring(0, 3).equals("add"))
			return true;
		return false;
	}

	private boolean isSub(Assem.Instr instr) {
		if (instr.assem.length() >= 3
				&& instr.assem.substring(0, 3).equals("sub"))
			return true;
		return false;
	}

	private boolean isMul(Assem.Instr instr) {
		if (instr.assem.length() >= 3
				&& instr.assem.substring(0, 3).equals("mul"))
			return true;
		return false;
	}

	private boolean isDiv(Assem.Instr instr) {
		if (instr.assem.length() >= 3
				&& instr.assem.substring(0, 3).equals("div"))
			return true;
		return false;
	}

	private boolean isSll(Assem.Instr instr) {
		if (instr.assem.length() >= 3
				&& instr.assem.substring(0, 3).equals("sll"))
			return true;
		return false;
	}

	private boolean isBeq(Assem.Instr instr) {
		if (instr.assem.length() >= 3
				&& instr.assem.substring(0, 3).equals("beq"))
			return true;
		return false;
	}

	private boolean isBne(Assem.Instr instr) {
		if (instr.assem.length() >= 3
				&& instr.assem.substring(0, 3).equals("bne"))
			return true;
		return false;
	}

	private boolean isBgt(Assem.Instr instr) {
		if (instr.assem.length() >= 3
				&& instr.assem.substring(0, 3).equals("bgt"))
			return true;
		return false;
	}

	private boolean isBge(Assem.Instr instr) {
		if (instr.assem.length() >= 3
				&& instr.assem.substring(0, 3).equals("bge"))
			return true;
		return false;
	}

	private boolean isBlt(Assem.Instr instr) {
		if (instr.assem.length() >= 3
				&& instr.assem.substring(0, 3).equals("blt"))
			return true;
		return false;
	}

	private boolean isBle(Assem.Instr instr) {
		if (instr.assem.length() >= 3
				&& instr.assem.substring(0, 3).equals("ble"))
			return true;
		return false;
	}

	private boolean isMove(Assem.Instr instr) {
		if (instr.assem.length() >= 4
				&& instr.assem.substring(0, 4).equals("move"))
			return true;
		return false;
	}


	private boolean isAllocedTemps(Temp.Temp def) {
		Temp.TempList tempPointer = Mips.MipsFrame.instance.callerSaveRegs;
		while (tempPointer != null) {
			if (def == tempPointer.head)
				return true;
			tempPointer = tempPointer.tail;
		}

		tempPointer = Mips.MipsFrame.instance.calleeSaveRegs;
		while (tempPointer != null) {
			if (def == tempPointer.head)
				return true;
			tempPointer = tempPointer.tail;
		}

		return false;
	}

}
