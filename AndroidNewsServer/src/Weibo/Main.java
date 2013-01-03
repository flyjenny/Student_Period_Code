package Weibo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import weibo4j.WeiboException;

public class Main {
	public static List<String> getToBeSent(){
		String driver = "com.mysql.jdbc.Driver";
		String dburl = "jdbc:mysql://127.0.0.1:3306/china?useUnicode=true&characterEncoding=UTF-8";
		String dbuser = "root";
		String password = "chenbin";
		List<String> list=new ArrayList<String>();
		String tmp=null;
		Connection conn;
		try {
			conn = DriverManager.getConnection(dburl, dbuser, password);
			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			Statement statement = conn.createStatement();
			String sql="select * from weibo.1347007924_tobesent";
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()){
				tmp=rs.getString("id")+" "+rs.getString("name")+" "+rs.getString("Text");
				list.add(tmp);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public static void main(String[] args) throws InterruptedException {
		Filter filter = new Filter();
		List<String> keywords = new ArrayList<String>();//需要关注的关键字
		keywords.add("test");
		keywords.add("风韵凸显");
		
		String token = "4aa705a76866ceef3ca3a858a9684592";
		String tokenSecret = "986423bdb97ab11e5b0a89a3a2c2e274";
		int flashtime=30;//刷新时间 单位：s
		List<String> weiboToBeSent=null;//等待發送的微博內容
//		List<String> weiboToBeSent=new ArrayList<String>();
		/*
		 * 初始化微博，把好友列表等存入weibo.1347007924_friendlist和weibo.laststatusid
		 */
//		WeiboInitial getfriends = new WeiboInitial(token, tokenSecret);
//		getfriends.storeFriendsToDb();
//		getfriends.storeLastStatusIdToDb();
//		getfriends.updateFriendsToDb();
//		getfriends.updateLastStatusIdToDb();
		/*
		 * 定时刷新微博，获取符合要求微博存入weibo.1347007924_tobesent
		 */
		int i=1;
		while (true) {
			try {
				filter.filterToDb(keywords);
//				weiboToBeSent=getToBeSent();
			} catch (WeiboException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("times:"+(i++));
			Thread.sleep(flashtime*1000);
		}
		
//		List<String> weiboToBeSent=getToBeSent();
	}
	
}
