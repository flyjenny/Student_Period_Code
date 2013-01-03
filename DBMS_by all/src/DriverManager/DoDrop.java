package DriverManager;

import java.io.UnsupportedEncodingException;

import Absyn.*;
import UserInterface.UserInterface;

class DoDrop extends DriverManager {
	DoDrop() {

	}

	void run(DropExp exp) throws Exception {
		if (exp.type == Const.TABLE)
			dropTable(exp);
		else {
			if (exp.type == Const.VIEW)
				dropView(exp);
			else
				dropIndex(exp);
		}
	}

	void dropTable(DropExp exp) throws Exception {
		UserInterface.changeshow("Processing: drop a table ...");

		IdList iList = exp.list;
		while (iList != null) {
			String tableName = iList.head;
			try {
				rm.DeleteTable(tableName);
			} catch (UnsupportedEncodingException e) {
				throw new Exception(
						"Error: endcoding error occurs in disk manager");
			}
			iList = iList.tail;
		}

		UserInterface.changeshow("Finished.");
	}

	void dropView(DropExp exp) throws Exception {
		UserInterface.changeshow("Processing: drop a view ...");

		IdList iList = exp.list;
		while (iList != null) {
			String viewName = iList.head;
			int index = viewNameList.indexOf(viewName);
			if (index != -1) {
				viewNameList.remove(index);
				viewContentList.remove(index);
			} else {
				throw new Exception("Error: view<" + viewName
						+ "> not existing");
			}
			iList = iList.tail;
		}

		UserInterface.changeshow("Finished.");
	}

	void dropIndex(DropExp exp) {
		UserInterface.changeshow("Processing: drop an index ...");

		UserInterface.changeshow("Finished.");
	}
}
