package WorkShiftSystem;
import infoManage.*;

import java.io.*;
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
	static int WORKTOTAL = 14;
	static int SHIFTTOTAL = 3;
	public WorkShiftList(){
		FileSystem file = new FileSystem();
		if (file.getWorkshift()==null){
			List = new String[SHIFTTOTAL][WORKTOTAL];
			freeTimeList = new FreeTime();
			Labor = new int[new UserInfoList().getList().size()];
			LaborID = new int[Labor.length];
			LaborName = new String[Labor.length];
		}
		else {
			 List = file.getWorkshift().getWorkShift();
			 freeTimeList = file.getWorkshift().getFreeTimeList();
			 Labor = file.getWorkshift().getLabor();
			 LaborID = file.getWorkshift().getLaborID();
			 LaborName = file.getWorkshift().getLaborName();
		}
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
	public boolean setFreeTime(int ID, boolean free[]){
		return freeTimeList.setFreeTime(ID, free);
	}
	public void setWorkShift(){
		new FileSystem().setWorkshiftSchedule(this);
	}
	public boolean isWorkShiftSet(){
		return List == null ? false : true;
	}
	public boolean deleFreeTime(int id, int time){
		return freeTimeList.deleteFreeTime(id, time);
	}
	public String[][] createWorkShiftSchedule(){
		if (!freeTimeList.isFreetimeset()){
			return null;
		}
		Sorting(1);
		List[0] = Scheduling(WORKTOTAL, 1);
		for (int i = 1; i < SHIFTTOTAL; i++){
			List[i] = Scheduling(WORKTOTAL, 0);
		}
		return List;
	}
	public void creatLabor(){
		int k = 0;
		int id = 0;
		UserInfoList userInfo = new UserInfoList();
		for (int i = 0; i < 14; i++){
			for (int j = 0; j < 3; j++){
				if (List[j][i]==null)
					continue;
				Iterator itr = userInfo.getList().entrySet().iterator();
				while (itr.hasNext()){
					Map.Entry entry = (Map.Entry)itr.next();
					if(userInfo.getUserName((Integer)entry.getKey()).equals(List[j][i])){
						id = (Integer)entry.getKey();
						break;
					}
				}
				int pos = -1;
				if (k!=0)
					for (int t = 0; t < k; t++){
						if (LaborID[t]==id){
							pos = t;
							break;
						}
					}
				if (pos!=-1){
					Labor[pos] += TimeOfWork[i];
				}
				else {
					LaborID[k] = id;
					LaborName[k] = new String(userInfo.getUserName(id));
					Labor[k] = TimeOfWork[i];
					k++;
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
		worklist.LaborID[i] = -1;
	}
	worklist.creatLabor();
	FileSystem file = new FileSystem();
	file.setWorkshiftSchedule(worklist);
	WorkShiftList test = file.getWorkshift();
}
}

class FreeTime implements Serializable{
	private employee[]emp;//员工
	private employee[]admin;//经理
	private boolean empty;
	public FreeTime(){
		UserInfoList userInfo = new UserInfoList();
		int adminNum = 0;
		int empNum = 0;
		Iterator itr = userInfo.getList().entrySet().iterator();
		while (itr.hasNext()){
			Map.Entry entry = (Map.Entry)itr.next();
			if (userInfo.getUserType((Integer)entry.getKey()) == 1){
				adminNum++;
			}
			else if (userInfo.getUserType((Integer)entry.getKey()) == 0){
				empNum++;
			}
		}
		emp = new employee[empNum];	admin = new employee[adminNum];
		itr = userInfo.getList().entrySet().iterator();
		int i = 0; int j = 0;
		while (itr.hasNext()){
			Map.Entry entry = (Map.Entry)itr.next();
			if (userInfo.getUserType((Integer)entry.getKey()) == 1){
				admin[i] = new employee(userInfo.getUserName((Integer)entry.getKey()), (Integer)entry.getKey());
				i++;
			}
			else if (userInfo.getUserType((Integer)entry.getKey()) == 0){
				emp[j] = new employee(userInfo.getUserName((Integer)entry.getKey()), (Integer)entry.getKey());
				j++;
			}
		}
		empty = true;
	}
	public boolean setFreeTime(int ID, boolean free[]){
		for (int i = 0; i < emp.length; i++){
			if (ID == emp[i].getID()){
				emp[i].setfreetime(free);
				empty = false;
				return true;
			}
		}
		for (int i = 0; i < admin.length; i++){
			if (ID == admin[i].getID()){
				admin[i].setfreetime(free);
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
	public boolean deleteFreeTime(int id, int time){
		for (int i = 0; i < emp.length; i++){
			if (emp[i].getID() == id){
				emp[i].deleteS(time);
				return true;
			}
		}
		for (int i = 0; i < admin.length; i++){
			if (admin[i].getID()==id){
				admin[i].deleteS(time);
				return true;
			}
		}
		return false;
	}
}

class employee implements Serializable{//每个雇员包括name 、ID 、空闲时间、职位Type四个属性。
	int[]basicfreetime;
	String name;int ID;int[]freetime;
	
	public String getName(){
		return name;
	}
	
	public int getID(){
		return ID;
	}
	
	public employee(String s,int i,int[]f){
		name=s;ID=i;freetime=f;
	}
	public employee(String s, int i){
		name = s; ID = i;
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
		freetime=new int[m];
		for(int i=0;i<select.length;i++){
			if(select[i]){freetime[k++]=i;}
		}
		basicfreetime=freetime;
		}
}