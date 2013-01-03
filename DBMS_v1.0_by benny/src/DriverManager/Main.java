package DriverManager;

import QueryManager.Parser.Parse;
import QueryManager.Parser.Absyn.*;

public class Main {
	public static void main(String args[]) {
		String fileName = "/home/micro2fly/workspace/testcases/testcase2.sql";
		Parse parser = new Parse(fileName);
		SqlExpList list = (SqlExpList) (parser.result);

		while (list != null) {
			SqlExp exp = list.head;
			list = list.tail;

			if (exp instanceof CreateTableExp) {
				createTable((CreateTableExp) exp);
				continue;
			}
			if (exp instanceof CreateViewExp) {
				createView((CreateViewExp) exp);
				continue;
			}
			if (exp instanceof CreateIndexExp) {
				createIndex((CreateIndexExp) exp);
				continue;
			}
		}

	}

	static void createTable(CreateTableExp e) {
	}
	static void createView(CreateViewExp e) {
	}
	static void createIndex(CreateIndexExp e) {
	}
}
