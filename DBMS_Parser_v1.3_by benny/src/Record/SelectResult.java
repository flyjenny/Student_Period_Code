package Record;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class SelectResult {
	public ArrayList<String> columnNames = new ArrayList<String>();
	public ArrayList<String> tableNames = new ArrayList<String>();
	public ArrayList<String> columnNewNames = new ArrayList<String>();
	public ArrayList<Integer> columnAggregates = new ArrayList<Integer>();
	public ArrayList<AttrDataType> columnDataTypes = new ArrayList<AttrDataType>();
	public ArrayList<String[]> tuples = new ArrayList<String[]>();
	public int columnCount = 0;
	public int tupleCount = 0;

	public SelectResult() {

	}

	public SelectResult(SelectResult se) {
		this.columnNames = se.columnNames;
		this.tableNames = se.tableNames;
		this.columnNewNames = se.columnNewNames;
		this.columnAggregates = se.columnAggregates;
		this.columnDataTypes = se.columnDataTypes;
		this.tuples = se.tuples;
		this.columnCount = se.columnCount;
		this.tupleCount = se.tupleCount;
	}

	public SelectResult(ArrayList<String> columnNames, ArrayList<String> tableNames,
			ArrayList<String> columnNewNames, ArrayList<Integer> columnAggregates,
			ArrayList<AttrDataType> columnDataTypes, ArrayList<String[]> tuples,
			int columnCount, int tupleCount) {
		this.columnNames = columnNames;
		this.tableNames = tableNames;
		this.columnNewNames = columnNewNames;
		this.columnAggregates = columnAggregates;
		this.columnDataTypes = columnDataTypes;
		this.tuples = tuples;
		this.columnCount = columnCount;
		this.tupleCount = tupleCount;
	}

	@SuppressWarnings("unchecked")
	public SelectResult unionWith(boolean unionAll, SelectResult se) {
		// test the legality
		if (columnCount == 0 || columnCount != se.columnCount) {
			// error
		}
		for (int i = columnCount; i > 0; i--) {
			if (!columnDataTypes.get(i - 1).equals(
					se.columnDataTypes.get(i - 1))) {
				// error
				break;
			}
		}
		// do union
		// modify mark
		Comparator cmp = new Comparator() {
			public int compare(Object a, Object b) {
				String[] sa = (String[]) a;
				String[] sb = (String[]) b;
				int len = sa.length;
				for (int i = 0; i < len; i++) {
					if (!sa[i].equals(sb[i]))
						return sa[i].compareTo(sb[i]);
				}
				return 0;
			}
		};
		SelectResult result = new SelectResult(this);
		result.tuples.addAll(se.tuples);
		if (unionAll) {
			result.tupleCount += se.tupleCount;
		} else {
			Object[] tuples = result.tuples.toArray();
			Arrays.sort(tuples, cmp);
			int len = tuples.length;
			result.tuples.clear();
			result.tupleCount = 0;
			for (int i = 0; i < len - 1; i++) {
				if (!cmpStrArray((String[])tuples[i], (String[])tuples[i+1])) {
					result.tuples.add((String[])tuples[i]);
					result.tupleCount++;
				}
			}
			result.tuples.add((String[])tuples[len - 1]);
			result.tupleCount++;
		}
		return result;
	}

	public boolean cmpStrArray(String[] s1, String[] s2) {
		int len1 = s1.length;
		int len2 = s2.length;

		if (len1 != len2)
			return false;

		boolean equal = true;
		for (int i = 0; i < len1; i++) {
			if (!s1[i].equals(s2[i])) {
				equal = false;
				break;
			}
		}
		return equal;
	}
}
