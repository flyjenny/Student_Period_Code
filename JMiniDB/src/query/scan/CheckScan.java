package query.scan;

import java.util.Map;

import exception.CheckFailError;

import query.plan.TempRow;
import query.predicate.Constant;
import query.predicate.FloatConstant;
import query.predicate.IntConstant;
import query.predicate.Predicate;
import query.predicate.StringConstant;
import record.Record;

public class CheckScan implements UpdateScan {
	private TempRow r;
	private Map<String, Integer> fmap;
	private int cur;
	private Predicate[] ps;
	private UpdateScan s;

	public CheckScan(UpdateScan s, Map<String, Integer> fmap, Predicate[] ps) {
		this.ps = ps;
		this.s = s;
		this.fmap = fmap;
		r = new TempRow(fmap.size());
		cur = 0;
		if (null != s)
			for (String key : fmap.keySet()) {
				r.setRow(fmap.get(key), s.getVal(key));
			}

	}

	@Override
	public void close() {
		for (Predicate p : ps) {
			if (!p.isSatisfied(this))
				throw new CheckFailError("check error");
		}
		r = null;
	}

	@Override
	public double getFloat(String fldName) {
		return (Double) r.getRow(fmap.get(fldName)).toJavaVal();
	}

	@Override
	public int getInt(String fldName) {
		return (Integer) r.getRow(fmap.get(fldName)).toJavaVal();
	}

	@Override
	public Scan getParentScan() {
		return null;
	}

	@Override
	public String getSingleFldName() {

		return fmap.size() == 1 ? fmap.keySet().iterator().next() : null;
	}

	@Override
	public String getString(String fldName) {
		return (String) r.getRow(fmap.get(fldName)).toJavaVal();
	}

	@Override
	public Constant getVal(String fldName) {
		return r.getRow(fmap.get(fldName));
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
		if (cur == 0) {
			cur++;
			return true;
		} else
			return false;
	}

	@Override
	public void reset() {
		cur = 0;
	}

	@Override
	public void setParentScan(Scan s) {

	}

	@Override
	public void delete() {
		// do a delete check

	}

	@Override
	public Record getRecord() {
		return null;
	}

	@Override
	public void insert() {
	}

	@Override
	public void moveToRecord(Record r) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setFloat(String fldName, double val) {
		r.setRow(fmap.get(fldName), new FloatConstant(val));

	}

	@Override
	public void setInt(String fldName, int val) {
		r.setRow(fmap.get(fldName), new IntConstant(val));

	}

	@Override
	public void setString(String fldName, String val) {
		r.setRow(fmap.get(fldName), new StringConstant(val));

	}

	@Override
	public void setVal(String fldName, Constant val) {
		r.setRow(fmap.get(fldName), val);

	}

}
