package DriverManager;

import java.util.Vector;

import Absyn.BoolExp;
import Absyn.IdList;
import Absyn.InsertExp;
import Absyn.IntExp;
import Absyn.RealExp;
import Absyn.StrExp;
import Absyn.ValueExp;
import Absyn.ValueExpList;

public class DoInsert {
	public DoInsert(InsertExp exp) {
		insert(exp);
	}

	void insert(InsertExp exp) {
		System.out.println("Processing: insert into a table ...");

		String tableName = exp.name;
		// test the legality of tableName
		// get the feature of the table
		// modify mark
		IdList iList = exp.iList;
		Vector<String> columnNames = new Vector<String>();
		while (iList != null) {
			String head = iList.head;
			columnNames.add(head);
			iList = iList.tail;
		}
		if (!columnNames.isEmpty()) {
			// test the legality of the list of columnName
		}
		ValueExpList vList = exp.vlist;
		Vector<String> values = new Vector<String>();
		while (vList != null) {
			String value = null;
			ValueExp head = vList.head;
			if (head instanceof IntExp) {
				value = (((IntExp) head).value) + "";
				continue;
			}
			if (head instanceof RealExp) {
				value = (((RealExp) head).value) + "";
				continue;
			}
			if (head instanceof StrExp) {
				value = (((StrExp) head).value);
				continue;
			}
			if (head instanceof BoolExp) {
				value = (((BoolExp) head).value) + "";
				continue;
			}
			// test the legality of the value
			// modify mark
			values.add(value);
		}
		// insert

		System.out.println("Finished.");
	}
}
