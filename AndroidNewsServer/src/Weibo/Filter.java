package Weibo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import weibo4j.Paging;
import weibo4j.Status;
import weibo4j.Weibo;
import weibo4j.WeiboException;

public class Filter {
	private WeiboInitial getFriends = null;
	private String $token = "4aa705a76866ceef3ca3a858a9684592";
	private String $tokenSecret = "986423bdb97ab11e5b0a89a3a2c2e274";
	private Weibo weibo = null;
	private String driver = "com.mysql.jdbc.Driver";
	private String dburl = "jdbc:mysql://127.0.0.1:3306/china?useUnicode=true&characterEncoding=UTF-8";
	private String dbuser = "root";
	private String password = "chenbin";

	private Connection conn = null;
	private Statement statement = null;

	public Filter() {

	}

	public void filterToDb(List<String> keywords) throws SQLException,
			WeiboException {
		conn = DriverManager.getConnection(dburl, dbuser, password);
		statement = conn.createStatement();
		String sql = "select * from weibo.laststatusid where id=1347007924";
		ResultSet rs = statement.executeQuery(sql);
		rs.next();
		long statusid = rs.getLong("statusid");
		String executeStr = null;
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		System.setProperty("weibo4j.oauth.consumerSecret",
				Weibo.CONSUMER_SECRET);
		Weibo weibo = new Weibo();
		weibo.setToken($token, $tokenSecret);
		int pageNum = 1;
		long LatesStutasId = 0;
		long currentStutasId = 0;
		List<Status> statuses = null;
		do {
			Paging page = new Paging(pageNum);
			statuses = weibo.getFriendsTimeline(page);
			LatesStutasId = statuses.get(0).getId();
			for (Status status : statuses) {
				currentStutasId = status.getId();
				if (currentStutasId > statusid) {
					for (String text : keywords) {
						if (status.getText().indexOf(text) > -1) {
							executeStr = "insert into weibo.1347007924_tobesent values("
									+ status.getId()
									+ ",'"
									+ status.getUser().getName()
									+ "','"
									+ status.getText() + "')";
							System.out.println(executeStr);
							try {
								statement.execute(executeStr);
							} catch (MySQLIntegrityConstraintViolationException e) {

							}
							break;
						}
					}
				} else {
					break;
				}
			}
			pageNum++;
		} while (currentStutasId > statusid);
		executeStr = "UPDATE weibo.laststatusid SET statusid=" + LatesStutasId
				+ " where statusid<" + LatesStutasId + " and id=1347007924";
		try {
			System.out.println(executeStr);
			statement.execute(executeStr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.close();
	}

	public void filterToDb(Map<String, List<String>> map) throws SQLException,
			WeiboException {
		conn = DriverManager.getConnection(dburl, dbuser, password);
		statement = conn.createStatement();
		String sql = "select * from weibo.laststatusid where id=1347007924";
		ResultSet rs = statement.executeQuery(sql);
		rs.next();
		long statusid = rs.getLong("statusid");
		String executeStr = null;
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		System.setProperty("weibo4j.oauth.consumerSecret",
				Weibo.CONSUMER_SECRET);
		Weibo weibo = new Weibo();
		weibo.setToken($token, $tokenSecret);
		
		Set<String> key = map.keySet();
		String name = null;

		int pageNum = 1;
		long LatesStutasId = 0;
		long currentStutasId = 0;
		String currentName = null;
		List<Status> statuses = null;
		boolean mark = false;
		do {
			Paging page = new Paging(pageNum);
			statuses = weibo.getFriendsTimeline(page);
			LatesStutasId = statuses.get(0).getId();
			for (Status status : statuses) {
				currentStutasId = status.getId();
				currentName = status.getUser().getName();
				if (currentStutasId > statusid) {
					mark = false;
					for (Iterator it = key.iterator(); it.hasNext();) {
						name = (String) it.next();
						if (name.equals(currentName)) {
							mark = true;
							break;
						}
					}
					if (mark) {
						List<String> keywords = (List<String>) map.get(name);
						for (String text : keywords) {
							if (status.getText().indexOf(text) > -1) {
								executeStr = "insert into weibo.1347007924_tobesent values("
										+ status.getId()
										+ ",'"
										+ status.getUser().getName()
										+ "','"
										+ status.getText() + "')";
								System.out.println(executeStr);
								try {
									statement.execute(executeStr);
								} catch (MySQLIntegrityConstraintViolationException e) {

								}
								break;
							}
						}
					}
				} else {
					break;
				}
			}
			pageNum++;
		} while (currentStutasId > statusid);
		executeStr = "UPDATE weibo.laststatusid SET statusid=" + LatesStutasId
				+ " where statusid<" + LatesStutasId + " and id=1347007924";
		try {
			System.out.println(executeStr);
			statement.execute(executeStr);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		conn.close();

	}

	public static void main(String[] args) throws SQLException, InterruptedException {
		Filter filter = new Filter();
		List<String> keywords = new ArrayList<String>();
		keywords.add("test");
		keywords.add("我");
		Map<String, List<String>> map = new HashMap<String, List<String>>();
		map.put("benny小斌", keywords);
		map.put("柒闲女", keywords);
		map.put("请叫我张建涛", keywords);
		while (true) {
			try {
				filter.filterToDb(map);
			} catch (WeiboException e) {
				e.printStackTrace();
			}
			Thread.sleep(30000);
		}

	}
}
