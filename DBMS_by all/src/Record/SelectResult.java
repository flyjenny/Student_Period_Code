package Record;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

public class SelectResult {
	public String[] columnNames = null;
	public String[] tableNames = null;
	public AttrDataType[] columnDataTypes = null;
	public ArrayList<String[]> tuples = new ArrayList<String[]>();
	public int columnCount = 0;
	public int tupleCount = 0;

	public SelectResult(SelectResult sr) {
		this.columnCount = sr.columnCount;
		this.tupleCount = sr.tupleCount;
		this.columnNames = new String[this.columnCount];
		this.tableNames = new String[this.columnCount];
		this.columnDataTypes = new AttrDataType[this.columnCount];

		for (int i = 0; i < this.columnCount; i++) {
			this.columnNames[i] = new String(sr.columnNames[i]);
			this.tableNames[i] = new String(sr.tableNames[i]);
			this.columnDataTypes[i] = new AttrDataType(sr.columnDataTypes[i]);
		}
		for (int i = 0; i < sr.tupleCount; i++) {
			this.tuples.add(sr.tuples.get(i));
		}
	}

	public SelectResult(String tableName, TableInfo tableInfo, Record[] records) {
		this.columnCount = tableInfo.attrNames.length;
		this.tupleCount = records.length;
		this.columnNames = tableInfo.attrNames;
		this.tableNames = new String[this.columnCount];
		this.columnDataTypes = tableInfo.attrDataTypes;

		for (int i = 0; i < this.columnCount; i++) {
			this.tableNames[i] = new String(tableName);
		}
		for (int i = 0; i < this.tupleCount; i++) {
			this.tuples.add(records[i].attrValue);
		}
	}

	public SelectResult(String[] columnNames, String[] tableNames,
			AttrDataType[] columnDataTypes, String[][] tuples) {
		this.columnCount = columnNames.length;
		this.tupleCount = tuples.length;
		this.columnNames = columnNames;
		this.tableNames = tableNames;
		this.columnDataTypes = columnDataTypes;

		for (int i = 0; i < this.tupleCount; i++) {
			this.tuples.add(tuples[i]);
		}
	}

	public void setMembers(String[] columnNames, String[] tableNames,
			AttrDataType[] columnDataTypes, ArrayList<String[]> tuples,
			int columnCount, int tupleCount) {
		this.columnNames = columnNames;
		this.tableNames = tableNames;
		this.columnDataTypes = columnDataTypes;
		this.tuples = tuples;
		this.columnCount = columnCount;
		this.tupleCount = tupleCount;
	}

	public void setTableName(String newName) {
		for (int i = 0; i < columnCount; i++) {
			this.tableNames[i] = new String(newName);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public SelectResult unionWith(boolean unionAll, SelectResult se)
			throws Exception {
		// test the legality
		if (se == null)
			return this;
		if (columnCount == 0 || columnCount != se.columnCount) {
			throw new Exception(
					"Error: union operation failed, numbers column of the two sides do not agree");
		}
		for (int i = columnCount; i > 0; i--) {
			if (!columnDataTypes[i - 1].equals(se.columnDataTypes[i - 1])) {
				throw new Exception(
						"Error: union operation failed, data type of the two sides do not agree");
			}
		}
		// do union
		tuples.addAll(se.tuples);
		if (unionAll) {
			tupleCount += se.tupleCount;
		} else {
			Object[] tupleArray = tuples.toArray();
			Arrays.sort(tupleArray, (Comparator) (new allComparator()));
			;
			int len = tupleArray.length;
			tuples.clear();
			tupleCount = 0;
			for (int i = 0; i < len - 1; i++) {
				if (!cmpStrArray((String[]) tupleArray[i],
						(String[]) tupleArray[i + 1])) {
					tuples.add((String[]) tupleArray[i]);
					tupleCount++;
				}
			}
			tuples.add((String[]) tupleArray[len - 1]);
			tupleCount++;
		}
		return this;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void clearDup() {
		Object[] tupleArray = tuples.toArray();
		Arrays.sort(tupleArray, (Comparator) (new allComparator()));
		int len = tupleArray.length;
		tuples.clear();
		tupleCount = 0;
		for (int i = 0; i < len - 1; i++) {
			if (!cmpStrArray((String[]) tupleArray[i],
					(String[]) tupleArray[i + 1])) {
				tuples.add((String[]) tupleArray[i]);
				tupleCount++;
			}
		}
		tuples.add((String[]) tupleArray[len - 1]);
		tupleCount++;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void group(int[] indexes) {
		Object[] tupleArray = tuples.toArray();
		Arrays.sort(tupleArray, (Comparator) (new groupComparator(indexes)));
		tuples.clear();
		for (int i = 0; i < tupleCount; i++) {
			tuples.add((String[]) tupleArray[i]);
		}
	}

	public void add(String[] tuple) {
		tuples.add(tuple);
		tupleCount++;
	}

	public void remove(int index) {
		tuples.remove(index);
		tupleCount--;
	}

	public void clear() {
		tuples.clear();
		tupleCount = 0;
	}

	public void setColumn(int l, int c, String value) {
		String[] oldTuple = tuples.get(l);
		String[] newTuple = new String[columnCount];
		for (int i = 0; i < columnCount; i++) {
			newTuple[i] = new String(oldTuple[i]);
		}
		newTuple[c] = new String(oldTuple[c]);
	}

	public void setColumns(int l, int[] cIndexes, String[] values) throws Exception {
		int len = cIndexes.length;
		if (len == values.length) {
			String[] oldTuple = tuples.get(l);
			String[] newTuple = new String[columnCount];
			for (int i = 0; i < columnCount; i++) {
				newTuple[i] = new String(oldTuple[i]);
			}
			for (int j = len - 1; j >= 0; j--)
				newTuple[cIndexes[j]] = new String(values[j]);
			;
			tuples.set(l, newTuple);
		} else {
			throw new Exception("Error: projection operation failed");
		}
	}

	public void setTuple(int l, String[] tuple) {
		tuples.set(l, tuple);
	}

	public String getColumn(int l, int c) {
		return tuples.get(l)[c];
	}

	public String[] getTuple(int l) {
		return tuples.get(l);
	}

	public void print() {
		for (int j = 0; j < columnCount; j++) {
			System.out.print(columnNames[j] + "\t");
		}
		System.out.print("\n");
		for (int i = 0; i < tupleCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				System.out.print(tuples.get(i)[j] + "\t");
			}
			System.out.print("\n");
		}
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

	class allComparator implements Comparator<String[]> {
		@Override
		public int compare(String[] s1, String[] s2) {
			int len = s1.length;
			for (int i = 0; i < len; i++) {
				int cmp = s1[i].compareTo(s2[i]);
				if (cmp != 0)
					return cmp;
			}
			return 0;
		}

	}

	class groupComparator implements Comparator<String[]> {
		public int[] indexes = null;

		public groupComparator(int[] indexes) {
			this.indexes = indexes;
		}

		@Override
		public int compare(String[] s1, String[] s2) {
			int len = indexes.length;
			for (int i = 0; i < len; i++) {
				int cmp = s1[indexes[i]].compareTo(s2[indexes[i]]);
				if (cmp != 0)
					return cmp;
			}
			return 0;
		}

	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int j = 0; j < columnCount; j++) {
			sb.append(columnNames[j] + "\t");
		}
		sb.append("\n");
		for (int i = 0; i < tupleCount; i++) {
			for (int j = 0; j < columnCount; j++) {
				sb.append(tuples.get(i)[j] + "\t");
			}
			sb.append("\n");
		}
		return sb.toString();
	}

}
