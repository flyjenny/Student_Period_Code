package absyn;

import metadata.Schema;

public class ColDef extends CreateDef {
	Attribute column;
	DataType dataType;
	Const defaultValue;
	boolean isPriKey;
	boolean hasForgnKey;
	Attribute forgnKey;
	Condition checkCond;

	public ColDef(int p, Attribute cn, DataType ty) {
		pos = p;
		column = cn;
		dataType = ty;
		defaultValue = null;
		isPriKey = false;
		hasForgnKey = false;
		forgnKey = null;
		checkCond = null;
	}

	public ColDef(int p, Attribute cn, DataType ty, Const dv) {
		pos = p;
		column = cn;
		dataType = ty;
		defaultValue = dv;
		isPriKey = false;
		hasForgnKey = false;
		forgnKey = null;
		checkCond = null;
	}

	public ColDef(int p, Attribute cn, DataType ty, Condition c) {
		pos = p;
		column = cn;
		dataType = ty;
		defaultValue = null;
		isPriKey = false;
		hasForgnKey = false;
		forgnKey = null;
		checkCond = c;
	}

	public ColDef(int p, Attribute cn, DataType ty, Const dv, Condition c) {
		pos = p;
		column = cn;
		dataType = ty;
		defaultValue = dv;
		isPriKey = false;
		hasForgnKey = false;
		forgnKey = null;
		checkCond = c;
	}

	public void setPrimaryKey() {
		isPriKey = true;
	}

	public void setForeignKey(Attribute a) {
		hasForgnKey = true;
		forgnKey = a;
	}

	public void addToSchema(Schema sch) {
		String colName = column.getName();
		sch.addField(colName, dataType.getType(), dataType.getLength());
	}

	public Condition getCond() {
		return checkCond;
	}

	public boolean hasForgnKey() {
		return hasForgnKey;
	}

	public boolean isPriKey() {
		return isPriKey;
	}

	public Attribute getForgnKey() {
		return forgnKey;
	}

	public Attribute getAttribute() {
		return column;
	}
}
