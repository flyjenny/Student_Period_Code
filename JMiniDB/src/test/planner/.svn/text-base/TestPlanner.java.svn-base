package test.planner;

import java.io.File;

import jminidb.JMiniDB;

import planner.Planner;

public class TestPlanner {
	public static void clean() {
		File fs = new File("d:\\dbtest");
		if (fs.exists()) {
			File fl[] = fs.listFiles();
			for (File f : fl) {
				f.delete();
			}
		}
		if (!fs.delete()) {
			throw new RuntimeException("unclose dir.");
		}

	}

	/**
	 * @return
	 */
	protected static Planner getPlane() {
		JMiniDB.initTest();
		return Planner.getSimplePlanner();
	}
}
