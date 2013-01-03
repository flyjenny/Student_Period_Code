package QueryManager.Parser;

import QueryManager.Parser.Absyn.Absyn;

public class Main {

	public static void main(String argv[]) {
		String fileName = argv[0];
		Parse parser = new Parse(fileName);
		Absyn root = parser.result;
		System.out.println(root.print(""));
	}

}