package absyn;

public class SelExp extends Absyn {
	int func;
	Value value;
	String alias;

	public SelExp(int f) {
		func = f;
		value = null;
	}

	public SelExp(Value v) {
		func = Attribute.NONE;
		value = v;
		alias = null;
	}

	public SelExp(int f, Value v) {
		func = f;
		value = v;
		alias = null;
	}

	public SelExp(Value v, String n) {
		func = Attribute.NONE;
		value = v;
		alias = n;
	}

	public SelExp(int f, Value v, String n) {
		func = f;
		value = v;
		alias = n;
	}

	public boolean isSelectAll() {
		return value == null;
	}

	public String getFieldName() {
		if (value == null)
			return null;
		else if (value instanceof Attribute) {
			return ((Attribute) value).getName();
		} else if (value instanceof AopValue) {
			// TODO colname for select "a+3"
		} else {
			// TODO colname for other select
		}
		return "";
	}

	public String getAlias() {
		return alias;
	}

	public int getFunc() {
		return func;
	}

	public String getFuncName() {
		switch (func) {
		case Attribute.COUNT:
			return "count";
		case Attribute.MAX:
			return "max";
		case Attribute.MIN:
			return "min";
		case Attribute.SUM:
			return "sum";
		case Attribute.AVG:
			return "avg";
		}
		return "none";
	}
}
