package weibo4j.Clawer;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class StoreToMySql {
	private String dburl = "jdbc:mysql://127.0.0.1:3306/weibo?useUnicode=true&characterEncoding=UTF-8";
	private String dbuser = "root";
	private String password = "chenbin";

	private Connection conn = null;
	private Statement statement = null;

	public StoreToMySql() {
	}

	public void storeToDb(String str) {
		try {
			conn = DriverManager.getConnection(dburl, dbuser, password);
			// if (!conn.isClosed()){
			// System.out.println("Succeeded connecting to the Database!");
			// }
			statement = conn.createStatement();
			statement.execute(str);
		} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e1) {
			System.out.println("DUPLICATE ENTRY");
		} catch (SQLException e) {
			if (e.getErrorCode() == 1366) {
				String newStr = formatStr(str);
				try {
					statement.execute(newStr);
				} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e1) {
					System.out.println("DUPLICATE ENTRY");
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

	public static void main(String[] args) {
		String str = "insert into weibo.weibo1 values(3403595427340105,'2012-01-19 17:04:09','美洋洋很喜欢我哦！[可爱]',-1,-1,'',-1,0,0,'2011275735','我是happy胖天使',12,1000,'天津','','f',160,149,854,73,'2011-03-07 00:00:00','n',-1,'')";
		StoreToMySql stms = new StoreToMySql();
		stms.storeToDb(str);

	}
}
