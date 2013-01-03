package Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import Flight.FlighFilter;

public class Server implements Runnable {
	public static void main(String args[]) {
		Thread desktopServerThread = new Thread(new Server());
		desktopServerThread.start();
	}

	public void run() {
		try {
			ServerSocket serverSocket = new ServerSocket(54321);
			while (true) {
				Socket client = serverSocket.accept();
				System.out.println("accept");
				try {
					BufferedReader in = new BufferedReader(
							new InputStreamReader(client.getInputStream()));
					String mStrMSG = in.readLine();
					System.out.println("receive: " + mStrMSG);

					String[] cut = mStrMSG.split("&");
					mStrMSG = "";

					if (cut[0].equals("getweibo")) {
						List<String> weiboToBeSent = getWeiboToBeSent();
						for (String tmp : weiboToBeSent) {
							mStrMSG = mStrMSG + tmp + "&";
						}
						clearWeiboToBeSent();
					} else if (cut[0].equals("addweibocondition")) {
						if (Global.$friend_keywords == null) {
							Global.$friend_keywords = new HashMap<String, List<String>>();
							Global.$keywords = new ArrayList<String>();
						}
						// Global.$friend_keywords.clear();
						Global.$keywords.clear();
						// List<String> keywords=new ArrayList<String>();
						for (int i = 1; i < cut.length; i++) {
							String[] detailCut = cut[i].split("%");
							List<String> keywords = new ArrayList<String>();
							keywords.clear();
							for (int j = 1; j < detailCut.length; j++) {
								keywords.add(detailCut[j]);
							}
							Global.$friend_keywords.put(detailCut[0], keywords);
						}

						String name = null;
						List<String> list = null;
						Set<String> keys = Global.$friend_keywords.keySet();
						for (Iterator it = keys.iterator(); it.hasNext();) {
							name = (String) it.next();
							list = Global.$friend_keywords.get(name);
							for (String key : list) {
								System.out.println(name + " " + key);
							}
						}

					} else if (cut[0].equals("deleteweibocondition")) {
						if (Global.$friend_keywords != null) {
							for (int i = 1; i < cut.length; i++) {
								Global.$friend_keywords.remove(cut[i]);
							}
						}
						String name = null;
						List<String> list = null;
						Set<String> keys = Global.$friend_keywords.keySet();
						for (Iterator it = keys.iterator(); it.hasNext();) {
							name = (String) it.next();
							list = Global.$friend_keywords.get(name);
							for (String key : list) {
								System.out.println(name + " " + key);
							}
						}

					} else if (cut[0].equals("modifyweibocondition")) {
						if (Global.$friend_keywords != null) {
							// Global.$keywords.clear();
							for (int i = 1; i < cut.length; i++) {
								String[] detailCut = cut[i].split("%");
								List<String> keywords = new ArrayList<String>();
								keywords.clear();

								for (int j = 1; j < detailCut.length; j++) {
									keywords.add(detailCut[j]);
								}
								if (Global.$friend_keywords
										.containsKey(detailCut[0])) {
									Global.$friend_keywords
											.remove(detailCut[0]);
									Global.$friend_keywords.put(detailCut[0],
											keywords);
								}
							}
						}
						String name = null;
						List<String> list = null;
						Set<String> keys = Global.$friend_keywords.keySet();
						for (Iterator it = keys.iterator(); it.hasNext();) {
							name = (String) it.next();
							list = Global.$friend_keywords.get(name);
							for (String key : list) {
								System.out.println(name + " " + key);
							}
						}
					} else if (cut[0].equals("getfligh")) {
						List<String> flighToBeSent = getFlighToBeSent();
						for (String tmp : flighToBeSent) {
							mStrMSG = mStrMSG + tmp + "&";
						}
						// clearFlighToBeSent();
					} else if (cut[0].equals("condition")) {
						Global.$firstday = cut[1];
						Global.$endday = cut[2];
						Global.$begin = cut[3];
						Global.$end = cut[4];
						Global.$highestprice = cut[5];
					} else if (cut[0].equals("getfriend")) {
						List<String> list = getFriendList();
						for (String tmp : list) {
							mStrMSG = mStrMSG + tmp + "&";
						}
					}

					PrintWriter out = new PrintWriter(new BufferedWriter(
							new OutputStreamWriter(client.getOutputStream())),
							true);
					out.println(mStrMSG);
					System.out.println("reply:" + mStrMSG);

					in.close();
					out.close();
				} catch (Exception e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
				} finally {
					client.close();
					System.out.println("close");
				}
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public List<String> getWeiboToBeSent() {
		String driver = "com.mysql.jdbc.Driver";
		String dburl = "jdbc:mysql://127.0.0.1:3306/china?useUnicode=true&characterEncoding=UTF-8";
		String dbuser = "root";
		String password = "chenbin";
		List<String> list = new ArrayList<String>();
		String tmp = null;
		Connection conn;
		try {
			conn = DriverManager.getConnection(dburl, dbuser, password);

			Statement statement = conn.createStatement();
			String sql = "select * from weibo.1347007924_tobesent";
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				tmp = rs.getString("id") + "%" + rs.getString("name") + "%"
						+ rs.getString("Text");
				list.add(tmp);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void clearWeiboToBeSent() {
		String driver = "com.mysql.jdbc.Driver";
		String dburl = "jdbc:mysql://127.0.0.1:3306/china?useUnicode=true&characterEncoding=UTF-8";
		String dbuser = "root";
		String password = "chenbin";
		Connection conn;
		try {
			conn = DriverManager.getConnection(dburl, dbuser, password);
			Statement statement = conn.createStatement();
			String execute = "TRUNCATE weibo.1347007924_tobesent";
			statement.execute(execute);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<String> getFlighToBeSent() {
		FlighFilter ff = new FlighFilter(Global.$firstday.replaceAll("-", ""),
				Global.$endday.replaceAll("-", ""), Global.$begin, Global.$end,
				Global.$highestprice);
		List<String> output = ff.Msg();
		return output;
	}

	public void clearFlighToBeSent() {
		String driver = "com.mysql.jdbc.Driver";
		String dburl = "jdbc:mysql://127.0.0.1:3306/china?useUnicode=true&characterEncoding=UTF-8";
		String dbuser = "root";
		String password = "chenbin";
		Connection conn;
		try {
			conn = DriverManager.getConnection(dburl, dbuser, password);
			Statement statement = conn.createStatement();
			String execute = "TRUNCATE flight.certainid_tobesent";
			statement.execute(execute);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public List<String> getFriendList() {

		String dburl = "jdbc:mysql://127.0.0.1:3306/china?useUnicode=true&characterEncoding=UTF-8";
		String dbuser = "root";
		String password = "chenbin";

		List<String> list = new ArrayList<String>();
		String tmp = null;
		Connection conn;
		try {
			conn = DriverManager.getConnection(dburl, dbuser, password);

			Statement statement = conn.createStatement();
			String sql = "select * from weibo.1347007924_friendlist";
			ResultSet rs = statement.executeQuery(sql);
			while (rs.next()) {
				tmp = rs.getString("userid") + "%" + rs.getString("name");
				list.add(tmp);
			}
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;

	}

}
