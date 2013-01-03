package Flight;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class Crawler/* implements Runnable */{
	private String driver = "com.mysql.jdbc.Driver";
	private String dburl = "jdbc:mysql://127.0.0.1:3306/china?useUnicode=true&characterEncoding=UTF-8";
	private String dbuser = "root";
	private String password = "chenbin";
	private Connection conn= null;
	private Statement statement = null;
	private String $date=null;
	private String $begin=null;
	private String $end=null;

	public static void main(String[] args) throws XPatherException{
		String begin="上海";
		String end="北京";
		String date="20111129";
		for(int i=20111201;i<20111211;i++){
			date=i+"";
			String url="http://m.qunar.com/search.action?begin="+begin+"&end="+end+"&date="+date+"&time=0&v=2&f=index&bd_source=";	
			Crawler cl=new Crawler(date,begin,end);
		}
		
		
		

	}
	public Crawler(String date,String begin,String end) throws XPatherException{
		$date=date;
		$begin=begin;
		$end=end;
		begin=java.net.URLEncoder.encode(begin);
		end=java.net.URLEncoder.encode(end);
		String url="http://m.qunar.com/search.action?begin="+begin+"&end="+end+"&date="+date+"&time=0&v=2&f=index&bd_source=";
		try {
			conn = DriverManager.getConnection(dburl, dbuser, password);
			if (!conn.isClosed())
//				System.out.println("Succeeded connecting to the Database!");
			statement = conn.createStatement();
			String content=null;
			do{
				content = getHTML(url, "UTF-8");
				url=getSourceUrl(content,$date,$begin,$end);
			}while(url.length()>18);
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public String getHTML(String pageURL, String encoding) {
		StringBuilder pageHTML = new StringBuilder();
		try {
			URL url = new URL(pageURL);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestProperty("User-Agent", "MSIE 7.0");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), encoding));
			String line = null;
			while ((line = br.readLine()) != null) {
				pageHTML.append(line);
				pageHTML.append("\r\n");
			}
			connection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageHTML.toString();
	}

	public String getSourceUrl(String html,String date,String begin,String end) throws XPatherException {
			
		HtmlCleaner cleaner = new HtmlCleaner();
		TagNode rootNode = cleaner.clean(html);
		String nextPageLink="http://m.qunar.com";
		try {
			Object[] subNodes = rootNode.evaluateXPath("/body/div");
			TagNode inputNode = (TagNode) subNodes[2];
			Object[] tr=inputNode.evaluateXPath("/table//tr");
			Object[] p=inputNode.evaluateXPath("/p");
			TagNode nextPage=(TagNode)p[p.length-2];
			Object[] a=nextPage.evaluateXPath("/a");
			TagNode link=null;
			
			for(int k=0;k<a.length;k++){
				link=(TagNode)a[k];		
				if(link.getText().toString().equals("[下页]")){
					nextPageLink=nextPageLink+link.getAttributeByName("href");
				}
			}
			TagNode tn=null;
			String  regex1="[#]+";
			String regex2="[\\(][\\s\\S]*[\\)]";
			String execute=null;
			for(int i=0;i<tr.length;i++){
				tn=(TagNode)tr[i];
				String[] tmp=tn.getText().toString().replaceAll("\\s","#").split(regex1);
				tmp[2]=tmp[2].replaceAll("&yen;", "");
				tmp[2]=tmp[2].replaceAll(regex2, "");
				execute="insert into flight.CertainID values('"+date+"','"+tmp[0]+"','"+begin+"','"+end+"','"+tmp[1]+"',"+tmp[2]+",'"+tmp[3]+"')";
//				System.out.println(execute);
				try {
					try{
						statement.execute(execute);
					}catch (MySQLIntegrityConstraintViolationException e1){
						// UPDATE weibo.1347007924_friendlist SET
						// statusid=9,statusText='hello' WHERE userid=1087770692
						// and statusid>9;
						execute= "update flight.certainid set Start='"+begin+"',End='"+end+"',Airport='"+tmp[1]+"',Price="+tmp[2]+",Time='"+tmp[3]+"' where Date='"+date+"' and FlighNO='"+tmp[0]+"' and Price>"+tmp[2];
//						System.out.println(execute);
						statement.execute(execute);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		} catch (java.lang.ArrayIndexOutOfBoundsException e) {

		}
		nextPageLink=nextPageLink.replaceAll("amp;", "");
		return nextPageLink;
	}
}
