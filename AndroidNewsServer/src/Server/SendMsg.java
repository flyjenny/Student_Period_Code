package Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.htmlcleaner.XPatherException;

import weibo4j.WeiboException;
import Weibo.*;
import Flight.*;

public class SendMsg {

	private static final int SERVERPORT = 54321;

	private static List<Socket> mClientList = new ArrayList<Socket>();

	private ExecutorService mExecutorService;

	private ServerSocket mServerSocket;

	private static List<String> $keywords = null;

	private static String $firstday = null;
	private static String $endday = null;
	private static String $begin = null;
	private static String $end = null;
	private static String $highestprice = null;

	public static void main(String args[]) {
		new SendMsg();
	}

	public SendMsg() {
		try {
			mServerSocket = new ServerSocket(SERVERPORT);
			mExecutorService = Executors.newCachedThreadPool();
			System.out.println("starting...");

			Socket client = null;
			while (true) {
				client = mServerSocket.accept();
				mClientList.add(client);
				mExecutorService.execute(new ThreadServer(client));
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static class FlashWeibo implements Runnable {
		// private List<String> keywords=null;
		private Filter filter = new Filter();

		public FlashWeibo(List<String> list) {
			String token = "4aa705a76866ceef3ca3a858a9684592";
			String tokenSecret = "986423bdb97ab11e5b0a89a3a2c2e274";
			// WeiboInitial getfriends = new WeiboInitial(token, tokenSecret);
			$keywords = list;
		}

		public void run() {
			try {
				if ($keywords != null) {
					filter.filterToDb($keywords);
				}
				// weiboToBeSent=getToBeSent();
			} catch (WeiboException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static class FlashFligh implements Runnable {
		public FlashFligh(String firstday, String endday, String begin,
				String end, String highestprice) {
			$firstday = firstday;
			$endday = endday;
			$begin = begin;
			$end = end;
			$highestprice = highestprice;
		}

		public void run() {
			Date dateTemp = null;
			Date dateTemp2 = null;
			try {
				dateTemp = new SimpleDateFormat("yyyy-MM-dd").parse($firstday);
				dateTemp2 = new SimpleDateFormat("yyyy-MM-dd").parse($endday);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			Calendar calendarTemp = Calendar.getInstance();
			calendarTemp.setTime(dateTemp);
			String handleDate = null;

			while (calendarTemp.getTime().getTime() != dateTemp2.getTime()) {
				handleDate = new SimpleDateFormat("yyyy-MM-dd")
						.format(calendarTemp.getTime());
				handleDate = handleDate.replaceAll("-", "");
				System.out.println(handleDate);
				/*
				 * 从网上把抓取数据存入数据库flight.certainid
				 */
				try {
					Crawler cl = new Crawler(handleDate, $begin, $end);
				} catch (XPatherException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				calendarTemp.add(Calendar.DAY_OF_YEAR, 1);

			}
		}
	}

	public static class ThreadServer implements Runnable {
		private Socket mSocket;
		private BufferedReader mBufferedReader;
		private PrintWriter mPrintWriter;
		private String mStrMSG;

		public ThreadServer(Socket socket) throws IOException {
			this.mSocket = socket;
			mBufferedReader = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			// mStrMSG = "user:" + this.mSocket.getInetAddress() +
			// " come total:" + mClientList.size();
			mStrMSG = "login successful!";
			sendMessage();
		}

		public void run() {
			try {
				while ((mStrMSG = mBufferedReader.readLine()) != null) {

					/*
					 * if(mStrMSG.trim().equals("exit")){
					 * mClientList.remove(mSocket); mBufferedReader.close();
					 * mPrintWriter.close(); mStrMSG = "user:" +
					 * this.mSocket.getInetAddress() + " exit. total:" +
					 * mClientList.size(); mSocket.close(); sendMessage();
					 * break; }else{ mStrMSG = mSocket.getInetAddress() + ":" +
					 * mStrMSG; sendMessage(); }
					 */
					System.out.println(mStrMSG);
					String[] cut = mStrMSG.split(" ");
					if (cut[0].equals("getweibo")) {
						mStrMSG = "";
						List<String> weiboToBeSent = getToBeSent();
						for (String tmp : weiboToBeSent) {
							mStrMSG = mStrMSG + tmp + "&";
						}
					} else if (cut[0].equals("keyword")) {
						$keywords.clear();
						for (int i = 1; i < cut.length; i++)
							$keywords.add(cut[i]);

					} else if (cut[0].equals("getfligh")) {
						mStrMSG = "";
						List<String> flighToBeSent = getFlighToBeSent();
						for (String tmp : flighToBeSent) {
							mStrMSG = mStrMSG + tmp + "&";
						}
					} else if (cut[0].equals("condition")) {
						$firstday = cut[1];
						$endday = cut[2];
						$begin = cut[3];
						$end = cut[4];
						$highestprice = cut[5];
					}
					mStrMSG = "";
					List<String> weiboToBeSent = getToBeSent();
					for (String tmp : weiboToBeSent) {
						mStrMSG = mStrMSG + tmp + "&";
					}
					// System.out.println(mStrMSG);
					sendMessage();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		private void sendMessage() throws IOException {
			System.out.println(mStrMSG);
			for (Socket client : mClientList) {
				mPrintWriter = new PrintWriter(client.getOutputStream(), true);
				mPrintWriter.println(mStrMSG);
			}
		}

		public List<String> getToBeSent() {
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

		public void clearToBeSent() {
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
			FlighFilter ff = new FlighFilter($firstday.replaceAll("-", ""),
					$endday.replaceAll("-", ""), $begin, $end, $highestprice);
			List<String> output = ff.Msg();
			return output;
		}
	}
}
