package DriverManager;

import Absyn.*;
import QueryManager.Parser.Parse;

public class Main {

	public static void main(String[] args) {
		String fileName = "/home/micro2fly/workspace/testcases/testcase1.sql";
		Parse parser = new Parse(fileName);
		SqlExpList list = (SqlExpList) (parser.result);

		while (list != null) {
			SqlExp exp = list.head;
			list = list.tail;

			if (exp instanceof CreateExp) {
				new DoCreate((CreateExp) exp);
				continue;
			}
			if (exp instanceof DropExp) {
				new DoDrop((DropExp) exp);
				continue;
			}
			if (exp instanceof InsertExp) {
				new DoInsert((InsertExp) exp);
				continue;
			}
			if (exp instanceof SelectExp) {
				new DoSelect((SelectExp) exp);
				continue;
			}
			System.out.println("Error.");
		}
	}

}
