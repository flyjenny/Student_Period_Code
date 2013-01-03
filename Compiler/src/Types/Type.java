package Types;

public abstract class Type {
	 public final static INT _int = new INT();
	 public final static STRING _string = new STRING();
	 public final static VOID _void = new VOID();
	 public final static NIL _nil = new NIL();
	 
   public Type actual() {return this;}
         
   public boolean coerceTo(Type t) { return false; }
   
   public boolean isIntType(Type t) { return _int.coerceTo(t); };
   public boolean isStringType(Type t) { return _string.coerceTo(t); };
   public boolean isVoidType(Type t) { return _void.coerceTo(t); };
//   public boolean isRecordType(Type t) { return RECORD.coerceTo(t); };
//   public boolean isArayType(Type t) { return ARRAY.coerceTo(t); };
//   public boolean isNameType(Type t) { return NAME.coerceTo(t); };
}

