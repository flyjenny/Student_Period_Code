package DriverManager;

import Absyn.*;
import Record.Record;
import UserInterface.UserInterface;

class DoInsert extends DriverManager {
	DoInsert() {

	}

	void run(InsertExp exp) throws Exception {
		UserInterface.changeshow("Processing: insert into a table ...");

		insert(exp);

		UserInterface.changeshow("Finished.");
	}

	void insert(InsertExp exp) throws Exception {
		// test if the table exists
		String tableName = exp.name;
		if (!rm.tableExists(tableName)) {
			throw new Exception("Error: table <" + tableName + "> not existing");
		}
		// get tableInfo

		// get columnNames
		IdList iList = exp.iList;
		String[] columnNames = null;
		if (iList != null) {
			columnNames = null;
			int cCount = 0;
			while (iList != null) {
				cCount++;
				iList = iList.tail;
			}
			columnNames = new String[cCount];
			iList = exp.iList;
			for (int i = 0; i < cCount; i++) {
				columnNames[i] = iList.head;
				iList = iList.tail;
			}
		} else {
			columnNames = rm.getTableInfo(tableName).attrNames;
		}
		// test the legality of columnNames

		// get values
		ValueExpList vList = exp.vlist;
		String[] values = null;
		int vCount = 0;
		while (vList != null) {
			vCount++;
			vList = vList.tail;
		}
		values = new String[vCount];
		vList = exp.vlist;
		for (int j = 0; j < vCount; j++) {
			values[j] = vList.head.getValue();
//			System.out.println(values[j]);
			vList = vList.tail;
		}
		// test the legality of values

		// insert
		rm.InsertRecord(tableName, new Record(columnNames, values));
	}
}
