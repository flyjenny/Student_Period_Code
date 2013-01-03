package Flight;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

public class FlighFilter {
	private String driver = "com.mysql.jdbc.Driver";
	private String dburl = "jdbc:mysql://127.0.0.1:3306/china?useUnicode=true&characterEncoding=UTF-8";
	private String dbuser = "root";
	private String password = "chenbin";
	private Connection conn = null;
	private Statement statement = null;
	private String $beginDate = null;
	private String $endDate = null;
	private String $beginPlace = null;
	private String $endPlace = null;
	private String $highestPrice = null;
	
	List<String> $result=new ArrayList<String>();

	public FlighFilter(String beginDate, String endDate, String beginPlace,
			String endPlace, String highestPrice) {
		$beginDate = beginDate;
		$endDate = endDate;
		$beginPlace = beginPlace;
		$endPlace = endPlace;
		$highestPrice = highestPrice;
		
	}
	public List<String> Msg(){
		
		String tmp=null;
		try {
			conn = DriverManager.getConnection(dburl, dbuser, password);
			if (!conn.isClosed())
				System.out.println("Succeeded connecting to the Database!");
			Statement statement = conn.createStatement();
			Statement statement1= conn.createStatement();
			String sql="select * from flight.certainid where Date >='"+$beginDate+"' and Date <='"+$endDate+"' and Start='"+$beginPlace+"' and End='"+$endPlace+"' and Price <="+$highestPrice;
			String execute=null;
			String date=null;
			String flighNO=null;
			String start=null;
			String end=null;
			String airport=null;
			String price=null;
			String time=null;
			
//			System.out.println(sql);
			ResultSet rs = statement.executeQuery(sql);
			while(rs.next()){
//				System.out.println(rs.getString("Date")+" "+rs.getString("FlighNO")+" "+rs.getString("Start")+" "+rs.getString("End")+" "+rs.getString("Airport")+" "+rs.getString("Price")+" "+rs.getString("Time"));
				date=rs.getString("Date");
				flighNO=rs.getString("FlighNO");
				start=rs.getString("Start");
				end=rs.getString("End");
				airport=rs.getString("Airport");
				price=rs.getString("Price");
				time=rs.getString("Time");
				tmp=date+" "+flighNO+" "+start+" "+end+" "+airport+" "+price+" "+time;
				execute="insert into flight.certainid_tobesent values('"+date+"','"+flighNO+"','"+start+"','"+end+"','"+airport+"',"+price+",'"+time+"')";
				try{
					statement1.execute(execute);
				}catch (MySQLIntegrityConstraintViolationException e1){

					execute= "update flight.certainid_tobesent set Start='"+start+"',End='"+end+"',Airport='"+airport+"',Price="+price+",Time='"+time+"' where Date='"+date+"' and FlighNO='"+flighNO+"' and Price>"+price;
//					System.out.println(execute);
					statement1.execute(execute);
				}
				$result.add(tmp);
				}
			conn.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return $result;
	}
	
	public static void main(String[] args){
		FlighFilter ff=new FlighFilter("20111201","20111211","上海","北京","700");
		List<String> output=ff.Msg();
		for(String list:output){
			System.out.println(list);
		}
	}
	
}
