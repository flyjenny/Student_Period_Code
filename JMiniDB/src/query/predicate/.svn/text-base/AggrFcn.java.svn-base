package query.predicate;

import query.scan.Scan;

public class AggrFcn {

	public static final int NONE = 0, COUNT = 1, MIN = 2, MAX = 3, SUM = 4,
			AVG = 5;

	private String fldName;
	private Constant value;
	private int func;
	private int count;
	private double sum;

	public AggrFcn(String fn, int f) {
		fldName = fn;
		func = f;
		count = 0;
		sum = 0;
	}

	public void init(Scan s) {
		count = 1;
		if (func > COUNT && func <= AVG) {
			value = s.getVal(fldName);
			Object v = value.toJavaVal();
			if (v instanceof Integer)
				sum = (Integer) v;
			else
				sum = (Double) v;
		}
	}

	public void calNext(Scan s) {
		count++;
		Constant newVal = s.getVal(fldName);
		if (newVal != null) {
			if (newVal instanceof IntConstant)
				sum += (Integer) newVal.toJavaVal();
			else if (newVal instanceof FloatConstant)
				sum += (Double) newVal.toJavaVal();
			switch (func) {
			case MIN:
				if (newVal.compareTo(value) < 0)
					value = newVal;
				break;
			case MAX:
				if (newVal.compareTo(value) > 0)
					value = newVal;
				break;
			case SUM:
				if (value instanceof IntConstant)
					value = new IntConstant(((Double) sum).intValue());
				else if (value instanceof FloatConstant)
					value = new FloatConstant(sum);
				break;
			case AVG:
				value = new FloatConstant(sum / count);
				break;
			}
		}
	}

	public String getFieldName() {
		return fldName;
	}

	public Constant getValue() {
		if (func == COUNT)
			return new IntConstant(count);
		else
			return value;
	}

}
