package Weibo;

import java.util.List;

import weibo4j.Paging;
import weibo4j.Status;
import weibo4j.User;
import weibo4j.Weibo;
import weibo4j.WeiboException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

import java.sql.*;

public class WeiboInitial {
	private String $token = "4aa705a76866ceef3ca3a858a9684592";
	private String $tokenSecret = "986423bdb97ab11e5b0a89a3a2c2e274";
	private Weibo weibo = null;
	private String driver = "com.mysql.jdbc.Driver";
	private String dburl = "jdbc:mysql://127.0.0.1:3306/china?useUnicode=true&characterEncoding=UTF-8";
	private String dbuser = "root";
	private String password = "chenbin";

	private Connection conn = null;
	private Statement statement = null;

	public static void main(String args[]) throws ClassNotFoundException,
			SQLException {
		String token = "4aa705a76866ceef3ca3a858a9684592";
		String tokenSecret = "986423bdb97ab11e5b0a89a3a2c2e274";
		WeiboInitial getfriends = new WeiboInitial(token, tokenSecret);
		getfriends.storeFriendsToDb();
		getfriends.storeLastStatusIdToDb();
		getfriends.updateFriendsToDb();
		getfriends.updateLastStatusIdToDb();
	}

	public WeiboInitial(String token, String tokenSecret) {
		this.$token = token;
		this.$tokenSecret = tokenSecret;
		System.setProperty("weibo4j.oauth.consumerKey", Weibo.CONSUMER_KEY);
		System.setProperty("weibo4j.oauth.consumerSecret",
				Weibo.CONSUMER_SECRET);
		weibo = new Weibo();
		weibo.setToken(token, tokenSecret);
		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void storeFriendsToDb() {
		try {
			conn = DriverManager.getConnection(dburl, dbuser, password);
			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			statement = conn.createStatement();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			User user = weibo.showUser("1347007924");// benny小斌id
			User friend = null;
			String executeStr = null;
			int count = user.getFriendsCount();
			count += 20;
			try {
				for (int i = 0; i < count; i += 20) {
					List<User> list = weibo.getFriendsStatuses(i);

					for (int j = 0; j < list.size(); j++) {
						friend = list.get(j);
						// insert into weibo.1347007924
						// values(1916624590,'禾苗苗flamia','f',104,133,142,3381210321761047,'@MissWeird
						// 我觉得2B是你的本性。。。//@国外奇趣精选:
						// 请理解天秤座，清高的并没有瞧不起谁，2B的只是为了你们能开心，幸福。')
						try {
							executeStr = "insert into weibo.1347007924_friendlist values("
									+ friend.getId()
									+ ",'"
									+ friend.getName()
									+ "','"
									+ friend.getGender()
									+ "',"
									+ friend.getFollowersCount()
									+ ","
									+ friend.getFriendsCount()
									+ ","
									+ friend.getStatusesCount()
									+ ","
									+ friend.getStatus().getId()
									+ ","
									+ "'"
									+ friend.getStatus().getText() + "')";
						} catch (NullPointerException e1) {
							executeStr = "insert into weibo.1347007924_friendlist values("
									+ friend.getId()
									+ ",'"
									+ friend.getName()
									+ "','"
									+ friend.getGender()
									+ "',"
									+ friend.getFollowersCount()
									+ ","
									+ friend.getFriendsCount()
									+ ","
									+ friend.getStatusesCount()
									+ ",0,"
									+ "'null')";

						}
						System.out.println(executeStr);
						try {
							statement.execute(executeStr);
						} catch (SQLException e) {

							executeStr = "insert into weibo.1347007924_friendlist values("
									+ friend.getId()
									+ ",'"
									+ friend.getName()
									+ "','"
									+ friend.getGender()
									+ "',"
									+ friend.getFollowersCount()
									+ ","
									+ friend.getFriendsCount()
									+ ","
									+ friend.getStatusesCount()
									+ ",0"
									+ ","
									+ "'null')";
							try {
								statement.execute(executeStr);
							} catch (MySQLIntegrityConstraintViolationException e1) {

							}
						}
					}
				}
				conn.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// System.exit(0);
		} catch (Exception ioe) {
			System.out.println("Failed to read the system input.");
			System.exit(-1);
		}
	}

	public void updateFriendsToDb() {
		try {
			conn = DriverManager.getConnection(dburl, dbuser, password);
			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			statement = conn.createStatement();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			User user = weibo.showUser("1347007924");// benny小斌id
			User friend = null;
			String executeStr = null;
			int count = user.getFriendsCount();
			count += 20;
			try {
				for (int i = 0; i < count; i += 20) {
					List<User> list = weibo.getFriendsStatuses(i);
					for (int j = 0; j < list.size(); j++) {
						friend = list.get(j);
						// UPDATE weibo.1347007924_friendlist SET
						// statusid=9,statusText='hello' WHERE userid=1087770692
						// and statusid>9;
						System.out.println(friend);
						try {
							executeStr = "UPDATE weibo.1347007924_friendlist SET statusCount="
									+ friend.getStatusesCount()
									+ ",statusid="
									+ friend.getStatus().getId()
									+ ",statusText='"
									+ friend.getStatus().getText()
									+ "' where statusCount<"
									+ friend.getStatusesCount()
									+ " and userid=" + friend.getId();
						} catch (NullPointerException e1) {
							executeStr = "UPDATE weibo.1347007924_friendlist SET statusCount="
									+ friend.getStatusesCount()
									+ ",statusid=0,statusText='null' where statusCount<"
									+ friend.getStatusesCount()
									+ " and userid=" + friend.getId();
						}

						System.out.println(executeStr);
						try {
							statement.execute(executeStr);
						} catch (SQLException e) {
							executeStr = "UPDATE weibo.1347007924_friendlist SET statusCount="
									+ friend.getStatusesCount()
									+ ",statusid="
									+ friend.getStatus().getId()
									+ ",statusText='null"
									+ "' where statusCount<"
									+ friend.getStatusesCount()
									+ " and userid=" + friend.getId();
							statement.execute(executeStr);
						}
					}
				}
				conn.close();

			} catch (Exception e1) {
				e1.printStackTrace();
			}
			// System.exit(0);
		} catch (Exception ioe) {
			System.out.println("Failed to read the system input.");
			System.exit(-1);
		}
	}

	public void storeLastStatusIdToDb() {
		try {
			conn = DriverManager.getConnection(dburl, dbuser, password);
			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			statement = conn.createStatement();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		String executeStr = null;
		try {
			Paging page = new Paging(1, 1);
			List<Status> statuses = weibo.getFriendsTimeline(page);
			executeStr = "insert into weibo.laststatusid values(1347007924,"
					+ statuses.get(0).getId() + ")";
			 System.out.println(executeStr);
			try {
				statement.execute(executeStr);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn.close();
		} catch (WeiboException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public void updateLastStatusIdToDb() {
		try {
			conn = DriverManager.getConnection(dburl, dbuser, password);
			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			statement = conn.createStatement();
		} catch (SQLException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		try {
			Paging page = new Paging(1, 1);
			List<Status> statuses = weibo.getFriendsTimeline(page);
			String executeStr = "UPDATE weibo.laststatusid SET statusid="
					+ statuses.get(0).getId() + " where statusid<"
					+ statuses.get(0).getId();
			// System.out.println(executeStr);
			try {
				statement.execute(executeStr);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			conn.close();
		} catch (WeiboException e) {
			e.printStackTrace();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
}
