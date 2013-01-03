package Record;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class SelectResult {
	public String[] columnNames = null;
	public String[] tableNames = null;
	public String[] columnNewNames = null;
	public int[] columnAggregates = null;
	public AttrDataType[] columnDataTypes = null;
	public ArrayList<String[]> tuples = new ArrayList<String[]>();
	public int columnCount = 0;
	public int tupleCount = 0;
	@SuppressWarnings("unchecked")
	public Comparator cmp = new Comparator() {
		public int compare(Object a, Object b) {
			String[] sa = (String[]) a;
			String[] sb = (String[]) b;
			int len = sa.length;
			for (int i = 0; i < len; i++) {
				int cmp = sa[i].compareTo(sb[i]);
				if (cmp != 0)
					return cmp;
			}
			return 0;
		}
	};

	public SelectResult() {

	}

	public SelectResult(String[] columnNames, String[] tableNames,
			String[] columnNewNames, int[] columnAggregates,
			AttrDataType[] columnDataTypes, ArrayList<String[]> tuples,
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
			if (!columnDataTypes[i - 1].equals(se.columnDataTypes[i - 1])) {
				// error
				break;
			}
		}
		// do union
		tuples.addAll(se.tuples);
		if (unionAll) {
			tupleCount += se.tupleCount;
		} else {
			Object[] tupleArray = tuples.toArray();
			Arrays.sort(tupleArray, cmp);
			int len = tupleArray.length;
			tuples.clear();
			tupleCount = 0;
			for (int i = 0; i < len - 1; i++) {
				if (!cmpStrArray((String[]) tupleArray[i], (String[]) tupleArray[i + 1])) {
					tuples.add((String[]) tupleArray[i]);
					tupleCount++;
				}
			}
			tuples.add((String[]) tupleArray[len - 1]);
			tupleCount++;
		}
		return this;
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
