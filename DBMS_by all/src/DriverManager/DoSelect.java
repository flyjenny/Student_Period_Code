package DriverManager;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.text.DecimalFormat;

import Absyn.*;
import Record.AttrDataType;
import Record.Record;
import Record.SelectResult;
import Record.TableInfo;
import UserInterface.UserInterface;
import QueryManager.Plans.LogicalQueryPlan;
import QueryManager.Plans.Plan;

class DoSelect extends DriverManager {
	SelectResult result = null;

	DoSelect() {

	}

	void run(Plan plan) throws Exception {
		System.out.println("Processing: query a table ...");

		result = select(plan.lqplist.get(0));
		int unionCount = plan.selectCondBinCount;
		for (int i = 0; i < unionCount; i++) {
			result = result.unionWith(plan.uni.get(i),
					select(plan.lqplist.get(i + 1)));
		}
		result.print();
		//System.out.println(result.toString());

		System.out.println("Finished.");
	}

	SelectResult select(LogicalQueryPlan plan) throws Exception {
		SelectResult[] tables = getTables(plan.production);
		SelectResult[] tablesTemp = getTempTables(plan.plans);
		// do production
		SelectResult result = doProduction(multiProduction(tables),
				multiProduction(tablesTemp));
		// do condition
		doCondition(result, plan.condition);
		// do projection
		doProjection(result, plan);
		return result;
	}

	SelectResult doProduction(SelectResult s1, SelectResult s2) {
		if (s1 == null)
			return s2;
		if (s2 == null)
			return s1;
		int cCount1 = s1.columnCount;
		int cCount2 = s2.columnCount;
		int columnCount = cCount1 + cCount2;
		int tCount1 = s1.tupleCount;
		int tCount2 = s2.tupleCount;
		int tupleCount = tCount1 * tCount2;
		String[] columnNames = new String[columnCount];
		String[] tableNames = new String[columnCount];
		AttrDataType[] columnDataTypes = new AttrDataType[columnCount];
		String[][] tuples = new String[tupleCount][columnCount];
		for (int i = 0; i < cCount1; i++) {
			columnNames[i] = s1.columnNames[i];
			tableNames[i] = s1.tableNames[i];
			columnDataTypes[i] = s1.columnDataTypes[i];
		}
		for (int i = 0; i < cCount2; i++) {
			columnNames[i + cCount1] = s2.columnNames[i];
			tableNames[i + cCount1] = s2.tableNames[i];
			columnDataTypes[i + cCount1] = s2.columnDataTypes[i];
		}
		for (int i = 0; i < tupleCount; i++) {
			int i1 = i / tCount2;
			int i2 = i % tCount2;
			for (int j = 0; j < cCount1; j++) {
				tuples[i][j] = s1.tuples.get(i1)[j];
			}
			for (int j = 0; j < cCount2; j++) {
				tuples[i][j + cCount1] = s2.tuples.get(i2)[j];
			}

		}
		return new SelectResult(columnNames, tableNames, columnDataTypes,
				tuples);
	}

	void doCondition(SelectResult sr, WhereExp exp) throws Exception {
		if (exp != null) {
			WhereCondExp condExp = exp.cond;
			int count = sr.tupleCount;
			for (int i = count - 1; i >= 0; i--) {
				if (!tupleMeetCond(sr, i, condExp)) {
					sr.remove(i);
				}
			}
		}
	}

	void doProjection(SelectResult sr, LogicalQueryPlan plan) throws Exception {
		SelectColumnList scList = plan.projection;
		WhereExp whereExp = plan.condition;
		GroupExp groupExp = null;
		OrderExp orderExp = null;
		int tupleCount = sr.tupleCount;
		if (whereExp != null) {
			groupExp = whereExp.groupExp;
			orderExp = whereExp.orderExp;
		}
		if (scList != null) {
			// get columnCount
			int columnCount = 0;
			SelectColumnList list = scList;
			while (list != null) {
				columnCount++;
				list = list.tail;
			}
			String[] columnNames = new String[columnCount];
			String[] tableNames = new String[columnCount];
			AttrDataType[] columnDataTypes = new AttrDataType[columnCount];
			ArrayList<String[]> tuples = new ArrayList<String[]>();
			int[] indexes = new int[columnCount];
			int[] aggregates = new int[columnCount];
			// get column info for the new selectResult
			list = scList;
			for (int i = 0; i < columnCount; i++) {
				SelectColumn sc = list.head;
				list = list.tail;
				if (!(sc.algebraExp instanceof AlgebraOp)) {
					throw new Exception(
							"Error: algebra between selected columns is not supported");
				}
				AlgebraOp op = (AlgebraOp) sc.algebraExp;
				if (op.column != null) {
					int index = getColumnIndex(op.column.cName,
							op.column.tName, sr.columnNames, sr.tableNames);
					if (sc.name != null)
						columnNames[i] = sc.name;
					else {
						columnNames[i] = op.column.cName;
						if (op.column.tName != null)
							columnNames[i] = op.column.tName + "."
									+ columnNames[i];
					}
					columnDataTypes[i] = sr.columnDataTypes[index];
					tableNames[i] = sr.tableNames[index];
					indexes[i] = index;
					aggregates[i] = Const.INVALID;
					continue;
				}
				if (op.aggregateExp != null) {
					int at = op.aggregateExp.type;
					int index = getColumnIndex(op.aggregateExp.column.cName,
							op.aggregateExp.column.tName, sr.columnNames,
							sr.tableNames);
					if (sc.name != null)
						columnNames[i] = sc.name;
					else {
						columnNames[i] = getAggregateName(at,
								op.aggregateExp.column.cName,
								op.aggregateExp.column.tName);
					}
					columnDataTypes[i] = sr.columnDataTypes[index];
					int vt = columnDataTypes[i].getValueType();
					if (vt == Const.STRINGVALUE
							&& (at == Const.AVG || at == Const.SUM)) {
						throw new Exception(
								"Error: string value can not do <avg> and <sum> aggregates");
					}
					tableNames[i] = sr.tableNames[index];
					indexes[i] = index;
					aggregates[i] = op.aggregateExp.type;
					continue;
				}
				throw new Exception("Error: column name expected");
			}
			// do projection
			for (int j = 0; j < tupleCount; j++) {
				String[] tuple = new String[columnCount];
				for (int k = 0; k < columnCount; k++) {
					tuple[k] = sr.tuples.get(j)[indexes[k]];
				}
				tuples.add(tuple);
			}
			sr.setMembers(columnNames, tableNames, columnDataTypes, tuples,
					columnCount, tupleCount);
			// do distinct
			if (plan.selectDist && tupleCount > 0)
				sr.clearDup();
			// do group
			group(sr, groupExp, aggregates);
			// do format
			format(sr);
			// do order
			order(sr, orderExp);
		} else {
			// no need to do projection
			// do distinct
			if (plan.selectDist && tupleCount > 0)
				sr.clearDup();
			// should not do group
			if (groupExp != null) {
				throw new Exception("Error: group expression is not expected");
			}
			// do order
			order(sr, orderExp);
		}
	}

	SelectResult multiProduction(SelectResult[] selectResults) {
		if (selectResults == null)
			return null;
		SelectResult sr = null;
		for (int i = selectResults.length - 1; i >= 0; i--) {
			sr = doProduction(selectResults[i], sr);
		}
		return sr;
	}

	boolean tupleMeetCond(SelectResult sr, int index, WhereCondExp exp)
			throws Exception {
		if (exp == null)
			return true;
		if (exp instanceof WhereCondBinExp) {
			WhereCondBinExp e = ((WhereCondBinExp) exp);
			boolean left = tupleMeetCond(sr, index, e.left);
			if (e.isAnd && left == false)
				return e.isNeg;
			if (!e.isAnd && left == true)
				return !e.isNeg;
			if (e.isAnd)
				left = left && tupleMeetCond(sr, index, e.right);
			else
				left = left || tupleMeetCond(sr, index, e.right);
			if (e.isNeg)
				return !left;
			else
				return left;
		}
		String[] cNames = sr.columnNames;
		String[] tNames = sr.tableNames;
		AttrDataType[] dTypes = sr.columnDataTypes;
		String[] tuple = sr.tuples.get(index);
		if (exp instanceof CmpExp) {
			CmpOp left = ((CmpExp) exp).left;
			CmpOp right = ((CmpExp) exp).right;
			int opType = ((CmpExp) exp).type;
			int iLeft = -1;
			int iRight = -1;
			String vLeft = null;
			String vRight = null;
			boolean cmp = false;

			if (left.column != null) {
				iLeft = getColumnIndex(left.column.cName, left.column.tName,
						cNames, tNames);
				vLeft = tuple[iLeft];
				if (iLeft == -1) {
					throw new Exception("Error: column " + left.column.cName
							+ "> not found");
				}
			} else {
				vLeft = left.value.getValue();
			}
			if (right.column != null) {
				iRight = getColumnIndex(right.column.cName, right.column.tName,
						cNames, tNames);
				vRight = tuple[iRight];
				if (iRight == -1) {
					throw new Exception("Error: column " + right.column.cName
							+ "> not found");
				}
			} else {
				vRight = right.value.getValue();
			}
			if (iLeft != -1) {
				if (iRight != -1) {
					cmp = cmpColumnAndColumn(opType, dTypes[iLeft],
							dTypes[iRight], vLeft, vRight);
				} else
					cmp = cmpValueAndValue(opType,
							dTypes[iLeft].getValueType(),
							right.value.getType(), vLeft, vRight);
			} else {
				if (iRight != -1) {
					cmp = cmpValueAndValue(opType, left.value.getType(),
							dTypes[iRight].getValueType(), vLeft, vRight);
				} else {
					cmp = cmpValueAndValue(opType, left.value.getType(),
							right.value.getType(), vLeft, vRight);
				}
			}
			if (exp.isNeg)
				return !cmp;
			else
				return cmp;
		}
		if (exp instanceof LikeExp) {
			Column column = ((LikeExp) exp).column;
			int cIndex = getColumnIndex(column.cName, column.tName, cNames,
					tNames);
			if (cIndex == -1) {
				throw new Exception("Error: column " + column.cName
						+ "> not found");
			}
			String cValue = tuple[cIndex];
			String format = ((LikeExp) exp).format;
			Pattern pattern=Pattern.compile(format);
			Matcher matcher = pattern.matcher(cValue);
			return matcher.matches();
		}
		return false;
	}

	void group(SelectResult sr, GroupExp exp, int[] aggregates)
			throws Exception {
		if (sr.tuples.isEmpty())
			return;
		int[] groupIndexes = null;
		if (exp != null) {
			ColumnList list = exp.list;
			int len = 0;
			while (list != null) {
				len++;
				list = list.tail;
			}
			list = exp.list;
			groupIndexes = new int[len];
			for (int i = 0; i < len; i++) {
				Column column = list.head;
				groupIndexes[i] = getColumnIndex(column.cName, column.tName,
						sr.columnNames, sr.tableNames);
				list = list.tail;
			}
			sr.group(groupIndexes);
		}
		int cNum = 0;
		for (int k = aggregates.length - 1; k >= 0; k--) {
			if (aggregates[k] != Const.INVALID)
				cNum++;
		}
		if (cNum == 0) {
			return;
		}
		int[] cIndexes = new int[cNum];
		int[] cAggregates = new int[cNum];
		int[] cValueTypes = new int[cNum];
		int count = 1;
		String[] curr = new String[cNum];
		String[] max = new String[cNum];
		String[] min = new String[cNum];
		String[] sum = new String[cNum];
		String[] avg = new String[cNum];
		int groupStart = sr.tupleCount - 1;

		int temp = 0;
		for (int k = aggregates.length - 1; k >= 0; k--) {
			if (aggregates[k] != Const.INVALID) {
				cIndexes[temp] = k;
				cAggregates[temp] = aggregates[k];
				cValueTypes[temp] = sr.columnDataTypes[k].getValueType();
				if (cValueTypes[temp] == Const.BOOLVALUE
						&& cAggregates[temp] != Const.COUNT) {
					throw new Exception(
							"Error: bool value can only do <count> aggregates");
				}
				if (cValueTypes[temp] == Const.STRINGVALUE
						&& (cAggregates[temp] == Const.AVG || cAggregates[temp] == Const.SUM)) {
					throw new Exception(
							"Error: string value can not do <avg> or <sum> aggregates");
				}
				temp++;
			}
		}
		for (int i = 0; i < cNum; i++) {
			avg[i] = sum[i] = min[i] = max[i] = curr[i] = sr.tuples
					.get(groupStart)[cIndexes[i]];
		}
		if (exp != null) {
			for (int j = groupStart; j >= 0; j--) {
				if (j != 0
						&& cmpGroupColumns(sr.tuples.get(j),
								sr.tuples.get(j - 1), groupIndexes)) {
					count++;
					for (int i = 0; i < cNum; i++) {
						int vType = cValueTypes[i];
						int index = cIndexes[i];
						if (cmpByValueType(Const.GT, vType,
								sr.tuples.get(j - 1)[index], max[i])) {
							max[i] = sr.tuples.get(j - 1)[index];
						}
						if (cmpByValueType(Const.GT, vType, min[i],
								sr.tuples.get(j - 1)[index])) {
							min[i] = sr.tuples.get(j - 1)[index];
						}
						sum[i] = sumByValueType(vType, sum[i],
								sr.tuples.get(j - 1)[index]);
					}
				} else {
					String[] newValues = new String[cNum];
					for (int i = 0; i < cNum; i++) {
						switch (cAggregates[i]) {
						case Const.AVG:
							newValues[i] = avgByVauleType(cValueTypes[i],
									sum[i], count);
							break;
						case Const.MAX:
							newValues[i] = max[i];
							break;
						case Const.MIN:
							newValues[i] = min[i];
							break;
						case Const.SUM:
							newValues[i] = sum[i];
							break;
						case Const.COUNT:
							newValues[i] = count + "";
							break;
						}
					}
					sr.setColumns(j, cIndexes, newValues);
					for (int i = groupStart; i > j; i--) {
						sr.remove(i);
					}
					if (j != 0) {
						count = 1;
						groupStart = j - 1;
						for (int i = 0; i < cNum; i++) {
							avg[i] = sum[i] = min[i] = max[i] = curr[i] = sr.tuples
									.get(groupStart)[cIndexes[i]];
						}
					}
				}
			}
		} else {
			for (int j = groupStart; j > 0; j--) {
				count++;
				for (int i = 0; i < cNum; i++) {
					int vType = cValueTypes[i];
					int index = cIndexes[i];
					if (cmpByValueType(Const.GT, vType,
							sr.tuples.get(j - 1)[index], max[i])) {
						max[i] = sr.tuples.get(j - 1)[index];
					}
					if (cmpByValueType(Const.GT, vType, min[i],
							sr.tuples.get(j - 1)[index])) {
						min[i] = sr.tuples.get(j - 1)[index];
					}
					sum[i] = sumByValueType(vType, sum[i],
							sr.tuples.get(j - 1)[index]);
				}
			}
			String[] newValues = new String[cNum];
			for (int i = 0; i < cNum; i++) {
				switch (cAggregates[i]) {
				case Const.AVG:
					newValues[i] = avgByVauleType(cValueTypes[i], sum[i], count);
					break;
				case Const.MAX:
					newValues[i] = max[i];
					break;
				case Const.MIN:
					newValues[i] = min[i];
					break;
				case Const.SUM:
					newValues[i] = sum[i];
					break;
				case Const.COUNT:
					newValues[i] = count + "";
					break;
				}
			}
			for (int j = sr.tupleCount - 1; j >= 0; j--) {
				sr.setColumns(j, cIndexes, newValues);
			}
		}
		if (sr.columnCount == 1)
			sr.clearDup();
	}

	void order(SelectResult sr, OrderExp exp) {
	}

	void format(SelectResult sr) {
		int tCount = sr.tupleCount;
		int cCount = sr.columnCount;
		AttrDataType[] types = sr.columnDataTypes;
		for (int i = 0; i < tCount; i++) {
			String[] oldTuple = sr.getTuple(i);
			for (int j = 0; j < cCount; j++) {
				if (types[j].type == Const.DECIMAL) {
					DecimalFormat df = new DecimalFormat("#");
					df.setMaximumFractionDigits(types[j].s);
					df.setMinimumFractionDigits(types[j].s);
					df.setMinimumIntegerDigits(types[j].p - types[j].s);
					oldTuple[j] = df.format(new Double(oldTuple[j]));
				}
			}
			sr.setTuple(i, oldTuple);
		}
	}

	boolean cmpGroupColumns(String[] t1, String[] t2, int[] indexes) {
		int len = indexes.length;
		for (int i = 0; i < len; i++) {
			if (!t1[indexes[i]].equals(t2[indexes[i]])) {
				return false;
			}
		}
		return true;
	}

	boolean cmpColumnAndColumn(int opType, AttrDataType dTypeLeft,
			AttrDataType dTypeRight, String vLeft, String vRight)
			throws Exception {
		if (!dTypeLeft.equals(dTypeRight)) {
			throw new Exception("Error: data types do not agree");
		}
		if (vLeft.equals("$null") || vRight.equals("$null"))
			return false;
		if (vLeft.charAt(0) == '#') {
			return (opType == Const.EQ) && isIn(vRight, vLeft);
		}
		if (vRight.charAt(0) == '#') {
			return (opType == Const.EQ) && isIn(vLeft, vRight);
		}
		return cmpByValueType(opType, dTypeLeft.getValueType(), vLeft, vRight);
	}

	boolean isIn(String s1, String s2) {
		String[] sa = s2.split("#");
		for (int i = sa.length - 1; i >= 0; i--) {
			if (s1.equals(sa[i]))
				return true;
		}
		return false;
	}

	boolean cmpValueAndValue(int opType, int vTypeLeft, int vTypeRight,
			String vLeft, String vRight) throws Exception {
		if (vTypeLeft != vTypeRight) {
			throw new Exception("Error: data types do not agree");
		}
		if (vLeft.equals("$null") || vRight.equals("$null"))
			return false;
		return cmpByValueType(opType, vTypeLeft, vLeft, vRight);
	}

	boolean cmpByValueType(int opType, int vType, String vLeft, String vRight)
			throws Exception {
		if (vType == Const.INTVALUE) {
			Integer i1 = Integer.valueOf(vLeft);
			Integer i2 = Integer.valueOf(vRight);
			return cmpByOpType(opType, i1.compareTo(i2));
		}
		if (vType == Const.REALVALUE) {
			Double d1 = Double.valueOf(vLeft);
			Double d2 = Double.valueOf(vRight);
			return cmpByOpType(opType, d1.compareTo(d2));
		}
		if (vType == Const.STRINGVALUE) {
			return cmpByOpType(opType, vLeft.compareTo(vRight));
		}
		if (vType == Const.BOOLVALUE) {
			if (opType != Const.EQ || opType != Const.NEQ) {
				throw new Exception(
						"Error: bool values can only get comparison result equal or not equal");
			} else {
				if (opType == Const.EQ)
					return vLeft.equals(vRight);
				else
					return !vLeft.equals(vRight);
			}
		}
		return false;
	}

	boolean cmpByOpType(int opType, int cmp) {
		switch (opType) {
		case (Const.EQ):
			return cmp == 0;
		case (Const.NEQ):
			return cmp != 0;
		case (Const.GT):
			return cmp > 0;
		case (Const.GE):
			return cmp >= 0;
		case (Const.LT):
			return cmp < 0;
		default:
			return cmp <= 0;
		}
	}

	String avgByVauleType(int vType, String sum, int count) throws Exception {
		if (vType == Const.INTVALUE) {
			int avg = Integer.valueOf(sum) / count;
			return new String("" + avg);
		}
		if (vType == Const.REALVALUE) {
			double avg = Double.valueOf(sum) / count;
			return new String("" + avg);
		}
		{
			throw new Exception(
					"Error: only integer or real value can do <sum> aggregates");
		}
	}

	String sumByValueType(int vType, String s1, String s2) throws Exception {
		if (vType == Const.INTVALUE) {
			int sum = Integer.valueOf(s1) + Integer.valueOf(s2);
			return new String("" + sum);
		}
		if (vType == Const.REALVALUE) {
			double sum = Double.valueOf(s1) + Double.valueOf(s2);
			return new String("" + sum);
		}
		{
			throw new Exception(
					"Error: only integer or real value can do <avg> aggregates");
		}
	}

	SelectResult[] getTables(FromTableList tableList) throws Exception {
		// get tableCount
		int tableCount = 0;
		FromTableList list = tableList;
		while (list != null) {
			tableCount++;
			list = list.tail;
		}
		// get tables
		if (tableCount != 0) {
			SelectResult[] tables = new SelectResult[tableCount];
			for (int i = 0; i < tableCount; i++) {
				String tName = tableList.head.tName;
				String nName = tableList.head.nName;
				tableList = tableList.tail;
				int index = viewNameList.indexOf(tName);
				if (index != -1) {
					tables[i] = new SelectResult(viewContentList.get(i));
					if (nName != null)
						tables[i].setTableName(nName);
					continue;
				}
				TableInfo tableInfo = rm.getTableInfo(tName);
				if (tableInfo == null) {
					throw new Exception("Error: table or view <" + tName + "> "
							+ "not existing");
				}
				Record[] records = rm.getRecord(tName);
				if (records == null) {
					throw new Exception("Error: table or view <" + tName
							+ "> is empty");
				}
				if (nName != null) {
					tables[i] = new SelectResult(nName, tableInfo, records);
				} else {
					tables[i] = new SelectResult(tName, tableInfo, records);
				}
			}
			return tables;
		}
		return null;
	}

	SelectResult[] getTempTables(ArrayList<LogicalQueryPlan> plans)
			throws Exception {
		if (plans != null) {
			int size = plans.size();
			if (size != 0) {
				SelectResult[] tempTables = new SelectResult[size];
				SelectResult tempTable = null;
				// get results of plans and store the results into tempTables
				for (int i = 0; i < size; i++) {
					tempTable = select(plans.get(i));
					if (tempTable.columnCount > 1) {
						throw new Exception("Error: select opertion failed");
					}
					if (tempTable.tupleCount == 0) {
						tempTable.add(new String[] { "$null" });
					} else {
						if (tempTable.tupleCount > 1) {
							String str = "";
							for (int j = tempTable.tupleCount - 1; j >= 0; j--) {
								str = str + "#" + tempTable.tuples.get(j)[0];
							}
							tempTable.clear();
							tempTable.add(new String[] { str });
						}
					}
					tempTables[i] = tempTable;
				}
				return tempTables;
			}
		}
		return null;
	}

	int getColumnIndex(String cName, String tName, String[] cNames,
			String[] tNames) {
		int len = cNames.length;
		for (int i = 0; i < len; i++) {
			if (cName.equals(cNames[i])) {
				if (tName != null) {
					if (tName.equals(tNames[i]))
						return i;
				} else
					return i;
			}
		}
		return -1;
	}

	String getAggregateName(int type, String columnName, String tableName) {
		if (tableName != null)
			columnName = tableName + "." + columnName;
		switch (type) {
		case Const.AVG:
			columnName = "avg(" + columnName + ")";
			break;
		case Const.MAX:
			columnName = "max(" + columnName + ")";
			break;
		case Const.MIN:
			columnName = "min(" + columnName + ")";
			break;
		case Const.SUM:
			columnName = "sum(" + columnName + ")";
			break;
		case Const.COUNT:
			columnName = "count(" + columnName + ")";
			break;
		}
		return columnName;
	}

	byte[] getConditionMark(SelectResult sr, WhereCondExp exp) throws Exception {
		byte[] marks = null;
		if (exp != null) {
			int count = sr.tupleCount;
			marks = new byte[count];
			for (int i = count - 1; i >= 0; i--) {
				if (tupleMeetCond(sr, i, exp)) {
					marks[i] = 1;
				} else {
					marks[i] = 0;
				}
			}
		}
		return marks;
	}

}
