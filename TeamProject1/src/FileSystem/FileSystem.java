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
import java.util.ArrayList;

import WageSystem.*;
import infoManage.*;
import WorkShiftSystem.*;


public class FileSystem {
	File FreetimeSource;
	File WorkscheduleSource;
	File SalarySource;
	File UserInfoSource;
	public FileSystem(){
		UserInfoSource = new File("UserInfo.dat");
		FreetimeSource = new File("Freetime.dat");
		WorkscheduleSource = new File("WorkSchedule.dat");
		SalarySource = new File("Salary.dat");
	}
	
	public UserInfoList getUserInfoList(){
		try{
			ObjectInputStream fin_obj = new ObjectInputStream(new FileInputStream(UserInfoSource));
			UserInfoList List = (UserInfoList)fin_obj.readObject();
			fin_obj.close();
			return List;
		}catch(IOException e){
		}catch (ClassNotFoundException e){
		}
		return null;
	}
	public void setUserInfoList(UserInfoList List){
		try{
			ObjectOutputStream fout_obj = new ObjectOutputStream(new FileOutputStream(UserInfoSource));
			fout_obj.writeObject(List);
			fout_obj.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public WorkShiftList getWorkshift(){
		try{
			ObjectInputStream fin_obj = new ObjectInputStream(new FileInputStream(WorkscheduleSource));
			WorkShiftList List = (WorkShiftList)fin_obj.readObject();
			fin_obj.close();
			return List;
		}catch(IOException e){
		}catch (ClassNotFoundException e){
		}
		return null;
	}
	public void setWorkshiftSchedule(WorkShiftList List){
		try{
			ObjectOutputStream fout_obj = new ObjectOutputStream(new FileOutputStream(WorkscheduleSource));
			fout_obj.writeObject(List);
			fout_obj.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
	public ListOfWage getSalary(){
		try{
			ObjectInputStream fin_obj = new ObjectInputStream(new FileInputStream(SalarySource));
			int total = fin_obj.read();
			ListOfWage List = (ListOfWage)fin_obj.readObject();
			fin_obj.close();
			return List;
		}catch(IOException e){
		}catch (ClassNotFoundException e){
		}
		return null;
	}
	public void setSalary(ListOfWage List){
		try{
			ObjectOutputStream fout_obj = new ObjectOutputStream(new FileOutputStream(SalarySource));
			fout_obj.writeObject(List);
			fout_obj.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}
}