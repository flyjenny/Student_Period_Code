package query.predicate;

import java.util.*;

import query.scan.Scan;

public class GroupValue {

	public Map<String, Constant> values = new HashMap<String, Constant>();

	public GroupValue(Scan s, List<String> gl) {
		for (String fn : gl)
			values.put(fn, s.getVal(fn));
	}

	public boolean equals(Scan s) {
		for (String fn : values.keySet())
			if (!values.get(fn).equals(s.getVal(fn)))
				return false;
		return true;
	}
}
