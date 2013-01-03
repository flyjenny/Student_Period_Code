package Assem;

public class MOVE extends Instr {

	public MOVE(String a, Temp.Temp d, Temp.Temp s) {
		assem = a;
		dst = new Temp.TempList(d, null);
		src = new Temp.TempList(s, null);
	}

	public Temp.TempList use() {
		return src;
	}

	public Temp.TempList def() {
		return dst;
	}

	public Targets jumps() {
		return null;
	}

	public void display() {
/*		System.out.print(assem + "  dst ");
		dst.display();
		System.out.print("  src ");
		src.display();
		System.out.println();
		*/
		String s = format(Mips.MipsFrame.instance);
		System.out.println(s);
	}

}
