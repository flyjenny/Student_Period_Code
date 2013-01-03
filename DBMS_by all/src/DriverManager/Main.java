package DriverManager;

public class Main {

	public static void main(String[] args) {
		// 启动DriverManager
		DriverManager dm = new DriverManager();
		// dm.runSqlFile("testcases/测试样例.txt");
		dm.runSqlFile("testcases/testcase1.sql");
	}

}
