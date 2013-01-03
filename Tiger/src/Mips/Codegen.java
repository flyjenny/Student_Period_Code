package Mips;

import Tree.CJUMP;

import java.util.ArrayList;
import java.util.HashSet;

public class Codegen {
	MipsFrame frame;
    int functionNameCount = 0;
    HashSet<Temp.Label> labelRecord = new HashSet<Temp.Label>();
    
	public Codegen(MipsFrame f) {
		frame = f;
	}

	private Assem.InstrList ilist = null, last = null;

	private void emit(Assem.Instr inst) {
		if (last != null)
			last = last.tail = new Assem.InstrList(inst, null);
		else
			last = ilist = new Assem.InstrList(inst, null);
	}

	Assem.InstrList codegen(Tree.Stm s) {
		Assem.InstrList l;
		munchStm(s);
		l = ilist;
		ilist = last = null;
		return l;
	}

	void munchStm(Tree.Stm stm) {
		if (stm instanceof Tree.JUMP)
			munchStm((Tree.JUMP) stm);
		if (stm instanceof Tree.LABEL)
			munchStm((Tree.LABEL) stm);
		if (stm instanceof Tree.EXP)
			munchStm((Tree.EXP) stm);
		if (stm instanceof Tree.CJUMP)
			munchStm((Tree.CJUMP) stm);
		if (stm instanceof Tree.MOVE)
			munchStm((Tree.MOVE) stm);
	}

	Temp.Temp munchExp(Tree.Expv exp) {
		if (exp instanceof Tree.CONST)
			return munchExp((Tree.CONST) exp);
		if (exp instanceof Tree.TEMP)
			return munchExp((Tree.TEMP) exp);
		if (exp instanceof Tree.NAME)
			return munchExp((Tree.NAME) exp);
		if (exp instanceof Tree.BINOP)
			return munchExp((Tree.BINOP) exp);
		if (exp instanceof Tree.MEM)
			return munchExp((Tree.MEM) exp);
		if (exp instanceof Tree.CALL)
			return munchExp((Tree.CALL) exp);
		if (exp instanceof Tree.ESEQ)
			return munchExp((Tree.ESEQ) exp);
		return null;
	}

	Temp.Temp munchExp(Tree.CONST exp) {
		Temp.Temp dst = new Temp.Temp();
		Temp.TempList dstList = new Temp.TempList(dst, null);
		if(exp.value == 0)
			emit(new Assem.OPER("move `d0,`s0",dstList,new Temp.TempList(frame.zero,null)));
		else
		    emit(new Assem.OPER("li `d0," + exp.value, dstList, null));
		return dst;
	}

	Temp.Temp munchExp(Tree.TEMP exp) {
		return exp.temp;
	}

	Temp.Temp munchExp(Tree.NAME exp) {
		Temp.Temp dst = new Temp.Temp();
		Temp.TempList dstList = new Temp.TempList(dst, null);
		emit(new Assem.OPER("la `d0," + exp.label.toString(), dstList, null));
		return dst;
	}

	Temp.Temp munchExp(Tree.BINOP exp) {
		String BinOp = matchBinOp(exp.binop);
		Temp.Temp dst = new Temp.Temp();
		Temp.TempList dstList = new Temp.TempList(dst, null);
		
		if (exp.left instanceof Tree.CONST 
				&& exp.right instanceof Tree.CONST) {
			Tree.CONST left = (Tree.CONST) exp.left;
			Tree.CONST right = (Tree.CONST) exp.right;
			int value = 0;
			if (BinOp.equals("add"))
				value = left.value + right.value;
			else if (BinOp.equals("sub"))
				value = left.value - right.value;
			else if (BinOp.equals("mul"))
				value = left.value * right.value;
			else if (BinOp.equals("div"))
				value = left.value / right.value;
			emit(new Assem.OPER("li `d0," + value, dstList, null));
			
		} else if (exp.left instanceof Tree.CONST && (BinOp.equals("add") || BinOp.equals("mul"))) {
			
			     Tree.CONST left = (Tree.CONST) exp.left;
			     Temp.Temp src = munchExp(exp.right);
			     Temp.TempList srcList = new Temp.TempList(src, null);
	
			     boolean hasEmit = false;
			     if(BinOp.equals("mul")){
			    	 int shiftBits = shiftBits(left.value);
			    	 if(shiftBits != -1){
			    		 hasEmit = true;
			    		 emit(new Assem.OPER("sll `d0,`s0,"+shiftBits,dstList,srcList));
			    	 }
			     } 
			     
			     if(!hasEmit)
			        emit(new Assem.OPER(BinOp + " `d0,`s0,"+ left.value, dstList,srcList));
			     
		} else if (exp.right instanceof Tree.CONST) {
			Tree.CONST right = (Tree.CONST) exp.right;
			Temp.Temp src = munchExp(exp.left);
			Temp.TempList srcList = new Temp.TempList(src, null);
		     boolean hasEmit = false;
		     if(BinOp.equals("mul")){
		    	 int shiftBits = shiftBits(right.value);
		    	 if(shiftBits != -1){
		    		 hasEmit = true;
		    		 emit(new Assem.OPER("sll `d0,`s0,"+shiftBits,dstList,srcList));
		    	 }
		     } 
		     
		     if(!hasEmit)
					emit(new Assem.OPER(BinOp + " `d0,`s0," + right.value, dstList,
							srcList));

		} else {
			Temp.Temp left = munchExp(exp.left);
			Temp.Temp right = munchExp(exp.right);
			Temp.TempList srcList = new Temp.TempList(left, new Temp.TempList(
					right, null));
			emit(new Assem.OPER(BinOp + " `d0,`s0,`s1", dstList, srcList));
		}
		return dst;
	}

	Temp.Temp munchExp(Tree.MEM exp) {
		Temp.Temp dst = new Temp.Temp();
		Temp.TempList dstList = new Temp.TempList(dst, null);
		if (exp.exp instanceof Tree.BINOP
				&& ((Tree.BINOP) exp.exp).binop == Tree.BINOP.PLUS
				&& ((Tree.BINOP) exp.exp).left instanceof Tree.CONST) {
			Tree.BINOP binopExp = (Tree.BINOP) exp.exp;
			Tree.CONST leftConst = (Tree.CONST) binopExp.left;
			Temp.Temp right = munchExp(binopExp.right);
			Temp.TempList srcList = new Temp.TempList(right, null);
			emit(new Assem.OPER("lw `d0," + leftConst.value + "(`s0)", dstList,
					srcList));
		} else if (exp.exp instanceof Tree.BINOP
				&& ((Tree.BINOP) exp.exp).binop == Tree.BINOP.PLUS
				&& ((Tree.BINOP) exp.exp).right instanceof Tree.CONST) {
			Tree.BINOP binopExp = (Tree.BINOP) exp.exp;
			Tree.CONST rightConst = (Tree.CONST) binopExp.right;
			Temp.Temp left = munchExp(binopExp.left);
			Temp.TempList srcList = new Temp.TempList(left, null);
			emit(new Assem.OPER("lw `d0," + rightConst.value + "(`s0)",
					dstList, srcList));
		} else if (exp.exp instanceof Tree.CONST) {
			Tree.CONST constExp = (Tree.CONST) exp.exp;
			emit(new Assem.OPER("lw `d0," + constExp.value, dstList, null));
		} else {
			Temp.Temp srcTemp = munchExp(exp.exp);
			Temp.TempList srcList = new Temp.TempList(srcTemp, null);
			emit(new Assem.OPER("lw `d0,0(`s0)", dstList, srcList));
		}
		return dst;
	}

	Temp.Temp munchExp(Tree.CALL exp) {
	
		    if(((Tree.NAME) exp.func).label.toString().equals("_print")){
		    	if(exp.args.head instanceof Tree.NAME){
					emit(new Assem.OPER("la `d0,"+ ((Tree.NAME) exp.args.head).label.toString(), new Temp.TempList(Mips.MipsFrame.argRegs.head,null), null));
		    		emit(new Assem.OPER("li `d0,4",new Temp.TempList(Mips.MipsFrame.v0,null),null));
		    		emit(new Assem.OPER("syscall",null,new Temp.TempList(Mips.MipsFrame.argRegs.head,new Temp.TempList(Mips.MipsFrame.v0,null))));
		    	}else{
					Temp.Temp src = munchExp(exp.args.head);
					emit(new Assem.OPER("move `d0,`s0", new Temp.TempList(Mips.MipsFrame.argRegs.head,null),
							new Temp.TempList(src, null)));
		    		emit(new Assem.OPER("li `d0,4",new Temp.TempList(Mips.MipsFrame.v0,null),null));
		    		emit(new Assem.OPER("syscall",null,new Temp.TempList(Mips.MipsFrame.argRegs.head,new Temp.TempList(Mips.MipsFrame.v0,null))));
				    
		    	}
		    	return frame.RV();
		    }
		  
		    if(((Tree.NAME) exp.func).label.toString().equals("_printi")){
		    	if(exp.args.head instanceof Tree.CONST){
					emit(new Assem.OPER("li `d0,"+ ((Tree.CONST) exp.args.head).value, new Temp.TempList(Mips.MipsFrame.argRegs.head,null), null));
		    		emit(new Assem.OPER("li `d0,1",new Temp.TempList(Mips.MipsFrame.v0,null),null));
		    		emit(new Assem.OPER("syscall",null,new Temp.TempList(Mips.MipsFrame.argRegs.head,new Temp.TempList(Mips.MipsFrame.v0,null))));
		    	}
		    	else{
					Temp.Temp src = munchExp(exp.args.head);
					emit(new Assem.OPER("move `d0,`s0", new Temp.TempList(Mips.MipsFrame.argRegs.head,null),
							new Temp.TempList(src, null)));
		    		emit(new Assem.OPER("li `d0,1",new Temp.TempList(Mips.MipsFrame.v0,null),null));
		    		emit(new Assem.OPER("syscall",null,new Temp.TempList(Mips.MipsFrame.argRegs.head,new Temp.TempList(Mips.MipsFrame.v0,null))));
		    	}
		    	return frame.RV();
		    }


		    if(((Tree.NAME) exp.func).label.toString().equals("_initArray")){
		    	if(exp.args.head instanceof Tree.CONST){
					emit(new Assem.OPER("li `d0,"+ ((Tree.CONST) exp.args.head).value, new Temp.TempList(Mips.MipsFrame.argRegs.head,null), null));
		    	}else{
					Temp.Temp src = munchExp(exp.args.head);
					emit(new Assem.OPER("move `d0,`s0", new Temp.TempList(Mips.MipsFrame.argRegs.head,null),
							new Temp.TempList(src, null)));
		    	}

		    	if(exp.args.tail != null){
			    	if(exp.args.tail.head instanceof Tree.CONST){
						emit(new Assem.OPER("li `d0,"+ ((Tree.CONST) exp.args.tail.head).value, new Temp.TempList(Mips.MipsFrame.argRegs.tail.head
								,null), null));
			    	}else{
						Temp.Temp src = munchExp(exp.args.tail.head);
						emit(new Assem.OPER("move `d0,`s0", new Temp.TempList(Mips.MipsFrame.argRegs.tail.head,null),
								new Temp.TempList(src, null)));
			    	}		    		
		    	}
		    	emit(new Assem.OPER("jal _initArray",new Temp.TempList(frame.v0,new Temp.TempList(frame.v1,frame.argRegs)),frame.argRegs));
		        return frame.RV();
		    }
		    
		    ArrayList<Tree.Expv> callArgs = new ArrayList<Tree.Expv>();
		    Tree.ExpList argPointer = exp.args;
		    while(argPointer != null){
		    	callArgs.add(argPointer.head);
		    	argPointer = argPointer.tail;
		    }
		    int argSize = (callArgs.size() - 4) * 4;
		    if(argSize > 0)
		    	emit(new Assem.OPER("sub `d0,`s0,"+argSize,new Temp.TempList(Mips.MipsFrame.instance.sp,null),new Temp.TempList(Mips.MipsFrame.instance.sp,null)));
			Tree.ExpList pointer = exp.args;
			int argcounter = 0;
			Temp.TempList argRegsPointer = Mips.MipsFrame.argRegs;
			Temp.TempList srcArgRegs = null;
			while (pointer != null) {
				if(argcounter < 4){
					if (pointer.head instanceof Tree.CONST) {
						emit(new Assem.OPER("li `d0,"+ ((Tree.CONST) pointer.head).value, new Temp.TempList(argRegsPointer.head,null), null));
					}else if(pointer.head instanceof Tree.NAME){
						emit(new Assem.OPER("la `d0,"+((Tree.NAME)pointer.head).label.toString(),new Temp.TempList(argRegsPointer.head,null),null));
					}else if(pointer.head instanceof Tree.BINOP
							&& ((Tree.BINOP)pointer.head).left instanceof Tree.TEMP
							&& ((Tree.BINOP)pointer.head).right instanceof Tree.TEMP){
						Tree.BINOP biNop = (Tree.BINOP)pointer.head;
						emit(new Assem.OPER(matchBinOp(biNop.binop)+" `d0,`s0,`s1",new Temp.TempList(argRegsPointer.head,null),
						new Temp.TempList(((Tree.TEMP)biNop.left).temp,new Temp.TempList(((Tree.TEMP)biNop.right).temp,null))));
					}else if(pointer.head instanceof Tree.BINOP
							&& ((Tree.BINOP)pointer.head).left instanceof Tree.TEMP
							&& ((Tree.BINOP)pointer.head).right instanceof Tree.CONST){
						Tree.BINOP biNop = (Tree.BINOP)pointer.head;
						emit(new Assem.OPER(matchBinOp(biNop.binop)+" `d0,`s0,"+((Tree.CONST)((Tree.BINOP)pointer.head).right).value
								,new Temp.TempList(argRegsPointer.head,null)
								,new Temp.TempList(((Tree.TEMP)biNop.left).temp,null)));
					}else if (pointer.head instanceof Tree.MEM
							&& ((Tree.MEM) pointer.head).exp instanceof Tree.BINOP
							&& ((Tree.BINOP) (((Tree.MEM) pointer.head).exp)).binop == Tree.BINOP.PLUS
							&& ((Tree.BINOP) (((Tree.MEM) pointer.head).exp)).left instanceof Tree.CONST) {

						Tree.BINOP binopExp = (Tree.BINOP) (((Tree.MEM)pointer.head).exp);
						Tree.CONST offset = (Tree.CONST) binopExp.left;
						Temp.Temp srcReg = munchExp(binopExp.right);
						Temp.TempList srcList = new Temp.TempList(srcReg, null);
						emit(new Assem.OPER("lw `d0," + offset.value + "(`s0)",
								new Temp.TempList(argRegsPointer.head,null), srcList));
					} else if (pointer.head instanceof Tree.MEM
							&& ((Tree.MEM) pointer.head).exp instanceof Tree.BINOP
							&& ((Tree.BINOP) (((Tree.MEM)pointer.head).exp)).binop == Tree.BINOP.PLUS
							&& ((Tree.BINOP) (((Tree.MEM)pointer.head).exp)).right instanceof Tree.CONST) {

						Tree.BINOP binopExp = (Tree.BINOP) (((Tree.MEM) pointer.head).exp);
						Tree.CONST offset = (Tree.CONST) binopExp.right;
						Temp.Temp srcReg = munchExp(binopExp.left);
						Temp.TempList srcList = new Temp.TempList(srcReg, null);
						emit(new Assem.OPER("lw `d0," + offset.value + "(`s0)",
								new Temp.TempList(argRegsPointer.head,null), srcList));
					}else {
						Temp.Temp src = munchExp(pointer.head);
						emit(new Assem.OPER("move `d0,`s0", new Temp.TempList(argRegsPointer.head,null),
								new Temp.TempList(src, null)));
					}
					srcArgRegs = new Temp.TempList(argRegsPointer.head,srcArgRegs);
					argRegsPointer = argRegsPointer.tail;
				}else{
					Frame.Access access = frame.allocLocal(true);
					if(callArgs.get(callArgs.size()+3 - argcounter) instanceof Tree.CONST){
						emit(new Assem.OPER("li `d0,"+((Tree.CONST) callArgs.get(callArgs.size()+3 - argcounter)).value,new Temp.TempList(frame.v1,null),null));
						emit(new Assem.OPER("sw `s0,"+(-((Mips.InFrame)access).offset)+"(`s1)",null,new Temp.TempList(frame.v1,new Temp.TempList(frame.fp,null))));						
					}else{
						Temp.Temp src = munchExp( callArgs.get(callArgs.size()+3 -argcounter));
						emit(new Assem.OPER("sw `s0,"+(-((Mips.InFrame)access).offset)+"(`s1)",null,new Temp.TempList(src,new Temp.TempList(frame.fp,null))));												
					}
				}
				++argcounter;					
				pointer = pointer.tail;
			}
			emit(new Assem.OPER("jal " + ((Tree.NAME) exp.func).label.toString(),new Temp.TempList(Mips.MipsFrame.instance.v0,new Temp.TempList(Mips.MipsFrame.instance.v1,Mips.MipsFrame.instance.callDefs)),srcArgRegs));
		    if(argSize > 0){
		    	((Mips.MipsFrame)frame).frameSize -= argSize;
		    	emit(new Assem.OPER("add `d0,`s0,"+argSize,new Temp.TempList(Mips.MipsFrame.instance.sp,null),new Temp.TempList(Mips.MipsFrame.instance.sp,null)));		    	
		    }
			return frame.RV();
	}

	Temp.Temp munchExp(Tree.ESEQ exp){
		munchStm(exp.stm);
		Temp.Temp temp = munchExp(exp.exp);
		return temp;
	}
	
	void munchStm(Tree.JUMP stm) {
		emit(new Assem.OPER("j " + stm.targets.head.toString(), null, null,
				stm.targets));
	}

	void munchStm(Tree.LABEL stm) {
		String label = stm.label.toString();
		emit(new Assem.LABEL(label + ":", stm.label));			
	}

	void munchStm(Tree.EXP stm) {
		munchExp(stm.exp);
	}

	void munchStm(Tree.CJUMP stm) {
		String RelOp = matchRelOp(stm.relop);
		if(stm.left instanceof Tree.CONST
				&& stm.right instanceof Tree.CONST){
			int left = ((Tree.CONST)stm.left).value;
			int right = ((Tree.CONST)stm.right).value;
			boolean jumpTrue = false;
			if(RelOp.equals("beq")){
				if(left == right)
					jumpTrue = true;
			}else if(RelOp.equals("bne")){
				if(left != right)
					jumpTrue = true;
			}else if(RelOp.equals("bgt")){
				if(left > right)
					jumpTrue = true;				
			}else if(RelOp.equals("bge")){
				if(left >= right)
					jumpTrue = true;
			}else if(RelOp.equals("blt")){
				if(left < right)
					jumpTrue = true;				
			}else if(RelOp.equals("ble")){
				if(left <= right)
					jumpTrue = true;
			}
			if(jumpTrue){
				Temp.LabelList targets = new Temp.LabelList(stm.iftrue,null);
				emit(new Assem.OPER("j "+stm.iftrue.toString(),null,null,targets));
			}
			else{
				Temp.LabelList targets = new Temp.LabelList(stm.iffalse,null);
				emit(new Assem.OPER("j "+stm.iffalse.toString(),null,null,targets));
			}

		}else if(stm.right instanceof Tree.CONST){
		    Temp.Temp leftTemp = munchExp(stm.left);
		    int right = ((Tree.CONST)stm.right).value;
		    Temp.TempList srcList = new Temp.TempList(leftTemp, null);
	    	Temp.LabelList branchList = new Temp.LabelList(stm.iftrue,
			    	new Temp.LabelList(stm.iffalse, null));
	    	if(right == 0)
	    		emit(new Assem.OPER(RelOp + " `s0,`s1,`j0", null, new Temp.TempList(leftTemp,new Temp.TempList(frame.zero,null)), branchList));
	    	else
		       emit(new Assem.OPER(RelOp + " `s0,"+right+",`j0", null, srcList, branchList));			
		}else if(stm.left instanceof Tree.CONST){
			int left = ((Tree.CONST)stm.left).value;
			Temp.Temp rightTemp = munchExp(stm.right);
		    Temp.TempList srcList = new Temp.TempList(rightTemp, null);
	    	Temp.LabelList branchList = new Temp.LabelList(stm.iftrue,
			    	new Temp.LabelList(stm.iffalse, null));
            
	    	if(RelOp.equals("beq") || RelOp.equals("bne")){
			    emit(new Assem.OPER(RelOp + " `s0,"+left+",`j0", null, srcList, branchList));			            	
            }else if(RelOp.equals("bgt")){
			    emit(new Assem.OPER("blt `s0,"+left+",`j0", null, srcList, branchList));			            	            	
            }else if(RelOp.equals("bge")){
			    emit(new Assem.OPER("ble `s0,"+left+",`j0", null, srcList, branchList));			            	            	            	
            }else if(RelOp.equals("blt")){
			    emit(new Assem.OPER("bgt `s0,"+left+",`j0", null, srcList, branchList));			            	            	
            }else if(RelOp.equals("ble")){
			    emit(new Assem.OPER("bge `s0,"+left+",`j0", null, srcList, branchList));			            	            	
            }
		}else{
		    Temp.Temp leftTemp = munchExp(stm.left);
		    Temp.Temp rightTemp = munchExp(stm.right);
		    Temp.TempList srcList = new Temp.TempList(leftTemp, new Temp.TempList(
				 rightTemp, null));
	    	Temp.LabelList branchList = new Temp.LabelList(stm.iftrue,
			    	new Temp.LabelList(stm.iffalse, null));
		    emit(new Assem.OPER(RelOp + "`s0,`s1,`j0", null, srcList, branchList));
		}
	}

	void munchStm(Tree.MOVE stm) {
		
		if (stm.dst instanceof Tree.TEMP) {
			Temp.Temp dst = ((Tree.TEMP) stm.dst).temp;
			Temp.TempList dstList = new Temp.TempList(dst, null);

			if (stm.src instanceof Tree.CONST) {
				Tree.CONST src = (Tree.CONST) stm.src;
				if(src.value == 0)
					emit(new Assem.OPER("move `d0,`s0",dstList,new Temp.TempList(frame.zero,null)));
				else
				    emit(new Assem.OPER("li `d0," + src.value, dstList, null));
			} else if (stm.src instanceof Tree.MEM
					&& ((Tree.MEM) stm.src).exp instanceof Tree.BINOP
					&& ((Tree.BINOP) (((Tree.MEM) stm.src).exp)).binop == Tree.BINOP.PLUS
					&& ((Tree.BINOP) (((Tree.MEM) stm.src).exp)).left instanceof Tree.CONST) {

				Tree.BINOP binopExp = (Tree.BINOP) (((Tree.MEM) stm.src).exp);
				Tree.CONST offset = (Tree.CONST) binopExp.left;
				Temp.Temp srcReg = munchExp(binopExp.right);
				Temp.TempList srcList = new Temp.TempList(srcReg, null);
				emit(new Assem.OPER("lw `d0," + offset.value + "(`s0)",
						dstList, srcList));
			} else if (stm.src instanceof Tree.MEM
					&& ((Tree.MEM) stm.src).exp instanceof Tree.BINOP
					&& ((Tree.BINOP) (((Tree.MEM) stm.src).exp)).binop == Tree.BINOP.PLUS
					&& ((Tree.BINOP) (((Tree.MEM) stm.src).exp)).right instanceof Tree.CONST) {

				Tree.BINOP binopExp = (Tree.BINOP) (((Tree.MEM) stm.src).exp);
				Tree.CONST offset = (Tree.CONST) binopExp.right;
				Temp.Temp srcReg = munchExp(binopExp.left);
				Temp.TempList srcList = new Temp.TempList(srcReg, null);
				emit(new Assem.OPER("lw `d0," + offset.value + "(`s0)",
						dstList, srcList));
			}else if(stm.src instanceof Tree.BINOP
					&& ((Tree.BINOP)stm.src).left instanceof Tree.CONST
					&& ((Tree.BINOP)stm.src).right instanceof Tree.CONST){
				
				Tree.BINOP binopSrc = (Tree.BINOP)stm.src;
				String binOp = matchBinOp(binopSrc.binop);
				int value = 0;
				if(binOp.equals("add")){
					value = ((Tree.CONST)binopSrc.left).value + ((Tree.CONST)binopSrc.right).value;
				}else if(binOp.equals("sub")){
					value = ((Tree.CONST)binopSrc.left).value - ((Tree.CONST)binopSrc.right).value;
				}else if(binOp.equals("mul")){
					value = ((Tree.CONST)binopSrc.left).value * ((Tree.CONST)binopSrc.right).value;
				}else if(binOp.equals("div")){
					value = ((Tree.CONST)binopSrc.left).value / ((Tree.CONST)binopSrc.right).value;
				}
				emit(new Assem.OPER("li `d0,"+value,dstList,null));
			}else if(stm.src instanceof Tree.BINOP
					&& ((Tree.BINOP)stm.src).left instanceof Tree.TEMP
					&& ((Tree.BINOP)stm.src).right instanceof Tree.TEMP) {
				Tree.BINOP binopSrc = (Tree.BINOP)stm.src;
				String binOp = matchBinOp(binopSrc.binop);
				Temp.TempList srcList = new Temp.TempList(((Tree.TEMP)((Tree.BINOP)stm.src).left).temp,
						new Temp.TempList(((Tree.TEMP)((Tree.BINOP)stm.src).right).temp,null));
				emit(new Assem.OPER(binOp+" `d0,`s0,`s1",dstList,srcList));
			}else if(stm.src instanceof Tree.BINOP
					&& ((Tree.BINOP)stm.src).left instanceof Tree.TEMP
					&& ((Tree.BINOP)stm.src).right instanceof Tree.CONST) {
				Tree.BINOP binopSrc = (Tree.BINOP)stm.src;
				String binOp = matchBinOp(binopSrc.binop);
				Temp.TempList srcList = new Temp.TempList(((Tree.TEMP)((Tree.BINOP)stm.src).left).temp,null);
				int right = ((Tree.CONST)((Tree.BINOP)stm.src).right).value;
				
			     boolean hasEmit = false;
			     if(binOp.equals("mul")){
			    	 int shiftBits = shiftBits(right);
			    	 if(shiftBits != -1){
			    		 hasEmit = true;
			    		 emit(new Assem.OPER("sll `d0,`s0,"+shiftBits,dstList,srcList));
			    	 }
			     } 
			     
			     if(!hasEmit)
						emit(new Assem.OPER(binOp+" `d0,`s0,"+right,dstList,srcList));
			     
			}else if(stm.src instanceof Tree.BINOP
					&& ((Tree.BINOP)stm.src).right instanceof Tree.TEMP
					&& ((Tree.BINOP)stm.src).left instanceof Tree.CONST
					&& (((Tree.BINOP)stm.src).binop == Tree.BINOP.PLUS || ((Tree.BINOP)stm.src).binop == Tree.BINOP.MUL)) {
				Tree.BINOP binopSrc = (Tree.BINOP)stm.src;
				String binOp = matchBinOp(binopSrc.binop);
				Temp.TempList srcList = new Temp.TempList(((Tree.TEMP)((Tree.BINOP)stm.src).right).temp,null);
				int left = ((Tree.CONST)((Tree.BINOP)stm.src).left).value;
			    boolean hasEmit = false;
			    if(binOp.equals("mul")){
			    	 int shiftBits = shiftBits(left);
			    	 if(shiftBits != -1){
			    		 hasEmit = true;
			    		 emit(new Assem.OPER("sll `d0,`s0,"+shiftBits,dstList,srcList));
			    	 }
			    } 
			     
			     if(!hasEmit)
						emit(new Assem.OPER(binOp+" `d0,`s0,"+left,dstList,srcList));

			}else if(stm.src instanceof Tree.BINOP
					&& ((Tree.BINOP)stm.src).left instanceof Tree.TEMP){
				
				Tree.BINOP binopSrc = (Tree.BINOP)stm.src;
				String binOp = matchBinOp(binopSrc.binop);
				Temp.Temp src = munchExp(binopSrc.right);
                Temp.TempList srcList = new Temp.TempList(((Tree.TEMP)binopSrc.left).temp,new Temp.TempList(src,null));
				emit(new Assem.OPER(binOp+" `d0,`s0,`s1",dstList,srcList));
				
			}else if(stm.src instanceof Tree.BINOP
					&& ((Tree.BINOP)stm.src).right instanceof Tree.TEMP){
				Tree.BINOP binopSrc = (Tree.BINOP)stm.src;
				String binOp = matchBinOp(binopSrc.binop);
				Temp.Temp src = munchExp(binopSrc.left);
                Temp.TempList srcList = new Temp.TempList(src,new Temp.TempList(((Tree.TEMP)binopSrc.right).temp,null));
				emit(new Assem.OPER(binOp+" `d0,`s0,`s1",dstList,srcList));
				
			}else if(stm.src instanceof Tree.BINOP
					&& ((Tree.BINOP)stm.src).left instanceof Tree.CONST
					&& (((Tree.BINOP)stm.src).binop == Tree.BINOP.PLUS || ((Tree.BINOP)stm.src).binop == Tree.BINOP.MUL)){
				
				Tree.BINOP binopSrc = (Tree.BINOP)stm.src;
				String binOp = matchBinOp(binopSrc.binop);
				Temp.Temp src = munchExp(binopSrc.right);
				int left = ((Tree.CONST)binopSrc.left).value;
				Temp.TempList srcList = new Temp.TempList(src,null);
				
			    boolean hasEmit = false;
			    if(binOp.equals("mul")){
			    	 int shiftBits = shiftBits(left);
			    	 if(shiftBits != -1){
			    		 hasEmit = true;
			    		 emit(new Assem.OPER("sll `d0,`s0,"+shiftBits,dstList,srcList));
			    	 }
			    } 
			     
			     if(!hasEmit)
			 		emit(new Assem.OPER(binOp+" `d0,`s0,"+left,dstList,srcList));
					
				
			}else if(stm.src instanceof Tree.BINOP
					&& ((Tree.BINOP)stm.src).right instanceof Tree.CONST){
				
				Tree.BINOP binopSrc = (Tree.BINOP)stm.src;
				String binOp = matchBinOp(binopSrc.binop);
				Temp.Temp src = munchExp(binopSrc.left);
				int right = ((Tree.CONST)binopSrc.right).value;
				Temp.TempList srcList = new Temp.TempList(src,null);
				
			     boolean hasEmit = false;
			     if(binOp.equals("mul")){
			    	 int shiftBits = shiftBits(right);
			    	 if(shiftBits != -1){
			    		 hasEmit = true;
			    		 emit(new Assem.OPER("sll `d0,`s0,"+shiftBits,dstList,srcList));
			    	 }
			     } 
			     
			     if(!hasEmit)
						emit(new Assem.OPER(binOp+" `d0,`s0,"+right,dstList,srcList));
				
			}else if(stm.src instanceof Tree.NAME){
				emit(new Assem.OPER("la `d0,"+((Tree.NAME)stm.src).label.toString(),new Temp.TempList(dst,null),null));
			}else {
				Temp.Temp src = munchExp(stm.src);
				emit(new Assem.MOVE("move `d0,`s0", dst, src));
			}

		} else if (stm.dst instanceof Tree.MEM) {
			Tree.MEM dst = (Tree.MEM) stm.dst;
			Temp.Temp src = munchExp(stm.src);
			Temp.TempList srcList = new Temp.TempList(src, null);
			if (dst.exp instanceof Tree.BINOP
					&& ((Tree.BINOP) dst.exp).binop == Tree.BINOP.PLUS
					&& ((Tree.BINOP) dst.exp).left instanceof Tree.CONST) {
				Tree.CONST offset = (Tree.CONST) (((Tree.BINOP) dst.exp).left);
				Temp.Temp secondsrc = munchExp(((Tree.BINOP) dst.exp).right);
                srcList = new Temp.TempList(secondsrc,srcList);
				emit(new Assem.OPER("sw `s1," + offset.value + "(`s0)",
						null, srcList));
			} else if (dst.exp instanceof Tree.BINOP
					&& ((Tree.BINOP) dst.exp).binop == Tree.BINOP.PLUS
					&& ((Tree.BINOP) dst.exp).right instanceof Tree.CONST) {
				Tree.CONST offset = (Tree.CONST) (((Tree.BINOP) dst.exp).right);
				Temp.Temp secondsrc = munchExp(((Tree.BINOP) dst.exp).left);
                srcList = new Temp.TempList(secondsrc,srcList);
				emit(new Assem.OPER("sw `s1," + offset.value + "(`s0)",
						null, srcList));
			} else if (dst.exp instanceof Tree.CONST) {
				Tree.CONST addr = (Tree.CONST) dst.exp;
				emit(new Assem.OPER("sw `s1," + addr.value, null, srcList));
			} else {
				Temp.Temp secondsrc = munchExp(dst.exp);
	            srcList = new Temp.TempList(secondsrc,srcList);
				emit(new Assem.OPER("sw `s1,0(`s0)", null, srcList));
			}
		}else {
			Temp.Temp dst = munchExp(stm.dst);
			Temp.Temp src = munchExp(stm.src);
			emit(new Assem.MOVE("move `d0,`s0", dst, src));
		}

	}

	String matchRelOp(int i) {
		switch (i) {
		case CJUMP.EQ:
			return new String("beq");
		case CJUMP.NE:
			return new String("bne");
		case CJUMP.GT:
			return new String("bgt");
		case CJUMP.LT:
			return new String("blt");
		case CJUMP.GE:
			return new String("bge");
		case CJUMP.LE:
			return new String("ble");
		default:
			Tiger.Overall.myerror.error(0,
					"Using matchRelOp at the wrong place");
			return new String("yeah!");
		}
	}

	String matchBinOp(int i) {
		switch (i) {
		case Tree.BINOP.PLUS:
			return new String("add");
		case Tree.BINOP.MINUS:
			return new String("sub");
		case Tree.BINOP.MUL:
			return new String("mul");
		case Tree.BINOP.DIV:
			return new String("div");
		default:
			Tiger.Overall.myerror.error(0,
					"Using matchBinOp at the wrong place");
			return new String("yeah!");
		}
	}
	
	int shiftBits(int num){
		int ans = 0;
		if(num == 0)
			return ans;
		while(num != 1){
			if((num-num/2*2) == 1)
				return -1;
			num /= 2;
			++ans;
		}
		return ans;
	}
}
