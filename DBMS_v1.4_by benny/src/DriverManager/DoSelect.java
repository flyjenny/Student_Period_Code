package DriverManager;

import Absyn.AggregateExp;
import Absyn.AlgebraOp;
import Absyn.Column;
import Absyn.Const;
import Absyn.SelectBinExp;
import Absyn.SelectColumn;
import Absyn.SelectColumnList;
import Absyn.SelectExp;
import Absyn.SelectOp;
import Absyn.WhereExp;
import Record.AttrDataType;
import Record.SelectResult;

public class DoSelect {
	public DoSelect(SelectExp exp) {
		System.out.println("Processing: query a table ...");
		select(exp);
		System.out.println("Finished.");
	}

	SelectResult select(SelectExp exp) {
		if (exp instanceof SelectOp) {
			SelectOp e = (SelectOp) exp;
			WhereExp whereExp = e.whereExp;
			SelectResult selectResult = new SelectResult();
			if (e.tList.tail == null) {
				if (e.tList.head.selectExp == null) {
					String tableName = e.tList.head.tName;
					String tableNewName = e.tList.head.nName;
					// test the legality of tableName
					// get the feature of the table
					// modify mark
					if (!e.selectAll) {
						SelectColumnList tail = e.cList;
						while (tail != null) {
							SelectColumn head = e.cList.head;
							tail = e.cList.tail;
							if (head.algebraExp instanceof AlgebraOp) {
								Column column = ((AlgebraOp) head.algebraExp).column;
								AggregateExp aggregateExp = ((AlgebraOp) head.algebraExp).aggregateExp;
								String tName = null;
								String columnName = null;
								String columnNewName = null;
								int columnAggregate = Const.INVALID;
								AttrDataType dataType = null;
								// get tName, columnName & columnAggregate
								if (column != null) {
									tName = column.tName;
									columnName = column.cName;
								} else {
									if (aggregateExp != null) {
										tName = aggregateExp.column.tName;
										columnName = aggregateExp.column.cName;
										columnAggregate = aggregateExp.type;
									} else {
										// error
										System.out.println("error");
									}
								}
								// test the legality of columnName
								// get the dataType of columnName
								// modify mark
								dataType = new AttrDataType(0, 0, 0, 0);
								// test the legality of tName
								if (tName != null) {
									if (tableNewName != null) {
										if (!tName.equals(tableNewName)) {
											// error
											System.out.println("error");
										}
									} else {
										if (!tName.equals(tableName)) {
											// error
											System.out.println("error");
										}
									}
								}
								// get the display name of the column
								if (head.name != null)
									columnNewName = head.name;
								else {
									if (tName != null)
										columnNewName = tName + "."
												+ columnName;
									else
										columnNewName = columnName;
									if (aggregateExp != null) {
										switch (aggregateExp.type) {
										case Const.AVG:
											columnNewName = "avg("
													+ columnNewName + ")";
											break;
										case Const.MAX:
											columnNewName = "max("
													+ columnNewName + ")";
											break;
										case Const.MIN:
											columnNewName = "min("
													+ columnNewName + ")";
											break;
										case Const.SUM:
											columnNewName = "sum("
													+ columnNewName + ")";
											break;
										case Const.COUNT:
											columnNewName = "count("
													+ columnNewName + ")";
											break;
										}
									}
								}
								//
								selectResult.columnNames.add(columnName);
								selectResult.tableNames.add(tableName);
								selectResult.columnNewNames.add(columnNewName);
								selectResult.columnAggregates
										.add(columnAggregate);
								selectResult.columnDataTypes.add(dataType);
								selectResult.columnCount++;
							} else {
								// error: AlgebraBinExp
							}
						}
					}
					// read the table
					// handle where condition
					// modify mark
				} else {
					// error: select from a temporary table
				}
			}
			return selectResult;
		} else {
			SelectBinExp e = (SelectBinExp) exp;
			return select(e.left).unionWith(e.unionAll, select(e.right));
		}
	}

}
