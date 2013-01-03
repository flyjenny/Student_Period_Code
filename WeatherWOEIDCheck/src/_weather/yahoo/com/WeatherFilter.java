package _weather.yahoo.com;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.sql.*;

import org.dom4j.*;

public class WeatherFilter {
	public static void readFileByLines(String fileName) throws IOException, ClassNotFoundException, SQLException {
		File file = new File(fileName);
		BufferedReader reader = null;
		String preurl = "http://weather.yahooapis.com/forecastrss?w=";
		String url = null;
		String splitString[]=null;
		String totalString=null;
		String driver = "com.mysql.jdbc.Driver";
		String dburl = "jdbc:mysql://127.0.0.1:3306/china?useUnicode=true&characterEncoding=UTF-8";
		String user = "root";
		String password = "chenbin";
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(dburl, user, password);

		if (!conn.isClosed())
			System.out.println("Succeeded connecting to the Database!");
		Statement statement = conn.createStatement();
		
		
//		FileWriter file1 = new FileWriter("R:/China_places_output.tsv", true);
		try {
			reader = new BufferedReader(new FileReader(file));
			String tempString = null;
			String cutString = null;
			int line = 1;
			// 一次读入一行，直到读入null为文件结束
			while ((tempString = reader.readLine()) != null) {
/*				// 显示行号
				cutString = tempString.substring(0, 8).trim();
				url = preurl + cutString;
				if (checkExist(getXML(url))) {
					System.out.println(url);
					file1.write(tempString + "\r\n");
				}
*/				splitString=tempString.split("\t");
				totalString="insert into placeid values("+splitString[0]+","+splitString[1]+","+splitString[2]+",\""+splitString[4]+"\")";
				System.out.println(totalString);
				statement.execute(totalString);
				line++;
			}
			conn.close();
			reader.close();
//			file1.flush();
//			file1.close();
		} catch (IOException e) {
//			file1.flush();
//			file1.close();
			e.printStackTrace();
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e1) {
				}
			}
		}
	}

	public static String getXML(String link) throws IOException {
		URL url = new URL(link);
		Reader reader = new InputStreamReader(new BufferedInputStream(
				url.openStream()));
		int c;
		StringBuffer tmp = new StringBuffer();
		while ((c = reader.read()) != -1) {
			tmp.append((char) c);
		}
		reader.close();
		return tmp.toString();
	}

	/**
	 * input the XML String
	 * 
	 * @param xml
	 * @return
	 */
	public static boolean checkExist(String xml) {
		Document doc;
		String display = null;
		try {
			doc = DocumentHelper.parseText(xml);
			Element root = (Element) doc
					.selectSingleNode("//rss/channel/title");
			display = root.getStringValue();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// System.out.println(display);
		if (display.equals("Yahoo! Weather - Error"))
			return false;
		else
			return true;
	}

	/**
	 * @param args
	 * @throws IOException
	 * @throws DocumentException
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static void main(String[] args) throws IOException,
			DocumentException, ClassNotFoundException, SQLException {
		
		readFileByLines("r:/China_places_valid.tsv");
		System.out.println("FNISH!");

/*		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://127.0.0.1:3306/china";
		String user = "root";
		String password = "chenbin";
		Class.forName(driver);
		Connection conn = DriverManager.getConnection(url, user, password);

		if (!conn.isClosed())
			System.out.println("Succeeded connecting to the Database!");
		Statement statement = conn.createStatement();
		String sql = "insert into placeid values(26198383,\"CN\",\"荆州市\",\"County\")";
		System.out.println(sql);
		statement.execute(sql);
		conn.close();
*/
	}
}
