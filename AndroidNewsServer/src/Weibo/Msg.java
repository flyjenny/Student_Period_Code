package Weibo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


public class Msg {
	public  List<String> getToBeSent(){
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
	public static void main(String[] args){
		String driver = "com.mysql.jdbc.Driver";
		String dburl = "jdbc:mysql://127.0.0.1:3306/china?useUnicode=true&characterEncoding=UTF-8";
		String dbuser = "root";
		String password = "chenbin";
		Connection conn;
		try {
			conn = DriverManager.getConnection(dburl, dbuser, password);
			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			Statement statement = conn.createStatement();
			String sql="select * from weibo.1347007924_tobesent";
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()){
				System.out.println(rs.getString("id")+" "+rs.getString("name")+" "+rs.getString("Text"));
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
}
