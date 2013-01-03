package Assem;


public class OPER extends Instr {

	public Targets jump;

	public OPER(String a, Temp.TempList d, Temp.TempList s, Temp.LabelList j) {
		assem = a;
		dst = d;
		src = s;
		jump = new Targets(j);
	}

	public OPER(String a, Temp.TempList d, Temp.TempList s) {
		assem = a;
		dst = d;
		src = s;
		jump = null;
	}

	public Temp.TempList use() {
		return src;
	}

	public Temp.TempList def() {
		return dst;
	}

	public Targets jumps() {
		return jump;
	}

	@Override
	public void display() {
/*		System.out.print(assem);
		System.out.print(" dst ");
		Temp.TempList dstPointer = dst;
		while(dstPointer != null){
			dstPointer.display();
			System.out.print(" ");			
			dstPointer = dstPointer.tail;
		}
		System.out.print(" src ");
		Temp.TempList srcPointer = src;
		while(srcPointer != null){
			if(srcPointer.head != null)
			  srcPointer.head.display();
			else
				System.out.print("null");	 
			System.out.print(" ");			
			srcPointer = srcPointer.tail;
		}
		if(jump != null){
		     Temp.LabelList targetPointer = jump.labels;
		     while(targetPointer != null){
			        if(targetPointer.head != null)
				       targetPointer.head.display();
			        else
				       System.out.print("null");	
			        targetPointer = targetPointer.tail;
			        System.out.print(" ");
		     }
		}
		System.out.println();*/
		String s = format(Mips.MipsFrame.instance);
		System.out.println(s);
	}

}
