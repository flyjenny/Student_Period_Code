package DriverManager;

import Record.Record;
import Record.SelectResult;
import Record.TableInfo;
import UserInterface.UserInterface;
import Absyn.*;

public class DoDelete extends DriverManager {
	DoDelete() {

	}

	void run(DeleteExp exp) throws Exception {
		UserInterface.changeshow("Processing: delete tuples in a table ...");

		delete(exp);

		UserInterface.changeshow("Finished.");
	}

	void delete(DeleteExp exp) throws Exception {
		String tName = exp.name;
		TableInfo tableInfo = rm.getTableInfo(tName);
		if (tableInfo == null) {
			throw new Exception("Error: table <" + tName + "> "
					+ "not existing");
		}
		Record[] records = rm.getRecord(tName);
		if (records == null) {
			throw new Exception("Error: table <" + tName + "> is empty");
		}
		SelectResult sr = new SelectResult(tName, tableInfo, records);
		DoSelect ds = new DoSelect();
		byte[] marks = ds.getConditionMark(sr, exp.cond);
		// call api
		rm.DeleteRecord(tName, marks);
	}

}
