package query.scan;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import query.plan.TempRow;
import query.predicate.Constant;
import query.predicate.FloatConstant;
import query.predicate.IntConstant;
import query.predicate.StringConstant;
import query.predicate.TypeMismatchException;

public class SortScan implements Scan {

	private Map<String, Integer> fmap;
	private List<TempRow> rs;

	private Scan parentScan;
	private Scan s;
	private Iterator<TempRow> ri;
	private TempRow cur;

	public SortScan(Scan s, Map<String, Integer> fmap, List<TempRow> rs) {
		this.fmap = fmap;
		this.rs = rs;
		ri = rs.iterator();
		s.setParentScan(this);
		this.s = s;
	}

	@Override
	public void close() {
		fmap = null;
		rs = null;
		cur = null;
		s.close();

	}

	@Override
	public double getFloat(String fldName) {
		Constant c = getVal(fldName);
		if (c != null)
			if (c instanceof FloatConstant)
				return (Double) c.toJavaVal();
		throw new TypeMismatchException();

	}

	@Override
	public int getInt(String fldName) {
		Constant c = getVal(fldName);
		if (c != null)
			if (c instanceof IntConstant)
				return (Integer) c.toJavaVal();
		throw new TypeMismatchException();
	}

	@Override
	public Scan getParentScan() {
		return parentScan;
	}

	@Override
	public String getSingleFldName() {
		if (isSingleCol()) {
			return fmap.keySet().iterator().next();
		} else {
			return null;
		}
	}

	@Override
	public String getString(String fldName) {
		Constant c = getVal(fldName);
		if (c != null)
			if (c instanceof StringConstant)
				return (String) c.toJavaVal();
		throw new TypeMismatchException();
	}

	@Override
	public Constant getVal(String fldName) {
		Integer i = fmap.get(fldName);
		if (null == i)
			return null;
		else
			return cur.getRow(i);
	}

	@Override
	public boolean hasField(String fldName) {
		return fmap.containsKey(fldName);
	}

	@Override
	public boolean isSingleCol() {
		return fmap.size() == 1;
	}

	@Override
	public boolean next() {
		if (ri.hasNext()) {
			cur = ri.next();
			return true;
		} else {
			return false;
		}
	}

	@Override
	public void reset() {
		ri = rs.iterator();
		cur = null;
	}

	@Override
	public void setParentScan(Scan s) {
		parentScan = s;

	}

}
