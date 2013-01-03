package DriverManager;

import Absyn.Const;
import Absyn.DropExp;
import Absyn.IdList;

public class DoDrop {
	public DoDrop(DropExp exp) {
		if (exp.type == Const.TABLE)
			dropTable(exp);
		else {
			if (exp.type == Const.VIEW)
				dropView(exp);
			else
				dropIndex(exp);
		}
	}

	void dropTable(DropExp exp) {
		System.out.println("Processing: drop a table ...");

		IdList iList = exp.list;
		while (iList != null) {
			String tableName = iList.head;
			// drop the table
			iList = iList.tail;
		}

		System.out.println("Finished.");
	}

	void dropView(DropExp exp) {
		System.out.println("Processing: drop a view ...");
		
		IdList iList = exp.list;
		while (iList != null) {
			String viewName = iList.head;
			// drop the view
			iList = iList.tail;
		}
		
		System.out.println("Finished.");
	}

	void dropIndex(DropExp exp) {
		System.out.println("Processing: drop an index ...");
		
		IdList iList = exp.list;
		while (iList != null) {
			String indexName = iList.head;
			// drop the index
			iList = iList.tail;
		}
		
		System.out.println("Finished.");
	}
}
