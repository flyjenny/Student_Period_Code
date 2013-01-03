package weibo4j.clawer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class StoreToMySql {
	private String dburl = null;
	private String dbuser = null;
	private String password = null;

	private Connection conn = null;
	private Statement statement = null;
	
	private int $duplicateCount=0;

	public StoreToMySql() {
		dburl=DbConfig.getValue("db_baseurl")+DbConfig.getValue("db_schema")+DbConfig.getValue("character_set");
		dbuser=DbConfig.getValue("db_user");
		password=DbConfig.getValue("db_pwd");
	}

	public void storeToDb(String str) {
		try {
			conn = DriverManager.getConnection(dburl, dbuser, password);
			statement = conn.createStatement();
			statement.execute(str);
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e1) {
			$duplicateCount++;
		} catch (SQLException e) {
			if (e.getErrorCode() == 1366) {
				String newStr = formatStr(str);
				try {
					statement.execute(newStr);
				} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e1) {
					$duplicateCount++;
//					System.out.println("RepostFrom DUPLICATE ENTRY");
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					StorePublicTimelineToMySql.logger.debug("inner Exception:"+newStr);
					StorePublicTimelineToMySql.logger.debug(e1.getMessage());
					e1.printStackTrace();
				}
			}
		}
	}

	public String formatStr(String str) {
		char[] newChar = str.toCharArray();
		String newStr = new String();
		for (int i = 0; i < newChar.length; i++) {
			try {
				if ((int) newChar[i] == 55356 || (int) newChar[i] == 55357) {

					i += 2;
				}
				if ((int) newChar[i] == 10024 || (int) newChar[i] == 10084) {
					i++;
				}
				if ((int) newChar[i] != 55356 && (int) newChar[i] != 55357
						&& (int) newChar[i] != 10024
						&& (int) newChar[i] != 10084) {
					newStr += newChar[i];
				} else {
					i--;
				}
			} catch (java.lang.ArrayIndexOutOfBoundsException e) {

			}
		}
		return newStr;
	}
	public void setDuplicateCount(){
		this.$duplicateCount=0;
	}
	public int getDupblicteCount(){
		return this.$duplicateCount;
	}
}
