package Frame;

public interface Frame extends Temp.TempMap{		
	public Frame newFrame(Temp.Label name,Util.BoolList formals);
    public Access allocLocal( boolean escape );
    public Access staticLink();
    public Temp.Temp FP();
    public Temp.Temp RV();
	public Frame newFrame( Util.BoolList fmls );
	public AccessList getFormals();
	public Tree.Expv externalCall(String func,Tree.ExpList args); 
	public Tree.Stm procEntryExit1(Tree.Stm body);
	public Assem.InstrList procEntryExit2(Assem.InstrList body);
	public Assem.InstrList procEntryExit3(Assem.InstrList body);
	public int wordsize();
	public Temp.Label frameName();
	public Assem.InstrList codegen(Tree.Stm stm);
	public String tempMap(Temp.Temp temp);
	public boolean isRedeclared();
}
