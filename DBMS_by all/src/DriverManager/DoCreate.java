package DriverManager;

import Absyn.*;
import QueryManager.Plans.Plan;
import Record.AttrDataType;
import Record.SelectResult;
import Record.TableInfo;
import UserInterface.UserInterface;

class DoCreate extends DriverManager {
	DoCreate() {

	}

	void run(Plan plan) throws Exception {
		CreateExp exp = plan.createexp;
		if (exp instanceof CreateTableExp)
			createTable((CreateTableExp) exp);
		else
			createView(plan);
	}

	void createTable(CreateTableExp exp) throws Exception {
		UserInterface.changeshow("Processing: create a table ...");

		String tableName = exp.name;
		// test the legality of tableName
		int index = viewNameList.indexOf(tableName);
		if (index != -1) {
			throw new Exception("Error: view <" + tableName
					+ "> has already existed");
		}
		if (rm.tableExists(tableName)) {
			throw new Exception("Error: table <" + tableName
					+ "> has already existed");
		}
		// get the columnDefCount
		ColumnDefList list = exp.list;
		int columnDefCount = 0;
		while (list != null) {
			list = list.tail;
			columnDefCount++;
		}
		// attributes for TableInfo
		AttrDataType[] types = new AttrDataType[columnDefCount];
		String[] names = new String[columnDefCount];
		String[] values = new String[columnDefCount];
		int[] constraints = new int[columnDefCount];
		// fill those arrays
		list = exp.list;
		for (int i = 0; i < columnDefCount; i++) {
			ColumnDef columnDef = list.head;
			list = list.tail;
			// get attrDataType
			types[i] = new AttrDataType(columnDef.dataType);
			// get attrName
			names[i] = columnDef.name;
			// get attrDefaultValue & attrConstraint
			String dv = "$null";
			int cons = Const.NOCONS;
			ColumnCons columnCons = columnDef.cons;
			if (columnCons != null) {
				ValueExp valueExp = columnCons.value;
				if (valueExp != null) {
					if (!(valueExp instanceof NullExp)) {
						dv = valueExp.getValue();
					}
				}
				cons = columnCons.type;
			}
			values[i] = dv;
			constraints[i] = cons;
		}
		TableInfo tableInfo = new TableInfo(tableName, types, names, values,
				constraints);
		rm.CreateTable(tableInfo);

		UserInterface.changeshow("Finished.");
	}

	void createView(Plan plan) throws Exception {
		UserInterface.changeshow("Processing: create a view ...");

		String viewName = ((CreateViewExp) (plan.createexp)).name;
		if (viewNameList.contains(viewName)) {
			throw new Exception("Error: view <" + viewName
					+ "> has already existed");
		}
		try {
			DoSelect ds = new DoSelect();
			ds.run(plan);
			SelectResult sr = ds.result;
			if (sr != null) {
				viewNameList.add(viewName);
				viewContentList.add(sr);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		UserInterface.changeshow("Finished.");
	}

	void createIndex(CreateIndexExp exp) {
		UserInterface.changeshow("Processing: create an index ...");

		UserInterface.changeshow("Finished.");
	}

}
