package DriverManager;

import Absyn.*;
import Record.Record;
import Record.SelectResult;
import Record.TableInfo;
import UserInterface.UserInterface;

public class DoUpdate extends DriverManager {
	DoUpdate() {

	}

	void run(UpdateExp exp) throws Exception {
		UserInterface.changeshow("Processing: update a table ...");

		update(exp);

		UserInterface.changeshow("Finished.");
	}

	void update(UpdateExp exp) throws Exception {
		String tName = exp.name;
		// get tableInfo & test the legality of tName
		TableInfo tableInfo = rm.getTableInfo(tName);
		if (tableInfo == null) {
			throw new Exception("Error: table <" + tName + "> "
					+ "not existing");
		}
		// store table <tName> to SelectResult
		Record[] records = rm.getRecord(tName);
		if (records == null) {
			throw new Exception("Error: table <" + tName + "> is empty");
		}
		SelectResult sr = new SelectResult(tName, tableInfo, records);
		int attrCount = tableInfo.attrNames.length;
		int vcharCount = 0;
		int recordCount = records.length;
		boolean[] markArray = new boolean[attrCount];
		boolean[] vcharMarkArray = null;
		for (int i = 0; i < attrCount; i++) {
			markArray[i] = false;
			if (tableInfo.attrDataTypes[i].type == Const.VARCHAR) {
				vcharCount++;
			}
		}
		if (vcharCount != 0)
			vcharMarkArray = new boolean[vcharCount];
		// do condition & get to know which tuples to update
		DoSelect ds = new DoSelect();
		byte[] marks = ds.getConditionMark(sr, exp.cond);
		// get to know which columns to update
		UpdateColumnList list = exp.list;
		int count = 0;
		while (list != null) {
			count++;
			list = list.tail;
		}
		String[] columnNames = new String[count];
		String[] values = new String[count];
		int[] indexes = new int[count];
		list = exp.list;
		for (int i = 0; i < count; i++) {
			UpdateColumn uc = list.head;
			columnNames[i] = new String(uc.name);
			int index = tableInfo.indexOfAttr(columnNames[i]);
			if (index == -1) {
				throw new Exception("Error: table <" + tName
						+ "> does not have column <" + columnNames[i] + ">");
			}
			indexes[i] = index;
			if (tableInfo.attrDataTypes[index].type == Const.VARCHAR) {
				markArray[index] = true;
			}
			values[i] = new String(uc.value.getValue());
			list = list.tail;
		}
		if (vcharCount != 0) {
			int temp = 0;
			for (int j = 0; j < attrCount; j++) {
				if (tableInfo.attrDataTypes[j].type == Const.VARCHAR) {
					vcharMarkArray[temp] = markArray[j];
					temp++;
				}
			}
		}
		// modify Record[] records
		for (int i = 0; i < recordCount; i++) {
			for (int j = 0; j < count; j++) {
				records[i].attrValue[indexes[j]] = new String(values[j]);
			}
		}
		// call api
		rm.UpdateRecord(tName, records, marks, vcharMarkArray);
	}

}
