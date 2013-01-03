package query.predicate;

public class FloatConstant implements Constant {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8908622576967226490L;
	private Double val;

	public FloatConstant(double d) {
		val = new Double(d);
	}

	public FloatConstant(Double d) {
		val = d;
	}

	public Object toJavaVal() {
		return val;
	}

	@Override
	public boolean equals(Object obj) {
		FloatConstant ic = (FloatConstant) obj;
		return ic != null && val.equals(ic.val);
	}

	public int compareTo(Constant c) {
		FloatConstant ic = (FloatConstant) c;
		return val.compareTo(ic.val);
	}

}
