package WorkShiftSystem;
import infoManage.*;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import jxl.*;
import jxl.write.*;

import FileSystem.*;
import FileSystem.FileSystem;


public class WorkShiftList implements Serializable
{
	private FreeTime freeTimeList;
	private String[][] List;
	private int[] Labor;
	private int[] LaborID;
	private String[] LaborName;
	static int TimeOfWork[] = new int[]{3,4,3,4,3,4,3,4,3,4,3,4,3,4};
	public static int SHIFTTOTAL = 14;
	public static int WORKERTOTAL = 3;
	public WorkShiftList(){
		FileSystem file = new FileSystem();
		WorkShiftList temp = file.getWorkshift(SHIFTTOTAL, WORKERTOTAL);
		if (temp==null){
			List = new String[WORKERTOTAL][SHIFTTOTAL];
			freeTimeList = new FreeTime(file.getCon());
			Labor = new int[new UserInfoList().getList().size()];
			LaborID = new int[Labor.length];
			LaborName = new String[Labor.length];
		}
		else {
			 List = temp.getWorkShift();
			 freeTimeList = temp.getFreeTimeList();
			 Labor = temp.getLabor();
			 LaborID = temp.getLaborID();
			 LaborName = temp.getLaborName();
		}
	}
	public WorkShiftList(Connection con){
		List = new String[WORKERTOTAL][SHIFTTOTAL];
		freeTimeList = new FreeTime(con);
		Labor = new int[new UserInfoList().getList().size()];
		LaborID = new int[Labor.length];
		LaborName = new String[Labor.length];
	}
	
	public boolean[] getFreeTime_usual(String id){
		return freeTimeList.getFreeTime_Usual(id);
	}
	
	public boolean[] getFreeTime_final(String id){
		return freeTimeList.getFreeTime_Final_bool(id);
	}
	public void setFreeTime_Usual(String id, boolean[]timelist){
		freeTimeList.setFreeTime(id, timelist);
	}
	public boolean setFreeTime_Final(String id, boolean free[]){
		return freeTimeList.setFreeTime_Final(id, free);
	}
	public String[][] getWorkShift(){
		return List;
	}
	public FreeTime getFreeTimeList(){
		return freeTimeList;
	}
	public int[] getLabor(){
		return Labor;
	}
	public int[] getLaborID(){
		return LaborID;
	}
	public String[] getLaborName(){
		return LaborName;
	}
	public void setWorkShift(){
		new FileSystem().setWorkshiftSchedule(SHIFTTOTAL, WORKERTOTAL, this);
	}
	public boolean isWorkShiftSet(){
		return List == null ? false : true;
	}
	public boolean deleFreeTime(String id, int time){
		return freeTimeList.deleteFreeTime(id, time);
	}
	public String[][] createWorkShiftSchedule(){
		if (!freeTimeList.isFreetimeset()){
			return null;
		}
		Sorting(1);
		List[0] = Scheduling(SHIFTTOTAL, 1);
		for (int i = 1; i < WORKERTOTAL; i++){
			List[i] = Scheduling(SHIFTTOTAL, 0);
		}
		return List;
	}
	public void creatLabor(){
		int k = 0;
		UserInfoList userInfo = new UserInfoList();
		Iterator itr = userInfo.getList().entrySet().iterator();
		while (itr.hasNext()){
			Map.Entry entry = (Map.Entry)itr.next();
			LaborID[k] = (Integer)entry.getKey();
			LaborName[k] = new String(userInfo.getUserName((String)entry.getKey()));
			k++;
		}
		for (int i = 0; i < 14; i++){
			for (int j = 0; j < 3; j++){
				if (List[j][i]==null)
					continue;
				int pos = -1;
				for (int l = 0; l < k; l++){
					if (LaborName[l].equals(List[j][i])){
						pos = l;
						break;
					}
				}
				if (pos!=-1){
					Labor[pos] += TimeOfWork[i];
				}
			}
		}
	}
	private void Sorting(int n){//n=1为经理，其余为员工
		employee[]temp;
		if(n==1){temp=freeTimeList.getAdmin();}
		else{temp=freeTimeList.getEmp();}
	 for(int i=0;i<temp.length-1;i++){
		 for (int j = 0; j < temp.length-i-1; j++){
			if(temp[j].freetime.length>temp[j+1].freetime.length){
				employee te=temp[j];temp[j]=temp[j+1];temp[j+1]=te;
				}	 }	 
	 }
	 }
	private String[] Scheduling(int n,int q){//n为共需排n个班次，q=1为经理，其余为员工
		employee[]temp;
		if(q==1){temp=freeTimeList.getAdmin();}
		else{temp=freeTimeList.getEmp();}
			String[]duty=new String[n];
			int flage=0;int m=0;
			while(m<temp.length){
				for(int p=0;p<temp[m].freetime.length;p++){ 
					if( duty[temp[m].freetime[p]]==null){
						duty[temp[m].freetime[p]]=temp[m].name;
						temp[m].deleteT(p);
						m++;//更改优先级
						flage=1;//for循环执行标志
						break;}
					}
				if(flage==0){m++;}else{flage=0;}
		}
		for(int t=0;t<duty.length;t++){System.out.println(duty[t]);}
		return duty;
}
public static void main(String args[])
{ 
	WorkShiftList worklist = new WorkShiftList();
/*	worklist.setFreeTime(0, new boolean[]{false, false, true, false, true, false, false, false, false});
	worklist.setFreeTime(1, new boolean[]{true, false, true, false, false, false, false, false, false});
	worklist.setFreeTime(2, new boolean[]{false, true, false, false, true, true, false, false, false});
	worklist.setFreeTime(3, new boolean[]{false, false, false, true, false, true, true, true, false});
	worklist.setFreeTime(4, new boolean[]{false, true, false, false, false, false, true, false, false});
	worklist.setFreeTime(5, new boolean[]{true, false, false, true, false, false, false, false, true});
	worklist.setFreeTime(6, new boolean[]{false, false, true, false, false, false, false, true, true});
	worklist.setFreeTime(7, new boolean[]{false, true, false, false, false, false, true, false, false});
	worklist.setFreeTime(8, new boolean[]{false, false, false, false, false, true, false, false, false});*/
	//worklist.createWorkShiftSchedule();
	for (int i = 0; i < 9; i++){
		worklist.Labor[i] = 0;
	}
	worklist.creatLabor();
	FileSystem file = new FileSystem();
//	file.setWorkshiftSchedule(worklist);
	WorkShiftList test = file.getWorkshift(SHIFTTOTAL,WORKERTOTAL);
}
}

class FreeTime implements Serializable{
	private employee[]emp;//员工
	private employee[]admin;//经理
	private boolean empty;
	public FreeTime(Connection con){
		try {
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("select id, name from userinfo where status = 0");
			rs.last();
			int tot = rs.getRow();
			emp = new employee[tot];
			rs.first();
			if(tot > 0){
				int k = 0;
				do{
					emp[k] = new employee(rs.getString(2), rs.getString(1));
					k++;
				}while(rs.next());
			}
			rs = stmt.executeQuery("select id, name from userinfo where status = 1");
			rs.last();
			tot = rs.getRow();
			admin = new employee[tot];
			rs.first();
			if(tot > 0){
				int k = 0;
				do{
					admin[k] = new employee(rs.getString(2), rs.getString(1));
					k++;
				}while(rs.next());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("FreeTimeList initializing fail");
		}
		
/*		UserInfoList userInfo = new UserInfoList();
		int adminNum = 0;
		int empNum = 0;
		Iterator itr = userInfo.getList().entrySet().iterator();
		while (itr.hasNext()){
			Map.Entry entry = (Map.Entry)itr.next();
			if (userInfo.getUserType((String)entry.getKey()) == 1){
				adminNum++;
			}
			else if (userInfo.getUserType((String)entry.getKey()) == 0){
				empNum++;
			}
		}
		emp = new employee[empNum];	admin = new employee[adminNum];
		itr = userInfo.getList().entrySet().iterator();
		int i = 0; int j = 0;
		while (itr.hasNext()){
			Map.Entry entry = (Map.Entry)itr.next();
			if (userInfo.getUserType((String)entry.getKey()) == 1){
				admin[i] = new employee(userInfo.getUserName((String)entry.getKey()), (String)entry.getKey());
				i++;
			}
			else if (userInfo.getUserType((String)entry.getKey()) == 0){
				emp[j] = new employee(userInfo.getUserName((String)entry.getKey()), (String)entry.getKey());
				j++;
			}
		}*/
		empty = true;
	}
	public boolean setFreeTime(String ID, boolean free[]){
		for (int i = 0; i < emp.length; i++){
			if (ID.equals(emp[i].getID())){
				emp[i].setfreetime(free);
				empty = false;
				return true;
			}
		}
		for (int i = 0; i < admin.length; i++){
			if (ID.equals(admin[i].getID())){
				admin[i].setfreetime(free);
				empty = false;
				return true;
			}
		}
		return false;
	}
	public boolean[] getFreeTime_Usual(String ID){
		for (int i = 0; i < emp.length; i++){
			if (ID.equals(emp[i].getID())){
				return emp[i].getFreeTime(ID);
			}
		}
		for (int i = 0; i < admin.length; i++){
			if (ID.equals(admin[i].getID())){
				return admin[i].getFreeTime(ID);
			}
		}
		return null;
	}
	public int[] getFreeTime_Final(String ID){
		for (int i = 0; i < emp.length; i++){
			if (ID.equals(emp[i].getID())){
				return emp[i].getFreeTime_Final(ID);
			}
		}
		for (int i = 0; i < admin.length; i++){
			if (ID.equals(admin[i].getID())){
				return admin[i].getFreeTime_Final(ID);
			}
		}
		return null;
	}
	public boolean[] getFreeTime_Final_bool(String ID){
		for (int i = 0; i < emp.length; i++){
			if (ID.equals(emp[i].getID())){
				return emp[i].getFreeTime_Final_bool(ID);
			}
		}
		for (int i = 0; i < admin.length; i++){
			if (ID.equals(admin[i].getID())){
				return admin[i].getFreeTime_Final_bool(ID);
			}
		}
		return null;
	}
	public boolean setFreeTime_Final(String ID, boolean free[]){
		for (int i = 0; i < emp.length; i++){
			if (ID.equals(emp[i].getID())){
				emp[i].setfreetime_final(free);
				empty = false;
				return true;
			}
		}
		for (int i = 0; i < admin.length; i++){
			if (ID.equals(emp[i].getID())){
				admin[i].setfreetime_final(free);
				empty = false;
				return true;
			}
		}
		return false;
	}
	public boolean isFreetimeset(){
		return (!empty);
	}
	public employee[] getAdmin(){
		return admin;
	}
	public employee[] getEmp(){
		return emp;
	}
	public boolean deleteFreeTime(String id, int time){
		for (int i = 0; i < emp.length; i++){
			if (emp[i].getID().equals(id)){
				emp[i].deleteS(time);
				return true;
			}
		}
		for (int i = 0; i < admin.length; i++){
			if (admin[i].getID().equals(id+"")){
				admin[i].deleteS(time);
				return true;
			}
		}
		return false;
	}
}

class employee implements Serializable{//每个雇员包括name 、ID 、空闲时间、职位Type四个属性。
	int[]basicfreetime;
	String name;String ID;int[]freetime;
	
	public String getName(){
		return name;
	}
	
	public String getID(){
		return new String(ID);
	}
	
	public employee(String s,String i,int[]f){
		name=s;ID=new String(i);freetime=f;
	}
	public employee(String s, String i){
		name = s; ID = new String(i);
	}
	//删除第x个班次
	void deleteS(int x){
		int t;
		for(t=0;t<freetime.length;t++){
			if(x==freetime[t]){break;}
		}
		if(t<freetime.length){deleteT(t);}
		else{System.out.println("无此班次");}
	}
	
	//删除该员工第p个空闲时间
	void deleteT(int p){
		int[]f=new int[freetime.length-1];
		for(int t=0;t<p;t++){f[t]=freetime[t];}
		for(int t=p+1;t<freetime.length;t++){f[t-1]=freetime[t];}
		freetime=f;
		
	}
	//根据每个人输入信息的空闲时间信息生成空闲时间表freetime[]；
	public void setfreetime(boolean[] select){
		int k=0;int m=0;
		for(int i=0;i<select.length;i++){if(select[i]){m++;}}//共有m个空闲时间
		basicfreetime=new int[m];
		for(int i=0;i<select.length;i++){
			if(select[i]){basicfreetime[k++]=i;}
		}
		}
	public void setfreetime_final(boolean[] select){
		int k=0;int m=0;
		for(int i=0;i<select.length;i++){if(select[i]){m++;}}//共有m个空闲时间
		freetime=new int[m];
		for(int i=0;i<select.length;i++){
			if(select[i]){freetime[k++]=i;}
		}
	}
	public boolean[] getFreeTime(String ID){
		boolean[] temp = new boolean[WorkShiftList.SHIFTTOTAL];
		for (int i = 0; i < basicfreetime.length; i++){
			temp[i] = true;
		}
		return temp;
	}
	public int[] getFreeTime_Final(String ID){
		return freetime;
	}
	public boolean[] getFreeTime_Final_bool(String ID){
		boolean[] temp = new boolean[WorkShiftList.SHIFTTOTAL];
		for (int i = 0; i < freetime.length; i++){
			temp[i] = true;
		}
		return temp;
	}
}