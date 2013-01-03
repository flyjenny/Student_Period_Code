package query.scan;

import java.util.*;

import query.predicate.*;

public class GroupByScan implements Scan {

	private Scan scan;
	private Scan parentScan;
	private List<String> groupList;
	private List<AggrFcn> aggrFcn;
	private boolean hasMore;
	private Predicate having;

	public GroupByScan(Scan s, List<String> gl, List<AggrFcn> af,
			Predicate having) {
		scan = s;
		s.setParentScan(this);
		groupList = gl;
		aggrFcn = af;
		this.having = having;
		reset();
	}

	public void reset() {
		scan.reset();
		hasMore = scan.next();
		firstTime = true;
	}

	private GroupValue gv;
	private boolean firstTime;

	public boolean next() {
		if (hasMore) {

			boolean havingOne = (null == having);

			for (AggrFcn af : aggrFcn)
				af.init(scan);

			if (!havingOne && null != having && having.isSatisfied(scan))
				havingOne = true;

			gv = new GroupValue(scan, groupList);
			while ((hasMore = scan.next()) && gv.equals(scan)) {
				if (!havingOne && null != having && having.isSatisfied(scan))
					havingOne = true;

				for (AggrFcn af : aggrFcn)
					af.calNext(scan);
			}
			firstTime = false;
			return havingOne ? true : next();
		} else if (firstTime) {
			firstTime = false;
			return true;
		} else {
			return false;
		}

	}

	public void close() {
		scan.close();
	}

	public Constant getVal(String fldName) {
		if (hasField(fldName)) {
			if (groupList.contains(fldName))
				return gv.values.get(fldName);
			else
				for (AggrFcn af : aggrFcn)
					if (af.getFieldName().equals(fldName))
						return af.getValue();
		}
		throw new RuntimeException("field " + fldName + " not found.");
	}

	public int getInt(String fldName) {
		return (Integer) getVal(fldName).toJavaVal();
	}

	public double getFloat(String fldName) {
		Object val = getVal(fldName).toJavaVal();
		if (val instanceof Double)
			return (Double) val;
		else if (val instanceof Integer) {
			return (Integer) val;
		} else
			return 0;

	}

	public String getString(String fldName) {
		return (String) getVal(fldName).toJavaVal();
	}

	public boolean hasField(String fldName) {
		if (groupList.contains(fldName))
			return true;
		for (AggrFcn af : aggrFcn) {
			if (af.getFieldName().equals(fldName))
				return true;
		}
		return false;
	}

	public Scan getParentScan() {
		return parentScan;
	}

	public void setParentScan(Scan s) {
		parentScan = s;
	}

	public String getSingleFldName() {
		if (isSingleCol()) {
			return groupList.iterator().next();
		} else {
			return null;
		}
	}

	public boolean isSingleCol() {
		return (groupList.size() == 1 && aggrFcn.isEmpty());
	}

}
