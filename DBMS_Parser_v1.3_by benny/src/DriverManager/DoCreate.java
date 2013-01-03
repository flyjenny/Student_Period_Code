package DriverManager;

import Absyn.BoolExp;
import Absyn.ColumnCons;
import Absyn.ColumnDef;
import Absyn.ColumnDefList;
import Absyn.Const;
import Absyn.CreateExp;
import Absyn.CreateIndexExp;
import Absyn.CreateTableExp;
import Absyn.CreateViewExp;
import Absyn.IntExp;
import Absyn.RealExp;
import Absyn.StrExp;
import Absyn.ValueExp;
import Record.AttrDataType;
import Record.TableInfo;

public class DoCreate {
	public DoCreate(CreateExp exp) {
		if (exp instanceof CreateTableExp) {
			createTable((CreateTableExp) exp);
		} else {
			if (exp instanceof CreateViewExp) {
				createView((CreateViewExp) exp);
			} else {
				createIndex((CreateIndexExp) exp);
			}
		}
	}

	void createTable(CreateTableExp exp) {
		System.out.println("Processing: create a table ...");

		String tableName = exp.name;
		// test the legality of tableName
		// modify mark
		// get the columnDefCount
		ColumnDefList list = exp.list;
		int columnDefCount = 0;
		while (list != null) {
			list = list.tail;
			columnDefCount++;
		}
		// attributes for TableFeature
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
			String dv = "";
			int cons = Const.NOCONS;
			ColumnCons columnCons = columnDef.cons;
			if (columnCons != null) {
				ValueExp valueExp = columnCons.value;
				if (valueExp != null) {
					do {
						if (valueExp instanceof IntExp) {
							dv = ((IntExp) valueExp).value + "";
							break;
						}
						if (valueExp instanceof RealExp) {
							dv = ((RealExp) valueExp).value + "";
							break;
						}
						if (valueExp instanceof StrExp) {
							dv = ((StrExp) valueExp).value;
							break;
						}
						if (valueExp instanceof BoolExp) {
							dv = ((BoolExp) valueExp).value + "";
							break;
						}
					} while (false);
				}
			}
			values[i] = dv;
			constraints[i] = cons;
		}
		// new TableInfo
		TableInfo tableInfo= new TableInfo(tableName, types, names, values,
				constraints);
		// modify mark

		System.out.println("Finished.");
	}

	void createView(CreateViewExp exp) {
		System.out.println("Processing: create a view ...");
		System.out.println("Finished.");
	}

	void createIndex(CreateIndexExp exp) {
		System.out.println("Processing: create an index ...");
		System.out.println("Finished.");
	}

}
