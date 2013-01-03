package DriverManager;

import java.io.*;
import java.util.ArrayList;

import QueryManager.Plans.Marks;
import QueryManager.Plans.Plan;
import Record.SelectResult;
import RecordManager.RecordManager;
import UserInterface.UserInterface;

public class DriverManager {
	static boolean firstStart = true;
	static RecordManager rm = null;
	static ArrayList<String> viewNameList = new ArrayList<String>();;
	static ArrayList<SelectResult> viewContentList = new ArrayList<SelectResult>();

	private DoAlter doAlter = null;
	private DoCreate doCreate = null;
	private DoDelete doDelete = null;
	private DoDrop doDrop = null;
	private DoInsert doInsert = null;
	private DoSelect doSelect = null;
	private DoUpdate doUpdate = null;

	public DriverManager() {
		if (firstStart) {
			// 启动RecordManager
			rm = new RecordManager(1 * 1024 * 1024, 1, "test19");
			// rm = new RecordManager();
			firstStart = false;
			// 启动各子引擎
			doAlter = new DoAlter();
			doCreate = new DoCreate();
			doDelete = new DoDelete();
			doDrop = new DoDrop();
			doInsert = new DoInsert();
			doSelect = new DoSelect();
			doUpdate = new DoUpdate();
		}

	}

	public void runSqlFile(String fileName) {
		String sqlStrList = openFile(fileName, "utf-8");
		if (sqlStrList != null) {
			runSqlList(sqlStrList);
		}
	}

	public void runSqlList(String sqls) {
		ArrayList<String> sqlList = cutToSqlList(sqls);
		int len = sqlList.size();
		for (int i = 0; i < len; i++) {
			System.out.println("………………………………………………………………………");
			String sql = sqlList.get(i);
			System.out.println(sql);
			if (!runSql(sql))
				break;
		}
	}

	public boolean runSql(String sql) {
		try {
			Plan plan = new Plan(sql);
			switch (plan.type) {
			case Marks.ALTEREXP:
				doAlter.run(plan.alterexp);
				break;
			case Marks.CREATEEXP:
				doCreate.run(plan);
				break;
			case Marks.DELETEEXP:
				doDelete.run(plan.deleteexp);
				break;
			case Marks.DROPEXP:
				doDrop.run(plan.dropexp);
				break;
			case Marks.INSERTEXP:
				doInsert.run(plan.insertexp);
				break;
			case Marks.SELECTEXP:
				doSelect.run(plan);
				break;
			case Marks.UPDATEEXP:
				doUpdate.run(plan.updateexp);
				break;
			}
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}

	public String openFile(String fileName, String encode) {
		BufferedReader bis = null;
		try {
			bis = new BufferedReader(new InputStreamReader(new FileInputStream(
					new File(fileName)), encode));
			String content = "";
			String temp;
			while ((temp = bis.readLine()) != null) {
				content += temp + "\n";
			}
			return content;
		} catch (UnsupportedEncodingException e) {
			System.out.println(e.getMessage());
			return null;
		} catch (FileNotFoundException e) {
			System.out.println(e.getMessage());
			return null;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		} finally {
			try {
				bis.close();
			} catch (IOException e) {
			}
		}
	}

	ArrayList<String> cutToSqlList(String sqls) {
		ArrayList<String> sqlList = new ArrayList<String>();
		if (sqls == null)
			return sqlList;
		BufferedReader bis = new BufferedReader(new StringReader(sqls));
		String temp = null;
		String sql = "";
		try {
			while ((temp = bis.readLine()) != null) {
				if (!temp.startsWith("//")) {
					sql += sql + temp;
					if (sql.endsWith(";")) {
						sql = sql.substring(0, sql.length() - 1);
						sqlList.add(sql);
						sql = "";
					} else {
						sql += "\n";
					}
				}
			}
			return sqlList;
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return sqlList;
		} finally {
			try {
				bis.close();
			} catch (IOException e) {
			}
		}
	}

}
