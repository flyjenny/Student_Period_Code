package FileSystem;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import WageSystem.*;
import infoManage.*;
import WorkShiftSystem.*;


public class FileSystem {
	Connection con;
	public static void main(String[]args){
		FileSystem file = new FileSystem();
		WorkShiftList temp = new WorkShiftList();
		file.setWorkshiftSchedule(14, 3, temp);
		file.setSalary(new ListOfWage());
		UserInfoList test_userinfo = new UserInfoList();
		//WorkShiftList test_workshift = file.getWorkshift(14, 3);
		WorkShiftList test_workshift = new WorkShiftList();
		ListOfWage wagelist = file.getSalary();
	}
	public FileSystem(){
		try {
			//-----------建立连接-------------
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/database?user=root");
			//----------------------------------
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Connecting error");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Class not found");
		}
	}
	
	public Connection getCon(){
		return con;
	}
	
	public UserInfoList getUserInfoList(){
		UserInfoList infoList = new UserInfoList(con);
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select id, name, password, gender, status, phone_num, address, poverty from userinfo");
			while (rs.next()){
				//-------------------studentNo--------name------------password---------gender-----------status-------phone number-------address-----------poverty
				infoList.addUser(rs.getString(1), rs.getString(2), rs.getString(3), rs.getBoolean(4), rs.getInt(5), rs.getString(6), rs.getString(7), rs.getBoolean(8));
			}
			return infoList;
		}catch (SQLException ex){
			System.out.println("UserInfo loading error");
		}
		return null;
	}

	public WorkShiftList getWorkshift(int SHIFTNUM, int WORKERNUM){
		WorkShiftList shiftList = new WorkShiftList(con);
		//==========================读排班表=============================
		try{
			Statement stmt = con.createStatement();
			Date date = new Date();
			String cmd = "select shiftnum, manager";
			for (int i = 0; i < WORKERNUM; i++){
				cmd += ", clerk"+i;
			}
			cmd += " from workshift_"+(date.getYear()+1900)+"_"+(1+date.getMonth())+"_"+date.getDate();
			ResultSet rs = stmt.executeQuery(cmd);
			int k = 0;
			while (rs.next()){
				shiftList.getWorkShift()[k][0] = rs.getString(1);
				for (int i = 1; i < WORKERNUM; i++){
					shiftList.getWorkShift()[k][i] = rs.getString(i+1);
					k++;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("WorkshiftList loading error");
			return null;
		}
		//======================================================
		//====================读空闲时间表=======================
		try {
			Statement stmt = con.createStatement();
			Date date = new Date();
			ResultSet rs = stmt.executeQuery("select id, name, freetime_usual, freetime_final from freetimelist_"+(date.getYear()+1900)+"_"+(1+date.getMonth())+"_"+date.getDate());
			while(rs.next()){
				//--------------常规时间-------------
				String free = rs.getString(2);
				if (free!=null){
					boolean[] flag = new boolean[free.length()];
					for (int i = 0; i < free.length(); i++){
						if (free.charAt(i) == '0')
							flag[i] = false;
						else flag[i] = true;
					}
					shiftList.setFreeTime_Usual(rs.getString(1), flag);
				}
				//-------------最终空闲时间----------------
				free = rs.getString(3);
				if (free!=null){
					boolean[] flag = new boolean[free.length()];
					for (int i = 0; i < free.length(); i++){
						if (free.charAt(i) == '0')
							flag[i] = false;
						else flag[i] = true;
					}
					shiftList.setFreeTime_Final(rs.getString(1), flag);
				}
			}
			return shiftList;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("FreetimeList loading error");
			return null;
		}

	}
	public void setWorkshiftSchedule(int SHIFTNUM, int WORKERNUM, WorkShiftList workList){
		Date date = new Date();
		if (new FileSystem().getWorkshift(SHIFTNUM, WORKERNUM) == null){
			//===================创建排班表=================
			try {
				Statement stmt = con.createStatement();
				String cmd = "CREATE TABLE workshift_"+(date.getYear()+1900)+"_"+(1+date.getMonth())+"_"+date.getDate()+
				"(shiftnum INTEGER AUTO_INCREMENT, "+"manager CHAR(10) NOT NULL";
				for (int i = 0; i < WORKERNUM; i++){
					cmd += ", clerk"+i+" CHAR(10)";
				}
				cmd += ",PRIMARY KEY (shiftnum))ENGINE = InnoDB;";
				stmt.execute(cmd);
				//----------------添加排班表信息--------------------
				for (int i = 0; i < SHIFTNUM; i++){
					ResultSet rs = stmt.executeQuery("select id from userinfo where name = '"+workList.getWorkShift()[i][0]+"'");
					rs.first();
					cmd = "INSERT workshift_"+(date.getYear()+1900)+"_"+(1+date.getMonth())+"_"+date.getDate()+
					"VALUES("+i+", '"+rs.getString(1)+"'";
					for (int j = 0; j < WORKERNUM-1; j++){
						rs = stmt.executeQuery("select id from userinfo where name = '"+workList.getWorkShift()[i][j+1]+"'");
						rs.first();
						cmd += ", '"+rs.getString(1)+"'";
					}
					cmd += ")";
					stmt.executeUpdate(cmd);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("WorkscheduleList creating fail");
			}
			//==================================================
			//=================创建空闲时间表====================
			try {
				Statement stmt = con.createStatement();
				String cmd = "CREATE TABLE database.freetimelist_"+(1900+date.getYear())+"_"+(1+date.getMonth())+"_"+date.getDate()+
				" (id CHAR(10),"+" name CHAR(15) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NOT NULL,"+
				"freetime_usual CHAR("+SHIFTNUM+"), "+"freetime_final CHAR("+SHIFTNUM+"), "+
				"status INTEGER UNSIGNED, PRIMARY KEY (id) )ENGINE = InnoDB;";
				stmt.execute(cmd);
				//------------添加空闲时间表信息----------------
				ResultSet rs = stmt.executeQuery("select id, name, status from userinfo");
				rs.last();
				int tot = rs.getRow();
				if (tot > 0){
					rs.first();
					do{
						cmd = "INSERT freetimelist_"+(1900+date.getYear())+"_"+(1+date.getMonth())+"_"+date.getDate()+
						"VALUES('";
						String id = rs.getString(1);
						cmd += id+"', '"+rs.getString(2)+"', '";
						boolean[] freetime = workList.getFreeTime_usual(id);
						char[] chartime = new char[freetime.length];
						for (int i = 0; i < freetime.length; i++){
							chartime[i] = freetime[i]?'1':'0';
						}
						cmd += chartime+"', '";
						freetime = workList.getFreeTime_final(id);
						for (int i = 0; i < freetime.length; i++){
							chartime[i] = freetime[i]?'1':'0';
						}
						cmd += chartime+"', "+rs.getString(3)+")";
						stmt.executeUpdate(cmd);
					}while(rs.next());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("FreetimeList creating fail");
			}
			//====================================================
		}
		else {
			try {
				//----------------更新排班表信息--------------------
				Statement stmt = con.createStatement();
				String cmd;
				for (int i = 0; i < SHIFTNUM; i++){
					ResultSet rs = stmt.executeQuery("select id from userinfo where name = '"+workList.getWorkShift()[i][0]+"'");
					rs.first();
					cmd = "UPDATE workshift_"+(date.getYear()+1900)+"_"+(1+date.getMonth())+"_"+date.getDate()+
					"SET manager = '"+rs.getString(1)+"'";
					for (int j = 0; j < WORKERNUM-1; j++){
						cmd += ", clerk"+j+" = '";
						rs = stmt.executeQuery("select id from userinfo where name = '"+workList.getWorkShift()[i][j+1]+"'");
						rs.first();
						cmd += rs.getString(1)+"'";
					}
					cmd += "WHERE shiftnum = "+i;
					stmt.executeUpdate(cmd);
				}
				//----------------更新空闲时间表-------------
				ResultSet rs = stmt.executeQuery("select id, name, status from userinfo");
				rs.last();
				int tot = rs.getRow();
				if (tot > 0){
					rs.first();
					do{
						cmd = "INSERT freetimelist_"+(1900+date.getYear())+"_"+(1+date.getMonth())+"_"+date.getDate()+
						"SET freetime_final = '";
						String id = rs.getString(1);
						boolean[] freetime = workList.getFreeTime_final(id);
						char[] chartime = new char[freetime.length];
						for (int i = 0; i < freetime.length; i++){
							chartime[i] = freetime[i]?'1':'0';
						}
						cmd += chartime+"' WHERE id = "+id;
						stmt.executeUpdate(cmd);
					}while(rs.next());
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("WorkscheduleList creating fail");
			}
		}
	}
	public ListOfWage getSalary(){
		ListOfWage wagelist = new ListOfWage();
		Date date = new Date();
		try{
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select id, name, status, wage_bonus, wage_fine, wage_total, wage_comment, labor_hour from wagelist_"+(1900+date.getYear())+"_"+(1+date.getMonth()));
			while (rs.next()){
				//----------------------id----------------name----------wage_basic-------wage_bonus------wage_fine----------wage_total----wage_comment------labor_hour--------
				wagelist.addToList(rs.getString(1), rs.getString(2), rs.getInt(3),  rs.getDouble(4), rs.getDouble(5), rs.getDouble(6), rs.getString(7), rs.getDouble(8));
			}
			return wagelist;
		}catch (SQLException ex){
			System.out.println("WageList loading error");
			return null;
		}
	}
	public void setSalary(ListOfWage List){
		Date date = new Date();
		if (new FileSystem().getSalary() == null){
			//===================创建工资表=================
			try {
				Statement stmt = con.createStatement();
				String cmd = "CREATE TABLE database.wagelist_"+(1900+date.getYear())+"_"+(1+date.getMonth())+
				" ( id CHAR(10) NOT NULL,"+
				"name CHAR(15) CHARACTER SET gb2312 COLLATE gb2312_chinese_ci NOT NULL,"+
				"status INTEGER DEFAULT 0, wage_bonus DOUBLE DEFAULT 0, "+
				"wage_fine DOUBLE DEFAULT 0, wage_total DOUBLE NOT NULL DEFAULT 0, "+
				"wage_comment CHAR(40) DEFAULT NULL, labor_hour DOUBLE DEFAULT 0, "+
				"PRIMARY KEY (id) )ENGINE = InnoDB;";
				stmt.execute(cmd);
				Iterator itr = List.getWageList().entrySet().iterator();
				while (itr.hasNext()){
					Map.Entry entry = (Map.Entry)itr.next();
					cmd = "INSERT wagelist_"+(1900+date.getYear())+"_"+(1+date.getMonth())+
					" values('"+entry.getKey()+"', '"+List.getName((String)entry.getKey())+
					"', "+List.getStatus((String)entry.getKey())+
					", "+List.getWage_bonus((String)entry.getKey())+
					", "+List.getWage_fine((String)entry.getKey())+
					", "+List.getWage_total((String)entry.getKey())+
					", '"+List.getWage_comment((String)entry.getKey())+
					"', "+List.getLabor_hour((String)entry.getKey())+")";
					stmt.executeUpdate(cmd);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("WageList creating fail");
			}
			//==================================================
		}
		else{
			try {
				Iterator itr = List.getWageList().entrySet().iterator();
				Statement stmt = con.createStatement();
				String cmd;
				while (itr.hasNext()){
					Map.Entry entry = (Map.Entry)itr.next();
					cmd = "UPDATE wagelist_"+(1900+date.getYear())+"_"+(1+date.getMonth())+
					" SET wage_bonus = "+List.getWage_bonus((String)entry.getKey())+
					", wage_fine = "+List.getWage_fine((String)entry.getKey())+
					", wage_total = "+List.getWage_total((String)entry.getKey())+
					", wage_comment = "+List.getWage_comment((String)entry.getKey())+
					", labor_hour = "+List.getLabor_hour((String)entry.getKey())+
					" WHERE id = "+entry.getKey();
					stmt.executeUpdate(cmd);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				System.out.println("WageList updating fail");
			}
		}
	}
}