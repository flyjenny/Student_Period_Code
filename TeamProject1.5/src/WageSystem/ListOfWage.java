package WageSystem;

/**
 * Copyright ® 2010 Shanghai.SJTU.BennyChan
 */

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.*;
import jxl.*;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import FileSystem.*;
/*
 * 记录工资制度的一个类
 * 包括员工、经理的基本工资
 */
class WageSystem{
	private static double $employeewageperhour;//员工每小时基本工资
	private static double $managerwageperhour;	//经理每小时基本工资
	
	WageSystem(double employeewageperhour,double managerwageperhour){
		$employeewageperhour=employeewageperhour;
		$managerwageperhour =managerwageperhour;
		
	}
	public static double getEmployeeWagePerHour(){
		return $employeewageperhour;
	}
	public static double getManagerWagePerHour(){
		return $managerwageperhour;
	}
}

/*
 * 储存计算员工工资所需要的个人信息
 */
class PersonalWageInfo implements Serializable{
//	private WageSystem $system;	
	private int $mark;					//信息中传入是经理还是普通员工的标记，0为员工，1为经理
	private String $ID;					//员工学号
	private String $name;					//姓名
	private double $hours;				//工作时长
	private double $bonus;				//奖金
	private double $fine;				//罚金
	private double $total;				//最终工资
	private String $remark;				//备注
	PersonalWageInfo(int mark,String id,String name,double hours,double bonus,double fine,String remark){
		$ID=id;
		$name=name;
		$mark=mark;
		$hours=hours;
		$bonus=bonus;
		$fine =fine ;
		$remark=remark;
		
		if ($mark==0)			//当$mark==0以员工工资制度计算
		$total=$hours*WageSystem.getEmployeeWagePerHour()+$bonus-$fine;
		if ($mark==1)			//当$mark==1以经理工资制度计算
		$total=$hours*WageSystem.getManagerWagePerHour()+$bonus-$fine;
	}
	public String getId(){
		return $ID;
	}
	public String getName(){
		return $name;
	}
	public double getHours(){
		return $hours;
	}
	public double getBonus(){
		return $bonus;
	}
	public double getFine(){
		return $fine;
	}
	public String getRemark(){
		return $remark;
	}
	public double getTotal(){
		return $total;
	}
	public int getMark(){
		return $mark;
	}
}
/*
 * 生成一个以ID为key，value为PersonalWageInfo的hashmap工资表
 */
public class ListOfWage implements Serializable{
	private Map<String,PersonalWageInfo> WageList;		//记录工资的HashMap
	
	public ListOfWage(){
		WageList=new HashMap<String,PersonalWageInfo>();
			
	}
	public void addToList(String id, String name, int status, double wage_bonus, double wage_fine, double wage_final, String wage_comment, double labor_hour){
		PersonalWageInfo newinfo = new PersonalWageInfo(status, id, name, labor_hour, wage_bonus, wage_fine, wage_comment);
		makeList(id, newinfo);
	}
	
	//根据得到的ID和学生个人工资情况添加到HashMap中
	public void makeList(String id,PersonalWageInfo info){
		WageList.put(id, info);
	}
	public Map<String,PersonalWageInfo> getWageList(){
		return WageList;
	}

	public PersonalWageInfo search(String ID){
		return WageList.get(ID);
	}
	
	public String getName(String id){
		return WageList.get(id).getName();
	}
	
	public double getWage_bonus(String id){
		return WageList.get(id).getBonus();
	}
	
	public double getWage_fine(String id){
		return WageList.get(id).getFine();
	}
	
	public double getLabor_hour(String id){
		return WageList.get(id).getHours();
	}
	
	public double getWage_total(String id){
		return WageList.get(id).getTotal();
	}
	
	public String getWage_comment(String id){
		return WageList.get(id).getRemark();
	}
	
	public int getStatus(String id){
		return WageList.get(id).getMark();
	}
	
	public Map<String,PersonalWageInfo> search(){
		return WageList;
	}
	
	public void output(String path) throws RowsExceededException, WriteException, IOException{
		jxl.write.WritableWorkbook wb=null;
		try
		{
			OutputStream os=new FileOutputStream(path==null?"WageList.xls":path);
			wb=Workbook.createWorkbook(os);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		jxl.write.WritableSheet ws = wb.createSheet("Sheet 1", 0);
		
		jxl.write.Label labela  = new jxl.write.Label (0, 0, "学号");
		jxl.write.Label labelb  = new jxl.write.Label (1, 0, "姓名");
		jxl.write.Label labelc  = new jxl.write.Label (2, 0, "工作时间（小时）");
		jxl.write.Label labeld  = new jxl.write.Label (3, 0, "奖金（元）");
		jxl.write.Label labele  = new jxl.write.Label (4, 0, "罚金（元）");
		jxl.write.Label labelf  = new jxl.write.Label (5, 0, "总计（元）");
		jxl.write.Label labelg  = new jxl.write.Label (6, 0, "备注");
		ws.addCell(labela);
		ws.addCell(labelb);
		ws.addCell(labelc);
		ws.addCell(labeld);
		ws.addCell(labele);
		ws.addCell(labelf);
		ws.addCell(labelg);
		
	
		Iterator iter=WageList.entrySet().iterator();
		int i=1;
		while(iter.hasNext()){
			Map.Entry entry=(Map.Entry) iter.next();
			jxl.write.Label  labelA = new jxl.write.Label(0, i, ((PersonalWageInfo) entry.getValue()).getId());
			jxl.write.Label  labelB = new jxl.write.Label (1, i, ((PersonalWageInfo) entry.getValue()).getName());
			jxl.write.Number labelC = new jxl.write.Number(2, i, ((PersonalWageInfo) entry.getValue()).getHours());
			jxl.write.Number labelD = new jxl.write.Number(3, i, ((PersonalWageInfo) entry.getValue()).getBonus());
			jxl.write.Number labelE = new jxl.write.Number(4, i, ((PersonalWageInfo) entry.getValue()).getFine());
			jxl.write.Number labelF = new jxl.write.Number(5, i, ((PersonalWageInfo) entry.getValue()).getTotal());
			jxl.write.Label  labelG = new jxl.write.Label (6, i, ((PersonalWageInfo) entry.getValue()).getRemark());
			ws.addCell(labelA);
			ws.addCell(labelB);
			ws.addCell(labelC);
			ws.addCell(labelD);
			ws.addCell(labelE);
			ws.addCell(labelF);
			ws.addCell(labelG);
			i++;
		}
		ws.setColumnView(2, 15);
		ws.setColumnView(6, 30);
		wb.write();
		wb.close();
		//	System.out.println("ID: "+entry.getKey()+" "+((PersonalWageInfo) entry.getValue()).getTotal()+"元");
	}
/*	public static void main(String[] args){
		PersonalWageInfo[] info={new PersonalWageInfo(1,12,"王玮",20,30,5,"全勤奖，迟到1次"),
				 new PersonalWageInfo(0,13,"陈斌",18,30,10,"全勤奖，迟到2次"),
				 new PersonalWageInfo(1,14,"王晓东",22,30,15,"全勤奖，迟到3次"),
				 new PersonalWageInfo(0,15,"张建涛",15,30,20,"全勤奖，迟到4次"),
				 new PersonalWageInfo(1,16,"关晟",21,30,25,"全勤奖，迟到5次"),
				 new PersonalWageInfo(0,17,"蒋星燃",12,30,5, "全勤奖，迟到1次"),
				 new PersonalWageInfo(0,18,"谭立明",19,30,10,"全勤奖，迟到2次"),
				 new PersonalWageInfo(1,19,"孙振喆",20,30,20,"全勤奖，迟到4次"),
				 new PersonalWageInfo(0,20,"段晓峰",23,30,15,"全勤奖，迟到3次"),
				};
		ListOfWage List =new ListOfWage();

		for (int i=0;i<info.length;i++){
			List.makeList(info[i].getId(), info[i]);
		}
		FileSystem file = new FileSystem();
		file.setSalary(List);
		ListOfWage test = file.getSalary();
	}*/
}

/*
 * 查询工资
 */
/*class WageSearch{
	private ListOfWage $Wage;	//传入一个工资表
	private int $mark;			//传入标记，0代表查询个人工资，1代表查询所有人工资
	private int $ID;			//个人学号
	WageSearch(int mark,int id,ListOfWage WageList){
		$mark=mark;
		$ID=id;
		$Wage=WageList;
	}
	public void search(){ 
		
		
		if ($mark==0){			//$mark==0查询个人工资
			PersonalWageInfo temp=$Wage.getWageList().get($ID);
			System.out.println("ID: "+$ID+" "+temp.getTotal()+"元");
		}
		if ($mark==1){			//$mark==1查询所有人工资
			Iterator iter=$Wage.getWageList().entrySet().iterator();
			while(iter.hasNext()){
				Map.Entry entry=(Map.Entry) iter.next();
				System.out.println("ID: "+entry.getKey()+" "+((PersonalWageInfo) entry.getValue()).getTotal()+"元");
			}
			
		}
	}
}*/

