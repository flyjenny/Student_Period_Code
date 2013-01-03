package query.scan;

import java.util.Map;

import exception.FieldNotFound;

import query.predicate.Constant;

public class ExtendScan implements Scan {

	private Scan parentScan;
	private Map<String, String> fmap;
	private Scan scan;

	public ExtendScan(Scan s, Map<String, String> fmap) {
		s.setParentScan(this);
		this.fmap = fmap;
		scan = s;

	}

	@Override
	public void close() {
		scan.close();
	}

	@Override
	public double getFloat(String fldName) {
		fldName = cleanFldName(fldName);
		String mName = fmap.get(fldName);
		if (mName == null)
			throw new FieldNotFound(fldName);
		return scan.getFloat(mName);
	}

	@Override
	public int getInt(String fldName) {
		fldName = cleanFldName(fldName);
		String mName = fmap.get(fldName);
		if (mName == null)
			throw new FieldNotFound(fldName);
		return scan.getInt(mName);
	}

	@Override
	public Scan getParentScan() {
		return parentScan;
	}

	@Override
	public String getSingleFldName() {
		if (fmap.size() == 1)
			return fmap.keySet().iterator().next();
		else
			return null;
	}

	@Override
	public String getString(String fldName) {
		fldName = cleanFldName(fldName);
		String mName = fmap.get(fldName);
		if (mName == null)
			throw new FieldNotFound(fldName);
		return scan.getString(mName);
	}

	@Override
	public Constant getVal(String fldName) {
		fldName = cleanFldName(fldName);
		return scan.getVal(fmap.get(fldName));
	}

	@Override
	public boolean hasField(String fldName) {
		fldName = cleanFldName(fldName);
		return fmap.containsKey(fldName);
	}

	private String cleanFldName(String fldName) {
		if (fldName.startsWith(".") && fldName.length() > 1)
			return fldName.substring(1);
		return fldName;
	}

	@Override
	public boolean isSingleCol() {
		return scan.isSingleCol();
	}

	@Override
	public boolean next() {
		return scan.next();
	}

	@Override
	public void reset() {
		scan.reset();

	}

	@Override
	public void setParentScan(Scan s) {
		parentScan = s;

	}

}
