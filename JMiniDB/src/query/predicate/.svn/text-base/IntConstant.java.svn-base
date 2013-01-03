package query.predicate;

public class IntConstant implements Constant {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5710254300528821934L;
	private Integer val;

	public IntConstant(int n) {
		val = new Integer(n);
	}

	public IntConstant(Integer n) {
		val = n;
	}

	public Object toJavaVal() {
		return val;
	}

	@Override
	public boolean equals(Object obj) {
		IntConstant ic = (IntConstant) obj;
		return ic != null && val.equals(ic.val);
	}

	public int compareTo(Constant c) {
		IntConstant ic = (IntConstant) c;
		return val.compareTo(ic.val);
	}

}
